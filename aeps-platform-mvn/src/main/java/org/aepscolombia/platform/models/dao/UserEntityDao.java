package org.aepscolombia.platform.models.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
//import java.sql.ResultSet;
import java.util.List;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.UserEntity;
import org.aepscolombia.platform.util.HibernateUtil;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

//import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase UserEntitiesDao
 *
 * Contiene los metodos para interactuar con la tabla UserEntities de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class UserEntityDao 
{    
    
    public HashMap findById(Integer id) {
        return null;
    }
    
    public List<UserEntity> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<UserEntity> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from UserEntity");
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
        return null;
    }   
    
    public UserEntity objectById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        UserEntity event = null;
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            event = (UserEntity) session.load(UserEntity.class, id);
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

    public boolean save(UserEntity event) throws HibernateException {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        boolean respond = false;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(event);
            tx.commit();
            respond = true;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return respond;
    }
    
    public void saveUserEnt(Integer idEnt, Integer idUser) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Transaction tx = null;
//        HibernateUtil.getInstanceConnection();

        try {
            tx = session.beginTransaction();
            String query = "insert into user_entity (id_usr_ent, id_project_usr_ent, id_user_usr_ent, id_entity_usr_ent, status) values (?,?,?,?,?)";

            // Ejecutamos la query y obtenemos el resultado.
            PreparedStatement stmt;
            stmt = HibernateUtil.getInstanceConnection().prepareStatement(query);
            stmt.setString(1, null);
            stmt.setInt(2, 1);
            stmt.setInt(3, idUser);
            stmt.setInt(4, idEnt);
            stmt.setInt(5, 1);
            
            stmt.executeUpdate();
            stmt.close();
            HibernateUtil.closeConnection();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } catch (SQLException ex) {
            if (tx != null) {
                tx.rollback();
            }
            ex.printStackTrace();
        }
        
//        return event;
    }

    public void delete(UserEntity event) {
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
