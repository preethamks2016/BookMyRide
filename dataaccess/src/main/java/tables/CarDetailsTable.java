/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package tables;

import car.Car;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

// Details of CarDetails table.
@Entity
public class CarDetailsTable {

  @Id
  private String carId;

  @Enumerated(EnumType.ORDINAL)
  private Car.CarType carType;

  @NotNull
  private String driverId;

  private String carModel;

  private String carLicense;

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

  public String getDriverId() {
    return driverId;
  }

  public void setDriverId(String driverId) {
    this.driverId = driverId;
  }

  public String getCarModel() {
    return carModel;
  }

  public void setCarModel(String carModel) {
    this.carModel = carModel;
  }

  public String getCarLicense() {
    return carLicense;
  }

  public void setCarLicense(String carLicense) {
    this.carLicense = carLicense;
  }
}
