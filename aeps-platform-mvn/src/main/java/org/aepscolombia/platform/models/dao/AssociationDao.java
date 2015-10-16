package org.aepscolombia.platform.models.dao;

import java.util.List;
//import org.aepscolombia.plataforma.models.dao.IEventoDao;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.Association;
import org.aepscolombia.platform.models.entity.Entities;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase AssociationDao
 *
 * Contiene los metodos para interactuar con la tabla Association de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class AssociationDao {

    public Association findById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        Association event = null;
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            event = (Association) session.load(Association.class, id);
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

    public List<Association> findByParams(String exclude) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Association> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String sql  = "select p.id_asc, p.id_entity_asc, p.name_asc, p.status, p.created_by from association p";
            if (!exclude.equals("")) sql += " where p.id_dos_uni not in ("+exclude+")";
            Query query = session.createSQLQuery(sql).addEntity("p", Association.class);
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
    
    public List<Entities> gelAllAgronomist(Integer idAssociation) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Entities> events = null;
        Transaction tx = null;
        String sql  = "";
        try {
            tx = session.beginTransaction();
            sql += "select usr.id_ent, usr.id_project_ent, usr.entity_type_ent, usr.document_number_ent, usr.document_type_ent, usr.document_issue_place_ent,"; 	
            sql += "usr.name_ent, usr.in_association_ent, usr.email_ent, usr.email_2_ent, usr.address_ent, usr.id_municipality_ent,"; 	
            sql += "usr.cellphone2_ent, usr.phone_ent, usr.cellphone_ent, usr.status, usr.gender_ent, usr.civil_status_ent,"; 	
            sql += "usr.validation_number_ent, usr.education_level_ent, usr.date_of_birth_ent, usr.first_name_1_ent, usr.person_type_ent,"; 	
            sql += "usr.first_name_2_ent, usr.last_name_1_ent, usr.last_name_2_ent, usr.agent_name_ent, usr.page_link_ent, usr.created_by";
            sql += " from entities usr";
            sql += " inner join extension_agents ext on (ext.id_entity_ext_age=usr.id_ent)";
            sql += " inner join agents_association agAsc on (agAsc.id_agent_age_asc=ext.id_ext_age)";
            sql += " inner join association ass on (ass.id_asc=agAsc.id_asso_age_asc)";
            sql += " where ass.id_entity_asc="+idAssociation;
            sql += " order by usr.name_ent asc"; 
            Query query = session.createSQLQuery(sql).addEntity("usr", Entities.class);
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

    public List<Association> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Association> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from Association where status=1");
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

    public void save(Association event) {
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

    public void delete(Association event) {
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
