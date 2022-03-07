package com.cognizant.cars_shop.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * A Vehicle.
 */
@Entity
@Table(name = "vehicle")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1)
    @Column(name = "make", nullable = false)
    private String make;

    @NotNull
    @Size(min = 1)
    @Column(name = "model", nullable = false)
    private String model;

    @NotNull
    @Column(name = "year_model", nullable = false)
    private Integer yearModel;

    @NotNull
    @Column(name = "price", precision = 21, scale = 2, nullable = false)
    private BigDecimal price;

    @NotNull
    @Column(name = "licensed", nullable = false)
    private Boolean licensed;

    @NotNull
    @Column(name = "date_added", nullable = false)
    private Instant dateAdded;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("vehicles")
    private Location location;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("vehicles")
    private Warehouse warehouse;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public Vehicle make(String make) {
        this.make = make;
        return this;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public Vehicle model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYearModel() {
        return yearModel;
    }

    public Vehicle yearModel(Integer yearModel) {
        this.yearModel = yearModel;
        return this;
    }

    public void setYearModel(Integer yearModel) {
        this.yearModel = yearModel;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Vehicle price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean isLicensed() {
        return licensed;
    }

    public Vehicle licensed(Boolean licensed) {
        this.licensed = licensed;
        return this;
    }

    public void setLicensed(Boolean licensed) {
        this.licensed = licensed;
    }

    public Instant getDateAdded() {
        return dateAdded;
    }

    public Vehicle dateAdded(Instant dateAdded) {
        this.dateAdded = dateAdded;
        return this;
    }

    public void setDateAdded(Instant dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Location getLocation() {
        return location;
    }

    public Vehicle location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public Vehicle warehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
        return this;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vehicle)) {
            return false;
        }
        return id != null && id.equals(((Vehicle) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
            "id=" + getId() +
            ", make='" + getMake() + "'" +
            ", model='" + getModel() + "'" +
            ", yearModel=" + getYearModel() +
            ", price=" + getPrice() +
            ", licensed='" + isLicensed() + "'" +
            ", dateAdded='" + getDateAdded() + "'" +
            "}";
    }
}
