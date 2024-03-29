package com.toursix.turnaround.service.todo;

import static com.toursix.turnaround.common.exception.ErrorCode.CONFLICT_TODO_DONE_EXCEPTION;
import static com.toursix.turnaround.common.exception.ErrorCode.CONFLICT_TODO_DONE_REVIEW_EXCEPTION;
import static com.toursix.turnaround.common.exception.ErrorCode.CONFLICT_TODO_REWARD_EXCEPTION;
import static com.toursix.turnaround.common.exception.ErrorCode.VALIDATION_TODO_DONE_REVIEW_EXCEPTION;
import static com.toursix.turnaround.common.exception.ErrorCode.VALIDATION_TODO_REWARD_EXCEPTION;

import com.toursix.turnaround.common.exception.ConflictException;
import com.toursix.turnaround.common.exception.ValidationException;
import com.toursix.turnaround.common.type.FileType;
import com.toursix.turnaround.common.util.DateUtils;
import com.toursix.turnaround.domain.activity.Activity;
import com.toursix.turnaround.domain.activity.repository.ActivityRepository;
import com.toursix.turnaround.domain.item.Item;
import com.toursix.turnaround.domain.todo.Done;
import com.toursix.turnaround.domain.todo.DoneReview;
import com.toursix.turnaround.domain.todo.PushStatus;
import com.toursix.turnaround.domain.todo.Todo;
import com.toursix.turnaround.domain.todo.TodoStage;
import com.toursix.turnaround.domain.todo.repository.DoneRepository;
import com.toursix.turnaround.domain.todo.repository.DoneReviewRepository;
import com.toursix.turnaround.domain.todo.repository.TodoRepository;
import com.toursix.turnaround.domain.user.Onboarding;
import com.toursix.turnaround.domain.user.Point;
import com.toursix.turnaround.domain.user.User;
import com.toursix.turnaround.domain.user.repository.UserRepository;
import com.toursix.turnaround.service.activity.ActivityServiceUtils;
import com.toursix.turnaround.service.image.provider.S3Provider;
import com.toursix.turnaround.service.image.provider.dto.request.ImageUploadFileRequest;
import com.toursix.turnaround.service.todo.dto.request.CreateDoneReviewRequestDto;
import com.toursix.turnaround.service.todo.dto.request.CreateTodoRequestDto;
import com.toursix.turnaround.service.todo.dto.request.UpdateTodoPushStateRequestDto;
import com.toursix.turnaround.service.todo.dto.request.UpdateTodoRequestDto;
import com.toursix.turnaround.service.todo.dto.response.DoneResponse;
import com.toursix.turnaround.service.todo.dto.response.RewardResponse;
import com.toursix.turnaround.service.user.UserServiceUtils;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
@Transactional
public class TodoService {

    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    private final TodoRepository todoRepository;
    private final DoneRepository doneRepository;
    private final DoneReviewRepository doneReviewRepository;

    private final S3Provider s3Provider;

    public void createTodo(CreateTodoRequestDto request, Long userId) {
        User user = UserServiceUtils.findUserById(userRepository, userId);
        Onboarding onboarding = user.getOnboarding();
        Activity activity = ActivityServiceUtils.findActivityById(activityRepository, request.getActivityId());
        LocalDateTime now = DateUtils.todayLocalDateTime();
        LocalDateTime startAt = request.getStartAt();
        LocalDateTime endAt = startAt.plusMinutes(activity.getDuration());
        TodoValidateUtils.validateStartAt(startAt, now, activity.getDuration());
        TodoValidateUtils.validateUniqueTodoTime(todoRepository, onboarding, null, startAt, endAt);
        Todo todo = todoRepository.save(
                Todo.newInstance(user.getOnboarding(), activity, startAt, request.getPushStatus()));
        onboarding.addTodo(todo);
    }

    public void updateTodo(UpdateTodoRequestDto request, Long todoId, Long userId) {
        User user = UserServiceUtils.findUserById(userRepository, userId);
        Onboarding onboarding = user.getOnboarding();
        Todo todo = TodoServiceUtils.findTodoById(todoRepository, todoId);
        LocalDateTime now = DateUtils.todayLocalDateTime();
        LocalDateTime startAt = request.getStartAt();
        LocalDateTime endAt = startAt.plusMinutes(todo.getActivity().getDuration());
        TodoValidateUtils.validateUpdatable(todo);
        TodoValidateUtils.validateStartAt(startAt, now, todo.getActivity().getDuration());
        TodoValidateUtils.validateUniqueTodoTime(todoRepository, onboarding, todo, startAt, endAt);
        todo.updateStartAt(startAt);
        todo.updatePushStatus(request.getPushStatus());
        onboarding.updateTodo(todo);
    }

