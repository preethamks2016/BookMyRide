/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package carsinlocation;

import car.CarStatus;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.ArrayList;
import java.util.List;

public class CarsInLocationResponse {
  /*
   * TODO: CRIO_TASK_MODULE_RESTAPI.4: Populate CarsInLocationResponse class.
   * Complete the class so that when Jackson serialization is used,
   * the following JSON response gets generated.
   * Output: CarsInLocationResponse - Array of carStatus
   * {
   *     "carStatuses": [{
   *         "carStatus": {
   *             "geoLocation": {
   *                 "latitude": 0.0036492730023700215,
   *                 "longitude": 0.04278965249297461
   *             },
   *             "carAvailability": "CAR_AVAILABLE"
   *         }
   *     }, {
   *         "carStatus": {
   *             "geoLocation": {
   *                 "latitude": 0.13222748716934793,
   *                 "longitude": 0.033663367718575685
   *             },
   *             "carAvailability": "CAR_AVAILABLE"
   *         }
   *     }]
   * }
   */

  private List<CarStatus> carStatuses = new ArrayList<>();

  @JsonGetter("carStatuses")
  @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
  public List<CarStatus> getCarStatuses() {
    if (this.carStatuses == null) {
      this.carStatuses = new ArrayList<>();
    }
    return this.carStatuses;
  }

  @JsonSetter("carStatuses")
  public void setCarStatuses(List<CarStatus> carStatuses) {
    this.carStatuses = carStatuses;
  }

}
