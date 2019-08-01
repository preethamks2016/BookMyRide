/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package tables;

import java.io.Serializable;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import location.GeoLocation;
import trip.Trip;

// Details of Trip table.
@Entity
public class TripTable implements Serializable {

  @Id
  private String tripId;

  @NotNull
  private String carId;

  @NotNull
  private String driverId;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "latitude", column = @Column(name = "sourceLocationLatitude")),
      @AttributeOverride(name = "longitude", column = @Column(name = "sourceLocationLongitude"))
  })
  private GeoLocation sourceLocation;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "latitude", column = @Column(name = "destinationLocationLatitude")),
      @AttributeOverride(name = "longitude",
          column = @Column(name = "destinationLocationLongitude"))
  })
  private GeoLocation destinationLocation;

  private Long startTimeInEpochs;
  private Long endTimeInEpochs;
  private Float tripPrice;

  @Enumerated(EnumType.ORDINAL)
  private Trip.TripStatus tripStatus;

  @Enumerated(EnumType.ORDINAL)
  private Trip.PaymentMode paymentMode;

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

  public String getDriverId() {
    return driverId;
  }

  public void setDriverId(String driverId) {
    this.driverId = driverId;
  }

  public GeoLocation getSourceLocation() {
    return sourceLocation;
  }

  public void setSourceLocation(GeoLocation sourceLocation) {
    this.sourceLocation = sourceLocation;
  }

  public GeoLocation getDestinationLocation() {
    return destinationLocation;
  }

  public void setDestinationLocation(GeoLocation destinationLocation) {
    this.destinationLocation = destinationLocation;
  }

  public Long getStartTimeInEpochs() {
    return startTimeInEpochs;
  }

  public void setStartTimeInEpochs(Long startTimeInEpochs) {
    this.startTimeInEpochs = startTimeInEpochs;
  }

  public Long getEndTimeInEpochs() {
    return endTimeInEpochs;
  }

  public void setEndTimeInEpochs(Long endTimeInEpochs) {
    this.endTimeInEpochs = endTimeInEpochs;
  }

  public Float getTripPrice() {
    return tripPrice;
  }

  public void setTripPrice(Float tripPrice) {
    this.tripPrice = tripPrice;
  }

  public Trip.TripStatus getTripStatus() {
    return tripStatus;
  }

  public void setTripStatus(Trip.TripStatus tripStatus) {
    this.tripStatus = tripStatus;
  }

  public Trip.PaymentMode getPaymentMode() {
    return paymentMode;
  }

  public void setPaymentMode(Trip.PaymentMode paymentMode) {
    this.paymentMode = paymentMode;
  }


}
