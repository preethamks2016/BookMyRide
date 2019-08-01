/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package location;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class GeoLocationTest {

  @Test
  @Tag("Level1")
  @Tag("QRIDE_ME_MODULE_SERIALIZATION")
  public void testJsonDeserialization() throws IOException {
    // TODO: CRIO_TEST_MODULE_SERIALIZATION.1 - Implement GeoLocationTest class.
    // Uncomment the following code once you are
    // ready for testing your GeoLocation class.

    String jsonToDeserialize = "{ \"latitude\": 12.908486, \"longitude\": 77.536386 }";
    GeoLocation geoLocationActual = null;

    try {
      geoLocationActual = new ObjectMapper().readValue(jsonToDeserialize, GeoLocation.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(12.908486, geoLocationActual.getLatitude(), 0.000001,
        "Validate the value of latitude");
    assertEquals(
        77.536386, geoLocationActual.getLongitude(), 0.000001,
        "Validate the value of latitude");
  }

  // TODO: CRIO_TEST_MODULE_SERIALIZATION.2 - Check for IOException for GeoLocation class.
  // Uncomment the following code once you are ready
  // for testing your GeoLocation class.
  @Test
  @Tag("Level2")
  @Tag("QRIDE_ME_MODULE_SERIALIZATION")
  public void testIoException() {
    String incorrectJsonToDeserialize =
        "{ \"latitude_error\": 12.908486, \"longitude\": 77.536386 }";
    Assertions.assertThrows(IOException.class, () -> {
      GeoLocation geoLocation =
          new ObjectMapper().readValue(incorrectJsonToDeserialize, GeoLocation.class);
    });
  }
}
