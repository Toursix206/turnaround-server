package com.toursix.turnaround.service.todo;

import static com.toursix.turnaround.common.exception.ErrorCode.CONFLICT_TODO_DONE_EXCEPTION;

import com.toursix.turnaround.common.exception.ConflictException;
import com.toursix.turnaround.common.type.FileType;
import com.toursix.turnaround.common.util.DateUtils;
import com.toursix.turnaround.domain.activity.Activity;
import com.toursix.turnaround.domain.activity.repository.ActivityRepository;
import com.toursix.turnaround.domain.done.Done;
import com.toursix.turnaround.domain.done.DoneReview;
import com.toursix.turnaround.domain.done.repository.DoneRepository;
import com.toursix.turnaround.domain.done.repository.DoneReviewRepository;
import com.toursix.turnaround.domain.todo.Todo;
import com.toursix.turnaround.domain.todo.repository.TodoRepository;
import com.toursix.turnaround.domain.user.Onboarding;
import com.toursix.turnaround.domain.user.User;
import com.toursix.turnaround.domain.user.repository.UserRepository;
import com.toursix.turnaround.service.activity.ActivityServiceUtils;
import com.toursix.turnaround.service.image.provider.S3Provider;
import com.toursix.turnaround.service.image.provider.dto.request.ImageUploadFileRequest;
import com.toursix.turnaround.service.todo.dto.request.CreateTodoRequestDto;
import com.toursix.turnaround.service.todo.dto.request.UpdateTodoRequestDto;
import com.toursix.turnaround.service.user.UserServiceUtils;
import java.time.LocalDateTime;
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
        TodoServiceUtils.validateStartAt(startAt, now, activity.getDuration());
        TodoServiceUtils.validateUniqueTodoTime(todoRepository, onboarding, startAt, endAt);
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
        TodoServiceUtils.validateUpdatable(todo);
        TodoServiceUtils.validateStartAt(startAt, now, todo.getActivity().getDuration());
        TodoServiceUtils.validateUniqueTodoTime(todoRepository, onboarding, startAt, endAt);
        todo.updateStartAt(startAt);
        todo.updatePushStatus(request.getPushStatus());
        onboarding.updateTodo(todo);
    }

    public void deleteTodo(Long todoId, Long userId) {
        User user = UserServiceUtils.findUserById(userRepository, userId);
        Onboarding onboarding = user.getOnboarding();
        Todo todo = TodoServiceUtils.findTodoById(todoRepository, todoId);
        TodoServiceUtils.validateUpdatable(todo);
        todo.delete();
        onboarding.deleteTodo(todo);
    }

    public void createDoneForActivity(Long todoId, MultipartFile image, Long userId) {
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
    }
}
