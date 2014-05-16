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
                <li><span>Recolectar datos</span></li>
            </ul>
        </div>
        <div class="container">			
            <!-- Example row of columns -->
            <% if (entTypeId==1 || entTypeId==3) { %>
                <div class="row">             
                    <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "producer/view")) { %>
                        <div class="span6 thumbnail custom-thumb">
                            <img class="img-responsive hidden-xs" src="img/producers.jpg" alt="">
                            <div class="caption">
                                <h3>Productores<span class="badge badge-success"><s:property value="numPro" /></span></h3>
                                <p>Administre información de contacto de los productores a su cargo</p>
                                <%--<s:url id="ajaxTest" value="/AjaxTest.action"/>--%>
                                <%--<sj:a id="link1" href="%{ajaxTest}" targets="div1">--%>
                                <p>   
                                    <s:a cssClass="btn btn-initial" href="listProducer.action" role="button" targets="divBodyLayout">Ir <i class="icon-white icon-double-angle-right"></i></s:a>                                                               
                                </p>
                            </div>
                        </div>
                        <div class="span6 thumbnail custom-thumb" style="margin-left: 10px">                
                    <% } else { %>
                        <div class="span6 thumbnail custom-thumb"> 
                    <% } %>
                    <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "farm/view")) { %>
                            <img class="img-responsive hidden-xs" src="img/farms.jpg" alt="">
                            <div class="caption">
                                <h3>Fincas<span class="badge badge-success"><s:property value="numFar" /></span></h3>
                                <p>Administre información de las fincas que usted tiene a cargo</p>
                                <p>
                                    <s:a cssClass="btn btn-initial" href="listFarm.action" role="button" targets="divBodyLayout">Ir <i class="icon-double-angle-right"></i></s:a>
                                </p>
                            </div>
                        </div> 
                    <% } %>
                </div>            
                <div class="row"> 
                    <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "field/view")) { %>
                        <div class="span6 thumbnail custom-thumb">
                            <img class="img-responsive hidden-xs" src="img/fields.jpg" alt="">
                            <div class="caption">
                                <h3>Lotes<span class="badge badge-success"><s:property value="numFie" /></span></h3>
                                <p>Administre información de los lotes en las fincas registradas por usted</p>
                                <p>
                                    <s:a cssClass="btn btn-initial" href="listField.action" role="button" targets="divBodyLayout">Ir <i class="icon-double-angle-right"></i></s:a>
                                </p>
                            </div>
                        </div>
                        <div class="span6 thumbnail custom-thumb" style="margin-left: 10px">
                    <% } else { %>
                        <div class="span6 thumbnail custom-thumb">
                    <% } %>
                    <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "crop/view")) { %>
                            <img class="img-responsive hidden-xs" src="img/crops.jpg" alt="">
                            <div class="caption">
                                <h3>Cultivos<span class="badge badge-success"><s:property value="numEve" /></span></h3>
                                <p>Registre aqui información de monitoreo de los cultivos</p>                        
                                <p>
                                    <s:a cssClass="btn btn-initial" href="%{contextPath}/crop/listCrop.action" role="button" targets="divBodyLayout">Ir <i class="icon-double-angle-right"></i></s:a>
                                </p>
                            </div>
                        </div>
                    <% } %>                
                </div>
                <div class="row">      
                    <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "soil/view")) { %>
                        <div class="span6 thumbnail custom-thumb">
                            <img class="img-responsive hidden-xs" src="img/soils.jpg" alt="">
                            <div class="caption">
                                <h3>Suelos<span class="badge badge-success"><s:property value="numRas" /></span></h3>
                                <p>Registre aqui información de caracterización del suelo en sus lotes</p>
                                <p>
                                    <s:a cssClass="btn btn-initial" href="%{contextPath}/soil/listSoil.action" role="button" targets="divBodyLayout">Ir <i class="icon-double-angle-right"></i></s:a>
                                </p>
                            </div>
                        </div>
                        <div class="span6 thumbnail custom-thumb" style="margin-left: 10px">
                    <% } else { %>
                        <div class="span6 thumbnail custom-thumb">
                    <% } %>
                    <% if (true) { %>
                            <img class="img-responsive hidden-xs" src="img/climate.jpg" alt="">
                            <div class="caption">
                                <h3>Clima</h3>
                                <p>Registre aqui información de clima de su finca si cuenta con equipos o datos meteorológicos</p>
                                <h3>En Construcción!!!</h3>
                            </div>
                        </div>
                    <% } %>
                </div>
            <% } else if (entTypeId==2) { %>
                <div class="row">          
                    <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "farm/view")) { %>
                        <div class="span6 thumbnail custom-thumb">
                            <img class="img-responsive hidden-xs" src="img/farms.jpg" alt="">
                            <div class="caption">
                                <h3>Fincas<span class="badge badge-success"><s:property value="numFar" /></span></h3>
                                <p>Administre información de las fincas que usted tiene a cargo</p>
                                <p>
                                    <s:a cssClass="btn btn-initial" href="listFarm.action" role="button" targets="divBodyLayout">Ir <i class="icon-double-angle-right"></i></s:a>                                    
                                </p>
                            </div>
                        </div> 
                        <div class="span6 thumbnail custom-thumb" style="margin-left: 10px">
                    <% } else { %>
                        <div class="span6 thumbnail custom-thumb"> 
                    <% } %>
                    <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "field/view")) { %>
                            <img class="img-responsive hidden-xs" src="img/fields.jpg" alt="">
                            <div class="caption">
                                <h3>Lotes<span class="badge badge-success"><s:property value="numFie" /></span></h3>
                                <p>Administre información de los lotes en las fincas registradas por usted</p>
                                <p>
                                    <s:a cssClass="btn btn-initial" href="listField.action" role="button" targets="divBodyLayout">Ir <i class="icon-double-angle-right"></i></s:a>
                                </p>
                            </div>
                        </div>
                    <% } %>
                </div>            
                <div class="row"> 
                    <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "crop/view")) { %>
                        <div class="span6 thumbnail custom-thumb">
                            <img class="img-responsive hidden-xs" src="img/crops.jpg" alt="">
                            <div class="caption">
                                <h3>Cultivos<span class="badge badge-success"><s:property value="numEve" /></span></h3>
                                <p>Registre aqui información de monitoreo de los cultivos</p>                        
                                <p>
                                    <s:a cssClass="btn btn-initial" href="%{contextPath}/crop/listCrop.action" role="button" targets="divBodyLayout">Ir <i class="icon-double-angle-right"></i></s:a>
                                </p>
                            </div>
                        </div>
                        <div class="span6 thumbnail custom-thumb" style="margin-left: 10px">
                    <% } else { %>
                        <div class="span6 thumbnail custom-thumb">
                    <% } %>   
                    <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "soil/view")) { %>
                            <img class="img-responsive hidden-xs" src="img/soils.jpg" alt="">
                            <div class="caption">
                                <h3>Suelos<span class="badge badge-success"><s:property value="numRas" /></span></h3>
                                <p>Registre aqui información de caracterización del suelo en sus lotes</p>
                                <p>
                                    <s:a cssClass="btn btn-initial" href="%{contextPath}/soil/listSoil.action" role="button" targets="divBodyLayout">Ir <i class="icon-double-angle-right"></i></s:a>
                                </p>
                            </div>
                        </div>
                    <% } %>   
                </div>
                <div class="row">
                    <% if (true) { %>
                        <div class="span6 thumbnail custom-thumb">
                            <img class="img-responsive hidden-xs" src="img/climate.jpg" alt="">
                            <div class="caption">
                                <h3>Clima</h3>
                                <p>Registre aqui información de clima de su finca si cuenta con equipos o datos meteorológicos</p>
                                <h3>En Construcción!!!</h3>
                            </div>
                        </div>
                    <% } %>
                </div>
            <% } %>
        </div>
    </body>
</html>
