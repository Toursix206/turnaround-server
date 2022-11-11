package com.toursix.turnaround.domain.done.repository;

import com.toursix.turnaround.domain.done.DoneReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoneReviewRepository extends JpaRepository<DoneReview, Long>, DoneReviewRepositoryCustom {

}
