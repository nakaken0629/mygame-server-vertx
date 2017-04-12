package red.itvirtuoso.mygame;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.stomp.Destination;
import io.vertx.ext.stomp.StompServer;
import io.vertx.ext.stomp.StompServerHandler;

public class MainVerticle extends AbstractVerticle {
  private StompServer server;

  @Override
  public void start() {
    server = StompServer.create(vertx)
      .handler(StompServerHandler.create(vertx)
        .destinationFactory((v, name) -> {
          if (name.startsWith("/queue")) {
            return Destination.queue(vertx, name);
          } else {
            return Destination.topic(vertx, name);
          }
        }))
      .listen(1234, "0.0.0.0", ar -> {
        if (ar.failed()) {
          System.out.println("Failing to start the STOMP server : " + ar.cause().getMessage());
        } else {
          System.out.println("Ready to receive STOMP frames");
        }
      });
  }

  @Override
  public void stop() throws Exception {
    server.close(ar -> {
      if (ar.succeeded()) {
        System.out.println("The STOMP server has been closed");
      } else {
        System.out.println("The STOMP server failed to close : " + ar.cause().getMessage());
      }
    });
  }
}
