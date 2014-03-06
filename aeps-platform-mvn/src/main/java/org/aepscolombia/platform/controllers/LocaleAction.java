/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aepscolombia.platform.controllers;

import com.opensymphony.xwork2.ActionContext;
import java.util.Locale;
import org.aepscolombia.platform.util.APConstants;

/**
 * Clase LocaleAction
 *
 * Contiene los metodos necesarios para realizar el cambio de idiomas del proyecto
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class LocaleAction extends BaseAction {
    
//    public LocaleAction() {
////        super();
//    }
    
    private String lang="";

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }   

    @Override
    public String execute() throws Exception {
        if (!lang.equals("")) {
            Locale locale = new Locale(lang);
    //        System.out.println("locale->"+ActionContext.getContext().getLocale());
            ActionContext.getContext().setLocale(locale);
            this.getSession().put(APConstants.SESSION_LANG, lang);
        }
//        return "states";
        return SUCCESS;
    }
}