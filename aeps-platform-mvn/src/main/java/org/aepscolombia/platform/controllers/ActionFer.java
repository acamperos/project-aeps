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
import java.util.List;
import org.aepscolombia.platform.models.dao.AmendmentsFertilizationsDao;
import org.aepscolombia.platform.models.dao.AmendmentsFertilizersDao;
import org.aepscolombia.platform.models.dao.ApplicationTypesDao;
import org.aepscolombia.platform.models.dao.ChemicalElementsDao;
import org.aepscolombia.platform.models.dao.ChemicalFertilizationsDao;
import org.aepscolombia.platform.models.dao.ChemicalFertilizersDao;
import org.aepscolombia.platform.models.dao.FertilizationsDao;
import org.aepscolombia.platform.models.dao.FertilizationsTypesDao;

import org.aepscolombia.platform.models.dao.LogEntitiesDao;
import org.aepscolombia.platform.models.dao.OrganicFertilizationsDao;
import org.aepscolombia.platform.models.dao.OrganicFertilizersDao;
import org.aepscolombia.platform.models.dao.ProductionEventsDao;
import org.aepscolombia.platform.models.dao.SowingDao;
import org.aepscolombia.platform.models.dao.UsersDao;
import org.aepscolombia.platform.models.entity.AmendmentsFertilizations;
import org.aepscolombia.platform.models.entity.AmendmentsFertilizers;
import org.aepscolombia.platform.models.entity.ApplicationTypes;
import org.aepscolombia.platform.models.entity.ChemicalElements;
import org.aepscolombia.platform.models.entity.ChemicalFertilizations;
import org.aepscolombia.platform.models.entity.ChemicalFertilizerComposition;
import org.aepscolombia.platform.models.entity.ChemicalFertilizers;
import org.aepscolombia.platform.models.entity.Fertilizations;
import org.aepscolombia.platform.models.entity.FertilizationsTypes;

