package org.aepscolombia.platform.models.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.aepscolombia.platform.models.entity.Entities;
import org.aepscolombia.platform.models.entity.Municipalities;
//import org.aepscolombia.plataforma.models.dao.IEventoDao;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.Farms;
import org.aepscolombia.platform.util.HibernateUtil;
import org.hibernate.Criteria;

/**
 * Clase FarmsDao
 *
 * Contiene los metodos para interactuar con la tabla Farms de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class FarmsDao 
{    

    public HashMap findById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        
        List<Object[]> events = null;
        Transaction tx = null;
        HashMap result = new HashMap();
        
        String sql = "";
        sql += "select fp.id_producer_far_pro, f.id_far, e.name_ent, f.name_far, f.address_far, f.phone_far, f.id_district_far,";
        sql += "f.georef_far, f.latitude_far, f.longitude_far, f.altitude_far, f.name_commune_far, m.name_mun, m.id_mun, m.id_departament_mun, f.estado_fin";
        sql += " from fincas f";
        sql += " inner join municipalities m on (m.id_mun=f.id_municipipality_far)";
        sql += " inner join log_entities le on le.id_object_log_ent=f.id_far and le.table_log_ent='farms'";   
        sql += " inner join farms_producers fp on fp.id_farm_far_pro=f.id_far"; 
        sql += " inner join producers p on p.id_pro=fp.id_producer_far_pro"; 
        sql += " inner join entities e on e.id_ent=p.id_entity_pro"; 
        sql += " where f.status_far=1";
        if (id!=null) {
            sql += " and f.id_far="+id;
        }
//        args.get("countTotal");
//        events.toArray();
//        System.out.println("sql->"+sql);        
        try {
            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql);            
            events = query.list();         
            
            for (Object[] datos : events) {
//                System.out.println(datos);
                HashMap temp = new HashMap();
                temp.put("id_producer", datos[0]);
                temp.put("id_farm", datos[1]);
                temp.put("name_producer", datos[2]);
                temp.put("name_farm", datos[3]);
                temp.put("dir_farm", datos[4]);                
                temp.put("latitude_farm", datos[8]);
                temp.put("length_farm", datos[9]);
                temp.put("altitude_farm", datos[10]);                
                temp.put("lane_farm", datos[11]);
                temp.put("id_mun", datos[13]);
                temp.put("id_dep", datos[14]);        
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
    
    public List<Farms> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Farms> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from Farms");
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
        
        String sql = "";       
        sql += "select fp.id_producer_far_pro, f.id_far, e.name_ent, f.name_far, f.address_far, f.phone_far, f.id_district_far,";
        sql += "f.georef_far, f.latitude_far, f.longitude_far, f.altitude_far, f.name_commune_far, m.id_mun, m.name_mun, dep.id_dep, dep.name_dep, f.status_far";
        sql += " from fincas f";
        sql += " inner join municipalities m on (m.id_mun=f.id_municipipality_far)";
        sql += " inner join departments dep on (dep.id_dep=m.id_departament_mun)";
        sql += " inner join log_entities le on le.id_object_log_ent=f.id_far and le.table_log_ent='farms'";   
        sql += " inner join farms_producers fp on fp.id_farm_far_pro=f.id_far"; 
        sql += " inner join producers p on p.id_pro=fp.id_producer_far_pro"; 
        sql += " inner join entities e on e.id_ent=p.id_entity_pro"; 
        sql += " where f.status_far=1";
        if (args.containsKey("idEntUser")) {
            sql += " and le.id_entity_log_ent="+args.get("idEntUser");
        }
        // if ($identProductor!='' ) sql .= "where";
        if (args.containsKey("idProducer")) {
            sql += " and fp.id_producer_far_pro="+args.get("idProducer");
        }

        if (args.containsKey("idFar")) {
            sql += " and f.id_far="+args.get("idFar");
        }
//        args.get("countTotal");
        
        int valIni = Integer.parseInt(String.valueOf(args.get("pageNow")));
//        int valIni = Integer.parseInt(args.get("pageNow"))*Integer.parseInt((String)args.get("maxResults"));
        int maxResults = Integer.parseInt(String.valueOf(args.get("maxResults")));
        if(valIni!=1){
            valIni = (valIni-1)*maxResults+1;
        }    
        sql += " order by e.name_ent ASC";
//        events.toArray();
//        System.out.println("sql->"+sql);
        try {
            tx = session.beginTransaction();
//            Query query = session.createSQLQuery(sql);
            Query query  = session.createSQLQuery(sql);
//            System.out.println("sql->"+query.list().size());
            HashMap tempTotal = new HashMap();
            tempTotal.put("countTotal", query.list().size());
            result.add(tempTotal);
            query.setFirstResult(valIni);
            query.setMaxResults(maxResults);      
            events = query.list();     
            
            for (Object[] datos : events) {
//                System.out.println(datos);
                HashMap temp = new HashMap();
                temp.put("id_producer", datos[0]);
                temp.put("id_farm", datos[1]);
                temp.put("name_producer", datos[2]);
                temp.put("name_farm", datos[3]);
                temp.put("dir_farm", datos[4]);                
                temp.put("latitude_farm", datos[8]);
                temp.put("length_farm", datos[9]);
                temp.put("altitude_farm", datos[10]);                
                temp.put("lane_farm", datos[11]);
                temp.put("id_mun", datos[12]);
                temp.put("name_mun", datos[13]);
                temp.put("name_dep", datos[15]);                
                temp.put("status", datos[16]);
                result.add(temp);
            }
//            System.out.println(result);
//            for (HashMap datos : result) {
//                System.out.println(datos.get("id_productor")+" "+datos.get("id_entidad")+" "+datos.get("cedula"));
//            }
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
    
    public Farms objectById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        Farms event = null;
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            event = (Farms) session.load(Farms.class, id);
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
    
    public void saveFarPro(Integer idFin, Integer idPro) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Transaction tx = null;
//        HibernateUtil.getInstanceConnection();

        try {
            tx = session.beginTransaction();
            String query = "insert into farms_producers values (?,?)";

            // Ejecutamos la query y obtenemos el resultado.
            PreparedStatement stmt;
            stmt = HibernateUtil.getInstanceConnection().prepareStatement(query);
            stmt.setInt(1, idFin);
            stmt.setInt(2, idPro);
            
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

    public void save(Farms event) {
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

    public void delete(Farms event) {
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
