package com.toursix.turnaround.controller.home;

import com.toursix.turnaround.common.dto.ErrorResponse;
import com.toursix.turnaround.common.dto.SuccessResponse;
import com.toursix.turnaround.common.success.SuccessCode;
import com.toursix.turnaround.config.interceptor.Auth;
import com.toursix.turnaround.config.resolver.UserId;
import com.toursix.turnaround.service.home.HomeRetrieveService;
import com.toursix.turnaround.service.home.dto.response.HomeMainResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Home")
@Validated
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class HomeRetrieveController {

    private final HomeRetrieveService homeRetrieveService;

    @ApiOperation(value = "[인증] 홈 페이지 - 홈 화면에 표시할 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "홈 메인 정보 조회 성공입니다."),
            @ApiResponse(code = 401, message = "토큰이 만료되었습니다. 다시 로그인 해주세요.", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "탈퇴했거나 존재하지 않는 유저입니다.", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생하였습니다.", response = ErrorResponse.class)
    })
    @Auth
    @GetMapping("/home")
    public ResponseEntity<SuccessResponse<HomeMainResponse>> getHomeMainInfo(@ApiIgnore @UserId Long userId) {
        return SuccessResponse.success(SuccessCode.GET_HOME_MAIN_INFO_SUCCESS,
                homeRetrieveService.getHomeMainInfo(userId));
    }
}
