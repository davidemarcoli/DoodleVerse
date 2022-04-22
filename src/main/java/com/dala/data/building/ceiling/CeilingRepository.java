package com.dala.data.building.ceiling;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CeilingRepository extends JpaRepository<Ceiling, Long> {
    Optional<Ceiling> getCeilingByColor(Integer color);
}
