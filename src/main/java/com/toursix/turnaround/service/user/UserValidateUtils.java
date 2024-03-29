package com.toursix.turnaround.service.user;

import static com.toursix.turnaround.common.exception.ErrorCode.CONFLICT_NICKNAME_EXCEPTION;
import static com.toursix.turnaround.common.exception.ErrorCode.CONFLICT_USER_EXCEPTION;
import static com.toursix.turnaround.common.exception.ErrorCode.VALIDATION_STATUS_EXCEPTION;

import com.toursix.turnaround.common.exception.ConflictException;
import com.toursix.turnaround.common.exception.ValidationException;
import com.toursix.turnaround.domain.user.User;
import com.toursix.turnaround.domain.user.UserSocialType;
import com.toursix.turnaround.domain.user.repository.OnbordingRepository;
import com.toursix.turnaround.domain.user.repository.UserRepository;
import com.toursix.turnaround.service.user.dto.request.UpdateMyPageSettingRequestDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserValidateUtils {

    static void validateNotExistsUser(UserRepository userRepository, String socialId, UserSocialType socialType) {
        if (userRepository.existsBySocialIdAndSocialType(socialId, socialType)) {
            throw new ConflictException(String.format("이미 존재하는 유저 (%s - %s) 입니다", socialId, socialType),
                    CONFLICT_USER_EXCEPTION);
        }
    }

    public static void validateNickname(OnbordingRepository onbordingRepository, String nickname) {
        if (onbordingRepository.existsByNickname(nickname)) {
            throw new ConflictException(String.format("이미 존재하는 닉네임 (%s) 입니다", nickname),
                    CONFLICT_NICKNAME_EXCEPTION);
        }
    }

    public static void validateIsPushState(UpdateMyPageSettingRequestDto reqeust, User user) {
        if (reqeust.getAgreeAllPushNotification() != null) {
            if (reqeust.getAgreeAllPushNotification() == user.getSetting().getAgreeAllPushNotification()) {
                throw new ValidationException(String.format("(%s) 유저의 알림 상태 중복입니다.", user.getId()),
                        VALIDATION_STATUS_EXCEPTION);
            }
        }
    }
}
