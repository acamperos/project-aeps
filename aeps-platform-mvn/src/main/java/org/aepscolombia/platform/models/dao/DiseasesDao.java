package org.aepscolombia.platform.models.dao;

import java.util.List;
//import org.aepscolombia.plataforma.models.dao.IEventoDao;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.Diseases;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase DiseasesDao
 *
 * Contiene los metodos para interactuar con la tabla Diseases de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class DiseasesDao 
{        
    public List<Diseases> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Diseases> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from Diseases");
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
    
    public List<Diseases> findAllByTypeCrop(Integer idTypeCrop, String countryCode) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        String sql  = "";        
        List<Diseases> event = null;
        Transaction tx = null;
				
        sql += "select ms.id_dis, ms.name_dis, ms.status_dis from diseases ms";
        sql += " inner join diseases_country cheCon on cheCon.id_seldis_dis_co=ms.id_dis";
        sql += " inner join diseases_crops_types t on t.id_disease_dis_cro_typ=ms.id_dis";
        sql += " where ms.status_dis=1";
        if (idTypeCrop!=null) {
            sql += " and t.id_crop_type_dis_cro_typ="+idTypeCrop;
        }
        if (countryCode!=null && !countryCode.equals("")) {
            sql += " and cheCon.country_dis_co='"+countryCode+"'";
        } 
        
        sql += " order by ms.name_dis ASC";
				
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("ms", Diseases.class);
            event = query.list();
            Diseases temp = new Diseases();
            temp.setIdDis(1000000);
            temp.setNameDis("Otro");
            event.add(temp);
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
    
    public Diseases objectById(Integer id) {
        Diseases event = null;
        return event;
    }    

    public void save(Diseases event) {
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

    public void delete(Diseases event) {
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
