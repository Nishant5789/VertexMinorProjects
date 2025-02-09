package com.motadata.VertexMinorProjects.RealtimeOrderProcessingSystem;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

public class NotificationVerticle extends AbstractVerticle {
  @Override
  public void start() {
    vertx.eventBus().consumer("notification.send", message -> {
      System.out.println("consume task in notification verticle " + Thread.currentThread().getName());
      JsonObject order = (JsonObject) message.body();
      System.out.println("Sending Notification: Order Confirmed for " + order.getString("customerName"));
    });
  }
}

