package org.aepscolombia.platform.models.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.aepscolombia.platform.models.entity.Entities;
import org.aepscolombia.platform.models.entity.IdiomCountry;
//import org.aepscolombia.plataforma.models.dao.IEventoDao;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.SowingTypes;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase SowingTypesDao
 *
 * Contiene los metodos para interactuar con la tabla SowingTypes de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class SowingTypesDao 
{        
    public List<SowingTypes> findAll(String countryCode) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<SowingTypes> events = null;
        Transaction tx = null;
        String sql  = "";        
//        sql += " select d.id_sow_typ, d.name_sow_typ, d.country_sow_typ, d.status_sow_typ from sowing_types d";
//        sql += " where d.status_sow_type=1";
//        if (countryCode!=null && !countryCode.equals("")) {
//            sql += " and d.country_sow_type='"+countryCode+"'";
//        }
        try {
            tx = session.beginTransaction();
//            Query query = session.createQuery("from SowingTypes");
//            Query query = session.createSQLQuery(sql).addEntity("d", SowingTypes.class);
            Query query = session.createQuery("from SowingTypes WHERE countrySowTyp.acronymIdCo = :country_code");
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
    
    public List<SowingTypes> findAllByTypeCrop(Integer idTypeCrop, String countryCode) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        String sql  = "";        
        List<SowingTypes> event = null;
        Transaction tx = null;
				
        sql += " select d.id_sow_typ, d.name_sow_typ, d.status_sow_typ, d.country_sow_typ from sowing_types d";
        sql += " inner join sowing_types_crops_types pm on pm.id_sowing_type_sow_typ_cro=d.id_sow_typ";
        sql += " where d.status_sow_type=1";
        sql += " and pm.id_crop_type_sow_typ_cro="+idTypeCrop;
        if (countryCode!=null && !countryCode.equals("")) {
            sql += " and d.country_sow_type='"+countryCode+"'";
        }
				
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("d", SowingTypes.class);
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
    
    public SowingTypes objectById(Integer id) {
        SowingTypes event = null;
        return event;
    }    

    public void save(SowingTypes event) {
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

    public void delete(SowingTypes event) {
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
