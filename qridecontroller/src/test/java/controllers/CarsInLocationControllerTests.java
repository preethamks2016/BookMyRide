/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import car.CarStatus;
import carsinlocation.CarsInLocationServiceImpl;
import interfaces.CarsInLocationService;
import interfaces.ReadDataService;
import java.util.ArrayList;
import java.util.List;
import location.GeoLocation;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
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
import utils.TestUtils;


// Scope: Tests /qride/v1/cars_in_location REST API with valid & invalid inputs.
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Application.class})
@AutoConfigureMockMvc
public class CarsInLocationControllerTests {

  @Autowired
  private MockMvc mockMvc;


  @InjectMocks
  private CarsInLocationController carsInLocationController;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);

    // Setup a mock class to easily run your tests.
    ReadDataService readDataServiceMock = Mockito.mock(ReadDataService.class);
    Answer<List<CarStatus>> answer =
        new Answer<List<CarStatus>>() {
          public List<CarStatus> answer(InvocationOnMock invocation) throws Throwable {
            GeoLocation geoLocation = invocation.getArgument(0);
            return generateCarStatus(geoLocation);
          }
        };
    when(readDataServiceMock.getCarsInLocation(isA(GeoLocation.class),
        isA(CarStatus.CarAvailability.class),
        isA(Double.class))).thenAnswer(answer);
    CarsInLocationService carsInLocationServiceimpl =
        new CarsInLocationServiceImpl(readDataServiceMock);
    carsInLocationController.setCarsInLocationService(carsInLocationServiceimpl);

    mockMvc = MockMvcBuilders.standaloneSetup(carsInLocationController).build();
  }

  // Utility function to generate a bunch of cars based on the geoLocation passed to help testing.
  private List<CarStatus> generateCarStatus(GeoLocation geoLocation) {
    List<CarStatus> carStatusList = new ArrayList<>();
    for (int i = 0; i < 15; i++) {
      CarStatus carStatus = new CarStatus();
      carStatus.setGeoLocation(
          TestUtils.createGeoLocationObject(
              geoLocation.getLatitude() + i, geoLocation.getLongitude() + i));
      carStatus.setCarAvailability(CarStatus.CarAvailability.CAR_AVAILABLE);
      carStatusList.add(carStatus);
    }
    return carStatusList;
  }

  @Test
  @Tag("Level2")
  @Tag("CarsInLocationApi")
  public void noParamShouldReturnClientError() throws Exception {
    this.mockMvc
        .perform(get("/qride/v1/cars_in_location"))
        .andDo(print())
        .andExpect(status().is4xxClientError());
  }

  // Note the small letter 'l' in geolocation which is the error.
  @Test
  @Tag("Level2")
  @Tag("CarsInLocationApi")
  public void incorrectGeoLocationParamShouldReturnClientError() throws Exception {
    String requestBody = "{\"geolocation\": {\"latitude\": 12.0, \"longitude\": 77.0}}";

    this.mockMvc
        .perform(
            get("/qride/v1/cars_in_location")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andDo(print())
        .andExpect(status().is4xxClientError());
  }

  @Test
  @Tag("Level2")
  @Tag("CarsInLocationApi")
  public void emptyLatitudeParamShouldReturnClientError() throws Exception {
    String requestBody = "{\"geoLocation\": {\"longitude\": 77.0}}";

    this.mockMvc
        .perform(
            get("/qride/v1/cars_in_location")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andDo(print())
        .andExpect(status().is4xxClientError());
  }

  @Test
  @Tag("Level1")
  @Tag("CarsInLocationApi")
  public void normalCase() throws Exception {
    String requestBody = "{\"geoLocation\": {\"latitude\": 12.0, \"longitude\": 77.0}}";

    this.mockMvc
        .perform(
            get("/qride/v1/cars_in_location")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.carStatuses").isArray())
        .andExpect(jsonPath("$.carStatuses", hasSize(15)))
        .andExpect(jsonPath("$.carStatuses[0].carStatus.geoLocation.latitude")
            .value(12.0))
        .andExpect(jsonPath("$.carStatuses[0].carStatus.carAvailability").value(
            Matchers.isOneOf("CAR_AVAILABLE")));
  }
}
