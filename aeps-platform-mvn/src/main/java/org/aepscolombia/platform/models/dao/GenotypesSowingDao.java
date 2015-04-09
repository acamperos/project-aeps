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
import org.aepscolombia.platform.models.entity.GenotypesSowing;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase GenotypesSowingDao
 *
 * Contiene los metodos para interactuar con la tabla GenotypesSowing de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class GenotypesSowingDao 
{        
    public List<GenotypesSowing> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<GenotypesSowing> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from GenotypesSowing");
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
    
    public List<GenotypesSowing> findAllByTypeCrop(Integer idTypeCrop) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        String sql  = "";        
        List<GenotypesSowing> event = null;
        Transaction tx = null;
				
        sql += "select ms.id_gen_sow, ms.name_gen_sow, ms.status_gen_sow from genotypes_sowing ms";
        sql += " inner join genotypes_sowing_crop_types t on t.id_gentoypes_sowing_gen_sow_cro=ms.id_gen_sow";
        sql += " where ms.status_gen_sow=1";
        if (idTypeCrop!=null) {
            sql += " and t.id_crop_type_gen_sow_cro="+idTypeCrop;
        }
				
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("ms", GenotypesSowing.class);
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
    
    public GenotypesSowing objectById(Integer id) {
        GenotypesSowing event = null;
        return event;
    }    

    public void save(GenotypesSowing event) {
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

    public void delete(GenotypesSowing event) {
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
