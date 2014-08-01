/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aepscolombia.platform.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.aepscolombia.platform.models.dao.BeansDao;
import org.aepscolombia.platform.models.dao.CassavasDao;
import org.aepscolombia.platform.models.dao.ChemicalsSowingDao;
import org.aepscolombia.platform.models.dao.ControlsDao;
import org.aepscolombia.platform.models.dao.CropsTypesDao;
import org.aepscolombia.platform.models.dao.DepartmentsDao;
import org.aepscolombia.platform.models.dao.DescriptionsProductionEventDao;
import org.aepscolombia.platform.models.dao.DocumentsTypesDao;
import org.aepscolombia.platform.models.dao.DoseUnitsDao;
import org.aepscolombia.platform.models.dao.EntitiesDao;
import org.aepscolombia.platform.models.dao.FertilizationsDao;

import org.aepscolombia.platform.models.dao.LogEntitiesDao;
import org.aepscolombia.platform.models.dao.FieldsDao;
import org.aepscolombia.platform.models.dao.GenotypesDao;
import org.aepscolombia.platform.models.dao.GenotypesSowingDao;
import org.aepscolombia.platform.models.dao.GrowingEnvironmentDao;
import org.aepscolombia.platform.models.dao.HarvestMethodsDao;
import org.aepscolombia.platform.models.dao.HarvestsDao;
import org.aepscolombia.platform.models.dao.IrrigationDao;
import org.aepscolombia.platform.models.dao.MaizeDao;
import org.aepscolombia.platform.models.dao.MonitoringDao;
import org.aepscolombia.platform.models.dao.PhysiologicalMonitoringDao;
import org.aepscolombia.platform.models.dao.PreparationsDao;
import org.aepscolombia.platform.models.dao.ProductionEventsDao;
import org.aepscolombia.platform.models.dao.ResidualsManagementDao;
import org.aepscolombia.platform.models.dao.ResultingProductsDao;
import org.aepscolombia.platform.models.dao.SeedsColorsDao;
import org.aepscolombia.platform.models.dao.SeedsInoculationsDao;
import org.aepscolombia.platform.models.dao.SeedsOriginsDao;
import org.aepscolombia.platform.models.dao.SeedsTypesDao;
import org.aepscolombia.platform.models.dao.SowingDao;
import org.aepscolombia.platform.models.dao.SowingTypesDao;
import org.aepscolombia.platform.models.dao.UsersDao;
import org.aepscolombia.platform.models.entity.Beans;
import org.aepscolombia.platform.models.entity.Cassavas;
import org.aepscolombia.platform.models.entity.ChemicalsSowing;
import org.aepscolombia.platform.models.entity.CropsTypes;
import org.aepscolombia.platform.models.entity.Departments;
import org.aepscolombia.platform.models.entity.DocumentsTypes;
import org.aepscolombia.platform.models.entity.DoseUnits;
import org.aepscolombia.platform.models.entity.Entities;
import org.aepscolombia.platform.models.entity.Fields;
import org.aepscolombia.platform.models.entity.Genotypes;
import org.aepscolombia.platform.models.entity.GenotypesSowing;
import org.aepscolombia.platform.models.entity.GrowingEnvironment;
import org.aepscolombia.platform.models.entity.HarvestMethods;
import org.aepscolombia.platform.models.entity.Harvests;

