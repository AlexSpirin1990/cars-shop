package com.cognizant.cars_shop.web.rest;

import com.cognizant.cars_shop.CarsShopApp;
import com.cognizant.cars_shop.domain.Vehicle;
import com.cognizant.cars_shop.domain.Location;
import com.cognizant.cars_shop.domain.Warehouse;
import com.cognizant.cars_shop.repository.VehicleRepository;
import com.cognizant.cars_shop.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.cognizant.cars_shop.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link VehicleResource} REST controller.
 */
@SpringBootTest(classes = CarsShopApp.class)
public class VehicleDTOResourceIT {

    private static final String DEFAULT_MAKE = "AAAAAAAAAA";
    private static final String UPDATED_MAKE = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEAR_MODEL = 1;
    private static final Integer UPDATED_YEAR_MODEL = 2;

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final Boolean DEFAULT_LICENSED = false;
    private static final Boolean UPDATED_LICENSED = true;

    private static final Instant DEFAULT_DATE_ADDED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_ADDED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restVehicleMockMvc;

    private Vehicle vehicle;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VehicleResource vehicleResource = new VehicleResource(vehicleRepository);
        this.restVehicleMockMvc = MockMvcBuilders.standaloneSetup(vehicleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vehicle createEntity(EntityManager em) {
        Vehicle vehicle = new Vehicle()
            .make(DEFAULT_MAKE)
            .model(DEFAULT_MODEL)
            .yearModel(DEFAULT_YEAR_MODEL)
            .price(DEFAULT_PRICE)
            .licensed(DEFAULT_LICENSED)
            .dateAdded(DEFAULT_DATE_ADDED);
        // Add required entity
        Location location;
        if (TestUtil.findAll(em, Location.class).isEmpty()) {
            location = LocationResourceIT.createEntity(em);
            em.persist(location);
            em.flush();
        } else {
            location = TestUtil.findAll(em, Location.class).get(0);
        }
        vehicle.setLocation(location);
        // Add required entity
        Warehouse warehouse;
        if (TestUtil.findAll(em, Warehouse.class).isEmpty()) {
            warehouse = WarehouseDTOResourceIT.createEntity(em);
            em.persist(warehouse);
            em.flush();
        } else {
            warehouse = TestUtil.findAll(em, Warehouse.class).get(0);
        }
        vehicle.setWarehouse(warehouse);
        return vehicle;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vehicle createUpdatedEntity(EntityManager em) {
        Vehicle vehicle = new Vehicle()
            .make(UPDATED_MAKE)
            .model(UPDATED_MODEL)
            .yearModel(UPDATED_YEAR_MODEL)
            .price(UPDATED_PRICE)
            .licensed(UPDATED_LICENSED)
            .dateAdded(UPDATED_DATE_ADDED);
        // Add required entity
        Location location;
        if (TestUtil.findAll(em, Location.class).isEmpty()) {
            location = LocationResourceIT.createUpdatedEntity(em);
            em.persist(location);
            em.flush();
        } else {
            location = TestUtil.findAll(em, Location.class).get(0);
        }
        vehicle.setLocation(location);
        // Add required entity
        Warehouse warehouse;
        if (TestUtil.findAll(em, Warehouse.class).isEmpty()) {
            warehouse = WarehouseDTOResourceIT.createUpdatedEntity(em);
            em.persist(warehouse);
            em.flush();
        } else {
            warehouse = TestUtil.findAll(em, Warehouse.class).get(0);
        }
        vehicle.setWarehouse(warehouse);
        return vehicle;
    }

    @BeforeEach
    public void initTest() {
        vehicle = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehicle() throws Exception {
        int databaseSizeBeforeCreate = vehicleRepository.findAll().size();

        // Create the Vehicle
        restVehicleMockMvc.perform(post("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicle)))
            .andExpect(status().isCreated());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeCreate + 1);
        Vehicle testVehicle = vehicleList.get(vehicleList.size() - 1);
        assertThat(testVehicle.getMake()).isEqualTo(DEFAULT_MAKE);
        assertThat(testVehicle.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testVehicle.getYearModel()).isEqualTo(DEFAULT_YEAR_MODEL);
        assertThat(testVehicle.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testVehicle.isLicensed()).isEqualTo(DEFAULT_LICENSED);
        assertThat(testVehicle.getDateAdded()).isEqualTo(DEFAULT_DATE_ADDED);
    }

    @Test
    @Transactional
    public void createVehicleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehicleRepository.findAll().size();

        // Create the Vehicle with an existing ID
        vehicle.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleMockMvc.perform(post("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicle)))
            .andExpect(status().isBadRequest());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMakeIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setMake(null);

        // Create the Vehicle, which fails.

        restVehicleMockMvc.perform(post("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicle)))
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModelIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setModel(null);

        // Create the Vehicle, which fails.

        restVehicleMockMvc.perform(post("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicle)))
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYearModelIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setYearModel(null);

        // Create the Vehicle, which fails.

        restVehicleMockMvc.perform(post("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicle)))
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setPrice(null);

        // Create the Vehicle, which fails.

        restVehicleMockMvc.perform(post("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicle)))
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLicensedIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setLicensed(null);

        // Create the Vehicle, which fails.

        restVehicleMockMvc.perform(post("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicle)))
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateAddedIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setDateAdded(null);

        // Create the Vehicle, which fails.

        restVehicleMockMvc.perform(post("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicle)))
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVehicles() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList
        restVehicleMockMvc.perform(get("/api/vehicles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicle.getId().intValue())))
            .andExpect(jsonPath("$.[*].make").value(hasItem(DEFAULT_MAKE)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].yearModel").value(hasItem(DEFAULT_YEAR_MODEL)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].licensed").value(hasItem(DEFAULT_LICENSED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateAdded").value(hasItem(DEFAULT_DATE_ADDED.toString())));
    }

    @Test
    @Transactional
    public void getVehicle() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get the vehicle
        restVehicleMockMvc.perform(get("/api/vehicles/{id}", vehicle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vehicle.getId().intValue()))
            .andExpect(jsonPath("$.make").value(DEFAULT_MAKE))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.yearModel").value(DEFAULT_YEAR_MODEL))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.licensed").value(DEFAULT_LICENSED.booleanValue()))
            .andExpect(jsonPath("$.dateAdded").value(DEFAULT_DATE_ADDED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVehicle() throws Exception {
        // Get the vehicle
        restVehicleMockMvc.perform(get("/api/vehicles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehicle() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();

        // Update the vehicle
        Vehicle updatedVehicle = vehicleRepository.findById(vehicle.getId()).get();
        // Disconnect from session so that the updates on updatedVehicle are not directly saved in db
        em.detach(updatedVehicle);
        updatedVehicle
            .make(UPDATED_MAKE)
            .model(UPDATED_MODEL)
            .yearModel(UPDATED_YEAR_MODEL)
            .price(UPDATED_PRICE)
            .licensed(UPDATED_LICENSED)
            .dateAdded(UPDATED_DATE_ADDED);

        restVehicleMockMvc.perform(put("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVehicle)))
            .andExpect(status().isOk());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
        Vehicle testVehicle = vehicleList.get(vehicleList.size() - 1);
        assertThat(testVehicle.getMake()).isEqualTo(UPDATED_MAKE);
        assertThat(testVehicle.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testVehicle.getYearModel()).isEqualTo(UPDATED_YEAR_MODEL);
        assertThat(testVehicle.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testVehicle.isLicensed()).isEqualTo(UPDATED_LICENSED);
        assertThat(testVehicle.getDateAdded()).isEqualTo(UPDATED_DATE_ADDED);
    }

    @Test
    @Transactional
    public void updateNonExistingVehicle() throws Exception {
        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();

        // Create the Vehicle

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleMockMvc.perform(put("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicle)))
            .andExpect(status().isBadRequest());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVehicle() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        int databaseSizeBeforeDelete = vehicleRepository.findAll().size();

        // Delete the vehicle
        restVehicleMockMvc.perform(delete("/api/vehicles/{id}", vehicle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
