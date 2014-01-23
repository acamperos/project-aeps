/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aepscolombia.platform.controllers;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.validator.annotations.*;

import org.aepscolombia.platform.util.HibernateUtil;
import org.aepscolombia.platform.util.ValidatorUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


/**
 * Clase ActionContact
 *
 * Contiene los metodos necesarios para enviar una solicitud por correo
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ActionContact extends BaseAction {
    
    public ActionContact() {
//        super();
    }

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }
}