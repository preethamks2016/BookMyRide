/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package read;

import car.Car;
import car.CarStatus;
import globals.GlobalConstants;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import location.GeoLocation;
import tables.CarStatusTable;

// This class contains methods to get available cars from datastore which can be either cache or db.
public class GetCarsInLocationFromDataStore {

  // Splitting the function into gerCarsInLocation & getCarsInLocationDb like this helps abstract
  // whether we are returning from cache or from db eventually.
  public List<CarStatus> getCarsInLocation(GeoLocation geoLocation,
      CarStatus.CarAvailability carAvailability, double searchRadiusInKm) {
    // Depending upon whether we have enabled cache server, use cache or DB calls.
    // This is useful to skip cache server for tests.
    return getCarsInLocationFromDb(geoLocation, carAvailability, searchRadiusInKm);
  }

  // TODO: CRIO_TASK_MODULE_SQL.1: Implement getCarsInLocationFromDb() function.
  // Input:
  //   geoLocation - A valid geolocation.
  //   carAvailability - Car availability type that we have to search for.
  //   searchRadiusInKm - Valid search radius.
  // Output:
  // List<CarStatus>
  //   1. Array of cars (carStatus objects) that have the given carAvailability type & are within
  //      the specified search radius (inclusive).
  //   2. Empty array, if there are no available cars or in case of invalid input.
  // Use the Haversine formula shown in GlobalConstants.HAVER_SINE_FORMULA to calculate the distance
  // between two lat/longs. There are definitely more accurate ways to calculate distance,
  // but make sure your submission code uses this formula.

  public double getHaversineDistance(double lat1, double lon1, double lat2, double lon2) {
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

  public double haverDistance(GeoLocation g1, GeoLocation g2) {
    return getHaversineDistance(g1.getLatitude(), g1.getLongitude(), g2.getLatitude(),
        g2.getLongitude());
  }

  public boolean fallsInRadius(CarStatusTable c, GeoLocation geoLocation, double radius) {
    return haverDistance(c.getGeoLocation(), geoLocation) < radius;
  }

  private List<CarStatus> getCarsInLocationFromDb(GeoLocation geoLocation,
      CarStatus.CarAvailability carAvailability, double searchRadiusInKm) {

    List<CarStatus> carStatuses = new ArrayList<>();
    // query to get cars within radius
    //@NamedQuery(name="",query="")

    EntityManagerFactory emf = GlobalConstants.getEntityManagerFactory();
    EntityManager em = emf.createEntityManager();
    try {
      //String queryString="SELECT c FROM CarStatusTable c
      // WHERE c.getCarAvailability() = :carAvailability";
      String queryString = "FROM CarStatusTable c";
      //GlobalConstants.SEARCH_RADIUS_CARS_AVAILABLE_IN_KM;
      TypedQuery<CarStatusTable> query = em.createQuery(queryString, CarStatusTable.class);
      //query.setParameter("carAvailability", CarAvailability.CAR_AVAILABLE);
      List<CarStatusTable> carStatusesTable = query.getResultList();
      if (carStatusesTable == null) {
        return carStatuses;
      }
      for (CarStatusTable c : carStatusesTable) {
        //System.out.println(c.getName());
        if (fallsInRadius(c, geoLocation, searchRadiusInKm)
            && c.getCarAvailability() == carAvailability) {
          CarStatus carStatus = FormatConverters.convertCarStatusTableIntoCarStatus(c);
          carStatuses.add(carStatus);
        }
      }
      // carStatuses=carStatusesTable;
      // return carStatuses;
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } finally {
      em.close();
    }
    return carStatuses;
  }


  public List<CarStatus> getCarsInLocationOfCarType(GeoLocation geoLocation, Car.CarType carType) {
    // Connect to SQL server and read the table.
    return getCarsInLocationOfCarTypeFromDb(geoLocation, carType);
  }

  // TODO: CRIO_TASK_MODULE_SCHEDULETRIP.5: Implement getCarsInLocationOfCarTypeFromDb().
  // Returns available cars of a specific type near the location ordered by their proximity to
  // the location.
  // Input:
  //   geoLocation - A valid geolocation.
  //   carType - Valid car type.
  // Output:
  //   1. Array of car statuses if there are cars available of the specific type near
  //      the geolocation.
  //      The cars must be ordered by their proximity to the given location; ie. cars closer by
  //      are first in the list.
  //   2. Empty array, if there are no available cars of this type or in case of invalid input.
  private List<CarStatus> getCarsInLocationOfCarTypeFromDb(
      GeoLocation geoLocation, Car.CarType carType) {
    return null;
  }

}
