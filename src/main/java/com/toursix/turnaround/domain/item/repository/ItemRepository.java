package com.toursix.turnaround.domain.item.repository;

import com.toursix.turnaround.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {

}
