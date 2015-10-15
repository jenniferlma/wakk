package code;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import util.FunctionUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.*;
import java.util.*;

import static java.sql.DriverManager.getConnection;

public class User {

    private long _internalId;
    private String _userPhoto;

    private String _userName;
    private String _externalId;
    private String _email;
    private String _passWord;

    private Set<Long> _groupList;
    private Set<Long> _contentList;

    private boolean _empty;
    private boolean _deleted;

    private Connection connection;

    public User() {

    }
    public static User buildFromId(long internalId) throws SQLException {
        // TODO query db to get item using internalId
        //Statement stmt = conn.createStatement();
        //ResultSet rs = stmt.executeQuery("SELECT * " +
        //        "FROM Customers WHERE Snum = 2001");
        return new User(internalId);
    }

    public User(long internalId) throws SQLException{

        try {
            Class.forName("org.postgres.Driver"); //Load Driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //Connect to Database using SSL Validation
        String url = "jdbc:postgresql://localhost:5432/kwak";
        Properties properties = new Properties();
        properties.setProperty("user", "postgres");
        properties.setProperty("password", "root");
        properties.setProperty("ssl", "true");
        connection = getConnection(url, properties);

        //Issue Query
        Statement statement = connection.createStatement();

        //Process Query Results
        ResultSet result = statement.executeQuery("SELECT * FROM user WHERE user_id = "+internalId+";");
        while(result.next()){
            //Add code to retrieve Blob type from DB
            System.out.println("User Found");
            System.out.println(result.getString(1));
            result.close();
        }
        statement.close();
        connection.close();
    }

    public static User buildFromJson(JsonObject json) throws SQLException {
        return new User(json);
    }

    public User(JsonObject json) throws SQLException {

        //TODO write this user object to db



        _internalId = FunctionUtil.generateId();
        _userPhoto = json.getString("userPhoto");
        _userName = json.getString("userName");
        _externalId = json.getString("externalId");
        _email = json.getString("email");
        _passWord = json.getString("passPhrase");


        _groupList = new HashSet<>();
        _contentList = new HashSet<>();

        _empty = false;
        _deleted = false;

        try {
            Class.forName("org.postgres.Driver"); //Load Driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            OutputStream os = new FileOutputStream(_userPhoto);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Connect to Database using SSL Validation
        String url = "jdbc:postgresql://localhost:5432/kwak";
        Properties properties = new Properties();
        properties.setProperty("user", "postgres");
        properties.setProperty("password", "root");
        properties.setProperty("ssl", "true");
        connection = getConnection(url, properties);
        //Utilize Prepared Statements for security. ?'s are placeholders for the VALUES which are filled in later.
        String queryStatement = "INSERT INTO user(user_id, internal_id, username, contact_email, user_photo_blob, password) VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(queryStatement);
        preparedStatement.setLong(1, get_internalId());
        preparedStatement.setString(3, get_userName());
        preparedStatement.setString(4, get_email());
        //preparedStatement.setBlob(5, get_userPhoto());
        //preparedStatement.setString(6, get_passWord());
        preparedStatement.executeUpdate();

        connection.close();

    }

    public long get_internalId() {
        return _internalId;
    }


    public void set_internalId(long _internalId) {
        this._internalId = _internalId;
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


    public String get_userPhoto() {
        return _userPhoto;
    }


    public void set_userPhoto(String _userPhoto) {
        this._userPhoto = _userPhoto;
    }

    public User buildUser() {

        return null;
    }

    public JsonObject asJson(){

        JsonObject json = new JsonObject();

        json.put("userId", _internalId);
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
        return _empty;
    }


}