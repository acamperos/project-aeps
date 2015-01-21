/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aepscolombia.platform.controllers;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.opensymphony.xwork2.ActionContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.aepscolombia.platform.models.dao.AssociationDao;
import org.aepscolombia.platform.models.dao.DepartmentsDao;
import org.aepscolombia.platform.models.dao.EntitiesDao;

import org.aepscolombia.platform.models.entity.Farms;
import org.aepscolombia.platform.models.dao.FarmsDao;
import org.aepscolombia.platform.models.dao.FieldsDao;
import org.aepscolombia.platform.models.dao.LogEntitiesDao;
import org.aepscolombia.platform.models.dao.MunicipalitiesDao;
import org.aepscolombia.platform.models.dao.ProducersDao;
import org.aepscolombia.platform.models.dao.SfGuardUserDao;
import org.aepscolombia.platform.models.dao.UsersDao;

import org.aepscolombia.platform.models.entity.Departments;
import org.aepscolombia.platform.models.entity.Entities;
import org.aepscolombia.platform.models.entity.FarmsProducers;
import org.aepscolombia.platform.models.entity.FarmsProducersId;
import org.aepscolombia.platform.models.entity.LogEntities;
import org.aepscolombia.platform.models.entity.Municipalities;
import org.aepscolombia.platform.models.entity.Producers;
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
 * Clase ActionFarm
 *
 * Contiene los metodos para interactuar con el modulo de Fincas
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ActionFarm extends BaseAction {

    //Atributos del formulario 
    /**
     * Atributos provenientes del formulario
     */
    private int idProducer;
    private int idFarm;
    private String name_producer;
    private String name_property;
    private String latitude_property;
    private String length_property;
    private Double latitude_degrees_property;
    private Double latitude_minutes_property;
    private Double latitude_seconds_property;
    private Double length_degrees_property;
    private Double length_minutes_property;
    private Double length_seconds_property;
    private String altitude_property;
    private String direction_property;
    private List<Departments> department_property;
    private List<Municipalities> city_property;
    private String depFar = "";
    private String cityFar = "";
    private String lane_property;
    private String identProducer;    
    public List<HashMap> listProducers;
    public List<HashMap> listProperties;
    private List<HashMap> listRoute;
    private Users user;
    private Integer idEntSystem;
    private Integer idUsrSystem;
    private Integer searchFromFarm;
    private String search_farm;    
    private UsersDao usrDao;
    private List<Entities> list_agronomist;
    private AssociationDao assDao;

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

    public Integer getSearchFromFarm() {
        return searchFromFarm;
    }

    public void setSearchFromFarm(Integer searchFromFarm) {
        this.searchFromFarm = searchFromFarm;
    }

    public String getSearch_farm() {
        return search_farm;
    }

    public void setSearch_farm(String search_farm) {
        this.search_farm = search_farm;
    }

    public List<Entities> getList_agronomist() {
        return list_agronomist;
    }

    public void setList_agronomist(List<Entities> list_agronomist) {
        this.list_agronomist = list_agronomist;
    }

    public AssociationDao getAssDao() {
        return assDao;
    }

    public void setAssDao(AssociationDao assDao) {
        this.assDao = assDao;
    }   
    
    public List<HashMap> getListProperties() {
        return listProperties;
    }

    public String getName_producer() {
        return name_producer;
    }

    public void setName_producer(String name_producer) {
        this.name_producer = name_producer;
    }

    public String getName_property() {
        return name_property;
    }

    public void setName_property(String name_property) {
        this.name_property = name_property;
    }

    public String getLatitude_property() {
        return latitude_property;
    }

    public void setLatitude_property(String latitude_property) {
        this.latitude_property = latitude_property;
    }

    public String getLength_property() {
        return length_property;
    }

    public void setLength_property(String length_property) {
        this.length_property = length_property;
    }

    public Double getLatitude_degrees_property() {
        return latitude_degrees_property;
    }

    public void setLatitude_degrees_property(Double latitude_degrees_property) {
        this.latitude_degrees_property = latitude_degrees_property;
    }

    public Double getLatitude_minutes_property() {
        return latitude_minutes_property;
    }

    public void setLatitude_minutes_property(Double latitude_minutes_property) {
        this.latitude_minutes_property = latitude_minutes_property;
    }

    public Double getLatitude_seconds_property() {
        return latitude_seconds_property;
    }

    public void setLatitude_seconds_property(Double latitude_seconds_property) {
        this.latitude_seconds_property = latitude_seconds_property;
    }

    public Double getLength_degrees_property() {
        return length_degrees_property;
    }

    public void setLength_degrees_property(Double length_degrees_property) {
        this.length_degrees_property = length_degrees_property;
    }

    public Double getLength_minutes_property() {
        return length_minutes_property;
    }

    public void setLength_minutes_property(Double length_minutes_property) {
        this.length_minutes_property = length_minutes_property;
    }

    public Double getLength_seconds_property() {
        return length_seconds_property;
    }

    public void setLength_seconds_property(Double length_seconds_property) {
        this.length_seconds_property = length_seconds_property;
    }

    public String getAltitude_property() {
        return altitude_property;
    }

    public void setAltitude_property(String altitude_property) {
        this.altitude_property = altitude_property;
    }

    public String getDirection_property() {
        return direction_property;
    }

    public void setDirection_property(String direction_property) {
        this.direction_property = direction_property;
    }

    public String getDepFar() {
        return depFar;
    }

    public void setDepFar(String depFar) {
        this.depFar = depFar;
    }

    public String getCityFar() {
        return cityFar;
    }

    public void setCityFar(String cityFar) {
        this.cityFar = cityFar;
    }

    public String getLane_property() {
        return lane_property;
    }

    public void setLane_property(String lane_property) {
        this.lane_property = lane_property;
    }

    public List<Departments> getDepartment_property() {
        return department_property;
    }
    
    public void setDepartment_property(List<Departments> department_property) {
        this.department_property = department_property;
    }

    public List<Municipalities> getCity_property() {
        return city_property;
    }    

    public void setCity_property(List<Municipalities> city_property) {
        this.city_property = city_property;
    }

    public int getIdProducer() {
        return idProducer;
    }

    public void setIdProducer(int idProducer) {
        this.idProducer = idProducer;
    }   

    public String getIdentProducer() {
        return identProducer;
    }

    public void setIdentProducer(String identProducer) {
        this.identProducer = identProducer;
    }   

    public List<HashMap> getListProducers() {
        return listProducers;
    }
    
    //Atributos generales de clase
    /**
     * Atributos generales de clase
     */
    private HashMap additionals;
    private int page = 1, countTotal, maxResults = 10;
    private int option_geo;
    private FarmsDao farDao = new FarmsDao();
    private LogEntitiesDao logDao = new LogEntitiesDao();
    private String state = "";
    private String info = "";
    private String valId = "", valName = "", selected = "";

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

    public int getOption_geo() {
        return option_geo;
    }

    public void setOption_geo(int option_geo) {
        this.option_geo = option_geo;
    }

    public String getState() {
        return state;
    }

    public String getInfo() {
        return info;
    }

    public FarmsDao getEntDao() {
        return farDao;
    }

    public void setFarDao(FarmsDao farDao) {
        this.farDao = farDao;
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
    
    private Integer typeEnt;

    public Integer getTypeEnt() {
        return typeEnt;
    }

    public void setTypeEnt(Integer typeEnt) {
        this.typeEnt = typeEnt;
    }   
    
    
    @Override
    public void prepare() throws Exception {
        user = (Users) ActionContext.getContext().getSession().get(APConstants.SESSION_USER);
//        user = (Users) this.getSession().get(APConstants.SESSION_USER);
        idEntSystem = UsersDao.getEntitySystem(user.getIdUsr());
        EntitiesDao entDao = new EntitiesDao();
        Entities entTemp = entDao.findById(idEntSystem);
        typeEnt = entTemp.getEntitiesTypes().getIdEntTyp();
        if (entTemp.getEntitiesTypes().getIdEntTyp()==2) {
            ProducersDao proDao = new ProducersDao();
            Producers proTemp   = new Producers();
            proTemp = proDao.objectByEntityId(idEntSystem);
            if (proTemp!=null) {
                idProducer    = proTemp.getIdPro();
                name_producer = entTemp.getNameEnt();
            }
        }
        this.setDepartment_property(new DepartmentsDao().findAll());
        usrDao = new UsersDao();
        idUsrSystem = user.getIdUsr();
        List<Municipalities> mun = new ArrayList<Municipalities>();
        mun.add(new Municipalities());           
        this.setCity_property(mun);
        assDao = new AssociationDao();
//        HashMap route = new HashMap();
//        route.put("getting", getText("email.from"));
//        listRoute.add(route);
//        route = new HashMap();
////        String val = getText('text.title.farm');
//        route.put("listFarm", getText("email.from"));
//        listRoute.add(route);
        
    }

    @Override
    public String execute() {
//        this.setType_ident_producer(new TiposDocumentosDao().findAll());
//        this.setDepartment_property(new DepartmentsDao().findAll());
        return SUCCESS;
    }
    
    private Map fieldError;

    public Map getFieldError() {
        return fieldError;
    }

    public void setFieldError(Map fieldError) {
        this.fieldError = fieldError;
    }
    
    
    
    public String viewPosition() {
        Double latPro = (latitude_property==null || latitude_property.isEmpty() || latitude_property.equals("")) ? 0.0 : Double.parseDouble(latitude_property.replace(',','.'));
        Double lonPro = (length_property==null || length_property.isEmpty() || length_property.equals("")) ? 0.0 : Double.parseDouble(length_property.replace(',','.'));
//        position = this.getRequest().getParameter("position");
//        if (position.equals("in")) {   
        info = "";
            if (latPro!=0) {
                if (latPro < (-4.3) || latPro > (13.5)) {
                    addFieldError("latitude_property", "Dato invalido valor entre -4.3 y 13.5");
                    state = "failure";
                    info  = "Dato invalido valor entre -4.3 y 13.5";
                }
            } else {
                addFieldError("latitude_property", "Dato invalido valor entre -4.3 y 13.5");
                state = "failure";
                info  = "Dato invalido valor entre -4.3 y 13.5";
            }
            
            if (lonPro!=0) {
                if (lonPro < (-81.8) || lonPro > (-66)) {
                    addFieldError("length_property", "Dato invalido valor entre -81.8 y -66");
                    state = "failure";
                    info  = "Dato invalido valor entre -81.8 y -66";
                }
            } else {
//                getFieldErrors()
                addFieldError("length_property", "Dato invalido valor entre -81.8 y -66");
                state = "failure";
                info  = "Dato invalido valor entre -81.8 y -66";
            }
            fieldError = getFieldErrors();
//        }
        if (state.equals("failure")) return "states";
        return SUCCESS;
    }

    /**
     * Metodo encargado de validar el formulario de Fincas
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
        if (actExe.equals("create") || actExe.equals("modify")) {
            HashMap required = new HashMap();
            if (typeEnt!=2) required.put("name_producer", name_producer);
            required.put("name_property", name_property);
            required.put("depFar", depFar);
            required.put("cityFar", cityFar);
            required.put("lane_property", lane_property);
            required.put("altitude_property", altitude_property);
            boolean enter = false;
//            if (option_geo == 1) {
                required.put("latitude_property", latitude_property);
                required.put("length_property", length_property);
//            } else if (option_geo == 2) {
//                required.put("latitude_degrees_property", latitude_degrees_property);
//                required.put("latitude_minutes_property", latitude_minutes_property);
//                required.put("latitude_seconds_property", latitude_seconds_property);
//                required.put("length_degrees_property", length_degrees_property);
//                required.put("length_minutes_property", length_minutes_property);
//                required.put("length_seconds_property", length_seconds_property);
//            }
//            System.out.println("values->"+required);
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
            
//            for (Iterator it = required.keySet().iterator(); it.hasNext();) {
//                String sK = (String) it.next();
//                String sV = String.valueOf(required.get(sK));
//                boolean stop = false;
//                if (sV.equals(sK)) {
//                    stop = true;
//                }
//                if (stop) {
//                    required.remove(sK);
//                }
//            }
            
//            System.out.println("latitude_seconds_property=>"+latitude_seconds_property);
//            Double altPro = (altitude_property==null || altitude_property.isEmpty() || altitude_property.equals("")) ? 0.0 : Double.parseDouble(altitude_property);
//            Double latPro = (latitude_property==null || latitude_property.isEmpty() || latitude_property.equals("")) ? 0.0 : Double.parseDouble(latitude_property);
//            Double lonPro = (length_property==null || length_property.isEmpty() || length_property.equals("")) ? 0.0 : Double.parseDouble(length_property);
            Double altPro = (altitude_property==null || altitude_property.isEmpty() || altitude_property.equals("")) ? 0.0 : Double.parseDouble(altitude_property.replace(',','.'));
            Double latPro = (latitude_property==null || latitude_property.isEmpty() || latitude_property.equals("")) ? 0.0 : Double.parseDouble(latitude_property.replace(',','.'));
            Double lonPro = (length_property==null || length_property.isEmpty() || length_property.equals("")) ? 0.0 : Double.parseDouble(length_property.replace(',','.'));
            
//            if (altitude_property) {    
            if (altPro < 0 || altPro > 9000) {
                addFieldError("altitude_property", "Dato invalido valor entre 0 y 9000");
                addActionError("Se ingreso una altitud invalida, por favor ingresar un valor entre 0 y 9000");
            }
//            }

            if (latitude_property.equals("") || latitude_property==null) {
                addFieldError("latitude_property", "Debe ingresar alguno de estos datos");
                addFieldError("latitude_degrees_property", "");
                addFieldError("latitude_minutes_property", "");
                addFieldError("latitude_seconds_property", "");
                addActionError("Debe ingresar la latitud en Decimales o en Grados");
            }
            
            if (length_property.equals("") || length_property==null) {
                addFieldError("length_property", "Debe ingresar alguno de estos datos");
                addFieldError("length_degrees_property", "");
                addFieldError("length_minutes_property", "");
                addFieldError("length_seconds_property", "");
                addActionError("Debe ingresar la longitud en Decimales o en Grados");
            }
            
//            if (option_geo == 1) {
//            if (!latitude_property.equals("") || latitude_property!=null) {
            if (latPro!=0) {
//                if (latPro!=null) { 
                if (latPro < (-4.3) || latPro > (13.5)) {
                    addFieldError("latitude_property", "Dato invalido valor entre -4.3 y 13.5");
                    addActionError("Se ingreso una latitud invalida, por favor ingresar un valor entre -4.3 y 13.5");
                }
//                }

//                if ((latitude_degrees_property < (-5) || latitude_degrees_property > 14)) {
//                    addFieldError("latitude_degrees_property", "Dato invalido valor entre -5 y 14");
//                    addActionError("Se ingreso una latitud en grados invalida, por favor ingresar un valor entre -5 y 14");
//                }
//
////                if (latitude_minutes_property && (latitude_minutes_property<0 || latitude_minutes_property>60)) {
//                if ((latitude_minutes_property < 0 || latitude_minutes_property > 60)) {
//                    addFieldError("latitude_minutes_property", "Dato invalido valor entre 0 y 59");
//                    addActionError("Se ingreso una latitud en minutos invalida, por favor ingresar un valor entre 0 y 59");
//                }
//
////                if (latitude_seconds_property && (latitude_seconds_property<0 || latitude_seconds_property>60)) {
//                if ((latitude_seconds_property < 0 || latitude_seconds_property > 60)) {
//                    addFieldError("latitude_seconds_property", "Dato invalido valor entre 0 y 59");
//                    addActionError("Se ingreso una latitud en segundos invalida, por favor ingresar un valor entre 0 y 59");
//                }
            }
            
            if (lonPro!=0) {
//            if (lonPro!=0 && (!length_property.equals("") || length_property!=null)) {
//                if (length_property) {    
                if (lonPro < (-81.8) || lonPro > (-66)) {
                    addFieldError("length_property", "Dato invalido valor entre -81.8 y -66");
                    addActionError("Se ingreso una longitud invalida, por favor ingresar un valor entre -81.8 y -66");
                }
//                }

//            } else if (option_geo == 2) {

//                if (latitude_degrees_property && (latitude_degrees_property<(-5) || latitude_degrees_property>14)) {
                

//                if (length_degrees_property && (length_degrees_property<(-82) || length_degrees_property>(-66))) {
//                if ((length_degrees_property < (-82) || length_degrees_property > (-66))) {
//                    addFieldError("length_degrees_property", "Dato invalido valor entre entre -82 y -66");
//                    addActionError("Se ingreso una longitud en grados invalida, por favor ingresar un valor entre -82 y -66");
//                }
//
////                if (length_minutes_property && (length_minutes_property<0 || length_minutes_property>60)) {
//                if ((length_minutes_property < 0 || length_minutes_property > 60)) {
//                    addFieldError("length_minutes_property", "Dato invalido valor entre 0 y 59");
//                    addActionError("Se ingreso una longitud en minutos invalida, por favor ingresar un valor entre 0 y 59");
//                }
//
////                if (length_seconds_property && (length_seconds_property<0 || length_seconds_property>60)) {
//                if ((length_seconds_property < 0 || length_seconds_property > 60)) {
//                    addFieldError("length_seconds_property", "Dato invalido valor entre 0 y 59");
//                    addActionError("Se ingreso una longitud en segundos invalida, por favor ingresar un valor entre 0 y 59");
//                }
            }
        }
    }
    
    /**
     * Se obtiene la lista de opciones de cada uno de los departamentos registrados en BD
     * @return lista de departamentos
     */
    public String comboDepartments() {
        DepartmentsDao eventoDao = new DepartmentsDao();
        department_property = eventoDao.findAll();
        return "combo";
    }

    /**
     * Se obtiene la lista de opciones de cada uno de los municipios registrados en BD
     * apartir de un departamento seleccionado previamente
     * @param depId: Identificacion de un departamento
     * @return lista de municipios
     */
    public String comboMunicipalities() {
        int depId = Integer.parseInt(this.getRequest().getParameter("depId"));
        MunicipalitiesDao eventoDao = new MunicipalitiesDao();
        city_property = eventoDao.findAll(depId);
        String cadena = "<option value=\"\">---</option>";
//        int i = 0;
        for (Municipalities data : city_property) {
            cadena += "<option value=\"" + data.getIdMun() + "\">" + data.getNameMun()+ "</option>";
        }
        city_property = null;
        state = "success";
        info  = cadena;
        return "combo";
    }  
    
    private String name_agronomist;
    private String selectAllname_agronomist;
    private String selectItemname_agronomist;      

    public String getName_agronomist() {
        return name_agronomist;
    }

    public void setName_agronomist(String name_agronomist) {
        this.name_agronomist = name_agronomist;
    }  

    public String getSelectAllname_agronomist() {
        return selectAllname_agronomist;
    }

    public void setSelectAllname_agronomist(String selectAllname_agronomist) {
        this.selectAllname_agronomist = selectAllname_agronomist;
    }

    public String getSelectItemname_agronomist() {
        return selectItemname_agronomist;
    }

    public void setSelectItemname_agronomist(String selectItemname_agronomist) {
        this.selectItemname_agronomist = selectItemname_agronomist;
    }
    
    /**
     * Encargado de buscar las coincidencias de un formulario de busqueda, para cada una de las
     * fincas registradas a un usuario
     * @param valName:  Nombre del valor a buscar
     * @param valId:    Identificacion del valor a buscar
     * @param selected: Valor seleccionado
     * @return lista de fincas
     */
    public String search() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "farm/list")) {
//        if (!false) {
            return BaseAction.NOT_AUTHORIZED;
        }
        valName     = (String) (this.getRequest().getParameter("valName"));
        valId       = (String) (this.getRequest().getParameter("valId"));
        selected    = (String) (this.getRequest().getParameter("selected"));
        viewInfo    = (String) (this.getRequest().getParameter("viewInfo"));
        String selAll = "false";
        if(selected==null) {
            selected="property";
            selAll = "true";
        }       
        
        if (selectAllname_agronomist!=null) {
            selAll = "true";
        }
        additionals = new HashMap();
        additionals.put("selected", selected);
        HashMap findParams = new HashMap();
        findParams.put("selAll", selAll);
        findParams.put("selItem", selectItemname_agronomist);
        Integer entTypeId = new EntitiesDao().getEntityTypeId(user.getIdUsr());
        findParams.put("entType", entTypeId);
        list_agronomist   = assDao.gelAllAgronomist(idEntSystem);
        if(searchFromFarm!=null && searchFromFarm==2) {
            search_farm = "";
        }
        
