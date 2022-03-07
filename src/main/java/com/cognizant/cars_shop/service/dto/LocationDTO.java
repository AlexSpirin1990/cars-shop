package com.cognizant.cars_shop.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class LocationDTO {
    @JsonProperty("lat")
    private final BigDecimal latitude;

    @JsonProperty("long")
    private final BigDecimal longitude;

    public BigDecimal getLatitude() {
        return latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public LocationDTO(BigDecimal latitude, BigDecimal longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
