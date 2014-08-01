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
import org.aepscolombia.platform.models.entity.OrganicFertilizations;
import org.aepscolombia.platform.models.entity.OrganicFertilizers;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase OrganicFertilizationsDao
 *
 * Contiene los metodos para interactuar con la tabla OrganicFertilizations de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class OrganicFertilizationsDao 
{    
    
    public HashMap findById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        
        List<Object[]> events = null;
        Transaction tx = null;
        HashMap result = new HashMap();
        return result;
    }
    
    public List<OrganicFertilizations> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<OrganicFertilizations> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from OrganicFertilizations");
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
    
    public OrganicFertilizations objectById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        String sql  = "";        
        OrganicFertilizations event = null;
        Transaction tx = null;
				
        sql += "select p.id_org_fer, p.id_fertilization_org_fer, p.id_product_org_fer,";
        sql += " p.other_product_org_fer, p.amount_product_used_org_fer, p.status, p.created_by"; 
        sql += " from organic_fertilizations p";
        sql += " where p.status=1 and p.id_fertilization_org_fer="+id;
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("p", OrganicFertilizations.class);
            event = (OrganicFertilizations)query.uniqueResult();
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
    
    public List<OrganicFertilizations> getListOrgFert(Integer idFert) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<OrganicFertilizations> eventsTemp = null;
        List<OrganicFertilizations> result     = new ArrayList<OrganicFertilizations>();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String sql = "select p.id_org_fer, p.id_fertilization_org_fer, p.id_product_org_fer,";
            sql += " p.other_product_org_fer, p.amount_product_used_org_fer, p.status, p.created_by"; 
            sql += " from organic_fertilizations p";
            sql += " where p.status=1 and p.id_fertilization_org_fer="+idFert;
            Query query = session.createSQLQuery(sql).addEntity("p", OrganicFertilizations.class);
            eventsTemp = query.list();
            for (OrganicFertilizations data : eventsTemp) {
                if (data!=null && data.getOtherProductOrgFer()!=null && !data.getOtherProductOrgFer().equals("")) data.setOrganicFertilizers(new OrganicFertilizers(1000000, "Otro"));
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

    public void save(OrganicFertilizations event) {
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

    public void delete(OrganicFertilizations event) {
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
