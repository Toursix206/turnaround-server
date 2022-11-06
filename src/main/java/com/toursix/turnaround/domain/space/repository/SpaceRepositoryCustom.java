package com.toursix.turnaround.domain.space.repository;

import com.toursix.turnaround.domain.space.Space;
import com.toursix.turnaround.domain.space.SpaceCategory;

public interface SpaceRepositoryCustom {

    Space findSpaceBySpaceCategory(SpaceCategory spaceCategory);
}
