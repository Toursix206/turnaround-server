package com.toursix.turnaround.domain.space.repository;

import com.toursix.turnaround.domain.space.SpaceCategory;
import com.toursix.turnaround.domain.space.SpaceCategoryType;

public interface SpaceCategoryRepositoryCustom {

    SpaceCategory findSpaceCategoryByName(SpaceCategoryType spaceCategoryType);
}
