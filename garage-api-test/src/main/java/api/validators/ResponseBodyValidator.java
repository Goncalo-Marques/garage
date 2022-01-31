package api.validators;

import retrofit2.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ResponseBodyValidator {
    public static void assertBodyNotNull(Response response) {
        assertThat("Response body should not be null", response.body(), notNullValue());
    }

    public static void assertBodyNull(Response response) {
        assertThat("Response body should be null", response.body(), nullValue());
    }

    public static void assertID(Integer actual, Integer expected) {
        assertThat("ID is not the expected", actual, is(expected));
    }

    public static void assertIDNotNull(Integer actual) {
        assertThat("ID should not be null", actual, notNullValue());
    }

    public static void assertClient(Integer actual, Integer expected) {
        assertThat("Client is not the expected", actual, is(expected));
    }
}
