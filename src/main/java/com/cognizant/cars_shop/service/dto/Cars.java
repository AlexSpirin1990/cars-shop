package com.cognizant.cars_shop.service.dto;

import java.util.List;

public class Cars {
    private final String location;
    private final List<VehicleDTO> vehicles;

    public String getLocation() {
        return location;
    }

    public List<VehicleDTO> getVehicles() {
        return vehicles;
    }

    public Cars(String location, List<VehicleDTO> vehicles) {
        this.location = location;
        this.vehicles = vehicles;
    }
}
