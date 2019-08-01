/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package controllers;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FareEstimatesController {

  @RequestMapping(value = "/qride/v1/fare_estimates")
  public ResponseEntity<String> fareEstimates(
      @Valid @RequestBody final String fareEstimatesRequest) {
    // To use ScheduleRide from Android app, some dummy implementation is needed for FareEstimates.
    // When you actually implement FareEstimates API as a part of subsequent modules,
    // you should replace this implementation.
    return ResponseEntity.status(HttpStatus.OK)
        .body("{\n"
            + "  \"estimatesForCarTypes\": [\n"
            + "    {\n"
            + "      \"estimateForCarType\": {\n"
            + "        \"carType\": \"CAR_TYPE_HATCHBACK\",\n"
            + "        \"tripPrice\": 129.00\n"
            + "      }\n"
            + "    },\n"
            + "    {\n"
            + "      \"estimateForCarType\": {\n"
            + "        \"carType\": \"CAR_TYPE_SEDAN\",\n"
            + "        \"tripPrice\": 140.00\n"
            + "      }\n"
            + "    },\n"
            + "    {\n"
            + "      \"estimateForCarType\": {\n"
            + "        \"carType\": \"CAR_TYPE_MINIVAN\",\n"
            + "        \"tripPrice\": 181.00\n"
            + "      }\n"
            + "    }\n"
            + "  ]\n"
            + "}\n");
  }
}
