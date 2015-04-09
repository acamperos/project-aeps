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
import org.aepscolombia.platform.models.entity.Controls;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase ControlsDao
 *
 * Contiene los metodos para interactuar con la tabla Controls de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ControlsDao 
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
    
    public List<Controls> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Controls> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from Controls");
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
                      
        sql += "select p.target_type_con, pl.name_pes, mal.name_wee, enf.name_dis, tp.name_che_con, p.other_chemical_product_con, cr.name_org_con, p.other_organic_product_con,";
		sql += " p.id_con, p.date_con, tob.name_tar_typ, p.dosis_con, ud.name_dos_uni, p.cleanings_con, p.cleanings_frequence_con,";
        sql += " p.other_pest_con, p.otro_weed_con, p.other_disease_con, p.control_type_con";
		sql += " from controls p"; 
        sql += " inner join production_events ep on ep.id_pro_eve=p.id_production_event_con";    
        sql += " inner join targets_types tob on tob.id_tar_typ=p.target_type_con";    
        sql += " left join diseases enf on enf.id_dis=p.id_disease_con and enf.status_dis=1";    
        sql += " left join pests pl on pl.id_pes=p.id_pest_con and pl.status_pes=1";    
        sql += " left join weeds mal on mal.id_wee=p.id_weed_con and mal.status_wee=1";    
        sql += " left join chemicals_controls tp on tp.id_che_con=p.chemical_product_used_con";    
        sql += " left join organic_controls cr on cr.id_org_con=p.organic_product_used_con";    
        sql += " left join dose_units ud on ud.id_dos_uni=p.dose_units_con and ud.status_dos_uni=1";    
        sql += " inner join log_entities le on le.id_object_log_ent=p.id_con and le.table_log_ent='controls' and le.action_type_log_ent='C'";
        sql += " where p.status=1 and ep.status=1";
        if (args.containsKey("idEvent")) {
            sql += " and p.id_production_event_con="+args.get("idEvent");
        }
		if (args.containsKey("idEntUser")) {
			sqlAdd += " and le.id_entity_log_ent="+args.get("idEntUser");
		}
		sqlAdd += " order by p.id_con ASC";
		sql += sqlAdd;
//        args.get("countTotal");
        
//        int valIni = Integer.parseInt(String.valueOf(args.get("pageNow")));
//        int maxResults = Integer.parseInt(String.valueOf(args.get("maxResults")));
//        if(valIni!=1){
//            valIni = (valIni-1)*maxResults+1;
//        }    
//        events.toArray();
        try {
            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql);
