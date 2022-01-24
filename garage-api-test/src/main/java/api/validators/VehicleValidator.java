package api.validators;

import api.mappings.garage.vehicle.CreateVehicleRequest;
import api.mappings.garage.vehicle.GetVehicleResponse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class VehicleValidator {
    public static void assertVehicleResponse(GetVehicleResponse actual, GetVehicleResponse expected) {
        if (expected == null) {
            assertThat("Response should be null", actual, nullValue());
            return;
        } else {
            assertThat("Response should not be null", actual, notNullValue());
        }

        assertThat("ID is not the expected", actual.getId(), is(expected.getId()));
        assertThat("Client is not the expected", actual.getClient(), is(expected.getClient()));
        assertThat("Brand is not the expected", actual.getBrand(), is(expected.getBrand()));
        assertThat("Model is not the expected", actual.getModel(), is(expected.getModel()));
        assertThat("Year is not the expected", actual.getYear(), is(expected.getYear()));
        assertThat("Type is not the expected", actual.getType(), is(expected.getType()));
        assertThat("Plate is not the expected", actual.getPlate(), is(expected.getPlate()));
        assertThat("Active is not the expected", actual.getActive(), is(expected.getActive()));
    }

    public static void assertVehicleResponseWithCreateRequest(GetVehicleResponse actual, CreateVehicleRequest expected) {
        if (expected == null) {
            assertThat("Response should be null", actual, nullValue());
            return;
        } else {
            assertThat("Response should not be null", actual, notNullValue());
        }

        assertThat("Brand is not the expected", actual.getBrand(), is(expected.getBrand()));
        assertThat("Model is not the expected", actual.getModel(), is(expected.getModel()));
        assertThat("Year is not the expected", actual.getYear(), is(expected.getYear()));
        assertThat("Type is not the expected", actual.getType(), is(expected.getType()));
        assertThat("Plate is not the expected", actual.getPlate(), is(expected.getPlate()));
        assertThat("Active is not the expected", actual.getActive(), is(expected.getActive()));
    }
}
