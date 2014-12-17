/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aepscolombia.platform.controllers;

import com.opensymphony.xwork2.ActionContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.aepscolombia.platform.models.dao.IrrigationDao;

import org.aepscolombia.platform.models.dao.LogEntitiesDao;
import org.aepscolombia.platform.models.dao.ProductionEventsDao;
import org.aepscolombia.platform.models.dao.IrrigationsTypesDao;
import org.aepscolombia.platform.models.dao.SfGuardUserDao;
import org.aepscolombia.platform.models.dao.SowingDao;
import org.aepscolombia.platform.models.dao.UsersDao;
import org.aepscolombia.platform.models.entity.Irrigation;

import org.aepscolombia.platform.models.entity.LogEntities;
import org.aepscolombia.platform.models.entity.Preparations;
import org.aepscolombia.platform.models.entity.ProductionEvents;
import org.aepscolombia.platform.models.entity.IrrigationsTypes;
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
 * Clase ActionIrr
 *
 * Contiene los metodos para interactuar con el modulo de riegos
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ActionIrr extends BaseAction {
    
    //Atributos del formulario 
    /**
     * Atributos provenientes del formulario
     */
    private int idCrop;    
    private int idIrr;    
    private int typeCrop;
    private List<HashMap> listIrr;
    private Users user;
    private Integer idEntSystem;    
    private Integer idUsrSystem;    

    private Irrigation irr = new Irrigation();
    private Sowing sowing = new Sowing();
    private List<IrrigationsTypes> type_irr_typ;
    private UsersDao usrDao;

    //Metodos getter y setter por cada variable del formulario 
    /**
     * Metodos getter y setter por cada variable del formulario
     */
    public Irrigation getIrr() {
        return irr;
    }

    public void setIrr(Irrigation irr) {
        this.irr = irr;
    }       

    public Sowing getSowing() {
        return sowing;
    }

    public void setSowing(Sowing sowing) {
        this.sowing = sowing;
    }      

    public int getIdIrr() {
        return idIrr;
    }

    public void setIdIrr(int idIrr) {
        this.idIrr = idIrr;
    }   

    public int getIdCrop() {
        return idCrop;
    }

    public void setIdCrop(int idCrop) {
        this.idCrop = idCrop;
    }  

    public List<IrrigationsTypes> getType_irr_typ() {
        return type_irr_typ;
    }

    public void setType_irr_typ(List<IrrigationsTypes> type_irr_typ) {
        this.type_irr_typ = type_irr_typ;
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

    public List<HashMap> getListIrr() {
        return listIrr;
    }
    
    
    
    //Atributos generales de clase
    /**
     * Atributos generales de clase
     */
    
    private ProductionEventsDao cropDao    = new ProductionEventsDao();
    private IrrigationDao irrDao           = new IrrigationDao();
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
     * Metodo encargado de validar el formulario de un riego
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
//            required.put("irr.useIrrigationIrr", irr.getUseIrrigationIrr());           
            
//            if (irr.getUseIrrigationIrr()!=null && irr.getUseIrrigationIrr()) {
                required.put("irr.dateIrr", irr.getDateIrr());
//                required.put("irr.amountIrr", irr.getAmountIrr());
                required.put("irr.irrigationsTypes.idIrrTyp", irr.getIrrigationsTypes().getIdIrrTyp());
//            }           			       	               
            
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
            
            Date dateSowing = null;
//            if (sowing.getDateSow()!=null) {
            if (sowing != null) {
                dateSowing = sowing.getDateSow();
                String dmySow  = new SimpleDateFormat("dd/MM/yyyy").format(sowing.getDateSow());
		
                if (!dmySow.equals("") && irr.getDateIrr()!=null) {

                    Integer valDiffBef = GlobalFunctions.compareDateBeforeSowing(irr.getDateIrr(), sowing.getDateSow());
                    Integer valDiffAff = GlobalFunctions.compareDateAfterSowing(irr.getDateIrr(), sowing.getDateSow(), 0);
                    if (valDiffBef==2 && valDiffAff==2) {
                        addFieldError("irr.dateIrr", "Dato invalido");                
                        addActionError("Se ingreso una fecha de riego que no se encuentra 6 meses antes de la siembra o 10 meses despues de la siembra ("+dmySow+")");
                    }				

                }	
            }

            if (irr.getAmountIrr()!=null) {
                if (irr.getAmountIrr()<0 || irr.getAmountIrr()>1000) {
                    addFieldError("irr.amountIrr", "Dato invalido valor entre 0 y 1000");
                    addActionError("Se ingreso una cantidad aportada por hectarea invalida, por favor ingresar un valor entre 0 y 1000");
                }
            }
            sowing=null;            
        }
    }     
    
    /**
     * Encargado de buscar las coincidencias de un formulario de busqueda, para cada uno de los
     * riegos registrados a un usuario
     * @param valName:  Nombre del valor a buscar
     * @param valId:    Identificacion del valor a buscar
     * @param selected: Valor seleccionado
     * @return lista de riegos
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
        listIrr = irrDao.findByParams(findParams);
        return SUCCESS;
    }
    

    /**
     * Encargado de mostrar en un formulario la informacion de un riego de la identificacion
     * @param idIrr:  Identificacion del riego
     * @return Informacion del riego
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
            this.setIdIrr(Integer.parseInt(this.getRequest().getParameter("idIrr")));
        } catch (NumberFormatException e) {
//            LOG.error("There was an error trying to parse the activityId parameter");
            this.setIdIrr(-1);
        }

        this.setType_irr_typ(new IrrigationsTypesDao().findAllByTypeCrop(tyCro));
        if (this.getIdIrr()!= -1) {
            irr = irrDao.objectById(this.getIdIrr());
        } 
        return SUCCESS;
    }

    /**
     * Encargado de guardar la informacion suministrada por el usuario para un riego
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
            
            String dmy    = new SimpleDateFormat("yyyy-MM-dd").format(irr.getDateIrr());
            Date dateIrr = new SimpleDateFormat("yyyy-MM-dd").parse(dmy);
            
            irr.setUseIrrigationIrr(true);
            irr.setProductionEvents(new ProductionEvents(idCrop));
            irr.setDateIrr(dateIrr);          
//            if (sowing.getChemicalsSowing().getIdCheSow()==-1) {
//                sowing.setChemicalsSowing(null);
//            }
//            
//            if (sowing.getDoseUnits().getIdDosUni()==-1) {
//                sowing.setDoseUnits(null);
//            }        
            irr.setStatus(true);
            session.saveOrUpdate(irr);
            
            LogEntities log = new LogEntities();
            log.setIdLogEnt(null);
            log.setIdEntityLogEnt(idEntSystem);
            log.setIdObjectLogEnt(irr.getIdIrr());
            log.setTableLogEnt("irrigation");
            log.setDateLogEnt(new Date());
            log.setActionTypeLogEnt(action);
            session.saveOrUpdate(log);           
            tx.commit();           
            state = "success";            
            if (action.equals("C")) {
                info  = "El riego ha sido agregado con exito";
//                return "list";
            } else if (action.equals("M")) {
                info  = "El riego ha sido modificado con exito";
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
            info  = "Fallo al momento de agregar un riego";
        } catch (ParseException e) { 
        
        } finally {
            session.close();
        }  
        
//        return ERROR;
        return "states";
    }
    
    /**
     * Encargado de borrar la informacion de un riego apartir de su identificacion
     * @param idIrr:  Identificacion del riego
     * @return Estado del proceso
     */
    public String delete() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "crop/delete")) {
            return BaseAction.NOT_AUTHORIZED;
        }
        Integer idIrr = 0;
        try {
            idIrr = Integer.parseInt(this.getRequest().getParameter("idIrr"));
        } catch (NumberFormatException e) {
            idIrr = -1;
        }
        
        if (idIrr==-1) {
            state = "failure";
            info  = "Fallo al momento de obtener la informacion a borrar";
            return "states";
        }
        
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();            
            Irrigation pr = irrDao.objectById(idIrr);      
            pr.setStatus(false);
//            session.delete(pro);        
            session.saveOrUpdate(pr);
            
            LogEntities log = new LogEntities();
            log.setIdLogEnt(null);
            log.setIdEntityLogEnt(idEntSystem);
            log.setIdObjectLogEnt(pr.getIdIrr());
            log.setTableLogEnt("irrigation");
            log.setDateLogEnt(new Date());
            log.setActionTypeLogEnt("D");
            session.saveOrUpdate(log);
//            logDao.save(log);
            tx.commit();         
            state = "success";
            info  = "El riego ha sido borrado con exito";
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            state = "failure";
            info  = "Fallo al momento de borrar un riego";
        } finally {
            session.close();
        }      
        
        return "states";
//        return SUCCESS;
    }
    
}