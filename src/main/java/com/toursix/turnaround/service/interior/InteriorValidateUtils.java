package com.toursix.turnaround.service.interior;

import static com.toursix.turnaround.common.exception.ErrorCode.FORBIDDEN_NOT_ENOUGH_BROOM_EXCEPTION;
import static com.toursix.turnaround.common.exception.ErrorCode.VALIDATION_OBTAIN_CLEAN_LEVEL_EXCEPTION;

import com.toursix.turnaround.common.exception.ForbiddenException;
import com.toursix.turnaround.common.exception.ValidationException;
import com.toursix.turnaround.domain.interior.CleanLevel;
import com.toursix.turnaround.domain.interior.Obtain;
import com.toursix.turnaround.domain.item.Item;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InteriorValidateUtils {

    public static void validateEnoughBroom(Item item) {
        if (item.getBroom() < 1) {
            throw new ForbiddenException(String.format("item (%s) 사용 가능한 빗자루가 없습니다.", item.getId()),
                    FORBIDDEN_NOT_ENOUGH_BROOM_EXCEPTION);
        }
    }

    public static void validateCleanableInterior(Obtain obtain) {
        if (obtain.getCleanLevel() == CleanLevel.ONE) {
            throw new ValidationException(String.format("이미 깨끗한 상태의 obtain (%s) 입니다.", obtain.getId()),
                    VALIDATION_OBTAIN_CLEAN_LEVEL_EXCEPTION);
        }
    }
}
