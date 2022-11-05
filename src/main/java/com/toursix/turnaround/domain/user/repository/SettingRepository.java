package com.toursix.turnaround.domain.user.repository;

import com.toursix.turnaround.domain.user.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingRepository extends JpaRepository<Setting, Long>, SettingRepositoryCustom {

}
