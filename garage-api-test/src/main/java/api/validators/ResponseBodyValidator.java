package api.validators;

import retrofit2.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ResponseBodyValidator {
    public static void assertBodyNotNull(Response response) {
        assertThat("Response body should not be null", response.body(), notNullValue());
    }

    public static void assertID(Integer actual, Integer expected) {
        assertThat("ID is not the expected", actual, is(expected));
    }
}
