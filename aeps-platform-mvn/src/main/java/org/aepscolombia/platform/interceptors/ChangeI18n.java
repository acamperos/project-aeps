/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aepscolombia.platform.interceptors;

import org.aepscolombia.platform.util.APConstants;


import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import java.util.Locale;
import java.util.Map;
import org.aepscolombia.platform.models.entity.Users;

/**
 * Clase ChangeI18n
 *
 * Este interceptor se encarga de obtener la localizacion definido para el sistema en una variable de sesion y este
 * se reasigna automaticamente
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ChangeI18n extends AbstractInterceptor 
{

  private static final long serialVersionUID = 7739155018211386527L;

  @Override
  public String intercept(ActionInvocation invocation) throws Exception {    
    Map<String, Object> session = invocation.getInvocationContext().getSession();
    String lang   = (session.get(APConstants.SESSION_LANG)!=null) ? (String)session.get(APConstants.SESSION_LANG) : "";
    String actionActual = (String)invocation.getInvocationContext().getContext().get(invocation.getInvocationContext().ACTION_NAME);
    String actionNext   = (String)session.get("action");       
    Users user = (Users) session.get(APConstants.SESSION_USER);
    
    if (!lang.equals("")) {
        Locale locale = new Locale(lang);
        invocation.getInvocationContext().setLocale(locale);
    }
    
    return invocation.invoke();
  }


}
