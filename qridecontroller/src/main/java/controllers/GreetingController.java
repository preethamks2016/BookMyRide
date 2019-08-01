/*
 * Copyright (c) Crio.Do 2018. All rights reserved
 */

package controllers;

import greeting.Greeting;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  @RequestMapping("/greeting")
  public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
    return new Greeting(counter.incrementAndGet(), String.format(template, name));
  }
}

// TODO: CRIO_TASK_MODULE_RESTAPI.2 - Make sure your REST API server works fine,
// by accessing
// http://localhost:8080/greeting?name=testing after running main function in Application.java.
