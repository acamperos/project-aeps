package org.aepscolombia.platform.models.dao;

import java.util.List;
//import org.aepscolombia.plataforma.models.dao.IEventoDao;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.AmendmentsFertilizers;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase AmendmentsFertilizersDao
 *
 * Contiene los metodos para interactuar con la tabla AmendmentsFertilizers de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class AmendmentsFertilizersDao 
{        
    public List<AmendmentsFertilizers> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<AmendmentsFertilizers> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from AmendmentsFertilizers");
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
    
    public List<AmendmentsFertilizers> findAllByStatus() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        String sql  = "";        
        List<AmendmentsFertilizers> event = null;
        Transaction tx = null;
        
        sql += "select cr.id_ame_fer, cr.name_ame_fer, cr.status_ame_fer from amendments_fertilizers cr";
		sql += " where cr.status_ame_fer=1";
//        if (idTypeCrop!=null) {
            //sql += " and t.id_crop_type_irr_typ_cro="+idTypeCrop;
//        }
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("p", AmendmentsFertilizers.class);
            event = query.list();
            AmendmentsFertilizers temp = new AmendmentsFertilizers();
            temp.setIdAmeFer(1000000);
            temp.setNameAmeFer("Otro");
            event.add(temp);
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
    
    public AmendmentsFertilizers objectById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        String sql  = "";        
        AmendmentsFertilizers event = null;
        Transaction tx = null;
				
        sql += "select p.id_ame_fer, p.name_ame_fer, p.status_ame_fer";
        sql += " from amendments_fertilizers p";
        sql += " inner join amendments_fertilizations fq on fq.id_product_ame_fer=p.id_ame_fer";
        sql += " where fq.id_fertilization_ame_fer="+id;
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("p", AmendmentsFertilizers.class);
            event = (AmendmentsFertilizers)query.uniqueResult();
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

    public void save(AmendmentsFertilizers event) {
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

    public void delete(AmendmentsFertilizers event) {
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
