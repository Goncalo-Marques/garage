package api.garage.helper;

import api.mappings.garage.ErrorResponse;

import java.sql.Timestamp;

public class ErrorVehicleResponses {
    public static ErrorResponse errorVehicleInvalidBody() {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(400)
                .error("Bad Request")
                .message("Invalid body")
                .path("/vehicle")
                .build();
    }

    public static ErrorResponse errorVehicleInvalidID(Integer vehicleID) {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(400)
                .error("Bad Request")
                .message("Invalid ID")
                .path("/vehicle/" + vehicleID)
                .build();
    }

    public static ErrorResponse errorVehicleInvalidPlate() {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(400)
                .error("Bad Request")
                .message("Invalid Plate")
                .path("/vehicle")
                .build();
    }

    public static ErrorResponse errorVehicleDuplicatedPlate() {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(400)
                .error("Bad Request")
                .message("Duplicated Plate")
                .path("/vehicle")
                .build();
    }

    public static ErrorResponse errorVehicleInvalidYear() {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(400)
                .error("Bad Request")
                .message("Invalid Year")
                .path("/vehicle")
                .build();
    }

    public static ErrorResponse errorVehicleNotFound(Integer vehicleID) {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(404)
                .error("Not Found")
                .message("Vehicle not found")
                .path("/vehicle/" + vehicleID)
                .build();
    }
}
