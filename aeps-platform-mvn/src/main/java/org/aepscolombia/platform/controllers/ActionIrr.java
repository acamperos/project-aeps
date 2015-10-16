
package org.aepscolombia.platform.controllers;

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
import org.aepscolombia.platform.models.dao.UseIrrigationDao;
import org.aepscolombia.platform.models.dao.UsersDao;
import org.aepscolombia.platform.models.entity.Irrigation;

import org.aepscolombia.platform.models.entity.LogEntities;
import org.aepscolombia.platform.models.entity.ProductionEvents;
import org.aepscolombia.platform.models.entity.IrrigationsTypes;
import org.aepscolombia.platform.models.entity.Sowing;
import org.aepscolombia.platform.models.entity.UseIrrigation;
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
    private List<UseIrrigation> type_uses_irr;
    private UsersDao usrDao;
    private String coCode;
    private ProductionEvents event = new ProductionEvents();

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

    public List<UseIrrigation> getType_uses_irr() {
        return type_uses_irr;
    }

    public void setType_uses_irr(List<UseIrrigation> type_uses_irr) {
        this.type_uses_irr = type_uses_irr;
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
    
      public ProductionEvents getEvent() {
        return event;
    }

    public void setEvent(ProductionEvents event) {
        this.event = event;
    } 
    
    /**
     * Atributos generales de clase
     */
    
    private ProductionEventsDao cropDao    = new ProductionEventsDao();
    private IrrigationDao irrDao           = new IrrigationDao();
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
//                required.put("irr.amountIrr", irr.getAmountIrr());                
//            }           ﻿  ﻿  ﻿         ﻿              
            if (coCode.equals("NI")) {
                required.put("irr.whatDoYouUseIrr.idUseIrr", irr.getWhatDoYouUseIrr().getIdUseIrr());
                if (irr.getWhatDoYouUseIrr().getIdUseIrr()==1) {
                    required.put("irr.dateIrr", irr.getDateIrr());
                    required.put("irr.thicknessSheetIrr", irr.getThicknessSheetIrr());
                } else if (irr.getWhatDoYouUseIrr().getIdUseIrr()==2) {
                    required.put("irr.dateWetIrr", irr.getDateWetIrr());
                    required.put("irr.durationIrr", irr.getDurationIrr());
                }
            } else if (coCode.equals("CO")) {
                required.put("irr.dateIrr", irr.getDateIrr());
                required.put("irr.irrigationsTypes.idIrrTyp", irr.getIrrigationsTypes().getIdIrrTyp());
            }
            
            for (Iterator it = required.keySet().iterator(); it.hasNext();) {
                String sK = (String) it.next();
                String sV = String.valueOf(required.get(sK));
//                System.out.println(sK + " : " + sV);
                if (StringUtils.trim(sV).equals("null") || StringUtils.trim(sV)==null || StringUtils.trim(sV).equals("") || sV.equals("-1")) {
                    addFieldError(sK, getText("message.fieldsrequired.irrigation"));
                    enter = true;
                }
            }
            
            if (enter) {
                addActionError(getText("message.missingfields.irrigation"));
            }
            
            Date dateSowing = null;
//            if (sowing.getDateSow()!=null) {
            HashMap prod  = cropDao.findById(idCrop);
            Integer tyCro = Integer.parseInt(String.valueOf(prod.get("typeCrop")));
            if (sowing != null) {
                dateSowing = sowing.getDateSow();
                String dmySow  = new SimpleDateFormat("dd/MM/yyyy").format(sowing.getDateSow());

                if (!dmySow.equals("") && irr.getDateIrr()!=null) {

//                    Integer valDiffBef = GlobalFunctions.compareDateBeforeSowing(irr.getDateIrr(), sowing.getDateSow(), tyCro);
                    Integer valDiffAff = GlobalFunctions.compareDateAfterSowingByAction(irr.getDateIrr(), sowing.getDateSow(), tyCro, 1);
                    if (valDiffAff==2) {
                        addFieldError("irr.dateIrr", getText("message.irrigationdateinvalidrank.irrigation"));
                        if (tyCro==1) {
                            addActionError(getText("desc.irrigationdateinvalidrankmaize.irrigation")+" ("+dmySow+")");
                        } else if (tyCro==2) {
                            addActionError(getText("desc.irrigationdateinvalidrankbeans.irrigation")+" ("+dmySow+")");
                        } else if (tyCro==4) {
                            addActionError(getText("desc.irrigationdateinvalidrankrice.irrigation")+" ("+dmySow+")");
                        }
                    }

                }
                
                if (!dmySow.equals("") && irr.getDateWetIrr()!=null) {
                    Integer valDiffAff = GlobalFunctions.compareDateAfterSowingByAction(irr.getDateWetIrr(), sowing.getDateSow(), tyCro, 1);
                    if (valDiffAff==2) {
                        addFieldError("irr.dateWetIrr", getText("message.irrigationwetdateinvalidrank.irrigation"));
                        if (tyCro==1) {
                            addActionError(getText("desc.irrigationwetdateinvalidrank.irrigation")+" ("+dmySow+")");
                        } else if (tyCro==2) {
                            addActionError(getText("desc.irrigationwetdateinvalidrank.irrigation")+" ("+dmySow+")");
                        } else if (tyCro==4) {
                            addActionError(getText("desc.irrigationwetdateinvalidrank.irrigation")+" ("+dmySow+")");
                        }
                    }

                }
            }

            if (irr.getAmountIrr()!=null) {
                if (irr.getAmountIrr()<0 || irr.getAmountIrr()>1000) {
                    addFieldError("irr.amountIrr", getText("message.invaliddataamount.irrigation"));
                    addActionError(getText("desc.invaliddataamount.irrigation"));
                }
            }  
            
            if (irr.getDurationIrr()!=null) {
                if (irr.getDurationIrr()<1 || irr.getDurationIrr()>5) {
                    addFieldError("irr.durationIrr", getText("message.invaliddataduration.irrigation"));
                    addActionError(getText("desc.invaliddataduration.irrigation"));
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

        this.setType_irr_typ(new IrrigationsTypesDao().findAllByTypeCrop(tyCro,coCode));
        this.setType_uses_irr(new UseIrrigationDao().findAll());
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
                        
            Date dateIrr    = null;
            Date dateWetIrr = null;
            String dmy      = new SimpleDateFormat("yyyy-MM-dd").format(irr.getDateIrr());
            if (irr.getWhatDoYouUseIrr()!=null) {
                if (irr.getWhatDoYouUseIrr().getIdUseIrr()==1) {
                    dateIrr = new SimpleDateFormat("yyyy-MM-dd").parse(dmy);
                } else if (irr.getWhatDoYouUseIrr().getIdUseIrr()==2) {
                    String dmyWet = new SimpleDateFormat("yyyy-MM-dd").format(irr.getDateWetIrr());
                    dateWetIrr = new SimpleDateFormat("yyyy-MM-dd").parse(dmyWet);
                }
            } else {
                dateIrr = new SimpleDateFormat("yyyy-MM-dd").parse(dmy);
            }
                
            irr.setUseIrrigationIrr(true);
            irr.setProductionEvents(new ProductionEvents(idCrop));
            irr.setDateIrr(dateIrr);      
            irr.setDateWetIrr(null);
            if (dateWetIrr!=null) {
                irr.setDateIrr(dateWetIrr);          
                irr.setDateWetIrr(dateWetIrr);
            }
//            if (sowing.getChemicalsSowing().getIdCheSow()==-1) {
//                sowing.setChemicalsSowing(null);
//            }
//            
//            if (sowing.getDoseUnits().getIdDosUni()==-1) {
//                sowing.setDoseUnits(null);
//            }        
            irr.setStatus(true);
            session.saveOrUpdate(irr);
            
            LogEntities log = null;            
            log = LogEntitiesDao.getData(idEntSystem, irr.getIdIrr(), "irrigation", action);
            if (log==null && !action.equals("M")) {
                log = new LogEntities();
                log.setIdLogEnt(null);
                log.setIdEntityLogEnt(idEntSystem);
                log.setIdObjectLogEnt(irr.getIdIrr());
                log.setTableLogEnt("irrigation");
                log.setDateLogEnt(new Date());
                log.setActionTypeLogEnt(action);
                session.saveOrUpdate(log);
            }       
            tx.commit();           
            state = "success";            
            if (action.equals("C")) {
                info  = getText("message.successadd.irrigation");
//                return "list";
            } else if (action.equals("M")) {
                info  = getText("message.successedit.irrigation");
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
                info  = getText("message.failadd.irrigation");
            } else if (action.equals("M")) {
                info  = getText("message.failedit.irrigation");
            }
        } catch (ParseException e) { 
        
        } finally {
            session.close();
        }  
        
//        return ERROR;
        return "states";
    }
    
    
    
    /**
     * Encargado de guardar la informacion suministrada por el usuario para los comentarios de los riegos
     * @return Estado del proceso
     */
    public String saveIrrComment() {
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
                        
            
            session.saveOrUpdate(event);
                             
            tx.commit();           
            state = "success";            
            if (action.equals("C")) {
                info  = getText("message.successadd.irrigation");
//                return "list";
            } else if (action.equals("M")) {
                info  = getText("message.successedit.irrigation");
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
                info  = getText("message.failadd.irrigation");
            } else if (action.equals("M")) {
                info  = getText("message.failedit.irrigation");
            }
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
            info  = getText("message.failgetinfo.irrigation");
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
            info  = getText("message.successdelete.irrigation");
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            state = "failure";
            info  = getText("message.faildelete.irrigation");
        } finally {
            session.close();
        }      
        
        return "states";
    }
    
}