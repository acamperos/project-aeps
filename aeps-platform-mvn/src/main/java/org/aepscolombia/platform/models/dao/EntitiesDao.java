package org.aepscolombia.platform.models.dao;

import java.util.List;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.Entities;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase EntitiesDao
 *
 * Contiene los metodos para interactuar con la tabla Entities de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class EntitiesDao {

    public Entities checkEntityIdent(String typeIdent, String ident) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        Entities event = null;
        Transaction tx = null;
        String sql = "";

        sql += "select usr.id_ent, usr.id_project_ent, usr.entity_type_ent, usr.document_number_ent, usr.document_type_ent, usr.document_issue_place_ent,"; 	
        sql += "usr.name_ent, usr.in_association_ent, usr.email_ent, usr.email_2_ent, usr.address_ent, usr.id_municipality_ent,"; 	
        sql += "usr.cellphone2_ent, usr.phone_ent, usr.cellphone_ent, usr.status, usr.gender_ent, usr.civil_status_ent,"; 	
        sql += "usr.validation_number_ent, usr.education_level_ent, usr.date_of_birth_ent, usr.first_name_1_ent,"; 	
        sql += "usr.first_name_2_ent, usr.last_name_1_ent, usr.last_name_2_ent";
        
//        sql += "select usr.id_usr, usr.name_user_usr, usr.password_usr, usr.cod_validation_usr, usr.status";
        sql += " from entities usr";
        sql += " where usr.document_type_ent='"+typeIdent+"'";
        sql += " and usr.document_number_ent="+ident;
//        System.out.println("sql->"+sql);
        
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("usr", Entities.class);
            event = (Entities)query.uniqueResult();
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
    
    public Entities findById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        Entities event = null;
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            String hql  = "FROM Entities E WHERE E.idEnt = :id_ent";
            Query query = session.createQuery(hql);
            query.setParameter("id_ent", id);
            event = (Entities)query.uniqueResult();
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

    public List<Entities> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Entities> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from Entities");
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

    public void save(Entities event) throws HibernateException {
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

    public void delete(Entities event) {
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
