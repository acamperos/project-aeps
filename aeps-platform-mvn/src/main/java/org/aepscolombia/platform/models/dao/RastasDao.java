package org.aepscolombia.platform.models.dao;

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
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase LotsDao
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
        sql += " and r.status=1";
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
        
        sql += "select r.id_ras, r.id_lote_ras, l.name_fie, e.name_ent, f.name_far, r.fecha_ras, r.numero_cajuela_ras, r.altitud_ras, r.latitud_ras, r.longitud_ras,";
        sql += " r.pendiente_terreno_ras, r.terreno_circundante_ras, r.posicion_perfil_ras, r.numero_capas_ras,";
        sql += " r.ph_ras, r.carbonatos_ras, r.profundidad_carbonatos_ras, r.piedras_superficie_ras, r.rocas_superficie_ras,";
        sql += " r.piedras_perfil_ras, r.rocas_perfil_ras, r.horizonte_pedrogoso_rocoso_ras, r.profundidad_horizonte_pedregoso_ras,";
        sql += " r.profundidad_primeras_piedras_ras, r.espesor_horizonte_pedregoso_ras, r.capas_endurecidas_ras, r.prufundidad_capas_ras,";
        sql += " r.espesor_capa_endurecida_ras, r.moteados_ras, r.profundidad_moteados_ras, r.moteados_mas_70cm_ras, r.estructura_ras,";
        sql += " r.erosion_ras, r.moho_ras, r.costras_duras_ras, r.exposicion_sol_ras, r.costras_blancas_ras, r.costras_negras_ras,";
        sql += " r.region_seca_ras, r.raices_vivas_ras, r.profundidad_raices_ras, r.plantas_pequenas_ras, r.hojarasca_ras,";
        sql += " r.suelo_negro_blando_ras, r.cuchillo_primer_horizonte_ras, r.cerca_rios_quebradas_ras, r.recubrimiento_vegetal_ras";               
        sql += " from rastas r";
        sql += " inner join log_entities le on le.id_object_log_ent=r.id_ras and le.table_log_ent='rastas' and le.action_type_log_ent='C'";   
        sql += " inner join fields l on r.id_lote_ras=l.id_fie";
        sql += " inner join fields_producers lp on lp.id_field_fie_pro=l.id_fie";
        sql += " left join farms f on f.id_far=l.id_farm_fie";
        sql += " inner join producers p on p.id_pro=lp.id_producer_fie_pro"; 
        sql += " inner join entities e on e.id_ent=p.id_entity_pro"; 
        sql += " where l.status=1 and f.status=1";
        sql += " and r.status=1";    
        
        
        if (args.containsKey("idEntUser")) {
            sqlAdd += " and le.id_entity_log_ent="+args.get("idEntUser");
        }  
        
        if (args.containsKey("search_soil")) {
            String valIdent = String.valueOf(args.get("search_soil"));
            if(!valIdent.equals(" ") && !valIdent.equals("") && !valIdent.equals("null")) { 
                sql += " and ((r.numero_cajuela_ras like '%"+valIdent+"%')";
//                Date asign = new Date(valIdent);
//                sql += " or (r.fecha_ras like '%"+asign+"%')";
                try {
                    String dateAsign = new SimpleDateFormat("yyyy-dd-MM").format(new Date(valIdent));
                    sql += " or (r.fecha_ras='"+dateAsign+"')";
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
                sql += " and r.fecha_ras='"+dateAsign+"'";
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
//        System.out.println("sql->"+sql);
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
//                System.out.println(data.length);
                HashMap temp = new HashMap();
                temp.put("id_ras", data[0]);
                temp.put("id_field", data[1]);
                temp.put("num_rasta", data[6]);             
                String val = String.valueOf(data[5]);
                Date newDate   = new SimpleDateFormat("yyyy-MM-dd").parse(val);
                temp.put("date", new SimpleDateFormat("dd/MM/yyyy").format(newDate));                
                temp.put("pendant", data[10]);
                temp.put("altitude", data[7]);
                temp.put("latitude", data[8]);                
                temp.put("length", data[9]);                
                temp.put("ground", data[11]);                
                temp.put("position", data[12]);                
                temp.put("ph", data[14]);                
                temp.put("carbonates", data[15]);                
                temp.put("num_layer", data[13]);                
                temp.put("status", data[46]);
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
        } catch (ParseException ex) {
//            Logger.getLogger(RastasDao.class.getName()).log(Level.SEVERE, null, ex);
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
        
        sql += "select count(r.id_ras), r.id_lote_ras";               
        sql += " from rastas r";
        sql += " inner join log_entities le on le.id_object_log_ent=r.id_ras and le.table_log_ent='rastas' and le.action_type_log_ent='C'";   
        sql += " inner join fields l on r.id_lote_ras=l.id_fie";
        sql += " inner join fields_producers lp on lp.id_field_fie_pro=l.id_fie";
        sql += " left join farms f on f.id_far=l.id_farm_fie";
        sql += " where l.status=1 and f.status=1";
        sql += " and r.status=1";     
        if (args.containsKey("idEntUser")) {
            sqlAdd += " and le.id_entity_log_ent="+args.get("idEntUser");
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
//            System.out.println("sql->"+sql);
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
}
