/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package write;

import interfaces.WriteDataService;
import trip.ScheduleTripTransactionInput;
import trip.ScheduleTripTransactionResult;

// TODO: CRIO_TASK_MODULE_SCHEDULETRIP: All API must call WriteDataServiceImpl.
// Make sure all your API handlers call this implementation
// instead of WriteDataServiceDummyImpl.
public class WriteDataServiceImpl implements WriteDataService {

  @Override
  public ScheduleTripTransactionResult scheduleTrip(
      ScheduleTripTransactionInput scheduleTripTransactionInput) {
    ScheduleTripTransaction scheduleTripTransaction = new ScheduleTripTransaction();
    return scheduleTripTransaction.scheduleTrip(scheduleTripTransactionInput);
  }
}
