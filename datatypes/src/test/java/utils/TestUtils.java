/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package utils;

import location.GeoLocation;

public class TestUtils {

  public static GeoLocation createGeoLocationObject(Double latitude, Double longitude) {
    GeoLocation geoLocation = new GeoLocation();
    geoLocation.setLatitude(latitude);
    geoLocation.setLongitude(longitude);
    return geoLocation;
  }

}