//        fardParams.put("nameProperty", this.getName_property());
//        System.out.println("this.getAltitude_property()->"+this.getAltitude_property());
        findParams.put("idEntUser", idEntSystem);
        findParams.put("search_farm", search_farm);
        findParams.put("name_producer", name_producer);
        findParams.put("name_property", name_property);
        findParams.put("depFar", depFar);
        findParams.put("cityFar", cityFar);
        findParams.put("lane_property", lane_property);
        findParams.put("altitude_property", altitude_property);
        findParams.put("latitude_property", latitude_property);
        findParams.put("length_property", length_property);
        int pageReq;
        if (this.getRequest().getParameter("page") == null) {
            pageReq = this.getPage();
        } else {
            pageReq = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter("page")));
        }
        findParams.put("pageNow", pageReq);
        findParams.put("maxResults", this.getMaxResults());
        FarmsDao farmDao = new FarmsDao();
//        System.out.println("entreeee");
        listProperties = farmDao.findByParams(findParams);
//        this.setCountTotal(100);
//        System.out.println("entreeee->"+listProperties.get(0).get("countTotal"));
        this.setCountTotal(Integer.parseInt(String.valueOf(listProperties.get(0).get("countTotal"))));
        this.setPage(page);
        listProperties.remove(0);
//        System.out.println("countTotal->"+this.getCountTotal());
        return SUCCESS;
    }
    
    /**
     * Bloque correspondiente al tratamiento de creacion y lectura de archivos
     *
     */    
    private InputStream inputStream;   
    
    public InputStream getInputStream() {  
        return inputStream;  
    }  
  
    public void setInputStream(InputStream inputStream) {  
        this.inputStream = inputStream;  
    }
    
    public String getReport() throws Exception {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "farm/list")) {
            return BaseAction.NOT_AUTHORIZED;
        }
        
        String selAll = "false";           
        if (selectAllname_agronomist!=null) {
            selAll = "true";
        }
        
        HashMap findParams = new HashMap();
        findParams.put("selAll", selAll);
        findParams.put("selItem", selectItemname_agronomist);
        Integer entTypeId = new EntitiesDao().getEntityTypeId(user.getIdUsr());
        findParams.put("entType", entTypeId);
        findParams.put("idEntUser", idEntSystem);        
        String fileName  = ""+getText("file.docfarm");
