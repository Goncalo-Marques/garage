package api.garage.helper;

import api.mappings.garage.vehicle.CreateVehicleRequest;

public class VehicleResquests {
    public static CreateVehicleRequest vehiclePositive() {
        return CreateVehicleRequest.builder()
                .brand("Tesla")
                .model("Model S")
                .year(2012)
                .type("Electric")
                .plate("GG-04-20")
                .active(false)
                .build();
    }
}
