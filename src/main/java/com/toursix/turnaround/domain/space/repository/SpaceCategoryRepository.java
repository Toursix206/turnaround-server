package com.toursix.turnaround.domain.space.repository;

import com.toursix.turnaround.domain.space.SpaceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceCategoryRepository extends JpaRepository<SpaceCategory, Long>, SpaceCategoryRepositoryCustom {

}
