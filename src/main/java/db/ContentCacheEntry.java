package db;
import code.IContent;
/**
 * Created by Eric Blair on 10/8/2015.
 */
public class ContentCacheEntry implements ObjectCacheEntry {

    private boolean _dirty;
    private IContent _content;

    @Override
    public void setDirty( boolean bool){
        _dirty = bool;
    }

    @Override
    public boolean isDirty(){
        return _dirty;
    }

    public IContent get(){
        return _content;
    }

    public ContentCacheEntry(IContent content){
        _dirty = false;
        _content = content;
    }


}
