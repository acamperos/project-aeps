<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% String valAmen  = String.valueOf(request.getAttribute("numRows")); %>
<% int numRowsAmen = Integer.parseInt(valAmen); %>
<% if(numRowsAmen==0) { %>
    <% numRowsAmen = Integer.parseInt(String.valueOf(request.getParameter("numRows"))); %>
<% } %>
<% request.setAttribute("formAmen", "amenFert["+(numRowsAmen-1)+"]"); %>
<% request.setAttribute("formAmenId", "formCropFer_amenFert_"+(numRowsAmen-1)); %>
<tr value="<%= numRowsAmen %>" id="RowAdditAme_<%= numRowsAmen %>">
    <td>
        <div id="divEnmiendasFer_<%= numRowsAmen %>" value="<%= numRowsAmen %>">
            <div class="row">
                <div class="span5">
                    <s:hidden name="%{#attr.formAmen}.idAmeFer"/>
                    <div class="control-group">
                        <label for="${formAmenId}__amendmentsFertilizers_idAmeFer" class="control-label req">
                            Producto:
                        </label>
                        <div class="controls">
                            <s:select
                                name="%{#attr.formAmen}.amendmentsFertilizers.idAmeFer"
                                value="%{#attr.amendmentsFertilizers.idAmeFer}"
                                list="type_prod_ame" 
                                listKey="idAmeFer" 
                                listValue="nameAmeFer"            
                                headerKey="-1" 
                                headerValue="---"
                                onchange="showOtherElement(this.value, 'divNewProAme')"
                            />
                        </div>                         
                    </div>                          
                </div> 
            </div> 
            <% String classNewProAme="hide"; %>
            <s:set name="idCheAme" value="%{#attr.formAmen}.amendmentsFertilizers.idAmeFer"/>
            <s:if test="%{#idCheAme==1000000}">
                <% classNewProAme = "";%>
            </s:if> 
            <div class="<%= classNewProAme %>" id="divNewProAme">
                <div class="row">
                    <div class="span5">
                        <div class="control-group">
                            <label for="${formAmenId}__otherProductAmeFer" class="control-label req">
                                 Otro producto:
                            </label>
                            <div class="controls">
                                <s:textfield name="%{#attr.formAmen}.otherProductAmeFer" value="%{#attr.otherProductAmeFer}"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>                                        
            <div class="row">
                <div class="span5">
                    <div class="control-group">
                        <label for="${formAmenId}__amountProductUsedAmeFer" class="control-label req">
                            Cantidad de producto (kg/ha):
                        </label>
                        <div class="controls">
                            <s:textfield name="%{#attr.formAmen}.amountProductUsedAmeFer" value="%{#attr.amountProductUsedAmeFer}"/>
                        </div>                         
                    </div>                          
                </div>   
                <div class="span2" style="padding-left:10px">
                    <a class="btn btn-small delete_rows_dt" title="Quitar" style="margin-bottom:1.2em" onclick="$('#RowAdditAme_<%= numRowsAmen %>').remove();"><i class="icon-trash"></i></a>
                </div>
            </div>            
            <script>	
                var formAmenId = '<%= request.getAttribute("formAmenId") %>';
                $("#"+formAmenId+"__amountProductUsedAmeFer").numeric({ negative: false });
                $("#"+formAmenId+"__amountProductUsedAmeFer").val(parsePointSeparated($("#"+formAmenId+"__amountProductUsedAmeFer").val())); 
            </script>
        </div>
    </td>
</tr>