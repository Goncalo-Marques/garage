package api.garage.client;

import api.mappings.garage.ErrorResponse;
import api.mappings.garage.client.ClientRequest;
import api.mappings.garage.client.ClientResponse;
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

public class GetClientNegativeTests {
    private Integer createdClientID;

    // para apagar caso nao seja criado o cliente com sucesso
    @AfterMethod
    public void deleteClient() throws IOException {
        if (createdClientID == null) return;

        Response<Void> response = Client.deleteClientByID(createdClientID, true);
        assertNoContent(response);

        createdClientID = null;
    }

    @Test(description = "ID: GT001")
    public void getNonExistentClientTest() throws IOException {
        ClientRequest client = clientPositive();


        Response<Integer> createResponse = Client.createClient(client);
        assertCreated(createResponse);
        createdClientID = createResponse.body();
        Integer idToTest = createdClientID;
        assertIDNotNull(idToTest);

        Response<Void> response = Client.deleteClientByID(idToTest, null);
        assertNoContent(response);
        createdClientID = null;

        Response<ClientResponse> getResponse = Client.getClientByID(idToTest);
        assertNotFound(getResponse);

        ErrorResponse expectedResponse = errorClientNotFound(idToTest);
        assertErrorResponse(getErrorResponse(getResponse), expectedResponse);
    }

    @Test(description = "ID: GT001")
    public void getClientWithID0Test() throws IOException {
        Integer clientIDToTest = 0;
        Response<ClientResponse> getResponse = Client.getClientByID(clientIDToTest);
        assertBadRequest(getResponse);

        ErrorResponse expectedResponse = errorClientInvalidID(clientIDToTest);
        assertErrorResponse(getErrorResponse(getResponse), expectedResponse);
    }

    @Test(description = "ID: GT001")
    public void getClientWithNegativeIDTest() throws IOException {
        Integer clientIdToTest = -50;
        Response<ClientResponse> getResponse = Client.getClientByID(clientIdToTest);

        ErrorResponse expectedResponse = errorClientInvalidID(clientIdToTest);
        assertErrorResponse(getErrorResponse(getResponse), expectedResponse);
    }
}


//TODO criar cliente com id na request - deve dar 400