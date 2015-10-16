package org.aepscolombia.platform.models.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.aepscolombia.platform.controllers.ActionField;
//import org.aepscolombia.plataforma.models.dao.IEventoDao;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.Fields;
import org.aepscolombia.platform.models.entity.FieldsProducers;
import org.aepscolombia.platform.models.entity.LogEntities;
import org.aepscolombia.platform.models.entityservices.SfGuardUser;
import org.aepscolombia.platform.util.GlobalFunctions;
import org.aepscolombia.platform.util.HibernateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * Clase FieldsDao
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
        sql += " e.name_ent, e.document_number_ent, e.document_type_ent, f.name_far,";
        sql += " mun.name_mun, d.name_dep, f.name_commune_far";
        sql += " from fields l";
        sql += " inner join log_entities le on le.id_object_log_ent=l.id_fie and le.table_log_ent='fields' and le.action_type_log_ent='C'";   
        sql += " inner join fields_producers lp on lp.id_field_fie_pro=l.id_fie";        
        sql += " left join farms f on f.id_far=l.id_farm_fie";
        sql += " left join municipalities mun on mun.id_mun=f.id_municipipality_far";
        sql += " left join departments d on d.id_dep=mun.id_department_mun";
        sql += " inner join producers p on p.id_pro=lp.id_producer_fie_pro"; 
        sql += " inner join entities e on e.id_ent=p.id_entity_pro"; 
        sql += " where l.status=1 and f.status=1 and e.status=1";
//        sql += " lp.tipo_contrato_lot_pro!=1";
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
                temp.put("name_mun", data[14]);       
                temp.put("name_dep", data[15]);       
                temp.put("name_commune", data[16]);       
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
        String entType = String.valueOf(args.get("entType"));
        
//        sql  = "select l.* from lotes l";	 
//		sql += " inner join log_entidades le on le.id_objeto_log_ent=l.id_lot and le.tabla_log_ent='lotes'";
// 		sql += " where l.estado=1";
//		if ($identProductor!='' ) sql += "where";
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
        sql += " l.latitude_fie, l.longitude_fie, l.area_fie, l.status, l.id_project_fie, e.name_ent, f.name_far, ft.name_fie_typ,";
        sql += " e.entity_type_ent, m.name_mun,";
        if (entType.equals("3")) {
            sql += " le.date_log_ent, entLe.name_ent as nameAgro";
        } else {
            sql += " le.date_log_ent";
        }
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
        sql += " left join municipalities m on (m.id_mun=f.id_municipipality_far)";
//        sql += " inner join departamentos dep on (dep.id_dep=m.id_departamento_mun)";
        sql += " inner join log_entities le on le.id_object_log_ent=l.id_fie and le.table_log_ent='fields' and le.action_type_log_ent='C'";   
        if (entType.equals("3")) {
            sql += " inner join entities entLe on (le.id_entity_log_ent=entLe.id_ent)"; 
            sql += " inner join extension_agents ext on (ext.id_entity_ext_age=entLe.id_ent)";
            sql += " inner join agents_association agAsc on (agAsc.id_agent_age_asc=ext.id_ext_age)";
            sql += " inner join association ass on (ass.id_asc=agAsc.id_asso_age_asc)";
        }
//        sql += " inner join fincas_productores fp on fp.id_finca_fin_pro=f.id_fin"; 
        sql += " inner join producers p on p.id_pro=lp.id_producer_fie_pro"; 
        sql += " inner join entities e on e.id_ent=p.id_entity_pro"; 
        sql += " where l.status=1 and f.status=1 and e.status=1";
//        sql += " where l.status=1 and l.contract_type_fie!=1";
        
