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
        <%@ include file="googleAnalytics.jsp" %>
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
                <li><s:a href="%{request.getContextPath()}/dashboard.action" targets="divBodyLayout"><i class="icon-home"></i><s:property value="getText('link.homeprivate')" /></s:a></li>
                <li><s:a href="%{request.getContextPath()}/getting.action" targets="divBodyLayout"><s:property value="getText('link.collectdata')" /></s:a></li>
                <li><span><s:property value="getText('link.soilstype')" /></span></li>
            </ul>
        </div>
        <div class="container">			       
            <div class="row"> 
                <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "soil/view")) { %>
                    <div class="span6 thumbnail custom-thumb">
                        <s:a href="%{contextPath}/soil/listSoil.action" role="button" targets="divBodyLayout"><img class="sprite sprite-soils" /></s:a>
                        <div class="caption">
                            <h3><s:property value="getText('title.soilrasta.soilstype')" /><span class="badge badge-success"><s:property value="numRas" /></span></h3>
                            <p><s:property value="getText('area.soilinfo.soilstype')" /></p>
                            <p>
                                <s:a cssClass="btn btn-initial" href="%{contextPath}/soil/listSoil.action" role="button" targets="divBodyLayout"><s:property value="getText('link.soilgo.soilstype')" /> <i class="icon-double-angle-right"></i></s:a>
                            </p>
                        </div>
                    </div>
                    <div class="span6 thumbnail custom-thumb" style="margin-left: 10px">
                <% } else { %>
                    <div class="span6 thumbnail custom-thumb">
                <% } %>
                <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "soil/view")) { %>
                        <s:a href="%{contextPath}/soilchemical/listSoilChemical.action" role="button" targets="divBodyLayout"><img class="sprite sprite-soils" /></s:a>
                        <div class="caption">
                            <h3><s:property value="getText('title.soilchemical.soilstype')" /><span class="badge badge-success"><s:property value="numSoils" /></span></h3>
                            <p><s:property value="getText('area.soilchemicalinfo.soilstype')" /></p>
                            <p>
                                <s:a cssClass="btn btn-initial" href="%{contextPath}/soilchemical/listSoilChemical.action" role="button" targets="divBodyLayout"><s:property value="getText('link.soilchemicalgo.soilstype')" /> <i class="icon-double-angle-right"></i></s:a>
                            </p>
                        </div>
                    </div>
                <% } %>                
            </div>
        </div>
    </body>
</html>
