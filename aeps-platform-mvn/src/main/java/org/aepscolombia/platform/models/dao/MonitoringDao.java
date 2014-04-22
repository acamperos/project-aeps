package org.aepscolombia.platform.models.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.aepscolombia.platform.models.entity.Entities;
//import org.aepscolombia.plataforma.models.dao.IEventoDao;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.Monitoring;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase MonitoringDao
 *
 * Contiene los metodos para interactuar con la tabla Monitoring de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class MonitoringDao 
{    
    
    public HashMap findById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        
        List<Object[]> events = null;
        Transaction tx = null;
        HashMap result = new HashMap();
        
        String sql = "";
        String sqlAdd = "";    
        
        sql += "select pe.id_pro_eve, l.id_farm_fie, l.name_fie, pe.id_crop_type_pro_eve, pe.expected_production_pro_eve, pe.former_crop_pro_eve, pe.draining_pro_eve, pe.status";
        sql += " from production_events pe";
        sql += " inner join log_entities le on le.id_object_log_ent=pe.id_pro_eve and le.table_log_ent='production_events' and le.action_type_log_ent='C'";   
        sql += " inner join fields l on l.id_fie=pe.id_field_pro_eve";
        sql += " where l.status=1 and f.status=1 and pe.status=1";
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
                temp.put("lastCrop", data[5]);
                temp.put("drainPlot", data[6]);                
                temp.put("status", data[8]);
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
    
    public List<Monitoring> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Monitoring> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from Monitoring");
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
                      
        sql += "select m.id_mon, m.date_mon, m.monitor_pests_mon, m.monitor_diseases_mon, m.monitor_weeds_mon from monitoring m";    
        sql += " inner join production_events ep on m.id_production_event_mon=ep.id_pro_eve"; 
        sql += " inner join log_entities le on le.id_object_log_ent=m.id_mon and le.table_log_ent='monitoring' and le.action_type_log_ent='C'"; 
        sql += " where m.status=1";
        if (args.containsKey("idEvent")) {
            sql += " and m.id_production_event_mon="+args.get("idEvent");
        }
		if (args.containsKey("idEntUser")) {
			sqlAdd += " and le.id_entity_log_ent="+args.get("idEntUser");
		}
		sqlAdd += " order by m.id_mon ASC";
		sql += sqlAdd;
//        args.get("countTotal");
        
//        int valIni = Integer.parseInt(String.valueOf(args.get("pageNow")));
//        int maxResults = Integer.parseInt(String.valueOf(args.get("maxResults")));
//        if(valIni!=1){
//            valIni = (valIni-1)*maxResults+1;
//        }    
//        events.toArray();
//        System.out.println("sql->"+sql);
        try {
            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql);
//            System.out.println("sql->"+query.list().size());
            events = query.list(); 			

            for (Object[] data : events) {
                HashMap temp = new HashMap();
                temp.put("idMon", data[0]);
                temp.put("dateMon", data[1]);
                temp.put("monPets", data[2]);             
                temp.put("monDis", data[3]);                
                temp.put("monWee", data[4]);
                String valPet = String.valueOf(temp.get("monPets"));
                String valDis = String.valueOf(temp.get("monDis"));
                String valWee = String.valueOf(temp.get("monWee"));
                if (valPet.equals("true")) {
                    temp.put("monDesPet", "Si");        
                } else {
                    temp.put("monDesPet", "No");        
                }
                
                if (valDis.equals("true")) {
                    temp.put("monDesDis", "Si");        
                } else {
                    temp.put("monDesDis", "No");        
                }
                
                if (valWee.equals("true")) {
                    temp.put("monDesWee", "Si");        
                } else {
                    temp.put("monDesWee", "No");        
                }
                
                result.add(temp);
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
    
    public Monitoring objectById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        Monitoring event = null;
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            String hql  = "FROM Monitoring E WHERE E.idMon = :id_mon";
            Query query = session.createQuery(hql);
            query.setParameter("id_mon", id);
            event = (Monitoring)query.uniqueResult();
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

    public void save(Monitoring event) {
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

    public void delete(Monitoring event) {
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
}
