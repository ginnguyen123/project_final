package cg.repository;

import cg.model.discount.Discount;
import cg.model.location_region.LocationRegion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRegionRepository extends JpaRepository<LocationRegion, Long> {
}
