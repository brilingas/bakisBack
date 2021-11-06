package lt.brilingas.guestregistry.service.impl.validation;

import lt.brilingas.guestregistry.dao.api.ParameterNotValidException;
import lt.brilingas.guestregistry.dao.api.QueryParameterFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.Map;

public class QueryParameterCreatorTest {
    IQueryParametersCreator queryParametersCreator = new QueryParametersCreator();

    @Test
    public void createOkTest() throws ParameterNotValidException {
        String fieldName = "abc";
        String functionName = "equals";
        String value = "def";

        Map<String, Map<QueryParameterFunction, String>> result = queryParametersCreator.create(
                Collections.singletonMap(fieldName + "" + functionName, value),
                Collections.singletonMap(fieldName, String.class));
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.containsKey(fieldName));
        Assertions.assertEquals(1, result.get(fieldName).size());
        Assertions.assertTrue(result.get(fieldName).containsKey(QueryParameterFunction.EQUALS));
        Assertions.assertEquals(value, result.get(fieldName).get(QueryParameterFunction.EQUALS));
    }
}
