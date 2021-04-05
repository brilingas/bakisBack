package lt.brilingas.guestregistry.dao.impl.mongo.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import lt.brilingas.guestregistry.dao.api.ParameterNotValidException;
import lt.brilingas.guestregistry.dao.api.QueryParameterFunction;
import lt.brilingas.guestregistry.dao.impl.mongo.dao.impl.IQueryBuilder;
import lt.brilingas.guestregistry.dao.impl.mongo.dao.impl.QueryBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.query.BasicQuery;
import java.util.Collections;

public class QueryBuilderTest {
    private IQueryBuilder queryBuilder = new QueryBuilder();

    @Test
    public void buildOkTest() throws JsonProcessingException, ParameterNotValidException {
        String fieldName = "Abc";
        QueryParameterFunction function = QueryParameterFunction.EQUALS;
        String value = "Def";

        BasicQuery result = queryBuilder.build(Collections.singletonMap(fieldName, Collections.singletonMap(function, value)),
                Collections.singletonMap(fieldName, String.class));
        BasicQuery expected = new BasicQuery("{" + fieldName + ": {$eq: \"" + value + "\"}}");
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void buildThrowsParameterNotValidExceptionTest() throws JsonProcessingException, ParameterNotValidException {
        String fieldName = "Abc";
        QueryParameterFunction function = QueryParameterFunction.EQUALS;
        String value = "Def";

        Assertions.assertThrows(ParameterNotValidException.class, () -> queryBuilder.build(
                Collections.singletonMap(fieldName, Collections.singletonMap(function, value)),
                Collections.singletonMap(fieldName, Integer.class)));
    }
}
