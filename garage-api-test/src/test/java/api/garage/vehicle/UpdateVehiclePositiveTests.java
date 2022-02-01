package api.garage.vehicle;

import api.mappings.garage.vehicle.VehicleRequest;
import api.mappings.garage.vehicle.VehicleResponse;
import api.retrofit.garage.Vehicle;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;
import java.time.LocalDate;

import static api.garage.helper.VehicleRequests.vehiclePositive;
import static api.validators.ResponseBodyValidator.assertBodyNotNull;
import static api.validators.ResponseBodyValidator.assertID;
import static api.validators.ResponseCodeValidator.*;
import static api.validators.VehicleValidator.assertVehicleResponseWithRequest;

public class UpdateVehiclePositiveTests {
    private Integer createdVehicleID;
    private VehicleRequest createdVehicleRequest;

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

    @Test(description = "ID: GT0078")
    public void updateVehicleTest() throws IOException {
        VehicleRequest updatedVehicleRequest = createdVehicleRequest;
        updatedVehicleRequest.setBrand("Honda");
        updatedVehicleRequest.setModel("Type R");
        updatedVehicleRequest.setYear(2020);
        updatedVehicleRequest.setType("Sport");
        updatedVehicleRequest.setPlate("XD-04-20");
        updatedVehicleRequest.setActive(false);

        Response<Integer> updateResponse = Vehicle.updateVehicle(createdVehicleID, updatedVehicleRequest);
        assertOk(updateResponse);
        assertBodyNotNull(updateResponse);
        assertID(updateResponse.body(), createdVehicleID);

        Response<VehicleResponse> getResponse = Vehicle.getVehicleByID(updateResponse.body());
        assertOk(getResponse);

        assertVehicleResponseWithRequest(getResponse.body(), updatedVehicleRequest);
        assertID(getResponse.body().getId(), updateResponse.body());
    }

    @DataProvider(name = "dataProviderValidPlates")
    public Object[][] dataProviderValidPlates() {
        return new Object[][]{
                {"ZZ-99-ZZ"}, {"99-ZZ-99"}, {"99-99-ZZ"}, {"ZZ-99-99"}};
    }

    @Test(description = "ID: GT0079", dataProvider = "dataProviderValidPlates")
    public void updateVehicleWithValidPlateTest(String vehiclePlateToTest) throws IOException {
        VehicleRequest updatedVehicleRequest = createdVehicleRequest;
        updatedVehicleRequest.setPlate(vehiclePlateToTest);

        Response<Integer> updateResponse = Vehicle.updateVehicle(createdVehicleID, updatedVehicleRequest);
        assertOk(updateResponse);
        assertBodyNotNull(updateResponse);
        assertID(updateResponse.body(), createdVehicleID);

        Response<VehicleResponse> getResponse = Vehicle.getVehicleByID(updateResponse.body());
        assertOk(getResponse);

        assertVehicleResponseWithRequest(getResponse.body(), updatedVehicleRequest);
        assertID(getResponse.body().getId(), updateResponse.body());
    }

    @Test(description = "ID: GT0080")
    public void updateVehicleWithYearEqualToTodayTest() throws IOException {
        VehicleRequest updatedVehicleRequest = createdVehicleRequest;
        updatedVehicleRequest.setYear(LocalDate.now().getYear());

        Response<Integer> updateResponse = Vehicle.updateVehicle(createdVehicleID, updatedVehicleRequest);
        assertOk(updateResponse);
        assertBodyNotNull(updateResponse);
        assertID(updateResponse.body(), createdVehicleID);

        Response<VehicleResponse> getResponse = Vehicle.getVehicleByID(updateResponse.body());
        assertOk(getResponse);

        assertVehicleResponseWithRequest(getResponse.body(), updatedVehicleRequest);
        assertID(getResponse.body().getId(), updateResponse.body());
    }

    @Test(description = "ID: GT0081")
    public void updateVehicleWithYearLessThanTodayTest() throws IOException {
        VehicleRequest updatedVehicleRequest = createdVehicleRequest;
        updatedVehicleRequest.setYear(LocalDate.now().minusYears(1).getYear());

        Response<Integer> updateResponse = Vehicle.updateVehicle(createdVehicleID, updatedVehicleRequest);
        assertOk(updateResponse);
        assertBodyNotNull(updateResponse);
        assertID(updateResponse.body(), createdVehicleID);

        Response<VehicleResponse> getResponse = Vehicle.getVehicleByID(updateResponse.body());
        assertOk(getResponse);

        assertVehicleResponseWithRequest(getResponse.body(), updatedVehicleRequest);
        assertID(getResponse.body().getId(), updateResponse.body());
    }
}
