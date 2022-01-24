package api.garage.vehicle;

import api.mappings.garage.vehicle.CreateVehicleRequest;
import api.mappings.garage.vehicle.GetVehicleResponse;
import api.retrofit.garage.Vehicle;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;

import static api.validators.ResponseBodyValidator.assertBodyNotNull;
import static api.validators.ResponseBodyValidator.assertID;
import static api.validators.ResponseCodeValidator.*;
import static api.validators.VehicleValidator.assertVehicleResponseWithCreateRequest;

public class CreateVehiclePositiveTests {
    private Integer createdClientID;

    @AfterMethod
    public void deleteVehicle() throws IOException {
        if (createdClientID == null) return;
        Response<Void> response = Vehicle.deleteVehicleByID(createdClientID);
        assertNoContent(response);
        createdClientID = null;
    }

    @Test(description = "ID: GT0001")
    public void createVehicleTest() throws IOException {
        CreateVehicleRequest requestBody = CreateVehicleRequest.builder()
                .brand("Tesla")
                .model("Model S")
                .year(2012)
                .type("Electric")
                .plate("GG-04-20")
                .active(false)
                .build();

        Response<Integer> createdResponse = Vehicle.createVehicle(requestBody);
        assertBodyNotNull(createdResponse);
        createdClientID = createdResponse.body();
        assertCreated(createdResponse);

        Response<GetVehicleResponse> getResponse = Vehicle.getVehicleByID(createdClientID);
        assertOk(getResponse);

        assertVehicleResponseWithCreateRequest(getResponse.body(), requestBody);
        assertID(getResponse.body().getId(), createdClientID);
    }
}
