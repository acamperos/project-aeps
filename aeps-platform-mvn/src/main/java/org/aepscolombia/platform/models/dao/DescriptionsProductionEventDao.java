package org.aepscolombia.platform.models.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//import org.aepscolombia.plataforma.models.dao.IEventoDao;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.DescriptionsProductionEvent;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase DescriptionsProductionEventDao
 *
 * Contiene los metodos para interactuar con la tabla DescriptionsProductionEvent de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class DescriptionsProductionEventDao 
{    
    
    public HashMap findById(Integer id) {
        HashMap result = new HashMap();
        return result;
    }
    
    public List<DescriptionsProductionEvent> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<DescriptionsProductionEvent> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from DescriptionsProductionEvent");
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
                      
        sql  += "select p.id_des_pro, p.date_des_pro, p.obs_des_pro";
        sql += " from descriptions_production_event p"; 
        sql += " inner join production_events ep on ep.id_pro_eve=p.id_production_event_des_pro";     
        sql += " inner join log_entities le on le.id_object_log_ent=p.id_des_pro and le.table_log_ent='descriptions' and le.action_type_log_ent='C'";   
		sql += " where p.status=1 and ep.status=1";
        if (args.containsKey("idEvent")) {
            sql += " and p.id_production_event_des_pro="+args.get("idEvent");
        }
		if (args.containsKey("idEntUser")) {
			sqlAdd += " and le.id_entity_log_ent="+args.get("idEntUser");
		}
		sqlAdd += " order by p.id_des_pro ASC";
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
                temp.put("idDesPro", data[0]);
                temp.put("dateDesPro", data[1]);
                temp.put("observationDesPro", data[2]);             
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
    
    public DescriptionsProductionEvent objectById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        String sql  = "";        
        DescriptionsProductionEvent event = null;
        Transaction tx = null;
				
//        sql += "select p.id_prep, p.id_production_event_prep, p.date_prep, p.preparation_type_prep,";
//        sql += " p.depth_prep, p.id_residuals_prep, p.use_hills_prep, p.other_preparation_type_prep,";
//        sql += " p.passings_number_prep, p.other_residuals_management_prep, p.status, p.created_by";
//        sql += " from residuals_management p";
//        sql += " where p.status=1 and p.id_production_event_res_man="+id;
        try {
            tx = session.beginTransaction();
            String hql  = "FROM DescriptionsProductionEvent E WHERE E.idDesPro = :id_des_pro";
            Query query = session.createQuery(hql);
            query.setParameter("id_des_pro", id);
            event = (DescriptionsProductionEvent)query.uniqueResult();
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

    public void save(DescriptionsProductionEvent event) {
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

    public void delete(DescriptionsProductionEvent event) {
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
    
    public static String getDescriptions(Integer idCrop) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Object[]> eventsTotal = null;
        List<Object[]> events = null;
        Transaction tx = null;
        String result = "[";
        
        String sql = "";  
        sql += "select DATE_FORMAT(des.date_des_pro,'%Y-%m-%d') as dateDes, des.obs_des_pro";
        sql += " from descriptions_production_event des"; 
        sql += " where des.status=1";
        sql += " and des.id_production_event_des_pro="+idCrop;
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
                    result += "{\"survey_solution[395]\":\""+data[0]+"\","+
                           "\"survey_solution[396]\":\""+data[1]+"\","+ 
                           "\"subform_id\":\""+54+"\","+ 
                           "\"idx\":"+numCaj+"}"; 
                } else {
                    result += "{\"survey_solution[395]\":\""+data[0]+"\","+
                           "\"survey_solution[396]\":\""+data[1]+"\","+ 
                           "\"subform_id\":\""+54+"\","+ 
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
    
    public static String getDescriptionsBeans(Integer idCrop) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Object[]> eventsTotal = null;
        List<Object[]> events = null;
        Transaction tx = null;
        String result = "[";
        
        String sql = "";  
        sql += "select DATE_FORMAT(des.date_des_pro,'%Y-%m-%d') as dateDes, des.obs_des_pro";
        sql += " from descriptions_production_event des"; 
        sql += " where des.status=1";
        sql += " and des.id_production_event_des_pro="+idCrop;
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
                    result += "{\"survey_solution[449]\":\""+data[0]+"\","+
                           "\"survey_solution[450]\":\""+data[1]+"\","+ 
                           "\"subform_id\":\""+63+"\","+ 
                           "\"idx\":"+numCaj+"}"; 
                } else {
                    result += "{\"survey_solution[449]\":\""+data[0]+"\","+
                           "\"survey_solution[450]\":\""+data[1]+"\","+ 
                           "\"subform_id\":\""+63+"\","+ 
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
