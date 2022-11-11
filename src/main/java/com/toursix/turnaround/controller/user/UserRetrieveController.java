package com.toursix.turnaround.controller.user;

import com.toursix.turnaround.common.dto.ErrorResponse;
import com.toursix.turnaround.common.dto.SuccessResponse;
import com.toursix.turnaround.common.success.SuccessCode;
import com.toursix.turnaround.config.interceptor.Auth;
import com.toursix.turnaround.config.resolver.UserId;
import com.toursix.turnaround.service.user.UserRetrieveService;
import com.toursix.turnaround.service.user.dto.response.MyPageHomeResponse;
import com.toursix.turnaround.service.user.dto.response.MyPageSettingResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Todo")
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class UserRetrieveController {

    private final UserRetrieveService userRetrieveService;

    @ApiOperation(value = "[인증] 마이 페이지 - 마이페이 설정 상태를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "활동 일정 리스트 조회 성공입니다."),
            @ApiResponse(code = 401, message = "토큰이 만료되었습니다. 다시 로그인 해주세요.", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "탈퇴했거나 존재하지 않는 유저입니다.", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생하였습니다.", response = ErrorResponse.class)
    })
    @Auth
    @GetMapping("/user/setting")
    public ResponseEntity<SuccessResponse<MyPageSettingResponse>> getMyPageSetting(@ApiIgnore @UserId Long userId) {
        return SuccessResponse.success(SuccessCode.GET_MYPAGE_SETTING_INFO_SUCCESS,
                userRetrieveService.getMyPageSetting(userId));
    }

    @ApiOperation(value = "[인증] 마이 페이지 - 마이페이 홈화면 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "활동 일정 리스트 조회 성공입니다."),
            @ApiResponse(code = 401, message = "토큰이 만료되었습니다. 다시 로그인 해주세요.", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "탈퇴했거나 존재하지 않는 유저입니다.", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생하였습니다.", response = ErrorResponse.class)
    })
    @Auth
    @GetMapping("/user/setting")
    public ResponseEntity<SuccessResponse<MyPageHomeResponse>> getMyPageHome(@ApiIgnore @UserId Long userId) {
        return SuccessResponse.success(SuccessCode.GET_MYPAGE_HOME_INFO_SUCCESS,
                userRetrieveService.getMyPageHome(userId));
    }
}
