package lt.brilingas.guestregistry.service.impl.validation.impl;

//import lt.brilingas.guestregistry.service.impl.config.ServiceTestConfig;
import lt.brilingas.guestregistry.service.api.FieldNotValidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FieldValidatorTest {
    @Autowired
    private FieldValidator fieldValidator;
    private final String FIELD_NAME = "Abc";
    private final String PATTERN = "^[a]{5}$";
    private final int LENGTH = 2;
    private final int NUM_VAL = 5;
    private final String STR_VAL = "Def";

    @Test
    public void validateObjectChecksTest() {
        checkTest(FIELD_NAME, ObjectCheck.notNull(), new Object(), FIELD_NAME + " is null");
        checkTest(FIELD_NAME, ObjectCheck.isNull(), new Object(), FIELD_NAME + " is not null");
    }

    private void checkTest(String fieldName, ObjectCheck check, Object obj, String exMessage) {
        check.setFunc((o) -> true);
        Assertions.assertDoesNotThrow(() -> fieldValidator.validate(obj, fieldName, check));

        check.setFunc((o) -> false);
        Throwable exception = Assertions.assertThrows(FieldNotValidException.class,
                () -> fieldValidator.validate(obj, fieldName, check));
        Assertions.assertEquals(exMessage, exception.getMessage());
    }

    @Test
    public void validateStringChecksTest() {
        checkTest(FIELD_NAME, StringCheck.minLength(LENGTH), null,
                FIELD_NAME + " is null");
        checkTest(FIELD_NAME, StringCheck.minLength(LENGTH), STR_VAL,
                FIELD_NAME + " is not valid (length must be no less than " + LENGTH + ")");
        checkTest(FIELD_NAME, StringCheck.maxLength(LENGTH), STR_VAL,
                FIELD_NAME + " is not valid (length must be no more than " + LENGTH + ")");
        checkTest(FIELD_NAME, StringCheck.matchesPattern(PATTERN), STR_VAL,
                FIELD_NAME + " is not valid (string does not match the pattern - " + PATTERN + ")");
    }

    private void checkTest(String fieldName, StringCheck check, String str, String exMessage) {
        if(str != null) {
            check.setFunc((o) -> true);
            Assertions.assertDoesNotThrow(() -> fieldValidator.validate(str, fieldName, check));
        }
        check.setFunc((o) -> false);
        Throwable exception = Assertions.assertThrows(FieldNotValidException.class,
                () -> fieldValidator.validate(str, fieldName, check));
        Assertions.assertEquals(exMessage, exception.getMessage());
    }

    private String generateString(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append('a');
        }
        return builder.toString();
    }

    @Test
    public void validateNumberChecksTest() {
        checkTest(FIELD_NAME, NumberCheck.min(NUM_VAL), null,
                FIELD_NAME + " is null");
        checkTest(FIELD_NAME, NumberCheck.min(NUM_VAL), NUM_VAL,
                FIELD_NAME + " is not valid (value must be no less than " + NUM_VAL + ")");
        checkTest(FIELD_NAME, NumberCheck.max(NUM_VAL), NUM_VAL,
                FIELD_NAME + " is not valid (value must be no more than " + NUM_VAL + ")");
    }

    private void checkTest(String fieldName, NumberCheck check, Number num, String exMessage) {
        if(num != null) {
            check.setFunc((o) -> true);
            Assertions.assertDoesNotThrow(() -> fieldValidator.validate(num, fieldName, check));
        }
        check.setFunc((o) -> false);
        Throwable exception = Assertions.assertThrows(FieldNotValidException.class,
                () -> fieldValidator.validate(num, fieldName, check));
        Assertions.assertEquals(exMessage, exception.getMessage());
    }
}
