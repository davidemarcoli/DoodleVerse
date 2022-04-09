package com.dala.data.building.wall;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WallRepository extends JpaRepository<Wall, Long> {
    public Optional<Wall> getWallByType(String type);
}
