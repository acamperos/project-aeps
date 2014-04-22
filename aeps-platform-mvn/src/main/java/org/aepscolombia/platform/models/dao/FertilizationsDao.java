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
import org.aepscolombia.platform.models.entity.Fertilizations;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase FertilizationsDao
 *
 * Contiene los metodos para interactuar con la tabla Fertilizations de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class FertilizationsDao 
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
        sql += " where l.status=1 and f.status=1 and pe.status=1";
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
    
    public List<Fertilizations> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Fertilizations> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from Fertilizations");
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
        List<HashMap> resultChe = new ArrayList<HashMap>();
        List<HashMap> resultOrg = new ArrayList<HashMap>();
        List<HashMap> resultAme = new ArrayList<HashMap>();
        
        String sql = "";     
        String sqlAdd = "";     
                      
        sql += "select p.id_fer, p.date_fer, p.fertilization_type_fer, ";
		sql += " tp.name_fer_typ, p.amount_product_used_fer";
        sql += " from fertilizations p"; 
        sql += " inner join production_events ep on ep.id_pro_eve=p.id_production_event_fer";    
        sql += " left join fertilizations_types tp on tp.id_fer_typ=p.fertilization_type_fer";    
        sql += " inner join log_entities le on le.id_object_log_ent=p.id_fer and le.table_log_ent='fertilizations' and le.action_type_log_ent='C'";   
		if (args.containsKey("idEvent")) { 
            sql += " where p.id_production_event_fer="+args.get("idEvent");
        }
        if (args.containsKey("idEntUser")) {
			sqlAdd += " and le.id_entity_log_ent="+args.get("idEntUser");
		}
		sqlAdd += " order by p.id_fer ASC";
		sql += sqlAdd;
        
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
				
				// $temp['id_fer']     			= $fila['ID_FER'];
        // $temp['fecha_fer']   	    = date('d/m/Y', strtotime($fila['FECHA_FER']));
        // $temp['id_tipo_fer']			= $fila['TIPO_FERTILIZACION_FER'];
        // $temp['nom_tipo_fer']			= utf8_encode($fila['NOMBRE_TI_FER']);
        // $temp['cantidad_product'] = $fila['CANTIDAD_PRODUCTO_USADO_FER'];
				// $temp['quimicos']  = array();
				// $temp['organicos'] = array();	
				// $temp['enmiendas'] = array();
				// if ($temp['id_tipo_fer']==1) {
					// $temp['info']  = self::getQuimicosFertilizacion($temp['id_fer'], $temp['cantidad_product']);
					// $list['qui'][] = $temp;
				// } elseif ($temp['id_tipo_fer']==2) {
					// $temp['info']  = self::getOrganicosFertilizacion($temp['id_fer']);
					// $list['org'][] = $temp;
				// } elseif ($temp['id_tipo_fer']==3) {
					// $temp['info']  = self::getEnmiendasFertilizacion($temp['id_fer']);
					// $list['enm'][] = $temp;
				// }
								
				for (Object[] data : events) {
                    HashMap temp = new HashMap();
                    temp.put("idFer", data[0]);
                    temp.put("dateFer", data[1]);
                    temp.put("idFerTyp", data[2]);             
                    temp.put("nameFerType", data[3]);                
                    temp.put("amountUsed", data[4]);
                    temp.put("infoChe", getChemicals(String.valueOf(temp.get("idFer")), Double.parseDouble(String.valueOf(temp.get("amountUsed")))));	
                    temp.put("infoOrg", getOrganics(String.valueOf(temp.get("idFer"))));	
                    temp.put("infoAme", getAmendments(String.valueOf(temp.get("idFer"))));	
                    String ferType = String.valueOf(temp.get("idFerTyp"));
                    if(ferType.equals("1")){
                        resultChe.add(temp);
                    } else if(ferType.equals("2")){
                        resultOrg.add(temp);
                    } else if(ferType.equals("3")){
                        resultAme.add(temp);
                    }
				}
                HashMap tempList = new HashMap();
                tempList.put("chemical", resultChe);			
                tempList.put("organic", resultOrg);			
                tempList.put("amendment", resultAme);		
                result.add(tempList);
