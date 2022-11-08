package com.toursix.turnaround.domain.deploy.repository;

import com.toursix.turnaround.domain.deploy.Deploy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeployRepository extends JpaRepository<Deploy, Long>, DeployRepositoryCustom {

}
