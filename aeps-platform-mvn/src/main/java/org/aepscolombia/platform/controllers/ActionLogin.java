/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aepscolombia.platform.controllers;

//import com.opensymphony.xwork2.inject.Inject;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.aepscolombia.platform.models.dao.EntitiesDao;
import org.aepscolombia.platform.models.dao.LogEntitiesDao;
import org.aepscolombia.platform.models.dao.ProducersDao;
import org.aepscolombia.platform.models.dao.UsersDao;
import org.aepscolombia.platform.models.dao.UserEntityDao;
import org.aepscolombia.platform.models.dao.UsersProfilesDao;
import org.aepscolombia.platform.models.entity.Entities;
import org.aepscolombia.platform.models.entity.LogEntities;
import org.aepscolombia.platform.models.entity.Profiles;
import org.aepscolombia.platform.models.entity.Producers;
import org.aepscolombia.platform.models.entity.EntitiesTypes;
import org.aepscolombia.platform.models.entity.UserEntity;
import org.aepscolombia.platform.models.entity.Users;
import org.aepscolombia.platform.models.entity.UsersProfiles;
import org.aepscolombia.platform.models.entity.UsersProfilesId;
import org.aepscolombia.platform.util.APConstants;
import org.aepscolombia.platform.util.GlobalFunctions;
import org.aepscolombia.platform.util.HibernateUtil;
import org.aepscolombia.platform.util.ValidatorUtil;
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
public class ActionLogin extends BaseAction {
    
    private static final Logger LOG = LoggerFactory.getLogger(ActionLogin.class);
    private static final long serialVersionUID = -890122014241894430L;
    private Users user;
    private UsersDao userDao     = new UsersDao();
    private EntitiesDao entDao     = new EntitiesDao();
    private ProducersDao proDao   = new ProducersDao();
    private LogEntitiesDao logDao  = new LogEntitiesDao();
    private UserEntityDao usrEntDao;
    private UsersProfilesDao usrPerDao;
    
    //Datos de acceso al sistema
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    //Datos de creacion de nuevo usuario
    private int typeUser;
    private String emailUser;
    private String celphoneUser;
    private String passwordUser;
    private String passwordRepUser;

    public int getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(int typeUser) {
        this.typeUser = typeUser;
    }

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

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public String getPasswordRepUser() {
        return passwordRepUser;
    }

    public void setPasswordRepUser(String passwordRepUser) {
        this.passwordRepUser = passwordRepUser;
    }
    //Datos de recuperacion de contrasena
    private String infoUser;

    public String getInfoUser() {
        return infoUser;
    }

    public void setInfoUser(String infoUser) {
        this.infoUser = infoUser;
    }

//    private UserManager userManager;
    public ActionLogin() {
        super();
    }
    
    private String logSel = "";

    public String getLogSel() {
        return logSel;
    }   
    
    //Datos para el manejo del captcha
    private String recaptcha_challenge_field;
    private String recapChanllenge;
    private String recaptcha_response_field;

    public void setRecaptcha_challenge_field(String recaptcha_challenge_field) {
        this.recaptcha_challenge_field = recaptcha_challenge_field;
    }

    public void setRecaptcha_response_field(String recaptcha_response_field) {
        this.recaptcha_response_field = recaptcha_response_field;
    }    
    

    @Override
    public String execute() throws Exception {        
        logSel  = this.getRequest().getParameter("logSel");
        return SUCCESS;
    }

    public String home() throws Exception {
        return SUCCESS;
    }

//    @Inject
//    public LoginAction(APConfig config, LogframeManager logframeManager, UserManager userManager) {
//      super(config, logframeManager);
//      this.userManager = userManager;
//    }
    public Users getUser() {
        return user;
    }
    
    public void setUser(Users user) {
        this.user = user;
    }