import org.aepscolombia.platform.models.entity.LogEntities;
import org.aepscolombia.platform.models.entity.ProductionEvents;
import org.aepscolombia.platform.models.entity.OrganicFertilizations;
import org.aepscolombia.platform.models.entity.OrganicFertilizers;
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
 * Clase ActionFer
 *
 * Contiene los metodos para interactuar con el modulo de fertilizacion
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ActionFer extends BaseAction {
    
    //Atributos del formulario 
    /**
     * Atributos provenientes del formulario
     */
    private int idCrop;    
    private int idFer;    
    private int typeCrop;
    private List<HashMap> listFert;
    private Users user;
    private Integer idEntSystem;    
    private Integer idUsrSystem;    

    private Fertilizations fer = new Fertilizations();
    private ChemicalFertilizations ferChe = new ChemicalFertilizations();
    private OrganicFertilizations ferOrg = new OrganicFertilizations();
    private AmendmentsFertilizations ferAme = new AmendmentsFertilizations();
    private Sowing sowing = new Sowing();
    private List<FertilizationsTypes> type_fer_typ;
    private List<ApplicationTypes> list_app_typ;
    private List<ChemicalFertilizers> type_prod_che;
    private List<OrganicFertilizers> type_prod_org;
    private List<AmendmentsFertilizers> type_prod_ame;
    
    private Double amountProductUsedChe=null;
    private Double amountProductUsedOrg=null;
    private Double amountProductUsedAme=null;
    private UsersDao usrDao;

    //Metodos getter y setter por cada variable del formulario 
    /**
     * Metodos getter y setter por cada variable del formulario
     */
    public int getIdFer() {
        return idFer;
    }

    public void setIdFer(int idFer) {
        this.idFer = idFer;
    }  

    public Double getAmountProductUsedChe() {
        return amountProductUsedChe;
    }

    public void setAmountProductUsedChe(Double amountProductUsedChe) {
        this.amountProductUsedChe = amountProductUsedChe;
    }

    public Double getAmountProductUsedOrg() {
        return amountProductUsedOrg;
    }

    public void setAmountProductUsedOrg(Double amountProductUsedOrg) {
        this.amountProductUsedOrg = amountProductUsedOrg;
    }

    public Double getAmountProductUsedAme() {
        return amountProductUsedAme;
    }

    public void setAmountProductUsedAme(Double amountProductUsedAme) {
        this.amountProductUsedAme = amountProductUsedAme;
    }

    public Sowing getSowing() {
        return sowing;
    }

    public void setSowing(Sowing sowing) {
        this.sowing = sowing;
    }      

    public Fertilizations getFer() {
        return fer;
    }

    public void setFer(Fertilizations fer) {
        this.fer = fer;
    }

    public ChemicalFertilizations getFerChe() {
        return ferChe;
    }

    public void setFerChe(ChemicalFertilizations ferChe) {
        this.ferChe = ferChe;
    }

    public OrganicFertilizations getFerOrg() {
        return ferOrg;
    }

    public void setFerOrg(OrganicFertilizations ferOrg) {
        this.ferOrg = ferOrg;
    }

    public AmendmentsFertilizations getFerAme() {
        return ferAme;
    }

    public void setFerAme(AmendmentsFertilizations ferAme) {
        this.ferAme = ferAme;
    }

    public List<FertilizationsTypes> getType_fer_typ() {
        return type_fer_typ;
    }

    public void setType_fer_typ(List<FertilizationsTypes> type_fer_typ) {
        this.type_fer_typ = type_fer_typ;
    }

    public List<ApplicationTypes> getList_app_typ() {
        return list_app_typ;
    }

    public void setList_app_typ(List<ApplicationTypes> list_app_typ) {
        this.list_app_typ = list_app_typ;
    }
    
    public List<ChemicalFertilizers> getType_prod_che() {
        return type_prod_che;
    }

    public void setType_prod_che(List<ChemicalFertilizers> type_prod_che) {
        this.type_prod_che = type_prod_che;
    }     

    public List<OrganicFertilizers> getType_prod_org() {
        return type_prod_org;
    }

    public void setType_prod_org(List<OrganicFertilizers> type_prod_org) {
        this.type_prod_org = type_prod_org;
    }

    public List<AmendmentsFertilizers> getType_prod_ame() {
        return type_prod_ame;
    }

    public void setType_prod_ame(List<AmendmentsFertilizers> type_prod_ame) {
        this.type_prod_ame = type_prod_ame;
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

    public List<HashMap> getListFert() {
        return listFert;
    }   
    
    //Atributos generales de clase
    /**
     * Atributos generales de clase
     */
    
    private ProductionEventsDao cropDao    = new ProductionEventsDao();
    private FertilizationsDao ferDao       = new FertilizationsDao();
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
    
    private List<ChemicalElements> additionalsElem;

    public List<ChemicalElements> getAdditionalsElem() {
        return additionalsElem;
    }

    public void setAdditionalsElem(List<ChemicalElements> additionalsElem) {
        this.additionalsElem = additionalsElem;
    }
    
    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }       
    
    @Override
    public void prepare() throws Exception {
        user = (Users) this.getSession().get(APConstants.SESSION_USER);
        idEntSystem = UsersDao.getEntitySystem(user.getIdUsr());  
        usrDao =  new UsersDao();
        idUsrSystem = user.getIdUsr();
    }
    
    
    /**
     * Metodo encargado de validar el formulario de una fertilizacion
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
            required.put("fer.dateFer", fer.getDateFer());      
            required.put("fer.fertilizationsTypes.idFerTyp", fer.getFertilizationsTypes().getIdFerTyp());                  
            
            if (fer.getFertilizationsTypes().getIdFerTyp() == 1) {
                required.put("ferChe.chemicalFertilizers.idCheFer", ferChe.getChemicalFertilizers().getIdCheFer());
                required.put("amountProductUsedChe", amountProductUsedChe);
                required.put("ferChe.unitCheFer", ferChe.getUnitCheFer());
                required.put("ferChe.applicationTypes.idAppTyp", ferChe.getApplicationTypes().getIdAppTyp());
                if (ferChe.getChemicalFertilizers().getIdCheFer() == 1000000) {
                    required.put("ferChe.otherProductCheFer", ferChe.getOtherProductCheFer());
                }
            }

            if (fer.getFertilizationsTypes().getIdFerTyp() == 2) {
                required.put("ferOrg.organicFertilizers.idOrgFer", ferOrg.getOrganicFertilizers().getIdOrgFer());
                required.put("amountProductUsedOrg", amountProductUsedOrg);
                if (ferOrg.getOrganicFertilizers().getIdOrgFer() == 1000000) {
                  required.put("ferOrg.otherProductOrgFer", ferOrg.getOtherProductOrgFer());
                }
            }

            if (fer.getFertilizationsTypes().getIdFerTyp() == 3) {
                required.put("ferAme.amendmentsFertilizers.idAmeFer", ferAme.getAmendmentsFertilizers().getIdAmeFer());
                required.put("amountProductUsedAme", amountProductUsedAme);
                if (ferAme.getAmendmentsFertilizers().getIdAmeFer() == 1000000) {
                  required.put("ferAme.otherProductAmeFer", ferAme.getOtherProductAmeFer());
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
            
            Date dateSowing = null;
//            if (sowing.getDateSow()!=null) {
            if (sowing != null) {
                dateSowing = sowing.getDateSow();
                String dmySow  = new SimpleDateFormat("dd/MM/yyyy").format(sowing.getDateSow());
		
                if (!dmySow.equals("") && fer.getDateFer()!=null) {

                    Integer valDiffBef = GlobalFunctions.compareDateBeforeSowing(fer.getDateFer(), sowing.getDateSow());
                    Integer valDiffAff = GlobalFunctions.compareDateAfterSowing(fer.getDateFer(), sowing.getDateSow(), 0);
                    if (valDiffBef==2 && valDiffAff==2) {
                        addFieldError("fer.dateFer", "Dato invalido");                
                        addActionError("Se ingreso una fecha de aplicación que no se encuentra 6 meses antes de la siembra o 10 meses despues de la siembra ("+dmySow+")");
                    }				

                }	
            }
            
            if (fer.getFertilizationsTypes()!=null && ferChe.getChemicalFertilizers()!=null) {
                if (fer.getFertilizationsTypes().getIdFerTyp() == 1 && ferChe.getChemicalFertilizers().getIdCheFer() == 1000000) {
                    Boolean entry = false;
                    Boolean errorCom = false;
                    int cont = 0;
                    for (ChemicalElements chem : additionalsElem) {
                        if (chem.getValueCheEle()!=null) {
                            entry = true;					
                        }				

                        if (chem.getValueCheEle()!=null && (chem.getValueCheEle()<0 || chem.getValueCheEle()>100)) {
                            addFieldError("additionalsElem["+cont+"].valueCheEle", "Dato invalido");
                            errorCom = true;					
                        }
                        cont++;
                    }		

                    if (errorCom) {
                        addActionError("Se ingresaron composiciones invalidas, por favor ingresar valores entre 0 y 100");
                    }

                    if (!entry) {
                        for (int i=0; i<additionalsElem.size(); i++) {                    
                            addFieldError("additionalsElem["+i+"].valueCheEle", "");            
                        }
                        addActionError("Se debe ingresar por lo menos alguna de las composiciones");
                    }
                }
            }           
            sowing=null;            
        }
    }     
    
    /**
     * Encargado de buscar las coincidencias de un formulario de busqueda, para cada uno de las
     * fertilizaciones registrados a un usuario
     * @param valName:  Nombre del valor a buscar
     * @param valId:    Identificacion del valor a buscar
     * @param selected: Valor seleccionado
     * @return lista de fertilizaciones
     */
    public String search() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "crop/list")) {
            return BaseAction.NOT_AUTHORIZED;
        }
        try {
            this.setIdCrop(Integer.parseInt(this.getRequest().getParameter("idCrop")));
        } catch (NumberFormatException e) {
//            LOG.error("There was an error trying to parse the activityId parameter");
            this.setIdCrop(-1);
        }
        
        HashMap findParams = new HashMap();        
        findParams.put("idEntUser", idEntSystem);
        findParams.put("idEvent", this.getIdCrop());
        listFert = ferDao.findByParams(findParams);
        return SUCCESS;
    }
    

    /**
     * Encargado de mostrar en un formulario la informacion de una fertilizacion de la identificacion
     * @param idFer:  Identificacion de la fertilizacion
     * @return Informacion de la fertilizacion
     */
    public String show() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "crop/create") || !usrDao.getPrivilegeUser(idUsrSystem, "crop/modify")) {
            return BaseAction.NOT_AUTHORIZED;
        }
        actExe = (String)(this.getRequest().getParameter("action"));
        try {
            this.setIdCrop(Integer.parseInt(this.getRequest().getParameter("idCrop")));
        } catch (NumberFormatException e) {
//            LOG.error("There was an error trying to parse the activityId parameter");
            this.setIdCrop(-1);
        }
        
        HashMap prod  = cropDao.findById(idCrop);
        Integer tyCro = Integer.parseInt(String.valueOf(prod.get("typeCrop")));
