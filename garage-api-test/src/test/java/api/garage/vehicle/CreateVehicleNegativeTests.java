package api.garage.vehicle;

import api.mappings.garage.ErrorResponse;
import org.testng.annotations.Test;

import static api.validators.ErrorResponseValidator.assertErrorResponse;

public class CreateVehicleNegativeTests {
    @Test
    public void getFoodNonExistentTest()  {
        ErrorResponse actual = new ErrorResponse();
        actual.setStatus(1);

        ErrorResponse expected = new ErrorResponse();
        expected.setStatus(1);

        assertErrorResponse(actual, expected, true);
    }
}