//                for (HashMap data : result) {
//                    System.out.println("values=>"+data);
//                }
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
    
    public HashMap getChemicals(String idFert, Double quantPro) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Object[]> eventsTotal = null;
        List<Object[]> events = null;
        Transaction tx = null;
        List<HashMap> result = new ArrayList<HashMap>();
        
        String sql = "";     
        String sqlAdd = "";     		
        
        sql += "select fq.id_che_fer, fq.name_che_fer, ep.id_product_che_fer, ep.other_product_che_fer,";
        sql += " eq.name_che_ele, p.percentage_che_fer_com";
        sql += " from chemical_fertilizer_composition p"; 
        sql += " inner join chemical_fertilizers fq on fq.id_che_fer=p.id_chemical_fertilizer_che_fer_com";
        sql += " inner join chemical_fertilizations ep on ep.id_product_che_fer=fq.id_che_fer";    
        sql += " inner join chemical_elements eq on eq.id_che_ele=p.id_elements_che_fer_com and eq.status_che_ele=1";    
//		sql += " inner join fertilizations fer on ep.id_fertilization_che_fer=fer.id_fer"; 
//        sql += " inner join production_events prod on prod.id_pro_eve=fer.id_production_event_fer";    
//        sql += " left join fertilizations_types tp on tp.id_fer_typ=fer.fertilization_type_fer";    
//        sql += " inner join log_entities le on le.id_object_log_ent=fer.id_fer and le.table_log_ent='fertilizations' and le.action_type_log_ent='C'";        
        sql += " where ep.id_fertilization_che_fer="+idFert;
//        if (args.containsKey("idEvent")) { 
//            sql += " where fer.id_production_event_fer="+args.get("idEvent");
//        }
//        if (args.containsKey("idEntUser")) {
//			sqlAdd += " and le.id_entity_log_ent="+args.get("idEntUser");
//		}
        sqlAdd += " order by fq.id_che_fer ASC";
        sql += sqlAdd;
//        args.get("countTotal");
//        events.toArray();
        HashMap temp = new HashMap();
        try {
            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql);
//            System.out.println("sql->"+query.list().size());
            events = query.list();

            // HashMap temp = new HashMap();
            // temp.put("composition", "");
            // temp.put("value", "");
            // result.add(temp);

            int i=1;
            int numElem = query.list().size();            
            String composition = "";
            String value = "";
            for (Object[] data : events) {
//                HashMap temp = new HashMap();
                temp.put("idFerChe", data[0]);
                temp.put("nameFerTyp", data[1]);
                temp.put("idFerTyp", data[2]);             
                temp.put("otherProduct", data[3]);    
                
//                String idCoFer = String.valueOf(data[4]);
                String nameComTyp = String.valueOf(data[4]);
                Double perCom  = Double.parseDouble(String.valueOf(data[5]));
                Double amountCom = ((perCom*quantPro)/100);
                if (i == numElem) {
                    composition += nameComTyp+": "+perCom;
                    value += nameComTyp+": "+amountCom;
//                    temp.put("composition", nameComTyp+": "+perCom);
//                    temp.put("value", nameComTyp+": "+amountCom);
                } else {
                    composition += nameComTyp+": "+perCom+", ";
                    value += nameComTyp+": "+amountCom+", ";
//                    temp.put("composition", nameComTyp+": "+perCom+", ");
//                    temp.put("value", nameComTyp+": "+amountCom+", ");
                }
                result.add(temp);
                i++;
            }
            temp.put("composition", composition);
            temp.put("value", value);
