package com.toursix.turnaround.domain.interior.repository;

import com.toursix.turnaround.domain.interior.InteriorCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InteriorCategoryRepository extends JpaRepository<InteriorCategory, Long>,
        InteriorCategoryRepositoryCustom {

}
