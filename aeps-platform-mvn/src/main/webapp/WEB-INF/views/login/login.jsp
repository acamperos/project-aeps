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
        <sj:head jqueryui="false"/>
        <sb:head includeScripts="true" includeScriptsValidation="true"/>
        <!--<link rel="stylesheet" href="scripts/css/jquery/jquery.validate.password.css"/>-->
        <script type="text/javascript" src="scripts/js/jquery/jquery.maskedinput.js"></script>
        <script type="text/javascript" src="scripts/js/jquery/jquery.numeric.js"></script>
        <script type="text/javascript" src="scripts/js/jquery/jquery.blockUI.js"></script>
        <script type="text/javascript" src="scripts/js/jquery/jquery.validate.js"></script>
        <script type="text/javascript" src="scripts/js/jquery/jquery.validate.password.js"></script>
        <script type="text/javascript" src="scripts/js/jquery/pwdMeter/jquery.pwdMeter.min.js"></script>
        <!--<link rel="stylesheet" href="scripts/css/generals/beoro.css">-->
        <link rel="stylesheet" href="scripts/css/generals/login.css">
        <link rel="stylesheet" href="scripts/css/font-awesome/css/font-awesome.min.css">
        <link href = 'http://fonts.googleapis.com/css?family=Istok+Web:400700400cursiva,700italicysubconjunto=latin,latin-ext' rel='stylesheet' type='text/css'>
        <!--<link rel="stylesheet" href="scripts/css/generals/main.css">-->
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
                                <label class="control-label" for="formLogin_username"><s:property value="getText('text.user.login')" /></label>
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
                            <!-- </div> -->
                            <div class="submit_sect">
                                <%--<sj:submit cssClass="btn btn-beoro-3" targets="result" value="Iniciar sesión" validate="true" validateFunction="bootstrapValidation"/>--%>
                                <sj:submit cssClass="btn btn-initial btn-large" onclick="addMessageProcess()" onCompleteTopics="completeLogin" value="%{getText('button.signin.login')}" validate="true" validateFunction="validationForm" />
                                <%--<sj:submit cssClass="btn btn-primary" validate="true" value="Iniciar sesion"/>--%>
                            </div>
                        </div>				
                        <script>
                            $.subscribe('completeLogin', function(event,data) {
                                $.unblockUI();
                            });
                        </script>
                    </s:form>
                </div>
                <!--<div id="result"></div>-->
                <div class="panel" style="display:none" id="divRestoreUser">
                    <h3 class="heading_main">No puede ingresar?</h3>
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
                                <!--<button type="submit" class="btn btn-primary">Recordar</button>-->
                                <sj:submit cssClass="btn btn-initial btn-large" onclick="addMessageProcess()" targets="divMessage" onCompleteTopics="completeRestore" value="Recuperar" validate="true" validateFunction="validationForm"/>
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
                    <h3 class="heading_main">Creaci&oacute;n de nuevo usuario</h3>
                    <s:form id="formNewUser" action="saveUser.action" method="post" theme="simple">
                        <s:hidden name="actExe" value="newuser"/>
                        <div class="form-group control-group">
                            <label class="control-label req" for="formNewUser_typeUser">Tipo de usuario:</label>
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
                                <label for="formNewUser_workType" class="control-label req">Usted como trabaja:</label>
                                <div class="controls">
                                    <s:select
                                        name="workType"
                                        list="#{'1':'Freelance', '2':'Unas fincas', '3':'Empresa privada', '4':'Gremio', '5':'Entidades nacionales'}"           
                                        headerKey="-1" 
                                        headerValue="---"   
                                        onchange="showOtherElementWorkType(this.value, 'divAssociationAdd');"
                                    />
                                </div>
                            </div>
                            <div class="hide" id="divAssociationAdd">
                                <div class="form-group control-group">
                                    <label class="control-label req" for="formNewUser_nameAssoExt">Nombre en donde trabaja:</label>
                                    <div class="controls">
                                        <s:textfield class="form-control" name="nameAssoExt"/>
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
                            <label class="control-label req" for="formNewUser_emailUser">Correo Electr&oacute;nico:</label>
                            <div class="controls">
                                <s:textfield type="email" class="form-control" id="formNewUser_emailUser" name="emailUser" placeholder="Ingrese correo"/>
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
                            <div class="span4 control-group">
                                <label class="control-label" for="formNewUser_celphoneUser">Celular:</label>
                                <div class="controls">
                                    <s:textfield class="form-control" id="formNewUser_celphoneUser" name="celphoneUser"/>                                   
                                </div>
                            </div>
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
                                <sj:submit cssClass="btn btn-initial btn-large" onclick="addMessageProcess()" targets="divMessage" onCompleteTopics="completeUser" value="Crear usuario" validate="true" validateFunction="validationForm"/>
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
                $('#divRegisterUser').slideUp();
                $('#divRestoreUser').slideUp();
                $('#divNewUser').slideDown();
            }            
        </script>
    </body>
</html>