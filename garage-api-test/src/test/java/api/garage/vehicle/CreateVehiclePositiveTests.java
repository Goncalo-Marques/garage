package api.garage.vehicle;

import api.mappings.garage.vehicle.CreateVehicleRequest;
import api.mappings.garage.vehicle.GetVehicleResponse;
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
import static api.validators.VehicleValidator.assertVehicleResponseWithCreateRequest;

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
        CreateVehicleRequest createdVehicleRequest = vehiclePositive();

        Response<Integer> createResponse = Vehicle.createVehicle(createdVehicleRequest);
        assertBodyNotNull(createResponse);
        createdVehicleID = createResponse.body();
        assertCreated(createResponse);

        Response<GetVehicleResponse> getResponse = Vehicle.getVehicleByID(createdVehicleID);
        assertOk(getResponse);

        assertVehicleResponseWithCreateRequest(getResponse.body(), createdVehicleRequest);
        assertID(getResponse.body().getId(), createdVehicleID);
    }

    @DataProvider(name = "dataProviderValidPlates")
    public Object[][] dataProviderValidPlates() {
        return new Object[][]{
                {"ZZ-99-ZZ"}, {"99-ZZ-99"}, {"99-99-ZZ"}, {"ZZ-99-99"}};
    }

    @Test(description = "ID: GT0001", dataProvider = "dataProviderValidPlates")
    public void createVehicleWithValidPlateTest(String vehiclePlateToTest) throws IOException {
        CreateVehicleRequest createdVehicleRequest = vehiclePositive();
        createdVehicleRequest.setPlate(vehiclePlateToTest);

        Response<Integer> createResponse = Vehicle.createVehicle(createdVehicleRequest);
        assertBodyNotNull(createResponse);
        createdVehicleID = createResponse.body();
        assertCreated(createResponse);

        Response<GetVehicleResponse> getResponse = Vehicle.getVehicleByID(createdVehicleID);
        assertOk(getResponse);

        assertVehicleResponseWithCreateRequest(getResponse.body(), createdVehicleRequest);
        assertID(getResponse.body().getId(), createdVehicleID);
    }

    @Test(description = "ID: GT0001")
    public void createVehicleWithYearEqualToTodayTest() throws IOException {
        CreateVehicleRequest createdVehicleRequest = vehiclePositive();
        createdVehicleRequest.setYear(LocalDate.now().getYear());

        Response<Integer> createResponse = Vehicle.createVehicle(createdVehicleRequest);
        assertBodyNotNull(createResponse);
        createdVehicleID = createResponse.body();
        assertCreated(createResponse);

        Response<GetVehicleResponse> getResponse = Vehicle.getVehicleByID(createdVehicleID);
        assertOk(getResponse);

        assertVehicleResponseWithCreateRequest(getResponse.body(), createdVehicleRequest);
        assertID(getResponse.body().getId(), createdVehicleID);
    }

    @Test(description = "ID: GT0001")
    public void createVehicleWithYearLessThanTodayTest() throws IOException {
        CreateVehicleRequest createdVehicleRequest = vehiclePositive();
        createdVehicleRequest.setYear(LocalDate.now().minusYears(1).getYear());

        Response<Integer> createResponse = Vehicle.createVehicle(createdVehicleRequest);
        assertBodyNotNull(createResponse);
        createdVehicleID = createResponse.body();
        assertCreated(createResponse);

        Response<GetVehicleResponse> getResponse = Vehicle.getVehicleByID(createdVehicleID);
        assertOk(getResponse);

        assertVehicleResponseWithCreateRequest(getResponse.body(), createdVehicleRequest);
        assertID(getResponse.body().getId(), createdVehicleID);
    }
}
