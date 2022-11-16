package com.toursix.turnaround.service.space;

import static com.toursix.turnaround.common.exception.ErrorCode.NOT_FOUND_SPACE_CATEGORY_EXCEPTION;
import static com.toursix.turnaround.common.exception.ErrorCode.NOT_FOUND_SPACE_EXCEPTION;

import com.toursix.turnaround.common.exception.NotFoundException;
import com.toursix.turnaround.domain.space.Space;
import com.toursix.turnaround.domain.space.SpaceCategory;
import com.toursix.turnaround.domain.space.SpaceCategoryType;
import com.toursix.turnaround.domain.space.repository.SpaceCategoryRepository;
import com.toursix.turnaround.domain.space.repository.SpaceRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpaceServiceUtils {

    public static SpaceCategory findSpaceCategoryByName(SpaceCategoryRepository spaceCategoryRepository,
            SpaceCategoryType spaceCategoryType) {
        SpaceCategory spaceCategory = spaceCategoryRepository.findSpaceCategoryByName(spaceCategoryType);
        if (spaceCategory == null) {
            throw new NotFoundException(String.format("존재하지 않는 공간 카테고리 (%s) 입니다", spaceCategory),
                    NOT_FOUND_SPACE_CATEGORY_EXCEPTION);
        }
        return spaceCategory;
    }

    public static Space findSpaceBySpaceCategory(SpaceRepository spaceRepository, SpaceCategory spaceCategory) {
        Space space = spaceRepository.findSpaceBySpaceCategory(spaceCategory);
        if (space == null) {
            throw new NotFoundException(String.format("해당 공간 카테고리 (%s) 에 해당하는 공간이 존재하지 않습니다.", spaceCategory.getName()),
                    NOT_FOUND_SPACE_EXCEPTION);
        }
        return space;
    }
}
