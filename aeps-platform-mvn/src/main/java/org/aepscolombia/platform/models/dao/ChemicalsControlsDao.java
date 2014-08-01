package org.aepscolombia.platform.models.dao;

import java.util.List;
//import org.aepscolombia.plataforma.models.dao.IEventoDao;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.ChemicalsControls;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase ChemicalsControlsDao
 *
 * Contiene los metodos para interactuar con la tabla ChemicalsControls de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ChemicalsControlsDao 
{        
    public List<ChemicalsControls> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<ChemicalsControls> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from ChemicalsControls");
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
    
    public List<ChemicalsControls> findAllByTargetType(Integer idTargetType, Integer idTypeCrop) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        String sql  = "";        
        List<ChemicalsControls> event = null;
        Transaction tx = null;
				
//        if (idTargetType>0) {
            sql += "select ms.id_che_con, ms.name_che_con, ms.comer_name_che_con, ms.target_name_che_con from chemicals_controls ms";
            sql += " inner join chemicals_controls_crops_types t on t.id_che_controls_che_con_cro_typ=ms.id_che_con";
    //        sql += " where ms.status_dis=1";            
            if (idTypeCrop!=null) {
                sql += " where t.id_crop_type_che_con_cro_typ="+idTypeCrop;
            }
            if (idTargetType!=null && idTargetType!=0) {
                sql += " and ms.target_name_che_con="+idTargetType;
            }
            sql += " order by ms.name_che_con ASC";

            try {
                tx = session.beginTransaction();
                Query query = session.createSQLQuery(sql).addEntity("ms", ChemicalsControls.class);
                event = query.list();
                ChemicalsControls temp = new ChemicalsControls();
                temp.setIdCheCon(1000000);
                temp.setNameCheCon("Otro");
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
    
    public ChemicalsControls objectById(Integer id) {
        ChemicalsControls event = null;
        return event;
    }    

    public void save(ChemicalsControls event) {
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

    public void delete(ChemicalsControls event) {
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
