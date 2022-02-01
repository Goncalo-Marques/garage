package api.garage.client;

import api.mappings.garage.ErrorResponse;
import api.mappings.garage.client.ClientRequest;
import api.retrofit.garage.Client;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import static api.garage.helper.ClientRequests.clientPositive;
import static api.garage.helper.ErrorClientResponses.*;
import static api.retrofit.garage.Error.getErrorResponse;
import static api.validators.ErrorResponseValidator.assertErrorResponse;
import static api.validators.ResponseCodeValidator.*;

public class UpdateClientNegativeTests {
    private Integer unexpectedCreatedClientID;
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
        if (unexpectedCreatedClientID != null) {
            Response<Void> response = Client.deleteClientByID(unexpectedCreatedClientID, null);
            assertNoContent(response);
        }

        if (createdClientID != null) {
            Response<Void> response = Client.deleteClientByID(createdClientID, null);
            assertNoContent(response);
        }

        unexpectedCreatedClientID = null;
        createdClientID = null;
        createdClientRequest = null;
    }

    @Test(description = "ID: GT0001")
    public void updateNonExistentClientTest() throws IOException {
        Integer clientIDToTest = createdClientID;

        Response<Void> response = Client.deleteClientByID(clientIDToTest, null);
        assertNoContent(response);
        createdClientID = null;

        ClientRequest updatedClientRequest = createdClientRequest;

        Response<Integer> updateResponse = Client.updateClient(clientIDToTest, updatedClientRequest);
        assertNotFound(updateResponse);

        ErrorResponse expectedResponse = errorClientNotFound(clientIDToTest);
        assertErrorResponse(getErrorResponse(updateResponse), expectedResponse);
    }

    @Test(description = "ID: GT0001")
    public void updateClientWithID0Test() throws IOException {
        ClientRequest updatedClientRequest = createdClientRequest;
        Integer clientIDToTest = 0;

        Response<Integer> updateResponse = Client.updateClient(clientIDToTest, updatedClientRequest);
        assertBadRequest(updateResponse);

        ErrorResponse expectedResponse = errorClientInvalidID(clientIDToTest);
        assertErrorResponse(getErrorResponse(updateResponse), expectedResponse);
    }

    @Test(description = "ID: GT0001")
    public void updateClientWithNegativeIDTest() throws IOException {
        ClientRequest updatedClientRequest = createdClientRequest;
        Integer clientIDToTest = -126;

        Response<Integer> updateResponse = Client.updateClient(clientIDToTest, updatedClientRequest);
        assertBadRequest(updateResponse);

        ErrorResponse expectedResponse = errorClientInvalidID(clientIDToTest);
        assertErrorResponse(getErrorResponse(updateResponse), expectedResponse);
    }

    @Test(description = "ID: GT0001")
    public void updateClientNullFieldsTest() throws IOException {
        ClientRequest updatedClientRequest = ClientRequest.builder().build();

        Response<Integer> updateResponse = Client.updateClient(createdClientID, updatedClientRequest);
        assertBadRequest(updateResponse);

        ErrorResponse expectedResponse = errorClientInvalidBodyByID(createdClientID);
        assertErrorResponse(getErrorResponse(updateResponse), expectedResponse);
    }

    @Test(description = "ID: GT0001")
    public void updateClientDuplicatedNIFTest() throws IOException {
        Integer clientNIFToTest = 260128856;

        ClientRequest createdClientRequest = clientPositive();
        createdClientRequest.setNif(clientNIFToTest);

        Response<Integer> createResponse = Client.createClient(createdClientRequest);
        unexpectedCreatedClientID = createResponse.body();
        assertCreated(createResponse);

        ClientRequest updatedClientRequest = createdClientRequest;
        updatedClientRequest.setNif(clientNIFToTest);

        Response<Integer> updateResponse = Client.updateClient(createdClientID, updatedClientRequest);
        assertBadRequest(updateResponse);

        ErrorResponse expectedResponse = errorClientDuplicatedNIFByID(createdClientID);
        assertErrorResponse(getErrorResponse(updateResponse), expectedResponse);
    }

    @Test(description = "ID: GT0001")
    public void updateClientWithBirthDateGreaterThanTodayTest() throws IOException {
        ClientRequest updatedClientRequest = createdClientRequest;
        updatedClientRequest.setBirthDate(new SimpleDateFormat("yyyy-MM-dd").format(Date.valueOf(LocalDate.now().plusDays(4))));

        Response<Integer> updateResponse = Client.updateClient(createdClientID, updatedClientRequest);
        assertBadRequest(updateResponse);

        ErrorResponse expectedResponse = errorClientInvalidBirthDateByID(createdClientID);
        assertErrorResponse(getErrorResponse(updateResponse), expectedResponse);
    }

    @Test(description = "ID: GT0001")
    public void updateClientWithClientDateGreaterThanTodayTest() throws IOException {
        ClientRequest updatedClientRequest = createdClientRequest;
        updatedClientRequest.setClientDate(new SimpleDateFormat("yyyy-MM-dd").format(Date.valueOf(LocalDate.now().plusDays(4))));

        Response<Integer> updateResponse = Client.updateClient(createdClientID, updatedClientRequest);
        assertBadRequest(updateResponse);

        ErrorResponse expectedResponse = errorClientInvalidClientDateByID(createdClientID);
        assertErrorResponse(getErrorResponse(updateResponse), expectedResponse);
    }


    @Test(description = "ID: GT0001")
    public void updateClientWithIDTest() throws IOException {
        ClientRequest updatedClientRequest = createdClientRequest;
        createdClientRequest.setId(2);

        Response<Integer> createResponse = Client.updateClient(createdClientID, updatedClientRequest);
        assertBadRequest(createResponse);

        ErrorResponse expectedResponse = errorClientInvalidIDBodyBydID(createdClientID);
        assertErrorResponse(getErrorResponse(createResponse), expectedResponse);
    }


    @Test(description = "ID: GT0001")
    public void updateClientWithInvalidPostalCode() throws IOException {
        ClientRequest updatedClientRequest = createdClientRequest;
        createdClientRequest.setPostalCode("3004147");

        Response<Integer> createResponse = Client.updateClient(createdClientID, updatedClientRequest);
        assertBadRequest(createResponse);

        ErrorResponse expectedResponse = errorClientInvalidPostalCodeByID(createdClientID);
        assertErrorResponse(getErrorResponse(createResponse), expectedResponse);
    }

    @Test(description = "ID: GT0001")
    public void updateClientWithInvalidPhoneNumber() throws IOException {
        ClientRequest updatedClientRequest = createdClientRequest;
        createdClientRequest.setPhoneNumber(9326459);

        Response<Integer> createResponse = Client.updateClient(createdClientID, updatedClientRequest);
        assertBadRequest(createResponse);

        ErrorResponse expectedResponse = errorClientInvalidPhoneNumberByID(createdClientID);
        assertErrorResponse(getErrorResponse(createResponse), expectedResponse);
    }
}
