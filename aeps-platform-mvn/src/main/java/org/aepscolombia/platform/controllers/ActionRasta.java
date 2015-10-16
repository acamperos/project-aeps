
package org.aepscolombia.platform.controllers;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.*;
import org.aepscolombia.platform.models.dao.AssociationDao;
import org.aepscolombia.platform.models.dao.EntitiesDao;

import org.aepscolombia.platform.models.dao.LogEntitiesDao;
import org.aepscolombia.platform.models.dao.FieldsDao;
import org.aepscolombia.platform.models.dao.RastasDao;
import org.aepscolombia.platform.models.dao.SfGuardUserDao;
import org.aepscolombia.platform.models.dao.UsersDao;
import org.aepscolombia.platform.models.entity.Entities;
import org.aepscolombia.platform.models.entity.Fields;

import org.aepscolombia.platform.models.entity.HorizontesRasta;
import org.aepscolombia.platform.models.entity.LogEntities;
import org.aepscolombia.platform.models.entity.Rastas;
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
import org.renjin.sexp.ListVector;

/**
 * Clase ActionRasta
 *
 * Contiene los metodos para interactuar con el modulo de Rasta
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ActionRasta extends BaseAction {
    
    /**
     * Atributos provenientes del formulario
     */
    private int idProducer;
    private int idFarm;
    private int idField;
    private Integer idRasta;
    private String nameField;
    
    private String search_soil; 
    private String num_rasta;
    private String date;
    private String pendant;
    private String altitude;
    private String latitude;
    private String length;
    private String ground;
    private String position;
    private String ph;
    private String carbonates;
    private List<HashMap> listSoils;
    private Integer searchFromSoil;
    
    private Users user;
    private Integer idEntSystem;    
    private Integer idUsrSystem;    
    private Rastas rasta = new Rastas();
    private UsersDao usrDao;
    private List<Entities> list_agronomist;
    private AssociationDao assDao;
    private String coCode;

    

    /**
     * Metodos getter y setter por cada variable del formulario
     */

    public Rastas getRasta() {
        return rasta;
    }

    public void setRasta(Rastas rasta) {
        this.rasta = rasta;
    }

    public String getNameField() {
        return nameField;
    }

    public void setNameField(String nameField) {
        this.nameField = nameField;
    } 

    public Integer getSearchFromSoil() {
        return searchFromSoil;
    }

    public void setSearchFromSoil(Integer searchFromSoil) {
        this.searchFromSoil = searchFromSoil;
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
    
    public int getIdProducer() {
        return idProducer;
    }

    public void setIdProducer(int idProducer) {
        this.idProducer = idProducer;
    }

    public Integer getIdRasta() {
        return idRasta;
    }

    public void setIdRasta(Integer idRasta) {
        this.idRasta = idRasta;
    }   
    
    public int getIdFarm() {
        return idFarm;
    }

    public void setIdFarm(int idFarm) {
        this.idFarm = idFarm;
    }

    public int getIdField() {
        return idField;
    }

    public void setIdField(int idField) {
        this.idField = idField;
    }

    public String getSearch_soil() {
        return search_soil;
    }

    public void setSearch_soil(String search_soil) {
        this.search_soil = search_soil;
    }

    public String getNum_rasta() {
        return num_rasta;
    }

    public void setNum_rasta(String num_rasta) {
        this.num_rasta = num_rasta;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPendant() {
        return pendant;
    }

    public void setPendant(String pendant) {
        this.pendant = pendant;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getGround() {
        return ground;
    }

    public void setGround(String ground) {
        this.ground = ground;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getCarbonates() {
        return carbonates;
    }

    public void setCarbonates(String carbonates) {
        this.carbonates = carbonates;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }    

    public List<HashMap> getListSoils() {
        return listSoils;
    }
    
    public String getCoCode() {
        return coCode;
    }
    
    
    
    
    /**
     * Atributos generales de clase
     */
    private HashMap additionals;
    private int page = 1, countTotal, maxResults = 10;
    private int option_geo_lot;
    
    private FieldsDao lotDao      = new FieldsDao();
    private RastasDao rastaDao    = new RastasDao();
    private LogEntitiesDao logDao = new LogEntitiesDao();
    
    private String state = "";
    private String info  = "";
    private String valId ="", valName="", selected="";
    
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
    
    private List<HorizontesRasta> additionalsAtrib; 

    public List<HorizontesRasta> getAdditionalsAtrib() {
        return additionalsAtrib;
    }

    public void setAdditionalsAtrib(List<HorizontesRasta> additionalsAtrib) {
        this.additionalsAtrib = additionalsAtrib;
    }
    
//    private Map<String, String[]> paraMap;
//    
//    public void setParameters(Map<String, String[]> parameters) {
//        this.paraMap= parameters;
//    }
    
    private String rowNew;

    public String getRowNew() {
        return rowNew;
    }

    public void setRowNew(String rowNew) {
        this.rowNew = rowNew;
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
        return SUCCESS;
    }   
    
    
    @Override
    public void prepare() throws Exception {
        user = (Users) this.getSession().get(APConstants.SESSION_USER);
        idEntSystem = UsersDao.getEntitySystem(user.getIdUsr());
//        rasta.setLatitudRas(4.567);
//        if (user.getIdUsr()!=null) idEntSystem = UsersDao.getEntitySystem(user.getIdUsr());
        additionalsAtrib = new ArrayList<HorizontesRasta>();        
        usrDao = new UsersDao();
        idUsrSystem = user.getIdUsr();
        EntitiesDao entDao = new EntitiesDao();
        Entities entTemp = entDao.findById(idEntSystem);
        typeEnt = entTemp.getEntitiesTypes().getIdEntTyp();
        assDao = new AssociationDao();
        coCode = (String) this.getSession().get(APConstants.COUNTRY_CODE);
//        rowNew = (String)(this.getRequest().getParameter("rowNew"));
//        if (rowNew!=null && rowNew.equals("true")) {
//            System.out.println("entreeeee");
//            additionalsAtrib.add(0,new HorizontesRasta());
//        }
//        additionalsAtrib.add(1,new HorizontesRasta());
    }
    
    private Map fieldError;

    public Map getFieldError() {
        return fieldError;
    }

    public void setFieldError(Map fieldError) {
        this.fieldError = fieldError;
    }
    
    public String viewPosition() {
        info = "";
        Double minlat = Double.parseDouble(getText("min.lat"));
        Double maxlat = Double.parseDouble(getText("max.lat"));
        Double minlon = Double.parseDouble(getText("min.lon"));
        Double maxlon = Double.parseDouble(getText("max.lon"));        
        if (rasta.getLatitudRas()!=null && (rasta.getLatitudRas()<(minlat) || rasta.getLatitudRas()>(maxlat))) {
            addFieldError("rasta.latitudRas", getText("message.invalidranklatitude.soil"));
            state = "failure";
            info += getText("desc.invalidranklatitude.soil");
        } else if (rasta.getLatitudRas()==null) {
            addFieldError("rasta.latitudRas", getText("message.invalidranklatitude.soil"));
            state = "failure";
            info += getText("desc.invalidranklatitude.soil");
        }

        if (rasta.getLongitudRas()!=null && (rasta.getLongitudRas()<(minlon) || rasta.getLongitudRas()>(maxlon))) {
            addFieldError("rasta.longitudRas", getText("message.invalidranklongitude.soil"));
            state = "failure";
            info += getText("desc.invalidranklongitude.soil");
        } else if (rasta.getLongitudRas()==null) {
            addFieldError("rasta.longitudRas", getText("message.invalidranklongitude.soil"));
            state = "failure";
            info += getText("desc.invalidranklongitude.soil");
        }
        fieldError = getFieldErrors();
        if (state.equals("failure")) return "states";
        return SUCCESS;
    }
    
    public String generateInf() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "soil/create") || !usrDao.getPrivilegeUser(idUsrSystem, "soil/modify")) {
            return BaseAction.NOT_AUTHORIZED;
        } 
        
        try {
            this.setIdRasta(Integer.parseInt(this.getRequest().getParameter("idRasta")));
        } catch (NumberFormatException e) {
            this.setIdRasta(-1);
        }        
        
        state = "failure";     
        ScriptEngineManager manager = new ScriptEngineManager();
        
        // create a Renjin engine:
        ScriptEngine engine = manager.getEngineByName("Renjin");
				
        if(engine == null) {
//            throw new RuntimeException("Renjin Script Engine not found on the classpath.");
            info  = getText("message.packagedontwork.soil");
        }
        
        if (this.getIdRasta()==-1) {
            info  = getText("message.failtogetrasta.soil");
        }
        
        try {
            
            String vec = "vec <- "+rastaDao.getInfoToReport(this.getIdRasta());    
            engine.eval(vec);
            
            ListVector res = (ListVector)engine.eval(new java.io.FileReader("inferidas.R"));            
            String depthEffective  = res.getElementAsString(0);
            String organicMaterial = res.getElementAsString(1);
            String internalDrain   = res.getElementAsString(2);
            String externalDrain   = res.getElementAsString(3);
            String[] infoMaterials = organicMaterial.split(",");     
//            System.out.println("infoMaterials->"+infoMaterials);
            
            HashMap valInf = new HashMap();
            valInf.put("depth", depthEffective);
            valInf.put("organic", infoMaterials);
            valInf.put("internal", internalDrain);
            valInf.put("external", externalDrain);
            
            Integer error = null;            
            if (depthEffective.equals("Error") || depthEffective.equals("Error.nd") || depthEffective.equals("ERROR.ND") || depthEffective.equals("NO CLASIFICADO")) {
                error = 1;
            }            
            
            for (int i = 0; i < infoMaterials.length; i++) {
                String temp = infoMaterials[i];
                if (temp.equals("ERROR.ND") || internalDrain.equals("NO CLASIFICADO")) {
                    error = 2;
                }
            }            
            
            if (internalDrain.equals("ERROR.ND") || internalDrain.equals("NO CLASIFICADO estruc") || internalDrain.equals("NO CLASIFICADO bueno") || internalDrain.equals("NO CLASIFICADO excesivo")) {
                error = 3;
            }
            
            if (externalDrain.equals("ERROR.ND") || externalDrain.equals("NO CLASIFICADO estruc") || externalDrain.equals("NO CLASIFICADO bueno") || externalDrain.equals("NO CLASIFICADO excesivo")) {
                error = 4;
            }
            
            if (error!=null) {
                if (error==1) info  = getText("message.failtogetprofundidad.soil");
                if (error==2) info  = getText("message.failtogetmateria.soil");
                if (error==3) info  = getText("message.failtogetdrenajein.soil");
                if (error==4) info  = getText("message.failtogetdrenajeext.soil");
//                return "states";
            }
            
            Rastas rasTemp = rastaDao.objectById(this.getIdRasta());
            info = rastaDao.getInfoRasta(this.getIdRasta(), valInf);         
            rasTemp.setProfundidadEfectivaRas(Double.parseDouble(depthEffective));
            rasTemp.setMateriaOrganicaRas(organicMaterial);
            rasTemp.setDrenajeInternoRas(internalDrain);
            rasTemp.setDranajeExternoRas(externalDrain);
            rastaDao.save(rasTemp);
            return SUCCESS;             
        } catch (ScriptException ex) {
//            System.out.println("Error mostrando la informacion");
            info  = getText("message.failtoshowinfo.soil");
//                Logger.getLogger(ActionContact.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
//            System.out.println("Error leyendo el archivo R");
            info  = getText("message.failtoloadscript.soil");
//                Logger.getLogger(ActionContact.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "states";
    }
    
    
    /**
     * Metodo encargado de validar el formulario de Suelos
     */
    @Override
    public void validate() {      
        /*
         * Se evalua dependiendo a la accion realizada:
         * 1) create: Al momento de guardar un registro por primera ves
         * 2) modify: Al momento de modificar un registro
         * 3) delete: Al momento de borrar un registro
         */
//        for (Iterator<String> it = paraMap.keySet().iterator(); it.hasNext();){
////            Map map;
//            String key = it.next();
//            if (key.startsWith("additionalsAtrib") && (paraMap.get(key)!=null)) {
//                System.out.println("val->"+paraMap.get(key));
////                 String[] stuff = map.get(key);
//            }
//        }
//        String[] checkedIds = this.getRequest().getParameterValues("additionalsAtrib");
//        additionalsAtrib = new ArrayList<HashMap>(request.getParameterValues("firstnames"));
//        if (actExe.equals("create") || actExe.equals("modify")) {
//            System.out.println("data->"+additionalsAtrib);
//            for (HorizontesRasta data : additionalsAtrib) {
//                System.out.println("horizonte->"+data.getColorHumedoHorRas());
//            }
//            System.out.println("data1->"+paraMap);
//            System.out.println("data2->"+this.getRequest().getParameter("additionalsAtrib"));
//        }
//        System.out.println("fehca->"+rasta.getFechaRas());
//        this.getRequest().getLocale();
//        NumberFormat numberFormatter;
//        String quantityOut;
//
//        numberFormatter = NumberFormat.getNumberInstance(new Locale("en_US"));
//        quantityOut = numberFormatter.format(rasta.getLatitudRas());
        if (actExe!=null && (actExe.equals("create") || actExe.equals("modify"))) {
//            System.out.println("entreeeeeeeeeeeeeeeeeeeee");
            HashMap required = new HashMap();
            required.put("nameField", nameField);
            required.put("rasta.fechaRas", rasta.getFechaRas());
            required.put("rasta.altitudRas", rasta.getAltitudRas());
            required.put("rasta.latitudRas", rasta.getLatitudRas());
            required.put("rasta.longitudRas", rasta.getLongitudRas());
            required.put("rasta.pendienteTerrenoRas", rasta.getPendienteTerrenoRas());
            required.put("rasta.terrenoCircundanteRas", rasta.getTerrenoCircundanteRas());
            required.put("rasta.posicionPerfilRas", rasta.getPosicionPerfilRas());
            required.put("rasta.phRas", rasta.getPhRas());
            required.put("rasta.carbonatosRas", rasta.getCarbonatosRas());
            required.put("rasta.piedrasSuperficieRas", rasta.getPiedrasSuperficieRas());
            required.put("rasta.rocasSuperficieRas", rasta.getRocasSuperficieRas());
            required.put("rasta.piedrasPerfilRas", rasta.getPiedrasPerfilRas());
            required.put("rasta.rocasPerfilRas", rasta.getRocasPerfilRas());
            required.put("rasta.estructuraRas", rasta.getEstructuraRas());
            required.put("rasta.costrasDurasRas", rasta.getCostrasDurasRas());            
            required.put("rasta.exposicionSolRas", rasta.getExposicionSolRas());
            required.put("rasta.costrasBlancasRas", rasta.getCostrasBlancasRas());
            required.put("rasta.costrasNegrasRas", rasta.getCostrasNegrasRas());
            required.put("rasta.plantasPequenasRas", rasta.getPlantasPequenasRas());
            required.put("rasta.recubrimientoVegetalRas", rasta.getRecubrimientoVegetalRas());
            
            if (rasta.getCarbonatosRas()!=null && !rasta.getCarbonatosRas().equals("no tiene") && !rasta.getCarbonatosRas().equals("-1")) { 
                required.put("rasta.profundidadCarbonatosRas", rasta.getProfundidadCarbonatosRas());
//                addFieldError("rasta.profundidadCarbonatosRas", "Dato requerido");                
            }

            if (rasta.getHorizontePedrogosoRocosoRas()!=null && rasta.getHorizontePedrogosoRocosoRas()) {  
                required.put("rasta.profundidadHorizontePedregosoRas", rasta.getProfundidadHorizontePedregosoRas());
                required.put("rasta.espesorHorizontePedregosoRas", rasta.getEspesorHorizontePedregosoRas());
                required.put("rasta.profundidadPrimerasPiedrasRas", rasta.getProfundidadPrimerasPiedrasRas());
                
//                addFieldError("rasta.profundidadHorizontePedregosoRas", "Dato requerido");
//                addFieldError("rasta.espesorHorizontePedregosoRas", "Dato requerido");
//                addFieldError("rasta.profundidadPrimerasPiedrasRas", "Dato requerido");
            }

            if (rasta.getCapasEndurecidasRas()!=null && rasta.getCapasEndurecidasRas()) {
                required.put("rasta.prufundidadCapasRas", rasta.getPrufundidadCapasRas());
                required.put("rasta.espesorCapaEndurecidaRas", rasta.getEspesorCapaEndurecidaRas());
                
//                addFieldError("rasta.prufundidadCapasRas", "Dato requerido");
//                addFieldError("rasta.espesorCapaEndurecidaRas", "Dato requerido");
            }

            if (rasta.getMoteadosRas()!=null && rasta.getMoteadosRas()) {
                required.put("rasta.profundidadMoteadosRas", rasta.getProfundidadMoteadosRas());
//                required.put("rasta.moteadosMas70cmRas", rasta.getMoteadosMas70cmRas());
            }

            if (rasta.getRaicesVivasRas()!=null && rasta.getRaicesVivasRas()) {
                required.put("rasta.profundidadRaicesRas", rasta.getProfundidadRaicesRas());
//                addFieldError("rasta.profundidadRaicesRas", "Dato requerido");
            }
//            
//            if (option_geo_lot == 1) {
//                required.put("latitude_property", latitude_lot);
//                required.put("length_property", length_lot);
//            } else if (option_geo_lot == 2) {
//                required.put("latitude_degrees_property", latitude_degrees_lot);
//                required.put("latitude_minutes_property", latitude_minutes_lot);
//                required.put("latitude_seconds_property", latitude_seconds_lot);
//                required.put("length_degrees_property", length_degrees_lot);
//                required.put("length_minutes_property", length_minutes_lot);
//                required.put("length_seconds_property", length_seconds_lot);
//            }

            boolean enter = false;
            for (Iterator it = required.keySet().iterator(); it.hasNext();) {
                String sK = (String) it.next();
                String sV = String.valueOf(required.get(sK));
//                System.out.println(sK + " : " + sV);
                if (StringUtils.trim(sV).equals("null") || StringUtils.trim(sV)==null || StringUtils.trim(sV).equals("") || sV.equals("-1")) {
                    addFieldError(sK, getText("message.fieldsrequired.soil"));
                    enter = true;
                }
            }
            
            if (enter) {
                addActionError(getText("message.missingfields.soil"));
            }
            
            Double minlat = Double.parseDouble(getText("min.lat"));
            Double maxlat = Double.parseDouble(getText("max.lat"));
            Double minlon = Double.parseDouble(getText("min.lon"));
            Double maxlon = Double.parseDouble(getText("max.lon"));
            Double minalt = Double.parseDouble(getText("min.alt"));
            Double maxalt = Double.parseDouble(getText("max.alt"));
            
            if (rasta.getLatitudRas()==null) {
                addFieldError("rasta.latitudRas", getText("message.putsomedatalatitude.soil"));
                addFieldError("latitude_degrees", " ");
                addFieldError("latitude_minutes", " ");
                addFieldError("latitude_seconds", " ");
                addActionError(getText("desc.putsomedatalatitude.soil"));
            }
            
            if (rasta.getLongitudRas()==null) {
                addFieldError("rasta.longitudRas", getText("message.putsomedatalongitude.soil"));
                addFieldError("length_degrees", " ");
                addFieldError("length_minutes", " ");
                addFieldError("length_seconds", " ");
                addActionError(getText("desc.putsomedatalongitude.soil"));
            }
            
            if (rasta.getAltitudRas()!=null && (rasta.getAltitudRas()<minalt || rasta.getAltitudRas()>maxalt)) {
                addFieldError("rasta.altitudRas", getText("message.datainvalidaltitude.soil"));
                addActionError(getText("desc.datainvalidaltitude.soil"));
            }

            if (rasta.getLatitudRas()!=null && (rasta.getLatitudRas()<(minlat) || rasta.getLatitudRas()>(maxlat))) {
                addFieldError("rasta.latitudRas", getText("message.invalidvalueranklatitude.soil"));
                addActionError(getText("desc.invalidvalueranklatitude.soil"));
            }

            if (rasta.getLongitudRas()!=null && (rasta.getLongitudRas()<(minlon) || rasta.getLongitudRas()>(maxlon))) {
                addFieldError("rasta.longitudRas", getText("message.invalidvalueranklongitude.soil"));
                addActionError(getText("desc.invalidvalueranklongitude.soil"));
            }

            if (rasta.getPendienteTerrenoRas()!=null && (rasta.getPendienteTerrenoRas()<0 || rasta.getPendienteTerrenoRas()>100)) {
                addFieldError("rasta.pendienteTerrenoRas", getText("message.invalidvaluerankpendant.soil"));
                addActionError(getText("desc.invalidvaluerankpendant.soil"));
            }

            if (rasta.getPhRas()!=null && (rasta.getPhRas()<0 || rasta.getPhRas()>14)) {
                addFieldError("rasta.phRas", getText("message.invalidvaluerankph.soil"));
                addActionError(getText("desc.invalidvaluerankph.soil"));
            }

            Boolean entry      = true;
            Boolean errorEsp   = false;
            Boolean errorClSe  = false;
            Boolean errorClHm  = false;
            int sumEspesor = 0;
            int cont = 1;
//            System.out.println("numHor=>"+additionalsAtrib.size());
            for (int i=0; i<additionalsAtrib.size(); i++) {
//            for (HorizontesRasta horizon : additionalsAtrib) {
                HorizontesRasta horizon = additionalsAtrib.get(i);
                if (horizon != null) {
//                if ((horizon.getNumeroHorizonteHorRas()<0) && (horizon.getEspesorHorRas()<0) && (horizon.getColorSecoHorRas()<0) && (horizon.getColorHumedoHorRas()<0) && (horizon.getTexturesId()<0) && (horizon.getResistenciasRompimientoId()<0)) {
//                (horizon.getNumeroHorizonteHorRas()!=null && horizon.getNumeroHorizonteHorRas()>0)
                    if ((horizon.getEspesorHorRas()!=null && horizon.getEspesorHorRas()>0) && ((horizon.getColorSecoHorRas()!=null && horizon.getColorSecoHorRas()>0) || (horizon.getColorHumedoHorRas()!=null && horizon.getColorHumedoHorRas()>0)) && (horizon.getTextures()!=null) && (horizon.getResistenciasRompimiento()!=null)) {
                        entry = false;
                    }
    //                System.out.println("horizon.getColorSecoHorRas()->"+horizon.getColorSecoHorRas());

                    if (horizon.getEspesorHorRas()!=null) {    
                        if (horizon.getEspesorHorRas()<0 || horizon.getEspesorHorRas()>150) {
                            addFieldError("additionalsAtrib["+cont+"].espesorHorRas", getText("message.invalidvalueespesor.soil"));
                            errorEsp = true;
                        } else {
                            sumEspesor += horizon.getEspesorHorRas();
                        }
                    }

                    if (horizon.getColorSecoHorRas()!=null) {    
                        if (horizon.getColorSecoHorRas()<1 || horizon.getColorSecoHorRas()>54) {
                            addFieldError("additionalsAtrib["+cont+"].colorSecoHorRas", getText("message.invalidvaluecolorseco.soil"));
                            errorClSe = true;
                        }
                    }

                    if (horizon.getColorHumedoHorRas()!=null) {    
                        if (horizon.getColorHumedoHorRas()<1 || horizon.getColorHumedoHorRas()>54) {
                            addFieldError("additionalsAtrib["+cont+"].colorHumedoHorRas", getText("message.invalidvaluecolorhumedo.soil"));
                            errorClHm = true;
                        }
                    }                
                }
                cont++;
            }

            if (errorEsp) {
                addActionError(getText("desc.invalidvalueespesor.soil"));
            }

            if (errorClSe) {
                addActionError(getText("desc.invalidvaluecolorseco.soil"));
            }

            if (errorClHm) {
                addActionError(getText("desc.invalidvaluecolorhumedo.soil"));
            }


            if (rasta.getProfundidadCarbonatosRas()!=null && (rasta.getProfundidadCarbonatosRas()<0 || rasta.getProfundidadCarbonatosRas()>sumEspesor)) {
                addFieldError("rasta.profundidadCarbonatosRas", getText("message.invalidvalueprofundidadcarbonatos.soil")+" "+sumEspesor);
                addActionError(getText("desc.invalidvalueprofundidadcarbonatos.soil")+" "+sumEspesor+" "+getText("desc.invalidvalueforespesorincorrect.soil"));
            }

            if (rasta.getProfundidadHorizontePedregosoRas()!=null && (rasta.getProfundidadHorizontePedregosoRas()<0 || rasta.getProfundidadHorizontePedregosoRas()>sumEspesor)) {            
                addFieldError("rasta.profundidadHorizontePedregosoRas", getText("message.invalidvalueprofundidadhorizonte.soil")+" "+sumEspesor);
                addActionError(getText("desc.invalidvalueprofundidadhorizonte.soil")+" "+sumEspesor+" "+getText("desc.invalidvalueforespesorincorrect.soil"));            
            }

            if (rasta.getEspesorHorizontePedregosoRas()!=null && (rasta.getEspesorHorizontePedregosoRas()<0 || rasta.getEspesorHorizontePedregosoRas()>sumEspesor)) {
                addFieldError("rasta.espesorHorizontePedregosoRas", getText("message.invalidvalueesphorizonte.soil")+" "+sumEspesor);
                addActionError(getText("desc.invalidvalueesphorizonte.soil")+" "+sumEspesor+" "+getText("desc.invalidvalueforespesorincorrect.soil"));            
            }

            if (rasta.getProfundidadPrimerasPiedrasRas()!=null && (rasta.getProfundidadPrimerasPiedrasRas()<0 || rasta.getProfundidadPrimerasPiedrasRas()>sumEspesor)) {
                addFieldError("rasta.profundidadPrimerasPiedrasRas", getText("message.invalidvaluepripiedras.soil")+" "+sumEspesor);
                addActionError(getText("desc.invalidvaluepripiedras.soil")+" "+sumEspesor+" "+getText("desc.invalidvalueforespesorincorrect.soil"));            
            }

            if (rasta.getPrufundidadCapasRas()!=null && (rasta.getPrufundidadCapasRas()<0 || rasta.getPrufundidadCapasRas()>sumEspesor)) {
                addFieldError("rasta.prufundidadCapasRas", getText("message.invalidvalueprocapas.soil")+" "+sumEspesor);
                addActionError(getText("desc.invalidvalueprocapas.soil")+" "+sumEspesor+" "+getText("desc.invalidvalueforespesorincorrect.soil"));            
            }
                        
            
            if (rasta.getEspesorCapaEndurecidaRas()!=null && !rasta.getEspesorCapaEndurecidaRas().equals("")) {
                int espCapa = Integer.parseInt(rasta.getEspesorCapaEndurecidaRas());
                if (espCapa<0 || espCapa>sumEspesor) {
                    addFieldError("rasta.espesorCapaEndurecidaRas", getText("message.invalidvalueespesorcapas.soil")+" "+sumEspesor);
                    addActionError(getText("desc.invalidvalueespesorcapas.soil")+" "+sumEspesor+" "+getText("desc.invalidvalueforespesorincorrect.soil"));            
                }
            }

            if (rasta.getProfundidadMoteadosRas()!=null && (rasta.getProfundidadMoteadosRas()<0 || rasta.getProfundidadMoteadosRas()>sumEspesor)) {
                addFieldError("rasta.profundidadMoteadosRas", getText("message.invalidvaluemoteados.soil")+" "+sumEspesor);
                addActionError(getText("desc.invalidvaluemoteados.soil")+" "+sumEspesor+" "+getText("desc.invalidvalueforespesorincorrect.soil"));            
            }

            if (rasta.getProfundidadRaicesRas()!=null && (rasta.getProfundidadRaicesRas()<0 || rasta.getProfundidadRaicesRas()>sumEspesor)) {
                addFieldError("rasta.profundidadRaicesRas", getText("message.invalidvalueraices.soil")+" "+sumEspesor);
                addActionError(getText("desc.invalidvalueraices.soil")+" "+sumEspesor+" "+getText("desc.invalidvalueforespesorincorrect.soil"));            
            }

            if (entry) {
                int i = 0;
                for (int j=0; j<additionalsAtrib.size(); j++) {
//                  for (HorizontesRasta horizon : additionalsAtrib) {
                    HorizontesRasta horizon = additionalsAtrib.get(j);   
                    if (horizon != null) {
//                for (int i=0; i<=additionalsAtrib.size(); i++) {                                    
//                    addFieldError("additionalsAtrib["+i+"].numeroHorizonteHorRas", getText("message.invalidvaluenumhorizonte.soil"));
                        addFieldError("additionalsAtrib["+i+"].espesorHorRas", getText("message.invalidvaluenumhorizonte.soil"));
    //                    System.out.println("tamaño=>"+additionalsAtrib.size());
                        if (additionalsAtrib.size()>0) {
                            if (horizon.getColorHumedoHorRas()==null && horizon.getColorSecoHorRas()==null) {
                                addFieldError("additionalsAtrib["+i+"].colorSecoHorRas", "");
                                addFieldError("additionalsAtrib["+i+"].colorHumedoHorRas", "");
                            }
                        }
                        addFieldError("additionalsAtrib["+i+"].textures", "");
                        addFieldError("additionalsAtrib["+i+"].resistenciasRompimiento", "");          
    //                    cont++;                        
                    }
                    i++;
                }
                addActionError(getText("desc.addsomehorizonte.soil"));    
            }

            if (sumEspesor>150) {
                for (int i=0; i<additionalsAtrib.size(); i++) {
//                    addFieldError("additionalsAtrib["+i+"].numeroHorizonteHorRas", getText("message.invalidtotalvalueespesor.soil"));
                    addFieldError("additionalsAtrib["+i+"].espesorHorRas", getText("message.invalidtotalvalueespesor.soil"));
                    addFieldError("additionalsAtrib["+i+"].colorSecoHorRas", "");
                    addFieldError("additionalsAtrib["+i+"].colorHumedoHorRas", "");
                    addFieldError("additionalsAtrib["+i+"].textures", "");
                    addFieldError("additionalsAtrib["+i+"].resistenciasRompimiento", "");
                }
                addActionError(getText("desc.invalidtotalvalueespesor.soil"));   
            }
        }
    }
    
    private Integer numRows=0;

    public Integer getNumRows() {
        return numRows;
    }

    public void setNumRows(Integer numRows) {
        this.numRows = numRows;
    }
    
    
    private Integer newReq=0;

    public Integer getNewRow() {
        return newReq;
    }
    
    public void setNewReq(Integer newReq) {
        this.newReq = newReq;
    }
    
            
  
    
    /**
    * Accion que permite cargar una nueva fila en el panel de campos adicionales de un documento
    *
    */
    public String showRowAdditional()
    {     
        actExe = (String)(this.getRequest().getParameter("action"));
//        newRow = Integer.parseInt(this.getRequest().getParameter("newRow"));
        String valNum = (this.getRequest().getParameter("numRows"));
        if (valNum!=null) {
            this.setNumRows(Integer.parseInt(valNum)+1);
        } else {
            this.setNumRows(1);
        }
        HorizontesRasta horizon = new HorizontesRasta();
//        horizon.setNumeroHorizonteHorRas(0);
//        System.out.println("row->"+additionalsAtrib.size());
        additionalsAtrib.add(horizon);
        
        return SUCCESS;
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
     * Encargado de buscar las coincidencias de un formulario de busqueda, para cada una de los
     * rastas registrados a un usuario
     * @param valName:  Nombre del valor a buscar
     * @param valId:    Identificacion del valor a buscar
     * @param selected: Valor seleccionado
     * @return lista de rastas
     */
    public String search() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "soil/list")) {
            return BaseAction.NOT_AUTHORIZED;
        }
        valName     = (String)(this.getRequest().getParameter("valName"));
        valId       = (String)(this.getRequest().getParameter("valId"));
        selected    = (String)(this.getRequest().getParameter("selected"));
        String selAll = "false";
        if(selected==null) {
            selected="rasta";
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
//        if (searchFromSoil!=null && searchFromSoil==2) {
//            num_rasta = date = pendant = altitude = latitude = length = ground = position = ph = carbonates = search_soil;            
//        } else if(searchFromSoil!=null && searchFromSoil==1) {
        if(searchFromSoil!=null && searchFromSoil==2) {
            search_soil = "";
        } 
        
        findParams.put("idEntUser", idEntSystem);
        findParams.put("search_soil", search_soil);
        findParams.put("num_rasta", num_rasta);
        findParams.put("date", date);
        findParams.put("pendant", pendant);
        findParams.put("altitude", altitude);
        findParams.put("latitude", latitude);
        findParams.put("length", length);
        findParams.put("ground", ground);
        findParams.put("position", position);
        findParams.put("ph", ph);
        findParams.put("carbonates", carbonates);
        
        int pageReq;
        if (this.getRequest().getParameter("page") == null) {
            pageReq = this.getPage();
        } else {
            pageReq = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter("page")));
        }
        findParams.put("pageNow", pageReq);
        findParams.put("maxResults", this.getMaxResults());
        listSoils = rastaDao.findByParams(findParams);
        this.setCountTotal(Integer.parseInt(String.valueOf(listSoils.get(0).get("countTotal"))));
        this.setPage(page);
        listSoils.remove(0);
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
        if (!usrDao.getPrivilegeUser(idUsrSystem, "soil/list")) {
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
        String fileName  = ""+getText("file.docrasta");
//        String fileName  = "rastasInfo.csv";
        rastaDao.getRastas(findParams, fileName);
  
        File f = new File(fileName);  
        inputStream = new FileInputStream(f);  
        f.delete();
        return "OUTPUTCSV"; 
    }    

    /**
     * Encargado de mostrar en un formulario la informacion de un rasta apartir de la identificacion
     * @param idRasta:  Identificacion del rasta
     * @return Informacion del rasta
     */
    public String show() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "soil/create") || !usrDao.getPrivilegeUser(idUsrSystem, "soil/modify")) {
            return BaseAction.NOT_AUTHORIZED;
        }
        actExe = (String)(this.getRequest().getParameter("action"));
        int pageReq;
        if (this.getRequest().getParameter("page") != null) {
            pageReq = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter("page")));
            this.setPage(pageReq);
        } else {
//            this.setPage(1);
        }
        try {
            this.setIdRasta(Integer.parseInt(this.getRequest().getParameter("idRasta")));
        } catch (NumberFormatException e) {
//            LOG.error("There was an error trying to parse the activityId parameter");
            this.setIdRasta(-1);
        }

