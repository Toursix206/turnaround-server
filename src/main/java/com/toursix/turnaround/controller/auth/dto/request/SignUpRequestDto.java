package com.toursix.turnaround.controller.auth.dto.request;

import com.toursix.turnaround.domain.common.Constraint;
import com.toursix.turnaround.domain.user.OnboardingProfileType;
import com.toursix.turnaround.domain.user.UserSocialType;
import com.toursix.turnaround.service.auth.dto.request.SignUpDto;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUpRequestDto {

    @ApiModelProperty(value = "소셜 로그인 타입 - KAKAO, APPLE", example = "KAKAO")
    @NotNull(message = "{user.socialType.notNull}")
    private UserSocialType socialType;

    @ApiModelProperty(value = "토큰 - socialToken", example = "ijv4qLk0I7jYuDpFe-9A-oAx59-AAfC6UbTuairPCj1zTQAAAYI6e-6o")
    @NotBlank(message = "{auth.token.notBlank}")
    private String token;

    @ApiModelProperty(value = "토큰 - fcmToken", example = "dfdafjdslkfjslfjslifsjvmdsklvdosijiofjamvsdlkvmiodsjfdiosmvsdjvosadjvosd")
    @NotBlank(message = "{auth.fcmToken.notBlank}")
    private String fcmToken;

    @ApiModelProperty(value = "프로필 이미지", example = "ONE")
    @NotNull(message = "{onboarding.profile.NotNull}")
    private OnboardingProfileType profileType;

    @ApiModelProperty(value = "nickname", example = "혜조니")
    @NotBlank(message = "{onboarding.nickname.notBlank}")
    @Size(max = Constraint.ONBOARDING_NICKNAME_MAX, message = "{onboarding.nickname.max}")
    private String nickname;

    public SignUpDto toServiceDto() {
        return SignUpDto.of(socialType, token, fcmToken, profileType, nickname);
    }
}
