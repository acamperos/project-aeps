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
        <sj:head jqueryui="false"/>
        <sb:head includeScripts="true" includeScriptsValidation="true"/>
        <!--<link rel="stylesheet" href="scripts/css/jquery/jquery.validate.password.css"/>-->
        <script type="text/javascript" src="scripts/js/jquery/jquery.maskedinput.js"></script>
        <script type="text/javascript" src="scripts/js/jquery/jquery.numeric.js"></script>
        <script type="text/javascript" src="scripts/js/jquery/jquery.blockUI.js"></script>
        <script type="text/javascript" src="scripts/js/colorbox/jquery.colorbox.min.js"></script>
        <script type="text/javascript" src="scripts/js/jquery/pwdMeter/jquery.pwdMeter.min.js"></script>
        <link rel="stylesheet" href="scripts/css/colorbox/colorbox.css"/>
        <link rel="stylesheet" href="scripts/css/generals/login.css">
        <link rel="stylesheet" href="scripts/css/font-awesome/css/font-awesome.min.css">
        <link href = 'http://fonts.googleapis.com/css?family=Istok+Web:400700400cursiva,700italicysubconjunto=latin,latin-ext' rel='stylesheet' type='text/css'>
        <!--<link rel="stylesheet" href="scripts/css/generals/main.css">-->
        <script type="text/javascript" src="scripts/js/generals/functions.js"></script>﻿  
        <script type="text/javascript">            
            (function(a) {
                a.fn.vAlign = function() {
                    return this.each(function() {
                        var b = a(this).height(), c = a(this).outerHeight(), b = (b + (c - b)) / 2;
                        a(this).css("margin-top", "-" + b + "px");
                        a(this).css("top", "50%");
                        a(this).css("position", "absolute")
                    })
                }
            })(jQuery);
            (function(a) {
                a.fn.hAlign = function() {
                    return this.each(function() {
                        var b = a(this).width(), c = a(this).outerWidth(), b = (b + (c - b)) / 2;
                        a(this).css("margin-left", "-" + b + "px");
                        a(this).css("left", "50%");
                        a(this).css("position", "absolute")
                    })
                }
            })(jQuery);
            $(document).ready(function() {
                $('#remPass').click(function() {
                    $('#divRegisterUser').slideUp('200');
                    $('#divNewUser').slideUp('200');
                    $('#divValidateUser').slideUp('200');
                    $('#divChangePass').slideUp('200');
                    $('#divRestoreUser').slideDown('200');
                });

                $('#accessSystem').click(function() {
                    $('#divNewUser').slideUp('200');
                    $('#divRestoreUser').slideUp('200');
                    $('#divValidateUser').slideUp('200');
                    $('#divChangePass').slideUp('200');
                    $('#divRegisterUser').slideDown('200');
                });

                $('#newUser').click(function() {
                    $('#divRegisterUser').slideUp('200');
                    $('#divRestoreUser').slideUp('200');
                    $('#divValidateUser').slideUp('200');
                    $('#divChangePass').slideUp('200');
                    $('#divNewUser').slideDown('200');
                    //$(this).children('span').toggle();
                });

                $('#accessSystemUser').click(function() {
                    $('#divNewUser').slideUp('200');
                    $('#divRestoreUser').slideUp('200');
                    $('#divValidateUser').slideUp('200');
                    $('#divChangePass').slideUp('200');
                    $('#divRegisterUser').slideDown('200');
                });
                
                $('#accessSystemUserVer').click(function() {
                    $('#divNewUser').slideUp('200');
                    $('#divRestoreUser').slideUp('200');
                    $('#divValidateUser').slideUp('200');
                    $('#divChangePass').slideUp('200');
                    $('#divRegisterUser').slideDown('200');
                });
                
                $('#verUser').click(function() {
                    $('#divNewUser').slideUp('200');
                    $('#divRestoreUser').slideUp('200');
                    $('#divRegisterUser').slideUp('200');
                    $('#divChangePass').slideUp('200');
                    $('#divValidateUser').slideDown('200');
                });
                
                $('#accessSystemChangePass').click(function() {
                    $('#divNewUser').slideUp('200');
                    $('#divRestoreUser').slideUp('200');
                    $('#divValidateUser').slideUp('200');
                    $('#divChangePass').slideUp('200');
                    $('#divRegisterUser').slideDown('200');
                });
                
                $('#changePass').click(function() {
                    $('#divNewUser').slideUp('200');
                    $('#divRestoreUser').slideUp('200');
                    $('#divRegisterUser').slideUp('200');
                    $('#divValidateUser').slideUp('200');
                    $('#divChangePass').slideDown('200');
                });
            });
        </script>
    </head>
    <body>
        <%@ include file="../generals/googleAnalytics.jsp" %>
        <div id="divMessage" style="display:none;"></div>        
        <div id="login-wrapper" class="clearfix">            
            <div class="main-col">
                <!-- <img src="img/beoro.png" alt="" class="logo_img" /> -->
                <a href="initial.action" class="logo_img span3"><img src="img/logoAEPS.png"/></a>
