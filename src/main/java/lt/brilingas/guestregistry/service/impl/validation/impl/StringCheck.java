package lt.brilingas.guestregistry.service.impl.validation.impl;

import lombok.Getter;
import lombok.Setter;
import java.util.function.Predicate;

@Getter
@Setter
public class StringCheck extends Check {
    private Predicate<String> func;
    private String exceptionParam;

    StringCheck(Type type, Predicate<String> func, String exceptionParam) {
        super(type);
        this.func = func;
        this.exceptionParam = exceptionParam;
    }

    public static StringCheck minLength(Integer minLength) {
        Predicate<String> func = (str) -> str.length() >= minLength;
        return new StringCheck(Type.STRING_MIN_LENGTH, func, minLength.toString());
    }

    public static StringCheck maxLength(Integer maxLength) {
        Predicate<String> func = (str) -> str.length() <= maxLength;
        return new StringCheck(Type.STRING_MAX_LENGTH, func, maxLength.toString());
    }

    public static StringCheck matchesPattern(String pattern) {
        Predicate<String> func = (str) -> str.matches(pattern);
        return new StringCheck(Type.STRING_MATCHES_PATTERN, func, pattern);
    }
}
