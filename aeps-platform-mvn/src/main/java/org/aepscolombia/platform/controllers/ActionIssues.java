/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aepscolombia.platform.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import org.aepscolombia.platform.models.dao.EntitiesDao;

import org.aepscolombia.platform.models.dao.LogEntitiesDao;
import org.aepscolombia.platform.models.dao.ProductionEventsDao;
import org.aepscolombia.platform.models.dao.SowingDao;
import org.aepscolombia.platform.models.dao.UsersDao;
import org.aepscolombia.platform.models.entity.Entities;

import org.aepscolombia.platform.models.entity.LogEntities;
import org.aepscolombia.platform.models.entity.Sowing;
import org.aepscolombia.platform.models.entity.TmpIssueReports;
import org.aepscolombia.platform.models.entity.Users;
import org.aepscolombia.platform.util.APConstants;
import org.aepscolombia.platform.util.GlobalFunctions;
import org.aepscolombia.platform.util.HibernateUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Clase ActionIssues
 *
 * Contiene los metodos para interactuar con el modulo de reporte de problemas 
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ActionIssues extends BaseAction {
    
    //Atributos del formulario 
    /**
     * Atributos provenientes del formulario
     */
    private int idCrop;    
    private int idIss;    
    private int typeCrop;
//    private List<HashMap> listDesPro;
    private Users user;
    private Integer idEntSystem;    
    private Integer idUsrSystem;    

    private TmpIssueReports issRep = new TmpIssueReports();
    private Sowing sowing = new Sowing();
    private UsersDao usrDao;

    //Metodos getter y setter por cada variable del formulario 
    /**
     * Metodos getter y setter por cada variable del formulario
     */
    public TmpIssueReports getIssRep() {
        return issRep;
    }

    public void setIssRep(TmpIssueReports issRep) {
        this.issRep = issRep;
    }        
    
    public Sowing getSowing() {
        return sowing;
    }

    public void setSowing(Sowing sowing) {
        this.sowing = sowing;
    }      

    public int getIdIss() {
        return idIss;
    }

    public void setIdIss(int idIss) {
        this.idIss = idIss;
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
    
//    public List<HashMap> getListDesPro() {
//        return listDesPro;
//    }
    
    //Atributos generales de clase
    /**
     * Atributos generales de clase
     */
    
//    private TmpIssueReportsDao tmpIssueDao = new TmpIssueReportsDao();
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
     * Metodo encargado de validar el formulario de un reporte de problemas
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
            HashMap required = new HashMap();
            required.put("issRep.nameIss", issRep.getNameIss());
            required.put("issRep.descriptionIss", issRep.getDescriptionIss());
            
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
            sowing=null;            
        }
    }     
    
    /**
     * Encargado de buscar las coincidencias de un formulario de busqueda, para cada uno de los
     * problemas registrados a un usuario
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
//        listDesPro = tmpIssueDao.findByParams(findParams);
        return SUCCESS;
    }
    

    /**
     * Encargado de mostrar en un formulario la informacion del reporte de problemas apartir de la identificacion
     * @param idIss:  Identificacion del reporte de problemas
     * @return Informacion del reporte de problemas
     */
    public String show() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "crop/create") || !usrDao.getPrivilegeUser(idUsrSystem, "crop/modify")) {
            return BaseAction.NOT_AUTHORIZED;
        }
        actExe = (String)(this.getRequest().getParameter("action"));
        if (actExe==null) {
            actExe = "create";
        }
        
        try {
            this.setIdCrop(Integer.parseInt(this.getRequest().getParameter("idCrop")));
        } catch (NumberFormatException e) {
//            LOG.error("There was an error trying to parse the activityId parameter");
            this.setIdCrop(-1);
        }
        
        try {
            this.setIdIss(Integer.parseInt(this.getRequest().getParameter("idIss")));
        } catch (NumberFormatException e) {
//            LOG.error("There was an error trying to parse the activityId parameter");
            this.setIdIss(-1);
        }
        
        if (this.getIdIss()!= -1) {
//            issRep = tmpIssueDao.objectById(this.getIdIss());
        } 
        return SUCCESS;
    }
    
    /**
     * Encargado de enviar el correo sobre las inquietudes del usuario
     * @return Estado del proceso
     */
