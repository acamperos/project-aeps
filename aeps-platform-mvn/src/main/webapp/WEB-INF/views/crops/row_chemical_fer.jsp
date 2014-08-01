<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.aepscolombia.platform.models.entity.ChemicalElements"%>
<%@page import="org.aepscolombia.platform.models.entity.ChemicalFertilizations"%>
<%@page import="java.util.List"%>
<% String valChe  = String.valueOf(request.getAttribute("numRows")); %>
<% int numRowsChe = Integer.parseInt(valChe); %>
<% if(numRowsChe==0) { %>
    <% numRowsChe = Integer.parseInt(String.valueOf(request.getParameter("numRows"))); %>
<% } %>
<% request.setAttribute("formChe", "chemFert["+(numRowsChe-1)+"]"); %>
<% request.setAttribute("formCheSel", "chemFert_"+(numRowsChe-1)); %>
<% request.setAttribute("formCheId", "formCropFer_chemFert_"+(numRowsChe-1)); %>
<% //int newRow = Integer.parseInt(String.valueOf(request.getAttribute("newRow"))); %>
<tr value="<%= numRowsChe %>" id="RowAdditChe_<%= numRowsChe %>">
    <td>
        <div id="divChemicalRow_<%= numRowsChe %>">
            <div class="row">
                <div class="span5">
                    <div class="control-group">
                        <s:if test="%{#attr.numRows>0}">
                            <s:set name="counter" value="%{#attr.numRows-1}"/>
                        </s:if>
                        <s:else>
                            <s:set name="counter" value="%{#attr.numRows}"/>
                        </s:else>                        
                        <label for="<%= request.getAttribute("formCheId") %>__applicationTypes_idAppTyp" class="control-label req">
                            Tipo de aplicación:
                        </label>
                        <div class="controls">
                            <s:select
                                id="%{#attr.formCheId}__applicationTypes_idAppTyp"
                                name="%{#attr.formChe}.applicationTypes.idAppTyp"
                                value="%{#attr.applicationTypes.idAppTyp}"
                                list="list_app_typ" 
                                listKey="idAppTyp" 
                                listValue="nameAppTyp"            
                                headerKey="-1" 
                                headerValue="---"
                                onchange="showOtherElementChemical('%{#attr.formCheId}__chemicalFertilizers_idCheFer', '%{#attr.formCheSel}__applicationTypes_idAppTyp', 'divNewProQui%{#counter}')
                                showApplicationProduct('%{#attr.formCheId}__applicationTypes_idAppTyp', '%{#attr.formCheId}__chemicalFertilizers_idCheFer', 'divAppProduct%{#counter}', 'divNewProQui%{#counter}', 'divPerEda%{#counter}');
                                changeChemicalFoliar('%{#attr.formCheId}__applicationTypes_idAppTyp', '%{#attr.formCheId}_otherProductCheFerLbl', 'Cual fue el producto')
                                "
                            />
                        </div>                         
                    </div>                          
                </div>   
            </div> 
            <% String classAppTyp="hide"; %>
            <s:set name="idAppTyp" value="%{#attr.applicationTypes.idAppTyp}"/>
            <s:if test="%{#idAppTyp==1}">
                <% classAppTyp = "";%>
            </s:if> 
            <div class="row <%= classAppTyp %>" id="divAppProduct<%= (numRowsChe-1) %>">
                <div class="span5">
                    <s:hidden name="%{#attr.formChe}.idCheFer"/>
                    <div class="control-group">
                        <label for="<%= request.getAttribute("formCheId") %>__chemicalFertilizers_idCheFer" class="control-label req">
                            Producto:
                        </label>
                        <div class="controls">
                            <s:select
                                id="%{#attr.formCheId}__chemicalFertilizers_idCheFer"
                                name="%{#attr.formChe}.chemicalFertilizers.idCheFer"
                                value="%{#attr.chemicalFertilizers.idCheFer}"
                                list="type_prod_che" 
                                listKey="idCheFer" 
                                listValue="nameCheFer"            
                                headerKey="-1" 
                                headerValue="---"
                                onchange="showOtherElementChemical('%{#attr.formCheId}__chemicalFertilizers_idCheFer', '%{#attr.formCheId}__applicationTypes_idAppTyp', 'divNewProQui%{#counter}')"
                            />
                            <s:hidden name="amountProductUsedCheFerHide" value="%{#attr.amountProductUsedCheFer}"/>
                        </div>                         
                    </div>                          
                </div> 
