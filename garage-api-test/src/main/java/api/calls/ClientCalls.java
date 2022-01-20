package api.calls;

import api.mappings.garage.client.ClientRequest;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ClientCalls {
    String ID="id";
    String CLIENTID="client/{client}";
    String CLIENT ="client";

    @GET(CLIENTID)
    Call<ClientRequest> getClient(@Path(ID) Integer clientID);

    @GET(CLIENT)
    Call<List<ClientRequest>> getAllClients();

    @PUT(CLIENT)
    Call<ClientRequest> updateClient(@Body ClientRequest client);

    @POST(CLIENT)
    Call<ClientRequest> createClient(@Body ClientRequest client);

    @DELETE(CLIENT)
    Call<Void> deleteClient(@Path(ID) Integer clientID);
}
