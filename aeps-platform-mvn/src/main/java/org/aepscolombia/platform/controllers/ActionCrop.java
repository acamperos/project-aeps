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
public class ActionCrop extends BaseAction {
    
    //Atributos del formulario 
    /**
     * Atributos provenientes del formulario
     */
    private int idField;
    private int idCrop;
    private String nameField;
    private int typeCrop;
    private String performObj;
    private int lastCrop;
    private boolean drainPlot;    
    private String nameTypeCrop;
    private String lastTypeCrop;
    private String nameDrainPlot;
    
    private String name_producer;
    private String type_doc;
    private Integer num_doc;
    private Integer num_farm;
    private String name_farm;
    private Integer num_field;
    private String name_field;
    
    private String search_crop; 
    private List<HashMap> listCrops;
    
    private List<HashMap> listPrep;
    private List<HashMap> listIrr;
    private List<HashMap> listFert;
    private List<HashMap> listCont;
    private List<HashMap> listMont;
    
    
    private List<DocumentsTypes> type_ident_producer;
    
    private List<ChemicalsSowing> type_chem_sow;
    private List<DoseUnits> type_dose_units;
    private List<Genotypes> type_genotypes;
    private List<GenotypesSowing> type_genotypes_sow;
    private List<GrowingEnvironment> type_grow_env;
    private List<SeedsInoculations> type_seed_in;
    private List<HarvestMethods> type_harv_meth;
    private List<ResultingProducts> type_res_pro;
    private List<SeedsColors> type_seed_color;
    private List<SeedsOrigins> type_seed_org;
    private List<SeedsTypes> type_seed_type;   
    private List<SowingTypes> type_sow_types;   
    
    private List<CropsTypes> type_crops;
    private Integer searchFrom;
    
    private Users user;
    private Integer idEntSystem;    
    private Integer idUsrSystem;    

    private Beans beans   = new Beans();
    private Cassavas cass = new Cassavas();
    private Harvests harv = new Harvests();
    private Maize maize   = new Maize();
    private PhysiologicalMonitoring phys = new PhysiologicalMonitoring();
    private Sowing sowing = new Sowing();
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

    public Cassavas getCass() {
        return cass;
    }

    public void setCass(Cassavas cass) {
        this.cass = cass;
    }

    public Harvests getHarv() {
        return harv;
    }

    public void setHarv(Harvests harv) {
        this.harv = harv;
    }

    public Maize getMaize() {
        return maize;
    }

    public void setMaize(Maize maize) {
        this.maize = maize;
    }

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

    public String getNameField() {
        return nameField;
    }

    public void setNameField(String nameField) {
        this.nameField = nameField;
    } 

    public Integer getSearchFrom() {
        return searchFrom;
    }

    public void setSearchFrom(Integer searchFrom) {
        this.searchFrom = searchFrom;
    }   
        
    public int getIdField() {
        return idField;
    }

    public void setIdField(int idField) {
        this.idField = idField;
    }

    public int getIdCrop() {
        return idCrop;
    }

    public void setIdCrop(int idCrop) {
        this.idCrop = idCrop;
    }

    public String getNameTypeCrop() {
        return nameTypeCrop;
    }

    public void setNameTypeCrop(String nameTypeCrop) {
        this.nameTypeCrop = nameTypeCrop;
    }   

    public String getNameDrainPlot() {
        return nameDrainPlot;
    }   

    public String getLastTypeCrop() {
        return lastTypeCrop;
    }   

    public int getTypeCrop() {
        return typeCrop;
    }

    public void setTypeCrop(int typeCrop) {
        this.typeCrop = typeCrop;
    }

    public String getPerformObj() {
        return performObj;
    }

    public void setPerformObj(String performObj) {
        this.performObj = performObj;
    }

    public int getLastCrop() {
        return lastCrop;
    }

    public void setLastCrop(int lastCrop) {
        this.lastCrop = lastCrop;
    }

    public boolean isDrainPlot() {
        return drainPlot;
    }

    public void setDrainPlot(boolean drainPlot) {
        this.drainPlot = drainPlot;
    }

