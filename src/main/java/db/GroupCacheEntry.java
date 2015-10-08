package db;
import code.Group;


/**
 * Created by Eric Blair on 10/8/2015.
 */
public class GroupCacheEntry implements  ObjectCacheEntry{

    private boolean _dirty;
    private Group _group;

    @Override
    public void setDirty( boolean bool){
        _dirty = bool;
    }

    @Override
    public boolean isDirty(){
        return _dirty;
    }

    public Group get(){
        return _group;
    }

    public GroupCacheEntry(Group group){
        _dirty = false;
        _group = group;
    }
}
