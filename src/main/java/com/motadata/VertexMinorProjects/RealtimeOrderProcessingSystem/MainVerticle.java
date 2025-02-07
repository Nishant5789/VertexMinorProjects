package com.motadata.VertexMinorProjects.RealtimeOrderProcessingSystem;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class MainVerticle extends AbstractVerticle {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(1));
    vertx.deployVerticle(new MainVerticle());
  }
  @Override
  public void start(Promise<Void> startPromise) {
    vertx.deployVerticle(new HttpVerticle());
    vertx.deployVerticle(new OrderProcessingVerticle());
    vertx.deployVerticle(new PaymentWorkerVerticle(), res -> {
      if (res.succeeded()) {
        System.out.println("Payment Worker Deployed Successfully!");
      }
    });
    vertx.deployVerticle(new InventoryVerticle());
    vertx.deployVerticle(new NotificationVerticle());

    startPromise.complete();
  }

}