    /**
     * Encargado de verificar si un usuario se encuentra registrado en el sistema
     * @return Estado del proceso
     */
    public String login() {
//        return SUCCESS;
        // attribute user is not null when the user try to login
//        if (user != null) {
            // Check if is a valid user
//            Usuarios loggedUser = userDao.login(user.getNombreUsuarioUsr().trim(), user.getPasswordUsr());
//        userDao = new UsuariosDao();
//        System.out.println("entreeee");
        if(this.getUsername()!=null && this.getPassword()!=null) {
            Users loggedUser = userDao.login(this.getUsername(), this.getPassword());
            if (loggedUser != null) {
//                loggedUser.setLastLogin(new Date());
//                userManager.saveLastLogin(loggedUser);
                this.getSession().put(APConstants.SESSION_USER, loggedUser);
//          LOG.info("User " + user.getEmail() + " logged in successfully.");
                return SUCCESS;
            } else {
//          LOG.info("User " + user.getEmail() + " tried to logged in but failed.");
//                user.setPasswordUsr(null);
//                addFieldError("loginMesage", getText("home.login.error"));
                this.setUsername("");
                this.setPassword("");
//                addFieldError("username", "Campo invalido");
//                addFieldError("password", "Campo invalido");
                addActionError("El usuario ingresado no se encuentra registrado");
//                return INPUT;
                return INPUT;
            }
        }
        return INPUT;
//        } else {
//            // Check if the user exists in the session
//            return (this.getCurrentUser() == null) ? INPUT : SUCCESS;
//        }
    }

    /**
     * Encargado de verificar el usuario y el codigo que se le envia a un usuario por correo para 
     * poder habilitar un usuario nuevo
     * @return Estado del proceso
     */
    public String verifyUser() {
        String codVal   = this.getRequest().getParameter("codVal");
        String nameUser = this.getRequest().getParameter("nameUser");
        if (nameUser != null && codVal != null) {
            Users loggedUser = userDao.getUserByCode(nameUser.trim(), codVal.trim());
            if (loggedUser != null) {
                loggedUser.setCodValidationUsr("");
                loggedUser.setStatusUsr(1);
                userDao.save(loggedUser);
//                loggedUser.setLastLogin(new Date());
//                userManager.saveLastLogin(loggedUser);
                this.getSession().put(APConstants.SESSION_USER, loggedUser);
//          LOG.info("User " + user.getEmail() + " logged in successfully.");
                return SUCCESS;
            } else {
//          LOG.info("User " + user.getEmail() + " tried to logged in but failed.");
//                user.setPasswordUsr(null);
//                addFieldError("loginMesage", getText("home.login.error"));
                return BaseAction.NOT_FOUND;
            }
        } else {
            return BaseAction.NOT_POSSIBLE;
        }

    }
    
