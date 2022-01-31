package api.garage.client;

import api.mappings.garage.ErrorResponse;
import api.mappings.garage.client.ClientRequest;
import api.mappings.garage.vehicle.VehicleRequest;
import api.retrofit.garage.Client;
import api.retrofit.garage.Vehicle;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static api.garage.helper.ClientRequests.clientPositive;
import static api.garage.helper.ErrorClientResponses.*;
import static api.garage.helper.ErrorVehicleResponses.errorVehicleInvalidYear;
import static api.garage.helper.VehicleRequests.vehiclePositive;
import static api.retrofit.garage.Error.getErrorResponse;
import static api.validators.ErrorResponseValidator.assertErrorResponse;
import static api.validators.ResponseCodeValidator.*;

public class CreateClientNegativeTests {
    private List<Integer> createdClientIDs = new ArrayList<>();

// para apagar caso nao seja criado o cliente com sucesso
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
        createClientRequest.setClientDate(String.valueOf(LocalDate.now()));

        Response<Integer> createResponse = Client.createClient(createClientRequest);
        createdClientIDs.add(createResponse.body()); // in case the vehicle is created by mistake
        assertBadRequest(createResponse);

        ErrorResponse expectedResponse = errorClientInvalidDate();
        assertErrorResponse(getErrorResponse(createResponse), expectedResponse);
    }

    @Test(description = "ID: GT0001")
    public void createClientWithBirthDateGreaterThanTodayTest() throws IOException {
        ClientRequest createClientRequest = clientPositive();
        createClientRequest.setBirthDate(String.valueOf(LocalDate.now()));

        Response<Integer> createResponse = Client.createClient(createClientRequest);
        createdClientIDs.add(createResponse.body()); // in case the vehicle is created by mistake
        assertBadRequest(createResponse);

        ErrorResponse expectedResponse = errorClientInvalidBirthDate();
        assertErrorResponse(getErrorResponse(createResponse), expectedResponse);
    }
}
