/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package interfaces;

import carsinlocation.CarsInLocationRequest;
import carsinlocation.CarsInLocationResponse;

// TIP:MODULE_RESTAPI: (DESIGN) - Such an interface provides a clear separation between the
// higher level communication module (REST API, gRPC etc) from the underlying business logic
// implementation.
// eg: In future, if we add gRPC as an additional communication mechanism,
// the interface implementation class CarsInLocationServiceImpl would require no changes
// as long as CarsInLocationRequest is populated correctly.
public interface CarsInLocationService {

  // TIP:MODULE_RESTAPI: (DESIGN) - If CarsInLocationRequest contains only GeoLocation for
  // now, why didn't we declare the function the following way:
  // CarsInLocationResponse getCarsInLocation(GeoLocation geoLocation);
  // Imagine what would happen if we have to pass more inputs to getCarsInLocation() in future.
  // What effect would it have on clients using this interface?

  // Input:
  //   CarsInLocationRequest - User's current location.
  //                         - Is expected to be a valid value.
  // Output: CarsInLocationResponse contains:
  //   1. Array of car statuses if there are cars available near the user location.
  //       - If the number of cars available near the user is < 10,
  //         then fetch cars close to completion within 1 km radius and return it back to users.
  //   2. Empty array, if there are no available cars.

  CarsInLocationResponse getCarsInLocation(CarsInLocationRequest carsInLocationRequest);
}
