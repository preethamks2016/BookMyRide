/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package controllers;

import globals.GlobalConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tables.setup.TableSetup;

@SpringBootApplication
public class Application {

  private static final Log logger = LogFactory.getLog(Application.class);

  // TODO: CRIO_TASK_MODULE_RESTAPI.1: Run main to start your app server!
  // Run this function to start your application.
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);

    // Setup the persistence context first before initializing the tables.
    GlobalConstants.initDatabase(GlobalConstants.PERSISTENCE_UNIT_NAME);

    // Setup the tables.
    TableSetup tableSetup = new TableSetup();

    tableSetup.init();

    // TIP:MODULE_RESTAPI: You can enable tracing for different classes & use logs for
    // debugging.
    // Make sure to enable tracing for your class in application.properties file.
    if (logger.isTraceEnabled()) {
      // TIP:MODULE_RESTAPI: If your server starts successfully,
      // you can find the following message in the logs.
      logger.trace("Congrats! Your app server has started");
    }
  }
}
