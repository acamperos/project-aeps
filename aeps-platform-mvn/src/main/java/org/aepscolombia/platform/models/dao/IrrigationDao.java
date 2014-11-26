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
import org.aepscolombia.platform.models.entity.Irrigation;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase IrrigationDao
 *
 * Contiene los metodos para interactuar con la tabla Irrigation de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class IrrigationDao 
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
    
    public List<Irrigation> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Irrigation> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from Irrigation");
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
                      
        sql += "select p.id_irr, p.date_irr, p.amount_irr, tp.name_irr_typ, p.irrigation_type_irr, p.use_irrigation_irr";
        sql += " from irrigation p"; 
        sql += " inner join production_events ep on ep.id_pro_eve=p.id_production_event_irr";    
        sql += " left join irrigations_types tp on tp.id_irr_typ=p.irrigation_type_irr and tp.status_irr_typ=1";    
        sql += " inner join log_entities le on le.id_object_log_ent=p.id_irr and le.table_log_ent='irrigation' and le.action_type_log_ent='C'";   
        sql += " where p.status=1 and ep.status=1";
        if (args.containsKey("idEvent")) { 
            sql += " and p.id_production_event_irr="+args.get("idEvent");
        }
		if (args.containsKey("idEntUser")) {
			sqlAdd += " and le.id_entity_log_ent="+args.get("idEntUser");
		}
		sqlAdd += " order by p.id_irr ASC";
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
                    temp.put("idIrr", data[0]);
                    temp.put("dateIrr", data[1]);
                    temp.put("amountIrr", data[2]);             
                    temp.put("nameIrrType", data[3]);                
                    temp.put("useIrr", data[5]);        
                    String valUse = String.valueOf(temp.get("useIrr"));
                    if (valUse.equals("true")) {
                        temp.put("useDesIrr", "Si");        
                    } else {
                        temp.put("useDesIrr", "No");        
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
    
    public Irrigation objectById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        Irrigation event = null;
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            String hql  = "FROM Irrigation E WHERE E.idIrr = :id_irr";
            Query query = session.createQuery(hql);
            query.setParameter("id_irr", id);
            event = (Irrigation)query.uniqueResult();
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

    public void save(Irrigation event) {
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

    public void delete(Irrigation event) {
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
    
    public static String getIrrigations(Integer idCrop) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Object[]> eventsTotal = null;
        List<Object[]> events = null;
        Transaction tx = null;
        String result = "[";
        
        String sql = "";  
        sql += "select DATE_FORMAT(ir.date_irr,'%Y-%m-%d') as dateIrr, ir.irrigation_type_irr, ir.amount_irr";
        sql += " from irrigation ir"; 
        sql += " where ir.status=1";
        sql += " and ir.id_production_event_irr="+idCrop;
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
                    result += "{\"survey_solution[391]\":\""+data[0]+"\","+
                           "\"survey_solution[392]\":\""+data[1]+"\","+ 
                           "\"survey_solution[393]\":\""+data[2]+"\","+ 
                           "\"subform_id\":\""+53+"\","+ 
                           "\"idx\":"+numCaj+"}"; 
                } else {
                    result += "{\"survey_solution[391]\":\""+data[0]+"\","+
                           "\"survey_solution[392]\":\""+data[1]+"\","+ 
                           "\"survey_solution[393]\":\""+data[2]+"\","+ 
                           "\"subform_id\":\""+53+"\","+ 
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
    
    public static String getIrrigationsBeans(Integer idCrop) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Object[]> eventsTotal = null;
        List<Object[]> events = null;
        Transaction tx = null;
        String result = "[";
        
        String sql = "";  
        sql += "select DATE_FORMAT(ir.date_irr,'%Y-%m-%d') as dateIrr, ir.irrigation_type_irr, ir.amount_irr";
        sql += " from irrigation ir"; 
        sql += " where ir.status=1";
        sql += " and ir.id_production_event_irr="+idCrop;
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
                    result += "{\"survey_solution[427]\":\""+data[0]+"\","+
                           "\"survey_solution[428]\":\""+data[1]+"\","+ 
                           "\"survey_solution[429]\":\""+data[2]+"\","+ 
                           "\"subform_id\":\""+58+"\","+ 
                           "\"idx\":"+numCaj+"}"; 
                } else {
                    result += "{\"survey_solution[427]\":\""+data[0]+"\","+
                           "\"survey_solution[428]\":\""+data[1]+"\","+ 
                           "\"survey_solution[429]\":\""+data[2]+"\","+ 
                           "\"subform_id\":\""+58+"\","+ 
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
