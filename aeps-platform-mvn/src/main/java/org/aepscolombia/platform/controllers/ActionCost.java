
package org.aepscolombia.platform.controllers;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import org.aepscolombia.platform.models.dao.CostIndirectProductionEventDao;
import org.aepscolombia.platform.models.dao.CropsTypesDao;
import org.aepscolombia.platform.models.dao.HarvestsDao;

import org.aepscolombia.platform.models.dao.LogEntitiesDao;
import org.aepscolombia.platform.models.dao.ProductionEventsDao;
import org.aepscolombia.platform.models.dao.SfGuardUserDao;
import org.aepscolombia.platform.models.dao.SowingDao;
import org.aepscolombia.platform.models.dao.UsersDao;
import org.aepscolombia.platform.models.entity.CostIndirectProductionEvent;
import org.aepscolombia.platform.models.entity.CropsTypes;
import org.aepscolombia.platform.models.entity.Fields;
import org.aepscolombia.platform.models.entity.Harvests;

import org.aepscolombia.platform.models.entity.LogEntities;
import org.aepscolombia.platform.models.entity.ProductionEvents;
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
 * Clase ActionHarvest
 *
 * Contiene los metodos para interactuar con el modulo de Cosecha
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ActionCost extends BaseAction {
    
    /**
     * Atributos provenientes del formulario
     */
    private Integer idCrop;    
    private Integer typeCrop;
    private int lastCrop;  
    private String nameTypeCrop;
    private String lastTypeCrop;
    private String otherCrop;
    private Users user;
    private Integer idEntSystem;    
    private Integer idUsrSystem;    
    private int idField;
    private CostIndirectProductionEvent costo  = new CostIndirectProductionEvent();
    private Sowing sowing = new Sowing();
    private UsersDao usrDao;
    private String coCode;
    private ProductionEvents event = new ProductionEvents();

    /**
     * Metodos getter y setter por cada variable del formulario
     */
    
    
    public String getLastTypeCrop() {
        return lastTypeCrop;
    }   

    public int getTypeCrop() {
        return typeCrop;
    }

    public void setTypeCrop(int typeCrop) {
        this.typeCrop = typeCrop;
    }

    public int getLastCrop() {
        return lastCrop;
    }

    public void setLastCrop(int lastCrop) {
        this.lastCrop = lastCrop;
    }
    
    public int getIdField() {
        return idField;
    }

    public void setIdField(int idField) {
        this.idField = idField;
    }
    
    public CostIndirectProductionEvent getCosto() {
        return costo;
    }

    public void setCosto(CostIndirectProductionEvent costo) {
        this.costo = costo;
    }   
    
      public ProductionEvents getEvent() {
        return event;
    }
    
    public void setEvent(ProductionEvents event) {
        this.event = event;
    }

    public Sowing getSowing() {
        return sowing;
    }

    public void setSowing(Sowing sowing) {
        this.sowing = sowing;
    }      

     public Integer getIdCrop() {
        return idCrop;
    }

    public void setIdCrop(Integer idCrop) {
        this.idCrop = idCrop;
    }   
    
     public String getOtherCrop() {
        return otherCrop;
    }

    public void setOtherCrop(String otherCrop) {
        this.otherCrop = otherCrop;
    } 
    
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }       
    
    /**
     * Atributos generales de clase
     */
    
    private ProductionEventsDao cropDao    = new ProductionEventsDao();    
    private CostIndirectProductionEventDao costDao    = new CostIndirectProductionEventDao();
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
        coCode = (String) this.getSession().get(APConstants.COUNTRY_CODE);
    }
    
    private void setValuesCrop(Integer idCrop) {
        HashMap cropInfo = cropDao.findById(idCrop);
        this.setIdField(Integer.parseInt(String.valueOf(cropInfo.get("idField"))));
        this.setTypeCrop(Integer.parseInt(String.valueOf(cropInfo.get("typeCrop"))));
//        this.setPerformObj(Double.parseDouble(String.valueOf(cropInfo.get("performObj"))));
        this.setLastCrop(Integer.parseInt(String.valueOf(cropInfo.get("lastCrop"))));
        this.setOtherCrop(String.valueOf(cropInfo.get("otherCrop")));
        
        if (this.getLastCrop()==1000000) {
            lastTypeCrop = this.getOtherCrop();
        } else {
            lastTypeCrop = new CropsTypesDao().objectById(this.getLastCrop()).getNameCroTyp();
        }
        
    }
    
    /**
     * Metodo encargado de validar el formulario de Cosecha
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
//        if (false && (actExe.equals("create") || actExe.equals("modify"))) {
        if (actExe.equals("create") || actExe.equals("modify")) {
            boolean enter = false;
            sowing = sowDao.objectById(this.getIdCrop());
            
            HashMap required = new HashMap();
            //required.put("harv.dateHar", harv.getDateHar());
            //required.put("harv.harvestMethods.idHarMet", harv.getHarvestMethods().getIdHarMet());                     
//            required.put("harv.commentHar", harv.getCommentHar());
            if (typeCrop==1 || typeCrop==2) {
                //required.put("harv.resultingProducts.idResPro", harv.getResultingProducts().getIdResPro());
            }
            
           /* if (harv.getResultingProducts().getIdResPro()!=0) {
                if (harv.getResultingProducts().getIdResPro()==1 || harv.getResultingProducts().getIdResPro()==2 || harv.getResultingProducts().getIdResPro()==5) {
                    required.put("harv.yieldHar", harv.getYieldHar());   
                    required.put("harv.humidityPercentageHar", harv.getHumidityPercentageHar());
                } else if (harv.getResultingProducts().getIdResPro()==3) {
                    required.put("harv.numberSacksSow", harv.getNumberSacksSow());
                } else if (harv.getResultingProducts().getIdResPro()==4) {
                    required.put("harv.yieldHar", harv.getYieldHar());   
                    required.put("harv.numberSacksSow", harv.getNumberSacksSow());
                }
                
//                if (harv.getResultingProducts().getIdResPro()==1 || harv.getResultingProducts().getIdResPro()==4) {
//                    required.put("harv.humidityPercentageHar", harv.getHumidityPercentageHar());
//                }
            } */
            
