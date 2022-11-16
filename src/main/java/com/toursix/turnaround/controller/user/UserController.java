package com.toursix.turnaround.controller.user;

import com.toursix.turnaround.common.dto.ErrorResponse;
import com.toursix.turnaround.common.dto.SuccessResponse;
import com.toursix.turnaround.config.interceptor.Auth;
import com.toursix.turnaround.config.resolver.UserId;
import com.toursix.turnaround.service.user.UserService;
import com.toursix.turnaround.service.user.dto.request.NicknameValidateRequestDto;
import com.toursix.turnaround.service.user.dto.request.UpdateMyPageSettingRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "User")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class UserController {

    private final UserService userService;

    @ApiOperation(
            value = "온보딩 페이지 - 닉네임 중복 여부를 확인합니다.",
            notes = "닉네임 중복 여부를 전달합니다. 중복인 경우 409, 중복이 아닌 경우 200을 보냅니다."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공입니다."),
            @ApiResponse(
                    code = 400,
                    message = "1. 닉네임을 입력해주세요. (nickname)\n"
                            + "2. 닉네임은 최대 8자까지 가능합니다. (nickname)",
                    response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "토큰이 만료되었습니다. 다시 로그인 해주세요.", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "탈퇴했거나 존재하지 않는 유저입니다.", response = ErrorResponse.class),
            @ApiResponse(code = 409, message = "이미 존재하는 닉네임입니다.", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생하였습니다.", response = ErrorResponse.class)
    })
    @PostMapping("/user/nickname/check")
    public ResponseEntity<SuccessResponse<String>> updateUserInfo(
            @Valid @RequestBody NicknameValidateRequestDto request) {
        userService.validateUniqueNickname(request);
        return SuccessResponse.OK;
    }

    @ApiOperation(value = "[인증] 마이 페이지 - 마이페이 설정 상태를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공입니다."),
            @ApiResponse(code = 400,
                    message = "1. 유저의 알림 여부를 입력해주세요. (agreeAllPushNotification)\n"
                            + "2. 잘못된 알림 상태입니다.",
                    response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "토큰이 만료되었습니다. 다시 로그인 해주세요.", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "탈퇴했거나 존재하지 않는 유저입니다.", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생하였습니다.", response = ErrorResponse.class)
    })
    @Auth
    @PutMapping("/user/setting")
    public ResponseEntity<SuccessResponse<String>> updateMyPageSetting(
            @Valid @RequestBody UpdateMyPageSettingRequestDto request, @ApiIgnore @UserId Long userId) {
        userService.updateMyPageSetting(request, userId);
        return SuccessResponse.OK;
    }
}
