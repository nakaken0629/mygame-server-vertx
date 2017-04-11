package red.itvirtuoso.mygame;

import io.vertx.core.AbstractVerticle;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start() {
    vertx.createHttpServer()
        .requestHandler(req -> req.response().end("Hello MyGame Vert.x!"))
        .listen(8080);
  }

}