<!--                <div class="formIngress" style="margin-bottom: 10px">
                    <s:url id="localeEN" namespace="/" action="localeLogin" >
                        <s:param name="lang">en</s:param>
                    </s:url>
                    <s:url id="localeES" namespace="/" action="localeLogin" >
                        <s:param name="lang">es</s:param>
                    </s:url>
                    <div class="span1" style="padding-top:10px; padding-right:15px">
                        <s:property value="getText('text.select.language')" />
                    </div>
                    <div class="btn-group">
                        <a class="btn" href="#"><s:property value="getText('text.language')" /></a>
                        <a class="btn dropdown-toggle" data-toggle="dropdown" href="#"><span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><s:a href="%{localeEN}"><img src="img/languages/kingdom-flat.png" class="img-rounded" /> <s:property value="getText('text.english')" /></s:a></li>
                            <li><s:a href="%{localeES}"><img src="img/languages/spain-flat.png" class="img-rounded" /> <s:property value="getText('text.spanish')" /></s:a></li>
                        </ul>
                    </div>
                </div>-->
                <!--<h3 class="logo_img"><a href="initial.action">AEPS</a></h3>-->
                <s:actionerror theme="bootstrap"/>
                <s:actionmessage theme="bootstrap"/>
                <s:fielderror theme="bootstrap"/>      
                <div class="panel" id="divRegisterUser">
                    <h3 class="heading_main"><s:property value="getText('text.titlecontact.login')" /></h3>
                    <s:form id="formLogin" action="signin.action">
                        <s:hidden name="actExe" value="login"/>
                        <!--                        <div class="control-group error">
                                                    <label class="control-label" for="inputError">Input with error</label>
                                                    <div class="controls">
                                                        <input type="text" id="inputError">
                                                        <span class="help-inline">Please correct the error</span>
                                                    </div>
                                                </div>-->
                        <div class="row">
                            <div class="span6 control-group">
                                <label class="control-label" for="formLogin_username">
                                    <s:property value="getText('text.user.login')" />
                                    <!--<img src="http://g.etfv.co/http://www.cnn.com" alt="Google" width="16" height="16" />-->
                                    <i class="icon-info-sign s2b_tooltip pop-over" data-content="El usuario es su correo electrónico que registro al inscribirse." data-title="Información" data-placement="right" data-trigger="hover"></i>
                                </label>
                                <div class="controls">
                                    <s:textfield id="formLogin_username" name="username"/>
                                </div>                         
                            </div>
                            <div class="span6 control-group">
                                <label class="control-label" for="formLogin_password"><s:property value="getText('text.password.login')" /></label>
                                <div class="controls">
                                    <s:password id="formLogin_password" name="password"/>
                                </div>
                            </div>
                            <!-- <div class="login_links"> -->
                            <div class="login_links span6">
                                <a href="javascript:void(0)" id="remPass">
                                    <span><s:property value="getText('text.forgetpass.login')" /></span>
                                </a>
                            </div>
                            <div class="login_links span6">
                                <a href="javascript:void(0)" id="newUser">
                                    <span><s:property value="getText('text.signup.login')" /></span>
                                </a>
                            </div>
                            <div class="login_links span6">
                                <a href="javascript:void(0)" id="verUser">
                                    <span><s:property value="getText('button.verifyuser.login')" /></span>
                                </a>
                            </div>
                            <div class="login_links span6">
                                <a href="javascript:void(0)" id="changePass">
                                    <span><s:property value="getText('button.changepasscel.login')" /></span>
                                </a>
                            </div>
                            <!-- </div> -->                            
                        </div>                 
                    </s:form>
                    <div class="submit_sect">
                        <sj:submit type="button" formIds="formLogin" cssClass="btn btn-initial btn-large" onclick="addMessageProcess(); ga('send', 'event', 'Register', 'click', 'SingIn');" onCompleteTopics="completeLogin" validate="true" validateFunction="validationForm"><s:property value="%{getText('button.signin.login')}"/></sj:submit>
                    </div>
                    <script>
                        $.subscribe('completeLogin', function(event,data) {
                            $.unblockUI();
//                                alert(event.originalEvent.request.responseText)
//                            document.location='http://localhost:8083/dashboard.action'
                        });
                    </script>
                </div>
                <!--<div id="result"></div>-->
                <div class="panel" style="display:none;" id="divRestoreUser">
                    <h3 class="heading_main">No puede ingresar ?</h3>
                    <s:form id="formValidate" action="restorePassword.action">
                        <s:hidden name="actExe" value="restuser"/>
                        <div class="row">
                            <!--                            <div class="span6">
                                                            <label for="formValidate_noidentify">Ingrese su cedula</label>
                            <%--<s:textfield id="formValidate_noidentify" name="noidentify" />--%>
                        </div> -->
                            <div class="span6 control-group">
                                <label class="control-label" for="formValidate_infoUser">Ingrese su correo ó celular</label>
                                <div class="controls">
                                    <s:textfield id="formValidate_infoUser" name="infoUser" />
                                </div>
                            </div>
                            <!--                            <div class="span6">
                            <label for="formValidate_email">Ingrese su correo</label>
                            <%--<s:textfield id="formValidate_email" name="email" />--%>
                        </div>-->
                            <div class="login_links span6">
                                <a href="javascript:void(0)" id="accessSystem">
                                    <span>Ingreso al sistema</span>
                                </a>
                            </div>
                            <div class="submit_sect">
                                <sj:submit type="button" id="btRestore" cssClass="btn btn-initial btn-large" onclick="addMessageProcess(); ga('send', 'event', 'Register', 'click', 'Remember');" targets="divMessage" onCompleteTopics="completeRestore" validate="true" validateFunction="validationForm">Recuperar</sj:submit>
                            </div>
                        </div>
                    </s:form>
                    <div class="hide">
                        <div id="confirm_dialog" class="cbox_content">
                            <div class="sepH_c">
                                <p>El correo electrónico que le mandamos puede ser filtrado como spam. <br />Si no encuentra el correo en su buzon principal,<br /> 
                                por favor no olvide de revisar el buzon de spam.</p>
                            </div>
                            <div>
                                <a href="#" class="btn btn-small confirm_ok">Ok</a>
                            </div>
                        </div>
                    </div>
                    <script>
                        $.subscribe('completeRestore', function(event, data) {
                            showDialogWarning('confirm_dialog');
                            completeForm('', 'formValidate', event.originalEvent.request.responseText);
                        });
                    </script>
                </div>
                <div class="panel" style="display:none;" id="divNewUser">
                    <h3 class="heading_main">Creaci&oacute;n de nuevo usuario</h3>
                    <s:form id="formNewUser" action="saveUser.action">
                        <s:hidden name="actExe" value="newuser"/>
                        <div class="form-group control-group">
                            <label class="control-label req" for="formNewUser_typeUser">
                                Tipo de usuario:
                                <i class="icon-info-sign itooltip s2b_tooltip pop-over" data-content="<strong>Use el perfil Productor</strong>: si usted administra directamente una o varias fincas de las cuales quiere registrar datos.<br/>
                                <strong>Use el perfil Agronomo</strong>: si usted asesora a varios productores de los cuales usted va a registrar datos (si ellos no pueden registrar sus datos ellos mismos).<br/>
                                <strong>Use el perfil Gremio</strong>: si usted es responsable de varios agronomos que colectan datos. Usted podra ver y administrar los datos de sus agronomos, pero no podra ingresar datos de fincas
                                ." data-title="Información" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <s:select
                                    name="typeUser"
                                    list="#{'2':'Productor', '1':'Agronomo/Asistente', '3':'Gremio'}"           
                                    headerKey="-1" 
                                    headerValue="---" 
                                    onchange="showOtherElementUser(this.value, 'divWorkType', 'divDataAssociation');"
                                />
                            </div>   
                        </div>
                        <div class="hide" id="divWorkType">
                            <div class="form-group control-group">
                                <label for="formNewUser_workType" class="control-label req">Usted trabaja como:</label>
                                <div class="controls">
                                    <s:select
                                        name="workType"
                                        list="#{'1':'Como independiente', '3':'Para empresa privada', '4':'Para gremio', '5':'Para entidades gubernamentales'}"           
                                        headerKey="-1" 
                                        headerValue="---"   
                                        onchange="showOtherElementWorkType(this.value, 'divAssociationAdd');"
                                    />
                                </div>
                            </div>
                            <div class="hide" id="divAssociationAdd">
                                <div class="form-group control-group">
                                    <label class="control-label req" for="formNewUser_idAssoExt">Sitio donde trabaja:</label>
                                    <div class="controls">
                                        <s:select
                                            name="idAssoExt" 
                                            list="association_list" 
                                            listKey="idAsc" 
                                            listValue="nameAsc"          
                                            headerKey=" " 
                                            headerValue="---"
                                        />
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group control-group hide" id="divDataAssociation">                     
                            <div class="form-group control-group">
                                <label class="control-label req" for="formNewUser_emailRep">Correo electrónico del representante legal:</label>
                                <div class="controls">
                                    <s:textfield type="email" name="emailRep" placeholder="Ingrese correo"/>
                                </div>
                            </div>
                            <div class="form-group control-group">
                                <label class="control-label req" for="formNewUser_pageLink">Pagina Web:</label>
                                <div class="controls">
                                    <s:textfield class="form-control" name="pageLink"/>
                                </div>
                            </div>
                            <div class="form-group control-group">
                                <label class="control-label req" for="formNewUser_direction">Direccion:</label>
                                <div class="controls">
                                    <s:textfield class="form-control" name="direction"/>
                                </div>
                            </div>
                            <div class="form-group control-group">
                                <label class="control-label req" for="formNewUser_nameAsso">Nombre del gremio:</label>
                                <div class="controls">
                                    <s:textfield class="form-control" name="nameAsso"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group control-group">
                            <label class="control-label" for="formNewUser_emailUser">Correo Electrónico:</label>
                            <div class="controls">
                                <s:textfield type="email" class="form-control" id="formNewUser_emailUser" name="emailUser" placeholder="Ingrese el correo"/>
                            </div>
                        </div>
                        <div class="form-group control-group">
                            <label class="control-label" for="formNewUser_celphoneUser">Celular:</label>
                            <div class="controls">
                                <s:textfield class="form-control" id="formNewUser_celphoneUser" name="celphoneUser"/>                                   
                            </div>
                        </div>
                        <div class="form-group control-group">
                            <div class="row-fluid">
                                <div class="span5" style="width: 48%;">
                                    <label class="control-label password req" for="formNewUser_passwordUser">Contrasena:</label>
                                    <label class="labelRequire" for="formNewUser_passwordUser">(Minimo 6 caracteres)</label>
                                    <div class="controls">
                                        <s:password class="form-control" id="formNewUser_passwordUser" name="passwordUser"/>
                                        <div id="pwdMeter" class="progress progress-danger">
                                            <div class="bar" style="width: 0%"></div>
                                            <span class="pwdText"></span>
                                        </div>
                                    </div>
                                    <script>
                                        if($('#formNewUser_passwordUser').length) {
                                            $('#formNewUser_passwordUser').pwdMeter({
                                                minLength: 6,
                                                displayGeneratePassword: false,
                                                neutralText:"",
                                                veryWeakText:"Muy Debil",
                                                weakText:"Debil",
                                                mediumText:"Normal",
                                                strongText:"Fuerte",
                                                veryStrongText:"Muy Fuerte"
                                            });  
                                        }
                                        
                                        $('.itooltip').popover({
//                                            placement: wheretoplace,
                                            html: true, 
                                            trigger: 'hover', 
                                            template: '<div class="popover whatever-you-want"><div class="arrow" style="top:65px;"></div><div class="popover-inner"><h3 class="popover-title"></h3><div class="popover-content"><p></p></div></div></div>'
                                        });
                                        
