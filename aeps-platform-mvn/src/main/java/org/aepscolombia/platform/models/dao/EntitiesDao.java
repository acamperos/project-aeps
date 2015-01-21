package org.aepscolombia.platform.models.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.aepscolombia.platform.controllers.ActionField;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.Entities;
import org.aepscolombia.platform.models.entity.EntitiesTypes;
import org.aepscolombia.platform.models.entity.UserEntity;
import org.aepscolombia.platform.models.entityservices.SfGuardUser;
import org.aepscolombia.platform.util.GlobalFunctions;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Clase EntitiesDao
 *
 * Contiene los metodos para interactuar con la tabla Entities de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class EntitiesDao {

    public Entities checkEntityIdent(String typeIdent, String ident) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        Entities event = null;
        Transaction tx = null;
        String sql = "";

        sql += "select usr.id_ent, usr.id_project_ent, usr.entity_type_ent, usr.document_number_ent, usr.document_type_ent, usr.document_issue_place_ent,"; 	
        sql += "usr.name_ent, usr.in_association_ent, usr.email_ent, usr.email_2_ent, usr.address_ent, usr.id_municipality_ent,"; 	
        sql += "usr.cellphone2_ent, usr.phone_ent, usr.cellphone_ent, usr.status, usr.gender_ent, usr.civil_status_ent,"; 	
        sql += "usr.validation_number_ent, usr.education_level_ent, usr.date_of_birth_ent, usr.first_name_1_ent, usr.person_type_ent,"; 	
        sql += "usr.first_name_2_ent, usr.last_name_1_ent, usr.last_name_2_ent, usr.agent_name_ent, usr.page_link_ent, usr.created_by";
        
//        sql += "select usr.id_usr, usr.name_user_usr, usr.password_usr, usr.cod_validation_usr, usr.status";
        sql += " from entities usr";
        if (!typeIdent.equals("")) sql += " where usr.status=1 and usr.document_type_ent='"+typeIdent+"'";
        if (!ident.equals("")) sql += " and usr.document_number_ent="+ident;
//        System.out.println("sql->"+sql);
        
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("usr", Entities.class);
            event = (Entities)query.uniqueResult();
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
    
    public static String getEntityType(Integer idUser) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        EntitiesTypes event = null;
        Transaction tx = null;
        String sql = "";
        String valEntity = "";

        sql += "select entTy.id_ent_typ, entTy.name_ent_typ, entTy.status_ent_typ";
        sql += " from entities_types entTy";
        sql += " inner join entities ent on ent.entity_type_ent=entTy.id_ent_typ";
        sql += " inner join user_entity usr on usr.id_entity_usr_ent=ent.id_ent and ent.status=1 and usr.status=1";
        if (idUser!=null) sql += " and usr.id_user_usr_ent="+idUser;
//        System.out.println("sql->"+sql);
        
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("entTy", EntitiesTypes.class);
            event = (EntitiesTypes)query.uniqueResult();
            valEntity = event.getNameEntTyp();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return valEntity;
    }
    
    public static Integer getEntityTypeId(Integer idUser) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        EntitiesTypes event = null;
        Transaction tx = null;
        String sql = "";
        Integer valEntity=0;

        sql += "select entTy.id_ent_typ, entTy.name_ent_typ, entTy.status_ent_typ";
        sql += " from entities_types entTy";
        sql += " inner join entities ent on ent.entity_type_ent=entTy.id_ent_typ";
        sql += " inner join user_entity usr on usr.id_entity_usr_ent=ent.id_ent and ent.status=1 and usr.status=1";
        if (idUser!=null) sql += " and usr.id_user_usr_ent="+idUser;
