/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package location;

import com.fasterxml.jackson.annotation.JsonGetter;
import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class GeoLocation implements Serializable {

  /*
   * TODO: CRIO_TASK_MODULE_SERIALIZATION.1- Populate GeoLocation class.
   * Complete this class such that it can be used to deserialize the following JSON.
   * Note that both latitude and longitude are Double.
   * {
   *   "latitude": 12.908486,
   *   "longitude": 77.536386
   * }
   */
  @NotNull
  private Double latitude;
  @NotNull
  private Double longitude;

  public GeoLocation(Double v, Double v1) {
    this.latitude = v;
    this.longitude = v1;
  }

  public GeoLocation() {
  }

  @JsonGetter("longitude")
  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  @JsonGetter("latitude")
  //@NotNull
  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }
}
