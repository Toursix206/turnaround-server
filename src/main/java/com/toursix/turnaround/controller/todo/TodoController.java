package com.toursix.turnaround.controller.todo;

import com.toursix.turnaround.common.dto.ErrorResponse;
import com.toursix.turnaround.common.dto.SuccessResponse;
import com.toursix.turnaround.common.success.SuccessCode;
import com.toursix.turnaround.config.interceptor.Auth;
import com.toursix.turnaround.config.resolver.UserId;
import com.toursix.turnaround.service.todo.TodoService;
import com.toursix.turnaround.service.todo.dto.request.CreateDoneReviewRequestDto;
import com.toursix.turnaround.service.todo.dto.request.CreateTodoRequestDto;
import com.toursix.turnaround.service.todo.dto.request.UpdateTodoPushStateRequestDto;
import com.toursix.turnaround.service.todo.dto.request.UpdateTodoRequestDto;
import com.toursix.turnaround.service.todo.dto.response.DoneResponse;
import com.toursix.turnaround.service.todo.dto.response.RewardResponse;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
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
            value = "[인증] 활동 이벤트 페이지 - 활동 시간의 푸시 알림 여부를 수정합니다.",
            notes = "기존의 푸시 알림 설정 값과 동일한 경우, 400을 전달합니다.\n" +
                    "알림 여부는 PushState (ON, OFF) 값으로 보내주세요."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공입니다."),
            @ApiResponse(code = 400,
                    message = "1. 활동의 알림 여부를 입력해주세요. (pushStatus)\n"
                            + "2. 잘못된 알림 상태입니다.",
                    response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "토큰이 만료되었습니다. 다시 로그인 해주세요.", response = ErrorResponse.class),
            @ApiResponse(
                    code = 404,
                    message = "1. 탈퇴했거나 존재하지 않는 유저입니다.\n"
                            + "2. 존재하지 않는 todo 입니다.",
                    response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생하였습니다.", response = ErrorResponse.class)
    })
    @Auth
    @PutMapping("/todo/{todoId}/push")
    public ResponseEntity<SuccessResponse<String>> updateTodoByPushState(
            @Valid @RequestBody UpdateTodoPushStateRequestDto request,
            @ApiParam(name = "todoId", value = "수정할 todo 의 id", required = true, example = "1")
            @PathVariable Long todoId,
            @ApiIgnore @UserId Long userId) {
        todoService.updateTodoByPushState(request, todoId, userId);
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

    @ApiOperation(
            value = "[인증] 활동 인증 페이지 - 활동을 인증합니다.",
            notes = "이미지 파일이 없는 경우, 400을 보냅니다."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "활동 인증 성공입니다."),
            @ApiResponse(code = 400,
                    message = "1. 필수적인 요청 값이 입력되지 않았습니다.\n"
                            + "2. 허용되지 않은 파일 형식입니다.",
                    response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "토큰이 만료되었습니다. 다시 로그인 해주세요.", response = ErrorResponse.class),
            @ApiResponse(
                    code = 404,
                    message = "1. 탈퇴했거나 존재하지 않는 유저입니다.\n"
                            + "2. 존재하지 않는 todo 입니다.",
                    response = ErrorResponse.class),
            @ApiResponse(code = 409, message = "이미 존재하는 인증된 활동입니다.", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생하였습니다.", response = ErrorResponse.class)
    })
    @Auth
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/todo/{todoId}/done")
    public ResponseEntity<SuccessResponse<DoneResponse>> createDoneForActivity(
            @ApiParam(name = "todoId", value = "인증할 todo 의 id", required = true, example = "1")
            @PathVariable Long todoId,
            @ApiParam(name = "image", value = "활동 인증 이미지")
            @RequestPart(required = false) MultipartFile image,
            @ApiIgnore @UserId Long userId) {
        todoService.createDoneForActivity(todoId, image, userId);
        return SuccessResponse.success(SuccessCode.CREATED_TODO_DONE_SUCCESS,
                todoService.createDoneForActivity(todoId, image, userId));
    }

    @ApiOperation(
            value = "[인증] 활동 리뷰 작성 페이지 - 활동을 리뷰를 작성합니다.",
            notes = "활동 인증을 하지 않은 경우, 400을 전달합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공입니다."),
            @ApiResponse(code = 400,
                    message = "1. 별점을 입력해주세요. (rating)\n"
                            + "2. 허용하지 않는 평점 범위를 입력하였습니다. (0 ~ 5)\n"
                            + "3. 리뷰 내용을 입력해주세요. (content)\n"
                            + "4. 리뷰 내용은 최소 10자 이상 입력해주세요. (content)\n"
                            + "5. 활동 인증이 완료되지 않았습니다.",
                    response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "토큰이 만료되었습니다. 다시 로그인 해주세요.", response = ErrorResponse.class),
            @ApiResponse(
                    code = 404,
                    message = "1. 탈퇴했거나 존재하지 않는 유저입니다.\n"
                            + "2. 존재하지 않는 todo 입니다.\n"
                            + "3. 활동 인증이 완료되지 않았습니다.",
                    response = ErrorResponse.class),
            @ApiResponse(code = 409, message = "이미 존재하는 인증된 활동입니다.", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생하였습니다.", response = ErrorResponse.class)
    })
    @Auth
    @PostMapping("/todo/{todoId}/review")
    public ResponseEntity<SuccessResponse<String>> createDoneReviewForTodo(
            @ApiParam(name = "todoId", value = "리뷰 작성 todo 의 id", required = true, example = "1")
            @PathVariable Long todoId,
            @Valid @RequestBody CreateDoneReviewRequestDto request,
            @ApiIgnore @UserId Long userId) {
        todoService.createDoneReviewForTodo(request, todoId, userId);
        return SuccessResponse.OK;
    }

    @ApiOperation(
            value = "[인증] 활동 이벤트 페이지 - 완료된 활동의 리워드를 받습니다.",
            notes = "실패하거나 진행 중인 활동으로 리워드를 받을 수 없는 경우, 400을 전달합니다.\n" +
                    "이미 리워드를 받은 활동의 경우, 409를 전달합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "인증 완료한 활동에 대한 리워드 획득 성공입니다."),
            @ApiResponse(code = 400, message = "리워드를 받을 수 없는 활동입니다.", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "토큰이 만료되었습니다. 다시 로그인 해주세요.", response = ErrorResponse.class),
            @ApiResponse(
                    code = 404,
                    message = "1. 탈퇴했거나 존재하지 않는 유저입니다.\n"
                            + "2. 존재하지 않는 todo 입니다.",
                    response = ErrorResponse.class),
            @ApiResponse(code = 409, message = "이미 리워드를 받은 활동입니다.", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생하였습니다.", response = ErrorResponse.class)
    })
    @Auth
    @PutMapping("/todo/{todoId}/reward")
    public ResponseEntity<SuccessResponse<RewardResponse>> rewardToUser(
            @ApiParam(name = "todoId", value = "인증 완료된 todo 의 id", required = true, example = "1")
            @PathVariable Long todoId,
            @ApiIgnore @UserId Long userId) {
        return SuccessResponse.success(SuccessCode.UPDATE_REWARD_SUCCESS, todoService.rewardToUser(todoId, userId));
    }

    @ApiOperation(
            value = "[인증] 활동 이벤트 페이지 - 예약된 모든 활동의 알림을 끕니다.",
            notes = "이미 모든 활동에 대한 알림이 꺼진 경우, 409을 전달합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공입니다."),
            @ApiResponse(code = 401, message = "토큰이 만료되었습니다. 다시 로그인 해주세요.", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "탈퇴했거나 존재하지 않는 유저입니다.", response = ErrorResponse.class),
            @ApiResponse(code = 409, message = "이미 모든 활동에 대한 알림이 꺼져있습니다.", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생하였습니다.", response = ErrorResponse.class)
    })
    @Auth
    @PutMapping("/todos/notification/off")
    public ResponseEntity<SuccessResponse<String>> turnOffTodosNotificationByUser(@ApiIgnore @UserId Long userId) {
        todoService.turnOffTodosNotificationByUser(userId);
        return SuccessResponse.OK;
    }
}
