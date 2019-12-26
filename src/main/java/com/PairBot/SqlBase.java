package com.PairBot;
import org.telegram.telegrambots.meta.api.objects.User;
import java.util.ArrayList;
import java.util.Properties;
import java.sql.*;
import java.util.List;
import java.util.HashMap;

public class SqlBase{

    public Connection connect;
    public SqlBase(){
        String URL = "jdbc:mysql://eu-cdbr-west-02.cleardb.net:3306/heroku_3975da312a01dc0";
        String LOGIN = "be23e826335c88";
        String PASSWORD = "e6871c6f";
        Properties properties = new Properties();
        properties.put("User", LOGIN);
        properties.put("password", PASSWORD);
        properties.put("characterUnicode", "true");
        properties.put("useUnicode", "true");
        properties.put("useSSL", "false");
        properties.put("autoReconnect", "true");

        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            connect = DriverManager.getConnection(URL, properties);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean allreadyRunning(Long id, String day){
        boolean isRun = false;
        try(Statement state = connect.createStatement()) {
            String query = "select * from chatsdata where id =" + id;
            ResultSet set = state.executeQuery(query);
            if (set.first())
            {
                isRun = set.getString("last_date").equals(day);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return isRun;
    }


    public boolean registration(Long id, User user){
        try(Statement state = connect.createStatement())
        {
            String query = "select * from usersdata where id="+id +" and user_id="+user.getId();
            if (state.executeQuery(query).first()){
                return false;
            }
            String username = user.getUserName();
            if (username == null)
            {
                username = user.getFirstName();
            }
            query = "insert into usersdata (id,user_id,username) value ("+id+","+user.getId()+",\""+username+"\")";
            state.executeUpdate(query);
            query = "select * from chatsdata where id="+id;
            if (!state.executeQuery(query).next()){
                state.executeUpdate("insert into chatsdata (id,last_date,lang) value ("+id+",\"00.00.0000\",\"RU\")");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public HashMap<String,Integer> getTopPair(Long id){
        HashMap<String,Integer> result = new HashMap<String,Integer>();
        try(Statement state = connect.createStatement()) {
            String query = "select * from winners where id=" + id;
            ResultSet allUsers = state.executeQuery(query);
            while (allUsers.next())
            {
                result.put(allUsers.getString("winners_names"),allUsers.getInt("count"));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public List<BaseUser> getUsers(Long id)
    {
        List<BaseUser> result = new ArrayList<BaseUser>();
        try(Statement state = connect.createStatement()){
            String query = "select * from usersdata where id="+id;
            ResultSet allUsers = state.executeQuery(query);
            while(allUsers.next()) {
                result.add(new BaseUser(allUsers.getLong(1), allUsers.getString("username")));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public void setPair(Long id,BaseUser winner1,BaseUser winner2,String Date)
    {
        try(Statement state = connect.createStatement())
        {
            String query = "select * from winners where id=" + id+" and winners_names ="+"\""+winner1.USERNAME +" + "+winner2.USERNAME+"\"";
            ResultSet allUsers = state.executeQuery(query);
            if (allUsers.first()){
                int count = allUsers.getInt("count") + 1;
                state.executeUpdate("update winners set count="+count+" where id="+id+" and winners_names ="+"\""+winner1.USERNAME +" + "+winner2.USERNAME+"\"");
            }
            else {
                query = "insert into winners (id,winners_names,count) value (" + id + ",\"" + winner1.USERNAME + " + " + winner2.USERNAME + "\"," + 1 + ")";
                state.executeUpdate(query);
            }
            state.executeUpdate("update chatsdata set last_date=\""+Date+"\" where id="+id);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public  Messages.LANGG getLang(Long id)
    {
        Messages.LANGG lang = Messages.LANGG.RU;
        try(Statement state = connect.createStatement()){
            String query = "select * from chatsdata where id="+id;
            ResultSet set = state.executeQuery(query);

            if (set.first())
            {
                 lang = Messages.LANGG.valueOf(set.getString("lang"));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return lang;
    }

    public void setLang(Long id,Messages.LANGG lang) {
        try (Statement state = connect.createStatement()) {
            String query = "select * from chatsdata where id="+id;
            if (!state.executeQuery(query).next()){
                state.executeUpdate("insert into chatsdata (id,last_date) value ("+id+",\"00.00.0000\",\"RU\")");
            }
            state.executeUpdate("update chatsdata set lang=\"" + lang.name() + "\" where id=" + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
