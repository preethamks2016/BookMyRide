/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package carsinlocation;

import car.CarStatus;
import car.CarStatus.CarAvailability;
import globals.GlobalConstants;
import interfaces.CarsInLocationService;
import interfaces.ReadDataService;
import java.util.ArrayList;
import java.util.List;

public class CarsInLocationServiceImpl implements CarsInLocationService {

  // Used to read data from storage/cache.
  private ReadDataService readDataService;

  public CarsInLocationServiceImpl(ReadDataService readDataServiceImpl) {
    readDataService = readDataServiceImpl;
  }

  @Override
  public CarsInLocationResponse getCarsInLocation(CarsInLocationRequest carsInLocationRequest) {
    /*
     * TODO: CRIO_TASK_MODULE_RESTAPI.5: Implement interface getCarsInLocation().
     * Implement interface CarsInLocationService.getCarsInLocation().
     * Input:
     *   CarsInLocationRequest - User's current location.
     *                         - Is expected to be a valid value.
     *
     * Output: CarsInLocationResponse contains:
     *   1. Array of car statuses if there are cars available near the user location.
     *      - If the number of cars available near the user is < 10,
     *        then fetch cars close to completion within 1 km radius and return it back to users.
     *   2. Empty array, if there are no available cars.
     */

    // TIP:MODULE_RESTAPI: Precondition check is a powerful tool in maintaining sane code.
    // Apart from communicating to other developers about the assumptions you have made in the
    // function, it also catches defects right when they occur.
    // Useful Link #1: http://www.oracle.com/us/technologies/java/assertions-139853.html
    // Useful Link #2: http://web.mit.edu/6.005/www/fa15/classes/08-avoiding-debugging/
    // You might also want to check if valid lat/long are passed in.
    final CarsInLocationResponse carsInLocationResponse = new CarsInLocationResponse();

    assert (carsInLocationRequest != null);
    List<CarStatus> carStatuses;
    carStatuses = readDataService.getCarsInLocation(carsInLocationRequest.getGeoLocation(),
        CarStatus.CarAvailability.CAR_AVAILABLE,
        GlobalConstants.SEARCH_RADIUS_CARS_AVAILABLE_IN_KM);

    if (carStatuses == null) {
      carStatuses = new ArrayList<>();
    }

    //fetch cars close to competion
    List<CarStatus> carStatuses1;
    if (carStatuses.size() < 10) {
      carStatuses1 = readDataService.getCarsInLocation(carsInLocationRequest.getGeoLocation(),
          CarAvailability.CAR_ON_TRIP_CLOSE_TO_COMPLETION,
          GlobalConstants.SEARCH_RADIUS_CARS_CLOSE_TO_COMPLETION_IN_KM);
      if (carStatuses1 == null) {
        carStatuses1 = new ArrayList<>();
      }
      // Appending the two lists
      carStatuses.addAll(carStatuses1);
    }

    carsInLocationResponse.setCarStatuses(carStatuses);

    return carsInLocationResponse;
  }

  public void setReadDataService(ReadDataService readDataService) {
    this.readDataService = readDataService;
  }
}
