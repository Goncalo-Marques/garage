package api.garage.vehicle;

import api.mappings.garage.vehicle.CreateVehicleRequest;
import api.mappings.garage.vehicle.GetVehicleResponse;
import api.retrofit.garage.Vehicle;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

import static api.garage.helper.VehicleResquests.vehiclePositive;
import static api.validators.ListValidator.assertListHasSize;
import static api.validators.ListValidator.assertListNotEmpty;
import static api.validators.ResponseBodyValidator.assertBodyNotNull;
import static api.validators.ResponseBodyValidator.assertID;
import static api.validators.ResponseCodeValidator.*;
import static api.validators.VehicleValidator.assertVehicleResponseWithCreateRequest;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class GetVehiclePositiveTests {
    private Integer createdVehicleID;
    private CreateVehicleRequest createdVehicleRequest;

    @BeforeMethod
    public void createVehicle() throws IOException {
        createdVehicleRequest = vehiclePositive();

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
        createdVehicleRequest = null;
    }

    @Test(description = "ID: GT0001")
    public void getAllVehiclesTest() throws IOException {
        Response<List<GetVehicleResponse>> getResponse = Vehicle.getAllVehicles();
        assertOk(getResponse);

        assertBodyNotNull(getResponse);
        assertListNotEmpty(getResponse.body());
        assertListHasSize(getResponse.body(), greaterThanOrEqualTo(1));
    }

    @Test(description = "ID: GT0001")
    public void getVehicleByIDTest() throws IOException {
        Response<GetVehicleResponse> getResponse = Vehicle.getVehicleByID(createdVehicleID);
        assertOk(getResponse);

        assertBodyNotNull(getResponse);
        assertVehicleResponseWithCreateRequest(getResponse.body(), createdVehicleRequest);
        assertID(getResponse.body().getId(), createdVehicleID);
    }
}
