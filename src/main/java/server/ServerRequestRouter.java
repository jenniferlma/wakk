package server;

import code.Content;
import code.Group;
import code.IContent;
import code.User;
import db.ObjectSupplier;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import util.FunctionUtil;

import static util.Constants.*;

/**
 * Created by Eric Blair on 10/8/2015.
 */
@SuppressWarnings("UnnecessaryUnboxing")
public class ServerRequestRouter implements Handler<HttpServerRequest> {

    private ObjectSupplier _objectSupplier;

    public ServerRequestRouter(ObjectSupplier objectSupplier){
        _objectSupplier = objectSupplier;
    }

    @Override
    public void handle(HttpServerRequest request) {

        System.out.println("URI requested " + request.absoluteURI());
        multiPlexHttpRequest(request);

    }



    public void handleGet(HttpServerRequest request){
        String internalIdAsString = request.getHeader(TYPE);
        String objectType = request.getHeader(INTERNAL_ID);

        switch (objectType){
            case USER :getUser(request,Long.valueOf(internalIdAsString).longValue());
                break;
            case CONTENT:getContent(request,Long.valueOf(internalIdAsString).longValue());
                break;
            case GROUP:getGroup(request, Long.valueOf(internalIdAsString).longValue());
                break;
            //TODO Make this a log, add more info
            default: System.out.println("Unsupported Get");
        }
    }
    public void handlePost(HttpServerRequest request){


        String objectType = request.getHeader(INTERNAL_ID);
        switch (objectType){
            case USER :postUser(request);
                break;
            case CONTENT:postContent(request);
                break;
            case GROUP:postGroup(request);
                break;
            //TODO Make this a log, add more info
            default: System.out.println("Unsupported Get");
        }
    }
    public void handlePut(HttpServerRequest request){
        String internalIdAsString = request.getHeader(TYPE);
        String objectType = request.getHeader(INTERNAL_ID);
        switch (objectType){
            case USER :putUser(request,Long.valueOf(internalIdAsString).longValue());
                break;
            case CONTENT:putContent(request,Long.valueOf(internalIdAsString).longValue());
                break;
            case GROUP:putGroup(request,Long.valueOf(internalIdAsString).longValue());
                break;
            //TODO Make this a log, add more info
            default: System.out.println("Unsupported Get");
        }
    }
    public void handleDelete(HttpServerRequest request){
        String internalIdAsString = request.getHeader(TYPE);
        String objectType = request.getHeader(INTERNAL_ID);

        switch (objectType){
            case USER :deleteUser(request,Long.valueOf(internalIdAsString).longValue());
                break;
            case CONTENT:deleteContent(request,Long.valueOf(internalIdAsString).longValue());
                break;
            case GROUP:deleteUser(request,Long.valueOf(internalIdAsString).longValue());
                break;
            //TODO Make this a log, add more info
            default: System.out.println("Unsupported Delete");
        }
    }

    public void handleUnsupported(HttpServerRequest request){
        String internalIdAsString = request.getHeader(TYPE);
        String objectType = request.getHeader(INTERNAL_ID);


    }
    public void multiPlexHttpRequest(HttpServerRequest request){



        HttpMethod verb = request.method();
        switch(verb){

            case GET:  handleGet(request);
                break;
            case POST:  handlePost(request);
                break;
            case PUT:  handlePut(request);
                break;
            case DELETE:  handleDelete(request);
                break;
            default:  ;
                break;
        }

    }

    public void getUser(HttpServerRequest request,long internalId){

        User user = _objectSupplier.getUser(internalId);

        if(user.empty()){
            request.response().setStatusCode(404).end("404, Not Found!");
        }
        JsonObject userAsJson = user.asJson();
        request.response().end(userAsJson.toString());

    }

    //TODO: the rest of the methods
    public void putUser(HttpServerRequest request,long internalId){
        MultiMap params = request.params();
        JsonObject requestParamsJson  = FunctionUtil.multiMapToJson(params);



    }
    public void postUser(HttpServerRequest request){

        MultiMap params = request.params();
        JsonObject requestParamsJson  = FunctionUtil.multiMapToJson(params);

    }
    public void deleteUser(HttpServerRequest request,long internalId){}

    public void getContent(HttpServerRequest request,long internalId){

        IContent content = _objectSupplier.getContent(internalId);

        if(content.empty()){
            request.response().setStatusCode(404).end("404, Not Found!");
        }
        JsonObject userAsJson = content.asJson();
        request.response().end(userAsJson.toString());
    }
    public void putContent(HttpServerRequest request,long internalId){

        MultiMap params = request.params();
        JsonObject requestParamsJson  = FunctionUtil.multiMapToJson(params);

    }
    public void postContent(HttpServerRequest request){
        MultiMap params = request.params();
        JsonObject requestParamsJson  = FunctionUtil.multiMapToJson(params);

    }
    public void deleteContent(HttpServerRequest request,long internalId){}

    public void getGroup(HttpServerRequest request,long internalId){

        Group group = _objectSupplier.getGroup(internalId);

        if(group.empty()){
            request.response().setStatusCode(404).end("404, Not Found!");
        }
        JsonObject userAsJson = group.asJson();
        request.response().end(userAsJson.toString());
    }
    public void postGroup(HttpServerRequest request){}
    public void putGroup(HttpServerRequest request,long internalId){}
    public void deleteGroup(HttpServerRequest request,long internalId){}

}
