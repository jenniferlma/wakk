package db;

import code.Content;
import code.IContent;
import code.Group;
import code.User;
import io.vertx.core.json.JsonObject;

import java.util.HashMap;
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


    }

    //TODO implement these
    public void makeGroup(JsonObject json) {

    }

    public void makeContent(JsonObject json) {
    }




}
