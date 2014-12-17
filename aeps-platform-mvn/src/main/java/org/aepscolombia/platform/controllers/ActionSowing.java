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
import org.aepscolombia.platform.models.dao.BeansDao;
import org.aepscolombia.platform.models.dao.CassavasDao;

import org.aepscolombia.platform.models.dao.LogEntitiesDao;
import org.aepscolombia.platform.models.dao.MaizeDao;
import org.aepscolombia.platform.models.dao.ProductionEventsDao;
import org.aepscolombia.platform.models.dao.SfGuardUserDao;
import org.aepscolombia.platform.models.dao.SowingDao;
import org.aepscolombia.platform.models.dao.UsersDao;
import org.aepscolombia.platform.models.entity.Beans;
import org.aepscolombia.platform.models.entity.Cassavas;
import org.aepscolombia.platform.models.entity.CropsTypes;

import org.aepscolombia.platform.models.entity.LogEntities;
import org.aepscolombia.platform.models.entity.Maize;
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
 * Clase ActionSowing
 *
 * Contiene los metodos para interactuar con el modulo de Siembra
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ActionSowing extends BaseAction {
    
    //Atributos del formulario 
    /**
     * Atributos provenientes del formulario
     */
    private int idCrop;    
    private int typeCrop;
    private Users user;
    private Integer idEntSystem;    
    private Integer idUsrSystem;    

    private Beans beans   = new Beans();
    private Cassavas cass = new Cassavas();
    private Maize maize   = new Maize();
    private Sowing sowing = new Sowing();
    private ProductionEvents event = new ProductionEvents();
    private UsersDao usrDao;

    //Metodos getter y setter por cada variable del formulario 
    /**
     * Metodos getter y setter por cada variable del formulario
     */
    public Beans getBeans() {
        return beans;
    }

    public void setBeans(Beans beans) {
        this.beans = beans;
    }

    public ProductionEvents getEvent() {
        return event;
    }

    public void setEvent(ProductionEvents event) {
        this.event = event;
    } 

    public Cassavas getCass() {
        return cass;
    }

    public void setCass(Cassavas cass) {
        this.cass = cass;
    }

    public Maize getMaize() {
        return maize;
    }

    public void setMaize(Maize maize) {
        this.maize = maize;
    }

    public Sowing getSowing() {
        return sowing;
    }

    public void setSowing(Sowing sowing) {
        this.sowing = sowing;
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
    
    //Atributos generales de clase
    /**
     * Atributos generales de clase
     */
    
    private ProductionEventsDao cropDao    = new ProductionEventsDao();
    private BeansDao beansDao     = new BeansDao();
    private CassavasDao cassDao   = new CassavasDao();
    private MaizeDao maizeDao     = new MaizeDao();
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
     * Metodo encargado de validar el formulario de Siembra
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
            required.put("sowing.dateSow", sowing.getDateSow());
            required.put("sowing.sowingTypes.idSowTyp", sowing.getSowingTypes().getIdSowTyp());
            required.put("sowing.seedsNumberSow", sowing.getSeedsNumberSow());
            required.put("sowing.treatedSeedsSow", sowing.isTreatedSeedsSow());
            required.put("sowing.genotypes.idGen", sowing.getGenotypes().getIdGen());                
            required.put("event.expected_production_pro_eve", event.getExpectedProductionProEve());     
            required.put("sowing.furrowsDistanceSow", sowing.getFurrowsDistanceSow());
            required.put("sowing.sitesDistanceSow", sowing.getSitesDistanceSow());
            
//            if (sowing.getGenotypesSowing().getIdGenSow()==1000000) {
            if (sowing.getGenotypes().getIdGen()!=null && sowing.getGenotypes().getIdGen()==1000000) {
                required.put("sowing.otherGenotypeSow", sowing.getOtherGenotypeSow());
            }
	
            if (typeCrop==1) {
              required.put("sowing.genotypesSowing.idGenSow", sowing.getGenotypesSowing().getIdGenSow());
              required.put("maize.seedsColors.idSeeCol", maize.getSeedsColors().getIdSeeCol());
              required.put("maize.seedsNumberSiteMai", maize.getSeedsNumberSiteMai());
            } else if (typeCrop==2) {
              required.put("beans.seedsNumberSiteBea", beans.getSeedsNumberSiteBea());              
              required.put("sowing.seedsOrigins.idSeeOri", sowing.getSeedsOrigins().getIdSeeOri());
//              required.put("beans.seedsTypes.idSeeTyp", beans.getSeedsTypes().getIdSeeTyp());
//              required.put("beans.seedsInoculations.idSeeIno", beans.getSeedsInoculations().getIdSeeIno());
              required.put("beans.growingEnvironment.idGroEnv", beans.getGrowingEnvironment().getIdGroEnv());

              if (beans.getOtroInoculationBea().equals("1000000")) {
                required.put("sowing.otherGenotypeSow", sowing.getOtherGenotypeSow());
              }
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
            
//            if (typeCrop==2) {
            required.put("sowing.furrowsDistanceSow", sowing.getFurrowsDistanceSow());
            if (sowing.getFurrowsDistanceSow()!=null && sowing.getFurrowsDistanceSow()!=0 && (sowing.getFurrowsDistanceSow()<0 || sowing.getFurrowsDistanceSow()>10)) {
                addFieldError("sowing.furrowsDistanceSow", "Dato invalido valor entre 0 y 10");
                addActionError("Se ingreso una distancia entre surcos invalida, por favor ingresar un valor entre 0 y 10");
            }

            required.put("sowing.sitesDistanceSow", sowing.getSitesDistanceSow());
            if (sowing.getFurrowsDistanceSow()!=null && sowing.getSitesDistanceSow()!=0 && (sowing.getSitesDistanceSow()<0 || sowing.getSitesDistanceSow()>10)) {
                addFieldError("sowing.sitesDistanceSow", "Dato invalido valor entre 0 y 10");
                addActionError("Se ingreso una distancia entre sitios invalida, por favor ingresar un valor entre 0 y 10");
            }
                
            if (typeCrop==2) {
                required.put("beans.seedsNumberSiteBea", beans.getSeedsNumberSiteBea());
                if (beans.getSeedsNumberSiteBea()!=0 && (beans.getSeedsNumberSiteBea()<1 || beans.getSeedsNumberSiteBea()>10)) {
                    addFieldError("beans.seedsNumberSiteBea", "Dato invalido valor entre 1 y 10");
                    addActionError("Se ingreso un numero de semillas por sitio invalido, por favor ingresar un valor entre 1 y 10");
                }
            } else if (typeCrop==1) {
                required.put("maize.seedsNumberSiteMai", maize.getSeedsNumberSiteMai());
                if (maize.getSeedsNumberSiteMai()!=null && maize.getSeedsNumberSiteMai()!=0 && (maize.getSeedsNumberSiteMai()<1 || maize.getSeedsNumberSiteMai()>10)) {
                    addFieldError("maize.seedsNumberSiteMai", "Dato invalido valor entre 1 y 10");
                    addActionError("Se ingreso un numero de semillas por sitio invalido, por favor ingresar un valor entre 1 y 10");
                }
            }

            if (event.getExpectedProductionProEve()!=null && event.getExpectedProductionProEve()!=0) {
                if (event.getExpectedProductionProEve()<0 || event.getExpectedProductionProEve()>30000) {
                    addFieldError("event.expected_production_pro_eve", "Dato invalido valor entre 0 y 30000");
                    addActionError("Se ingreso un rendimiento histórico obtenido invalido, por favor ingresar un valor entre 0 y 30000");
                }
            }
            
        }
    }     

    /**
     * Encargado de guardar la informacion suministrada por el usuario para una siembra
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
//        info = "La siembra ha sido modificado con exito";
        

        try {
            tx = session.beginTransaction();           
            
            String dmy   = new SimpleDateFormat("yyyy-MM-dd").format(sowing.getDateSow());
            Date dateSow = new SimpleDateFormat("yyyy-MM-dd").parse(dmy);
            
//            event.setFields(event.getFields());
//            event.setCropsTypes(new CropsTypes(2));
//            event.setIdProjectProEve(event.getIdProjectProEve());
//            event.setStatus(event.isStatus());
            session.saveOrUpdate(event);
            
            if (sowing.getIdSow()==null) {
                Sowing sowTemp = sowDao.objectById(idCrop);
                if (sowTemp!=null) {
                    sowing.setIdSow(sowTemp.getIdSow());
                }
            }
            
            sowing.setProductionEvents(new ProductionEvents(idCrop));
            sowing.setDateSow(dateSow);          
            if (sowing.getChemicalsSowing().getIdCheSow()==-1) {
                sowing.setChemicalsSowing(null);
            }
            
            if (sowing.getDoseUnits()!=null && (sowing.getDoseUnits().getIdDosUni()==-1 || sowing.getChemicalsSowing().getIdCheSow()==3)) {
                sowing.setDoseUnits(null);
            }
//            sowing.setSowingTypes(new SowingTypes(idCrop));          
            sowing.setStatus(true);
            session.saveOrUpdate(sowing);
            
            
            Maize maizeOld  = maizeDao.objectById(this.getIdCrop());
            if(maizeOld!=null) session.delete(maizeOld);
            
            Beans beansOld  = beansDao.objectById(this.getIdCrop());
            if(beansOld!=null) session.delete(beansOld);

            if (typeCrop==1) {        
                maize.setProductionEvents(new ProductionEvents(idCrop));
                maize.setStatus(true);
                session.saveOrUpdate(maize);
            } else if (typeCrop==2) {  
                beans.setSeedsTypes(null);
                beans.setProductionEvents(new ProductionEvents(idCrop));
                beans.setStatus(true);
                session.saveOrUpdate(beans);
            } else if (typeCrop==3) {
//                Cassavas ca = new Cassavas();
//                ca.setIdCas(null);
//                ca.setProductionEvents(pro);
            }
            
            LogEntities log = new LogEntities();
            log.setIdLogEnt(null);
            log.setIdEntityLogEnt(idEntSystem);
            log.setIdObjectLogEnt(sowing.getIdSow());
            log.setTableLogEnt("sowing");
            log.setDateLogEnt(new Date());
            log.setActionTypeLogEnt(action);
            session.saveOrUpdate(log);           
            
            tx.commit();             
            state = "success";            
            if (action.equals("C")) {
                info  = "La siembra ha sido agregada con exito";
//                return "list";
            } else if (action.equals("M")) {
                info  = "La siembra ha sido modificada con exito";
//                return "list";
            }
            SfGuardUserDao sfDao = new SfGuardUserDao();
            SfGuardUser sfUser   = sfDao.getUserByLogin(user.getCreatedBy(), user.getNameUserUsr(), "");            
            GlobalFunctions.sendInformationCrop(idCrop, typeCrop, sfUser.getId());
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
//            System.out.println("error->"+e.getMessage());
            state = "failure";
            info  = "Fallo al momento de agregar una siembra";
        } catch (ParseException e) { 
        
        } finally {
            session.close();
        }  
        
//        return ERROR;
        return "states";
    }
}