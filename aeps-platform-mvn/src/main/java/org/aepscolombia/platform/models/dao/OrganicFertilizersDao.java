package org.aepscolombia.platform.models.dao;

import java.util.List;
//import org.aepscolombia.plataforma.models.dao.IEventoDao;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.OrganicFertilizers;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase OrganicFertilizersDao
 *
 * Contiene los metodos para interactuar con la tabla OrganicFertilizers de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class OrganicFertilizersDao 
{        
    public List<OrganicFertilizers> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<OrganicFertilizers> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from OrganicFertilizers");
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
    
    public List<OrganicFertilizers> findAllByStatus() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        String sql  = "";        
        List<OrganicFertilizers> event = null;
        Transaction tx = null;
        
        sql += "select cr.id_org_fer, cr.name_org_fer, cr.status_org_fer from organic_fertilizers cr";
		sql += " where cr.status_org_fer=1";
//        if (idTypeCrop!=null) {
            //sql += " and t.id_crop_type_irr_typ_cro="+idTypeCrop;
//        }
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("p", OrganicFertilizers.class);
            event = query.list();
            OrganicFertilizers temp = new OrganicFertilizers();
            temp.setIdOrgFer(1000000);
            temp.setNameOrgFer("Otro");
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
    
    public OrganicFertilizers objectById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        String sql  = "";        
        OrganicFertilizers event = null;
        Transaction tx = null;
				
        sql += "select p.id_org_fer, p.name_org_fer, p.status_org_fer";
        sql += " from organic_fertilizers p";
        sql += " inner join organic_fertilizations fq on fq.id_product_org_fer=p.id_org_fer";
        sql += " where fq.id_fertilization_org_fer="+id;
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("p", OrganicFertilizers.class);
            event = (OrganicFertilizers)query.uniqueResult();
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

    public void save(OrganicFertilizers event) {
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

    public void delete(OrganicFertilizers event) {
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
