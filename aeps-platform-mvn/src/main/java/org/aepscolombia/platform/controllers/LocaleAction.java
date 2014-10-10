/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aepscolombia.platform.controllers;

import com.opensymphony.xwork2.ActionContext;
import java.util.Locale;
import java.util.Map;
import org.aepscolombia.platform.util.APConstants;

/**
 * Clase LocaleAction
 *
 * Contiene los metodos necesarios para realizar el cambio de idiomas del proyecto
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class LocaleAction extends BaseAction {
    
//    public LocaleAction() {
////        super();
//    }
    
    private String lang="";

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }   

    @Override
    public String execute() throws Exception {
        if (!lang.equals("")) {
            Locale locale = new Locale(lang);
    //        System.out.println("locale->"+ActionContext.getContext().getLocale());
            ActionContext.getContext().setLocale(locale);
//            this.getSession().put(APConstants.SESSION_LANG, lang);
            Map<String, Object> userSession=ActionContext.getContext().getSession();
            userSession.put(APConstants.SESSION_LANG, lang);
            this.setSession(userSession);
        }
        /*
        MongoClient mongo = new MongoClient("localhost", 27017);
        DB db = mongo.getDB("ciat");
         
        DBCollection col = db.getCollection("log_form_records");
        
//        Set<String> collections = db.getCollectionNames();
//        System.out.println(collections);
        
//        DBObject query = BasicDBObjectBuilder.start()
//                .add("InsertedId", 614)
//                .add("form_id", 5).get();
        BasicDBObject query = new BasicDBObject();
		query.put("InsertedId", "614");
		query.put("form_id", "5");
        
        DBCursor cursor = col.find(query);
        while(cursor.hasNext()) {
            System.out.println(cursor.next());
        }
        String csv = "producersInfo.csv";
        CSVWriter writer = new CSVWriter(new FileWriter(csv));

        //Create record
        String [] record = "4,David,Miller,Australia,30".split(",");
        //Write the record to file
        writer.writeNext(record);
//        System.out.println("prince royce");

        //close the writer
        writer.flush();
        writer.close();*/
//        return Response.ok(writer).header("Content-Disposition", "attachment; filename=producersInfo.csv").build();
        
//        return "states";
        return SUCCESS;
    }
     
}