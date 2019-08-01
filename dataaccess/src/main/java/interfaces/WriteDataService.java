/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package interfaces;

import trip.ScheduleTripTransactionInput;
import trip.ScheduleTripTransactionResult;

public interface WriteDataService {

  // Does everything to schedule a trip:
  //   - Finds out if there is a car available of given carType in the nearby radius.
  //   - If available, creates a Trip entry, populates it and stores it in the appropriate table.
  //   - Also changes the status of the specific car.
  // Input:
  //   scheduleTripTransactionInput - Contains carType, tripPrice, start/end locations.
  // Output:
  //   1. If we are able to find a car available of this particular car type within
  //      the search radius, schedule the closest proximity car & return
  //      ScheduleTripTransactionResult populated with:
  //      - scheduleTripTransactionStatus = SUCCESSFULLY_BOOKED.
  //      - trip details with updated tripId, carId, driverId, startTimeInEpochs,
  //        source/destination locations, trip price, & tripStatus = TRIP_STATUS_SCHEDULED.
  //   2. If no cars are available, return ScheduleTripTransactionResult populated with:
  //      - scheduleTripTransactionStatus = NO_CARS_AVAILABLE without any trip information.
  ScheduleTripTransactionResult scheduleTrip(
      ScheduleTripTransactionInput scheduleTripTransactionInput);
}
