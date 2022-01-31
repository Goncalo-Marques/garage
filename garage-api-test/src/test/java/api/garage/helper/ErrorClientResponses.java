package api.garage.helper;

import api.mappings.garage.ErrorResponse;

import java.sql.Timestamp;

public class ErrorClientResponses {
    public static ErrorResponse errorClientInvalidBody() {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(400)
                .error("Bad Request")
                .message("Invalid body")
                .path("/client")
                .build();
    }

    public static ErrorResponse errorClientNotFound(Integer vehicleID) {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(404)
                .error("Not Found")
                .message("Client not found")
                .path("/client/" + vehicleID)
                .build();
    }

    public static ErrorResponse errorClientInvalidID(Integer vehicleID) {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(400)
                .error("Bad Request")
                .message("Invalid ID")
                .path("/client/" + vehicleID)
                .build();
    }

    public static ErrorResponse errorClientDuplicatedNIF() {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(400)
                .error("Bad Request")
                .message("Duplicated NIF")
                .path("/client")
                .build();
    }

    public static ErrorResponse errorClientInvalidDate() {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(400)
                .error("Bad Request")
                .message("Client date should not be greater than today")
                .path("/client")
                .build();
    }

    public static ErrorResponse errorClientInvalidBirthDate() {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(400)
                .error("Bad Request")
                .message("Birthday date should not be greater than today")
                .path("/client")
                .build();
    }
}
