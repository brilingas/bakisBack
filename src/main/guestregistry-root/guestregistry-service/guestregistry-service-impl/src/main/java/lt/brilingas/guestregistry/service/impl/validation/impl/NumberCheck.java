package lt.brilingas.guestregistry.service.impl.validation.impl;

import lombok.Getter;
import lombok.Setter;
import java.util.function.Predicate;

@Getter
@Setter
public class NumberCheck extends Check {
    private Predicate<Number> func;
    private String exceptionParam;

    NumberCheck(Type type, Predicate<Number> func, String exceptionParam) {
        super(type);
        this.func = func;
        this.exceptionParam = exceptionParam;
    }

    public static NumberCheck min(Number min) {
        Predicate<Number> func = (num) -> num.doubleValue() >= min.doubleValue();
        return new NumberCheck(Type.NUMBER_MIN, func, min.toString());
    }

    public static NumberCheck max(Number max) {
        Predicate<Number> func = (num) -> num.doubleValue() <= max.doubleValue();
        return new NumberCheck(Type.NUMBER_MAX, func, max.toString());
    }
}
