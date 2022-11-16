package com.toursix.turnaround.service.notification;

import com.toursix.turnaround.domain.notification.PushMessage;
import com.toursix.turnaround.domain.todo.PushStatus;
import com.toursix.turnaround.domain.todo.Todo;
import com.toursix.turnaround.domain.user.Onboarding;
import com.toursix.turnaround.domain.user.User;
import com.toursix.turnaround.service.firebase.FirebaseCloudMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class NotificationService {

    private final FirebaseCloudMessageService firebaseCloudMessageService;

    public void sendBeforeOneHourTodoNotification(User to, Todo todo) {
        if (to.getSetting().getAgreeAllPushNotification() && todo.getPushStatus() == PushStatus.ON) {
            firebaseCloudMessageService.sendMessageTo(to, PushMessage.BEFORE_ONE_HOUR_TODO.getTitle(),
                    beforeOneHourTodoPushBody(to.getOnboarding()));
        }
    }

    public void sendStartTodoNotification(User to, Todo todo) {
        if (to.getSetting().getAgreeAllPushNotification() && todo.getPushStatus() == PushStatus.ON) {
            firebaseCloudMessageService.sendMessageTo(to, PushMessage.START_TODO.getTitle(),
                    PushMessage.START_TODO.getBody());
        }
    }

    public void sendRemindTodoNotification(User to, Todo todo) {
        if (to.getSetting().getAgreeAllPushNotification() && todo.getPushStatus() == PushStatus.ON) {
            firebaseCloudMessageService.sendMessageTo(to, PushMessage.REMIND_TODO.getTitle(),
                    PushMessage.REMIND_TODO.getBody());
        }
    }

    public void sendRemindRewardNotification(User to, Todo todo) {
        if (to.getSetting().getAgreeAllPushNotification()) {
            firebaseCloudMessageService.sendMessageTo(to, PushMessage.REMIND_REWARD.getTitle(),
                    remindRewardPushBody(to.getOnboarding()));
        }
    }

    private String beforeOneHourTodoPushBody(Onboarding onboarding) {
        return String.format("%s%s", onboarding.getNickname(), PushMessage.BEFORE_ONE_HOUR_TODO.getBody());
    }

    private String remindRewardPushBody(Onboarding onboarding) {
        return String.format("%s%s", onboarding.getNickname(), PushMessage.REMIND_REWARD.getBody());
    }
}