//        String fileName  = "farmsInfo.csv";
        farDao.getFarms(findParams, fileName);
  
        File f = new File(fileName);  
        inputStream = new FileInputStream(f);  
        return "OUTPUTCSV"; 
    }

    /**
     * Encargado de mostrar en un formulario la informacion de una finca apartir de la identificacion
     * @param idFar:  Identificacion de la finca
     * @return Informacion de la finca
     */
    public String show() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "farm/create") || !usrDao.getPrivilegeUser(idUsrSystem, "farm/modify")) {
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
            this.setIdFarm(Integer.parseInt(this.getRequest().getParameter("idFar")));
        } catch (NumberFormatException e) {
            this.setIdFarm(-1);
//            return;
        }

        this.setDepartment_property(new DepartmentsDao().findAll());
//        System.out.println("id farm->"+this.getIdFarm());
        if (this.getIdFarm() != -1) {
            FarmsDao eventoDao = new FarmsDao();
//            System.out.println("id_producer->"+this.getIdProducer());
            HashMap farmInfo = eventoDao.findById(this.getIdFarm());
//            System.out.println("valores->" + farmInfo);

            //Pasar la conversion a grados, min, seg por defecto ??
            this.setIdProducer(Integer.parseInt(String.valueOf(farmInfo.get("id_producer"))));
            this.setIdFarm(Integer.parseInt(String.valueOf(farmInfo.get("id_farm"))));
            this.setName_producer(String.valueOf(farmInfo.get("name_producer")));
            this.setName_property(String.valueOf(farmInfo.get("name_farm")));
            this.setLatitude_property(String.valueOf(farmInfo.get("latitude_farm")));
            this.setLength_property(String.valueOf(farmInfo.get("length_farm")));
//            this.setOption_geo(1);

            this.setAltitude_property(String.valueOf(farmInfo.get("altitude_farm")));
            this.setDirection_property(String.valueOf(farmInfo.get("dir_farm")));
            this.setLane_property(String.valueOf(farmInfo.get("lane_farm")));
            this.setDepFar(String.valueOf(farmInfo.get("id_dep")));
            this.setCityFar(String.valueOf(farmInfo.get("id_mun")));
            this.setCity_property(new MunicipalitiesDao().findAll(Integer.parseInt(String.valueOf(farmInfo.get("id_dep")))));
        } else {
            List<Municipalities> mun = new ArrayList<Municipalities>();
            mun.add(new Municipalities());           
            this.setCity_property(mun);
        }
        return SUCCESS;
    }

    /**
     * Encargado de guardar la informacion suministrada por el usuario para una finca
     * @return Estado del proceso
     */
    public String saveData() throws SQLException {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "farm/create") || !usrDao.getPrivilegeUser(idUsrSystem, "farm/modify")) {
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
        Transaction tx  = null;
        HashMap proData = proDao.findById(idProducer); 

        Double altPro = Double.parseDouble(altitude_property.replace(',','.'));
        Double latPro = Double.parseDouble(latitude_property.replace(',','.'));
        Double lonPro = Double.parseDouble(length_property.replace(',','.'));
//        Double altPro = Double.parseDouble(altitude_property);
//        Double latPro = Double.parseDouble(latitude_property);
//        Double lonPro = Double.parseDouble(length_property);
        
        if (option_geo == 2) {
            latPro = (latitude_minutes_property / 60) + (latitude_seconds_property / 3600);
            latPro = (latitude_degrees_property < 0) ? ((Math.abs(latitude_degrees_property)) + latPro) * -1 : (latitude_degrees_property + latPro);

            lonPro = (length_minutes_property / 60) + (length_seconds_property / 3600);
            lonPro = (length_degrees_property < 0) ? ((Math.abs(length_degrees_property)) + lonPro) * -1 : (length_degrees_property + lonPro);
        }

        try {
            tx = session.beginTransaction();
            SfGuardUserDao sfDao = new SfGuardUserDao();
            SfGuardUser sfUser = sfDao.getUserByLogin(user.getCreatedBy(), user.getNameUserUsr(), "");
            Farms far = null;
            int idProOld = 0;
            if (idFarm<=0) {
                far = new Farms();
                far.setIdFar(null);
                far.setGeorefFar(true);
                far.setIdProjectFar("1");
                far.setStatus(true);
            } else {
                HashMap fieldInfo = farDao.findById(idFarm);
                idProOld = Integer.parseInt(String.valueOf(fieldInfo.get("id_producer")));
                far = farDao.objectById(idFarm);                
            }     
            far.setNameFar(name_property);
            far.setAddressFar(direction_property);            
            far.setLatitudeFar(latPro);
            far.setLongitudeFar(lonPro);
            far.setAltitudeFar(altPro);            
            far.setNameCommuneFar(lane_property);     
            far.setMunicipalities(new Municipalities(Integer.parseInt(cityFar)));
            Integer idUserMobile = null;
            if (sfUser!=null) {
                idUserMobile = sfUser.getId().intValue();
            }
            far.setCreatedBy(idUserMobile);
            session.saveOrUpdate(far);
            depFar   = String.valueOf(MunicipalitiesDao.getDepartmentId(Integer.parseInt(cityFar)));
            
//            farDao.save(far);
//            System.out.println("valId->"+far.getIdFar());            
            
            if(far.getIdFar()>0 && action.equals("M")) {
                if(idProOld!=idProducer) {
                    FarmsProducers farTemp = farDao.checkFarmProducer(far.getIdFar(), idProOld);
                    session.delete(farTemp);
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
                FarmsProducers farPro = new FarmsProducers();
                farPro.setId(new FarmsProducersId(far.getIdFar(), idProducer));
                farPro.setFarms(far);   
                farPro.setProducers(proDao.objectById(idProducer));
                session.saveOrUpdate(farPro);
            }

            /*LogEntities log = new LogEntities();
            log.setIdLogEnt(null);
            log.setIdEntityLogEnt(idEntSystem); //Colocar el usuario registrado en el sistema
            log.setIdObjectLogEnt(far.getIdFar());
            log.setTableLogEnt("farms");
            log.setDateLogEnt(new Date());
            log.setActionTypeLogEnt(action);
            session.saveOrUpdate(log);*/
//            logDao.save(log);
            
            /*
            "102": "Nombre de la finca" => nameFarm
            "103": "-30.98664622,-64.10017675,601" Capturar posicion => lat, lng, alt
            "105": "Vereda" => district
            "108": "Indicación (como llegar)" => address
            "241": "Seleccione el productor asociado" => Seleccion (solo dato seleccionado) => prodId
            "336": "Departamento" => department
            "338": "Municipio (Amazonas)" => municipality
            */
            
            //Manejo para ingresar datos en MongoDB            

            HashMap valInfo = new HashMap();
            valInfo.put("farmId", far.getIdFar());
            valInfo.put("nameFarm", far.getNameFar());
            valInfo.put("prodId", idProducer);
            valInfo.put("nameProd", proData.get("name"));
            valInfo.put("district", far.getNameCommuneFar());
            valInfo.put("address", far.getAddressFar());
            valInfo.put("lat", latPro);
            valInfo.put("lng", lonPro);
            valInfo.put("alt", altPro);
            valInfo.put("department", depFar);
            valInfo.put("municipality", cityFar);
            valInfo.put("userMobileId", idUserMobile); 
//            System.out.println("valInfo=>"+valInfo);
            
            BasicDBObject query = new BasicDBObject();
            query.put("InsertedId", ""+far.getIdFar());
            query.put("form_id", "3");
            
            MongoClient mongo = null;
            try {
                mongo = new MongoClient("localhost", 27017);
            } catch (UnknownHostException ex) {
                Logger.getLogger(ActionField.class.getName()).log(Level.SEVERE, null, ex);
            }
            DB db = mongo.getDB("ciat");
            DBCollection col = db.getCollection("log_form_records");

            DBCursor cursor    = col.find(query);
            WriteResult result = null;
            BasicDBObject jsonField = GlobalFunctions.generateJSONFarm(valInfo);
            
            if (cursor.count()>0) {
                System.out.println("actualizo mongo");
                result = col.update(query, jsonField);
            } else {
                System.out.println("inserto mongo");
                result = col.insert(jsonField);
            }
            
            if (result.getError()!=null) {
                throw new HibernateException("");
            }
            
            mongo.close();
            tx.commit();
            state = "success";
            if (action.equals("C")) {
                info = "La finca ha sido agregada con exito";
//                return "list";
            } else if (action.equals("M")) {
                info = "La finca ha sido modificada con exito";
            }            
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            state = "failure";
            info = "Fallo al momento de agregar una finca";
        } finally {
            session.close();
        }

        return "states";
//        return ERROR;
    }

    /**
     * Encargado de borrar la informacion de una finca apartir de su identificacion
     * @param idFar:  Identificacion de la finca
     * @return Estado del proceso
     */
    public String delete() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "farm/delete")) {
            return BaseAction.NOT_AUTHORIZED;
        }
        Integer idFar = 0;
        try {
            idFar = Integer.parseInt(this.getRequest().getParameter("idFar"));
        } catch (NumberFormatException e) {
            idFar = -1;
        }

        if (idFar == -1) {
            state = "failure";
            info  = "Fallo al momento de obtener la informacion a borrar";
            return "states";
        }

        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Farms far = farDao.objectById(idFar);
            far.setStatus(false);     
            session.saveOrUpdate(far);