//            System.out.println("sql->"+query.list().size());
            events = query.list(); 			

            for (Object[] data : events) {
                String nameObj = "";
                int targetTy = Integer.parseInt(String.valueOf(data[0]));

                if (targetTy==1) {
                    nameObj = (!String.valueOf(data[1]).equals("null") ? String.valueOf(data[1]) : String.valueOf(data[15]));
                } else if (targetTy==2) {
                    nameObj = (!String.valueOf(data[2]).equals("null") ? String.valueOf(data[2]) : String.valueOf(data[16]));
                } else if (targetTy==3) {
                    nameObj = (!String.valueOf(data[3]).equals("null") ? String.valueOf(data[3]) : String.valueOf(data[17]));
                }

                String nameChe = (!String.valueOf(data[4]).equals("null") ? String.valueOf(data[4]) : String.valueOf(data[5]));
                String nameOrg = (!String.valueOf(data[6]).equals("null") ? String.valueOf(data[6]) : String.valueOf(data[7]));
                HashMap temp = new HashMap();
                temp.put("idCon", data[8]);
                temp.put("dateCon", data[9]);
                temp.put("idTarTyp", targetTy);             
                temp.put("nameTarTyp", data[10]);             
                temp.put("nameConTyp", nameObj);                
                temp.put("chemCon", nameChe);
                temp.put("orgCon", nameOrg);
                String valUnit = "";
                if(data[12]!=null) {
                    valUnit = "-"+data[12];
                }
                temp.put("doseCon", data[11]+valUnit);                
//                temp.put("doseChemCon", (temp.get("chemCon").equals("null") || temp.get("chemCon").equals("")) ? "" : data[11]);              
//                temp.put("unitChemCon", (temp.get("chemCon").equals("null") || temp.get("chemCon").equals("")) ? "" : valUnit);                
//                temp.put("doseOrgCon", (temp.get("orgCon").equals("null") || temp.get("orgCon").equals("")) ? "" : data[11]);
//                temp.put("unitOrgCon", (temp.get("orgCon").equals("null") || temp.get("orgCon").equals("")) ? "" : valUnit);
                temp.put("cleaning", (String.valueOf(data[13]).equals("1")) ? "Si" : "No");
                temp.put("frequence", data[14]);             
                temp.put("conType", data[18]);             
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
    
    public Controls objectById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        Controls event = null;
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            String hql  = "FROM Controls E WHERE E.idCon = :id_con";
            Query query = session.createQuery(hql);
            query.setParameter("id_con", id);
            event = (Controls)query.uniqueResult();
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

    public void save(Controls event) {
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

    public void delete(Controls event) {
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
    
    public static String getControls(Integer idCrop) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Object[]> eventsTotal = null;
        List<Object[]> events = null;
        Transaction tx = null;
        String result = "[";
        
        String sql = "";      
        sql += "select DATE_FORMAT(c.date_con,'%Y-%m-%d') as dateCon, c.target_type_con, c.id_pest_con, c.id_weed_con,";
        sql += " c.id_disease_con, c.control_type_con";
        sql += " from controls c"; 
        sql += " where c.status=1";
        sql += " and c.id_production_event_con="+idCrop;
//        System.out.println("sql->"+sql);
        int numCaj    = 0;
        int totResult = 0;
        try {
            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql);
            events    = query.list(); 
            totResult = events.size();

            for (Object[] data : events) {
                numCaj++;
                if (totResult==numCaj) {
                    result += "{\"survey_solution[236]\":\""+data[0]+"\","+
                           "\"survey_solution[237]\":\""+data[1]+"\","+ 
                           "\"survey_solution[383]\":\""+data[2]+"\","+ 
                           "\"survey_solution[384]\":\""+data[3]+"\","+ 
                           "\"survey_solution[385]\":\""+data[4]+"\","+ 
                           "\"survey_solution[239]\":\""+data[5]+"\","+ 
                           "\"subform_id\":\""+49+"\","+ 
                           "\"idx\":"+numCaj+"}"; 
                } else {
                    result += "{\"survey_solution[236]\":\""+data[0]+"\","+
                           "\"survey_solution[237]\":\""+data[1]+"\","+ 
                           "\"survey_solution[383]\":\""+data[2]+"\","+ 
                           "\"survey_solution[384]\":\""+data[3]+"\","+ 
                           "\"survey_solution[385]\":\""+data[4]+"\","+ 
                           "\"survey_solution[239]\":\""+data[5]+"\","+ 
                           "\"subform_id\":\""+49+"\","+ 
                           "\"idx\":"+numCaj+"},"; 
                }         
            }
            result += "]";
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
    
    public static String getControlsBeans(Integer idCrop) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Object[]> eventsTotal = null;
        List<Object[]> events = null;
        Transaction tx = null;
        String result = "[";
        
        String sql = "";      
        sql += "select DATE_FORMAT(c.date_con,'%Y-%m-%d') as dateCon, c.target_type_con, c.id_pest_con, c.id_weed_con,";
        sql += " c.id_disease_con, c.control_type_con";
        sql += " from controls c"; 
        sql += " where c.status=1";
        sql += " and c.id_production_event_con="+idCrop;
//        System.out.println("sql->"+sql);
        int numCaj    = 0;
        int totResult = 0;
        try {
            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql);
            events    = query.list(); 
            totResult = events.size();

            for (Object[] data : events) {
                numCaj++;
                if (totResult==numCaj) {
                    result += "{\"survey_solution[436]\":\""+data[0]+"\","+
                           "\"survey_solution[437]\":\""+data[1]+"\","+ 
                           "\"survey_solution[438]\":\""+data[2]+"\","+ 
                           "\"survey_solution[439]\":\""+data[3]+"\","+ 
                           "\"survey_solution[440]\":\""+data[4]+"\","+ 
                           "\"survey_solution[441]\":\""+data[5]+"\","+ 
                           "\"subform_id\":\""+61+"\","+ 
                           "\"idx\":"+numCaj+"}"; 
                } else {
                    result += "{\"survey_solution[436]\":\""+data[0]+"\","+
                           "\"survey_solution[437]\":\""+data[1]+"\","+ 
                           "\"survey_solution[438]\":\""+data[2]+"\","+ 
                           "\"survey_solution[439]\":\""+data[3]+"\","+ 
                           "\"survey_solution[440]\":\""+data[4]+"\","+ 
                           "\"survey_solution[441]\":\""+data[5]+"\","+ 
                           "\"subform_id\":\""+61+"\","+ 
                           "\"idx\":"+numCaj+"},"; 
                }         
            }
            result += "]";
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
