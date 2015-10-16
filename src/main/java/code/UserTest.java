package code;

import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

/**
 * Created by kclaiborne on 10/15/2015.
 */
public class UserTest {

    @Test
    public void test()throws SQLException{
        Long internalId = 000000000000000000L;
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

}
