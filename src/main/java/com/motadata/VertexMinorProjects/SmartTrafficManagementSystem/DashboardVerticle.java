package com.motadata.VertexMinorProjects.SmartTrafficManagementSystem;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

public class DashboardVerticle extends AbstractVerticle {
  @Override
  public void start() {
    EventBus eventBus = vertx.eventBus();
    eventBus.consumer("traffic.update", message -> {
      JsonObject trafficData = (JsonObject) message.body();
      System.out.println("Dashboard Update: " + trafficData);
    });
  }
}
