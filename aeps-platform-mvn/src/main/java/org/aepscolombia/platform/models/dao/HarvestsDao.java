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
import org.aepscolombia.platform.models.entity.Harvests;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase HarvestsDao
 *
 * Contiene los metodos para interactuar con la tabla Harvests de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class HarvestsDao 
{    
    
    public HashMap findById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        
        List<Object[]> events = null;
        Transaction tx = null;
        HashMap result = new HashMap();
        return result;
    }
    
    public List<Harvests> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Harvests> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from Harvests");
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
    
    public Harvests objectById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        String sql  = "";        
        Harvests event = null;
        Transaction tx = null;
				
        sql += "select p.id_har, p.id_production_event_har, p.date_har, p.comment_har, p.method_har, p.production_har,";
        sql += " p.yield_har, p.storage_har, p.production_per_plant_har, p.expected_product_type_har,";
        sql += " p.humidity_percentage_har, p.number_sacks_sow, p.weight_avg_sacks_sow, p.load_hectare_sow, ";
        sql += " p.cost_saleprice_har, p.cost_namebuyer_har,p.cost_packing_har,p.cost_type_har,p.cost_combine_har,p.cost_storage_har," ;
        sql += "p.cost_tractor_har,p.cost_granalero_trailer_har,p.cost_zorreo_har,p.cost_cabuya_har,p.cost_transport_collectioncenter_har,";
        sql += "p.cost_workforce_har,p.cost_transport_workforce_har,p.cost_sheller_har,p.cost_workforce_sheller_har,";
        sql += " p.status, p.created_by";
        sql += " from harvests p";
        sql += " where p.id_production_event_har="+id;
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("p", Harvests.class);
            event = (Harvests)query.uniqueResult();
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

    public void save(Harvests event) {
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

    public void delete(Harvests event) {
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
