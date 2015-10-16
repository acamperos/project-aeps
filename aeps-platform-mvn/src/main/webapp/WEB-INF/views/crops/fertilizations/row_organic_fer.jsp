<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% String valOrg  = String.valueOf(request.getAttribute("numRows")); %>
<% int numRowsOrg = Integer.parseInt(valOrg); %>
<% if(numRowsOrg==0) { %>
    <% numRowsOrg = Integer.parseInt(String.valueOf(request.getParameter("numRows"))); %>
<% } %>
<% request.setAttribute("formOrg", "orgFert["+(numRowsOrg-1)+"]"); %>
<% request.setAttribute("formOrgId", "formCropFer_orgFert_"+(numRowsOrg-1)); %>
<tr value="<%= numRowsOrg %>" id="RowAdditOrg_<%= numRowsOrg %>">
    <td>
        <div id="divOrganicoFer_<%= numRowsOrg %>" value="<%= numRowsOrg %>">
            <div class="row">
                <div class="span5">
                    <s:hidden name="%{#attr.formOrg}.idOrgFer"/>
                    <div class="control-group">
                        <label for="${formOrgId}__organicFertilizers_idOrgFer" class="control-label req">
                            <s:property value="getText('select.orgfertilizer.fertilization')" />:
                        </label>
                        <div class="controls">
                            <s:select
                                name="%{#attr.formOrg}.organicFertilizers.idOrgFer"
                                value="%{#attr.organicFertilizers.idOrgFer}"
                                list="type_prod_org" 
                                listKey="idOrgFer" 
                                listValue="nameOrgFer"            
                                headerKey="-1" 
                                headerValue="---"
                                onchange="showOtherElement(this.value, 'divNewProOrg')"
                            />
                        </div>                         
                    </div>                          
                </div> 
                        
                              <div   class="span5" style="padding-left: 28px">
                             <div class="control-group">
                                    <s:label for="formRowChemical_fer" cssClass="control-label " value="%{getText('select.chemfertilizer.formapp')}:"></s:label>
                                        <div class="controls">

                                        <s:select
                                            
                                              id="%{#attr.formOrgId}__costFormAppOrgFer"
                                              name="%{#attr.formOrg}.costFormAppOrgFer"
                                              value="%{#attr.costFormAppOrgFer}"             
                                              list="#{'0':'---','1':'Manual', '2':'Mecánica','3':'Aérea'}"            
                                              headerKey="-1" 
                                            />

                                    </div>
                                     
                                </div> 
                              </div>
                                        
                                        
            </div> 
            <% String classNewProOrg="hide"; %>
            <s:set name="idCheOrg" value="%{#attr.formOrg}.organicFertilizers.idOrgFer"/>
            <s:if test="%{#idCheOrg==1000000}">
                <% classNewProOrg = "";%>
            </s:if> 
            <div class="<%= classNewProOrg %>" id="divNewProOrg">
                <div class="row">
                    <div class="span5">
                        <div class="control-group">
                            <label for="${formOrgId}__otherProductOrgFer" class="control-label req">
                                <s:property value="getText('text.otherproductorg.fertilization')" />:
                            </label>
                            <div class="controls">
                                <s:textfield name="%{#attr.formOrg}.otherProductOrgFer" value="%{#attr.otherProductOrgFer}"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="span5">
                    <div class="control-group">
                        <label for="${formOrgId}__costAppCheFer" class="control-label">
                            <s:property value="getText('text.costapp.fertilization')" />:
                        </label>
                        <div class="controls">
                            <s:textfield name="%{#attr.formOrg}.costAppOrgFer" id="%{#attr.formOrgId}__costAppOrgFer" value="%{#attr.costAppOrgFer}"/>
                        </div>                         
                    </div>                          
                </div> 
                <div class="span5" style="padding-left: 28px">
                    <div class="control-group">
                        <label for="${formOrgId}__amountProductUsedOrgFer" class="control-label req">
                            <s:property value="getText('text.amountproductorg.fertilization')" />:
                        </label>
                        <div class="controls">
                            <s:textfield name="%{#attr.formOrg}.amountProductUsedOrgFer" value="%{#attr.amountProductUsedOrgFer}"/>
                        </div>                         
                    </div>                          
                </div> 
               
            </div>     
                    <div class="row">
                        <div class="span5" >
                            <div class="control-group">   
                                <label for="${formOrgId}__costProductOrgFer" class="control-label">
                                    <s:property value="getText('text.costproduct.fertilization')" />:
                                </label>
                                <div class="controls">
                                    <s:textfield name="%{#attr.formOrg}.costProductOrgFer" id="%{#attr.formOrgId}__costProductOrgFer" value="%{#attr.costProductOrgFer}"/>
                                </div>                         
                            </div>                          
                        </div> 
                <div class="span2" style="padding-left:10px">
                    <a class="btn btn-small delete_rows_dt" title="<s:property value="getText('link.removeorgfert.fertilization')" />" style="margin-bottom:1.2em" onclick="$('#RowAdditOrg_<%= numRowsOrg %>').remove();"><i class="icon-trash"></i></a>
                </div>
                    </div>   
            <script>	
                var formOrgId = '<%= request.getAttribute("formOrgId") %>';
                $("#"+formOrgId+"__amountProductUsedOrgFer").numeric({ negative: false });
                $("#"+formOrgId+"__amountProductUsedOrgFer").val(parsePointSeparated($("#"+formOrgId+"__amountProductUsedOrgFer").val())); 
                
                 $("#__costAppOrgFer").maskMoney({suffix: ' $'});
                 $("#"+formOrgId+"__costAppOrgFer").maskMoney({suffix: ' $'});
                 $("#__costProductOrgFer").maskMoney({suffix: ' $'});
                 $("#"+formOrgId+"__costProductOrgFer").maskMoney({suffix: ' $'});
                 
                 
            
            </script>
        </div>
    </td>
</tr>