//            session.delete(far);
//            farDao.delete(far);

            LogEntities log = new LogEntities();
            log.setIdLogEnt(null);
            log.setIdEntityLogEnt(idEntSystem); //Colocar el usuario registrado en el sistema
            log.setIdObjectLogEnt(far.getIdFar());
            log.setTableLogEnt("farms");
            log.setDateLogEnt(new Date());
            log.setActionTypeLogEnt("D");
            session.saveOrUpdate(log);
//            logDao.save(log);
            
            BasicDBObject query = new BasicDBObject();
            query.put("InsertedId", ""+far.getIdFar());
            query.put("form_id", "3");
            
            MongoClient mongo = null;
            try {
                mongo = new MongoClient("localhost", 27017);
            } catch (UnknownHostException ex) {
                Logger.getLogger(ActionField.class.getName()).log(Level.SEVERE, null, ex);
            }
            DB db = mongo.getDB("ciat");
            DBCollection col = db.getCollection("log_form_records");
            WriteResult result = null;
            
            System.out.println("borro mongo");
            result = col.remove(query);
            
            if (result.getError()!=null) {
                throw new HibernateException("");
            }
            mongo.close();
            
            FieldsDao fieDao = new FieldsDao();
            fieDao.deleteFieldsMongo(far.getIdFar());
            
            tx.commit();
            state = "success";
            info = "La finca ha sido borrada con exito";
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            state = "failure";
            info  = "Fallo al momento de borrar una finca";
        } finally {
            session.close();
        }

        return "states";
//        return SUCCESS;
    }
    
    /**
     * Encargado de borrar la informacion de las fincas que se han seleccionado
     * @param valSel:  Valores que se han seleccionado para borrar
     * @return Estado del proceso
     */
    public String deleteAll() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "farm/delete")) {
            return BaseAction.NOT_AUTHORIZED;
        }
        String valSel = "";        
        try {
            valSel = String.valueOf(this.getRequest().getParameter("valSel"));
        } catch (NumberFormatException e) {
            valSel = "-1";
        }
        
        if (valSel.equals("-1")) {
            state = "failure";
            info  = "Fallo al momento de obtener la informacion a borrar";
            return "states";
        }
        
        state = farDao.deleteAllFarms(valSel, idEntSystem);
        if (state.equals("success")) {
            info = "La o las finca(s) ha(n) sido borrada(s) con exito";
        } else if (state.equals("failure")) {
            info = "Fallo al momento de borrar una finca";
        }
        
        return "states";
    }
}