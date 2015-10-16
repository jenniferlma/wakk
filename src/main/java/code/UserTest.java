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
        User user = new User();
        user.buildFromId(internalId);
        Long actual = user.get_internalId();
        user.get_externalId();
        user.get_userName();
        user.get_passWord();
        user.get_email();
        System.out.println(user.get_internalId());
        System.out.println(user.get_userName());
        System.out.println(user.get_passWord());
        System.out.println(user.get_email());
        System.out.println(user.get_externalId());
        assertEquals(internalId, actual);
    }

}
