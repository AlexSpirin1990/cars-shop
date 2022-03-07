package com.cognizant.cars_shop.util;

import com.cognizant.cars_shop.domain.Vehicle;
import com.cognizant.cars_shop.service.dto.Cars;
import com.cognizant.cars_shop.service.dto.LocationDTO;
import com.cognizant.cars_shop.service.dto.VehicleDTO;
import com.cognizant.cars_shop.service.dto.WarehouseDTO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class WarehouseUtil {
    public static List<WarehouseDTO> asListWarehouses(List<Vehicle> vehicles) {
        List<WarehouseDTO> warehouses = new ArrayList<>();

        vehicles.stream()
            .collect(Collectors.groupingBy(Vehicle::getWarehouse, Collectors.toList())).forEach((warehouse, warehouseVehicles) -> {
                warehouseVehicles = warehouseVehicles.stream().sorted(Comparator.comparing(Vehicle::getDateAdded)).collect(Collectors.toList());
                Cars cars = new Cars(warehouseVehicles.get(0).getLocation().getName(), warehouseVehicles.stream().map(VehicleUtil::asTO).collect(Collectors.toList()));
                warehouses.add(new WarehouseDTO(warehouse.getId(), warehouse.getName(), new LocationDTO(warehouse.getLocationLat(), warehouse.getLocationLat()),
                    cars));
            });

        return warehouses;
    }
}
