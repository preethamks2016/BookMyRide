/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package scheduletrip;

import interfaces.ScheduleTripService;
import interfaces.WriteDataService;
import trip.ScheduleTripTransactionInput;
import trip.ScheduleTripTransactionResult;

public class ScheduleTripServiceImpl implements ScheduleTripService {

  // Used to write data to storage.
  private WriteDataService writeDataService;

  public ScheduleTripServiceImpl(WriteDataService writeDataServiceImpl) {
    this.writeDataService = writeDataServiceImpl;
  }

  @Override
  public ScheduleTripResponse scheduleTrip(ScheduleTripRequest scheduleTripRequest) {
    // TODO: CRIO_TASK_MODULE_SCHEDULETRIP.4: - Implement scheduleTrip().
    // Implement interface ScheduleTripService.scheduleTrip().
    // Schedule a trip when car type, the estimated price, and
    // the source/destination locations are given.
    // Input:
    //   scheduleTripRequest - Contains valid car type, estimate price & source &
    //                         destination locations.
    // Output:
    //   1. If we are able to find a car available of this particular car type within
    //      the search radius, schedule it & return ScheduleTripResponse populated with:
    //        - scheduleTripStatus = SUCCESSFULLY_BOOKED.
    //        - trip details with updated startTimeInEpochs
    //          & tripStatus = TRIP_STATUS_SCHEDULED.
    //   2. If no cars are available, return ScheduleTripResponse populated with:
    //        - scheduleTripStatus = NO_CARS_AVAILABLE without any trip information.
    assert (scheduleTripRequest != null);
    assert (scheduleTripRequest.getCarType() != null);
    // assert (scheduleTripRequest.getTripPrice() != null);
    assert (scheduleTripRequest.getSourceLocation() != null);
    assert (scheduleTripRequest.getDestinationLocation() != null);

    ScheduleTripTransactionInput scheduleTripTransactionInput = new ScheduleTripTransactionInput(
        scheduleTripRequest.getCarType(),
        scheduleTripRequest.getTripPrice(), scheduleTripRequest.getSourceLocation(),
        scheduleTripRequest.getDestinationLocation());

    ScheduleTripTransactionResult scheduleTripTransactionResult = this.writeDataService
        .scheduleTrip(scheduleTripTransactionInput);

    ScheduleTripResponse scheduleTripResponse = new ScheduleTripResponse();
    scheduleTripResponse
        .setScheduleTripStatus(scheduleTripTransactionResult.getScheduleTripTransactionStatus());
    scheduleTripResponse.setTrip(scheduleTripTransactionResult.getTrip());
    return scheduleTripResponse;
    //return null;
  }

  public void setWriteDataService(WriteDataService writeDataService) {
    this.writeDataService = writeDataService;
  }
}
