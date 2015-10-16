
package org.aepscolombia.platform.interceptors;

import org.aepscolombia.platform.util.APConstants;


import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import java.util.Iterator;
import java.util.Map;

/**
 * Clase ViewActionInterceptor
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
    String namespace = (String)invocation.getProxy().toString();
    
//    System.out.println("values->"+namespace);
    
    Map params = invocation.getInvocationContext().getParameters();
    String addValues = "";
    int i=0;
    
    if (!params.isEmpty()) {
        Iterator entries = params.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String[] value = (String[]) entry.getValue();
            String valGet  = value[0];
            if(i==0) {
                addValues += "?"+entry.getKey()+ "=" + valGet;
            } else {
                addValues += "&"+entry.getKey()+ "=" + valGet;
            }
            i++;
        }
    }
    
    String actionNext   = (String)session.get("action");       
    String result = "";
    if (actionActual.equals(actionNext) && !actionActual.equals("dashboard") && !actionActual.equals("initial") && !actionActual.equals("initial")) {
        session.put("action", "");
        result = invocation.invoke();      
    } else if (actionActual.equals("home") || actionActual.equals("homePrivate")) {
        session.put("action", "");
        result = invocation.invoke();
    } else {
        session.put("actionUrl", actionActual+".action"+addValues);
        session.put("action", actionActual);
        result = APConstants.ACTION_PAGE;     
    }
    
    if(actionActual.equals("login") || actionActual.equals("signin") || actionActual.equals("initial") || actionActual.equals("principal")) result = invocation.invoke();
    
    return result;
  }


}
