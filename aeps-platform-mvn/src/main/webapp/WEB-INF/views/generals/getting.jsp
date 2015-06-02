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
                <li><s:a href="dashboard.action" targets="divBodyLayout"><i class="icon-home"></i><s:property value="getText('link.homeprivate')" /></s:a></li>
                <li><span><s:property value="getText('link.collectdata')" /></span></li>
            </ul>
        </div>
        <div class="container">			
            <% if (entTypeId==1 || entTypeId==3) { %>
                <div class="row">             
                    <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "producer/view")) { %>
                        <div class="span6 thumbnail custom-thumb">
                            <s:a href="%{contextPath}/listProducer.action" role="button" targets="divBodyLayout"><img class="sprite sprite-producers" /></s:a>
                            <div class="caption">
                                <h3><s:property value="getText('title.producers.getting')" /><span class="badge badge-success"><s:property value="numPro" /></span></h3>
                                <p><s:property value="getText('area.producerinfo.getting')" /></p>
                                <p>   
                                    <s:a cssClass="btn btn-initial" href="%{contextPath}/listProducer.action" role="button" targets="divBodyLayout"><s:property value="getText('link.producergo.getting')" /> <i class="icon-white icon-double-angle-right"></i></s:a>                                                               
                                </p>
                            </div>
                        </div>
                        <div class="span6 thumbnail custom-thumb" style="margin-left: 10px">                
                    <% } else { %>
                        <div class="span6 thumbnail custom-thumb"> 
                    <% } %>
                    <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "farm/view")) { %>
                            <s:a href="%{contextPath}/listFarm.action" role="button" targets="divBodyLayout"><img class="sprite sprite-farms" /></s:a>
                            <div class="caption">
                                <h3><s:property value="getText('title.farms.getting')" /><span class="badge badge-success"><s:property value="numFar" /></span></h3>
                                <p><s:property value="getText('area.farmsinfo.getting')" /></p>
                                <p>
                                    <s:a cssClass="btn btn-initial" href="%{contextPath}/listFarm.action" role="button" targets="divBodyLayout"><s:property value="getText('link.farmsgo.getting')" /> <i class="icon-double-angle-right"></i></s:a>
                                </p>
                            </div>
                        </div> 
                    <% } %>
                </div>            
                <div class="row"> 
                    <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "field/view")) { %>
                        <div class="span6 thumbnail custom-thumb">
                            <s:a href="%{contextPath}/listField.action" role="button" targets="divBodyLayout"><img class="sprite sprite-fields" /></s:a>
                            <div class="caption">
                                <h3><s:property value="getText('title.field.getting')" /><span class="badge badge-success"><s:property value="numFie" /></span></h3>
                                <p><s:property value="getText('area.fieldinfo.getting')" /></p>
                                <p>
                                    <s:a cssClass="btn btn-initial" href="%{contextPath}/listField.action" role="button" targets="divBodyLayout"><s:property value="getText('link.fieldgo.getting')" /> <i class="icon-double-angle-right"></i></s:a>
                                </p>
                            </div>
                        </div>
                        <div class="span6 thumbnail custom-thumb" style="margin-left: 10px">
                    <% } else { %>
                        <div class="span6 thumbnail custom-thumb">
                    <% } %>
                    <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "crop/view")) { %>
                            <s:a href="%{contextPath}/crop/listCrop.action" role="button" targets="divBodyLayout"><img class="sprite sprite-crops" /></s:a>
                            <div class="caption">
                                <h3><s:property value="getText('title.crop.getting')" /><span class="badge badge-success"><s:property value="numEve" /></span></h3>
                                <p><s:property value="getText('area.cropinfo.getting')" /></p>                        
                                <p>
                                    <s:a cssClass="btn btn-initial" href="%{contextPath}/crop/listCrop.action" role="button" targets="divBodyLayout"><s:property value="getText('link.cropgo.getting')" /> <i class="icon-double-angle-right"></i></s:a>
                                </p>
                            </div>
                        </div>
                    <% } %>                
                </div>
                <div class="row">      
                    <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "soil/view")) { %>
                        <div class="span6 thumbnail custom-thumb">
                            <s:a href="%{contextPath}/soil/soilsType.action" role="button" targets="divBodyLayout"><img class="sprite sprite-soils" /></s:a>
                            <div class="caption">
                                <h3><s:property value="getText('title.soil.getting')" /></h3>
                                <p><s:property value="getText('area.soilinfo.getting')" /></p>
                                <p>
                                    <s:a cssClass="btn btn-initial" href="%{contextPath}/soil/soilsType.action" role="button" targets="divBodyLayout"><s:property value="getText('link.soilgo.getting')" /> <i class="icon-double-angle-right"></i></s:a>
                                </p>
                            </div>
                        </div>
                        <div class="span6 thumbnail custom-thumb" style="margin-left: 10px">
                    <% } else { %>
                        <div class="span6 thumbnail custom-thumb">
                    <% } %>
                    <% if (true) { %>
                            <img class="sprite sprite-climate"/>
                            <div class="caption">
                                <h3><s:property value="getText('title.climate.getting')" /></h3>
                                <p><s:property value="getText('area.climateinfo.getting')" /></p>
                                <h4><s:property value="getText('title.building.getting')" />!!!</h4>
                            </div>
                        </div>
                    <% } %>
                </div>
            <% } else if (entTypeId==2) { %>
                <div class="row">          
                    <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "farm/view")) { %>
                        <div class="span6 thumbnail custom-thumb">
                            <s:a href="%{contextPath}/listFarm.action" role="button" targets="divBodyLayout"><img class="sprite sprite-farms" /></s:a>
                            <div class="caption">
                                <h3><s:property value="getText('title.farms.getting')" /><span class="badge badge-success"><s:property value="numFar" /></span></h3>
                                <p><s:property value="getText('area.farmsinfo.getting')" /></p>
                                <p>
                                    <s:a cssClass="btn btn-initial" href="listFarm.action" role="button" targets="divBodyLayout"><s:property value="getText('link.farmsgo.getting')" /> <i class="icon-double-angle-right"></i></s:a>                                    
                                </p>
                            </div>
                        </div> 
                        <div class="span6 thumbnail custom-thumb" style="margin-left: 10px">
                    <% } else { %>
                        <div class="span6 thumbnail custom-thumb"> 
                    <% } %>
                    <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "field/view")) { %>
                            <s:a href="%{contextPath}/listField.action" role="button" targets="divBodyLayout"><img class="sprite sprite-fields" /></s:a>
                            <div class="caption">
                                <h3><s:property value="getText('title.field.getting')" /><span class="badge badge-success"><s:property value="numFie" /></span></h3>
                                <p><s:property value="getText('area.fieldinfo.getting')" /></p>
                                <p>
                                    <s:a cssClass="btn btn-initial" href="listField.action" role="button" targets="divBodyLayout"><s:property value="getText('link.fieldgo.getting')" /> <i class="icon-double-angle-right"></i></s:a>
                                </p>
                            </div>
                        </div>
                    <% } %>
                </div>            
                <div class="row"> 
                    <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "crop/view")) { %>
                        <div class="span6 thumbnail custom-thumb">
                            <s:a href="%{contextPath}/crop/listCrop.action" role="button" targets="divBodyLayout"><img class="sprite sprite-crops" /></s:a>
                            <div class="caption">
                                <h3><s:property value="getText('title.crop.getting')" /><span class="badge badge-success"><s:property value="numEve" /></span></h3>
                                <p><s:property value="getText('area.cropinfo.getting')" /></p>                        
                                <p>
                                    <s:a cssClass="btn btn-initial" href="%{contextPath}/crop/listCrop.action" role="button" targets="divBodyLayout"><s:property value="getText('link.cropgo.getting')" /> <i class="icon-double-angle-right"></i></s:a>
                                </p>
                            </div>
                        </div>
                        <div class="span6 thumbnail custom-thumb" style="margin-left: 10px">
                    <% } else { %>
                        <div class="span6 thumbnail custom-thumb">
                    <% } %>   
                    <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "soil/view")) { %>
                            <s:a href="%{contextPath}/soil/soilsType.action" role="button" targets="divBodyLayout"><img class="sprite sprite-soils" /></s:a>
                            <div class="caption">
                                <h3><s:property value="getText('title.soil.getting')" /></h3>
                                <p><s:property value="getText('area.soilinfo.getting')" /></p>
                                <p>
                                    <s:a cssClass="btn btn-initial" href="%{contextPath}/soil/soilsType.action" role="button" targets="divBodyLayout"><s:property value="getText('link.soilgo.getting')" /> <i class="icon-double-angle-right"></i></s:a>
                                </p>
                            </div>
                        </div>
                    <% } %>   
                </div>
                <div class="row">
                    <% if (true) { %>
                        <div class="span6 thumbnail custom-thumb">
                            <img class="sprite sprite-climate"/>
                            <div class="caption">
                                <h3><s:property value="getText('title.climate.getting')" /></h3>
                                <p><s:property value="getText('area.climateinfo.getting')" /></p>
                                <h4><s:property value="getText('title.building.getting')" />!!!</h4>
                            </div>
                        </div>
                    <% } %>
                </div>
            <% } %>
        </div>
    </body>
</html>
