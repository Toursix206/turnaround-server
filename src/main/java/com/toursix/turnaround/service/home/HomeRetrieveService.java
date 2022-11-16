package com.toursix.turnaround.service.home;

import com.toursix.turnaround.common.util.DateUtils;
import com.toursix.turnaround.domain.space.Acquire;
import com.toursix.turnaround.domain.todo.Todo;
import com.toursix.turnaround.domain.user.Onboarding;
import com.toursix.turnaround.domain.user.User;
import com.toursix.turnaround.domain.user.repository.UserRepository;
import com.toursix.turnaround.service.home.dto.response.HomeMainResponse;
import com.toursix.turnaround.service.todo.TodoServiceUtils;
import com.toursix.turnaround.service.user.UserServiceUtils;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class HomeRetrieveService {

    private final UserRepository userRepository;

    public HomeMainResponse getHomeMainInfo(Long userId) {
        User user = UserServiceUtils.findUserById(userRepository, userId);
        Onboarding onboarding = user.getOnboarding();
        LocalDateTime now = DateUtils.todayLocalDateTime();
        //TODO 공간 여러개인 경우 고려
        Acquire acquire = onboarding.getAcquires().get(0);
        List<Todo> todos = onboarding.getTodos();
        List<Todo> inProgressTodayTodos = TodoServiceUtils.filterInProgressTodayTodos(now, todos);
        return HomeMainResponse.of(now, onboarding, acquire, inProgressTodayTodos);
    }
}
