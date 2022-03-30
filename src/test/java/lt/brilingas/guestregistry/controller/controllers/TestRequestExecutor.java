package lt.brilingas.guestregistry.controller.controllers;

import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Base64;

public class TestRequestExecutor {
    public static MvcResult post(MockMvc mockMvc, String url, HttpStatus responseStatus, String contentJsonStr)
            throws Exception {
        return getResult(mockMvc, responseStatus, MockMvcRequestBuilders.post(url)
                .content(contentJsonStr)
                .contentType(MediaType.APPLICATION_JSON));
    }

    public static MvcResult postWithBasicAuthorization(MockMvc mockMvc, String url, HttpStatus responseStatus,
                                                       String contentJsonStr, String username, String password) throws Exception {
        return getResult(mockMvc, responseStatus, MockMvcRequestBuilders.post(url)
                .content(contentJsonStr)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", calculateBasicAuthorizationValue(username, password)));
    }

    public static MvcResult put(MockMvc mockMvc, String url, HttpStatus responseStatus, String contentJsonStr)
            throws Exception {
        return getResult(mockMvc, responseStatus, MockMvcRequestBuilders.put(url)
                .content(contentJsonStr)
                .contentType(MediaType.APPLICATION_JSON));
    }

    public static MvcResult putWithBasicAuthorization(MockMvc mockMvc, String url, HttpStatus responseStatus,
                                                      String contentJsonStr, String username, String password) throws Exception {
        return getResult(mockMvc, responseStatus, MockMvcRequestBuilders.put(url)
                .content(contentJsonStr)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", calculateBasicAuthorizationValue(username, password)));
    }

    public static MvcResult get(MockMvc mockMvc, String url, HttpStatus responseStatus) throws Exception {
        return getResult(mockMvc, responseStatus, MockMvcRequestBuilders.get(url));
    }

    public static MvcResult getWithBasicAuthorization(MockMvc mockMvc, String url, HttpStatus responseStatus,
                                                      String username, String password) throws Exception {
        return getResult(mockMvc, responseStatus, MockMvcRequestBuilders.get(url)
                .header("Authorization", calculateBasicAuthorizationValue(username, password)));
    }

    public static MvcResult delete(MockMvc mockMvc, String url, HttpStatus responseStatus) throws Exception {
        return getResult(mockMvc, responseStatus, MockMvcRequestBuilders.delete(url));
    }

    public static MvcResult deleteWithBasicAuthorization(MockMvc mockMvc, String url, HttpStatus responseStatus,
                                                         String username, String password) throws Exception {
        return getResult(mockMvc, responseStatus, MockMvcRequestBuilders.delete(url)
                .header("Authorization", calculateBasicAuthorizationValue(username, password)));
    }

    private static MvcResult getResult(MockMvc mockMvc, HttpStatus responseStatus, RequestBuilder requestBuilder)
            throws Exception {
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assertions.assertEquals(responseStatus.value(), result.getResponse().getStatus());
        return result;
    }

    private static String calculateBasicAuthorizationValue(String username, String password) {
        String str = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(str.getBytes());
    }
}
