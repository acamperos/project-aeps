<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <link rel="icon" type="image/ico" href="favicon.ico">
        <title>AEPS</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width"> 							
        <link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/css/bootstrap/bootstrap-responsive.min.css">
        <link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/css/bootstrap/bootstrap.min.css">
        <link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/css/generals/justified-nav.css">
        <link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/css/generals/main.css">
        <link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/css/generals/style.css">
        <link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/css/generals/responsive.css">
        <link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/css/generals/responsiveslides.css" />		
        <link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/css/generals/beoro.css">
    </head>
    <body>
        <%@ taglib prefix="s" uri="/struts-tags" %>
        <%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
        <div class="container">
            <div class="row">
                <div class="span12">
                    <div class="hero-unit center">
                        <h1>Permiso no autorizado <small><font face="Tahoma" color="red">Error 401</font></small></h1>
                        <br />
                        <p>Usted no se encuentra registrado en el sistema para poder realizar esta accion, por favor ingrese al sistema o comuniquese con el administrador respectivo. </p>
<!--                            Use your browsers <b>Back</b> button to navigate to the page you have prevously come from</p>
                        <p><b>Or you could just press this neat little button:</b></p>-->
                        <a href="<%= request.getContextPath() %>/initial.action" class="btn btn-large btn-info"><i class="icon-home icon-white"></i> Llevar al inicio</a>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
