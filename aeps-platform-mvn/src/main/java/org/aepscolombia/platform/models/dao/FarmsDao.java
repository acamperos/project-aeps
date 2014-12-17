package org.aepscolombia.platform.models.dao;

import au.com.bytecode.opencsv.CSVWriter;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.aepscolombia.platform.controllers.ActionField;
//import org.aepscolombia.plataforma.models.dao.IEventoDao;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.Farms;
import org.aepscolombia.platform.models.entity.FarmsProducers;
import org.aepscolombia.platform.models.entity.FarmsProducersId;
import org.aepscolombia.platform.models.entity.LogEntities;
import org.aepscolombia.platform.models.entity.Producers;
import org.aepscolombia.platform.models.entityservices.SfGuardUser;
import org.aepscolombia.platform.util.GlobalFunctions;
import org.aepscolombia.platform.util.HibernateUtil;

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
        sql += " f.georef_far, f.latitude_far, f.longitude_far, f.altitude_far, f.name_commune_far, m.name_mun, m.id_mun, m.id_department_mun, f.status,";
        sql += " le.date_log_ent";
        sql += " from farms f";
        sql += " inner join municipalities m on (m.id_mun=f.id_municipipality_far)";
        sql += " inner join log_entities le on le.id_object_log_ent=f.id_far and le.table_log_ent='farms' and le.action_type_log_ent='C'";   
        sql += " inner join farms_producers fp on fp.id_farm_far_pro=f.id_far"; 
        sql += " inner join producers p on p.id_pro=fp.id_producer_far_pro"; 
        sql += " inner join entities e on e.id_ent=p.id_entity_pro"; 
        sql += " where f.status=1 and e.status=1";
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
                temp.put("dateLog", data[16]);        
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
        
