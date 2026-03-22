package com.project.tbs.inventoryservice.repository;

import com.project.tbs.inventoryservice.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
