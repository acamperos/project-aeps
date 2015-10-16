
package org.aepscolombia.platform.controllers;

import com.opensymphony.xwork2.ActionContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.aepscolombia.platform.models.dao.LogEntitiesDao;
import org.aepscolombia.platform.models.dao.PreparationsDao;
import org.aepscolombia.platform.models.dao.PreparationsTypesDao;
import org.aepscolombia.platform.models.dao.ProductionEventsDao;
import org.aepscolombia.platform.models.dao.ResidualsClasificationDao;
import org.aepscolombia.platform.models.dao.SfGuardUserDao;
import org.aepscolombia.platform.models.dao.SowingDao;
import org.aepscolombia.platform.models.dao.UsersDao;

import org.aepscolombia.platform.models.entity.LogEntities;
import org.aepscolombia.platform.models.entity.Preparations;
import org.aepscolombia.platform.models.entity.PreparationsTypes;
import org.aepscolombia.platform.models.entity.ProductionEvents;
import org.aepscolombia.platform.models.entity.ResidualsClasification;
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
 * Clase ActionPrep
 *
 * Contiene los metodos para interactuar con el modulo de preparaciones
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ActionPrep extends BaseAction {
    
    /**
     * Atributos provenientes del formulario
     */
    private int idCrop;    
    private int idPrep;    
    private int typeCrop;
    private List<HashMap> listPrep;
    private Users user;
    private Integer idEntSystem;    
    private Integer idUsrSystem;    

    private Preparations prep = new Preparations();
    private Sowing sowing = new Sowing();
    private List<PreparationsTypes> type_prep_typ;
    private List<ResidualsClasification> type_res_clas;
    private UsersDao usrDao;
    private String coCode;

    /**
     * Metodos getter y setter por cada variable del formulario
     */
    public Preparations getPrep() {
        return prep;
    }

    public void setPrep(Preparations prep) {
        this.prep = prep;
    }   

    public Sowing getSowing() {
        return sowing;
    }

    public void setSowing(Sowing sowing) {
        this.sowing = sowing;
    }      

    public int getIdPrep() {
        return idPrep;
    }

    public void setIdPrep(int idPrep) {
        this.idPrep = idPrep;
    }   

    public int getIdCrop() {
        return idCrop;
    }

    public void setIdCrop(int idCrop) {
        this.idCrop = idCrop;
    }  

    public List<PreparationsTypes> getType_prep_typ() {
        return type_prep_typ;
    }

    public void setType_prep_typ(List<PreparationsTypes> type_prep_typ) {
        this.type_prep_typ = type_prep_typ;
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
    
    public List<HashMap> getListPrep() {
        return listPrep;
    }
    
    /**
     * Atributos generales de clase
     */
    
    private ProductionEventsDao cropDao    = new ProductionEventsDao();
    private PreparationsDao prepDao        = new PreparationsDao();
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
//        coCode = (String) user.getCountryUsr().getAcronymIdCo();
        coCode = (String) this.getSession().get(APConstants.COUNTRY_CODE);
        usrDao = new UsersDao();
        idUsrSystem = user.getIdUsr();
//        user.setCountryUsr(null);
    }
    
    
    /**
     * Metodo encargado de validar el formulario de una preparacion
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
            required.put("prep.datePrep", prep.getDatePrep());
            required.put("prep.preparationsTypes.idPreTyp", prep.getPreparationsTypes().getIdPreTyp());            
//            required.put("prep.residualsClasification.idResCla", prep.getResidualsClasification().getIdResCla());
            

            if (prep.getPreparationsTypes().getIdPreTyp() == 1000000) {
                required.put("prep.otherPreparationTypePrep", prep.getOtherPreparationTypePrep());
                required.put("prep.passingsNumberPrep", prep.getPassingsNumberPrep());
            } else if (prep.getPreparationsTypes().getIdPreTyp()>0 && prep.getPreparationsTypes().getIdPreTyp() != 1000000) {
                if (prep.getPreparationsTypes().getIdPreTyp()>=1 && prep.getPreparationsTypes().getIdPreTyp()<=5)
                    required.put("prep.depthPrep", prep.getDepthPrep());
//                required.put("prep.passingsNumberPrep", prep.getPassingsNumberPrep());
            }

//            if (prep.getResidualsClasification().getIdResCla() == 1000000) {
//                required.put("prep.otherResidualsManagementPrep", prep.getOtherResidualsManagementPrep());
//            }           
            
            for (Iterator it = required.keySet().iterator(); it.hasNext();) {
                String sK = (String) it.next();
                String sV = String.valueOf(required.get(sK));
//                System.out.println(sK + " : " + sV);
                if (StringUtils.trim(sV).equals("null") || StringUtils.trim(sV)==null || StringUtils.trim(sV).equals("") || sV.equals("-1")) {
                    addFieldError(sK, getText("message.fieldsrequired.preparation"));
                    enter = true;
                }
            }
            
            if (enter) {
                addActionError(getText("message.missingfields.preparation"));
            }
            
            Date dateSowing = null;
//            if (sowing.getDateSow()!=null) {
            if (sowing != null) {
                dateSowing = sowing.getDateSow();
                String dmySow  = new SimpleDateFormat("dd/MM/yyyy").format(sowing.getDateSow());

//                if (sowing.getDateSow()!=null && prep.getDatePrep()!=null) {
                if (!dmySow.equals("") && prep.getDatePrep()!=null) {

                    Integer valDiffAff = GlobalFunctions.compareDateBeforeSowing(prep.getDatePrep(), sowing.getDateSow(), 0);
                    if (valDiffAff==2) {
        //				$fails[]  = $prefix.'date_harvest_crop';
                        addFieldError("prep.datePrep", getText("message.preparationdateinvalidrank.preparation"));                
                        addActionError(getText("desc.preparationdateinvalidrank.preparation")+" ("+dmySow+")");
                    }

                }
            }

            if (prep.getDepthPrep()!=null) {
                if (prep.getDepthPrep()<0 || prep.getDepthPrep()>200) {
                    addFieldError("prep.depthPrep", getText("message.invaliddatadepth.preparation"));
                    addActionError(getText("desc.invaliddatadepth.preparation"));
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
     * @return lista de preparaciones
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
        listPrep = prepDao.findByParams(findParams);
        return SUCCESS;
    }
    

    /**
     * Encargado de mostrar en un formulario la informacion de una preparacion apartir de la identificacion
     * @param idPrep:  Identificacion de la preparacion
     * @return Informacion de la preparacion
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
            this.setIdPrep(Integer.parseInt(this.getRequest().getParameter("idPrep")));
        } catch (NumberFormatException e) {
//            LOG.error("There was an error trying to parse the activityId parameter");
            this.setIdPrep(-1);
        }

        this.setType_prep_typ(new PreparationsTypesDao().findAllByTypeCrop(tyCro, coCode));
        this.setType_res_clas(new ResidualsClasificationDao().findAllByTypeCrop(tyCro));
        if (this.getIdPrep()!= -1) {
            prep = prepDao.objectById(this.getIdPrep());
        } 
        return SUCCESS;
    }

    /**
     * Encargado de guardar la informacion suministrada por el usuario para una preparacion
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
            
            String dmy    = new SimpleDateFormat("yyyy-MM-dd").format(prep.getDatePrep());
            Date datePrep = new SimpleDateFormat("yyyy-MM-dd").parse(dmy);
            
            prep.setProductionEvents(new ProductionEvents(idCrop));
            prep.setDatePrep(datePrep);          
//            if (sowing.getChemicalsSowing().getIdCheSow()==-1) {
//                sowing.setChemicalsSowing(null);
//            }
//            
//            if (sowing.getDoseUnits().getIdDosUni()==-1) {
//                sowing.setDoseUnits(null);
//            }        
            prep.setStatus(true);
            session.saveOrUpdate(prep);
            
            LogEntities log = null;            
            log = LogEntitiesDao.getData(idEntSystem, prep.getIdPrep(), "preparations", action);
            if (log==null && !action.equals("M")) {
                log = new LogEntities();
                log.setIdLogEnt(null);
                log.setIdEntityLogEnt(idEntSystem);
                log.setIdObjectLogEnt(prep.getIdPrep());
                log.setTableLogEnt("preparations");
                log.setDateLogEnt(new Date());
                log.setActionTypeLogEnt(action);
                session.saveOrUpdate(log);
            }      
            tx.commit();           
            state = "success";            
            if (action.equals("C")) {
                info  = getText("message.successadd.preparation");
//                return "list";
            } else if (action.equals("M")) {
                info  = getText("message.successedit.preparation");
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
                info  = getText("message.failadd.preparation");
            } else if (action.equals("M")) {
                info  = getText("message.failedit.preparation");
            }            
        } catch (ParseException e) { 
        
        } finally {
            session.close();
        }  
        
//        return ERROR;
        return "states";
    }
    
    /**
     * Encargado de borrar la informacion de una preparación apartir de su identificacion
     * @param idPrep:  Identificacion de la preparación
     * @return Estado del proceso
     */
    public String delete() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "crop/delete")) {
            return BaseAction.NOT_AUTHORIZED;
        }
        Integer idPrep = 0;
        try {
            idPrep = Integer.parseInt(this.getRequest().getParameter("idPrep"));
        } catch (NumberFormatException e) {
            idPrep = -1;
        }
        
        if (idPrep==-1) {
            state = "failure";
            info  = getText("message.failgetinfo.preparation");
            return "states";
        }
        
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();            
            Preparations pr = prepDao.objectById(idPrep);      
            pr.setStatus(false);
//            session.delete(pro);        
            session.saveOrUpdate(pr);
            
            LogEntities log = new LogEntities();
            log.setIdLogEnt(null);
            log.setIdEntityLogEnt(idEntSystem);
            log.setIdObjectLogEnt(pr.getIdPrep());
            log.setTableLogEnt("preparations");
            log.setDateLogEnt(new Date());
            log.setActionTypeLogEnt("D");
            session.saveOrUpdate(log);
//            logDao.save(log);
            tx.commit();         
            state = "success";
            info  = getText("message.successdelete.preparation");
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            state = "failure";
            info  = getText("message.faildelete.preparation");
        } finally {
            session.close();
        }      
        
        return "states";
    }
    
}