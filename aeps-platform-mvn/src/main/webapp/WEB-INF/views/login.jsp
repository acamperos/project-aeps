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
        <!-- <link href='http://fonts.googleapis.com/css?family=Open+Sans+Condensed:300' rel='stylesheet'> -->
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
            /*$(document).ready(function() {
             if($('#login-wrapper').length) {
             $("#login-wrapper").vAlign().hAlign()
             };
             if($('#login-validate').length) {
             $('#login-validate').validate({
             onkeyup: false,
             errorClass: 'error',
             rules: {
             login_name: { required: true },
             login_password: { required: true }
             }
             })
             }
             if($('#forgot-validate').length) {
             $('#forgot-validate').validate({
             onkeyup: false,
             errorClass: 'error',
             rules: {
             forgot_email: { required: true, email: true }
             }
             })
             }
             $('#pass_login').click(function() {
             $('.panel:visible').slideUp('200',function() {
             $('.panel').not($(this)).slideDown('200');
             });
             $(this).children('span').toggle();
             });
             });*/
            $(document).ready(function() {
                $('#remPass').click(function() {
                    //$('.panel:visible').slideUp('200',function() {
                    //$('.panel').not($(this)).slideDown('200');
                    //});
                    $('#divRegisterUser').slideUp('200');
                    $('#divNewUser').slideUp('200');
                    $('#divRestoreUser').slideDown('200');
                    //$(this).children('span').toggle();
                });

                $('#accessSystem').click(function() {
                    $('#divNewUser').slideUp('200');
                    $('#divRestoreUser').slideUp('200');
                    $('#divRegisterUser').slideDown('200');
                    //$(this).children('span').toggle();
                });

                $('#newUser').click(function() {
                    $('#divRegisterUser').slideUp('200');
                    $('#divRestoreUser').slideUp('200');
                    $('#divNewUser').slideDown('200');
                    //$(this).children('span').toggle();
                });

                $('#accessSystemUser').click(function() {
                    $('#divNewUser').slideUp('200');
                    $('#divRestoreUser').slideUp('200');
                    $('#divRegisterUser').slideDown('200');
                });
            });
        </script>
    </head>
    <body>
        <div id="divMessage" style="display:none"></div>        
        <div id="login-wrapper" class="clearfix">            
            <div class="main-col">
                <!-- <img src="img/beoro.png" alt="" class="logo_img" /> -->
                <h3 class="logo_img">AEPS</h3>
                <s:actionerror theme="bootstrap"/>
                <s:actionmessage theme="bootstrap"/>
                <s:fielderror theme="bootstrap"/>        
                <div class="panel" id="divRegisterUser">
                    <p class="heading_main">Ingreso al Sistema</p>
                    <s:form id="formLogin" action="login.action" method="post" theme="simple">
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
                                <label class="control-label" for="formLogin_username">Usuario</label>
                                <div class="controls">
                                    <s:textfield id="formLogin_username" name="username"/>
                                </div>                         
                            </div>
                            <div class="span6 control-group">
                                <label class="control-label" for="formLogin_password">Contrase&ntilde;a</label>
                                <div class="controls">
                                    <s:password id="formLogin_password" name="password"/>
                                </div>
                            </div>
                            <!-- <div class="login_links"> -->
                            <div class="login_links span6">
                                <a href="javascript:void(0)" id="remPass">
                                    <!-- <span>Â¿Olvido su usuario y/o contrase&ntilde;a?</span> -->
                                    <span>¿Olvido su contrase&ntilde;a?</span>
                                </a>
                            </div>
                            <div class="login_links span6">
                                <a href="javascript:void(0)" id="newUser">
                                    <span>Registrarse</span>
                                </a>
                            </div>
                            <!-- </div> -->
                            <div class="submit_sect">
                                <%--<sj:submit cssClass="btn btn-beoro-3" targets="result" value="Iniciar sesión" validate="true" validateFunction="bootstrapValidation"/>--%>
                                <sj:submit cssClass="btn btn-beoro-3" replaceTarget="true" value="Iniciar sesión" validate="true" validateFunction="validationForm"/>
                                <%--<sj:submit cssClass="btn btn-beoro-3" validate="true" value="Iniciar sesion"/>--%>
                                <!--<button type="submit" class="btn btn-beoro-3">Iniciar sesi&oacute;n</button>-->
                            </div>
                        </div>									
                    </s:form>
                </div>
                <!--<div id="result"></div>-->
                <div class="panel" style="display:none" id="divRestoreUser">
                    <p class="heading_main">No puede ingresar?</p>
                    <s:form id="formValidate" action="restorePassword.action" method="post" theme="simple">
                        <s:hidden name="actExe" value="restuser"/>
                        <div class="row">
                            <!--                            <div class="span6">
                                                            <label for="formValidate_noidentify">Ingrese su cedula</label>
                            <%--<s:textfield id="formValidate_noidentify" name="noidentify" />--%>
                        </div> -->
                            <div class="span6 control-group">
                                <label class="control-label" for="formValidate_infoUser">Ingrese su correo/celular</label>
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
                                <!--<button type="submit" class="btn btn-beoro-3">Recordar</button>-->
                                <sj:submit cssClass="btn btn-beoro-3" targets="divMessage" onCompleteTopics="completeRestore" value="Recuperar" validate="true" validateFunction="validationForm"/>
                            </div>
                        </div>
                    </s:form>
                    <script>
                        $.subscribe('completeRestore', function(event, data) {
                            completeForm('', 'formValidate', event.originalEvent.request.responseText);
                        });
                    </script>
                </div>
                <div class="panel" style="display:none" id="divNewUser">
                    <p class="heading_main">Creaci&oacute;n de nuevo usuario</p>
                    <s:form id="formNewUser" action="saveUser.action" method="post" theme="simple">
                        <s:hidden name="actExe" value="newuser"/>
                        <div class="form-group control-group">
                            <label class="control-label" for="formNewUser_typeUser">Tipo de usuario *:</label>
                            <div class="controls">
                                <select class="form-control" id="formNewUser_typeUser" name="typeUser">
                                    <option value="1">Agricultor</option>
                                    <option value="2">Agronomo/Asistente</option>
                                    <option value="3">Gremio</option>
                                </select>
                            </div>   
                        </div>
                        <div class="form-group control-group">
                            <label class="control-label" for="formNewUser_emailUser">Correo Electr&oacute;nico *:</label>
                            <div class="controls">
                                <s:textfield class="form-control" id="formNewUser_emailUser" name="emailUser" placeholder="Ingrese correo"/>
                            </div>
                        </div>
                        <div class="form-group control-group">
                            <label class="control-label" for="formNewUser_passwordUser">Contrasena *:</label>
                            <div class="controls">
                                <s:password class="form-control" id="formNewUser_passwordUser" name="passwordUser"/>
                            </div>
                        </div>
                        <div class="form-group control-group">
                            <label class="control-label" for="formNewUser_passwordRepUser">Repetir contrasena *:</label>
                            <div class="controls">
                                <s:password class="form-control" id="formNewUser_passwordRepUser" name="passwordRepUser"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="span4 control-group">
                                <label class="control-label" for="formNewUser_celphoneUser">Celular *:</label>
                                <div class="controls">
                                    <s:textfield class="form-control" id="formNewUser_celphone" name="celphone"/>                                    
                                </div>
                            </div>
                            <!--                            <div class="span6">
                                                            <label for="formNewUser_telephone">Telefono :</label>
                                                            <input class="form-control" id="formNewUser_telephone">
                                                        </div>-->
                            <div class="login_links span6">
                                <a href="javascript:void(0)" id="accessSystemUser">
                                    <span>Ingreso al sistema</span>
                                </a>
                            </div>
                            <div class="span4">
                                <script type="text/javascript"
                                src="http://api.recaptcha.net/challenge?k=6LflSe0SAAAAAKRNMJfAcEmedhkzuSBFjGr8cbCA">
                                </script>
                                <noscript>
                                    <iframe src="http://api.recaptcha.net/noscript?k=6LflSe0SAAAAAKRNMJfAcEmedhkzuSBFjGr8cbCA"
                                    height="300" width="500" frameborder="0"></iframe>
                                    <br>
                                    <s:textarea name="recaptcha_challenge_field" rows="3" cols="40"/>
                                    <s:hidden name="recaptcha_response_field" value="manual_challenge"/>
                                </noscript>
                            </div>
                            <div class="span4">
                                <!--<button type="submit" class="btn btn-beoro-3">Crear usuario</button>-->
                                <sj:submit cssClass="btn btn-beoro-3" targets="divMessage" onCompleteTopics="completeUser" value="Crear usuario" validate="true" validateFunction="validationForm"/>
                            </div>
                        </div>                            
                    </s:form>
                    <script>
                        $.subscribe('completeUser', function(event, data) {
                            completeForm('', 'formNewUser', event.originalEvent.request.responseText);
                        });
                    </script>
                </div>
            </div>
            <!-- <div class="login_links">
                <a href="javascript:void(0)" id="pass_login"><span>Forgot password?</span><span style="display:none">Account login</span></a>
            </div> -->
        </div>
        <% String logSel = String.valueOf(request.getParameter("logSel"));%>
        <script>
            var val = "<%=logSel%>";
            if (val == 'new') {
                $('#divRegisterUser').slideUp();
                $('#divRestoreUser').slideUp();
                $('#divNewUser').slideDown();
            }            
        </script>
    </body>
</html>