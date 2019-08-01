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
import tables.CarDetailsTable;
import tables.CarStatusTable;
import tables.DriverDetailsTable;

// Class that populates various tables with some dummy values for dev purposes.
// In reality, the databases will be either created organically or directly populated
// using SQL scripts in the case of data migrations.
public class TableSetup {

  private static final int numberOfCarsInACity = 1000;
  private static final int numberOfCities = 6;

  private final EntityManagerFactory emf;

  public TableSetup() {
    this.emf = GlobalConstants.getEntityManagerFactory();
  }

  public void init() {
    assert (emf != null);

    // If the tables are already initialized, no need to initialize them again.
    // If you are worried about the consistency of data in your database, it may be much easier
    // to reset the database or truncate the tables.
    if (areTablesAlreadyInitialized()) {
      return;
    }

    initCarStatusTable();
    initCarDetailsTable();
    initDriverDetailsTable();
  }

  private boolean areTablesAlreadyInitialized() {
    // HACK: We don't want to do the initialization of tables multiple times if they have been
    // initialized already.
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

  // In reality, the table may not be necessarily organized this way and it could very well be
  // restricted to cities. But this helps understand certain SQL bottlenecks better.
  private void initCarStatusTable() {
    Double[] bangaloreLatitudeBoundaries = {12.818943, 13.112025};
    Double[] bangaloreLongitudeBoundaries = {77.431182, 77.767639};
    positionCarsWithinBoundary(
        0 * numberOfCarsInACity, bangaloreLatitudeBoundaries, bangaloreLongitudeBoundaries);

    Double[] chennaiLatitudeBoundaries = {12.847508, 13.596159};
    Double[] chennaiLongitudeBoundaries = {77.481075, 77.700260};
    positionCarsWithinBoundary(
        1 * numberOfCarsInACity, chennaiLatitudeBoundaries, chennaiLongitudeBoundaries);

    Double[] hyderabadLatitudeBoundaries = {17.175781, 17.587303};
    Double[] hyderabadLongitudeBoundaries = {78.260192, 78.696899};
    positionCarsWithinBoundary(
        2 * numberOfCarsInACity, hyderabadLatitudeBoundaries, hyderabadLongitudeBoundaries);

    Double[] delhiLatitudeBoundaries = {28.404286, 28.838257};
    Double[] delhiLongitudeBoundaries = {76.859436, 77.416992};
    positionCarsWithinBoundary(
        3 * numberOfCarsInACity, delhiLatitudeBoundaries, delhiLongitudeBoundaries);

    Double[] mumbaiLatitudeBoundaries = {18.927938, 19.223854};
    Double[] mumbaiLongitudeBoundaries = {72.813720, 72.978515};
    positionCarsWithinBoundary(
        4 * numberOfCarsInACity, mumbaiLatitudeBoundaries, mumbaiLongitudeBoundaries);

    Double[] kolkattaLatitudeBoundaries = {21.854701, 23.072993};
    Double[] kolkattaLongitudeBoundaries = {87.864990, 88.809814};
    positionCarsWithinBoundary(
        5 * numberOfCarsInACity, kolkattaLatitudeBoundaries, kolkattaLongitudeBoundaries);
  }

  private void positionCarsWithinBoundary(
      int startingCarId, Double[] latitudeBoundaries, Double[] longitudeBoundaries) {
    CarStatusTable carStatusTable = new CarStatusTable();

    // Using a constant seed to make the testing easier.
    Random generator = new Random(GlobalConstants.CAR_STATUS_TABLE_INIT_SEED);
    EntityManager em = emf.createEntityManager();
    em.getTransaction().begin();

    for (int i = startingCarId; i < startingCarId + numberOfCarsInACity; i++) {
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

  private void initCarDetailsTable() {
    // Using a constant seed to make the testing easier.
    Random generator = new Random(GlobalConstants.CAR_DETAILS_TABLE_INIT_SEED);

    String[] carModel = {"UNKNOWN", "TATA_INDICA", "TOYOTA ETIOS", "TOYOTA QUALIS"};
    EntityManager em = emf.createEntityManager();
    em.getTransaction().begin();

    for (int i = 0; i < numberOfCarsInACity * numberOfCities; i++) {
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

    for (int i = 0; i < numberOfCarsInACity * numberOfCities; i++) {
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