//        this.setType_property_lot(new FieldTypesDao().findAll());
        if (this.getIdRasta()!= -1) {
            rasta      = rastaDao.objectById(this.getIdRasta());
            idField    = rasta.getFields().getIdFie();
            Fields fie = lotDao.objectById(idField);
            nameField  = fie.getNameFie();
            rowNew     = "false";
//            nameField = rasta.getFields().getNameFie();            
//            String dateActual = new SimpleDateFormat("dd/MM/yyyy").format(rasta.getFechaRas());   
//            rasta.setFechaRas(new Date(dateActual));
//            Date asignRasta = null;
//            Date asignRasta = rasta.getFechaRas();
//            System.out.println("date->"+dateActual);
//            try {
//                System.out.println("date->"+new SimpleDateFormat("yyyy-MM-dd").parse(dateActual));
//                asignRasta = new SimpleDateFormat("dd/MM/yyyy").parse(dateActual);
//            } catch (ParseException ex) {
//                System.out.println("errorConversion->"+ex.getMessage());
//            }
//            rasta.setFechaRas(asignRasta);
            additionalsAtrib = rastaDao.getHorizonRasta(this.getIdRasta());
            newReq = (additionalsAtrib.size());
//            System.out.println("size->"+newReq);
//            for (HorizontesRasta horizon : additionalsAtrib) {
//                System.out.println("additionalsAtrib->"+horizon.getColorSecoHorRas());        
//            }
//            System.out.println("valores->" + rasta);
        } else {
//            HorizontesRasta hor  = new HorizontesRasta(new ResistenciasRompimiento(1), new Textures(1), 1, 1.0, 1, 3);
//            additionalsAtribTemp.add(0,new HorizontesRasta());
//            this.setAdditionalsAtrib(additionalsAtribTemp);
//            additionalsAtrib = new ArrayList<HorizontesRasta>();
//            rowNew = "true";
        }
        return SUCCESS;
    }    

    /**
     * Encargado de guardar la informacion suministrada por el usuario para un rasta
     * @return Estado del proceso
     */
    public String saveData() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "soil/create") || !usrDao.getPrivilegeUser(idUsrSystem, "soil/modify")) {
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
//        info = "El rasta ha sido modificado con exito";
        
        //Numero de horizontes con datos
//        int avalaible = 0;
        List<HorizontesRasta> additionalsAtribTemp = new ArrayList<HorizontesRasta>();
        for (int i=0; i<additionalsAtrib.size(); i++) {
            HorizontesRasta horizon = additionalsAtrib.get(i);
            horizon.setIdHorRas(null);
            if (horizon != null) {
                additionalsAtribTemp.add(horizon);
            }
        }
        setAdditionalsAtrib(additionalsAtribTemp);

        try {
//            int idProOld = 0;
            tx = session.beginTransaction();
            SfGuardUserDao sfDao = new SfGuardUserDao();
            SfGuardUser sfUser = sfDao.getUserByLogin(user.getCreatedBy(), user.getNameUserUsr(), "");
//            Fields lot = null;
//            if (rasta.getIdRas()<=0) {
//                lot = new Fields();
//                lot.setIdFie(null);
//                lot.setIdProjectFie("1");
//                lot.setStatusFie(true); 
//                lot.setMeasureUnitFie("1");
//            } else {
//                HashMap fieldInfo = lotDao.findById(idField);
//                idProOld = Integer.parseInt(String.valueOf(fieldInfo.get("id_producer")));
//                lot = lotDao.objectById(idField);
//            }
//            lot.setIdFarmFie(idFarm);
//            if(idFarm>0) lot.setFarms(new Farms(idFarm));
//            lot.setNameFie(name_lot);
//            lot.setAltitudeFie(altLot);
//            lot.setLatitudeFie(latLot);
//            lot.setLongitudeFie(lonLot);
//            lot.setAreaFie(areaLot);            
            if (rasta.getIdRas()!=null) rastaDao.deleteHorizonte(rasta.getIdRas());
            
            String dmy     = new SimpleDateFormat("yyyy-MM-dd").format(rasta.getFechaRas());
            Date dateRasta = new SimpleDateFormat("yyyy-MM-dd").parse(dmy);
            String dateRastaIn = dmy;
            
            rasta.setFields(new Fields(idField));            
            rasta.setFechaRas(dateRasta);            
            rasta.setStatus(true);
//            rasta.setCreatedBy(sfUser.getId().intValue());
            session.saveOrUpdate(rasta);    
            
            rasta.setNumeroCajuelaRas(rasta.getIdRas());
            session.saveOrUpdate(rasta);
            
//            rastaDao.save(lot);
            
            //Agregar los horizontes
            int numCaj    = 0;
            String valHor = "[";        
            
            //Desde Java cuando se quita un solo horizonte arriba y se dejan los demas no permite el guardado
            for (HorizontesRasta horizon : additionalsAtrib) {
//                HorizontesRasta horizon = additionalsAtrib.get(i);
//                if (horizon != null) {
                    if (horizon.getColorSecoHorRas()!=null || horizon.getColorHumedoHorRas()!=null){                    
                        horizon.setRastas(rasta);
        //                horizon.setResistenciasRompimiento(new ResistenciasRompimiento(horizon.getResistenciasRompimientoId()));
        //                horizon.setTextures(new Textures(horizon.getTexturesId()));
                        horizon.setStatus(true);
                        numCaj++;
                        horizon.setNumeroHorizonteHorRas(numCaj);
                        session.saveOrUpdate(horizon);                    

                        if (additionalsAtrib.size()==numCaj) {
                            valHor += "{\"survey_solution[242]\":\""+numCaj+"\","+
                                   "\"survey_solution[116]\":\""+horizon.getEspesorHorRas()+"\","+ 
                                   "\"survey_solution[117]\":\""+horizon.getColorSecoHorRas()+"\","+ 
                                   "\"survey_solution[118]\":\""+horizon.getColorHumedoHorRas()+"\","+ 
                                   "\"survey_solution[119]\":\""+horizon.getTextures().getIdTex()+"\","+ 
                                   "\"survey_solution[120]\":\""+horizon.getResistenciasRompimiento().getIdResRom()+"\","+ 
                                   "\"subform_id\":\""+30+"\","+ 
                                   "\"idx\":"+numCaj+"}"; 
                        } else {
                            valHor += "{\"survey_solution[242]\":\""+numCaj+"\","+
                                   "\"survey_solution[116]\":\""+horizon.getEspesorHorRas()+"\","+ 
                                   "\"survey_solution[117]\":\""+horizon.getColorSecoHorRas()+"\","+ 
                                   "\"survey_solution[118]\":\""+horizon.getColorHumedoHorRas()+"\","+ 
                                   "\"survey_solution[119]\":\""+horizon.getTextures().getIdTex()+"\","+ 
                                   "\"survey_solution[120]\":\""+horizon.getResistenciasRompimiento().getIdResRom()+"\","+ 
                                   "\"subform_id\":\""+30+"\","+ 
                                   "\"idx\":"+numCaj+"},"; 
                        }
                    }
            }
            valHor += "]";            
            rasta.setNumeroCapasRas(numCaj);
            session.saveOrUpdate(rasta);
            
            LogEntities log = null;            
            log = LogEntitiesDao.getData(idEntSystem, rasta.getIdRas(), "rastas", action);
            if (log==null && !action.equals("M")) {
                log = new LogEntities();
                log.setIdLogEnt(null);
                log.setIdEntityLogEnt(idEntSystem);
                log.setIdObjectLogEnt(rasta.getIdRas());
                log.setTableLogEnt("rastas");
                log.setDateLogEnt(new Date());
                log.setActionTypeLogEnt(action);
                session.saveOrUpdate(log);
            }
//            logDao.save(log);   
            
            /*
            "109": "Fecha del Rasta" => dateRasta
            "110": "Coordenadas" Capturar posicion => lat, lng, alt
            "112": "Pendiente" => pend
            "113": "Terreno circundante" => terreno
            "114": "Posicion del perfil" => position
            "115": "Horizontes [[\"survey_solution[242]\":"Numero" => number,\"survey_solution[116]\":"Espesor (cm)" => espesor,\"survey_solution[117]\":"Color seco" => colSeco,\"survey_solution[118]\":"Color humedo" => colHum,\"survey_solution[119]\":"Textura" => textura,\"survey_solution[120]\":"Resistencia al rompimiento" => resistencia,\"subform_id\":\"30\",\"idx\":1]]"
            "121": "Caracteristicas y Observaciones" => caract
            "122": "Carbonatos" => carbonato
            "123": "Profundidad Carbonatos (cm)" => profCar
            "124": "Pedregosidad superficial rocas" => pedrSupRo
            "125": "Pedregosidad superficial piedras" => pedrSupPie
            "126": "Pedregosidad en el Perfil rocas" => pedrPerRo
            "127": "Pedregosidad en el Perfil piedras" => pedrPerPie
            "128": "Horizonte pedregoso o rocoso" => horPed
            "129": "Profundidad de horizonte pedregoso o rocoso (cm)" => profHorPedr
            "130": "Espesor de horizonte pedregoso o rocoso (cm)" => espHor
            "131": "Profundidad de primeras rocas o piedras (cm)" => profPri
            "132": "Capas endurecidas" => capasEnd
            "133": "Profundidad de capas endurecidas (cm)" => profCap
            "134": "Espesor de capas endurecidas (cm)" => espCap
            "135": "Moteados" => moteado
            "136": "Profundidad de moteados (cm)" => profMot
            "137": "Moteados mas bajo de 70cm" => moteadoBajo
            "138": "Estructura del suelo" => estSuelo
            "139": "Erosion" => erosion
            "140": "Moho" => moho
            "141": "Costras duras" => cosDur
            "142": "Sitio expuesto a sol" => sitioSol
            "143": "Costras blancas" => cosBlan
            "144": "Costras negras" => cosNeg
            "145": "Region seca" => regSeca
            "146": "Raices vivas" => regViva
            "147": "Profundidad de raices vivas (cm)" => profRai
            "148": "Crecimiento de las plantas afectadas" => crecPlan
            "149": "Mucha hojarasca" => muchaHoja
            "150": "Suelo es muy negro, muy blando" => sueNegro
            "151": "Cuchillo entra sin ningún esfuerzo" => cuchilloEnt
            "152": "Cerca de agua subterránea muy superficial" => cercaAgua
            "153": "Recubrimiento vegetal del suelo" => recVeg
            "247": "Seleccion el lote asociado" => Seleccion => selLote
            */
            
            //Manejo para ingresar datos en MongoDB            
            HashMap valInfo = new HashMap();            
            valInfo.put("dateRasta", dateRastaIn);
            valInfo.put("pend", rasta.getPendienteTerrenoRas());
            valInfo.put("terreno", rasta.getTerrenoCircundanteRas());
            valInfo.put("position", rasta.getPosicionPerfilRas());
            valInfo.put("pH", rasta.getPhRas());
            valInfo.put("carbonato", rasta.getCarbonatosRas());
            valInfo.put("profCar", rasta.getProfundidadCarbonatosRas());
            valInfo.put("pedrSupRo", rasta.getRocasSuperficieRas());
            valInfo.put("pedrSupPie", rasta.getPiedrasSuperficieRas());
            valInfo.put("pedrPerRo", rasta.getRocasPerfilRas());
            valInfo.put("pedrPerPie", rasta.getPiedrasPerfilRas());
            valInfo.put("horPed", rasta.getHorizontePedrogosoRocosoRas());
            valInfo.put("profHorPedr", rasta.getProfundidadHorizontePedregosoRas());
            valInfo.put("espHor", rasta.getEspesorHorizontePedregosoRas());
            valInfo.put("profPri", rasta.getProfundidadPrimerasPiedrasRas());
            valInfo.put("capasEnd", rasta.getCapasEndurecidasRas());
            valInfo.put("profCap", rasta.getPrufundidadCapasRas());
            valInfo.put("espCap", rasta.getEspesorCapaEndurecidaRas());
            valInfo.put("moteado", rasta.getMoteadosRas());
            valInfo.put("profMot", rasta.getProfundidadMoteadosRas());
            valInfo.put("moteadoBajo", rasta.getMoteadosMas70cmRas());
            valInfo.put("estSuelo", rasta.getEstructuraRas());
            valInfo.put("erosion", rasta.getErosionRas());
            valInfo.put("moho", rasta.getMohoRas());
            valInfo.put("cosDur", rasta.getCostrasDurasRas());
            valInfo.put("sitioSol", rasta.getExposicionSolRas());
            valInfo.put("cosBlan", rasta.getCostrasBlancasRas());
            valInfo.put("cosNeg", rasta.getCostrasNegrasRas());
            valInfo.put("regSeca", rasta.getRegionSecaRas());
            valInfo.put("raiViva", rasta.getRaicesVivasRas());
            valInfo.put("profRai", rasta.getProfundidadRaicesRas());
            valInfo.put("crecPlan", rasta.getPlantasPequenasRas());
            valInfo.put("muchaHoja", rasta.getHojarascaRas());
            valInfo.put("sueNegro", rasta.getSueloNegroBlandoRas());
            valInfo.put("cuchilloEnt", rasta.getCuchilloPrimerHorizonteRas());
            valInfo.put("cercaAgua", rasta.getCercaRiosQuebradasRas());
            valInfo.put("recVeg", rasta.getRecubrimientoVegetalRas());
            valInfo.put("rastaId", rasta.getIdRas());
            valInfo.put("valHorizons", valHor);
            
            valInfo.put("fieldId", idField);
            HashMap fieldInfo = lotDao.findById(idField);
            valInfo.put("nameField", String.valueOf(fieldInfo.get("name_lot")));
            
            valInfo.put("lat", rasta.getLatitudRas());
            valInfo.put("lng", rasta.getLongitudRas());
            valInfo.put("alt", rasta.getAltitudRas());
            valInfo.put("userMobileId", sfUser.getId());      
            
            BasicDBObject query = new BasicDBObject();
            query.put("InsertedId", ""+rasta.getIdRas());
            query.put("form_id", "6");
            
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
            BasicDBObject jsonField = null;
//            jsonField          = GlobalFunctions.generateJSONSoil(valInfo);
            
            if (cursor.count()>0) {
                System.out.println("actualizo mongo");
//                result = col.update(query, jsonField);
            } else {
                System.out.println("inserto mongo");
//                result = col.insert(jsonField);
            }
            
//            if (result.getError()!=null) {
//                throw new HibernateException("");
//            }            
            
            mongo.close();
            tx.commit();           
            state = "success";
            if (action.equals("C")) {
                info = getText("message.successadd.soil");
            } else if (action.equals("M")) {
                info = getText("message.successedit.soil");
            }            
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            state = "failure";
            if (action.equals("C")) {
                info  = getText("message.failadd.soil");
            } else if (action.equals("M")) {
                info  = getText("message.failedit.soil");
            }
        } catch (ParseException e) { 
        
        } finally {
            session.close();
        }       
        
//        return ERROR;
        return "states";
    }

    /**
     * Encargado de borrar la informacion de un rasta apartir de su identificacion
     * @param idRasta:  Identificacion del rasta
     * @return Estado del proceso
     */
    public String delete() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "soil/delete")) {
            return BaseAction.NOT_AUTHORIZED;
        }
        Integer idRasta = 0;
        try {
            idRasta = Integer.parseInt(this.getRequest().getParameter("idRasta"));
        } catch (NumberFormatException e) {
            idRasta = -1;
        }
        
        if (idRasta==-1) {
            state = "failure";
            info  = getText("message.failgetinfo.soil");
            return "states";
        }
        
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
//            rastaDao.deleteHorizonte(idRasta);
            
            Rastas ras = rastaDao.objectById(idRasta);      
            ras.setStatus(false);     
            session.saveOrUpdate(ras);
