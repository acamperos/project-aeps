package org.aepscolombia.platform.models.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.aepscolombia.platform.models.entity.Entities;
//import org.aepscolombia.plataforma.models.dao.IEventoDao;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.ChemicalElements;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase ChemicalElementsDao
 *
 * Contiene los metodos para interactuar con la tabla ChemicalElements de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ChemicalElementsDao 
{    
    
    public HashMap findById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        
        List<Object[]> events = null;
        Transaction tx = null;
        HashMap result = new HashMap();
        
        String sql = "";
        String sqlAdd = "";    
        
        sql += "select pe.id_pro_eve, l.id_farm_fie, l.name_fie, pe.id_crop_type_pro_eve, pe.expected_production_pro_eve, pe.former_crop_pro_eve, pe.draining_pro_eve, pe.status";
        sql += " from production_events pe";
        sql += " inner join log_entities le on le.id_object_log_ent=pe.id_pro_eve and le.table_log_ent='production_events' and le.action_type_log_ent='C'";   
        sql += " inner join fields l on l.id_fie=pe.id_field_pro_eve";
        sql += " where l.status=1 and pe.status=1";
        if (id!=null) {
            sql += " and pe.id_pro_eve="+id;
        }
//        args.get("countTotal");
//        events.toArray();
//        System.out.println("sql->"+sql);        
        try {
            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql);            
            events = query.list();         
            
            for (Object[] data : events) {
//                System.out.println(data);
                HashMap temp = new HashMap();
                temp.put("idCrop", data[0]);
                temp.put("idField", data[1]);
                temp.put("nameField", data[2]);             
                temp.put("typeCrop", data[3]);                
                temp.put("performObj", data[4]);
                temp.put("lastCrop", data[5]);
                temp.put("drainPlot", data[6]);                
                temp.put("status", data[8]);
                result = (temp);
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
    
    public List<ChemicalElements> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<ChemicalElements> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from ChemicalElements");
//            Query query = session.createQuery("from ChemicalElements WHERE countryCheEle.acronymIdCo = :country_code");
//            query.setParameter("country_code", countryCode);
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

    public List<ChemicalElements> findByParams(Integer idFert) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Object[]> events = null;
        Transaction tx = null;
        List<ChemicalElements> result = new ArrayList<ChemicalElements>();
        
        String sql = "";     
        String sqlAdd = "";     
                      
        sql += "select p.id_che_ele, p.name_che_ele";
        sql += " from chemical_elements p"; 
        sql += " where p.status_che_ele=1";     
		sql += " order by p.id_che_ele ASC";
        
//        args.get("countTotal");
        
//        int valIni = Integer.parseInt(String.valueOf(args.get("pageNow")));
//        int maxResults = Integer.parseInt(String.valueOf(args.get("maxResults")));
//        if(valIni!=1){
//            valIni = (valIni-1)*maxResults+1;
//        }    
//        events.toArray();
//        System.out.println("sql->"+sql);
        try {
            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql);
//            System.out.println("sql->"+query.list().size());
            events = query.list(); 

            for (Object[] data : events) {
                ChemicalElements temp = new ChemicalElements();
                Integer valId  = Integer.parseInt(String.valueOf(data[0]));
                Double valChe  = getValuePercentage(idFert, valId);
                temp.setIdCheEle(valId);
                temp.setNameCheEle(String.valueOf(data[1]));
//                System.out.println("valName=>"+temp.getNameCheEle());
//                temp.setStatusCheEle(true);
                temp.setValueCheEle(valChe);
                result.add(temp);
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
    
    public Double getValuePercentage(Integer idFert, Integer idEle) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Object[]> events = null;
        Transaction tx = null;
        
        String sql = "";    
        Double perEle=null;
        
        if (idFert!=null && idFert>0) {
            sql += "select p.id_che_ele, p.name_che_ele, eq.percentage_che_fer_com";
            sql += " from chemical_elements p";                  
            sql += " left join chemical_fertilizer_composition eq on eq.id_elements_che_fer_com=p.id_che_ele";    
            sql += " left join chemical_fertilizers fq on fq.id_che_fer=eq.id_chemical_fertilizer_che_fer_com";
            sql += " left join chemical_fertilizers_country ferCo on ferCo.id_selfer_che_fer_co=fq.id_che_fer";   
            sql += " left join chemical_fertilizations ep on ep.id_product_che_fer=fq.id_che_fer";    
            sql += " where p.status_che_ele=1 and ep.status=1";     
            sql += " and ep.id_che_fer="+idFert;                
            sql += " and p.id_che_ele="+idEle;     
            sql += " order by p.id_che_ele ASC";        
        
            try {
                tx = session.beginTransaction();
                Query query  = session.createSQLQuery(sql);
                events = query.list(); 

                for (Object[] data : events) {
                    perEle = Double.parseDouble(String.valueOf(data[2]));
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
        }
        return perEle;
    }    
    
    public ChemicalElements objectById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        ChemicalElements event = null;
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            String hql  = "FROM ChemicalElements E WHERE E.idIrr = :id_irr";
            Query query = session.createQuery(hql);
            query.setParameter("id_irr", id);
            event = (ChemicalElements)query.uniqueResult();
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

    public void save(ChemicalElements event) {
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

    public void delete(ChemicalElements event) {
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
