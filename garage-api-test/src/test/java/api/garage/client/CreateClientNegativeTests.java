package api.garage.client;

import api.mappings.garage.ErrorResponse;
import api.mappings.garage.client.ClientRequest;
import api.retrofit.garage.Client;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;

import static api.garage.helper.ErrorClientResponses.errorClientBody;
import static api.retrofit.garage.Error.getErrorResponse;
import static api.validators.ErrorResponseValidator.assertErrorResponse;
import static api.validators.ResponseCodeValidator.*;

public class CreateClientNegativeTests {
    private Integer createClientID;

// para apagar caso nao seja criado o cliente com sucesso
    @AfterMethod
    public void deleteClient() throws IOException {
        if (createClientID == null) return;
        Response<Void> response = Client.deleteClientByID(createClientID, true);
        assertNoContent(response);
        createClientID = null;
    }

    @Test(description = "ID GT001")
    public void createClientNullFieldsTest() throws IOException {
        ClientRequest requestBody = ClientRequest.builder().build();

        Response<Integer> createResponse = Client.createClient(requestBody);

        // in case the client is created by mistake
        createClientID = createResponse.body();

        assertBadRequest(createResponse);
        errorClientBody();
        assertErrorResponse(getErrorResponse(createResponse), errorClientBody());
    }

}
