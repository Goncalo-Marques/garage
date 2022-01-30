package api.garage.vehicle;

import api.mappings.garage.ErrorResponse;
import api.mappings.garage.vehicle.VehicleRequest;
import api.retrofit.garage.Vehicle;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;
import java.time.LocalDate;

import static api.garage.helper.ErrorVehicleResponses.*;
import static api.garage.helper.VehicleRequests.vehiclePositive;
import static api.retrofit.garage.Error.getErrorResponse;
import static api.validators.ErrorResponseValidator.assertErrorResponse;
import static api.validators.ResponseCodeValidator.*;

public class UpdateVehicleNegativeTests {
    private Integer unexpectedCreatedVehicleID;
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
        if (unexpectedCreatedVehicleID != null) {
            Response<Void> response = Vehicle.deleteVehicleByID(unexpectedCreatedVehicleID);
            assertNoContent(response);
        }

        if (createdVehicleID != null) {
            Response<Void> response = Vehicle.deleteVehicleByID(createdVehicleID);
            assertNoContent(response);
        }

        unexpectedCreatedVehicleID = null;
        createdVehicleID = null;
        createdVehicleRequest = null;
    }

    @Test(description = "ID: GT0001")
    public void updateNonExistentVehicleTest() throws IOException {
        Integer vehicleIDToTest = createdVehicleID;

        Response<Void> response = Vehicle.deleteVehicleByID(vehicleIDToTest);
        assertNoContent(response);
        createdVehicleID = null;

        VehicleRequest updatedVehicleRequest = createdVehicleRequest;

        Response<Integer> updateResponse = Vehicle.updateVehicle(vehicleIDToTest, updatedVehicleRequest);
        assertNotFound(updateResponse);

        ErrorResponse expectedResponse = errorVehicleNotFound(vehicleIDToTest);
        assertErrorResponse(getErrorResponse(updateResponse), expectedResponse);
    }

    @Test(description = "ID: GT0001")
    public void updateVehicleWithID0Test() throws IOException {
        VehicleRequest updatedVehicleRequest = createdVehicleRequest;
        Integer vehicleIDToTest = 0;

        Response<Integer> updateResponse = Vehicle.updateVehicle(vehicleIDToTest, updatedVehicleRequest);
        assertBadRequest(updateResponse);

        ErrorResponse expectedResponse = errorVehicleInvalidID(vehicleIDToTest);
        assertErrorResponse(getErrorResponse(updateResponse), expectedResponse);
    }

    @Test(description = "ID: GT0001")
    public void updateVehicleWithNegativeIDTest() throws IOException {
        VehicleRequest updatedVehicleRequest = createdVehicleRequest;
        Integer vehicleIDToTest = -100;

        Response<Integer> updateResponse = Vehicle.updateVehicle(vehicleIDToTest, updatedVehicleRequest);
        assertBadRequest(updateResponse);

        ErrorResponse expectedResponse = errorVehicleInvalidID(vehicleIDToTest);
        assertErrorResponse(getErrorResponse(updateResponse), expectedResponse);
    }


    @Test(description = "ID: GT0001")
    public void updateVehicleNullFieldsTest() throws IOException {
        VehicleRequest updatedVehicleRequest = VehicleRequest.builder().build();

        Response<Integer> updateResponse = Vehicle.updateVehicle(createdVehicleID, updatedVehicleRequest);
        assertBadRequest(updateResponse);

        ErrorResponse expectedResponse = errorVehicleInvalidBodyByID(createdVehicleID);
        assertErrorResponse(getErrorResponse(updateResponse), expectedResponse);
    }

    @Test(description = "ID: GT0001")
    public void updateVehicleDuplicatedPlateTest() throws IOException {
        String vehiclePlateToTest = "ZZ-99-99";

        VehicleRequest createdVehicleRequest = vehiclePositive();
        createdVehicleRequest.setPlate(vehiclePlateToTest);

        Response<Integer> createResponse = Vehicle.createVehicle(createdVehicleRequest);
        unexpectedCreatedVehicleID = createResponse.body();
        assertCreated(createResponse);

        VehicleRequest updatedVehicleRequest = createdVehicleRequest;
        updatedVehicleRequest.setPlate(vehiclePlateToTest);

        Response<Integer> updateResponse = Vehicle.updateVehicle(createdVehicleID, updatedVehicleRequest);
        assertBadRequest(updateResponse);

        ErrorResponse expectedResponse = errorVehicleDuplicatedPlateByID(createdVehicleID);
        assertErrorResponse(getErrorResponse(updateResponse), expectedResponse);
    }

    @DataProvider(name = "dataProviderInvalidPlates")
    public Object[][] dataProviderInvalidPlates() {
        return new Object[][]{
                {"ZZ99ZZ"}, {"99ZZ99"}, {"ZZ-ZZ-99"}, {"99-ZZ-ZZ"},
                {"ZZ-ZZ-ZZ"}, {"99-99-99"}, {""}, {null}};
    }

    @Test(description = "ID: GT0001", dataProvider = "dataProviderInvalidPlates")
    public void updateVehicleWithInvalidPlateTest(String vehiclePlateToTest) throws IOException {
        VehicleRequest updatedVehicleRequest = createdVehicleRequest;
        updatedVehicleRequest.setPlate(vehiclePlateToTest);

        Response<Integer> updateResponse = Vehicle.updateVehicle(createdVehicleID, updatedVehicleRequest);
        assertBadRequest(updateResponse);

        ErrorResponse expectedResponse = errorVehicleInvalidPlateByID(createdVehicleID);
        assertErrorResponse(getErrorResponse(updateResponse), expectedResponse);
    }

    @Test(description = "ID: GT0001")
    public void updateVehicleWithYearGreaterThanTodayTest() throws IOException {
        VehicleRequest updatedVehicleRequest = createdVehicleRequest;
        updatedVehicleRequest.setYear(LocalDate.now().plusYears(1).getYear());

        Response<Integer> updateResponse = Vehicle.updateVehicle(createdVehicleID, updatedVehicleRequest);
        assertBadRequest(updateResponse);

        ErrorResponse expectedResponse = errorVehicleInvalidYearByID(createdVehicleID);
        assertErrorResponse(getErrorResponse(updateResponse), expectedResponse);
    }

    @Test(description = "ID: GT0001")
    public void updateVehicleWithEmptyActiveStatusTest() throws IOException {
        VehicleRequest updatedVehicleRequest = createdVehicleRequest;
        updatedVehicleRequest.setActive(null);

        Response<Integer> updateResponse = Vehicle.updateVehicle(createdVehicleID, updatedVehicleRequest);
        assertBadRequest(updateResponse);

        ErrorResponse expectedResponse = errorVehicleInvalidActiveStatusByID(createdVehicleID);
        assertErrorResponse(getErrorResponse(updateResponse), expectedResponse);
    }
}
