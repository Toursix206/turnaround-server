package com.toursix.turnaround.service.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateMyPageSettingRequestDto {

    @NotNull(message = "{user.agreeBenefitAndEvent.notNull}")
    private Boolean agreeBenefitAndEvent;

    @JsonProperty("agreeBenefitAndEvent")
    public Boolean isAgreeBenefitAndEvent() {
        return agreeBenefitAndEvent;
    }
}