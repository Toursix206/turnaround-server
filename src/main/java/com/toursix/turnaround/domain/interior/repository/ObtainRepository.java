package com.toursix.turnaround.domain.interior.repository;

import com.toursix.turnaround.domain.interior.Obtain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObtainRepository extends JpaRepository<Obtain, Long>, ObtainRepositoryCustom {

}
