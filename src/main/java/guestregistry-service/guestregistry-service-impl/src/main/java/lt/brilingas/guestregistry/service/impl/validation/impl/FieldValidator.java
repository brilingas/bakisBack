package lt.brilingas.guestregistry.service.impl.validation.impl;

import lt.brilingas.guestregistry.service.data.FieldNotValidException;
import org.springframework.stereotype.Service;

@Service
public class FieldValidator {
    public void validate(Object obj, String fieldName, ObjectCheck... checks) throws FieldNotValidException {
        for (ObjectCheck check : checks) {
            if (!check.getFunc().test(obj)) {
                switch (check.getType()) {
                    case OBJECT_NOT_NULL -> throw new FieldNotValidException(fieldName + " is null");
                    case OBJECT_IS_NULL -> throw new FieldNotValidException(fieldName + " is not null");
                    default -> throw new IllegalArgumentException();
                }
            }
        }
    }

    public void validate(String str, String fieldName, StringCheck... checks) throws FieldNotValidException {
        checkNotNull(str, fieldName);
        for (StringCheck check : checks) {
            if (!check.getFunc().test(str)) {
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

    public void validate(Number num, String fieldName, NumberCheck... checks) throws FieldNotValidException {
        checkNotNull(num, fieldName);
        for (NumberCheck check : checks) {
            if (!check.getFunc().test(num)) {
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

    private void checkNotNull(Object obj, String fieldName) throws FieldNotValidException {
        if (obj == null) throw new FieldNotValidException(fieldName + " is null");
    }
}
