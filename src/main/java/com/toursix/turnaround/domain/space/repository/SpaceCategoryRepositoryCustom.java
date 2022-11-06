package com.toursix.turnaround.domain.space.repository;

import com.toursix.turnaround.domain.space.SpaceCategory;

public interface SpaceCategoryRepositoryCustom {

    SpaceCategory findSpaceCategoryByName(String name);
}
