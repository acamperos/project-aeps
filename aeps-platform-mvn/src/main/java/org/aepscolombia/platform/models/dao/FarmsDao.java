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
import org.aepscolombia.platform.models.entity.FarmsProducers;
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
        sql += "f.georef_far, f.latitude_far, f.longitude_far, f.altitude_far, f.name_commune_far, m.name_mun, m.id_mun, m.id_departament_mun, f.status_far";
        sql += " from farms f";
        sql += " inner join municipalities m on (m.id_mun=f.id_municipipality_far)";
        sql += " inner join log_entities le on le.id_object_log_ent=f.id_far and le.table_log_ent='farms' and le.action_type_log_ent='C'";   
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
            
            for (Object[] data : events) {
//                System.out.println(data);
                HashMap temp = new HashMap();
                temp.put("id_producer", data[0]);
                temp.put("id_farm", data[1]);
                temp.put("name_producer", data[2]);
                temp.put("name_farm", data[3]);
                temp.put("dir_farm", data[4]);                
                temp.put("latitude_farm", data[8]);
                temp.put("length_farm", data[9]);
                temp.put("altitude_farm", data[10]);                
                temp.put("lane_farm", data[11]);
                temp.put("id_mun", data[13]);
                temp.put("id_dep", data[14]);        
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
    
    public FarmsProducers checkFarmProducer(Integer idFarm, Integer idProducer) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        FarmsProducers event = null;
        Transaction tx = null;
        String sql = "";

        sql += "select fp.id_farm_far_pro, fp.id_producer_far_pro ";
        
//        sql += "select usr.id_usr, usr.name_user_usr, usr.password_usr, usr.cod_validation_usr, usr.status_usr";
        sql += " from farms_producers fp";
        sql += " where fp.id_farm_far_pro="+idFarm;
        sql += " and fp.id_producer_far_pro="+idProducer;
//        System.out.println("sql->"+sql);
        
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("usr", FarmsProducers.class);
            event = (FarmsProducers)query.uniqueResult();
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
        sql += " from farms f";
        sql += " inner join municipalities m on (m.id_mun=f.id_municipipality_far)";
        sql += " inner join departments dep on (dep.id_dep=m.id_departament_mun)";
        sql += " inner join log_entities le on le.id_object_log_ent=f.id_far and le.table_log_ent='farms' and le.action_type_log_ent='C'";   
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
        
        if (args.containsKey("name_producer")) {
            String valIdent = String.valueOf(args.get("name_producer"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and e.name_ent like '%"+args.get("name_producer")+"%'";
        }
        if (args.containsKey("name_property")) {
            String valIdent = String.valueOf(args.get("name_property"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and f.name_far like '%"+args.get("name_property")+"%'";
        }
        if (args.containsKey("depFar")) {
            String valIdent = String.valueOf(args.get("depFar"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and m.id_departament_mun="+args.get("depFar");
        }
        if (args.containsKey("cityFar")) {
            String valIdent = String.valueOf(args.get("cityFar"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and m.id_mun="+args.get("cityFar");
        }
        if (args.containsKey("lane_property")) {
            String valIdent = String.valueOf(args.get("lane_property"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and f.name_commune_far like '%"+args.get("lane_property")+"%'";
        }
        if (args.containsKey("altitude_property")) {
            String valIdent = String.valueOf(args.get("altitude_property"));
            if(!valIdent.equals("") && !valIdent.equals("null")) sql += " and f.altitude_far="+args.get("altitude_property");
        }
        if (args.containsKey("latitude_property")) {
            String valIdent = String.valueOf(args.get("latitude_property"));
            if(!valIdent.equals("") && !valIdent.equals("null")) sql += " and f.latitude_far="+args.get("latitude_property");
        }
        if (args.containsKey("length_property")) {
            String valIdent = String.valueOf(args.get("length_property"));
            if(!valIdent.equals("") && !valIdent.equals("null")) sql += " and f.longitude_far="+args.get("length_property");
        }
//        args.get("countTotal");
        
        int valIni = Integer.parseInt(String.valueOf(args.get("pageNow")));
//        int valIni = Integer.parseInt(args.get("pageNow"))*Integer.parseInt((String)args.get("maxResults"));
        int maxResults = Integer.parseInt(String.valueOf(args.get("maxResults")));
        if(valIni!=1){
            valIni = (valIni-1)*maxResults+1;
        }    
//        sql += " order by e.name_ent ASC";
        sql += " order by f.name_far ASC";
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
            if(query.list().size()>maxResults) {
                query.setFirstResult(valIni);
                query.setMaxResults(maxResults);      
            }
            events = query.list();     
            
            for (Object[] data : events) {
//                System.out.println(data);
                HashMap temp = new HashMap();
                temp.put("id_producer", data[0]);
                temp.put("id_farm", data[1]);
                temp.put("name_producer", data[2]);
                temp.put("name_farm", data[3]);
                temp.put("dir_farm", data[4]);                
                temp.put("latitude_farm", data[8]);
                temp.put("length_farm", data[9]);
                temp.put("altitude_farm", data[10]);                
                temp.put("lane_farm", data[11]);
                temp.put("id_mun", data[12]);
                temp.put("name_mun", data[13]);
                temp.put("name_dep", data[15]);                
                temp.put("status", data[16]);
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
            String hql  = "FROM Farms E WHERE E.idFar = :id_far";
            Query query = session.createQuery(hql);
            query.setParameter("id_far", id);
            event = (Farms)query.uniqueResult();
//            event = (Farms) session.load(Farms.class, id);
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
