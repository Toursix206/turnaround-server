package com.toursix.turnaround.domain.done;

import static com.toursix.turnaround.common.exception.ErrorCode.VALIDATION_RATING_RANGE_EXCEPTION;

import com.toursix.turnaround.common.exception.ValidationException;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class DoneReviewRating {

    private static final int MIN_RATING_VALUE = 1;
    private static final int MAX_RATING_VALUE = 5;

    @Column
    private Integer score;

    private void validateRatingInAvailableRange(int rating) {
        if (rating < MIN_RATING_VALUE || rating > MAX_RATING_VALUE) {
            throw new ValidationException(String.format("잘못된 Rating 값입니다. (%s)", rating),
                    VALIDATION_RATING_RANGE_EXCEPTION);
        }
    }

    public static DoneReviewRating of(int score) {
        return new DoneReviewRating(score);
    }

    public void setScore(int score) {
        validateRatingInAvailableRange(score);
        this.score = score;
    }
}
