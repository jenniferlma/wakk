package code;

import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

/**
 * Created by kclaiborne on 10/15/2015.
 */
public class GroupTest {

    @Test
    public void test()throws SQLException{
        Long internalId = 000000000000000000L;
        Group group = new Group(internalId);
        Long actual = group.get_internalId();

        group.set_groupName("Xavier Institute for Higher Learning");
        String groupName = group.get_groupName();

        group.set_description("");
        group.get_userName();
        group.get_passWord();
        group.get_email();
        System.out.println(user);
        assertEquals(internalId, actual);
    }

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

