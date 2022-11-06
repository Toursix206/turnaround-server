package com.toursix.turnaround.domain.interior.repository;

import com.toursix.turnaround.domain.interior.Interior;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InteriorRepository extends JpaRepository<Interior, Long>, InteriorRepositoryCustom {

}
