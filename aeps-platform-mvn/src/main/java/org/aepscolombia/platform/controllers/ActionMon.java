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
import org.aepscolombia.platform.models.dao.DiseasesDao;

import org.aepscolombia.platform.models.dao.LogEntitiesDao;
import org.aepscolombia.platform.models.dao.ProductionEventsDao;
import org.aepscolombia.platform.models.dao.MonitoringDao;
import org.aepscolombia.platform.models.dao.PestsDao;
import org.aepscolombia.platform.models.dao.SfGuardUserDao;
import org.aepscolombia.platform.models.dao.SowingDao;
import org.aepscolombia.platform.models.dao.UsersDao;
import org.aepscolombia.platform.models.dao.WeedsDao;
import org.aepscolombia.platform.models.entity.Diseases;

import org.aepscolombia.platform.models.entity.LogEntities;
import org.aepscolombia.platform.models.entity.ProductionEvents;
import org.aepscolombia.platform.models.entity.Monitoring;
import org.aepscolombia.platform.models.entity.Pests;
import org.aepscolombia.platform.models.entity.Sowing;
import org.aepscolombia.platform.models.entity.Users;
import org.aepscolombia.platform.models.entity.Weeds;
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
 * Clase ActionPrep
 *
 * Contiene los metodos para interactuar con el modulo de monitoreo
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ActionMon extends BaseAction {
    
    //Atributos del formulario 
    /**
     * Atributos provenientes del formulario
     */
    private int idCrop;    
    private int idMon;    
    private int typeCrop;
    private List<HashMap> listMont;
    private Users user;
    private Integer idEntSystem;    
    private Integer idUsrSystem;    

    private Monitoring mon = new Monitoring();
    private Sowing sowing = new Sowing();
    private UsersDao usrDao;
    private List<Pests> type_pest_con;
    private List<Weeds> type_weeds_con;
    private List<Diseases> type_dis_con;

    //Metodos getter y setter por cada variable del formulario 
    /**
     * Metodos getter y setter por cada variable del formulario
     */
    public Monitoring getMon() {
        return mon;
    }

    public void setMon(Monitoring mon) {
        this.mon = mon;
    }           

    public Sowing getSowing() {
        return sowing;
    }

    public void setSowing(Sowing sowing) {
        this.sowing = sowing;
    }      

    public int getIdMon() {
        return idMon;
    }

    public void setIdMon(int idMon) {
        this.idMon = idMon;
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

    public List<HashMap> getListMont() {
        return listMont;
    }  
    
    public List<Pests> getType_pest_con() {
        return type_pest_con;
    }

    public void setType_pest_con(List<Pests> type_pest_con) {
        this.type_pest_con = type_pest_con;
    }

    public List<Diseases> getType_dis_con() {
        return type_dis_con;
    }

    public void setType_dis_con(List<Diseases> type_dis_con) {
        this.type_dis_con = type_dis_con;
    }
    
    public List<Weeds> getType_weeds_con() {
        return type_weeds_con;
    }

    public void setType_weeds_con(List<Weeds> type_weeds_con) {
        this.type_weeds_con = type_weeds_con;
    }
    
    
    //Atributos generales de clase
    /**
     * Atributos generales de clase
     */
    
    private ProductionEventsDao cropDao    = new ProductionEventsDao();
    private MonitoringDao monDao           = new MonitoringDao();
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
     * Metodo encargado de validar el formulario de un monitoreo
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
            required.put("mon.dateMon", mon.getDateMon());   
            
            if (mon.getMonitorPestsMon()!=null && mon.getMonitorPestsMon()) {
                required.put("mon.pests.idPes", mon.getPests().getIdPes());
                required.put("mon.perImpactPestMon", mon.getPerImpactPestMon());   
            }      
            if (mon.getMonitorWeedsMon()!=null && mon.getMonitorWeedsMon()) {
                required.put("mon.weeds.idWee", mon.getWeeds().getIdWee());
                required.put("mon.perImpactWeedMon", mon.getPerImpactWeedMon());   
            }      
            if (mon.getMonitorDiseasesMon()!=null && mon.getMonitorDiseasesMon()) {
                required.put("mon.diseases.idDis", mon.getDiseases().getIdDis());
                required.put("mon.perImpactDiseaseMon", mon.getPerImpactDiseaseMon());   
            }      
            
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
		
                if (!dmySow.equals("") && mon.getDateMon()!=null) {
                    Integer valDiffAff = GlobalFunctions.compareDateAfterSowing(mon.getDateMon(), sowing.getDateSow(), 0);
                    if (valDiffAff==2) {
                        addFieldError("mon.dateMon", "Dato invalido");                
                        addActionError("Se ingreso una fecha de monitoreo que no se encuentra dentro de los 10 meses despues de la siembra ("+dmySow+")");
                    }				

                }	
            }

            if (mon.getMonitorPestsMon()==null && mon.getMonitorDiseasesMon()==null && mon.getMonitorWeedsMon()==null) {
                addFieldError("mon.monitorPestsMon", "");
                addFieldError("mon.monitorDiseasesMon", "");
                addFieldError("mon.monitorWeedsMon", "");
                addActionError("Se debe seleccionar por lo menos alguna de las opciones a las cuales realiza un monitoreo");
            }
            sowing=null;            
        }
    }     
    
    /**
     * Encargado de buscar las coincidencias de un formulario de busqueda, para cada uno de los
     * monitoreos registrados a un usuario
     * @param valName:  Nombre del valor a buscar
     * @param valId:    Identificacion del valor a buscar
     * @param selected: Valor seleccionado
     * @return lista de monitoreos
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
        listMont = monDao.findByParams(findParams);
        return SUCCESS;
    }
    

    /**
     * Encargado de mostrar en un formulario la informacion de un monitoreo de la identificacion
     * @param idIrr:  Identificacion del monitoreo
     * @return Informacion del monitoreo
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
        this.setType_dis_con(new DiseasesDao().findAllByTypeCrop(tyCro));
        this.setType_pest_con(new PestsDao().findAllByTypeCrop(tyCro));
        this.setType_weeds_con(new WeedsDao().findAllByTypeCrop(tyCro));
        try {
            this.setIdMon(Integer.parseInt(this.getRequest().getParameter("idMon")));
        } catch (NumberFormatException e) {
//            LOG.error("There was an error trying to parse the activityId parameter");
            this.setIdMon(-1);
        }

        if (this.getIdMon()!= -1) {
            mon = monDao.objectById(this.getIdMon());
        } 
        return SUCCESS;
    }

    /**
     * Encargado de guardar la informacion suministrada por el usuario para un monitoreo
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
            
            String dmy   = new SimpleDateFormat("yyyy-MM-dd").format(mon.getDateMon());
            Date dateMon = new SimpleDateFormat("yyyy-MM-dd").parse(dmy);
            
            mon.setProductionEvents(new ProductionEvents(idCrop));
            mon.setDateMon(dateMon);          
            if (mon.getMonitorPestsMon()==null || !mon.getMonitorPestsMon()) {
                mon.setPests(null);   
            }      
            if (mon.getMonitorWeedsMon()==null || !mon.getMonitorWeedsMon()) {
                mon.setWeeds(null);
            }      
            if (mon.getMonitorDiseasesMon()==null || !mon.getMonitorDiseasesMon()) {
                mon.setDiseases(null);
            }   
            
//            if (mon.getMonitorPestsMon()==null && mon.getMonitorDiseasesMon()==null && mon.getMonitorWeedsMon()==null) {
//            if (sowing.getChemicalsSowing().getIdCheSow()==-1) {
//                sowing.setChemicalsSowing(null);
//            }
//            
//            if (sowing.getDoseUnits().getIdDosUni()==-1) {
//                sowing.setDoseUnits(null);
//            }        
            mon.setStatus(true);
            session.saveOrUpdate(mon);
            
            LogEntities log = new LogEntities();
            log.setIdLogEnt(null);
            log.setIdEntityLogEnt(idEntSystem);
            log.setIdObjectLogEnt(mon.getIdMon());
            log.setTableLogEnt("monitoring");
            log.setDateLogEnt(new Date());
            log.setActionTypeLogEnt(action);
            session.saveOrUpdate(log);           
            tx.commit();           
            state = "success";            
            if (action.equals("C")) {
                info  = "El monitoreo ha sido agregado con exito";
//                return "list";
            } else if (action.equals("M")) {
                info  = "El monitoreo ha sido modificado con exito";
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
            info  = "Fallo al momento de agregar un monitoreo";
        } catch (ParseException e) { 
        
        } finally {
            session.close();
        }  
        
//        return ERROR;
        return "states";
    }
    
    /**
     * Encargado de borrar la informacion de un monitoreo apartir de su identificacion
     * @param idMon:  Identificacion del monitoreo
     * @return Estado del proceso
     */
    public String delete() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "crop/delete")) {
            return BaseAction.NOT_AUTHORIZED;
        }
        Integer idMon = 0;
        try {
            idMon = Integer.parseInt(this.getRequest().getParameter("idMon"));
        } catch (NumberFormatException e) {
            idMon = -1;
        }
        
        if (idMon==-1) {
            state = "failure";
            info  = "Fallo al momento de obtener la informacion a borrar";
            return "states";
        }
        
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();            
            Monitoring mon = monDao.objectById(idMon);      
            mon.setStatus(false);
//            session.delete(pro);        
            session.saveOrUpdate(mon);
            
            LogEntities log = new LogEntities();
            log.setIdLogEnt(null);
            log.setIdEntityLogEnt(idEntSystem);
            log.setIdObjectLogEnt(mon.getIdMon());
            log.setTableLogEnt("monitoring");
            log.setDateLogEnt(new Date());
            log.setActionTypeLogEnt("D");
            session.saveOrUpdate(log);
//            logDao.save(log);
            tx.commit();         
            state = "success";
            info  = "El monitoreo ha sido borrado con exito";
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            state = "failure";
            info  = "Fallo al momento de borrar un monitoreo";
        } finally {
            session.close();
        }      
        
        return "states";
//        return SUCCESS;
    }
    
}