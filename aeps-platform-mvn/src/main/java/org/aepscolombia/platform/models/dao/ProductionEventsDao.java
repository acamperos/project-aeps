package org.aepscolombia.platform.models.dao;

import au.com.bytecode.opencsv.CSVWriter;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.aepscolombia.platform.controllers.ActionField;
import org.aepscolombia.platform.models.entity.Entities;
//import org.aepscolombia.plataforma.models.dao.IEventoDao;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.ProductionEvents;
import org.aepscolombia.platform.models.entityservices.SfGuardUser;
import org.aepscolombia.platform.util.GlobalFunctions;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase ProductionEventsDao
 *
 * Contiene los metodos para interactuar con la tabla ProductionEvents de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ProductionEventsDao 
{    
    
    public HashMap findById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        
        List<Object[]> events = null;
        Transaction tx = null;
        HashMap result = new HashMap();
        
        String sql = "";
        String sqlAdd = "";    
        
        sql += "select pe.id_pro_eve, l.id_fie, l.name_fie, pe.id_crop_type_pro_eve, pe.expected_production_pro_eve,";
        sql += " pe.former_crop_pro_eve, pe.draining_pro_eve, pe.status, pe.other_former_crop_pro_eve";
        sql += " from production_events pe";
        sql += " inner join log_entities le on le.id_object_log_ent=pe.id_pro_eve and le.table_log_ent='production_events' and le.action_type_log_ent='C'";   
        sql += " inner join fields l on l.id_fie=pe.id_field_pro_eve";
        sql += " where l.status=1 and pe.status=1";
        if (id!=null) {
            sql += " and pe.id_pro_eve="+id;
        }
//        args.get("countTotal");
//        events.toArray();
//        System.out.println("sql->"+sql);        
        try {
            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql);            
            events = query.list();         
            
            for (Object[] data : events) {
//                System.out.println(data);
                HashMap temp = new HashMap();
                temp.put("idCrop", data[0]);
                temp.put("idField", data[1]);
                temp.put("nameField", data[2]);             
                temp.put("typeCrop", data[3]);                
                temp.put("performObj", data[4]);                
                temp.put("drainPlot", data[6]);                
                temp.put("status", data[7]);
                if (data[8]!=null) {                    
                    temp.put("lastCrop", 1000000);
                    temp.put("otherCrop", data[8]);
                } else {
                    if (data[5]==null) {
                        temp.put("lastCrop", 1000000);
                    } else {
                        temp.put("lastCrop", data[5]);
                    }
                    temp.put("otherCrop", "");
                }
                result = temp;
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }
    
    public List<ProductionEvents> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<ProductionEvents> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from ProductionEvents");
            events = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return events;
    }

    public List findByParams(HashMap args) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Object[]> eventsTotal = null;
        List<Object[]> events = null;
        Transaction tx = null;
        List<HashMap> result = new ArrayList<HashMap>();
        
        String sql = "";     
        String sqlAdd = "";     
        String entType = String.valueOf(args.get("entType"));
                      
        sql += "select pe.id_pro_eve, e.document_type_ent, e.document_number_ent, e.name_ent, f.id_far, f.name_far,";
        sql += " l.id_fie, l.name_fie, s.date_sow, mg.name_gen, pe.status, e.entity_type_ent, le.date_log_ent, ct.name_cro_typ,";
        if (entType.equals("3")) {
            sql += " har.date_har, entLe.name_ent as nameAgro";
        } else {
            sql += " har.date_har";
        }
        sql += " from production_events pe";
        sql += " inner join crops_types ct on ct.id_cro_typ=pe.id_crop_type_pro_eve";
//        sql += " left join sowing s on pe.id_pro_eve=s.id_production_event_sow and s.status=1";
        sql += " left join sowing s on pe.id_pro_eve=s.id_production_event_sow and s.status=1";
        sql += " left join harvests har on pe.id_pro_eve=har.id_production_event_har and har.status=1";
