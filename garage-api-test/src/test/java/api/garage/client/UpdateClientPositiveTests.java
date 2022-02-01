package api.garage.client;

import api.mappings.garage.client.ClientRequest;
import api.mappings.garage.client.ClientResponse;
import api.retrofit.garage.Client;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import static api.garage.helper.ClientRequests.clientPositive;
import static api.validators.ClientValidator.assertClientResponse;
import static api.validators.ResponseBodyValidator.assertBodyNotNull;
import static api.validators.ResponseBodyValidator.assertID;
import static api.validators.ResponseCodeValidator.*;

public class UpdateClientPositiveTests {

    private Integer createdClientID;
    private ClientRequest createdClientRequest;

    @BeforeMethod
    public void createClient() throws IOException {
        createdClientRequest = clientPositive();

        Response<Integer> response = Client.createClient(createdClientRequest);
        assertCreated(response);

        createdClientID = response.body();
    }

    @AfterMethod
    public void deleteClient() throws IOException {
        if (createdClientID == null) return;

        Response<Void> response = Client.deleteClientByID(createdClientID, null);
        assertNoContent(response);

        createdClientID = null;
        createdClientRequest = null;
    }

    @Test(description = "ID: GT0001")
    public void updateClientTest() throws IOException, ParseException {
        ClientRequest updatedClientRequest = createdClientRequest;
        updatedClientRequest.setFirstName("Jose");
        updatedClientRequest.setLastName("Marques");
        updatedClientRequest.setAddress("Rua das Barrocas");
        updatedClientRequest.setPostalCode("3587-008");
        updatedClientRequest.setCity("Agueda");
        updatedClientRequest.setCountry("Portugal");
        updatedClientRequest.setPhoneNumber(916234785);
        updatedClientRequest.setNif(257369987);
        updatedClientRequest.setBirthDate("1997-08-07");
        updatedClientRequest.setClientDate("2020-08-07");

        Response<Integer> updateResponse = Client.updateClient(createdClientID, updatedClientRequest);
        assertOk(updateResponse);
        assertBodyNotNull(updateResponse);
        assertID(updateResponse.body(), createdClientID);

        Response<ClientResponse> getResponse = Client.getClientByID(updateResponse.body());
        assertOk(getResponse);

        assertClientResponse(getResponse.body(), updatedClientRequest);
        assertID(getResponse.body().getId(), updateResponse.body());
    }

    @Test(description = "ID: GT0001")
    public void updateClientWithBirthDateEqualToTodayTest() throws IOException, ParseException {
        ClientRequest updatedClientRequest = createdClientRequest;
        updatedClientRequest.setBirthDate(new SimpleDateFormat("yyyy-MM-dd").format(Date.valueOf(LocalDate.now())));

        Response<Integer> updateResponse = Client.updateClient(createdClientID, updatedClientRequest);
        assertOk(updateResponse);
        assertBodyNotNull(updateResponse);
        assertID(updateResponse.body(), createdClientID);

        Response<ClientResponse> getResponse = Client.getClientByID(updateResponse.body());
        assertOk(getResponse);

        assertClientResponse(getResponse.body(), updatedClientRequest);
        assertID(getResponse.body().getId(), updateResponse.body());
    }

    @Test(description = "ID: GT0001")
    public void updateClientWithBirthDateLessThanTodayTest() throws IOException, ParseException {
        ClientRequest updatedClientRequest = createdClientRequest;
        updatedClientRequest.setBirthDate(new SimpleDateFormat("yyyy-MM-dd").format(Date.valueOf(LocalDate.now().minusDays(25))));

        Response<Integer> updateResponse = Client.updateClient(createdClientID, updatedClientRequest);
        assertOk(updateResponse);
        assertBodyNotNull(updateResponse);
        assertID(updateResponse.body(), createdClientID);

        Response<ClientResponse> getResponse = Client.getClientByID(updateResponse.body());
        assertOk(getResponse);

        assertClientResponse(getResponse.body(), updatedClientRequest);
        assertID(getResponse.body().getId(), updateResponse.body());
    }

    @Test(description = "ID: GT0001")
    public void updateClientWithClientDateEqualToTodayTest() throws IOException, ParseException {
        ClientRequest updatedClientRequest = createdClientRequest;
        updatedClientRequest.setClientDate(new SimpleDateFormat("yyyy-MM-dd").format(Date.valueOf(LocalDate.now())));

        Response<Integer> updateResponse = Client.updateClient(createdClientID, updatedClientRequest);
        assertOk(updateResponse);
        assertBodyNotNull(updateResponse);
        assertID(updateResponse.body(), createdClientID);

        Response<ClientResponse> getResponse = Client.getClientByID(updateResponse.body());
        assertOk(getResponse);

        assertClientResponse(getResponse.body(), updatedClientRequest);
        assertID(getResponse.body().getId(), updateResponse.body());
    }

    @Test(description = "ID: GT0001")
    public void updateClientWithClientDateLessThanTodayTest() throws IOException, ParseException {
        ClientRequest updatedClientRequest = createdClientRequest;
        updatedClientRequest.setClientDate(new SimpleDateFormat("yyyy-MM-dd").format(Date.valueOf(LocalDate.now().minusDays(25))));

        Response<Integer> updateResponse = Client.updateClient(createdClientID, updatedClientRequest);
        assertOk(updateResponse);
        assertBodyNotNull(updateResponse);
        assertID(updateResponse.body(), createdClientID);

        Response<ClientResponse> getResponse = Client.getClientByID(updateResponse.body());
        assertOk(getResponse);

        assertClientResponse(getResponse.body(), updatedClientRequest);
        assertID(getResponse.body().getId(), updateResponse.body());
    }
}
