package api.garage.vehicle;

import api.mappings.garage.vehicle.VehicleRequest;
import api.mappings.garage.vehicle.VehicleResponse;
import api.retrofit.garage.Vehicle;
import org.testng.annotations.AfterMethod;
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

public class CreateVehiclePositiveTests {
    private Integer createdVehicleID;

    @AfterMethod
    public void deleteVehicle() throws IOException {
        if (createdVehicleID == null) return;

        Response<Void> response = Vehicle.deleteVehicleByID(createdVehicleID);
        assertNoContent(response);

        createdVehicleID = null;
    }

    @Test(description = "ID: GT0052")
    public void createVehicleTest() throws IOException {
        VehicleRequest createdVehicleRequest = vehiclePositive();

        Response<Integer> createResponse = Vehicle.createVehicle(createdVehicleRequest);
        assertBodyNotNull(createResponse);
        createdVehicleID = createResponse.body();
        assertCreated(createResponse);

        Response<VehicleResponse> getResponse = Vehicle.getVehicleByID(createdVehicleID);
        assertOk(getResponse);

        assertVehicleResponseWithRequest(getResponse.body(), createdVehicleRequest);
        assertID(getResponse.body().getId(), createdVehicleID);
    }

    @DataProvider(name = "dataProviderValidPlates")
    public Object[][] dataProviderValidPlates() {
        return new Object[][]{
                {"ZZ-99-ZZ"}, {"99-ZZ-99"}, {"99-99-ZZ"}, {"ZZ-99-99"}};
    }

    @Test(description = "ID: GT0053", dataProvider = "dataProviderValidPlates")
    public void createVehicleWithValidPlateTest(String vehiclePlateToTest) throws IOException {
        VehicleRequest createdVehicleRequest = vehiclePositive();
        createdVehicleRequest.setPlate(vehiclePlateToTest);

        Response<Integer> createResponse = Vehicle.createVehicle(createdVehicleRequest);
        assertBodyNotNull(createResponse);
        createdVehicleID = createResponse.body();
        assertCreated(createResponse);

        Response<VehicleResponse> getResponse = Vehicle.getVehicleByID(createdVehicleID);
        assertOk(getResponse);

        assertVehicleResponseWithRequest(getResponse.body(), createdVehicleRequest);
        assertID(getResponse.body().getId(), createdVehicleID);
    }

    @Test(description = "ID: GT0054")
    public void createVehicleWithYearEqualToTodayTest() throws IOException {
        VehicleRequest createdVehicleRequest = vehiclePositive();
        createdVehicleRequest.setYear(LocalDate.now().getYear());

        Response<Integer> createResponse = Vehicle.createVehicle(createdVehicleRequest);
        assertBodyNotNull(createResponse);
        createdVehicleID = createResponse.body();
        assertCreated(createResponse);

        Response<VehicleResponse> getResponse = Vehicle.getVehicleByID(createdVehicleID);
        assertOk(getResponse);

        assertVehicleResponseWithRequest(getResponse.body(), createdVehicleRequest);
        assertID(getResponse.body().getId(), createdVehicleID);
    }

    @Test(description = "ID: GT0055")
    public void createVehicleWithYearLessThanTodayTest() throws IOException {
        VehicleRequest createdVehicleRequest = vehiclePositive();
        createdVehicleRequest.setYear(LocalDate.now().minusYears(1).getYear());

        Response<Integer> createResponse = Vehicle.createVehicle(createdVehicleRequest);
        assertBodyNotNull(createResponse);
        createdVehicleID = createResponse.body();
        assertCreated(createResponse);

        Response<VehicleResponse> getResponse = Vehicle.getVehicleByID(createdVehicleID);
        assertOk(getResponse);

        assertVehicleResponseWithRequest(getResponse.body(), createdVehicleRequest);
        assertID(getResponse.body().getId(), createdVehicleID);
    }
}