//            System.out.println("values=>"+temp);
//            for (HashMap data : result) {
////                    System.out.println(data.get("id_productor")+" "+data.get("id_entidad")+" "+data.get("cedula"));
//                System.out.println("values=>"+data);
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
        return temp;
    }
    
    public HashMap getOrganics(String idFert) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Object[]> eventsTotal = null;
        List<Object[]> events = null;
        Transaction tx = null;
        List<HashMap> result = new ArrayList<HashMap>();
        HashMap temp = new HashMap();
        
        String sql = "";     
        String sqlAdd = "";   
        
        sql += "select p.id_org_fer, eq.name_org_fer, p.id_product_org_fer, p.other_product_org_fer";
        sql += " from organic_fertilizations p"; 
        sql += " left join organic_fertilizers eq on eq.id_org_fer=p.id_product_org_fer and eq.status_org_fer=1";    
        sql += " where p.id_fertilization_org_fer="+idFert;
//        if (args.containsKey("idEvent")) { 
//            sql += " where fer.id_production_event_fer="+args.get("idEvent");
//        }
//        if (args.containsKey("idEntUser")) {
//			sqlAdd += " and le.id_entity_log_ent="+args.get("idEntUser");
//		}
        
//        sql += " where p.id_fertilization_org_fer="+idFert;
        sqlAdd += " order by p.id_org_fer ASC";
        sql += sqlAdd;
//        args.get("countTotal");
//        events.toArray();
//        System.out.println("sql->"+sql);
        try {
            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql);
//            System.out.println("sql->"+query.list().size());
            events = query.list();				
        // $temp['nom_tipo_fer_org']  = utf8_encode($fila['NOMBRE_FER_ORGO']);
        // $temp['otro_producto_org'] = utf8_encode($fila['OTRO_PRODUCTO_FER_ORGA']);
				
            for (Object[] data : events) {
                temp.put("idFerOrg", data[0]);
                temp.put("nameFerTypOrg", data[1]);
                temp.put("idFerTypOrg", data[2]);             
                temp.put("otherProductOrg", data[3]);   
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
        return temp;
    }
    
    public HashMap getAmendments(String idFert) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Object[]> eventsTotal = null;
        List<Object[]> events = null;
        Transaction tx = null;
        List<HashMap> result = new ArrayList<HashMap>();
        HashMap temp = new HashMap();
        
        String sql = "";     
        String sqlAdd = "";   
        
        sql += "select p.id_ame_fer, eq.name_ame_fer, p.id_product_ame_fer, p.other_product_ame_fer";
        sql += " from amendments_fertilizations p"; 
        sql += " left join amendments_fertilizers eq on eq.id_ame_fer=p.id_product_ame_fer and eq.status_ame_fer=1";    
        sql += " where p.id_fertilization_ame_fer="+idFert;
//        if (args.containsKey("idEvent")) { 
//            sql += " where fer.id_production_event_fer="+args.get("idEvent");
//        }
//        if (args.containsKey("idEntUser")) {
//			sqlAdd += " and le.id_entity_log_ent="+args.get("idEntUser");
//		}        
        
        sqlAdd += " order by p.id_ame_fer ASC";
        sql += sqlAdd;
//        args.get("countTotal");
//        events.toArray();
//        System.out.println("sql->"+sql);
        try {
            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql);
//            System.out.println("sql->"+query.list().size());
            events = query.list();				
        // $temp['nom_tipo_fer_end']  = utf8_encode($fila['NOMBRE_FER_ENM']);
        // $temp['otro_producto_end'] = utf8_encode($fila['OTRO_PRODUCTO_FE_END']);
				
				
            for (Object[] data : events) {
                temp.put("idFerAme", data[0]);
                temp.put("nameFerTypAme", data[1]);
                temp.put("idFerTypAme", data[2]);             
                temp.put("otherProductAme", data[3]);  
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
        return temp;
    }
    
    public Fertilizations objectById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        Fertilizations event = null;
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            String hql  = "FROM Fertilizations E WHERE E.idFer = :id_fer";
            Query query = session.createQuery(hql);
            query.setParameter("id_fer", id);
            event = (Fertilizations)query.uniqueResult();
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

    public void save(Fertilizations event) {
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

    public void delete(Fertilizations event) {
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
