package org.aepscolombia.platform.models.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.aepscolombia.platform.models.entity.ApplicationTypes;
import org.aepscolombia.platform.models.entity.ChemicalFertilizations;
import org.aepscolombia.platform.models.entity.ChemicalFertilizers;
import org.aepscolombia.platform.models.entity.Fertilizations;
//import org.aepscolombia.plataforma.models.dao.IEventoDao;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase ChemicalFertilizationsDao
 *
 * Contiene los metodos para interactuar con la tabla ChemicalFertilizations de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ChemicalFertilizationsDao 
{    
    
    public HashMap findById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        
        List<Object[]> events = null;
        Transaction tx = null;
        HashMap result = new HashMap();
        return result;
    }
    
    public List<ChemicalFertilizations> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<ChemicalFertilizations> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from ChemicalFertilizations");
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
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Object[]> eventsTotal = null;
        List<Object[]> events = null;
        Transaction tx = null;
        List<HashMap> result = new ArrayList<HashMap>();
        return result;
    }    
    
    public ChemicalFertilizations objectById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        String sql  = "";        
        ChemicalFertilizations event = null;
        Transaction tx = null;
				
        sql += "select p.id_che_fer, p.id_fertilization_che_fer, p.id_product_che_fer,";
        sql += " p.other_product_che_fer, p.status, p.application_type_che_fer, p.unit_che_fer, p.created_by"; 
        sql += " from chemical_fertilizations p";
        sql += " where p.status=1 and p.id_fertilization_che_fer="+id;
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("p", ChemicalFertilizations.class);
            event = (ChemicalFertilizations)query.uniqueResult();
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
    
    public List<ChemicalFertilizations> getListChemFertOld(Integer idFert) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<ChemicalFertilizations> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String sql = "select p.id_che_fer, p.id_fertilization_che_fer, p.id_product_che_fer,";
            sql += " p.other_product_che_fer, p.status, p.application_type_che_fer, p.amount_product_used_che_fer, p.unit_che_fer, p.created_by"; 
            sql += " from chemical_fertilizations p";
            sql += " where p.status=1 and p.id_fertilization_che_fer="+idFert;
            Query query = session.createSQLQuery(sql).addEntity("p", ChemicalFertilizations.class);
            events  = query.list();
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
    
    public List<ChemicalFertilizationsObj> getListChemFert(Integer idFert) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<ChemicalFertilizations> eventsTemp = null;
        List<ChemicalFertilizationsObj> result  = new ArrayList<ChemicalFertilizationsObj>();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String sql = "select p.id_che_fer, p.id_fertilization_che_fer, p.id_product_che_fer,";
            sql += " p.other_product_che_fer, p.status, p.application_type_che_fer, p.amount_product_used_che_fer, p.unit_che_fer, p.created_by"; 
            sql += " from chemical_fertilizations p";
            sql += " where p.status=1 and p.id_fertilization_che_fer="+idFert;
//            System.out.println("sql=>"+sql);
            Query query = session.createSQLQuery(sql).addEntity("p", ChemicalFertilizations.class);
            eventsTemp  = query.list();
//            for (Object[] data : eventsTemp) {
            for (ChemicalFertilizations data : eventsTemp) {
                ChemicalFertilizationsObj cheFer = new ChemicalFertilizationsObj();                
//                Integer idCheFer = Integer.parseInt(String.valueOf(data[0]));
//                Integer idFer    = Integer.parseInt(String.valueOf(data[1]));
//                Integer idPro    = Integer.parseInt(String.valueOf(data[2]));
//                String otherTemp = String.valueOf(data[3]);
//                Boolean status   = Boolean.valueOf(String.valueOf(data[4]));
//                Integer idAppTyp = Integer.parseInt(String.valueOf(data[5]));
//                Double  amoFer   = Double.parseDouble(String.valueOf(data[6]));
//                Integer unitFer  = Integer.parseInt(String.valueOf(data[7]));
                
                
                Integer idCheFer = data.getIdCheFer();
                String otherTemp = data.getOtherProductCheFer();
                
                cheFer.setAdditionalsElem(new ChemicalElementsDao().findByParams(idCheFer));
                cheFer.setIdCheFer(idCheFer);
                cheFer.setFertilizations(data.getFertilizations());
                cheFer.setStatus(data.getStatus());
                cheFer.setChemicalFertilizers(data.getChemicalFertilizers());
                cheFer.setOtherProductCheFer(otherTemp);
                cheFer.setApplicationTypes(data.getApplicationTypes());
                cheFer.setAmountProductUsedCheFer(data.getAmountProductUsedCheFer());
                cheFer.setUnitCheFer(data.getUnitCheFer());
//                System.out.println("data=>"+cheFer.getAmountProductUsedCheFer());
//                data.setAdditionalsElem(new ChemicalElementsDao().findByParams(data.getIdCheFer()));
//                if (data!=null && data.getOtherProductCheFer()!=null && !data.getOtherProductCheFer().equals("")) data.setChemicalFertilizers(new ChemicalFertilizers(1000000, "Otro"));
                if (data!=null && otherTemp!=null && !otherTemp.equals("")) cheFer.setChemicalFertilizers(new ChemicalFertilizers(1000000, "Otro"));
                result.add(cheFer);
//                cheFer = null;
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    public void save(ChemicalFertilizations event) {
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

    public void delete(ChemicalFertilizations event) {
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
