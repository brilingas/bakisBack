package lt.brilingas.guestregistry.service.impl.validation.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NumberCheckTest {
    @Test
    public void minLengthTest() {
        final Integer val = 5;
        validateCheck(NumberCheck.min(val),
                Check.Type.NUMBER_MIN,
                val,
                val - 1,
                val + "");
    }

    @Test
    public void maxLengthTest() {
        final Integer val = 5;
        validateCheck(NumberCheck.max(val),
                Check.Type.NUMBER_MAX,
                val,
                val + 1,
                val + "");
    }

    private void validateCheck(NumberCheck check, Check.Type checkType, Number validValue, Number invalidValue,
                               String exceptionParam) {
        Assertions.assertEquals(checkType, check.getType());
        Assertions.assertTrue(check.getFunc().test(validValue));
        Assertions.assertFalse(check.getFunc().test(invalidValue));
        Assertions.assertEquals(exceptionParam, check.getExceptionParam());
    }
}
