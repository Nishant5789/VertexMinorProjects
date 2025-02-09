package com.motadata.VertexMinorProjects.ChatAppUsingTCP;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServer extends AbstractVerticle {
  private Set<NetSocket> clients = ConcurrentHashMap.newKeySet(); // Thread-safe client list

  @Override
  public void start() {
    NetServer server = vertx.createNetServer();

    server.connectHandler(socket -> {
      clients.add(socket);
      System.out.println("New client connected!");

      socket.handler(buffer -> {
        String message = buffer.toString();
        System.out.println("Received: " + message);
        broadcastMessage(socket, message);
      });

      socket.closeHandler(v -> {
        clients.remove(socket);
        System.out.println("Client disconnected.");
      });

      socket.exceptionHandler(err -> {
        System.out.println("Socket error: " + err.getMessage());
        clients.remove(socket);
      });

    });

    server.listen(4321, "localhost").onComplete(res -> {
      if (res.succeeded()) {
        System.out.println("Chat server is running on port 4321...");
      } else {
        System.out.println("Failed to start server: " + res.cause().getMessage());
      }
    });
  }

  private void broadcastMessage(NetSocket sender, String message) {
    for (NetSocket client : clients) {
      if (client != sender) { // Don't send message back to sender
        client.write("Client: " + message);
      }
    }
  }

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new ChatServer());
  }
}

