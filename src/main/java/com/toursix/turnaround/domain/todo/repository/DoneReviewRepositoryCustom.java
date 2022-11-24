package com.toursix.turnaround.domain.todo.repository;

import com.toursix.turnaround.domain.todo.DoneReview;

public interface DoneReviewRepositoryCustom {

    DoneReview findDoneReviewById(Long doneReviewId);
}
