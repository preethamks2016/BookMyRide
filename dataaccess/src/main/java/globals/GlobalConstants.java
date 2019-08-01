/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package globals;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GlobalConstants {

  public static final String HAVER_SINE_FORMULA =
      "111.045 * DEGREES(ACOS(COS(RADIANS(:latPoint)) * COS(RADIANS(latitude))"
          + " * COS(RADIANS(:longPoint) - RADIANS(longitude)) + SIN(RADIANS(:latPoint))"
          + " * SIN(RADIANS(latitude))))";

  // TIP:MODULE_SQL: This will be the persistence unit you will be using for dev.
  // You can find its definition in persistence.xml file.
  public static final String PERSISTENCE_UNIT_NAME = "qrideDatabaseEmbedded";

  // TIP:MODULE_SCHEDULETRIP:Use following persistence unit name to connect to MySQL server
  // Note that you should have a MySQL server installed & started separately unlike the embedded
  // H2 database.
  // Also you may have to update the <ip:port>, username & password in the persistence.xml file.
  // public static final String PERSISTENCE_UNIT_NAME = "qrideMySQLDB";

  // Name of the test persistence unit.
  public static final String PERSISTENCE_TEST_UNIT_NAME = "qrideDatabaseEmbedded";

  // Radius within which available cars are searched.
  public static final double SEARCH_RADIUS_CARS_AVAILABLE_IN_KM = 3.0;
  // Radius within which cars close to completion are searched.
  public static final double SEARCH_RADIUS_CARS_CLOSE_TO_COMPLETION_IN_KM = 1.0;

  // Fixed seeds for random number generation to make sure unit tests work fine.
  public static final int CAR_STATUS_TABLE_INIT_SEED = 777;
  public static final int CAR_DETAILS_TABLE_INIT_SEED = 778;
  public static final int DRIVER_DETAILS_TABLE_INIT_SEED = 779;
  public static final int TRIP_TABLE_INIT_SEED = 780;

  // EntityManagerFactory that will be used. You need to set this to the test, dev/production
  // databases appropriately.
  private static EntityManagerFactory emf;

  public static void initDatabase(String persistenceUnit) {
    assert (emf == null);
    emf = Persistence.createEntityManagerFactory(persistenceUnit);
  }

  public static EntityManagerFactory getEntityManagerFactory() {
    assert (emf != null);
    return emf;
  }
}
