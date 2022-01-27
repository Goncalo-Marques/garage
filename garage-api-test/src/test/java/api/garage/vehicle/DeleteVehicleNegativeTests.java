package api.garage.vehicle;

import api.mappings.garage.ErrorResponse;
import api.mappings.garage.vehicle.CreateVehicleRequest;
import api.mappings.garage.vehicle.GetVehicleResponse;
import api.retrofit.garage.Vehicle;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;

import static api.garage.helper.ErrorVehicleResponses.errorVehicleNotFound;
import static api.garage.helper.VehicleResquests.vehiclePositive;
import static api.retrofit.garage.Error.getErrorResponse;
import static api.validators.ErrorResponseValidator.assertErrorResponse;
import static api.validators.ResponseBodyValidator.assertIDNotNull;
import static api.validators.ResponseCodeValidator.*;

// TODO: testar apagar manualmente veiculos com id acima de 2147483647 (int32)
// deve retornar 400, mas so pode ser testado manualmente (documentar eventualmente no relatorio)
public class DeleteVehicleNegativeTests {
    private Integer createdVehicleID;

    @AfterMethod
    public void deleteVehicle() throws IOException {
        if (createdVehicleID == null) return;

        Response<Void> response = Vehicle.deleteVehicleByID(createdVehicleID);
        assertNoContent(response);

        createdVehicleID = null;
    }

    @Test(description = "ID: GT0001")
    public void deleteNonExistentVehicleTest() throws IOException {
        CreateVehicleRequest vehicle = vehiclePositive();

        Response<Integer> createResponse = Vehicle.createVehicle(vehicle);
        assertCreated(createResponse);
        createdVehicleID = createResponse.body();
        Integer idToTest = createdVehicleID;
        assertIDNotNull(idToTest);

        Response<Void> response = Vehicle.deleteVehicleByID(idToTest);
        assertNoContent(response);
        createdVehicleID = null;

        Response<Void> getResponse = Vehicle.deleteVehicleByID(idToTest);
        assertNotFound(getResponse);

        ErrorResponse expectedResponse = errorVehicleNotFound(idToTest);
        assertErrorResponse(getErrorResponse(getResponse), expectedResponse);
    }

    // delete vehicle duas vezes (esperar not found)
    // delete vehicle 0 e negativo e esperar bad request
}
