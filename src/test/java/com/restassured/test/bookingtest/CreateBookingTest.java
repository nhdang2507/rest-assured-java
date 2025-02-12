package com.restassured.test.bookingtest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restassured.Category;
import com.restassured.helper.Helper;
import com.restassured.model.BookingDates;
import com.restassured.model.BookingRequest;
import com.restassured.test.BaseTest;
import com.restassured.util.RestClient;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.restassured.HttpMethod.GET;
import static com.restassured.HttpMethod.POST;
import static com.restassured.constant.ApplicationConstant.*;
import static com.restassured.test.constant.TestCategory.BOOKING;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreateBookingTest extends BaseTest {
    private final BookingDates bookingDates = new BookingDates();
    private final BookingRequest bookingRequest = new BookingRequest();
    private String createBookingRequestBody;
    private Map<String, String> createBookingRequestHeader = new HashMap<>();

    @BeforeClass
    public void setCreateBookingRequest() {
        //Set header
        createBookingRequestHeader.put("Content-Type", CONTENT_TYPE);
        createBookingRequestHeader.put("Accept", CONTENT_TYPE);

        // Set body
        bookingDates.setCheckIn("2025-04-01");
        bookingDates.setCheckOut("2025-04-18");

        bookingRequest.setFirstName(getMockService().names().first().get()); //random first name
        bookingRequest.setLastName(getMockService().names().last().get()); //random last name
        bookingRequest.setTotalPrice(Helper.getRandomNumber(100, 500));
        bookingRequest.setDepositPaid(true);
        bookingRequest.setBookingDates(bookingDates);
        bookingRequest.setAdditionalNeeds("Breakfast");

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            createBookingRequestBody = objectMapper.writeValueAsString(bookingRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Category(BOOKING)
    @Test(description = "Verify that a user can create new booking")
    public void testCreateBooking() {
        new RestClient(RESTFUL_BOOKER_BASE_URL, BOOKING_SERVICE_ENDPOINT, Collections.emptyMap(),
                true, createBookingRequestHeader, createBookingRequestBody)
                .sendRequest(POST)
                .statusCode(SC_OK)
                .body("bookingid", notNullValue())
                .body("booking.firstname", equalTo(bookingRequest.getFirstName()))
                .body("booking.lastname", equalTo(bookingRequest.getLastName()))
                .body("booking.totalprice", equalTo(bookingRequest.getTotalPrice()))
                .body("booking.depositpaid", equalTo(bookingRequest.isDepositPaid()))
                .body("booking.bookingdates.checkin", equalTo(bookingDates.getCheckIn()))
                .body("booking.bookingdates.checkout", equalTo(bookingDates.getCheckOut()))
                .body("booking.additionalneeds", equalTo(bookingRequest.getAdditionalNeeds()));
    }

    @Category(BOOKING)
    @Test(description = "Verify that a new booking request cannot be raised without the required fields")
    public void testCreateBookingWithoutRequiredFields() {
        new RestClient(RESTFUL_BOOKER_BASE_URL, BOOKING_SERVICE_ENDPOINT, Collections.emptyMap(),
                true, createBookingRequestHeader, EMPTY)
                .sendRequest(POST)
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }
}
