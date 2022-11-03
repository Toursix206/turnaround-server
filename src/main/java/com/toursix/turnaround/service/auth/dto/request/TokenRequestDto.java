package com.toursix.turnaround.service.auth.dto.request;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenRequestDto {

    @ApiModelProperty(value = "토큰 - accessToken", example = "eyJhbGciOiJIUzUxMiJ9.eyJVU0VSX0lEIjoxLCJleHAiOjE2NTg4NDA2NzN9.udnKnDSK08EuX56E5k-vkYUbZYofuo12YdiM9gEPY4eqdfzM_xt4MpgTimTuQ8ipmMxWZNCaTjtentg8vLyfgQ")
    @NotBlank(message = "{auth.accessToken.notBlank}")
    private String accessToken;

    @ApiModelProperty(value = "토큰 - refreshToken", example = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2NTk0NDM2NzN9.1L4eWqLGvob8jsTe5ZQVbmWpitVjZ0wMIoYRg6qPyum1iLaVOV_AT6nM0FtO5OrMM_9VXRWzMaON2S4E_QsxzQ")
    @NotBlank(message = "{auth.refreshToken.notBlank}")
    private String refreshToken;
}
