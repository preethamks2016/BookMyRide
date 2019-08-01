/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package write;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import car.Car;
import car.CarStatus;
import globals.GlobalConstants;
import interfaces.WriteDataService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import location.GeoLocation;
import org.h2.tools.RunScript;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tables.CarStatusTable;
import tables.TripTable;
import tables.setup.TestTableSetup;
import trip.ScheduleTripTransactionInput;
import trip.ScheduleTripTransactionResult;
import trip.Trip;


public class WriteDataServiceImplTest {

  private static EntityManagerFactory emf;
  private static EntityManager em;
  private final WriteDataService writeDataService = new WriteDataServiceImpl();

  @BeforeAll
  public static void init() throws FileNotFoundException, SQLException {
    // TIP:MODULE_SCHEDULETRIP: You can uncomment the following lines & go to
    // http://localhost:8082/ in your browser to debug the database.
    // Server webServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082");
    // webServer.start();
    // 1. Make sure you suspend only the specific thread and not all threads.
    //    See here for details: https://www.jetbrains.com/help/idea/breakpoints.html#d1481308e168
    // 2. Also make sure you type the connection url correctly as given in persistence.xml file.

    // Initialize the database.
    GlobalConstants.initDatabase(GlobalConstants.PERSISTENCE_TEST_UNIT_NAME);
    emf = GlobalConstants.getEntityManagerFactory();
    em = emf.createEntityManager();

    Session session = em.unwrap(Session.class);
    session.doWork(
        new Work() {
          @Override
          public void execute(Connection connection) throws SQLException {
            try {
              File script = new File(getClass().getResource("/schema.sql").getFile());
              RunScript.execute(connection, new FileReader(script));
            } catch (FileNotFoundException e) {
              throw new RuntimeException("Could not initialize the data base with schema.sql");
            }
          }
        });

    // Setup the tables.
    TestTableSetup testTableSetup = new TestTableSetup();
    testTableSetup.init(true);
  }

  @AfterAll
  public static void tearDown() {
    // TIP:MODULE_SCHEDULETRIP: If you are hitting this assert, then it is possible that
    // you are running all the JUnit tests in the same JVM. You might have to use the 'Fork mode' in
    // Intellij test configuration to fix that.
    // Refer https://www.jetbrains.com/help/idea/run-debug-configuration-junit.html for details.
    em.clear();
    em.close();
    emf.close();
  }

  // TODO: CRIO_TEST_MODULE_SCHEDULETRIP - Add/modify tests to ensure
  // WriteDataServiceImpl.scheduleTrip() is tested well.
  @Test
  @Tag("Level1")
  @Tag("ScheduleTripApi")
  @Tag("QRIDE_ME_MODULE_SCHEDULE_TRIP")
  public void scheduleTripNormalCase() {
    ScheduleTripTransactionInput scheduleTripTransactionInput =
        new ScheduleTripTransactionInput(
            Car.CarType.CAR_TYPE_MINIVAN,
            120.00F,
            new GeoLocation(12.908486, 77.536386),
            new GeoLocation(50.00, 100.00));

    // Store the current time to check if the startTimeInEpochs was set correctly.
    final Long currentTimeInEpochs = System.currentTimeMillis() / 1000;

    ScheduleTripTransactionResult scheduleTripTransactionResult =
        writeDataService.scheduleTrip(scheduleTripTransactionInput);
    assertNotNull(scheduleTripTransactionResult);
    assertEquals(
        ScheduleTripTransactionResult.ScheduleTripTransactionStatus.SUCCESSFULLY_BOOKED,
        scheduleTripTransactionResult.getScheduleTripTransactionStatus(),
        "TransactionStatus must be set to SUCCESS");
    assertNotNull(scheduleTripTransactionResult.getTrip());
    Trip trip = scheduleTripTransactionResult.getTrip();
    assertNotNull(trip.getCarId());
    assertNotNull(trip.getDriverId());
    // assertNull(trip.getPaymentMode(), "Payment mode must not be set");
    assertEquals(trip.getPaymentMode(), null, "Payment mode must not be set");
    assertEquals(
        Trip.TripStatus.TRIP_STATUS_SCHEDULED, trip.getTripStatus(),
        "Trip status must be SCHEDULED");
    assertTrue(currentTimeInEpochs <= trip.getStartTimeInEpochs(),
        "Trip start time must be set");

    EntityManager testEm = GlobalConstants.getEntityManagerFactory().createEntityManager();

    // Check if the car availability was updated to CAR_ON_TRIP.
    CarStatusTable carStatusEntry = testEm.find(CarStatusTable.class, trip.getCarId());
    assertEquals(
        CarStatus.CarAvailability.CAR_ON_TRIP,
        carStatusEntry.getCarAvailability(),
        "Car availability must be CAR_ON_TRIP");

    // Alternatively we are also going to read from the database directly to check the same.
    TripTable tripTableEntry = testEm.find(TripTable.class, trip.getTripId());
    assertEquals(
        trip.getTripId(), tripTableEntry.getTripId(),
        "TripId must be equal when read from database"
    );
    testEm.close();
  }
}

