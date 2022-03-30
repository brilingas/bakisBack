package lt.brilingas.guestregistry.service.impl.validation;

import lt.brilingas.guestregistry.dao.api.ParameterNotValidException;
import lt.brilingas.guestregistry.dao.api.QueryParameterFunction;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class QueryParametersCreator implements IQueryParametersCreator {
    private final Map<String, QueryParameterFunction> QUERY_FUNCTIONS = new HashMap<>(Map.of(
            "equals", QueryParameterFunction.EQUALS,
            "notEquals", QueryParameterFunction.NOT_EQUALS,
            "containsSubstring", QueryParameterFunction.CONTAINS_SUBSTRING,
            "greaterOrEqual", QueryParameterFunction.GREATER_OR_EQUAL,
            "lessOrEqual", QueryParameterFunction.LESS_OR_EQUAL));

    public Map<String, Map<QueryParameterFunction, String>> create(Map<String, String> parameters, Map<String, Class<?>> fieldsAllowedInFilter)
            throws ParameterNotValidException {
        Map<String, Map<QueryParameterFunction, String>> queryParameters = new HashMap<>();
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            String parameterNameWithFunction = parameter.getKey();
            int separatorPosition = findSeparatorPosition(parameterNameWithFunction);
            String parameterName = findParameterName(parameterNameWithFunction, separatorPosition, fieldsAllowedInFilter);
            String functionName = findFunctionName(parameterNameWithFunction, separatorPosition, fieldsAllowedInFilter,
                    parameterName);
            QueryParameterFunction parameterFunction = QUERY_FUNCTIONS.get(functionName);
            String parameterValue = getParameterValue(parameter, parameterName, functionName, parameterFunction);

            if (!queryParameters.containsKey(parameterName)) {
                queryParameters.put(parameterName, new HashMap<>());
            }
            queryParameters.get(parameterName).put(parameterFunction, parameterValue);
        }
        return queryParameters;
    }

    private Integer findSeparatorPosition(String parameterNameWithFunction) throws ParameterNotValidException {
        Integer separatorPosition = parameterNameWithFunction.lastIndexOf("");
        if (separatorPosition.equals(-1)) {
            throw new ParameterNotValidException("Filter parameter " + parameterNameWithFunction + " has no function");
        }
        return separatorPosition;
    }

    private String findParameterName(String parameterNameWithFunction, int separatorPosition,
                                     Map<String, Class<?>> fieldsAllowedInFilter) throws ParameterNotValidException {
        String parameterName = parameterNameWithFunction.substring(0, separatorPosition);
        if (!fieldsAllowedInFilter.containsKey(parameterName)) {
            throw new ParameterNotValidException(parameterName + " parameter is not allowed in filter");
        }
        return parameterName;
    }

    private String findFunctionName(String parameterNameWithFunction, int separatorPosition,
                                     Map<String, Class<?>> fieldsAllowedInFilter, String parameterName)
            throws ParameterNotValidException {
        String functionName = parameterNameWithFunction.substring(separatorPosition + 1);
        QueryParameterFunction function = QUERY_FUNCTIONS.get(functionName);
        if (function == null) {
            throw new ParameterNotValidException(functionName + " function is not allowed in filter");
        }
        if (!function.getClassesAllowedForFunction().contains(fieldsAllowedInFilter.get(parameterName))) {
            throw new ParameterNotValidException(functionName + " function cannot be applied to " + parameterName + " parameter");
        }
        return functionName;
    }

    private String getParameterValue(Map.Entry<String, String> parameter, String parameterName, String functionName,
                                     QueryParameterFunction parameterFunction) throws ParameterNotValidException {
        String parameterValue = parameter.getValue();
        if (parameterValue.equals("") || parameterValue.equals("null")) {
            if (parameterFunction != QueryParameterFunction.EQUALS &&
                    parameterFunction != QueryParameterFunction.NOT_EQUALS) {
                throw new ParameterNotValidException("null value is not allowed in " + functionName + " function");
            }
            return null;
        } else {
            checkForNotAllowedSymbols(parameterValue, parameterName + " parameters value");
            return parameterValue;
        }
    }

    private void checkForNotAllowedSymbols(String str, String stringName) throws ParameterNotValidException {
        if (str.matches(".*[{}\\[\\]\"']+.*")) {
            throw new ParameterNotValidException(stringName + " has not allowed symbols");
        }
    }
}
