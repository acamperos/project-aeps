package org.aepscolombia.platform.models.dao;

import java.util.List;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.aepscolombia.platform.models.entity.Users;
import org.aepscolombia.platform.util.HibernateUtil;
import java.util.HashMap;
import org.aepscolombia.platform.models.entity.Privileges;
import org.aepscolombia.platform.models.entity.UserEntity;

/**
 * Clase UsersDao
 *
 * Contiene los metodos para interactuar con la tabla Users de la base de datos (BD)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class UsersDao 
{    
    
    /**
     * Encargado de obtener verificar si un usuario ya se encuentra registrado en el sistema
     * @param username:  Nombre del usuario
     * @return Objeto del usuario o vacio (NULL) en caso de no encontrar nada
     */
    public Users checkUsername(String username) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Users events = null;
        Transaction tx = null;
        String sql = "";        

        sql += "select usr.id_usr, usr.name_user_usr, usr.salt_usr, usr.password_usr, usr.country_usr, usr.last_in_usr, usr.cod_validation_usr, usr.status, usr.created_by";
        sql += " from users usr";
        sql += " where usr.name_user_usr='"+username+"'";
//        System.out.println("sql->"+sql);
        
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("usr", Users.class);
            events = (Users)query.uniqueResult();
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
    
    /**
     * Encargado de obtener la entidad del usuario que se encuentra registrado en el sistema
     * @param idUser:  Identificacion del usuario
     * @return Identificacion (Int) de la entidad
     */
    public static Integer getEntitySystem(Integer idUser) 
	{
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session  = sessions.openSession();
        Integer idEnt    = null;
        UserEntity user  = null;
        Transaction tx = null;
        String sql = "";        
        sql  = "select ue.id_usr_ent, ue.id_project_usr_ent, ue.id_user_usr_ent, ue.id_entity_usr_ent, ue.status";
        sql += " from user_entity ue";
		sql += " where ue.status=1";		
        sql += " and ue.id_user_usr_ent="+idUser;
//        System.out.println("sql->"+sql);
        
        try {
            tx    = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("ue", UserEntity.class);
            user  = (UserEntity) query.uniqueResult();
            idEnt = user.getEntities().getIdEnt();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return idEnt;
	}

    /**
     * Encargado de obtener la informacion de un usuario apartir del nombre de usuario y/o su contraseña
     * @param username:  Nombre del usuario
     * @param password:  Contraseña del usuario
     * @return Objeto del usuario o vacio (NULL) en caso de no encontrar nada
     */
    public Users getUserByLogin(String username, String password) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Users events = null;
        Transaction tx = null;
        String sql = "";        

        sql += "select usr.id_usr, usr.name_user_usr, usr.salt_usr, usr.password_usr, usr.country_usr, usr.last_in_usr, usr.cod_validation_usr, usr.status, usr.created_by";
        sql += " from users usr";
//        sql += " where usr.status=1";
        sql += " where usr.status<>0";
        if (username!=null && username!="") {
            sql += " and usr.name_user_usr='"+username+"'";
        }  
        if (password!=null && password!="") {
            sql += " and usr.password_usr='"+password+"'";
        }  
//        System.out.println("sql->"+sql);
        
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("usr", Users.class);
            events = (Users)query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
//            if (tx != null) {
//                tx.rollback();
//            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return events;
    }
    
    /**
     * Encargado de obtener la informacion de un usuario apartir del nombre de usuario y su codigo de validacion
     * @param username:  Nombre del usuario
     * @param codValidation:  Codigo de validacion del usuario
     * @return Objeto del usuario o vacio (NULL) en caso de no encontrar nada
     */
    public Users getUserByCode(String username, String codValidation) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Users events = null;
        Transaction tx = null;
        String sql = "";        

        sql += "select usr.id_usr, usr.name_user_usr, usr.salt_usr, usr.password_usr, usr.country_usr, usr.last_in_usr, usr.cod_validation_usr, usr.status, usr.created_by";
        sql += " from users usr ";
        sql += " where usr.status=2 ";
        if (username!=null) {
            sql += " and usr.name_user_usr='"+username+"'";
        }  
        if (codValidation!=null) {
            sql += " and usr.cod_validation_usr='"+codValidation+"'";
        } else {
            sql += " and usr.cod_validation_usr=''";
        } 
        
