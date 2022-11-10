package com.toursix.turnaround.controller.todo;

import com.toursix.turnaround.common.dto.ErrorResponse;
import com.toursix.turnaround.common.dto.SuccessResponse;
import com.toursix.turnaround.config.interceptor.Auth;
import com.toursix.turnaround.config.resolver.UserId;
import com.toursix.turnaround.service.todo.TodoService;
import com.toursix.turnaround.service.todo.dto.request.CreateTodoRequestDto;
import com.toursix.turnaround.service.todo.dto.request.UpdateTodoRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Todo")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class TodoController {

    private final TodoService todoService;

    @ApiOperation(
            value = "[인증] 활동 페이지 - 활동을 예약합니다.",
            notes = "소요시간을 더했을 때 자정이 넘어가는 경우,\n" +
                    "2주 이후의 예약 시간에 대해 400을 보냅니다.\n" +
                    "다른 일정과 겹칠 경우 409를 보냅니다.\n" +
                    "startAt 은 yyyy-MM-dd'T'HH:mm:ss 형식으로 입력해주세요."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "생성 성공입니다."),
            @ApiResponse(code = 401, message = "토큰이 만료되었습니다. 다시 로그인 해주세요.", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "정책에 위배되는 예약 시간입니다.", response = ErrorResponse.class),
            @ApiResponse(
                    code = 404,
                    message = "1. 탈퇴했거나 존재하지 않는 유저입니다.\n"
                            + "2. 존재하지 않는 활동입니다.",
                    response = ErrorResponse.class),
            @ApiResponse(code = 409, message = "다른 활동과 겹치는 일정입니다.", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생하였습니다.", response = ErrorResponse.class)
    })
    @Auth
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/todo")
    public ResponseEntity<SuccessResponse<String>> createTodo(
            @Valid @RequestBody CreateTodoRequestDto request,
            @ApiIgnore @UserId Long userId) {
        todoService.createTodo(request, userId);
        return SuccessResponse.CREATED;
    }

    @ApiOperation(
            value = "[인증] 활동 이벤트 페이지 - 활동 일정을 수정합니다.",
            notes = "소요시간을 더했을 때 자정이 넘어가는 경우,\n" +
                    "2주 이후의 예약 시간에 대해 400을 보냅니다.\n" +
                    "이미 성공, 실패한 일정일 경우 403을 보냅니다.\n" +
                    "다른 일정과 겹칠 경우 409를 보냅니다.\n" +
                    "startAt 은 yyyy-MM-dd'T'HH:mm:ss 형식으로 입력해주세요."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공입니다."),
            @ApiResponse(code = 400, message = "정책에 위배되는 예약 시간입니다.", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "토큰이 만료되었습니다. 다시 로그인 해주세요.", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "수정/삭제 할 수 없는 일정입니다.", response = ErrorResponse.class),
            @ApiResponse(
                    code = 404,
                    message = "1. 탈퇴했거나 존재하지 않는 유저입니다.\n"
                            + "2. 존재하지 않는 todo 입니다.",
                    response = ErrorResponse.class),
            @ApiResponse(code = 409, message = "다른 활동과 겹치는 일정입니다.", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생하였습니다.", response = ErrorResponse.class)
    })
    @Auth
    @PutMapping("/todo/{todoId}")
    public ResponseEntity<SuccessResponse<String>> updateTodo(
            @Valid @RequestBody UpdateTodoRequestDto request,
            @ApiParam(name = "todoId", value = "수정할 todo 의 id", required = true, example = "1")
            @PathVariable Long todoId,
            @ApiIgnore @UserId Long userId) {
        todoService.updateTodo(request, todoId, userId);
        return SuccessResponse.OK;
    }

    @ApiOperation(
            value = "[인증] 활동 이벤트 페이지 - 활동 일정을 삭제합니다.",
            notes = "이미 성공, 실패한 일정일 경우 403을 보냅니다."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공입니다."),
            @ApiResponse(code = 401, message = "토큰이 만료되었습니다. 다시 로그인 해주세요.", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "수정/삭제 할 수 없는 일정입니다.", response = ErrorResponse.class),
            @ApiResponse(
                    code = 404,
                    message = "1. 탈퇴했거나 존재하지 않는 유저입니다.\n"
                            + "2. 존재하지 않는 todo 입니다.",
                    response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생하였습니다.", response = ErrorResponse.class)
    })
    @Auth
    @DeleteMapping("/todo/{todoId}")
    public ResponseEntity<SuccessResponse<String>> deleteTodo(
            @ApiParam(name = "todoId", value = "삭제할 todo 의 id", required = true, example = "1")
            @PathVariable Long todoId,
            @ApiIgnore @UserId Long userId) {
        todoService.deleteTodo(todoId, userId);
        return SuccessResponse.OK;
    }
}
