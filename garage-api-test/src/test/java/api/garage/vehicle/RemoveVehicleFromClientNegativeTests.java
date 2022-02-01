package api.garage.vehicle;

import api.mappings.garage.ErrorResponse;
import api.retrofit.garage.Client;
import api.retrofit.garage.Vehicle;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;

import static api.garage.helper.ClientRequests.clientPositive;
import static api.garage.helper.ErrorVehicleResponses.*;
import static api.garage.helper.VehicleRequests.vehiclePositive;
import static api.retrofit.garage.Error.getErrorResponse;
import static api.validators.ErrorResponseValidator.assertErrorResponse;
import static api.validators.ResponseCodeValidator.*;

public class RemoveVehicleFromClientNegativeTests {
    private Integer createdVehicleID;
    private Integer createdClientID;

    @BeforeMethod
    public void createVehicleAndClient() throws IOException {
        Response<Integer> responseVehicle = Vehicle.createVehicle(vehiclePositive());
        assertCreated(responseVehicle);
        createdVehicleID = responseVehicle.body();

        Response<Integer> responseClient = Client.createClient(clientPositive());
        assertCreated(responseClient);
        createdClientID = responseClient.body();

        Response<Void> addResponse = Vehicle.addVehicleToClient(createdVehicleID, createdClientID);
        assertNoContent(addResponse);
    }

    @AfterMethod
    public void deleteVehicleAndClient() throws IOException {
        if (createdVehicleID != null) {
            Response<Void> response = Vehicle.deleteVehicleByID(createdVehicleID);
            assertNoContent(response);
        }

        if (createdClientID != null) {
            Response<Void> response = Client.deleteClientByID(createdClientID, null);
            assertNoContent(response);
        }

        createdVehicleID = null;
        createdClientID = null;
    }

    @Test(description = "ID: GT0065")
    public void removeVehicleFromClientWithID0Test() throws IOException {
        Integer vehicleIDToTest = 0;
        Response<Void> removeResponse = Vehicle.removeVehicleFromClient(vehicleIDToTest);
        assertBadRequest(removeResponse);

        ErrorResponse expectedResponse = errorRemovingVehicleFromClientInvalidID(vehicleIDToTest);
        assertErrorResponse(getErrorResponse(removeResponse), expectedResponse);
    }

    @Test(description = "ID: GT0066")
    public void removeVehicleFromClientWithNegativeIDTest() throws IOException {
        Integer vehicleIDToTest = -100;
        Response<Void> removeResponse = Vehicle.removeVehicleFromClient(vehicleIDToTest);
        assertBadRequest(removeResponse);

        ErrorResponse expectedResponse = errorRemovingVehicleFromClientInvalidID(vehicleIDToTest);
        assertErrorResponse(getErrorResponse(removeResponse), expectedResponse);
    }

    @Test(description = "ID: GT0067")
    public void removeNonExistentVehicleFromClientTest() throws IOException {
        Integer vehicleIDToTest = createdVehicleID;

        Response<Void> deleteResponse = Vehicle.deleteVehicleByID(createdVehicleID);
        assertNoContent(deleteResponse);
        createdVehicleID = null;

        Response<Void> removeResponse = Vehicle.removeVehicleFromClient(vehicleIDToTest);
        assertNotFound(removeResponse);

        ErrorResponse expectedResponse = errorRemovingVehicleFromClientNotFound(vehicleIDToTest);
        assertErrorResponse(getErrorResponse(removeResponse), expectedResponse);
    }

    @Test(description = "ID: GT0068")
    public void removeClientFromVehicleWithoutClientTest() throws IOException {
        Response<Void> removeResponse = Vehicle.removeVehicleFromClient(createdVehicleID);
        assertNoContent(removeResponse);

        removeResponse = Vehicle.removeVehicleFromClient(createdVehicleID);
        assertBadRequest(removeResponse);

        ErrorResponse expectedResponse = errorVehicleAlreadyHasNoClient(createdVehicleID);
        assertErrorResponse(getErrorResponse(removeResponse), expectedResponse);
    }
}
