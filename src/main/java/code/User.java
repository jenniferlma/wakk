package code;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.postgresql.largeobject.LargeObject;
import org.postgresql.largeobject.LargeObjectManager;
import util.FunctionUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    private byte[] _byteGroupList;
    private Set<Long> _contentList;

    private boolean _empty;
    private boolean _deleted;

    private Connection connection;

    public User(){}

    public User(long internalId) throws SQLException{
        //Modified by ams 10/15/15

        //Initiate Connection to DB
        Connection conn = null;
        this.connection = createDBConnection(conn);

        //Necessary if we use Blob
        this.connection.setAutoCommit(false);
        LargeObjectManager largeObjectManager = ((org.postgresql.PGConnection)this.connection).getLargeObjectAPI();

        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM kwakschema.user WHERE internal_id = " + internalId + ";");
        //ResultSet result = statement.executeQuery("SELECT * " + "FROM kwakschema.user WHERE internal_id = " + internalId + ";");
        preparedStatement.setString(7, "image.gif"); //TODO: Verify image_name parameter index in DB
        ResultSet result = preparedStatement.executeQuery();

        //Below can also be done by the column index number: System.out.println(result.getString("ColumnIndexNo");
        while(result.next()){
            System.out.println("User Found!");
            this._internalId = result.getLong("internal_id");
            this._userName = result.getString("username");
            this._email = result.getString("contact_email");
            this._passWord = result.getString("password");
            this._externalId = result.getString("external_id");

            // -----Start Bytea and Blob Code-----
            // Bytea and Blob code referenced from http://www.postgresql.org/docs/7.4/static/jdbc-binary-data.html
            /*
            Bytea: Stores the data in a column, exported as part of a backup. Uses standard database functions to save and retrieve. Recommended for your needs.
            Not well suited for storing very large amounts of binary data. A column can hold up to 1GB of binary data and requires a huge amount of memory to process
            the large value.
            */
            //Insert Bytea Image
            this._byteGroupList = result.getBytes("group_photo");

            /*
            Blob (Large Binary Object): Stores the data externally, not normally exported as part of a backup. Requires special database functions to save and retrieve.
            Better suited for storing very large values. Stores the binary data in a separate table in a special format and refers to that table by sotring a value of
            type OID in the actual table. When deleting a row that contains a Large Object reference, it does not delete the Large Object, instead deleting
            the Large Object is a separate operation that needs to be performed.
            */
            //Insert LargeObject
            int oid = result.getInt(1); //TODO: Verify image_name parameter index in DB
            LargeObject largeObject = largeObjectManager.open(oid, LargeObjectManager.READ);
            byte buffer[] = new byte[largeObject.size()];
            largeObject.read(buffer, 0, largeObject.size());
            largeObject.close();
            // -----End Bytea and Blob Code-----
        }

        result.close();
        preparedStatement.close();
        this.connection.close();
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

        //Initiate Connection to DB
        Connection conn = null;
        this.connection = createDBConnection(conn);

        //Below can also be done by the column index number: System.out.println(result.getString("ColumnIndexNo");
        //Utilize Prepared Statements for security. ?'s are placeholders for the VALUES which are filled in later.
        String queryStatement = "INSERT INTO kwakschema.user(internal_id, username, contact_email, user_photo, password, external_id) VALUES(?, ?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = this.connection.prepareStatement(queryStatement);
        preparedStatement.setLong(1, _internalId);
        preparedStatement.setString(3, _userName);
        preparedStatement.setString(4, _email);

        // -----Start Bytea and Blob Code-----
        // Bytea and Blob code referenced from http://www.postgresql.org/docs/7.4/static/jdbc-binary-data.html
        /*
        Bytea: Stores the data in a column, exported as part of a backup. Uses standard database functions to save and retrieve. Recommended for your needs.
        Not well suited for storing very large amounts of binary data. A column can hold up to 1GB of binary data and requires a huge amount of memory to process
        the large value.
        */
        //Insert Bytea Image
        File file1 = new File("image.gif");
        FileInputStream fis1 = null;
        try {
            fis1 = new FileInputStream(file1);
            preparedStatement.setString(5, file1.getName()); //TODO: Create new Text Type Column image_name
            preparedStatement.setBinaryStream(6, fis1, file1.length()); //TODO: Update Blob Type Column image_bytea
            fis1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        Blob (Large Binary Object): Stores the data externally, not normally exported as part of a backup. Requires special database functions to save and retrieve.
        Better suited for storing very large values. Stores the binary data in a separate table in a special format and refers to that table by sotring a value of
        type OID in the actual table. When deleting a row that contains a Large Object reference, it does not delete the Large Object, instead deleting
        the Large Object is a separate operation that needs to be performed.
        */
        //Insert LargeObject
        this.connection.setAutoCommit(false);
        LargeObjectManager largeObjectManager = ((org.postgresql.PGConnection)this.connection).getLargeObjectAPI();
        Long oid = largeObjectManager.createLO(LargeObjectManager.READ | LargeObjectManager.WRITE);
        LargeObject largeObject = largeObjectManager.open(oid, LargeObjectManager.WRITE);
        File file2 = new File("image.gif");
        FileInputStream fis2 = null;

        try {
            fis2 = new FileInputStream(file2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        byte buffer[] = new byte[2048];
        int s, tl = 0;

        try {
            while((s = fis2.read(buffer, 0, 2048)) > 0){
                largeObject.write(buffer, 0, s);
                tl += s;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        largeObject.close();

        preparedStatement.setString(5, file2.getName()); //TODO: Create Text Column image_name
        preparedStatement.setLong(6, oid); //TODO: Create Int Column object_identifier
        // -----End Bytea and Blob Code-----

        //TODO: Update Paramter Index to reflect DB after changes to previous TODO
        preparedStatement.setString(7, _passWord);
        preparedStatement.setArray(8, (Array) _groupList);
        preparedStatement.setArray(9, (Array) _contentList);
        preparedStatement.executeUpdate();

        //Start of continued Bytea and Blob Code
        try{
            fis1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fis2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //End of continued Bytea and Blob Code

        preparedStatement.close();
        this.connection.close();
    }

    public static User buildFromId(long internalId) throws SQLException {
        //Retrieve row(s) from DB
        return new User(internalId);
    }

    public static User buildFromJson(JsonObject json) throws SQLException {
        //Insert row(s) into DB
        return new User(json);
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