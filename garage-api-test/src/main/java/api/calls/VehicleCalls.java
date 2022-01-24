package api.calls;

import api.mappings.garage.vehicle.CreateVehicleRequest;
import api.mappings.garage.vehicle.GetVehicleResponse;
import api.mappings.garage.vehicle.UpdateVehicleRequest;
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
    Call<GetVehicleResponse> getVehicle(@Path(ID) Integer vehicleID);

    @GET(VEHICLE)
    Call<List<GetVehicleResponse>> getAllVehicles();

    @PUT(ADD_VEHICLE_CLIENT)
    Call<Void> addVehicleToClient(@Path(VEHICLE_ID) Integer vehicleID, @Path(CLIENT_ID) Integer clientID);

    @PUT(VEHICLE_BY_ID)
    Call<Integer> updateVehicle(@Path(ID) Integer vehicleID, @Body UpdateVehicleRequest requestBody);

    @POST(VEHICLE)
    Call<Integer> createVehicle(@Body CreateVehicleRequest requestBody);

    @DELETE(VEHICLE_BY_ID)
    Call<Void> deleteVehicle(@Path(ID) Integer vehicleID);

    @DELETE(REMOVE_VEHICLE_CLIENT)
    Call<Void> removeVehicleFromClient(@Path(ID) Integer vehicleID);
}
