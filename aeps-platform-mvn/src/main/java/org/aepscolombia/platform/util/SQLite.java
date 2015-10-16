package org.aepscolombia.platform.util;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author HSOTELO
 */
public class SQLite extends DBBase {
      
    /*Member Class*/
    private String database;
    
    @Override
    void setUrl() {
        this.setUrl("jdbc:sqlite:" + this.getDatabase());
    }

    @Override
    boolean setConnection()  {
        try {
            Class.forName("org.sqlite.JDBC");
            setConnection(DriverManager.getConnection(this.getUrl()));
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return getConnection()!=null;
    }
    
    /***
     * Method Construct 
     * @param database Full path where is located the database in sqlite
     */
    public SQLite(String database)
    {        
        super();
        this.database=database;
        this.setUrl();
        this.setConnection();
    }

    /**
     * @return the database
     */
    public String getDatabase() {
        return database;
    }

    /**
     * @param database the database to set
     */
    public void setDatabase(String database) {
        this.database = database;
    }
}
