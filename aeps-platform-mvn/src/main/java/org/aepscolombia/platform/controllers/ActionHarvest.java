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
import org.aepscolombia.platform.models.dao.HarvestsDao;

import org.aepscolombia.platform.models.dao.LogEntitiesDao;
import org.aepscolombia.platform.models.dao.ProductionEventsDao;
import org.aepscolombia.platform.models.dao.SfGuardUserDao;
import org.aepscolombia.platform.models.dao.SowingDao;
import org.aepscolombia.platform.models.dao.UsersDao;
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
public class ActionHarvest extends BaseAction {
    
    //Atributos del formulario 
    /**
     * Atributos provenientes del formulario
     */
    private int idCrop;    
    private int typeCrop;
    private Users user;
    private Integer idEntSystem;    
    private Integer idUsrSystem;    

    private Harvests harv  = new Harvests();
    private Sowing sowing = new Sowing();
    private UsersDao usrDao;

    //Metodos getter y setter por cada variable del formulario 
    /**
     * Metodos getter y setter por cada variable del formulario
     */
    public Harvests getHarv() {
        return harv;
    }

    public void setHarv(Harvests harv) {
        this.harv = harv;
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
    private HarvestsDao harDao    = new HarvestsDao();
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
            required.put("harv.dateHar", harv.getDateHar());
            required.put("harv.harvestMethods.idHarMet", harv.getHarvestMethods().getIdHarMet());                     
//            required.put("harv.commentHar", harv.getCommentHar());
            if (typeCrop==1 || typeCrop==2) {
                required.put("harv.resultingProducts.idResPro", harv.getResultingProducts().getIdResPro());
            }
            
            if (harv.getResultingProducts().getIdResPro()!=0) {
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
//                }﻿  ﻿  
            } 
            
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
//                $fechaSiembra = date('m-d-Y', strtotime($params['fecha_siembra']));
//                try {
//                    String dateAsign = new SimpleDateFormat("yyyy-dd-MM").format(new Date(fechaSiembra));
//                } catch (IllegalArgumentException ex) {
//                }
//            }

//                if (sowing.getDateSow()!=null && harv.getDateHar()!=null) {
                if (!dmySow.equals("") && harv.getDateHar()!=null) {

                    Integer valDiffAff = GlobalFunctions.compareDateAfterSowing(harv.getDateHar(), sowing.getDateSow(), typeCrop);
                    if (valDiffAff==2) {
        //				$fails[]  = $prefix.'date_harvest_crop';
                        addFieldError("harv.dateHar", "Dato invalido");                
                        if (typeCrop==3) {
                            addActionError("Se ingreso una fecha de la cosecha que no se encuentra dentro de los 18 meses despues de la siembra ("+dateSowing+")");
                        } else {
                            addActionError("Se ingreso una fecha de la cosecha que no se encuentra dentro de los 10 meses despues de la siembra ("+dateSowing+")");
                        }
                    }

                }
            }

            if (harv.getProductionHar()!=null && harv.getProductionHar()!=0) {
                if (harv.getProductionHar()<0) {
                    addFieldError("harv.productionHar", "Dato invalido valor mayor a 0");
                    addActionError("Se ingreso una cantidad de produccion invalida, por favor ingresar un valor mayor a 0");
                }
            }

            if (harv.getYieldHar()!=null && harv.getYieldHar()!=0) {
                if (harv.getYieldHar()<0 || harv.getYieldHar()>30000) {
                    addFieldError("harv.yieldHar", "Dato invalido valor entre 0 y 30000");
                    addActionError("Se ingreso un rendimiento invalido, por favor ingresar un valor entre 0 y 30000");
                }
            }
            
            if (harv.getHumidityPercentageHar() != null) {
                if (harv.getHumidityPercentageHar()<0 || harv.getHumidityPercentageHar()>100) {
                    addFieldError("harv.humidityPercentageHar", "Dato invalido valor entre 0 y 100");
                    addActionError("Se ingreso un porcentaje de humedad invalido, por favor ingresar un valor entre 0 y 100");
                }
            }
            sowing=null;
        }
    }     

    /**
     * Encargado de guardar la informacion suministrada por el usuario para una cosecha
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
//        info = "La cosecha ha sido modificado con exito";
        

        try {
            tx = session.beginTransaction();
            
            String dmy     = new SimpleDateFormat("yyyy-MM-dd").format(harv.getDateHar());
            Date dateHar   = new SimpleDateFormat("yyyy-MM-dd").parse(dmy);
            if (harv.getIdHar()==null) {
                Harvests harvTemp = harDao.objectById(idCrop);
                if (harvTemp!=null) {
                    harv.setIdHar(harvTemp.getIdHar());
                }
            }           
            
            harv.setProductionEvents(new ProductionEvents(idCrop));
//            harv.setDateHar(new Date());
//            harv.setProductionHar(14);
//            harv.setYieldHar(12.45);
            harv.setDateHar(dateHar);     
            harv.setStatus(true);
            session.saveOrUpdate(harv);
            
            LogEntities log = new LogEntities();
            log.setIdLogEnt(null);
            log.setIdEntityLogEnt(idEntSystem);
            log.setIdObjectLogEnt(harv.getIdHar());
            log.setTableLogEnt("harvests");
            log.setDateLogEnt(new Date());
            log.setActionTypeLogEnt(action);
            session.saveOrUpdate(log);            
            
            tx.commit();           
            state = "success";            
            if (action.equals("C")) {
                info  = "La cosecha ha sido agregada con exito";
//                return "list";
            } else if (action.equals("M")) {
                info  = "La cosecha ha sido modificada con exito";
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
            info  = "Fallo al momento de agregar una cosecha";
        } catch (ParseException e) { 
        
        } finally {
            session.close();
        }  
        
//        return ERROR;
        return "states";
    }
}