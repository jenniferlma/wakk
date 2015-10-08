package db;

/**
 * Created by Eric Blair on 10/8/2015.
 */
public class ContentCacheEntry implements ObjectCacheEntry {

    private boolean _dirty;
    private Content _content;

    @Override
    public void setDirty( boolean bool){
        _dirty = bool;
    }

    @Override
    public boolean isDirty(){
        return _dirty;
    }

    public Content get(){
        return _Content;
    }

    public ContentCacheEntry(Content content){
        _dirty = false;
        _content = content;
    }


}
