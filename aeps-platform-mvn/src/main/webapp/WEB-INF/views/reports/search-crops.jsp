<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="org.aepscolombia.platform.models.entity.Users"%>
<%@page import="org.aepscolombia.platform.util.APConstants"%>
<%@page import="org.aepscolombia.platform.models.dao.EntitiesDao"%>
<% Users user = (Users) session.getAttribute(APConstants.SESSION_USER); %>
<% Integer entTypeId = new EntitiesDao().getEntityTypeId(user.getIdUsr()); %>
<s:actionerror theme="bootstrap"/>
<s:actionmessage theme="bootstrap"/>
<s:fielderror theme="bootstrap"/>
<s:form id="formCropSearch" action="getReportPro.action" cssClass="form-horizontal">
    <div class="control-group">
        <s:label for="formCropSearch_typeReport" cssClass="control-label" value="%{getText('select.searchreporttype.report')}:"></s:label>
        <div class="controls">
            <s:select
                name="typeReport"
                list="#{'1':'Anual'}"           
                headerKey="-1" 
                headerValue="---" 
                onchange="changeReport('formCropSearch_typeReport', 'divRepYear', 'divRepDep');"
                />
        </div>   
    </div>
    <div id="divRepYear" class="hide">
        <div class="control-group">
            <s:label for="formCropSearch_selYear" cssClass="control-label" value="%{getText('select.searchrankyear.report')}:"></s:label>
            <div class="controls">
                <s:select
                    name="selYear"
                    list="#{'1':'Un solo año', '2':'Varios años'}"           
                    headerKey="1" 
                    onchange="changeRepYear('formCropSearch_selYear', 'divRange', 'divRangeMore');"
                    />
            </div>   
        </div>
        <div class="row hide" id="divRange">
            <div class="span4">
                <div class="control-group">
                    <s:label for="formCropSearch_year_begin" cssClass="control-label" value="%{getText('text.searchyear.report')}:"></s:label>
                    <div class="controls">
                        <s:textfield name="year_begin" />
                        <span class="help-block" style="margin-top: 5px;">min: 2000; max: 2014</span>
                    </div>                         
                </div>                          
            </div>              
            <div id="divRangeMore" class="hide">
                <div class="span0" style="margin-left: 0px;">
                    <s:label cssClass="control-label" value="y" cssStyle="width: 20px; text-align: left;"></s:label>
                </div>
                <div>
                    <div class="control-group">
                        <div class="controls">
                            <s:textfield name="year_end" />
                        </div>
                    </div>
                </div>
            </div>            
        </div>
        <% if (entTypeId==5) { %>
            <div class="row">
                <div class="span5">
                    <div class="control-group">
                        <s:label for="formCropSearch_name_producer" cssClass="control-label" value="%{getText('text.searchproducer.report')}:"></s:label>
                        <div class="controls">
                            <s:textfield name="name_producer" />
                        </div>                          
                    </div>                          
                </div>            
            </div>
            <div class="row">
                <div class="span5">
                    <div class="control-group">
                        <s:label for="formCropSearch_type_doc" cssClass="control-label" value="%{getText('select.searchdoctype.report')}:"></s:label>
                        <div class="controls">
                           <s:select
                            name="type_doc"
                            list="type_ident_producer" 
                            listKey="acronymDocTyp" 
                            listValue="nameDocTyp" 
                            onchange="selConf(this.value, 'formCropSearch_num_doc');"
                            headerKey="-1" 
                            headerValue="---" />
                        </div>                          
                    </div>                          
                </div>                 
                <div class="span4" style="padding-left: 28px">
                    <div class="control-group">
                        <s:label for="formCropSearch_num_doc" cssClass="control-label" value="%{getText('text.searchdocnum.report')}:"></s:label>
                        <div class="controls">
                            <s:textfield name="num_doc" />
                        </div>
                    </div>
                </div>
            </div> 
            <script>
                $("#formCropSearch_num_doc").mask("999999?9999", {placeholder: ""});
//                $("#formCropSearch_num_doc").numeric({decimal: false, negative: false});
            </script>
        <% } %>
        <script>            
            changeRepYear('formCropSearch_selYear', 'divRange', 'divRangeMore');
            if($('#formCropSearch_year_begin').length) {
                $("#formCropSearch_year_begin").spinner({
                    min: 2000,
                    max: 2014
                });
            }
            if($('#formCropSearch_year_end').length) {
                $("#formCropSearch_year_end").spinner({
                    min: 2000,
                    max: 2014
                });
            }
        </script>
    </div>
    <div id="divRepDep" class="hide">
        <div class="row">
            <div class="span5">
                <div class="control-group">
                    <s:label for="formCropSearch_depSel" cssClass="control-label" value="%{getText('select.searchdepartment.report')}:"></s:label>
                    <s:select
                        name="depSel" 
                        list="list_departments" 
                        listKey="idDep" 
                        listValue="nameDep"          
                        headerKey=" " 
                        headerValue="---"
                    />
                </div>
            </div>
        </div>
        <% if (entTypeId==1) { %>
            <div class="row">
                <div class="span5">
                    <div class="control-group">
                        <s:label for="formCropSearch_nameProDep" cssClass="control-label" value="%{getText('text.searchproducer.report')}:"></s:label>
                        <div class="controls">
                            <s:textfield name="nameProDep" />
                        </div>                          
                    </div>                          
                </div>            
            </div>
            <div class="row">
                <div class="span5">
                    <div class="control-group">
                        <s:label for="formCropSearch_typeDocDep" cssClass="control-label" value="%{getText('select.searchdoctype.report')}:"></s:label>
                        <div class="controls">
                           <s:select
                            name="typeDocDep"
                            list="type_ident_producer" 
                            listKey="acronymDocTyp" 
                            listValue="nameDocTyp" 
                            onchange="selConf(this.value, 'formCropSearch_num_doc');"
                            headerKey="-1" 
                            headerValue="---" />
                        </div>                          
                    </div>                          
                </div>                 
                <div class="span4" style="padding-left: 28px">
                    <div class="control-group">
                        <s:label for="formCropSearch_numDocDep" cssClass="control-label" value="%{getText('text.searchdocnum.report')}:"></s:label>
                        <div class="controls">
                            <s:textfield name="numDocDep" />
                        </div>
                    </div>
                </div>
            </div>
        <% } %>
        <script>
            $("#formCropSearch_numDocDep").mask("999999?9999", {placeholder: ""});
//            $("#formCropSearch_numDocDep").numeric({decimal: false, negative: false});
        </script>
    </div>
    <div> 
        <sj:submit type="button" cssClass="btn btn-initial" onclick="addMessageProcess()" targets="divConListCrop" onCompleteTopics="completeSearchCrop"><s:property value="getText('button.generaterep.report')" /> <i class="icon-search"></i></sj:submit>
    </div>    
</s:form>        
<script>
//    $("#formCropSearch_year_begin").numeric();
//    $("#formCropSearch_year_end").numeric();
    $.subscribe('completeSearchCrop', function(event, data) {
       $.unblockUI();
    });
</script>