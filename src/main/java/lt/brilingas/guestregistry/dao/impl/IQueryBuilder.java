package lt.brilingas.guestregistry.dao.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import lt.brilingas.guestregistry.dao.api.ParameterNotValidException;
import lt.brilingas.guestregistry.dao.api.QueryParameterFunction;
import org.springframework.data.mongodb.core.query.BasicQuery;
import java.util.Map;

public interface IQueryBuilder {
    public BasicQuery build(Map<String, Map<QueryParameterFunction, String>> parameters,
                            Map<String, Class<?>> fieldsAllowedInFilter)
            throws JsonProcessingException, ParameterNotValidException;
}
