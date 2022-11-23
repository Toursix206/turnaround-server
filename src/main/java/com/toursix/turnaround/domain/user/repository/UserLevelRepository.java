package com.toursix.turnaround.domain.user.repository;

import com.toursix.turnaround.domain.user.UserLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLevelRepository extends JpaRepository<UserLevel, Long>, UserLevelRepositoryCustom {

}
