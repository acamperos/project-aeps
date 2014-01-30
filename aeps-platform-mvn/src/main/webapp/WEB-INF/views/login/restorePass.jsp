<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%--<%@ taglib uri="http://code.google.com/p/jcaptcha4struts2/taglib/2.0" prefix="jcaptcha" %>--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <link rel="icon" type="image/ico" href="favicon.ico">
        <title>AEPS</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width"> 				
        <sj:head jqueryui="true"/>
        <sb:head includeScripts="true" includeScriptsValidation="true"/>	
        <link rel="stylesheet" href="scripts/css/generals/beoro.css">
        <link rel="stylesheet" href="scripts/css/generals/login.css">
        <style>
            body {
                padding-top: 50px;
                padding-bottom: 20px;
            }
        </style>				
        <script type="text/javascript" src="scripts/js/generals/functions.js"></script>	
    </head>
    <body>
        <s:actionerror theme="bootstrap"/>
        <s:actionmessage theme="bootstrap"/>
        <s:fielderror theme="bootstrap"/>
        <div id="divMessage" style="display:none"></div>
        <div id="login-wrapper" class="clearfix">
            <div class="main-col">
                <!-- <img src="img/beoro.png" alt="" class="logo_img" /> -->
                <h3 class="logo_img">AEPS</h3>
                <div class="panel" id="divRestPass">
                    <p class="heading_main">Recuperacion de contraseña</p>
                    <s:form id="formRestCon" action="changePassUser.action" method="post" theme="simple">
                        <s:hidden name="actExe" value="changepass"/>
                        <s:hidden name="idUser"/>
                        <div class="form-group control-group">
                            <label class="control-label" for="formRestCon_passRest">Nueva Contrasena *:</label>
                            <div class="controls">
                                <s:password class="form-control" id="formRestCon_passRest" name="passRest"/>
                            </div>
                        </div>
                        <div class="form-group control-group">
                            <label class="control-label" for="formRestCon_passRestCon">Repetir contrasena *:</label>
                            <div class="controls">
                                <s:password class="form-control" id="formRestCon_passRestCon" name="passRestCon"/>
                            </div>
                        </div>
                        <div class="form-group control-group">
                            <!--<button type="submit" class="btn btn-primary">Crear usuario</button>-->
                            <sj:submit cssClass="btn btn-primary" targets="divMessage" onCompleteTopics="completeRestore" value="Restaurar contraseña" validate="true" validateFunction="validationForm"/>
                        </div>                            
                    </s:form>
                    <script>
                        $.subscribe('completeRestore', function(event, data) {
                            completeForm('', 'formRestCon', event.originalEvent.request.responseText);
                        });
                    </script>
                </div>
            </div>            
        </div>
    </body>
</html>