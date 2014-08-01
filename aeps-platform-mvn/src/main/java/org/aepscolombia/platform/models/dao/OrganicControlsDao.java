package org.aepscolombia.platform.models.dao;

import java.util.List;
//import org.aepscolombia.plataforma.models.dao.IEventoDao;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.OrganicControls;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase OrganicControlsDao
 *
 * Contiene los metodos para interactuar con la tabla OrganicControls de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class OrganicControlsDao 
{        
    public List<OrganicControls> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<OrganicControls> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from OrganicControls");
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
    
    public List<OrganicControls> findAllByTargetType(Integer idTargetType, Integer idTypeCrop) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        String sql  = "";        
        List<OrganicControls> event = null;
        Transaction tx = null;
		
//        if (idTargetType>0) {
            sql += "select ms.id_org_con, ms.name_org_con, ms.target_type_org_con from organic_controls ms";
            sql += " inner join organic_controls_crops_types t on t.id_org_control_cro_typ=ms.id_org_con";
    //        sql += " where ms.status_dis=1";            
            if (idTypeCrop!=null) {
                sql += " where t.id_crop_type_cro_typ="+idTypeCrop;
            }
            if (idTargetType!=null && idTargetType!=0) {
                sql += " and ms.target_type_org_con="+idTargetType;
            }         
            sql += " order by ms.name_org_con ASC";

            try {
                tx = session.beginTransaction();
                Query query = session.createSQLQuery(sql).addEntity("ms", OrganicControls.class);
                event = query.list();
                OrganicControls temp = new OrganicControls();
                temp.setIdOrgCon(1000000);
                temp.setNameOrgCon("Otro");
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
//        }
        return event;
    }
    
    public OrganicControls objectById(Integer id) {
        OrganicControls event = null;
        return event;
    }    

    public void save(OrganicControls event) {
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

    public void delete(OrganicControls event) {
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