import org.aepscolombia.platform.models.entity.LogEntities;
import org.aepscolombia.platform.models.entity.Maize;
import org.aepscolombia.platform.models.entity.PhysiologicalMonitoring;
import org.aepscolombia.platform.models.entity.ProductionEvents;
import org.aepscolombia.platform.models.entity.ResultingProducts;
import org.aepscolombia.platform.models.entity.SeedsColors;
import org.aepscolombia.platform.models.entity.SeedsInoculations;
import org.aepscolombia.platform.models.entity.SeedsOrigins;
import org.aepscolombia.platform.models.entity.SeedsTypes;
import org.aepscolombia.platform.models.entity.Sowing;
import org.aepscolombia.platform.models.entity.SowingTypes;
import org.aepscolombia.platform.models.entity.Users;
import org.aepscolombia.platform.util.APConstants;
import org.aepscolombia.platform.util.HibernateUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Clase ActionCrop
 *
 * Contiene los metodos para interactuar con el modulo de Cultivo
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ActionReport extends BaseAction {
    
    //Atributos del formulario 
    /**
     * Atributos provenientes del formulario
     */
    private Integer typeReport; 

    //Reporte Anual
    private Integer selYear;
    private Integer year_begin;
    private Integer year_end;
    private String name_producer;
    private String type_doc;
    private String num_doc;

    //Reporte Departamento
    private String depSel;
    private String nameProDep;
    private String typeDocDep;
    private String numDocDep;
    private List<DocumentsTypes> type_ident_producer;    
    private List<Departments> list_departments;
    
    private Users user;
    private Integer idEntSystem;    
    private Integer idUsrSystem;    
    private ProductionEvents event = new ProductionEvents();
    private UsersDao usrDao;

    //Metodos getter y setter por cada variable del formulario 
    /**
     * Metodos getter y setter por cada variable del formulario
     */    

    public ProductionEvents getEvent() {
        return event;
    }

    public void setEvent(ProductionEvents event) {
        this.event = event;
    }  
    
    public void setType_ident_producer(List<DocumentsTypes> type_ident_producer) {
        this.type_ident_producer = type_ident_producer;
    }

    public List<DocumentsTypes> getType_ident_producer() {
        return type_ident_producer;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
    
    public String getName_producer() {
        return name_producer;
    }

    public void setName_producer(String name_producer) {
        this.name_producer = name_producer;
    }

    public String getType_doc() {
        return type_doc;
    }

    public void setType_doc(String type_doc) {
        this.type_doc = type_doc;
    }

    public String getNum_doc() {
        return num_doc;
    }

    public void setNum_doc(String num_doc) {
        this.num_doc = num_doc;
    }

    public Integer getTypeReport() {
        return typeReport;
    }

    public void setTypeReport(Integer typeReport) {
        this.typeReport = typeReport;
    }

    public Integer getSelYear() {
        return selYear;
    }

    public void setSelYear(Integer selYear) {
        this.selYear = selYear;
    }

    public Integer getYear_begin() {
        return year_begin;
    }

    public void setYear_begin(Integer year_begin) {
        this.year_begin = year_begin;
    }

    public Integer getYear_end() {
        return year_end;
    }

    public void setYear_end(Integer year_end) {
        this.year_end = year_end;
    }

    public String getDepSel() {
        return depSel;
    }

    public void setDepSel(String depSel) {
        this.depSel = depSel;
    }

    public String getNameProDep() {
        return nameProDep;
    }

    public void setNameProDep(String nameProDep) {
        this.nameProDep = nameProDep;
    }

    public String getTypeDocDep() {
        return typeDocDep;
    }

    public void setTypeDocDep(String typeDocDep) {
        this.typeDocDep = typeDocDep;
    }

    public String getNumDocDep() {
        return numDocDep;
    }

    public void setNumDocDep(String numDocDep) {
        this.numDocDep = numDocDep;
    }

    public List<Departments> getList_departments() {
        return list_departments;
    }

    public void setList_departments(List<Departments> list_departments) {
        this.list_departments = list_departments;
    }
    
    
    //Atributos generales de clase
    /**
     * Atributos generales de clase
     */
    private HashMap additionals;
    private int page = 1, countTotal, maxResults = 10;
    
    private ProductionEventsDao cropDao    = new ProductionEventsDao();    
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

    public void setPage(int page) {
        this.page = page;
    }

    public void setCountTotal(int countTotal) {
        this.countTotal = countTotal;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public int getPage() {
        return page;
    }

    public int getCountTotal() {
        return countTotal;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public HashMap getAdditionals() {
        return additionals;
    }    
    
    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }    
    
    private Integer typeEnt;

    public Integer getTypeEnt() {
        return typeEnt;
    }

    public void setTypeEnt(Integer typeEnt) {
        this.typeEnt = typeEnt;
    }
    
    @Override
    public void prepare() throws Exception {
        user = (Users) this.getSession().get(APConstants.SESSION_USER);
        idEntSystem = UsersDao.getEntitySystem(user.getIdUsr());
        this.setType_ident_producer(new DocumentsTypesDao().findAll());
        this.setList_departments(new DepartmentsDao().findAll());
        usrDao = new UsersDao();
        idUsrSystem = user.getIdUsr();
        EntitiesDao entDao = new EntitiesDao();
        Entities entTemp = entDao.findById(idEntSystem);
        typeEnt = entTemp.getEntitiesTypes().getIdEntTyp();
    }
    
    
    /**
     * Metodo encargado de validar el formulario de Cultivo
     */
    @Override
    public void validate() {       
        /*
         * Se evalua dependiendo a la accion realizada:
         * 1) create: Al momento de guardar un registro por primera ves
         * 2) modify: Al momento de modificar un registro
         * 3) delete: Al momento de borrar un registro
         */
        if (actExe.equals("create") || actExe.equals("modify")) {
            HashMap required = new HashMap();
//            required.put("nameField", nameField);
//            required.put("typeCrop", typeCrop);
//            required.put("lastCrop", lastCrop);
//            required.put("drainPlot", drainPlot);    
//            boolean enter = false;
//            
//            if (lastCrop==1000000) {
//                required.put("otherCrop", otherCrop);
//            }
//            
//            for (Iterator it = required.keySet().iterator(); it.hasNext();) {
//                String sK = (String) it.next();
//                String sV = String.valueOf(required.get(sK));
////                System.out.println(sK + " : " + sV);
//                if (StringUtils.trim(sV).equals("null") || StringUtils.trim(sV)==null || StringUtils.trim(sV).equals("") || sV.equals("-1")) {
//                    addFieldError(sK, "El campo es requerido");
//                    enter = true;
//                }
//            }
//            
//            if (enter) {
//                addActionError("Faltan campos por ingresar por favor digitelos");
//            }
//            
//            System.out.println("performObj->"+performObj);
        }
    }
    
    private int numRows=0;

    public Integer getNumRows() {
        return numRows;
    }

    public void setNumRows(Integer numRows) {
        this.numRows = numRows;
    }

    /**
     * Encargado de generar el reporte de productividad a partir de las condiciones dadas por el usuario
     * cultivos registrados a un usuario
     * @param valName:  Nombre del valor a buscar
     * @param valId:    Identificacion del valor a buscar
     * @param selected: Valor seleccionado
     * @return lista de cultivos
     */
    public String getReport() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "crop/list")) {
            return BaseAction.NOT_AUTHORIZED;
        }
        
        additionals = new HashMap();
        HashMap findParams = new HashMap();
        
        findParams.put("idEntUser", idEntSystem);
        findParams.put("selYear", selYear);
        findParams.put("year_begin", year_begin);
        findParams.put("year_end", year_end);
        findParams.put("name_producer", name_producer);
        findParams.put("type_doc", type_doc);
        findParams.put("num_doc", num_doc);
        findParams.put("depSel", depSel);
        findParams.put("nameProDep", nameProDep);
        findParams.put("typeDocDep", typeDocDep);
        findParams.put("numDocDep", numDocDep);
        
        String semester = "";
        HashMap semA;
        HashMap semB;
        String categories = "";
        String outlier    = "";
        String values     = "";
        if (typeReport == 1) {
            if (typeEnt==1 && name_producer==null && num_doc==null) {            
                if (selYear == 1) {
                    semester = "'"+year_begin+"-01-01' and '"+year_begin+"-06-30'";
                    semA = cropDao.getReportAnnualAgronomist(findParams, semester, 0);
                    semester = "'"+year_begin+"-07-01' and '"+year_begin+"-12-31'";
                    semB = cropDao.getReportAnnualAgronomist(findParams, semester, 1);
                    categories = "categories : ['semA("+year_begin+")', 'semB("+year_begin+")']";
                    values     = "values: ["+semA.get("info")+","+semB.get("info")+"]";
                } else if (selYear == 2) { 
                    Integer diff = year_end - year_begin;
                    categories = "categories : [";
                    values     = "values: [";
                    int cont   = 1;
                    for (int i=1; i<=diff; i++) {
                        semester = "'"+year_begin+"-01-01' and '"+year_begin+"-06-30'";
                        semA = cropDao.getReportAnnualAgronomist(findParams, semester, (i-1));
                        semester = "'"+year_begin+"-07-01' and '"+year_begin+"-12-31'";
                        semB = cropDao.getReportAnnualAgronomist(findParams, semester, i);                        
                        if (cont!=diff) {
                            categories += "'semA("+year_begin+")', 'semB("+year_begin+")', ";
                            values  += semA.get("info")+","+semB.get("info")+",";
                        } else {
                            categories += "'semA("+year_begin+")', 'semB("+year_begin+")'";
                            values  += semA.get("info")+","+semB.get("info");
                        }
                        
                    }
                    categories += "]";
                    values += "]";
                }
                info    = "{"+categories+""+values+"}";
                return "reportBox";
            } else {
                values  = "values: "+cropDao.getReportAnnualProducer(findParams);
                info    = "{"+values+"}";
                return "reportDet";
            }
        } else if (typeReport == 2) {
        } 
        return SUCCESS;
    }    

}