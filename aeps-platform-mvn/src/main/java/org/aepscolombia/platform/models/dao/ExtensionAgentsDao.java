package org.aepscolombia.platform.models.dao;

import java.util.List;
//import org.aepscolombia.plataforma.models.dao.IEventoDao;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.ExtensionAgents;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase ExtensionAgentsDao
 *
 * Contiene los metodos para interactuar con la tabla ExtensionAgents de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ExtensionAgentsDao {

    public ExtensionAgents findById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        ExtensionAgents event = null;
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            event = (ExtensionAgents) session.load(ExtensionAgents.class, id);
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

    public List<ExtensionAgents> findByParams(String exclude) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<ExtensionAgents> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String sql  = "select p.id_asc, p.id_entity_asc, p.name_asc, p.status, p.created_by from association p";
            if (!exclude.equals("")) sql += " where p.id_dos_uni not in ("+exclude+")";
            Query query = session.createSQLQuery(sql).addEntity("p", ExtensionAgents.class);
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
    
    /**
     * Encargado de verificar si un usuario que ha ingresado un registro en particular en el sistema, tiene asociado un gremio
     * para que este pueda ver la informacion relacionada
     * @param idAsso:  Identificacion de la asociacion
     * @param idExt:  Identificacion del usuario
     * @return Resultado (boolean) esperado
     */
    public static boolean verifyAssociation(Integer idAsso, Integer idExt) 
    {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session  = sessions.openSession();
        ExtensionAgents extAgent  = null;
        Transaction tx   = null;
        boolean permission = false;
        String sql = "";        
        sql  = "select ue.id_ext_age, ue.id_entity_ext_age, ue.work_type_ext_age, ue.id_asso_ext_age, ue.status, ue.created_by";
        sql += " from extension_agents ue";            
        sql += " inner join agents_association agAsc on (agAsc.id_agent_age_asc=ext.id_ext_age)";
        sql += " inner join association asso on agAsc.id_asso_age_asc=asso.id_asc";            
        sql += " where asso.id_entity_asc="+idAsso;	        
        if (idExt!=null) sql += " and ue.id_entity_ext_age="+idExt;
        sql += " and ue.status=1";
//        System.out.println("sql->"+sql);
        
        try {
            tx    = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("ue", ExtensionAgents.class);
            extAgent    = (ExtensionAgents) query.uniqueResult();
            if (extAgent!=null) permission = true;
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return permission;
    }

    public void save(ExtensionAgents event) {
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

    public void delete(ExtensionAgents event) {
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
