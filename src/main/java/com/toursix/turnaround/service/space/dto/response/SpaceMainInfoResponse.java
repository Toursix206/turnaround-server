package com.toursix.turnaround.service.space.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.toursix.turnaround.common.util.MathUtils;
import com.toursix.turnaround.domain.interior.CleanLevel;
import com.toursix.turnaround.domain.interior.Obtain;
import com.toursix.turnaround.domain.space.Acquire;
import com.toursix.turnaround.domain.user.Onboarding;
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
public class SpaceMainInfoResponse {

    private int level;
    private int experience;
    private int broom;
    private int cleanScore;
    private List<InteriorInfo> interiors;
    //TODO 토스트 메시지 정책 확정되면 반영

    @ToString
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    public static class InteriorInfo {

        private Long obtainId;
        private String interiorName;
        private CleanLevel interiorCleanLevel;
        private boolean isCleanable;

        @JsonProperty("isCleanable")
        public boolean isCleanable() {
            return isCleanable;
        }
    }

    public static SpaceMainInfoResponse of(Onboarding onboarding, Acquire acquire) {
        return SpaceMainInfoResponse.builder()
                .level(onboarding.getLevel())
                //TODO 레벨, 경험치 정책 확정되면 수정
                .experience(MathUtils.percent(onboarding.getExperience(), 100))
                .broom(onboarding.getItem().getBroom())
                .cleanScore(acquire.getCleanScore())
                .interiors(onboarding.getObtains().stream()
                        .filter(Obtain::getIsEquipped)
                        .map(obtain -> InteriorInfo.builder()
                                .obtainId(obtain.getId())
                                .interiorName(obtain.getInterior().getName())
                                .interiorCleanLevel(obtain.getCleanLevel())
                                .isCleanable(obtain.getCleanLevel() != CleanLevel.CLEAN)
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
