package db;

import code.User;

/**
 * Created by Eric Blair on 10/8/2015.
 */
public class UserCacheEntry implements ObjectCacheEntry{

    private boolean _dirty;
    private User _user;

    @Override
    public void setDirty( boolean bool){
       _dirty = bool;
    }

    @Override
    public boolean isDirty(){
        return _dirty;
    }

    public User get(){
        return _user;
    }

    public UserCacheEntry(User user){
        _dirty = false;
        _user = user;
    }


}
