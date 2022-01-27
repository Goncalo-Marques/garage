package api.garage.client;

import api.mappings.garage.client.ClientRequest;
import api.mappings.garage.client.ClientResponse;
import api.retrofit.garage.Client;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;

import static api.garage.helper.ClientRequests.clientPositive;
import static api.validators.ResponseBodyValidator.assertBodyNotNull;
import static api.validators.ResponseCodeValidator.*;

public class CreateClientPositiveTests {
    private Integer createClientID;

    @AfterMethod
    public void deleteClient() throws IOException {
        if (createClientID == null) return;
        Response<Void> response = Client.deleteClientByID(createClientID, true);
        assertNoContent(response);
        createClientID = null;
    }

    @Test(description = "ID GT001")
    public void createVehicleTest() throws IOException {
        ClientRequest requestBody = clientPositive();

        Response<Integer> createResponse = Client.createClient(requestBody);
        assertBodyNotNull(createResponse);
        createClientID = createResponse.body();
        assertCreated(createResponse);

        Response<ClientResponse> getResponse = Client.getClientByID(createClientID);
        assertOk(getResponse);
    }
}
