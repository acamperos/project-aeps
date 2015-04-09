package org.aepscolombia.platform.models.dao;

import java.util.List;
//import org.aepscolombia.plataforma.models.dao.IEventoDao;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.FertilizationsTypes;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase FertilizationsTypesDao
 *
 * Contiene los metodos para interactuar con la tabla FertilizationsTypes de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class FertilizationsTypesDao 
{        
    public List<FertilizationsTypes> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<FertilizationsTypes> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from FertilizationsTypes");
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
    
    public List<FertilizationsTypes> findAllByStatus(Integer idTypeCrop) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        String sql  = "";        
        List<FertilizationsTypes> event = null;
        Transaction tx = null;
        
        sql += "select cr.id_irr_typ, cr.name_irr_typ, cr.status_irr_typ from fertilizations_types cr";
//		sql += " inner join irrigations_types_crops_types t on t.id_irrigation_type_irr_typ_cro=cr.id_irr_typ";
		sql += " where cr.status_irr_typ=1";
        if (idTypeCrop!=null) {
            //sql += " and t.id_crop_type_irr_typ_cro="+idTypeCrop;
        }
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("p", FertilizationsTypes.class);
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
    
    public FertilizationsTypes objectById(Integer id) {
        FertilizationsTypes event = null;
        return event;
    }    

    public void save(FertilizationsTypes event) {
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

    public void delete(FertilizationsTypes event) {
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
