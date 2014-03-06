/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aepscolombia.platform.controllers;

//import com.opensymphony.xwork2.inject.Inject;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.aepscolombia.platform.models.dao.DepartmentsDao;
import org.aepscolombia.platform.models.dao.DocumentsTypesDao;
import org.aepscolombia.platform.models.dao.EntitiesDao;
import org.aepscolombia.platform.models.dao.LogEntitiesDao;
import org.aepscolombia.platform.models.dao.ProducersDao;
import org.aepscolombia.platform.models.dao.UsersDao;
import org.aepscolombia.platform.models.dao.UserEntityDao;
import org.aepscolombia.platform.models.dao.UsersProfilesDao;
import org.aepscolombia.platform.models.entity.Departments;
import org.aepscolombia.platform.models.entity.DocumentsTypes;
import org.aepscolombia.platform.models.entity.Entities;
import org.aepscolombia.platform.models.entity.LogEntities;
import org.aepscolombia.platform.models.entity.Profiles;
import org.aepscolombia.platform.models.entity.Producers;
import org.aepscolombia.platform.models.entity.EntitiesTypes;
import org.aepscolombia.platform.models.entity.Municipalities;
import org.aepscolombia.platform.models.entity.UserEntity;
import org.aepscolombia.platform.models.entity.Users;
import org.aepscolombia.platform.models.entity.UsersProfiles;
import org.aepscolombia.platform.models.entity.UsersProfilesId;
import org.aepscolombia.platform.util.APConstants;
import org.aepscolombia.platform.util.GlobalFunctions;
import org.aepscolombia.platform.util.HibernateUtil;
import org.aepscolombia.platform.util.ValidatorUtil;
import org.apache.commons.lang.StringUtils;
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
    private UsersDao userDao       = new UsersDao();
    private EntitiesDao entDao     = new EntitiesDao();
    private LogEntitiesDao logDao  = new LogEntitiesDao();
    private UserEntityDao usrEntDao;
    private UsersProfilesDao usrPerDao;
    
    //Datos de acceso al sistema
    
    private String emailUser;
    private String celphoneUser;
    private int noRecords;
    private String changePass;
    private String newPass;
    private String confirmNewPass;

    private String typeDocument;
    private String noDocument;
    private String firstName;
    private String secondName;
    private String firstLastName;
    private String secondLastName;
    private int department;
    private int municipality;
    private List<DocumentsTypes> type_ident_producer;
    private List<Departments> department_producer;
    private List<Municipalities> city_producer;
    private Integer idEntSystem;

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

    public int getNoRecords() {
        return noRecords;
    }

    public void setNoRecords(int noRecords) {
        this.noRecords = noRecords;
    }

    public String getChangePass() {
        return changePass;
    }

    public void setChangePass(String changePass) {
        this.changePass = changePass;
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

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public int getMunicipality() {
        return municipality;
    }

    public void setMunicipality(int municipality) {
        this.municipality = municipality;
    }
    
    public void setType_ident_producer(List<DocumentsTypes> type_ident_producer) {
        this.type_ident_producer = type_ident_producer;
    }

    public List<DocumentsTypes> getType_ident_producer() {
//        ArrayList<TiposDocumentos> prueba = new ArrayList<TiposDocumentos>();
//        prueba.add(new TiposDocumentos("cc", "cedula"));
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
    
    

    public ActionProfile() {        
        super();
    }
    
    @Override
    public void prepare() throws Exception {
        user = (Users) this.getSession().get(APConstants.SESSION_USER);
        idEntSystem = UsersDao.getEntitySystem(user.getIdUsr());
        this.setType_ident_producer(new DocumentsTypesDao().findAll());
        this.setDepartment_producer(new DepartmentsDao().findAll());
        List<Municipalities> mun = new ArrayList<Municipalities>();
        mun.add(new Municipalities());           
        this.setCity_producer(mun);
    }
    

    @Override
    public String execute() throws Exception {        
        return SUCCESS;
    }

    /**
     * Encargado de verificar si un usuario se encuentra registrado en el sistema
     * @return Estado del proceso
     */
    public String getInformation() {
        return SUCCESS;
//        if(this.getUsername()!=null && this.getPassword()!=null) {
//            Users loggedUser = userDao.login(this.getUsername(), this.getPassword());
////            System.out.println("entreeee");
//            if (loggedUser != null) {
//                this.setUsername("");
//                this.setPassword("");
//                this.getSession().put(APConstants.SESSION_USER, loggedUser);
////          LOG.info("User " + user.getEmail() + " logged in successfully.");
//                return SUCCESS;
//            } else {
////          LOG.info("User " + user.getEmail() + " tried to logged in but failed.");
//                this.setUsername("");
//                this.setPassword("");
////                addFieldError("username", username);
////                addFieldError("password", password);
//                addActionError("La informacion ingresada es invalida");
////                return INPUT;
//                return INPUT;
//            }
//        }
//        return INPUT;
//        
//        SessionFactory sessions = HibernateUtil.getSessionFactory();
//        Session session = sessions.openSession();
//        Transaction tx  = session.beginTransaction();
//
//        try {
//            String passRes = GlobalFunctions.generateMD5(this.getPassRest());
////            String passResCon = this.getPassRestCon();
//
//            Users user = (Users) userDao.objectById(this.getIdUser());
//            user.setPasswordUsr(passRes);
//            user.setStatusUsr(1);
//            session.saveOrUpdate(user);
//
////            LogEntities logPro = new LogEntities();
////            logPro.setIdLogEnt(null);
////            logPro.setIdEntityLogEnt(ent.getIdEnt()); //Colocar el usuario registrado en el sistema
////            logPro.setIdObjectLogEnt(user.getIdUsr());
////            logPro.setTableLogEnt("users");
////            logPro.setDateLogEnt(new Date());
////            logPro.setActionTypeLogEnt("U");
////            session.saveOrUpdate(logPro);
//            tx.commit();
//            state = "success";
//            info  = "La contraseña se ha regenerado con exito";
//        } catch (HibernateException e) {
//            if (tx != null) {
//                tx.rollback();
//            }
//            e.printStackTrace();
//            state = "failure";
//            info  = "Fallo al momento de regenerar la contraseña";
//        }
//        return "states";
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
         * 1) login: Al momento de ingresar al sistema
         * 2) newuser: Al momento de crear un nuevo usuario
         * 3) restuser: Al momento de recuperar la informacion de un usuario
         * 4) changepass: Al momento de recuperar la contraseña
         */
        if (actExe.equals("newuser")) {           
//            if ((this.getEmailUser()==null || this.getEmailUser().isEmpty()) && (this.getCelphoneUser()==null || this.getCelphoneUser().isEmpty())) {
//                addFieldError("emailUser", "Campo obligatorio");
//                addFieldError("celphoneUser", "Campo obligatorio");
//                addActionError("Se debe ingresar por lo menos correo electronico o celular para el registro");
//            }
//
//            if (this.getEmailUser()!=null || !this.getEmailUser().isEmpty()) {
//                if (!ValidatorUtil.validateEmail(this.getEmailUser())) {
//                    addFieldError("emailUser", "Ha ingresado un correo con formato invalido");
//                    this.setPassword(null);
//                }
//            }

//            if (this.getPasswordUser()!=null && this.getPasswordUser().length() < 6) {
//                addFieldError("passwordUser", "Campo incompleto");
//                addActionError("Debe ingresar una contraseña de mas de 6 caracteres");
//            }
//
//            if (this.getPasswordUser()!=null && this.getPasswordUser().length() > 10) {
//                addFieldError("passwordUser", "Campo muy largo");
//                addActionError("Debe ingresar una contraseña de menos de 10 caracteres");
//            }
//            if ((this.getPasswordRepUser()==null || this.getPasswordRepUser().isEmpty()) || !this.getPasswordUser().equals(this.getPasswordRepUser())) {
//                addFieldError("passwordUser", "");
//                addFieldError("passwordRepUser", "");
//                addActionError("Las contrasenas ingresadas deben coincidir");
//            }

            if (this.getEmailUser()!=null) {
                Users resUser = userDao.checkUsername(this.getEmailUser());
                
                if (resUser!=null) {      
                    addFieldError("emailUser", "El usuario ya existe");
                    addActionError("El usuario ingresado ya se encuentra en el sistema");
                }
            }
            
        } else if (actExe.equals("changepass")) {
            
//            if (this.getPassRest()!=null && this.getPassRest().length() < 6) {
//                addFieldError("passRest", "Campo incompleto");
//                addActionError("Debe ingresar una contraseña de mas de 6 caracteres");
//            }
//
//            if (this.getPassRest()!=null && this.getPassRest().length() > 10) {
//                addFieldError("passRest", "Campo muy largo");
//                addActionError("Debe ingresar una contraseña de menos de 10 caracteres");
//            }
//            
//            if ((this.getPassRestCon()==null || this.getPassRestCon().isEmpty()) || !this.getPassRest().equals(this.getPassRestCon())) {
//                addFieldError("passRest", "");
//                addFieldError("passRestCon", "");
//                addActionError("Las contrasenas ingresadas deben coincidir");
//            }
            
        }
    }

    /**
     * Encargado de guardar la informacion al momento de crear un nuevo usuario
     * @return Estado del proceso
     */
    public String saveData() {
        String action = "C";
//        if (actExe.equals("save")) {
//            action = "C";
//        } else if (actExe.equals("modify")) {
//            action = "M";
//        }
        if (action.equals("C")) {
        }
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Transaction tx  = session.beginTransaction();       
        
//        state = "success";
//        info  = "Pruebas";
//        return "states";
        
        try {
            String codValidation = GlobalFunctions.getSalt();
//            String passTransform = GlobalFunctions.generateMD5(this.getPasswordUser());

            Entities ent = new Entities();
            ent.setIdEnt(null);
//            ent.setEntityTypeEnt(1);
            ent.setEntitiesTypes(new EntitiesTypes(1));
//            if(this.getCelphoneUser()!=null && !celphoneUser.equals("")) ent.setCellphoneEnt(Long.parseLong(this.getCelphoneUser()));
//            ent.setCellphoneEnt((long) Integer.parseInt(this.getCelphoneUser()));
//            ent.setCellphoneEnt((long)317524765);
            ent.setEmailEnt(this.getEmailUser());
            ent.setStatusEnt(true);
            session.saveOrUpdate(ent);
//            entDao.save(ent);

            LogEntities log = new LogEntities();
            log.setIdLogEnt(null);
            log.setIdEntityLogEnt(ent.getIdEnt());
            log.setIdObjectLogEnt(ent.getIdEnt());
            log.setTableLogEnt("entities");
            log.setDateLogEnt(new Date());
            log.setActionTypeLogEnt(action);
            session.saveOrUpdate(log);
//            logDao.save(log);

//            if (this.getTypeUser() == 1) {
//                Producers pro = new Producers();
//                pro.setIdPro(null);
//                pro.setEntities(ent);
//                pro.setStatusPro(true);
//                session.saveOrUpdate(pro);
//
//                LogEntities logPro = new LogEntities();
//                logPro.setIdLogEnt(null);
//                logPro.setIdEntityLogEnt(ent.getIdEnt());
//                logPro.setIdObjectLogEnt(pro.getIdPro());
//                logPro.setTableLogEnt("producers");
//                logPro.setDateLogEnt(new Date());
//                logPro.setActionTypeLogEnt(action);
//                session.saveOrUpdate(logPro);
////                logDao.save(logPro);
//
//            }

            String nameUser = null;
//            if (!this.getEmailUser().isEmpty()) {
//                nameUser = this.getEmailUser();
//            } else if (!this.getCelphoneUser().isEmpty()) {
//                nameUser = this.getCelphoneUser();
//            }

            Users user = new Users();
            user.setIdUsr(null);
            user.setNameUserUsr(nameUser);
//            user.setPasswordUsr(this.getPasswordUser());
            user.setPasswordUsr("");
            user.setCodValidationUsr(codValidation);
            user.setStatusUsr(2);//Estado inhabilitado hasta confirmar
            session.saveOrUpdate(user);
//            userDao.save(user);
            
            
            LogEntities logPro = new LogEntities();
            logPro.setIdLogEnt(null);
            logPro.setIdEntityLogEnt(ent.getIdEnt());
            logPro.setIdObjectLogEnt(user.getIdUsr());
            logPro.setTableLogEnt("users");
            logPro.setDateLogEnt(new Date());
            logPro.setActionTypeLogEnt(action);
            session.saveOrUpdate(logPro);
//            logDao.save(logPro);
//            throw new HibernateException("Error creando usuario entidades");
//            tx.rollback();
            
//            usrEntDao.saveUserEnt(ent.getIdEnt(), user.getIdUsr());
            
//            boolean resUsrEnt;
            UserEntity usrEnt = new UserEntity();
            usrEnt.setIdUsrEnt(null);
            usrEnt.setIdProjectUsrEnt(null);
            usrEnt.setUsers(user);
            usrEnt.setEntities(ent);
            usrEnt.setStatusUsrEnt(true);
            session.saveOrUpdate(usrEnt);
//            resUsrEnt = usrEntDao.save(usrEnt);
//            if(!resUsrEnt) tx.rollback();
            
////            resUsrEnt = usrEntDao.save(usrEnt);
////            if(!resUsrEnt) throw new HibernateException("Error creando usuario entidades");
////
////            //Guardar el usuario segun el perfil designado
////            if(this.getTypeUser()==1) {
            int profile = 0;
//            if(this.getTypeUser()==1) {
//                profile = 3;
//            } else if(this.getTypeUser()==2) {
//                profile = 4;
//            } else if(this.getTypeUser()==3) {
//                profile = 5;
//            }
//            boolean resUsrPro;
            UsersProfiles usrPer = new UsersProfiles();
            Profiles prof = new Profiles(profile);
            usrPer.setId(new UsersProfilesId(user.getIdUsr(), prof.getIdPro()));
            usrPer.setUsers(user);
            usrPer.setProfiles(prof);
            usrPer.setIdProjectUsrPro(null);
            session.saveOrUpdate(usrPer);

            tx.commit();
            GlobalFunctions.sendEmail(this.getEmailUser(), getText("email.from"), getText("email.fromPass"), getText("email.subjectNewUser"), GlobalFunctions.messageToNewUser(this.getRequest().getLocalAddr(), user.getNameUserUsr(), codValidation));
            state = "success";
            info  = "El usuario ha sido agregado con exito, confirmar la inscripcion a traves de su correo";//Tener la posibilidad de enviarlo por celular
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            state = "failure";
            info  = "Fallo al momento de agregar un usuario";
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
//            java.util.logging.Logger.getLogger(ActionLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            ex.printStackTrace();
//            java.util.logging.Logger.getLogger(ActionLogin.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.close();
        }

        return "states";
//        return ERROR;
    }
}