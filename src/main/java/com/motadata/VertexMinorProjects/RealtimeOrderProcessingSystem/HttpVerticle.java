package com.motadata.VertexMinorProjects.RealtimeOrderProcessingSystem;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class HttpVerticle extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) {
    Router router = Router.router(vertx);
    router.post("/order").handler(this::handleOrderRequest);
    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8080, http -> {
        if (http.succeeded()) {
          startPromise.complete();
          System.out.println("HTTP Server started on port 8080");
        } else {
          startPromise.fail(http.cause());
        }
      });
  }

  private void handleOrderRequest(RoutingContext ctx) {
    ctx.request().bodyHandler(buffer-> {
      JsonObject order = buffer.toJsonObject();
      if (order == null) {
        ctx.response().setStatusCode(400).end("Invalid JSON payload");
        return;
      }
      System.out.println("request to order verticle "+Thread.currentThread().getName());
      vertx.eventBus().request("order.process", order, reply -> {
        if (reply.succeeded()) {
//          System.out.println("------------------");
//          System.out.println(reply.result());
//          System.out.println(reply.result().body());
//          System.out.println(reply.result().body().toString());
//          System.out.println("------------------");
          System.out.println("reply come from order verticle "+Thread.currentThread().getName());
          ctx.response().setStatusCode(200).end(reply.result().body().toString());
        } else {
          ctx.response().setStatusCode(500).end("Order Processing Failed");
        }
      });
    });
  }
}

