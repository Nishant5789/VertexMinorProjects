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
    System.out.println("request to Payment verticle "+Thread.currentThread().getName());
    vertx.eventBus().request("payment.process", order, reply -> {
      if (reply.succeeded()) {
        System.out.println("Payment Success! Notifying Inventory & Notification Service");
        System.out.println("publish to inventry  & notification "+Thread.currentThread().getName());
        // Publish success event
        vertx.eventBus().publish("inventory.update", order);
        vertx.eventBus().publish("notification.send", order);
        System.out.println("responce come from Payment verticle "+Thread.currentThread().getName());
        message.reply(new JsonObject().put("status", "Order Confirmed"));
      } else {
        message.fail(500, "Payment Failed");
      }
    });
  }
}
