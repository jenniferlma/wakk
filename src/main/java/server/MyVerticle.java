package server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

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
                String testPage = request.path(); // could also use request.uri();
                System.out.println(testPage);
                HttpServerResponse response = request.response();
                switch (testPage) {
                    case "/testUser":
                        response.setStatusCode(200);
                        response.headers()
                                .add("Content-Length", String.valueOf(57))
                                .add("Content-Type", "text/html");
                        response.write("<h1>User</h1>");
                        response.end();
                        break;
                    case "/testGroup":
                        response.setStatusCode(200);
                        response.headers()
                                .add("Content-Length", String.valueOf(57))
                                .add("Content-Type", "text/html");
                        response.write("<h1>Group</h1>");
                        response.end();
                        break;
                    case "/testContent":
                        response.setStatusCode(200);
                        response.headers()
                                .add("Content-Length", String.valueOf(57))
                                .add("Content-Type", "text/html");
                        response.write("<h1>Content</h1>");
                        response.end();
                        break;
                    //TODO Make this a log, add more info
                    default:
                        System.out.println("Unsupported Get");
                }

            } // end inline handle method
        }); // end requestHandler call on _httpServer

        _httpServer.listen(9999, "localhost");
    }// end start method


    @Override
    public void stop(Future stopFuture) throws Exception {
        System.out.println("Http server stopped!");
    }
}
