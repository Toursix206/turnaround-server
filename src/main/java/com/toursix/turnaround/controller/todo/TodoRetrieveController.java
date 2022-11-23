package com.toursix.turnaround.controller.todo;

import com.toursix.turnaround.common.dto.ErrorResponse;
import com.toursix.turnaround.common.dto.SuccessResponse;
import com.toursix.turnaround.common.success.SuccessCode;
import com.toursix.turnaround.config.interceptor.Auth;
import com.toursix.turnaround.config.resolver.UserId;
import com.toursix.turnaround.service.activity.dto.response.ActivityGuideResponse;
import com.toursix.turnaround.service.todo.TodoRetrieveService;
import com.toursix.turnaround.service.todo.dto.response.TodoInfoResponse;
import com.toursix.turnaround.service.todo.dto.response.TodoMainResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Todo")
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class TodoRetrieveController {

    private final TodoRetrieveService todoRetrieveService;

    @ApiOperation(
            value = "[인증] 활동 이벤트 페이지 - 활동 일정을 조회합니다.",
            notes = "todoStatus - PURPLE, BLACK, WHITE"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "활동 일정 리스트 조회 성공입니다."),
            @ApiResponse(code = 401, message = "토큰이 만료되었습니다. 다시 로그인 해주세요.", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "탈퇴했거나 존재하지 않는 유저입니다.", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생하였습니다.", response = ErrorResponse.class)
    })
    @Auth
    @GetMapping("/todos")
    public ResponseEntity<SuccessResponse<TodoMainResponse>> getTodoMainInfo(@ApiIgnore @UserId Long userId) {
        return SuccessResponse.success(SuccessCode.GET_SPACE_MAIN_INFO_SUCCESS,
                todoRetrieveService.getTodoMainInfo(userId));
    }

    @ApiOperation(value = "[인증] 활동 이벤트 페이지 - 활동 세부 내용을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "활동 일정 세부 내용 조회 성공입니다."),
            @ApiResponse(code = 401, message = "토큰이 만료되었습니다. 다시 로그인 해주세요.", response = ErrorResponse.class),
            @ApiResponse(code = 404,
                    message = "1. 탈퇴했거나 존재하지 않는 유저입니다.\n"
                            + "2. 존재하지 않는 todo 입니다.",
                    response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생하였습니다.", response = ErrorResponse.class)
    })
    @Auth
    @GetMapping("/todo/{todoId}")
    public ResponseEntity<SuccessResponse<TodoInfoResponse>> getTodoInfo(
            @ApiParam(name = "todoId", value = "조회할 todo 의 id", required = true, example = "1")
            @PathVariable Long todoId,
            @ApiIgnore @UserId Long userId) {
        return SuccessResponse.success(SuccessCode.GET_TODO_INFO_SUCCESS,
                todoRetrieveService.getTodoInfo(todoId, userId));
    }

    @ApiOperation(value = "[인증] 활동 페이지 - 활동 가이드 내용을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "활동별 가이드 조회 성공입니다."),
            @ApiResponse(code = 401, message = "토큰이 만료되었습니다. 다시 로그인 해주세요.", response = ErrorResponse.class),
            @ApiResponse(code = 404,
                    message = "1. 존재하지 않는 활동입니다.\n"
                            + "2. 존재하지 않는 활동 가이드입니다.\n"
                            + "3. 탈퇴했거나 존재하지 않는 유저입니다.",
                    response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생하였습니다.", response = ErrorResponse.class)
    })
    @Auth
    @GetMapping("/todo/{todoId}/guide")
    public ResponseEntity<SuccessResponse<ActivityGuideResponse>> getActivityGuide(
            @ApiParam(name = "todoId", value = "활동 시작을 진행할 todo 의 id", required = true, example = "1")
            @PathVariable Long todoId,
            @ApiIgnore @UserId Long userId) {
        return SuccessResponse.success(SuccessCode.GET_ACTIVITY_GUIDE_INFO_SUCCESS,
                todoRetrieveService.getTodoGuide(todoId, userId));
    }
}
