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
public class ViewActionInterceptor extends AbstractInterceptor 
{

  private static final long serialVersionUID = 7739155018211386527L;

  @Override
  public String intercept(ActionInvocation invocation) throws Exception {    
    Map<String, Object> session = invocation.getInvocationContext().getSession();
    String actionActual = (String)invocation.getInvocationContext().getContext().get(invocation.getInvocationContext().ACTION_NAME);
    String namespace = (String)invocation.getProxy().getNamespace();
    
    String actionNext   = (String)session.get("action");       
    String result = "";
    if (actionActual.equals(actionNext) && !actionActual.equals("dashboard") && !actionActual.equals("initial")) {
        session.put("action", "");
        result = invocation.invoke();      
    } else {
        session.put("action", actionActual);
        result = APConstants.ACTION_PAGE;     
    }
    
    if(actionActual.equals("login") || actionActual.equals("signin")) result = invocation.invoke();
    
    return result;
  }


}
