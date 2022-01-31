package api.validators;

import api.mappings.garage.client.ClientRequest;
import api.mappings.garage.client.ClientResponse;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static api.validators.ListValidator.assertListHasSize;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.*;

public class ClientValidator {
    public static void assertClientResponse(ClientResponse actual, ClientRequest expected) {
        if (expected == null) {
            assertThat("Response should be null", actual, nullValue());
            return;
        }

        assertThat("Response should not be null", actual, notNullValue());
        assertThat("First name is not the expected", actual.getFirstName(), is(expected.getFirstName()));
        assertThat("Last name is not the expected", actual.getLastName(), is(expected.getLastName()));
        assertThat("Address is not the expected", actual.getAddress(), is(expected.getAddress()));
        assertThat("Postal Code is not the expected", actual.getPostalCode(), is(expected.getPostalCode()));
        assertThat("City is not the expected", actual.getCity(), is(expected.getCity()));
        assertThat("Country is not the expected", actual.getCountry(), is(expected.getCountry()));

        // TODO VÊ SE ESTE ASSERT DO TELEMOVEL ESTA BEM
        assertThat("Phone number is not the expected", actual.getPhoneNumber(), is(expected.getPhoneNumber()));
        if (actual.getPhoneNumber() != null)
            assertThat("Client phone number can only have 9 digits", String.valueOf(actual.getPhoneNumber()).length(), is(String.valueOf(expected.getPhoneNumber()).length()));
        Matcher phoneNumberPattern = Pattern.compile("^([0-9]{9})$").matcher(String.valueOf(actual.getPhoneNumber()));
        assertThat("Phone number is not valid", phoneNumberPattern.matches());

        // TODO VÊ SE ESTE ASSERT DO NIF ESTA BEM
        assertThat("NIF is not the expected", actual.getNif(), is(expected.getNif()));
        if (actual.getNif() != null)
            assertThat("Client phone number can only have 9 digits", String.valueOf(actual.getNif()).length(), is(String.valueOf(expected.getNif()).length()));
        Matcher NIFPatern = Pattern.compile("^([0-9]{9})$").matcher(String.valueOf(actual.getNif()));
        assertThat("NIF number is not valid", NIFPatern.matches());

        // TODO VÊ SE ESTE ASSERT DA DATA ESTA BEM
        assertThat("Birth Date is not the expected", actual.getBirthDate(), is(expected.getBirthDate()));
        if (actual.getBirthDate() != null)
            assertThat("Birth date is not valid", actual.getBirthDate(), is(lessThanOrEqualTo(String.valueOf(LocalDate.now()))));

        // TODO VÊ SE ESTE ASSERT DA DATA ESTA BEM
        assertThat("Client Date is not the expected", actual.getClientDate(), is(expected.getClientDate()));
        if (actual.getClientDate() != null)
            assertThat("Client date is not valid", actual.getClientDate(), is(lessThanOrEqualTo(String.valueOf(LocalDate.now()))));

        assertListHasSize(actual.getVehicles(), 0);
    }

    //TODO validar o codigo postal numero de telefone, NIF, datas
}
