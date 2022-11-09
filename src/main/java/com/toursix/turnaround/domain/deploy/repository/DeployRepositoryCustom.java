package com.toursix.turnaround.domain.deploy.repository;

import com.toursix.turnaround.domain.deploy.Deploy;

public interface DeployRepositoryCustom {

    Deploy findDeployByOs(String os);
}
