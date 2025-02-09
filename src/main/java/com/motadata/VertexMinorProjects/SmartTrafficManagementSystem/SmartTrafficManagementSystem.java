package com.motadata.VertexMinorProjects.SmartTrafficManagementSystem;

import io.vertx.core.*;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import java.util.Random;

public class SmartTrafficManagementSystem {
  public static void main(String[] args) {
    VertxOptions vertxOptions = new VertxOptions().setWorkerPoolSize(2); // Global Worker Pool
    Vertx vertx = Vertx.vertx(vertxOptions);

    vertx.deployVerticle(new TrafficSensorVerticle());
//    vertx.deployVerticle(new AIPredictionVerticle(), new DeploymentOptions().setWorker(true));
//    vertx.deployVerticle(new TrafficControlVerticle(), new DeploymentOptions().setWorker(false));
//    vertx.deployVerticle(new EmergencyServiceVerticle(), new DeploymentOptions().setWorker(true));
    vertx.deployVerticle(new DashboardVerticle());


    vertx.deployVerticle(new AIPredictionVerticle(), new DeploymentOptions().setWorker(true).setWorkerPoolSize(2));
    vertx.deployVerticle(new TrafficControlVerticle(), new DeploymentOptions().setWorker(true).setWorkerPoolSize(2));
    vertx.deployVerticle(new EmergencyServiceVerticle(), new DeploymentOptions().setWorker(true).setWorkerPoolSize(1));
//    vertx.deployVerticle(new DashboardVerticle());
  }
}

