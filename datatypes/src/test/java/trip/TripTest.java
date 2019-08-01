/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package trip;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import utils.TestUtils;

// TODO: CRIO_TEST_MODULE_SCHEDULETRIP..1 - Uncomment the tests once you are
// ready for testing your Trip class.
public class TripTest {

  @Test
  @Tag("Level1")
  @Tag("Trip")
  @Tag("QRIDE_ME_MODULE_SCHEDULETRIP")
  public void testJsonGeneration() {
    final String expectedJsonString =
        "{"
            + "\"tripId\":\"TRIP_ID_1\","
            + "\"carId\":\"CAR_ID_1\","
            + "\"driverId\":\"DRIVER_ID_1\","
            + "\"sourceLocation\":{"
            + "\"latitude\":12.908486,"
            + "\"longitude\":75.536386"
            + "},"
            + "\"destinationLocation\":{"
            + "\"latitude\":12.008486,"
            + "\"longitude\":78.536386"
            + "},"
            + "\"startTimeInEpochs\":1512152782,"
            + "\"endTimeInEpochs\":1512170782,"
            + "\"tripPrice\":129.00,"
            + "\"tripStatus\":\"TRIP_STATUS_COMPLETED\","
            + "\"paymentMode\":\"PAYTM_PAYMENT\""
            + "}";

    Trip trip = new Trip();
    trip.setTripId("TRIP_ID_1");
    trip.setCarId("CAR_ID_1");
    trip.setDriverId("DRIVER_ID_1");
    trip.setSourceLocation(TestUtils.createGeoLocationObject(12.908486, 75.536386));
    trip.setDestinationLocation(TestUtils.createGeoLocationObject(12.008486, 78.536386));
    trip.setStartTimeInEpochs(1512152782L);
    trip.setEndTimeInEpochs(1512170782L);
    trip.setTripPrice(129.00F);
    trip.setTripStatus(Trip.TripStatus.TRIP_STATUS_COMPLETED);
    trip.setPaymentMode(Trip.PaymentMode.PAYTM_PAYMENT);

    String actualJsonString = new String();
    try {
      actualJsonString = new ObjectMapper().writeValueAsString(trip);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    try {
      JSONAssert.assertEquals(expectedJsonString, actualJsonString, true);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  @Test
  @Tag("Level1")
  @Tag("Trip")
  @Tag("QRIDE_ME_MODULE_SCHEDULETRIP")
  public void testMissingFieldJsonGeneration() {
    //    // Leaving out endTimeInEpochs & paymentMode.
    final String expectedJsonString =
        "{"
            + "\"tripId\":\"TRIP_ID_1\","
            + "\"carId\":\"CAR_ID_1\","
            + "\"driverId\":\"DRIVER_ID_1\","
            + "\"sourceLocation\":{"
            + "\"latitude\":12.908486,"
            + "\"longitude\":75.536386"
            + "},"
            + "\"destinationLocation\":{"
            + "\"latitude\":12.008486,"
            + "\"longitude\":78.536386"
            + "},"
            + "\"startTimeInEpochs\":1512152782,"
            + "\"tripPrice\":129.00,"
            + "\"tripStatus\":\"TRIP_STATUS_COMPLETED\""
            + "}";

    Trip trip = new Trip();
    trip.setTripId("TRIP_ID_1");
    trip.setCarId("CAR_ID_1");
    trip.setDriverId("DRIVER_ID_1");
    trip.setSourceLocation(TestUtils.createGeoLocationObject(12.908486, 75.536386));
    trip.setDestinationLocation(TestUtils.createGeoLocationObject(12.008486, 78.536386));
    trip.setStartTimeInEpochs(1512152782L);
    trip.setTripPrice(129.00F);
    trip.setTripStatus(Trip.TripStatus.TRIP_STATUS_COMPLETED);

    String actualJsonString = new String();
    try {
      actualJsonString = new ObjectMapper().writeValueAsString(trip);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    System.out.println("ActualJsonString: " + actualJsonString);
    System.out.println("ExpectedJsonString: " + expectedJsonString);
    try {
      JSONAssert.assertEquals(expectedJsonString, actualJsonString, true);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }
}
