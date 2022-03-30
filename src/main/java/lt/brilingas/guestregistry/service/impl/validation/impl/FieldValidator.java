package lt.brilingas.guestregistry.service.impl.validation.impl;
import lt.brilingas.guestregistry.service.api.FieldNotValidException;
import org.springframework.stereotype.Service;

@Service
public class  FieldValidator {
    public void validate(Object object, String fieldName, ObjectCheck... checks) throws FieldNotValidException {
        for (ObjectCheck check : checks) {
            if (!check.getFunc().test(object)) {
                switch (check.getType()) {
                    case OBJECT_NOT_NULL -> throw new FieldNotValidException(fieldName + " is null");
                    case OBJECT_IS_NULL -> throw new FieldNotValidException(fieldName + " is not null");
                    default -> throw new IllegalArgumentException();
                }
            }
        }
    }

    public void validate(String string, String fieldName, StringCheck... checks) throws FieldNotValidException {
        checkNotNull(string, fieldName);
        for (StringCheck check : checks) {
            if (!check.getFunc().test(string)) {
                switch (check.getType()) {
                    case STRING_MIN_LENGTH -> throw new FieldNotValidException(fieldName +
                            " is not valid (length must be no less than " + check.getExceptionParam() + ")");
                    case STRING_MAX_LENGTH -> throw new FieldNotValidException(fieldName +
                            " is not valid (length must be no more than " + check.getExceptionParam() + ")");
                    case STRING_MATCHES_PATTERN -> throw new FieldNotValidException(fieldName +
                            " is not valid (string does not match the pattern - " + check.getExceptionParam() + ")");
                    default -> throw new IllegalArgumentException();
                }
            }
        }
    }

    public void validate(Number number, String fieldName, NumberCheck... checks) throws FieldNotValidException {
        checkNotNull(number, fieldName);
        for (NumberCheck check : checks) {
            if (!check.getFunc().test(number)) {
                switch (check.getType()) {
                    case NUMBER_MIN -> throw new FieldNotValidException(fieldName +
                            " is not valid (value must be no less than " + check.getExceptionParam() + ")");
                    case NUMBER_MAX -> throw new FieldNotValidException(fieldName +
                            " is not valid (value must be no more than " + check.getExceptionParam() + ")");
                    default -> throw new IllegalArgumentException();
                }
            }
        }
    }

    private void checkNotNull(Object object, String fieldName) throws FieldNotValidException {
        if (object == null) throw new FieldNotValidException(fieldName + " is null");
    }
}
