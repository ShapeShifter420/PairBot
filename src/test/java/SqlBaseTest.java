import com.PairBot.SqlBase;
import com.PairBot.BaseUser;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import java.sql.*;
import java.util.HashMap;
import java.util.List;

public class SqlBaseTest{

    public static void main(String[] args) throws Exception {
        JUnitCore runner = new JUnitCore();
        Result result = runner.run(SqlBaseTest.class);
        System.out.println("run tests: " + result.getRunCount());
        System.out.println("failed tests: " + result.getFailureCount());
        System.out.println("ignored tests: " + result.getIgnoreCount());
        System.out.println("success: " + result.wasSuccessful());
    }
    @Test
    public void testAllReadyRunning0() throws SQLException {
//        SqlBase base = new SqlBase("all_chat_test");
//        try(java.sql.Statement stat = base.connect.createStatement()){
//            stat.executeUpdate("insert into chatsdata (id,last_date) value ("+0+",\"00.00.0000\")");
//            Assert.assertTrue(base.allreadyRunning((long) 0,"00.00.0000"));
//            Assert.assertFalse(base.allreadyRunning((long) 1,"00.00.0000"));
//            Assert.assertFalse(base.allreadyRunning((long) 0,"00.00.0010"));
//        }
//        catch (SQLException e){
//            Assert.fail();}
    }
    @Test
    public void testGetTopPair() throws SQLException {
//        SqlBase base = new SqlBase("all_chat_test");
//        try(java.sql.Statement stat = base.connect.createStatement()){
//            stat.executeUpdate("insert into winners (id,winners_names,count) value (0,\"lupa + pupa\",99)");
//            stat.executeUpdate("insert into winners (id,winners_names,count) value (1,\"lupa + pupa\",99)");
//            stat.executeUpdate("insert into winners (id,winners_names,count) value (1,\"lupa + dupa\",99)");
//            HashMap<String,Integer> dict = base.getTopPair((long)0);
//            Assert.assertEquals(1,dict.size());
//            dict = base.getTopPair((long)1);
//            Assert.assertEquals(2,dict.size());
//        }
//        catch (SQLException e){
//            Assert.fail();}
    }
    @Test

    public void testUsers() throws SQLException {
//        SqlBase base = new SqlBase("all_chat_test");
//        try(java.sql.Statement stat = base.connect.createStatement()){
//            stat.executeUpdate("insert into usersdata (id,user_id,username) value (0,1,\"lupa\")");
//            stat.executeUpdate("insert into usersdata (id,user_id,username) value (1,1,\"lupa\")");
//            stat.executeUpdate("insert into usersdata (id,user_id,username) value (1,2,\"dupa\")");
//            List<BaseUser> users = base.getUsers((long)0);
//            Assert.assertEquals(1,users.size());
//            users = base.getUsers((long)1);
//            Assert.assertEquals(2,users.size());
//        }
//        catch (SQLException e){
//            Assert.fail();}}
    }
    @Test

    public void testSetWinners() throws SQLException {
//        SqlBase base = new SqlBase("all_chat_test");
//        try(java.sql.Statement stat = base.connect.createStatement()){
//            BaseUser winner1 = new BaseUser((long)0,"det");
//            BaseUser winner2 = new BaseUser((long)1,"det2");
//            base.setPair((long)3,winner1,winner2,"00.00.0000");
//            HashMap<String,Integer> dict = base.getTopPair((long)3);
//            Assert.assertEquals(1,dict.size());
//        }
//        catch (SQLException e){
//            Assert.fail();}
    }

}