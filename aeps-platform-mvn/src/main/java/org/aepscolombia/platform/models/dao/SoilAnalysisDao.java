package org.aepscolombia.platform.models.dao;

import au.com.bytecode.opencsv.CSVWriter;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.aepscolombia.platform.controllers.ActionField;
import org.aepscolombia.platform.models.entity.LogEntities;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.SoilAnalysis;
import org.aepscolombia.platform.models.entityservices.SfGuardUser;
import org.aepscolombia.platform.util.GlobalFunctions;
import org.aepscolombia.platform.util.HibernateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * Clase SoilAnalysisDao
 *
 * Contiene los metodos para interactuar con la tabla SoilAnalysis de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class SoilAnalysisDao 
{    
    
    public HashMap findById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        
        List<Object[]> events = null;
        Transaction tx = null;
        HashMap result = new HashMap();
        
        String sql = "";
        String sqlAdd = "";
        
        sql += "select r.id_so_ana, r.id_production_event_so_ana, l.id_fie, l.name_fie, e.name_ent, f.name_far, r.date_sampling_so_ana, r.sample_number_so_ana, r.sand_so_ana, r.lemon_so_ana, r.clay_so_ana,";
        sql += " r.texture_so_ana, r.organic_material_so_ana, r.dapa_so_ana,";
        sql += " r.ph_so_ana, r.elec_conductivity_so_ana, r.cation_interchangeability_so_ana, r.co_so_ana, r.nitrogen_so_ana,";
        sql += " r.phosphorus_so_ana, r.potassium_so_ana, r.calcium_so_ana, r.magnesium_so_ana,";
        sql += " r.sulfur_so_ana, r.boron_so_ana, r.zinc_so_ana, r.iron_so_ana,";
        sql += " r.sodium_so_ana, r.manganese_so_ana, r.copper_so_ana, r.silicon_so_ana, r.exchangeable_acidity_h_so_ana,";
        sql += " r.exchangeable_acidity_three_so_ana, r.id_project_so_ana, r.status";

        sql += " from soil_analysis r";
        sql += " inner join log_entities le on le.id_object_log_ent=r.id_so_ana and le.table_log_ent='soil_analysis' and le.action_type_log_ent='C'";   
        sql += " inner join production_events ep on ep.id_pro_eve=r.id_production_event_so_ana";  
        sql += " inner join fields l on ep.id_field_pro_eve=l.id_fie";
        sql += " inner join fields_producers lp on lp.id_field_fie_pro=l.id_fie";
        sql += " left join farms f on f.id_far=l.id_farm_fie";
        sql += " inner join producers p on p.id_pro=lp.id_producer_fie_pro"; 
        sql += " inner join entities e on e.id_ent=p.id_entity_pro"; 
        sql += " where l.status=1 and f.status=1";
        sql += " and r.status=1 and e.status=1";
        if (id!=null) {
            sql += " and r.id_so_ana="+id;
        }
//        args.get("countTotal");
//        events.toArray();
//        System.out.println("sql->"+sql);        
        try {
            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql);            
            events = query.list();         
            
            for (Object[] data : events) {
                HashMap temp = new HashMap();
                temp.put("id_lot", data[0]);
                temp.put("id_farm", data[1]);
                temp.put("type_lot", data[2]);             
                temp.put("name_lot", data[3]);                
                temp.put("altitude_lot", data[4]);
                temp.put("latitude_lot", data[5]);
                temp.put("length_lot", data[6]);                
                temp.put("area_lot", data[7]);                
                temp.put("status", data[8]);
                temp.put("id_producer", data[9]);
                temp.put("name_producer", data[10]);
                temp.put("name_farm", data[11]);       
                result = (temp);
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
    
    public List<SoilAnalysis> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<SoilAnalysis> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from SoilAnalysis");
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
        
        sql += "select r.id_so_ana, r.id_production_event_so_ana, crop.name_cro_typ, l.id_fie, l.name_fie, e.name_ent, f.name_far,";
        sql += " r.date_sampling_so_ana, r.sample_number_so_ana, r.sand_so_ana, r.lemon_so_ana, r.clay_so_ana, tex.name_tex, r.organic_material_so_ana, r.dapa_so_ana,";
        sql += " r.ph_so_ana, r.elec_conductivity_so_ana, r.cation_interchangeability_so_ana, r.co_so_ana, r.nitrogen_so_ana,";
        sql += " r.phosphorus_so_ana, r.potassium_so_ana, r.calcium_so_ana, r.magnesium_so_ana,";
        sql += " r.sulfur_so_ana, r.boron_so_ana, r.zinc_so_ana, r.iron_so_ana,";
        sql += " r.sodium_so_ana, r.manganese_so_ana, r.copper_so_ana, r.silicon_so_ana, r.exchangeable_acidity_h_so_ana,";
        sql += " r.exchangeable_acidity_three_so_ana, r.id_project_so_ana, r.status,";
        if (entType.equals("3")) {
            sql += " le.date_log_ent, entLe.name_ent as nameAgro";
        } else {
            sql += " le.date_log_ent";
        }
        sql += " from soil_analysis r";
        sql += " inner join log_entities le on le.id_object_log_ent=r.id_so_ana and le.table_log_ent='soil_analysis' and le.action_type_log_ent='C'";   
        sql += " inner join production_events ep on ep.id_pro_eve=r.id_production_event_so_ana";  
        sql += " inner join crops_types crop on ep.id_crop_type_pro_eve=crop.id_cro_typ";
        sql += " inner join textures tex on tex.id_tex=r.texture_so_ana";
        if (entType.equals("3")) {
            sql += " inner join entities entLe on (le.id_entity_log_ent=entLe.id_ent)"; 
            sql += " inner join extension_agents ext on (ext.id_entity_ext_age=entLe.id_ent)";
            sql += " inner join agents_association agAsc on (agAsc.id_agent_age_asc=ext.id_ext_age)";
            sql += " inner join association ass on (ass.id_asc=agAsc.id_asso_age_asc)";
        }
        sql += " inner join fields l on ep.id_field_pro_eve=l.id_fie";
        sql += " inner join fields_producers lp on lp.id_field_fie_pro=l.id_fie";
        sql += " left join farms f on f.id_far=l.id_farm_fie";
        sql += " left join municipalities m on m.id_mun = f.id_municipipality_far";
        sql += " left join departments d on d.id_dep=m.id_department_mun";
        sql += " inner join producers p on p.id_pro=lp.id_producer_fie_pro"; 
        sql += " inner join entities e on e.id_ent=p.id_entity_pro"; 
        sql += " where ep.status=1 and f.status=1";
        sql += " and r.status=1 and e.status=1";    
        
                
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
        
        if (args.containsKey("search_soil")) {
            String valIdent = String.valueOf(args.get("search_soil"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) { 
                sql += " and ((r.sample_number_so_ana like '%"+valIdent+"%')";
                
                try {
                    String dateAsign = new SimpleDateFormat("yyyy-MM-dd").format(new Date(valIdent));
                    sql += " or (r.date_sampling_so_ana like '%"+dateAsign+"%')";                    
                } catch (IllegalArgumentException ex) {
//                    ex.printStackTrace();
//                    Logger.getLogger(SoilAnalysisDao.class.getName()).log(Level.SEVERE, null, ex);
                }
                sql += " or (crop.name_cro_typ like '%"+valIdent+"%')";
                sql += " or (l.name_fie like '%"+valIdent+"%')";
                sql += " or (f.name_far like '%"+valIdent+"%')";
                sql += " or (m.name_mun like '%"+valIdent+"%')";
                sql += " or (d.name_dep like '%"+valIdent+"%'))";
            }
        }       
                
        if (args.containsKey("sample_number")) {
            String valIdent = String.valueOf(args.get("sample_number"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and r.sample_number_so_ana like '%"+args.get("sample_number")+"%'";
        }        
        if (args.containsKey("date_sampling")) {
            String valIdent = String.valueOf(args.get("date_sampling"));    
//            SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");            
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) {
//                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(oldstring);
                String dateAsign = new SimpleDateFormat("yyyy-MM-dd").format(new Date(valIdent));
                sql += " and r.date_sampling_so_ana like '%"+dateAsign+"%'";
            }
        }
        if (args.containsKey("id_crop_type")) {
            String valIdent = String.valueOf(args.get("id_crop_type"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("-1") && !valIdent.equals("null")) sql += " and crop.id_cro_typ='"+args.get("id_crop_type")+"'";
        }    
        if (args.containsKey("name_field")) {
            String valIdent = String.valueOf(args.get("name_field"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and l.name_fie like '%"+args.get("name_field")+"%'";
        }        
        if (args.containsKey("name_farm")) {
            String valIdent = String.valueOf(args.get("name_farm"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and f.name_far like '%"+args.get("name_farm")+"%'";
        }  
        if (args.containsKey("name_mun")) {
            String valIdent = String.valueOf(args.get("name_mun"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and m.name_mun like '%"+args.get("name_mun")+"%'";
        } 
        if (args.containsKey("name_dep")) {
            String valIdent = String.valueOf(args.get("name_dep"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and d.name_dep like '%"+args.get("name_dep")+"%'";
        }
        sql += sqlAdd;

//        if (args.containsKey("idFin")) {
//            sql += " and f.id_fin="+args.get("idFin");
//        }
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
//        sql += " order by e.name_ent ASC";
        sql += " order by r.id_so_ana ASC";
//        events.toArray();
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
            
//            sql += "select r.id_so_ana, r.id_production_event_so_ana, crop.name_cro_typ, l.id_fie, l.name_fie, e.name_ent, f.name_far,";
//            sql += " r.date_sampling_so_ana, r.sample_number_so_ana, r.sand_so_ana, r.lemon_so_ana, r.clay_so_ana, r.texture_so_ana, r.organic_material_so_ana, r.dapa_so_ana,";
//            sql += " r.ph_so_ana, r.elec_conductivity_so_ana, r.cation_interchangeability_so_ana, r.co_so_ana, r.nitrogen_so_ana,";
//            sql += " r.phosphorus_so_ana, r.potassium_so_ana, r.calcium_so_ana, r.magnesium_so_ana,";
//            sql += " r.sulfur_so_ana, r.boron_so_ana, r.zinc_so_ana, r.iron_so_ana,";
//            sql += " r.sodium_so_ana, r.manganese_so_ana, r.copper_so_ana, r.silicon_so_ana, r.exchangeable_acidity_h_so_ana,";
//            sql += " r.exchangeable_acidity_three_so_ana, r.id_project_so_ana, r.status,";
            for (Object[] data : events) {
                HashMap temp = new HashMap();
                temp.put("id_soil", data[0]);
                temp.put("id_crop", data[1]);
                temp.put("name_crop", data[2]);
                temp.put("id_field", data[3]);
                temp.put("date", data[7]);   
                temp.put("num_sampling", data[8]);          
                temp.put("texture_type", data[12]);                
                temp.put("name_field", data[4]);
                temp.put("name_producer", data[5]);                
                temp.put("name_farm", data[6]);
                                  
//                String val = String.valueOf(data[5]);
//                Date newDate   = new SimpleDateFormat("yyyy-MM-dd").parse(val);              
                temp.put("ph", data[15]);                
                temp.put("cation", data[17]);                    
                temp.put("status", data[35]);
                temp.put("dateLog", data[36]);
                if (entType.equals("3")) {
                    temp.put("nameAgro", data[37]);
                }
                result.add(temp);
            }
//            System.out.println("values->"+result);
//            System.out.println(result);
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
    
    public static Integer countData(HashMap args) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Object[] events = null;
        Transaction tx = null;
        Integer result = 0;
        
        String sql = "";     
        String sqlAdd = "";    
        String entType = String.valueOf(args.get("entType"));
        
        sql += "select count(r.id_so_ana), r.id_so_ana";               
        sql += " from soil_analysis r";
        sql += " inner join log_entities le on le.id_object_log_ent=r.id_so_ana and le.table_log_ent='soil_analysis' and le.action_type_log_ent='C'";   
        if (entType.equals("3")) {
            sql += " inner join entities entLe on (le.id_entity_log_ent=entLe.id_ent)"; 
            sql += " inner join extension_agents ext on (ext.id_entity_ext_age=entLe.id_ent)";
            sql += " inner join agents_association agAsc on (agAsc.id_agent_age_asc=ext.id_ext_age)";
            sql += " inner join association ass on (ass.id_asc=agAsc.id_asso_age_asc)";
        }
        sql += " inner join production_events ep on ep.id_pro_eve=r.id_production_event_so_ana";  
        sql += " inner join fields l on ep.id_field_pro_eve=l.id_fie";
        sql += " inner join fields_producers lp on lp.id_field_fie_pro=l.id_fie";
        sql += " inner join farms f on f.id_far=l.id_farm_fie";
        sql += " inner join producers p on p.id_pro=lp.id_producer_fie_pro"; 
        sql += " inner join entities e on e.id_ent=p.id_entity_pro";
        
        sql += " where ep.status=1 and f.status=1";
        sql += " and r.status=1 and e.status=1"; 
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
    
    public SoilAnalysis objectById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        SoilAnalysis event = null;
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            String hql  = "FROM SoilAnalysis E WHERE E.idSoAna = :id_analysis";
            Query query = session.createQuery(hql);
            query.setParameter("id_analysis", id);
            event = (SoilAnalysis)query.uniqueResult();
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

    public void save(SoilAnalysis event) {
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

    public void delete(SoilAnalysis event) {
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
    
    public void getSoilAnalysis(HashMap args, String fileName) 
    {        
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Object[]> events = null;
        Transaction tx = null;    

        String sql = "";
        String entType = String.valueOf(args.get("entType"));
        
        sql += "select r.id_so_ana as ID_ANALISIS, l.id_fie as ID_LOTE, f.id_far as ID_FINCA, p.id_pro as ID_PROD, e.name_ent as USUARIO, crop.name_cro_typ as CULTIVO, r.sample_number_so_ana as MUESTRA, ";
        sql += "DATE_FORMAT(r.date_sampling_so_ana,'%Y-%m-%d') as FECHA_MUESTREO, r.sand_so_ana as ARENA, r.lemon_so_ana as LIMOS, r.clay_so_ana as ARCILLA, tex.name_tex as TEXTURA, ";
        sql += "r.organic_material_so_ana as MATERIA_ORGANICA, r.dapa_so_ana as DAPA, r.ph_so_ana as PH, ";
        sql += "r.elec_conductivity_so_ana as CONDUCTIVIDAD_ELECTRICA, r.cation_interchangeability_so_ana as CAPACIDAD_INTERCAMBIO_CATIONICO, r.co_so_ana as CO, ";
        sql += "r.nitrogen_so_ana as NITROGENO, r.phosphorus_so_ana as FOSFORO, r.potassium_so_ana as POTASIO, r.calcium_so_ana as CALCIO, ";
        sql += "r.magnesium_so_ana as MAGNESIO, r.sulfur_so_ana as AZUFRE, r.boron_so_ana as BORO, r.zinc_so_ana as ZINC, ";
        sql += "r.iron_so_ana as HIERRO, r.sodium_so_ana as SODIO, r.manganese_so_ana as MANGANESO, r.copper_so_ana as COBRE, ";
        sql += "r.silicon_so_ana as SILICIO, r.exchangeable_acidity_h_so_ana as ACIDEZ_INTERCAMBIABLE_H, r.exchangeable_acidity_three_so_ana as ACIDEZ_INTERCAMBIABLE_3";
        sql += " from soil_analysis r";
        sql += " inner join production_events ep on ep.id_pro_eve=r.id_production_event_so_ana";
        sql += " inner join textures tex on tex.id_tex=r.texture_so_ana";
        sql += " inner join crops_types crop on ep.id_crop_type_pro_eve=crop.id_cro_typ";
        sql += " inner join fields l on ep.id_field_pro_eve=l.id_fie";
        sql += " inner join fields_producers lp on lp.id_field_fie_pro = l.id_fie";
        sql += " inner join farms f on l.id_farm_fie=f.id_far";
        sql += " inner join farms_producers fp on f.id_far = fp.id_farm_far_pro";
        sql += " inner join producers p on p.id_pro = fp.id_producer_far_pro";
        sql += " inner join entities ent on ent.id_ent = p.id_entity_pro";
        sql += " left join log_entities le on le.id_object_log_ent = r.id_so_ana AND le.table_log_ent = 'soil_analysis'";
        sql += " inner join entities e on le.id_entity_log_ent = e.id_ent";
        if (entType.equals("3")) {
            sql += " inner join extension_agents ext on (ext.id_entity_ext_age=e.id_ent)";
            sql += " inner join agents_association agAsc on (agAsc.id_agent_age_asc=ext.id_ext_age)";
            sql += " inner join association ass on (ass.id_asc=agAsc.id_asso_age_asc)";
        }
        sql += " where le.action_type_log_ent = 'C'";
        sql += " and r.status=1 and l.status=1 and f.status=1 and ent.status=1";
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
            sql += " inner join agents_association agAsc on (agAsc.id_agent_age_asc=ext.id_ext_age)";
            sql += " inner join association ass on (ass.id_asc=agAsc.id_asso_age_asc)";
        }
        sql += "	where le.action_type_log_ent = 'D' AND le.table_log_ent = 'soil_analysis'";
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
            File myFileTemp = new File("soilsTemp.xls");
            FileInputStream fis = new FileInputStream(myFileTemp);
            
            HSSFWorkbook workbook = new HSSFWorkbook(fis);            
            HSSFSheet sheet = workbook.getSheetAt(0);
            
            Map<String, Object[]> dataSheet = new TreeMap<String, Object[]>();
//            CSVWriter writer = new CSVWriter(new FileWriter(fileName), ';');
            Query query  = session.createSQLQuery(sql);
            events = query.list();
        
            Object[] val = {
                "ID_ANALISIS",
                "ID_LOTE",
                "ID_FINCA",
                "ID_PROD",
                "USUARIO",
                "CULTIVO",
                "MUESTRA",
                "FECHA_MUESTREO",
                "ARENA",
                "LIMOS",
                "ARCILLA",
                "TEXTURA",
                "MATERIA_ORGANICA",
                "DAPA",
                "PH",
                "CONDUCTIVIDAD_ELECTRICA",
                "CAPACIDAD_INTERCAMBIO_CATIONICO",
                "CO",
                "NITROGENO(N)",
                "FOSFORO(P)",
                "POTASIO(K)",
                "CALCIO(Ca)",
                "MAGNESIO(Mg)",
                "AZUFRE(S)",
                "BORO(B)",
                "ZINC(Zn)",
                "HIERRO(Fe)",
                "SODIO(Na)",
                "MANGANESO(Mn)",
                "COBRE(Cu)",
                "SILICIO(Si)",
                "ACIDEZ_INTERCAMBIABLE_H",
                "ACIDEZ_INTERCAMBIABLE_3"
            };
            dataSheet.put("1", val);
            Integer cont = 2;
            for (Object[] data : events) {
                Object[] valTemp = {
                    data[0],
                    data[1],
                    data[2],
                    data[3],
                    data[4],
                    data[5],
                    data[6],
                    data[7],
                    data[8],
                    data[9],
                    data[10],
                    data[11],
                    data[12],
                    data[13],
                    data[14],
                    data[15],
                    data[16],
                    data[17],
                    data[18],
                    data[19],
                    data[20],
                    data[21],
                    data[22],
                    data[23],
                    data[24],
                    data[25],
                    data[26],
                    data[27],
                    data[28],
                    data[29],
                    data[30],
                    data[31],
                    data[32]
                };
//                writer.writeNext(valTemp);
                dataSheet.put(""+cont, valTemp);
                cont++;
            }
//            writer.close();
            Set<String> keyset = dataSheet.keySet();
            int rownum = 0;
            for (String key : keyset)
            {
                Row row = sheet.createRow(rownum++);
                Object [] objArr = dataSheet.get(key);
                int cellnum = 0;
                for (Object obj : objArr)
                {
                    Cell cell = row.createCell(cellnum++);
                    if (obj instanceof String) {
                        cell.setCellValue((String) obj);
                    } else if (obj instanceof Boolean) {
                        cell.setCellValue((Boolean) obj);
                    } else if (obj instanceof Timestamp) {
                        cell.setCellValue((Timestamp) obj);
                    } else if (obj instanceof Date) {
                        cell.setCellValue((Date) obj);
                    } else if (obj instanceof Double) {
                        cell.setCellValue((Double) obj);
                    } else if (obj instanceof Integer) {
                        cell.setCellValue((Integer) obj);
                    } 
                }
            }
            File myFile = new File(fileName);
            if (!myFile.exists()) myFile.createNewFile();
            FileOutputStream out = new FileOutputStream(myFile);
            workbook.write(out);
            out.close();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } catch (IOException ex) {
//            Logger.getLogger(SoilAnalysisDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.close();
        }
//        return result;
    }
    
    public String deleteAllSoilAnalysis(String valSel, Integer idEntSystem) 
    {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        List<SoilAnalysis> events = null;
        Transaction tx = null;
        String sql = "";
        String state = "failure";         
        
        
        sql += "select r.id_so_ana, r.id_production_event_so_ana, r.date_sampling_so_ana, r.sample_number_so_ana, r.sand_so_ana, r.lemon_so_ana, r.clay_so_ana,"; 	
        sql += "r.texture_so_ana, r.organic_material_so_ana, r.dapa_so_ana, r.ph_so_ana, r.elec_conductivity_so_ana, r.cation_interchangeability_so_ana,"; 	
        sql += "r.co_so_ana, r.nitrogen_so_ana, r.phosphorus_so_ana, r.potassium_so_ana, r.calcium_so_ana,";	
        sql += "r.magnesium_so_ana, r.sulfur_so_ana, r.boron_so_ana, r.zinc_so_ana,"; 	
        sql += "r.iron_so_ana, r.sodium_so_ana, r.manganese_so_ana, r.copper_so_ana, r.silicon_so_ana,"; 	
        sql += "r.exchangeable_acidity_h_so_ana, r.exchangeable_acidity_three_so_ana, r.id_project_so_ana,"; 	
        sql += "r.status, r.created_by";
        sql += " from soil_analysis r";
        if (!valSel.equals("")) sql += " where r.status=1 and r.id_so_ana in ("+valSel+")";
//        System.out.println("sql=>"+sql);          
        
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("r", SoilAnalysis.class);
            events = query.list();
            MongoClient mongo = new MongoClient("localhost", 27017);
            for (SoilAnalysis soil : events) {
//                System.out.println("soilId->"+soil.getIdSoAna());
                soil.setStatus(false);     
                session.saveOrUpdate(soil);

                LogEntities log = new LogEntities();
                log.setIdLogEnt(null);
                log.setIdEntityLogEnt(idEntSystem);
                log.setIdObjectLogEnt(soil.getIdSoAna());
                log.setTableLogEnt("soil_analysis");
                log.setDateLogEnt(new Date());
                log.setActionTypeLogEnt("D");
                session.saveOrUpdate(log);

                /*BasicDBObject queryMongo = new BasicDBObject();
                queryMongo.put("InsertedId", ""+ras.getIdRas());
                queryMongo.put("form_id", "6");

                DB db = mongo.getDB("ciat");
                DBCollection col = db.getCollection("log_form_records");
                WriteResult result = null;

                System.out.println("borro mongo");
                result = col.remove(queryMongo);

                if (result.getError()!=null) {
                    throw new HibernateException("");
                }*/
            }
            mongo.close();
            tx.commit();
            state = "success";
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ActionField.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.close();
        } 
        return state;
    }
    
    /*
    public String getInfoRasta(Integer id, HashMap valInf) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        
        List<Object[]> events = null;
        Transaction tx = null;
        
        String sql = "";        
        sql += "select ent.name_ent, r.ph_ras, r.estructura_ras, r.exposicion_sol_ras, r.recubrimiento_vegetal_ras, r.pendiente_terreno_ras";
        sql += " from rastas r";
        sql += " inner join fields l on r.id_lote_ras = l.id_fie";
        sql += " inner join fields_producers lp on lp.id_field_fie_pro = l.id_fie";
        sql += " inner join farms f on l.id_farm_fie=f.id_far";
        sql += " inner join farms_producers fp on f.id_far = fp.id_farm_far_pro";
        sql += " inner join producers p on p.id_pro = fp.id_producer_far_pro";
        sql += " inner join entities ent on ent.id_ent = p.id_entity_pro";
        sql += " left join log_entities le on le.id_object_log_ent = r.id_ras AND le.table_log_ent = 'rastas'";
        sql += " inner join entities e on le.id_entity_log_ent = e.id_ent";
        sql += " where r.status=1";
        if (id!=null) {
            sql += " and r.id_ras="+id;
            sql += " and le.id_object_log_ent not in (select le.id_object_log_ent from log_entities le where le.id_object_log_ent = "+id+" and le.action_type_log_ent = 'D' AND le.table_log_ent = 'rastas')";
        }
        sql += " and le.action_type_log_ent = 'C'";
        
        String dataGeneral = "{\"valTable\" : [{";        
//        args.get("countTotal");
//        events.toArray();
//        System.out.println("sql->"+sql);        
        try {
            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql);            
            events = query.list();         
            
            for (Object[] data : events) {                
                dataGeneral   += "\"producerId\": \""+data[0]+"\",";
                dataGeneral   += "\"depthId\": \""+valInf.get("depth")+"\",";
                dataGeneral   += "\"phId\": \""+data[1]+"\",";
                dataGeneral   += "\"structureId\": \""+data[2]+"\",";
                dataGeneral   += "\"exposeId\": \""+data[3]+"\",";
                dataGeneral   += "\"coveringId\": \""+data[4]+"\",";
                dataGeneral   += "\"drainIntId\": \""+valInf.get("internal")+"\",";
                dataGeneral   += "\"drainExtId\": \""+valInf.get("external")+"\",";
                dataGeneral   += "\"valDes\": \"Pendiente: "+data[5]+"%\",";
                dataGeneral   += "\"valIn\": \""+data[5]+"\"";
            }           
            dataGeneral   += "}],";    
            String[] infoMaterials = (String[])valInf.get("organic");  
            dataGeneral   += "}";                      
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return dataGeneral;
    }
    
    public void setInfoMongo() 
    {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        
        List<Object[]> events = null;
        Transaction tx = null;
        
        String sql = "";
        
        sql += "select l.id_fie, r.id_ras, l.name_fie, e.email_ent";
        sql += " from rastas r";
        sql += " inner join fields l on r.id_lote_ras = l.id_fie";
        sql += " inner join fields_producers lp on lp.id_field_fie_pro = l.id_fie";
        sql += " inner join farms f on l.id_farm_fie=f.id_far";
        sql += " inner join log_entities le on le.id_object_log_ent = r.ID_RAS AND le.table_log_ent = 'rastas'";
        sql += " inner join entities e on le.id_entity_log_ent = e.id_ent";
        sql += " where le.id_entity_log_ent in (3,4,5,6,8,200,201,202,665,706,707,708,709,710,711,712,713,714,715,823)";
        sql += " and le.action_type_log_ent = 'C'";
        sql += " and r.status=1 and l.status=1 and f.status=1";
        sql += " and le.id_object_log_ent not in (select le.id_object_log_ent from log_entities le where le.id_entity_log_ent in (3,4,5,6,8,200,201,202,665,706,707,708,709,710,711,712,713,714,715,823) and le.action_type_log_ent = 'D' AND le.table_log_ent = 'rastas')";

        
        try {
            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql);            
            events = query.list();         
            
            for (Object[] data : events) {
//                System.out.println(data);
                HashMap temp = new HashMap();
                temp.put("idField", data[0]);
                temp.put("idRasta", data[1]);
                temp.put("nameField", data[2]);
                
                String emailUser = String.valueOf(data[3]);
                
                SfGuardUserDao sfDao = new SfGuardUserDao();
                SfGuardUser sfUser   = sfDao.getUserByLogin(null, emailUser, "");
                Integer idUserMobile = null;
                if (sfUser!=null) {
                    idUserMobile = sfUser.getId().intValue();
                }
                
                Integer idRasta = Integer.parseInt(String.valueOf(temp.get("idRasta")));
                SoilAnalysis rasta = this.objectById(idRasta);
//                System.out.println("idRasta=>"+idRasta);
                
                String dateRastaIn = new SimpleDateFormat("yyyy-MM-dd").format(rasta.getFechaRas());
                String valHor = this.getHorizons(idRasta);
                
                HashMap valInfo = new HashMap();            
                valInfo.put("dateRasta", dateRastaIn);
                valInfo.put("pend", rasta.getPendienteTerrenoRas());
                valInfo.put("terreno", rasta.getTerrenoCircundanteRas());
                valInfo.put("position", rasta.getPosicionPerfilRas());
                valInfo.put("pH", rasta.getPhRas());
                valInfo.put("carbonato", rasta.getCarbonatosRas());
                valInfo.put("profCar", rasta.getProfundidadCarbonatosRas());
                valInfo.put("pedrSupRo", rasta.getRocasSuperficieRas());
                valInfo.put("pedrSupPie", rasta.getPiedrasSuperficieRas());
                valInfo.put("pedrPerRo", rasta.getRocasPerfilRas());
                valInfo.put("pedrPerPie", rasta.getPiedrasPerfilRas());
                valInfo.put("horPed", rasta.getHorizontePedrogosoRocosoRas());
                valInfo.put("profHorPedr", rasta.getProfundidadHorizontePedregosoRas());
                valInfo.put("espHor", rasta.getEspesorHorizontePedregosoRas());
                valInfo.put("profPri", rasta.getProfundidadPrimerasPiedrasRas());
                valInfo.put("capasEnd", rasta.getCapasEndurecidasRas());
                valInfo.put("profCap", rasta.getPrufundidadCapasRas());
                valInfo.put("espCap", rasta.getEspesorCapaEndurecidaRas());
                valInfo.put("moteado", rasta.getMoteadosRas());
                valInfo.put("profMot", rasta.getProfundidadMoteadosRas());
                valInfo.put("moteadoBajo", rasta.getMoteadosMas70cmRas());
                valInfo.put("estSuelo", rasta.getEstructuraRas());
                valInfo.put("erosion", rasta.getErosionRas());
                valInfo.put("moho", rasta.getMohoRas());
                valInfo.put("cosDur", rasta.getCostrasDurasRas());
                valInfo.put("sitioSol", rasta.getExposicionSolRas());
                valInfo.put("cosBlan", rasta.getCostrasBlancasRas());
                valInfo.put("cosNeg", rasta.getCostrasNegrasRas());
                valInfo.put("regSeca", rasta.getRegionSecaRas());
                valInfo.put("raiViva", rasta.getRaicesVivasRas());
                valInfo.put("profRai", rasta.getProfundidadRaicesRas());
                valInfo.put("crecPlan", rasta.getPlantasPequenasRas());
                valInfo.put("muchaHoja", rasta.getHojarascaRas());
                valInfo.put("sueNegro", rasta.getSueloNegroBlandoRas());
                valInfo.put("cuchilloEnt", rasta.getCuchilloPrimerHorizonteRas());
                valInfo.put("cercaAgua", rasta.getCercaRiosQuebradasRas());
                valInfo.put("recVeg", rasta.getRecubrimientoVegetalRas());
                valInfo.put("rastaId", rasta.getIdRas());

                valInfo.put("fieldId", temp.get("idField"));
                valInfo.put("nameField", temp.get("nameField"));
                valInfo.put("lat", rasta.getLatitudRas());
                valInfo.put("lng", rasta.getLongitudRas());
                valInfo.put("alt", rasta.getAltitudRas());
                valInfo.put("userMobileId", idUserMobile);      

                BasicDBObject queryMongo = new BasicDBObject();
                queryMongo.put("InsertedId", ""+rasta.getIdRas());
                queryMongo.put("form_id", "6");

                MongoClient mongo = null;
                try {
                    mongo = new MongoClient("localhost", 27017);
                } catch (UnknownHostException ex) {
                    Logger.getLogger(ActionField.class.getName()).log(Level.SEVERE, null, ex);
                }
                DB db = mongo.getDB("ciat");
                DBCollection col = db.getCollection("log_form_records");

                DBCursor cursor    = col.find(queryMongo);
                WriteResult result = null;
                BasicDBObject jsonField = null;
                jsonField          = GlobalFunctions.generateJSONSoil(valInfo);

                if (cursor.count()>0) {
                    System.out.println("actualizo mongo");
                    result = col.update(queryMongo, jsonField);
                } else {
                    System.out.println("inserto mongo");
                    result = col.insert(jsonField);
                }

                if (result.getError()!=null) {
                    throw new HibernateException("");
                }

                mongo.close();
                
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
    
    public void deleteSoilAnalysisMongo(Integer idField) 
    {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        
        List<Object[]> events = null;
        Transaction tx = null;
        
        String sql = "";
        
        sql += "select ep.id_ras, ep.fecha_ras";
        sql += " from rastas ep";
        sql += " where ep.status=1";
        if (idField!=null) {
            sql += " and ep.id_lote_ras="+idField;
        }        
        
        try {
            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql);            
            events = query.list();         
            
            MongoClient mongo = null;
            mongo = new MongoClient("localhost", 27017);
            
            for (Object[] data : events) {
//                System.out.println(data);
                HashMap temp = new HashMap();
                temp.put("id_ras", data[0]);
                temp.put("date_ras", data[1]);          
                
                BasicDBObject queryMongo = new BasicDBObject();
                queryMongo.put("InsertedId", ""+temp.get("id_ras"));
                queryMongo.put("form_id", "6");
                
                DB db = mongo.getDB("ciat");
                DBCollection col = db.getCollection("log_form_records");
                WriteResult result = null;

                System.out.println("borro mongo");
                result = col.remove(queryMongo);

                if (result.getError()!=null) {
                    throw new HibernateException("");
                }                        
                
            }   
            
            mongo.close();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ActionField.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            System.out.println("Error ingresando al MongoDB");
        } finally {
            session.close();
        }
    } */  
    
}
