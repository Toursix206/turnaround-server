package com.toursix.turnaround.service.todo.dto.response;

import com.toursix.turnaround.domain.activity.ActivityCategory;
import com.toursix.turnaround.domain.activity.ActivityType;
import com.toursix.turnaround.domain.interior.InteriorType;
import com.toursix.turnaround.domain.todo.Todo;
import com.toursix.turnaround.service.activity.dto.response.ActivityInfo;
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

    @Builder(access = AccessLevel.PRIVATE)
    public TodoInfoResponse(Long activityId, ActivityCategory category, ActivityType type, String name,
            int point, int broom, InteriorType rewardItem) {
        super(activityId, category, type, name, broom);
        this.point = point;
        this.rewardItem = rewardItem;
    }

    public static TodoInfoResponse of(Todo todo) {
        return TodoInfoResponse.builder()
                .activityId(todo.getActivity().getId())
                .category(todo.getActivity().getCategory())
                .type(todo.getActivity().getType())
                .name(todo.getActivity().getName())
                .point(todo.getActivity().getPoint())
                .broom(todo.getActivity().getBroom())
                .rewardItem(null)
                .build();
    }
}
