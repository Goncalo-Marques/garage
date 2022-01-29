package api.validators;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DateValidator {
    public static void assertDatelessEqualTo(LocalDate actual, LocalDate expected) {
        if (expected == null) {
            assertThat("Date should be null", actual, nullValue());
            return;
        }

        assertThat("Date should not be null", actual, notNullValue());
        assertThat("Date is not the expected", actual, is(expected));
    }

    public static void assertDatelessThanOrEqualTo(LocalDate actual, LocalDate expected) {
        if (expected == null) {
            assertThat("Date should be null", actual, nullValue());
            return;
        }

        assertThat("Date should not be null", actual, notNullValue());
        assertThat("Date is not the expected", (actual.isBefore(expected) || actual.isEqual(expected)));
    }
}
