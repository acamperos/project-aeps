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
import org.aepscolombia.platform.models.entity.ResidualsManagement;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase ResidualsManagementDao
 *
 * Contiene los metodos para interactuar con la tabla ResidualsManagement de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ResidualsManagementDao 
{    
    
    public HashMap findById(Integer id) {
        HashMap result = new HashMap();
        return result;
    }
    
    public List<ResidualsManagement> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<ResidualsManagement> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from ResidualsManagement");
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
                      
        sql  += "select p.id_res_man, p.date_res_man, cr.name_res_cla, p.other_residuals_management_res_man";
        sql += " from residuals_management p"; 
        sql += " inner join production_events ep on ep.id_pro_eve=p.id_production_event_res_man";     
        sql += " left join residuals_clasification cr on cr.id_res_cla=p.id_residuals_type_res_man and cr.status_res_cla=1";    
        sql += " inner join log_entities le on le.id_object_log_ent=p.id_res_man and le.table_log_ent='residuals_management' and le.action_type_log_ent='C'";   
		sql += " where p.status=1 and ep.status=1";
        if (args.containsKey("idEvent")) {
            sql += " and p.id_production_event_res_man="+args.get("idEvent");
        }
		if (args.containsKey("idEntUser")) {
			sqlAdd += " and le.id_entity_log_ent="+args.get("idEntUser");
		}
		sqlAdd += " order by p.id_res_man ASC";
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
                temp.put("idResMan", data[0]);
                temp.put("dateResMan", data[1]);
                temp.put("residualsResMan", data[2]);
                temp.put("otherResidualsResMan", data[3]);                
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
    
    public ResidualsManagement objectById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        String sql  = "";        
        ResidualsManagement event = null;
        Transaction tx = null;
				
//        sql += "select p.id_prep, p.id_production_event_prep, p.date_prep, p.preparation_type_prep,";
//        sql += " p.depth_prep, p.id_residuals_prep, p.use_hills_prep, p.other_preparation_type_prep,";
//        sql += " p.passings_number_prep, p.other_residuals_management_prep, p.status, p.created_by";
//        sql += " from residuals_management p";
//        sql += " where p.status=1 and p.id_production_event_res_man="+id;
        try {
            tx = session.beginTransaction();
            String hql  = "FROM ResidualsManagement E WHERE E.idResMan = :id_res_man";
            Query query = session.createQuery(hql);
            query.setParameter("id_res_man", id);
            event = (ResidualsManagement)query.uniqueResult();
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

    public void save(ResidualsManagement event) {
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

    public void delete(ResidualsManagement event) {
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
    
    public static String getResiduals(Integer idCrop) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Object[]> eventsTotal = null;
        List<Object[]> events = null;
        Transaction tx = null;
        String result = "[";
        
        String sql = "";    
        sql += "select DATE_FORMAT(rm.date_res_man,'%Y-%m-%d') as dateRes, rm.id_residuals_type_res_man, rm.other_residuals_management_res_man";
        sql += " from residuals_management rm"; 
        sql += " where rm.status=1";
        sql += " and rm.id_production_event_res_man="+idCrop;
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
                    result += "{\"survey_solution[380]\":\""+data[0]+"\","+
                           "\"survey_solution[381]\":\""+data[1]+"\","+ 
                           "\"survey_solution[382]\":\""+data[2]+"\","+ 
                           "\"subform_id\":\""+52+"\","+ 
                           "\"idx\":"+numCaj+"}"; 
                } else {
                    result += "{\"survey_solution[380]\":\""+data[0]+"\","+
                           "\"survey_solution[381]\":\""+data[1]+"\","+ 
                           "\"survey_solution[382]\":\""+data[2]+"\","+ 
                           "\"subform_id\":\""+52+"\","+ 
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
    
    public static String getResidualsBeans(Integer idCrop) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Object[]> eventsTotal = null;
        List<Object[]> events = null;
        Transaction tx = null;
        String result = "[";
        
        String sql = "";    
        sql += "select DATE_FORMAT(rm.date_res_man,'%Y-%m-%d') as dateRes, rm.id_residuals_type_res_man, rm.other_residuals_management_res_man";
        sql += " from residuals_management rm"; 
        sql += " where rm.status=1";
        sql += " and rm.id_production_event_res_man="+idCrop;
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
                    result += "{\"survey_solution[400]\":\""+data[0]+"\","+
                           "\"survey_solution[399]\":\""+data[1]+"\","+ 
                           "\"survey_solution[401]\":\""+data[2]+"\","+ 
                           "\"subform_id\":\""+55+"\","+ 
                           "\"idx\":"+numCaj+"}"; 
                } else {
                    result += "{\"survey_solution[400]\":\""+data[0]+"\","+
                           "\"survey_solution[399]\":\""+data[1]+"\","+ 
                           "\"survey_solution[401]\":\""+data[2]+"\","+ 
                           "\"subform_id\":\""+55+"\","+ 
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
