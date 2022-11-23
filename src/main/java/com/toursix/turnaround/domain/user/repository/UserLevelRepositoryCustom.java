package com.toursix.turnaround.domain.user.repository;

import com.toursix.turnaround.domain.user.UserLevel;

public interface UserLevelRepositoryCustom {

    UserLevel findLevelByExperience(int experience);
}
