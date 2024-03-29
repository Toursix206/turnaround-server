package com.toursix.turnaround.service.activity.dto.response;

import com.toursix.turnaround.domain.activity.ActivityGuide;
import com.toursix.turnaround.domain.common.Status;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
public class ActivityGuideResponse {

    private Long activityId;
    private String name;
    List<ActivityGuideInfo> guides;

    public static ActivityGuideResponse of(Long activityId, String name, List<ActivityGuideInfo> guides) {
        return ActivityGuideResponse.builder()
                .activityId(activityId)
                .name(name)
                .guides(guides)
                .build();
    }

    @ToString
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    public static class ActivityGuideInfo {

        private int step;
        private String title;
        private String content;
        private String imageUrl;

        public static ActivityGuideInfo of(ActivityGuide activityGuide) {
            return ActivityGuideInfo.builder()
                    .step(activityGuide.getStep())
                    .title(activityGuide.getTitle())
                    .content(activityGuide.getContent())
                    .imageUrl(activityGuide.getImageUrl())
                    .build();
        }

        public static List<ActivityGuideInfo> of(List<ActivityGuide> activityGuides) {
            return activityGuides.stream().sorted(Comparator.comparing(ActivityGuide::getStep))
                    .filter(activityGuide -> activityGuide.getStatus() == Status.ACTIVE)
                    .map(ActivityGuideInfo::of).collect(Collectors.toList());
        }
    }
}
