package code;

import io.vertx.core.json.JsonObject;

/**
 * Created by Eric Blair on 10/8/2015.
 */
public class Group {


    private boolean empty;
    private boolean deleted;

    public static Group buildFromId( Long internalId){
        return null;
    }

    public JsonObject asJson(){
        JsonObject groupAsJson = new JsonObject();
        return  groupAsJson;
    }

    public String toString(){
        return asJson().toString();
    }

    public boolean empty() {
        return empty;
    }
}
