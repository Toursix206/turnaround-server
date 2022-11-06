package com.toursix.turnaround.domain.space.repository;

import com.toursix.turnaround.domain.space.Acquire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcquireRepository extends JpaRepository<Acquire, Long>, AcquireRepositoryCustom {

}
