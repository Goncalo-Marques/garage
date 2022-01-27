package api.garage.helper;

import api.mappings.garage.ErrorResponse;

import java.sql.Timestamp;

public class ErrorClientResponses {
    public static ErrorResponse errorClientBody() {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(404)
                .error("Not Found")
                .message("Client not found")
                .path("/client")
                .build();
    }

    public static ErrorResponse errorClientNotFound(Integer vehicleID) {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(404)
                .error("Not Found")
                .message("Client not found")
                .path("/vehicle/" + vehicleID)
                .build();
    }

    public static ErrorResponse errorClientInvalidID(Integer vehicleID) {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(400)
                .error("Bad Request")
                .message("Invalid ID")
                .path("/vehicle/" + vehicleID)
                .build();
    }
}
