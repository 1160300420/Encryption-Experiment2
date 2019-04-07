package DBTool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
 
public class DBUtil {
    public static  String url="jdbc:mysql://localhost:3306/demodatabase?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false";//链接的mysql
    private static String driverClass="com.mysql.cj.jdbc.Driver";
    private static String username="root";
    private static String password="password";
    private static Connection conn;
    //装载驱动
    static{
        try{
            Class.forName(driverClass);
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    //获取数据库连接
    public static Connection getConnection(){
        try{
            conn=DriverManager.getConnection(url,username,password);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return conn;
    }
    //建立数据库连接
    public static void main(String[] args){
        Connection conn=DBUtil.getConnection();
        if(conn==null){
            System.out.println("数据库连接失败！");
        }
        }
    //关闭数据库连接
    public static void Close(){
        if(conn!=null){
            try{
                conn.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
    }