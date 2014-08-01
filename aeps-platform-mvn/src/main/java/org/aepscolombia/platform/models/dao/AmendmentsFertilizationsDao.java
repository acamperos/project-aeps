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
import org.aepscolombia.platform.models.entity.AmendmentsFertilizations;
import org.aepscolombia.platform.models.entity.AmendmentsFertilizers;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase AmendmentsFertilizationsDao
 *
 * Contiene los metodos para interactuar con la tabla AmendmentsFertilizations de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class AmendmentsFertilizationsDao 
{    
    
    public HashMap findById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        
        List<Object[]> events = null;
        Transaction tx = null;
        HashMap result = new HashMap();
        return result;
    }
    
    public List<AmendmentsFertilizations> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<AmendmentsFertilizations> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from AmendmentsFertilizations");
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
    
    public AmendmentsFertilizations objectById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        String sql  = "";        
        AmendmentsFertilizations event = null;
        Transaction tx = null;
				
        sql += "select p.id_ame_fer, p.id_fertilization_ame_fer, p.id_product_ame_fer,";
        sql += " p.other_product_ame_fer, p.amount_product_used_ame_fer, p.status, p.created_by"; 
        sql += " from amendments_fertilizations p";
        sql += " where p.id_fertilization_ame_fer="+id;
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("p", AmendmentsFertilizations.class);
            event = (AmendmentsFertilizations)query.uniqueResult();
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
    
    public List<AmendmentsFertilizations> getListAmeFert(Integer idFert) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<AmendmentsFertilizations> eventsTemp = null;
        List<AmendmentsFertilizations> result     = new ArrayList<AmendmentsFertilizations>();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String sql = "select p.id_ame_fer, p.id_fertilization_ame_fer, p.id_product_ame_fer,";
            sql += " p.other_product_ame_fer, p.amount_product_used_ame_fer, p.status, p.created_by"; 
            sql += " from amendments_fertilizations p";
            sql += " where p.id_fertilization_ame_fer="+idFert;
            Query query = session.createSQLQuery(sql).addEntity("p", AmendmentsFertilizations.class);
            eventsTemp = query.list();
            for (AmendmentsFertilizations data : eventsTemp) {
                if (data!=null && data.getOtherProductAmeFer()!=null && !data.getOtherProductAmeFer().equals("")) data.setAmendmentsFertilizers(new AmendmentsFertilizers(1000000, "Otro"));
                result.add(data);
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

    public void save(AmendmentsFertilizations event) {
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

    public void delete(AmendmentsFertilizations event) {
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
