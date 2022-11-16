package com.toursix.turnaround.service.todo;

import com.toursix.turnaround.common.util.DateUtils;
import com.toursix.turnaround.domain.todo.PushStatus;
import com.toursix.turnaround.domain.todo.Todo;
import com.toursix.turnaround.domain.todo.TodoStage;
import com.toursix.turnaround.domain.user.Onboarding;
import com.toursix.turnaround.domain.user.User;
import com.toursix.turnaround.domain.user.repository.UserRepository;
import com.toursix.turnaround.service.notification.NotificationService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class TodoScheduledService {

    private final UserRepository userRepository;

    private final NotificationService notificationService;

    /**
     * 5분마다 실행
     */
    @Scheduled(cron = "0  0/5  *  *  *  *")
    public void scheduledBeforeOneHourTodos() {
        LocalDateTime now = DateUtils.todayLocalDateTime();
        List<User> users = userRepository.findAllActiveUser();
        users.forEach(user -> {
            Onboarding onboarding = user.getOnboarding();
            List<Todo> todos = onboarding.getTodos().stream()
                    .filter(todo -> todo.getPushStatus() == PushStatus.ON)
                    .filter(todo -> todo.getStage() == TodoStage.IN_PROGRESS)
                    .filter(todo -> DateUtils.isBeforeOneHour(now, todo.getStartAt()))
                    .collect(Collectors.toList());
            if (!todos.isEmpty()) {
                notificationService.sendBeforeOneHourTodoNotification(user);
            }
        });
    }

    /**
     * 5분마다 실행
     */
    @Scheduled(cron = "0  0/5  *  *  *  *")
    public void scheduledStartTodos() {
        LocalDateTime now = DateUtils.todayLocalDateTime();
        List<User> users = userRepository.findAllActiveUser();
        users.forEach(user -> {
            Onboarding onboarding = user.getOnboarding();
            List<Todo> todos = onboarding.getTodos().stream()
                    .filter(todo -> todo.getPushStatus() == PushStatus.ON)
                    .filter(todo -> todo.getStage() == TodoStage.IN_PROGRESS)
                    .filter(todo -> DateUtils.isSameTime(now, todo.getStartAt()))
                    .collect(Collectors.toList());
            if (!todos.isEmpty()) {
                notificationService.sendStartTodoNotification(user);
            }
        });
    }

    /**
     * 21시 0분 0초마다 실행
     */
    @Scheduled(cron = "0  0  21  *  *  *")
    public void scheduledRemindTodos() {
        LocalDateTime now = DateUtils.todayLocalDateTime();
        List<User> users = userRepository.findAllActiveUser();
        users.forEach(user -> {
            Onboarding onboarding = user.getOnboarding();
            List<Todo> todos = onboarding.getTodos().stream()
                    .filter(todo -> todo.getPushStatus() == PushStatus.ON)
                    .filter(todo -> todo.getStage() == TodoStage.IN_PROGRESS)
                    .filter(todo -> todo.getEndAt().isBefore(now))
                    .collect(Collectors.toList());
            if (!todos.isEmpty()) {
                notificationService.sendRemindTodoNotification(user);
            }
        });
    }

    /**
     * 22시 0분 0초마다 실행
     */
    @Scheduled(cron = "0  0  22  *  *  *")
    public void scheduledRemindRewards() {
        LocalDateTime now = DateUtils.todayLocalDateTime();
        List<User> users = userRepository.findAllActiveUser();
        users.forEach(user -> {
            Onboarding onboarding = user.getOnboarding();
            List<Todo> todos = onboarding.getTodos().stream()
                    .filter(todo -> todo.getStage() == TodoStage.SUCCESS)
                    .collect(Collectors.toList());
            if (!todos.isEmpty()) {
                notificationService.sendRemindRewardNotification(user);
            }
        });
    }
}
