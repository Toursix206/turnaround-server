package com.toursix.turnaround.service.space.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.toursix.turnaround.domain.interior.CleanLevel;
import com.toursix.turnaround.domain.interior.Obtain;
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
@Builder(access = AccessLevel.PROTECTED)
public class InteriorInfo {

    private Long obtainId;
    private String interiorName;
    private CleanLevel interiorCleanLevel;
    private boolean isCleanable;

    @JsonProperty("isCleanable")
    public boolean isCleanable() {
        return isCleanable;
    }

    public static InteriorInfo of(Obtain obtain) {
        return InteriorInfo.builder()
                .obtainId(obtain.getId())
                .interiorName(obtain.getInterior().getName())
                .interiorCleanLevel(obtain.getCleanLevel())
                .isCleanable(obtain.getCleanLevel() != CleanLevel.CLEAN)
                .build();
    }
}
