package cg.repository;

import cg.model.customer.Customer;
import cg.model.discount.Discount;
import cg.model.location_region.LocationRegion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRegionRepository extends JpaRepository<LocationRegion, Long> {

    LocationRegion findLocationRegionByAddress(String address);

    List<LocationRegion> findAllByCustomer(Customer customer);
}
