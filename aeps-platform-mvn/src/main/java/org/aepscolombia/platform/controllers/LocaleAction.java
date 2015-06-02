
package org.aepscolombia.platform.controllers;

import com.opensymphony.xwork2.ActionContext;
import java.util.Locale;
import java.util.Map;
import org.aepscolombia.platform.util.APConstants;
import org.aepscolombia.platform.util.GlobalFunctions;

/**
 * Clase LocaleAction
 *
 * Contiene los metodos necesarios para realizar el cambio de idiomas del proyecto
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class LocaleAction extends BaseAction 
{    
    
    private String lang="";

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }   

    @Override
    public String execute() throws Exception {
//        if (!lang.equals("")) {            
    //        System.out.println("locale->"+ActionContext.getContext().getLocale());
//        Map<String, Object> userSession=this.getSession();
//        String langTemp    = "esco";//(String)userSession.get(APConstants.SESSION_LANG);
//        System.out.println("langTempNew=>"+langTemp);
//        if (langTemp == null || langTemp.equals("")) {
        String countryCode = "";
        try {
            countryCode = (String)this.getRequest().getParameter("countryCode");
        } catch(Exception e) {
            countryCode = "-1";
        } 
        Map<String, Object> userSession=ActionContext.getContext().getSession();
        String langTemp    = (String)userSession.get(APConstants.SESSION_LANG);
//        System.out.println("langAssign=>"+langTemp);
//        System.out.println("lang=>"+lang);
        countryCode = "NI";
        userSession.put(APConstants.COUNTRY_CODE, countryCode);
        Locale locale=null;
        if (langTemp!=null) locale = new Locale(langTemp);
//        ActionContext.getContext().setLocale(locale);
        
        if ((langTemp==null || langTemp.equals("")) || ((lang!=null && !lang.equals("")) && !lang.equals("en"))) {
            String language = GlobalFunctions.getLanguageByCountryCode(countryCode);
//            System.out.println("language=>"+language+"-lang=>"+lang);
            if ((lang==null || lang.equals("")) || language.equals(lang)) {
                lang = language+countryCode.toLowerCase();
            }                 
            locale      = new Locale(lang);            
    //            this.getSession().put(APConstants.SESSION_LANG, lang);
    //            Map<String, Object> userSession=ActionContext.getContext().getSession();
            userSession.put(APConstants.SESSION_LANG, lang);
            this.setSession(userSession);
        } else if ((lang!=null && !lang.equals("")) && !lang.equals("es")){
//            System.out.println("entreeee");
            locale      = new Locale(lang);
            userSession.put(APConstants.SESSION_LANG, lang);
            this.setSession(userSession);
//            lang = langTemp;
        }        
        ActionContext.getContext().setLocale(locale);
        
        return SUCCESS;
    }
    
    private String countryVal;

    public String getCountryVal() {
        return countryVal;
    }

    public void setCountryVal(String countryVal) {
        this.countryVal = countryVal;
    }
    
    
    public String getCountry() throws Exception {
        String countryCode = "";
        try {
            countryCode = (String)this.getRequest().getParameter("countryCode");
        } catch(Exception e) {
            countryCode = "-1";
        } 
        setCountryVal(countryCode);
        return SUCCESS;
    }
         
}