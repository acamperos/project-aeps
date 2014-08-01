package org.aepscolombia.platform.models.dao;

import java.util.List;
//import org.aepscolombia.plataforma.models.dao.IEventoDao;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.ChemicalFertilizers;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase ChemicalFertilizersDao
 *
 * Contiene los metodos para interactuar con la tabla ChemicalFertilizers de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ChemicalFertilizersDao 
{        
    public List<ChemicalFertilizers> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<ChemicalFertilizers> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from ChemicalFertilizers");
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
    
    public List<ChemicalFertilizers> findAllByStatus() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        String sql  = "";        
        List<ChemicalFertilizers> event = null;
        Transaction tx = null;
        
        sql += "select cr.id_che_fer, cr.name_che_fer, cr.status_che_fer from chemical_fertilizers cr";
		sql += " where cr.status_che_fer=1";
//        if (idTypeCrop!=null) {
            //sql += " and t.id_crop_type_irr_typ_cro="+idTypeCrop;
//        }
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("p", ChemicalFertilizers.class);
            event = query.list();
            ChemicalFertilizers temp = new ChemicalFertilizers();
            temp.setIdCheFer(1000000);
            temp.setNameCheFer("Otro");
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
    
    public ChemicalFertilizers objectById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        String sql  = "";        
        ChemicalFertilizers event = null;
        Transaction tx = null;
				
        sql += "select p.id_che_fer, p.name_che_fer, p.status_che_fer";
        sql += " from chemical_fertilizers p";
        sql += " inner join chemical_fertilizations fq on fq.id_product_che_fer=p.id_che_fer";
        sql += " where fq.id_che_fer="+id;
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("p", ChemicalFertilizers.class);
            event = (ChemicalFertilizers)query.uniqueResult();
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

    public void save(ChemicalFertilizers event) {
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

    public void delete(ChemicalFertilizers event) {
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
