package api.garage.vehicle;

import api.mappings.garage.vehicle.VehicleRequest;
import api.mappings.garage.vehicle.VehicleResponse;
import api.retrofit.garage.Vehicle;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;

import static api.garage.helper.VehicleRequests.vehiclePositive;
import static api.validators.ResponseCodeValidator.*;

public class DeleteVehiclePositiveTests {
    private Integer createdVehicleID;

    @BeforeMethod
    public void createVehicle() throws IOException {
        VehicleRequest createdVehicleRequest = vehiclePositive();

        Response<Integer> response = Vehicle.createVehicle(createdVehicleRequest);
        assertCreated(response);

        createdVehicleID = response.body();
    }

    @AfterMethod
    public void deleteVehicle() throws IOException {
        if (createdVehicleID == null) return;

        Response<Void> response = Vehicle.deleteVehicleByID(createdVehicleID);
        assertNoContent(response);

        createdVehicleID = null;
    }

    @Test(description = "ID: GT0059")
    public void deleteVehicleTest() throws IOException {
        Integer idToTest = createdVehicleID;

        Response<Void> deleteResponse = Vehicle.deleteVehicleByID(idToTest);
        assertNoContent(deleteResponse);
        createdVehicleID = null;

        Response<VehicleResponse> getResponse = Vehicle.getVehicleByID(idToTest);
        assertNotFound(getResponse);
    }
}
