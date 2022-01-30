package api.garage.helper;

import api.mappings.garage.vehicle.VehicleRequest;

public class VehicleRequests {
    public static VehicleRequest vehiclePositive() {
        return VehicleRequest.builder()
                .brand("Tesla")
                .model("Model S")
                .year(2012)
                .type("Electric")
                .plate("GG-04-20")
                .active(true)
                .build();
    }
}
