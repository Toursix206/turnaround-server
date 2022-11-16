package com.toursix.turnaround.service.user.dto.request;

import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateMyPageSettingRequestDto {

    @NotNull(message = "{user.agreeAllPushNotification.notNull}")
    private Boolean agreeAllPushNotification;
}
