/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package car;

import com.fasterxml.jackson.annotation.JsonTypeName;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import location.GeoLocation;

@JsonTypeName("carStatus")
public class CarStatus {

  /*
   * TODO: CRIO_TASK_MODULE_SERIALIZATION.2- Complete this class such that it can be used to create
   * following JSON format.
   *
   * {
   *      "geoLocation": {
   *          "latitude": 32.37718599937095,
   *          "longitude": 82.71417729525433
   *      },
   *      "carAvailability" : "CAR_AVAILABLE"
   * }
   */
  @NotNull
  @Valid
  private GeoLocation geoLocation;
  private CarStatus.CarAvailability carAvailability;

  public CarAvailability getCarAvailability() {
    return carAvailability;
  }

  public void setCarAvailability(CarAvailability carAvailable) {
    carAvailability = carAvailable;
  }

  public GeoLocation getGeoLocation() {
    return geoLocation;
  }

  public void setGeoLocation(GeoLocation geoLocation) {
    this.geoLocation = geoLocation;
  }

  public enum CarAvailability {
    UNKNOWN,
    CAR_OFF_DUTY,
    CAR_AVAILABLE,
    CAR_ON_TRIP,
    CAR_ON_TRIP_CLOSE_TO_COMPLETION
  }
}
