package com.toursix.turnaround.service.home.dto.response;

import com.toursix.turnaround.domain.common.Status;
import com.toursix.turnaround.domain.interior.Obtain;
import com.toursix.turnaround.domain.space.Acquire;
import com.toursix.turnaround.domain.todo.Todo;
import com.toursix.turnaround.domain.user.Onboarding;
import com.toursix.turnaround.service.space.dto.response.InteriorInfo;
import com.toursix.turnaround.service.todo.dto.response.TodoInfo;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class HomeMainResponse {

    private String nickname;
    private int level;
    private int broom;
    private int cleanScore;
    private int todosCnt;
    private List<TodoInfo> todos;
    private List<InteriorInfo> interiors;

    public static HomeMainResponse of(LocalDateTime now, Onboarding onboarding, Acquire acquire, List<Todo> todos) {
        return HomeMainResponse.builder()
                .nickname(onboarding.getNickname())
                .level(onboarding.getLevel())
                .broom(onboarding.getItem().getBroom())
                .cleanScore(acquire.getCleanScore())
                .todosCnt(todos.size())
                .todos(todos.stream()
                        .sorted(Comparator.comparing(Todo::getStartAt))
                        .map(todo -> TodoInfo.of(now, todo))
                        .collect(Collectors.toList()))
                .interiors(onboarding.getObtains().stream()
                        .filter(obtain -> obtain.getStatus() == Status.ACTIVE)
                        .filter(Obtain::getIsEquipped)
                        .map(InteriorInfo::of)
                        .collect(Collectors.toList()))
                .build();
    }
}
