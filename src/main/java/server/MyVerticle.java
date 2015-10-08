package server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;

public class MyVerticle extends AbstractVerticle {

    private HttpServer _httpServer = null;



    @Override
    public void start() throws Exception {
        _httpServer = vertx.createHttpServer();
        System.out.println("Http server is started!");
        _httpServer.requestHandler(new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest request) {
                System.out.println("incoming request!");
            }
        });

        _httpServer.listen(9999);
    }


    @Override
    public void stop(Future stopFuture) throws Exception {
        System.out.println("Http server stopped!");
    }
}
