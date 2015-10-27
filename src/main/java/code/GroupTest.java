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
        Long internalId = 0L;
        Group group = new Group(internalId);
        Long actual = group.get_internalId();

        group.set_groupName("Xavier Institute for Higher Learning");
        String groupName = group.get_groupName();

        group.set_description("");
        group.get_password();
        group.get_groupName();
        System.out.println(group);
        assertEquals(internalId, actual);
    }

}






