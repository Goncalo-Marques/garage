package api.garage.client;

import api.mappings.garage.ErrorResponse;
import api.mappings.garage.client.ClientRequest;
import api.retrofit.garage.Client;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static api.garage.helper.ClientRequests.clientPositive;
import static api.garage.helper.ErrorClientResponses.*;
import static api.retrofit.garage.Error.getErrorResponse;
import static api.validators.ErrorResponseValidator.assertErrorResponse;
import static api.validators.ResponseCodeValidator.*;

public class CreateClientNegativeTests {
    private List<Integer> createdClientIDs = new ArrayList<>();

    // To delete if client doesn't be created with success
    @AfterMethod
    public void deleteClient() throws IOException {
        if (createdClientIDs == null) return;
        for (int i = 0; i < createdClientIDs.size(); i++){
            if (createdClientIDs.get(i) == null) continue;
            Response<Void> response = Client.deleteClientByID(createdClientIDs.get(i), null);
            assertNoContent(response);
        }

        createdClientIDs.clear();
    }

    @Test(description = "ID GT001")
    public void createClientNullFieldsTest() throws IOException {
        ClientRequest requestBody = ClientRequest.builder().build();

        Response<Integer> createResponse = Client.createClient(requestBody);
        createdClientIDs.add(createResponse.body());// in case the client is created by mistake
        assertBadRequest(createResponse);

        ErrorResponse expectedResponse = errorClientInvalidBody();
        assertErrorResponse(getErrorResponse(createResponse), expectedResponse);
    }

    @Test(description = "ID GT001")
    public void createClientDuplicatedNIF() throws IOException {
        ClientRequest createClientRequest = clientPositive();

        Response<Integer> createResponse = Client.createClient(createClientRequest);
        createdClientIDs.add(createResponse.body());// in case the client is created by mistake
        assertCreated(createResponse);

        createResponse = Client.createClient(createClientRequest);
        createdClientIDs.add(createResponse.body());
        assertBadRequest(createResponse);

        ErrorResponse expectedResponse = errorClientDuplicatedNIF();
        assertErrorResponse(getErrorResponse(createResponse), expectedResponse);
    }

    @Test(description = "ID: GT0001")
    public void createClientWithClientDateGreaterThanTodayTest() throws IOException {
        ClientRequest createClientRequest = clientPositive();
        createClientRequest.setClientDate(new SimpleDateFormat("yyyy-MM-dd").format(Date.valueOf(LocalDate.now().plusDays(4))));

        Response<Integer> createResponse = Client.createClient(createClientRequest);
        createdClientIDs.add(createResponse.body());
        assertBadRequest(createResponse);

        ErrorResponse expectedResponse = errorClientInvalidClientDate();
        assertErrorResponse(getErrorResponse(createResponse), expectedResponse);
    }

    @Test(description = "ID: GT0001")
    public void createClientWithBirthDateGreaterThanTodayTest() throws IOException {
        ClientRequest createClientRequest = clientPositive();
        createClientRequest.setBirthDate(new SimpleDateFormat("yyyy-MM-dd").format(Date.valueOf(LocalDate.now().plusDays(4))));

        Response<Integer> createResponse = Client.createClient(createClientRequest);
        createdClientIDs.add(createResponse.body());
        assertBadRequest(createResponse);

        ErrorResponse expectedResponse = errorClientInvalidBirthDate();
        assertErrorResponse(getErrorResponse(createResponse), expectedResponse);
    }

    @Test(description = "ID: GT0001")
    public void createClientWithIDTest() throws IOException {
        ClientRequest createClientRequest = clientPositive();
        createClientRequest.setId(2);

        Response<Integer> createResponse = Client.createClient(createClientRequest);
        createdClientIDs.add(createResponse.body());
        assertBadRequest(createResponse);

        ErrorResponse expectedResponse = errorClientInvalidIDBody();
        assertErrorResponse(getErrorResponse(createResponse), expectedResponse);
    }


    @Test(description = "ID: GT0001")
    public void createClientWithInvalidPostalCode() throws IOException {
        ClientRequest createClientRequest = clientPositive();
        createClientRequest.setPostalCode("3004147");

        Response<Integer> createResponse = Client.createClient(createClientRequest);
        createdClientIDs.add(createResponse.body());
        assertBadRequest(createResponse);

        ErrorResponse expectedResponse = errorClientInvalidPostalCode();
        assertErrorResponse(getErrorResponse(createResponse), expectedResponse);
    }

    @Test(description = "ID: GT0001")
    public void createClientWithInvalidPhoneNumber() throws IOException {
        ClientRequest createClientRequest = clientPositive();
        createClientRequest.setPhoneNumber(9326459);

        Response<Integer> createResponse = Client.createClient(createClientRequest);
        createdClientIDs.add(createResponse.body());
        assertBadRequest(createResponse);

        ErrorResponse expectedResponse = errorClientInvalidPhoneNumber();
        assertErrorResponse(getErrorResponse(createResponse), expectedResponse);
    }
}
