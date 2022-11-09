package com.toursix.turnaround.domain.activity.repository;

import com.toursix.turnaround.domain.activity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long>, ActivityRepositoryCustom {

}
