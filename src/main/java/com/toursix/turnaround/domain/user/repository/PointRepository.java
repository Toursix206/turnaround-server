package com.toursix.turnaround.domain.user.repository;

import com.toursix.turnaround.domain.user.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long>, PointRepositoryCustom {

}
