package com.toursix.turnaround.controller.todo;

import com.toursix.turnaround.common.dto.ErrorResponse;
import com.toursix.turnaround.common.dto.SuccessResponse;
import com.toursix.turnaround.config.interceptor.Auth;
import com.toursix.turnaround.config.resolver.UserId;
import com.toursix.turnaround.service.todo.TodoService;
import com.toursix.turnaround.service.todo.dto.request.CreateTodoRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
                    "2주 이후의 예약 시간에 대해 403을 보냅니다.\n" +
                    "startAt 은 yyyy-MM-dd'T'HH:mm:ss 형식으로 입력해주세요."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "생성 성공입니다."),
            @ApiResponse(code = 401, message = "토큰이 만료되었습니다. 다시 로그인 해주세요.", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "정책에 위배되는 예약 시간입니다.", response = ErrorResponse.class),
            @ApiResponse(
                    code = 404,
                    message = "1. 탈퇴했거나 존재하지 않는 유저입니다.\n"
                            + "2. 존재하지 않는 활동입니다.",
                    response = ErrorResponse.class),
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
}
