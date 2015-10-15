package db;

import code.*;
import code.Content;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import util.Constants;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Eric Blair on 10/8/2015.
 */
public class ObjectSupplier {

    private Map<Long,CacheEntry> _objectCache;


    public ObjectSupplier(){
        _objectCache = new HashMap<>();
    }


    public User getUser(long internalId){

        CacheEntry<User> cacheEntry = _objectCache.get(new Long(internalId));
        try{
            return cacheEntry.isDirty()? User.buildFromId(internalId) : cacheEntry.get();
        }catch(SQLException e){
            //TODO make this a logging statement
            System.out.println("FAILED GETTING USER  " + internalId + " FROM DB"  );
        }

        return cacheEntry.get();
    }

    public IContent getContent(long internalId){
        CacheEntry<IContent> cacheEntry = _objectCache.get(new Long(internalId));

        return cacheEntry.isDirty()? Content.buildFromId(internalId) : cacheEntry.get();
    }

    public Group getGroup(long internalId){
        CacheEntry<Group> cacheEntry = _objectCache.get(internalId);
        try{
            return cacheEntry.isDirty()? Group.buildFromId(internalId) : cacheEntry.get();
        }catch(SQLException e){
            //TODO make this a logging statement
            System.out.println("FAILED GETTING GROUP  " + internalId + " FROM DB"  );
        }
        return cacheEntry.get();
    }

    public void delete(long  internalId){

        _objectCache.remove(internalId);
    }

    public void makeUser(JsonObject json) {

        CacheEntry<User> user;

        user = new CacheEntry<>(User.buildFromJson(json));
        long key = user.get().get_internalId();
        _objectCache.put(key, user);
        //TODO write user to db


    }

    public void updateUser(JsonObject json) {

        long key = json.getLong(Constants.USER_ID);
        CacheEntry<User> user = _objectCache.get(key);
        JsonObject currentUserJson = user.get().asJson();
        Iterator<Map.Entry<String,Object>> itCurrent = currentUserJson.iterator();
        Iterator<Map.Entry<String,Object>> itUpdate = json.iterator();
        JsonObject newUserObject = new JsonObject();
        while(itCurrent.hasNext()){
            Map.Entry<String,Object> entryCurrent = itCurrent.next();
            Map.Entry<String,Object> entryUpdate = itUpdate.next();

            if(entryCurrent.getKey().equals("grouplist") || entryCurrent.getKey().equals("contentlist")){
                JsonArray updatedList = new JsonArray();
                JsonArray currentValues = ((JsonArray)entryCurrent.getValue());
                updatedList.addAll(currentValues);
                JsonArray updateValues = ((JsonArray)entryUpdate.getValue());
                updatedList.addAll(updateValues);
                newUserObject.put(entryCurrent.getKey(), updatedList);
                continue;
            }
            if("".equals(entryUpdate.getValue())){
                newUserObject.put(entryCurrent.getKey(), entryCurrent.getValue());
            }
            else{
                newUserObject.put(entryCurrent.getKey(), entryUpdate.getValue());
            }

                user = new CacheEntry<>(User.buildFromJson(newUserObject));


        }


        _objectCache.put(key, user);


    }

    //TODO implement these
    public void makeGroup(JsonObject json) {
        CacheEntry<Group> group = new CacheEntry<>(Group.buildFromJson(json));
        long key = group.get().get_internalId();
        _objectCache.put(key, group);
        //TODO write group to db
    }

    public void updateGroup(JsonObject json) {

        long key = json.getLong(Constants.USER_ID);
        CacheEntry<Group> group = _objectCache.get(key);
        JsonObject currentGroupJson = group.get().asJson();
        Iterator<Map.Entry<String,Object>> itCurrent = currentGroupJson.iterator();
        Iterator<Map.Entry<String,Object>> itUpdate = json.iterator();
        JsonObject newGroupObject = new JsonObject();
        while(itCurrent.hasNext()){
            Map.Entry<String,Object> entryCurrent = itCurrent.next();
            Map.Entry<String,Object> entryUpdate = itUpdate.next();
            if(entryCurrent.getKey().equals("userlist") || entryCurrent.getKey().equals("contentlist")){
                JsonArray updatedList = new JsonArray();
                JsonArray currentValues = ((JsonArray)entryCurrent.getValue());
                updatedList.addAll(currentValues);
                JsonArray updateValues = ((JsonArray)entryUpdate.getValue());
                updatedList.addAll(updateValues);
                newGroupObject.put(entryCurrent.getKey(), updatedList);
                continue;
            }
            if("".equals(entryUpdate.getValue())){
                newGroupObject.put(entryCurrent.getKey(), entryCurrent.getValue());
            }
            else{
                newGroupObject.put(entryCurrent.getKey(), entryUpdate.getValue());
            }
            group = new CacheEntry<>(Group.buildFromJson(newGroupObject));
            //TODO write group to db
        }


        _objectCache.put(key, group);


    }

    public void makeContent(JsonObject json) {

        CacheEntry<IContent> content = new CacheEntry<>(Content.buildFromJson(json));
        long key = content.get().getInternalId();
        _objectCache.put(key, content);
        //TODO write content to db
    }


    public void updateContent(JsonObject json) {

        long key = json.getLong(Constants.USER_ID);
        CacheEntry<IContent> content = _objectCache.get(key);
        JsonObject currentContentJson = content.get().asJson();
        Iterator<Map.Entry<String,Object>> itCurrent = currentContentJson.iterator();
        Iterator<Map.Entry<String,Object>> itUpdate = json.iterator();
        JsonObject newContentObject = new JsonObject();
        while(itCurrent.hasNext()){
            Map.Entry<String,Object> entryCurrent = itCurrent.next();
            Map.Entry<String,Object> entryUpdate = itUpdate.next();

            if("".equals(entryUpdate.getValue())){
                newContentObject.put(entryCurrent.getKey(), entryCurrent.getValue());
            }
            else{
                newContentObject.put(entryCurrent.getKey(), entryUpdate.getValue());
            }
            content = new CacheEntry<>(Content.buildFromJson(newContentObject));
            //TODO write content to db
        }


        _objectCache.put(key, content);


    }

}
