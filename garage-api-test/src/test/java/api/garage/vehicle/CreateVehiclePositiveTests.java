package api.garage.vehicle;

import api.mappings.garage.vehicle.CreateVehicleRequest;
import api.mappings.garage.vehicle.GetVehicleResponse;
import api.retrofit.garage.Vehicle;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;

import static api.garage.helper.VehicleResquests.vehiclePositive;
import static api.validators.ResponseBodyValidator.assertBodyNotNull;
import static api.validators.ResponseCodeValidator.*;

public class CreateVehiclePositiveTests {
    private Integer createdVehicleID;

    @AfterMethod
    public void deleteVehicle() throws IOException {
        if (createdVehicleID == null) return;

        Response<Void> response = Vehicle.deleteVehicleByID(createdVehicleID);
        assertNoContent(response);

        createdVehicleID = null;
    }

    @Test(description = "ID: GT0001")
    public void createVehicleTest() throws IOException {
        CreateVehicleRequest requestBody = vehiclePositive();

        Response<Integer> createResponse = Vehicle.createVehicle(requestBody);
        assertBodyNotNull(createResponse);
        createdVehicleID = createResponse.body();
        assertCreated(createResponse);

        Response<GetVehicleResponse> getResponse = Vehicle.getVehicleByID(createdVehicleID);
        assertOk(getResponse);
    }
}
