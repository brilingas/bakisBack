package lt.brilingas.guestregistry.service.impl.validation.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ObjectCheckTest {
    @Test
    public void notNullTest() {
        validateCheck(ObjectCheck.notNull(),
                Check.Type.OBJECT_NOT_NULL,
                new Object(),
                null);
    }

    @Test
    public void isNullTest() {
        validateCheck(ObjectCheck.isNull(),
                Check.Type.OBJECT_IS_NULL,
                null,
                new Object());
    }

    private void validateCheck(ObjectCheck check, Check.Type checkType, Object validValue, Object invalidValue) {
        Assertions.assertEquals(checkType, check.getType());
        Assertions.assertTrue(check.getFunc().test(validValue));
        Assertions.assertFalse(check.getFunc().test(invalidValue));
    }
}
