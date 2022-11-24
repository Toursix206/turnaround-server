package com.toursix.turnaround.domain.todo.repository;

import com.toursix.turnaround.domain.todo.Done;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoneRepository extends JpaRepository<Done, Long>, DoneRepositoryCustom {

}
