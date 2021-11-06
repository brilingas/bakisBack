package lt.brilingas.guestregistry.service.impl.validation;

import lt.brilingas.guestregistry.dao.api.ParameterNotValidException;
import lt.brilingas.guestregistry.dao.api.QueryParameterFunction;
import java.util.Map;

public interface IQueryParametersCreator {
    public Map<String, Map<QueryParameterFunction, String>> create(Map<String, String> parameters,
                                                                   Map<String, Class<?>> fieldsAllowedInFilter)
            throws ParameterNotValidException;
}
