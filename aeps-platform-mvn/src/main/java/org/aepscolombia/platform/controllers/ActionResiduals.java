
package org.aepscolombia.platform.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.aepscolombia.platform.models.dao.LogEntitiesDao;
import org.aepscolombia.platform.models.dao.ProductionEventsDao;
import org.aepscolombia.platform.models.dao.ResidualsClasificationDao;
import org.aepscolombia.platform.models.dao.ResidualsManagementDao;
import org.aepscolombia.platform.models.dao.SfGuardUserDao;
import org.aepscolombia.platform.models.dao.SowingDao;
import org.aepscolombia.platform.models.dao.UsersDao;

import org.aepscolombia.platform.models.entity.LogEntities;
import org.aepscolombia.platform.models.entity.ProductionEvents;
import org.aepscolombia.platform.models.entity.ResidualsClasification;
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
 * Clase ActionResiduals
 *
 * Contiene los metodos para interactuar con el modulo de manejo de rastrojos
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ActionResiduals extends BaseAction {
    
    /**
     * Atributos provenientes del formulario
     */
    private int idCrop;    
    private int idResMan;    
    private int typeCrop;
    private List<HashMap> listResMan;
    private Users user;
    private Integer idEntSystem;    
    private Integer idUsrSystem;    

    private ResidualsManagement resMan = new ResidualsManagement();
    private Sowing sowing = new Sowing();
    private List<ResidualsClasification> type_res_clas;
    private UsersDao usrDao;

    /**
     * Metodos getter y setter por cada variable del formulario
     */
    public ResidualsManagement getResMan() {
        return resMan;
    }

    public void setResMan(ResidualsManagement resMan) {
        this.resMan = resMan;
    }        
    
    public Sowing getSowing() {
        return sowing;
    }

    public void setSowing(Sowing sowing) {
        this.sowing = sowing;
    }      

    public int getIdResMan() {
        return idResMan;
    }

    public void setIdResMan(int idResMan) {
        this.idResMan = idResMan;
    }   

    public int getIdCrop() {
        return idCrop;
    }

    public void setIdCrop(int idCrop) {
        this.idCrop = idCrop;
    }  

    public List<ResidualsClasification> getType_res_clas() {
        return type_res_clas;
    }

    public void setType_res_clas(List<ResidualsClasification> type_res_clas) {
        this.type_res_clas = type_res_clas;
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
    
    public List<HashMap> getListResMan() {
        return listResMan;
    }
    
    /**
     * Atributos generales de clase
     */
    
    private ProductionEventsDao cropDao    = new ProductionEventsDao();
    private ResidualsManagementDao resDao  = new ResidualsManagementDao();
    private SowingDao sowDao      = new SowingDao();
    private LogEntitiesDao logDao = new LogEntitiesDao();
    
    private String state = "";
    private String info  = "";
    
    /**
     * Metodos getter y setter por cada variable general de la clase
     */    

    public String getState() {
        return state;
    }

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
            required.put("resMan.dateResMan", resMan.getDateResMan());
            required.put("resMan.residualsClasification.idResCla", resMan.getResidualsClasification().getIdResCla());

            if (resMan.getResidualsClasification().getIdResCla() == 1000000) {
                required.put("resMan.otherResidualsManagementResMan", resMan.getOtherResidualsManagementResMan());
            }
            
            for (Iterator it = required.keySet().iterator(); it.hasNext();) {
                String sK = (String) it.next();
                String sV = String.valueOf(required.get(sK));
//                System.out.println(sK + " : " + sV);
                if (StringUtils.trim(sV).equals("null") || StringUtils.trim(sV)==null || StringUtils.trim(sV).equals("") || sV.equals("-1")) {
                    addFieldError(sK, getText("message.fieldsrequired.residual"));
                    enter = true;
                }
            }
            
            if (enter) {
                addActionError(getText("message.missingfields.residual"));
            }
            
            Date dateSowing = null;
//            if (sowing.getDateSow()!=null) {
            if (sowing != null) {
                dateSowing = sowing.getDateSow();
                String dmySow  = new SimpleDateFormat("dd/MM/yyyy").format(sowing.getDateSow());

//                if (sowing.getDateSow()!=null && prep.getDatePrep()!=null) {
                if (!dmySow.equals("") && resMan.getDateResMan()!=null) {

                    Integer valDiffAff = GlobalFunctions.compareDateBeforeSowing(resMan.getDateResMan(), sowing.getDateSow(), 0);
                    if (valDiffAff==2) {
                        addFieldError("resMan.dateResMan", getText("message.residualdateinvalidrank.residual"));                
                        addActionError(getText("desc.residualdateinvalidrank.residual")+" ("+dmySow+")");
                    }

                }
            }
            sowing=null;            
        }
    }     
    
    /**
     * Encargado de buscar las coincidencias de un formulario de busqueda, para cada uno de las
     * preparaciones registradas a un usuario
     * @param valName:  Nombre del valor a buscar
     * @param valId:    Identificacion del valor a buscar
     * @param selected: Valor seleccionado
     * @return lista de manejo de rastrojo
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
        listResMan = resDao.findByParams(findParams);
        return SUCCESS;
    }
    

    /**
     * Encargado de mostrar en un formulario la informacion de un manejo de rastrojo apartir de la identificacion
     * @param idResMan:  Identificacion del manejo de rastrojo
     * @return Informacion del manejo de rastrojo
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
            this.setIdResMan(Integer.parseInt(this.getRequest().getParameter("idResMan")));
        } catch (NumberFormatException e) {
//            LOG.error("There was an error trying to parse the activityId parameter");
            this.setIdResMan(-1);
        }

        this.setType_res_clas(new ResidualsClasificationDao().findAllByTypeCrop(tyCro));
        if (this.getIdResMan()!= -1) {
            resMan = resDao.objectById(this.getIdResMan());
        } 
        return SUCCESS;
    }

    /**
     * Encargado de guardar la informacion suministrada por el usuario para un manejo de rastrojo
     * @return Estado del proceso
     */
    public String saveData() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "crop/create") || !usrDao.getPrivilegeUser(idUsrSystem, "crop/modify")) {
            return BaseAction.NOT_AUTHORIZED;
        }
        String action = "";
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
            
            String dmy    = new SimpleDateFormat("yyyy-MM-dd").format(resMan.getDateResMan());
            Date dateRes  = new SimpleDateFormat("yyyy-MM-dd").parse(dmy);
            
            resMan.setProductionEvents(new ProductionEvents(idCrop));
            resMan.setDateResMan(dateRes);          
            resMan.setStatus(true);
            session.saveOrUpdate(resMan);
            
            LogEntities log = null;            
            log = LogEntitiesDao.getData(idEntSystem, resMan.getIdResMan(), "residuals_management", action);
            if (log==null && !action.equals("M")) {
                log = new LogEntities();
                log.setIdLogEnt(null);
                log.setIdEntityLogEnt(idEntSystem);
                log.setIdObjectLogEnt(resMan.getIdResMan());
                log.setTableLogEnt("residuals_management");
                log.setDateLogEnt(new Date());
                log.setActionTypeLogEnt(action);
                session.saveOrUpdate(log);
            }          
            tx.commit();           
            state = "success";            
            if (action.equals("C")) {
                info  = getText("message.successadd.residual");
//                return "list";
            } else if (action.equals("M")) {
                info  = getText("message.successedit.residual");
//                return "list";
            }
            HashMap prod  = cropDao.findById(idCrop);
            Integer tyCro = Integer.parseInt(String.valueOf(prod.get("typeCrop")));
            SfGuardUserDao sfDao = new SfGuardUserDao();
            SfGuardUser sfUser   = sfDao.getUserByLogin(user.getCreatedBy(), user.getNameUserUsr(), "");            
