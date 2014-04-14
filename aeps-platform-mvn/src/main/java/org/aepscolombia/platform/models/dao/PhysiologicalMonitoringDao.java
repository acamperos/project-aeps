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
import org.aepscolombia.platform.models.entity.PhysiologicalMonitoring;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase PhysiologicalMonitoringDao
 *
 * Contiene los metodos para interactuar con la tabla PhysiologicalMonitoring de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class PhysiologicalMonitoringDao 
{    
    
    public HashMap findById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        
        List<Object[]> events = null;
        Transaction tx = null;
        HashMap result = new HashMap();
        return result;
    }
    
    public List<PhysiologicalMonitoring> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<PhysiologicalMonitoring> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from PhysiologicalMonitoring");
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
        return result;
    }    
    
    public PhysiologicalMonitoring objectById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        String sql  = "";        
        PhysiologicalMonitoring event = null;
        Transaction tx = null;
				
        sql += "select p.id_phy_mon, p.id_monitoring_phy_mon, p.id_production_event_phy_mon, p.emergence_phy_mon, ";
        sql += " p.20_days_population_mon_fis, p.flowering_date_phy_mon, p.status, p.created_by";
        sql += " from physiological_monitoring p";
        sql += " where p.id_production_event_phy_mon="+id;
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("p", PhysiologicalMonitoring.class);
            event = (PhysiologicalMonitoring)query.uniqueResult();
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

    public void save(PhysiologicalMonitoring event) {
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

    public void delete(PhysiologicalMonitoring event) {
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
