
package org.aepscolombia.platform.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.aepscolombia.platform.models.dao.AssociationDao;
import org.aepscolombia.platform.models.dao.DepartmentsDao;
import org.aepscolombia.platform.models.dao.EntitiesDao;

import org.aepscolombia.platform.models.dao.LogEntitiesDao;
import org.aepscolombia.platform.models.dao.ProductionEventsDao;
import org.aepscolombia.platform.models.dao.SfGuardUserDao;
import org.aepscolombia.platform.models.dao.SoilAnalysisDao;
import org.aepscolombia.platform.models.dao.TexturesDao;
import org.aepscolombia.platform.models.dao.UsersDao;
import org.aepscolombia.platform.models.entity.CropsTypes;
import org.aepscolombia.platform.models.entity.Departments;
import org.aepscolombia.platform.models.entity.Entities;

import org.aepscolombia.platform.models.entity.LogEntities;
import org.aepscolombia.platform.models.entity.Municipalities;
import org.aepscolombia.platform.models.entity.ProductionEvents;
import org.aepscolombia.platform.models.entity.SoilAnalysis;
import org.aepscolombia.platform.models.entity.Textures;
import org.aepscolombia.platform.models.entity.Users;
import org.aepscolombia.platform.models.entityservices.SfGuardUser;
import org.aepscolombia.platform.util.APConstants;
import org.aepscolombia.platform.util.HibernateUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Clase ActionSoil
 *
 * Contiene los metodos para interactuar con el modulo del Suelo Fisico-Químico
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ActionSoil extends BaseAction {
    
    /**
     * Atributos provenientes del formulario
     */
    private int idCrop;
    private Integer idSoil;
    private String nameCrop;
    
    private String search_soil;
    private String sample_number;
    private String date_sampling;
    private String id_crop_type;
    private String name_field;
    private String name_farm;
    private String name_mun;
    private String name_dep;
    private List<HashMap> listSoils;
    private Integer searchFromSoil;
    
    private Users user;
    private Integer idEntSystem;    
    private Integer idUsrSystem;    
    private SoilAnalysis soil = new SoilAnalysis();
    private UsersDao usrDao;
    private List<Entities> list_agronomist;
    private AssociationDao assDao;
    private String coCode;
    private String typeTexture;
    private List<Textures> type_textures;
    private List<Departments> department_producer;
    private List<Municipalities> city_producer;
    

    /**
     * Metodos getter y setter por cada variable del formulario
     */   

    public SoilAnalysis getSoil() {
        return soil;
    }

    public void setSoil(SoilAnalysis soil) {
        this.soil = soil;
    }    

    public String getNameCrop() {
        return nameCrop;
    }

    public void setNameCrop(String nameCrop) {
        this.nameCrop = nameCrop;
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
    
    public List<Departments> getDepartment_producer() {
        return department_producer;
    }

    public void setDepartment_producer(List<Departments> department_producer) {
        this.department_producer = department_producer;
    }
    
    public List<Municipalities> getCity_producer() {
        return city_producer;
    }

    public void setCity_producer(List<Municipalities> city_producer) {
        this.city_producer = city_producer;
    }

    public List<Textures> getType_textures() {
        return type_textures;
    }

    public void setType_textures(List<Textures> type_textures) {
        this.type_textures = type_textures;
    }   

    public AssociationDao getAssDao() {
        return assDao;
    }

    public void setAssDao(AssociationDao assDao) {
        this.assDao = assDao;
    }   
    public Integer getIdSoil() {
        return idSoil;
    }

    public void setIdSoil(Integer idSoil) {
        this.idSoil = idSoil;
    }   

    public int getIdCrop() {
        return idCrop;
    }

    public void setIdCrop(int idCrop) {
        this.idCrop = idCrop;
    }    

    public String getSearch_soil() {
        return search_soil;
    }

    public void setSearch_soil(String search_soil) {
        this.search_soil = search_soil;
    }    

    public String getSample_number() {
        return sample_number;
    }

    public void setSample_number(String sample_number) {
        this.sample_number = sample_number;
    }

    public String getDate_sampling() {
        return date_sampling;
    }

    public void setDate_sampling(String date_sampling) {
        this.date_sampling = date_sampling;
    }

    public String getId_crop_type() {
        return id_crop_type;
    }

    public void setId_crop_type(String id_crop_type) {
        this.id_crop_type = id_crop_type;
    }

    public String getName_field() {
        return name_field;
    }

    public void setName_field(String name_field) {
        this.name_field = name_field;
    }

    public String getName_farm() {
        return name_farm;
    }

    public void setName_farm(String name_farm) {
        this.name_farm = name_farm;
    }

    public String getName_mun() {
        return name_mun;
    }

    public void setName_mun(String name_mun) {
        this.name_mun = name_mun;
    }

    public String getName_dep() {
        return name_dep;
    }

    public void setName_dep(String name_dep) {
        this.name_dep = name_dep;
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

    public String getTypeTexture() {
        return typeTexture;
    }

    public void setTypeTexture(String typeTexture) {
        this.typeTexture = typeTexture;
    }
        
    
    
    /**
     * Atributos generales de clase
     */
    private HashMap additionals;
    private int page = 1, countTotal, maxResults = 10;
    
    private ProductionEventsDao eventDao      = new ProductionEventsDao();
    private SoilAnalysisDao soilDao    = new SoilAnalysisDao();
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
        usrDao = new UsersDao();
        idUsrSystem = user.getIdUsr();
        coCode = (String) this.getSession().get(APConstants.COUNTRY_CODE);
        this.setDepartment_producer(new DepartmentsDao().findAll(coCode));
        this.setType_textures(new TexturesDao().findAll());
//        this.setType_textures(new TexturesDao().findAll());
        List<Municipalities> mun = new ArrayList<Municipalities>();
        mun.add(new Municipalities());
        this.setCity_producer(mun);
        EntitiesDao entDao = new EntitiesDao();
        Entities entTemp = entDao.findById(idEntSystem);
        typeEnt = entTemp.getEntitiesTypes().getIdEntTyp();
        assDao = new AssociationDao();        
    }
    
    private Map fieldError;

    public Map getFieldError() {
        return fieldError;
    }

    public void setFieldError(Map fieldError) {
        this.fieldError = fieldError;
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
        if (actExe!=null && (actExe.equals("create") || actExe.equals("modify"))) {
            HashMap required = new HashMap();
            required.put("nameCrop", nameCrop);
            required.put("soil.dateSamplingSoAna", soil.getDateSamplingSoAna());
            required.put("soil.sampleNumberSoAna", soil.getSampleNumberSoAna());
            required.put("typeTexture", typeTexture);
//            System.out.println("soil.textures=>"+typeTexture);
            boolean enter = false;
            for (Iterator it = required.keySet().iterator(); it.hasNext();) {
                String sK = (String) it.next();
                String sV = String.valueOf(required.get(sK));
//                System.out.println(sK + " : " + sV);
                if (StringUtils.trim(sV).equals("null") || StringUtils.trim(sV)==null || StringUtils.trim(sV).equals("") || sV.equals("-1")) {
                    addFieldError(sK, getText("message.fieldsrequired.soilanalysis"));
                    enter = true;
                }
            }
            
            if (enter) {
                addActionError(getText("message.missingfields.soilanalysis"));
            }
            
            if (soil.getSandSoAna()!=null && (soil.getSandSoAna()<0 || soil.getSandSoAna()>100)) {
                addFieldError("soil.sandSoAna", getText("message.datainvalidsand.soilanalysis"));
                addActionError(getText("desc.datainvalidsand.soilanalysis"));
            }
            
            if (soil.getLemonSoAna()!=null && (soil.getLemonSoAna()<0 || soil.getLemonSoAna()>100)) {
                addFieldError("soil.lemonSoAna", getText("message.datainvalidlemon.soilanalysis"));
                addActionError(getText("desc.datainvalidlemon.soilanalysis"));
            }
            
            if (soil.getClaySoAna()!=null && (soil.getClaySoAna()<0 || soil.getClaySoAna()>100)) {
                addFieldError("soil.claySoAna", getText("message.datainvalidclay.soilanalysis"));
                addActionError(getText("desc.datainvalidclay.soilanalysis"));
            }
            
            if (soil.getOrganicMaterialSoAna()!=null && (soil.getOrganicMaterialSoAna()<0 || soil.getOrganicMaterialSoAna()>100)) {
                addFieldError("soil.organicMaterialSoAna", getText("message.datainvalidorganicmat.soilanalysis"));
                addActionError(getText("desc.datainvalidorganicmat.soilanalysis"));
            }
            
            if (soil.getCoSoAna()!=null && (soil.getCoSoAna()<0 || soil.getCoSoAna()>100)) {
                addFieldError("soil.coSoAna", getText("message.datainvalidco.soilanalysis"));
                addActionError(getText("desc.datainvalidco.soilanalysis"));
            }
            
            if (soil.getNitrogenSoAna()!=null && (soil.getNitrogenSoAna()<0 || soil.getNitrogenSoAna()>100)) {
                addFieldError("soil.nitrogenSoAna", getText("message.datainvalidnitrogen.soilanalysis"));
                addActionError(getText("desc.datainvalidnitrogen.soilanalysis"));
            }
        }
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
     * suelos registrados a un usuario
     * @param valName:  Nombre del valor a buscar
     * @param valId:    Identificacion del valor a buscar
     * @param selected: Valor seleccionado
     * @return lista de suelos fisico-químico
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
            selected="soil";
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
        if(searchFromSoil!=null && searchFromSoil==2) {
            search_soil = "";
        }
                
        findParams.put("idEntUser", idEntSystem);
        findParams.put("search_soil", search_soil);
        findParams.put("sample_number", sample_number);
        findParams.put("date_sampling", date_sampling);
        findParams.put("id_crop_type", id_crop_type);
        findParams.put("name_field", name_field);
        findParams.put("name_farm", name_farm);
        findParams.put("name_mun", name_mun);
        findParams.put("name_dep", name_dep);
        
        int pageReq;
        if (this.getRequest().getParameter("page") == null) {
            pageReq = this.getPage();
        } else {
            pageReq = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter("page")));
        }
        findParams.put("pageNow", pageReq);
        findParams.put("maxResults", this.getMaxResults());
        listSoils = soilDao.findByParams(findParams);
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
        String fileName  = ""+getText("file.docsoil");
//        String fileName  = "soilsInfo.csv";
        soilDao.getSoilAnalysis(findParams, fileName);
  
        File f = new File(fileName);  
        inputStream = new FileInputStream(f);  
        f.delete();
        return "OUTPUTCSV"; 
    }    

    /**
     * Encargado de mostrar en un formulario la informacion de un suelo fisico-químico apartir de la identificacion
     * @param idSoil:  Identificacion del suelo
     * @return Informacion de un suelo fisico-químico
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
            this.setIdSoil(Integer.parseInt(this.getRequest().getParameter("idSoil")));
        } catch (NumberFormatException e) {
//            LOG.error("There was an error trying to parse the activityId parameter");
            this.setIdSoil(-1);
        }
        this.setDepartment_producer(new DepartmentsDao().findAll(coCode));
        this.setType_textures(new TexturesDao().findAll());
        if (this.getIdSoil()!= -1) {
            soil       = soilDao.objectById(this.getIdSoil());
            idCrop     = soil.getProductionEvents().getIdProEve();
            HashMap event = eventDao.findById(idCrop);
            nameCrop   = String.valueOf(event.get("nameCrop"));
        } 
        return SUCCESS;
    }    

    /**
     * Encargado de guardar la informacion suministrada por el usuario para un suelo fisico-químico
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

        try {
//            int idProOld = 0;
            tx = session.beginTransaction();
            SfGuardUserDao sfDao = new SfGuardUserDao();
            SfGuardUser sfUser = sfDao.getUserByLogin(user.getCreatedBy(), user.getNameUserUsr(), "");      
            
            String dmy     = new SimpleDateFormat("yyyy-MM-dd").format(soil.getDateSamplingSoAna());
            Date dateSoil  = new SimpleDateFormat("yyyy-MM-dd").parse(dmy);
            
            soil.setProductionEvents(new ProductionEvents(idCrop));            
            soil.setDateSamplingSoAna(dateSoil);        
            soil.setTextures(new Textures(Integer.parseInt(typeTexture)));
            soil.setStatus(true);
//            soil.setCreatedBy(sfUser.getId().intValue());
            session.saveOrUpdate(soil);    
            
            LogEntities log = null;            
            log = LogEntitiesDao.getData(idEntSystem, soil.getIdSoAna(), "soil_analysis", action);
            if (log==null && !action.equals("M")) {
                log = new LogEntities();
                log.setIdLogEnt(null);
                log.setIdEntityLogEnt(idEntSystem);
                log.setIdObjectLogEnt(soil.getIdSoAna());
                log.setTableLogEnt("soil_analysis");
                log.setDateLogEnt(new Date());
                log.setActionTypeLogEnt(action);
                session.saveOrUpdate(log);
            }
//            logDao.save(log);   
            
            /*
            "109": "Fecha del Suelo" => dateSuelo
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
            /*HashMap valInfo = new HashMap();            
            valInfo.put("dateSuelo", dateRastaIn);
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
            valInfo.put("soilId", rasta.getIdRas());
            valInfo.put("valHorizons", valHor);
            
            valInfo.put("fieldId", idField);
            HashMap fieldInfo = lotDao.findById(idField);
            valInfo.put("nameCrop", String.valueOf(fieldInfo.get("name_lot")));
            
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
            
            mongo.close();*/
            tx.commit();           
            state = "success";
            if (action.equals("C")) {
                info = getText("message.successadd.soilanalysis");
            } else if (action.equals("M")) {
                info = getText("message.successedit.soilanalysis");
            }            
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            state = "failure";
            if (action.equals("C")) {
                info  = getText("message.failadd.soilanalysis");
            } else if (action.equals("M")) {
                info  = getText("message.failedit.soilanalysis");
            }
        } catch (ParseException e) { 
        
        } finally {
            session.close();
        }       
        
