package com.toursix.turnaround.domain.user.repository;

import com.toursix.turnaround.domain.user.Onboarding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnbordingRepository extends JpaRepository<Onboarding, Long>, OnbordingRepositoryCustom {

}
