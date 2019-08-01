/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package tables.setup;

import car.Car;
import car.CarStatus;
import globals.GlobalConstants;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import location.GeoLocation;
import location.GeoUtils;
import tables.CarDetailsTable;
import tables.CarStatusTable;
import tables.DriverDetailsTable;
import tables.TripTable;
import trip.Trip;

// Class that populates various tables with some dummy values for tests.
public class TestTableSetup {

  private final EntityManagerFactory emf;

  public TestTableSetup() {
    emf = GlobalConstants.getEntityManagerFactory();
    assert (emf != null);
  }

  public void init() {
    init(false);
  }

  // Initialize the trip table only if requested for the tests.
  public void init(boolean initializeTripTable) {
    assert (emf != null);

    if (areTablesAlreadyInitialized()) {
      return;
    }

    initCarStatusTable();
    if (initializeTripTable) {
      initTripTable();
    }
    initCarDetailsTable();
    initDriverDetailsTable();
  }

  private boolean areTablesAlreadyInitialized() {
    // HACK: We don't want to do the initialization of tables multiple times during tests.
    // As a quick hack, we are just checking whether the CAR_ID_1 exists in CarStatus table
    // and if so, not proceed ahead with initializing the tables.
    EntityManager em = emf.createEntityManager();
    CarStatusTable carDetailsTableEntry = em.find(CarStatusTable.class, "CAR_ID_1");
    if (carDetailsTableEntry == null) {
      return false;
    } else {
      return true;
    }
  }

  private void initCarStatusTable() {
    // Position the cars randomly within Bangalore.
    // 12.973277, 77.596921 - E
    // 12.953472, 77.478061 - W
    // 13.018920, 77.532130 - N
    // 12.882345, 77.492376 - S
    Double[] bangaloreLatitudeBoundaries = {12.870595, 13.039963};
    Double[] bangaloreLongitudeBoundaries = {77.481075, 77.700260};
    positionCarsWithinBoundary(0, bangaloreLatitudeBoundaries, bangaloreLongitudeBoundaries);

    Double[] bayAreaLatitudeBoundaries = {37.377695, 37.431580};
    Double[] bayAreaLongitudeBoundaries = {-121.964033, -121.902025};
    positionCarsWithinBoundary(3000, bayAreaLatitudeBoundaries, bayAreaLongitudeBoundaries);
  }

  private void positionCarsWithinBoundary(
      int startingCarId, Double[] latitudeBoundaries, Double[] longitudeBoundaries) {
    CarStatusTable carStatusTable = new CarStatusTable();

    // Using a constant seed to make the testing easier.
    Random generator = new Random(GlobalConstants.CAR_STATUS_TABLE_INIT_SEED);
    EntityManager em = emf.createEntityManager();
    em.getTransaction().begin();

    for (int i = startingCarId; i < startingCarId + 3000; i++) {
      GeoLocation geoLocation = new GeoLocation();
      geoLocation.setLatitude(
          latitudeBoundaries[0]
              + generator.nextDouble() * (latitudeBoundaries[1] - latitudeBoundaries[0]));
      geoLocation.setLongitude(
          longitudeBoundaries[0]
              + generator.nextDouble() * (longitudeBoundaries[1] - longitudeBoundaries[0]));

      carStatusTable.setCarId("CAR_ID_" + i);
      carStatusTable.setCarAvailability(CarStatus.CarAvailability.values()[i % 5]);
      carStatusTable.setCarType(Car.CarType.values()[i % 4]);
      carStatusTable.setGeoLocation(geoLocation);
      em.persist(carStatusTable);
      em.flush();
      em.clear();
    }
    em.getTransaction().commit();
    em.close();
  }

  private void initTripTable() {
    Double[] bangaloreLatitudeBoundaries = {12.870595, 13.039963};
    Double[] bangaloreLongitudeBoundaries = {77.481075, 77.700260};
    createTripsWithinBoundaryRange(bangaloreLatitudeBoundaries, bangaloreLongitudeBoundaries);
  }

