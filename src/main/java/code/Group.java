package code;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import util.FunctionUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Eric Blair on 10/8/2015, modified by wendyjan on 10/14/2015.
 * Java Encapsulation of group
 */
public class Group {

    private long _internalId;
    private String _groupName; // group's username
    private String _description;
    private String _password;
    private long _groupPhoto;
    private long _groupLeader; // long user ID
    private Set<Long> _userList; // contains user IDs
    private Set<Long> _contentList; // contains content IDs
    private boolean _empty;
    private boolean _deleted;

    public Group(long internalId) throws SQLException {
        // todo for Kendra?
    }

    public Group(JsonObject json) {

        // TODO write this user object to dB

        _internalId = FunctionUtil.generateId();
        _groupName = json.getString("groupName");
        _description = json.getString("description");
        _password = json.getString("password");

        _groupPhoto = json.getLong("groupPhoto");
        _groupLeader = json.getLong("groupLeader");

        _userList = new HashSet<>(json.getJsonArray("userList").getList());
        _contentList = new HashSet<>(json.getJsonArray("contentList").getList());

        _empty = false;
        _deleted = false;
    }

    public static Group buildFromId(Long internalId) throws SQLException {
        return new Group(internalId);
    }



    public JsonObject asJson(){
        JsonObject groupAsJson = new JsonObject();

        groupAsJson.put("groupId", _internalId);
        groupAsJson.put("groupName",  _groupName);
        groupAsJson.put("description", _description);
        groupAsJson.put("password", _password);

        groupAsJson.put("groupPhoto", _groupPhoto);
        groupAsJson.put("groupLeader", _groupLeader);

        List<Long> users = new ArrayList<>(_userList);
        List<Long> content = new ArrayList<>(_contentList);

        groupAsJson.put("userList", new JsonArray(users));
        groupAsJson.put("contentList", new JsonArray(content));
        return  groupAsJson;
    }




    public String toString(){
        return asJson().toString();
    }

    public boolean empty() {
        return _empty;
    }

    public static Group buildFromJson(JsonObject json) {
        return new Group(json);
    }

    public long get_internalId() {
        return _internalId;
    }

    public String get_groupName() {
        return _groupName;
    }

    public void set_groupName(String _groupName) {
        this._groupName = _groupName;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public long get_groupPhoto() {
        return _groupPhoto;
    }

    public void set_groupPhoto(long _groupPhoto) {
        this._groupPhoto = _groupPhoto;
    }

    public long get_groupLeader() {
        return _groupLeader;
    }

    public void set_groupLeader(long _groupLeader) {
        this._groupLeader = _groupLeader;
    }

    public Set<Long> get_userList() {
        return _userList;
    }

    public void append_to_userList(Long newUser) {
        _contentList.add(newUser);
    }

    public void remove_from_userList(Long oldUser) {
        _contentList.remove(oldUser);
    }

    public Set<Long> get_contentList() {
        return _contentList;
    }

    public void append_to_contentList(Long newContent) {
        _contentList.add(newContent);
    }

    public void remove_from_contentList(Long oldContent) {
        _contentList.remove(oldContent);
    }

    public boolean is_empty() {
        return _empty;
    }

    public void set_empty(boolean _empty) {
        this._empty = _empty;
    }

    public boolean is_deleted() {
        return _deleted;
    }

    public void set_deleted(boolean _deleted) {
        this._deleted = _deleted;
    }


} // end Group class

