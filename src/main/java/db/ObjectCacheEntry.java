package db;

public interface ObjectCacheEntry{

    boolean isDirty();
    void setDirty(boolean bool);
}