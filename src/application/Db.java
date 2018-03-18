package application;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class Db {

    final private String driver = "com.mysql.jdbc.Driver";
    final private String url = "jdbc:mysql://localhost/";
    final private String encode = "?useUnicode=true&characterEncoding=utf-8";

    private String dbName = "sakila";
    private String dbUser = "root";
    private String dbPass = "";

    private Connection conn = null;
    private Statement st = null;
    private PreparedStatement preSt = null;
    LoggerClass lg = new LoggerClass();
    public Db() {
    }

    public Db(String dbName, String dbUser, String dbPass) {
        this.dbName = dbName;
        this.dbUser = dbUser;
        this.dbPass = dbPass;
    }

    public Statement connect() {
        try {
            if (st != null) {
                disconnect();
            }
            if (preSt != null) {
                disconnect();
            }
            Class.forName(driver);
            conn = DriverManager.getConnection(url + dbName + encode, dbUser, dbPass);
            st = conn.createStatement();
//            System.out.println("Connection Successful");
        } catch (ClassNotFoundException | SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("Connection Error:" + e);
        }
        return st;
    }

    public PreparedStatement preConnect(String query) {
        try {
            if (preSt != null) {
                disconnect();
            }
            if (st != null) {
                disconnect();
            }
            Class.forName(driver);
            conn = DriverManager.getConnection(url + dbName + encode, dbUser, dbPass);
            preSt = conn.prepareStatement(query);
//            System.out.println("Connection Successful");
        } catch (ClassNotFoundException | SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("Connection Error:" + e);
        }
        return preSt;
    }

    public void disconnect() {
        try {
            if (preSt != null) {
                preSt.close();
//                System.out.println("PreparedStatement Disconnection Successful");
                preSt = null;
            }
            if (st != null) {
                st.close();
//                System.out.println("Statement Disconnection Successful");
                st = null;
            }
            if (conn != null) {
                conn.close();
//                System.out.println("Disconnection Successful");
                conn = null;
            }
        } catch (SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("Disconnection Error:" + e);
        }

    }

    public String insertQuery(String tableName, String[] values) {
        String insert = "insert into '" + tableName + "' values(";
        for (int i = 0; i < values.length; i++) {
            if (i == 0) {
                if (values[i] == null) {
                    insert += values[i];
                } else {
                    insert += "'" + values[i] + "'";
                }
            } else {
                if (values[i] == null) {
                    insert += "," + values[i];
                } else {
                    insert += ",'" + values[i] + "'";
                }
                if (i == values.length - 1) {
                    insert += ")";
                }
            }
        }
        return insert;
    }
    
    public String preInsertQuery(String tableName ,int valueCount){
        String insert="insert into "+tableName+" values(";
        for (int i = 0; i < valueCount; i++) {
            if (i!=valueCount-1) {
                insert+="?,";
            }else{
               insert+="?"; 
            }
        }
        return insert;
    }
    

}
