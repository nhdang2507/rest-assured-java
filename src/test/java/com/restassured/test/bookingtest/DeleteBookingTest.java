package com.restassured.test.bookingtest;

import com.restassured.Category;
import com.restassured.test.BaseTest;
import com.restassured.util.RestClient;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.restassured.HttpMethod.DELETE;
import static com.restassured.constant.ApplicationConstant.*;
import static com.restassured.test.constant.TestCategory.BOOKING;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;

public class DeleteBookingTest extends BaseTest {
    private String deleteBookingEndPoint;
    private Map<String, String> deleteBookingRequestHeader = new HashMap<>();

    @BeforeClass
    public void setDeleteBookingRequest() {
        //Set end point
        deleteBookingEndPoint = BOOKING_SERVICE_ENDPOINT + "/2164";

        //Set header
        deleteBookingRequestHeader.put("Content-Type", CONTENT_TYPE);
        deleteBookingRequestHeader.put("Accept", CONTENT_TYPE);
    }

    @Category(BOOKING)
    @Test(description = "Verify that a user can delete a booking")
    public void testDeleteBooking() {
        new RestClient(RESTFUL_BOOKER_BASE_URL, deleteBookingEndPoint, Collections.emptyMap(),
                true, deleteBookingRequestHeader, EMPTY)
                .sendRequest(DELETE)
                .statusCode(SC_CREATED);
    }

    @Category(BOOKING)
    @Test(description = "Verify that a booking cannot be deleted without the authentication")
    public void testBookingDeletionWithoutAuthentication() {
        new RestClient(RESTFUL_BOOKER_BASE_URL, deleteBookingEndPoint, Collections.emptyMap(),
                false, Collections.emptyMap(), EMPTY)
                .sendRequest(DELETE)
                .statusCode(SC_FORBIDDEN);
    }
}
