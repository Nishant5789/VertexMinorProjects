package com.motadata.VertexMinorProjects.SmartTrafficManagementSystem;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

public class EmergencyServiceVerticle extends AbstractVerticle {
  @Override
  public void start() {
    EventBus eventBus = vertx.eventBus();
    eventBus.consumer("traffic.update", message -> {
      JsonObject trafficData = (JsonObject) message.body();
      if (trafficData.getInteger("congestion") > 80) {
        System.out.println("Emergency Alert: Notifying authorities!");
      }
    });
  }
}

