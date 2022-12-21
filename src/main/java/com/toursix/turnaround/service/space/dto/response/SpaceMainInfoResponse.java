package com.toursix.turnaround.service.space.dto.response;

import com.toursix.turnaround.common.util.MathUtils;
import com.toursix.turnaround.domain.common.Status;
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

    private String spaceType;
    private int interiorCount;
    private int level;
    private int experience;
    private int broom;
    private int cleanScore;
    private List<InteriorInfo> interiors;
    //TODO 토스트 메시지 정책 확정되면 반영

    public static SpaceMainInfoResponse of(Onboarding onboarding, Acquire acquire) {
        return SpaceMainInfoResponse.builder()
                .spaceType(acquire.getSpace().getCategory().getName().getValue())
                .interiorCount(onboarding.getObtains().size())
                .level(onboarding.getLevel())
                //TODO 레벨, 경험치 정책 확정되면 수정
                .experience(MathUtils.percent(onboarding.getExperience(), 100))
                .broom(onboarding.getItem().getBroom())
                .cleanScore(acquire.getCleanScore())
                .interiors(onboarding.getObtains().stream()
                        .filter(obtain -> obtain.getStatus() == Status.ACTIVE)
                        .filter(Obtain::getIsEquipped)
                        .map(InteriorInfo::of)
                        .collect(Collectors.toList()))
                .build();
    }
}
