/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aepscolombia.platform.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.aepscolombia.platform.models.dao.DepartmentsDao;

import org.aepscolombia.platform.models.entity.Farms;
import org.aepscolombia.platform.models.dao.FarmsDao;
import org.aepscolombia.platform.models.dao.LogEntitiesDao;
import org.aepscolombia.platform.models.dao.MunicipalitiesDao;
import org.aepscolombia.platform.models.dao.ProducersDao;
import org.aepscolombia.platform.models.dao.UsersDao;

import org.aepscolombia.platform.models.entity.Departments;
import org.aepscolombia.platform.models.entity.FarmsProducers;
import org.aepscolombia.platform.models.entity.FarmsProducersId;
import org.aepscolombia.platform.models.entity.LogEntities;
import org.aepscolombia.platform.models.entity.Municipalities;
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
    private double latitude_degrees_property;
    private double latitude_minutes_property;
    private double latitude_seconds_property;
    private double length_degrees_property;
    private double length_minutes_property;
    private double length_seconds_property;
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
    private Users user;
    private Integer idEntSystem;

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

    public double getLatitude_degrees_property() {
        return latitude_degrees_property;
    }

    public void setLatitude_degrees_property(double latitude_degrees_property) {
        this.latitude_degrees_property = latitude_degrees_property;
    }

    public double getLatitude_minutes_property() {
        return latitude_minutes_property;
    }

    public void setLatitude_minutes_property(double latitude_minutes_property) {
        this.latitude_minutes_property = latitude_minutes_property;
    }

    public double getLatitude_seconds_property() {
        return latitude_seconds_property;
    }

    public void setLatitude_seconds_property(double latitude_seconds_property) {
        this.latitude_seconds_property = latitude_seconds_property;
    }

    public double getLength_degrees_property() {
        return length_degrees_property;
    }

    public void setLength_degrees_property(double length_degrees_property) {
        this.length_degrees_property = length_degrees_property;
    }

    public double getLength_minutes_property() {
        return length_minutes_property;
    }

    public void setLength_minutes_property(double length_minutes_property) {
        this.length_minutes_property = length_minutes_property;
    }

    public double getLength_seconds_property() {
        return length_seconds_property;
    }

    public void setLength_seconds_property(double length_seconds_property) {
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
    
    @Override
    public void prepare() throws Exception {
        user = (Users) this.getSession().get(APConstants.SESSION_USER);
        idEntSystem = UsersDao.getEntitySystem(user.getIdUsr());
        this.setDepartment_property(new DepartmentsDao().findAll());
        List<Municipalities> mun = new ArrayList<Municipalities>();
        mun.add(new Municipalities());           
        this.setCity_property(mun);
    }

    @Override
    public String execute() throws Exception {
//        this.setType_ident_producer(new TiposDocumentosDao().findAll());
//        this.setDepartment_property(new DepartmentsDao().findAll());
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
            required.put("name_producer", name_producer);
            required.put("name_property", name_property);
            required.put("depFar", depFar);
            required.put("cityFar", cityFar);
            required.put("lane_property", lane_property);
            required.put("altitude_property", altitude_property);
            if (option_geo == 1) {
                required.put("latitude_property", latitude_property);
                required.put("length_property", length_property);
            } else if (option_geo == 2) {
                required.put("latitude_degrees_property", latitude_degrees_property);
                required.put("latitude_minutes_property", latitude_minutes_property);
                required.put("latitude_seconds_property", latitude_seconds_property);
                required.put("length_degrees_property", length_degrees_property);
                required.put("length_minutes_property", length_minutes_property);
                required.put("length_seconds_property", length_seconds_property);
            }
            for (Iterator it = required.keySet().iterator(); it.hasNext();) {
                String sK = (String) it.next();
                String sV = String.valueOf(required.get(sK));
//                System.out.println(sK + " : " + sV);
//                addFieldError(sK, "El campo es requerido");
                if (StringUtils.trim(sV).equals("") || sV.equals("-1")) {
                    addFieldError(sK, "El campo es requerido");
                }
            }
            
            double altPro = Double.parseDouble(altitude_property);
            double latPro = Double.parseDouble(latitude_property);
            double lonPro = Double.parseDouble(length_property);
            
//            if (altitude_property) {    
            if (altPro < 0 || altPro > 9000) {
                addFieldError("altitude_property", "Dato invalido");
                addActionError("Se ingreso una altitud invalida, por favor ingresar un valor entre 0 y 9000");
            }
//            }

            if (option_geo == 1) {
//                if (latPro!=null) { 
                if (latPro < (-4.3) || latPro > (13.5)) {
                    addFieldError("latitude_property", "Dato invalido");
                    addActionError("Se ingreso una latitud invalida, por favor ingresar un valor entre -4.3 y 13.5");
                }
//                }

//                if (length_property) {    
                if (lonPro < (-81.8) || lonPro > (-66)) {
                    addFieldError("length_property", "Dato invalido");
                    addActionError("Se ingreso una longitud invalida, por favor ingresar un valor entre -81.8 y -66");
                }
//                }			

            } else if (option_geo == 2) {

//                if (latitude_degrees_property && (latitude_degrees_property<(-5) || latitude_degrees_property>14)) {
                if ((latitude_degrees_property < (-5) || latitude_degrees_property > 14)) {
                    addFieldError("latitude_degrees_property", "Dato invalido");
                    addActionError("Se ingreso una latitud en grados invalida, por favor ingresar un valor entre -5 y 14");
                }

//                if (latitude_minutes_property && (latitude_minutes_property<0 || latitude_minutes_property>60)) {
                if ((latitude_minutes_property < 0 || latitude_minutes_property > 60)) {
                    addFieldError("latitude_minutes_property", "Dato invalido");
                    addActionError("Se ingreso una latitud en minutos invalida, por favor ingresar un valor entre 0 y 60");
                }

//                if (latitude_seconds_property && (latitude_seconds_property<0 || latitude_seconds_property>60)) {
                if ((latitude_seconds_property < 0 || latitude_seconds_property > 60)) {
                    addFieldError("latitude_seconds_property", "Dato invalido");
                    addActionError("Se ingreso una latitud en segundos invalida, por favor ingresar un valor entre 0 y 60");
                }

//                if (length_degrees_property && (length_degrees_property<(-82) || length_degrees_property>(-66))) {
                if ((length_degrees_property < (-82) || length_degrees_property > (-66))) {
                    addFieldError("length_degrees_property", "Dato invalido");
                    addActionError("Se ingreso una longitud en grados invalida, por favor ingresar un valor entre -82 y -66");
                }

//                if (length_minutes_property && (length_minutes_property<0 || length_minutes_property>60)) {
                if ((length_minutes_property < 0 || length_minutes_property > 60)) {
                    addFieldError("length_minutes_property", "Dato invalido");
                    addActionError("Se ingreso una longitud en minutos invalida, por favor ingresar un valor entre 0 y 60");
                }

//                if (length_seconds_property && (length_seconds_property<0 || length_seconds_property>60)) {
                if ((length_seconds_property < 0 || length_seconds_property > 60)) {
                    addFieldError("length_seconds_property", "Dato invalido");
                    addActionError("Se ingreso una longitud en segundos invalida, por favor ingresar un valor entre 0 y 60");
                }
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
    
    /**
     * Encargado de buscar las coincidencias de un formulario de busqueda, para cada una de las
     * fincas registradas a un usuario
     * @param valName:  Nombre del valor a buscar
     * @param valId:    Identificacion del valor a buscar
     * @param selected: Valor seleccionado
     * @return lista de fincas
     */
    public String search() {
        valName     = (String) (this.getRequest().getParameter("valName"));
        valId       = (String) (this.getRequest().getParameter("valId"));
        selected    = (String) (this.getRequest().getParameter("selected"));
        if (selected == null) selected = "property";
        additionals = new HashMap();
        additionals.put("selected", selected);
        HashMap findParams = new HashMap();
//        fardParams.put("nameProperty", this.getName_property());
//        System.out.println("this.getAltitude_property()->"+this.getAltitude_property());
        findParams.put("idEntUser", idEntSystem);
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
        FarmsDao eventoDao = new FarmsDao();
//        System.out.println("entreeee");
        listProperties = eventoDao.findByParams(findParams);
//        this.setCountTotal(100);
//        System.out.println("entreeee->"+listProperties.get(0).get("countTotal"));
        this.setCountTotal(Integer.parseInt(String.valueOf(listProperties.get(0).get("countTotal"))));
        this.setPage(page);
        listProperties.remove(0);
//        System.out.println("countTotal->"+this.getCountTotal());
        return SUCCESS;
    }

    /**
     * Encargado de mostrar en un formulario la informacion de una finca apartir de la identificacion
     * @param idFar:  Identificacion de la finca
     * @return Informacion de la finca
     */
    public String show() {
        actExe = (String)(this.getRequest().getParameter("action"));
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

        double altPro = Double.parseDouble(altitude_property);
        double latPro = Double.parseDouble(latitude_property);
        double lonPro = Double.parseDouble(length_property);
        
        if (option_geo == 2) {
            latPro = (latitude_minutes_property / 60) + (latitude_seconds_property / 3600);
            latPro = (latitude_degrees_property < 0) ? ((Math.abs(latitude_degrees_property)) + latPro) * -1 : (latitude_degrees_property + latPro);

            lonPro = (length_minutes_property / 60) + (length_seconds_property / 3600);
            lonPro = (length_degrees_property < 0) ? ((Math.abs(length_degrees_property)) + lonPro) * -1 : (length_degrees_property + lonPro);
        }

        try {
            tx = session.beginTransaction();
            Farms far = null;
            int idProOld = 0;
            if (idFarm<=0) {
                far = new Farms();
                far.setIdFar(null);
                far.setGeorefFar(true);
                far.setIdProjectFar("1");
                far.setStatusFar(true);
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
            session.saveOrUpdate(far);
//            farDao.save(far);
//            System.out.println("valId->"+far.getIdFar());

            
            FarmsProducers farPro = new FarmsProducers();
            farPro.setId(new FarmsProducersId(far.getIdFar(), idProducer));
            farPro.setFarms(far);   
            farPro.setProducers(proDao.objectById(idProducer));
            session.saveOrUpdate(farPro);
            if(far.getIdFar()>0 && action.equals("M")) {
                FarmsProducers farTemp = farDao.checkFarmProducer(far.getIdFar(), idProOld);
                session.delete(farTemp);
//                System.out.println("id field->"+fiePro.getFields().getIdFie());
//                fiePro = new FieldsProducers();
//                fiePro.setId(new FieldsProducersId(lot.getIdFie(), idProducer));
//                fiePro.setFields(lot);   
//                fiePro.setProducers(proDao.objectById(idProducer));
//                fiePro.setFieldTypes(new FieldTypes(typeLot));
//                session.saveOrUpdate(fiePro);
            }

            LogEntities log = new LogEntities();
            log.setIdLogEnt(null);
            log.setIdEntityLogEnt(idEntSystem); //Colocar el usuario registrado en el sistema
            log.setIdObjectLogEnt(far.getIdFar());
            log.setTableLogEnt("farms");
            log.setDateLogEnt(new Date());
            log.setActionTypeLogEnt(action);
            session.saveOrUpdate(log);
//            logDao.save(log);

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
            info = "Fallo al momento de agregar una farm";
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
            session.delete(far);
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
}