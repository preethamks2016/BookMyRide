/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package scheduletrip;

import car.Car.CarType;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import location.GeoLocation;

public class ScheduleTripRequest {

  @Valid
  @NotNull
  CarType carType;
  @Valid
  @NotNull
  Float tripPrice;
  @Valid
  @NotNull
  GeoLocation sourceLocation;
  @Valid
  @NotNull
  GeoLocation destinationLocation;

  @JsonGetter("carType")
  public CarType getCarType() {
    return carType;
  }

  @JsonSetter("carType")
  public void setCarType(CarType carTypeHatchback) {
    this.carType = carTypeHatchback;
  }

  @JsonGetter("tripPrice")
  public Float getTripPrice() {
    return tripPrice;
  }

  @JsonSetter("tripPrice")
  public void setTripPrice(Float v) {
    this.tripPrice = v;
  }

  @JsonGetter("sourceLocation")
  public GeoLocation getSourceLocation() {
    return sourceLocation;
  }

  @JsonSetter("sourceLocation")
  public void setSourceLocation(GeoLocation geoLocationObject) {
    this.sourceLocation = geoLocationObject;
  }

  @JsonGetter("destinationLocation")
  public GeoLocation getDestinationLocation() {
    return destinationLocation;
  }

  @JsonSetter("destinationLocation")
  public void setDestinationLocation(GeoLocation geoLocationObject) {
    this.destinationLocation = geoLocationObject;
  }
  // TODO: CRIO_TASK_MODULE_SCHEDULETRIP.2: Populate ScheduleTripRequest class.
  // Complete the class such that it can be used to deserialize
  // the following JSON request.
  // {
  //  "carType": "CAR_TYPE_HATCHBACK",
  //  "tripPrice": 129.00,
  //  "sourceLocation": {
  //    "latitude": 12.908486,
  //    "longitude": 77.536386
  //  },
  //  "destinationLocation": {
  //    "latitude": 12.908486,
  //    "longitude": 77.536386
  //  }
  // }

}
