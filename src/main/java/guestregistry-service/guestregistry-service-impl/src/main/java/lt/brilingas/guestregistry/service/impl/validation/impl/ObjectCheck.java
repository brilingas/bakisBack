package lt.brilingas.guestregistry.service.impl.validation.impl;

import lombok.Getter;
import lombok.Setter;
import java.util.function.Predicate;

@Getter
@Setter
public class ObjectCheck extends Check {
    private Predicate<Object> func;

    ObjectCheck(Type type, Predicate<Object> func) {
        super(type);
        this.func = func;
    }

    public static ObjectCheck notNull() {
        Predicate<Object> func = (obj) -> obj != null;
        return new ObjectCheck(Type.OBJECT_NOT_NULL, func);
    }

    public static ObjectCheck isNull() {
        Predicate<Object> func = (obj) -> obj == null;
        return new ObjectCheck(Type.OBJECT_IS_NULL, func);
    }
}
