package code;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by kclaiborne on 10/15/2015.
 */
public class UserTest {

    @Test
    public void testBuildFromId()throws SQLException{
        Long internalId = 0L;
        User user = new User(internalId);
        Long actualInternalId = user.get_internalId();
        String externalId = user.get_externalId();
        String username = user.get_userName();
        String password = user.get_passWord();
        String email = user.get_email();
        System.out.println(actualInternalId);
        System.out.println(username);
        System.out.println(password);
        System.out.println(email);
        System.out.println(externalId);
        assertEquals(internalId, actualInternalId);
    }

    @Test
    public void testBuildFromJSON() throws SQLException{
        JsonObject json = new JsonObject();
        Long _internalId = 0L;
        String _userName = "kendracl";
        String _passWord = "password123";
        String _email = "kendracl@domain.com";
        Long _externalId = 123L;
        //JsonObject _userPhoto = ;

        json.put("userId", _internalId);
        json.put("userName",  _userName);
        json.put("passPhrase", _passWord);
        json.put("email", _email);
        json.put("externalId", _externalId);
        //json.put("userPhoto", _userPhoto);

        ArrayList<String> _groupList = new ArrayList<String>();
        ArrayList<String> _contentList = new ArrayList<String>();
        _groupList.add("Group 1");
        _contentList.add("Content 1");

        List<String> groups = new ArrayList<String>(_groupList);
        List<String> content = new ArrayList<String>(_contentList);

        json.put("groupList", new JsonArray(groups));
        json.put("contentList", new JsonArray(content));

        User user = new User(json);
    }

}