    public String getSearch_crop() {
        return search_crop;
    }

    public void setSearch_crop(String search_crop) {
        this.search_crop = search_crop;
    }    
    
    public void setType_ident_producer(List<DocumentsTypes> type_ident_producer) {
        this.type_ident_producer = type_ident_producer;
    }

    public List<DocumentsTypes> getType_ident_producer() {
        return type_ident_producer;
    }

    public List<ChemicalsSowing> getType_chem_sow() {
        return type_chem_sow;
    }

    public void setType_chem_sow(List<ChemicalsSowing> type_chem_sow) {
        this.type_chem_sow = type_chem_sow;
    }

    public List<DoseUnits> getType_dose_units() {
        return type_dose_units;
    }

    public void setType_dose_units(List<DoseUnits> type_dose_units) {
        this.type_dose_units = type_dose_units;
    }

    public List<Genotypes> getType_genotypes() {
        return type_genotypes;
    }

    public void setType_genotypes(List<Genotypes> type_genotypes) {
        this.type_genotypes = type_genotypes;
    }

    public List<GenotypesSowing> getType_genotypes_sow() {
        return type_genotypes_sow;
    }

    public void setType_genotypes_sow(List<GenotypesSowing> type_genotypes_sow) {
        this.type_genotypes_sow = type_genotypes_sow;
    }

    public List<SeedsInoculations> getType_seed_in() {
        return type_seed_in;
    }

    public void setType_seed_in(List<SeedsInoculations> type_seed_in) {
        this.type_seed_in = type_seed_in;
    }   

    public List<GrowingEnvironment> getType_grow_env() {
        return type_grow_env;
    }

    public void setType_grow_env(List<GrowingEnvironment> type_grow_env) {
        this.type_grow_env = type_grow_env;
    }

    public List<HarvestMethods> getType_harv_meth() {
        return type_harv_meth;
    }

    public void setType_harv_meth(List<HarvestMethods> type_harv_meth) {
        this.type_harv_meth = type_harv_meth;
    }

    public List<ResultingProducts> getType_res_pro() {
        return type_res_pro;
    }

    public void setType_res_pro(List<ResultingProducts> type_res_pro) {
        this.type_res_pro = type_res_pro;
    }

    public List<SeedsColors> getType_seed_color() {
        return type_seed_color;
    }

    public void setType_seed_color(List<SeedsColors> type_seed_color) {
        this.type_seed_color = type_seed_color;
    }

    public List<SeedsOrigins> getType_seed_org() {
        return type_seed_org;
    }

    public void setType_seed_org(List<SeedsOrigins> type_seed_org) {
        this.type_seed_org = type_seed_org;
    }

    public List<SeedsTypes> getType_seed_type() {
        return type_seed_type;
    }

    public void setType_seed_type(List<SeedsTypes> type_seed_type) {
        this.type_seed_type = type_seed_type;
    }   

    public List<SowingTypes> getType_sow_types() {
        return type_sow_types;
    }

    public void setType_sow_types(List<SowingTypes> type_sow_types) {
        this.type_sow_types = type_sow_types;
    }    

    public List<CropsTypes> getType_crops() {
        return type_crops;
    }

