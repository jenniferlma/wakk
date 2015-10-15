package db;

import code.Content;
import code.IContent;
import code.Group;
import code.User;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
import util.Constants;

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

        return cacheEntry.isDirty()? User.buildFromId(internalId) : cacheEntry.get();
    }

    public IContent getContent(long internalId){
        CacheEntry<IContent> cacheEntry = _objectCache.get(new Long(internalId));

        return cacheEntry.isDirty()? Content.buildFromId(internalId) : cacheEntry.get();
    }

    public Group getGroup(long internalId){
        CacheEntry<Group> cacheEntry = _objectCache.get(new Long(internalId));

        return cacheEntry.isDirty()? Group.buildFromId(internalId) : cacheEntry.get();
    }

    public void delete(long  internalId){

        _objectCache.remove(internalId);
    }

    public void makeUser(JsonObject json) {

        CacheEntry<User> user = new CacheEntry<>(User.buildFromJson(json));
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

            if("".equals(entryUpdate.getValue())){
                newUserObject.put(entryCurrent.getKey(), entryCurrent.getValue());
            }
            else{
                newUserObject.put(entryCurrent.getKey(), entryUpdate.getValue());
            }
            user = new CacheEntry<>(User.buildFromJson(newUserObject));
            //TODO write user to db
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

    public void makeContent(JsonObject json) {
    }




}
