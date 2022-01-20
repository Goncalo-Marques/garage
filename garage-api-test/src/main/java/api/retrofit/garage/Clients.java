package api.retrofit.garage;

import api.calls.ClientCalls;
import api.mappings.garage.client.ClientRequest;
import api.retrofit.RetrofitBuilder;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class Clients {
    private static final ClientCalls clientCalls = new RetrofitBuilder().getRetrofit().create(ClientCalls.class);

    // OBTER CLIENT POR ID
    public static Response<ClientRequest> getClientByID(Integer clientID) throws IOException {
        return clientCalls.getClient(clientID).execute();
    }

    // OBTER TODOS OS CLIENTES
    public static Response<List<ClientRequest>> getAllClients() throws IOException {
        return clientCalls.getAllClients().execute();
    }

    // CRIAR UM CLIENTE
    public static Response<ClientRequest> createClient(ClientRequest client) throws IOException {
        return clientCalls.createClient(client).execute();
    }

    // ATUALIZAR UM CLIENTE
    public static Response<ClientRequest> updateClient(ClientRequest client) throws IOException {
        return clientCalls.updateClient(client).execute();
    }

    // APAGAR UM CLIENTE
    public static Response<Void> deleteClientByID(Integer clientID) throws IOException {
        return clientCalls.deleteClient(clientID).execute();
    }
}
