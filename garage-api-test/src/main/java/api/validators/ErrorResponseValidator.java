package api.validators;

import api.mappings.garage.ErrorResponse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ErrorResponseValidator {
    public static void assertErrorResponse(ErrorResponse actual, ErrorResponse expected, boolean ignoreTimestamp) {
        if (expected == null) {
            assertThat("Response should be null", actual, nullValue());
            return;
        } else {
            assertThat("Response should not be null", actual, notNullValue());
        }

        if (!ignoreTimestamp)
            assertThat("Timestamp is not the expected", actual.getTimestamp(), is(expected.getTimestamp()));

        assertThat("Status is not the expected", actual.getStatus(), is(expected.getStatus()));
        assertThat("Error is not the expected", actual.getError(), is(expected.getError()));
        assertThat("Message is not the expected", actual.getMessage(), is(expected.getMessage()));
        assertThat("Path is not the expected", actual.getPath(), is(expected.getPath()));
    }
}
