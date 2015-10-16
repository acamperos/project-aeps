<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page import="java.lang.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.aepscolombia.platform.util.JavascriptHelper"%>            
<% String tableFerGen = "display:none;";%>
<% String labelFerGen = "";%>
<%@page import="org.aepscolombia.platform.models.dao.EntitiesDao"%>
<%@page import="org.aepscolombia.platform.models.entity.Users"%>
<%@page import="org.aepscolombia.platform.models.dao.UsersDao"%>
<%@page import="org.aepscolombia.platform.util.APConstants"%>
<% Users userFer  = (Users) session.getAttribute(APConstants.SESSION_USER); %>
<% UsersDao usrFerDao = new UsersDao(); %>
<% Integer entTypeFerId = new EntitiesDao().getEntityTypeId(userFer.getIdUsr()); %>

<s:if test="listFert.size() > 0">
    <% tableFerGen = "";%>
    <% labelFerGen = "display:none;";%> 
</s:if>            

<div class="msgWin" id="divFer"></div>   
<div id="divFerGen" class="w-box">
    <fieldset>
        <legend><s:property value="getText('title.fertilizationlist.fertilization')" /></legend>        
        <% if (usrFerDao.getPrivilegeUser(userFer.getIdUsr(), "crop/create")) { %>
            <% if (entTypeFerId!=3) { %>
                <button type="button" class="btn btn-initial btn-space" onclick="viewForm('/crop/showFer.action?action=create', 'idCrop', '${idCrop}', '<s:property value="getText('title.addfertilization.fertilization')" />', 1050, 550);">
                    <i class="icon-plus"></i> <s:property value="getText('button.addfertilization.fertilization')" />
                </button>
            <% } %>
        <% } %>
        <table class="table table-bordered table-hover" style="<%= tableFerGen %>" id='tblFerGen'>
            <thead>
                <tr>
                    <th><s:property value="getText('tr.appdate.fertilization')" /></th>
                    <th><s:property value="getText('tr.typefer.fertilization')" /></th>                    
                    <th><s:property value="getText('tr.amountproduct.fertilization')" /></th>       
                    <th><s:property value="getText('tr.whicfertilization.fertilization')" /></th>
                    <% if (usrFerDao.getPrivilegeUser(userFer.getIdUsr(), "crop/modify") || (usrFerDao.getPrivilegeUser(userFer.getIdUsr(), "crop/delete"))) { %>
                        <th><s:property value="getText('tr.action.fertilization')" /></th>
                    <% } %>
                </tr>
            </thead>
            <tbody>
                <s:set name="valIdFer" value="0"/>
                
                <s:iterator value="listFert" var="ferGen" status="estatus">
                    <s:if test="%{ferTyp.equals('Quimica')}">
                        <tr id="trFerChe${idFerChe}">
                    </s:if>
                    <s:elseif test="%{ferTyp.equals('Organica')}">
                        <tr id="trFerOrg${idFerOrg}">
                    </s:elseif>
                    <s:elseif test="%{ferTyp.equals('Enmienda')}">
                        <tr id="trFerAme${idFerAme}">
                    </s:elseif>

                    <s:if test="%{#valIdFer!=#attr.idFer}">   
                        <%@ include file="row-fermain.jsp" %>
                    </s:if>                                
                    <s:else>
                        <%@ include file="row-fergen.jsp" %>
                    </s:else>                                                               
                    </tr>
                    <s:set name="valIdFer" value="%{#attr.idFer}"/>
                </s:iterator>
            </tbody>
        </table>
        <label style="<%= labelFerGen %>"><s:property value="getText('label.nofounddata.fertilization')" /></label>
        <div class="hide">
            <div id="confirm_dialog_fergen" class="cbox_content">
                <div class="sepH_c"><strong><s:property value="getText('label.deletefertilization.fertilization')" />?</strong></div>
                <div>
                    <a href="#" class="btn btn-small btn-initial confirm_yes"><s:property value="getText('link.optyes')" /></a>
                    <a href="#" class="btn btn-small confirm_no"><s:property value="getText('link.optno')" /></a>
                </div>
            </div>
        </div>
    </fieldset>
</div>