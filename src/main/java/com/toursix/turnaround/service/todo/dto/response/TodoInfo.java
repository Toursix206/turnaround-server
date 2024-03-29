package com.toursix.turnaround.service.todo.dto.response;

import com.toursix.turnaround.domain.activity.ActivityCategory;
import com.toursix.turnaround.domain.todo.Todo;
import com.toursix.turnaround.domain.todo.TodoStatus;
import com.toursix.turnaround.service.todo.TodoServiceUtils;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
public class TodoInfo {

    private Long todoId;
    private String activityName;
    private ActivityCategory activityCategory;
    private TodoStatus todoStatus;
    private String leftTime;

    public static TodoInfo of(LocalDateTime now, Todo todo) {
        return TodoInfo.builder()
                .todoId(todo.getId())
                .activityName(todo.getActivity().getName())
                .activityCategory(todo.getActivity().getCategory())
                .todoStatus(TodoStatus.get(now, todo))
                .leftTime(TodoServiceUtils.todoInfoLeftTime(now, todo))
                .build();
    }
}
