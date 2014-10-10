package org.aepscolombia.platform.models.dao;

import au.com.bytecode.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.aepscolombia.platform.models.entity.HorizontesRasta;
//import org.aepscolombia.plataforma.models.dao.IEventoDao;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.Rastas;
import org.aepscolombia.platform.util.GlobalFunctions;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase RastasDao
 *
 * Contiene los metodos para interactuar con la tabla Lots de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class RastasDao 
{    
    
    public HashMap findById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        
        List<Object[]> events = null;
        Transaction tx = null;
        HashMap result = new HashMap();
        
        String sql = "";
        String sqlAdd = "";
        
        
        sql += "select r.id_ras, r.id_lote_ras, l.name_fie, e.name_ent, f.name_far, r.fecha_ras, r.numero_cajuela_ras, r.altitud_ras, r.latitud_ras, r.longitud_ras,";
        sql += " r.pendiente_terreno_ras, r.terreno_circundante_ras, r.posicion_perfil_ras, r.numero_capas_ras,";
        sql += " r.ph_ras, r.carbonatos_ras, r.profundidad_carbonatos_ras, r.piedras_superficie_ras, r.rocas_superficie_ras,";
        sql += " r.piedras_perfil_ras, r.rocas_perfil_ras, r.horizonte_pedrogoso_rocoso_ras, r.profundidad_horizonte_pedregoso_ras,";
        sql += " r.profundidad_primeras_piedras_ras, r.espesor_horizonte_pedregoso_ras, r.capas_endurecidas_ras, r.prufundidad_capas_ras,";
        sql += " r.espesor_capa_endurecida_ras, r.moteados_ras, r.profundidad_moteados_ras, r.moteados_mas_70cm_ras, r.estructura_ras,";
        sql += " r.erosion_ras, r.moho_ras, r.costras_duras_ras, r.exposicion_sol_ras, r.costras_blancas_ras, r.costras_negras_ras,";
        sql += " r.region_seca_ras, r.raices_vivas_ras, r.profundidad_raices_ras, r.plantas_pequenas_ras, r.hojarasca_ras,";
        sql += " r.suelo_negro_blando_ras, r.cuchillo_primer_horizonte_ras, r.cerca_rios_quebradas_ras, r.recubrimiento_vegetal_ras";
        
//        sql += "select l.id_fie, l.id_farm_fie, l.contract_type_fie, l.name_fie, l.altitude_fie,";
//        sql += " l.latitude_fie, l.longitude_fie, l.area_fie, l.status, lp.id_producer_fie_pro,";
//        sql += " e.name_ent, f.name_far";
        sql += " from rastas r";
        sql += " inner join log_entities le on le.id_object_log_ent=r.id_ras and le.table_log_ent='rastas' and le.action_type_log_ent='C'";   
        sql += " inner join fields l on r.id_lote_ras=l.id_fie";
        sql += " inner join fields_producers lp on lp.id_field_fie_pro=l.id_fie";
        sql += " left join farms f on f.id_far=l.id_farm_fie";
        sql += " inner join producers p on p.id_pro=lp.id_producer_fie_pro"; 
        sql += " inner join entities e on e.id_ent=p.id_entity_pro"; 
        sql += " where l.status=1 and f.status=1";
        sql += " and r.status=1 and e.status=1";
//        sql += " lp.tipo_contrato_lot_pro!=1";
        // if ($identProductor!='' ) sql += "where";
//        sql += sqlAdd;
        if (id!=null) {
            sql += " and r.id_ras="+id;
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
                temp.put("name_farm", data[11]);       
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
    
    public List<Rastas> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Rastas> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from Rastas");
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
        
        sql += "select r.id_ras, r.id_lote_ras, l.name_fie, e.name_ent, f.name_far, r.fecha_ras, r.numero_cajuela_ras, r.altitud_ras, r.latitud_ras, r.longitud_ras,";
        sql += " r.pendiente_terreno_ras, r.terreno_circundante_ras, r.posicion_perfil_ras, r.numero_capas_ras,";
        sql += " r.ph_ras, r.carbonatos_ras, r.profundidad_carbonatos_ras, r.piedras_superficie_ras, r.rocas_superficie_ras,";
        sql += " r.piedras_perfil_ras, r.rocas_perfil_ras, r.horizonte_pedrogoso_rocoso_ras, r.profundidad_horizonte_pedregoso_ras,";
        sql += " r.profundidad_primeras_piedras_ras, r.espesor_horizonte_pedregoso_ras, r.capas_endurecidas_ras, r.prufundidad_capas_ras,";
        sql += " r.espesor_capa_endurecida_ras, r.moteados_ras, r.profundidad_moteados_ras, r.moteados_mas_70cm_ras, r.estructura_ras,";
        sql += " r.erosion_ras, r.moho_ras, r.costras_duras_ras, r.exposicion_sol_ras, r.costras_blancas_ras, r.costras_negras_ras,";
        sql += " r.region_seca_ras, r.raices_vivas_ras, r.profundidad_raices_ras, r.plantas_pequenas_ras, r.hojarasca_ras,";
        sql += " r.suelo_negro_blando_ras, r.cuchillo_primer_horizonte_ras, r.cerca_rios_quebradas_ras, r.recubrimiento_vegetal_ras, r.status,";               
        if (entType.equals("3")) {
            sql += " le.date_log_ent, entLe.name_ent as nameAgro";
        } else {
            sql += " le.date_log_ent";
        }
        sql += " from rastas r";
        sql += " inner join log_entities le on le.id_object_log_ent=r.id_ras and le.table_log_ent='rastas' and le.action_type_log_ent='C'";   
        if (entType.equals("3")) {
            sql += " inner join entities entLe on (le.id_entity_log_ent=entLe.id_ent)"; 
            sql += " inner join extension_agents ext on (ext.id_entity_ext_age=entLe.id_ent)";
            sql += " inner join association ass on (ass.id_asc=ext.id_asso_ext_age)";
        }
        sql += " inner join fields l on r.id_lote_ras=l.id_fie";
        sql += " inner join fields_producers lp on lp.id_field_fie_pro=l.id_fie";
        sql += " left join farms f on f.id_far=l.id_farm_fie";
        sql += " inner join producers p on p.id_pro=lp.id_producer_fie_pro"; 
        sql += " inner join entities e on e.id_ent=p.id_entity_pro"; 
        sql += " where l.status=1 and f.status=1";
        sql += " and r.status=1 and e.status=1";    
        
                
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
        
        if (args.containsKey("search_soil")) {
            String valIdent = String.valueOf(args.get("search_soil"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) { 
                sql += " and ((r.numero_cajuela_ras like '%"+valIdent+"%')";
//                Date asign = new Date(valIdent);
//                sql += " or (r.fecha_ras like '%"+asign+"%')";
                try {
                    String dateAsign = new SimpleDateFormat("yyyy-dd-MM").format(new Date(valIdent));
                    sql += " or (r.fecha_ras like '%"+dateAsign+"%')";
//                    sql += " or (r.fecha_ras like '%"+dateAsign+"%')";
                } catch (IllegalArgumentException ex) {
//                    Logger.getLogger(RastasDao.class.getName()).log(Level.SEVERE, null, ex);
                }
                sql += " or (r.pendiente_terreno_ras like '%"+valIdent+"%')";
                sql += " or (r.altitud_ras like '%"+valIdent+"%')";
                sql += " or (r.latitud_ras like '%"+valIdent+"%')";
                sql += " or (r.longitud_ras like '%"+valIdent+"%')";
                sql += " or (r.terreno_circundante_ras='"+valIdent+"')";
                sql += " or (r.posicion_perfil_ras='"+valIdent+"')";
                sql += " or (r.ph_ras like '%"+valIdent+"%')";
                sql += " or (r.carbonatos_ras='"+valIdent+"'))";
            }
        }
        
        if (args.containsKey("num_rasta")) {
            String valIdent = String.valueOf(args.get("num_rasta"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and r.numero_cajuela_ras like '%"+args.get("num_rasta")+"%'";
        }
        if (args.containsKey("date")) {
            String valIdent = String.valueOf(args.get("date"));            
//            SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");            
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) {
                String dateAsign = new SimpleDateFormat("yyyy-dd-MM").format(new Date(valIdent));
                sql += " and r.fecha_ras like '%"+dateAsign+"%'";
//                try {
                    //                Date myDate = (Date)args.get("date");
//                    Date dmy = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(args.get("date")));
//                    sql += " and r.fecha_ras like '%'"+dmy.toString()+"%'";
    //            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and r.fecha_ras like '%"+args.get("name_property_lot")+"%'";
//                } catch (ParseException ex) {
//                    Logger.getLogger(RastasDao.class.getName()).log(Level.SEVERE, null, ex);
//                }
            }
//            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and r.fecha_ras like '%"+args.get("name_property_lot")+"%'";
        }
        if (args.containsKey("pendant")) {
            String valIdent = String.valueOf(args.get("pendant"));
//            if(!valIdent.equals("0") && !valIdent.equals("-1") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and r.pendiente_terreno_ras="+args.get("pendant");
            if(!valIdent.equals("0") && !valIdent.equals("-1") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and r.pendiente_terreno_ras like '%"+args.get("pendant")+"%'";
        }
        if (args.containsKey("altitude")) {
            String valIdent = String.valueOf(args.get("altitude"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and r.altitud_ras like '%"+args.get("altitude")+"%'";
        }    
        if (args.containsKey("latitude")) {
            String valIdent = String.valueOf(args.get("latitude"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and r.latitud_ras like '%"+args.get("latitude")+"%'";
        }        
        if (args.containsKey("length")) {
            String valIdent = String.valueOf(args.get("length"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and r.longitud_ras like '%"+args.get("length")+"%'";
        }       
        
        if (args.containsKey("ground")) {
            String valIdent = String.valueOf(args.get("ground"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("-1") && !valIdent.equals("null")) sql += " and r.terreno_circundante_ras='"+args.get("ground")+"'";
        }
        if (args.containsKey("position")) {
            String valIdent = String.valueOf(args.get("position"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("-1") && !valIdent.equals("null")) sql += " and r.posicion_perfil_ras='"+args.get("position")+"'";
        }
        if (args.containsKey("ph")) {
            String valIdent = String.valueOf(args.get("ph"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) sql += " and r.ph_ras like '%"+args.get("ph")+"%'";
        }
        if (args.containsKey("carbonates")) {
            String valIdent = String.valueOf(args.get("carbonates"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("-1") && !valIdent.equals("null")) sql += " and r.carbonatos_ras='"+args.get("carbonates")+"'";
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
//            valIni = ((valIni-1)*maxResults)+1;
            valIni = ((valIni-1)*maxResults);
        } else {
            valIni = 0;
        }     
//        sql += " order by e.name_ent ASC";
        sql += " order by r.id_ras ASC";
//        events.toArray();
        try {

//        sql += "select r.id_ras-0, r.id_lote_ras-1, l.name_fie-2, e.name_ent-3, f.name_far-4, r.fecha_ras-5, r.numero_cajuela_ras-6, r.altitud_ras-7, r.latitud_ras-8, r.longitud_ras-9,";
//        sql += " r.pendiente_terreno_ras-10, r.terreno_circundante_ras-11, r.posicion_perfil_ras-12, r.numero_capas_ras-13,";
//        sql += " r.ph_ras-14, r.carbonatos_ras-15, r.profundidad_carbonatos_ras-16, r.piedras_superficie_ras-17, r.rocas_superficie_ras-18,";
//        sql += " r.piedras_perfil_ras-19, r.rocas_perfil_ras-20, r.horizonte_pedrogoso_rocoso_ras-21, r.profundidad_horizonte_pedregoso_ras-22,";
//        sql += " r.profundidad_primeras_piedras_ras-23, r.espesor_horizonte_pedregoso_ras-24, r.capas_endurecidas_ras-25, r.prufundidad_capas_ras-26,";
//        sql += " r.espesor_capa_endurecida_ras-27, r.moteados_ras-28, r.profundidad_moteados_ras-29, r.moteados_mas_70cm_ras-30, r.estructura_ras-31,";
//        sql += " r.erosion_ras-32, r.moho_ras-33, r.costras_duras_ras-34, r.exposicion_sol_ras-35, r.costras_blancas_ras-36, r.costras_negras_ras-37,";
//        sql += " r.region_seca_ras-38, r.raices_vivas_ras-39, r.profundidad_raices_ras-40, r.plantas_pequenas_ras-41, r.hojarasca_ras-42,";
//        sql += " r.suelo_negro_blando_ras-43, r.cuchillo_primer_horizonte_ras-44, r.cerca_rios_quebradas_ras-45, r.recubrimiento_vegetal_ras-46, r.status-47";
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
//        num_rasta        
//        date
//        pendant
//        altitude
//        latitude
//        length
//        ground
//        position
//        ph
//        carbonates
//        
            for (Object[] data : events) {
                HashMap temp = new HashMap();
                temp.put("id_ras", data[0]);
                temp.put("id_field", data[1]);
                temp.put("num_rasta", data[6]);          
                temp.put("name_producer", data[3]);                
                temp.put("name_farm", data[4]);
                temp.put("name_field", data[2]);                  
//                String val = String.valueOf(data[5]);
//                Date newDate   = new SimpleDateFormat("yyyy-MM-dd").parse(val);
                temp.put("date", data[5]);                
                temp.put("pendant", data[10]);
                temp.put("altitude", data[7]);
                temp.put("latitude", data[8]);                
                temp.put("length", data[9]);                
                temp.put("ground", data[11]);                
                temp.put("position", data[12]);                
                temp.put("ph", data[14]);                
                temp.put("carbonates", data[15]);                
                temp.put("num_layer", data[13]);                
                temp.put("status", data[47]);
                temp.put("dateLog", data[48]);
                if (entType.equals("3")) {
                    temp.put("nameAgro", data[49]);
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
        
        String sql = "";     
        String sqlAdd = "";    
        String entType = String.valueOf(args.get("entType"));
        
        sql += "select count(r.id_ras), r.id_lote_ras";               
        sql += " from rastas r";
        sql += " inner join log_entities le on le.id_object_log_ent=r.id_ras and le.table_log_ent='rastas' and le.action_type_log_ent='C'";   
        if (entType.equals("3")) {
            sql += " inner join entities entLe on (le.id_entity_log_ent=entLe.id_ent)"; 
            sql += " inner join extension_agents ext on (ext.id_entity_ext_age=entLe.id_ent)";
            sql += " inner join association ass on (ass.id_asc=ext.id_asso_ext_age)";
        }
        sql += " inner join fields l on r.id_lote_ras=l.id_fie";
        sql += " inner join fields_producers lp on lp.id_field_fie_pro=l.id_fie";
        sql += " inner join farms f on f.id_far=l.id_farm_fie";
        sql += " inner join producers p on p.id_pro=lp.id_producer_fie_pro"; 
        sql += " inner join entities e on e.id_ent=p.id_entity_pro";
        
        sql += " where l.status=1 and f.status=1";
        sql += " and r.status=1 and e.status=1"; 
        if (!entType.equals("3") && args.containsKey("idEntUser")) {
            sqlAdd += " and le.id_entity_log_ent="+args.get("idEntUser");
        } else {
            sqlAdd += " and ass.id_entity_asc="+args.get("idEntUser");
        }
        sql += sqlAdd;
//        System.out.println("sql->"+sql);
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
    
    public List<HorizontesRasta> getHorizonRasta(Integer idRas) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<HorizontesRasta> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String sql  = "select h.id_hor_ras, h.id_rasta_hor_ras, h.numero_horizonte_hor_ras, h.espesor_hor_ras,";
            sql += " h.color_seco_hor_ras, h.color_humedo_hor_ras, h.textura_hor_ras, h.resistencia_rompimiento_hor_ras,";
            sql += " h.status, h.created_by";
            sql += " from horizontes_rasta h where h.id_rasta_hor_ras = "+idRas;
            Query query = session.createSQLQuery(sql).addEntity("h", HorizontesRasta.class);
//            Query query = session.createQuery("from HorizontesRasta where Rastas = :id_ras");
//            query.setParameter("id_ras", idRas);
//            query.setParameter("id_ras", new Rastas(idRas));
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
    
    public Rastas objectById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        Rastas event = null;
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            String hql  = "FROM Rastas E WHERE E.idRas = :id_ras";
            Query query = session.createQuery(hql);
            query.setParameter("id_ras", id);
            event = (Rastas)query.uniqueResult();
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

    public void save(Rastas event) {
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

    public void delete(Rastas event) {
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
    
    public int deleteHorizonte(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

//        Rastas event = null;
        int numDelete = 0;
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            String sql  = "delete from horizontes_rasta where id_hor_ras="+id;
            Query query = session.createSQLQuery(sql);
            numDelete   = query.executeUpdate();
//            String hql  = "FROM HorizontesRasta E WHERE E.idRas = :id_ras";
//            Query query = session.createQuery(hql);
//            query.setParameter("id_ras", id);
//            event = (Rastas)query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return numDelete;
    }
    
    public String getInfoToReport(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        
        List<Object[]> events = null;
        Transaction tx = null;
        String result  = "data.frame(";
        
        String sql = "";        
        sql += "select l.id_fie, f.id_far, r.PENDIENTE_TERRENO_RAS,";
        sql += "UPPER(r.TERRENO_CIRCUNDANTE_RAS), UPPER(r.POSICION_PERFIL_RAS), r.NUMERO_CAPAS_RAS, espesorRasta(r.ID_RAS, e.id_ent), cSecoRasta(r.ID_RAS, e.id_ent), cHumedoRasta(r.ID_RAS, e.id_ent),";
        sql += "texturaRasta(r.ID_RAS, e.id_ent), resistenciaRasta(r.ID_RAS, e.id_ent), r.PH_RAS, UPPER(r.CARBONATOS_RAS), IF(r.CARBONATOS_RAS='no tiene','NA',r.PROFUNDIDAD_CARBONATOS_RAS), UPPER(r.PIEDRAS_SUPERFICIE_RAS),";
        sql += "UPPER(r.ROCAS_SUPERFICIE_RAS), UPPER(r.PIEDRAS_PERFIL_RAS), UPPER(r.ROCAS_PERFIL_RAS), IF(r.HORIZONTE_PEDROGOSO_ROCOSO_RAS=1,'SI','NO'),";
        sql += "IF(r.HORIZONTE_PEDROGOSO_ROCOSO_RAS=1,r.PROFUNDIDAD_HORIZONTE_PEDREGOSO_RAS,'NA'), IF(r.HORIZONTE_PEDROGOSO_ROCOSO_RAS=1,r.ESPESOR_HORIZONTE_PEDREGOSO_RAS,'NA'), IF(r.HORIZONTE_PEDROGOSO_ROCOSO_RAS=1,r.PROFUNDIDAD_PRIMERAS_PIEDRAS_RAS,'NA'), IF(r.CAPAS_ENDURECIDAS_RAS=1,'SI','NO'),";
        sql += "IF(r.CAPAS_ENDURECIDAS_RAS=1,r.PRUFUNDIDAD_CAPAS_RAS,'NA'), IF(r.CAPAS_ENDURECIDAS_RAS=1,r.ESPESOR_CAPA_ENDURECIDA_RAS,'NA'), IF(r.MOTEADOS_RAS=1,'SI','NO'), IF(r.MOTEADOS_RAS=1,r.PROFUNDIDAD_MOTEADOS_RAS,'NA'),";
        sql += "IF(r.MOTEADOS_MAS_70CM_RAS=1,'SI','NO'), UPPER(r.ESTRUCTURA_RAS), IF(r.EROSION_RAS=1,'SI','NO'), IF(r.MOHO_RAS=1,'SI','NO'), UPPER(r.COSTRAS_DURAS_RAS),";
        sql += "UPPER(r.EXPOSICION_SOL_RAS), UPPER(r.COSTRAS_BLANCAS_RAS), UPPER(r.COSTRAS_NEGRAS_RAS),";
        sql += "IF(r.REGION_SECA_RAS=1,'SI','NO'), IF(r.RAICES_VIVAS_RAS=1,'SI','NO'), IF(r.RAICES_VIVAS_RAS=1,r.PROFUNDIDAD_RAICES_RAS,'NA'), UPPER(r.plantas_pequenas_ras), IF(r.HOJARASCA_RAS=1,'SI','NO'), IF(r.SUELO_NEGRO_BLANDO_RAS=1,'SI','NO'), IF(r.CUCHILLO_PRIMER_HORIZONTE_RAS=1,'SI','NO'),";
        sql += "IF(r.CERCA_RIOS_QUEBRADAS_RAS=1,'SI','NO'), UPPER(r.RECUBRIMIENTO_VEGETAL_RAS)";
        sql += " from fields l";
        sql += " inner join rastas r on r.id_lote_ras = l.id_fie";
        sql += " inner join fields_producers lp on lp.id_field_fie_pro = l.id_fie";
        sql += " inner join farms f on l.id_farm_fie=f.id_far";
        sql += " inner join farms_producers fp on f.id_far = fp.id_farm_far_pro";
        sql += " inner join producers p on p.id_pro = fp.id_producer_far_pro";
        sql += " inner join entities ent on ent.id_ent = p.id_entity_pro";
        sql += " left join log_entities le on le.id_object_log_ent = r.id_ras AND le.table_log_ent = 'rastas'";
        sql += " inner join entities e on le.id_entity_log_ent = e.id_ent";
        sql += " where r.status=1";
        if (id!=null) {
            sql += " and r.id_ras="+id;
            sql += " and le.id_object_log_ent not in (select le.id_object_log_ent from log_entities le where le.id_object_log_ent = "+id+" and le.action_type_log_ent = 'D' AND le.table_log_ent = 'rastas')";
        }
        sql += " and le.action_type_log_ent = 'C'";
//        args.get("countTotal");
//        events.toArray();
//        System.out.println("sql->"+sql);        
//        try {
//            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql);            
            events = query.list();         
            
            for (Object[] data : events) {
                for (int i=0;i<data.length;i++) {
                    String temp = String.valueOf(data[i]);
                    if (i<(data.length-1)) {
                        result += GlobalFunctions.checkDataRasta(temp)+",";
                    } else {
                        result += GlobalFunctions.checkDataRasta(temp);
                    }             
                }
                result += ")";
            }
//            tx.commit();
//        } catch (HibernateException e) {
//            if (tx != null) {
//                tx.rollback();
//            }
//            e.printStackTrace();
//        } finally {
            session.close();
//        }
        return result;
    }
    
    public String getInfoRasta(Integer id, HashMap valInf) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        
        List<Object[]> events = null;
        Transaction tx = null;
        
        String sql = "";        
        sql += "select ent.name_ent, r.ph_ras, r.estructura_ras, r.exposicion_sol_ras, r.recubrimiento_vegetal_ras, r.pendiente_terreno_ras";
        sql += " from rastas r";
        sql += " inner join fields l on r.id_lote_ras = l.id_fie";
        sql += " inner join fields_producers lp on lp.id_field_fie_pro = l.id_fie";
        sql += " inner join farms f on l.id_farm_fie=f.id_far";
        sql += " inner join farms_producers fp on f.id_far = fp.id_farm_far_pro";
        sql += " inner join producers p on p.id_pro = fp.id_producer_far_pro";
        sql += " inner join entities ent on ent.id_ent = p.id_entity_pro";
        sql += " left join log_entities le on le.id_object_log_ent = r.id_ras AND le.table_log_ent = 'rastas'";
        sql += " inner join entities e on le.id_entity_log_ent = e.id_ent";
        sql += " where r.status=1";
        if (id!=null) {
            sql += " and r.id_ras="+id;
            sql += " and le.id_object_log_ent not in (select le.id_object_log_ent from log_entities le where le.id_object_log_ent = "+id+" and le.action_type_log_ent = 'D' AND le.table_log_ent = 'rastas')";
        }
        sql += " and le.action_type_log_ent = 'C'";
        
        String dataGeneral = "{\"valTable\" : [{";        
//        args.get("countTotal");
//        events.toArray();
//        System.out.println("sql->"+sql);        
        try {
            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql);            
            events = query.list();         
            
            for (Object[] data : events) {                
                dataGeneral   += "\"producerId\": \""+data[0]+"\",";
                dataGeneral   += "\"depthId\": \""+valInf.get("depth")+"\",";
                dataGeneral   += "\"phId\": \""+data[1]+"\",";
                dataGeneral   += "\"structureId\": \""+data[2]+"\",";
                dataGeneral   += "\"exposeId\": \""+data[3]+"\",";
                dataGeneral   += "\"coveringId\": \""+data[4]+"\",";
                dataGeneral   += "\"drainIntId\": \""+valInf.get("internal")+"\",";
                dataGeneral   += "\"drainExtId\": \""+valInf.get("external")+"\",";
                dataGeneral   += "\"valDes\": \"Pendiente: "+data[5]+"%\",";
                dataGeneral   += "\"valIn\": \""+data[5]+"\"";
            }           
            dataGeneral   += "}],";    
            String[] infoMaterials = (String[])valInf.get("organic");
            dataGeneral   += this.getInfoHorizontes(id, infoMaterials);    
            dataGeneral   += "}";                      
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return dataGeneral;
    }
    
    
    public String getInfoHorizontes(Integer idRasta, String[] materials) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        
        List<Object[]> events = null;
        Transaction tx  = null;
        String result   = "";        
        Integer cont    = 0;
        
        String sql = "";        
        sql += "select h.numero_horizonte_hor_ras, h.espesor_hor_ras, rr.nombre_res_rom, t.prefix_tex, h.color_seco_hor_ras, h.color_humedo_hor_ras, t.name_tex";
        sql += " from horizontes_rasta h";
        sql += " inner join resistencias_rompimiento rr on rr.id_res_rom=h.resistencia_rompimiento_hor_ras";
        sql += " inner join textures t on t.id_tex=h.textura_hor_ras";
        sql += " where h.status=1";
        if (idRasta!=null) {
            sql += " and h.id_rasta_hor_ras="+idRasta;
        }
        sql += " order by h.id_rasta_hor_ras";
        
        String profiles  = "\"detailProfiles\" : [{";         
        String depths    = "\"data\" : [";
        String textures  = "\"infoProfile\" : [{";
        String valColors = "\"detailColors\" : [{";
        /*
         "data" : [{"point" :"[[1, -20]]", "color": "#0077FF"},{"point" :"[[1, -40]]", "color": "#7D0096"},{"point" :"[[1, -60]]", "color": "#DE000F"}], 
         "infoProfile" : [{"pro1": "a","pro2": "f","pro3": "l"}]         
         */    
        
//        { "detailProfiles" : [{"profile1" : "<center>Horizonte 1:</center><p>Espesor: 30cm, <br/>Resistencia al rompimiento : Friable, <br/>Materia organica : MEDIA</p>", "profile2" : "<center>Horizonte 1:</center><p>Espesor: 30cm, <br/>Resistencia al rompimiento : Friable, <br/>Materia organica : MEDIA</p>", "profile3" : "<center>Horizonte 1:</center><p>Espesor: 30cm, <br/>Resistencia al rompimiento : Friable, <br/>Materia organica : MEDIA</p>"}], "valTable" : [{"producerId" : "Jose Arana", "depthId" : "Profundidad Efectiva (cm) - 110", "phId" : "5", "structureId" : "Granular", "exposeId" : "La Mañana y Tarde", "coveringId" : "Bueno", "drainIntId" : "Bueno", "drainExtId" : "Lento"}], "valIn" : "Inclinacion: 12%", "data" : [{"point" :"[[1, -20]]", "color": "#0077FF"},{"point" :"[[1, -40]]", "color": "#7D0096"},{"point" :"[[1, -60]]", "color": "#DE000F"}], "infoProfile" : [{"pro1": "a","pro2": "f","pro3": "l"}] }
//        args.get("countTotal");
//        events.toArray();
//        System.out.println("sql->"+sql);        
//        String[] colors = {"#99A52D","#F5B304","#C68628","#B4CA29","#E4DE2C"};
        String[] colors = {"#311A0E", "#311B05", "#322012", "#3E3222", "#311A0E", "#695341", "#705A44", "#6C5C44", "#87775F", "#A2927A", "#605C53", "#868375", "#7A735C", "#5A3117", "#6A3003",
        "#5D3F03", "#6B4400", "#7F5B08", "#785306", "#6A3003", "#FEFEFE", "#8B492D", "#90320C", "#975717", "#A6580F", "#986F37", "#A94F0F", "#A6640D", "#BA3215", "#D05A0F",
        "#7F5B08", "#8D5F0A", "#A6640D", "#BA772A", "#BC7908", "#CB7908", "#E39647", "#D19554", "#D19554", "#F5AA8C", "#EBB300", "#FCD18A", "#A79742", "#B8A776", "#B7BA8C",
        "#CDC097", "#C9CD99", "#DDE5D3", "#CEB06F", "#DEC491", "#E1D3B0", "#EDE693", "#E9E3AE", "#F7EFC0"}; 
        
        
        try {
            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql);            
            events = query.list();             
            
            for (Object[] data : events) {
                Double espDouble = Double.parseDouble(String.valueOf(data[1]));
                Integer esp      = espDouble.intValue();
                Integer nColorSeco = Integer.parseInt(String.valueOf(data[4]));
                Integer nColorHum  = Integer.parseInt(String.valueOf(data[5]));
                String colorSel    = "";
                String colorDes    = "";
                if ((nColorSeco!=null || nColorSeco!=0) && (nColorHum!=null || nColorHum!=0)) {
                    colorSel = colors[nColorHum-1];
                    colorDes = "<b>Color Humedo: </b>"+nColorHum;
                } else if ((nColorSeco==null || nColorSeco==0) && (nColorHum!=null || nColorHum!=0)) {
                    colorSel = colors[nColorHum-1];
                    colorDes = "<b>Color Humedo: </b>"+nColorHum;
                } else if ((nColorSeco!=null || nColorSeco!=0) && (nColorHum==null || nColorHum!=0)) {
                    colorSel = colors[nColorSeco-1];
                    colorDes = "<b>Color Seco: </b>"+nColorSeco;
                }  
                
                if (cont!=(events.size()-1)) {
                    profiles += "\"profile"+(cont+1)+"\": \"<center><b>Horizonte "+(cont+1)+": </b></center><p><b>Espesor: </b>"+esp+", <br/><b>Resistencia al rompimiento: </b> "+data[2]+", <br/><b>Materia organica: </b>"+materials[cont];
                    profiles += ", <br/><b>Color seco: </b>"+data[4]+", <br/><b>Color humedo: </b>"+data[5]+", <br/><b>Textura: </b>"+data[6]+"\",";
                    depths   += "{\"point\": \"[[1, -"+data[1]+"]]\", \"color\": \""+colorSel+"\"},";
                    textures += "\"pro"+(cont+1)+"\": \""+data[3]+"\",";
                    valColors += "\"text"+(cont+1)+"\": \""+colorDes+"\",";
                } else {
                    profiles += "\"profile"+(cont+1)+"\": \"<center><b>Horizonte "+(cont+1)+": </b></center><p><b>Espesor: </b>"+esp+", <br/><b>Resistencia al rompimiento: </b> "+data[2]+", <br/><b>Materia organica: </b>"+materials[cont];
                    profiles += ", <br/><b>Color seco: </b>"+data[4]+", <br/><b>Color humedo: </b>"+data[5]+", <br/><b>Textura: </b>"+data[6]+"\"";
                    depths   += "{\"point\": \"[[1, -"+data[1]+"]]\", \"color\": \""+colorSel+"\"}";
                    textures += "\"pro"+(cont+1)+"\": \""+data[3]+"\"";
                    valColors += "\"text"+(cont+1)+"\": \""+colorDes+"\"";
                }                
                cont++;
            }
            profiles  += "}]";
            textures  += "}]";
            valColors += "}]";
            depths    += "]";
            result    += profiles+","+depths+","+textures+","+valColors;
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
    
    public void getRastas(HashMap args, String fileName) 
    {        
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Object[]> events = null;
        Transaction tx = null;    

        String sql = "";
        String entType = String.valueOf(args.get("entType"));
        
        sql += "select e.name_ent as USUARIO, r.PENDIENTE_TERRENO_RAS as PENDIENTE,";
        sql += "UPPER(r.TERRENO_CIRCUNDANTE_RAS) as TERRENO, UPPER(r.POSICION_PERFIL_RAS) as POSICION, r.NUMERO_CAPAS_RAS as CAPAS, espesorRasta(r.ID_RAS, le.id_entity_log_ent) as ESPESORES, cSecoRasta(r.ID_RAS, le.id_entity_log_ent) as COLORES_SECOS, cHumedoRasta(r.ID_RAS, le.id_entity_log_ent) as COLORES_HUMEDOS, ";
        sql += "texturaRasta(r.ID_RAS, le.id_entity_log_ent) as TEXTURAS, resistenciaRasta(r.ID_RAS, le.id_entity_log_ent) as RESISTENCIAS, r.PH_RAS as PH, UPPER(r.CARBONATOS_RAS) as CARBONATOS, IF(r.CARBONATOS_RAS='no tiene','NA',r.PROFUNDIDAD_CARBONATOS_RAS) as PROF_CARBONATOS, UPPER(r.PIEDRAS_SUPERFICIE_RAS) as PIEDRAS_SUPERFICIE,";
        sql += "UPPER(r.ROCAS_SUPERFICIE_RAS) as ROCAS_SUPERFICIE, UPPER(r.PIEDRAS_PERFIL_RAS) as PIEDRAS_PERFIL, UPPER(r.ROCAS_PERFIL_RAS) as ROCAS_PERFIL, IF(r.HORIZONTE_PEDROGOSO_ROCOSO_RAS=1,'SI','NO') as HORIZONTE_PEDREGOSO, ";
        sql += "IF(r.HORIZONTE_PEDROGOSO_ROCOSO_RAS=1,r.PROFUNDIDAD_HORIZONTE_PEDREGOSO_RAS,'NA') as PROF_HORIZONTE_PEDREGOSO, IF(r.HORIZONTE_PEDROGOSO_ROCOSO_RAS=1,r.ESPESOR_HORIZONTE_PEDREGOSO_RAS,'NA') as ESP_HORIZONTE_PEDREGOSO, IF(r.HORIZONTE_PEDROGOSO_ROCOSO_RAS=1,r.PROFUNDIDAD_PRIMERAS_PIEDRAS_RAS,'NA') as PROF_PRIMERAS_PIEDRAS, IF(r.CAPAS_ENDURECIDAS_RAS=1,'SI','NO') as CAPAS_ENDURECIDAS, ";
        sql += "IF(r.CAPAS_ENDURECIDAS_RAS=1,r.PRUFUNDIDAD_CAPAS_RAS,'NA') as PROF_CAPA_ENDURECIDA, IF(r.CAPAS_ENDURECIDAS_RAS=1,r.ESPESOR_CAPA_ENDURECIDA_RAS,'NA') as ESP_CAPA_ENDURECIDA, IF(r.MOTEADOS_RAS=1,'SI','NO') as MOTEADOS, IF(r.MOTEADOS_RAS=1,r.PROFUNDIDAD_MOTEADOS_RAS,'NA') as PROF_MOTEADOS,";
        sql += "IF(r.MOTEADOS_MAS_70CM_RAS=1,'SI','NO') as MOTEADOS_70CM, UPPER(r.ESTRUCTURA_RAS) as ESTRUCTURA, IF(r.EROSION_RAS=1,'SI','NO') as EROSION, IF(r.MOHO_RAS=1,'SI','NO') as MOHO, UPPER(r.COSTRAS_DURAS_RAS) as COSTRAS_DURAS, ";
        sql += "UPPER(r.EXPOSICION_SOL_RAS) as EXPOSICION_SOL, UPPER(r.COSTRAS_BLANCAS_RAS) as COSTRAS_BLANCAS, UPPER(r.COSTRAS_NEGRAS_RAS) as COSTRAS_NEGRAS,";
        sql += "IF(r.REGION_SECA_RAS=1,'SI','NO') as REGION_SECA, IF(r.RAICES_VIVAS_RAS=1,'SI','NO') as RAICES_VIVAS, IF(r.RAICES_VIVAS_RAS=1,r.PROFUNDIDAD_RAICES_RAS,'NA') as PROF_RAICES, UPPER(r.plantas_pequenas_ras) as PLANTAS_PEQUEÑAS, IF(r.HOJARASCA_RAS=1,'SI','NO') as HOJARASCA, IF(r.SUELO_NEGRO_BLANDO_RAS=1,'SI','NO') as SUELO_NEGRO_BLANDO, IF(r.CUCHILLO_PRIMER_HORIZONTE_RAS=1,'SI','NO') as CUCHILLO_PR_HORIZONTE,";
        sql += "IF(r.CERCA_RIOS_QUEBRADAS_RAS=1,'SI','NO') as CERCA_RIOS_QUEBRADAS, UPPER(r.RECUBRIMIENTO_VEGETAL_RAS) as RECUBRIMIENTO_VEGETAL";
        sql += " from rastas r";
        sql += " inner join fields l on r.id_lote_ras = l.id_fie";
        sql += " inner join fields_producers lp on lp.id_field_fie_pro = l.id_fie";
        sql += " inner join farms f on l.id_farm_fie=f.id_far";
        sql += " inner join farms_producers fp on f.id_far = fp.id_farm_far_pro";
        sql += " inner join producers p on p.id_pro = fp.id_producer_far_pro";
        sql += " inner join entities ent on ent.id_ent = p.id_entity_pro";
        sql += " left join log_entities le on le.id_object_log_ent = r.ID_RAS AND le.table_log_ent = 'rastas'";
        sql += " inner join entities e on le.id_entity_log_ent = e.id_ent";
        if (entType.equals("3")) {
            sql += " inner join extension_agents ext on (ext.id_entity_ext_age=e.id_ent)";
            sql += " inner join association ass on (ass.id_asc=ext.id_asso_ext_age)";
        }
        sql += " where le.action_type_log_ent = 'C'";
        sql += " and r.status=1 and l.status=1 and f.status=1 and ent.status=1";
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
        sql += "	where le.action_type_log_ent = 'D' AND le.table_log_ent = 'rastas'";
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
        sql += " order by l.id_fie, e.name_ent, ent.name_ent";

//        System.out.println("sql=>"+sql);
        try {
            tx = session.beginTransaction();
            CSVWriter writer = new CSVWriter(new FileWriter(fileName), ';');
            Query query  = session.createSQLQuery(sql);
            events = query.list();
        
            String[] val = {
                "USUARIO",
                "PENDIENTE",
                "TERRENO",
                "POSICION",
                "CAPAS",
                "ESPESORES",
                "COLORES_SECOS",
                "COLORES_HUMEDOS",
                "TEXTURAS",
                "RESISTENCIAS",
                "PH",
                "CARBONATOS",
                "PROF_CARBONATOS",
                "PIEDRAS_SUPERFICIE",
                "ROCAS_SUPERFICIE",
                "PIEDRAS_PERFIL",
                "ROCAS_PERFIL",
                "HORIZONTE_PEDREGOSO",
                "PROF_HORIZONTE_PEDREGOSO",
                "ESP_HORIZONTE_PEDREGOSO",
                "PROF_PRIMERAS_PIEDRAS",
                "CAPAS_ENDURECIDAS",
                "PROF_CAPA_ENDURECIDA",
                "ESP_CAPA_ENDURECIDA",
                "MOTEADOS",
                "PROF_MOTEADOS",
                "MOTEADOS_70CM",
                "ESTRUCTURA",
                "EROSION",
                "MOHO",
                "COSTRAS_DURAS",
                "EXPOSICION_SOL",
                "COSTRAS_BLANCAS",
                "COSTRAS_NEGRAS",
                "REGION_SECA",
                "RAICES_VIVAS",
                "PROF_RAICES",
                "PLANTAS_PEQUEÑAS",
                "HOJARASCA",
                "SUELO_NEGRO_BLANDO",
                "CUCHILLO_PR_HORIZONTE",
                "CERCA_RIOS_QUEBRADAS",
                "RECUBRIMIENTO_VEGETAL"
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
                    String.valueOf(data[10]),
                    String.valueOf(data[11]),
                    String.valueOf(data[12]),
                    String.valueOf(data[13]),
                    String.valueOf(data[14]),
                    String.valueOf(data[15]),
                    String.valueOf(data[16]),
                    String.valueOf(data[17]),
                    String.valueOf(data[18]),
                    String.valueOf(data[19]),
                    String.valueOf(data[20]),
                    String.valueOf(data[21]),
                    String.valueOf(data[22]),
                    String.valueOf(data[23]),
                    String.valueOf(data[24]),
                    String.valueOf(data[25]),
                    String.valueOf(data[26]),
                    String.valueOf(data[27]),
                    String.valueOf(data[28]),
                    String.valueOf(data[29]),
                    String.valueOf(data[30]),
                    String.valueOf(data[31]),
                    String.valueOf(data[32]),
                    String.valueOf(data[33]),
                    String.valueOf(data[34]),
                    String.valueOf(data[35]),
                    String.valueOf(data[36]),
                    String.valueOf(data[37]),
                    String.valueOf(data[38]),
                    String.valueOf(data[39]),
                    String.valueOf(data[40]),
                    String.valueOf(data[41]),
                    String.valueOf(data[42])
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
//            Logger.getLogger(RastasDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.close();
        }
//        return result;
    }
    
}
