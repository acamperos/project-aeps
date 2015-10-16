package org.aepscolombia.platform.models.dao;

import java.util.List;
//import org.aepscolombia.plataforma.models.dao.IEventoDao;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.Pests;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase PestsDao
 *
 * Contiene los metodos para interactuar con la tabla Pests de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class PestsDao 
{        
    public List<Pests> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Pests> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from Pests");
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
    
    public List<Pests> findAllByTypeCrop(Integer idTypeCrop, String countryCode) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        String sql  = "";        
        List<Pests> event = null;
        Transaction tx = null;
				
        sql += "select ms.id_pes, ms.name_pes, ms.status_pes from pests ms";
        sql += " inner join pests_country cheCon on cheCon.id_selpes_pes_co=ms.id_pes";
        sql += " inner join pests_crops_types t on t.id_pest_pes_cro_typ=ms.id_pes";
        sql += " where ms.status_pes=1";
        if (idTypeCrop!=null) {
            sql += " and t.id_crop_type_pes_cro_typ="+idTypeCrop;
        }
        
        if (countryCode!=null && !countryCode.equals("")) {
            sql += " and cheCon.country_pes_co='"+countryCode+"'";
        }
        sql += " order by ms.name_pes ASC";
				
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("ms", Pests.class);
            event = query.list();
            Pests temp = new Pests();
            temp.setIdPes(1000000);
            temp.setNamePes("Otro");
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
    
    public Pests objectById(Integer id) {
        Pests event = null;
        return event;
    }    

    public void save(Pests event) {
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

    public void delete(Pests event) {
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