<!--                <div class="span4">
                    <span class="add-on">
                        <%--<s:property value="%{#attr.chemFert[#counter].additionalsElem[0].nameCheEle}" />--%>
                    </span>
                    <%--<s:textfield name="%{#attr.formChe}.additionalsElem" value="%{#attr.chemFert[#counter].additionalsElem[0].nameCheEle}" cssClass="input-degrees"/>--%>
                </div>-->
            </div> 
            <% String classNewProChe="hide"; %>
            <s:set name="idCheFer" value="%{#attr.chemicalFertilizers.idCheFer}"/>
            <s:if test="%{#idCheFer==1000000 || #idAppTyp==2}">
                <% classNewProChe = "";%>
            </s:if> 
            <div class="<%= classNewProChe %>" id="divNewProQui<%= (numRowsChe-1) %>">
                <div class="row">
                    <div class="span5">
                        <div class="control-group">
                            <label for="${formCheId}_otherProductCheFer" id="${formCheId}_otherProductCheFerLbl" class="control-label req">
                                 Otro producto:
                            </label>
                            <div class="controls">
                                <s:textfield name="%{#attr.formChe}.otherProductCheFer" id="%{#attr.formCheId}_otherProductCheFer" value="%{#attr.otherProductCheFer}"/>                                
                            </div>
                        </div>
                    </div>
                </div>
                <% String classEdafico="hide"; %>
                <s:if test="%{#idAppTyp==1}">
                    <% classEdafico = "";%>
                </s:if>
                <div class="row <%= classEdafico %>" id="divPerEda<%= (numRowsChe-1) %>" style="margin-left:153px">
                    <table class="tableComposition" style="width:50%">
                        <thead>
                            <tr>
                                <td colspan="5"><label>Manejar valores en porcentajes (Ingresar por lo menos alguna de las composiciones)</label></td>
                            </tr>
                        </thead>
                        <s:set name="additionalsElemChe" value="%{#attr.chemFert[#counter].additionalsElem}"/>
                        <s:iterator value="%{#attr.chemFert[#counter].additionalsElem}" var="element" status="estatus">   
                            <s:if test="%{#estatus.index==0}">   
                                <tr>
                            </s:if>    
                            <s:if test="%{#estatus.index>=0 && #estatus.index<=4}">                                                        
                                <td>
                                    <div class="span1">
                                        <div class="control-group">
                                            <div class="input-prepend controls" style="margin-left:0">
                                                <span class="add-on"><s:property value="nameCheEle" /></span>
                                                <s:textfield name="%{#attr.formChe}.additionalsElem[%{#estatus.index}].valueCheEle" value="%{#attr.valueCheEle}" cssClass="input-degrees"/>
                                                <s:hidden name="%{#attr.formChe}.additionalsElem[%{#estatus.index}].idCheEle" value="%{#attr.idCheEle}"/>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </s:if>                                                    
                            <s:if test="%{#estatus.index==4}">   
                                </tr>
                            </s:if> 
                            <s:if test="%{#estatus.index==5}">   
                                <tr>
                            </s:if>    
                            <s:if test="%{#estatus.index>=5 && #estatus.index<=#estatus.count}">                                                        
                                <td>
                                    <div class="span1">
                                        <div class="control-group">
                                            <div class="input-prepend controls" style="margin-left:0">
                                                <span class="add-on"><s:property value="nameCheEle" /></span>
                                                <s:textfield id="%{#attr.formCheId}__additionalsElem_%{#estatus.index}__valueCheEle" name="%{#attr.formChe}.additionalsElem[%{#estatus.index}].valueCheEle" value="%{#attr.valueCheEle}" cssClass="input-degrees"/>
                                                <s:hidden id="%{#attr.formCheId}__additionalsElem_%{#estatus.index}__idCheEle" name="%{#attr.formChe}.additionalsElem[%{#estatus.index}].idCheEle" value="%{#attr.idCheEle}"/>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </s:if>
                            <s:if test="%{#estatus.index==#estatus.count}">   
                                </tr>
                            </s:if> 
                            <script>	
                                var formCheId = '<%= request.getAttribute("formCheSel") %>';
                                $("#"+formCheId+"__additionalsElem_0__valueCheEle").numeric({ negative: false });
                                $("#"+formCheId+"__additionalsElem_1__valueCheEle").numeric({ negative: false });
                                $("#"+formCheId+"__additionalsElem_2__valueCheEle").numeric({ negative: false });
                                $("#"+formCheId+"__additionalsElem_3__valueCheEle").numeric({ negative: false });
                                $("#"+formCheId+"__additionalsElem_4__valueCheEle").numeric({ negative: false });
                                $("#"+formCheId+"__additionalsElem_5__valueCheEle").numeric({ negative: false });
                                $("#"+formCheId+"__additionalsElem_6__valueCheEle").numeric({ negative: false });
                                $("#"+formCheId+"__additionalsElem_7__valueCheEle").numeric({ negative: false });
                                $("#"+formCheId+"__additionalsElem_8__valueCheEle").numeric({ negative: false });
                            </script>
                        </s:iterator>
                    </table>
                </div>
            </div>
            <div class="row">
                <div class="span5">
                    <div class="control-group">
                        <label for="${formCheId}__amountProductUsedCheFer" class="control-label req">
                            Cantidad de producto:
                        </label>
                        <div class="controls">
                            <s:textfield name="%{#attr.formChe}.amountProductUsedCheFer" id="%{#attr.formCheId}__amountProductUsedCheFer" value="%{#attr.amountProductUsedCheFer}"/>
                        </div>                         
                    </div>                          
                </div>   
                <div class="span5" style="padding-left: 28px">
                    <div class="control-group">
                        <label for="<%= request.getAttribute("formCheId") %>__unitCheFer" class="control-label req">
                            Unidad:
                        </label>
                        <div class="controls">
                            <s:select
                                id="%{#attr.formCheId}__unitCheFer"
                                name="%{#attr.formChe}.unitCheFer"
                                value="%{#attr.unitCheFer}"
                                list="#{'1':'kg/ha', '2':'lt/ha'}"           
                                headerKey="-1" 
                                headerValue="---"
                            />
                        </div>
                    </div>
                </div>
            </div>
            <script>	
                var formCheId = '<%= request.getAttribute("formCheId") %>';
                $("#"+formCheId+"__amountProductUsedCheFer").numeric({ negative: false });
                $("#"+formCheId+"__amountProductUsedCheFer").val(parsePointSeparated($("#"+formCheId+"__amountProductUsedCheFer").val())); 
            </script>
        </div>
    </td>
    <td style="vertical-align: middle ! important; padding: 45px 50px 0px 0px;">
        <a class="btn btn-small delete_rows_dt" title="Quitar" style="margin-bottom:1.2em" onclick="$('#RowAdditChe_<%= numRowsChe %>').remove();"><i class="icon-trash"></i></a>
    </td>
</tr>