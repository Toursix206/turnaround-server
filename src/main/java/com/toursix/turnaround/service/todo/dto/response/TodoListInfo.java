package com.toursix.turnaround.service.todo.dto.response;

import com.toursix.turnaround.domain.activity.ActivityCategory;
import com.toursix.turnaround.domain.todo.Todo;
import com.toursix.turnaround.domain.todo.TodoStatus;
import com.toursix.turnaround.service.todo.TodoServiceUtils;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoListInfo extends TodoInfo {

    private int duration;

    @Builder(access = AccessLevel.PRIVATE)
    public TodoListInfo(Long todoId, String activityName, ActivityCategory activityCategory, TodoStatus todoStatus,
            String leftTime,
            int duration) {
        super(todoId, activityName, activityCategory, todoStatus, leftTime);
        this.duration = duration;
    }

    public static TodoListInfo of(LocalDateTime now, Todo todo) {
        return TodoListInfo.builder()
                .todoId(todo.getId())
                .activityName(todo.getActivity().getName())
                .activityCategory(todo.getActivity().getCategory())
                .todoStatus(TodoStatus.get(now, todo))
                .leftTime(TodoServiceUtils.todoInfoLeftTime(now, todo))
                .duration(todo.getActivity().getDuration())
                .build();
    }
}
