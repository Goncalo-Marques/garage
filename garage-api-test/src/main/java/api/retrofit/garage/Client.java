package api.retrofit.garage;

import api.calls.ClientCalls;
import api.mappings.garage.client.ClientRequest;
import api.mappings.garage.client.ClientResponse;
import api.retrofit.RetrofitBuilder;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class Client {
    private static final ClientCalls clientCalls = new RetrofitBuilder().getRetrofit().create(ClientCalls.class);

    // get client by id
    public static Response<ClientResponse> getClientByID(Integer clientID) throws IOException {
        return clientCalls.getClient(clientID).execute();
    }

    // get all clients
    public static Response<List<ClientResponse>> getAllClients() throws IOException {
        return clientCalls.getAllClients().execute();
    }

    // create client
    public static Response<ClientRequest> createClient(ClientRequest client) throws IOException {
        return clientCalls.createClient(client).execute();
    }

    // update client
    public static Response<Integer> updateClient(Integer clientID, ClientRequest client) throws IOException {
        return clientCalls.updateClient(clientID, client).execute();
    }

    // delete client
    public static Response<Void> deleteClientByID(Integer clientID, Boolean removeVehicles) throws IOException {
        return clientCalls.deleteClient(clientID, removeVehicles).execute();
    }
}