    public void setType_crops(List<CropsTypes> type_crops) {
        this.type_crops = type_crops;
    }   

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }    

    public List<HashMap> getListPrep() {
        return listPrep;
    }

    public List<HashMap> getListIrr() {
        return listIrr;
    }

    public List<HashMap> getListFert() {
        return listFert;
    }

    public List<HashMap> getListCont() {
        return listCont;
    }

    public List<HashMap> getListMont() {
        return listMont;
    }   

    public List<HashMap> getListCrops() {
        return listCrops;
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

    public Integer getNum_doc() {
        return num_doc;
    }

    public void setNum_doc(Integer num_doc) {
        this.num_doc = num_doc;
    }

    public Integer getNum_farm() {
        return num_farm;
    }

    public void setNum_farm(Integer num_farm) {
        this.num_farm = num_farm;
    }

    public String getName_farm() {
        return name_farm;
    }

    public void setName_farm(String name_farm) {
        this.name_farm = name_farm;
    }

    public Integer getNum_field() {
        return num_field;
    }

    public void setNum_field(Integer num_field) {
        this.num_field = num_field;
    }

    public String getName_field() {
        return name_field;
    }

    public void setName_field(String name_field) {
        this.name_field = name_field;
    }
    
    
    
    
    //Atributos generales de clase
    /**
     * Atributos generales de clase
     */
    private HashMap additionals;
    private int page = 1, countTotal, maxResults = 10;
    private int option_geo_lot;
    
    private FieldsDao lotDao      = new FieldsDao();
    private ProductionEventsDao cropDao    = new ProductionEventsDao();
    private BeansDao beansDao     = new BeansDao();
    private CassavasDao cassDao   = new CassavasDao();
    private ControlsDao conDao    = new ControlsDao();
    private FertilizationsDao fertDao     = new FertilizationsDao();
    private HarvestsDao harDao    = new HarvestsDao();
    private IrrigationDao irrDao  = new IrrigationDao();
    private MaizeDao maizeDao     = new MaizeDao();
    private MonitoringDao monDao     = new MonitoringDao();
    private PhysiologicalMonitoringDao physDao     = new PhysiologicalMonitoringDao();
    private PreparationsDao prepDao     = new PreparationsDao();
    private SowingDao sowDao      = new SowingDao();
    private LogEntitiesDao logDao = new LogEntitiesDao();
    
    private String state = "";
    private String info  = "";
    private String valId = "", valName="", selected="";
    
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

//    @Override
    public String getInfo() {
        return info;
    }
    
//    @Override
//    public void setInfo(String info) {
//        this.info = info;
//    }

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
        usrDao = new UsersDao();
        idUsrSystem = user.getIdUsr();
        EntitiesDao entDao = new EntitiesDao();
        Entities entTemp = entDao.findById(idEntSystem);
        typeEnt = entTemp.getEntitiesTypes().getIdEntTyp();
//        if (user.getIdUsr()!=null) idEntSystem = UsersDao.getEntitySystem(user.getIdUsr());
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
//        numberFormatter = NumberFormat.getNumberInstance(new Locale("en_US"));
//        quantityOut = numberFormatter.format(rasta.getLatitudRas());
//        System.out.println("entreeeeeeeeeeeeeeeeeeeee->"+quantityOut);
        if (actExe.equals("create") || actExe.equals("modify")) {
            HashMap required = new HashMap();
            required.put("nameField", nameField);
            required.put("typeCrop", typeCrop);
            required.put("performObj", performObj);
            required.put("lastCrop", lastCrop);
            required.put("drainPlot", drainPlot);    
            boolean enter = false;
            
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
            
//            System.out.println("performObj->"+performObj);
            
//            double performDou = (performObj.equals("")) ? 0.0 : Double.parseDouble(performObj.replace(',','.'));
            double performDou = (performObj.equals("")) ? 0.0 : Double.parseDouble(performObj);

            if (performDou!=0) {
                if (performDou<0 || performDou>30000) {
                    addFieldError("performObj", "Dato invalido valor entre 0 y 30000");
                    addActionError("Se ingreso un objetivo de rendimiento invalido, por favor ingresar un valor entre 0 y 30000");
                }
            }
        }
    }
    
    private int numRows=0;

    public Integer getNumRows() {
        return numRows;
    }

    public void setNumRows(Integer numRows) {
        this.numRows = numRows;
    }       
    
    private int pageReq;

    public int getPageReq() {
        return pageReq;
    }
    
    
    private void setValuesCrop(Integer idCrop) {
        HashMap cropInfo = cropDao.findById(idCrop);
        this.setNameField(String.valueOf(cropInfo.get("nameField")));
        this.setIdField(Integer.parseInt(String.valueOf(cropInfo.get("idField"))));
        this.setTypeCrop(Integer.parseInt(String.valueOf(cropInfo.get("typeCrop"))));
        this.setPerformObj(String.valueOf(cropInfo.get("performObj")));
//        this.setPerformObj(Double.parseDouble(String.valueOf(cropInfo.get("performObj"))));
        this.setLastCrop(Integer.parseInt(String.valueOf(cropInfo.get("lastCrop"))));
        this.setDrainPlot((Boolean)cropInfo.get("drainPlot"));    
//        CropsTypes crop = new CropsTypesDao().objectById(typeCrop);
        
        this.setNameTypeCrop(new CropsTypesDao().objectById(typeCrop).getNameCroTyp());
        lastTypeCrop = new CropsTypesDao().objectById(this.getLastCrop()).getNameCroTyp();
        if (this.getLastCrop()==1) {
            nameDrainPlot = "Si";
        } else {
            nameDrainPlot = "No";
        }
    }
    
    public String showDataCrop() 
    {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "crop/list")) {
            return BaseAction.NOT_AUTHORIZED;
        }
        try {
            this.setIdCrop(Integer.parseInt(this.getRequest().getParameter("idCrop")));
        } catch (NumberFormatException e) {
            this.setIdCrop(-1);
        }
        if (this.getIdCrop()!= -1) {
            setValuesCrop(this.getIdCrop());
        }
        return SUCCESS;
    }
  
    
    /**
    * Accion que permite visualizar la informacion general de un cultivo
    *
    */
    public String view()
    {     
        if (!usrDao.getPrivilegeUser(idUsrSystem, "crop/list")) {
            return BaseAction.NOT_AUTHORIZED;
        }
//        actExe = (String)(this.getRequest().getParameter("action"));
        actExe = "modify";
        
//        int pageReq;
        if (this.getRequest().getParameter("page") == null) {
            pageReq = this.getPage();
        } else {
            pageReq = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter("page")));
        }
        this.getRequest().getParameterNames();
        try {
            this.setIdCrop(Integer.parseInt(this.getRequest().getParameter("idCrop")));
        } catch (NumberFormatException e) {
            this.setIdCrop(-1);
        }
