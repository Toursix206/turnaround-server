package com.toursix.turnaround.controller.interior;

import com.toursix.turnaround.common.dto.ErrorResponse;
import com.toursix.turnaround.common.dto.SuccessResponse;
import com.toursix.turnaround.common.success.SuccessCode;
import com.toursix.turnaround.config.interceptor.Auth;
import com.toursix.turnaround.config.resolver.UserId;
import com.toursix.turnaround.service.interior.InteriorService;
import com.toursix.turnaround.service.space.dto.response.SpaceMainInfoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Interior")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class InteriorController {

    private final InteriorService interiorService;

    @ApiOperation(
            value = "[인증] 방타버스 페이지 - 빗자루 아이템을 사용하여 인테리어를 청소합니다.",
            notes = "이미 깨끗한 인테리어에 아이템을 사용할 경우 400을 보냅니다.\n" +
                    "사용 가능한 빗자루가 없을 경우 403을 보냅니다."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "인테리어 청소 성공입니다."),
            @ApiResponse(code = 401, message = "토큰이 만료되었습니다. 다시 로그인 해주세요.", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "이미 깨끗한 인테리어에는 빗자루를 사용할 수 없습니다.", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "사용 가능한 빗자루가 없습니다.", response = ErrorResponse.class),
            @ApiResponse(
                    code = 404,
                    message = "1. 탈퇴했거나 존재하지 않는 유저입니다.\n"
                            + "2. 존재하지 않는 obtain 입니다.",
                    response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생하였습니다.", response = ErrorResponse.class)
    })
    @Auth
    @PutMapping("/interior/obtain/{obtainId}/clean")
    public ResponseEntity<SuccessResponse<SpaceMainInfoResponse>> updateCleanScore(
            @ApiParam(name = "obtainId", value = "청소할 obtain 의 id", required = true, example = "1")
            @PathVariable Long obtainId,
            @ApiIgnore @UserId Long userId) {
        return SuccessResponse.success(SuccessCode.UPDATE_INTERIOR_CLEAN_SCORE_SUCCESS,
                interiorService.updateCleanScore(obtainId, userId));
    }
}
