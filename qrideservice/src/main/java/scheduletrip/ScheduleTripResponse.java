/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package scheduletrip;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import trip.ScheduleTripTransactionResult.ScheduleTripTransactionStatus;
import trip.Trip;

public class ScheduleTripResponse {

  @Valid
  @NotNull
  private ScheduleTripTransactionStatus scheduleTripStatus;
  @Valid
  @NotNull
  private Trip trip;

  @JsonGetter("scheduleTripStatus")
  public ScheduleTripTransactionStatus getScheduletripStatus() {
    return this.scheduleTripStatus;
  }

  @JsonGetter("trip")
  public Trip getTrip() {
    return this.trip;
  }

  @JsonSetter("trip")
  public void setTrip(Trip trip) {
    this.trip = trip;
  }

  @JsonSetter("scheduleTripStatus")
  public void setScheduleTripStatus(ScheduleTripTransactionStatus scheduleTripTransactionStatus) {
    this.scheduleTripStatus = scheduleTripTransactionStatus;
  }
  // TODO: CRIO_TASK_MODULE_SCHEDULETRIP.3: Populate ScheduleTripResponse class.
  // Complete the class so that when Jackson serialization is used,
  // the following JSON response gets generated.
  // Reuse ScheduleTripTransactionResult.ScheduleTripTransactionStatus for scheduleTripStatus field.
  // {
  //  "scheduleTripStatus": "SUCCESSFULLY_BOOKED",
  //  "trip": {
  //    "tripId": "TRIP_ID_1",
  //    "carId": "CAR_ID_1",
  //    "driverId": "DRIVER_ID_1",
  //    "sourceLocation": {
  //      "latitude": 12.908486,
  //      "longitude": 77.536386
  //    },
  //    "destinationLocation": {
  //      "latitude": 12.908486,
  //      "longitude": 77.536386
  //    },
  //    "startTimeInEpochs": 1512152782,
  //    "tripPrice": 129.00,
  //    "tripStatus": "TRIP_STATUS_SCHEDULED"
  //  }
  // }

}
