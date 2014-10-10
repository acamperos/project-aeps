package org.aepscolombia.platform.controllers;

import javax.servlet.http.HttpServletRequest;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import java.io.InputStream;
import java.util.Map;
import org.aepscolombia.platform.models.entity.Users;
import org.aepscolombia.platform.util.APConstants;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
//import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase BaseAction
 *
 * Contiene los metodos para basico para interactuar con todos los modulos que se vayan a emplear en el sistema AEPS
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class BaseAction extends ActionSupport implements Preparable, SessionAware, ServletRequestAware {

    private static final long serialVersionUID = -740360140511380630L;
    private static final Logger LOG = LoggerFactory.getLogger(BaseAction.class);
    
    //Estado de accion
    /**
     * Estado que se puede encontrar un usuario al momento de interactuar con la plataforma
     */
    public static final String CANCEL = "cancel";
    public static final String NEXT = "next";
    public static final String NOT_LOGGED = "401";
    public static final String NOT_AUTHORIZED = "403";
    public static final String NOT_FOUND = "404";
    public static final String NOT_POSSIBLE = "400";    
    
    //Botones de accion
    /**
     * Botones que contienen acciones comunes entre modulos
     */
    protected boolean save;
    protected boolean next;
    protected boolean delete;
    protected boolean cancel;    
   

    //Metodos getter y setter por cada boton de accion
    /**
     * Metodos getter y setter por cada boton de accion
     */
    public String save() {
        return SUCCESS;
    }

    public String next() {
        return NEXT;
    }
    
    public String cancel() {
        return CANCEL;
    }

    public String delete() {
        return SUCCESS;
    }

    public void setCancel(boolean cancel) {
        this.cancel = true;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public void setSave(boolean save) {
        this.save = true;
    }

    public void setNext(boolean next) {
        this.next = true;
    }
    
    //Propiedades de estado de las peticiones que se manejan a traves de AJAX
    /**
     * Propiedades de estado de las peticiones que se manejan a traves de AJAX
     */
    protected String state;
    protected String info;
    public String actExe = "";   
    public String viewInfo;

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
    
    public String getActExe() {
        return actExe;
    }

    public void setActExe(String actExe) {
        this.actExe = actExe;
    }

    public String getViewInfo() {
        return viewInfo;
    }

    public void setViewInfo(String viewInfo) {
        this.viewInfo = viewInfo;
    }
    
    //Variable para el manejo de peticiones del cliente
    /**
     * Variable para el manejo de peticiones del cliente
     */
    private HttpServletRequest request;
    
    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
    
    public HttpServletRequest getRequest() {
        return request;
    }

    public BaseAction() {
    }    

    @Override
    public String execute() throws Exception {
        if (save) {
            return save();
        } else if (delete) {
            return delete();
        } else if (cancel) {
            return cancel();
        } else if (next) {
            return next();
        }
        return INPUT;
    }
    
    
    @Override
    public void prepare() throws Exception {
    }
   

    /**
     * Valida si el usuario se encuentra logeado o no en el sistema
     *
     * @return el estado en se encuetra el usuario (true en caso correcto y false en caso contrario)
     */
    public boolean isLogged() {
        if (this.getCurrentUser() == null) {
            return false;
        }
        return true;
    }

    /**
     * Obtiene el usuario que esta actualmente habilitado para la sesion
     *
     * @return el objeto de usuario encontrado o vacio (null) en caso contrario
     */
    public Users getCurrentUser() {
        Users u = null;
        try {
            u = (Users) session.get(APConstants.SESSION_USER);
        } catch (Exception e) {
//      LOG.warn("There was a problem trying to find the user in the session.");
        }
        return u;
    }
    
       
    /**
     * Bloque correspondiente a la sesion de un usuario
     *
     */
    private Map<String, Object> session;

    public Map<String, Object> getSession() {
        return session;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}