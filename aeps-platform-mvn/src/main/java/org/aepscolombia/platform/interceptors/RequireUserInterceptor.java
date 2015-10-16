
package org.aepscolombia.platform.interceptors;

import com.opensymphony.xwork2.Action;
import org.aepscolombia.platform.controllers.BaseAction;
import org.aepscolombia.platform.util.APConstants;
import org.aepscolombia.platform.models.entity.Users;


import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import java.util.Map;
import org.aepscolombia.platform.models.dao.UsersDao;

/**
 * Clase RequireUserInterceptor
 *
 * Este interceptor se encarga de validar si el usuario se encuentra actualmente dentro del sistema o no, para asi habilitar el acceso
 * a los contenidos de la pagina o recurso especifico.
 * Si el usuario no cuenta con una sesion habilitada se retorna un error 401 (Autenticacion requerida)
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class RequireUserInterceptor extends AbstractInterceptor 
{

  private static final long serialVersionUID = 7739155018211386527L;

  @Override
  public String intercept(ActionInvocation invocation) throws Exception {
    String result = BaseAction.NOT_LOGGED;
    String actionActual = (String)invocation.getInvocationContext().getContext().get(invocation.getInvocationContext().ACTION_NAME);
//    invocation.getProxy().getConfig().getPackageName()
       
    Map<String, Object> session = invocation.getInvocationContext().getSession();
    Users user = (Users) session.get(APConstants.SESSION_USER);
//    
//    System.out.println("action->"+actionActual);    
    
    if (user != null && !actionActual.equals("signin")) {
//        Integer userAsign = new UsersDao().getEntitySystem(user.getIdUsr());
//        System.out.println("user asig->"+userAsign);
        result = invocation.invoke();
    }    
    
    if (user == null && actionActual.equals("signin")) {
        result = invocation.invoke();  
    } 
    
    if (user != null && actionActual.equals("signin")) {
        result = Action.SUCCESS;  
    }
    
//    } else {
//        if (actionActual.equals("login")) {
//            result = Action.INPUT;  
//        }
//    }
    return result;
  }


}