    /**
     * Encargado de cerrar la sesion de un usuario
     * poder habilitar un usuario nuevo
     * @return Estado del proceso
     */
    public String logout() {
        Users user = (Users) this.getSession().get(APConstants.SESSION_USER);
        if (user != null) {
//          LOG.info("User {} logout succesfully", user.getNameUserUsr());
        }
        Map session = this.getSession();
        session.clear();
        session.remove(APConstants.SESSION_USER);
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
     * Encargado de enviar el correo para recuperar la contraseña de un usuario
     * @return Estado del proceso
     */
    public String restorePassword() {
        String nameUser = this.getInfoUser(); //this.getRequest().getParameter("nameUser");
        String codValidation = "";
        try {
            codValidation = GlobalFunctions.getSalt();
        } catch (NoSuchAlgorithmException ex) {
            java.util.logging.Logger.getLogger(ActionLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            java.util.logging.Logger.getLogger(ActionLogin.class.getName()).log(Level.SEVERE, null, ex);
        }        
        Users loggedUser = userDao.getUserByLogin(nameUser.trim(), "");
        if (loggedUser != null) {
//          LOG.info("User " + user.getEmail() + " logged in successfully.");
            loggedUser.setCodValidationUsr(codValidation);
            loggedUser.setStatusUsr(2);
            userDao.save(loggedUser);
            GlobalFunctions.sendEmail(loggedUser.getNameUserUsr(), getText("email.from"), getText("email.fromPass"), getText("email.subjectNewUser"), GlobalFunctions.messageToRestoreUser(loggedUser.getNameUserUsr(), codValidation));
//            addActionMessage("El usuario ha sido agregado con exito, confirmar la inscripcion a traves de su correo");
            state = "success";
            info  = "Se ha enviado la informacion al correo para que proceda a recuperar la contraseña";            
        } else {
//          LOG.info("User " + user.getEmail() + " tried to logged in but failed.");
//                user.setPasswordUsr(null);
//                addFieldError("loginMesage", getText("home.login.error"));
            addFieldError("infoUser", "Campo obligatorio");
            state = "failure";
            info  = "El usuario ingresado no se encuentra registrado";
//            addActionError("El usuario ingresado no se encuentra registrado");
        }
        
        return "states";
    } 
    
    /**
     * Encargado de verificar el usuario y el codigo que se le envia a un usuario por correo para 
     * poder habilitar a un usuario que desea generar una nueva contraseña
     * @return Estado del proceso
     */
    public String verifyUserToRestore() {
        String codVal   = this.getRequest().getParameter("codVal");
        String nameUser = this.getRequest().getParameter("nameUser");
        if (nameUser != null && codVal != null) {
            Users loggedUser = userDao.getUserByCode(nameUser.trim(), codVal.trim());
            if (loggedUser != null) {
                this.setIdUser(loggedUser.getIdUsr());
                loggedUser.setCodValidationUsr("");
                userDao.save(loggedUser);
                return SUCCESS;
            } else {
                return BaseAction.NOT_FOUND;
            }
        } else {
            return BaseAction.NOT_POSSIBLE;
        }
    }
    
    private String passRest;
    private String passRestCon;
    private Integer idUser;

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }   

    public String getPassRest() {
        return passRest;
    }

    public void setPassRest(String passRest) {
        this.passRest = passRest;
    }

    public String getPassRestCon() {
        return passRestCon;
    }

    public void setPassRestCon(String passRestCon) {
        this.passRestCon = passRestCon;
    }
    
    
    
    /**
     * Encargado de cambiar la contraseña para un usuario
     * @return Estado del proceso
     */    
    public String changePassUser() {
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Transaction tx  = session.beginTransaction();

        try {
            String passRes = GlobalFunctions.generateMD5(this.getPassRest());
//            String passResCon = this.getPassRestCon();

            Users user = (Users) userDao.objectById(this.getIdUser());
            user.setPasswordUsr(passRes);
            user.setStatusUsr(1);
            session.saveOrUpdate(user);

//            LogEntities logPro = new LogEntities();
//            logPro.setIdLogEnt(null);
//            logPro.setIdEntityLogEnt(ent.getIdEnt()); //Colocar el usuario registrado en el sistema
//            logPro.setIdObjectLogEnt(user.getIdUsr());
//            logPro.setTableLogEnt("users");
//            logPro.setDateLogEnt(new Date());
//            logPro.setActionTypeLogEnt("U");
//            session.saveOrUpdate(logPro);
            tx.commit();
//            addActionMessage("El usuario ha sido agregado con exito, confirmar la inscripcion a traves de su correo");
            state = "success";
            info  = "La contraseña se ha regenerado con exito";
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
//            addActionError("Fallo al momento de agregar un productor");
            state = "failure";
            info  = "Fallo al momento de regenerar la contraseña";
        }
        return "states";
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
//        System.out.println("ingress->"+this.getIngress());
        if (actExe.equals("login")) {
//            System.out.println("username->"+this.getUsername());
//            addActionError("Se han ingresado datos invalidos");
//            if (this.getUsername() != null || this.getPassword() != null) {
                if (this.getUsername()==null || this.getUsername().isEmpty()) {
                    addFieldError("username", "Campo obligatorio");
                }
                if (this.getPassword()==null || this.getPassword().isEmpty()) {
                    addFieldError("password", "Campo obligatorio");
                }
                /* else if (!ValidatorUtil.validateEmail(this.getUsername())) {
                 addFieldError("username", "Ha ingresado un correo con formato invalido");
                 this.setPassword(null);
                 }*/
//            }
        } else if (actExe.equals("newuser")) {           

//            if (this.getTypeUser() == 0) {
//                addFieldError("typeUser", "Campo obligatorio");
//            }
//            boolean enter = false;
            if ((this.getEmailUser()==null || this.getEmailUser().isEmpty()) && (this.getCelphoneUser()==null || this.getCelphoneUser().isEmpty())) {
                addFieldError("emailUser", "Campo obligatorio");
                addFieldError("celphoneUser", "Campo obligatorio");
                addActionError("Se debe ingresar por lo menos correo electronico o celular para el registro");
            }

            if (this.getEmailUser()!=null || !this.getEmailUser().isEmpty()) {
                if (!ValidatorUtil.validateEmail(this.getEmailUser())) {
                    addFieldError("emailUser", "Ha ingresado un correo con formato invalido");
                    this.setPassword(null);
                }
            }

            if (this.getPasswordUser()!=null && this.getPasswordUser().length() < 6) {
                addFieldError("passwordUser", "Campo incompleto");
                addActionError("Debe ingresar una contraseña de mas de 6 caracteres");
            }

            if (this.getPasswordUser()!=null && this.getPasswordUser().length() > 10) {
                addFieldError("passwordUser", "Campo muy largo");
                addActionError("Debe ingresar una contraseña de menos de 10 caracteres");
            }
            
//            String codValidation="";
//            try {
//                codValidation = GlobalFunctions.getSalt();
//            } catch (NoSuchAlgorithmException ex) {
//                java.util.logging.Logger.getLogger(ActionLogin.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (NoSuchProviderException ex) {
//                java.util.logging.Logger.getLogger(ActionLogin.class.getName()).log(Level.SEVERE, null, ex);
//            }
            
//            recaptcha_challenge_field = "03AHJ_VusuBg6Y-qf8bZ2NXBgNPHmeXkxqNVwYV1ZordnW2E8R7sAaCuvzx9tg9TLzJ1Vm78hzHtm6xnvoHJBJmHcd3irfyxU7BRuQ1lar7nPm_7hKSQ7Lev0yg9CyTz-YM5sU1i8A-XZZXTwXYiXiJQ8SpYJpuZf0wddb_jnIX2c-5Aab0PiT3GM";
//            boolean valid = ValidatorUtil.verifyCaptcha(this.getRequest().getRemoteAddr(), recaptcha_challenge_field, recaptcha_response_field);
//            String remoteAddr = this.getRequest().getRemoteAddr();            
//            ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
//            reCaptcha.setPrivateKey("6LflSe0SAAAAAJdGyUcUw3jL56wtYISB3CHbopu0");
//            ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, recaptcha_challenge_field, recaptcha_response_field);
////            boolean valid = reCaptchaResponse.isValid();
////            System.out.println("answer->"+remoteAddr);    
////            System.out.println("codigos->"+recaptcha_challenge_field+"||"+recaptcha_response_field);   
//            String path = null;
//            if (!valid) {
//              addActionError("El codigo ingresado es incorrecto, intentelo nuevamente y recargue la imagen");
//            }
            
//            ReCaptcha captcha = ReCaptchaFactory.newReCaptcha("6LflSe0SAAAAAKRNMJfAcEmedhkzuSBFjGr8cbCA", "6LflSe0SAAAAAJdGyUcUw3jL56wtYISB3CHbopu0", false);
//            ReCaptchaResponse response = captcha.checkAnswer(this.getRequest().getRemoteAddr(), recaptcha_challenge_field, recaptcha_response_field);
//
//            if (!response.isValid()) {
//                addActionError("El codigo ingresado es incorrecto, intentelo nuevamente y recargue la imagen");
//            }
            
            if ((this.getPasswordRepUser()==null || this.getPasswordRepUser().isEmpty()) || !this.getPasswordUser().equals(this.getPasswordRepUser())) {
//                System.out.println("pass->"+this.getPasswordUser());
//                System.out.println("pass1->"+this.getPasswordRepUser());
                addFieldError("passwordUser", "");
                addFieldError("passwordRepUser", "");
                addActionError("Las contrasenas ingresadas deben coincidir");
            } // La contrasenas no estan pasando por este condicional

            if (this.getEmailUser()!=null) {
                Users resUser = userDao.checkUsername(this.getEmailUser());
                
                if (resUser!=null) {      
                    addFieldError("emailUser", "El usuario ya existe");
                    addActionError("El usuario ingresado ya se encuentra en el sistema");
                }
            }
            
        } else if (actExe.equals("restuser")) {
            if (this.getInfoUser()==null || this.getInfoUser().isEmpty()) {
                addFieldError("infoUser", "Campo obligatorio");
                addActionError("Se debe ingresar algun dato para realizar la recuperacion de la contrasena");
            }
        } else if (actExe.equals("changepass")) {
            
            if (this.getPassRest()!=null && this.getPassRest().length() < 6) {
                addFieldError("passRest", "Campo incompleto");
                addActionError("Debe ingresar una contraseña de mas de 6 caracteres");
            }

            if (this.getPassRest()!=null && this.getPassRest().length() > 10) {
                addFieldError("passRest", "Campo muy largo");
                addActionError("Debe ingresar una contraseña de menos de 10 caracteres");
            }
            
            if ((this.getPassRestCon()==null || this.getPassRestCon().isEmpty()) || !this.getPassRest().equals(this.getPassRestCon())) {
                addFieldError("passRest", "");
                addFieldError("passRestCon", "");
                addActionError("Las contrasenas ingresadas deben coincidir");
            }
            
        }
    }

    /**
     * Encargado de guardar la informacion al momento de crear un nuevo usuario
     * @return Estado del proceso
     */
    public String saveData() {
//        addActionMessage("Productor adicionado con exito");
//        addActionError("Se debe ingresar por lo menos alguno de los datos de contacto (Telefono fijo, Celular, Correo electrónico)");
//        System.out.println("mensajes->"+this.getActionMessages());
        String action = "C";
//        if (actExe.equals("save")) {
//            action = "C";
//        } else if (actExe.equals("modify")) {
//            action = "M";
//        }
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Transaction tx  = session.beginTransaction();

        try {
//            tx = session.beginTransaction();
            String codValidation = GlobalFunctions.getSalt();
            String passTransform = GlobalFunctions.generateMD5(this.getPasswordUser());
//            System.out.println("codVal->"+codValidation);
//            System.out.println("passTrans->"+passTransform);
            

            Entities ent = new Entities();
            ent.setIdEnt(null);
//            ent.setEntityTypeEnt(1);
            ent.setEntitiesTypes(new EntitiesTypes(1));
//            ent.setCelularEnt((long) Integer.parseInt(this.getCelphoneUser()));
            ent.setCellphoneEnt((long)317524765);
            ent.setEmailEnt(this.getEmailUser());
            ent.setStatusEnt(true);
            session.saveOrUpdate(ent);
//            entDao.save(ent);
            
//            System.out.println("id last->"+ent.getIdEnt());

            LogEntities log = new LogEntities();
            log.setIdLogEnt(null);
            log.setIdEntityLogEnt(ent.getIdEnt());
            log.setIdObjectLogEnt(ent.getIdEnt());
            log.setTableLogEnt("entities");
            log.setDateLogEnt(new Date());
            log.setActionTypeLogEnt(action);
            session.saveOrUpdate(log);
//            logDao.save(log);

            if (this.getTypeUser() == 1) {
                Producers pro = new Producers();
                pro.setIdPro(null);
                pro.setEntities(ent);
                pro.setStatusPro(true);
                session.saveOrUpdate(pro);
//                proDao.save(pro);

                LogEntities logPro = new LogEntities();
                logPro.setIdLogEnt(null);
                logPro.setIdEntityLogEnt(ent.getIdEnt()); //Colocar el usuario registrado en el sistema
                logPro.setIdObjectLogEnt(pro.getIdPro());
                logPro.setTableLogEnt("producers");
                logPro.setDateLogEnt(new Date());
                logPro.setActionTypeLogEnt(action);
                session.saveOrUpdate(logPro);
//                logDao.save(logPro);

            }

            String nameUser = null;
            if (!this.getEmailUser().isEmpty()) {
                nameUser = this.getEmailUser();
            } else if (!this.getCelphoneUser().isEmpty()) {
                nameUser = this.getCelphoneUser();
            }

            Users user = new Users();
            user.setIdUsr(null);
            user.setNameUserUsr(nameUser);
//            user.setPasswordUsr(this.getPasswordUser());
            user.setPasswordUsr(passTransform);
            user.setCodValidationUsr(codValidation);
            user.setStatusUsr(2);//Estado inhabilitado hasta confirmar
            session.saveOrUpdate(user);
//            userDao.save(user);
            
            
            LogEntities logPro = new LogEntities();
            logPro.setIdLogEnt(null);
            logPro.setIdEntityLogEnt(ent.getIdEnt()); //Colocar el usuario registrado en el sistema
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
            if(this.getTypeUser()==1) {
                profile = 3;
            } else if(this.getTypeUser()==2) {
                profile = 4;
            } else if(this.getTypeUser()==3) {
                profile = 5;
            }
//            boolean resUsrPro;
            UsersProfiles usrPer = new UsersProfiles();
            Profiles prof = new Profiles(profile);
            usrPer.setId(new UsersProfilesId(user.getIdUsr(), prof.getIdPro()));
            usrPer.setUsers(user);
            usrPer.setProfiles(prof);
            usrPer.setIdProjectUsrPro(null);
            session.saveOrUpdate(usrPer);
//            resUsrPro = usrPerDao.save(usrPer);
//            if(!resUsrPro) throw new HibernateException("Error creando usuario perfiles");

//            }

            tx.commit();
//            System.out.println("para->"+getText("email.to"));
//            System.out.println("de->"+getText("email.from"));
//            System.out.println("sujeto->"+getText("email.subjectNewUser"));
            GlobalFunctions.sendEmail(this.getEmailUser(), getText("email.from"), getText("email.fromPass"), getText("email.subjectNewUser"), GlobalFunctions.messageToNewUser(user.getNameUserUsr(), codValidation));
//            addActionMessage("El usuario ha sido agregado con exito, confirmar la inscripcion a traves de su correo");
            state = "success";
            info  = "El usuario ha sido agregado con exito, confirmar la inscripcion a traves de su correo";//Tener la posibilidad de enviarlo por celular
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
//            System.out.println("falar en portugues1");
//            System.out.println("error->"+e.getMessage());
//            addActionError("Fallo al momento de agregar un productor");
            state = "failure";
            info  = "Fallo al momento de agregar un usuario";
        } catch (NoSuchAlgorithmException ex) {
//            System.out.println("falar en portugues2");
            ex.printStackTrace();
//            java.util.logging.Logger.getLogger(ActionLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
//            System.out.println("falar en portugues3");
            ex.printStackTrace();
//            java.util.logging.Logger.getLogger(ActionLogin.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.close();
        }

        return "states";
//        return ERROR;
    }
}