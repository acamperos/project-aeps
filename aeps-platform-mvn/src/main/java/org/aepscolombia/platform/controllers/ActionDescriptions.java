/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aepscolombia.platform.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.aepscolombia.platform.models.dao.DescriptionsProductionEventDao;

import org.aepscolombia.platform.models.dao.LogEntitiesDao;
import org.aepscolombia.platform.models.dao.ProductionEventsDao;
import org.aepscolombia.platform.models.dao.SfGuardUserDao;
import org.aepscolombia.platform.models.dao.SowingDao;
import org.aepscolombia.platform.models.dao.UsersDao;
import org.aepscolombia.platform.models.entity.DescriptionsProductionEvent;

import org.aepscolombia.platform.models.entity.LogEntities;
import org.aepscolombia.platform.models.entity.ProductionEvents;
import org.aepscolombia.platform.models.entity.ResidualsManagement;
import org.aepscolombia.platform.models.entity.Sowing;
import org.aepscolombia.platform.models.entity.Users;
import org.aepscolombia.platform.models.entityservices.SfGuardUser;
import org.aepscolombia.platform.util.APConstants;
import org.aepscolombia.platform.util.GlobalFunctions;
import org.aepscolombia.platform.util.HibernateUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Clase ActionDescriptions
 *
 * Contiene los metodos para interactuar con el modulo de observaciones en un cultivo
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ActionDescriptions extends BaseAction {
    
    //Atributos del formulario 
    /**
     * Atributos provenientes del formulario
     */
    private int idCrop;    
    private int idDesPro;    
    private int typeCrop;
    private List<HashMap> listDesPro;
    private Users user;
    private Integer idEntSystem;    
    private Integer idUsrSystem;    

    private DescriptionsProductionEvent desPro = new DescriptionsProductionEvent();
    private Sowing sowing = new Sowing();
    private UsersDao usrDao;

    //Metodos getter y setter por cada variable del formulario 
    /**
     * Metodos getter y setter por cada variable del formulario
     */
    public DescriptionsProductionEvent getDesPro() {
        return desPro;
    }

    public void setDesPro(DescriptionsProductionEvent desPro) {
        this.desPro = desPro;
    }        
    
    public Sowing getSowing() {
        return sowing;
    }

    public void setSowing(Sowing sowing) {
        this.sowing = sowing;
    }      

    public int getIdDesPro() {
        return idDesPro;
    }

    public void setIdDesPro(int idDesPro) {
        this.idDesPro = idDesPro;
    }   

    public int getIdCrop() {
        return idCrop;
    }

    public void setIdCrop(int idCrop) {
        this.idCrop = idCrop;
    }  
    
    public int getTypeCrop() {
        return typeCrop;
    }

    public void setTypeCrop(int typeCrop) {
        this.typeCrop = typeCrop;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }       
    
    public List<HashMap> getListDesPro() {
        return listDesPro;
    }
    
    //Atributos generales de clase
    /**
     * Atributos generales de clase
     */
    
    private ProductionEventsDao cropDao    = new ProductionEventsDao();
    private DescriptionsProductionEventDao desDao = new DescriptionsProductionEventDao();
    private SowingDao sowDao      = new SowingDao();
    private LogEntitiesDao logDao = new LogEntitiesDao();
    
    private String state = "";
    private String info  = "";
    
    //Metodos getter y setter por cada variable general de la clase
    /**
     * Metodos getter y setter por cada variable general de la clase
     */    

    public String getState() {
        return state;
    }

//    @Override
    public String getInfo() {
        return info;
    }

    public LogEntitiesDao getLogDao() {
        return logDao;
    }

    public void setLogDao(LogEntitiesDao logDao) {
        this.logDao = logDao;
    }      
    
    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }       
    
    @Override
    public void prepare() throws Exception {
        user = (Users) this.getSession().get(APConstants.SESSION_USER);
        idEntSystem = UsersDao.getEntitySystem(user.getIdUsr());
        usrDao = new UsersDao();
        idUsrSystem = user.getIdUsr();
    }
    
    
    /**
     * Metodo encargado de validar el formulario de un manejo de rastrojo
     */
    @Override
    public void validate() {       
        /*
         * Se evalua dependiendo a la accion realizada:
         * 1) create: Al momento de guardar un registro por primera ves
         * 2) modify: Al momento de modificar un registro
         * 3) delete: Al momento de borrar un registro
         */
//        numberFormatter = NumberFormat.getNumberInstance(new Locale("en_US"));
//        quantityOut = numberFormatter.format(rasta.getLatitudRas());
        if (actExe.equals("create") || actExe.equals("modify")) {
            boolean enter = false;
            sowing = sowDao.objectById(this.getIdCrop());
            HashMap required = new HashMap();
            required.put("desPro.dateDesPro", desPro.getDateDesPro());
            required.put("desPro.obsDesPro", desPro.getObsDesPro());
            
            for (Iterator it = required.keySet().iterator(); it.hasNext();) {
                String sK = (String) it.next();
                String sV = String.valueOf(required.get(sK));
//                System.out.println(sK + " : " + sV);
                if (StringUtils.trim(sV).equals("null") || StringUtils.trim(sV)==null || StringUtils.trim(sV).equals("") || sV.equals("-1")) {
                    addFieldError(sK, "El campo es requerido");
                    enter = true;
                }
            }
            
            if (enter) {
                addActionError("Faltan campos por ingresar por favor digitelos");
            }
            
//            Date dateSowing = null;
////            if (sowing.getDateSow()!=null) {
//            if (sowing != null) {
//                dateSowing = sowing.getDateSow();
//                String dmySow  = new SimpleDateFormat("dd/MM/yyyy").format(sowing.getDateSow());
//﻿  ﻿  
////                if (sowing.getDateSow()!=null && prep.getDatePrep()!=null) {
//                if (!dmySow.equals("") && desPro.getDateDesPro()!=null) {
//
//                    Integer valDiffAff = GlobalFunctions.compareDateBeforeSowing(desPro.getDateDesPro(), sowing.getDateSow());
//                    if (valDiffAff==2) {
//        //﻿  ﻿  ﻿  ﻿  $fails[]  = $prefix.'date_harvest_crop';
//                        addFieldError("resMan.dateResMan", "Dato invalido");                
//                        addActionError("Se ingreso una fecha de trabajo de mas de 6 meses antes de la siembra ("+dmySow+")");
//                    }﻿  ﻿  ﻿  ﻿  
//
//                }﻿  
//            }
            sowing=null;            
        }
    }     
    
    /**
     * Encargado de buscar las coincidencias de un formulario de busqueda, para cada uno de las
     * preparaciones registradas a un usuario
     * @param valName:  Nombre del valor a buscar
     * @param valId:    Identificacion del valor a buscar
     * @param selected: Valor seleccionado
     * @return lista de observaciones en un cultivo
     */
    public String search() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "crop/list")) {
            return BaseAction.NOT_AUTHORIZED;
        }
        try {
            this.setIdCrop(Integer.parseInt(this.getRequest().getParameter("idCrop")));
        } catch (NumberFormatException e) {
//            LOG.error("There was an error trying to parse the activityId parameter");
            this.setIdCrop(-1);
        }
        
        HashMap findParams = new HashMap();        
        findParams.put("idEntUser", idEntSystem);
        findParams.put("idEvent", this.getIdCrop());
        listDesPro = desDao.findByParams(findParams);
        return SUCCESS;
    }
    

    /**
     * Encargado de mostrar en un formulario la informacion observada en un cultivo apartir de la identificacion
     * @param idDesPro:  Identificacion de la observacion en un cultivo
     * @return Informacion de la observacion en un cultivo
     */
    public String show() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "crop/create") || !usrDao.getPrivilegeUser(idUsrSystem, "crop/modify")) {
            return BaseAction.NOT_AUTHORIZED;
        }
        actExe = (String)(this.getRequest().getParameter("action"));
        try {
            this.setIdCrop(Integer.parseInt(this.getRequest().getParameter("idCrop")));
        } catch (NumberFormatException e) {
//            LOG.error("There was an error trying to parse the activityId parameter");
            this.setIdCrop(-1);
        }
        
        HashMap prod  = cropDao.findById(idCrop);
        Integer tyCro = Integer.parseInt(String.valueOf(prod.get("typeCrop")));
