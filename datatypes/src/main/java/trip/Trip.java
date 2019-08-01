/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package trip;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import location.GeoLocation;

@JsonInclude(Include.NON_NULL)
public class Trip {

  private String tripId;
  private String carId;
  private GeoLocation destinationLocation;
  private GeoLocation sourceLocation;
  private String driverId;
  private long startTimeInEpochs;
  private long endTimeInEpochs;
  private Float tripPrice;
  private TripStatus tripStatus;
  private PaymentMode paymentMode;

  public String getTripId() {
    return tripId;
  }

  public void setTripId(String tripId) {
    this.tripId = tripId;
  }

  public String getCarId() {
    return carId;
  }

  public void setCarId(String carId) {
    this.carId = carId;
  }

  public GeoLocation getDestinationLocation() {
    return destinationLocation;
  }

  public void setDestinationLocation(GeoLocation destinationLocation) {
    this.destinationLocation = destinationLocation;
  }

  public GeoLocation getSourceLocation() {
    return sourceLocation;
  }

  public void setSourceLocation(GeoLocation geoLocation) {
    this.sourceLocation = geoLocation;
  }

  public String getDriverId() {
    return driverId;
  }

  public void setDriverId(String driverId) {
    this.driverId = driverId;
  }

  public long getStartTimeInEpochs() {
    return startTimeInEpochs;
  }

  public void setStartTimeInEpochs(long l) {
    this.startTimeInEpochs = l;
  }

  public long getEndTimeInEpochs() {
    return endTimeInEpochs;
  }

  public void setEndTimeInEpochs(long l) {
    this.endTimeInEpochs = l;
  }

  public Float getTripPrice() {
    return tripPrice;
  }

  public void setTripPrice(Float v) {
    this.tripPrice = v;
  }

  public TripStatus getTripStatus() {
    return tripStatus;
  }

  public void setTripStatus(TripStatus tripStatusCompleted) {
    this.tripStatus = tripStatusCompleted;
  }

  public PaymentMode getPaymentMode() {
    return paymentMode;
  }

  public void setPaymentMode(PaymentMode cardPayment) {
    this.paymentMode = cardPayment;
  }
  // TODO: CRIO_TASK_MODULE_SCHEDULETRIP.1 - Populate ScheduleTrip class.
  // Complete the class such that it can be used to create
  // the following JSON format.
  // {
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
  //    "endTimeInEpochs": 1512170782,
  //    "tripPrice": 129.00,
  //    "tripStatus": "TRIP_STATUS_COMPLETED",
  //    "paymentMode": "PAYTM_PAYMENT"
  // }
  // tripId, carId are strings; startTimeInEpochs, endTimeInEpochs are time epochs;

  public enum PaymentMode {
    UNKNOWN,
    CASH_PAYMENT,
    CARD_PAYMENT,
    PAYTM_PAYMENT
  }

  public enum TripStatus {
    UNKNOWN,
    TRIP_STATUS_SCHEDULED,
    TRIP_STATUS_ONGOING,
    TRIP_STATUS_COMPLETED,
    TRIP_STATUS_CANCELLED
  }
}
