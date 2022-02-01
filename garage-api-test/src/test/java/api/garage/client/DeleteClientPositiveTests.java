package api.garage.client;

import api.mappings.garage.client.ClientRequest;
import api.mappings.garage.client.ClientResponse;
import api.mappings.garage.vehicle.VehicleResponse;
import api.retrofit.garage.Client;
import api.retrofit.garage.Vehicle;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;

import static api.garage.helper.ClientRequests.clientPositive;
import static api.garage.helper.VehicleRequests.vehiclePositive;
import static api.validators.ResponseCodeValidator.*;

public class DeleteClientPositiveTests {
    private Integer createdClientID;
    private Integer createdVehicleID;

    @BeforeMethod
    public void createClient() throws IOException {
        ClientRequest createdClientRequest = clientPositive();

        Response<Integer> response = Client.createClient(createdClientRequest);
        assertCreated(response);

        createdClientID = response.body();
    }

    @AfterMethod
    public void deleteClientAndVehicle() throws IOException {
        if (createdClientID != null) {
            Response<Void> response = Client.deleteClientByID(createdClientID, null);
            assertNoContent(response);
        }
        if (createdVehicleID != null) {
            Response<Void> response = Vehicle.deleteVehicleByID(createdVehicleID);
            assertNoContent(response);
        }

        createdClientID = null;
        createdVehicleID = null;
    }

    @Test(description = "ID: GT0017")
    public void deleteClientTest() throws IOException {
        Integer clientIdToTest = createdClientID;

        Response<Void> deleteResponse = Client.deleteClientByID(clientIdToTest, null);
        assertNoContent(deleteResponse);
        createdClientID = null;

        Response<ClientResponse> getResponse = Client.getClientByID(clientIdToTest);
        assertNotFound(getResponse);
    }

    @Test(description = "ID: GT0018")
    public void deleteClientRemoveClientTrueTest() throws IOException {
        Integer clientIDToTest = createdClientID;
        Integer vehicleIDToTest;

        // CREATE VEHICLE
        Response<Integer> responseVehicle = Vehicle.createVehicle(vehiclePositive());
        assertCreated(responseVehicle);
        createdVehicleID = responseVehicle.body();
        vehicleIDToTest = createdVehicleID;

        // ADD VEHICLE TO CLIENT
        Response<Void> addResponse = Vehicle.addVehicleToClient(vehicleIDToTest, clientIDToTest);
        assertNoContent(addResponse);

        // DELETE CLIENT AND REMOVE VEHICLES EQUAL TRUE
        Response<Void> deleteResponse = Client.deleteClientByID(clientIDToTest, true);
        assertNoContent(deleteResponse);
        createdClientID = null;
        createdVehicleID = null;

        Response<ClientResponse> getResponseClient = Client.getClientByID(clientIDToTest);
        assertNotFound(getResponseClient);

        Response<VehicleResponse> getResponseVehicle = Vehicle.getVehicleByID(vehicleIDToTest);
        assertNotFound(getResponseVehicle);
    }

    @Test(description = "ID: GT0019")
    public void deleteClientRemoveClientFalseTest() throws IOException {
        Integer clientIDToTest = createdClientID;
        Integer vehicleIDToTest;

        // CREATE VEHICLE
        Response<Integer> responseVehicle = Vehicle.createVehicle(vehiclePositive());
        assertCreated(responseVehicle);
        createdVehicleID = responseVehicle.body();
        vehicleIDToTest = createdVehicleID;

        // ADD VEHICLE TO CLIENT
        Response<Void> addResponse = Vehicle.addVehicleToClient(vehicleIDToTest, clientIDToTest);
        assertNoContent(addResponse);

        // DELETE CLIENT AND REMOVE VEHICLES EQUAL TRUE
        Response<Void> deleteResponse = Client.deleteClientByID(clientIDToTest, false);
        assertNoContent(deleteResponse);
        createdClientID = null;

        Response<ClientResponse> getResponseClient = Client.getClientByID(clientIDToTest);
        assertNotFound(getResponseClient);

        Response<VehicleResponse> getResponseVehicle = Vehicle.getVehicleByID(vehicleIDToTest);
        assertOk(getResponseVehicle);
    }

    @Test(description = "ID: GT0020")
    public void deleteClientRemoveClientNullTest() throws IOException {
        Integer clientIDToTest = createdClientID;
        Integer vehicleIDToTest;

        // CREATE VEHICLE
        Response<Integer> responseVehicle = Vehicle.createVehicle(vehiclePositive());
        assertCreated(responseVehicle);
        createdVehicleID = responseVehicle.body();
        vehicleIDToTest = createdVehicleID;

        // ADD VEHICLE TO CLIENT
        Response<Void> addResponse = Vehicle.addVehicleToClient(vehicleIDToTest, clientIDToTest);
        assertNoContent(addResponse);

        // DELETE CLIENT AND REMOVE VEHICLES EQUAL TRUE
        Response<Void> deleteResponse = Client.deleteClientByID(clientIDToTest, null);
        assertNoContent(deleteResponse);
        createdClientID = null;

        Response<ClientResponse> getResponseClient = Client.getClientByID(clientIDToTest);
        assertNotFound(getResponseClient);

        Response<VehicleResponse> getResponseVehicle = Vehicle.getVehicleByID(vehicleIDToTest);
        assertOk(getResponseVehicle);
    }
}
