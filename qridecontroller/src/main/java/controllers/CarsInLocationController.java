/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package controllers;

import carsinlocation.CarsInLocationRequest;
import carsinlocation.CarsInLocationResponse;
import carsinlocation.CarsInLocationServiceImpl;
import interfaces.CarsInLocationService;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import read.ReadDataServiceImpl;

/*
 * TODO: CRIO_TASK_MODULE_RESTAPI: - Implement CarsInLocation REST API.
 * URL: /qride/v1/cars_in_location
 * Description: Given a geolocation, return list of cars available for ride in that location.
 * Method: POST
 * Data Params: User's current location.
 * {
 *  "geoLocation": {
 *    "latitude": 12.908486,
 *    "longitude": 77.536386
 *  }
 * }
 *
 * Success Output:
 * HTTP Code: 200
 * Content:
 * 1. Array of car statuses if there are cars available near the user location.
 * 2. Empty array, if there are no available cars.
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
 *
 * Error Response:
 * HTTP Code: 4xx, if client side error.
 *          : 5xx, if server side error.
 */
@RestController
public class CarsInLocationController {

  private CarsInLocationService carsInLocationService;

  CarsInLocationController() {
    carsInLocationService = new CarsInLocationServiceImpl(new ReadDataServiceImpl());
  }

  @RequestMapping(value = "/qride/v1/cars_in_location")
  public ResponseEntity<CarsInLocationResponse> carsInLocation(
      @Valid @NotNull @RequestBody final CarsInLocationRequest carsInLocationRequest) {

    return ResponseEntity.status(HttpStatus.OK)
        .body(carsInLocationService.getCarsInLocation(carsInLocationRequest));
  }

  public void setCarsInLocationService(CarsInLocationService carsInLocationService) {
    this.carsInLocationService = carsInLocationService;
  }
}