//        System.out.println("tyCro=>"+tyCro);
        
        try {
            this.setIdFer(Integer.parseInt(this.getRequest().getParameter("idFer")));
        } catch (NumberFormatException e) {
//            LOG.error("There was an error trying to parse the activityId parameter");
            this.setIdFer(-1);
        }

        this.setType_fer_typ(new FertilizationsTypesDao().findAll());
        this.setList_app_typ(new ApplicationTypesDao().findAll());
        this.setType_prod_che(new ChemicalFertilizersDao().findAllByStatus());
        this.setType_prod_org(new OrganicFertilizersDao().findAllByStatus());
        this.setType_prod_ame(new AmendmentsFertilizersDao().findAllByStatus());
        additionalsElem = new ChemicalElementsDao().findByParams(this.getIdFer());
        if (this.getIdFer()!= -1) {
            fer    = ferDao.objectById(this.getIdFer());
            ferChe = new ChemicalFertilizationsDao().objectById(this.getIdFer());            
            ferOrg = new OrganicFertilizationsDao().objectById(this.getIdFer());            
            ferAme = new AmendmentsFertilizationsDao().objectById(this.getIdFer());   
            amountProductUsedChe = fer.getAmountProductUsedFer();
            amountProductUsedOrg = fer.getAmountProductUsedFer();
            amountProductUsedAme = fer.getAmountProductUsedFer(); 
            if (ferChe!=null && ferChe.getOtherProductCheFer()!=null && !ferChe.getOtherProductCheFer().equals("")) ferChe.setChemicalFertilizers(new ChemicalFertilizers(1000000, "Otro"));
            if (ferOrg!=null && ferOrg.getOtherProductOrgFer()!=null && !ferOrg.getOtherProductOrgFer().equals("")) ferOrg.setOrganicFertilizers(new OrganicFertilizers(1000000, "Otro"));
            if (ferAme!=null && ferAme.getOtherProductAmeFer()!=null && !ferOrg.getOtherProductOrgFer().equals("")) ferAme.setAmendmentsFertilizers(new AmendmentsFertilizers(1000000, "Otro"));
        } 
        return SUCCESS;
    }

    /**
     * Encargado de guardar la informacion suministrada por el usuario para una fertilizacion
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

        try {
            tx = session.beginTransaction();           
            
            String dmy   = new SimpleDateFormat("yyyy-MM-dd").format(fer.getDateFer());
            Date dateFer = new SimpleDateFormat("yyyy-MM-dd").parse(dmy);
            
            fer.setProductionEvents(new ProductionEvents(idCrop));
            fer.setDateFer(dateFer);          
            if (fer.getFertilizationsTypes().getIdFerTyp()==1) {
                fer.setAmountProductUsedFer(amountProductUsedChe);
            } else if (fer.getFertilizationsTypes().getIdFerTyp()==2) {
                fer.setAmountProductUsedFer(amountProductUsedOrg);
            } else if (fer.getFertilizationsTypes().getIdFerTyp()==3) {
                fer.setAmountProductUsedFer(amountProductUsedAme);
            }            
            
//            if (sowing.getChemicalsSowing().getIdCheSow()==-1) {
//                sowing.setChemicalsSowing(null);
//            }            
//            if (sowing.getDoseUnits().getIdDosUni()==-1) {
//                sowing.setDoseUnits(null);
//            }        
            fer.setStatus(true);
            session.saveOrUpdate(fer);
            
            if (action.equals("M")) {
                if (fer.getIdFer()>0) {
                    ChemicalFertilizations ferCheTemp  = new ChemicalFertilizationsDao().objectById(this.getIdFer());
                    OrganicFertilizations ferOrgTemp  = new OrganicFertilizationsDao().objectById(this.getIdFer());
                    AmendmentsFertilizations ferAmeTemp  = new AmendmentsFertilizationsDao().objectById(this.getIdFer());
                    if (fer.getFertilizationsTypes().getIdFerTyp()==1) {
                        if (ferOrgTemp!=null) session.delete(ferOrgTemp);
                        if (ferAmeTemp!=null) session.delete(ferAmeTemp);                    
                    } else if (fer.getFertilizationsTypes().getIdFerTyp()==2) {
                        if (ferCheTemp!=null) session.delete(ferCheTemp);
                        if (ferAmeTemp!=null) session.delete(ferAmeTemp);
                    } else if (fer.getFertilizationsTypes().getIdFerTyp()==3) {
                        if (ferCheTemp!=null) session.delete(ferCheTemp);
                        if (ferOrgTemp!=null) session.delete(ferOrgTemp);
                    }
                }
            }
            
            if (fer.getFertilizationsTypes().getIdFerTyp()==1) {
                ChemicalFertilizers cheFer = null;
                if (ferChe.getOtherProductCheFer()!=null && !ferChe.getOtherProductCheFer().equals("")) {
                    cheFer = new ChemicalFertilizersDao().objectById(fer.getIdFer());
                    if (cheFer!=null) session.delete(cheFer);     

                    cheFer = new ChemicalFertilizers();
                    cheFer.setNameCheFer(ferChe.getOtherProductCheFer());
                    cheFer.setStatusCheFer(true);
                    session.saveOrUpdate(cheFer);
                    
                    ferChe.setChemicalFertilizers(cheFer);

                    for (ChemicalElements elem : additionalsElem) {
                        if (elem.getValueCheEle()!=null) {
                            ChemicalFertilizerComposition chem = new ChemicalFertilizerComposition();
                            chem.setChemicalElements(elem);
                            chem.setChemicalFertilizers(cheFer);
                            chem.setPercentageCheFerCom(elem.getValueCheEle());
                            Double quant = (fer.getAmountProductUsedFer()*elem.getValueCheEle())/100;		

                            chem.setRawElementQuantityCheFerCom(quant);
                            session.saveOrUpdate(chem);
                        }
                    }
                }                
                
                ferChe.setFertilizations(fer);
                ferChe.setStatus(true);
                session.saveOrUpdate(ferChe);            
            } else if (fer.getFertilizationsTypes().getIdFerTyp()==2) {
                ferOrg.setFertilizations(fer);
                ferOrg.setStatus(true);
                if (ferOrg.getOtherProductOrgFer()!=null && !ferOrg.getOtherProductOrgFer().equals("")) {
                    ferOrg.setOrganicFertilizers(null);
                }
                session.saveOrUpdate(ferOrg);      
            } else if (fer.getFertilizationsTypes().getIdFerTyp()==3) {
                ferAme.setFertilizations(fer);
                ferAme.setStatus(true);
                if (ferAme.getOtherProductAmeFer()!=null && !ferAme.getOtherProductAmeFer().equals("")) {
                    ferAme.setAmendmentsFertilizers(null);
                }
                session.saveOrUpdate(ferAme);      
            }     
            
            LogEntities log = new LogEntities();
            log.setIdLogEnt(null);
            log.setIdEntityLogEnt(idEntSystem);
            log.setIdObjectLogEnt(fer.getIdFer());
            log.setTableLogEnt("fertilizations");
            log.setDateLogEnt(new Date());
            log.setActionTypeLogEnt(action);
            session.saveOrUpdate(log);
            tx.commit();           
            state = "success";            
            if (action.equals("C")) {
                info  = "La fertilizacion ha sido agregada con exito";
//                return "list";
            } else if (action.equals("M")) {
                info  = "La fertilizacion ha sido modificada con exito";
//                return "list";
            }
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
//            System.out.println("error->"+e.getMessage());
            state = "failure";
            info  = "Fallo al momento de agregar una fertilizacion";
        } catch (ParseException e) { 
        
        } finally {
            session.close();
        }  
        
//        return ERROR;
        return "states";
    }
    
    /**
     * Encargado de borrar la informacion de una fertilizacion apartir de su identificacion
     * @param idFer:  Identificacion de la fertilizacion
     * @return Estado del proceso
     */
    public String delete() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "crop/delete")) {
            return BaseAction.NOT_AUTHORIZED;
        }
        Integer idFer = 0;
        try {
            idFer = Integer.parseInt(this.getRequest().getParameter("idFer"));
        } catch (NumberFormatException e) {
            idFer = -1;
        }
        
        if (idFer==-1) {
            state = "failure";
            info  = "Fallo al momento de obtener la informacion a borrar";
            return "states";
        }
        
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();            
            Fertilizations pr = ferDao.objectById(idFer);      
            pr.setStatus(false);
//            session.delete(pro);        
            session.saveOrUpdate(pr);
            
            LogEntities log = new LogEntities();
            log.setIdLogEnt(null);
            log.setIdEntityLogEnt(idEntSystem);
            log.setIdObjectLogEnt(pr.getIdFer());
            log.setTableLogEnt("fertilizations");
            log.setDateLogEnt(new Date());
            log.setActionTypeLogEnt("D");
            session.saveOrUpdate(log);
//            logDao.save(log);
            tx.commit();         
            state = "success";
            info  = "La fertilizacion ha sido borrada con exito";
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            state = "failure";
            info  = "Fallo al momento de borrar una fertilizacion";
        } finally {
            session.close();
        }      
        
        return "states";
//        return SUCCESS;
    }
    
}