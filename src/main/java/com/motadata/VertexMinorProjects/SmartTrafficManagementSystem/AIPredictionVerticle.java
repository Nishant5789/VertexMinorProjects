package com.motadata.VertexMinorProjects.SmartTrafficManagementSystem;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

public class AIPredictionVerticle extends AbstractVerticle {
  @Override
  public void start() {
    EventBus eventBus = vertx.eventBus();

    eventBus.consumer("traffic.predict", message -> {
    System.out.println("task assign at predication verticle " + Thread.currentThread().getName() );
      JsonObject data = (JsonObject) message.body();
      vertx.executeBlocking(promise -> {
        int congestionLevel = data.getInteger("congestion");
        boolean highCongestion = congestionLevel > 70;
        System.out.println("blocking task at predication verticle " + Thread.currentThread().getName() );
        promise.complete(new JsonObject().put("highCongestion", highCongestion));
      }, res -> message.reply(res.result()));
      System.out.println("blocking task complete at predication verticle " + Thread.currentThread().getName() );
    });
  }
}
