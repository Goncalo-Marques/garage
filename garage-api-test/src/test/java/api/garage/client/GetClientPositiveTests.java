package api.garage.client;
import api.mappings.garage.client.ClientRequest;
import api.mappings.garage.client.ClientResponse;
import api.retrofit.garage.Client;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static api.garage.helper.ClientRequests.clientPositive;
import static api.validators.ClientValidator.assertClientResponse;
import static api.validators.ListValidator.assertListHasSize;
import static api.validators.ListValidator.assertListNotEmpty;
import static api.validators.ResponseBodyValidator.*;
import static api.validators.ResponseCodeValidator.*;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class GetClientPositiveTests {
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
    public void getAllClientsTest() throws IOException {
        Response<List<ClientResponse>> getResponse = Client.getAllClients();
        assertOk(getResponse);

        assertBodyNotNull(getResponse);
        assertListNotEmpty(getResponse.body());
        assertListHasSize(getResponse.body(), greaterThanOrEqualTo(1));
    }

    @Test(description = "ID: GT0001")
    public void getClientByIDTest() throws IOException, ParseException {
        Response<ClientResponse> getResponse = Client.getClientByID(createdClientID);
        assertOk(getResponse);

        assertBodyNotNull(getResponse);
        assertClientResponse(getResponse.body(), createdClientRequest);
        assertID(getResponse.body().getId(), createdClientID);
    }
}
