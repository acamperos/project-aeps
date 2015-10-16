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
import org.aepscolombia.platform.models.entity.ResultingProducts;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase ResultingProductsDao
 *
 * Contiene los metodos para interactuar con la tabla ResultingProducts de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ResultingProductsDao 
{        
    public List<ResultingProducts> findAll(String countryCode) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<ResultingProducts> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
//            Query query = session.createQuery("from ResultingProducts");
            Query query = session.createQuery("from ResultingProducts WHERE countryResPro.acronymIdCo = :country_code and statusResPro=1");
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
    
    public List<ResultingProducts> findAllByTypeCrop(Integer idTypeCrop, String countryCode) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        String sql  = "";        
        List<ResultingProducts> event = null;
        Transaction tx = null;
				
        sql += " select d.id_res_pro, d.name_res_pro, d.status_res_pro, d.country_res_pro from resulting_products d";
        sql += " inner join resutling_products_crops_types pm on pm.id_resulting_product_res_pro_cro=d.id_res_pro";
        sql += " where d.status_res_pro=1";
        sql += " and pm.id_crop_type_res_pro_cro="+idTypeCrop;
        if (countryCode!=null && !countryCode.equals("")) {
            sql += " and d.country_res_pro='"+countryCode+"'";
        }
				
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("d", ResultingProducts.class);
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
    
    public ResultingProducts objectById(Integer id) {
        ResultingProducts event = null;
        return event;
    }    

    public void save(ResultingProducts event) {
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

    public void delete(ResultingProducts event) {
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
