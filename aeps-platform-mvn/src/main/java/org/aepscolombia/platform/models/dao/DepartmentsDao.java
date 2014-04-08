package org.aepscolombia.platform.models.dao;

import java.util.List;
//import org.aepscolombia.plataforma.models.dao.IEventoDao;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.Departments;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase DepartmentsDao
 *
 * Contiene los metodos para interactuar con la tabla Departments de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class DepartmentsDao {
    
    public Departments findById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        Departments evento = null;
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            evento = (Departments) session.load(Departments.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return evento;
    }

    public List findByParams(String[] args) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Object[]> eventos = null;
        Transaction tx = null;
//        eventos.toArray();
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery("select id_dep, name_dep from departments");
            eventos = query.list();
//            for (Object[] datos : eventos) {
//                System.out.println(datos[0] + "-" + datos[1]);
//            }
//            System.out.println(JSONUtil.serialize(eventos));
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return eventos;
    }

    public List<Departments> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Departments> eventos = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql  = "from Departments order by nameDep ASC";
            Query query = session.createQuery(hql);
            eventos = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return eventos;
    }

    public void save(Departments evento) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(evento);
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

    public void delete(Departments evento) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(evento);
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
