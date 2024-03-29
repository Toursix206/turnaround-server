package com.toursix.turnaround.service.todo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.toursix.turnaround.domain.activity.ActivityCategory;
import com.toursix.turnaround.domain.activity.ActivityType;
import com.toursix.turnaround.domain.interior.InteriorType;
import com.toursix.turnaround.domain.todo.PushStatus;
import com.toursix.turnaround.domain.todo.Todo;
import com.toursix.turnaround.service.activity.dto.response.ActivityInfo;
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
public class TodoInfoResponse extends ActivityInfo {

    // TODO rewardItem 확정되면 수정하기
    private int point;
    private InteriorType rewardItem;
    private String leftTime;
    private boolean isAfterStartAt;
    private PushStatus pushStatus;

    @JsonProperty("isAfterStartAt")
    public boolean isAfterStartAt() {
        return isAfterStartAt;
    }

    @Builder(access = AccessLevel.PRIVATE)
    public TodoInfoResponse(Long activityId, ActivityCategory category, ActivityType type, String name,
            int point, int broom, InteriorType rewardItem, String leftTime, boolean isAfterStartAt,
            PushStatus pushStatus) {
        super(activityId, category, type, name, broom);
        this.point = point;
        this.rewardItem = rewardItem;
        this.leftTime = leftTime;
        this.isAfterStartAt = isAfterStartAt;
        this.pushStatus = pushStatus;
    }

    public static TodoInfoResponse of(LocalDateTime now, Todo todo) {
        return TodoInfoResponse.builder()
                .activityId(todo.getActivity().getId())
                .category(todo.getActivity().getCategory())
                .type(todo.getActivity().getType())
                .name(todo.getActivity().getName())
                .point(todo.getActivity().getPoint())
                .broom(todo.getActivity().getBroom())
                .rewardItem(null)
                .leftTime(TodoServiceUtils.todoDetailInfoLeftTime(now, todo))
                .isAfterStartAt(now.isAfter(todo.getStartAt()))
                .pushStatus(todo.getPushStatus())
                .build();
    }
}
