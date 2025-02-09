package com.motadata.VertexMinorProjects.SmartTrafficManagementSystem;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

public class TrafficControlVerticle extends AbstractVerticle {
  @Override
  public void start() {
    EventBus eventBus = vertx.eventBus();
    eventBus.consumer("traffic.data", message -> {
      JsonObject trafficData = (JsonObject) message.body();
      Promise<Void> promise = Promise.promise();
      System.out.println("assign task to traffic control verticle " + Thread.currentThread().getName());

      eventBus.request("traffic.predict", trafficData, reply -> {
        if (reply.succeeded()) {
          JsonObject result = (JsonObject) reply.result().body();
          boolean highCongestion = result.getBoolean("highCongestion");
          System.out.println("Traffic Control: Adjusting lights. Congestion high: " + Thread.currentThread().getName() +" "+highCongestion);
          promise.complete();
        } else {
          promise.fail("Prediction failed");
        }
      });

      promise.future().onSuccess(v -> eventBus.publish("traffic.update", trafficData));
    });
  }
}

