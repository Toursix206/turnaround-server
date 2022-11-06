package com.toursix.turnaround.service.interior;

import static com.toursix.turnaround.common.exception.ErrorCode.NOT_FOUND_INTERIOR_EXCEPTION;

import com.toursix.turnaround.common.exception.NotFoundException;
import com.toursix.turnaround.domain.interior.Interior;
import com.toursix.turnaround.domain.interior.repository.InteriorRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InteriorServiceUtils {

    public static Interior findInteriorByName(InteriorRepository interiorRepository, String name) {
        Interior interior = interiorRepository.findInteriorByName(name);
        if (interior == null) {
            throw new NotFoundException(String.format("해당 이름 (%s) 을 가진 인테리어가 존재하지 않습니다.", name),
                    NOT_FOUND_INTERIOR_EXCEPTION);
        }
        return interior;
    }
}
