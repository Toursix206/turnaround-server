package com.toursix.turnaround.domain.done.repository;

import com.toursix.turnaround.domain.done.Done;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoneRepository extends JpaRepository<Done, Long>, DoneRepositoryCustom {

}
