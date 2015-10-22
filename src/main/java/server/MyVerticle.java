package server;

import code.User;
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
                        Long internalId = 000000000000000000L;
                        try {
                            User user = new User(internalId);
                            //String user_des = user.toString();
                            String user_des = "username: ";
                            user_des += user.get_userName();
                            user_des += "<br>";
                            user_des += "id: ";
                            user_des += user.get_externalId();
                            user_des = "<h1>" + user_des + "</h1>";
                            response.write(user_des);
                            System.out.println("HI");
                        }
                        catch (Exception e) {System.out.println(e.getStackTrace());}
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
