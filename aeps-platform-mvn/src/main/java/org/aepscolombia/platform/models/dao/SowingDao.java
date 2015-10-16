package org.aepscolombia.platform.models.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//import org.aepscolombia.plataforma.models.dao.IEventoDao;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.Sowing;
import org.aepscolombia.platform.util.HibernateUtil;
import org.hibernate.Criteria;

/**
 * Clase SowingDao
 *
 * Contiene los metodos para interactuar con la tabla Sowing de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class SowingDao 
{    
    
    public HashMap findById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        
        List<Object[]> events = null;
        Transaction tx = null;
        HashMap result = new HashMap();
        return result;
    }
    
    public List<Sowing> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Sowing> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from Sowing");
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

    public List findByParams(HashMap args) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Object[]> eventsTotal = null;
        List<Object[]> events = null;
        Transaction tx = null;
        List<HashMap> result = new ArrayList<HashMap>();
        return result;
    }    
    
    public Sowing objectById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        String sql  = "";        
        Sowing event = null;
        Transaction tx = null;
				
        sql += "select p.id_sow, p.id_production_event_sow, p.date_sow, p.sowing_type_sow, p.seeds_number_sow, p.genotype_sow,";
        sql += " p.treated_seeds_sow, p.used_chemical_sow, p.reason_treatment_sow, p.seed_treatment_type_sow,";
        sql += " p.seed_treatment_dosis_sow, p.furrows_distance_sow, p.sites_distance_sow, p.genotyte_type_seed_sow,";
        sql += " p.seed_origin_sow, p.free_seed_origin_sow, p.other_genotype_sow, p.other_chemical_used_sow,";
        sql += " p.dose_unit_sow,p.cost_sow,p.cost_seed_sow,p.comment_sow ,p.status, p.created_by, g.id_gen, g.name_gen, g.code_gen, g.status_gen, g.crop_type_gen, g.country_gen"; 
        sql += " from sowing p";
        sql += " inner join genotypes g on g.id_gen=p.genotype_sow";
        sql += " where p.id_production_event_sow="+id;
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("p", Sowing.class).addJoin("g", "p.genotypes").addEntity("p", Sowing.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);;
            event = (Sowing)query.uniqueResult();
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

    public void save(Sowing event) {
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

    public void delete(Sowing event) {
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
