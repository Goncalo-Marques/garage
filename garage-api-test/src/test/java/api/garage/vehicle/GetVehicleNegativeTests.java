package api.garage.vehicle;

import api.mappings.garage.ErrorResponse;
import api.mappings.garage.vehicle.CreateVehicleRequest;
import api.mappings.garage.vehicle.GetVehicleResponse;
import api.retrofit.garage.Vehicle;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;

import static api.garage.helper.ErrorVehicleResponses.errorVehicleInvalidID;
import static api.garage.helper.ErrorVehicleResponses.errorVehicleNotFound;
import static api.garage.helper.VehicleResquests.vehiclePositive;
import static api.retrofit.garage.Error.getErrorResponse;
import static api.validators.ErrorResponseValidator.assertErrorResponse;
import static api.validators.ResponseBodyValidator.assertIDNotNull;
import static api.validators.ResponseCodeValidator.*;


// TODO: testar buscar manualmente veiculos com id acima de 2147483647 (int32)
// deve retornar 400, mas so pode ser testado manualmente (documentar eventualmente no relatorio)
public class GetVehicleNegativeTests {
    private Integer createdVehicleID;

    @AfterMethod
    public void deleteVehicle() throws IOException {
        if (createdVehicleID == null) return;

        Response<Void> response = Vehicle.deleteVehicleByID(createdVehicleID);
        assertNoContent(response);

        createdVehicleID = null;
    }

    @Test(description = "ID: GT0001")
    public void getNonExistentVehicleTest() throws IOException {
        CreateVehicleRequest vehicle = vehiclePositive();

        Response<Integer> createResponse = Vehicle.createVehicle(vehicle);
        assertCreated(createResponse);
        createdVehicleID = createResponse.body();
        Integer vehicleIDToTest = createdVehicleID;
        assertIDNotNull(vehicleIDToTest);

        Response<Void> response = Vehicle.deleteVehicleByID(vehicleIDToTest);
        assertNoContent(response);
        createdVehicleID = null;

        Response<GetVehicleResponse> getResponse = Vehicle.getVehicleByID(vehicleIDToTest);
        assertNotFound(getResponse);

        ErrorResponse expectedResponse = errorVehicleNotFound(vehicleIDToTest);
        assertErrorResponse(getErrorResponse(getResponse), expectedResponse);
    }

    @Test(description = "ID: GT0001")
    public void getVehicleWithID0Test() throws IOException {
        Integer vehicleIDToTest = 0;
        Response<GetVehicleResponse> getResponse = Vehicle.getVehicleByID(vehicleIDToTest);
        assertBadRequest(getResponse);

        ErrorResponse expectedResponse = errorVehicleInvalidID(vehicleIDToTest);
        assertErrorResponse(getErrorResponse(getResponse), expectedResponse);
    }

    @Test(description = "ID: GT0001")
    public void getVehicleWithNegativeIDTest() throws IOException {
        Integer vehicleIDToTest = -100;
        Response<GetVehicleResponse> getResponse = Vehicle.getVehicleByID(vehicleIDToTest);
        assertBadRequest(getResponse);

        ErrorResponse expectedResponse = errorVehicleInvalidID(vehicleIDToTest);
        assertErrorResponse(getErrorResponse(getResponse), expectedResponse);
    }
}
