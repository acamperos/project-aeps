/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aepscolombia.platform.controllers;

//package com.jgeppert.struts2.bootstrap.;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.*;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

@ParentPackage(value = "org.aepscolombia.plataforma.controllers")
@InterceptorRef("jsonValidationWorkflowStack")
@Result(location = "validation.jsp", name = "success")
@Validations(requiredStrings = {
        @RequiredStringValidator(fieldName = "user", type = ValidatorType.FIELD, message = "Username is required"),
        @RequiredStringValidator(fieldName = "password", type = ValidatorType.FIELD, message = "Password is required"),
        @RequiredStringValidator(fieldName = "favourite", type = ValidatorType.FIELD, message = "Choose your Favourite Color"),
        @RequiredStringValidator(fieldName = "biograhy", type = ValidatorType.FIELD, message = "Biography is required")
}, expressions = {
        @ExpressionValidator(expression = "password.trim().length() > 5", message = "Password must have as minimum 6 Characters.")
}, fieldExpressions = {
        @FieldExpressionValidator(fieldName = "password", expression = "password.trim().length() > 6", message = "Password must have as minimum 6 Characters."),
        @FieldExpressionValidator(fieldName = "agree", expression = "agree == true", message = "Accept the Agreement.")
})
public class ValidationSubmit extends ActionSupport {

    private String user;
    private String password;
    private boolean agree = false;
    private String biograhy;
    private String favourite;

    public String execute() throws Exception {

        // Reset the Form Values
        user = "";
        password = "";
        agree = false;
        biograhy = "";
        favourite = "";

        addActionMessage("Thank you for Registration!");

        return SUCCESS;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAgree() {
        return agree;
    }

    public void setAgree(boolean agree) {
        this.agree = agree;
    }

    public String getBiograhy() {
        return biograhy;
    }

    public void setBiograhy(String biograhy) {
        this.biograhy = biograhy;
    }

    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }
}