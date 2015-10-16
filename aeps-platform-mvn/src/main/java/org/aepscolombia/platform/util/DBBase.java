package org.aepscolombia.platform.util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author HSOTELO
 */
public abstract class DBBase {
    /*Members Class*/
    private String server;
    private String bd;
    private String login ;
    private String password;
    
    private String query;
        
    private String url;
    private Connection connection;
    private Statement statement;
    private ResultSet recordSet;
    
    /**
     * @return the server
     */
    public String getServer() {
        return server;
    }

    /**
     * @param server the server to set
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * @return the bd
     */
    public String getBd() {
        return bd;
    }

    /**
     * @param bd the bd to set
     */
    public void setBd(String bd) {
        this.bd = bd;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the query
     */
    public String getQuery() {
        return query;
    }

    /**
     * @param query the query to set
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * @param connection the connection to set
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * @return the statement
     */
    public Statement getStatement() {
        return statement;
    }

    /**
     * @param statement the statement to set
     */
    public void setStatement(Statement statement) {
        this.statement = statement;
    }
    
    /**
     * @return the record set
     */
    public ResultSet getRecordSet() {
        return recordSet;
    }

    /**
     * @param recordSet the record set to set
     */
    public void setStatement(ResultSet recordSet) {
        this.recordSet = recordSet;
    }
    
    /*Construct*/
    
    /***
     * Method Construct 
     * @param server Server name or ip where database is locate
     * @param bd Name of database
     * @param login User login for database
     * @param password Password for database
     */
    public DBBase (String server,String bd,String login,String password)
    {
        this.server=server;
        this.bd=bd;
        this.login=login;
        this.password=password;
        this.query="";
        setUrl();
        setConnection();
    }
    
    /***
     * Method Construct
     */
    public DBBase(){
        this.server="";
        this.bd="";
        this.login="";
        this.password="";
        this.query="";
    }
    
    
    /*Methods Abstract*/
    
    /**
     * Method that establish the url for the connection with database
     */
    abstract void setUrl();
    
    /**
     * Method that build the object connection with database
     * @return True if is correct, false otherwise
     */
    abstract boolean setConnection();

    /**
     * Method that execute a query to database and bring the result.
     * This method is only for bring results from database
     * @throws SQLException 
     */
    public void getResults () throws SQLException
    {
        this.statement = this.connection.createStatement();        
        this.recordSet = this.statement.executeQuery (this.query);
    }
    
    /**
     * Method that execute a query to database and bring the result.
     * This method is only for bring results from database
     * @param query to execute
     * @throws SQLException 
     */
    public void getResults(String query) throws SQLException
    {
        this.query=query;
        this.getResults();
    }
    
    /**
     * Method that execute a query in the database for update it. 
     * The query type can be insert, updates or delete
     * @param query to execute
     * @return affected rows
     * @throws SQLException 
     */
    public int update(String query) throws SQLException 
    {
        this.query=query;
        this.statement = this.connection.createStatement();
        int affectedRows = this.statement.executeUpdate(this.query);
        this.statement.close();
        return affectedRows;
    }
    
    public ArrayList<String> getColumnNames() throws SQLException
    {
        ArrayList<String> fields=new ArrayList();
        ResultSetMetaData rsmd = this.recordSet.getMetaData();
        for (int i = 1; i <= rsmd.getColumnCount(); i++ ) 
            fields.add(rsmd.getColumnLabel(i));
        return fields;
    }
}
