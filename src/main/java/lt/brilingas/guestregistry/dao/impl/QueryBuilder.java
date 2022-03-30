package lt.brilingas.guestregistry.dao.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.RawValue;
import lt.brilingas.guestregistry.dao.api.ParameterNotValidException;
import lt.brilingas.guestregistry.dao.api.QueryParameterFunction;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@Service
public class QueryBuilder implements IQueryBuilder {
    private final Map<QueryParameterFunction, String> QUERY_FUNCTION_NAMES = new HashMap<>(Map.of(
            QueryParameterFunction.EQUALS, "$eq",
            QueryParameterFunction.NOT_EQUALS, "$ne",
            QueryParameterFunction.CONTAINS_SUBSTRING, "$regex",
            QueryParameterFunction.GREATER_OR_EQUAL, "$gte",
            QueryParameterFunction.LESS_OR_EQUAL, "$lte"));

    public BasicQuery build(Map<String, Map<QueryParameterFunction, String>> parameters,
                            Map<String, Class<?>> fieldsAllowedInFilter)
            throws ParameterNotValidException, JsonProcessingException {
        ObjectMapper JSONMapper = new ObjectMapper();
        ObjectNode queryJSON = JSONMapper.createObjectNode();

        for (Map.Entry<String, Map<QueryParameterFunction, String>> parameter : parameters.entrySet()) {
            String parameterName = parameter.getKey();
            ObjectNode addedParameterNode = queryJSON.putObject(parameterName);
            for (Map.Entry<QueryParameterFunction, String> functionWithValue : parameter.getValue().entrySet()) {
                String parameterValue = functionWithValue.getValue();
                String parameterFunctionString = QUERY_FUNCTION_NAMES.get(functionWithValue.getKey());

                if (parameterValue == null) {
                    addedParameterNode.put(parameterFunctionString, (String) null);
                } else {
                    RawValue rawValue = createRawValue(fieldsAllowedInFilter.get(parameterName), parameterName, parameterValue);
                    addedParameterNode.putRawValue(parameterFunctionString, rawValue);
                }
                addQueryFunctionOptions(addedParameterNode, functionWithValue.getKey());
            }
        }
        return new BasicQuery(JSONMapper.writeValueAsString(queryJSON));
    }

    private RawValue createRawValue(Class<?> parameterClass, String parameterName, String stringValue)
            throws ParameterNotValidException {
        RawValue rawValue;
        try {
            if (String.class.equals(parameterClass) || Enum.class.equals(parameterClass)) {
                rawValue = new RawValue("\"" + stringValue + "\"");
            } else if (Boolean.class.equals(parameterClass)) {
                rawValue = new RawValue("" + Boolean.parseBoolean(stringValue));
            } else if (Integer.class.equals(parameterClass) || Long.class.equals(parameterClass)) {
                rawValue = new RawValue("" + Long.parseLong(stringValue));
            } else if (Double.class.equals(parameterClass) || Float.class.equals(parameterClass)) {
                rawValue = new RawValue("" + Double.parseDouble(stringValue));
            } else if (Instant.class.equals(parameterClass)) {
                rawValue = new RawValue("new Date(" + Instant.parse(stringValue).toEpochMilli() + ")");
            } else {
                throw new IllegalArgumentException();
            }
        } catch (NumberFormatException | DateTimeParseException ex) {
            throw new ParameterNotValidException(parameterName + " parameter value is not valid");
        }
        return rawValue;
    }

    private void addQueryFunctionOptions(ObjectNode addedParameterNode, QueryParameterFunction parameterFunction) {
        if (parameterFunction == QueryParameterFunction.CONTAINS_SUBSTRING) {
            addedParameterNode.put("$options", "i");  // option "i" - case insensitivity
        }
    }
}
