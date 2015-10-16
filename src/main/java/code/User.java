package code;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import util.FunctionUtil;

import java.sql.*;
import java.util.*;

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

    /*public User(long internalId) throws SQLException{
        return new User(internalId);
    }*/


    public static User buildFromJson(JsonObject json) throws SQLException {
        return new User(json);
    }

    public void buildFromId(long internalId) throws SQLException {
        //Modified by ams 10/15/15
        connection = createDBConnection(connection);

        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * " +
                "FROM kwakschema.user WHERE internal_id = "+internalId+";");

        while(result.next()){
            //Add code to retrieve Blob type from DB
            System.out.println("Group Found!");
            this._internalId = result.getLong("internal_id");
            this._userName = result.getString("username");
            this._email = result.getString("contact_email");
            this._passWord = result.getString("password");
            this._externalId = result.getString("external_id");
            //this._groupPhoto = result.getString("group_photo"); //Bytea: Stores the data in a column, exported as part of a backup. Uses standard database functions to save and retrieve.
            //this._groupPhoto = result.getBlob("group_photo"); //Blob: Stores the data externally, not normally exported as part of a backup. Requires special database functions to save and retrieve.
            //The above can also be done by the column index number: System.out.println(result.getString("ColumnIndexNo");
        }
        statement.close();
        connection.close();
    }

    public User(JsonObject json) throws SQLException {

        _internalId = FunctionUtil.generateId();
        _userPhoto = json.getString("userPhoto");
        _userName = json.getString("userName");
        _externalId = json.getString("externalId");
        _email = json.getString("email");
        _passWord = json.getString("passPhrase");

        _groupList = new HashSet<>(json.getJsonArray("groupList").getList());
        _contentList = new HashSet<>(json.getJsonArray("contentList").getList());

        _empty = false;
        _deleted = false;


        //Utilize Prepared Statements for security. ?'s are placeholders for the VALUES which are filled in later.
        String queryStatement = "INSERT INTO kwakschema.user(internal_id, username, contact_email, user_photo, password, external_id) VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(queryStatement);
        preparedStatement.setLong(1, _internalId);
        preparedStatement.setString(3, _userName);
        preparedStatement.setString(4, _email);
        preparedStatement.setString(5, _userPhoto); //Bytea: Stores the data in a column, exported as part of a backup. Uses standard database functions to save and retrieve. Recommended for your needs.
        preparedStatement.setString(6, _passWord); //Blob: Stores the data externally, not normally exported as part of a backup. Requires special database functions to save and retrieve.
        //The above can also be done by the column index number: System.out.println(result.getString("ColumnIndexNo");
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

    private Connection createDBConnection(Connection connection) throws SQLException{
        //TODO Setup Class.forName correctly
        /*try {
            Class.forName("org.postgres.Driver"); //Load Driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/

        DriverManager.registerDriver(new org.postgresql.Driver());

        //Connect to Database using SSL Validation
        String url = "jdbc:postgresql://localhost:5432/kwak";
        Properties properties = new Properties();
        properties.setProperty("user", "postgres");
        properties.setProperty("password", "root");
        //TODO Setup SSL Connection correctly
        //properties.setProperty("ssl", "false");
        //properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
        connection = DriverManager.getConnection(url, properties);

        return connection;
    }

}