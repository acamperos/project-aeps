package org.aepscolombia.platform.models.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//import org.aepscolombia.plataforma.models.dao.IEventoDao;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.Fields;
import org.aepscolombia.platform.util.HibernateUtil;
import org.hibernate.Criteria;

/**
 * Clase LotsDao
 *
 * Contiene los metodos para interactuar con la tabla Lots de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class FieldsDao 
{    
    
    public HashMap findById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        
        List<Object[]> events = null;
        Transaction tx = null;
        HashMap result = new HashMap();
        
        String sql = "";
        String sqlAdd = "";
        
        sql += "select l.id_fie, l.id_farm_fie, lp.contract_type_fie_pro, l.name_fie, l.altitude_fie,";
        sql += " l.latitude_fie, l.longitude_fie, l.area_fie, l.status_fie, lp.id_producer_fie_pro,";
        sql += " e.nombre_ent, f.name_far";
        sql += " from fields l";
        sql += " inner join log_entities le on le.id_object_log_ent=l.id_fie and le.table_log_ent='fields'";   
        sql += " inner join fields_producers lp on lp.id_field_fie_pro=l.id_fie";
        sql += " left join farms f on f.id_far=l.id_farm_fie";
//        sql += " left join municipios m on (m.id_mun=f.id_municipio_fin)";
//        sql += " left join fincas_productores fp on fp.id_finca_fin_pro=f.id_fin"; 
        sql += " inner join producers p on p.id_pro=lp.id_producer_fie_pro"; 
        sql += " inner join entities e on e.id_ent=p.id_entity_pro"; 
        sql += " where l.status_fie=1 and f.status_far=1";
//        sql += " lp.tipo_contrato_lot_pro!=1";
        // if ($identProductor!='' ) sql += "where";
//        sql += sqlAdd;
        if (id!=null) {
            sql += " and l.id_fie="+id;
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
                temp.put("id_lot", datos[0]);
                temp.put("id_farm", datos[1]);
                temp.put("type_lot", datos[2]);             
                temp.put("name_lot", datos[3]);                
                temp.put("altitude_lot", datos[4]);
                temp.put("latitude_lot", datos[5]);
                temp.put("length_lot", datos[6]);                
                temp.put("area_lot", datos[7]);                
                temp.put("status", datos[8]);
                temp.put("id_producer", datos[9]);
                temp.put("name_producer", datos[10]);
                temp.put("name_farm", datos[11]);       
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
    
    public List<Fields> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Fields> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from Fields");
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
        String sqlAdd = "";     
        
        
//        sql  = "select l.* from lotes l";	 
//		sql += " inner join log_entidades le on le.id_objeto_log_ent=l.id_lot and le.tabla_log_ent='lotes'";
//// 		sql += " where l.estado=1";
//		// if ($identProductor!='' ) sql += "where";
//		if ($identFinca!='') {
//      sql += " left join fincas f on f.ID_FINCA=l.FINCA_ID_FINCA_LOT";
//			sqlAdd += " and l.FINCA_ID_FINCA_LOT=".$identFinca;			
//		} elseif ($identProductor!='') {
//      sql += " left join lotes_productores lp on lp.ID_LOTE_LO_PRO=l.ID_LOT";
//      sqlAdd += " and lp.ID_PRODUCTOR_LO_PRO=".$identProductor;      
//    }
//		sql += sqlAdd;
        
        sql += "select l.id_fie, l.id_farm_fie, lp.contract_type_fie_pro, l.name_fie, l.altitude_fie,";
        sql += " l.latitude_fie, l.longitude_fie, l.area_fie, l.status_fie, l.id_project_fie";
        sql += " from fields l";
        sql += " inner join fields_producers lp on lp.id_field_fie_pro=l.id_fie";
//        sql += " left join fincas f on f.id_fin=l.id_finca_lot";
//        if (args.containsKey("idFin")) {            
//            sqlAdd += " and f.estado_fin=1 and l.id_finca_lot="+args.get("idFin");
//        } else if (args.containsKey("idLote")) {            
//            sqlAdd += " and l.id_lote_lot_pro="+args.get("idLote");
//        }
//        sql += " left join lotes_productores lp on lp.id_lote_lot_pro=l.id_lot";
//        sql += " inner join municipios m on (m.id_mun=f.id_municipio_fin)";
//        sql += " inner join departamentos dep on (dep.id_dep=m.id_departamento_mun)";
        sql += " inner join log_entities le on le.id_log_ent=l.id_fie and le.table_log_ent='fields'";   
//        sql += " inner join fincas_productores fp on fp.id_finca_fin_pro=f.id_fin"; 
        sql += " inner join producers p on p.id_pro=lp.id_producer_fie_pro"; 
        sql += " inner join entities e on e.id_ent=p.id_entity_pro"; 
        sql += " where l.status_fie=1 and lp.contract_type_fie_pro!=1";
        if (args.containsKey("idEntUser")) {
            sql += " and le.id_entity_log_ent="+args.get("idEntUser");
        }
        // if ($identProductor!='' ) sql += "where";
        sql += sqlAdd;

//        if (args.containsKey("idFin")) {
//            sql += " and f.id_fin="+args.get("idFin");
//        }
//        args.get("countTotal");
        
        int valIni = Integer.parseInt(String.valueOf(args.get("pageNow")));
//        int valIni = Integer.parseInt(args.get("pageNow"))*Integer.parseInt((String)args.get("maxResults"));
        int maxResults = Integer.parseInt(String.valueOf(args.get("maxResults")));
        if(valIni!=1){
            valIni = (valIni-1)*maxResults+1;
        }    
//        sql += " order by e.nombre_ent ASC";
        sql += " order by l.nombre_lot ASC";
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
                temp.put("id_lot", datos[0]);
                temp.put("id_farm", datos[1]);
                temp.put("type_lot", datos[2]);             
                temp.put("name_lot", datos[3]);                
                temp.put("altitude_lot", datos[4]);
                temp.put("latitude_lot", datos[5]);
                temp.put("length_lot", datos[6]);                
                temp.put("area_lot", datos[7]);                
                temp.put("status", datos[8]);
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
    
    public Fields objectById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        Fields event = null;
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            event = (Fields) session.load(Fields.class, id);
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
    
    public void saveLotPro(Integer idFin, Integer idPro, Integer typeLot) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Transaction tx = null;
//        HibernateUtil.getInstanceConnection();

        try {
            tx = session.beginTransaction();
            String query = "insert into fields_producers values (?,?,?)";

            // Ejecutamos la query y obtenemos el resultado.
            PreparedStatement stmt;
            stmt = HibernateUtil.getInstanceConnection().prepareStatement(query);
            stmt.setInt(1, idFin);
            stmt.setInt(2, idPro);
            stmt.setInt(3, typeLot);
            
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

    public void save(Fields event) {
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

    public void delete(Fields event) {
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
