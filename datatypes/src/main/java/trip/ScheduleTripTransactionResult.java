/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package trip;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ScheduleTripTransactionResult {

  @Valid
  @NotNull
  ScheduleTripTransactionResult.ScheduleTripTransactionStatus scheduleTripTransactionStatus;
  @Valid
  @NotNull
  Trip trip;

  public ScheduleTripTransactionStatus getScheduleTripTransactionStatus() {
    return scheduleTripTransactionStatus;
  }

  public void setScheduleTripTransactionStatus(
      ScheduleTripTransactionStatus scheduleTripTransactionStatus) {
    this.scheduleTripTransactionStatus = scheduleTripTransactionStatus;
  }

  public Trip getTrip() {
    return trip;
  }

  public void setTrip(Trip trip) {
    this.trip = trip;
  }

  public enum ScheduleTripTransactionStatus {
    UNKNOWN,
    SUCCESSFULLY_BOOKED,
    PRICE_CHANGED,
    NO_CARS_AVAILABLE,
    DATABASE_ERROR
  }
}
