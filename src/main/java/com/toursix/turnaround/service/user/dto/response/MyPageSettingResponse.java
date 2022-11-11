package com.toursix.turnaround.service.user.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class MyPageSettingResponse {

    private Boolean agreeBenefitAndEvent;

    @JsonProperty("agreeBenefitAndEvent")
    public Boolean isAgreeBenefitAndEvent() {
        return agreeBenefitAndEvent;
    }

    public static MyPageSettingResponse of(boolean agreeBenefitAndEvent) {
        return MyPageSettingResponse.builder()
                .agreeBenefitAndEvent(agreeBenefitAndEvent)
                .build();
    }
}