//        System.out.println("idCropReq->"+this.getRequest().);
//        System.out.println("idCrop->"+idCrop);
//        System.out.println("page->"+this.getRequest().getParameterNames().toString());
        if (this.getIdCrop()!= -1) {
            setValuesCrop(this.getIdCrop());
            
            HashMap findParams = new HashMap();       
            findParams.put("idEvent", this.getIdCrop());       
            findParams.put("idEntUser", idEntSystem);       
            listPrep = prepDao.findByParams(findParams);
            listIrr  = irrDao.findByParams(findParams);
            listFert = fertDao.findByParams(findParams);
            listCont = conDao.findByParams(findParams);
            listMont = monDao.findByParams(findParams);
            
            beans  = beansDao.objectById(this.getIdCrop());
            cass   = cassDao.objectById(this.getIdCrop());
            harv   = harDao.objectById(this.getIdCrop());
            maize  = maizeDao.objectById(this.getIdCrop());
            phys   = physDao.objectById(this.getIdCrop());
            sowing = sowDao.objectById(this.getIdCrop());
            
//            harv.setIdHar(12);
//            List<Genotypes> event = null;
//            System.out.println("values=>"+typeCrop);
//            System.out.println("values12=>"+beans.getGrowingEnvironment().getIdGroEnv());
            this.setType_genotypes(new GenotypesDao().findAllByTypeCrop(typeCrop, 0));
            if(sowing!=null && sowing.getIdSow()!=0) {
                if(typeCrop==1 && maize.getSeedsColors()!=null) {
                    this.setType_genotypes(new GenotypesDao().findAllByTypeCrop(typeCrop, maize.getSeedsColors().getIdSeeCol()));
                } else if(typeCrop==2 && beans.getGrowingEnvironment()!=null) {
                    this.setType_genotypes(new GenotypesDao().findAllByTypeCrop(typeCrop, beans.getGrowingEnvironment().getIdGroEnv()));
                } else if(typeCrop==3) {
                    this.setType_genotypes(new GenotypesDao().findAllByTypeCrop(typeCrop, 0));
                }       
                 
            }
            
            HashMap fieldInfo = lotDao.findById(this.getIdField());
            
//            Nombre del Productor: Juan Felipe Rodriguez nameProducer
//            Cedula del Productor: 1130668332 docProducer
//            Nombre de la finca: La Poderosa typeDocProducer
//            Nombre del Lote: Esto es lo mio nameField
//            System.out.println("values->"+fieldInfo);
            
            this.setName_producer(String.valueOf(fieldInfo.get("name_producer")));
            String numDocTemp = String.valueOf(fieldInfo.get("no_doc_pro"));
            if (!numDocTemp.equals("null")) this.setNum_doc(Integer.parseInt(numDocTemp));
            this.setType_doc(String.valueOf(fieldInfo.get("type_doc_pro")));
            this.setName_farm(String.valueOf(fieldInfo.get("name_farm")));
            
            this.setType_chem_sow(new ChemicalsSowingDao().findAllByTypeCrop(typeCrop));
            this.setType_dose_units(new DoseUnitsDao().findAll());            
            this.setType_genotypes_sow(new GenotypesSowingDao().findAllByTypeCrop(typeCrop));
            this.setType_grow_env(new GrowingEnvironmentDao().findAll());
            this.setType_seed_in(new SeedsInoculationsDao().findAll());            
            this.setType_harv_meth(new HarvestMethodsDao().findAll());
            this.setType_res_pro(new ResultingProductsDao().findAllByTypeCrop(typeCrop));
            this.setType_seed_color(new SeedsColorsDao().findAll());
            this.setType_seed_org(new SeedsOriginsDao().findAllByTypeCrop(typeCrop));
            this.setType_seed_type(new SeedsTypesDao().findAll());           
            this.setType_sow_types(new SowingTypesDao().findAll());           
            
        }       
        return SUCCESS;
    }
    
    /**
     * Se obtiene la lista de materiales geneticos a partir de un color del endospermo seleccionado previamente
     * @param idColor: Identificacion de color del endospermo
     * @return lista de materiales geneticos
     */
    public String comboGetListGenByCol() {
        String chain = "<option value=\"\">---</option>";
        if(!this.getRequest().getParameter("idColor").equals("-1")) {
            int idColor    = Integer.parseInt(this.getRequest().getParameter("idColor"));
            int typeCropId = Integer.parseInt(this.getRequest().getParameter("typeCrop"));
            
            type_genotypes = new GenotypesDao().findAllByTypeCrop(typeCropId, idColor);
//            MunicipalitiesDao eventDao = new MunicipalitiesDao();
//            city_producer = eventDao.findAll(depId);            
            for (Genotypes data : type_genotypes) {
    //            System.out.println(data.toString());
                chain += "<option value=\"" + data.getIdGen()+ "\">" + data.getNameGen()+ "</option>";
            }
        }
        type_genotypes = null;
        state = "success";
        info  = chain;
        return "combo";
    }
    
    /**
     * Se obtiene la lista de materiales geneticos a partir de un habito de crecimiento seleccionado previamente
     * @param idGrow: Identificacion del habito de crecimiento
     * @return lista de materiales geneticos
     */
    public String comboGetListGenByGrowing() {
        String chain = "<option value=\"\">---</option>";
        if(!this.getRequest().getParameter("idGrow").equals("-1")) {
            int idGrow     = Integer.parseInt(this.getRequest().getParameter("idGrow"));
            int typeCropId = Integer.parseInt(this.getRequest().getParameter("typeCrop"));
            
            type_genotypes = new GenotypesDao().findAllByTypeCrop(typeCropId, idGrow);
            for (Genotypes data : type_genotypes) {
    //            System.out.println(data.toString());
                chain += "<option value=\"" + data.getIdGen()+ "\">" + data.getNameGen()+ "</option>";
            }
        }
        type_genotypes = null;
        state = "success";
        info = chain;
        return "combo";
    }

    /**
     * Encargado de buscar las coincidencias de un formulario de busqueda, para cada uno de los
     * cultivos registrados a un usuario
     * @param valName:  Nombre del valor a buscar
     * @param valId:    Identificacion del valor a buscar
     * @param selected: Valor seleccionado
     * @return lista de cultivos
     */
    public String search() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "crop/list")) {
            return BaseAction.NOT_AUTHORIZED;
        }
        valName     = (String)(this.getRequest().getParameter("valName"));
        valId       = (String)(this.getRequest().getParameter("valId"));
        selected    = (String)(this.getRequest().getParameter("selected"));
        if(selected==null) selected="crop";
        
        additionals = new HashMap();
        additionals.put("selected", selected);
        HashMap findParams = new HashMap();
        
        if(searchFrom!=null && searchFrom==2) {
            search_crop = "";
        } 
        
        findParams.put("idEntUser", idEntSystem);
        findParams.put("search_crop", search_crop);
        findParams.put("name_producer", name_producer);
        findParams.put("idCrop", idCrop);
        findParams.put("type_doc", type_doc);
        findParams.put("num_doc", num_doc);
        findParams.put("num_farm", num_farm);
        findParams.put("name_farm", name_farm);
        findParams.put("num_field", num_field);
        findParams.put("name_field", name_field);
        
        int pageReq;
        if (this.getRequest().getParameter("page") == null) {
            pageReq = this.getPage();
        } else {
            pageReq = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter("page")));
        }
        findParams.put("pageNow", pageReq);
        findParams.put("maxResults", this.getMaxResults());
        listCrops = cropDao.findByParams(findParams);
        this.setCountTotal(Integer.parseInt(String.valueOf(listCrops.get(0).get("countTotal"))));
        this.setPage(page);
        listCrops.remove(0);
