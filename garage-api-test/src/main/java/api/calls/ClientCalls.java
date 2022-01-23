package api.calls;

import api.mappings.garage.client.ClientRequest;
import api.mappings.garage.client.ClientResponse;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ClientCalls {
    // parameters
    String ID = "id";
    String REMOVE_VEHICLES = "removeVehicles";

    // paths
    String CLIENT_BY_ID = "client/{id}";
    String CLIENT = "client";

    @GET(CLIENT_BY_ID)
    Call<ClientResponse> getClient(@Path(ID) Integer clientID);

    @GET(CLIENT)
    Call<List<ClientResponse>> getAllClients();

    @PUT(CLIENT_BY_ID)
    Call<Integer> updateClient(@Path(ID) Integer clientID, @Body ClientRequest requestBody);

    @POST(CLIENT)
    Call<Integer> createClient(@Body ClientRequest requestBody);

    @DELETE(CLIENT_BY_ID)
    Call<Void> deleteClient(@Path(ID) Integer clientID, @Query(REMOVE_VEHICLES) Boolean removeVehicles);
}
