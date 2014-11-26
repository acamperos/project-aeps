<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="icon" type="image/ico" href="img/favicon.ico">
    </head>
    <body>
        <%@ include file="../generals/googleAnalytics.jsp" %>
        <%@page import="org.aepscolombia.platform.models.entity.Users"%>
        <%@page import="org.aepscolombia.platform.models.dao.UsersDao"%>
        <%@page import="org.aepscolombia.platform.util.APConstants"%>
        <%@page import="org.aepscolombia.platform.models.dao.EntitiesDao"%>
        <% Users user = (Users) session.getAttribute(APConstants.SESSION_USER); %>
        <% UsersDao usrDao = new UsersDao(); %>
        <% Integer entTypeId = new EntitiesDao().getEntityTypeId(user.getIdUsr()); %>
        <div class="container">
            <ul id="breadcrumbs">
                <s:set id="contextPath"  value="#request.get('javax.servlet.forward.context_path')" />
                <li><s:a href="dashboard.action" targets="divBodyLayout"><i class="icon-home"></i>Inicio</s:a></li>
                <li><span>Reportes</span></li>
            </ul>
        </div>
        <div class="container">			
            <!-- Example row of columns -->
            <% if (entTypeId==1 || entTypeId==2) { %>
                <div class="row">             
                    <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "crop/view")) { %>
                        <div class="span6 thumbnail custom-thumb">
                            <s:a href="%{contextPath}/listCropRep.action" role="button" targets="divBodyLayout"><img class="img-responsive hidden-xs" src="img/crops.jpg" alt=""></s:a>
                            <div class="caption">
                                <h3>Productividad</h3>
                                <p>Ver la produccion en un periodo de tiempo determinado</p>                        
                                <p>
                                    <s:a cssClass="btn btn-initial" href="%{contextPath}/listCropRep.action" role="button" targets="divBodyLayout">Ir <i class="icon-double-angle-right"></i></s:a>
                                </p>
                            </div>
                        </div> 
                        <div class="span6 thumbnail custom-thumb" style="margin-left: 10px">                
                    <% } else { %>
                        <div class="span6 thumbnail custom-thumb"> 
                    <% } %>
                    <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "soil/view")) { %>
                            <s:a href="%{contextPath}/listSoilRep.action" role="button" targets="divBodyLayout"><img class="img-responsive hidden-xs" src="img/soils.jpg" alt=""></s:a>
                            <div class="caption">
                                <h3>Suelo</h3>
                                <p>Ver la informacion resultante de la caracterizacion del suelo</p>     
                                <p>
                                    <s:a cssClass="btn btn-initial" href="%{contextPath}/listSoilRep.action" role="button" targets="divBodyLayout">Ir <i class="icon-double-angle-right"></i></s:a>
                                </p>
                            </div>
                        </div>
                    <% } %>   
                </div>                            
            <% } %>
        </div>
    </body>
</html>