//        sql += "select usr.id_usr, usr.name_user_usr, usr.password_usr, usr.cod_validation_usr, usr.status";
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
        String entType = String.valueOf(args.get("entType"));
        sql += "select fp.id_producer_far_pro, f.id_far, e.name_ent, f.name_far, f.address_far, f.phone_far, f.id_district_far,";
        sql += "f.georef_far, f.latitude_far, f.longitude_far, f.altitude_far, f.name_commune_far, m.id_mun, m.name_mun, dep.id_dep, dep.name_dep, f.status, ";
        sql += " e.entity_type_ent,";
        if (entType.equals("3")) {
            sql += " le.date_log_ent, entLe.name_ent as nameAgro";
        } else {
            sql += " le.date_log_ent";
        }
        sql += " from farms f";
        sql += " inner join municipalities m on (m.id_mun=f.id_municipipality_far)";
        sql += " inner join departments dep on (dep.id_dep=m.id_department_mun)";
        sql += " inner join log_entities le on le.id_object_log_ent=f.id_far and le.table_log_ent='farms' and le.action_type_log_ent='C'";   
        if (entType.equals("3")) {
            sql += " inner join entities entLe on (le.id_entity_log_ent=entLe.id_ent)"; 
            sql += " inner join extension_agents ext on (ext.id_entity_ext_age=entLe.id_ent)";
            sql += " inner join association ass on (ass.id_asc=ext.id_asso_ext_age)";
        }
        sql += " inner join farms_producers fp on fp.id_farm_far_pro=f.id_far"; 
        sql += " inner join producers p on p.id_pro=fp.id_producer_far_pro"; 
        sql += " inner join entities e on e.id_ent=p.id_entity_pro"; 
        sql += " where f.status=1 and e.status=1";
        
        if (!entType.equals("3") && args.containsKey("idEntUser")) {
            sql += " and le.id_entity_log_ent="+args.get("idEntUser");
        } else {
            String selAll  = String.valueOf(args.get("selAll"));
            if (selAll.equals("true")) {
                sql += " and ass.id_entity_asc="+args.get("idEntUser");
            } else {
                sql += " and le.id_entity_log_ent in ("+args.get("selItem")+")";
            }
        }
        
        if (args.containsKey("search_farm")) {
            String valIdent = String.valueOf(args.get("search_farm"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) { 
                sql += " and ((e.name_ent like '%"+valIdent+"%')";
                sql += " or (f.name_far like '%"+valIdent+"%')";
//                Date asign = new Date(valIdent);
//                sql += " or (r.fecha_ras like '%"+asign+"%')";
                try {
                    String dateAsign = new SimpleDateFormat("yyyy-dd-MM").format(new Date(valIdent));
//                    sql += " or (r.fecha_ras like '%"+dateAsign+"%')";
                } catch (IllegalArgumentException ex) {
//                    Logger.getLogger(RastasDao.class.getName()).log(Level.SEVERE, null, ex);
                }
                sql += " or (m.id_department_mun='"+valIdent+"')";
                sql += " or (m.id_mun='"+valIdent+"')";
                sql += " or (f.name_commune_far like '%"+valIdent+"%')";
//                sql += " or (r.terreno_circundante_ras like '%"+valIdent+"%')";
//                sql += " or (r.posicion_perfil_ras like '%"+valIdent+"%')";
//                sql += " or (r.ph_ras like '%"+valIdent+"%')";
                sql += " or (f.altitude_far like '%"+valIdent+"%')";
                sql += " or (f.latitude_far like '%"+valIdent+"%')";
                sql += " or (f.longitude_far like '%"+valIdent+"%'))";
            }
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
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and m.id_department_mun="+args.get("depFar");
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
            if(!valIdent.equals("") && !valIdent.equals("null")) sql += " and f.altitude_far like '%"+args.get("altitude_property")+"%'";
        }
        if (args.containsKey("latitude_property")) {
            String valIdent = String.valueOf(args.get("latitude_property"));
            if(!valIdent.equals("") && !valIdent.equals("null")) sql += " and f.latitude_far like '%"+args.get("latitude_property")+"%'";
        }
        if (args.containsKey("length_property")) {
            String valIdent = String.valueOf(args.get("length_property"));
            if(!valIdent.equals("") && !valIdent.equals("null")) sql += " and f.longitude_far like '%"+args.get("length_property")+"%'";
        }
//        args.get("countTotal");
        
        int valIni = Integer.parseInt(String.valueOf(args.get("pageNow")));
//        int valIni = Integer.parseInt(args.get("pageNow"))*Integer.parseInt((String)args.get("maxResults"));
        int maxResults = Integer.parseInt(String.valueOf(args.get("maxResults")));
        if(valIni!=1){
//            valIni = ((valIni-1)*maxResults)+1;
            valIni = ((valIni-1)*maxResults);
        } else {
            valIni = 0;
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
                temp.put("typeEnt", data[17]);
                temp.put("dateLog", data[18]);
                if (entType.equals("3")) {
                    temp.put("nameAgro", data[19]);
                }
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
    
    public static Integer countData(HashMap args) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Object[] events = null;
        Transaction tx  = null;
        Integer result  = 0;
        String entType = String.valueOf(args.get("entType"));
        
        String sql = "";       
        sql += "select count(f.id_far), f.name_far";
        sql += " from farms f";
        sql += " inner join farms_producers fp on fp.id_farm_far_pro=f.id_far"; 
        sql += " inner join producers p on p.id_pro=fp.id_producer_far_pro"; 
        sql += " inner join entities e on e.id_ent=p.id_entity_pro"; 
        sql += " inner join log_entities le on le.id_object_log_ent=f.id_far and le.table_log_ent='farms' and le.action_type_log_ent='C'";   
        if (entType.equals("3")) {
            sql += " inner join entities entLe on (le.id_entity_log_ent=entLe.id_ent)"; 
            sql += " inner join extension_agents ext on (ext.id_entity_ext_age=entLe.id_ent)";
            sql += " inner join association ass on (ass.id_asc=ext.id_asso_ext_age)";
        }
        sql += " where f.status=1 and e.status=1";
        if (!entType.equals("3") && args.containsKey("idEntUser")) {
            sql += " and le.id_entity_log_ent="+args.get("idEntUser");
        } else {
            sql += " and ass.id_entity_asc="+args.get("idEntUser");
        }
        try {
            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql);
            events = (Object[])query.uniqueResult();
            result = Integer.parseInt(String.valueOf(events[0]));
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
//            String query = "insert into farms_producers values (?,?)";
            
            
//            String hql  = "FROM Entities E WHERE E.idEnt = :id_ent";
//            Query query = session.createQuery(hql);
//            query.setParameter("id_ent", id);
            
            FarmsProducersId farmPro = new FarmsProducersId(idFin,idPro);
            Producers prod = new Producers(idPro);
            
            Farms far = new Farms(idFin);
            
            FarmsProducers farPro = new FarmsProducers(farmPro, prod, far);

            session.saveOrUpdate(farPro);
            
            // Ejecutamos la query y obtenemos el resultado.
//            PreparedStatement stmt;
//            stmt = HibernateUtil.getInstanceConnection().prepareStatement(query);
//            stmt.setInt(1, idFin);
//            stmt.setInt(2, idPro);
            
//            stmt.executeUpdate();
//            stmt.close();
//            HibernateUtil.closeConnection();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
//        } catch (SQLException ex) {
//            if (tx != null) {
//                tx.rollback();
//            }
//            ex.printStackTrace();
//        }
        
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
    
    public void getFarms(HashMap args, String fileName) 
    {        
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Object[]> events = null;
        Transaction tx = null;  

        String sql = "";
        String entType = String.valueOf(args.get("entType"));
        
        sql += "select f.id_far as ID_FINCA, p.id_pro as ID_PROD, e.name_ent as USUARIO, ent.name_ent as PRODUCTOR, concat(ent.document_type_ent, ':', ent.document_number_ent) as CEDULA, f.name_far as FINCA,";
        sql += "f.address_far as DIRECCION, f.latitude_far as LATITUD, f.longitude_far as LONGITUD, f.altitude_far as ALTITUD, m.name_mun as MUNICIPIO";
        sql += " from farms f";
        sql += " inner join farms_producers fp on f.id_far = fp.id_farm_far_pro";
        sql += " inner join producers p on p.id_pro = fp.id_producer_far_pro";
        sql += " inner join entities ent on ent.id_ent = p.id_entity_pro";
        sql += " inner join municipalities m on m.id_mun  = f.id_municipipality_far";
        sql += " inner join departments d on d.id_dep=m.id_department_mun";
        sql += " left join log_entities le on le.id_object_log_ent = f.id_far AND le.table_log_ent = 'farms'";
        sql += " inner join entities e on le.id_entity_log_ent = e.ID_ENT";
        if (entType.equals("3")) {
            sql += " inner join extension_agents ext on (ext.id_entity_ext_age=e.id_ent)";
            sql += " inner join association ass on (ass.id_asc=ext.id_asso_ext_age)";
        }
        sql += " where le.action_type_log_ent = 'C'";
        sql += " and f.status=1 and ent.status=1";
        if (!entType.equals("3") && args.containsKey("idEntUser")) {
            sql += " and le.id_entity_log_ent="+args.get("idEntUser");
        } else {
            String selAll  = String.valueOf(args.get("selAll"));
            if (selAll.equals("true")) {
                sql += " and ass.id_entity_asc="+args.get("idEntUser");
            } else {
                sql += " and le.id_entity_log_ent in ("+args.get("selItem")+")";
            }
        }
        sql += " and le.id_object_log_ent not in (";
        sql += "	select le.id_object_log_ent from log_entities le ";
        if (entType.equals("3")) {
            sql += "	inner join entities entLe on (le.id_entity_log_ent=entLe.id_ent)";
            sql += "	inner join extension_agents ext on (ext.id_entity_ext_age=entLe.id_ent)";
            sql += "	inner join association ass on (ass.id_asc=ext.id_asso_ext_age)";
        }
        sql += "	where le.action_type_log_ent = 'D' AND le.table_log_ent = 'farms'";
        if (!entType.equals("3") && args.containsKey("idEntUser")) {
            sql += " and le.id_entity_log_ent="+args.get("idEntUser");
        } else {
            String selAll  = String.valueOf(args.get("selAll"));
            if (selAll.equals("true")) {
                sql += " and ass.id_entity_asc="+args.get("idEntUser");
            } else {
                sql += " and le.id_entity_log_ent in ("+args.get("selItem")+")";
            }
        }
        sql += ")";
        sql += " order by e.name_ent";

//        System.out.println("sql=>"+sql);
        try {
            tx = session.beginTransaction();
            CSVWriter writer = new CSVWriter(new FileWriter(fileName), ';');
            Query query  = session.createSQLQuery(sql);
            events = query.list();  
        
            String[] val = {
                "ID_FINCA",
                "ID_PROD",                
                "USUARIO",
                "PRODUCTOR",
                "CEDULA",
                "FINCA",
                "DIRECCION",
                "LATITUD",
                "LONGITUD",
                "ALTITUD",
                "MUNICIPIO"
            };
            writer.writeNext(val);
            for (Object[] data : events) {
                String[] valTemp = {
                    String.valueOf(data[0]),
                    String.valueOf(data[1]),
                    String.valueOf(data[2]),
                    String.valueOf(data[3]),
                    String.valueOf(data[4]),
                    String.valueOf(data[5]),
                    String.valueOf(data[6]),
                    String.valueOf(data[7]),
                    String.valueOf(data[8]),
                    String.valueOf(data[9]),
                    String.valueOf(data[10])
                };
                writer.writeNext(valTemp);
            }
            writer.close();
//            stmt   = con.prepareStatement(sql);     
//            result = stmt.executeQuery();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } catch (IOException ex) {
//            Logger.getLogger(FarmsDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.close();
        }
//        return result;
    }
    
    public void setInfoMongo() 
    {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        
        List<Object[]> events = null;
        Transaction tx = null;
        
        String sql = "";
        
        sql += "select fp.id_producer_far_pro, f.id_far, ent.name_ent, f.name_far, f.address_far, f.phone_far, f.id_district_far,";
        sql += "f.georef_far, f.latitude_far, f.longitude_far, f.altitude_far, f.name_commune_far, m.id_mun, m.name_mun, dep.id_dep, dep.name_dep, f.status, ";
        sql += " ent.entity_type_ent, e.email_ent,";
        sql += " le.date_log_ent";
        sql += " from farms f";
        sql += " inner join farms_producers fp on f.id_far = fp.id_farm_far_pro";
        sql += " inner join producers p on p.id_pro = fp.id_producer_far_pro";
        sql += " inner join entities ent on ent.id_ent = p.id_entity_pro";
        sql += " inner join municipalities m on m.id_mun  = f.id_municipipality_far";
        sql += " inner join departments dep on dep.id_dep=m.id_department_mun";
        sql += " inner join log_entities le on le.id_object_log_ent = f.id_far AND le.table_log_ent = 'farms'";
        sql += " inner join entities e on le.id_entity_log_ent = e.ID_ENT";
        sql += " where le.id_entity_log_ent in (3,4,5,6,8,200,201,202,665,706,707,708,709,710,711,712,713,714,715,823)";
        sql += " and le.action_type_log_ent = 'C'";
        sql += " and f.status=1 and ent.status=1";
        sql += " and le.id_object_log_ent not in (select le.id_object_log_ent from log_entities le where le.id_entity_log_ent in (3,4,5,6,8,200,201,202,665,706,707,708,709,710,711,712,713,714,715,823) and le.action_type_log_ent = 'D' AND le.table_log_ent = 'farms')";

        
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
                temp.put("id_mun", data[12]);
                temp.put("id_dep", data[14]);        
                temp.put("dateLog", data[19]);           
                
                String emailUser = String.valueOf(data[18]);
                
                SfGuardUserDao sfDao = new SfGuardUserDao();
                SfGuardUser sfUser   = sfDao.getUserByLogin(emailUser, "");
                Integer idUserMobile = null;
                if (sfUser!=null) {
                    idUserMobile = sfUser.getId().intValue();
                }
                
                HashMap valInfo = new HashMap();
                valInfo.put("farmId", temp.get("id_farm"));
                valInfo.put("nameFarm", temp.get("name_farm"));
                valInfo.put("prodId", temp.get("id_producer"));
                valInfo.put("nameProd", temp.get("name_producer"));
                valInfo.put("district", temp.get("lane_farm"));
                valInfo.put("address", temp.get("dir_farm"));
                valInfo.put("lat", temp.get("latitude_farm"));
                valInfo.put("lng", temp.get("length_farm"));
                valInfo.put("alt", temp.get("altitude_farm"));
                valInfo.put("department", temp.get("id_dep"));
                valInfo.put("municipality", temp.get("id_mun"));
                valInfo.put("userMobileId", idUserMobile); 

                BasicDBObject queryMongo = new BasicDBObject();
                queryMongo.put("InsertedId", ""+temp.get("id_farm"));
                queryMongo.put("form_id", "3");

                MongoClient mongo = null;
                try {
                    mongo = new MongoClient("localhost", 27017);
                } catch (UnknownHostException ex) {
                    Logger.getLogger(ActionField.class.getName()).log(Level.SEVERE, null, ex);
                }
                DB db = mongo.getDB("ciat");
                DBCollection col = db.getCollection("log_form_records");

                DBCursor cursor    = col.find(queryMongo);
                WriteResult result = null;
                BasicDBObject jsonField = GlobalFunctions.generateJSONFarm(valInfo);

                if (cursor.count()>0) {
                    System.out.println("actualizo mongo");
                    result = col.update(queryMongo, jsonField);
                } else {
                    System.out.println("inserto mongo");
                    result = col.insert(jsonField);
                }

                if (result.getError()!=null) {
                    throw new HibernateException("");
                }

                mongo.close();
                
            }   
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Error ingresando al MongoDB");
        } finally {
            session.close();
        }
    }
    
    
    public void deleteFarmsMongo(Integer idPro) 
    {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        
        List<Object[]> events = null;
        Transaction tx = null;
        
        String sql = "";
        
        sql += "select f.id_far, f.name_far";
        sql += " from farms f";
        sql += " inner join farms_producers fp on f.id_far = fp.id_farm_far_pro";
        sql += " where f.status=1";
        if (idPro!=null) {
            sql += " and fp.id_producer_far_pro="+idPro;
        }        
        
        try {
            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql);            
            events = query.list();         
            
            MongoClient mongo = null;
            mongo = new MongoClient("localhost", 27017);
            
            for (Object[] data : events) {
//                System.out.println(data);
                HashMap temp = new HashMap();
                temp.put("id_farm", data[0]);
                temp.put("name_farm", data[1]);          
                
                BasicDBObject queryMongo = new BasicDBObject();
                queryMongo.put("InsertedId", ""+temp.get("id_farm"));
                queryMongo.put("form_id", "3");
                
                DB db = mongo.getDB("ciat");
                DBCollection col = db.getCollection("log_form_records");
                WriteResult result = null;

                System.out.println("borro mongo");
                result = col.remove(queryMongo);

                if (result.getError()!=null) {
                    throw new HibernateException("");
                }                
                
                Integer idFarm = Integer.parseInt(String.valueOf(temp.get("id_farm")));
                FieldsDao fieDao = new FieldsDao();
                fieDao.deleteFieldsMongo(idFarm);
                
            }   
            
            mongo.close();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ActionField.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            System.out.println("Error ingresando al MongoDB");
        } finally {
            session.close();
        }
    }
    
    public String deleteAllFarms(String valSel, Integer idEntSystem) 
    {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        List<Farms> events = null;
        Transaction tx = null;
        String sql = "";
        String state = "failure";

        sql += "select f.id_far, f.name_far, f.address_far, f.phone_far, f.id_municipipality_far, f.id_district_far, f.georef_far,"; 	
        sql += "f.latitude_far, f.longitude_far, f.altitude_far, f.area_far, f.unit_area_far, f.id_project_far, f.name_commune_far, f.status, f.created_by";
        sql += " from farms f";
        if (!valSel.equals("")) sql += " where f.status=1 and f.id_far in ("+valSel+")";
//        System.out.println("sql=>"+sql);          
        
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("f", Farms.class);
            events = query.list();
            MongoClient mongo = new MongoClient("localhost", 27017);
            for (Farms farm : events) {
//                System.out.println("farId->"+farm.getIdFar());
                farm.setStatus(false);     
                session.saveOrUpdate(farm);

                LogEntities log = new LogEntities();
                log.setIdLogEnt(null);
                log.setIdEntityLogEnt(idEntSystem);
                log.setIdObjectLogEnt(farm.getIdFar());
                log.setTableLogEnt("farms");
                log.setDateLogEnt(new Date());
                log.setActionTypeLogEnt("D");
                session.saveOrUpdate(log);

                BasicDBObject queryMongo = new BasicDBObject();
                queryMongo.put("InsertedId", ""+farm.getIdFar());
                queryMongo.put("form_id", "3");

                DB db = mongo.getDB("ciat");
                DBCollection col = db.getCollection("log_form_records");
                WriteResult result = null;

                System.out.println("borro mongo");
                result = col.remove(queryMongo);

                if (result.getError()!=null) {
                    throw new HibernateException("");
                }

                FieldsDao fieDao = new FieldsDao();
                fieDao.deleteFieldsMongo(farm.getIdFar());
            }
            mongo.close();
            tx.commit();
            state = "success";
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ActionField.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.close();
        } 
        return state;
    }
    
}
