package org.aepscolombia.platform.models.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.aepscolombia.plataforma.models.dao.IEventoDao;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.IdiomCountry;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase IdiomCountryDao
 *
 * Contiene los metodos para interactuar con la tabla IdiomCountry de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class IdiomCountryDao {

    public IdiomCountry findById(Integer id) 
		{
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        IdiomCountry event = null;
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            event = (IdiomCountry) session.load(IdiomCountry.class, id);
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

    public List findByParams(String[] args) 
    {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Object[]> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery("select acronym_doc_typ, name_doc_typ from documents_types");
            events = query.list();
//            for (Object[] data : events) {
//                System.out.println(data[0] + "-" + data[1]);
//            }
//            System.out.println(JSONUtil.serialize(events));
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

    public List<IdiomCountry> findAll() 
    {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<IdiomCountry> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from IdiomCountry");
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
    
    public IdiomCountry objectById(String acronymId) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        String sql  = "";        
        IdiomCountry event = null;
        Transaction tx = null;
				
        sql += "select p.acronym_id_co, p.country_id_co, p.status_id_co";
        sql += " from idiom_country p";
        sql += " where p.acronym_id_co='"+acronymId+"'";
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("p", IdiomCountry.class);
            event = (IdiomCountry)query.uniqueResult();
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

    public void save(IdiomCountry event) {
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

    public void delete(IdiomCountry event) {
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
