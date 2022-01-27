package api.validators;

import api.mappings.garage.client.ClientRequest;
import api.mappings.garage.client.ClientResponse;

import static api.validators.ListValidator.assertListHasSize;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.*;

public class ClientValidator {
    public static void assertClientResponse(ClientResponse actual, ClientRequest expected){
        if (expected == null) {
            assertThat("Response should be null", actual, nullValue());
            return;
        }else{
            assertThat("Response should not be null", actual, notNullValue());
        }

        assertThat("First name is not the expected", actual.getFirstName(), is(expected.getFirstName()));
        assertThat("Last name is not the expected", actual.getLastName(), is(expected.getLastName()));
        assertThat("Address is not the expected", actual.getAddress(), is(expected.getAddress()));
        assertThat("Postal Code is not the expected", actual.getPostalCode(), is(expected.getPostalCode()));
        assertThat("City is not the expected", actual.getCity(), is(expected.getCity()));
        assertThat("Country is not the expected", actual.getCountry(), is(expected.getCountry()));
        assertThat("Phone number is not the expected", actual.getPhoneNumber(), is(expected.getPhoneNumber()));
        assertThat("NIF is not the expected", actual.getNif(), is(expected.getNif()));
        assertThat("Birth Date is not the expected", actual.getBirthDate(), is(expected.getBirthDate()));
        assertThat("Client Date is not the expected", actual.getClientDate(), is(expected.getClientDate()));
        assertListHasSize(actual.getVehicles(), 0);
    }

    //TODO validar o codigo postal numero de telefone, NIF, datas
}
