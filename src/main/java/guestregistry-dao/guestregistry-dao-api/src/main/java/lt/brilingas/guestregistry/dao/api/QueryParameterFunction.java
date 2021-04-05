package lt.brilingas.guestregistry.dao.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Getter
public enum QueryParameterFunction {  //DB query function with allowed data types set
    EQUALS(new HashSet<>(Set.of(String.class, Enum.class, Boolean.class, Integer.class, Long.class, Double.class, Float.class))),
    NOT_EQUALS(new HashSet<>(Set.of(String.class, Enum.class, Boolean.class, Integer.class, Long.class, Double.class, Float.class))),
    CONTAINS_SUBSTRING(new HashSet<>(Set.of(String.class))),
    GREATER_OR_EQUAL(new HashSet<>(Set.of(Integer.class, Long.class, Double.class, Float.class, Instant.class))),
    LESS_OR_EQUAL(new HashSet<>(Set.of(Integer.class, Long.class, Double.class, Float.class, Instant.class)));

    private final Set<Class<?>> classesAllowedForFunction;
}