//            System.out.println("value=>"+harv.getYieldHar());
            
//            if (harv.getProductionHar()==0) {
//                addFieldError("harv.productionHar", "El campo es requerido");
//            }
            
//            if (harv.getYieldHar()==0) {
//                addFieldError("harv.yieldHar", "El campo es requerido");
//            }
            
            for (Iterator it = required.keySet().iterator(); it.hasNext();) {
                String sK = (String) it.next();
                String sV = String.valueOf(required.get(sK));
//                System.out.println(sK + " : " + sV);
                if (StringUtils.trim(sV).equals("null") || StringUtils.trim(sV)==null || StringUtils.trim(sV).equals("") || sV.equals("-1")) {
                    addFieldError(sK, getText("message.fieldsrequired.cost"));
                    enter = true;
                }
            }
            
            if (enter) {
                addActionError(getText("message.missingfields.cost"));
            }
            
            Date dateSowing = null;
//            if (sowing.getDateSow()!=null) {
            if (sowing != null) {
                dateSowing = sowing.getDateSow();
                String dmySow  = new SimpleDateFormat("dd/MM/yyyy").format(sowing.getDateSow());
//                $fechaSiembra = date('m-d-Y', strtotime($params['fecha_siembra']));
//                try {
//                    String dateAsign = new SimpleDateFormat("yyyy-MM-dd").format(new Date(fechaSiembra));
//                } catch (IllegalArgumentException ex) {
//                }
//            }

//                if (sowing.getDateSow()!=null && harv.getDateHar()!=null) {
              /* if (!dmySow.equals("") && harv.getDateHar()!=null) {

                    Integer valDiffAff = GlobalFunctions.compareDateAfterSowingByAction(harv.getDateHar(), sowing.getDateSow(), typeCrop, 7);
                    if (valDiffAff==2) {
        //				$fails[]  = $prefix.'date_harvest_crop';
                        addFieldError("harv.dateHar", getText("message.harvestdateinvalidrank.harvest"));                
                        if (typeCrop==3) {
                            addActionError(getText("desc.harvestdateinvalidrankcassava.harvest")+" ("+dmySow+")");
                        } else {
                            addActionError(getText("desc.harvestdateinvalidrankother.harvest")+" ("+dmySow+")");
                        }
                    }

                }*/
            }

           /* if (harv.getProductionHar()!=null && harv.getProductionHar()!=0) {
                if (harv.getProductionHar()<0) {
                    addFieldError("harv.productionHar", getText("message.invaliddataproduction.harvest"));
                    addActionError(getText("desc.invaliddataproduction.harvest"));
                }
            }*/
            
          /*  if (harv.getResultingProducts().getIdResPro()!=0 && (harv.getYieldHar()!=null && harv.getYieldHar()!=0)) {
//            1 Grano seco a min 100 max 20000
//            4 Ensilaje min 1000 max 60000
//            3 Fresco min 1000 max 60000 
                if (harv.getResultingProducts().getIdResPro()==1 || harv.getResultingProducts().getIdResPro()==2 || harv.getResultingProducts().getIdResPro()==5) {
                    if (harv.getYieldHar()<100 || harv.getYieldHar()>20000) {
                        addFieldError("harv.yieldHar", getText("message.invaliddatayielddry.harvest"));
                        addActionError(getText("desc.invaliddatayielddry.harvest"));
                    }
                } else if (harv.getResultingProducts().getIdResPro()==3 || harv.getResultingProducts().getIdResPro()==4) {
                    if (harv.getYieldHar()<1000 || harv.getYieldHar()>60000) {
                        addFieldError("harv.yieldHar", getText("message.invaliddatayieldsilage.harvest"));
                        addActionError(getText("desc.invaliddatayieldsilage.harvest"));
                    }
                }
                
            } */

