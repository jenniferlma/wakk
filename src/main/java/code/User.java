package code;

import com.sun.xml.internal.bind.v2.TODO;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class User {

    private long _userId;
    private long _userPhoto;

    private String _userName;
    private String _externalId;
    private String _email;
    private String _passWord;

    private Set<Long> _groupList;
    private Set<Long> _contentList;

    private boolean empty;
    private boolean deleted;

    public User() {


    }
    public static User buildFromId(long internalId){

        //TODO make user object from db queries

        return new User();
    }

    public long get_userId() {
        return _userId;
    }


    public void set_userId(long _userId) {
        this._userId = _userId;
    }


    public String get_userName() {
        return _userName;
    }


    public void set_userName(String _userName) {
        this._userName = _userName;
    }


    public String get_externalId() {
        return _externalId;
    }


    public void set_externalId(String _externalId) {
        this._externalId = _externalId;
    }


    public Set<Long> get_groupList() {
        return _groupList;
    }


    public void set_groupList(Set<Long> _groupList) {
        this._groupList = _groupList;
    }


    public String get_email() {
        return _email;
    }


    public void set_email(String _email) {
        this._email = _email;
    }


    public String get_passWord() {
        return _passWord;
    }


    public void set_passWord(String _passWord) {
        this._passWord = _passWord;
    }


    public long get_userPhoto() {
        return _userPhoto;
    }


    public void set_userPhoto(long _userPhoto) {
        this._userPhoto = _userPhoto;
    }

    public User buildUser() {

        return null;
    }

    public JsonObject asJson(){

        JsonObject json = new JsonObject();

        json.put("userId", _userId);
        json.put("userPhoto", _userPhoto);
        json.put("userName",  _userName);
        json.put("externalId", _externalId);
        json.put("email", _email);
        json.put("passPhrase", _passWord);



        List<Long> groups = new ArrayList<>(_groupList);
        List<Long> content = new ArrayList<>(_contentList);

        json.put("groupList", new JsonArray(groups));
        json.put("contentList", new JsonArray(content));

        return json;

    }

    public String toString(){

        return asJson().toString();
    }

    public boolean empty(){
        return empty;
    }


}