//                                        $('[data-toggle=popover]').popover();
//
//                                        $('[data-toggle=popover]').on('shown.bs.popover', function () {
//                                          $('.popover').css('top',parseInt($('.popover').css('top')) + 52 + 'px !important')
//                                        })

//                                        function wheretoplace(){
//                                            var width = window.innerWidth;
//                                            if (width<500) return 'bottom';
//                                            return 'right';
//                                        }
                                    </script>
                                </div>
                            </div>
                        </div>
                        <div class="form-group control-group">
                            <label class="control-label req" for="formNewUser_passwordRepUser">Repetir contrasena:</label>
                            <label class="labelRequire" for="formNewUser_passwordRepUser">(Minimo 6 caracteres)</label>
                            <div class="controls">
                                <s:password class="form-control" id="formNewUser_passwordRepUser" name="passwordRepUser"/>
                            </div>
                        </div>
                        <div class="row">                            
                            <div class="login_links span6">
                                <a href="javascript:void(0)" id="accessSystemUser">
                                    <span>Ingreso al sistema</span>
                                </a>
                            </div>
                            <div class="span4">
                                <p class="warnField reqBef">Campos Requeridos</p>
                            </div>
                            <div class="span4">
                                <s:hidden name="intoVal" value="in"/>
                                <%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
                                <%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>
                                <%
                                    ReCaptcha captcha = ReCaptchaFactory.newReCaptcha("6Le3bu4SAAAAAAIy3mS2Ov8XerDrpgVxmWOShi9C", "6Le3bu4SAAAAAAdFTwmmT_2XuBKPGUhfdlgpRseY", false);
                                    String captchaScript = captcha.createRecaptchaHtml(request.getParameter("error"), null);
                                    out.print(captchaScript);
                                %>
                            </div>                            
                            <div class="span4">
                                <!--<button type="submit" class="btn btn-primary">Crear usuario</button>-->
                                <sj:submit cssClass="btn btn-initial btn-large" onclick="addMessageProcess(); ga('send', 'event', 'Register', 'click', 'SignUp');" targets="divMessage" onCompleteTopics="completeUser" value="Crear usuario" validate="true" validateFunction="validationForm"/>
                            </div>
                        </div>                            
                    </s:form>
                    <script>
                        $.subscribe('completeUser', function(event, data) {
                            completeForm('', 'formNewUser', event.originalEvent.request.responseText);
                        });
                        if($('.pop-over').length) {
                            $('.pop-over').popover({html:true});
                        }
                    </script>
                </div>
                <div class="panel" style="display:none;" id="divValidateUser">
                    <h3 class="heading_main"><s:property value="getText('title.validatedata.login')" /></h3>
                    <s:form id="formVerify" action="verifyUserManual.action">
                        <s:hidden name="actExe" value="verifyuser"/>
                        <div class="row">
                            <div class="span6 control-group">
                                <label class="control-label req" for="formVerify_nameUser">
                                    <s:property value="getText('text.verifycel.login')" />
                                </label>
                                <div class="controls">
                                    <s:textfield id="formVerify_nameUser" name="nameUser"/>
                                </div>                         
                            </div>                                            
                        </div>    
                        <div class="row">
                            <div class="span6 control-group">
                                <label class="control-label req" for="formVerify_codVal">
                                    <s:property value="getText('text.codvalidation.login')" />
                                </label>
                                <div class="controls">
                                    <s:textfield id="formVerify_codVal" name="codVal"/>
                                </div>                         
                            </div>                                            
                        </div> 
                        <div class="row">    
                            <div class="login_links span6">
                                <a href="javascript:void(0)" id="accessSystemUserVer">
                                    <span>Ingreso al sistema</span>
                                </a>
                            </div>
                        </div> 
                    </s:form>
                    <div class="submit_sect">
                        <sj:submit type="button" formIds="formVerify" cssClass="btn btn-initial btn-large" onclick="addMessageProcess(); ga('send', 'event', 'Register', 'click', 'VerifyUser');" targets="divMessage" onCompleteTopics="completeVerify" validate="true" validateFunction="validationForm"><s:property value="%{getText('button.verifyuser.login')}"/></sj:submit>
                    </div>
                    <script>
                        $.subscribe('completeVerify', function(event,data) {                            
                            var json = jQuery.parseJSON(event.originalEvent.request.responseText);
                            if (json.state == 'failure') {
                                completeForm('', 'formVerify', event.originalEvent.request.responseText);
                            } else {
                                $.unblockUI();
//                                alert(json.info);
                                var host = window.location.host;
                                var protocol = window.location.protocol;
                                document.location = protocol+'//'+host+'/'+json.info;
                            }
                        });
                    </script>
                </div>
                <div class="panel" style="display:none;" id="divChangePass">
                    <h3 class="heading_main"><s:property value="getText('button.changepasscel.login')" /></h3>
                    <s:form id="formChangePass" action="verifyUserToRestoreMan.action">
                        <s:hidden name="actExe" value="changePassUser"/>
                        <div class="row">
                            <div class="span6 control-group">
                                <label class="control-label req" for="formChangePass_nameUser">
                                    <s:property value="getText('text.verifycel.login')" />
                                </label>
                                <div class="controls">
                                    <s:textfield id="formChangePass_nameUser" name="nameUser"/>
                                </div>                         
                            </div>                                            
                        </div>    
                        <div class="row">
                            <div class="span6 control-group">
                                <label class="control-label req" for="formChangePass_codVal">
                                    <s:property value="getText('text.codvalidation.login')" />
                                </label>
                                <div class="controls">
                                    <s:textfield id="formChangePass_codVal" name="codVal"/>
                                </div>                         
                            </div>                                            
                        </div> 
                        <div class="row">    
                            <div class="login_links span6">
                                <a href="javascript:void(0)" id="accessSystemChangePass">
                                    <span>Ingreso al sistema</span>
                                </a>
                            </div>
                        </div> 
                    </s:form>
                    <div class="submit_sect">
                        <sj:submit type="button" formIds="formChangePass" cssClass="btn btn-initial btn-large" onclick="addMessageProcess(); ga('send', 'event', 'Register', 'click', 'ChangePass');" targets="divMessage" onCompleteTopics="completeChangePass" validate="true" validateFunction="validationForm"><s:property value="%{getText('button.verifychangepass.login')}"/></sj:submit>
                    </div>
                    <script>
                        $.subscribe('completeChangePass', function(event,data) {                            
                            var json = jQuery.parseJSON(event.originalEvent.request.responseText);
                            if (json.state == 'failure') {
                                completeForm('', 'formChangePass', event.originalEvent.request.responseText);
                            } else {
                                $.unblockUI();
                                var host = window.location.host;
                                var protocol = window.location.protocol;
                                document.location = protocol+'//'+host+'/'+json.info;
                            }
                        });
                    </script>
                </div>
            </div>
        </div>
        <% String logSel = String.valueOf(request.getParameter("logSel"));%>
        <script>            
            $.mask.definitions['h'] = "[3]";
            $('#formNewUser_celphoneUser').mask("h999999999",{placeholder:""});
            $('#formNewUser_passwordRepUser').bind("cut copy paste",function(e) {
                e.preventDefault();
            });
            var val = "<%=logSel%>";
            if (val == 'new') {
                $('#divRegisterUser').slideUp('200');
                $('#divRestoreUser').slideUp('200');
                $('#divValidateUser').slideUp('200');
                $('#divChangePass').slideUp('200');
                $('#divNewUser').slideDown();
            }            
        </script>
    </body>
</html>