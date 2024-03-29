package com.toursix.turnaround.service.firebase;

import static com.toursix.turnaround.common.exception.ErrorCode.INTERNAL_SERVER_EXCEPTION;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.toursix.turnaround.common.exception.InternalServerException;
import com.toursix.turnaround.common.util.HttpHeaderUtils;
import com.toursix.turnaround.common.util.JwtUtils;
import com.toursix.turnaround.common.util.YamlPropertySourceFactory;
import com.toursix.turnaround.domain.user.User;
import com.toursix.turnaround.external.client.firebase.FirebaseApiClient;
import com.toursix.turnaround.service.firebase.dto.request.FcmMessage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
@PropertySource(value = "classpath:application-firebase.yml", factory = YamlPropertySourceFactory.class, ignoreResourceNotFound = true)
public class FirebaseCloudMessageService {

    @Value("${cloud.firebase.config.path}")
    private String firebaseConfigPath;

    private final ObjectMapper objectMapper;
    private final FirebaseApiClient firebaseApiCaller;
    private final JwtUtils jwtProvider;

    public void sendMessageTo(User to, String title, String body) {
        try {
            String targetToken = to.getFcmToken();
            String message = makeMessage(targetToken, title, body);
            firebaseApiCaller.requestFcmMessaging(HttpHeaderUtils.withBearerToken(getAccessToken()),
                    message);
        } catch (Exception exception) {
            jwtProvider.expireRefreshToken(to.getId());
            to.resetFcmToken();
            log.error(exception.getMessage(), exception);
        }
    }

    private String makeMessage(String targetToken, String title, String body) {
        FcmMessage fcmMessage = FcmMessage.builder()
                .validateOnly(false)
                .message(FcmMessage.Message.builder()
                        .notification(FcmMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .build())
                        .android(FcmMessage.Android.builder()
                                .data(FcmMessage.Data.builder()
                                        .title(title)
                                        .body(body)
                                        .build())
                                .build())
                        .apns(FcmMessage.Apns.builder()
                                .payload(FcmMessage.Payload.builder()
                                        .aps(FcmMessage.Aps.builder()
                                                .alert(FcmMessage.Alert.builder()
                                                        .title(title)
                                                        .body(body)
                                                        .build())
                                                .build())
                                        .build())
                                .build())
                        .token(targetToken)
                        .build())
                .build();
        try {
            return objectMapper.writeValueAsString(fcmMessage);
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            throw new InternalServerException("FCM makeMessage exception",
                    INTERNAL_SERVER_EXCEPTION);
        }
    }

    private String getAccessToken() {
        try {
            GoogleCredentials googleCredentials = GoogleCredentials
                    .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                    .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
            googleCredentials.refreshIfExpired();
            return googleCredentials.getAccessToken().getTokenValue();
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            throw new InternalServerException("FCM getAccessToken exception",
                    INTERNAL_SERVER_EXCEPTION);
        }
    }
}
