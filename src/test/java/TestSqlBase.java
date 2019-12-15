import com.PairBot.SqlBase;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Properties;
import java.sql.*;
import java.util.List;
import java.util.HashMap;

class TestSqlBase extends TestCase {
    @Test
    public boolean testAllReadyRunning() throws SQLException {
        SqlBase base = new SqlBase("all_chat_test");
        try(java.sql.Statement stat = base.connect.createStatement()){
            stat.executeUpdate("insert into chatsdata (id,last_date) value ("+0+",\"00.00.0000\")");
            return base.allreadyRunning((long) 0,"00.00.0000");
        }
        catch (SQLException e){
            return false;}

    }
}