package com.dala.data.building.industryfacility;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndustryFacilityRepository extends JpaRepository<IndustryFacility, Long> {
}
