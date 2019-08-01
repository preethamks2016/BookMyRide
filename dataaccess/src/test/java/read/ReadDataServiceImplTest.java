/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package read;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import car.Car;
import car.CarStatus;
import globals.GlobalConstants;
import interfaces.ReadDataService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.h2.tools.RunScript;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tables.setup.TestTableSetup;
import utils.TestUtils;


// Scope: Tests ReadDataService interface implementation for both normal & boundary cases.
public class ReadDataServiceImplTest {

  private static EntityManagerFactory emf;
  private static EntityManager em;
  private final ReadDataService readDataService = new ReadDataServiceImpl();

  @BeforeAll
  public static void init() throws FileNotFoundException, SQLException {
    System.out.println("In init - will be run only once");
    // TIP:MODULE_SQL: You can uncomment the following lines & go to http://localhost:8082/
    // in your browser to debug the database.
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
    System.out.println("Initialize Tables");
    TestTableSetup testTableSetup = new TestTableSetup();
    testTableSetup.init();
  }

  @AfterAll
  public static void tearDown() {
    // TIP:MODULE_SQL: If you are hitting this assert, then it is possible that you are
    // running all the JUnit tests in the same JVM. You might have to use the 'Fork mode' in
    // Intellij test configuration to fix that.
    // Refer https://www.jetbrains.com/help/idea/run-debug-configuration-junit.html for details.
    em.clear();
    em.close();
    emf.close();
  }

  public static double getHaversineDistance(double lat1, double lon1, double lat2, double lon2) {
    final double R = 6372.8; // In kilometers
    double lat = Math.toRadians(lat2 - lat1);
    double lon = Math.toRadians(lon2 - lon1);
    lat1 = Math.toRadians(lat1);
    lat2 = Math.toRadians(lat2);
    double a = Math.pow(Math.sin(lat / 2), 2)
        + Math.pow(Math.sin(lon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
    double c = 2 * Math.asin(Math.sqrt(a));
    return R * c;
  }

  // TODO: CRIO_TEST_MODULE_SQL_1 - Add/modify tests to ensure
  // ReadDataServiceImpl.getCarsInLocation() is tested well.
  @Test
  @Tag("Level1")
  @Tag("CarsInLocationApi")
  @Tag("QRIDE_ME_MODULE_SQL")
  public void getCarsInLocationNormalCase() {
    List<CarStatus> carStatusList =
        readDataService.getCarsInLocation(TestUtils.createGeoLocationObject(
            12.953472, 77.478061), CarStatus.CarAvailability.CAR_AVAILABLE,
            GlobalConstants.SEARCH_RADIUS_CARS_AVAILABLE_IN_KM);
    // Assert based on our test table data.
    assertEquals(20, carStatusList.size(),
        "# of available cars expected to be within the radius"
    );
    // Check if you are returning the cars with the right availability status.
    // [Optional] You can also check if the cars returned are within the specified radius.
  }

  // Test for handling cases where there are no cars available in a given location.
  @Test
  @Tag("Level1")
  @Tag("CarsInLocationApi")
  @Tag("QRIDE_ME_MODULE_SQL")
  public void getCarsInLocationEmptyCase() {
    List<CarStatus> carStatusList =
        readDataService.getCarsInLocation(TestUtils.createGeoLocationObject(14.00D,
            80.00D), CarStatus.CarAvailability.CAR_AVAILABLE,
            GlobalConstants.SEARCH_RADIUS_CARS_AVAILABLE_IN_KM);
    assertEquals(0, carStatusList.size(),
        "# of available cars expected to be within the radius"
    );
  }

  @Test
  @Tag("Level2")
  @Tag("ScheduleTripApi")
  @Tag("QRIDE_ME_MODULE_SCHEDULETRIP")
  public void getCarsInLocationOfCarTypeNormalCase() {

    List<CarStatus> carStatusList = readDataService
        .getCarsInLocationOfCarType(TestUtils.createGeoLocationObject(
            12.953472, 77.478061), Car.CarType.CAR_TYPE_HATCHBACK);

    assertNotEquals(0, carStatusList.size());
    for (CarStatus carStatus : carStatusList) {
      assertEquals(carStatus.getCarAvailability(), CarStatus.CarAvailability.CAR_AVAILABLE);
    }
  }
}
