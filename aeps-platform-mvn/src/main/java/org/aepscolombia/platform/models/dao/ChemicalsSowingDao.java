package org.aepscolombia.platform.models.dao;

import java.util.List;
//import org.aepscolombia.plataforma.models.dao.IEventoDao;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.ChemicalsSowing;
import org.aepscolombia.platform.models.entity.IdiomCountry;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase ChemicalsSowingDao
 *
 * Contiene los metodos para interactuar con la tabla ChemicalsSowing de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ChemicalsSowingDao 
{        
    public List<ChemicalsSowing> findAll(String countryCode) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<ChemicalsSowing> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from ChemicalsSowing");
//            Query query = session.createQuery("from ChemicalsSowing WHERE countryCheSow.acronymIdCo = :country_code");
//            query.setParameter("country_code", countryCode);
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
    
    public List<ChemicalsSowing> findAllByTypeCrop(Integer idTypeCrop, String countryCode) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        String sql  = "";        
        List<ChemicalsSowing> event = null;
        Transaction tx = null;
				
        sql += "select p.id_che_sow, p.name_che_sow, p.country_che_sow, p.status_che_sow";
        sql += " from chemicals_sowing p";
        sql += " inner join chemicals_sowing_crops_types t on t.id_chemical_sowing_che_sow_cro_typ=p.id_che_sow";
        sql += " where p.status_che_sow=1";
        if (idTypeCrop!=null) {
            sql += " and t.id_crop_type_che_sow_cro_typ="+idTypeCrop;
        }
        /*if (countryCode!=null && !countryCode.equals("")) {
                sql += " and p.country_ame_fer='"+countryCode+"'";
            }*/
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("p", ChemicalsSowing.class);
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
    
    public ChemicalsSowing objectById(Integer id) {
        ChemicalsSowing event = null;
        return event;
    }    

    public void save(ChemicalsSowing event) {
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

    public void delete(ChemicalsSowing event) {
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