//            GlobalFunctions.sendInformationCrop(idCrop, tyCro, sfUser.getId());
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
//            System.out.println("error->"+e.getMessage());
            state = "failure";
            if (action.equals("C")) {
                info  = getText("message.failadd.residual");
            } else if (action.equals("M")) {
                info  = getText("message.failedit.residual");
            }
        } catch (ParseException e) { 
        
        } finally {
            session.close();
        }  
        
//        return ERROR;
        return "states";
    }
    
    /**
     * Encargado de borrar la informacion de un manejo de rastrojo apartir de su identificacion
     * @param idResMan:  Identificacion del manejo de rastrojo
     * @return Estado del proceso
     */
    public String delete() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "crop/delete")) {
            return BaseAction.NOT_AUTHORIZED;
        }
        Integer idResMan = 0;
        try {
            idResMan = Integer.parseInt(this.getRequest().getParameter("idResMan"));
        } catch (NumberFormatException e) {
            idResMan = -1;
        }
        
        if (idResMan==-1) {
            state = "failure";
            info  = getText("message.failgetinfo.residual");
            return "states";
        }
        
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();            
            ResidualsManagement pr = resDao.objectById(idResMan);      
            pr.setStatus(false);
//            session.delete(pro);        
            session.saveOrUpdate(pr);
            
            LogEntities log = new LogEntities();
            log.setIdLogEnt(null);
            log.setIdEntityLogEnt(idEntSystem);
            log.setIdObjectLogEnt(pr.getIdResMan());
            log.setTableLogEnt("residuals_management");
            log.setDateLogEnt(new Date());
            log.setActionTypeLogEnt("D");
            session.saveOrUpdate(log);
//            logDao.save(log);
            tx.commit();         
            state = "success";
            info  = getText("message.successdelete.residual");
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            state = "failure";
            info  = getText("message.faildelete.residual");
        } finally {
            session.close();
        }      
        
        return "states";
//        return SUCCESS;
    }
    
}