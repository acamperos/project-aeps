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
import org.aepscolombia.platform.models.entity.Fields;
import org.aepscolombia.platform.models.entity.FieldsProducers;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase LotsDao
 *
 * Contiene los metodos para interactuar con la tabla Fields de la base de datos (BD)
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
        
        sql += "select l.id_fie, l.id_farm_fie, l.contract_type_fie, l.name_fie, l.altitude_fie,";
        sql += " l.latitude_fie, l.longitude_fie, l.area_fie, l.status, lp.id_producer_fie_pro,";
        sql += " e.name_ent, e.document_number_ent, e.document_type_ent, f.name_far";
        sql += " from fields l";
        sql += " inner join log_entities le on le.id_object_log_ent=l.id_fie and le.table_log_ent='fields' and le.action_type_log_ent='C'";   
        sql += " inner join fields_producers lp on lp.id_field_fie_pro=l.id_fie";
        sql += " left join farms f on f.id_far=l.id_farm_fie";
//        sql += " left join municipios m on (m.id_mun=f.id_municipio_fin)";
//        sql += " left join fincas_productores fp on fp.id_finca_fin_pro=f.id_fin"; 
        sql += " inner join producers p on p.id_pro=lp.id_producer_fie_pro"; 
        sql += " inner join entities e on e.id_ent=p.id_entity_pro"; 
        sql += " where l.status=1 and f.status=1";
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
            
            for (Object[] data : events) {
//                System.out.println(data);
                HashMap temp = new HashMap();
                temp.put("id_lot", data[0]);
                temp.put("id_farm", data[1]);
                temp.put("type_lot", data[2]);             
                temp.put("name_lot", data[3]);                
                temp.put("altitude_lot", data[4]);
                temp.put("latitude_lot", data[5]);
                temp.put("length_lot", data[6]);                
                temp.put("area_lot", data[7]);                
                temp.put("status", data[8]);
                temp.put("id_producer", data[9]);
                temp.put("name_producer", data[10]);
                temp.put("no_doc_pro", data[11]);       
                temp.put("type_doc_pro", data[12]);       
                temp.put("name_farm", data[13]);       
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
//         ft.name_fie_typ
        sql += "select l.id_fie, l.id_farm_fie, l.contract_type_fie, l.name_fie, l.altitude_fie,";
        sql += " l.latitude_fie, l.longitude_fie, l.area_fie, l.status, l.id_project_fie, e.name_ent, f.name_far, ft.name_fie_typ";
        sql += " from fields l";
        sql += " inner join fields_producers lp on lp.id_field_fie_pro=l.id_fie";
        sql += " inner join field_types ft on ft.id_fie_typ=l.contract_type_fie";
        sql += " left join farms f on f.id_far=l.id_farm_fie";
        
//        if (args.containsKey("idFin")) {            
//            sqlAdd += " and f.estado_fin=1 and l.id_finca_lot="+args.get("idFin");
//        } else if (args.containsKey("idLote")) {            
//            sqlAdd += " and l.id_lote_lot_pro="+args.get("idLote");
//        }
//        sql += " left join lotes_productores lp on lp.id_lote_lot_pro=l.id_lot";
//        sql += " inner join municipios m on (m.id_mun=f.id_municipio_fin)";
//        sql += " inner join departamentos dep on (dep.id_dep=m.id_departamento_mun)";
        sql += " inner join log_entities le on le.id_object_log_ent=l.id_fie and le.table_log_ent='fields' and le.action_type_log_ent='C'";   
//        sql += " inner join fincas_productores fp on fp.id_finca_fin_pro=f.id_fin"; 
        sql += " inner join producers p on p.id_pro=lp.id_producer_fie_pro"; 
        sql += " inner join entities e on e.id_ent=p.id_entity_pro"; 
        sql += " where l.status=1";
//        sql += " where l.status=1 and l.contract_type_fie!=1";
        
//        if (args.containsKey("idFarm")) {
//            sqlAdd += " left join farms f on f.id_far=l.id_farm_fie";
//            sqlAdd += " and l.id_farm_fie="+args.get("idFarm");
//        }
//        if (args.containsKey("idProducer")) {
//            sqlAdd += " and le.id_entity_log_ent="+args.get("idProducer");
//        }        
        
        
        if (args.containsKey("idEntUser")) {
            sqlAdd += " and le.id_entity_log_ent="+args.get("idEntUser");
        }
        
        
        if (args.containsKey("search_field")) {
            String valIdent = String.valueOf(args.get("search_field"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) { 
                sql += " and ((e.name_ent like '%"+valIdent+"%')";
                sql += " or (f.name_far like '%"+valIdent+"%')";
                try {
                    String dateAsign = new SimpleDateFormat("yyyy-dd-MM").format(new Date(valIdent));
//                    sql += " or (r.fecha_ras like '%"+dateAsign+"%')";
                } catch (IllegalArgumentException ex) {
//                    Logger.getLogger(RastasDao.class.getName()).log(Level.SEVERE, null, ex);
                }
                sql += " or (l.contract_type_fie='"+valIdent+"')";
                sql += " or (l.altitude_fie like '%"+valIdent+"%')";
                sql += " or (l.area_fie like '%"+valIdent+"%')";
                sql += " or (l.latitude_fie like '%"+valIdent+"%')";
                sql += " or (l.longitude_fie like '%"+valIdent+"%'))";
            }
        }
        
        if (args.containsKey("name_producer_lot")) {
            String valIdent = String.valueOf(args.get("name_producer_lot"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and e.name_ent like '%"+args.get("name_producer_lot")+"%'";
        }
        if (args.containsKey("name_property_lot")) {
            String valIdent = String.valueOf(args.get("name_property_lot"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and f.name_far like '%"+args.get("name_property_lot")+"%'";
        }
        if (args.containsKey("typeLot")) {
            String valIdent = String.valueOf(args.get("typeLot"));
            if(!valIdent.equals("0") && !valIdent.equals("-1") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and l.contract_type_fie="+args.get("typeLot");
        }
        if (args.containsKey("altitude_lot")) {
            String valIdent = String.valueOf(args.get("altitude_lot"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and l.altitude_fie like '%"+args.get("altitude_lot")+"%'";
        }
        if (args.containsKey("area_lot")) {
            String valIdent = String.valueOf(args.get("area_lot"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and l.area_fie like '%"+args.get("area_lot")+"%'";
        }        
        if (args.containsKey("latitude_property")) {
            String valIdent = String.valueOf(args.get("latitude_property"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and l.latitude_fie like '%"+args.get("latitude_property")+"%'";
        }        
        if (args.containsKey("length_property")) {
            String valIdent = String.valueOf(args.get("length_property"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and l.longitude_fie like '%"+args.get("length_property")+"%'";
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
//        sql += " order by e.name_ent ASC";
        sql += " order by l.name_fie ASC";
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
                temp.put("id_lot", data[0]);
                temp.put("id_farm", data[1]);
                temp.put("type_lot", data[2]);             
                temp.put("name_lot", data[3]);                
                temp.put("altitude_lot", data[4]);
                temp.put("latitude_lot", data[5]);
                temp.put("length_lot", data[6]);                
                temp.put("area_lot", data[7]);                
                temp.put("status", data[8]);
                temp.put("name_producer", data[10]);
                temp.put("name_far", data[11]);
                temp.put("name_type_lot", data[12]);
                result.add(temp);
            }
//            System.out.println("values->"+result);
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
            String hql  = "FROM Fields E WHERE E.idFie = :id_fie";
            Query query = session.createQuery(hql);
            query.setParameter("id_fie", id);
            event = (Fields)query.uniqueResult();
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
    
    public FieldsProducers checkFieldProducer(Integer idField, Integer idProducer) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        FieldsProducers event = null;
        Transaction tx = null;
        String sql = "";

        sql += "select fp.id_field_fie_pro, fp.id_producer_fie_pro";
//        sql += "select fp.id_field_fie_pro, fp.id_producer_fie_pro, fp.contract_type_fie_pro";
        
//        sql += "select usr.id_usr, usr.name_user_usr, usr.password_usr, usr.cod_validation_usr, usr.status";
        sql += " from fields_producers fp";
        sql += " where fp.id_field_fie_pro="+idField;
        sql += " and fp.id_producer_fie_pro="+idProducer;
//        System.out.println("sql->"+sql);
        
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("usr", FieldsProducers.class);
            event = (FieldsProducers)query.uniqueResult();
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
