/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package carsinlocation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import car.CarStatus;
import car.CarStatus.CarAvailability;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import globals.GlobalConstants;
import interfaces.CarsInLocationService;
import interfaces.ReadDataService;
import java.util.ArrayList;
import java.util.List;
import location.GeoLocation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import utils.TestUtils;


// Scope: Tests CarsInLocationServiceImpl.getCarsInLocation() function.
public class CarsInLocationServiceImplTest {

  private CarsInLocationService carsInLocationServiceImpl;

  // Utility function to generate a bunch of cars based on the geoLocation passed to help testing.
  private List<CarStatus> generateCarStatus(
      GeoLocation geoLocation, CarStatus.CarAvailability carAvailability, int numberOfCars) {
    List<CarStatus> carStatusList = new ArrayList<>();
    for (int i = 0; i < numberOfCars; i++) {
      CarStatus carStatus = new CarStatus();

      carStatus.setGeoLocation(
          TestUtils.createGeoLocationObject(
              geoLocation.getLatitude() + i, geoLocation.getLongitude() + i));
      carStatus.setCarAvailability(carAvailability);
      carStatusList.add(carStatus);
    }
    return carStatusList;
  }

  @BeforeEach
  public void setUp() throws Exception {
  }

  @AfterEach
  public void tearDown() throws Exception {
  }

  // TODO: CRIO_TEST_MODULE_RESTAPI.1 - Make code changes to ensure
  // CarsInLocationServiceImpl.getCarsInLocation() works as intended.
  // Some helper code has been given as well which you can uncomment once ready.
  //
  // TIP:MODULE_RESTAPI: Try noting down what you are testing
  // similar to the following template. Following is just an EXAMPLE.
  // Code Under Test: CarsInLocationHandlerImpl.getCarsInLocation()
  // Test case: Check if CarsInLocationHandlerImpl.getCarsInLocation() is doing the right thing,
  //            even if dependency layer fails to keep up with its contract and returns null.
  // Dependency Layer/Function: ReadDataHandler.getCarsInLocation()
  // Mock Setup:
  //   - Function to mock: ReadDataHandler.getCarsInLocation()
  //   - Input to mock: GeoLocation(50, 100)
  //   - Pre-set output from mock: <xyz>
  // Test Input: CarsInLocationRequest.GeoLocation(50, 100)
  // Expected Test Output: Returned CarsInLocationResponse.carStatuses is not null,
  //                       but an empty array.
  @Test
  @Tag("Level1")
  @Tag("CarsInLocationApi")
  @Tag("QRIDE_ME_MODULE_RESTAPI")
  public void getCarsInLocationRegularCase() throws Exception {
    // Create a mock readDataService that would return back a bunch of available cars using
    // generateCarStatus() utility function.
    ReadDataService readDataServiceMock = Mockito.mock(ReadDataService.class);
    GeoLocation geoLocation = TestUtils.createGeoLocationObject(50.0, 100.0);
    Mockito.when(readDataServiceMock.getCarsInLocation(
        geoLocation, CarStatus.CarAvailability.CAR_AVAILABLE,
        GlobalConstants.SEARCH_RADIUS_CARS_AVAILABLE_IN_KM))
        .thenReturn(generateCarStatus(geoLocation, CarStatus.CarAvailability.CAR_AVAILABLE, 10));
    carsInLocationServiceImpl = new CarsInLocationServiceImpl(readDataServiceMock);

    System.out.println(
        "CarsInLocationServiceImplTest:getCarsInLocationRegularCase "
            + "You need to make changes to the following code to test your class");

    CarsInLocationRequest carsInLocationRequest = new CarsInLocationRequest();
    carsInLocationRequest.setGeoLocation(geoLocation);
    CarsInLocationResponse carsInLocationResponse =
        carsInLocationServiceImpl.getCarsInLocation(carsInLocationRequest);

    // Compare CarsInLocationResponse based on what we would expect based on logic in
    // generateCarStatus().
    List<CarStatus> expectedCarStatusList =
        generateCarStatus(geoLocation, CarAvailability.CAR_AVAILABLE, 10);

    String actualResponseJsonString = "";
    try {
      actualResponseJsonString = new ObjectMapper().writeValueAsString(carsInLocationResponse);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    System.out.println(actualResponseJsonString);

    CarsInLocationResponse expectedCarsInLocationResponse = new CarsInLocationResponse();
    expectedCarsInLocationResponse.setCarStatuses(expectedCarStatusList);
    //json serializer
    String expectedCarStatusListString = "";
    try {
      expectedCarStatusListString = new ObjectMapper()
          .writeValueAsString(expectedCarsInLocationResponse);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    assertEquals(actualResponseJsonString, expectedCarStatusListString,
        "Validate if the generated cars json is correct");

  }

  // TODO: CRIO_TEST_MODULE_RESTAPI.2 - Test if CarsInLocationServiceImpl.getCarsInLocation().
  // can handle when readDataService.getCarsInLocation() returns null due to a bug.
  // Some helper code has been given as well which you can uncomment once ready.
  @Test
  @Tag("Level1")
  @Tag("CarsInLocationApi")
  @Tag("QRIDE_ME_MODULE_RESTAPI")
  public void getCarsInLocationEmptyResponse() throws Exception {
    ReadDataService readDataServiceMock = Mockito.mock(ReadDataService.class);
    GeoLocation geoLocation = TestUtils.createGeoLocationObject(50.0, 100.0);
    Mockito.when(readDataServiceMock.getCarsInLocation(geoLocation,

        CarStatus.CarAvailability.CAR_AVAILABLE,
        GlobalConstants.SEARCH_RADIUS_CARS_AVAILABLE_IN_KM)).thenReturn(null);
    carsInLocationServiceImpl = new CarsInLocationServiceImpl(readDataServiceMock);
    System.out.println(
        "CarsInLocationServiceImplTest:getCarsInLocationEmptyResponse "
            + "You need to make changes to the following code to test your class");

    CarsInLocationRequest carsInLocationRequest = new CarsInLocationRequest();
    carsInLocationRequest.setGeoLocation(geoLocation);
    CarsInLocationResponse carsInLocationResponse =
        carsInLocationServiceImpl.getCarsInLocation(carsInLocationRequest);

    assertNotNull(
        carsInLocationResponse.getCarStatuses(), "CarsInLocationResponse must not be empty");
    assertEquals(0, carsInLocationResponse.getCarStatuses().size());
  }
}
