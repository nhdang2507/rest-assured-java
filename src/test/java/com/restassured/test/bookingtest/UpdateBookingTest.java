package com.restassured.test.bookingtest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restassured.Category;
import com.restassured.helper.Helper;
import com.restassured.model.BookingDates;
import com.restassured.model.BookingRequest;
import com.restassured.test.BaseTest;
import com.restassured.util.RestClient;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.restassured.HttpMethod.PUT;
import static com.restassured.constant.ApplicationConstant.*;
import static com.restassured.test.constant.TestCategory.BOOKING;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class UpdateBookingTest extends BaseTest {
    private final BookingDates bookingDates = new BookingDates();
    private final BookingRequest bookingRequest = new BookingRequest();
    private String updateBookingRequestBody;
    private String updateBookingEndPoint;
    private Map<String, String> updateBookingRequestHeader = new HashMap<>();

    @BeforeClass
    public void setUpdateBookingRequest() {
        //Set end point
        updateBookingEndPoint = BOOKING_SERVICE_ENDPOINT + "/1469";
        //Set header
        updateBookingRequestHeader.put("Content-Type", CONTENT_TYPE);
        updateBookingRequestHeader.put("Accept", CONTENT_TYPE);

        // Set body
        bookingDates.setCheckIn("2025-04-05");
        bookingDates.setCheckOut("2025-04-19");

        bookingRequest.setFirstName(getMockService().names().first().get()); //random first name
        bookingRequest.setLastName(getMockService().names().last().get()); //random last name
        bookingRequest.setTotalPrice(Helper.getRandomNumber(100, 500));
        bookingRequest.setDepositPaid(true);
        bookingRequest.setBookingDates(bookingDates);
        bookingRequest.setAdditionalNeeds("Launch");

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            updateBookingRequestBody = objectMapper.writeValueAsString(bookingRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Category(BOOKING)
    @Test(description = "Verify that a user can create new booking")
    public void testUpdateBooking() {
        new RestClient(RESTFUL_BOOKER_BASE_URL, updateBookingEndPoint, Collections.emptyMap(),
                true, updateBookingRequestHeader, updateBookingRequestBody)
                .sendRequest(PUT)
                .statusCode(SC_OK)
                .body("firstname", equalTo(bookingRequest.getFirstName()))
                .body("lastname", equalTo(bookingRequest.getLastName()))
                .body("totalprice", equalTo(bookingRequest.getTotalPrice()))
                .body("depositpaid", equalTo(bookingRequest.isDepositPaid()))
                .body("bookingdates.checkin", equalTo(bookingDates.getCheckIn()))
                .body("bookingdates.checkout", equalTo(bookingDates.getCheckOut()))
                .body("additionalneeds", equalTo(bookingRequest.getAdditionalNeeds()));
    }

    @Category(BOOKING)
    @Test(description = "Verify that a booking cannot be raised without the required fields")
    public void testCreateBookingWithoutRequiredFields() {
        new RestClient(RESTFUL_BOOKER_BASE_URL, updateBookingEndPoint, Collections.emptyMap(),
                true, updateBookingRequestHeader, EMPTY)
                .sendRequest(PUT)
                .statusCode(SC_BAD_REQUEST);
    }

    @Category(BOOKING)
    @Test(description = "Verify that a booking cannot be updated without the authentication")
    public void testUpdateBookingWithoutAuthentication() {
        new RestClient(RESTFUL_BOOKER_BASE_URL, updateBookingEndPoint, Collections.emptyMap(),
                false, updateBookingRequestHeader, updateBookingRequestBody)
                .sendRequest(PUT)
                .statusCode(SC_FORBIDDEN);
    }
}
