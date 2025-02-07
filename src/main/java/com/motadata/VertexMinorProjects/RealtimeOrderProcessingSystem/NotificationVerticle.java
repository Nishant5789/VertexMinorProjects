package com.motadata.VertexMinorProjects.RealtimeOrderProcessingSystem;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

public class NotificationVerticle extends AbstractVerticle {
  @Override
  public void start() {
    vertx.eventBus().consumer("notification.send", message -> {
      JsonObject order = (JsonObject) message.body();
      System.out.println("Sending Notification: Order Confirmed for " + order.getString("customerName"));
    });
  }
}

