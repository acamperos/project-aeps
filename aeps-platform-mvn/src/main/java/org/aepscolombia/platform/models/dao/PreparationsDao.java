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
import org.aepscolombia.platform.models.entity.Preparations;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase PreparationsDao
 *
 * Contiene los metodos para interactuar con la tabla Preparations de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class PreparationsDao 
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
    
    public List<Preparations> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Preparations> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from Preparations");
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
                      
        sql  += "select p.id_prep, p.date_prep, tp.name_pre_typ, p.other_preparation_type_prep, p.depth_prep, p.passings_number_prep";
        sql += " from preparations p"; 
        sql += " inner join production_events ep on ep.id_pro_eve=p.id_production_event_prep";    
        sql += " left join preparations_types tp on tp.id_pre_typ=p.preparation_type_prep and tp.status_pre_typ=1";     
        sql += " inner join log_entities le on le.id_object_log_ent=p.id_prep and le.table_log_ent='preparations' and le.action_type_log_ent='C'";   
		sql += " where p.status=1 and ep.status=1";
        if (args.containsKey("idEvent")) {
            sql += " and p.id_production_event_prep="+args.get("idEvent");
        }
		if (args.containsKey("idEntUser")) {
			sqlAdd += " and le.id_entity_log_ent="+args.get("idEntUser");
		}
		sqlAdd += " order by p.id_prep ASC";
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
            events = query.list(); 

            for (Object[] data : events) {
                HashMap temp = new HashMap();
                temp.put("idPrep", data[0]);
                temp.put("datePrep", data[1]);
                temp.put("namePrep", data[2]);             
                temp.put("otherNamePrep", data[3]);                
                temp.put("depthPrep", data[4]);            
                temp.put("passNum", data[5]);            
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
    
    public Preparations objectById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        String sql  = "";        
        Preparations event = null;
        Transaction tx = null;
				
        sql += "select p.id_prep, p.id_production_event_prep, p.date_prep, p.preparation_type_prep,";
        sql += " p.depth_prep, p.id_residuals_prep, p.use_hills_prep, p.other_preparation_type_prep,";
        sql += " p.passings_number_prep, p.status, p.created_by";
        sql += " from preparations p";
        sql += " where p.status=1 and p.id_production_event_prep="+id;
        try {
            tx = session.beginTransaction();
            String hql  = "FROM Preparations E WHERE E.idPrep = :id_prep";
            Query query = session.createQuery(hql);
            query.setParameter("id_prep", id);
            event = (Preparations)query.uniqueResult();
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

    public void save(Preparations event) {
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

    public void delete(Preparations event) {
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
    
    public static String getPreparations(Integer idCrop) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Object[]> eventsTotal = null;
        List<Object[]> events = null;
        Transaction tx = null;
        String result = "[";
        
        String sql = "";                   
        sql += "select DATE_FORMAT(p.date_prep,'%Y-%m-%d') as datePrep, p.preparation_type_prep, p.other_preparation_type_prep, FORMAT(p.depth_prep,0),";
        sql += " FORMAT(p.passings_number_prep,0), p.id_prep";
        sql += " from preparations p"; 
        sql += " where p.status=1";
        sql += " and p.id_production_event_prep="+idCrop;
//        System.out.println("sql->"+sql);
        int numCaj    = 0;
        int totResult = 0;
        try {
            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql);
            events    = query.list(); 
            totResult = events.size();

            for (Object[] data : events) {
                numCaj++;
                if (totResult==numCaj) {
                    result += "{\"survey_solution[200]\":\""+data[0]+"\","+
                           "\"survey_solution[201]\":\""+data[1]+"\","+ 
                           "\"survey_solution[202]\":\""+data[2]+"\","+ 
                           "\"survey_solution[203]\":\""+data[3]+"\","+ 
                           "\"survey_solution[378]\":\""+data[4]+"\","+ 
                           "\"subform_id\":\""+46+"\","+ 
                           "\"idx\":"+numCaj+"}"; 
                } else {
                    result += "{\"survey_solution[200]\":\""+data[0]+"\","+
                           "\"survey_solution[201]\":\""+data[1]+"\","+ 
                           "\"survey_solution[202]\":\""+data[2]+"\","+ 
                           "\"survey_solution[203]\":\""+data[3]+"\","+ 
                           "\"survey_solution[378]\":\""+data[4]+"\","+ 
                           "\"subform_id\":\""+46+"\","+ 
                           "\"idx\":"+numCaj+"},"; 
                }         
            }
            result += "]";
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
    
    public static String getPreparationsBeans(Integer idCrop) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Object[]> eventsTotal = null;
        List<Object[]> events = null;
        Transaction tx = null;
        String result = "[";
        
        String sql = "";                   
        sql += "select DATE_FORMAT(p.date_prep,'%Y-%m-%d') as datePrep, p.preparation_type_prep, p.other_preparation_type_prep, FORMAT(p.depth_prep,0),";
        sql += " FORMAT(p.passings_number_prep,0), p.id_prep";
        sql += " from preparations p"; 
        sql += " where p.status=1";
        sql += " and p.id_production_event_prep="+idCrop;
//        System.out.println("sql->"+sql);
        int numCaj    = 0;
        int totResult = 0;
        try {
            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql);
            events    = query.list(); 
            totResult = events.size();

            for (Object[] data : events) {
                numCaj++;
                if (totResult==numCaj) {
                    result += "{\"survey_solution[404]\":\""+data[0]+"\","+
                           "\"survey_solution[405]\":\""+data[1]+"\","+ 
                           "\"survey_solution[406]\":\""+data[2]+"\","+ 
                           "\"survey_solution[407]\":\""+data[3]+"\","+ 
                           "\"survey_solution[408]\":\""+data[4]+"\","+ 
                           "\"subform_id\":\""+57+"\","+ 
                           "\"idx\":"+numCaj+"}"; 
                } else {
                    result += "{\"survey_solution[404]\":\""+data[0]+"\","+
                           "\"survey_solution[405]\":\""+data[1]+"\","+ 
                           "\"survey_solution[406]\":\""+data[2]+"\","+ 
                           "\"survey_solution[407]\":\""+data[3]+"\","+ 
                           "\"survey_solution[408]\":\""+data[4]+"\","+ 
                           "\"subform_id\":\""+57+"\","+ 
                           "\"idx\":"+numCaj+"},"; 
                }         
            }
            result += "]";
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
}
