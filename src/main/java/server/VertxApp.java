package server; /**
 * Created by wendyjan on 10/8/15.
 */
import io.vertx.core.Vertx;

public class VertxApp {

    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle("server.MyVerticle");
        // above line could also have been vertx.deployVerticle(new server.MyVerticle());
    }

}
