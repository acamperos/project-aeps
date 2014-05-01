package org.aepscolombia.platform.models.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.aepscolombia.plataforma.models.dao.IEventoDao;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.Municipalities;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase LotsDao
 *
 * Contiene los metodos para interactuar con la tabla Municipalities de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class MunicipalitiesDao {

    public Municipalities findById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        Municipalities event = null;
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            String hql  = "FROM Municipalities E WHERE E.idMun = :id_mun";
            Query query = session.createQuery(hql);
            query.setParameter("id_mun", id);
            event = (Municipalities)query.uniqueResult();            
//            event = (Municipalities) session.load(Municipalities.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            //session.close();
        }
        return event;
    }
    
    public static Integer getDepartmentId(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Object[] events = null;
        Transaction tx  = null;
        Integer result  = 0;
        
        String sql = "";       
        sql += "select usr.id_department_mun, usr.id_mun, usr.code_mun, usr.name_mun ";
        sql += " from municipalities usr";
        sql += " where usr.id_mun="+id;
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
    
    public List<Municipalities> findAll(int depId) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Municipalities> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
//            System.out.println("depId->"+depId);
            String text = "select m.id_mun, m.id_department_mun, m.code_mun, m.name_mun from municipalities m "+
                    "where m.id_department_mun = :depId";
            Query query = session.createSQLQuery(text).addEntity("m", Municipalities.class).setParameter("depId", depId);
//            Query query = session.createQuery(text).setParameter("depId", depId);
//            query.
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

    public List findByParams(String[] args) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Object[]> events = null;
        Transaction tx = null;
//        events.toArray();
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery("select id_mun, id_department_mun, code_mun, name_mun from municipalities");
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

    public void save(Municipalities event) {
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

    public void delete(Municipalities event) {
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
