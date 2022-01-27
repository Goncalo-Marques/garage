package api.garage.vehicle;

import api.mappings.garage.ErrorResponse;
import api.mappings.garage.vehicle.CreateVehicleRequest;
import api.retrofit.garage.Vehicle;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;

import static api.retrofit.garage.Error.getErrorResponse;
import static api.validators.ErrorResponseValidator.assertErrorResponse;
import static api.validators.ResponseCodeValidator.assertBadRequest;
import static api.validators.ResponseCodeValidator.assertNoContent;

public class CreateVehicleNegativeTests {
    private Integer createdVehicleID;

    @AfterMethod
    public void deleteVehicle() throws IOException {
        if (createdVehicleID == null) return;
        Response<Void> response = Vehicle.deleteVehicleByID(createdVehicleID);
        assertNoContent(response);
        createdVehicleID = null;
    }

    @Test(description = "ID: GT0001")
    public void createVehicleNullFieldsTest() throws IOException {
        CreateVehicleRequest requestBody = CreateVehicleRequest.builder().build();

        Response<Integer> createResponse = Vehicle.createVehicle(requestBody);

        // in case the vehicle is created by mistake
        createdVehicleID = createResponse.body();

        assertBadRequest(createResponse);

        ErrorResponse expectedResponse = ErrorResponse.builder()
                .status(400)
                .error("Bad Request")
                .message("Cannot invoke \\\"String.matches(String)\\\" because \\\"plate\\\" is null")
                .path("/vehicle")
                .build();
        // TODO: currently not returning the right status. Actual: 500; Expected: 400
        assertErrorResponse(getErrorResponse(createResponse), expectedResponse, true);
    }
}