    public void updateTodoByPushState(UpdateTodoPushStateRequestDto request, Long todoId, Long userId) {
        User user = UserServiceUtils.findUserById(userRepository, userId);
        Todo todo = TodoServiceUtils.findTodoById(todoRepository, todoId);
        TodoValidateUtils.validateTodoByTodoStatus(todo, request.getPushStatus(), user);
        todo.updatePushStatus(request.getPushStatus());
    }

    public void deleteTodo(Long todoId, Long userId) {
        User user = UserServiceUtils.findUserById(userRepository, userId);
        Onboarding onboarding = user.getOnboarding();
        Todo todo = TodoServiceUtils.findTodoById(todoRepository, todoId);
        TodoValidateUtils.validateUpdatable(todo);
        todo.delete();
        onboarding.deleteTodo(todo);
    }

    public DoneResponse createDoneForActivity(Long todoId, MultipartFile image, Long userId) {
        User user = UserServiceUtils.findUserById(userRepository, userId);
        Onboarding onboarding = user.getOnboarding();
        Todo todo = TodoServiceUtils.findTodoById(todoRepository, todoId);
        if (todo.getDone() != null) {
            throw new ConflictException(
                    String.format("이미 존재하는 활동 (%s) 의 인증 (%s) 입니다.", todo.getId(), todo.getDone().getId()),
                    CONFLICT_TODO_DONE_EXCEPTION);
        }
        String imageUrl = s3Provider.uploadFile(ImageUploadFileRequest.of(FileType.ACTIVITY_CERTIFICATION_IMAGE),
                image);
        Done done = doneRepository.save(Done.newInstance(todo, imageUrl));
        DoneReview doneReview = doneReviewRepository.save(
                DoneReview.newInstance(onboarding, todo.getActivity(), done));
        onboarding.addDoneReview(doneReview);
        todo.setStage(TodoStage.SUCCESS);
        return DoneResponse.of(doneReview.getId());
    }

    public RewardResponse rewardToUser(Long todoId, Long userId) {
        User user = UserServiceUtils.findUserById(userRepository, userId);
        Onboarding onboarding = user.getOnboarding();
        Todo todo = TodoServiceUtils.findTodoById(todoRepository, todoId);
        giveBroomByTodoToUser(onboarding, todo);
        return RewardResponse.of(todo.getActivity().getBroom());
    }

    public void createDoneReviewForTodo(CreateDoneReviewRequestDto request, Long doneReviewId, Long userId) {
        User user = UserServiceUtils.findUserById(userRepository, userId);
        Onboarding onboarding = user.getOnboarding();
        DoneReview doneReview = TodoServiceUtils.findDoneReviewById(doneReviewRepository, doneReviewId);
        Done done = doneReview.getDone();
        if (!doneReview.checkTodoStage()) {
            throw new ValidationException(String.format("인증이 완료된 활동 (%s) 이 아닙니다.", done.getId()),
                    VALIDATION_TODO_DONE_REVIEW_EXCEPTION);
        }
        if (doneReview.getIsWritten()) {
            throw new ConflictException(String.format("이미 리뷰가 작성된 활동 (%s) 입니다.", done.getId()),
                    CONFLICT_TODO_DONE_REVIEW_EXCEPTION);
        }
        doneReview.update(request.getRating(), request.getContent());
        onboarding.updateDoneReview(doneReview);
        giveTurningPointToUser(user, doneReview.getActivity());
    }

    public void turnOffTodosNotificationByUser(Long userId) {
        User user = UserServiceUtils.findUserById(userRepository, userId);
        List<Todo> todos = user.getOnboarding().getTodos();
        TodoValidateUtils.validateTodosByTodoStatus(todos, user);
        todos.stream()
                .filter(todo -> todo.getPushStatus() != PushStatus.OFF)
                .forEach(todo -> todo.updatePushStatus(PushStatus.OFF));
    }

    private void giveBroomByTodoToUser(Onboarding onboarding, Todo todo) {
        if (todo.getStage() == TodoStage.FAIL || todo.getStage() == TodoStage.IN_PROGRESS) {
            throw new ValidationException(String.format("실패한 활동 (%s) 은 리워드를 받을 수 없습니다.", todo.getId()),
                    VALIDATION_TODO_REWARD_EXCEPTION);
        }

        if (todo.getStage() == TodoStage.SUCCESS_REWARD) {
            throw new ConflictException(String.format("리워드를 받은 활동 (%s) 입니다.", todo.getId()),
                    CONFLICT_TODO_REWARD_EXCEPTION);
        }

        Item item = onboarding.getItem();
        item.addBroom(todo.getActivity().getBroom());
        todo.setStage(TodoStage.SUCCESS_REWARD);
    }

    private void giveTurningPointToUser(User user, Activity activity) {
        Point point = user.getPoint();
        point.addAmount(activity.getPoint());
    }
}
