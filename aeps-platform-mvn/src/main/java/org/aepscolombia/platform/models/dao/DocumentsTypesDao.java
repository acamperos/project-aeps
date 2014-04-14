package org.aepscolombia.platform.models.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.aepscolombia.plataforma.models.dao.IEventoDao;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.DocumentsTypes;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase DocumentsTypesDao
 *
 * Contiene los metodos para interactuar con la tabla DocumentsTypes de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class DocumentsTypesDao {

    public DocumentsTypes findById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        DocumentsTypes event = null;
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            event = (DocumentsTypes) session.load(DocumentsTypes.class, id);
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

    public List findByParams(String[] args) {
//        String sql = "SELECT ID as {c.id}, NAME as {c.name}, " + 
//         "BIRTHDATE as {c.birthDate}, MOTHER_ID as {c.mother}, {mother.*} " +
//         "FROM CAT_LOG c, CAT_LOG m WHERE {c.mother} = c.ID";
//
//        List loggedCats = sess.createSQLQuery(sql)
//                .addEntity("cat", Cat.class)
//                .addEntity("mother", Cat.class).list()
//        JSONUtil fd = new JSONUtil();
//        fd.
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Object[]> events = null;
        Transaction tx = null;
//        events.toArray();
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery("select acronym_doc_typ, name_doc_typ from documents_types");
            events = query.list();
//            for (Object[] data : events) {
//                System.out.println(data[0] + "-" + data[1]);
//            }
//            System.out.println(JSONUtil.serialize(events));
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

    public List<DocumentsTypes> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<DocumentsTypes> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from DocumentsTypes");
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

    public void save(DocumentsTypes event) {
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

    public void delete(DocumentsTypes event) {
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
