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
import java.util.logging.Level;
import java.util.logging.Logger;

import org.aepscolombia.platform.models.dao.LogEntitiesDao;
import org.aepscolombia.platform.models.dao.PhysiologicalMonitoringDao;
import org.aepscolombia.platform.models.dao.ProductionEventsDao;
import org.aepscolombia.platform.models.dao.SowingDao;
import org.aepscolombia.platform.models.dao.UsersDao;
import org.aepscolombia.platform.models.entity.Beans;
import org.aepscolombia.platform.models.entity.Cassavas;
import org.aepscolombia.platform.models.entity.CropsTypes;
import org.aepscolombia.platform.models.entity.Fields;

import org.aepscolombia.platform.models.entity.LogEntities;
import org.aepscolombia.platform.models.entity.Maize;
import org.aepscolombia.platform.models.entity.PhysiologicalMonitoring;
import org.aepscolombia.platform.models.entity.ProductionEvents;
import org.aepscolombia.platform.models.entity.Sowing;
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
 * Clase ActionPhyMon
 *
 * Contiene los metodos para interactuar con el modulo de Monitoreo Fisiologíco
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ActionPhys extends BaseAction {
    
    //Atributos del formulario 
    /**
     * Atributos provenientes del formulario
     */
    private int idCrop;
    private int typeCrop;
    private Users user;
    private Integer idEntSystem;    
    
    private PhysiologicalMonitoring phys = new PhysiologicalMonitoring();
    private Sowing sowing = new Sowing();

    //Metodos getter y setter por cada variable del formulario 
    /**
     * Metodos getter y setter por cada variable del formulario
     */    

    public PhysiologicalMonitoring getPhys() {
        return phys;
    }

    public void setPhys(PhysiologicalMonitoring phys) {
        this.phys = phys;
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
    private PhysiologicalMonitoringDao physDao = new PhysiologicalMonitoringDao();
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
    }
    
    
    /**
     * Metodo encargado de validar el formulario de Monitoreo Fisiologíco
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
            required.put("phys.emergencePhyMon", phys.getEmergencePhyMon());
            required.put("phys.daysPopulationMonFis", phys.getDaysPopulationMonFis());
            required.put("phys.floweringDatePhyMon", phys.getFloweringDatePhyMon());            
            
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
//            if (sowing.getDateSow().compareTo(dateSowing)==0) {
            if (sowing != null && !enter) {
                try {
                    //            } else {
                    String dmySow  = new SimpleDateFormat("dd/MM/yyyy").format(sowing.getDateSow());
                    dateSowing = sowing.getDateSow();

                    Date dateSow = new SimpleDateFormat("dd/MM/yyyy").parse(dmySow);
                    
                    String dmyEme  = new SimpleDateFormat("yyyy-MM-dd").format(phys.getEmergencePhyMon());
                    Date dateEme = new SimpleDateFormat("yyyy-MM-dd").parse(dmyEme);
                    
    //                $fechaSiembra = date('m-d-Y', strtotime($params['fecha_siembra']));
    //                try {
    //                    String dateAsign = new SimpleDateFormat("yyyy-dd-MM").format(new Date(fechaSiembra));
    //                } catch (IllegalArgumentException ex) {
    //                }
//                    System.out.println("dates->"+dateEme);
//                    System.out.println("dates1->"+dateSow);

                    if (!dmySow.equals("") && phys.getEmergencePhyMon()!=null) {		
//                        System.out.println("fechaSie->"+sowing.getDateSow());
                        Integer valDiffAff = GlobalFunctions.compareDateAfterSowing(phys.getEmergencePhyMon(), sowing.getDateSow(), 0);
                        if (valDiffAff==2) {
                            addFieldError("phys.emergencePhyMon", "Dato invalido");
                            addActionError("Se ingreso una fecha de emergencia que no se encuentra dentro de los 10 meses despues de la siembra ("+dmySow+")");
                        }
                    }

                    if (!dmySow.equals("") && phys.getFloweringDatePhyMon()!=null) {
//                        System.out.println("fechaSie1->"+sowing.getDateSow());
                        Integer valDiffAff = GlobalFunctions.compareDateAfterSowing(phys.getFloweringDatePhyMon(), sowing.getDateSow(), 0);
                        if (valDiffAff==2) {
                            addFieldError("phys.floweringDatePhyMon", "Dato invalido");
                            addActionError("Se ingreso una fecha de floración que no se encuentra dentro de los 10 meses despues de la siembra ("+dmySow+")");
                        }
                    }
                } catch (ParseException ex) {
//                    Logger.getLogger(ActionPhyMon.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            
//            if (phys.getDaysPopulationMonFis()!=0) {
            if (phys.getDaysPopulationMonFis() != null) {
//                System.out.println("values=>"+phys.getDaysPopulationMonFis());
              if (phys.getDaysPopulationMonFis()<0 || phys.getDaysPopulationMonFis()>300000) {
                  addFieldError("phys.daysPopulationMonFis", "Dato invalido");
                  addActionError("Se ingreso una poblacion invalida, por favor ingresar un valor entre 0 y 300000");
              }
            }
            sowing=null;
        }
    }     

    /**
     * Encargado de guardar la informacion suministrada por el usuario para un monitoreo fisiologíco
     * @return Estado del proceso
     */
    public String saveData() {
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
//        info = "El monitoreo fisiologíco ha sido modificado con exito";
        

        try {
            tx = session.beginTransaction();
            
            String dmyEmer     = new SimpleDateFormat("yyyy-MM-dd").format(phys.getEmergencePhyMon());
            Date dateEmergence = new SimpleDateFormat("yyyy-MM-dd").parse(dmyEmer);
            
            String dmyFlow     = new SimpleDateFormat("yyyy-MM-dd").format(phys.getFloweringDatePhyMon());
            Date dateFlow      = new SimpleDateFormat("yyyy-MM-dd").parse(dmyFlow);
            
            phys.setProductionEvents(new ProductionEvents(idCrop));
            phys.setEmergencePhyMon(dateEmergence);     
            phys.setFloweringDatePhyMon(dateFlow);
            phys.setStatus(true);
            session.saveOrUpdate(phys);
            
            LogEntities log = new LogEntities();
            log.setIdLogEnt(null);
            log.setIdEntityLogEnt(idEntSystem);
            log.setIdObjectLogEnt(phys.getIdPhyMon());
            log.setTableLogEnt("physiological_monitoring");
            log.setDateLogEnt(new Date());
            log.setActionTypeLogEnt(action);
            session.saveOrUpdate(log);
            tx.commit();           
            state = "success";            
            if (action.equals("C")) {
                info  = "El monitoreo fisiologíco ha sido agregado con exito";
//                return "list";
            } else if (action.equals("M")) {
                info  = "El monitoreo fisiologíco ha sido modificado con exito";
//                return "list";
            }
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
//            System.out.println("error->"+e.getMessage());
            state = "failure";
            info  = "Fallo al momento de agregar un monitoreo fisiologíco";
        } catch (ParseException e) { 
        
        } finally {
            session.close();
        }  
        
//        return ERROR;
        return "states";
    }
}