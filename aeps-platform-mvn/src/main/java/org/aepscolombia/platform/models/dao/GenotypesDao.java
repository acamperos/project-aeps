package org.aepscolombia.platform.models.dao;

import java.util.List;
//import org.aepscolombia.plataforma.models.dao.IEventoDao;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.Genotypes;
import org.aepscolombia.platform.models.entity.IdiomCountry;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase GenotypesDao
 *
 * Contiene los metodos para interactuar con la tabla Genotypes de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class GenotypesDao 
{        
    public List<Genotypes> findAll(String countryCode) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Genotypes> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
//            Query query = session.createQuery("from Genotypes");
            Query query = session.createQuery("from Genotypes WHERE countryGen.acronymIdCo = :country_code");
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
    
    public List<Genotypes> findAllByTypeCrop(Integer idTypeCrop, Integer idFilter, String countryCode) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        String sql  = "";        
        List<Genotypes> event = null;
        Transaction tx = null;
				
        sql  += "select mg.id_gen, mg.name_gen, mg.code_gen, mg.country_gen, mg.status_gen, mg.crop_type_gen from genotypes mg";
        if (idTypeCrop==1) {
            sql += " inner join genotypes_colors t on t.id_genotype_gen_col=mg.id_gen";
        } else if (idTypeCrop==2) {
            sql += " inner join genotypes_growing_environments t on t.id_genotype_gen_gro_env=mg.id_gen";
        }
        sql += " where mg.status_gen=1";
        if (idFilter!=0 && idTypeCrop==1) {
            sql += " and t.id_seed_color_gen_col="+idFilter;
        } else if (idFilter!=0 && idTypeCrop==2) {
            sql += " and t.id_growing_environment_gen_gro_env="+idFilter;
        } else if (idTypeCrop==3) {
            sql += " and (mg.crop_type_gen="+idTypeCrop+" or mg.id_gen=1000000)";
        }
        
        if (countryCode!=null && !countryCode.equals("")) {
            sql += " and mg.country_gen='"+countryCode+"'";
        }
				
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("mg", Genotypes.class);
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
    
    public Genotypes objectById(Integer id) {
        Genotypes event = null;
        return event;
    }    

    public void save(Genotypes event) {
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

    public void delete(Genotypes event) {
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
