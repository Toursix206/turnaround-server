package com.toursix.turnaround.domain.todo.repository;

import com.toursix.turnaround.domain.todo.DoneReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoneReviewRepository extends JpaRepository<DoneReview, Long>, DoneReviewRepositoryCustom {

}