//        sql += " left join genotypes mg on mg.id_gen=s.genotype_sow and mg.status_gen=1";    
        sql += " left join genotypes mg on mg.id_gen=s.genotype_sow";    
    
        sql += " inner join log_entities le on le.id_object_log_ent=pe.id_pro_eve and le.table_log_ent='production_events' and le.action_type_log_ent='C'";   
        if (entType.equals("3")) {
            sql += " inner join entities entLe on (le.id_entity_log_ent=entLe.id_ent)"; 
            sql += " inner join extension_agents ext on (ext.id_entity_ext_age=entLe.id_ent)";
            sql += " inner join association ass on (ass.id_asc=ext.id_asso_ext_age)";
        }
        sql += " inner join fields l on l.id_fie=pe.id_field_pro_eve";
        sql += " inner join farms f on f.id_far=l.id_farm_fie";
        sql += " inner join farms_producers fp on f.id_far=fp.id_farm_far_pro"; 
        sql += " inner join producers p on p.id_pro=fp.id_producer_far_pro"; 
        sql += " inner join entities e on e.id_ent=p.id_entity_pro"; 
        sql += " where l.status=1 and f.status=1 and pe.status=1 and e.status=1 and ct.status_cro_typ=1";        
                
        if (!entType.equals("3") && args.containsKey("idEntUser")) {
            sqlAdd += " and le.id_entity_log_ent="+args.get("idEntUser");
        } else {
            String selAll  = String.valueOf(args.get("selAll"));
            if (selAll.equals("true")) {
                sqlAdd += " and ass.id_entity_asc="+args.get("idEntUser");
            } else {
                sqlAdd += " and le.id_entity_log_ent in ("+args.get("selItem")+")";
            }
        }
     
        if (args.containsKey("search_crop")) {
            String valIdent = String.valueOf(args.get("search_crop"));
            if(!valIdent.equals(" ") && !valIdent.equals("-1") && !valIdent.equals("") && !valIdent.equals("null")) { 
                sql += " and ((e.name_ent like '%"+valIdent+"%')";
                try {
                    String dateAsign = new SimpleDateFormat("yyyy-dd-MM").format(new Date(valIdent));
                    sql += " or (s.date_sow like '%"+dateAsign+"%')";
                    sql += " or (har.date_har like '%"+dateAsign+"%')";
                } catch (IllegalArgumentException ex) {
                }
                
                sql += " or (pe.id_pro_eve like '%"+valIdent+"%')";
                sql += " or (e.document_type_ent='"+valIdent+"')";
                sql += " or (e.document_number_ent like '%"+valIdent+"%')";
                
                sql += " or (f.id_far like '%"+valIdent+"%')";
                sql += " or (f.name_far like '%"+valIdent+"%')";
                sql += " or (l.id_farm_fie like '%"+valIdent+"%')";
                sql += " or (l.name_fie like '%"+valIdent+"%'))";
            }
        }
        
        if (args.containsKey("date_sowing")) {
            String valIdent = String.valueOf(args.get("date_sowing"));            
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) {
                String dateAsign = new SimpleDateFormat("yyyy-dd-MM").format(new Date(valIdent));
                sql += " and s.date_sow like '%"+dateAsign+"%'";
            }
        }
        
        if (args.containsKey("date_harvest")) {
            String valIdent = String.valueOf(args.get("date_harvest"));            
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) {
                String dateAsign = new SimpleDateFormat("yyyy-dd-MM").format(new Date(valIdent));
                sql += " and har.date_har like '%"+dateAsign+"%'";
            }
        }
        
        if (args.containsKey("name_producer")) {
            String valIdent = String.valueOf(args.get("name_producer"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and e.name_ent like '%"+valIdent+"%'";
        }
        if (args.containsKey("idCrop")) {
            String valIdent = String.valueOf(args.get("idCrop"));
            if(!valIdent.equals("0") && !valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and pe.id_pro_eve like '%"+valIdent+"%'";
        }
        if (args.containsKey("type_doc")) {
            String valIdent = String.valueOf(args.get("type_doc"));
            if(!valIdent.equals("0") && !valIdent.equals("-1") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and e.document_type_ent='"+valIdent+"'";
        }
        if (args.containsKey("num_doc")) {
            String valIdent = String.valueOf(args.get("num_doc"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and e.document_number_ent like '%"+valIdent+"%'";
        }
        if (args.containsKey("num_farm")) {
            String valIdent = String.valueOf(args.get("num_farm"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and f.id_far like '%"+valIdent+"%'";
        }        
        if (args.containsKey("name_farm")) {
            String valIdent = String.valueOf(args.get("name_farm"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and f.name_far like '%"+valIdent+"%'";
        }   
        if (args.containsKey("num_field")) {
            String valIdent = String.valueOf(args.get("num_field"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and l.id_farm_fie like '%"+valIdent+"%'";
        } 
        if (args.containsKey("name_field")) {
            String valIdent = String.valueOf(args.get("name_field"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and l.name_fie like '%"+valIdent+"%'";
        } 
        // if ($identProductor!='' ) sql += "where";
        sql += sqlAdd;

//        args.get("countTotal");
        
        int valIni = Integer.parseInt(String.valueOf(args.get("pageNow")));
//        int valIni = Integer.parseInt(args.get("pageNow"))*Integer.parseInt((String)args.get("maxResults"));
        int maxResults = Integer.parseInt(String.valueOf(args.get("maxResults")));
        if(valIni!=1){
//            valIni = ((valIni-1)*maxResults)+1;
            valIni = ((valIni-1)*maxResults);
        } else {
            valIni = 0;
        }     
        sql += " order by pe.id_pro_eve ASC";
//        events.toArray();
//        System.out.println("sql->"+sql);
        try {
            tx = session.beginTransaction();
//            Query query = session.createSQLQuery(sql);
            Query query  = session.createSQLQuery(sql);
//            System.out.println("sql->"+query.list().size());
            HashMap tempTotal = new HashMap();
            tempTotal.put("countTotal", query.list().size());
            result.add(tempTotal);
            if(query.list().size()>maxResults) {
                query.setFirstResult(valIni);
                query.setMaxResults(maxResults);      
            }
            events = query.list();     
                    
            for (Object[] data : events) {
                HashMap temp = new HashMap();
                temp.put("idCrop", data[0]);
                temp.put("num_crop", data[0]);
                temp.put("type_doc", data[1]);
                temp.put("num_doc", data[2]);             
                temp.put("name_producer", data[3]);                
                temp.put("num_farm", data[4]);
                temp.put("name_farm", data[5]);
                temp.put("num_field", data[6]);                
                temp.put("name_field", data[7]);                
                temp.put("date_sowing", data[8]);
                temp.put("name_genotype", data[9]);
                temp.put("status", data[10]);
                temp.put("typeEnt", data[11]);
                temp.put("dateLog", data[12]);
                temp.put("nameCrop", data[13]);
                temp.put("date_harvest", data[14]);
                if (entType.equals("3")) {
                    temp.put("nameAgro", data[15]);
                }
                result.add(temp);
            }
//            for (HashMap datos : result) {
//                System.out.println(datos.get("id_productor")+" "+datos.get("id_entidad")+" "+datos.get("cedula"));
//            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }    
    
    public HashMap getReportAnnualAgronomist(HashMap args, String semester, int posVal) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Object[]> events = null;
        Transaction tx = null;
        HashMap result = new HashMap();
        String resultInfo = "";
        String resultOut  = "";
        
        String sql = "";        
//        sql += "select ep.id_pro_eve, year(har.date_har), ent.id_ent, ent.name_ent, har.yield_har";
        sql += "select ep.id_pro_eve, har.date_har, ent.id_ent, ent.name_ent, har.yield_har";
        sql += " from production_events ep";
        sql += " inner join fields l on ep.id_field_pro_eve = l.id_fie";
        sql += " inner join farms f on l.id_farm_fie=f.id_far";
        sql += " inner join farms_producers fp on f.id_far = fp.id_farm_far_pro";
        sql += " inner join producers p on p.id_pro = fp.id_producer_far_pro";
        sql += " inner join entities ent on ent.id_ent = p.id_entity_pro";
        sql += " inner join harvests har on ep.id_pro_eve = har.id_production_event_har";
        sql += " inner join log_entities le on le.id_object_log_ent=ep.id_pro_eve and le.table_log_ent='production_events' and le.action_type_log_ent='C'";
        sql += " where ep.status=1";        
        
        if (args.containsKey("idEntUser")) {
            sql += " and le.id_entity_log_ent="+args.get("idEntUser");
        }
        
        if(!semester.equals(" ") && !semester.equals("") && !semester.equals("null")) {
            sql += " and har.date_har between "+semester;
        }
        sql += " and har.yield_har is not null";
        sql += " order by har.yield_har";
//        sql += " order by har.date_har";
        
//        System.out.println("sql->"+sql);
        try {
            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql);
            events       = query.list();     
            Integer totalVal = query.list().size();
            String[] dataRes = new String[totalVal];
            String[] dataBox = new String[5];
            int con = 0;
            for (Object[] data : events) {
                dataRes[con] = String.valueOf(data[4]);
                con++;
            }
            
            if (totalVal==1) {
                String valAss = dataRes[0];
                resultInfo = "["+valAss+","+valAss+","+valAss+","+valAss+","+valAss+"]";
            } else if (totalVal==2) {
                Integer valIni = Integer.parseInt(dataRes[0]);
                Integer valFin = Integer.parseInt(dataRes[1]);
                Integer median = ((valIni+valFin)/2);                
                resultInfo = "["+valIni+","+valIni+","+median+","+valFin+","+valFin+"]";
            } else if (totalVal==3) {
                Integer valIni = Integer.parseInt(dataRes[0]);
                Integer valFin = Integer.parseInt(dataRes[2]);
                String median  = dataRes[1];
                resultInfo = "["+valIni+","+valIni+","+median+","+valFin+","+valFin+"]";                
            } else if (totalVal==4) {
                Integer valIni = Integer.parseInt(dataRes[0]);
                Integer valFir = Integer.parseInt(dataRes[1]);
                Integer valSec = Integer.parseInt(dataRes[2]);                
                Integer valFin = Integer.parseInt(dataRes[3]);                
                valFir = ((valFir+valIni)/2);
                Integer median = ((valFir+valSec)/2);  
                valSec = ((valFin+valSec)/2);
                resultInfo = "["+valIni+","+valFir+","+median+","+valSec+","+valFin+"]";                
            } else if (totalVal==5) {        
                dataBox    = dataRes;                 
                resultInfo = "["+dataRes[0]+","+dataRes[1]+","+dataRes[2]+","+dataRes[3]+","+dataRes[4]+"]"; 
                resultOut  = findOutlier(dataBox, dataRes, posVal);
            } else if (totalVal!=0) {
                int q1 = (int) Math.round(totalVal*0.25);
                int q2 = (int) Math.round(totalVal*0.50);
                int q3 = (int) Math.round(totalVal*0.75);
                dataBox[0] = dataRes[0];
                dataBox[1] = dataRes[q1-1];
                dataBox[2] = dataRes[q2-1];
                dataBox[3] = dataRes[q3-1];
                dataBox[4] = dataRes[totalVal-1];
                resultInfo = "["+dataRes[0]+","+dataRes[q1-1]+","+dataRes[q2-1]+","+dataRes[q3-1]+","+dataRes[totalVal-1]+"]";
//                System.out.println("dataBox=>"+resultInfo);
                resultOut  = findOutlier(dataBox, dataRes, posVal);
            } else {
                resultInfo = "[0,0,0,0,0]";
            }           
//            resultOut = "["+events.get(q1-1)+","+events.get(q2-1)+","+events.get(q3-1)+"]";
            result.put("info", resultInfo);
            result.put("out", resultOut);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }    
    
    public String findOutlier(String[] dataBox, String[] dataTotal, int posVal) {
        String result = "";
        Double valFir = Double.parseDouble(dataBox[1]);              
        Double valSec = Double.parseDouble(dataBox[3]);   
        double riScaleDouble = (valSec-valFir)*1.5;
        double riMin = valFir-riScaleDouble;
        double riMax = valFir+riScaleDouble;
        for (int i=0;i<dataTotal.length;i++){
            Double valTemp = Double.parseDouble(dataTotal[i]);
            if (valTemp<riMin || valTemp>riMax) {
                result += "["+posVal+","+valTemp+"],";
            }
        }
        if (!result.equals("")) result = result.substring(0, (result.length()-1));      
        return result;
    }
    
    public HashMap getReportAnnualProducer(HashMap args) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Object[]> events = null;
        Transaction tx = null;
        HashMap result  = new HashMap();
        String formatVal   = "\"values\": \"[";
        String formatDate  = "\"format\": [{";
        
        String sql = "";        
        sql += "select ep.id_pro_eve, har.date_har, year(har.date_har), month(har.date_har), day(har.date_har), ent.id_ent, ent.name_ent, har.yield_har";
        sql += " from production_events ep";
        sql += " inner join fields l on ep.id_field_pro_eve = l.id_fie";
        sql += " inner join farms f on l.id_farm_fie=f.id_far";
        sql += " inner join farms_producers fp on f.id_far = fp.id_farm_far_pro";
        sql += " inner join producers p on p.id_pro = fp.id_producer_far_pro";
        sql += " inner join entities ent on ent.id_ent = p.id_entity_pro";
        sql += " inner join harvests har on ep.id_pro_eve = har.id_production_event_har";
        sql += " inner join log_entities le on le.id_object_log_ent=ep.id_pro_eve and le.table_log_ent='production_events' and le.action_type_log_ent='C'";
        sql += " where ep.status=1";        
        
        if (args.containsKey("idEntUser")) {
            sql += " and le.id_entity_log_ent="+args.get("idEntUser");
        }
        
        if (args.containsKey("selYear")) {
            String selYear = String.valueOf(args.get("selYear"));
            if (selYear.equals("1")) {
                if (args.containsKey("year_begin")) {
                    String valIdent = String.valueOf(args.get("year_begin"));            
                    if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) {
                        sql += " and year(har.date_har) = '"+valIdent+"'";
                    }
                }
            } else if (selYear.equals("2")) {
                if (args.containsKey("year_begin") && args.containsKey("year_end")) {
                    String valBegin = String.valueOf(args.get("year_begin"));            
                    String valEnd   = String.valueOf(args.get("year_end"));            
                    if((!valBegin.equals(" ") && !valBegin.equals("") && !valBegin.equals("null")) && (!valEnd.equals(" ") && !valEnd.equals("") && !valEnd.equals("null"))) {
                        sql += " and year(har.date_har) between '"+valBegin+"' and '"+valEnd+"'";
                    }
                }
            }          
        }        
        
        if (args.containsKey("name_producer")) {
            String valIdent = String.valueOf(args.get("name_producer"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and ent.name_ent like '%"+valIdent+"%'";
        }
        
        if (args.containsKey("type_doc")) {
            String valIdent = String.valueOf(args.get("type_doc"));
            if(!valIdent.equals("0") && !valIdent.equals("-1") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and ent.document_type_ent='"+valIdent+"'";
        }
        
        if (args.containsKey("num_doc")) {
            String valIdent = String.valueOf(args.get("num_doc"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and ent.document_number_ent like '%"+valIdent+"%'";
        }
        
        sql += " order by har.date_har";
        try {
            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql);
            events       = query.list();     
            int totalVal = query.list().size();
            int cont = 1;
            for (Object[] data : events) {
                String valIdent  = String.valueOf(data[1]);
                Date date=null;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd").parse(valIdent);
                } catch (ParseException ex) {
//                    Logger.getLogger(ProductionEventsDao.class.getName()).log(Level.SEVERE, null, ex);
                }
//                System.out.println("result=>"+date.getTime());
//                long date = new Date(valIdent).getTime();
                if (cont!=totalVal) {
                    formatVal += "["+date.getTime()+", "+data[7]+"],";
                    formatDate += "\"set"+date.getTime()+"\": \""+data[4]+" de "+data[2]+"\",";
                } else {
                    formatVal += "["+date.getTime()+", "+data[7]+"]";
                    formatDate += "\"set"+date.getTime()+"\": \""+data[4]+" de "+data[2]+"\"";
                }
                cont++;
            }
            formatVal += "]\"";
            formatDate += "}]";
            result.put("info", formatVal);
            result.put("format", formatDate);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }
    
    
    
    public static Integer countData(HashMap args) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Object[] events = null;
        Transaction tx = null;
        Integer result = 0;
        
        String sql = "";     
        String sqlAdd = "";     
        String entType = String.valueOf(args.get("entType"));
                      
        sql += "select count(pe.id_pro_eve), pe.id_field_pro_eve";
        sql += " from production_events pe";  
        sql += " inner join log_entities le on le.id_object_log_ent=pe.id_pro_eve and le.table_log_ent='production_events' and le.action_type_log_ent='C'";   
        if (entType.equals("3")) {
            sql += " inner join entities entLe on (le.id_entity_log_ent=entLe.id_ent)"; 
            sql += " inner join extension_agents ext on (ext.id_entity_ext_age=entLe.id_ent)";
            sql += " inner join association ass on (ass.id_asc=ext.id_asso_ext_age)";
        }
        sql += " inner join fields l on l.id_fie=pe.id_field_pro_eve";
        sql += " inner join farms f on f.id_far=l.id_farm_fie";        
        sql += " inner join farms_producers fp on fp.id_farm_far_pro=f.id_far"; 
        sql += " inner join producers p on p.id_pro=fp.id_producer_far_pro"; 
        sql += " inner join entities e on e.id_ent=p.id_entity_pro";
        sql += " where l.status=1 and f.status=1 and pe.status=1 and e.status=1";        
        
        if (!entType.equals("3") && args.containsKey("idEntUser")) {
            sqlAdd += " and le.id_entity_log_ent="+args.get("idEntUser");
        } else {
            sqlAdd += " and ass.id_entity_asc="+args.get("idEntUser");
        }
        sql += sqlAdd;
//        System.out.println("sql->"+sql);
        try {
            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql);
            events = (Object[])query.uniqueResult();
            result = Integer.parseInt(String.valueOf(events[0]));
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }    
    
    public ProductionEvents objectById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        ProductionEvents event = null;
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            String hql  = "FROM ProductionEvents E WHERE E.idProEve = :id_crop";
            Query query = session.createQuery(hql);
            query.setParameter("id_crop", id);
            event = (ProductionEvents)query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return event;
    }    

    public void save(ProductionEvents event) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(event);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void delete(ProductionEvents event) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(event);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    public void getProductionEvents(HashMap args, String fileName) 
    {        
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Object[]> events = null;
        Transaction tx = null;    

        String sql = "";
        String entType = String.valueOf(args.get("entType"));
        
        sql += "select ep.id_pro_eve as ID_CULTIVO, l.id_fie as ID_LOTE, f.id_far as ID_FINCA, p.id_pro as ID_PROD, e.name_ent as USUARIO, ent.name_ent as PRODUCTOR, concat(ent.document_type_ent, ':', ent.document_number_ent) as CEDULA, f.name_far as FINCA, l.latitude_fie as LAT_LOTE, l.longitude_fie as LONG_LOTE, ";
        sql += "sie.date_sow as FECHA_SIEMBRA, sie.sowing_type_sow as TIPO_SIEMBRA, sie.seeds_number_sow as NUM_SEMILLAS, IF(sie.treated_seeds_sow=true,'SI','NO') as SEM_TRATADAS, sie.furrows_distance_sow as DIST_SURCOS, sie.sites_distance_sow as DIST_PLANTAS, ";
        sql += "tc.name_cro_typ as TIPO_CULTIVO, IF(tc.id_cro_typ=1,csem.color_see_col,'N/A') as COLOR_ENDOSPERMO, IF(tc.id_cro_typ=2,fr.seeds_number_site_bea,'N/A') as SEM_POR_SITIO, IF(tc.id_cro_typ=2,ts.name_see_typ,'N/A') as TIPO_DE_SEMILLA, ";
        sql += "IF(tc.id_cro_typ=2,IF(fr.growing_environment_bea = 1, 'Arbustivo',IF(fr.growing_environment_bea = 2, 'Voluble', '')),'N/A') as HABITO_CRECIMIENTO, mg.name_gen as MATERIAL_GENETICO, ";
        sql += "ep.expected_production_pro_eve as OBJ_RDT, t.name_cro_typ as CULT_ANT, ep.draining_pro_eve as DRENAJE, ";
        sql += "mf.emergence_phy_mon as FECHA_EMERGENCIA, mf.20_days_population_mon_fis as POBLACION_20DIAS, mf.flowering_date_phy_mon as FECHA_FLORACION, ";
        sql += "cs.date_har as FECHA_COSECHA, cs.method_har as METODO_COSECHA, cs.yield_har as RDT, pres.name_res_pro as PROD_ESPERADA";
        sql += " from production_events ep";
        sql += " inner join fields l on ep.id_field_pro_eve = l.id_fie";
        sql += " inner join fields_producers lp on lp.id_field_fie_pro = l.id_fie";
        sql += " inner join farms f on l.id_farm_fie=f.id_far";
        sql += " inner join farms_producers fp on f.id_far = fp.id_farm_far_pro";
        sql += " inner join producers p on p.id_pro = fp.id_producer_far_pro";
        sql += " inner join entities ent on ent.id_ent = p.id_entity_pro";
        sql += " inner join municipalities m on m.id_mun  = f.id_municipipality_far";
        sql += " inner join departments d on d.id_dep=m.id_department_mun";
        sql += " left join crops_types t on t.id_cro_typ=ep.former_crop_pro_eve and t.status_cro_typ=1";
        sql += " left join sowing sie on sie.id_production_event_sow = ep.id_pro_eve";
        sql += " left join genotypes mg on mg.id_gen=sie.genotype_sow and mg.status_gen=1";
        sql += " left join beans fr on fr.id_production_event_bea = ep.id_pro_eve";
        sql += " left join seeds_types ts on ts.id_see_typ = fr.seed_type_bea";
        sql += " left join maize ma on ma.id_production_event_mai = ep.id_pro_eve";
        sql += " left join seeds_colors csem on csem.id_see_col = ma.grain_color_mai and csem.status_see_col=1";
        sql += " left join rains ll on ll.id_production_event_rain = ep.id_pro_eve";
        sql += " left join physiological_monitoring mf on mf.id_production_event_phy_mon = ep.id_pro_eve";
        sql += " left join harvests cs on cs.id_production_event_har = ep.id_pro_eve";
        sql += " left join resulting_products pres on pres.id_res_pro = cs.expected_product_type_har and pres.status_res_pro = 1";
        sql += " inner join crops_types tc on tc.id_cro_typ = ep.id_crop_type_pro_eve and tc.status_cro_typ=1";
        sql += " left join log_entities le on le.id_object_log_ent = ep.id_pro_eve AND le.table_log_ent = 'production_events'";
        sql += " inner join entities e on le.id_entity_log_ent = e.id_ent";
        if (entType.equals("3")) {
            sql += " inner join extension_agents ext on (ext.id_entity_ext_age=e.id_ent)";
            sql += " inner join association ass on (ass.id_asc=ext.id_asso_ext_age)";
        }
        sql += " where le.action_type_log_ent = 'C'";
        sql += " and ep.status=1 and l.status=1 and f.status=1 and ent.status=1";
        sql += " and ep.id_crop_type_pro_eve in (1,2)";
        if (!entType.equals("3") && args.containsKey("idEntUser")) {
            sql += " and le.id_entity_log_ent="+args.get("idEntUser");
        } else {
            String selAll  = String.valueOf(args.get("selAll"));
            if (selAll.equals("true")) {
                sql += " and ass.id_entity_asc="+args.get("idEntUser");
            } else {
                sql += " and le.id_entity_log_ent in ("+args.get("selItem")+")";
            }
        }
        sql += " and le.id_object_log_ent not in (";
        sql += "	select le.id_object_log_ent from log_entities le ";
        if (entType.equals("3")) {
            sql += "	inner join entities entLe on (le.id_entity_log_ent=entLe.id_ent)";
            sql += "	inner join extension_agents ext on (ext.id_entity_ext_age=entLe.id_ent)";
            sql += "	inner join association ass on (ass.id_asc=ext.id_asso_ext_age)";
        }
        sql += "	where le.action_type_log_ent = 'D' AND le.table_log_ent = 'production_events'";
        if (!entType.equals("3") && args.containsKey("idEntUser")) {
            sql += " and le.id_entity_log_ent="+args.get("idEntUser");
        } else {
            String selAll  = String.valueOf(args.get("selAll"));
            if (selAll.equals("true")) {
                sql += " and ass.id_entity_asc="+args.get("idEntUser");
            } else {
                sql += " and le.id_entity_log_ent in ("+args.get("selItem")+")";
            }
        }
        sql += ")";
        sql += " order by ep.id_pro_eve, e.name_ent, ent.name_ent";

//        System.out.println("sql=>"+sql);
        try {
            tx = session.beginTransaction();
            CSVWriter writer = new CSVWriter(new FileWriter(fileName), ';');
            Query query  = session.createSQLQuery(sql);
            events = query.list();
        
            String[] val = {
                "ID_CULTIVO",
                "ID_LOTE",
                "ID_FINCA",
                "ID_PROD",
                "USUARIO",
                "PRODUCTOR",
                "CEDULA",
                "FINCA",
                "LAT_LOTE",
                "LONG_LOTE",
                "FECHA_SIEMBRA",
                "TIPO_SIEMBRA",
                "NUM_SEMILLAS",
                "SEM_TRATADAS",
                "DIST_SURCOS",
                "DIST_PLANTAS",
                "TIPO_CULTIVO",
                "COLOR_ENDOSPERMO",
                "SEM_POR_SITIO",
                "TIPO_SEMILLA",
                "HABITO_CRECIMIENTO",
                "MATERIAL_GENETICO",
                "OBJ_RDT",
                "CULT_ANT",
                "DRENAJE",
                "FECHA_EMERGENCIA",
                "POBLACION_20DIAS",
                "FECHA_FLORACION",
                "FECHA_COSECHA",
                "METODO_COSECHA",
                "RDT",
                "PROD_ESPERADA"
            };
            writer.writeNext(val);
            for (Object[] data : events) {
                String[] valTemp = {
                    String.valueOf(data[0]),
                    String.valueOf(data[1]),
                    String.valueOf(data[2]),
                    String.valueOf(data[3]),
                    String.valueOf(data[4]),
                    String.valueOf(data[5]),
                    String.valueOf(data[6]),
                    String.valueOf(data[7]),
                    String.valueOf(data[8]),
                    String.valueOf(data[9]),
                    String.valueOf(data[10]),
                    String.valueOf(data[11]),
                    String.valueOf(data[12]),
                    String.valueOf(data[13]),
                    String.valueOf(data[14]),
                    String.valueOf(data[15]),
                    String.valueOf(data[16]),
                    String.valueOf(data[17]),
                    String.valueOf(data[18]),
                    String.valueOf(data[19]),
                    String.valueOf(data[20]),
                    String.valueOf(data[21]),
                    String.valueOf(data[22]),
                    String.valueOf(data[23]),
                    String.valueOf(data[24]),
                    String.valueOf(data[25]),
                    String.valueOf(data[26]),
                    String.valueOf(data[27]),
                    String.valueOf(data[28]),
                    String.valueOf(data[29]),
                    String.valueOf(data[30]),
                    String.valueOf(data[31])
                };
                writer.writeNext(valTemp);
            }
            writer.close();
//            stmt   = con.prepareStatement(sql);     
//            result = stmt.executeQuery();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } catch (IOException ex) {
//            Logger.getLogger(ProductionEventsDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.close();
        }
//        return result;
    }
    
    public HashMap getDataCropById(Integer id) 
    {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        
        List<Object[]> events = null;
        Transaction tx = null;
        HashMap result = new HashMap();
        
        String sql = "";        
        /*
        "191": "Fecha de cosecha" => "dateHar"
        "192": "Método de cosecha" => "methodHar"
        "193": "Producto esperado" => "expectedProduct"
        "194": "% de Humedad de la cosecha" => "humPer"
        "195": "Rendimiento (kg/ha)" => "yield" 
        "205": "Fecha de siembra" => "dateSow"
        "206": "Método de siembra" => "methodSow"
        "207": "Kilogramos de semilla sembrada X hectárea (kg/ha)" => "seedsNumber"
        "208": "Tipo de material" => "genotyteType"
        "211": "Material genético (Blanco)" => "genotypeWhite"
        "212": "Material genético (Amarillo)" => "genotypeYellow"
        "213": "Color del endospermo" => "grainColor"
        "214": "Semillas tratadas ?" => "treatedSeeds"
        "226": "Cultivo anterior" => "formerCrop"
        "227": "Se hace drenaje en la parcela" => "drainingPro"
        "230": "Fecha de emergencia" => "emergencePhy"
        "231": "Poblacion a los 20 días" => "twentyDaysPopulation"
        "232": "Fecha de floración" => "floweringDate"
        "233": "Cantidad total (kg)" => "productionHar"
        "234": "Observaciones que afectaron la producción" => "commentHar"
        "240": "Seleccione el lote asociado" => "idField"
        "371": "Mejor rendimiento obtenido en el lote (kg/ha)" => "expectedProduction"
        "372": "Producto usado" => "usedChemical" ("treatedSeeds")
        "373": "Distancia entre surcos (m)" => "furrowsDistance" ("treatedSeeds")
        "374": "Distancia entre sitios (m)" => "sitesDistance" ("treatedSeeds")
        "375": "Numero de semillas por sitio" => "seedsNumberSite"
        "386": "Porcentaje de resiembra" => "percentageRes"
        "387": "Numero de bulto (ha)" => "numberSacks"
        "388": "Peso promedio de un bulto (kg/bulto)" => "weightAvg"
        "389": "Peso promedio de la bolsa" => "weightAvgPouch"
        */
        
        sql += "select DATE_FORMAT(ha.date_har,'%Y-%m-%d') as dateHar, ha.method_har, ha.expected_product_type_har, ha.humidity_percentage_har, FORMAT(ha.yield_har,0),"; //4
        sql += " ha.storage_har, ha.production_har, ha.comment_har, ha.number_sacks_sow, ha.weight_avg_sacks_sow,";//9
        sql += " DATE_FORMAT(s.date_sow,'%Y-%m-%d') as dateSow, s.sowing_type_sow, FORMAT(s.seeds_number_sow,0), s.genotyte_type_seed_sow, s.genotype_sow, s.treated_seeds_sow,";//15
        sql += " s.used_chemical_sow, FORMAT(s.furrows_distance_sow,0), FORMAT(s.sites_distance_sow,0),";//18
        sql += " mai.grain_color_mai, FORMAT(mai.seeds_number_site_mai,0),";//20
        sql += " DATE_FORMAT(pm.emergence_phy_mon,'%Y-%m-%d') as dateEmer, pm.20_days_population_mon_fis, DATE_FORMAT(pm.flowering_date_phy_mon,'%Y-%m-%d') as dateFlow, pm.percentage_reseeding_phy_mon,";//24
        sql += " pe.id_field_pro_eve, pe.former_crop_pro_eve, pe.draining_pro_eve, FORMAT(pe.expected_production_pro_eve,0),";//28
        sql += " pe.id_pro_eve, pe.status, ha.id_har, s.id_sow, mai.id_mai, pm.id_phy_mon,";//34
        sql += " l.id_fie, l.name_fie, s.seed_origin_sow, be.seeds_number_site_bea, be.seeds_inoculation_bea,";//39
        sql += " be.otro_inoculation_bea, be.growing_environment_bea, ha.load_hectare_sow, be.id_bea, pe.id_crop_type_pro_eve";//44
        sql += " from production_events pe";
        sql += " inner join fields l on pe.id_field_pro_eve = l.id_fie";
        sql += " left join harvests ha on ha.id_production_event_har=pe.id_pro_eve";   
        sql += " left join sowing s on s.id_production_event_sow=pe.id_pro_eve";   
        sql += " left join maize mai on mai.id_production_event_mai=pe.id_pro_eve";   
        sql += " left join beans be on be.id_production_event_bea=pe.id_pro_eve";   
        sql += " left join physiological_monitoring pm on pm.id_production_event_phy_mon=pe.id_pro_eve";   
        sql += " where pe.status=1 and l.status=1";
        if (id!=null) {
            sql += " and pe.id_pro_eve="+id;
        }
        
//        System.out.println("sql->"+sql);        
        try {
            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql);            
            events = query.list();         
            
            for (Object[] data : events) {
                
                HashMap temp = new HashMap();
                temp.put("dateHar", data[0]);
                temp.put("methodHar", data[1]);
                temp.put("expectedProduct", data[2]);             
                temp.put("humPer", data[3]);      
                temp.put("yield", data[4]);        
                temp.put("dateSow", data[10]);
                temp.put("methodSow", data[11]);                         
                temp.put("seedsNumber", data[12]);              
                temp.put("genotyteType", data[13]);           
                
                temp.put("grainColor", data[19]);      
                String seedColor = String.valueOf(temp.get("grainColor"));
                if (seedColor.equals("1")) {
                    temp.put("genotypeWhite", data[14]);                
                    temp.put("genotypeYellow", ""); 
                } else if (seedColor.equals("2")) {
                    temp.put("genotypeWhite", "");                
                    temp.put("genotypeYellow", data[14]); 
                }                         
                
                temp.put("treatedSeeds", data[15]);
                temp.put("formerCrop", data[26]);
                temp.put("drainingPro", data[27]);
                temp.put("emergencePhy", data[21]);
                temp.put("twentyDaysPopulation", data[22]);
                temp.put("floweringDate", data[23]);
                temp.put("productionHar", data[6]);
                
                temp.put("commentHar", data[7]);
                temp.put("idField", data[25]);
                temp.put("expectedProduction", data[28]);
                temp.put("usedChemical", data[16]);
                temp.put("furrowsDistance", data[17]);
                temp.put("sitesDistance", data[18]);
                temp.put("seedsNumberSite", data[20]);
                temp.put("percentageRes", data[24]);
                temp.put("numberSacks", data[8]);
                temp.put("storage", data[5]);
                
                //3 - Mazorca => peso bulto
                //4 - Ensilaje => peso bolsa
                String valProduct = String.valueOf(temp.get("expectedProduct"));
                if (valProduct.equals("3")) {
                    temp.put("weightAvg", data[9]);
                    temp.put("weightAvgPouch", "");
                } else if (valProduct.equals("4")) {
                    temp.put("weightAvg", "");
                    temp.put("weightAvgPouch", data[9]);
                }                
                
                
                String cropType = String.valueOf(data[44]);
                String prep="", fert="", mont="", cont="", res="", irr="", obs="";
                if (cropType.equals("1")) {
                    prep = new PreparationsDao().getPreparations(id);
                    fert = new FertilizationsDao().getFertilizations(id);
                    mont = new MonitoringDao().getMonitorings(id);
                    cont = new ControlsDao().getControls(id);
                    res  = new ResidualsManagementDao().getResiduals(id);
                    irr  = new IrrigationDao().getIrrigations(id);
                    obs  = new DescriptionsProductionEventDao().getDescriptions(id);
                } else if (cropType.equals("2")) {
                    prep = new PreparationsDao().getPreparationsBeans(id);
                    fert = new FertilizationsDao().getFertilizationsBeans(id);
                    mont = new MonitoringDao().getMonitoringsBeans(id);
                    cont = new ControlsDao().getControlsBeans(id);
                    res  = new ResidualsManagementDao().getResidualsBeans(id);
                    irr  = new IrrigationDao().getIrrigationsBeans(id);
                    obs  = new DescriptionsProductionEventDao().getDescriptionsBeans(id);
                }
                
//                System.out.println("prep=>"+prep);
//                System.out.println("fert=>"+fert);
//                System.out.println("mont=>"+mont);
//                System.out.println("cont=>"+cont);
//                System.out.println("res=>"+res);
//                System.out.println("irr=>"+irr);
//                System.out.println("obs=>"+obs);
                
                temp.put("preparations", prep);
                temp.put("fertilizations", fert);
                temp.put("monitorings", mont);
                temp.put("controls", cont);
                temp.put("residuals", res);
                temp.put("irrigations", irr);
                temp.put("observations", obs);
                
                temp.put("idCrop", data[29]);
                temp.put("status", data[30]);
                temp.put("idHar", data[31]);
                temp.put("idSow", data[32]);
                temp.put("idMaize", data[33]);
                temp.put("idPhys", data[34]);
                temp.put("lat", "");
                temp.put("lng", "");
                temp.put("alt", ""); 
                
                temp.put("fieldId", data[35]);
                temp.put("nameField", data[36]); 
                
                //Nuevas de Frijol
                temp.put("origin", data[37]); 
                temp.put("seedsNumber", data[38]); 
                temp.put("seedsIn", data[39]); 
                temp.put("otherIn", data[40]); 
                temp.put("growingEnv", data[41]); 
                temp.put("loadHec", data[42]); 
                temp.put("idBean", data[43]); 
                temp.put("cropType", data[44]); 
                
                String growingEnv = String.valueOf(temp.get("growingEnv"));
                if (growingEnv.equals("1")) {
                    temp.put("genotypeArb", data[14]);                
                    temp.put("genotypeVol", ""); 
                } else if (growingEnv.equals("2")) {
                    temp.put("genotypeArb", "");                
                    temp.put("genotypeVol", data[14]); 
                }
                
                result = temp;
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }
    
    
    public void setInfoMongo() 
    {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        
        List<Object[]> events = null;
        Transaction tx = null;
        
        String sql = "";
        
        sql += "select ep.id_pro_eve, ep.id_crop_type_pro_eve, e.email_ent";
        sql += " from production_events ep";
        sql += " inner join fields l on ep.id_field_pro_eve = l.id_fie";
        sql += " inner join farms f on l.id_farm_fie=f.id_far";
        sql += " inner join log_entities le on le.id_object_log_ent = ep.id_pro_eve AND le.table_log_ent = 'production_events'";
        sql += " inner join entities e on le.id_entity_log_ent = e.id_ent";
        sql += " where le.id_entity_log_ent in (3,4,5,6,8,200,201,202,665,706,707,708,709,710,711,712,713,714,715,823)";
        sql += " and le.action_type_log_ent = 'C'";
        sql += " and ep.status=1 and l.status=1 and f.status=1";
        sql += " and le.id_object_log_ent not in (select le.id_object_log_ent from log_entities le where le.id_entity_log_ent in (3,4,5,6,8,200,201,202,665,706,707,708,709,710,711,712,713,714,715,823) and le.action_type_log_ent = 'D' AND le.table_log_ent = 'production_events')";

        
        try {
            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql);            
            events = query.list();         
            
            for (Object[] data : events) {
//                System.out.println(data);
                HashMap temp = new HashMap();
                temp.put("idCrop", data[0]);
                temp.put("typeCrop", data[1]);     
                
                String emailUser = String.valueOf(data[2]);
                
                SfGuardUserDao sfDao = new SfGuardUserDao();
                SfGuardUser sfUser   = sfDao.getUserByLogin(emailUser, "");
                Long idUserMobile    = null;
                if (sfUser!=null) {
                    idUserMobile = sfUser.getId();
                }                
                
                Integer idCrop   = Integer.parseInt(String.valueOf(temp.get("idCrop")));
                Integer typeCrop = Integer.parseInt(String.valueOf(temp.get("typeCrop")));                
                
                GlobalFunctions.sendInformationCrop(idCrop, typeCrop, idUserMobile);
                
            }   
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error ingresando al MongoDB");
        } finally {
            session.close();
        }
    }
    
}