//        System.out.println("countTotal->"+this.getCountTotal());
        return SUCCESS;
    }
    

    /**
     * Encargado de mostrar en un formulario la informacion de un cultivo apartir de la identificacion
     * @param idCrop:  Identificacion del cultivo
     * @return Informacion del cultivo
     */
    public String show() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "crop/create") || !usrDao.getPrivilegeUser(idUsrSystem, "crop/modify")) {
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
            this.setIdCrop(Integer.parseInt(this.getRequest().getParameter("idCrop")));
        } catch (NumberFormatException e) {
//            LOG.error("There was an error trying to parse the activityId parameter");
            this.setIdCrop(-1);
        }

        this.setType_ident_producer(new DocumentsTypesDao().findAll());
        this.setType_crops(new CropsTypesDao().findAll());
        if (this.getIdCrop()!= -1) {
            setValuesCrop(this.getIdCrop());
            
//            Fields fie = lotDao.objectById(idField);
//            nameField  = fie.getNameFie();
        } 
        return SUCCESS;
    }    

    /**
     * Encargado de guardar la informacion suministrada por el usuario para un cultivo
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
//        info = "El cultivo ha sido modificado con exito";
        

        try {
            tx = session.beginTransaction();
            
            ProductionEvents pro = null;
            if (idCrop<=0) {
                pro = new ProductionEvents();
                pro.setIdProEve(null);
            } else {
                pro = cropDao.objectById(idCrop);
            }                        
            
//            double performDou = (performObj.equals("")) ? 0.0 : Double.parseDouble(performObj.replace(',','.'));
            double performDou = (performObj.equals("")) ? 0.0 : Double.parseDouble(performObj);
            
            pro.setFields(new Fields(idField));
            pro.setCropsTypes(new CropsTypes(typeCrop));
            pro.setIdProjectProEve(1);
            pro.setExpectedProductionProEve(performDou);
            pro.setFormerCropProEve(lastCrop);
            pro.setDrainingProEve(drainPlot);
            pro.setStatus(true);
            session.saveOrUpdate(pro);
            
//            entDao.save(ent);          
//            System.out.println("pruebaCrea");      
            
            /*if (action.equals("C")) {        
                if (typeCrop==1) {
                    Maize ma = new Maize();
                    ma.setIdMai(null);
                    ma.setProductionEvents(pro);
                    session.saveOrUpdate(ma);            
                } else if (typeCrop==2) {
                    Beans be = new Beans();
                    be.setIdBea(null);
                    be.setProductionEvents(pro);
                } else if (typeCrop==3) {
                    Cassavas ca = new Cassavas();
                    ca.setIdCas(null);
                    ca.setProductionEvents(pro);
                }
            }*/
            
            if (action.equals("M")) {
                if (idCrop>0) {
                    beans  = beansDao.objectById(idCrop);
                    cass   = cassDao.objectById(idCrop);
                    maize  = maizeDao.objectById(idCrop);
                    if (typeCrop==1) {
                        if (beans!=null) session.delete(beans);
                        if (cass!=null) session.delete(cass);                    
                    } else if (typeCrop==2) {
                        if (cass!=null) session.delete(cass);
                        if (maize!=null) session.delete(maize);
                    } else if (typeCrop==3) {
                        if (maize!=null) session.delete(maize);
                        if (beans!=null) session.delete(beans);
                    }
                }
            }
            
            if (typeCrop==1) {
                Maize ma = new Maize();
                ma.setIdMai(null);
                ma.setProductionEvents(pro);
                session.saveOrUpdate(ma);            
            } else if (typeCrop==2) {
                Beans be = new Beans();
                be.setIdBea(null);
                be.setProductionEvents(pro);
                session.saveOrUpdate(be);
            } else if (typeCrop==3) {
                Cassavas ca = new Cassavas();
                ca.setIdCas(null);
                ca.setProductionEvents(pro);
                session.saveOrUpdate(ca);
            }
            
            beans = null;
            cass  = null;
            maize = null;
            
            LogEntities log = new LogEntities();
            log.setIdLogEnt(null);
            log.setIdEntityLogEnt(idEntSystem);
            log.setIdObjectLogEnt(pro.getIdProEve());
            log.setTableLogEnt("production_events");
            log.setDateLogEnt(new Date());
            log.setActionTypeLogEnt(action);
            session.saveOrUpdate(log);
            idCrop = pro.getIdProEve();
            tx.commit();           
            state = "success";            
            if (action.equals("C")) {
                info  = "El cultivo ha sido agregado con exito";
//                return "list";
            } else if (action.equals("M")) {
                info  = "El cultivo ha sido modificado con exito";
//                return "list";
            }
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
//            System.out.println("error->"+e.getMessage());
            state = "failure";
            info  = "Fallo al momento de agregar un cultivo";
        } finally {
            session.close();
        }  
        
