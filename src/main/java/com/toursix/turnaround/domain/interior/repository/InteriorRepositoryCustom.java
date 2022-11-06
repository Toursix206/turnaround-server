package com.toursix.turnaround.domain.interior.repository;

import com.toursix.turnaround.domain.interior.Interior;

public interface InteriorRepositoryCustom {

    Interior findInteriorByName(String name);
}