//        System.out.println("tyCro=>"+tyCro);
        
        try {
            this.setIdDesPro(Integer.parseInt(this.getRequest().getParameter("idDesPro")));
        } catch (NumberFormatException e) {
//            LOG.error("There was an error trying to parse the activityId parameter");
            this.setIdDesPro(-1);
        }
        
        if (this.getIdDesPro()!= -1) {
            desPro = desDao.objectById(this.getIdDesPro());
        } 
        return SUCCESS;
    }

    /**
     * Encargado de guardar la informacion suministrada por el usuario para una observacion en un cultivo
     * @return Estado del proceso
     */
    public String saveData() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "crop/create") || !usrDao.getPrivilegeUser(idUsrSystem, "crop/modify")) {
            return BaseAction.NOT_AUTHORIZED;
        }
        String action = "";
//        System.out.println("Entre a guardar la info");
        /*
         * Se evalua dependiendo a la accion realizada:
         * 1) create: Al momento de guardar un registro por primera ves
         * 2) modify: Al momento de modificar un registro
         * 3) delete: Al momento de borrar un registro
         */
        if (actExe.equals("create")) {
            action = "C";
        } else if (actExe.equals("modify")) {
            action = "M";
        }
        
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Transaction tx = null;        

        try {
            tx = session.beginTransaction();           
            
            String dmy    = new SimpleDateFormat("yyyy-MM-dd").format(desPro.getDateDesPro());
            Date dateDes  = new SimpleDateFormat("yyyy-MM-dd").parse(dmy);
            
            desPro.setProductionEvents(new ProductionEvents(idCrop));
            desPro.setDateDesPro(dateDes);          
            desPro.setStatus(true);
            session.saveOrUpdate(desPro);
            
            LogEntities log = new LogEntities();
            log.setIdLogEnt(null);
            log.setIdEntityLogEnt(idEntSystem);
            log.setIdObjectLogEnt(desPro.getIdDesPro());
            log.setTableLogEnt("descriptions");
            log.setDateLogEnt(new Date());
            log.setActionTypeLogEnt(action);
            session.saveOrUpdate(log);         
            tx.commit();           
            state = "success";            
            if (action.equals("C")) {
                info  = "La observacion ha sida agregada con exito";
//                return "list";
            } else if (action.equals("M")) {
                info  = "La observacion ha sida modificada con exito";
//                return "list";
            }
            HashMap prod  = cropDao.findById(idCrop);
            Integer tyCro = Integer.parseInt(String.valueOf(prod.get("typeCrop")));
            SfGuardUserDao sfDao = new SfGuardUserDao();
            SfGuardUser sfUser   = sfDao.getUserByLogin(user.getCreatedBy(), user.getNameUserUsr(), "");            
            GlobalFunctions.sendInformationCrop(idCrop, tyCro, sfUser.getId());
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
//            System.out.println("error->"+e.getMessage());
            state = "failure";
            info  = "Fallo al momento de agregar una descripcion";
        } catch (ParseException e) { 
        
        } finally {
            session.close();
        }  
        