//    public String sendInformation() {
//        state = "success";
//        info  = "Se le ha enviado la informacion al administrador del sistema, espere su respuesta.";
//        GlobalFunctions.sendEmail(getText("email.from"), getText("email.from"), getText("email.fromPass"), getText("email.subjectContact"), GlobalFunctions.messageToSendContact(this.getNameUser(), this.getEmailUser(), this.getWhatneed()));
//        return "states";
//    }

    /**
     * Encargado de guardar la informacion suministrada por el usuario para un reporte de problemas
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
            
            session.saveOrUpdate(issRep);
            
            LogEntities log = new LogEntities();
            log.setIdLogEnt(null);
            log.setIdEntityLogEnt(idEntSystem);
            log.setIdObjectLogEnt(issRep.getIdIss());
            log.setTableLogEnt("issues");
            log.setDateLogEnt(new Date());
            log.setActionTypeLogEnt(action);
            session.saveOrUpdate(log);
            tx.commit();           
            state = "success";            
//            if (action.equals("C")) {
//                info  = "El reporte ha sido agregado con exito";
////                return "list";
//            } else if (action.equals("M")) {
//                info  = "El reporte ha sido modificado con exito";
////                return "list";
//            }
            info  = "Se le ha enviado la informacion al administrador del sistema, espere su respuesta.";
            EntitiesDao entDao = new EntitiesDao();
            Entities entTemp   = entDao.findById(idEntSystem);
            GlobalFunctions.sendEmail(getText("email.fromContact"), getText("email.from"), getText("email.fromPass"), getText("email.subjectContact"), GlobalFunctions.reportToSend(entTemp.getNameEnt(), entTemp.getEmailEnt(), issRep.getNameIss(), issRep.getDescriptionIss()));
        
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
//            System.out.println("error->"+e.getMessage());
            state = "failure";
            info  = "Fallo al momento de agregar un reporte de problemas";
        } finally {
            session.close();
        }  
        
//        return ERROR;
        return "states";
    }
    
    /**
     * Encargado de borrar la informacion del reporte de problemas apartir de su identificacion
     * @param idIss:  Identificacion del reporte de problemas
     * @return Estado del proceso
     */
    public String delete() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "crop/delete")) {
            return BaseAction.NOT_AUTHORIZED;
        }
        Integer idIss = 0;
        try {
            idIss = Integer.parseInt(this.getRequest().getParameter("idIss"));
        } catch (NumberFormatException e) {
            idIss = -1;
        }
        
        if (idIss==-1) {
            state = "failure";
            info  = "Fallo al momento de obtener la informacion a borrar";
            return "states";
        }
        
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();            
//            TmpIssueReports pr = tmpIssueDao.objectById(idIss);      
//            pr.setStatus(false);
//            session.delete(pro);        
//            session.saveOrUpdate(pr);
            
//            LogEntities log = new LogEntities();
//            log.setIdLogEnt(null);
//            log.setIdEntityLogEnt(idEntSystem);
//            log.setIdObjectLogEnt(pr.getIdIss());
//            log.setTableLogEnt("issues");
//            log.setDateLogEnt(new Date());
//            log.setActionTypeLogEnt("D");
//            session.saveOrUpdate(log);
//            logDao.save(log);
            tx.commit();         
            state = "success";
            info  = "El reporte ha sido borrado con exito";
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            state = "failure";
            info  = "Fallo al momento de borrar un reporte de problemas";
        } finally {
            session.close();
        }      
        
        return "states";
//        return SUCCESS;
    }
    
}