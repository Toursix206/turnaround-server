package com.toursix.turnaround.service.user.dto.request;

import com.toursix.turnaround.domain.common.Constraint;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class NicknameValidateRequestDto {

    @NotBlank(message = "{onboarding.nickname.notBlank}")
    @Size(max = Constraint.ONBOARDING_NICKNAME_MAX, message = "{onboarding.nickname.max}")
    private String nickname;
}
