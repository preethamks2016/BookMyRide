/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package trip;

import car.Car;
import location.GeoLocation;

public class ScheduleTripTransactionInput {

  private Car.CarType carType;
  private Float tripPrice;
  private GeoLocation sourceLocation;
  private GeoLocation destinationLocation;

  public ScheduleTripTransactionInput(
      Car.CarType carType,
      Float tripPrice,
      GeoLocation sourceLocation,
      GeoLocation destinationLocation) {
    this.carType = carType;
    this.tripPrice = tripPrice;
    this.sourceLocation = sourceLocation;
    this.destinationLocation = destinationLocation;
  }

  public Car.CarType getCarType() {
    return carType;
  }

  public void setCarType(Car.CarType carType) {
    this.carType = carType;
  }

  public Float getTripPrice() {
    return tripPrice;
  }

  public void setTripPrice(Float tripPrice) {
    this.tripPrice = tripPrice;
  }

  public GeoLocation getSourceLocation() {
    return sourceLocation;
  }

  public void setSourceLocation(GeoLocation sourceLocation) {
    this.sourceLocation = sourceLocation;
  }

  public GeoLocation getDestinationLocation() {
    return destinationLocation;
  }

  public void setDestinationLocation(GeoLocation destinationLocation) {
    this.destinationLocation = destinationLocation;
  }
}