//            session.delete(ras);            
            
            LogEntities log = new LogEntities();
            log.setIdLogEnt(null);
            log.setIdEntityLogEnt(idEntSystem); 
            log.setIdObjectLogEnt(ras.getIdRas());
            log.setTableLogEnt("rastas");
            log.setDateLogEnt(new Date());
            log.setActionTypeLogEnt("D");
            session.saveOrUpdate(log);
//            logDao.save(log);
            
            BasicDBObject query = new BasicDBObject();
            query.put("InsertedId", ""+ras.getIdRas());
            query.put("form_id", "6");
            
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
            
            tx.commit();         
            state = "success";
            info  = getText("message.successdelete.soil");
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            state = "failure";
            info  = getText("message.faildelete.soil");
        } finally {
            session.close();
        }      
        
        return "states";
//        return SUCCESS;
    }
    
    /**
     * Encargado de borrar la informacion de los rastas que se han seleccionado
     * @param valSel:  Valores que se han seleccionado para borrar
     * @return Estado del proceso
     */
    public String deleteAll() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "soil/delete")) {
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
            info  = getText("message.failgetinfo.soil");
            return "states";
        }
        
        state = rastaDao.deleteAllRastas(valSel, idEntSystem);
        if (state.equals("success")) {
            info  = getText("message.successalldelete.soil");
        } else if (state.equals("failure")) {
            info  = getText("message.failalldelete.soil");
        }
//        state = "success";
        return "states";
    }
    
}