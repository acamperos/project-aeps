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
                    <li id="nav1" class="separate"><s:a href="getting" cssClass="btn" targets="divBodyLayout">Recolección</s:a></li>
                    <li id="nav2" class="separate"><s:a href="reports" cssClass="btn" targets="divBodyLayout">Reportes</s:a></li>
                    <li id="nav3"><s:a href="viewIssue" cssClass="btn" targets="divBodyLayout">Reportar problema</s:a></li>
                </ul>
            </nav>
        </div>
<!--        <div class="container">
            <ul class="thumbnails">
                <li class="span4">
                    <div class="thumbnail">
                        <h3 align="center">Recolección de datos</h3>
                        <img src="http://placehold.it/320x200">
                        <div class="caption">
                            <a href="http://bootsnipp.com/" class="btn btn-primary btn-block">Abrir</a>
                        </div>
                    </div>
                </li>
                <li class="span4">
                    <div class="thumbnail">
                        <h3 align="center">Reportes</h3>
                        <img src="http://placehold.it/320x200">
                        <div class="caption">
                            <a href="http://bootsnipp.com/" class="btn btn-primary btn-block">Abrir</a>
                        </div>
                    </div>
                </li>
            </ul>
        </div>-->
        <div class="container">
            <div class="row">
                <div class="span12">
                    <h3>Resumen General</h3>
                </div>
            </div>
            <div class="panel">
                <div class="panel-body">
                    <%@page import="org.aepscolombia.platform.models.dao.UsersDao"%>
                    <%@page import="org.aepscolombia.platform.models.dao.LogEntitiesDao"%>
                    <%@page import="java.util.Date"%>
                    <% Integer idEnt = new UsersDao().getEntitySystem(user.getIdUsr()); %>
                    <% Date dateIngress = new LogEntitiesDao().getDateIngress(idEnt, user.getIdUsr()); %>
                    
                    <% request.setAttribute("dateLast", user.getLastInUsr()); %>
                    <% request.setAttribute("dateIngress", dateIngress); %>
                    <!-- <p>Reportes Generales</p> -->
                    <s:date name="%{#attr.dateLast}" format="dd/MM/yyyy" var="dateTransformLastlogin"/>
                    <s:date name="%{#attr.dateIngress}" format="dd/MM/yyyy" var="dateTransformIngress"/>
                    <h4>Ultima fecha de acceso: <s:property value="%{#dateTransformLastlogin}" /></h4>
                    <h4>Fecha de inscripcion al sistema: <s:property value="%{#dateTransformIngress}" /></h4>
<!--                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi elit dui, porta ac scelerisque placerat, rhoncus vitae sem. Nulla eget libero enim, facilisis accumsan eros.
                    </p>-->
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
