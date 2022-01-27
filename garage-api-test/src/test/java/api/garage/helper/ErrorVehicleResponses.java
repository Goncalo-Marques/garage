package api.garage.helper;

import api.mappings.garage.ErrorResponse;

public class ErrorVehicleResponses {
    public static ErrorResponse errorVehicleInvalidBody() {
        return ErrorResponse.builder()
                .status(400)
                .error("Bad Request")
                .message("Invalid body")
                .path("/vehicle")
                .build();
    }

    public static ErrorResponse errorVehicleInvalidID(Integer vehicleID) {
        return ErrorResponse.builder()
                .status(400)
                .error("Bad Request")
                .message("Invalid ID")
                .path("/vehicle/" + vehicleID)
                .build();
    }

    public static ErrorResponse errorVehicleNotFound(Integer vehicleID) {
        return ErrorResponse.builder()
                .status(404)
                .error("Not Found")
                .message("Vehicle not found")
                .path("/vehicle/" + vehicleID)
                .build();
    }
}
