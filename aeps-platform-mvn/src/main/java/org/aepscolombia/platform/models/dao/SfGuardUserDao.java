package org.aepscolombia.platform.models.dao;

import java.util.List;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entityservices.SfGuardUser;
import org.aepscolombia.platform.util.HibernateUtilMobile;

/**
 * Clase SfGuardUserDao
 *
 * Contiene los metodos para interactuar con la tabla SfGuardUser de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class SfGuardUserDao {
    
    public SfGuardUser getUserByLogin(Integer idUser, String username, String password) {
        SessionFactory sessions = HibernateUtilMobile.getSessionFactory();
        Session session = sessions.openSession();
        SfGuardUser events = null;
        Transaction tx = null;
        String sql = "";        

        if (idUser!=null || (username!=null && !username.equals(""))) {
            sql += "select usr.id, usr.email_address, usr.first_name, usr.last_name, usr.algorithm, usr.salt, usr.password,";
            sql += " usr.is_super_admin, usr.last_login, usr.username, usr.is_active, usr.created_by, usr.updated_by,";
            sql += " usr.created_at, usr.updated_at, usr.deleted_at, usr.deleted_by, usr.can_login";  
    //        sql += "select usr.id_usr, usr.name_user_usr, usr.password_usr, usr.last_in_usr, usr.cod_validation_usr, usr.status, usr.created_by";
            sql += " from sf_guard_user usr";
    //        sql += " where usr.status=1";
            if (idUser!=null) {
                sql += " where usr.id="+idUser;
            } else if ((idUser==null) && (username!=null && !username.equals(""))) {
                sql += " where usr.username='"+username+"'";
            } else if (username!=null && !username.equals("")) {
                sql += " where usr.email_address='"+username+"'";
            }  
            if (password!=null && !password.equals("")) {
                sql += " and usr.password='"+password+"'";
            }  
//        System.out.println("sql->"+sql);

            try {
                tx = session.beginTransaction();
                Query query = session.createSQLQuery(sql).addEntity("usr", SfGuardUser.class);
                events = (SfGuardUser)query.uniqueResult();
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
        return events;
    }
    
    public SfGuardUser findById(Integer id) {
        SessionFactory sessions = HibernateUtilMobile.getSessionFactory();
        Session session = sessions.openSession();

        SfGuardUser event = null;
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            String hql  = "FROM SfGuardUser E WHERE E.id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            event = (SfGuardUser)query.uniqueResult();
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

    public List<SfGuardUser> findAll() {
        SessionFactory sessions = HibernateUtilMobile.getSessionFactory();
        Session session = sessions.openSession();
        List<SfGuardUser> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from SfGuardUser");
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

    public void save(SfGuardUser event) throws HibernateException {
        SessionFactory sessions = HibernateUtilMobile.getSessionFactory();
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

    public void delete(SfGuardUser event) {
        SessionFactory sessions = HibernateUtilMobile.getSessionFactory();
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
