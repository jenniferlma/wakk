package code;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import util.FunctionUtil;

import java.sql.*;
import java.util.*;

import static java.sql.DriverManager.getConnection;

/**
 * Created by Eric Blair on 10/8/2015, modified by wendyjan on 10/14/2015.
 * Java Encapsulation of group
 */
public class Group {

    private long _internalId;
    private String _externalId;
    private String _groupName; // group's username
    private String _description;
    private String _password; //Future TODO create method to encrypt and decrypt password
    private String _groupPhoto;
    private long _groupLeader; // long user ID
    private Set<Long> _userList; // contains user IDs
    private Set<Long> _contentList; // contains content IDs
    private boolean _empty;
    private boolean _deleted;

    private Connection connection;

    public Group(long internalId) throws SQLException {
        // todo for Kendra?

        connection = createDBConnection(connection);

        //Issue Query
        Statement statement = connection.createStatement();

        //Process Query Results (Records)
        ResultSet result = statement.executeQuery("SELECT * FROM kwakschema.group WHERE user_id = " + internalId); //No need to include ; in sql statement
        while(result.next()){
            //Add code to retrieve Blob type from DB
            System.out.println("Group Found!");
            this._internalId = result.getLong("internal_id");
            this._groupName = result.getString("group_name");
            this._groupLeader = result.getLong("group_leader");
            this._description = result.getString("description");
            this._password = result.getString("password");
            this._externalId = result.getString("external_id");
            this._groupPhoto = result.getString("group_photo"); //Bytea: Stores the data in a column, exported as part of a backup. Uses standard database functions to save and retrieve. Recommended for your needs.
            //this._groupPhoto = result.getBlob("group_photo"); //Blob: Stores the data externally, not normally exported as part of a backup. Requires special database functions to save and retrieve.
            //The above can also be done by the column index number: System.out.println(result.getString("ColumnIndexNo");
        }
        result.close();
        statement.close();
        connection.close();
    }

    public Group(JsonObject json) throws SQLException {

        // TODO write this user object to dB

        _internalId = FunctionUtil.generateId();
        _externalId = json.getString("externalId"); //TODO Is this needed?
        _groupName = json.getString("groupName");
        _description = json.getString("description");
        _password = json.getString("password");

        _groupPhoto = json.getString("groupPhoto");
        _groupLeader = json.getLong("groupLeader");

        _userList = new HashSet<>(json.getJsonArray("userList").getList());
        _contentList = new HashSet<>(json.getJsonArray("contentList").getList());

        _empty = false;
        _deleted = false;

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
        //Utilize Prepared Statements for security. ?'s are placeholders for the VALUES which are filled in later.
        String queryStatement = "INSERT INTO user(internal_id, group_leader, group_photo, group_description, user_list, content_list, password, group_name, external_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);"; //DB does not currently have a foreign key (ID that references the internal_id in another table to relate two tables). This may want to be added in the future.
        PreparedStatement preparedStatement = connection.prepareStatement(queryStatement);
        preparedStatement.setLong(1, _internalId);
        preparedStatement.setLong(2, _groupLeader);
        //preparedStatement.setBlob(3, _groupPhoto); //Blob Data Type
        preparedStatement.setString(3, _groupName); //Bytea Data Type
        preparedStatement.setString(4, _description);
        //preparedStatement.setArray(5, _userList); //Todo create method to add HashSet elements into Array
        //preparedStatement.setArray(6, _contentList); /Todo create method to add HashSet elements into Array
        preparedStatement.setString(7, _password);
        preparedStatement.setString(8, _groupName);
        preparedStatement.setString(9, _externalId);
        preparedStatement.executeUpdate();

        connection.close();
    }

    public static Group buildFromId(Long internalId) throws SQLException {
        return new Group(internalId);
    }



    public JsonObject asJson(){
        JsonObject groupAsJson = new JsonObject();

        groupAsJson.put("groupId", _internalId);
        groupAsJson.put("externalId", _externalId); //TODO Is this needed?
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

    public static Group buildFromJson(JsonObject json) throws SQLException {
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

    public String get_groupPhoto() {
        return _groupPhoto;
    }

    public void set_groupPhoto(String _groupPhoto) {
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

    private Connection createDBConnection(Connection connection) throws SQLException{
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

        return connection;
    }


} // end Group class

