package com.motadata.VertexMinorProjects.RealtimeOrderProcessingSystem;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

public class InventoryVerticle extends AbstractVerticle {
  @Override
  public void start() {
    vertx.eventBus().consumer("inventory.update", message -> {
      JsonObject order = (JsonObject) message.body();
      System.out.println("Updating Inventory for Order: " + order);
    });
  }
}

