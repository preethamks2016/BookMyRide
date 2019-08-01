/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package tables;

import car.Car;
import car.CarStatus;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Version;
import location.GeoLocation;

// Details of CarStatusTable.
@Entity
//@NamedQuery(name="",query="")
public class CarStatusTable {

  @Id
  private String carId;

  @Version
  private int version;

  @Enumerated(EnumType.ORDINAL)
  private Car.CarType carType;

  @Enumerated(EnumType.ORDINAL)
  private CarStatus.CarAvailability carAvailability;

  @Embedded
  private GeoLocation geoLocation;

  public String getCarId() {
    return carId;
  }

  public void setCarId(String carId) {
    this.carId = carId;
  }

  public Car.CarType getCarType() {
    return carType;
  }

  public void setCarType(Car.CarType carType) {
    this.carType = carType;
  }

  public CarStatus.CarAvailability getCarAvailability() {
    return carAvailability;
  }

  public void setCarAvailability(CarStatus.CarAvailability carAvailability) {
    this.carAvailability = carAvailability;
  }

  public GeoLocation getGeoLocation() {
    return geoLocation;
  }

  public void setGeoLocation(GeoLocation geoLocation) {
    this.geoLocation = geoLocation;
  }
}
