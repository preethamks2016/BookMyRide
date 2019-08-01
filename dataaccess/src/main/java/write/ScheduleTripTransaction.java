/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package write;

import car.CarStatus;
import car.CarStatus.CarAvailability;
import globals.GlobalConstants;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import read.FormatConverters;
import tables.CarStatusTable;
import tables.DriverDetailsTable;
import tables.TripTable;
import trip.ScheduleTripTransactionInput;
import trip.ScheduleTripTransactionResult;
import trip.ScheduleTripTransactionResult.ScheduleTripTransactionStatus;
import trip.Trip;


// This class contains methods to schedule a trip.
public class ScheduleTripTransaction {

  // TODO: CRIO_TASK_MODULE_SCHEDULETRIP.6: Implement scheduleTrip() function.
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
  //      - trip details with updated tripId (any random id works), carId, driverId,
  //        startTimeInEpochs, source/destination locations, trip price,
  //        & tripStatus = TRIP_STATUS_SCHEDULED.
  //   2. If no cars are available, return ScheduleTripTransactionResult populated with:
  //      - scheduleTripTransactionStatus = NO_CARS_AVAILABLE without any trip information.
  //   3. In case of any other errors, return ScheduleTripTransactionResult populated with
  //      appropriate scheduleTripTransactionStatus without any trip information.
  //   If a car was selected, make sure to update the Trip info & status of the selected car
  //   to CAR_ON_TRIP in appropriate tables.
  public ScheduleTripTransactionResult scheduleTrip(
      ScheduleTripTransactionInput scheduleTripTransactionInput) {
    ScheduleTripTransactionResult scheduleTripTransactionResult =
        new ScheduleTripTransactionResult();
    Trip trip = new Trip();

    List<CarStatusTable> carStatusesTable = getCarsInProximity(scheduleTripTransactionInput);
    if (carStatusesTable == null || carStatusesTable.size() == 0) {
      scheduleTripTransactionResult
          .setScheduleTripTransactionStatus(ScheduleTripTransactionStatus.NO_CARS_AVAILABLE);
    } else {
      CarStatusTable carStatusTable = carStatusesTable.get(0);
      // Trip trip = populateTrip(carStatusTable);
      TripTable tripTable = createNewTripTableEntry(scheduleTripTransactionInput, carStatusTable);
      scheduleTripTransactionResult
          .setScheduleTripTransactionStatus(ScheduleTripTransactionStatus.SUCCESSFULLY_BOOKED);

      // For multiple Transactions
      EntityManagerFactory emf = GlobalConstants.getEntityManagerFactory();
      EntityManager em = emf.createEntityManager();

      em.getTransaction().begin();
      em.persist(tripTable);
      carStatusTable.setCarAvailability(CarAvailability.CAR_ON_TRIP);
      em.getTransaction().commit();

      trip = FormatConverters.convertTripTableEntryIntoTrip(tripTable);

      em.close();
      assert trip != null;
    }

    scheduleTripTransactionResult.setTrip(trip);
    return scheduleTripTransactionResult;
  }

  // TODO: CRIO_TASK_MODULE_SCHEDULETRIP.7: Creation of New TripTable From ScheduleTrip.
  // Utility function to create a new TripTable entry from scheduleTripTransactionInput.
  // Helps keep your code modularized, but it is not mandatory to use this function.

  private TripTable createNewTripTableEntry(
      ScheduleTripTransactionInput scheduleTripTransactionInput, CarStatusTable carStatusTable) {

    // CarStatus carStatus = FormatConverters.convertCarStatusTableIntoCarStatus(carStatusTable);
    TripTable tripTable = new TripTable();
    EntityManagerFactory emf = GlobalConstants.getEntityManagerFactory();

    EntityManager entityManager = emf.createEntityManager();

    TypedQuery<DriverDetailsTable> query = entityManager.createQuery(
        "SELECT d FROM DriverDetailsTable d where d.carId=:crId ",
        DriverDetailsTable.class);
    query.setParameter("crId", carStatusTable.getCarId());

    DriverDetailsTable driverDetailsTable = query.getSingleResult();
    if (driverDetailsTable != null) {
      long epochs = Instant.now().toEpochMilli();
      tripTable.setStartTimeInEpochs(epochs);
      tripTable.setTripId("TRIP_ID_" + epochs);
      tripTable.setCarId(driverDetailsTable.getCarId());
      tripTable.setDriverId(driverDetailsTable.getDriverId());
      tripTable.setSourceLocation(scheduleTripTransactionInput
          .getSourceLocation());
      tripTable.setDestinationLocation(scheduleTripTransactionInput
          .getDestinationLocation());
      tripTable.setTripPrice(scheduleTripTransactionInput.getTripPrice());
      tripTable.setTripStatus(Trip.TripStatus.TRIP_STATUS_SCHEDULED);
    }

    entityManager.close();
    return tripTable;
  }

  private List<CarStatusTable> getCarsInProximity(
      ScheduleTripTransactionInput scheduleTripTransactionInput) {
    List<CarStatusTable> carStatusesTable = new ArrayList<>();
    EntityManagerFactory emf = GlobalConstants.getEntityManagerFactory();
    EntityManager em = emf.createEntityManager();
    try {
      //String queryString="SELECT c FROM CarStatusTable c
      // WHERE c.getCarAvailability() = :carAvailability";

      // query to get cars within radius
      // @NamedQuery(name="",query="")
      String queryString = "SELECT c FROM CarStatusTable AS c WHERE carType = :carType "
          + "AND " + GlobalConstants.HAVER_SINE_FORMULA + " < :radius ORDER BY "
          + GlobalConstants.HAVER_SINE_FORMULA + " ASC";
      //GlobalConstants.SEARCH_RADIUS_CARS_AVAILABLE_IN_KM;
      TypedQuery<CarStatusTable> query = em.createQuery(queryString, CarStatusTable.class);
      //query.setParameter("carAvailability", CarAvailability.CAR_AVAILABLE);
      query.setParameter("carType", scheduleTripTransactionInput.getCarType());
      query.setParameter("radius", GlobalConstants.SEARCH_RADIUS_CARS_AVAILABLE_IN_KM);
      query
          .setParameter("latPoint",
              scheduleTripTransactionInput.getSourceLocation().getLatitude());
      query.setParameter("longPoint",
          scheduleTripTransactionInput.getSourceLocation().getLongitude());

      carStatusesTable = query.getResultList();

    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } finally {
      em.close();
    }
    return carStatusesTable;
  }

}
// end of class