//        return ERROR;
        return "states";
    }

    /**
     * Encargado de borrar la informacion de un suelo fisico-químico apartir de su identificacion
     * @param idSoil:  Identificacion del suelo
     * @return Estado del proceso
     */
    public String delete() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "soil/delete")) {
            return BaseAction.NOT_AUTHORIZED;
        }
        Integer idSoil = 0;
        try {
            idSoil = Integer.parseInt(this.getRequest().getParameter("idSoil"));
        } catch (NumberFormatException e) {
            idSoil = -1;
        }
        
        if (idSoil==-1) {
            state = "failure";
            info  = getText("message.failgetinfo.soil");
            return "states";
        }
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            
            SoilAnalysis soil = soilDao.objectById(idSoil);      
            soil.setStatus(false);     
            session.saveOrUpdate(soil);
//            session.delete(ras);            
            
            LogEntities log = new LogEntities();
            log.setIdLogEnt(null);
            log.setIdEntityLogEnt(idEntSystem); 
            log.setIdObjectLogEnt(soil.getIdSoAna());
            log.setTableLogEnt("soil_analysis");
            log.setDateLogEnt(new Date());
            log.setActionTypeLogEnt("D");
            session.saveOrUpdate(log);
//            logDao.save(log);
            
            /*BasicDBObject query = new BasicDBObject();
            query.put("InsertedId", ""+soil.getIdSoAna());
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
            mongo.close();*/
            
            tx.commit();         
            state = "success";
            info  = getText("message.successdelete.soilanalysis");
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            state = "failure";
            info  = getText("message.faildelete.soilanalysis");
        } finally {
            session.close();
        }      
        
        return "states";
//        return SUCCESS;
    }
    
    /**
     * Encargado de borrar la informacion de los suelos fisico-químicos que se han seleccionado
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
            info  = getText("message.failgetinfo.soilanalysis");
            return "states";
        }
        
        state = soilDao.deleteAllSoilAnalysis(valSel, idEntSystem);
        if (state.equals("success")) {
            info  = getText("message.successalldelete.soilanalysis");
        } else if (state.equals("failure")) {
            info  = getText("message.failalldelete.soilanalysis");
        }
//        state = "success";
        return "states";
    }
    
}