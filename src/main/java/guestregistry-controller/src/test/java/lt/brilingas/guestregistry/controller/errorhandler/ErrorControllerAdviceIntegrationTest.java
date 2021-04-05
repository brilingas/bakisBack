package lt.brilingas.guestregistry.controller.errorhandler;

import lt.brilingas.guestregistry.controller.controllers.TestRequestExecutor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebMvcTest(controllers = {ErrorControllerAdviceTestController.class})
@AutoConfigureMockMvc(addFilters = false)  //disable filters from Spring Security/ other
public class ErrorControllerAdviceIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    private final String EX_MESSAGE = "exMessage";

    @Test
    public void handleBadRequestValueNotValidFieldNotValidExceptionTest() throws Exception {
        testException("/field_not_valid_exception",
                "Bad request due to error in syntax: ",
                EX_MESSAGE,
                HttpStatus.BAD_REQUEST);
    }

    @Test
    public void handleBadRequestValueNotValidParameterNotValidExceptionTest() throws Exception {
        testException("/parameter_not_valid_exception",
                "Bad request due to error in syntax: ",
                EX_MESSAGE,
                HttpStatus.BAD_REQUEST);
    }

    @Test
    public void handleBadRequestHttpMessageNotReadableExceptionTest() throws Exception {
        testException("/http_message_not_readable_exception",
                "Bad request due to error in syntax",
                HttpStatus.BAD_REQUEST);
    }

    @Test
    public void handleResourceNotFoundResourceNotFoundExceptionTest() throws Exception {
        testException("/resource_not_found_exception",
                "Resource not found: ",
                EX_MESSAGE,
                HttpStatus.NOT_FOUND);
    }

    @Test
    public void handleServerErrorExceptionTest() throws Exception {
        testException("/exception",
                "Internal server error",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void testException(String url, String messagePrefix, String message, HttpStatus httpStatus) throws Exception {
        MvcResult result = TestRequestExecutor.get(mockMvc, url + "?message=" + message, httpStatus);
        testRequestResult(result, messagePrefix + message);
    }

    private void testException(String url, String message, HttpStatus httpStatus) throws Exception {
        MvcResult result = TestRequestExecutor.get(mockMvc, url, httpStatus);
        testRequestResult(result, message);
    }

    private void testRequestResult(MvcResult result, String message) throws UnsupportedEncodingException {
        String resultString = result.getResponse().getContentAsString();
        Pattern p = Pattern.compile("^\\{\"errorId\":[0-9]{1,10},\"message\":\"" + message + "\"}$");
        Matcher m = p.matcher(resultString);
        Assertions.assertTrue(m.matches());
    }
}
