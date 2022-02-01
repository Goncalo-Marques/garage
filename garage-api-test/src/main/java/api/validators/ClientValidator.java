package api.validators;

import api.mappings.garage.client.ClientRequest;
import api.mappings.garage.client.ClientResponse;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static api.validators.ListValidator.assertListHasSize;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.*;

public class ClientValidator {
    public static void assertClientResponse(ClientResponse actual, ClientRequest expected) throws ParseException {
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

        assertThat("Phone number is not the expected", actual.getPhoneNumber(), is(expected.getPhoneNumber()));
        Matcher phoneNumberPattern = Pattern.compile("^([0-9]{9})$").matcher(String.valueOf(actual.getPhoneNumber()));
        assertThat("Phone number is not valid", phoneNumberPattern.matches());

        assertThat("NIF is not the expected", actual.getNif(), is(expected.getNif()));
        Matcher NIFPatern = Pattern.compile("^([0-9]{9})$").matcher(String.valueOf(actual.getNif()));
        assertThat("NIF number is not valid", NIFPatern.matches());

        assertThat("Birth Date is not the expected", actual.getBirthDate(), is(expected.getBirthDate()));
        if (actual.getBirthDate() != null)
            assertThat("Birth date is not valid", actual.getBirthDate(), is(lessThanOrEqualTo(String.valueOf(LocalDate.now()))));

        assertThat("Client Date is not the expected", actual.getClientDate(), is(expected.getClientDate()));
        if (actual.getClientDate() != null)
            assertThat("Client date is not valid", new SimpleDateFormat("yyyy-MM-dd").parse(actual.getClientDate()), is(lessThanOrEqualTo(new Date(System.currentTimeMillis()))));

        assertListHasSize(actual.getVehicles(), 0);
    }
}
