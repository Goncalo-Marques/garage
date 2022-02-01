package api.garage.vehicle;

import api.mappings.garage.vehicle.VehicleResponse;
import api.retrofit.garage.Client;
import api.retrofit.garage.Vehicle;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;

import static api.garage.helper.ClientRequests.clientPositive;
import static api.garage.helper.VehicleRequests.vehiclePositive;
import static api.validators.ResponseBodyValidator.assertClient;
import static api.validators.ResponseCodeValidator.*;

public class AddVehicleToClientPositiveTests {
    private Integer createdVehicleID;
    private Integer createdClientID;

    @BeforeMethod
    public void createVehicleAndClient() throws IOException {
        Response<Integer> responseVehicle = Vehicle.createVehicle(vehiclePositive());
        assertCreated(responseVehicle);
        createdVehicleID = responseVehicle.body();

        Response<Integer> responseClient = Client.createClient(clientPositive());
        assertCreated(responseClient);
        createdClientID = responseClient.body();
    }

    @AfterMethod
    public void deleteVehicleAndClient() throws IOException {
        if (createdVehicleID != null) {
            Response<Void> response = Vehicle.deleteVehicleByID(createdVehicleID);
            assertNoContent(response);
        }

        if (createdClientID != null) {
            Response<Void> response = Client.deleteClientByID(createdClientID, null);
            assertNoContent(response);
        }

        createdVehicleID = null;
        createdClientID = null;
    }

    @Test(description = "ID: GT0046")
    public void addVehicleToClientTest() throws IOException {
        Response<Void> response = Vehicle.addVehicleToClient(createdVehicleID, createdClientID);
        assertNoContent(response);

        Response<VehicleResponse> getResponse = Vehicle.getVehicleByID(createdVehicleID);
        assertOk(getResponse);

        assertClient(getResponse.body().getClient(), createdClientID);
    }
}
