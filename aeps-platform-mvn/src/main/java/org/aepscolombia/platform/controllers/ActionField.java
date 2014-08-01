/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aepscolombia.platform.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.aepscolombia.platform.models.dao.EntitiesDao;
import org.aepscolombia.platform.models.dao.FarmsDao;

import org.aepscolombia.platform.models.entity.Farms;
import org.aepscolombia.platform.models.dao.LogEntitiesDao;
import org.aepscolombia.platform.models.dao.FieldsDao;
import org.aepscolombia.platform.models.dao.FieldTypesDao;
import org.aepscolombia.platform.models.dao.ProducersDao;
import org.aepscolombia.platform.models.dao.UsersDao;
import org.aepscolombia.platform.models.entity.Entities;

import org.aepscolombia.platform.models.entity.LogEntities;
import org.aepscolombia.platform.models.entity.Fields;
import org.aepscolombia.platform.models.entity.FieldTypes;
import org.aepscolombia.platform.models.entity.FieldsProducers;
import org.aepscolombia.platform.models.entity.FieldsProducersId;
import org.aepscolombia.platform.models.entity.Users;
import org.aepscolombia.platform.util.APConstants;

import org.aepscolombia.platform.util.HibernateUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Clase ActionFarm
 *
 * Contiene los metodos para interactuar con el modulo de Lotes
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ActionField extends BaseAction {
    
    //Atributos del formulario 
    /**
     * Atributos provenientes del formulario
     */
    private int idProducer;
    private int idFarm;
    private int idField;
    private String name_producer_lot;
    private String name_property_lot;
    private int typeLot;
    private String name_lot;
    private String latitude_lot;
    private String length_lot;
    private Double latitude_degrees_lot;
    private Double latitude_minutes_lot;
    private Double latitude_seconds_lot;
    private Double length_degrees_lot;
    private Double length_minutes_lot;
    private Double length_seconds_lot;
    private String altitude_lot;
    private String area_lot;
    public List<HashMap> listLot;    
    private List<FieldTypes> type_property_lot;   
    private Users user;
    private Integer idEntSystem;
    private Integer idUsrSystem;
    private Integer searchFromField;
    private String search_field;
    private UsersDao usrDao;

    
    //Metodos getter y setter por cada variable del formulario 
    /**
     * Metodos getter y setter por cada variable del formulario
     */
    public int getIdFarm() {
        return idFarm;
    }

    public void setIdFarm(int idFarm) {
        this.idFarm = idFarm;
    }      

    public Integer getSearchFromField() {
        return searchFromField;
    }

    public void setSearchFromField(Integer searchFromField) {
        this.searchFromField = searchFromField;
    }

    public String getSearch_field() {
        return search_field;
    }

    public void setSearch_field(String search_field) {
        this.search_field = search_field;
    }   
    
    public int getIdProducer() {
        return idProducer;
    }

    public void setIdProducer(int idProducer) {
        this.idProducer = idProducer;
    }   

    public int getIdField() {
        return idField;
    }

    public void setIdField(int idField) {
        this.idField = idField;
    }
    
    public String getName_producer_lot() {
        return name_producer_lot;
    }

    public void setName_producer_lot(String name_producer_lot) {
        this.name_producer_lot = name_producer_lot;
    }

    public int getTypeLot() {
        return typeLot;
    }

    public void setTypeLot(int typeLot) {
        this.typeLot = typeLot;
    }

    public String getName_lot() {
        return name_lot;
    }

    public void setName_lot(String name_lot) {
        this.name_lot = name_lot;
    }

    public String getLatitude_lot() {
        return latitude_lot;
    }

    public void setLatitude_lot(String latitude_lot) {
        this.latitude_lot = latitude_lot;
    }

    public String getLength_lot() {
        return length_lot;
    }

    public void setLength_lot(String length_lot) {
        this.length_lot = length_lot;
    }

    public Double getLatitude_degrees_lot() {
        return latitude_degrees_lot;
    }

    public void setLatitude_degrees_lot(Double latitude_degrees_lot) {
        this.latitude_degrees_lot = latitude_degrees_lot;
    }

    public Double getLatitude_minutes_lot() {
        return latitude_minutes_lot;
    }

    public void setLatitude_minutes_lot(Double latitude_minutes_lot) {
        this.latitude_minutes_lot = latitude_minutes_lot;
    }

    public Double getLatitude_seconds_lot() {
        return latitude_seconds_lot;
    }

    public void setLatitude_seconds_lot(Double latitude_seconds_lot) {
        this.latitude_seconds_lot = latitude_seconds_lot;
    }

    public Double getLength_degrees_lot() {
        return length_degrees_lot;
    }

    public void setLength_degrees_lot(Double length_degrees_lot) {
        this.length_degrees_lot = length_degrees_lot;
    }

    public Double getLength_minutes_lot() {
        return length_minutes_lot;
    }

    public void setLength_minutes_lot(Double length_minutes_lot) {
        this.length_minutes_lot = length_minutes_lot;
    }

    public Double getLength_seconds_lot() {
        return length_seconds_lot;
    }

    public void setLength_seconds_lot(Double length_seconds_lot) {
        this.length_seconds_lot = length_seconds_lot;
    }

    public String getAltitude_lot() {
        return altitude_lot;
    }

    public void setAltitude_lot(String altitude_lot) {
        this.altitude_lot = altitude_lot;
    }

    public String getArea_lot() {
        return area_lot;
    }

    public void setArea_lot(String area_lot) {
        this.area_lot = area_lot;
    }

    public List<FieldTypes> getType_property_lot() {
        return type_property_lot;
    }

    public void setType_property_lot(List<FieldTypes> type_property_lot) {
        this.type_property_lot = type_property_lot;
    }   

    public String getName_property_lot() {
        return name_property_lot;
    }

    public void setName_property_lot(String name_property_lot) {
        this.name_property_lot = name_property_lot;
    }
    
    public List<HashMap> getListLot() {
        return listLot;
    }   
    
    //Atributos generales de clase
    /**
     * Atributos generales de clase
     */
    private HashMap additionals;
    private int page = 1, countTotal, maxResults = 10;
    private int option_geo_lot;
    
    private FieldsDao lotDao    = new FieldsDao();
    private LogEntitiesDao logDao = new LogEntitiesDao();
    
    private String state = "";
    private String info  = "";
    private String valId ="", valName="", selected="";
    
    //Metodos getter y setter por cada variable general de la clase
    /**
     * Metodos getter y setter por cada variable general de la clase
     */
    public String getValId() {
        return valId;
    }

    public String getValName() {
        return valName;
    }

    public String getSelected() {
        return selected;
    }

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

    public int getOption_geo_lot() {
        return option_geo_lot;
    }

    public void setOption_geo_lot(int option_geo_lot) {
        this.option_geo_lot = option_geo_lot;
    }       
    
    private Integer typeEnt;

    public Integer getTypeEnt() {
        return typeEnt;
    }

    public void setTypeEnt(Integer typeEnt) {
        this.typeEnt = typeEnt;
    }
    
    @Override
    public String execute() throws Exception {
//        this.setType_ident_producer(new TiposDocumentosDao().findAll());
//        this.setDepartment_producer(new DepartamentosDao().findAll());
//        this.setType_property_lot(new FieldTypesDao().findAll());
        return SUCCESS;
    }
    
    @Override
    public void prepare() throws Exception {
        user = (Users) this.getSession().get(APConstants.SESSION_USER);
        idEntSystem = UsersDao.getEntitySystem(user.getIdUsr());
        this.setType_property_lot(new FieldTypesDao().findAll());
        usrDao = new UsersDao();
        idUsrSystem = user.getIdUsr();
        EntitiesDao entDao = new EntitiesDao();
        Entities entTemp = entDao.findById(idEntSystem);
        typeEnt = entTemp.getEntitiesTypes().getIdEntTyp();
    }
    
    private Map fieldError;

    public Map getFieldError() {
        return fieldError;
    }

    public void setFieldError(Map fieldError) {
        this.fieldError = fieldError;
    }
    
    
    
    public String viewPosition() {
        Double lonLot = (length_lot.equals("")) ? 0.0 : Double.parseDouble(length_lot.replace(',','.'));
        Double latLot = (latitude_lot.equals("")) ? 0.0 : Double.parseDouble(latitude_lot.replace(',','.'));
        info = "";
        if (latLot!=0) {
            if (latLot < (-4.3) || latLot > (13.5)) {
                addFieldError("latitude_lot", "Dato invalido valor entre -4.3 y 13.5");
                state = "failure";
                info  += "Dato invalido valor entre -4.3 y 13.5";
            }
        } else {
            addFieldError("latitude_lot", "Dato invalido valor entre -4.3 y 13.5");
            state = "failure";
            info  += "Dato invalido valor entre -4.3 y 13.5";
        }

        if (lonLot!=0) {
            if (lonLot < (-81.8) || lonLot > (-66)) {
                addFieldError("length_lot", "Dato invalido valor entre -81.8 y -66");
                state = "failure";
                info  += "Dato invalido valor entre -81.8 y -66";
            }
        } else {
            addFieldError("length_lot", "Dato invalido valor entre -81.8 y -66");
            state = "failure";
            info  += "Dato invalido valor entre -81.8 y -66";
        }
        fieldError = getFieldErrors();
        if (state.equals("failure")) return "states";
        return SUCCESS;
    }
    
    
    /**
     * Metodo encargado de validar el formulario de Lotes
     */
    @Override
    public void validate() {       
//        super.validate();
        /*
         * Se evalua dependiendo a la accion realizada:
         * 1) create: Al momento de guardar un registro por primera ves
         * 2) modify: Al momento de modificar un registro
         * 3) delete: Al momento de borrar un registro
         */
//        System.out.println("11111111111111111111");
        if (actExe.equals("create") || actExe.equals("modify")) {
//            System.out.println("entreeeeeeeeeeeeeeeeeeeeez");
            HashMap required = new HashMap();
//            required.put("name_producer_lot", name_producer_lot);
            required.put("name_property_lot", name_property_lot);
            required.put("name_lot", name_lot);
            required.put("typeLot", typeLot);
            required.put("altitude_lot", altitude_lot);
//            required.put("area_lot", area_lot);
            boolean enter = false;
            
//            if (option_geo_lot == 1) {
                required.put("latitude_lot", latitude_lot);
                required.put("length_lot", length_lot);
//            } else if (option_geo_lot == 2) {
//                required.put("latitude_degrees_property", latitude_degrees_lot);
//                required.put("latitude_minutes_property", latitude_minutes_lot);
//                required.put("latitude_seconds_property", latitude_seconds_lot);
//                required.put("length_degrees_property", length_degrees_lot);
//                required.put("length_minutes_property", length_minutes_lot);
//                required.put("length_seconds_property", length_seconds_lot);
//            }
            
//            System.out.println("required->"+required);
            
            for (Iterator it = required.keySet().iterator(); it.hasNext();) {
                String sK = (String) it.next();
                String sV = String.valueOf(required.get(sK));
//                System.out.println(sK + " : " + sV);
//                addFieldError(sK, "El campo es requerido");
                if (StringUtils.trim(sV).equals("null") || StringUtils.trim(sV)==null || StringUtils.trim(sV).equals("") || sV.equals("-1")) {
                    addFieldError(sK, "El campo es requerido");
                    enter = true;
                }
            }
            
            if (enter) {
                addActionError("Faltan campos por ingresar por favor digitelos");
            }
            
            Double altLot = (altitude_lot.equals("")) ? 0.0 : Double.parseDouble(altitude_lot.replace(',','.'));
            Double latLot = (latitude_lot.equals("")) ? 0.0 : Double.parseDouble(latitude_lot.replace(',','.'));
            Double lonLot = (length_lot.equals("")) ? 0.0 : Double.parseDouble(length_lot.replace(',','.'));
            Double areaLot = (area_lot.equals("")) ? 0.0 : Double.parseDouble(area_lot.replace(',','.'));
//            Double altLot = (altitude_lot.equals("")) ? 0.0 : Double.parseDouble(altitude_lot);
//            Double latLot = (latitude_lot.equals("")) ? 0.0 : Double.parseDouble(latitude_lot);
//            Double lonLot = (length_lot.equals("")) ? 0.0 : Double.parseDouble(length_lot);
//            Double areaLot = (area_lot.equals("")) ? 0.0 : Double.parseDouble(area_lot);
            
//            if (altitude_property) {    
            if (altLot<0 || altLot>9000) {
                addFieldError("altitude_lot", "Dato invalido valor entre 0 y 9000");
                addActionError("Se ingreso una altitud invalida, por favor ingresar un valor entre 0 y 9000");
            }
//            }
                
            if (areaLot<0 || areaLot>3000) {
                addFieldError("area_lot", "Dato invalido valor entre 0 y 3000");
                addActionError("Se ingreso un area invalida, por favor ingresar un valor entre 0 y 3000");
            }
            
            if (latitude_lot.equals("") || latitude_lot==null) {
                addFieldError("latitude_lot", "Debe ingresar alguno de estos datos");
                addFieldError("latitude_degrees_lot", "");
                addFieldError("latitude_minutes_lot", "");
                addFieldError("latitude_seconds_lot", "");
                addActionError("Debe ingresar la latitud en Decimales o en Grados");
            }
            
            if (length_lot.equals("") || length_lot==null) {
                addFieldError("length_lot", "Debe ingresar alguno de estos datos");
                addFieldError("length_degrees_lot", "");
                addFieldError("length_minutes_lot", "");
                addFieldError("length_seconds_lot", "");
                addActionError("Debe ingresar la longitud en Decimales o en Grados");
            }

//            if (option_geo_lot == 1) {
//                if (latitude_property!=null) { 
            if (latLot!=0) {
                if (latLot<(-4.3) || latLot>(13.5)) {
                    addFieldError("latitude_lot", "Dato invalido valor entre -4.3 y 13.5");
                    addActionError("Se ingreso una latitud invalida, por favor ingresar un valor entre -4.3 y 13.5");                        
                }
//                }

//                if (length_property) {    
                    
//                }			

//            } else if (option_geo_lot == 2) {

//                if (latitude_degrees_property && (latitude_degrees_property<(-5) || latitude_degrees_property>14)) {
//                if ((latitude_degrees_lot<(-5) || latitude_degrees_lot>14)) {
//                    addFieldError("latitude_degrees_lot", "Dato invalido");
//                    addActionError("Se ingreso una latitud en grados invalida, por favor ingresar un valor entre -5 y 14");
//                }
//
////                if (latitude_minutes_property && (latitude_minutes_property<0 || latitude_minutes_property>60)) {
//                if ((latitude_minutes_lot<0 || latitude_minutes_lot>60)) {
//                    addFieldError("latitude_minutes_lot", "Dato invalido");
//                    addActionError("Se ingreso una latitud en minutos invalida, por favor ingresar un valor entre 0 y 60");
//                }
//
////                if (latitude_seconds_property && (latitude_seconds_property<0 || latitude_seconds_property>60)) {
//                if ((latitude_seconds_lot<0 || latitude_seconds_lot>60)) {
//                    addFieldError("latitude_seconds_lot", "Dato invalido");
//                    addActionError("Se ingreso una latitud en segundos invalida, por favor ingresar un valor entre 0 y 60");
//                }		
            
            }
            
            if (lonLot!=0) {
                
                if (lonLot<(-81.8) || lonLot>(-66)) {
                    addFieldError("length_lot", "Dato invalido valor entre -81.8 y -66");
                    addActionError("Se ingreso una longitud invalida, por favor ingresar un valor entre -81.8 y -66");
                }
//                if (lonLot!=0) {
////                if (length_degrees_property && (length_degrees_property<(-82) || length_degrees_property>(-66))) {
//                    if ((length_degrees_lot<(-82) || length_degrees_lot>(-66))) {
//                        addFieldError("length_degrees_lot", "Dato invalido");
//                        addActionError("Se ingreso una longitud en grados invalida, por favor ingresar un valor entre -82 y -66");
//                    }
//
//    //                if (length_minutes_property && (length_minutes_property<0 || length_minutes_property>60)) {
//                    if ((length_minutes_lot<0 || length_minutes_lot>60)) {
//                        addFieldError("length_minutes_lot", "Dato invalido");
//                        addActionError("Se ingreso una longitud en minutos invalida, por favor ingresar un valor entre 0 y 60");
//                    }
//
//    //                if (length_seconds_property && (length_seconds_property<0 || length_seconds_property>60)) {
//                    if ((length_seconds_lot<0 || length_seconds_lot>60)) {
//                        addFieldError("length_seconds_lot", "Dato invalido");
//                        addActionError("Se ingreso una longitud en segundos invalida, por favor ingresar un valor entre 0 y 60");
//                    }
//                }
            }
        }
    }
//    public String crear() {    
//        return SUCCESS;
//    }    
    
    /**
     * Se obtiene la lista de opciones de cada uno de los tipos de lotes registrados en BD
     * @return lista de tipos de lotes
     */
    public String comboFieldTypes() {
        FieldTypesDao eventDao = new FieldTypesDao();
        type_property_lot = eventDao.findAll();
        return "combo";
    } 

    /**
     * Encargado de buscar las coincidencias de un formulario de busqueda, para cada una de los
     * lotes registrados a un usuario
     * @param valName:  Nombre del valor a buscar
     * @param valId:    Identificacion del valor a buscar
     * @param selected: Valor seleccionado
     * @return lista de lotes
     */
    public String search() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "field/list")) {
            return BaseAction.NOT_AUTHORIZED;
        }
        valName     = (String)(this.getRequest().getParameter("valName"));
        valId       = (String)(this.getRequest().getParameter("valId"));
        selected    = (String)(this.getRequest().getParameter("selected"));
        viewInfo    = (String)(this.getRequest().getParameter("viewInfo"));
        if(selected==null) selected="lot";
        additionals = new HashMap();
        additionals.put("selected", selected);
        HashMap findParams = new HashMap();
        
        if(searchFromField!=null && searchFromField==2) {
            search_field = "";
        }
        
