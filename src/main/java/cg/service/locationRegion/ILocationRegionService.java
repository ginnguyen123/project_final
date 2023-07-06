package cg.service.locationRegion;

import cg.model.customer.Customer;
import cg.model.location_region.LocationRegion;
import cg.service.IGeneralService;

import java.util.List;

public interface ILocationRegionService extends IGeneralService<LocationRegion, Long> {

    List<LocationRegion> findAllByCustomer(Customer customer);
}
