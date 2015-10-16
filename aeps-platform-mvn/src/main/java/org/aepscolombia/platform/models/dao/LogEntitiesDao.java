package org.aepscolombia.platform.models.dao;

import java.util.Date;
import java.util.List;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.LogEntities;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase LogEntitiesDao
 *
 * Contiene los metodos para interactuar con la tabla LogEntities de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class LogEntitiesDao {

    /**
     * Encargado de obtener la fecha en que fue registrado el usuario
     * @param idEnt:  Identificacion de la entidad en el sistema
     * @param idUser:  Identificacion del usuario en el sistema
     * @return Fecha de ingreso en el sistema
     */
    public static Date getDateIngress(Integer idEnt, Integer idUser) 
    {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session  = sessions.openSession();
        Date dateIngress = null;
        LogEntities logEnt  = null;
        Transaction tx = null;
        String sql = "";        
        sql  = "select ue.id_log_ent, ue.id_entity_log_ent, ue.id_object_log_ent, ue.table_log_ent, ue.date_log_ent, ue.action_type_log_ent ";
        sql += " from log_entities ue";
		sql += " where ue.id_entity_log_ent="+idEnt;		
        sql += " and ue.id_object_log_ent="+idUser;
        sql += " and ue.table_log_ent='users'";
        sql += " and ue.action_type_log_ent='C'";
//        System.out.println("sql->"+sql);
        
        try {
            tx    = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("ue", LogEntities.class);
            logEnt      = (LogEntities) query.uniqueResult();
            if (logEnt!=null) dateIngress = logEnt.getDateLogEnt();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return dateIngress;
    }
    
    public LogEntities findById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        LogEntities event = null;
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            event = (LogEntities) session.load(LogEntities.class, id);
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

    public List<LogEntities> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<LogEntities> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from LogEntities");
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

    public void save(LogEntities event) throws HibernateException {
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

    public void delete(LogEntities event) {
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
    
    /**
     * Encargado de obtener el registro historico de un dato
     * @param idEnt:  Identificacion de la entidad en el sistema
     * @param idObj:  Identificacion del objeto que se encuentra en el sitema
     * @param tableName:  Nombre de la tabla relacionada
     * @param actionName:  Accion realizada
     * @return Objeto (LogEntities) relacionado
     */
    public static LogEntities getData(Integer idEnt, Integer idObj, String tableName, String actionName) 
    {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session  = sessions.openSession();
        LogEntities logEnt  = null;
        Transaction tx   = null;
        String sql = "";        
        sql  = "select ue.id_log_ent, ue.id_entity_log_ent, ue.id_object_log_ent, ue.table_log_ent, ue.date_log_ent, ue.action_type_log_ent ";
        sql += " from log_entities ue";
        if (idEnt!=null) {
            sql += " where ue.id_entity_log_ent="+idEnt;
            sql += " and ue.id_object_log_ent="+idObj;
        } else {
            sql += " where ue.id_object_log_ent="+idObj;
        }		        
        sql += " and ue.table_log_ent='"+tableName+"'";
        sql += " and ue.action_type_log_ent='"+actionName+"'";
//        System.out.println("sql->"+sql);
        
        try {
            tx    = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("ue", LogEntities.class);
            logEnt      = (LogEntities) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return logEnt;
    }
}