//        findParams.put("name_lot", this.getName_lot());
        findParams.put("idEntUser", idEntSystem);
        findParams.put("search_field", search_field);
        findParams.put("name_producer_lot", name_producer_lot);
        findParams.put("name_property_lot", name_property_lot);
        findParams.put("typeLot", typeLot);
        findParams.put("altitude_lot", altitude_lot);
        findParams.put("area_lot", area_lot);
        findParams.put("latitude_property", latitude_lot);
        findParams.put("length_property", length_lot);
        int pageReq;
        if (this.getRequest().getParameter("page") == null) {
            pageReq = this.getPage();
        } else {
            pageReq = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter("page")));
        }
        findParams.put("pageNow", pageReq);
        findParams.put("maxResults", this.getMaxResults());
//        FarmsDao eventDao = new FarmsDao();
        listLot = lotDao.findByParams(findParams);
//        this.setCountTotal(100);
        this.setCountTotal(Integer.parseInt(String.valueOf(listLot.get(0).get("countTotal"))));
        this.setPage(page);
        listLot.remove(0);
//        System.out.println("countTotal->"+this.getCountTotal());
        return SUCCESS;
    }

    /**
     * Encargado de mostrar en un formulario la informacion de un lote apartir de la identificacion
     * @param idField:  Identificacion del lote
     * @return Informacion del lote
     */
    public String show() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "field/create") || !usrDao.getPrivilegeUser(idUsrSystem, "field/modify")) {
            return BaseAction.NOT_AUTHORIZED;
        }
        actExe = (String)(this.getRequest().getParameter("action"));
        viewInfo    = (String)(this.getRequest().getParameter("viewInfo"));
        int pageReq;
        if (this.getRequest().getParameter("page") != null) {
            pageReq = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter("page")));
            this.setPage(pageReq);
        }
        try {
            this.setIdField(Integer.parseInt(this.getRequest().getParameter("idField")));
        } catch (NumberFormatException e) {
//            LOG.error("There was an error trying to parse the activityId parameter");
            this.setIdField(-1);
        }

        this.setType_property_lot(new FieldTypesDao().findAll());

        if (this.getIdField()!= -1) {
//            LotsDao eventDao = new LotsDao();
//            System.out.println("id_productor->"+this.getIdProductor());
            HashMap fieldInfo = lotDao.findById(this.getIdField());
//            System.out.println("valores->" + fieldInfo);
            
            //Pasar la conversion a grados, min, seg por defecto ??
//            this.setIdProductor(Integer.parseInt(String.valueOf(fieldInfo.get("id_productor"))));
            this.setIdFarm(Integer.parseInt(String.valueOf(fieldInfo.get("id_farm"))));
            this.setIdField(Integer.parseInt(String.valueOf(fieldInfo.get("id_lot"))));
//            this.setIdProducer(Integer.parseInt(String.valueOf(fieldInfo.get("id_producer"))));
            this.setTypeLot(Integer.parseInt(String.valueOf(fieldInfo.get("type_lot"))));
            this.setName_producer_lot(String.valueOf(fieldInfo.get("name_producer")));
            this.setName_property_lot(String.valueOf(fieldInfo.get("name_farm")));
            this.setName_lot(String.valueOf(fieldInfo.get("name_lot")));
            this.setLatitude_lot(String.valueOf(fieldInfo.get("latitude_lot")).replace('.',','));
            this.setLength_lot(String.valueOf(fieldInfo.get("length_lot")).replace('.',','));
//            this.setOption_geo(1);
            
            this.setAltitude_lot(String.valueOf(fieldInfo.get("altitude_lot")));
            this.setArea_lot(String.valueOf(fieldInfo.get("area_lot")));
        }
        return SUCCESS;
    }    

    /**
     * Encargado de guardar la informacion suministrada por el usuario para un lote
     * @return Estado del proceso
     */
    public String saveData() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "field/create") || !usrDao.getPrivilegeUser(idUsrSystem, "field/modify")) {
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

        ProducersDao proDao = new ProducersDao();
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Transaction tx = null;
        
        Double altLot = Double.parseDouble(altitude_lot.replace(',','.'));
        Double latLot = Double.parseDouble(latitude_lot.replace(',','.'));
        Double lonLot = Double.parseDouble(length_lot.replace(',','.'));
        Double areaLot = null;
        if (!area_lot.isEmpty()) {
            areaLot = Double.parseDouble(area_lot.replace(',','.'));
        }
//        Double altLot = Double.parseDouble(altitude_lot);
//        Double latLot = Double.parseDouble(latitude_lot);
//        Double lonLot = Double.parseDouble(length_lot);
//        Double areaLot = Double.parseDouble(area_lot);
        
//        if (option_geo_lot == 2) {
//            latLot = (latitude_minutes_lot/60) + (latitude_seconds_lot/3600);
//            latLot = (latitude_degrees_lot<0) ? ((Math.abs(latitude_degrees_lot))+latLot)*-1 : (latitude_degrees_lot+latLot);
//
//            lonLot = (length_minutes_lot/60) + (length_seconds_lot/3600);
//            lonLot = (length_degrees_lot<0) ? ((Math.abs(length_degrees_lot))+lonLot)*-1 : (length_degrees_lot+lonLot);
//        }  

        try {
            int idProOld = 0;
            tx = session.beginTransaction();
            Fields lot = null;
//            FarmsDao farmDao = new FarmsDao();
            HashMap objFarm = new FarmsDao().findById(idFarm);
            idProducer = Integer.parseInt(String.valueOf(objFarm.get("id_producer")));
            
            if (idField<=0) {
                lot = new Fields();
                lot.setIdFie(null);
                lot.setIdProjectFie("1");
                lot.setStatus(true); 
                lot.setMeasureUnitFie("1");
            } else {
                HashMap fieldInfo = lotDao.findById(idField);
                idProOld = Integer.parseInt(String.valueOf(fieldInfo.get("id_producer")));
                lot = lotDao.objectById(idField);
            }
//            lot.setIdFarmFie(idFarm);
            if(idFarm>0) lot.setFarms(new Farms(idFarm));
            lot.setNameFie(name_lot);
            lot.setAltitudeFie(altLot);
            lot.setLatitudeFie(latLot);
            lot.setLongitudeFie(lonLot);
            if (areaLot!=null) lot.setAreaFie(areaLot);            
            lot.setFieldTypes(new FieldTypes(typeLot));
//            lot.setControlPlagasLot(true);
//            lot.setControlEnfermedadesLot(true);       
            session.saveOrUpdate(lot);
//            lotDao.save(lot);
            
            if(lot.getIdFie()>0 && action.equals("M")) {
                FieldsProducers fieTemp = lotDao.checkFieldProducer(lot.getIdFie(), idProOld);
                if(idProOld!=idProducer) {                    
                    session.delete(fieTemp);
                } else {
//                    fieTemp.setFieldTypes(new FieldTypes(typeLot));
                    session.saveOrUpdate(fieTemp);
                }
//                System.out.println("id field->"+fiePro.getFields().getIdFie());
//                fiePro = new FieldsProducers();
//                fiePro.setId(new FieldsProducersId(lot.getIdFie(), idProducer));
//                fiePro.setFields(lot);   
//                fiePro.setProducers(proDao.objectById(idProducer));
//                fiePro.setFieldTypes(new FieldTypes(typeLot));
//                session.saveOrUpdate(fiePro);
            }
            
            if(idProOld!=idProducer) {
//            if(action.equals("C")) {
                FieldsProducers fiePro = new FieldsProducers();
                fiePro.setId(new FieldsProducersId(lot.getIdFie(), idProducer));
                fiePro.setFields(lot);   
                fiePro.setProducers(proDao.objectById(idProducer));
//                fiePro.setFieldTypes(new FieldTypes(typeLot));
                session.saveOrUpdate(fiePro);
            }
            
            LogEntities log = new LogEntities();
            log.setIdLogEnt(null);
            log.setIdEntityLogEnt(idEntSystem); //Colocar el usuario registrado en el sistema
            log.setIdObjectLogEnt(lot.getIdFie());
            log.setTableLogEnt("fields");
            log.setDateLogEnt(new Date());
            log.setActionTypeLogEnt(action);
            session.saveOrUpdate(log);
//            logDao.save(log);            
            
            tx.commit();           
            state = "success";
            if (action.equals("C")) {
                info = "El lote ha sido agregado con exito";
            } else if (action.equals("M")) {
                info  = "El lote ha sido modificado con exito";
            }            
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            state = "failure";
            info  = "Fallo al momento de agregar el lote";
        } finally {
            session.close();
        }       
        
        return "states";
//        return ERROR;
    }

    /**
     * Encargado de borrar la informacion de un lote apartir de su identificacion
     * @param idField:  Identificacion del lote
     * @return Estado del proceso
     */
    public String delete() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "field/delete")) {
            return BaseAction.NOT_AUTHORIZED;
        }
        Integer idField = 0;
        try {
            idField = Integer.parseInt(this.getRequest().getParameter("idFar"));
        } catch (NumberFormatException e) {
            idField = -1;
        }
        
        if (idField==-1) {
            state = "failure";
            info  = "Fallo al momento de obtener la informacion a borrar";
            return "states";
        }
        
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Fields lot = lotDao.objectById(idField);  
            lot.setStatus(false);     
            session.saveOrUpdate(lot);
//            session.delete(lot);
//            lotDao.delete(lot);            
            
            LogEntities log = new LogEntities();
            log.setIdLogEnt(null);
            log.setIdEntityLogEnt(idEntSystem); //Colocar el usuario registrado en el sistema
            log.setIdObjectLogEnt(lot.getIdFie());
            log.setTableLogEnt("fields");
            log.setDateLogEnt(new Date());
            log.setActionTypeLogEnt("D");
            session.saveOrUpdate(log);
//            logDao.save(log);
            tx.commit();         
            state = "success";
            info  = "El lote ha sido borrado con exito";
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            state = "failure";
            info  = "Fallo al momento de borrar un lote";
        } finally {
            session.close();
        }      
        
        return "states";
//        return SUCCESS;
    }
}