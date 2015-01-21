package org.aepscolombia.platform.models.dao;

import au.com.bytecode.opencsv.CSVWriter;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.aepscolombia.platform.models.entity.Producers;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase ProducersDao
 *
 * Contiene los metodos para interactuar con la tabla Producers de la base de data (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ProducersDao 
{    
    
    public HashMap findById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        
        List<Object[]> events = null;
        Transaction tx = null;
        HashMap result = new HashMap();
        
        String sql = "";
        
        sql += "select p.id_pro, e.id_ent, e.document_number_ent, e.document_type_ent, e.name_ent, e.document_issue_place_ent,";
        sql += " e.cellphone_ent, e.cellphone2_ent, e.phone_ent, e.address_ent, m.name_mun, e.email_ent,";
        sql += " e.email_2_ent, e.in_association_ent, e.id_project_ent, e.status, e.validation_number_ent, m.id_department_mun,";
        sql += " m.id_mun, e.first_name_1_ent, e.first_name_2_ent, e.last_name_1_ent, e.last_name_2_ent";
        sql += " from producers p";
        sql += " inner join entities e on (p.id_entity_pro=e.id_ent)";
        sql += " inner join municipalities m on (m.id_mun=e.id_municipality_ent)";
        sql += " where e.status=1";
        if (id!=null) {
            sql += " and p.id_pro="+id;
        }
//        args.get("countTotal");
//        events.toArray();
//        System.out.println("sql->"+sql);
        try {
            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql);
//            System.out.println("sql->"+query.list().size());
//                         .addEntity("p", Productores.class)
//                         .addEntity("e", Entidades.class);
//                         .addJoin("e", "inner join entidades e on p.id_entidad_pro=e.id_ent")
//                         .addEntity("e", Entidades.class)
//                         .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//            System.out.println("events->"+query.list());            

            
            events = query.list();         
            
            for (Object[] data : events) {
//                System.out.println(data);
                HashMap temp = new HashMap();
                temp.put("id_producer", data[0]);
                temp.put("id_entity", data[1]);
                temp.put("document", data[2]);
                temp.put("type_document", data[3]);
                temp.put("name", data[4]);
                temp.put("site_document", data[5]);
                temp.put("cellphone", data[6]);
                temp.put("cellphone_2", data[7]);
                temp.put("phone", data[8]);
                temp.put("direction", data[9]);
                temp.put("city", data[10]);
                
                if (data[11]==null) data[11]="";                
                if (data[12]==null) data[12]="";                
                if (data[19]==null) data[19]="";                
                if (data[20]==null) data[20]="";                
                if (data[21]==null) data[21]="";                
                if (data[22]==null) data[22]="";               
                
                temp.put("e_mail_1", data[11]);
                temp.put("e_mail_2", data[12]);
                temp.put("associate", data[13]);
//                temp.put("fecha_in", data[14]);
                temp.put("id_project", data[14]);
                temp.put("status", data[15]);
                temp.put("digit", data[16]);
                temp.put("id_dep", data[17]);                
                temp.put("id_mun", data[18]);                
                temp.put("name_1", data[19]);
                temp.put("name_2", data[20]);
                temp.put("last_name_1", data[21]);
                temp.put("last_name_2", data[22]);                
                result = (temp);
            }
//            System.out.println(result);
//            for (HashMap data : result) {
//                System.out.println(data.get("id_productor")+" "+data.get("id_entidad")+" "+data.get("cedula"));
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

//        Productores event = null;
//        Transaction tx = null;
//
//        try {
//            tx = session.beginTransaction();
//            event = (Productores) session.load(Productores.class, id);
//            tx.commit();
//        } catch (HibernateException e) {
//            if (tx != null) {
//                tx.rollback();
//            }
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
//        return event;
    }
    
    public List<Producers> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Producers> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from Producers");
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
        String entType = String.valueOf(args.get("entType"));
        
//        sql += "select p.*, e.* from productores p";
        sql += "select p.id_pro, e.id_ent, e.document_number_ent, e.document_type_ent, e.name_ent, e.document_issue_place_ent,";
        sql += " e.cellphone_ent, e.cellphone2_ent, e.phone_ent, e.address_ent, m.name_mun, e.email_ent,";
        sql += " e.email_2_ent, e.in_association_ent, e.id_project_ent, e.status, e.entity_type_ent, e.agent_name_ent, e.validation_number_ent,";
        if (entType.equals("3")) {
            sql += " le.date_log_ent, entLe.name_ent as nameAgro";
        } else {
            sql += " le.date_log_ent";
        }
        sql += " from producers p";        
        sql += " inner join entities e on (p.id_entity_pro=e.id_ent)";       
        sql += " inner join municipalities m on (m.id_mun=e.id_municipality_ent)";
        sql += " inner join log_entities le on (le.id_object_log_ent=p.id_pro and le.table_log_ent='producers' and le.action_type_log_ent='C')";
        if (entType.equals("3")) {
            sql += " inner join entities entLe on (le.id_entity_log_ent=entLe.id_ent)"; 
            sql += " inner join extension_agents ext on (ext.id_entity_ext_age=entLe.id_ent)";
            sql += " inner join association ass on (ass.id_asc=ext.id_asso_ext_age)";
        }
        sql += " where e.status=1 and e.entity_type_ent=2";        
        
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
//        System.out.println("sql=>"+sql);
        
        if (args.containsKey("search_producer")) {
            String valIdent = String.valueOf(args.get("search_producer"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) { 
                sql += " and ((e.document_type_ent='"+valIdent+"')";
                sql += " or (e.document_number_ent like '%"+valIdent+"%')";
//                Date asign = new Date(valIdent);
//                sql += " or (r.fecha_ras like '%"+asign+"%')";
                try {
                    String dateAsign = new SimpleDateFormat("yyyy-dd-MM").format(new Date(valIdent));
//                    sql += " or (r.fecha_ras like '%"+dateAsign+"%')";
                } catch (IllegalArgumentException ex) {
//                    Logger.getLogger(RastasDao.class.getName()).log(Level.SEVERE, null, ex);
                }
                sql += " or (e.first_name_1_ent like '%"+valIdent+"%')";
                sql += " or (e.last_name_1_ent like '%"+valIdent+"%')";
                sql += " or (e.email_ent like '%"+valIdent+"%')";
                sql += " or (e.address_ent like '%"+valIdent+"%')";     
                sql += " or (e.name_ent like '%"+valIdent+"%')";
                sql += " or (e.agent_name_ent like '%"+valIdent+"%')";
//                sql += " or (m.id_department_mun='"+valIdent+"')";
//                sql += " or (r.terreno_circundante_ras like '%"+valIdent+"%')";
//                sql += " or (r.posicion_perfil_ras like '%"+valIdent+"%')";
//                sql += " or (r.ph_ras like '%"+valIdent+"%')";
                sql += " or (m.name_mun like '%"+valIdent+"%'))";
            }
        }
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
        if (args.containsKey("typeIdent")) {
            String valType = String.valueOf(args.get("typeIdent"));
            if(!valType.equals("-1") && !valType.equals("") && !valType.equals("null")) sql += " and e.document_type_ent='"+args.get("typeIdent")+"'";
        }
        if (args.containsKey("identProducer")) {
            String valIdent = String.valueOf(args.get("identProducer"));
//            if(!valIdent.equals("null")) sql += " and (e.document_number_ent!='"+args.get("identProductor")+"' OR e.name_ent not like '%"+args.get("identProducer")+"%')";
            if(!valIdent.equals("") && !valIdent.equals("null")) sql += " and e.document_number_ent like '%"+args.get("identProducer")+"%'";
        }
        if (args.containsKey("names_producer_1")) {
            String valIdent = String.valueOf(args.get("names_producer_1"));
            if(!valIdent.equals("") && !valIdent.equals("null")) sql += " and e.first_name_1_ent like '%"+args.get("names_producer_1")+"%'";
        }
        if (args.containsKey("last_names_producer_1")) {
            String valIdent = String.valueOf(args.get("last_names_producer_1"));
            if(!valIdent.equals("") && !valIdent.equals("null")) sql += " and e.last_name_1_ent like '%"+args.get("last_names_producer_1")+"%'";
        }        
        if (args.containsKey("nameCompany")) {
            String valIdent = String.valueOf(args.get("nameCompany"));
            if(!valIdent.equals("") && !valIdent.equals("null")) sql += " and e.name_ent like '%"+valIdent+"%'";
        }
        if (args.containsKey("firstNameRep")) {
            String valIdent = String.valueOf(args.get("firstNameRep"));
            if(!valIdent.equals("") && !valIdent.equals("null")) sql += " and e.agent_name_ent like '%"+valIdent+"%'";
        }
        if (args.containsKey("direction_producer")) {
            String valIdent = String.valueOf(args.get("direction_producer"));
            if(!valIdent.equals("") && !valIdent.equals("null")) sql += " and e.address_ent like '%"+args.get("direction_producer")+"%'";
        }
        
        if (args.containsKey("email_producer")) {
            String valIdent = String.valueOf(args.get("email_producer"));
            if(!valIdent.equals("") && !valIdent.equals("null")) sql += " and e.email_ent like '%"+args.get("email_producer")+"%'";
        }
        
        if (args.containsKey("depPro")) {
            String valIdent = String.valueOf(args.get("depPro"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and m.id_department_mun="+args.get("depPro");
        }
        if (args.containsKey("cityPro")) {
            String valIdent = String.valueOf(args.get("cityPro"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and m.id_mun="+args.get("cityPro");
        }
        sql += " order by e.name_ent ASC";
//        events.toArray();
//        System.out.println("valIni->"+valIni);
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
//                         .addEntity("p", Productores.class)
//                         .addEntity("e", Entidades.class);
//                         .addJoin("e", "inner join entidades e on p.id_entidad_pro=e.id_ent")
//                         .addEntity("e", Entidades.class)
//                         .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//            System.out.println("events->"+query.list());            

            
            events = query.list();         
            for (Object[] data : events) {
                HashMap temp = new HashMap();
                temp.put("id_producer", data[0]);
                temp.put("id_entity", data[1]);
                temp.put("document", data[2]);
                temp.put("type_document", data[3]);
                temp.put("name", data[4]);
                temp.put("site_document", data[5]);
                temp.put("cellphone", data[6]);
                temp.put("cellphone_2", data[7]);
                temp.put("phone", data[8]);
                temp.put("direction", data[9]);
                temp.put("city", data[10]);
                temp.put("e_mail_1", data[11]);
                temp.put("e_mail_2", data[12]);
                temp.put("associate", data[13]);
//                temp.put("fecha_in", data[14]);
                temp.put("id_project", data[14]);
                temp.put("status", data[15]);
                temp.put("typeEnt", data[16]);
                temp.put("agentName", data[17]);
                temp.put("digVer", data[18]);
                temp.put("dateLog", data[19]);
                if (entType.equals("3")) {
                    temp.put("nameAgro", data[20]);
                }
                result.add(temp);
            }
//            System.out.println(result);
//            for (HashMap data : result) {
//                System.out.println(data.get("id_producer")+" "+data.get("id_entity")+" "+data.get("document"));
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
        String entType = String.valueOf(args.get("entType"));
        
//        sql += "select p.*, e.* from productores p";
        sql += "select count(p.id_pro), p.id_entity_pro";
        sql += " from producers p";
        sql += " inner join entities e on (p.id_entity_pro=e.id_ent)";
        sql += " inner join log_entities le on (le.id_object_log_ent=p.id_pro and le.table_log_ent='producers' and le.action_type_log_ent='C')";
        if (entType.equals("3")) {
            sql += " inner join entities entLe on (le.id_entity_log_ent=entLe.id_ent)"; 
            sql += " inner join extension_agents ext on (ext.id_entity_ext_age=entLe.id_ent)";
            sql += " inner join association ass on (ass.id_asc=ext.id_asso_ext_age)";
        }
        sql += " where e.status=1 and e.entity_type_ent=2";       
        if (!entType.equals("3") && args.containsKey("idEntUser")) {
            sql += " and le.id_entity_log_ent="+args.get("idEntUser");
        } else {
            sql += " and ass.id_entity_asc="+args.get("idEntUser");
        }
        
//        System.out.println("sql=>"+sql);
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
    
    public Producers objectByEntityId(Integer idEnt) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Producers event = null;
        Transaction tx = null;
        
        String sql = "";
        
        sql += "select p.id_pro, p.id_entity_pro, p.address_pro, p.status, p.created_by";
        sql += " from producers p";
        sql += " where p.status=1 and p.id_entity_pro="+idEnt; 
//        System.out.println("sql->"+sql);
        try {
            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql).addEntity("p", Producers.class);;
            event = (Producers)query.uniqueResult();
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
    
    public Producers objectById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        Producers event = null;
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            String hql  = "FROM Producers E WHERE E.idPro = :id_pro";
            Query query = session.createQuery(hql);
            query.setParameter("id_pro", id);
            event = (Producers)query.uniqueResult();
//            event = (Producers) session.load(Producers.class, id);
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

    public void save(Producers event) {
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

    public void delete(Producers event) {
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
    
    public void getProducers(HashMap args, String fileName)
    {        
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Object[]> events = null;
        Transaction tx = null;             

        String sql = "";
        String entType = String.valueOf(args.get("entType"));

        sql += "select p.id_pro as ID_PROD, e.name_ent as USUARIO, ent.name_ent as PRODUCTOR, concat(ent.document_type_ent, ':', ent.document_number_ent) as CEDULA, ";
        sql += "ent.cellphone_ent as CELULAR, ent.phone_ent as TELEFONO, ent.email_ent as CORREO_ELE, dep.name_dep as DEPARTAMENTO";
        sql += " from producers p";
        sql += " inner join entities ent on ent.ID_ENT = p.id_entity_pro";
        sql += " inner join log_entities le on le.id_object_log_ent = p.id_pro AND le.table_log_ent = 'producers'";
        sql += " inner join entities e on le.id_entity_log_ent = e.ID_ENT";
        if (entType.equals("3")) {
            sql += " inner join extension_agents ext on (ext.id_entity_ext_age=e.id_ent)";
            sql += " inner join association ass on (ass.id_asc=ext.id_asso_ext_age)";
        }
        sql += " inner join municipalities m on m.id_mun = ent.id_municipality_ent";
        sql += " inner join departments dep on dep.id_dep=m.id_department_mun";
        sql += " where le.action_type_log_ent = 'C'";
        sql += " and ent.status=1";
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
        sql += "﻿  select le.id_object_log_ent from log_entities le ";
        if (entType.equals("3")) {
            sql += "﻿  inner join entities entLe on (le.id_entity_log_ent=entLe.id_ent)";
            sql += "﻿  inner join extension_agents ext on (ext.id_entity_ext_age=entLe.id_ent)";
            sql += "﻿  inner join association ass on (ass.id_asc=ext.id_asso_ext_age)";
        }
        sql += "﻿  where le.action_type_log_ent = 'D' AND le.table_log_ent = 'producers'";
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
        sql += " order by e.name_ent";
//        System.out.println("sql=>"+sql);
        
        try {
            tx = session.beginTransaction();
            CSVWriter writer = new CSVWriter(new FileWriter(fileName), ';');
            Query query  = session.createSQLQuery(sql);
            events = query.list();  
            String[] val = {
                "ID_PROD",
                "USUARIO",
                "PRODUCTOR",
                "CEDULA",
                "CELULAR",
                "TELEFONO",
                "CORREO_ELE",
                "DEPARTAMENTO"
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
                    String.valueOf(data[7])
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
//            Logger.getLogger(ProducersDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.close();
        }
//        return result;
    }
    
    public String deleteAllEntities(String valSel) 
    {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        List<Entities> events = null;
        Transaction tx = null;
        String sql = "";
        String state = "failure";

        sql += "select usr.id_ent, usr.id_project_ent, usr.entity_type_ent, usr.document_number_ent, usr.document_type_ent, usr.document_issue_place_ent,";
        sql += "usr.name_ent, usr.in_association_ent, usr.email_ent, usr.email_2_ent, usr.address_ent, usr.id_municipality_ent,";
        sql += "usr.cellphone2_ent, usr.phone_ent, usr.cellphone_ent, usr.status, usr.gender_ent, usr.civil_status_ent,";
        sql += "usr.validation_number_ent, usr.education_level_ent, usr.date_of_birth_ent, usr.first_name_1_ent, usr.person_type_ent,";
        sql += "usr.first_name_2_ent, usr.last_name_1_ent, usr.last_name_2_ent, usr.agent_name_ent, usr.page_link_ent, usr.created_by";
        sql += " from entities usr";
        if (!valSel.equals("")) sql += " where usr.status=1 and usr.id_ent in ("+valSel+")";
//        System.out.println("sql=>"+sql);
        
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("usr", Entities.class);
            events = query.list();
            Producers pTemp   = null;
            MongoClient mongo = new MongoClient("localhost", 27017);
            for (Entities ent : events) {
                pTemp = this.objectByEntityId(ent.getIdEnt());  
//                System.out.println("entId->"+ent.getIdEnt());
//                System.out.println("proId->"+pTemp.getIdPro());
                ent.setStatus(false);     
                session.saveOrUpdate(ent);

                BasicDBObject queryMongo = new BasicDBObject();
                queryMongo.put("InsertedId", ""+ent.getIdEnt());
                queryMongo.put("form_id", "4");
                
                DB db = mongo.getDB("ciat");
                DBCollection col = db.getCollection("log_form_records");
                WriteResult result = null;

                System.out.println("borro mongo");
                result = col.remove(queryMongo);

                if (result.getError()!=null) {
                    throw new HibernateException("");
                }                

                FarmsDao farDao = new FarmsDao();
                farDao.deleteFarmsMongo(pTemp.getIdPro());
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
    
}
