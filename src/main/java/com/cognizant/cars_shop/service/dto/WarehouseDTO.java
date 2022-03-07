package com.cognizant.cars_shop.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WarehouseDTO {
    @JsonProperty("_id")
    private final Long id;
    private final String name;
    private final LocationDTO location;
    private final Cars cars;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public Cars getCars() {
        return cars;
    }

    public WarehouseDTO(Long id, String name, LocationDTO location, Cars cars) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.cars = cars;
    }
}
