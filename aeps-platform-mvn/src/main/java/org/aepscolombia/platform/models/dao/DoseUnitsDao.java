package org.aepscolombia.platform.models.dao;

import java.util.List;
//import org.aepscolombia.plataforma.models.dao.IEventoDao;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.DoseUnits;
import org.aepscolombia.platform.models.entity.IdiomCountry;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase DoseUnitsDao
 *
 * Contiene los metodos para interactuar con la tabla DoseUnits de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class DoseUnitsDao {

    public DoseUnits findById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        DoseUnits event = null;
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            event = (DoseUnits) session.load(DoseUnits.class, id);
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

    public List<DoseUnits> findByParams(String exclude, String countryCode) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<DoseUnits> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String sql  = "select p.id_dos_uni, p.name_dos_uni, p.status_dos_uni, p.country_dos_uni from dose_units p";
            sql += " where p.id_dos_uni not in ("+exclude+")";
            if (countryCode!=null && !countryCode.equals("")) {
                sql += " and p.country_dos_uni='"+countryCode+"'";
            } 
//            sql += " where p.name_dos_uni not in ("+exclude+")";
//            sql += " and p.id_dos_uni in ("+include+")";
            Query query = session.createSQLQuery(sql).addEntity("p", DoseUnits.class);
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

    public List<DoseUnits> findAll(String countryCode) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<DoseUnits> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from DoseUnits WHERE countryDosUni.acronymIdCo = :country_code");
            query.setParameter("country_code", countryCode);
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

    public void save(DoseUnits event) {
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

    public void delete(DoseUnits event) {
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
