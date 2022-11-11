package com.toursix.turnaround.service.todo.dto.response;

import com.toursix.turnaround.domain.todo.Todo;
import java.time.LocalDateTime;
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
public class TodoMainResponse {

    private int successTodosCnt;
    private int todayTodosCnt;
    private int thisWeekTodosCnt;
    private int nextTodosCnt;
    private List<TodoInfo> successTodos;
    private List<TodoInfo> todayTodos;
    private List<TodoInfo> thisWeekTodos;
    private List<TodoInfo> nextTodos;

    public static TodoMainResponse of(LocalDateTime now, List<Todo> successTodos, List<Todo> todayTodos,
            List<Todo> thisWeekTodos, List<Todo> nextTodos) {
        return TodoMainResponse.builder()
                .successTodosCnt(successTodos.size())
                .todayTodosCnt(todayTodos.size())
                .thisWeekTodosCnt(thisWeekTodos.size())
                .nextTodosCnt(nextTodos.size())
                .successTodos(successTodos.stream()
                        .map(successTodo -> TodoInfo.of(now, successTodo))
                        .collect(Collectors.toList()))
                .todayTodos(todayTodos.stream()
                        .map(todayTodo -> TodoInfo.of(now, todayTodo))
                        .collect(Collectors.toList()))
                .thisWeekTodos(thisWeekTodos.stream()
                        .map(thisWeekTodo -> TodoInfo.of(now, thisWeekTodo))
                        .collect(Collectors.toList()))
                .nextTodos(nextTodos.stream()
                        .map(nextTodo -> TodoInfo.of(now, nextTodo))
                        .collect(Collectors.toList()))
                .build();
    }
}
