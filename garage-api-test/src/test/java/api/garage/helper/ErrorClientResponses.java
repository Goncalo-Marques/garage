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

    public static ErrorResponse errorClientInvalidID(Integer clientID) {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(400)
                .error("Bad Request")
                .message("Invalid ID")
                .path("/client/" + clientID)
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

    public static ErrorResponse errorClientDuplicatedNIFByID(Integer clientID) {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(400)
                .error("Bad Request")
                .message("Duplicated NIF")
                .path("/client/" + clientID)
                .build();
    }

    public static ErrorResponse errorClientInvalidClientDate() {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(400)
                .error("Bad Request")
                .message("Invalid client date")
                .path("/client")
                .build();
    }

    public static ErrorResponse errorClientInvalidBirthDate() {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(400)
                .error("Bad Request")
                .message("Invalid birth date")
                .path("/client")
                .build();
    }

    public static ErrorResponse errorClientInvalidIDBody() {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(400)
                .error("Bad Request")
                .message("Id should be null on creation")
                .path("/client")
                .build();
    }

    public static ErrorResponse errorClientInvalidIDBodyBydID(Integer clientID) {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(400)
                .error("Bad Request")
                .message("Id on body should be null on Update")
                .path("/client/" + clientID)
                .build();
    }

    public static ErrorResponse errorClientInvalidPostalCode() {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(400)
                .error("Bad Request")
                .message("Invalid postal code")
                .path("/client")
                .build();
    }

    public static ErrorResponse errorClientInvalidPhoneNumber() {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(400)
                .error("Bad Request")
                .message("Invalid Phone Number")
                .path("/client")
                .build();
    }

    public static ErrorResponse errorClientInvalidPhoneNumberByID(Integer clientID) {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(400)
                .error("Bad Request")
                .message("Invalid Phone Number")
                .path("/client/" + clientID)
                .build();
    }

    public static ErrorResponse errorClientInvalidPostalCodeByID(Integer clientID) {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(400)
                .error("Bad Request")
                .message("Invalid postal code")
                .path("/client/" + clientID)
                .build();
    }

    public static ErrorResponse errorClientNotFound(Integer clientID) {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(404)
                .error("Not Found")
                .message("Client not found")
                .path("/client/" + clientID)
                .build();
    }

    public static ErrorResponse errorClientInvalidBodyByID(Integer clientID) {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(400)
                .error("Bad Request")
                .message("Invalid body")
                .path("/client/" + clientID)
                .build();
    }

    public static ErrorResponse errorClientInvalidBirthDateByID(Integer clientID) {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(400)
                .error("Bad Request")
                .message("Invalid birtth date")
                .path("/client/" + clientID)
                .build();
    }
    public static ErrorResponse errorClientInvalidClientDateByID(Integer clientID) {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(400)
                .error("Bad Request")
                .message("Invalid client date")
                .path("/client/" + clientID)
                .build();
    }
}
