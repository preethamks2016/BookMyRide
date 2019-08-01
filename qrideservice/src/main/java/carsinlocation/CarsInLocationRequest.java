/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package carsinlocation;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import location.GeoLocation;

public class CarsInLocationRequest {

  @NotNull
  @Valid
  private GeoLocation geoLocation;

  @JsonGetter("geoLocation")
  public GeoLocation getGeoLocation() {
    return geoLocation;
  }
  /*
   * TODO: CRIO_TASK_MODULE_RESTAPI.3:  Complete the class such that it can be used to deserialize
   * the following JSON request.
   * {
   *   "geoLocation": {
   *     "latitude": 12.908486,
   *     "longitude": 77.536386
   *  }
   * }
   */

  //
  //  try {
  //    geoLocation = new ObjectMapper().readValue(jsonToDeserialize, GeoLocation.class);
  //  } catch (
  //  IOException e) {
  //    e.printStackTrace();
  //  }

  @JsonSetter("geoLocation")
  public void setGeoLocation(GeoLocation geoLocation) {
    this.geoLocation = geoLocation;
  }
}