  private void createTripsWithinBoundaryRange(
      Double[] latitudeBoundaries, Double[] longitudeBoundaries) {
    // Using a constant seed to make the testing easier.
    Random generator = new Random(GlobalConstants.TRIP_TABLE_INIT_SEED);

    EntityManager em = emf.createEntityManager();
    em.getTransaction().begin();

    // CarIDs in the trip should overlap with the cars in CarStatus table.
    for (int i = 1500; i < 3000; i++) {
      TripTable tripTable = new TripTable();
      tripTable.setTripId("TRIP_ID_" + i);
      tripTable.setCarId("CAR_ID_" + i);
      tripTable.setDriverId("DRIVER_ID_" + i);

      GeoLocation sourceLocation = new GeoLocation();
      sourceLocation.setLatitude(
          latitudeBoundaries[0]
              + generator.nextDouble() * (latitudeBoundaries[1] - latitudeBoundaries[0]));
      sourceLocation.setLongitude(
          longitudeBoundaries[0]
              + generator.nextDouble() * (longitudeBoundaries[1] - longitudeBoundaries[0]));
      tripTable.setSourceLocation(sourceLocation);

      GeoLocation destinationLocation = new GeoLocation();
      destinationLocation.setLatitude(
          latitudeBoundaries[0]
              + generator.nextDouble() * (latitudeBoundaries[1] - latitudeBoundaries[0]));
      destinationLocation.setLongitude(
          longitudeBoundaries[0]
              + generator.nextDouble() * (longitudeBoundaries[1] - longitudeBoundaries[0]));
      tripTable.setDestinationLocation(destinationLocation);
      tripTable.setStartTimeInEpochs(1512825182L + i * 1000);
      tripTable.setEndTimeInEpochs(tripTable.getStartTimeInEpochs() + 2500);
      tripTable.setTripPrice(
          GeoUtils.getDistanceBetweenLocationsInKms(sourceLocation, destinationLocation) * 10);
      tripTable.setTripStatus(Trip.TripStatus.values()[i % 5]);
      tripTable.setPaymentMode(Trip.PaymentMode.values()[i % 4]);

      em.persist(tripTable);
      em.flush();
      em.clear();
    }
    em.getTransaction().commit();
    em.close();
  }

  private void initCarDetailsTable() {
    // Using a constant seed to make the testing easier.
    Random generator = new Random(GlobalConstants.CAR_DETAILS_TABLE_INIT_SEED);

    String[] carModel = {"UNKNOWN", "TATA_INDICA", "TOYOTA ETIOS", "TOYOTA QUALIS"};
    EntityManager em = emf.createEntityManager();
    em.getTransaction().begin();

    for (int i = 0; i < 6000; i++) {
      CarDetailsTable carDetailsTable = new CarDetailsTable();
      carDetailsTable.setCarId("CAR_ID_" + i);
      carDetailsTable.setCarType(Car.CarType.values()[i % 4]);
      carDetailsTable.setDriverId("DRIVER_ID_" + i);
      carDetailsTable.setCarModel(carModel[i % 4]);
      carDetailsTable.setCarLicense("KA 01 " + generator.nextInt(9000) + 1000);
      em.persist(carDetailsTable);
      em.flush();
      em.clear();
    }
    em.getTransaction().commit();
  }

  private void initDriverDetailsTable() {
    // Using a constant seed to make the testing easier.
    Random generator = new Random(GlobalConstants.DRIVER_DETAILS_TABLE_INIT_SEED);

    EntityManager em = emf.createEntityManager();
    em.getTransaction().begin();

    for (int i = 0; i < 6000; i++) {
      DriverDetailsTable driverDetailsTable = new DriverDetailsTable();
      driverDetailsTable.setDriverId("DRIVER_ID_" + i);
      driverDetailsTable.setCarId("CAR_ID_" + i);
      driverDetailsTable.setName("CAR_DRIVER_NAME_" + i);
      driverDetailsTable.setPhone("+91" + (generator.nextInt(900000000) + 9000000000L));
      em.persist(driverDetailsTable);
      em.flush();
      em.clear();
    }
    em.getTransaction().commit();
  }
}
