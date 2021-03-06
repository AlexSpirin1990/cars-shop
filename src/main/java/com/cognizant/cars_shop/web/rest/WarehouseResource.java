package com.cognizant.cars_shop.web.rest;

import com.cognizant.cars_shop.domain.Warehouse;
import com.cognizant.cars_shop.repository.VehicleRepository;
import com.cognizant.cars_shop.repository.WarehouseRepository;
import com.cognizant.cars_shop.service.dto.WarehouseDTO;
import com.cognizant.cars_shop.util.WarehouseUtil;
import com.cognizant.cars_shop.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing {@link com.cognizant.cars_shop.domain.Warehouse}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class WarehouseResource {

    private final Logger log = LoggerFactory.getLogger(WarehouseResource.class);

    private static final String ENTITY_NAME = "warehouse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WarehouseRepository warehouseRepository;
    private final VehicleRepository vehicleRepository;

    public WarehouseResource(WarehouseRepository warehouseRepository, VehicleRepository vehicleRepository) {
        this.warehouseRepository = warehouseRepository;
        this.vehicleRepository = vehicleRepository;
    }

    /**
     * {@code POST  /warehouses} : Create a new warehouse.
     *
     * @param warehouse the warehouse to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new warehouse, or with status {@code 400 (Bad Request)} if the warehouse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/warehouses")
    public ResponseEntity<Warehouse> createWarehouse(@Valid @RequestBody Warehouse warehouse) throws URISyntaxException {
        log.debug("REST request to save Warehouse : {}", warehouse);
        if (warehouse.getId() != null) {
            throw new BadRequestAlertException("A new warehouse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Warehouse result = warehouseRepository.save(warehouse);
        return ResponseEntity.created(new URI("/api/warehouses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /warehouses} : Updates an existing warehouse.
     *
     * @param warehouse the warehouse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated warehouse,
     * or with status {@code 400 (Bad Request)} if the warehouse is not valid,
     * or with status {@code 500 (Internal Server Error)} if the warehouse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/warehouses")
    public ResponseEntity<Warehouse> updateWarehouse(@Valid @RequestBody Warehouse warehouse) throws URISyntaxException {
        log.debug("REST request to update Warehouse : {}", warehouse);
        if (warehouse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Warehouse result = warehouseRepository.save(warehouse);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, warehouse.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /warehouses} : get all the warehouses.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of warehouses in body.
     */
    @GetMapping("/warehouses")
    public List<Warehouse> getAllWarehouses() {
        log.debug("REST request to get all Warehouses");
        return warehouseRepository.findAll();
    }

    /**
     * {@code GET  /warehouses/:id} : get the "id" warehouse.
     *
     * @param id the id of the warehouse to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the warehouse, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/warehouses/{id}")
    public ResponseEntity<Warehouse> getWarehouse(@PathVariable Long id) {
        log.debug("REST request to get Warehouse : {}", id);
        Optional<Warehouse> warehouse = warehouseRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(warehouse);
    }

    /**
     * {@code DELETE  /warehouses/:id} : delete the "id" warehouse.
     *
     * @param id the id of the warehouse to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/warehouses/{id}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable Long id) {
        log.debug("REST request to delete Warehouse : {}", id);
        warehouseRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code GET  /warehousesForShop} : get all the warehouses for shop.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of WarehouseDTO in body.
     */
    @GetMapping("/warehouses/forShop")
    public List<WarehouseDTO> getAllWarehousesForShop() {
        log.debug("REST request to get all Warehouses");
        return WarehouseUtil.asListWarehouses(vehicleRepository.findAll());
    }

    /**
     * {@code POST  /warehouses} : Create a new warehouse.
     *
     * @param warehouses the warehouses to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new warehouses.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/warehouses/batch")
    public ResponseEntity<List<Warehouse>> createWarehouses(@Valid @RequestBody List<Warehouse> warehouses) throws URISyntaxException {
        log.debug("REST request to save Warehouses : {}", warehouses);
        List<Warehouse> result = warehouseRepository.saveAll(warehouses);
        return ResponseEntity.created(new URI("/api/warehouses/batch/" + result.stream().map(Warehouse::getId).collect(Collectors.toList())))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.stream().map(Warehouse::getId).collect(Collectors.toList()).toString()))
            .body(result);
    }
}
