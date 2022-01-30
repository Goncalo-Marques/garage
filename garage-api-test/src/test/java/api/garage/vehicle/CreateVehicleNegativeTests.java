package api.garage.vehicle;

import api.mappings.garage.ErrorResponse;
import api.mappings.garage.vehicle.VehicleRequest;
import api.retrofit.garage.Vehicle;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static api.garage.helper.ErrorVehicleResponses.*;
import static api.garage.helper.VehicleRequests.vehiclePositive;
import static api.retrofit.garage.Error.getErrorResponse;
import static api.validators.ErrorResponseValidator.assertErrorResponse;
import static api.validators.ResponseCodeValidator.*;

public class CreateVehicleNegativeTests {
    private List<Integer> createdVehicleIDs = new ArrayList<>();

    @AfterMethod
    public void deleteVehicles() throws IOException {
        if (createdVehicleIDs == null) return;

        for (int i = 0; i < createdVehicleIDs.size(); i++) {
            if (createdVehicleIDs.get(i) == null) continue;
            Response<Void> response = Vehicle.deleteVehicleByID(createdVehicleIDs.get(i));
            assertNoContent(response);
        }

        createdVehicleIDs.clear();
    }

    @Test(description = "ID: GT0001")
    public void createVehicleNullFieldsTest() throws IOException {
        VehicleRequest requestBody = VehicleRequest.builder().build();

        Response<Integer> createResponse = Vehicle.createVehicle(requestBody);
        createdVehicleIDs.add(createResponse.body()); // in case the vehicle is created by mistake
        assertBadRequest(createResponse);

        ErrorResponse expectedResponse = errorVehicleInvalidBody();
        assertErrorResponse(getErrorResponse(createResponse), expectedResponse);
    }

    @Test(description = "ID: GT0001")
    public void createVehicleDuplicatedPlateTest() throws IOException {
        VehicleRequest createdVehicleRequest = vehiclePositive();

        Response<Integer> createResponse = Vehicle.createVehicle(createdVehicleRequest);
        createdVehicleIDs.add(createResponse.body());
        assertCreated(createResponse);

        createResponse = Vehicle.createVehicle(createdVehicleRequest);
        createdVehicleIDs.add(createResponse.body());  // in case the vehicle is created by mistake
        assertBadRequest(createResponse);

        ErrorResponse expectedResponse = errorVehicleDuplicatedPlate();
        assertErrorResponse(getErrorResponse(createResponse), expectedResponse);
    }

    @DataProvider(name = "dataProviderInvalidPlates")
    public Object[][] dataProviderInvalidPlates() {
        return new Object[][]{
                {"ZZ99ZZ"}, {"99ZZ99"}, {"ZZ-ZZ-99"}, {"99-ZZ-ZZ"},
                {"ZZ-ZZ-ZZ"}, {"99-99-99"}, {""}, {null}};
    }

    @Test(description = "ID: GT0001", dataProvider = "dataProviderInvalidPlates")
    public void createVehicleWithInvalidPlateTest(String vehiclePlateToTest) throws IOException {
        VehicleRequest createdVehicleRequest = vehiclePositive();
        createdVehicleRequest.setPlate(vehiclePlateToTest);

        Response<Integer> createResponse = Vehicle.createVehicle(createdVehicleRequest);
        createdVehicleIDs.add(createResponse.body()); // in case the vehicle is created by mistake
        assertBadRequest(createResponse);

        ErrorResponse expectedResponse = errorVehicleInvalidPlate();
        assertErrorResponse(getErrorResponse(createResponse), expectedResponse);
    }

    @Test(description = "ID: GT0001")
    public void createVehicleWithYearGreaterThanTodayTest() throws IOException {
        VehicleRequest createdVehicleRequest = vehiclePositive();
        createdVehicleRequest.setYear(LocalDate.now().plusYears(1).getYear());

        Response<Integer> createResponse = Vehicle.createVehicle(createdVehicleRequest);
        createdVehicleIDs.add(createResponse.body()); // in case the vehicle is created by mistake
        assertBadRequest(createResponse);

        ErrorResponse expectedResponse = errorVehicleInvalidYear();
        assertErrorResponse(getErrorResponse(createResponse), expectedResponse);
    }

    @Test(description = "ID: GT0001")
    public void createVehicleWithEmptyActiveStatusTest() throws IOException {
        VehicleRequest createdVehicleRequest = vehiclePositive();
        createdVehicleRequest.setActive(null);

        Response<Integer> createResponse = Vehicle.createVehicle(createdVehicleRequest);
        createdVehicleIDs.add(createResponse.body()); // in case the vehicle is created by mistake
        assertBadRequest(createResponse);

        ErrorResponse expectedResponse = errorVehicleInvalidActiveStatus();
        assertErrorResponse(getErrorResponse(createResponse), expectedResponse);
    }
}
