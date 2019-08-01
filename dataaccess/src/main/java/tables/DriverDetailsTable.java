/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package tables;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

// Details of DriverDetails table.
@Entity
public class DriverDetailsTable {

  @Id
  private String driverId;

  @NotNull
  private String carId;

  private String name;
  private String phone;

  public String getDriverId() {
    return driverId;
  }

  public void setDriverId(String driverId) {
    this.driverId = driverId;
  }

  public String getCarId() {
    return carId;
  }

  public void setCarId(String carId) {
    this.carId = carId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
}