//        System.out.println("sql->"+sql);
        
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("usr", Users.class);
            events = (Users)query.uniqueResult();
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

    /**
     * Encargado de obtener la informacion de un usuario apartir del nombre de usuario y su codigo de validacion,
     * siempre y cuando estos datos se encuentren diligenciados, en caso contrario se le informara al usuario de los fallo presentados
     * @param username:  Nombre del usuario
     * @param password:  Contraseña del usuario
     * @return Objeto del usuario o vacio (NULL) en caso de no encontrar nada
     */
    public Users login(String username, String password) {
      if ((username != null || username.isEmpty()) && (password != null || password.isEmpty())) {
//        System.out.println("md5->"+password);  
//        Users userFound = this.getUserByLogin(username, GlobalFunctions.generateMD5(password));
        Users userFound = this.getUserByLogin(username, password);
        if (userFound != null) {
//          temp.setPasswordUsr(GlobalFunctions.generateMD5(password));
//          if (userFound.getPasswordUsr().equals(temp.getPasswordUsr())) {
            return userFound;
//          }
        }
      }
      return null;
    }

    public List findByParams(HashMap args) {
        return null;
    }

    public List<Users> findAll() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        List<Users> events = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from Users");
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
    
    public Users objectById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();

        String sql  = "";        
        Users event = null;
        Transaction tx = null;
        sql += "select usr.id_usr, usr.name_user_usr, usr.salt_usr, usr.password_usr, usr.country_usr, usr.last_in_usr, usr.cod_validation_usr, usr.status, usr.created_by";
        sql += " from users usr";
        sql += " where usr.id_usr="+id;
        try {
            tx = session.beginTransaction();
//            Query query  = session.createSQLQuery(sql);
//            event = (Users) session.load(Users.class, id);
            Query query = session.createSQLQuery(sql).addEntity("usr", Users.class);
            event = (Users)query.uniqueResult();
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
    
    public HashMap findById(Integer id) {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        
        List<Object[]> events = null;
        Transaction tx = null;
        HashMap result = new HashMap();
        
        String sql = "";        
        
        sql += "select usr.id_usr, usr.name_user_usr, ent.email_ent, ";
        sql += "ent.cellphone_ent, ent.entity_type_ent, ent.document_number_ent, ent.document_type_ent, ent.name_ent ";
        sql += "from users usr ";
        sql += "inner join user_entity usrEnt on usrEnt.id_usr_ent=usr.id_usr ";
        sql += "inner join users_profiles usrPer on usrPer.id_users_usr_pro=usr.id_usr ";
        sql += "inner join profiles per on per.id_pro=usrPer.id_profile_usr_pro ";
        sql += "inner join entities ent on ent.id_ent=usrEnt.id_entity_usr_ent ";
        sql += "where usr.status=1 and per.status=1 ";
        sql += "and usrEnt.status=1 and ent.status=1";
        if (id!=null) {
            sql += " and usr.id_usr="+id;
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
                temp.put("id_user", data[0]);
                temp.put("name_user", data[1]);
                temp.put("email_user", data[2]);
                temp.put("cel_user", data[3]);
                temp.put("type_ent_user", data[4]);                
                temp.put("num_doc_user", data[5]);
                temp.put("type_doc_user", data[6]);
                temp.put("name_ent_user", data[7]);                
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
    
//    public void saveFinPro(Integer idFin, Integer idPro) {
//        SessionFactory sessions = HibernateUtil.getSessionFactory();
//        Session session = sessions.openSession();
//        Transaction tx = null;
////        HibernateUtil.getInstanceConnection();
//
//        try {
//            tx = session.beginTransaction();
//            String query = "insert into fincas_productores values (?,?)";
//
//            // Ejecutamos la query y obtenemos el resultado.
//            PreparedStatement stmt;
//            stmt = HibernateUtil.getInstanceConnection().prepareStatement(query);
//            stmt.setInt(1, idFin);
//            stmt.setInt(2, idPro);
//            
//            stmt.executeUpdate();
//            stmt.close();
//            HibernateUtil.closeConnection();
//
//            tx.commit();
//        } catch (HibernateException e) {
//            if (tx != null) {
//                tx.rollback();
//            }
//            e.printStackTrace();
//        } catch (SQLException ex) {
//            if (tx != null) {
//                tx.rollback();
//            }
//            ex.printStackTrace();
//        }
//        
////        return event;
//    }
    
     /**
     * Encargado de verificar si un usuario cuenta con permiso determinado en el sistema
     * @param idUser:  Identificacion del usuario
     * @param perUser:  Permiso del usuario
     * @return Identificacion (Int) de la entidad
     */
    public static Boolean getPrivilegeUser(Integer idUser, String perUser) 
	{
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session  = sessions.openSession();
        Privileges priv  = null;
        Transaction tx   = null;
        Boolean result   = false;
        String sql = "";        
        sql  = "select pv.id_pri, pv.route_pri, pv.name_pri, pv.description_pri";
        sql += " from users_profiles ue";
		sql += " inner join profiles pr on ue.id_profile_usr_pro=pr.id_pro"; 
		sql += " inner join profiles_privileges pp on pp.id_profile_pro_pri=pr.id_pro"; 
		sql += " inner join privileges pv on pv.id_pri=pp.id_privilege_pro_pri"; 
		sql += " where ue.id_users_usr_pro="+idUser;
		sql += " and pv.route_pri='"+perUser+"'";
//        System.out.println("sql->"+sql);
        
        try {
            tx    = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity("pv", Privileges.class);
            priv  = (Privileges) query.uniqueResult();
            if (priv!=null) result = true;
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

    public void save(Users event) {
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

    public void delete(Users event) {
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
