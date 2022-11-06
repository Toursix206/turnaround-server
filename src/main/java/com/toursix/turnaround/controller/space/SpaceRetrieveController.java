package com.toursix.turnaround.controller.space;

import com.toursix.turnaround.common.dto.ErrorResponse;
import com.toursix.turnaround.common.dto.SuccessResponse;
import com.toursix.turnaround.common.success.SuccessCode;
import com.toursix.turnaround.config.interceptor.Auth;
import com.toursix.turnaround.config.resolver.UserId;
import com.toursix.turnaround.service.space.SpaceRetrieveService;
import com.toursix.turnaround.service.space.dto.response.SpaceMainInfoResponse;
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

@Api(tags = "Space")
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class SpaceRetrieveController {

    private final SpaceRetrieveService spaceRetrieveService;

    @ApiOperation(
            value = "[인증] 방타버스 페이지 - 방타버스 메인 페이지에 필요한 정보를 조회합니다.",
            notes = "레벨, 빗자루, 청결도, 인테리어들의 청결도 상태를 조회합니다.\n" +
                    "MVP에서 제공되는 인테리어 이름 - BASIC_BED, BASIC_TABLE, BASIC_WALL, BASIC_WINDOW\n" +
                    "청결도 상태 높은 순서 - ONE, TWO, THREE"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "방타버스 메인 정보 조회 성공입니다."),
            @ApiResponse(code = 401, message = "토큰이 만료되었습니다. 다시 로그인 해주세요.", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "탈퇴했거나 존재하지 않는 유저입니다.", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생하였습니다.", response = ErrorResponse.class)
    })
    @Auth
    @GetMapping("/space")
    public ResponseEntity<SuccessResponse<SpaceMainInfoResponse>> getSpaceMainInfo(@ApiIgnore @UserId Long userId) {
        return SuccessResponse.success(SuccessCode.GET_SPACE_MAIN_INFO_SUCCESS,
                spaceRetrieveService.getSpaceMainInfo(userId));
    }
}
