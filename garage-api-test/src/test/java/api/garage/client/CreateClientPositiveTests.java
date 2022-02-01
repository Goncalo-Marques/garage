package api.garage.client;

import api.mappings.garage.client.ClientRequest;
import api.mappings.garage.client.ClientResponse;
import api.retrofit.garage.Client;
import api.retrofit.garage.Vehicle;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import static api.garage.helper.ClientRequests.clientPositive;
import static api.garage.helper.VehicleRequests.vehiclePositive;
import static api.validators.ClientValidator.assertClientResponse;
import static api.validators.ListValidator.assertListHasSize;
import static api.validators.ResponseBodyValidator.assertBodyNotNull;
import static api.validators.ResponseBodyValidator.assertID;
import static api.validators.ResponseCodeValidator.*;

public class CreateClientPositiveTests {
    private Integer createdClientID;
    private Integer createdVehicleID;

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

    @Test(description = "ID GT001")
    public void createClientTest() throws IOException, ParseException {
        ClientRequest createdClientRequest = clientPositive();

        Response<Integer> createResponse = Client.createClient(createdClientRequest);
        assertBodyNotNull(createResponse);
        createdClientID = createResponse.body();
        assertCreated(createResponse);

        Response<ClientResponse> getResponse = Client.getClientByID(createdClientID);
        assertOk(getResponse);

        assertClientResponse(getResponse.body(), createdClientRequest);
        assertID(getResponse.body().getId(), createdClientID);
    }

    @Test(description = "ID GT001")
    public void createClientAndVehicleTest() throws IOException {

        // CREATE VEHICLE
        Response<Integer> responseVehicle = Vehicle.createVehicle(vehiclePositive());
        assertCreated(responseVehicle);
        createdVehicleID = responseVehicle.body();

        // CREATE CLIENT
        Response<Integer> responseClient = Client.createClient(clientPositive());
        assertCreated(responseClient);
        createdClientID = responseClient.body();

        // ADD VEHICLE TO CLIENT
        Response<Void> addResponse = Vehicle.addVehicleToClient(createdVehicleID, createdClientID);
        assertNoContent(addResponse);

        Response<ClientResponse> getResponse = Client.getClientByID(createdClientID);
        assertOk(getResponse);
        assertBodyNotNull(getResponse);
        assertListHasSize(getResponse.body().getVehicles(), 1);

    }

    @Test(description = "ID GT001")
    public void createClientWithBirthDateEqualToTodayTest() throws IOException, ParseException {
        ClientRequest createdClientRequest = clientPositive();
        createdClientRequest.setBirthDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));

        Response<Integer> createResponse = Client.createClient(createdClientRequest);
        assertBodyNotNull(createResponse);
        createdClientID = createResponse.body();
        assertCreated(createResponse);

        Response<ClientResponse> getResponse = Client.getClientByID(createdClientID);
        assertOk(getResponse);

        assertClientResponse(getResponse.body(), createdClientRequest);
        assertID(getResponse.body().getId(), createdClientID);
    }

    @Test(description = "ID GT001")
    public void createClientWithBirthDateLessThanToTodayTest() throws IOException, ParseException {
        ClientRequest createdClientRequest = clientPositive();
        createdClientRequest.setBirthDate(new SimpleDateFormat("yyyy-MM-dd").format(Date.valueOf(LocalDate.now().minusDays(4))));

        Response<Integer> createResponse = Client.createClient(createdClientRequest);
        assertBodyNotNull(createResponse);
        createdClientID = createResponse.body();
        assertCreated(createResponse);

        Response<ClientResponse> getResponse = Client.getClientByID(createdClientID);
        assertOk(getResponse);

        assertClientResponse(getResponse.body(), createdClientRequest);
        assertID(getResponse.body().getId(), createdClientID);
    }


    @Test(description = "ID GT001")
    public void createClientWithClientDateEqualToTodayTest() throws IOException, ParseException {
        ClientRequest createdClientRequest = clientPositive();
        createdClientRequest.setClientDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));

        Response<Integer> createResponse = Client.createClient(createdClientRequest);
        assertBodyNotNull(createResponse);
        createdClientID = createResponse.body();
        assertCreated(createResponse);

        Response<ClientResponse> getResponse = Client.getClientByID(createdClientID);
        assertOk(getResponse);

        assertClientResponse(getResponse.body(), createdClientRequest);
        assertID(getResponse.body().getId(), createdClientID);
    }

    @Test(description = "ID GT001")
    public void createClientWithClientDateLessThanToTodayTest() throws IOException, ParseException {
        ClientRequest createdClientRequest = clientPositive();
        createdClientRequest.setClientDate(new SimpleDateFormat("yyyy-MM-dd").format(Date.valueOf(LocalDate.now().minusDays(4))));

        Response<Integer> createResponse = Client.createClient(createdClientRequest);
        assertBodyNotNull(createResponse);
        createdClientID = createResponse.body();
        assertCreated(createResponse);

        Response<ClientResponse> getResponse = Client.getClientByID(createdClientID);
        assertOk(getResponse);

        assertClientResponse(getResponse.body(), createdClientRequest);
        assertID(getResponse.body().getId(), createdClientID);
    }
}
