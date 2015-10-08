package db;

import code.User;

/**
 * Created by Eric Blair on 10/8/2015.
 */
public class CacheEntry<T> implements ObjectCacheEntry{

    private boolean _dirty;
    private T _object;

    @Override
    public void setDirty( boolean bool){
       _dirty = bool;
    }

    @Override
    public boolean isDirty(){
        return _dirty;
    }

    public T get(){
        return _object;
    }

    public CacheEntry(T object){
        _dirty = false;
        _object = object;
    }


}
