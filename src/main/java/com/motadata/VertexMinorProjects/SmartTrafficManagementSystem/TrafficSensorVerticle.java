package com.motadata.VertexMinorProjects.SmartTrafficManagementSystem;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

import java.util.Random;

public class TrafficSensorVerticle extends AbstractVerticle {
  @Override
  public void start() {
    EventBus eventBus = vertx.eventBus();
    vertx.setPeriodic(200, id -> {
      System.out.println("trafic sensoring start " + Thread.currentThread().getName());
      JsonObject trafficData = new JsonObject()
        .put("road", "Highway-21")
        .put("congestion", new Random().nextInt(100));
      eventBus.publish("traffic.data", trafficData);
    });
  }
}