//        System.out.println("sql->"+sql);
        
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("entTy", EntitiesTypes.class);
            event = (EntitiesTypes)query.uniqueResult();
            valEntity = event.getIdEntTyp();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return valEntity;
    }
    
    public Entities findById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        Entities event = null;
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            String hql  = "FROM Entities E WHERE E.idEnt = :id_ent";
            Query query = session.createQuery(hql);
            query.setParameter("id_ent", id);
            event = (Entities)query.uniqueResult();
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

    public List<Entities> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Entities> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from Entities");
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

    public void save(Entities event) throws HibernateException {
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

    public void delete(Entities event) {
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
    
    public void setInfoMongo() 
    {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        
        List<Object[]> events = null;
        Transaction tx = null;
        
        String sql = "";
        
        sql += "select p.id_pro, e.id_ent, e.document_number_ent, e.document_type_ent, e.name_ent, e.document_issue_place_ent,";
        sql += " e.cellphone_ent, e.cellphone2_ent, e.phone_ent, e.address_ent, m.name_mun, e.email_ent,";
        sql += " e.email_2_ent, e.in_association_ent, e.id_project_ent, e.status, e.validation_number_ent, m.id_department_mun,";
        sql += " m.id_mun, e.first_name_1_ent, e.first_name_2_ent, e.last_name_1_ent, e.last_name_2_ent, ent.email_ent as emailUser";
        sql += " from producers p";
        sql += " inner join entities e on (p.id_entity_pro=e.id_ent)";	
        sql += " inner join municipalities m on (m.id_mun=e.id_municipality_ent)";
        sql += " inner join log_entities le on le.id_object_log_ent = p.id_pro AND le.table_log_ent = 'producers'";
        sql += " inner join entities ent on le.id_entity_log_ent = ent.ID_ENT";
        sql += " where le.id_entity_log_ent in (3,4,5,6,8,200,201,202,665,706,707,708,709,710,711,712,713,714,715,823)";
        sql += " and le.action_type_log_ent = 'C'";
        sql += " and le.id_object_log_ent not in (select le.id_object_log_ent from log_entities le where le.id_entity_log_ent in (3,4,5,6,8,200,201,202,665,706,707,708,709,710,711,712,713,714,715,823) and le.action_type_log_ent = 'D' AND le.table_log_ent = 'producers')";
        sql += " and e.status=1";
        
        try {
            tx = session.beginTransaction();
            Query query  = session.createSQLQuery(sql);            
            events = query.list();         
            
            for (Object[] data : events) {
//                System.out.println(data);
                HashMap temp = new HashMap();
                temp.put("id_producer", data[0]);
                temp.put("id_entity", data[1]);
                temp.put("document", data[2]);
                temp.put("type_document", data[3]);
                temp.put("name", data[4]);
                temp.put("site_document", data[5]);
                temp.put("cellphone", data[6]);
                temp.put("cellphone_2", data[7]);
                temp.put("phone", data[8]);
                temp.put("direction", data[9]);
                temp.put("city", data[10]);
                
                if (data[11]==null) data[11]="";                
                if (data[12]==null) data[12]="";                
                if (data[19]==null) data[19]="";                
                if (data[20]==null) data[20]="";                
                if (data[21]==null) data[21]="";                
                if (data[22]==null) data[22]="";               
                
                temp.put("e_mail_1", data[11]);
                temp.put("e_mail_2", data[12]);
                temp.put("associate", data[13]);
                temp.put("id_project", data[14]);
                temp.put("status", data[15]);
                temp.put("digit", data[16]);
                temp.put("id_dep", data[17]);                
                temp.put("id_mun", data[18]);                
                temp.put("name_1", data[19]);
                temp.put("name_2", data[20]);
                temp.put("last_name_1", data[21]);
                temp.put("last_name_2", data[22]);   
                
                String emailUser = String.valueOf(data[23]);
                
                SfGuardUserDao sfDao = new SfGuardUserDao();
                SfGuardUser sfUser   = sfDao.getUserByLogin(null, emailUser, "");
                Integer idUserMobile = null;
                if (sfUser!=null) {
                    idUserMobile = sfUser.getId().intValue();
                }
                
                HashMap valInfo = new HashMap();
                valInfo.put("entId", temp.get("id_entity"));
                valInfo.put("prodId", temp.get("id_producer"));
                valInfo.put("docType", temp.get("type_document"));
                valInfo.put("docNum", temp.get("document"));
                valInfo.put("firstName1", temp.get("name_1"));
                valInfo.put("firstName2", temp.get("name_2"));
                valInfo.put("lastName1", temp.get("last_name_1"));
                valInfo.put("lastName2", temp.get("last_name_2"));
                valInfo.put("direction", temp.get("direction"));
                valInfo.put("phone", temp.get("phone"));
                valInfo.put("cellphone", temp.get("cellphone"));
                valInfo.put("email", temp.get("e_mail_1"));
                valInfo.put("validation", temp.get("digit"));
                valInfo.put("department", temp.get("id_dep"));
                valInfo.put("municipality", temp.get("id_mun"));
                valInfo.put("userMobileId", idUserMobile);      

                BasicDBObject queryMongo = new BasicDBObject();
                queryMongo.put("InsertedId", ""+temp.get("id_entity"));
                queryMongo.put("form_id", "4");

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
                jsonField          = GlobalFunctions.generateJSONProducer(valInfo);

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
    
}
