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
import static api.garage.helper.ErrorVehicleResponses.errorAddingVehicleToClientNotFound;
import static api.garage.helper.ErrorVehicleResponses.errorVehicleAlreadyHasClient;
import static api.garage.helper.VehicleRequests.vehiclePositive;
import static api.retrofit.garage.Error.getErrorResponse;
import static api.validators.ErrorResponseValidator.assertErrorResponse;
import static api.validators.ResponseCodeValidator.*;

public class AddVehicleToClientNegativeTests {
    private Integer createdVehicleID;
    private Integer createdClientID;
    private Integer unexpectedCreatedClientID;

    @BeforeMethod
    public void createVehicleAndClient() throws IOException {
        Response<Integer> responseVehicle = Vehicle.createVehicle(vehiclePositive());
        assertCreated(responseVehicle);
        createdVehicleID = responseVehicle.body();

        Response<Integer> responseClient = Client.createClient(clientPositive());
        assertCreated(responseClient);
        createdClientID = responseClient.body();
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

        if (unexpectedCreatedClientID != null) {
            Response<Void> response = Client.deleteClientByID(unexpectedCreatedClientID, null);
            assertNoContent(response);
        }

        createdVehicleID = null;
        createdClientID = null;
        unexpectedCreatedClientID = null;
    }

    @Test(description = "ID: GT0041")
    public void addNonExistentVehicleToNonExistentClientTest() throws IOException {
        Integer vehicleIDToTest = createdVehicleID;
        Integer clientIDToTest = createdClientID;

        Response<Void> deleteResponse = Vehicle.deleteVehicleByID(createdVehicleID);
        assertNoContent(deleteResponse);
        createdVehicleID = null;

        deleteResponse = Client.deleteClientByID(createdClientID, null);
        assertNoContent(deleteResponse);
        createdClientID = null;

        Response<Void> response = Vehicle.addVehicleToClient(vehicleIDToTest, clientIDToTest);
        assertNotFound(response);

        ErrorResponse expectedResponse = errorAddingVehicleToClientNotFound(vehicleIDToTest, clientIDToTest);
        assertErrorResponse(getErrorResponse(response), expectedResponse);
    }

    @Test(description = "ID: GT0042")
    public void addNonExistentVehicleToExistentClientTest() throws IOException {
        Integer vehicleIDToTest = createdVehicleID;

        Response<Void> deleteResponse = Vehicle.deleteVehicleByID(createdVehicleID);
        assertNoContent(deleteResponse);
        createdVehicleID = null;

        Response<Void> response = Vehicle.addVehicleToClient(vehicleIDToTest, createdClientID);
        assertNotFound(response);

        ErrorResponse expectedResponse = errorAddingVehicleToClientNotFound(vehicleIDToTest, createdClientID);
        assertErrorResponse(getErrorResponse(response), expectedResponse);
    }

    @Test(description = "ID: GT0043")
    public void addExistentVehicleToNonExistentClientTest() throws IOException {
        Integer clientIDToTest = createdClientID;

        Response<Void> deleteResponse = Client.deleteClientByID(createdClientID, null);
        assertNoContent(deleteResponse);
        createdClientID = null;

        Response<Void> response = Vehicle.addVehicleToClient(createdVehicleID, clientIDToTest);
        assertNotFound(response);

        ErrorResponse expectedResponse = errorAddingVehicleToClientNotFound(createdVehicleID, clientIDToTest);
        assertErrorResponse(getErrorResponse(response), expectedResponse);
    }

    @Test(description = "ID: GT0044")
    public void addVehicleToClientDuplicatedTest() throws IOException {
        Response<Void> response = Vehicle.addVehicleToClient(createdVehicleID, createdClientID);
        assertNoContent(response);

        response = Vehicle.addVehicleToClient(createdVehicleID, createdClientID);
        assertBadRequest(response);

        ErrorResponse expectedResponse = errorVehicleAlreadyHasClient(createdVehicleID, createdClientID);
        assertErrorResponse(getErrorResponse(response), expectedResponse);
    }

    @Test(description = "ID: GT0045")
    public void addVehicleToDifferentClientTest() throws IOException {
        Response<Void> response = Vehicle.addVehicleToClient(createdVehicleID, createdClientID);
        assertNoContent(response);

        Response<Integer> responseClient = Client.createClient(clientPositive());
        assertCreated(responseClient);
        unexpectedCreatedClientID = responseClient.body();

        response = Vehicle.addVehicleToClient(createdVehicleID, unexpectedCreatedClientID);
        assertBadRequest(response);

        ErrorResponse expectedResponse = errorVehicleAlreadyHasClient(createdVehicleID, unexpectedCreatedClientID);
        assertErrorResponse(getErrorResponse(response), expectedResponse);
    }
}
