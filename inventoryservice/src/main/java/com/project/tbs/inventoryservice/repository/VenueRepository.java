package com.project.tbs.inventoryservice.repository;

import com.project.tbs.inventoryservice.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Long> {
}
