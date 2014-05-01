<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page import="org.aepscolombia.platform.models.entity.Users"%>
<%@page import="org.aepscolombia.platform.models.dao.UsersDao"%>
<%@page import="org.aepscolombia.platform.util.APConstants"%>
<% Users user = (Users) session.getAttribute(APConstants.SESSION_USER); %>
<% UsersDao usrDao = new UsersDao(); %>
<!DOCTYPE html>
<s:actionerror theme="bootstrap"/>
<s:actionmessage theme="bootstrap"/>
<s:fielderror theme="bootstrap"/>
<div class="container">
    <div class="panel">
        <div class="panel-body">
            <div id="divMessProfile"></div>
            <div class="tabbable tabbable-bordered">
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#tab1" data-toggle="tab">Perfil</a></li>
                    <li><a href="#tab2" data-toggle="tab">Configuración</a></li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane active" id="tab1">
                        <%@ include file="form-profile.jsp" %>
                    </div>
                    <div class="tab-pane" id="tab2">
                        <%@ include file="form-settings.jsp" %>
                    </div>
                </div>
            </div>            
        </div>
    </div>
</div>