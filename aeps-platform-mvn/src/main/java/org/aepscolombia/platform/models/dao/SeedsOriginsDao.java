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
import org.aepscolombia.platform.models.entity.SeedsOrigins;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase SeedsOriginsDao
 *
 * Contiene los metodos para interactuar con la tabla SeedsOrigins de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class SeedsOriginsDao 
{        
    public List<SeedsOrigins> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<SeedsOrigins> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from SeedsOrigins");
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
    
    public List<SeedsOrigins> findAllByTypeCrop(Integer idTypeCrop) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        String sql  = "";        
        List<SeedsOrigins> event = null;
        Transaction tx = null;
				
        sql += "select ms.id_see_ori, ms.name_see_ori, ms.status_see_ori from seeds_origins ms";
        sql += " inner join seeds_origin_crops_types t on t.id_seed_ori_see_ori_cro=ms.id_see_ori";
        sql += " where ms.status_see_ori=1";
        if (idTypeCrop!=null) {
            sql += " and t.id_crop_type_see_ori_cro="+idTypeCrop;
        }
				
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("ms", SeedsOrigins.class);
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
    
    public SeedsOrigins objectById(Integer id) {
        SeedsOrigins event = null;
        return event;
    }    

    public void save(SeedsOrigins event) {
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

    public void delete(SeedsOrigins event) {
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
