package api.validators;

import org.hamcrest.Matcher;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ListValidator {
    public static void assertListNotEmpty(List<?> list) {
        assertThat("List should not be null", list, notNullValue());
        assertThat("List should not be empty", list, not(empty()));
    }

    public static void assertListHasSize(List<?> list, Integer size) {
        assertThat("List should not be null", list, notNullValue());
        assertThat("List does not have the expected size", list, hasSize(size));
    }

    public static void assertListHasSize(List<?> list, Matcher<? super Integer> matcher) {
        assertThat("List should not be null", list, notNullValue());
        assertThat("List does not have the expected size", list, hasSize(matcher));
    }
}
