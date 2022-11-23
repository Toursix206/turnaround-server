package com.toursix.turnaround.controller.activity;

import com.toursix.turnaround.common.dto.ErrorResponse;
import com.toursix.turnaround.common.dto.SuccessResponse;
import com.toursix.turnaround.common.success.SuccessCode;
import com.toursix.turnaround.config.interceptor.Auth;
import com.toursix.turnaround.config.resolver.UserId;
import com.toursix.turnaround.config.validator.AllowedSortProperties;
import com.toursix.turnaround.service.activity.ActivityRetrieveService;
import com.toursix.turnaround.service.activity.dto.request.GetActivitiesRequestDto;
import com.toursix.turnaround.service.activity.dto.response.ActivityPagingResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Activity")
@Validated
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ActivityRetrieveController {

    private final ActivityRetrieveService activityRetrieveService;

    @ApiOperation(value = "[인증] 활동 페이지 - 활동 리스트를 조회합니다.",
            notes = "type - FREE (무료), KIT (KIT). (** MVP에서는 FREE 밖에 없으므로 FREE로 보내주세요.)\n" +
                    "category - null (전체)\n" +
                    "page - 0 부터 조회를 시작합니다.\n" +
                    "size - 1 ~ 100 사이의 값으로 한 페이지의 크기를 설정합니다.\n" +
                    "sort - 정렬 조건을 설정합니다. createdAt,DESC (최신순)으로 보내주세요.\n" +
                    "response 의 nextPage 를 다음 요청의 page 에 담아서 다음 스크롤에 대한 응답을 받습니다.\n" +
                    "nextPage 가 -1 인 경우 더 이상 불러올 정보가 없다는 뜻입니다."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "활동 리스트 조회 성공입니다."),
            @ApiResponse(code = 400, message = "허용하지 않는 정렬기준을 입력했습니다.", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "토큰이 만료되었습니다. 다시 로그인 해주세요.", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "탈퇴했거나 존재하지 않는 유저입니다.", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생하였습니다.", response = ErrorResponse.class)
    })
    @Auth
    @GetMapping("/activities")
    public ResponseEntity<SuccessResponse<ActivityPagingResponse>> getActivitiesUsingPaging(
            GetActivitiesRequestDto request,
            @AllowedSortProperties({"createdAt"}) Pageable pageable,
            @ApiIgnore @UserId Long userId) {
        return SuccessResponse.success(SuccessCode.GET_ACTIVITIES_INFO_SUCCESS,
                activityRetrieveService.getActivitiesUsingPaging(request, pageable, userId));
    }
}
