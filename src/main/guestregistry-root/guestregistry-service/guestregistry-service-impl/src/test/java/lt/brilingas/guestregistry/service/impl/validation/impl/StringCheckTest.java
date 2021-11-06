package lt.brilingas.guestregistry.service.impl.validation.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringCheckTest {
    @Test
    public void minLengthTest() {
        final int length = 5;
        validateCheck(StringCheck.minLength(length),
                Check.Type.STRING_MIN_LENGTH,
                generateString(length),
                generateString(length - 1),
                length + "");
    }

    @Test
    public void maxLengthTest() {
        final int length = 5;
        validateCheck(StringCheck.maxLength(length),
                Check.Type.STRING_MAX_LENGTH,
                generateString(length),
                generateString(length + 1),
                length + "");
    }

    @Test
    public void matchesPatternTest() {
        final String pattern = "^[a]{5}$";
        validateCheck(StringCheck.matchesPattern(pattern),
                Check.Type.STRING_MATCHES_PATTERN,
                "aaaaa",
                "abc",
                pattern);
    }

    private void validateCheck(StringCheck check, Check.Type checkType, String validValue, String invalidValue,
                               String exceptionParam) {
        Assertions.assertEquals(checkType, check.getType());
        Assertions.assertTrue(check.getFunc().test(validValue));
        Assertions.assertFalse(check.getFunc().test(invalidValue));
        Assertions.assertEquals(exceptionParam, check.getExceptionParam());
    }

    private String generateString(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append('a');
        }
        return builder.toString();
    }
}
