package api.retrofit.garage;

import api.calls.VehicleCalls;
import api.mappings.garage.vehicle.VehicleRequest;
import api.mappings.garage.vehicle.VehicleResponse;
import api.retrofit.RetrofitBuilder;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class Vehicle {
    private static final VehicleCalls vehicleCalls = new RetrofitBuilder().getRetrofit().create(VehicleCalls.class);

    // get vehicle by id
    public static Response<VehicleResponse> getVehicleByID(Integer vehicleID) throws IOException {
        return vehicleCalls.getVehicle(vehicleID).execute();
    }

    // get all vehicles
    public static Response<List<VehicleResponse>> getAllVehicles() throws IOException {
        return vehicleCalls.getAllVehicles().execute();
    }

    // create vehicle
    public static Response<Integer> createVehicle(VehicleRequest vehicle) throws IOException {
        return vehicleCalls.createVehicle(vehicle).execute();
    }

    // update vehicle
    public static Response<Integer> updateVehicle(Integer vehicleID, VehicleRequest vehicle) throws IOException {
        return vehicleCalls.updateVehicle(vehicleID, vehicle).execute();
    }

    // add vehicle to client
    public static Response<Void> addVehicleToClient(Integer vehicleID, Integer clientID) throws IOException {
        return vehicleCalls.addVehicleToClient(vehicleID, clientID).execute();
    }

    // delete vehicle
    public static Response<Void> deleteVehicleByID(Integer vehicleID) throws IOException {
        return vehicleCalls.deleteVehicle(vehicleID).execute();
    }

    // remove vehicle from client
    public static Response<Void> removeVehicleFromClient(Integer vehicleID) throws IOException {
        return vehicleCalls.removeVehicleFromClient(vehicleID).execute();
    }
}
