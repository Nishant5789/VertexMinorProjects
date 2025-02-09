package com.motadata.VertexMinorProjects.ChatAppUsingTCP;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;
import java.util.Scanner;

public class ChatClient extends AbstractVerticle {
  private NetSocket socket;

  @Override
  public void start() {
    NetClient client = vertx.createNetClient();

    client.connect(4321, "localhost").onComplete(res -> {
      if (res.succeeded()) {
        System.out.println("Connected to chat server!");
        socket = res.result();

        socket.handler(buffer -> System.out.println("Server: " + buffer.toString()));

        startUserInput();
      } else {
        System.out.println("Failed to connect: " + res.cause().getMessage());
      }
    });
  }

  private void startUserInput() {
    Scanner scanner = new Scanner(System.in);
    while (true) {
      String message = scanner.nextLine();
      if ("exit".equalsIgnoreCase(message)) {
        socket.close();
        System.out.println("Disconnected from server.");
        break;
      }
      socket.write(Buffer.buffer(message));
    }
    scanner.close();
  }

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new ChatClient());
  }
}
