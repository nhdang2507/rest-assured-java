package com.restassured.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restassured.Category;
import com.restassured.model.AuthenticationRequest;
import com.restassured.util.RestClient;
import org.testng.annotations.Test;

import static com.restassured.HttpMethod.POST;
import static com.restassured.constant.ApplicationConstant.AUTH_SERVICE_ENDPOINT;
import static com.restassured.constant.ApplicationConstant.RESTFUL_BOOKER_BASE_URL;
import static com.restassured.constant.AuthenticationConstant.PASSWORD;
import static com.restassured.constant.AuthenticationConstant.USERNAME;
import static com.restassured.test.constant.TestCategory.AUTHENTICATION;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;

public class AuthTest extends BaseTest {
    @Category(AUTHENTICATION)
    @Test(description = "Verify that a user can get the access token")
    public void testAuthentication() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setUsername(USERNAME);
        authenticationRequest.setPassword(PASSWORD);

        ObjectMapper objectMapper = new ObjectMapper();
        String authRequestJson;
        try {
            authRequestJson = objectMapper.writeValueAsString(authenticationRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        new RestClient(RESTFUL_BOOKER_BASE_URL, AUTH_SERVICE_ENDPOINT, authRequestJson)
                .sendRequest(POST)
                .statusCode(SC_OK)
                .body("token", notNullValue());
    }
}