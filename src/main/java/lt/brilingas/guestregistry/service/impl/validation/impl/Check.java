package lt.brilingas.guestregistry.service.impl.validation.impl;

import lombok.Getter;
import java.util.function.Predicate;

public abstract class Check {
    public enum Type {
        OBJECT_NOT_NULL,
        OBJECT_IS_NULL,
        STRING_MIN_LENGTH,
        STRING_MAX_LENGTH,
        STRING_MATCHES_PATTERN,
        NUMBER_MIN,
        NUMBER_MAX
    }

    @Getter
    private Type type;

    Check(Type type) {
        this.type = type;
    }

    public abstract Predicate<?> getFunc();
}