//        return ERROR;
        return "states";
    }
    
    /**
     * Encargado de borrar la informacion de una observacion en un cultivo apartir de su identificacion
     * @param idDesPro:  Identificacion de la observacion en un cultivo
     * @return Estado del proceso
     */
    public String delete() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "crop/delete")) {
            return BaseAction.NOT_AUTHORIZED;
        }
        Integer idDesPro = 0;
        try {
            idDesPro = Integer.parseInt(this.getRequest().getParameter("idDesPro"));
        } catch (NumberFormatException e) {
            idDesPro = -1;
        }
        
        if (idDesPro==-1) {
            state = "failure";
            info  = "Fallo al momento de obtener la informacion a borrar";
            return "states";
        }
        
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();            
            DescriptionsProductionEvent pr = desDao.objectById(idDesPro);      
            pr.setStatus(false);
//            session.delete(pro);        
            session.saveOrUpdate(pr);
            
            LogEntities log = new LogEntities();
            log.setIdLogEnt(null);
            log.setIdEntityLogEnt(idEntSystem);
            log.setIdObjectLogEnt(pr.getIdDesPro());
            log.setTableLogEnt("descriptions");
            log.setDateLogEnt(new Date());
            log.setActionTypeLogEnt("D");
            session.saveOrUpdate(log);
//            logDao.save(log);
            tx.commit();         
            state = "success";
            info  = "La observacion ha sido borrada con exito";
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            state = "failure";
            info  = "Fallo al momento de borrar un observacion en un cultivo";
        } finally {
            session.close();
        }      
        
        return "states";
//        return SUCCESS;
    }
    
}