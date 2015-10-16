
package org.aepscolombia.platform.controllers;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.aepscolombia.platform.models.dao.AssociationDao;
import org.aepscolombia.platform.models.dao.EntitiesDao;
import org.aepscolombia.platform.models.dao.LogEntitiesDao;
import org.aepscolombia.platform.models.dao.ProducersDao;
import org.aepscolombia.platform.models.dao.SfGuardUserDao;
import org.aepscolombia.platform.models.dao.UsersDao;
import org.aepscolombia.platform.models.dao.UserEntityDao;
import org.aepscolombia.platform.models.dao.UsersProfilesDao;
import org.aepscolombia.platform.models.entity.AgentsAssociation;
import org.aepscolombia.platform.models.entity.Association;
import org.aepscolombia.platform.models.entity.Entities;
import org.aepscolombia.platform.models.entity.Producers;
import org.aepscolombia.platform.models.entity.EntitiesTypes;
import org.aepscolombia.platform.models.entity.ExtensionAgents;
import org.aepscolombia.platform.models.entity.UserEntity;
import org.aepscolombia.platform.models.entity.Users;
import org.aepscolombia.platform.models.entity.WorkTypeExtAgent;
import org.aepscolombia.platform.models.entityservices.SfGuardUser;
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
    private Integer workType;
    private String emailRep;
    private String pageLink;
    private String direction;
    private String nameAsso;
    private String idAssoExt;    
    private List<Association> association_list;

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

    public List<Association> getAssociation_list() {
        return association_list;
    }

    public void setAssociation_list(List<Association> association_list) {
        this.association_list = association_list;
    }   

    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    public String getEmailRep() {
        return emailRep;
    }

    public void setEmailRep(String emailRep) {
        this.emailRep = emailRep;
    }

    public String getPageLink() {
        return pageLink;
    }

    public void setPageLink(String pageLink) {
        this.pageLink = pageLink;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getNameAsso() {
        return nameAsso;
    }

    public void setNameAsso(String nameAsso) {
        this.nameAsso = nameAsso;
    }

    public String getIdAssoExt() {
        return idAssoExt;
    }

    public void setIdAssoExt(String idAssoExt) {
        this.idAssoExt = idAssoExt;
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

    public Users getUser() {
        return user;
    }
    
    public void setUser(Users user) {
        this.user = user;
    }   
    
    @Override
    public void prepare() throws Exception {
        this.setAssociation_list(new AssociationDao().findAll());
    }

    /**
     * Encargado de verificar si un usuario se encuentra registrado en el sistema
     * @return Estado del proceso
     */
    public String signin() {
        if(this.getUsername()!=null && this.getPassword()!=null) {
            String userUsr=this.getUsername().trim();
            String passUsr=this.getPassword().trim();
            String saltUsr="";
//          String passTransform = GlobalFunctions.generateMD5(this.getPasswordUser());            
            Users usrTemp = userDao.getUserByLogin(userUsr, "");
            if (usrTemp!=null) saltUsr = usrTemp.getSaltUsr();
            
            String passRes = GlobalFunctions.generateSHA1(saltUsr+passUsr);            
            
//          String passRes = GlobalFunctions.generateSHA1(passUsr, saltUsr);
            
            Users loggedUser = userDao.login(userUsr, passRes);
            if (loggedUser != null) {
                this.setUsername("");
                this.setPassword("");                
//              this.setLastLogin(loggedUser.getLastInUsr());
                String countryCode = (String)this.getSession().get(APConstants.COUNTRY_CODE);
//                loggedUser.setCountryUsr(new IdiomCountry(countryCode));          
                userDao.save(loggedUser);
                this.getSession().put(APConstants.SESSION_USER, loggedUser);
                
//              this.getSession().put(APConstants.SESSION_USER, loggedUser);
//              LOG.info("User " + user.getEmail() + " logged in successfully.");
//              return "states";
                return SUCCESS;
            } else {
                this.setUsername("");
                this.setPassword("");
                addActionError(getText("message.invalidinfo.login"));
                return INPUT;
            }
        }
        return INPUT;
    }
    
    /**
     * Encargado de ingresar al sistema de forma gratuita
     * @return Estado del proceso
     */
    public String tryFree() {
        String userUsr=getText("user.usernamefree");
        String passUsr=getText("user.passwordfree");
        String saltUsr="";

        Users usrTemp = userDao.getUserByLogin(userUsr, "");
        if (usrTemp!=null) saltUsr = usrTemp.getSaltUsr();

        String passRes   = GlobalFunctions.generateSHA1(saltUsr+passUsr);
        Users loggedUser = userDao.login(userUsr, passRes);
        if (loggedUser != null) {
            String countryCode = (String)this.getSession().get(APConstants.COUNTRY_CODE);
//            loggedUser.setCountryUsr(new IdiomCountry(countryCode));        
            userDao.save(loggedUser);
            this.getSession().put(APConstants.SESSION_USER, loggedUser);
            return SUCCESS;
        } else {
            return INPUT;
        }
    }

    /**
     * Encargado de verificar con el usuario y el codigo que se le envia por correo, para 
     * poder que se habilite en el sistema
     * @return Estado del proceso
     */
    public String verifyUser() {
        String codVal   = this.getRequest().getParameter("codVal");
        String nameUser = this.getRequest().getParameter("nameUser");
        if (nameUser != null && codVal != null) {
            Users loggedUser = userDao.getUserByCode(nameUser.trim(), codVal.trim());
            if (loggedUser != null) {
                loggedUser.setCodValidationUsr("");
                loggedUser.setStatus(1);
                String countryCode = (String)this.getSession().get(APConstants.COUNTRY_CODE);
//                loggedUser.setCountryUsr(new IdiomCountry(countryCode));            
                userDao.save(loggedUser);
                this.getSession().put(APConstants.SESSION_USER, loggedUser);
//          LOG.info("User " + user.getEmail() + " logged in successfully.");
                return SUCCESS;
            } else {
//          LOG.info("User " + user.getEmail() + " tried to logged in but failed.");
                return BaseAction.NOT_FOUND;
            }
        } else {
            return BaseAction.NOT_POSSIBLE;
        }

    }
    
    /**
     * Encargado de verificar con el usuario y el codigo que se le envia por celular, para
     * poder que se habilite en el sistema
     * @return Estado del proceso
     */
    public String verifyUserManual() {
        String codVal   = this.getRequest().getParameter("codVal");
        String nameUser = this.getRequest().getParameter("nameUser");
        Users loggedUser = userDao.getUserByCode(nameUser.trim(), codVal.trim());
        this.setAssociation_list(null);
        if (loggedUser != null) {
            loggedUser.setCodValidationUsr("");
            loggedUser.setStatus(1);
            String countryCode = (String)this.getSession().get(APConstants.COUNTRY_CODE);
//            loggedUser.setCountryUsr(new IdiomCountry(countryCode));            
            userDao.save(loggedUser);
            this.getSession().put(APConstants.SESSION_USER, loggedUser);
//          LOG.info("User " + user.getEmail() + " logged in successfully.");
            info = "accessPlatform.action";
            return "states";
        } else {
            state = "failure";
            info  = getText("message.invalidinfocellphone.login");
            return "states";
        }

    }
    
    /**
     * Encargado de obtener el usuario y el codigo que se le envia a un usuario por celular para 
     * poder redirigirlo a la seccion donde puede generar una nueva contraseña
     * @return Estado del proceso
     */
    public String verifyUserToRestoreMan() {
        String codVal   = this.getRequest().getParameter("codVal");
        String nameUser = this.getRequest().getParameter("nameUser");
        this.setAssociation_list(null);
        info = "verifyUserToRestore.action?codVal="+codVal+"&nameUser="+nameUser;
        return "states";
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
        user.setLastInUsr(new Date());
        userDao.save(user);
//        session.clear();
        session.remove(APConstants.SESSION_USER);
        return SUCCESS;
    }    
    
    /**
     * Propiedades de estado de las peticiones que se manejan a traves de AJAX
     */
    protected String state;
    protected String info;
    
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
        String nameUser = this.getInfoUser(); 
        nameUser = nameUser.trim();
        String codValidation = "";
        try {
            codValidation = GlobalFunctions.getSalt();
        } catch (NoSuchAlgorithmException ex) {
//            java.util.logging.Logger.getLogger(ActionLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
//            java.util.logging.Logger.getLogger(ActionLogin.class.getName()).log(Level.SEVERE, null, ex);
        }        
        this.setAssociation_list(null);
        Users loggedUser = userDao.getUserByLogin(nameUser, "");
        if (loggedUser != null) {
//          LOG.info("User " + user.getEmail() + " logged in successfully.");            
            loggedUser.setStatus(2);
            boolean isNum = ValidatorUtil.validateNumber(nameUser);            
            if (isNum) {
                String randomCode = GlobalFunctions.getRandomKey();
                String messageSms = getText("message.restorepassword.login")+" "+randomCode;                
                loggedUser.setCodValidationUsr(randomCode);
                GlobalFunctions.sendSms(loggedUser.getNameUserUsr(), messageSms);
            } else {
                loggedUser.setCodValidationUsr(codValidation);
                GlobalFunctions.sendEmail(loggedUser.getNameUserUsr(), getText("email.from"), getText("email.fromPass"), getText("email.subjectNewUser"), GlobalFunctions.messageToRestoreUser(this.getRequest().getLocalAddr(), loggedUser.getNameUserUsr(), codValidation), null);
            }
            userDao.save(loggedUser);            
            state = "success";
            info  = getText("message.sendinfoviaemailcellphone.login");            
        } else {
//          LOG.info("User " + user.getEmail() + " tried to logged in but failed.");
            addFieldError("infoUser", "Campo obligatorio");
            state = "failure";
            info  = getText("message.invaliduser.login");
        }
        
        return "states";
    } 
    
    //Datos de envio de informacion
    private String nameUser;
    private String celphone;
    private String telephone;
    private String whatneed;
    private String codVal;

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getCodVal() {
        return codVal;
    }

    public void setCodVal(String codVal) {
        this.codVal = codVal;
    }
    
    public String getCelphone() {
        return celphone;
    }

    public void setCelphone(String celphone) {
        this.celphone = celphone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getWhatneed() {
        return whatneed;
    }

    public void setWhatneed(String whatneed) {
        this.whatneed = whatneed;
    }
    
    
    
    
    /**
     * Encargado de enviar el correo sobre las inquietudes del usuario
     * @return Estado del proceso
     */
    public String sendInformation() {
        state = "success";
        info  = getText("message.sendissuetoadmin.login")+".";
        GlobalFunctions.sendEmail(getText("email.from"), getText("email.from"), getText("email.fromPass"), getText("email.subjectContact"), GlobalFunctions.messageToSendContact(this.getNameUser(), this.getEmailUser(), this.getWhatneed()), null);
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
//            String passRes = GlobalFunctions.generateSHA1(this.getPassRest());
//            String passResCon = this.getPassRestCon();
//            String saltUsr = GlobalFunctions.getSalt();
            
            Double salt = (Math.floor(Math.random()*999999+100000));
//            int valAss = salt.intValue();
            
            this.setAssociation_list(null);
//            String passTransform = GlobalFunctions.generateSHA1(this.getPasswordUser());
//            String passRes = GlobalFunctions.generateSHA1(this.getPassRest(), saltUsr);

            Users user = (Users) userDao.objectById(this.getIdUser());
            
            
            String saltUsr = GlobalFunctions.generateMD5(salt+user.getNameUserUsr());
            String passRes = GlobalFunctions.generateSHA1(saltUsr+this.getPassRest());
            
            user.setSaltUsr(saltUsr);
            user.setPasswordUsr(passRes);
            user.setStatus(1);
            session.saveOrUpdate(user);
            
            
            SfGuardUserDao sfDao = new SfGuardUserDao();
            SfGuardUser sfUser = sfDao.getUserByLogin(user.getCreatedBy(), user.getNameUserUsr(), "");
            sfUser.setSalt(saltUsr);
            sfUser.setPassword(passRes);
            sfDao.save(sfUser);

//            LogEntities logPro = new LogEntities();
//            logPro.setIdLogEnt(null);
//            logPro.setIdEntityLogEnt(ent.getIdEnt()); 
//            logPro.setIdObjectLogEnt(user.getIdUsr());
//            logPro.setTableLogEnt("users");
//            logPro.setDateLogEnt(new Date());
//            logPro.setActionTypeLogEnt("M");
//            session.saveOrUpdate(logPro);
            tx.commit();
            state = "success";
            info  = getText("message.successrestorepassword.login");
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            state = "failure";
            info  = getText("message.failrestorepassword.login");
        }
//        } catch (NoSuchAlgorithmException ex) {
////            java.util.logging.Logger.getLogger(ActionLogin.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NoSuchProviderException ex) {
////            java.util.logging.Logger.getLogger(ActionLogin.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return "states";
    }
    

    /**
     * Metodo encargado de validar el formulario de un nuevo usuario a registrar
     */
    @Override
    public void validate() 
    {
        /*
         * Se evalua dependiendo a la accion realizada:
         * 1) login: Al momento de ingresar al sistema
         * 2) newuser: Al momento de crear un nuevo usuario
         * 3) restuser: Al momento de recuperar la informacion de un usuario
         * 4) changepass: Al momento de recuperar la contraseña
         */
//        System.out.println("ingress->"+this.getIngress());
        if (actExe.equals("login")) {            
            if (this.getUsername()==null || this.getUsername().isEmpty()) {
                addFieldError("username", getText("message.fieldusernamerequired.login"));
            }
            if (this.getPassword()==null || this.getPassword().isEmpty()) {
                addFieldError("password", getText("message.fieldpasswordrequired.login"));
            }
            if (!getFieldErrors().isEmpty()) {
                addActionError(getText("desc.fieldusernamerequired.login"));
            }
            
            if(this.getUsername()!=null && this.getPassword()!=null) {
                String userUsr = this.getUsername().trim();
                String passUsr = this.getPassword().trim();
                String saltUsr = "";         
                Users usrTemp  = userDao.getUserByLogin(userUsr, "");
                if (usrTemp!=null) saltUsr = usrTemp.getSaltUsr();

                String passRes   = GlobalFunctions.generateSHA1(saltUsr+passUsr);
                Users loggedUser = userDao.login(userUsr, passRes);                
                if (loggedUser == null) {
                    addFieldError("username", getText("message.fieldusernameinvalid.login"));
                    addFieldError("password", getText("message.fieldusernameinvalid.login"));
                    addActionError(getText("desc.fieldusernameinvalid.login"));
                }
            }
            
        } else if (actExe.equals("newuser")) {           
            HashMap required = new HashMap();
            required.put("typeUser", typeUser);
            if (typeUser==1) {            
                required.put("workType", workType);
                if (workType==3 || workType==4 || workType==5) {
                    required.put("idAssoExt", idAssoExt);
                }
            } else if (typeUser==3) {
                if (this.getEmailRep()!=null || !this.getEmailRep().isEmpty()) {
                    if (!ValidatorUtil.validateEmail(this.getEmailRep())) {
                        addFieldError("emailRep", getText("message.invalidemail.login"));
                        this.setPassword(null);
                    }
                }
                required.put("emailRep", emailRep);
                required.put("pageLink", pageLink);
                required.put("direction", direction);    
                required.put("nameAsso", nameAsso);            
            }
//            required.put("emailUser", emailUser);
            required.put("passwordUser", passwordUser);    
            required.put("passwordRepUser", passwordRepUser);    
            boolean enter = false;
            
            for (Iterator it = required.keySet().iterator(); it.hasNext();) {
                String sK = (String) it.next();
                String sV = String.valueOf(required.get(sK));
                if (StringUtils.trim(sV).equals("null") || StringUtils.trim(sV)==null || StringUtils.trim(sV).equals("") || sV.equals("-1")) {
                    addFieldError(sK, getText("message.fieldsrequired.login"));
                    enter = true;
                }
            }

            
            if (enter) {
                addActionError(getText("message.missingfields.login"));
            }
            
            if ((this.getEmailUser()==null || this.getEmailUser().isEmpty()) && (this.getCelphoneUser()==null || this.getCelphoneUser().isEmpty())) {
//            if ((this.getEmailUser()==null || this.getEmailUser().isEmpty())) {
                addFieldError("emailUser", getText("message.needemailcellphone.login"));
                addFieldError("celphoneUser", getText("message.needemailcellphone.login"));
                addActionError(getText("desc.needemailcellphone.login"));
            }

            if ((this.getEmailUser()!=null || !this.getEmailUser().isEmpty()) && (this.getCelphoneUser()==null || this.getCelphoneUser().isEmpty())) {
                if (!ValidatorUtil.validateEmail(this.getEmailUser())) {
                    addFieldError("emailUser", getText("message.invalidemail.login"));
                    this.setPassword(null);
                }
            }            
            
            if (this.getPasswordUser()!=null && this.getPasswordUser().length() < 6) {
                addFieldError("passwordUser", getText("message.fieldincomplete.login"));
                addActionError(getText("desc.fieldincomplete.login"));
            }
            
//            if (this.getPasswordUser()!=null && this.getPasswordUser().length() > 10) {
//                addFieldError("passwordUser", "Campo muy largo");
//                addActionError("Debe ingresar una contraseña de menos de 10 caracteres");
//            }
            
//            System.out.println("datos->"+this.getRequest().getLocalAddr()+" datos1->"+this.getRequest().getLocalName()+" datos2->"+this.getRequest().getMethod());
//            this.getRequest().getRemoteAddr()            
//            if (!result.isValid()) {
//                addActionError("El codigo ingresado es incorrecto, intentelo nuevamente y recargue la imagen");
//            }
//            
//            captcha  = null;
//            result   = null;
//            recaptcha_challenge_field = ""; 
//            recaptcha_response_field  = "";
            
            if (getFieldErrors().isEmpty()) {
//                return NONE;
            }
            
//            boolean resVerify = ValidatorUtil.verifyCaptcha(this.getRequest().getRemoteAddr(), recaptcha_challenge_field, recaptcha_response_field);
//            if (!resVerify) {
//                addActionError("El codigo ingresado es incorrecto, intentelo nuevamente y recargue la imagen");
//            } 
            
            if ((this.getPasswordRepUser()==null || this.getPasswordRepUser().isEmpty()) || !this.getPasswordUser().equals(this.getPasswordRepUser())) {
                addFieldError("passwordUser", "");
                addFieldError("passwordRepUser", "");
                addActionError(getText("message.notsamepassword.login"));
            }

            if (this.getEmailUser()!=null || this.getCelphoneUser()!=null) {
                String nameUser = this.getEmailUser();
                if (this.getEmailUser()==null) nameUser = this.getCelphoneUser();
                Users resUser = userDao.checkUsername(nameUser);
                
                if (resUser!=null) {      
                    addFieldError("emailUser", getText("message.userexist.login"));
                    addActionError(getText("desc.userexist.login"));
                }
            }
            
        } else if (actExe.equals("restuser")) {
            if (this.getInfoUser()==null || this.getInfoUser().isEmpty()) {
                addFieldError("infoUser", getText("message.requireduser.login"));
                addActionError(getText("desc.requireduser.login"));
            }
        } else if (actExe.equals("changepass")) {
            
            if (this.getPassRest()!=null && this.getPassRest().length() < 6) {
                addFieldError("passRest", getText("message.fieldincompletepassword.login"));
                addActionError(getText("desc.fieldincompletepassword.login"));
            }

            if (this.getPassRest()!=null && this.getPassRest().length() > 10) {
                addFieldError("passRest", getText("message.fieldverylong.login"));
                addActionError(getText("desc.fieldverylong.login"));
            }
            
            if ((this.getPassRestCon()==null || this.getPassRestCon().isEmpty()) || !this.getPassRest().equals(this.getPassRestCon())) {
                addFieldError("passRest", "");
                addFieldError("passRestCon", "");
                addActionError(getText("message.notsamepassword.login"));
            }
            
        } else if (actExe.equals("contact")) {            
            HashMap required = new HashMap();
            required.put("nameUser", nameUser);
            required.put("emailUser", emailUser);
            required.put("whatneed", whatneed);
            boolean enterFields = false;
            for (Iterator it = required.keySet().iterator(); it.hasNext();) {
                String sK = (String) it.next();
                String sV = (String) required.get(sK);
                if (StringUtils.trim(sV).equals("null") || StringUtils.trim(sV)==null || StringUtils.trim(sV).equals("") || sV.equals("-1")) {
                    enterFields = true;
                    addFieldError(sK, getText("message.fieldsrequired.contact"));
                }
            }
            
            if (enterFields) {
                addActionError(getText("message.missingfields.contact"));
            }
        
        } else if (actExe.equals("verifyuser") || actExe.equals("changePassUser")) {            
            HashMap required = new HashMap();
            required.put("nameUser", nameUser);
            required.put("codVal", codVal);
            boolean enterFields = false;
            for (Iterator it = required.keySet().iterator(); it.hasNext();) {
                String sK = (String) it.next();
                String sV = (String) required.get(sK);
                if (StringUtils.trim(sV).equals("null") || StringUtils.trim(sV)==null || StringUtils.trim(sV).equals("") || sV.equals("-1")) {
                    enterFields = true;
                    addFieldError(sK, getText("message.fieldsrequiredverify.login"));
                }
            }
            
            if (enterFields) {
                addActionError(getText("message.missingfieldsverify.login"));
            }
        }
    }

    /**
     * Encargado de guardar la informacion al momento de crear un nuevo usuario
     * @return Estado del proceso
     */
    public String saveData() 
    {
        String action = "C";
//        if (actExe.equals("save")) {
//            action = "C";
//        } else if (actExe.equals("modify")) {
//            action = "M";
//        }
        if (action.equals("C")) {
            ReCaptcha captcha = ReCaptchaFactory.newReCaptcha("6Le3bu4SAAAAAAIy3mS2Ov8XerDrpgVxmWOShi9C", "6Le3bu4SAAAAAAdFTwmmT_2XuBKPGUhfdlgpRseY", false);
            ReCaptchaResponse result = captcha.checkAnswer(this.getRequest().getRemoteAddr(), recaptcha_challenge_field, recaptcha_response_field);
            if (!result.isValid()) { 
                state = "failure";
                info  = getText("message.codeincorrect.login");         
                return "states";
            }
        }
        this.setAssociation_list(null);
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Transaction tx  = session.beginTransaction();       
        
        
        try {
            String codValidation = GlobalFunctions.getSalt();
//            String saltUsr       = GlobalFunctions.getSalt();
            Double salt = (Math.floor(Math.random()*999999+100000));
//            int valAss = salt.intValue();
            String saltUsr = GlobalFunctions.generateMD5(salt+this.getEmailUser());
            String passTransform = GlobalFunctions.generateSHA1(saltUsr+this.getPasswordUser());
            
            Entities ent = new Entities();
            ent.setIdEnt(null);
//            ent.setEntityTypeEnt(1);
            ent.setEntitiesTypes(new EntitiesTypes(this.getTypeUser()));
            if(this.getCelphoneUser()!=null && !this.getCelphoneUser().equals("")) ent.setCellphoneEnt(Long.parseLong(this.getCelphoneUser()));
            ent.setEmailEnt(this.getEmailUser());
            ent.setStatus(true);
            if (this.getTypeUser()==3) {
                ent.setEmail2Ent(this.getEmailRep());
                ent.setPageLinkEnt(this.getPageLink());
                ent.setAddressEnt(this.getDirection());
                ent.setNameEnt(this.getNameAsso());  
            }
            session.saveOrUpdate(ent);
//            entDao.save(ent);

            /*LogEntities log = new LogEntities();
            log.setIdLogEnt(null);
            log.setIdEntityLogEnt(ent.getIdEnt());
            log.setIdObjectLogEnt(ent.getIdEnt());
            log.setTableLogEnt("entities");
            log.setDateLogEnt(new Date());
            log.setActionTypeLogEnt(action);
            session.saveOrUpdate(log);*/
//            logDao.save(log);
                        
//            System.out.println("Result = "+result);
            if (this.getTypeUser() == 2) {
                Producers pro = new Producers();
                pro.setIdPro(null);
                pro.setEntities(ent);
                pro.setStatus(true);
                session.saveOrUpdate(pro);

                /*LogEntities logPro = new LogEntities();
                logPro.setIdLogEnt(null);
                logPro.setIdEntityLogEnt(ent.getIdEnt());
                logPro.setIdObjectLogEnt(pro.getIdPro());
                logPro.setTableLogEnt("producers");
                logPro.setDateLogEnt(new Date());
                logPro.setActionTypeLogEnt(action);
                session.saveOrUpdate(logPro);*/
//                logDao.save(logPro);

            } else if (this.getTypeUser() == 1) {                
                ExtensionAgents ext = new ExtensionAgents();
                ext.setIdExtAge(null);
                ext.setEntities(ent);
                ext.setWorkTypeExtAge(new WorkTypeExtAgent(this.getWorkType()));                
                if (workType==3 || workType==4 || workType==5) {
                    ext.setStatus(false);
                } else {
                    ext.setStatus(true);
                }
                session.saveOrUpdate(ext);  
                if (this.getIdAssoExt()!=null && !this.getIdAssoExt().equals(" ")) {
                    Integer idAss = Integer.parseInt(this.getIdAssoExt());
                    AgentsAssociation agAsc = new AgentsAssociation();
                    agAsc.setAssociation(new Association(idAss));
                    agAsc.setExtensionAgents(new ExtensionAgents(ext.getIdExtAge()));
                    session.saveOrUpdate(agAsc);
//                    ext.setIdAssoExtAge(new Association(idAss));
                }

                /*LogEntities logPro = new LogEntities();
                logPro.setIdLogEnt(null);
                logPro.setIdEntityLogEnt(ent.getIdEnt());
                logPro.setIdObjectLogEnt(ext.getIdExtAge());
                logPro.setTableLogEnt("extension_agents");
                logPro.setDateLogEnt(new Date());
                logPro.setActionTypeLogEnt(action);
                session.saveOrUpdate(logPro);*/
//                logDao.save(logPro);

            } else if (this.getTypeUser() == 3) {
                Association asc = new Association();
                asc.setIdAsc(null);
                asc.setEntities(ent);
                asc.setNameAsc(this.getNameAsso());
                asc.setStatus(1);
                session.saveOrUpdate(asc);

                /*LogEntities logPro = new LogEntities();
                logPro.setIdLogEnt(null);
                logPro.setIdEntityLogEnt(ent.getIdEnt());
                logPro.setIdObjectLogEnt(asc.getIdAsc());
                logPro.setTableLogEnt("association");
                logPro.setDateLogEnt(new Date());
                logPro.setActionTypeLogEnt(action);
                session.saveOrUpdate(logPro);*/
            }

            String nameUser = null;
            if (!this.getEmailUser().isEmpty()) {
                nameUser = this.getEmailUser();
            } else if (!this.getCelphoneUser().isEmpty()) {
                nameUser = this.getCelphoneUser();
            }
            

            SfGuardUserDao sfDao = new SfGuardUserDao();
            SfGuardUser sfUser   = sfDao.getUserByLogin(null, nameUser, "");    
            
            if (sfUser==null) {
                sfUser = new SfGuardUser();
                sfUser.setEmailAddress(this.getEmailUser());
                sfUser.setFirstName("");
                sfUser.setLastName("");
                sfUser.setAlgorithm("sha1");
                sfUser.setSalt(saltUsr);
                sfUser.setPassword(passTransform);
                sfUser.setIsActive(true);
                sfUser.setIsSuperAdmin(false);
                sfUser.setUsername(nameUser);
                sfUser.setCreatedAt(new Date());
                sfUser.setUpdatedAt(new Date());
                sfUser.setCanLogin(false);                
            } else {
                sfUser.setSalt(saltUsr);
                sfUser.setPassword(passTransform);
                sfUser.setUpdatedAt(new Date());
            }
            sfDao.save(sfUser);
                
            String userContact = this.getCelphoneUser();
            boolean isNum = ValidatorUtil.validateNumber(userContact);
            String randomCode = GlobalFunctions.getRandomKey();
                
            Users user = new Users();
            user.setIdUsr(null);
            user.setNameUserUsr(nameUser);
//            user.setPasswordUsr(this.getPasswordUser());
            user.setSaltUsr(saltUsr);
            user.setPasswordUsr(passTransform);
            if (isNum) {
                user.setCodValidationUsr(randomCode);
            } else {
                user.setCodValidationUsr(codValidation);
            }            
            user.setLastInUsr(null);
            user.setStatus(2);//Estado inhabilitado hasta confirmar
//            user.setCreatedBy(sfUser.getId().intValue());
            session.saveOrUpdate(user);
//            userDao.save(user);
            
            
            /*LogEntities logPro = new LogEntities();
            logPro.setIdLogEnt(null);
            logPro.setIdEntityLogEnt(ent.getIdEnt());
            logPro.setIdObjectLogEnt(user.getIdUsr());
            logPro.setTableLogEnt("users");
            logPro.setDateLogEnt(new Date());
            logPro.setActionTypeLogEnt(action);
            session.saveOrUpdate(logPro);*/
//            logDao.save(logPro);
//            throw new HibernateException("Error creando usuario entidades");
//            tx.rollback();
            
//            usrEntDao.saveUserEnt(ent.getIdEnt(), user.getIdUsr());
            
            UserEntity usrEnt = new UserEntity();
            usrEnt.setIdUsrEnt(null);
            usrEnt.setIdProjectUsrEnt(null);
            usrEnt.setUsers(user);
            usrEnt.setEntities(ent);
            usrEnt.setStatus(true);
            session.saveOrUpdate(usrEnt);
//            resUsrEnt = usrEntDao.save(usrEnt);
//            if(!resUsrEnt) tx.rollback();
            
////            resUsrEnt = usrEntDao.save(usrEnt);
////            if(!resUsrEnt) throw new HibernateException("Error creando usuario entidades");
////
            //Guardar el usuario segun el perfil designado
            int profile = 0;
            if(this.getTypeUser()==1) {
                profile = 4;
            } else if(this.getTypeUser()==2) {
                profile = 3;
            } else if(this.getTypeUser()==3) {
                profile = 5;
            }
						
            /*UsersProfiles usrPer = new UsersProfiles();
            Profiles prof = new Profiles(profile);
            usrPer.setId(new UsersProfilesId(user.getIdUsr(), prof.getIdPro()));
            usrPer.setUsers(user);
            usrPer.setProfiles(prof);
            usrPer.setIdProjectUsrPro(null);
            session.saveOrUpdate(usrPer);*/

            tx.commit();
            state = "success";
//            String host = this.getRequest().getRemoteHost();
            String host = "gisweb.ciat.cgiar.org";            
            String messageSms = getText("message.codeofactivation.login")+" "+randomCode;
            if (this.getTypeUser() == 3) {
                GlobalFunctions.sendEmail("contact@open-aeps.org", getText("email.from"), getText("email.fromPass"), getText("email.subjectNewUser"), GlobalFunctions.messageToValidateUser(host, user.getNameUserUsr()), null);
                info  = getText("message.successaddassociation.login");
            } else if (this.getTypeUser() == 1) {
                if (this.getWorkType()== 1 || this.getWorkType()== 2) {
                    if (!isNum) GlobalFunctions.sendEmail(this.getEmailUser(), getText("email.from"), getText("email.fromPass"), getText("email.subjectNewUser"), GlobalFunctions.messageToNewUser(host, user.getNameUserUsr(), codValidation), null);
                    if (isNum)  GlobalFunctions.sendSms(userContact, messageSms);
                    info  = getText("message.successaddagronomist.login");//Tener la posibilidad de enviarlo por celular
                } else {
                    //Enviar correo al representante del gremio o empresa privada encargado (PENDING)
                    GlobalFunctions.sendEmail("contact@open-aeps.org", getText("email.from"), getText("email.fromPass"), getText("email.subjectNewUser"), GlobalFunctions.messageToValidateUser(host, user.getNameUserUsr()), null);
                    info  = getText("message.successaddagronomistindependent.login");//Tener la posibilidad de enviarlo por celular
                }
            } else if (this.getTypeUser() == 2) {
                if (!isNum) GlobalFunctions.sendEmail(this.getEmailUser(), getText("email.from"), getText("email.fromPass"), getText("email.subjectNewUser"), GlobalFunctions.messageToNewUser(host, user.getNameUserUsr(), codValidation), null);
                if (isNum)  GlobalFunctions.sendSms(userContact, messageSms);
                info  = getText("message.successaddproducer.login");//Tener la posibilidad de enviarlo por celular
            }
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            state = "failure";
            info  = getText("message.failadduser.login");
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
//            java.util.logging.Logger.getLogger(ActionLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            ex.printStackTrace();
//            java.util.logging.Logger.getLogger(ActionLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            ex.printStackTrace();
            state = "failure";
            info  = getText("message.failaddmobileuser.login");
        } finally {
            session.close();
        }

        return "states";
//        return ERROR;
    }
}