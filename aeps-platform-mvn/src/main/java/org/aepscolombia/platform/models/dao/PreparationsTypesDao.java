package org.aepscolombia.platform.models.dao;

import java.util.List;
//import org.aepscolombia.plataforma.models.dao.IEventoDao;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.PreparationsTypes;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase PreparationsTypesDao
 *
 * Contiene los metodos para interactuar con la tabla PreparationsTypes de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class PreparationsTypesDao 
{        
    public List<PreparationsTypes> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<PreparationsTypes> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from PreparationsTypes");
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
    
    public List<PreparationsTypes> findAllByTypeCrop(Integer idTypeCrop) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        String sql  = "";        
        List<PreparationsTypes> event = null;
        Transaction tx = null;
        
        sql += "select tp.id_pre_typ, tp.name_pre_typ, tp.status_pre_typ from preparations_types tp";
		sql += " inner join preparations_types_crops_types t on t.id_preparation_type_pre_typ_cro=tp.id_pre_typ";
		sql += " where tp.status_pre_typ=1";
        if (idTypeCrop!=null) {
            sql += " and t.id_crop_type_pre_typ_cro="+idTypeCrop;
        }
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("p", PreparationsTypes.class);
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
    
    public PreparationsTypes objectById(Integer id) {
        PreparationsTypes event = null;
        return event;
    }    

    public void save(PreparationsTypes event) {
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

    public void delete(PreparationsTypes event) {
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
