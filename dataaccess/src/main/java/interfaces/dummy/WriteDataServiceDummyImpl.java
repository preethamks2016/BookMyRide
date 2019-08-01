/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package interfaces.dummy;

import interfaces.WriteDataService;
import location.GeoLocation;
import trip.ScheduleTripTransactionInput;
import trip.ScheduleTripTransactionResult;
import trip.Trip;

// TIP:MODULE_SCHEDULETRIP: A bunch of dummy functions to test your REST API handler side
// of logic before implementing the SQL layer.
// Once you have implemented the SQL layer, you don't need this anymore.
public class WriteDataServiceDummyImpl implements WriteDataService {

  @Override
  public ScheduleTripTransactionResult scheduleTrip(
      ScheduleTripTransactionInput scheduleTripTransactionInput) {
    assert (scheduleTripTransactionInput != null);
    ScheduleTripTransactionResult scheduleTripTransactionResult =
        new ScheduleTripTransactionResult();
    scheduleTripTransactionResult.setScheduleTripTransactionStatus(
        ScheduleTripTransactionResult.ScheduleTripTransactionStatus.SUCCESSFULLY_BOOKED);
    Trip trip = new Trip();
    trip.setTripId("TRIP_ID_1");
    trip.setCarId("CAR_ID_1");
    trip.setDriverId("DRIVER_ID_1");
    trip.setSourceLocation(new GeoLocation(12.908486, 77.536386));
    trip.setDestinationLocation(new GeoLocation(12.838833, 77.488080));
    trip.setStartTimeInEpochs(1512461508L);
    trip.setEndTimeInEpochs(1512463546L);
    trip.setTripPrice(129.00F);
    trip.setTripStatus(Trip.TripStatus.TRIP_STATUS_COMPLETED);
    trip.setPaymentMode(Trip.PaymentMode.CARD_PAYMENT);
    scheduleTripTransactionResult.setTrip(trip);

    return scheduleTripTransactionResult;
  }
}
