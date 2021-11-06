package lt.brilingas.guestregistry.controller.errorhandler;

import lt.brilingas.guestregistry.dao.api.ParameterNotValidException;
import lt.brilingas.guestregistry.service.data.FieldNotValidException;
import lt.brilingas.guestregistry.service.data.ResourceNotFoundException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

@RestController
public class ErrorControllerAdviceTestController {
    @GetMapping(path = "/field_not_valid_exception")
    public String getFieldNotValidException(@RequestParam String message) throws FieldNotValidException {
        throw new FieldNotValidException(message);
    }

    @GetMapping(path = "/parameter_not_valid_exception")
    public String getParameterNotValidException(@RequestParam String message) throws ParameterNotValidException {
        throw new ParameterNotValidException(message);
    }

    @GetMapping(path = "/http_message_not_readable_exception")
    public String getHttpMessageNotReadableException() {
        throw new HttpMessageNotReadableException("");
    }

    @GetMapping(path = "/resource_not_found_exception")
    public String getResourceNotFoundException(@RequestParam String message) throws ResourceNotFoundException {
        throw new ResourceNotFoundException(message);
    }

    @GetMapping(path = "/exception")
    public String getException() throws Exception {
        throw new Exception();
    }
}
