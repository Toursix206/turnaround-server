package com.toursix.turnaround.service.todo.dto.request;

import com.toursix.turnaround.domain.common.Constraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CreateDoneReviewRequestDto {

    @NotNull(message = "{doneReview.rating.notNull}")
    private Integer rating;

    @NotBlank(message = "{doneReview.content.notBlank}")
    @Size(min = Constraint.DONE_REVIEW_CONTENT_MIN, message = "{doneReview.content.min}")
    private String content;
}