//            if (harv.getYieldHar()!=null && harv.getYieldHar()!=0) {
//                if (harv.getYieldHar()<0 || harv.getYieldHar()>30000) {
//                    addFieldError("harv.yieldHar", getText("message.invaliddatayield.harvest"));
//                    addActionError(getText("desc.invaliddatayield.harvest"));
//                }
//            }
          /*  
            if (harv.getHumidityPercentageHar() != null) {
                if (harv.getHumidityPercentageHar()<0 || harv.getHumidityPercentageHar()>100) {
                    addFieldError("harv.humidityPercentageHar", getText("message.invaliddatahumidity.harvest"));
                    addActionError(getText("desc.invaliddatahumidity.harvest"));
                }
            }*/
            sowing=null;
        }
    }     

    /**
     * Encargado de guardar la informacion suministrada por el usuario 
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
//        info = "La cosecha ha sido modificado con exito";
        

        try {
            tx = session.beginTransaction();
            
           // String dmy     = new SimpleDateFormat("yyyy-MM-dd").format(harv.getDateHar());
            //Date dateHar   = new SimpleDateFormat("yyyy-MM-dd").parse(dmy);
            if (costo.getIdCostIndPro()==null) {
                CostIndirectProductionEvent costTemp = costDao.objectById(idCrop);
                if (costTemp!=null) {
                    costo.setIdCostIndPro(costTemp.getIdCostIndPro());
                }
            }           
            
            if (coCode.equals("NI")) {
              //  Double yieldHar = harv.getYieldHar()*65.71;
               // Integer proHar  = harv.getProductionHar()*46;
                //harv.setYieldHar(yieldHar);            
                //harv.setProductionHar(proHar);
            }
            
            costo.setProductionEvents(new ProductionEvents(idCrop));
//            harv.setDateHar(new Date());
//            harv.setProductionHar(14);
//            harv.setYieldHar(12.45);
            //harv.setDateHar(dateHar);     
            costo.setStatus(true);
            session.saveOrUpdate(costo);
            
            LogEntities log = null;            
            log = LogEntitiesDao.getData(idEntSystem, costo.getIdCostIndPro(), "cost_indirect_production_event", action);
            if (log==null && !action.equals("M")) {
                log = new LogEntities();
                log.setIdLogEnt(null);
                log.setIdEntityLogEnt(idEntSystem);
                log.setIdObjectLogEnt(costo.getIdCostIndPro());
                log.setTableLogEnt("cost_indirect_production_event");
                log.setDateLogEnt(new Date());
                log.setActionTypeLogEnt(action);
                session.saveOrUpdate(log);
            }                        
            
            tx.commit();           
            state = "success";            
            if (action.equals("C")) {
                info  = getText("message.successadd.cost");
//                return "list";
            } else if (action.equals("M")) {
                info  = getText("message.successedit.cost");
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
                info  = getText("message.failadd.cost");
            } else if (action.equals("M")) {
                info  = getText("message.failedit.cost");
            }
        } finally {
            session.close();
        }  
        
//        return ERROR;
        return "states";
    }
    
    
     /**
     * Encargado de guardar la informacion suministrada
     * @return Estado del proceso
     */
    public String saveDataPerformance() {
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
         if (this.getIdCrop()!= -1) {
            setValuesCrop(this.getIdCrop());
        }

        try {
            tx = session.beginTransaction();
            
                        
             if (event.getIdProEve()==null) {
                ProductionEvents eventTemp = cropDao.objectById(idCrop);
                if (eventTemp!=null) {
                    event.setIdProEve(eventTemp.getIdProEve());
                }
            }   
            event.setFields(new Fields(idField));
            event.setCropsTypes(new CropsTypes(typeCrop));
            event.setIdProjectProEve(1);            
            if (lastCrop==1000000) {
                event.setFormerCropProEve(null);
                event.setOtherFormerCropProEve(otherCrop);
            } else {
                event.setFormerCropProEve(lastCrop);
            }
            event.setStatus(true); 
            session.saveOrUpdate(event);  
            tx.commit();           
            
           
            
            state = "success";            
            if (action.equals("C")) {
                info  = getText("message.successadd.crop");
//                return "list";
            } else if (action.equals("M")) {
                info  = getText("message.successedit.crop");
//                return "list";
            }
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
//            System.out.println("error->"+e.getMessage());
            state = "failure";
            if (action.equals("C")) {
                info  = getText("message.failadd.crop");
            } else if (action.equals("M")) {
                info  = getText("message.failedit.crop");
            }
        } finally {
            session.close();
        }  
        
//        return ERROR;
        return "states";
    }

}