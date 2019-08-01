/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package read;

import car.Car;
import car.CarStatus;
import interfaces.ReadDataService;
import java.util.List;
import location.GeoLocation;

// TODO: CRIO_TASK_MODULE_SCHEDULETRIP: Make sure all your API handlers call this
// implementation instead of ReadDataServiceDummyImpl.
// Class contains the real implementation of ReadDataServices reading data
// from Datastore (SQL/Cache).
public class ReadDataServiceImpl implements ReadDataService {

  // TODO: CRIO_TASK_MODULE_SQL.3: Ensure CarsInLocationServiceImpl class starts
  // using this implementation instead of ReadDataServiceDummyImpl.
  @Override
  public List<CarStatus> getCarsInLocation(GeoLocation geoLocation,
      CarStatus.CarAvailability carAvailability, double searchRadiusInKm) {
    assert (geoLocation != null);
    assert (geoLocation.getLongitude() != null);
    assert (geoLocation.getLatitude() != null);

    GetCarsInLocationFromDataStore getCarsInLocationFromDataStore =
        new GetCarsInLocationFromDataStore();
    return getCarsInLocationFromDataStore.getCarsInLocation(geoLocation, carAvailability,
        searchRadiusInKm);
  }

  @Override
  public List<CarStatus> getCarsInLocationOfCarType(GeoLocation geoLocation, Car.CarType carType) {
    GetCarsInLocationFromDataStore getCarsInLocationFromDataStore =
        new GetCarsInLocationFromDataStore();
    return getCarsInLocationFromDataStore.getCarsInLocationOfCarType(geoLocation, carType);
  }
}
