package com.toursix.turnaround.domain.user.repository;

import com.toursix.turnaround.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
}
