package com.cognizant.cars_shop.service.dto;

import com.cognizant.cars_shop.util.InstantSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;

public class VehicleDTO {
    @JsonProperty("_id")
    @NotNull
    private final Long id;

    @NotNull
    @Size(min = 1)
    private final String make;

    @NotNull
    @Size(min = 1)
    private final String model;

    @JsonProperty("year_model")
    @NotNull
    private final Integer yearModel;

    @NotNull
    private final BigDecimal price;

    @NotNull
    private final Boolean licensed;

    @JsonProperty("date_added")
    @JsonSerialize(using = InstantSerializer.class)
    @NotNull
    private final Instant dateAdded;

    public Long getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public Integer getYearModel() {
        return yearModel;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Boolean getLicensed() {
        return licensed;
    }

    public Instant getDateAdded() {
        return dateAdded;
    }

    public VehicleDTO(Long id, String make, String model, Integer yearModel, BigDecimal price, Boolean licensed, Instant dateAdded) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.yearModel = yearModel;
        this.price = price;
        this.licensed = licensed;
        this.dateAdded = dateAdded;
    }
}
