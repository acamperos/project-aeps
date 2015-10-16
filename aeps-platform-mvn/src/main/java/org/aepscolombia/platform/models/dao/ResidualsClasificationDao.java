package org.aepscolombia.platform.models.dao;

import java.util.List;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.ResidualsClasification;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase ResidualsClasificationDao
 *
 * Contiene los metodos para interactuar con la tabla ResidualsClasification de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ResidualsClasificationDao 
{        
    public List<ResidualsClasification> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<ResidualsClasification> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from ResidualsClasification");
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
    
    public List<ResidualsClasification> findAllByTypeCrop(Integer idTypeCrop) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        String sql  = "";        
        List<ResidualsClasification> event = null;
        Transaction tx = null;
        
        sql += "select cr.id_res_cla, cr.name_res_cla, cr.status_res_cla, cr.country_res_cla from residuals_clasification cr";
        sql += " inner join residuals_clasifications_crops_types t on t.id_residuals_cla_cro_typ=cr.id_res_cla";
        sql += " where cr.status_res_cla=1";
        if (idTypeCrop!=null) {
            sql += " and t.id_crop_type_cro_typ="+idTypeCrop;
        }
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("cr", ResidualsClasification.class);
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
    
    public ResidualsClasification objectById(Integer id) {
        ResidualsClasification event = null;
        return event;
    }    

    public void save(ResidualsClasification event) {
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

    public void delete(ResidualsClasification event) {
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
