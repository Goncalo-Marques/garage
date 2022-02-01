package api.garage.client;

import api.mappings.garage.ErrorResponse;
import api.mappings.garage.client.ClientRequest;
import api.retrofit.garage.Client;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;

import static api.garage.helper.ClientRequests.clientPositive;
import static api.garage.helper.ErrorClientResponses.errorClientInvalidID;
import static api.garage.helper.ErrorClientResponses.errorClientNotFound;
import static api.retrofit.garage.Error.getErrorResponse;
import static api.validators.ErrorResponseValidator.assertErrorResponse;
import static api.validators.ResponseBodyValidator.assertIDNotNull;
import static api.validators.ResponseCodeValidator.*;

public class DeleteClientNegativeTests {
    private Integer createdClientID;

    @AfterMethod
    public void deleteClient() throws IOException {
        if (createdClientID == null) return;

        Response<Void> response = Client.deleteClientByID(createdClientID, null);
        assertNoContent(response);

        createdClientID = null;
    }

    @Test(description = "ID: GT0001")
    public void deleteNonExistentClientTest() throws IOException {
        ClientRequest client = clientPositive();

        Response<Integer> createResponse = Client.createClient(client);
        assertCreated(createResponse);
        createdClientID = createResponse.body();
        Integer clientIDToTest = createdClientID;
        assertIDNotNull(clientIDToTest);

        Response<Void> response = Client.deleteClientByID(clientIDToTest, null);
        assertNoContent(response);
        createdClientID = null;

        Response<Void> getResponse = Client.deleteClientByID(clientIDToTest, null);
        assertNotFound(getResponse);

        ErrorResponse expectedResponse = errorClientNotFound(clientIDToTest);
        assertErrorResponse(getErrorResponse(getResponse), expectedResponse);
    }

    @Test(description = "ID: GT0001")
    public void deleteClientWithID0Test() throws IOException {
        Integer clientIDToTest = 0;
        Response<Void> getResponse = Client.deleteClientByID(clientIDToTest, null);
        assertBadRequest(getResponse);

        ErrorResponse expectedResponse = errorClientInvalidID(clientIDToTest);
        assertErrorResponse(getErrorResponse(getResponse), expectedResponse);
    }

    @Test(description = "ID: GT0001")
    public void deleteClientWithNegativeIDTest() throws IOException {
        Integer clientIDToTest = -19;
        Response<Void> getResponse = Client.deleteClientByID(clientIDToTest, null);
        assertBadRequest(getResponse);

        ErrorResponse expectedResponse = errorClientInvalidID(clientIDToTest);
        assertErrorResponse(getErrorResponse(getResponse), expectedResponse);
    }
}
