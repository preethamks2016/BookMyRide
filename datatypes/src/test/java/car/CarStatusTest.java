/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package car;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import location.GeoLocation;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class CarStatusTest {

  @Test
  @Tag("Level1")
  @Tag("QRIDE_ME_MODULE_SERIALIZATION")
  public void testJsonGeneration() {
    // TODO: CRIO_TEST_MODULE_SERIALIZATION.3 - Implement CarStatus class.
    // Uncomment the following code once you are ready for
    // testing CarStatus class.
    final String expectedJsonString =
        "{\"geoLocation\":{\"latitude\":12.908486,\"longitude\":77.536386},"
            + "\"carAvailability\":\"CAR_AVAILABLE\"}";

    // Setting up an CarStatus object for testing.
    CarStatus carStatus = new CarStatus();
    carStatus.setCarAvailability(CarStatus.CarAvailability.CAR_AVAILABLE);
    GeoLocation geoLocation = new GeoLocation();
    geoLocation.setLatitude(12.908486);
    geoLocation.setLongitude(77.536386);
    carStatus.setGeoLocation(geoLocation);

    String actualJsonString = "";

    try {
      actualJsonString = new ObjectMapper().writeValueAsString(carStatus);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    assertEquals(expectedJsonString, actualJsonString,
        "Validate if the generated json is correct");
  }
}
