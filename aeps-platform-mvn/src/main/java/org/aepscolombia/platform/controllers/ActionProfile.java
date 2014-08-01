/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aepscolombia.platform.controllers;

//import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.ActionContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.aepscolombia.platform.models.dao.DepartmentsDao;
import org.aepscolombia.platform.models.dao.DocumentsTypesDao;
import org.aepscolombia.platform.models.dao.EntitiesDao;
import org.aepscolombia.platform.models.dao.FarmsDao;
import org.aepscolombia.platform.models.dao.FieldsDao;
import org.aepscolombia.platform.models.dao.LogEntitiesDao;
import org.aepscolombia.platform.models.dao.MunicipalitiesDao;
import org.aepscolombia.platform.models.dao.ProducersDao;
import org.aepscolombia.platform.models.dao.ProductionEventsDao;
import org.aepscolombia.platform.models.dao.RastasDao;
import org.aepscolombia.platform.models.dao.UsersDao;
import org.aepscolombia.platform.models.dao.UserEntityDao;
import org.aepscolombia.platform.models.dao.UsersProfilesDao;
import org.aepscolombia.platform.models.entity.Departments;
import org.aepscolombia.platform.models.entity.DocumentsTypes;
import org.aepscolombia.platform.models.entity.Entities;
import org.aepscolombia.platform.models.entity.LogEntities;
import org.aepscolombia.platform.models.entity.Municipalities;
import org.aepscolombia.platform.models.entity.Users;
import org.aepscolombia.platform.util.APConstants;
import org.aepscolombia.platform.util.GlobalFunctions;
import org.aepscolombia.platform.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase ActionLogin
 *
 * Contiene los metodos necesarios al momento de identificar un usuario o crear uno nuevo
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ActionProfile extends BaseAction {
    
    private static final Logger LOG = LoggerFactory.getLogger(ActionProfile.class);
    private static final long serialVersionUID = -890122014241894430L;
    private Users user;
    private Entities ent;
    private UsersDao userDao       = new UsersDao();
    private EntitiesDao entDao     = new EntitiesDao();
    private LogEntitiesDao logDao  = new LogEntitiesDao();
    private UserEntityDao usrEntDao;
    private UsersProfilesDao usrPerDao;
    
    //Datos de acceso al sistema
    
    private String emailUser;
    private String celphoneUser;
    private Integer noRecords;
    private Boolean changePass;
    private String passActual;
    private String newPass;
    private String confirmNewPass;

    private String typeDocument;
    private String noDocument;
    private String firstName;
    private String secondName;
    private String firstLastName;
    private String secondLastName;
    private Integer department;
    private Integer municipality;
    private List<DocumentsTypes> type_ident_producer;
    private List<Departments> department_producer;
    private List<Municipalities> city_producer;
    private Integer idEntSystem;
    private String nameCompany;                            
    private String firstNameRep;
    private String secondNameRep;
    private String firstLastNameRep;
    private String secondLastNameRep;
    private Integer digVer;
    private Integer numPro;
    private Integer numFar;
    private Integer numRas;
    private Integer numFie;
    private Integer numEve;

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getCelphoneUser() {
        return celphoneUser;
    }

    public void setCelphoneUser(String celphoneUser) {
        this.celphoneUser = celphoneUser;
    }   

    public Integer getNoRecords() {
        return noRecords;
    }

    public void setNoRecords(Integer noRecords) {
        this.noRecords = noRecords;
    }

    public Integer getNumPro() {
        return numPro;
    }

    public void setNumPro(Integer numPro) {
        this.numPro = numPro;
    }

    public Integer getNumFar() {
        return numFar;
    }

    public void setNumFar(Integer numFar) {
        this.numFar = numFar;
    }

    public Integer getNumRas() {
        return numRas;
    }

    public void setNumRas(Integer numRas) {
        this.numRas = numRas;
    }

    public Integer getNumFie() {
        return numFie;
    }

    public void setNumFie(Integer numFie) {
        this.numFie = numFie;
    }

    public Integer getNumEve() {
        return numEve;
    }

    public void setNumEve(Integer numEve) {
        this.numEve = numEve;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public String getFirstNameRep() {
        return firstNameRep;
    }

    public void setFirstNameRep(String firstNameRep) {
        this.firstNameRep = firstNameRep;
    }

    public String getSecondNameRep() {
        return secondNameRep;
    }

    public void setSecondNameRep(String secondNameRep) {
        this.secondNameRep = secondNameRep;
    }

    public String getFirstLastNameRep() {
        return firstLastNameRep;
    }

    public void setFirstLastNameRep(String firstLastNameRep) {
        this.firstLastNameRep = firstLastNameRep;
    }

    public String getSecondLastNameRep() {
        return secondLastNameRep;
    }

    public void setSecondLastNameRep(String secondLastNameRep) {
        this.secondLastNameRep = secondLastNameRep;
    }  

    public Integer getDigVer() {
        return digVer;
    }

    public void setDigVer(Integer digVer) {
        this.digVer = digVer;
    }
    
    public Boolean getChangePass() {
        return changePass;
    }

    public void setChangePass(Boolean changePass) {
        this.changePass = changePass;
    }

    public String getPassActual() {
        return passActual;
    }

    public void setPassActual(String passActual) {
        this.passActual = passActual;
    }
    
    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getConfirmNewPass() {
        return confirmNewPass;
    }

    public void setConfirmNewPass(String confirmNewPass) {
        this.confirmNewPass = confirmNewPass;
    }

    public String getTypeDocument() {
        return typeDocument;
    }

    public void setTypeDocument(String typeDocument) {
        this.typeDocument = typeDocument;
    }

    public String getNoDocument() {
        return noDocument;
    }

    public void setNoDocument(String noDocument) {
        this.noDocument = noDocument;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getFirstLastName() {
        return firstLastName;
    }

    public void setFirstLastName(String firstLastName) {
        this.firstLastName = firstLastName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public Integer getMunicipality() {
        return municipality;
    }

    public void setMunicipality(Integer municipality) {
        this.municipality = municipality;
    }
    
    public void setType_ident_producer(List<DocumentsTypes> type_ident_producer) {
        this.type_ident_producer = type_ident_producer;
    }

    public List<DocumentsTypes> getType_ident_producer() {
        return type_ident_producer;
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
    
    
    @Override
    public void prepare() throws Exception {
        user = (Users) ActionContext.getContext().getSession().get(APConstants.SESSION_USER);
//        user = (Users) this.getSession().get(APConstants.SESSION_USER);
        idEntSystem = UsersDao.getEntitySystem(user.getIdUsr());
        ent = entDao.findById(idEntSystem);
        this.setType_ident_producer(new DocumentsTypesDao().findAll());
        this.setDepartment_producer(new DepartmentsDao().findAll());
        List<Municipalities> mun = new ArrayList<Municipalities>();
        mun.add(new Municipalities());         
        this.setTypeDocument("-1");
        this.setCity_producer(mun);
    }
    

    @Override
    public String execute() throws Exception {        
        return SUCCESS;
    }

    /**
     * Encargado de traer la informacion del perfil y la configuracion para un usuario registrado en el sistema
     * @return Estado del proceso
     */
    public String getInformation() {       
        if (ent.getDocumentsTypes()!=null) typeDocument = ent.getDocumentsTypes().getAcronymDocTyp();
        noDocument   = ent.getDocumentNumberEnt();
        if (ent.getMunicipalities()!=null) {
            municipality = ent.getMunicipalities().getIdMun();
//            HashMap munTemp = new MunicipalitiesDao().objectById(municipality);
            if (municipality!=-1) department = MunicipalitiesDao.getDepartmentId(municipality);
            if (department!=-1) this.setCity_producer(new MunicipalitiesDao().findAll(department));
        }
        passActual   = user.getPasswordUsr();
        if (typeDocument.equals("NIT")) {
            nameCompany = ent.getNameEnt();
            firstNameRep  = ent.getFirstName1Ent();
            secondNameRep = ent.getFirstName2Ent();
            firstLastNameRep  = ent.getLastName1Ent();
            secondLastNameRep = ent.getLastName2Ent();
            digVer = ent.getValidationNumberEnt();
        } else {
            firstName  = ent.getFirstName1Ent();
            secondName = ent.getFirstName2Ent();
            firstLastName  = ent.getLastName1Ent();
            secondLastName = ent.getLastName2Ent();                               
        }   
        return SUCCESS;
    }
    
    //Propiedades de estado de las peticiones que se manejan a traves de AJAX
    /**
     * Propiedades de estado de las peticiones que se manejan a traves de AJAX
     */
    protected String state;
    protected String info;
    
    //Metodos getter y setter por cada propiedad de estado de las peticiones
    /**
     * Metodos getter y setter por cada propiedad de estado de las peticiones
     */
    public void setState(String state) {
        this.state = state;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getState() {
        return state;
    }

    public String getInfo() {
        return info;
    }
    

    /**
     * Metodo encargado de validar el formulario de un nuevo usuario a registrar
     */
    @Override
    public void validate() {
        /*
         * Se evalua dependiendo a la accion realizada:
         * 1) configProfile: Al momento de guardar la informacion del perfil
         * 2) configSetting: Al momento de guardar la informacion de configuracion
         */
        if (actExe.equals("configProfile")) {     
//            if (this.getEmailUser()!=null || !this.getEmailUser().isEmpty()) {
//                if (!ValidatorUtil.validateEmail(this.getEmailUser())) {
//                    addFieldError("emailUser", "Ha ingresado un correo con formato invalido");
//                    this.setPassword(null);
//                }
//            }
//            if (this.getEmailUser()!=null) {
//                Users resUser = userDao.checkUsername(this.getEmailUser());
//                
//                if (resUser!=null) {      
//                    addFieldError("emailUser", "El usuario ya existe");
//                    addActionError("El usuario ingresado ya se encuentra en el sistema");
//                }
//            }
        } else if (actExe.equals("configSetting")) {
            if (this.getChangePass()) {
                String saltUsr=user.getSaltUsr();
//                String passRes = GlobalFunctions.generateSHA1(this.getPassActual(), saltUsr);
                String passRes = GlobalFunctions.generateMD5(saltUsr+this.getPassActual());

                Users loggedUser = userDao.login(user.getNameUserUsr(), passRes);
                if (loggedUser == null) {
                    addFieldError("passActual", "Dato invalido");
                    addActionError("La contraseña ingresada es incorrecta");
                } else {
                    if (this.getNewPass()!=null && this.getNewPass().length() < 6) {
                        addFieldError("newPass", "Dato invalido minimo 6 caracteres");
                        addActionError("Debe ingresar una contraseña de mas de 6 caracteres");
                    }

//                    if (this.getNewPass()!=null && this.getNewPass().length() > 10) {
//                        addFieldError("newPass", "Dato invalido maximo 10 caracteres");
//                        addActionError("Debe ingresar una contraseña de menos de 10 caracteres");
//                    }

                    if ((this.getNewPass()==null || this.getConfirmNewPass().isEmpty()) || !this.getNewPass().equals(this.getConfirmNewPass())) {
                        addFieldError("newPass", "");
                        addFieldError("confirmNewPass", "");
                        addActionError("Las contrasenas ingresadas deben coincidir");
                    }
                }
            }         
        }
    }
    
    public String getValues() {
        HashMap findParams = new HashMap();
        findParams.put("idEntUser", idEntSystem);
        numPro = new ProducersDao().countData(findParams);
        numFar = new FarmsDao().countData(findParams);
        numRas = new RastasDao().countData(findParams);        
        numFie = new FieldsDao().countData(findParams);
        numEve = new ProductionEventsDao().countData(findParams);
        return SUCCESS;
    }

    /**
     * Encargado de guardar la informacion del perfil de un usuario
     * @return Estado del proceso
     */
    public String saveProfile() {
        String action = "M";
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Transaction tx  = session.beginTransaction();      
        
        try {
            
            if (typeDocument==null || typeDocument.equals("-1")) {
                ent.setDocumentsTypes(null);
            } else {
                ent.setDocumentsTypes(new DocumentsTypes(typeDocument));
            }
            if (municipality==null || municipality==-1) {
                ent.setMunicipalities(null);
            } else {
                ent.setMunicipalities(new Municipalities(municipality));
            }
            ent.setDocumentNumberEnt(noDocument);
            if (typeDocument.equals("NIT")) {
                ent.setNameEnt(nameCompany);
                ent.setFirstName1Ent(firstNameRep);
                ent.setFirstName2Ent(secondNameRep);
                ent.setLastName1Ent(firstLastNameRep);
                ent.setLastName2Ent(secondLastNameRep);
                ent.setValidationNumberEnt(digVer);
            } else {
                ent.setFirstName1Ent(firstName);
                ent.setFirstName2Ent(secondName);
                ent.setLastName1Ent(firstLastName);
                ent.setLastName2Ent(secondLastName);                             
            }
            session.saveOrUpdate(ent);
            
            if (noDocument!=null) {
                LogEntities log = new LogEntities();
                log.setIdLogEnt(null);
                log.setIdEntityLogEnt(ent.getIdEnt());
                log.setIdObjectLogEnt(ent.getIdEnt());
                log.setTableLogEnt("entities");
                log.setDateLogEnt(new Date());
                log.setActionTypeLogEnt(action);
                session.saveOrUpdate(log);
//                info  = "La informacion del perfil ha sido modificada con exito";
            } else {
//                info  = "La informacion del perfil ha sido modificada con exito";
            }

            tx.commit();
            state = "success";
            info  = "La informacion del perfil ha sido modificada con exito";
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            state = "failure";
            info  = "Fallo al momento de cambiar la informacion del perfil";
        } finally {
            session.close();
        }
        return "states";
    }
    
    /**
     * Encargado de guardar la informacion de configuracion de un usuario
     * @return Estado del proceso
     */
    public String saveSetting() {
        String action = "M";
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Transaction tx  = session.beginTransaction();      
        
        try {
            state = "success";
            if (this.getChangePass()) {
//                String saltUsr = GlobalFunctions.getSalt();
//                String passRes = GlobalFunctions.generateSHA1(newPass, saltUsr);
                Double salt = (Math.floor(Math.random()*999999+100000));
                String saltUsr = GlobalFunctions.generateMD5(salt+user.getNameUserUsr());
                String passRes = GlobalFunctions.generateMD5(saltUsr+newPass);

                user.setSaltUsr(saltUsr);
                user.setPasswordUsr(passRes);
//                user.setStatus(1);
                session.saveOrUpdate(user);

                LogEntities logPro = new LogEntities();
                logPro.setIdLogEnt(null);
                logPro.setIdEntityLogEnt(ent.getIdEnt());
                logPro.setIdObjectLogEnt(user.getIdUsr());
                logPro.setTableLogEnt("users");
                logPro.setDateLogEnt(new Date());
                logPro.setActionTypeLogEnt(action);
                session.saveOrUpdate(logPro);

                tx.commit();
                info  = "La informacion de configuracion ha sido modificada con exito";
            } else {
                info  = "El usuario no ha ingresado ninguna informacion al sistema";
            }
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            state = "failure";
            info  = "Fallo al momento de cambiar la informacion de configuracion";
//        } catch (NoSuchAlgorithmException ex) {
////            java.util.logging.Logger.getLogger(ActionProfile.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NoSuchProviderException ex) {
////            java.util.logging.Logger.getLogger(ActionProfile.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.close();
        }
        return "states";
    }
}