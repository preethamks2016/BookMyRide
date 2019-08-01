/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package interfaces.dummy;

import car.Car;
import car.CarStatus;
import interfaces.ReadDataService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import location.GeoLocation;

// TIP:MODULE_SCHEDULETRIP: A bunch of dummy functions to test your REST API handler side
// of logic before implementing the SQL layer.
// Once you have implemented the SQL layer, you don't need this anymore.
public class ReadDataServiceDummyImpl implements ReadDataService {

  @Override
  // TIP:MODULE_RESTAPI: You can use the following dummy function to test your REST API
  // handler side of logic without having to implement the SQL layer in this module.
  // The following function generates a list of CarStatus objects randomly near any given lat/long.
  public List<CarStatus> getCarsInLocation(GeoLocation geoLocation,
      CarStatus.CarAvailability carAvailability, double searchRadiusInKm) {
    assert (geoLocation != null);

    List<CarStatus> carStatusList = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      CarStatus carStatus = new CarStatus();
      GeoLocation carGeoLocation = new GeoLocation();
      // Random latitude/longitude within a certain range.
      carGeoLocation.setLatitude(
          geoLocation.getLatitude() + ThreadLocalRandom.current().nextDouble(0.000001, 0.2));
      carGeoLocation.setLongitude(
          geoLocation.getLongitude() + ThreadLocalRandom.current().nextDouble(0.000001, 0.2));
      carStatus.setGeoLocation(carGeoLocation);
      carStatus.setCarAvailability(carAvailability);
      carStatusList.add(carStatus);
    }
    return carStatusList;
  }

  @Override
  public List<CarStatus> getCarsInLocationOfCarType(GeoLocation geoLocation, Car.CarType carType) {
    System.out.println("ReadDataServiceSt5ubImpl:getCarsInLocationOfCarType not implemented");
    assert (false);
    return null;
  }
}
