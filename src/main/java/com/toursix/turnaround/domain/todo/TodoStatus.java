package com.toursix.turnaround.domain.todo;

import java.time.LocalDateTime;

public enum TodoStatus {
    PURPLE,
    BLACK,
    WHITE,
    ;

    public static TodoStatus get(LocalDateTime now, Todo todo) {
        if (todo.getStage() == TodoStage.SUCCESS) {
            return PURPLE;
        }
        if (now.isAfter(todo.getStartAt())) {
            return BLACK;
        }
        return WHITE;
    }
}
