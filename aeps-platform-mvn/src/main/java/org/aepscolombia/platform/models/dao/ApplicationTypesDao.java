package org.aepscolombia.platform.models.dao;

import java.util.List;
//import org.aepscolombia.plataforma.models.dao.IEventoDao;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.ApplicationTypes;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase ApplicationTypesDao
 *
 * Contiene los metodos para interactuar con la tabla ApplicationTypes de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ApplicationTypesDao 
{        
    public List<ApplicationTypes> findAll(String countryCode) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<ApplicationTypes> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
//            String sql = "select p.id_ame_fer, p.id_fertilization_ame_fer, p.id_product_ame_fer,";
//            sql += " p.other_product_ame_fer, p.amount_product_used_ame_fer, p.country_ame_fer, p.status, p.created_by"; 
//            sql += " from amendments_fertilizations p";
//            sql += " where p.id_fertilization_ame_fer="+idFert;
            
            /*if (countryCode!=null && !countryCode.equals("")) {
                sql += " and p.country_ame_fer='"+countryCode+"'";
            }*/
//            Query query = session.createSQLQuery(sql).addEntity("p", ApplicationTypes.class);
            Query query = session.createQuery("from ApplicationTypes");
//            Query query = session.createQuery("from ApplicationTypes WHERE countryAppTyp.acronymIdCo = :country_code");
//            query.setParameter("country_code", countryCode);
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
    
    public List<ApplicationTypes> findAllById(String exclude) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        String sql  = "";        
        List<ApplicationTypes> event = null;
        Transaction tx = null;
        
        sql += "select cr.id_app_typ, cr.name_app_typ, cr.country_app_typ from application_types cr";
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("cr", ApplicationTypes.class);
            event = query.list();
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
    
    public ApplicationTypes objectById(Integer id) {
        ApplicationTypes event = null;
        return event;
    }    

    public void save(ApplicationTypes event) {
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

    public void delete(ApplicationTypes event) {
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