//        return ERROR;
        return "states";
    }

    /**
     * Encargado de borrar la informacion de un cultivo apartir de su identificacion
     * @param idCrop:  Identificacion del cultivo
     * @return Estado del proceso
     */
    public String delete() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "crop/delete")) {
            return BaseAction.NOT_AUTHORIZED;
        }
        Integer idCrop = 0;
        try {
            idCrop = Integer.parseInt(this.getRequest().getParameter("idCrop"));
        } catch (NumberFormatException e) {
            idCrop = -1;
        }
        
        if (idCrop==-1) {
            state = "failure";
            info  = "Fallo al momento de obtener la informacion a borrar";
            return "states";
        }
        
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();            
            ProductionEvents pro = cropDao.objectById(idCrop);      
            pro.setStatus(false);
//            session.delete(pro);        
            session.saveOrUpdate(pro);
            
            LogEntities log = new LogEntities();
            log.setIdLogEnt(null);
            log.setIdEntityLogEnt(idEntSystem); //Colocar el usuario registrado en el sistema
            log.setIdObjectLogEnt(pro.getIdProEve());
            log.setTableLogEnt("production_events");
            log.setDateLogEnt(new Date());
            log.setActionTypeLogEnt("D");
            session.saveOrUpdate(log);
//            logDao.save(log);
            tx.commit();         
            state = "success";
            info  = "El cultivo ha sido borrado con exito";
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            state = "failure";
            info  = "Fallo al momento de borrar un cultivo";
        } finally {
            session.close();
        }      
        
        return "states";
//        return SUCCESS;
    }
}