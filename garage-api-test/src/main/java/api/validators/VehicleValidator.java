package api.validators;

import api.mappings.garage.vehicle.CreateVehicleRequest;
import api.mappings.garage.vehicle.GetVehicleResponse;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class VehicleValidator {
    public static void assertVehicleResponseWithCreateRequest(GetVehicleResponse actual, CreateVehicleRequest expected) {
        if (expected == null) {
            assertThat("Response should be null", actual, nullValue());
            return;
        }

        assertThat("Response should not be null", actual, notNullValue());
        assertThat("Brand is not the expected", actual.getBrand(), is(expected.getBrand()));
        assertThat("Model is not the expected", actual.getModel(), is(expected.getModel()));
        assertThat("Year is not the expected", actual.getYear(), is(expected.getYear()));
        assertThat("Year is not valid", actual.getYear(), is(lessThanOrEqualTo(LocalDate.now().getYear())));
        assertThat("Type is not the expected", actual.getType(), is(expected.getType()));
        Matcher platePattern = Pattern.compile("^([A-Z][A-Z]-\\d\\d-[A-Z][A-Z])|(\\d\\d-[A-Z][A-Z]-\\d\\d)|(\\d\\d-\\d\\d-[A-Z][A-Z])|([A-Z][A-Z]-\\d\\d-\\d\\d)$").matcher(actual.getPlate());
        assertThat("Plate is not valid", platePattern.matches());
        assertThat("Plate is not the expected", actual.getPlate(), is(expected.getPlate()));
        assertThat("Active is not the expected", actual.getActive(), is(expected.getActive()));
    }
}
