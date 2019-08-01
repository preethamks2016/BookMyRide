/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package scheduletrip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

import car.Car;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import interfaces.WriteDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import trip.ScheduleTripTransactionInput;
import trip.ScheduleTripTransactionResult;
import trip.Trip;
import utils.TestUtils;

// Scope: Tests ScheduleTripServiceImpl.scheduleTrip() function.
// TODO: CRIO_TEST_MODULE_SCHEDULETRIP - Make code changes to ensure
// ScheduleTripServiceImpl.scheduleTrip() works as intended.
// Some helper code has been given as well which you can uncomment once ready.
public class ScheduleTripServiceImplTest {

  private ScheduleTripServiceImpl scheduleTripServiceImpl;

  private ScheduleTripTransactionResult generateScheduleTripTransaction(
      ScheduleTripTransactionInput scheduleTripTransactionInput) {
    ScheduleTripTransactionResult scheduleTripTransactionResult =
        new ScheduleTripTransactionResult();
    scheduleTripTransactionResult.setScheduleTripTransactionStatus(
        ScheduleTripTransactionResult.ScheduleTripTransactionStatus.SUCCESSFULLY_BOOKED);
    // We use the first digit of the trip price to generate response just for testing.
    int number =
        Integer.parseInt(
            scheduleTripTransactionInput.getTripPrice().toString().substring(0, 1));
    Trip trip = new Trip();
    trip.setTripId("TRIP_ID_" + number);
    trip.setCarId("CAR_ID_" + number);
    trip.setDriverId("DRIVER_ID_" + number);
    trip.setSourceLocation(scheduleTripTransactionInput.getSourceLocation());
    trip.setDestinationLocation(scheduleTripTransactionInput.getDestinationLocation());
    trip.setStartTimeInEpochs(1512461508L);
    trip.setTripPrice(scheduleTripTransactionInput.getTripPrice());
    trip.setTripStatus(Trip.TripStatus.TRIP_STATUS_SCHEDULED);
    scheduleTripTransactionResult.setTrip(trip);

    return scheduleTripTransactionResult;
  }

  @BeforeEach
  public void setUp() throws Exception {
    WriteDataService writeDataServiceMock = Mockito.mock(WriteDataService.class);
    // Feel free to use WriteDataService.class, withSettings().verboseLogging() if you need verbose
    // logging.

    Answer<ScheduleTripTransactionResult> answer =
        new Answer<ScheduleTripTransactionResult>() {
          public ScheduleTripTransactionResult answer(InvocationOnMock invocation)
              throws Throwable {
            ScheduleTripTransactionInput scheduleTripTransactionInput =
                invocation.getArgument(0);
            return generateScheduleTripTransaction(scheduleTripTransactionInput);
          }
        };
    when(writeDataServiceMock.scheduleTrip(isA(ScheduleTripTransactionInput.class)))
        .thenAnswer(answer);
    scheduleTripServiceImpl = new ScheduleTripServiceImpl(writeDataServiceMock);
  }

  @Test
  @Tag("Level1")
  @Tag("ScheduleTripApi")
  @Tag("QRIDE_ME_MODULE_SCHEDULETRIP")
  public void scheduleTripRegularCase() throws Exception {
    ScheduleTripRequest scheduleTripRequest =
        new ScheduleTripRequest();
    scheduleTripRequest.setCarType(Car.CarType.CAR_TYPE_HATCHBACK);
    scheduleTripRequest.setTripPrice(92.12F);
    scheduleTripRequest.setSourceLocation(TestUtils.createGeoLocationObject(10.62, 150.75));
    scheduleTripRequest.setDestinationLocation(
        TestUtils.createGeoLocationObject(15.62, 198.75));

    ScheduleTripResponse scheduleTripResponse =
        scheduleTripServiceImpl.scheduleTrip(scheduleTripRequest);

    // TODO CRIO_TEST_MODULE_SCHEDULETRIP : Assert the ScheduleTripResponse With our logical Output
    // Compare ScheduleTripResponse based on what we would expect
    // based on logic in generateScheduleTripTransaction().
    System.out.println(
        "ScheduleTripServiceImplTest:scheduleTripRegularCase "
            + "You need to make changes to the following code to test your class");
    String expectedJsonString = new String();
    ScheduleTripTransactionInput scheduleTripTransactionInput = new ScheduleTripTransactionInput(
        scheduleTripRequest.getCarType(), scheduleTripRequest.getTripPrice(),
        scheduleTripRequest.getSourceLocation(), scheduleTripRequest.getDestinationLocation());
    ScheduleTripTransactionResult scheduleTripTransactionResult = generateScheduleTripTransaction(
        scheduleTripTransactionInput);
    ScheduleTripResponse expectedResponse =
        new ScheduleTripResponse();
    expectedResponse
        .setScheduleTripStatus(scheduleTripTransactionResult.getScheduleTripTransactionStatus());
    expectedResponse.setTrip(scheduleTripTransactionResult.getTrip());
    String responseJsonString = new String();
    try {
      responseJsonString = new ObjectMapper().writeValueAsString(scheduleTripResponse);
      expectedJsonString = new ObjectMapper().writeValueAsString(expectedResponse);

    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    assertEquals(expectedJsonString, responseJsonString);

    System.out.println(responseJsonString);
  }
}
