package api.calls;

import api.mappings.garage.vehicle.VehicleRequest;
import api.mappings.garage.vehicle.VehicleResponse;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface VehicleCalls {
    // parameters
    String ID = "id";
    String VEHICLE_ID = "vehicleId";
    String CLIENT_ID = "clientId";

    // paths
    String VEHICLE = "vehicle";
    String VEHICLE_BY_ID = "vehicle/{id}";
    String ADD_VEHICLE_CLIENT = "vehicle/{vehicleId}/client/{clientId}";
    String REMOVE_VEHICLE_CLIENT = "vehicle/{vehicleId}/client/";

    @GET(VEHICLE_BY_ID)
    Call<VehicleResponse> getVehicle(@Path(ID) Integer vehicleID);

    @GET(VEHICLE)
    Call<List<VehicleResponse>> getAllVehicles();

    @PUT(ADD_VEHICLE_CLIENT)
    Call<Void> addVehicleToClient(@Path(VEHICLE_ID) Integer vehicleID, @Path(CLIENT_ID) Integer clientID);

    @PUT(VEHICLE_BY_ID)
    Call<Integer> updateVehicle(@Path(ID) Integer vehicleID, @Body VehicleRequest requestBody);

    @POST(VEHICLE)
    Call<Integer> createVehicle(@Body VehicleRequest requestBody);

    @DELETE(VEHICLE_BY_ID)
    Call<Void> deleteVehicle(@Path(ID) Integer vehicleID);

    @DELETE(REMOVE_VEHICLE_CLIENT)
    Call<Void> removeVehicleFromClient(@Path(VEHICLE_ID) Integer vehicleID);
}
