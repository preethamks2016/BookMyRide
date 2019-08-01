/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package controllers;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import car.Car;
import interfaces.ScheduleTripService;
import interfaces.WriteDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import scheduletrip.ScheduleTripServiceImpl;
import trip.ScheduleTripTransactionInput;
import trip.ScheduleTripTransactionResult;
import trip.Trip;

// Scope: Tests /qride/v1/schedule_trip REST API with valid & invalid inputs.
// TODO: CRIO_TEST_MODULE_SCHEDULETRIP - Write test cases to test REST API's
// Add/modify necessary tests to ensure the REST API is tested well.
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Application.class})
@AutoConfigureMockMvc
public class ScheduleTripControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Mock
  private WriteDataService writeDataService;

  @InjectMocks
  private ScheduleTripController scheduleTripController;

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
    trip.setTripPrice(129.00F);
    trip.setTripStatus(Trip.TripStatus.TRIP_STATUS_SCHEDULED);
    scheduleTripTransactionResult.setTrip(trip);

    return scheduleTripTransactionResult;
  }

  private ScheduleTripTransactionResult generateFailedScheduleTripTransaction() {
    ScheduleTripTransactionResult scheduleTripTransactionResult =
        new ScheduleTripTransactionResult();
    scheduleTripTransactionResult.setScheduleTripTransactionStatus(
        ScheduleTripTransactionResult.ScheduleTripTransactionStatus.NO_CARS_AVAILABLE);
    return scheduleTripTransactionResult;
  }

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    Answer<ScheduleTripTransactionResult> answer =
        new Answer<ScheduleTripTransactionResult>() {
          public ScheduleTripTransactionResult answer(InvocationOnMock invocation)
              throws Throwable {
            ScheduleTripTransactionInput scheduleTripTransactionInput =
                invocation.getArgument(0);

            if (scheduleTripTransactionInput.getCarType() == Car.CarType.CAR_TYPE_HATCHBACK) {
              // If it is HATCHBACK, then we return a proper response.
              return generateScheduleTripTransaction(scheduleTripTransactionInput);
            } else {
              // If it is not HATCHBACK, then we return NO_CARS_AVAILALBLE.
              return generateFailedScheduleTripTransaction();
            }
          }
        };
    when(writeDataService.scheduleTrip(isA(ScheduleTripTransactionInput.class))).thenAnswer(answer);
    ScheduleTripService scheduleTripServiceImpl;
    scheduleTripServiceImpl = new ScheduleTripServiceImpl(writeDataService);
    scheduleTripController.setScheduleTripService(scheduleTripServiceImpl);

    mockMvc = MockMvcBuilders.standaloneSetup(scheduleTripController).build();
  }

  @Test
  @Tag("Level2")
  @Tag("ScheduleTripApi")
  @Tag("QRIDE_ME_MODULE_SCHEDULETRIP")
  public void noParamShouldReturnClientError() throws Exception {
    this.mockMvc
        .perform(get("/qride/v1/schedule_trip"))
        .andDo(print())
        .andExpect(status().is4xxClientError());
  }

  @Test
  @Tag("Level2")
  @Tag("ScheduleTripApi")
  @Tag("QRIDE_ME_MODULE_SCHEDULETRIP")
  public void incorrectParamShouldReturnClientError() throws Exception {
    String requestBody = "{\"randomJsonInput\": \"test\"}";

    this.mockMvc
        .perform(
            get("/qride/v1/schedule_trip")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andDo(print())
        .andExpect(status().is4xxClientError());
  }

  @Test
  @Tag("Level2")
  @Tag("ScheduleTripApi")
  @Tag("QRIDE_ME_MODULE_SCHEDULETRIP")
  public void missingParamShouldReturnClientError() throws Exception {
    String requestBody =
        "{"
            + "\"carType\":\"CAR_TYPE_HATCHBACK\","
            + "\"sourceLocation\":{"
            + "\"latitude\":12.908486,"
            + "\"longitude\":77.536386"
            + "},"
            + "\"destinationLocation\":{"
            + "\"latitude\":12.908486,"
            + "\"longitude\":77.536386"
            + "}"
            + "}";

    this.mockMvc
        .perform(
            get("/qride/v1/schedule_trip")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andDo(print())
        .andExpect(status().is4xxClientError());
  }

  @Test
  @Tag("Level1")
  @Tag("ScheduleTripApi")
  @Tag("QRIDE_ME_MODULE_SCHEDULETRIP")
  public void normalCase() throws Exception {
    String requestBody =
        "{"
            + "\"carType\":\"CAR_TYPE_HATCHBACK\","
            + "\"tripPrice\":829.00,"
            + "\"sourceLocation\":{"
            + "\"latitude\":12.908486,"
            + "\"longitude\":77.536386"
            + "},"
            + "\"destinationLocation\":{"
            + "\"latitude\":12.108416,"
            + "\"longitude\":78.536386"
            + "}"
            + "}";

    this.mockMvc
        .perform(
            get("/qride/v1/schedule_trip")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.scheduleTripStatus").exists())
        .andExpect(
            jsonPath("$.scheduleTripStatus")
                .value(
                    ScheduleTripTransactionResult.ScheduleTripTransactionStatus.SUCCESSFULLY_BOOKED
                        .name()))
        .andExpect(jsonPath("$.trip").exists())
        .andExpect(jsonPath("$.trip.tripId").value("TRIP_ID_8"))
        .andExpect(
            jsonPath("$.trip.tripStatus").value(Trip.TripStatus
                .TRIP_STATUS_SCHEDULED.name()));
  }


  @Test
  @Tag("Level1")
  @Tag("ScheduleTripApi")
  @Tag("QRIDE_ME_MODULE_SCHEDULETRIP")
  public void properRequestShouldReturnSuccess() throws Exception {
    String requestBody = "{\"carType\": \"CAR_TYPE_HATCHBACK\",\"tripPrice\": 129.00,"
        + "\"sourceLocation\": {\"latitude\": 12.90,\"longitude\": 78.53},"
        + "\"destinationLocation\": {\"latitude\": 12.908486,\"longitude"
        + "\": 77.536386}}";

    this.mockMvc
        .perform(
            get("/qride/v1/schedule_trip")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andDo(print())
        .andExpect(status().is2xxSuccessful());
  }

}
