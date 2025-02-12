package com.restassured.service.app;

import com.restassured.helper.Helper;
import com.restassured.util.RestClient;

import static com.restassured.HttpMethod.GET;
import static com.restassured.constant.ApplicationConstant.*;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.http.HttpStatus.SC_OK;

public class BookingService {
    public static String getBookingIdFromBookingDb() {
        return new RestClient(RESTFUL_BOOKER_BASE_URL, BOOKING_SERVICE_ENDPOINT, CONTENT_TYPE, EMPTY)
                .sendRequest(GET)
                .statusCode(SC_OK)
                .extract()
                .body()
                .jsonPath()
                .get(String.format("[%d].bookingid", Helper.getRandomNumber(0, 20)))
                .toString();
    }
}
