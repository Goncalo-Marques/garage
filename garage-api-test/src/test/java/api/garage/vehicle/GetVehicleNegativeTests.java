package api.garage.vehicle;

import api.mappings.garage.ErrorResponse;
import api.mappings.garage.vehicle.GetVehicleResponse;
import api.retrofit.garage.Vehicle;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;

import static api.retrofit.garage.Error.getErrorResponse;
import static api.validators.ErrorResponseValidator.assertErrorResponse;
import static api.validators.ResponseCodeValidator.assertNotFound;

public class GetVehicleNegativeTests {
    @Test(description = "ID: GT0001")
    public void getNonExistentVehicleTest() throws IOException {
        Integer vehicleIDToTest = -100;
        Response<GetVehicleResponse> getResponse = Vehicle.getVehicleByID(vehicleIDToTest);
        assertNotFound(getResponse);

        ErrorResponse expectedResponse = ErrorResponse.builder()
                .status(404)
                .error("Not Found")
                .message("Vehicle not found")
                .path("/vehicle/" + vehicleIDToTest)
                .build();
        assertErrorResponse(getErrorResponse(getResponse), expectedResponse, true);
    }

    @DataProvider(name = "dataProviderWithinInt32Capacity")
    public Object[][] dataProviderWithinInt32Capacity() {
        return new Object[][]{
                {-2147483648},
                {2147483647}};
    }

    // TODO: perguntar ao professor se podemos confiar que um numero gigante não seja encontrado ou
    //  devemos colocar essa possibilidade em mente. nesse caso, nao faz sentido esperar 404 do num positivo
    @Test(description = "ID: GT0001", dataProvider = "dataProviderWithinInt32Capacity")
    public void getVehicleByIDWithinInt32CapacityTest(Integer vehicleIDToTest) throws IOException {
        Response<GetVehicleResponse> getResponse = Vehicle.getVehicleByID(vehicleIDToTest);
        assertNotFound(getResponse);

        ErrorResponse expectedResponse = ErrorResponse.builder()
                .status(404)
                .error("Not Found")
                .message("Vehicle not found")
                .path("/vehicle/" + vehicleIDToTest)
                .build();
        assertErrorResponse(getErrorResponse(getResponse), expectedResponse, true);
    }

    // TODO: testar buscar manualmente veiculos com id fora do int32 -2147483648 to 2147483647
    // deve retornar 400, mas so pode ser testado manualmente (documentar eventualmente no relatorio)
}
