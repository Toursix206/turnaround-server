package com.toursix.turnaround.domain.todo.repository;

import com.toursix.turnaround.domain.todo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoRepositoryCustom {

}
