package com.motadata.VertexMinorProjects.RealtimeOrderProcessingSystem;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.core.eventbus.Message;

public class OrderProcessingVerticle extends AbstractVerticle {
  @Override
  public void start() {
    vertx.eventBus().consumer("order.process", this::processOrder);
  }

  private void processOrder(Message<JsonObject> message) {
    JsonObject order = message.body();
    System.out.println("Processing Order: " + order);

    // Request payment processing
    vertx.eventBus().request("payment.process", order, reply -> {
      if (reply.succeeded()) {
        System.out.println("Payment Success! Notifying Inventory & Notification Service");

        // Publish success event
        vertx.eventBus().publish("inventory.update", order);
        vertx.eventBus().publish("notification.send", order);

        message.reply(new JsonObject().put("status", "Order Confirmed"));
      } else {
        message.fail(500, "Payment Failed");
      }
    });
  }
}
