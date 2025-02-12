package com.restassured.test.bookingtest;


import com.restassured.Category;
import com.restassured.test.BaseTest;
import com.restassured.util.RestClient;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;

import java.util.Collections;

import static com.restassured.HttpMethod.GET;
import static com.restassured.constant.ApplicationConstant.BOOKING_SERVICE_ENDPOINT;
import static com.restassured.constant.ApplicationConstant.RESTFUL_BOOKER_BASE_URL;
import static com.restassured.service.app.BookingService.getBookingIdFromBookingDb;
import static com.restassured.test.constant.TestCategory.BOOKING;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.http.HttpStatus.SC_OK;
import static org.testng.Assert.assertTrue;


public class GetBookingInfoTest extends BaseTest {
    private String getBookingInfoByIDEndPoint;

    @Category(BOOKING)
    @Test(description = "Verify that a user can get the booking ids")
    public void testGetAllBookings() {
        ValidatableResponse response = new RestClient(RESTFUL_BOOKER_BASE_URL, BOOKING_SERVICE_ENDPOINT,
                Collections.emptyMap(), false, Collections.emptyMap(), EMPTY)
                .sendRequest(GET)
                .statusCode(SC_OK);

        assertTrue(response.extract().body().jsonPath().getList("$").size() > 1);
    }

    @Category(BOOKING)
    @Test(description = "Verify that a user can get the booking info by id")
    public void testGetBookingInfoByID() {
        getBookingInfoByIDEndPoint = BOOKING_SERVICE_ENDPOINT.concat("/").concat(getBookingIdFromBookingDb());

        new RestClient(RESTFUL_BOOKER_BASE_URL, getBookingInfoByIDEndPoint, Collections.emptyMap(),
                false, Collections.emptyMap(), EMPTY)
                .sendRequest(GET)
                .statusCode(SC_OK);
    }
}
