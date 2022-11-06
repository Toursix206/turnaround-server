package com.toursix.turnaround.domain.space.repository;

import com.toursix.turnaround.domain.space.Space;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceRepository extends JpaRepository<Space, Long>, SpaceRepositoryCustom {

}
