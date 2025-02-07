package com.motadata.VertexMinorProjects.RealtimeOrderProcessingSystem;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.core.eventbus.Message;

public class PaymentWorkerVerticle extends AbstractVerticle {
  @Override
  public void start() {
    vertx.eventBus().consumer("payment.process", this::processPayment);
  }

  private void processPayment(Message<JsonObject> message) {
    JsonObject order = message.body();
    System.out.println("Processing Payment for Order: " + order);

    // Simulate heavy processing (fraud detection, payment gateway call)
    vertx.setTimer(3000, id -> {
      System.out.println("Payment Successful for Order: " + order);
      message.reply(new JsonObject().put("status", "Payment Successful"));
    });
  }
}

