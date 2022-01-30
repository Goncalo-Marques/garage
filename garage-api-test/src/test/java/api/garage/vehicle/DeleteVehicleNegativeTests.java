package api.garage.vehicle;

import api.mappings.garage.ErrorResponse;
import api.mappings.garage.vehicle.CreateVehicleRequest;
import api.retrofit.garage.Vehicle;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;

import static api.garage.helper.ErrorVehicleResponses.errorVehicleInvalidID;
import static api.garage.helper.ErrorVehicleResponses.errorVehicleNotFound;
import static api.garage.helper.VehicleRequests.vehiclePositive;
import static api.retrofit.garage.Error.getErrorResponse;
import static api.validators.ErrorResponseValidator.assertErrorResponse;
import static api.validators.ResponseBodyValidator.assertIDNotNull;
import static api.validators.ResponseCodeValidator.*;

public class DeleteVehicleNegativeTests {
    private Integer createdVehicleID;

    @AfterMethod
    public void deleteVehicle() throws IOException {
        if (createdVehicleID == null) return;

        Response<Void> response = Vehicle.deleteVehicleByID(createdVehicleID);
        assertNoContent(response);

        createdVehicleID = null;
    }

    @Test(description = "ID: GT0001")
    public void deleteNonExistentVehicleTest() throws IOException {
        CreateVehicleRequest vehicle = vehiclePositive();

        Response<Integer> createResponse = Vehicle.createVehicle(vehicle);
        assertCreated(createResponse);
        createdVehicleID = createResponse.body();
        Integer vehicleIDToTest = createdVehicleID;
        assertIDNotNull(vehicleIDToTest);

        Response<Void> response = Vehicle.deleteVehicleByID(vehicleIDToTest);
        assertNoContent(response);
        createdVehicleID = null;

        Response<Void> getResponse = Vehicle.deleteVehicleByID(vehicleIDToTest);
        assertNotFound(getResponse);

        ErrorResponse expectedResponse = errorVehicleNotFound(vehicleIDToTest);
        assertErrorResponse(getErrorResponse(getResponse), expectedResponse);
    }

    @Test(description = "ID: GT0001")
    public void deleteVehicleWithID0Test() throws IOException {
        Integer vehicleIDToTest = 0;
        Response<Void> getResponse = Vehicle.deleteVehicleByID(vehicleIDToTest);
        assertBadRequest(getResponse);

        ErrorResponse expectedResponse = errorVehicleInvalidID(vehicleIDToTest);
        assertErrorResponse(getErrorResponse(getResponse), expectedResponse);
    }

    @Test(description = "ID: GT0001")
    public void deleteVehicleWithNegativeIDTest() throws IOException {
        Integer vehicleIDToTest = -100;
        Response<Void> getResponse = Vehicle.deleteVehicleByID(vehicleIDToTest);
        assertBadRequest(getResponse);

        ErrorResponse expectedResponse = errorVehicleInvalidID(vehicleIDToTest);
        assertErrorResponse(getErrorResponse(getResponse), expectedResponse);
    }
}
