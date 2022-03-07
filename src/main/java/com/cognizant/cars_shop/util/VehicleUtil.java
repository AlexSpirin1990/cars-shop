package com.cognizant.cars_shop.util;

import com.cognizant.cars_shop.domain.Vehicle;
import com.cognizant.cars_shop.service.dto.VehicleDTO;

public class VehicleUtil {
    public static VehicleDTO asTO(Vehicle vehicle) {
        return new VehicleDTO(vehicle.getId(), vehicle.getMake(), vehicle.getModel(),
            vehicle.getYearModel(), vehicle.getPrice(), vehicle.isLicensed(),
            vehicle.getDateAdded());
    }
}
