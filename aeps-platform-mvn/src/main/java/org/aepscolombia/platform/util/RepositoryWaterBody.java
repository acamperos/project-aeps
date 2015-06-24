package org.aepscolombia.platform.util;
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
public class RepositoryWaterBody {
    public static String geocoding_database_world="waterbody.db";
    public static String geocoding_database_world_table="waterbody";
     /*Members Class*/
    private SQLite db;
    
    /*Members Class*/
    private int NCOLS;
    private int NROWS;
    private double XLLCORNER;
    private double YLLCORNER;
    private double CELLSIZE;
    private String NODATA_value;
    private boolean init;
    
    
    /**
     * Method Construct
     * @param source path of database
     */
    public RepositoryWaterBody(String source)
    {
        db=new SQLite(source);
        init=false;
    }
    
    /**
     * Method that initialize the properties from querys
     * @throws SQLException 
     */
    private void load() throws SQLException
    {
        db.getResults("Select key,valueInit From conf Where key like 'raster_%'");
        while(db.getRecordSet().next())
        {
            if(db.getRecordSet().getString("key").toLowerCase().endsWith("ncols"))
                NCOLS=db.getRecordSet().getInt("valueInit");
            else if(db.getRecordSet().getString("key").toLowerCase().endsWith("nrows"))
                NROWS=db.getRecordSet().getInt("valueInit");
            else if(db.getRecordSet().getString("key").toLowerCase().endsWith("xllcorner"))
                XLLCORNER=db.getRecordSet().getDouble("valueInit");
            else if(db.getRecordSet().getString("key").toLowerCase().endsWith("yllcorner"))
                YLLCORNER=db.getRecordSet().getDouble("valueInit");
            else if(db.getRecordSet().getString("key").toLowerCase().endsWith("cellsize"))
                CELLSIZE=db.getRecordSet().getDouble("valueInit");
            else if(db.getRecordSet().getString("key").toLowerCase().endsWith("nodata_value"))
                NODATA_value=db.getRecordSet().getString("valueInit");
        }
        init=true;
    }
    
    /**
     * Method that search in the database the status of this point
     * @param lat Latitude in degree
     * @param lon Longitude in degree
     * @return if it is -1, it would be because don't found, the otherwise should be greater than or equal to zero
     */
    public String getDataFromLatLon(double lat,double lon)
    {        
        String a=null, query;
        int row,col;
        try {
            
            if(!init)
                load();
            col=(int)((lon-XLLCORNER)/CELLSIZE) + 1;
            row=NROWS-((int)((lat-YLLCORNER)/CELLSIZE));
            db.getResults("Select key,valueInit,valueEnd From conf Where key like '" + RepositoryWaterBody.geocoding_database_world_table + "%' and " + String.valueOf(col) + " between cast(valueInit as integer) and cast(valueEnd as integer)");
            if(db.getRecordSet().next())
            {
                query="Select col" + String.valueOf(col) + " From " + db.getRecordSet().getString("key") + " Where id=" + String.valueOf(row);
                db.getResults(query);
                if(db.getRecordSet().next())
                    a=db.getRecordSet().getString(1);
            }
        } 
        catch (Exception ex) {
            System.out.println("Error check database water. " + ex);
        }
        return a;
    }

    /**
     * @return the NODATA_value
     */
    public String getNODATA_value() {
        return NODATA_value;
    }
}
