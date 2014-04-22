<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <body>
        <div class="container">
            <nav>
                <ul id="menu" class="nav">
                    <li id="nav1"><s:a href="getting.action" cssClass="btn" targets="divBodyLayout">Recolección de Datos</s:a></li>
                </ul>
            </nav>
        </div>
        <div class="container">
            <div class="row">
                <div class="span12">
                    <h3>Resumen General</h3>
                </div>
            </div>
            <div class="panel">
                <div class="panel-body">
                    <% request.setAttribute("dateLast", user.getLastInUsr()); %>
                    <!-- <p>Reportes Generales</p> -->
                    <s:date name="%{#attr.dateLast}" format="dd/MM/yyyy" var="dateTransformLastlogin"/>
                    <h4>Ultima fecha de acceso: <s:property value="%{#dateTransformLastlogin}" /></h4>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi elit dui, porta ac scelerisque placerat, rhoncus vitae sem. Nulla eget libero enim, facilisis accumsan eros.
                    </p>
                </div>
            </div>				
            <!-- Example row of columns -->
            <!-- <div class="row">
                <div class="span4">
                    <img src="img/logo-google-play-vetor.png"/>
                    <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
                    <p><a class="btn btn-primary" href="#" role="button">View details Â»</a></p>
                </div>
                <div class="span4">
                    <h2>Heading</h2>
                    <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
                    <p><a class="btn btn-primary" href="#" role="button">View details Â»</a></p>
             </div>
                <div class="span4">
                    <h2>Heading</h2>
                    <p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Vestibulum id ligula porta felis euismod semper. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa.</p>
                    <p><a class="btn btn-primary" href="#" role="button">View details Â»</a></p>
                </div>
            </div> -->            
        </div>        
    </body>
</html>