//        if (args.containsKey("idFarm")) {
//            sqlAdd += " left join farms f on f.id_far=l.id_farm_fie";
//            sqlAdd += " and l.id_farm_fie="+args.get("idFarm");
//        }
//        if (args.containsKey("idProducer")) {
//            sqlAdd += " and le.id_entity_log_ent="+args.get("idProducer");
//        }        
        
                
        if (!entType.equals("3") && args.containsKey("idEntUser")) {
            sqlAdd += " and le.id_entity_log_ent="+args.get("idEntUser");
        } else {
            String selAll  = String.valueOf(args.get("selAll"));
            if (selAll.equals("true")) {
                sqlAdd += " and ass.id_entity_asc="+args.get("idEntUser");
            } else {
                sqlAdd += " and le.id_entity_log_ent in ("+args.get("selItem")+")";
            }
        }
        
        
        if (args.containsKey("search_field")) {
            String valIdent = String.valueOf(args.get("search_field"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) { 
                sql += " and ((e.name_ent like '%"+valIdent+"%')";
                sql += " or (f.name_far like '%"+valIdent+"%')";
                sql += " or (l.name_fie like '%"+valIdent+"%')";
                try {
                    String dateAsign = new SimpleDateFormat("yyyy-MM-dd").format(new Date(valIdent));
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
        sql += sqlAdd;

//        if (args.containsKey("idFin")) {
//            sql += " and f.id_fin="+args.get("idFin");
//        }
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
                temp.put("typeEnt", data[13]);
                temp.put("city", data[14]);
                temp.put("dateLog", data[15]);
                if (entType.equals("3")) {
                    temp.put("nameAgro", data[16]);
                }
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
    
    public static Integer countData(HashMap args) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Object[] events = null;
        Transaction tx = null;
        Integer result = 0;
        String entType = String.valueOf(args.get("entType"));
        
        String sql = "";     
        String sqlAdd = "";     
        sql += "select count(l.id_fie), l.id_farm_fie";
        sql += " from fields l";
        sql += " left join farms f on f.id_far=l.id_farm_fie";
        sql += " inner join farms_producers fp on fp.id_farm_far_pro=f.id_far"; 
        sql += " inner join producers p on p.id_pro=fp.id_producer_far_pro"; 
        sql += " inner join entities e on e.id_ent=p.id_entity_pro";        
        sql += " inner join log_entities le on le.id_object_log_ent=l.id_fie and le.table_log_ent='fields' and le.action_type_log_ent='C'";   
        if (entType.equals("3")) {
            sql += " inner join entities entLe on (le.id_entity_log_ent=entLe.id_ent)"; 
            sql += " inner join extension_agents ext on (ext.id_entity_ext_age=entLe.id_ent)";
            sql += " inner join agents_association agAsc on (agAsc.id_agent_age_asc=ext.id_ext_age)";
            sql += " inner join association ass on (ass.id_asc=agAsc.id_asso_age_asc)";
        }
        sql += " where l.status=1 and f.status=1 and e.status=1";
        if (!entType.equals("3") && args.containsKey("idEntUser")) {
            sqlAdd += " and le.id_entity_log_ent="+args.get("idEntUser");
        } else {
            sqlAdd += " and ass.id_entity_asc="+args.get("idEntUser");
        }
        sql += sqlAdd;
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

        sql += "select fp.id_field_fie_pro, fp.id_producer_fie_pro, fp.created_by";
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
    
    public void getFields(HashMap args, String fileName) 
    {        
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Object[]> events = null;
        Transaction tx = null;  

        String sql = "";
        String entType = String.valueOf(args.get("entType"));
        
        sql += "select n.id_fie as ID_LOTE, f.id_far as ID_FINCA, p.id_pro as ID_PROD, e.name_ent as USUARIO, ent.name_ent as PRODUCTOR, concat(ent.document_type_ent, ':', ent.document_number_ent) as CEDULA, n.name_fie as LOTE,";
        sql += "ft.name_fie_typ as CONTRATO, n.latitude_fie as LATITUD, n.longitude_fie as LONGITUD, n.altitude_fie as ALTITUD, n.area_fie as AREA, m.name_mun as MUNICIPIO, d.name_dep as DEPARTAMENTO";
        sql += " from fields n";
        sql += " inner join field_types ft on ft.id_fie_typ=n.contract_type_fie";
        sql += " inner join fields_producers lp on lp.id_field_fie_pro = n.id_fie";
        sql += " inner join farms f on n.id_farm_fie=f.id_far";
        sql += " inner join farms_producers fp on f.id_far = fp.id_farm_far_pro";
        sql += " inner join producers p on p.id_pro = fp.id_producer_far_pro";
        sql += " inner join entities ent on ent.id_ent = p.id_entity_pro";
        sql += " inner join municipalities m on m.id_mun  = f.id_municipipality_far";
        sql += " inner join departments d on d.id_dep=m.id_department_mun";
        sql += " left join log_entities le on le.id_object_log_ent = n.id_fie AND le.table_log_ent = 'fields'";
        sql += " inner join entities e on le.id_entity_log_ent = e.id_ent";
        if (entType.equals("3")) {
            sql += " inner join extension_agents ext on (ext.id_entity_ext_age=e.id_ent)";
            sql += " inner join agents_association agAsc on (agAsc.id_agent_age_asc=ext.id_ext_age)";
            sql += " inner join association ass on (ass.id_asc=agAsc.id_asso_age_asc)";
        }
        sql += " where le.action_type_log_ent = 'C'";
        sql += " and n.status=1 and f.status=1 and ent.status=1";
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
            sql += " inner join agents_association agAsc on (agAsc.id_agent_age_asc=ext.id_ext_age)";
            sql += " inner join association ass on (ass.id_asc=agAsc.id_asso_age_asc)";
        }
        sql += "	where le.action_type_log_ent = 'D' AND le.table_log_ent = 'fields'";
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
            File myFileTemp = new File("fieldsTemp.xls");
            FileInputStream fis = new FileInputStream(myFileTemp);
            
            HSSFWorkbook workbook = new HSSFWorkbook(fis);            
            HSSFSheet sheet = workbook.getSheetAt(0);
            
            Map<String, Object[]> dataSheet = new TreeMap<String, Object[]>();
//            CSVWriter writer = new CSVWriter(new FileWriter(fileName), ';');
            Query query  = session.createSQLQuery(sql);
            events = query.list();  
        
            Object[] val = {
                "ID_LOTE",
                "ID_FINCA",
                "ID_PROD",
                "USUARIO",
                "PRODUCTOR",
                "CEDULA",
                "LOTE",
                "CONTRATO",
                "LATITUD",
                "LONGITUD",
                "ALTITUD",
                "AREA",
                "MUNICIPIO",
                "DEPARTAMENTO"
            };
//            writer.writeNext(val);
            dataSheet.put("1", val);
            Integer cont = 2;
            for (Object[] data : events) {
                Object[] valTemp = {
                    data[0],
                    data[1],
                    data[2],
                    data[3],
                    data[4],
                    data[5],
                    data[6],
                    data[7],
                    data[8],
                    data[9],
                    data[10],
                    data[11],
                    data[12],
                    data[13]
                };
//                writer.writeNext(valTemp);
                dataSheet.put(""+cont, valTemp);
                cont++;
            }
//            writer.close();
            Set<String> keyset = dataSheet.keySet();
            int rownum = 0;
            for (String key : keyset)
            {
                Row row = sheet.createRow(rownum++);
                Object [] objArr = dataSheet.get(key);
                int cellnum = 0;
                for (Object obj : objArr)
                {
                    Cell cell = row.createCell(cellnum++);
                    if (obj instanceof String) {
                        cell.setCellValue((String) obj);
                    } else if (obj instanceof Boolean) {
                        cell.setCellValue((Boolean) obj);
                    } else if (obj instanceof Timestamp) {
                        cell.setCellValue((Timestamp) obj);
                    } else if (obj instanceof Date) {
                        cell.setCellValue((Date) obj);
                    } else if (obj instanceof Double) {
                        cell.setCellValue((Double) obj);
                    } else if (obj instanceof Integer) {
                        cell.setCellValue((Integer) obj);
                    } 
                }
            }
            File myFile = new File(fileName);
            if (!myFile.exists()) myFile.createNewFile();
            FileOutputStream out = new FileOutputStream(myFile);
            workbook.write(out);
            out.close();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } catch (IOException ex) {
//            Logger.getLogger(FieldsDao.class.getName()).log(Level.SEVERE, null, ex);
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
        
        sql += "select l.id_fie, l.id_farm_fie, l.contract_type_fie, l.name_fie, l.altitude_fie,";
        sql += " l.latitude_fie, l.longitude_fie, l.area_fie, l.status, l.id_project_fie, ent.name_ent, f.name_far,";
        sql += " ent.entity_type_ent, m.name_mun,";
        sql += " le.date_log_ent, e.email_ent as emailUser";
        sql += " from fields l";
        sql += " inner join fields_producers lp on lp.id_field_fie_pro = l.id_fie";
        sql += " inner join farms f on l.id_farm_fie=f.id_far";
        sql += " inner join farms_producers fp on f.id_far = fp.id_farm_far_pro";
        sql += " inner join producers p on p.id_pro = fp.id_producer_far_pro";
        sql += " inner join entities ent on ent.id_ent = p.id_entity_pro";
        sql += " inner join municipalities m on m.id_mun  = f.id_municipipality_far";
        sql += " inner join departments d on d.id_dep=m.id_department_mun";
        sql += " inner join log_entities le on le.id_object_log_ent = l.id_fie AND le.table_log_ent = 'fields'";
        sql += " inner join entities e on le.id_entity_log_ent = e.id_ent";
        sql += " where le.id_entity_log_ent in (3,4,5,6,8,200,201,202,665,706,707,708,709,710,711,712,713,714,715,823)";
        sql += " and le.action_type_log_ent = 'C'";
        sql += " and l.status=1 and f.status=1 and ent.status=1";
        sql += " and le.id_object_log_ent not in (select le.id_object_log_ent from log_entities le where le.id_entity_log_ent in (3,4,5,6,8,200,201,202,665,706,707,708,709,710,711,712,713,714,715,823) and le.action_type_log_ent = 'D' AND le.table_log_ent = 'fields')";

        
        try {
            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql);            
            events = query.list();         
            
            for (Object[] data : events) {
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
                temp.put("typeEnt", data[12]);
                temp.put("city", data[13]);
                temp.put("dateLog", data[14]);       
                
                String emailUser = String.valueOf(data[15]);
                
                SfGuardUserDao sfDao = new SfGuardUserDao();
                SfGuardUser sfUser   = sfDao.getUserByLogin(null, emailUser, "");
                Integer idUserMobile = null;
                if (sfUser!=null) {
                    idUserMobile = sfUser.getId().intValue();
                }
                
                HashMap valInfo = new HashMap();
                valInfo.put("fieldId", temp.get("id_lot"));
                valInfo.put("farmId", temp.get("id_farm"));
                valInfo.put("nameFarm", temp.get("name_far"));
                valInfo.put("typeField", temp.get("type_lot"));
                valInfo.put("nameField", temp.get("name_lot"));
                valInfo.put("lat", temp.get("latitude_lot"));
                valInfo.put("lng", temp.get("length_lot"));
                valInfo.put("alt", temp.get("altitude_lot"));
                valInfo.put("areaField", temp.get("area_lot"));
                valInfo.put("userMobileId", idUserMobile);      

                BasicDBObject queryMongo = new BasicDBObject();
                queryMongo.put("InsertedId", ""+temp.get("id_lot"));
                queryMongo.put("form_id", "5");

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
                BasicDBObject jsonField = null;
                jsonField          = GlobalFunctions.generateJSONField(valInfo);

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
            ex.printStackTrace();
            System.out.println("Error ingresando al MongoDB");
        } finally {
            session.close();
        }
    }
    
    public void deleteFieldsMongo(Integer idFarm) 
    {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        
        List<Object[]> events = null;
        Transaction tx = null;
        
        String sql = "";
        
        sql += "select f.id_fie, f.name_fie";
        sql += " from fields f";
        sql += " where f.status=1";
        if (idFarm!=null) {
            sql += " and f.id_farm_fie="+idFarm;
        }        
        
        try {
            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql);            
            events = query.list();         
            
            MongoClient mongo = null;
            mongo = new MongoClient("localhost", 27017);
            
            for (Object[] data : events) {
                HashMap temp = new HashMap();
                temp.put("id_fie", data[0]);
                temp.put("name_fie", data[1]);          
                
                BasicDBObject queryMongo = new BasicDBObject();
                queryMongo.put("InsertedId", ""+temp.get("id_fie"));
                queryMongo.put("form_id", "5");
                
                DB db = mongo.getDB("ciat");
                DBCollection col = db.getCollection("log_form_records");
                WriteResult result = null;

                System.out.println("borro mongo");
                result = col.remove(queryMongo);

                if (result.getError()!=null) {
                    throw new HibernateException("");
                }                
                
                Integer idField = Integer.parseInt(String.valueOf(temp.get("id_fie")));
                
                ProductionEventsDao cropDao = new ProductionEventsDao();
                cropDao.deleteCropsMongo(idField);
                
                RastasDao rasDao = new RastasDao();
                rasDao.deleteRastasMongo(idField);
                
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
    
    public String deleteAllFields(String valSel, Integer idEntSystem) 
    {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        List<Fields> events = null;
        Transaction tx = null;
        String sql = "";
        String state = "failure";         

        sql += "select f.id_fie, f.id_farm_fie, f.name_fie, f.altitude_fie, f.latitude_fie, f.longitude_fie, f.area_fie, f.measure_unit_fie,"; 	
        sql += "f.pests_control_fie, f.diseases_control_fie, f.status, f.id_project_fie, f.contract_type_fie, f.created_by";
        sql += " from fields f";
        if (!valSel.equals("")) sql += " where f.status=1 and f.id_fie in ("+valSel+")";
//        System.out.println("sql=>"+sql);          
        
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("f", Fields.class);
            events = query.list();
            MongoClient mongo = new MongoClient("localhost", 27017);
            for (Fields fie : events) {
//                System.out.println("fieId->"+fie.getIdFie());
                fie.setStatus(false);     
                session.saveOrUpdate(fie);

                LogEntities log = new LogEntities();
                log.setIdLogEnt(null);
                log.setIdEntityLogEnt(idEntSystem);
                log.setIdObjectLogEnt(fie.getIdFie());
                log.setTableLogEnt("fields");
                log.setDateLogEnt(new Date());
                log.setActionTypeLogEnt("D");
                session.saveOrUpdate(log);

                BasicDBObject queryMongo = new BasicDBObject();
                queryMongo.put("InsertedId", ""+fie.getIdFie());
                queryMongo.put("form_id", "5");

                DB db = mongo.getDB("ciat");
                DBCollection col = db.getCollection("log_form_records");
                WriteResult result = null;

                System.out.println("borro mongo");
                result = col.remove(queryMongo);

                if (result.getError()!=null) {
                    throw new HibernateException("");
                }

                ProductionEventsDao cropDao = new ProductionEventsDao();
                cropDao.deleteCropsMongo(fie.getIdFie());

                RastasDao rasDao = new RastasDao();
                rasDao.deleteRastasMongo(fie.getIdFie());
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
    
    public List getLastCrops(Integer idField) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Object[]> eventsTotal = null;
        List<Object[]> events = null;
        Transaction tx = null;
        List<HashMap> result = new ArrayList<HashMap>();
        
        String sql = "";     
        String sqlAdd = "";     
        sql += "select l.id_fie, f.name_cro_typ";
        sql += " from production_events ev";
        sql += " inner join fields l on ev.id_field_pro_eve=l.id_fie";
        sql += " inner join crops_types f on f.id_cro_typ=ev.former_crop_pro_eve";       
        sql += " where l.status=1 and ev.status=1";
        sql += " and l.id_fie="+idField;
        sql += " order by l.name_fie ASC";
        try {
            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql);
            events = query.list();     
            
            for (Object[] data : events) {
                HashMap temp = new HashMap();
                temp.put("id_lot", data[0]);
                temp.put("crop_name", data[1]);
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
    
}
