<%@page import="java.util.HashMap"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.aepscolombia.platform.models.entity.Users"%>
<%@page import="org.aepscolombia.platform.models.dao.UsersDao"%>
<%@page import="org.aepscolombia.platform.util.APConstants"%>
<% Users user = (Users) session.getAttribute(APConstants.SESSION_USER); %>
<% UsersDao usrDao = new UsersDao(); %>
<!DOCTYPE html>
<html>
    <head></head>
    <body>
        <s:actionerror theme="bootstrap"/>
        <s:actionmessage theme="bootstrap"/>
        <s:fielderror theme="bootstrap"/>
        <div class="row-fluid alert fade in">
            <s:text name="%{getText('area.aboutgeolocation')}" />			
        </div>
        <div class="row-fluid" id="divRastaForm">
            <s:form id="formRasta" action="saveSoil" cssClass="form-horizontal">
                <fieldset>         
                    <legend><s:property value="getText('title.features.soil')" /></legend>
                    <s:hidden name="rasta.idRas"/>    
                    <div class="control-group">
                        <label for="formRasta_nameField" class="control-label req">
                            <s:property value="getText('text.selectfield.soil')" />:
                            <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.selectfield.soil')" />." data-title="<s:property value="getText('help.selectfield.soil')" />" data-placement="right" data-trigger="hover"></i>
                        </label>
                        <div class="controls">
                            <s:hidden name="idField"/>
                            <s:textfield
                                name="nameField"         
                                readonly="true"
                                onclick="listInfo('/viewField.action?selected=rasta', 'formRasta_nameField', 'formRasta_idField', 'divListRastaForm', 'divRastaForm')"
                            />
                            <a class="btn" onclick="listInfo('/viewField.action?selected=rasta', 'formRasta_nameField', 'formRasta_idField', 'divListRastaForm', 'divRastaForm')"><i class="icon-search"></i></a>
                        </div>  
                    </div>  
                    <div class="control-group">
                        <s:label for="formRasta_rasta_fechaRas" cssClass="control-label req" value="%{getText('text.daterasta.soil')}:"></s:label>
                        <div class="date controls">
                            <s:date name="rasta.fechaRas" format="dd/MM/yyyy" var="dateTransform"/>
                            <s:textfield cssClass="form-control" name="rasta.fechaRas" value="%{#dateTransform}" readonly="true"/>
                            <span class="prefix sec">&nbsp;[dd/mm/yyyy]</span>
                            <span class="add-on"><i class="icon-calendar"></i></span>
                        </div>
                    </div>    
                    <div class="control-group">
                        <label for="formRasta_rasta_latitudRas" class="control-label req">
                            <s:property value="getText('text.latitudedecimal.soil')" />:
                            <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.latitudedecimal.soil')" />." data-title="<s:property value="getText('help.latitudedecimal.soil')" />" data-placement="right" data-trigger="hover"></i>
                        </label>
                        <div class="controls">
                            <s:textfield cssClass="form-control" name="rasta.latitudRas" onkeyup="generateDegrees('formRasta_rasta_latitudRas', 'formRasta_rasta_latitude_degrees', 'formRasta_rasta_latitude_minutes', 'formRasta_rasta_latitude_seconds')"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <label for="formRasta_rasta_latitude_degrees" class="control-label req">
                            <s:property value="getText('text.latitudedegree.soil')" />:
                            <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.latitudedegree.soil')" />." data-title="<s:property value="getText('help.latitudedegree.soil')" />" data-placement="right" data-trigger="hover"></i>
                        </label>
                        <div class="controls">
                            <div class="row-fluid">
                                <div class="span2 input-prepend controls" style="width: 100px;">
                                    <span class="add-on"><s:property value="getText('text.degrees.soil')" /></span>
                                    <input type="text" name="latitude_degrees" onkeyup="generateDecimals('formRasta_rasta_latitudRas', 'formRasta_rasta_latitude_degrees', 'formRasta_rasta_latitude_minutes', 'formRasta_rasta_latitude_seconds')" id="formRasta_rasta_latitude_degrees" class="input-degrees"/>
                                </div>
                                <div class="span2 input-prepend controls" style="width: 100px; margin-left: 2%">
                                    <span class="add-on"><s:property value="getText('text.minutes.soil')" /></span>
                                    <input type="text" name="latitude_minutes" onkeyup="generateDecimals('formRasta_rasta_latitudRas', 'formRasta_rasta_latitude_degrees', 'formRasta_rasta_latitude_minutes', 'formRasta_rasta_latitude_seconds'); checkValue('formRasta_rasta_latitude_minutes', 59);" id="formRasta_rasta_latitude_minutes" class="input-degrees"/>
                                </div>
                                <div class="span2 input-prepend controls" style="width: 100px; margin-left: 2.2%">
                                    <span class="add-on"><s:property value="getText('text.seconds.soil')" /></span>
                                    <input type="text" name="latitude_seconds" onkeyup="generateDecimals('formRasta_rasta_latitudRas', 'formRasta_rasta_latitude_degrees', 'formRasta_rasta_latitude_minutes', 'formRasta_rasta_latitude_seconds'); checkValueSecond('formRasta_rasta_latitude_seconds', 60);" id="formRasta_rasta_latitude_seconds" class="input-degrees"/>
                                </div>
                            </div>
                        </div>
                    </div>                            
                    <div class="control-group">
                        <label for="formRasta_rasta_longitudRas" class="control-label req">
                            <s:property value="getText('text.longitudedecimal.soil')" />:
                            <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.longitudedecimal.soil')" />." data-title="<s:property value="getText('help.longitudedecimal.soil')" />" data-placement="right" data-trigger="hover"></i>
                        </label>
                        <div class="controls">
                            <s:textfield cssClass="form-control" id="formRasta_rasta_longitudRas" name="rasta.longitudRas" onkeyup="generateDegrees('formRasta_rasta_longitudRas', 'formRasta_rasta_length_degrees', 'formRasta_rasta_length_minutes', 'formRasta_rasta_length_seconds')"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <label for="formRasta_rasta_length_degrees" class="control-label req">
                            <s:property value="getText('text.longitudedegree.soil')" />:
                            <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.longitudedegree.soil')" />." data-title="<s:property value="getText('help.longitudedegree.soil')" />" data-placement="right" data-trigger="hover"></i>
                        </label>
                        <div class="controls">
                            <div class="row-fluid">
                                <div class="span2 input-prepend controls" style="width: 100px;">
                                    <span class="add-on"><s:property value="getText('text.degrees.soil')" /></span>
                                    <input type="text" name="length_degrees" onkeyup="generateDecimals('formRasta_rasta_longitudRas', 'formRasta_rasta_length_degrees', 'formRasta_rasta_length_minutes', 'formRasta_rasta_length_seconds')" id="formRasta_rasta_length_degrees" class="input-degrees"/>
                                </div>
                                <div class="span2 input-prepend controls" style="width: 100px; margin-left: 2%">
                                    <span class="add-on"><s:property value="getText('text.minutes.soil')" /></span>
                                    <input type="text" name="length_minutes" onkeyup="generateDecimals('formRasta_rasta_longitudRas', 'formRasta_rasta_length_degrees', 'formRasta_rasta_length_minutes', 'formRasta_rasta_length_seconds'); checkValue('formRasta_rasta_length_minutes', 59);" id="formRasta_rasta_length_minutes" class="input-degrees"/>
                                </div>
                                <div class="span2 input-prepend controls" style="width: 100px; margin-left: 2.2%">
                                    <span class="add-on"><s:property value="getText('text.seconds.soil')" /></span>
                                    <input type="text" name="length_seconds" onkeyup="generateDecimals('formRasta_rasta_longitudRas', 'formRasta_rasta_length_degrees', 'formRasta_rasta_length_minutes', 'formRasta_rasta_length_seconds'); checkValueSecond('formRasta_rasta_length_seconds', 60);" id="formRasta_rasta_length_seconds" class="input-degrees"/>
                                </div>
                            </div>
                        </div>
                    </div>   
                    <div class="control-group">
                        <div class="controls">
                            <button type="button" class="btn btn-initial btn-space" onclick="viewPositionRasta('/soil/viewPositionSoil.action', 'formRasta', 'rasta.latitudRas', 'formRasta_rasta_latitudRas', 'rasta.longitudRas', 'formRasta_rasta_longitudRas', 'divRastaForm', 'divListRastaForm');">
                                <i class="icon-map-marker" style="font-size: 18px"></i> <s:property value="getText('button.showgeolocation.soil')" />
                            </button>
                        </div>
                    </div>
                    <div class="control-group">
                        <s:label for="formRasta_rasta_altitudRas" cssClass="control-label req" value="%{getText('text.altitudemeters.soil')}:"></s:label>
                            <div class="controls">
                            <s:textfield cssClass="form-control" id="formRasta_rasta_altitudRas" name="rasta.altitudRas" tooltip="%{getText('desc.altitudemeters.soil')}"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <s:label for="formRasta_rasta_pendienteTerrenoRas" cssClass="control-label req" value="%{getText('text.pendant.soil')}:"></s:label>
                            <div class="controls">
                            <s:textfield cssClass="form-control" id="formRasta_rasta_pendienteTerrenoRas" name="rasta.pendienteTerrenoRas" tooltip="%{getText('desc.pendant.soil')}"/>
                        </div>
                    </div>
                    <div class="form-group control-group">
                        <s:label for="formRasta_rasta_terrenoCircundanteRas" cssClass="control-label req" value="%{getText('select.ground.soil')}:"></s:label>
                        <div class="controls">
                            <s:select
                                name="rasta.terrenoCircundanteRas"
                                list="{'plano o llano', 'ondulado', 'montañoso', 'ondulado y montañoso'}"           
                                headerKey="-1" 
                                headerValue="---" />
                        </div>   
                    </div>
                    <div class="form-group control-group">
                        <s:label for="formRasta_rasta_posicionPerfilRas" cssClass="control-label req" value="%{getText('select.position.soil')}:"></s:label>
                        <div class="controls">
                            <s:select
                                name="rasta.posicionPerfilRas"
                                list="{'meseta', 'cima', 'ladera convexa', 'ladera cóncava', 'ladera plana', 'plano', 'plano con ondulaciones', 'pie de una elevación'}"           
                                headerKey="-1" 
                                headerValue="---" />
                        </div>   
                    </div>
                    <div class="control-group">
                        <%@page import="java.lang.*"%>
                        <%@page import="java.util.List"%>
                        <%@page import="java.util.ArrayList"%>
                        <% String actionOpt = String.valueOf(request.getAttribute("actExe"));%>
                        <% String rowNew    = String.valueOf(request.getAttribute("rowNew"));%>
                        <table class="table table-condensed" style="width: auto;">
                            <thead>
                                <tr>
                                    <th colspan="1" rowspan="1" style="width:128px;padding-left:0;text-align: center;"><s:property value="getText('tr.density.soil')" /></th>
                                    <th colspan="1" rowspan="1" style="width:60px;padding-left:0;text-align: center;"><s:property value="getText('tr.drycolor.soil')" /></th>
                                    <th colspan="1" rowspan="1" style="width:60px;padding-left:0;text-align: center;"><s:property value="getText('tr.wetcolor.soil')" /></th>
                                    <th colspan="1" rowspan="1" style="width:175px;padding-left:0;text-align: center;"><s:property value="getText('tr.texture.soil')" /></th>
                                    <th colspan="1" rowspan="1" style="width:250px;padding-left:0;text-align: center;"><s:property value="getText('tr.resistance.soil')" /></th>
                                </tr>
                            </thead>
                            <tbody id="tableAdit">
                                <s:if test="additionalsAtrib.size()>0">
                                    <s:iterator value="additionalsAtrib" var="horizon" status="estatus">
                                        <s:include value="row-additional-horizon.jsp">
                                            <s:param name="numRows" value="#estatus.index+1" />
                                            <s:param name="actionOpt" value="{request.actExe}" />
                                        </s:include>
                                    </s:iterator>
                                </s:if>
                                <s:else>
                                    <tr value="0">
                                    </tr>   
                                </s:else>
                            </tbody>
                        </table>
                        <button type="button" class="btn btn-default" onclick="showRowAdditionalItem('../soil/showRowAdditional?action=<%=actionOpt%>&rowNew=<%=rowNew%>', 'tableAdit')"><i class="icon-plus"></i> <s:property value="getText('button.addhorizon.soil')" /></button>
                    </div> 
                    <div class="control-group">
                        <s:label for="formRasta_rasta_phRas" cssClass="control-label req" value="%{getText('text.amountph.soil')}:"></s:label>
                        <div class="controls">
                            <s:textfield cssClass="form-control" name="rasta.phRas" tooltip="%{getText('desc.amountph.soil')}"/>
                        </div>
                    </div>
                    <div class="form-group control-group">
                        <s:label for="formRasta_rasta_carbonatosRas" cssClass="control-label req" value="%{getText('select.carbonates.soil')}:"></s:label>
                            <div class="controls">
                                <s:select
                                    name="rasta.carbonatosRas"
                                    list="{'no tiene', 'bajos a muy bajos', 'medios', 'altos'}"           
                                    headerKey="-1" 
                                    headerValue="---"
                                    onchange="showOtherElementCarbonato(this.value, 'divCarbonato')" />
                            </div>   
                        </div>
                        <% String classCarbonatos = "hide"; %>
                        <s:set name="carbonato" value="rasta.carbonatosRas"/>
                        <s:if test="%{(#carbonato.equals('bajos a muy bajos')) || (#carbonato.equals('medios')) || (#carbonato.equals('altos'))}">
                            <% classCarbonatos = "";%>
                        </s:if>      
                        <div class="<%= classCarbonatos %>" id="divCarbonato">
                            <div class="control-group">
                                <s:label for="formRasta_rasta_profundidadCarbonatosRas" cssClass="control-label" value="%{getText('text.carbonatesdepth.soil')}:"></s:label>
                                <div class="controls">
                                    <s:textfield cssClass="form-control" name="rasta.profundidadCarbonatosRas" tooltip="%{getText('desc.carbonatesdepth.soil')}"/>&nbsp;cm
                                </div>
                            </div>
                        </div>
                </fieldset>
                <fieldset>
                    <legend><s:property value="getText('title.stoniness.soil')" /></legend>
                    <div class="row-fluid">
                        <div class="span8 control-group">
                            <s:label for="formRasta_rasta_rocasSuperficieRas" cssClass="control-label req" value="%{getText('select.stoninessuperficial.soil')}:"></s:label>
                            <div class="controls">
                                <s:select
                                    name="rasta.rocasSuperficieRas"
                                    list="{'sin rocas', 'rocoso', 'muy rocoso'}"           
                                    headerKey="-1" 
                                    headerValue="---" />                            
                                <s:select
                                    name="rasta.piedrasSuperficieRas"
                                    list="{'sin piedras', 'pedregoso', 'muy pedregoso'}"           
                                    headerKey="-1" 
                                    headerValue="---" />
                            </div>
                        </div>
                    </div>
                    <div class="row-fluid">
                        <div class="span8 control-group">
                            <s:label for="formRasta_rasta_rocasPerfilRas" cssClass="control-label req" value="%{getText('select.stoninesprofile.soil')}:"></s:label>
                            <div class="controls">
                                <s:select
                                    name="rasta.rocasPerfilRas"
                                    list="{'sin rocas', 'rocoso', 'muy rocoso'}"           
                                    headerKey="-1" 
                                    headerValue="---" />
                                <s:select
                                    name="rasta.piedrasPerfilRas"
                                    list="{'sin piedras', 'pedregoso', 'muy pedregoso'}"           
                                    headerKey="-1" 
                                    headerValue="---" />
                            </div>
                        </div>
                    </div>
                    <div class="control-group">
                        <s:label for="formRasta_rasta_horizontePedrogosoRocosoRas" cssClass="control-label" value="%{getText('radio.horizon.soil')}:"></s:label>
                        <div class="controls radioSelect">
                            <s:radio list="#{'true':'Si', 'false':'No'}" onclick="showSelectionRasta(this.value, 'divHorPedre')" name="rasta.horizontePedrogosoRocosoRas" />
                        </div>
                    </div> 
                    <% String classHorPedre = "hide"; %>
                    <s:set name="horPedre" value="rasta.horizontePedrogosoRocosoRas"/>
                    <s:if test="%{#horPedre}">
                        <% classHorPedre = "";%>
                    </s:if>      
                    <div class="<%= classHorPedre %>" id="divHorPedre">
                        <div class="control-group">
                            <s:label for="formRasta_rasta_profundidadHorizontePedregosoRas" cssClass="control-label" value="%{getText('text.horizondepth.soil')}:"></s:label>
                            <div class="controls">
                                <s:textfield cssClass="form-control" name="rasta.profundidadHorizontePedregosoRas" tooltip="%{getText('desc.horizondepth.soil')}"/>&nbsp;cm
                            </div>
                        </div>
                        <div class="control-group">
                            <s:label for="formRasta_rasta_espesorHorizontePedregosoRas" cssClass="control-label" value="%{getText('text.densityhorizon.soil')}:"></s:label>
                            <div class="controls">
                                <s:textfield cssClass="form-control" name="rasta.espesorHorizontePedregosoRas" tooltip="%{getText('desc.densityhorizon.soil')}"/>&nbsp;cm
                            </div>
                        </div>
                        <div class="control-group">
                            <s:label for="formRasta_rasta_profundidadPrimerasPiedrasRas" cssClass="control-label" value="%{getText('text.depthrock.soil')}:"></s:label>
                            <div class="controls">
                                <s:textfield cssClass="form-control" name="rasta.profundidadPrimerasPiedrasRas" tooltip="%{getText('desc.depthrock.soil')}"/>&nbsp;cm
                            </div>
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <legend><s:property value="getText('title.layerhardened.soil')" /></legend>    
                    <div class="control-group">
                        <s:label for="formRasta_rasta_capasEndurecidasRas" cssClass="control-label" value="%{getText('radio.layerhardened.soil')}:"></s:label>
                        <div class="controls radioSelect">
                            <s:radio list="#{'true':'Si', 'false':'No'}" onclick="showSelectionRasta(this.value, 'divCapasEnd')" name="rasta.capasEndurecidasRas" />
                        </div>
                    </div>
                    <% String classCapasEnd = "hide"; %>
                    <s:set name="capasEnd" value="rasta.capasEndurecidasRas"/>
                    <s:if test="%{#capasEnd}">
                        <% classCapasEnd = "";%>
                    </s:if>      
                    <div class="<%= classCapasEnd %>" id="divCapasEnd">
                        <div class="control-group">
                            <s:label for="formRasta_rasta_prufundidadCapasRas" cssClass="control-label" value="%{getText('text.depthlayerhardened.soil')}:"></s:label>
                                <div class="controls">
                                <s:textfield cssClass="form-control" name="rasta.prufundidadCapasRas" tooltip="%{getText('desc.depthlayerhardened.soil')}"/>&nbsp;cm
                            </div>
                        </div>
                        <div class="control-group">
                            <s:label for="formRasta_rasta_espesorCapaEndurecidaRas" cssClass="control-label" value="%{getText('text.densitylayerhardened.soil')}:"></s:label>
                                <div class="controls">
                                <s:textfield cssClass="form-control" name="rasta.espesorCapaEndurecidaRas" tooltip="%{getText('desc.densitylayerhardened.soil')}"/>&nbsp;cm
                            </div>
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <legend><s:property value="getText('title.mottledground.soil')" /></legend>    
                    <div class="control-group">
                        <s:label for="formRasta_rasta_moteadosRas" cssClass="control-label" value="%{getText('radio.mottled.soil')}:"></s:label>
                            <div class="controls radioSelect">
                            <s:radio list="#{'true':'Si', 'false':'No'}" onclick="showSelectionRasta(this.value, 'divMoteado')" name="rasta.moteadosRas" />
                        </div>
                    </div>
                    <% String classMoteado = "hide"; %>
                    <s:set name="moteado" value="rasta.moteadosRas"/>
                    <s:if test="%{#moteado}">
                        <% classMoteado = "";%>
                    </s:if>      
                    <div class="<%= classMoteado %>" id="divMoteado">
                        <div class="control-group">
                            <s:label for="formRasta_rasta_profundidadMoteadosRas" cssClass="control-label" value="%{getText('text.depthmottled.soil')}:"></s:label>
                            <div class="controls">
                                <s:textfield cssClass="form-control" name="rasta.profundidadMoteadosRas" tooltip="%{getText('desc.depthmottled.soil')}"/>&nbsp;cm
                            </div>
                        </div>
                        <div class="control-group">
                            <s:label for="formRasta_rasta_moteadosMas70cmRas" cssClass="control-label" value="%{getText('radio.seventymottled.soil')}:"></s:label>
                            <div class="controls radioSelect">
                                <s:radio list="#{'true':'Si', 'false':'No'}" name="rasta.moteadosMas70cmRas" />
                            </div>
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <legend><s:property value="getText('title.soilstructure.soil')" /></legend>    
                    <div class="form-group control-group">
                        <s:label for="formRasta_rasta_estructuraRas" cssClass="control-label req" value="%{getText('select.soilstructure.soil')}:"></s:label>
                        <div class="controls">
                            <s:select
                                name="rasta.estructuraRas"
                                list="{'granular', 'aterronada', 'prismática', 'columnar', 'laminar', 'suelta o polvosa', 'masiva'}"           
                                headerKey="-1" 
                                headerValue="---" />
                        </div>   
                    </div>
                    <div class="control-group">
                        <s:label for="formRasta_rasta_erosionRas" cssClass="control-label" value="%{getText('radio.erosion.soil')}:"></s:label>
                        <div class="controls radioSelect">
                            <s:radio list="#{'true':'Si', 'false':'No'}" name="rasta.erosionRas" />
                        </div>
                    </div>
                    <div class="control-group">
                        <s:label for="formRasta_rasta_mohoRas" cssClass="control-label" value="%{getText('radio.mold.soil')}:"></s:label>
                        <div class="controls radioSelect">
                            <s:radio list="#{'true':'Si', 'false':'No'}" name="rasta.mohoRas" />
                        </div>
                    </div>
                    <div class="form-group control-group">
                        <s:label for="formRasta_rasta_costrasDurasRas" cssClass="control-label req" value="%{getText('select.hardcrusts.soil')}:"></s:label>
                        <div class="controls">
                            <s:select
                                name="rasta.costrasDurasRas"
                                list="{'muy marcadas', 'poco marcadas', 'no hay'}"           
                                headerKey="-1" 
                                headerValue="---" />
                        </div>   
                    </div>
                    <div class="form-group control-group">
                        <s:label for="formRasta_rasta_exposicionSolRas" cssClass="control-label req" value="%{getText('select.placethesun.soil')}:"></s:label>
                        <div class="controls">
                            <s:select
                                name="rasta.exposicionSolRas"
                                list="{'la mañana y la tarde', 'la mañana', 'la tarde'}"           
                                headerKey="-1" 
                                headerValue="---" />
                        </div>   
                    </div>
                    <div class="form-group control-group">
                        <s:label for="formRasta_rasta_costrasBlancasRas" cssClass="control-label req" value="%{getText('select.whitecrusts.soil')}:"></s:label>
                        <div class="controls">
                            <s:select
                                name="rasta.costrasBlancasRas"
                                list="{'muy marcadas', 'poco marcadas', 'no hay'}"           
                                headerKey="-1" 
                                headerValue="---" />
                        </div>   
                    </div>
                    <div class="form-group control-group">
                        <s:label for="formRasta_rasta_costrasNegrasRas" cssClass="control-label req" value="%{getText('select.blackcrusts.soil')}:"></s:label>
                        <div class="controls">
                            <s:select
                                name="rasta.costrasNegrasRas"
                                list="{'muy marcadas', 'poco marcadas', 'no hay'}"           
                                headerKey="-1" 
                                headerValue="---" />
                        </div>   
                    </div>
                    <div class="control-group">
                        <s:label for="formRasta_rasta_regionSecaRas" cssClass="control-label" value="%{getText('radio.dryregion.soil')}:"></s:label>
                        <div class="controls radioSelect">
                            <s:radio list="#{'true':'Si', 'false':'No'}" name="rasta.regionSecaRas" />
                        </div>
                    </div>
                    <div class="control-group">
                        <s:label for="formRasta_rasta_raicesVivasRas" cssClass="control-label" value="%{getText('radio.livingroots.soil')}:"></s:label>
                        <div class="controls radioSelect">
                            <s:radio list="#{'true':'Si', 'false':'No'}" onclick="showSelectionRasta(this.value, 'divRaicesVivas')" name="rasta.raicesVivasRas" />
                        </div>
                    </div>
                    <% String classRaicesVivas = "hide"; %>
                    <s:set name="raicesVivas" value="rasta.raicesVivasRas"/>
                    <s:if test="%{#raicesVivas}">
                        <% classRaicesVivas = "";%>
                    </s:if>      
                    <div class="<%= classRaicesVivas %>" id="divRaicesVivas">
                        <div class="control-group">
                            <s:label for="formRasta_rasta_profundidadRaicesRas" cssClass="control-label" value="%{getText('text.depthlivingroots.soil')}:"></s:label>
                            <div class="controls">
                                <s:textfield cssClass="form-control" name="rasta.profundidadRaicesRas" tooltip="%{getText('desc.depthlivingroots.soil')}"/>&nbsp;cm
                            </div>
                        </div>
                    </div>
                    <div class="form-group control-group">
                        <s:label for="formRasta_rasta_plantasPequenasRas" cssClass="control-label req" value="%{getText('select.affectedplant.soil')}:"></s:label>
                            <div class="controls">
                                <s:select
                                    name="rasta.plantasPequenasRas"
                                    list="{'poco afectadas', 'muy afectadas', 'plantas normales', 'no hay cultivo'}"           
                                    headerKey="-1" 
                                    headerValue="---" />
                            </div>   
                        </div>
                        <div class="control-group">
                            <s:label for="formRasta_rasta_hojarascaRas" cssClass="control-label" value="%{getText('radio.muchlitter.soil')}:"></s:label>
                            <div class="controls radioSelect">
                            <s:radio list="#{'true':'Si', 'false':'No'}" name="rasta.hojarascaRas" />
                        </div>
                    </div>
                    <div class="control-group">
                        <s:label for="formRasta_rasta_sueloNegroBlandoRas" cssClass="control-label" value="%{getText('radio.soilblacksoft.soil')}:"></s:label>
                            <div class="controls radioSelect">
                            <s:radio list="#{'true':'Si', 'false':'No'}" name="rasta.sueloNegroBlandoRas" />
                        </div>
                    </div>
                    <div class="control-group">
                        <s:label for="formRasta_rasta_cuchilloPrimerHorizonteRas" cssClass="control-label" value="%{getText('radio.knifewithouteffort.soil')}:"></s:label>
                            <div class="controls radioSelect">
                            <s:radio list="#{'true':'Si', 'false':'No'}" name="rasta.cuchilloPrimerHorizonteRas" />
                        </div>
                    </div>
                    <div class="control-group">
                        <s:label for="formRasta_rasta_cercaRiosQuebradasRas" cssClass="control-label" value="%{getText('radio.nearundergroundsuperficial.soil')}:"></s:label>
                        <div class="controls radioSelect">
                            <s:radio list="#{'true':'Si', 'false':'No'}" name="rasta.cercaRiosQuebradasRas" />
                        </div>
                    </div>
                    <div class="form-group control-group">
                        <s:label for="formRasta_rasta_recubrimientoVegetalRas" cssClass="control-label req" value="%{getText('select.vegetationcover.soil')}:"></s:label>
                        <div class="controls">
                            <s:select
                                name="rasta.recubrimientoVegetalRas"
                                list="{'muy bueno', 'bueno', 'regular', 'espaciado', 'sin cobertura'}"           
                                headerKey="-1" 
                                headerValue="---" />
                        </div>   
                    </div>
                </fieldset>
                <p class="warnField reqBef"><s:property value="getText('label.requirefields')" /></p>
                <div> 
                    <s:hidden name="page"/>
                    <s:hidden name="actExe"/>    
                    <s:hidden name="rowNew"/>    
                    <s:hidden name="newRow" value="1"/>    
                    <% String actExe   = String.valueOf(request.getAttribute("actExe")); %>
                    <% if ((actExe.equals("create") && usrDao.getPrivilegeUser(user.getIdUsr(), "soil/create")) || (actExe.equals("modify") && usrDao.getPrivilegeUser(user.getIdUsr(), "soil/modify"))) { %>                
                        <sj:submit id="btnSoil" type="button" cssClass="btn btn-initial btn-large" onclick="searchDecimalNumber('formRasta'); addMessageProcess()" targets="divMessage" onCompleteTopics="completeRasta" validate="true" validateFunction="validationForm"><i class="icon-save"></i>  <s:property value="getText('button.saverasta.soil')" /></sj:submit>
                    <% } %>
                    <button class="btn btn-large bt_cancel_producer" onclick="resetForm('formRasta'); closeWindow();"><i class="icon-ban-circle"></i>  <s:property value="getText('button.cancel')" /></button>
                </div>    
            </s:form>        
            <script>
                var page = $("#formRasta_page").val();

                $.mask.definitions['i'] = "[-0-9]";
                $.mask.definitions['f'] = "[-.0-9]";
                
//                Date.prototype.format = function(format)
//                {
//                  var o = {
//                    "M+" : this.getMonth()+1, //month
//                    "d+" : this.getDate(),    //day
//                    "h+" : this.getHours(),   //hour
//                    "m+" : this.getMinutes(), //minute
//                    "s+" : this.getSeconds(), //second
//                    "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
//                    "S" : this.getMilliseconds() //millisecond
//                  }
//
//                  if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
//                    (this.getFullYear()+"").substr(4 - RegExp.$1.length));
//                  for(var k in o)if(new RegExp("("+ k +")").test(format))
//                    format = format.replace(RegExp.$1,
//                      RegExp.$1.length==1 ? o[k] :
//                        ("00"+ o[k]).substr((""+ o[k]).length));
//                  return format;
//                }
//                
//                if ($("#formRasta_rasta_fechaRas").val()) {
//                    $("#formRasta_rasta_fechaRas").val(new Date($("#formRasta_rasta_fechaRas").val()).parse($("#formRasta_rasta_fechaRas").val()));
//                }

//                if ($("#formRasta_rasta_fechaRas").val()) {
//                    function dateToDMY(date) {
//                        var d = date.getDate();
//                        var m = date.getMonth() + 1;
//                        var y = date.getFullYear();
//    //                    return '' + y + '-' + (m<=9 ? '0' + m : m) + '-' + (d <= 9 ? '0' + d : d);
//                        return '' + (d <= 9 ? '0' + d : d) + '/' + (m<=9 ? '0' + m : m) + '/' + y;
//                    }
//                    
//                    var str = $("#formRasta_rasta_fechaRas").val();
////                    str = str.split(" ");
////                    alert(str[0])
//                    alert($("#formRasta_rasta_fechaRas").val())
//                    alert(Date.parse($("#formRasta_rasta_fechaRas").val()))
//                    $("#formRasta_rasta_fechaRas").val(dateToDMY(new Date(Date.parse($("#formRasta_rasta_fechaRas").val()))));
//                    alert($("#formRasta_rasta_fechaRas").val())
//                }
                generateDegrees('formRasta_rasta_latitudRas', 'formRasta_rasta_latitude_degrees', 'formRasta_rasta_latitude_minutes', 'formRasta_rasta_latitude_seconds');
                generateDegrees('formRasta_rasta_longitudRas', 'formRasta_rasta_length_degrees', 'formRasta_rasta_length_minutes', 'formRasta_rasta_length_seconds');
                    
                $("#formRasta_rasta_fechaRas").datepicker({dateFormat: 'dd/mm/yy'});
                $("#formRasta_rasta_fechaRas").mask("99/99/9999", {placeholder: ""});
                
                $("#formRasta_rasta_latitudRas").numeric();
                $("#formRasta_rasta_latitudRas").val(parsePointSeparated($("#formRasta_rasta_latitudRas").val()));
                
                $("#formRasta_rasta_longitudRas").numeric();
                $("#formRasta_rasta_longitudRas").val(parsePointSeparated($("#formRasta_rasta_longitudRas").val()));
                
                $("#formRasta_rasta_altitudRas").numeric({decimal: false, negative: false});                
                $("#formRasta_rasta_length_degrees").numeric({decimal: false});
                $("#formRasta_rasta_length_minutes").numeric({decimal: false});
                $("#formRasta_rasta_length_seconds").numeric({decimal: false});
                $("#formRasta_rasta_latitude_degrees").numeric({decimal: false});
                $("#formRasta_rasta_latitude_minutes").numeric({decimal: false});
                $("#formRasta_rasta_latitude_seconds").numeric({decimal: false});
                
                $("#formRasta_rasta_pendienteTerrenoRas").numeric({negative: false});
                $("#formRasta_rasta_pendienteTerrenoRas").val(parsePointSeparated($("#formRasta_rasta_pendienteTerrenoRas").val()));
                
                $("#formRasta_rasta_phRas").numeric({negative: false});
                $("#formRasta_rasta_phRas").val(parsePointSeparated($("#formRasta_rasta_phRas").val()));
                
                $("#formRasta_rasta_profundidadCarbonatosRas").numeric({decimal: true, negative: false});
                $("#formRasta_rasta_profundidadCarbonatosRas").val(parsePointSeparated($("#formRasta_rasta_profundidadCarbonatosRas").val()));
                
                $("#formRasta_rasta_profundidadHorizontePedregosoRas").numeric({decimal: true, negative: false});
                $("#formRasta_rasta_profundidadHorizontePedregosoRas").val(parsePointSeparated($("#formRasta_rasta_profundidadHorizontePedregosoRas").val()));
                
                $("#formRasta_rasta_profundidadPrimerasPiedrasRas").numeric({decimal: true, negative: false});
                $("#formRasta_rasta_profundidadPrimerasPiedrasRas").val(parsePointSeparated($("#formRasta_rasta_profundidadPrimerasPiedrasRas").val()));
                
                $("#formRasta_rasta_espesorHorizontePedregosoRas").numeric({decimal: true, negative: false});
                $("#formRasta_rasta_espesorHorizontePedregosoRas").val(parsePointSeparated($("#formRasta_rasta_espesorHorizontePedregosoRas").val()));
                
                $("#formRasta_rasta_espesorCapaEndurecidaRas").numeric({decimal: false, negative: false});
                $("#formRasta_rasta_prufundidadCapasRas").numeric({decimal: true, negative: false});
                $("#formRasta_rasta_prufundidadCapasRas").val(parsePointSeparated($("#formRasta_rasta_prufundidadCapasRas").val()));
                
                $("#formRasta_rasta_profundidadMoteadosRas").numeric({decimal: true, negative: false});
                $("#formRasta_rasta_profundidadMoteadosRas").val(parsePointSeparated($("#formRasta_rasta_profundidadMoteadosRas").val()));
                $("#formRasta_rasta_profundidadRaicesRas").numeric({decimal: true, negative: false});
                $("#formRasta_rasta_profundidadRaicesRas").val(parsePointSeparated($("#formRasta_rasta_profundidadRaicesRas").val()));
                
                $.subscribe('completeRasta', function(event, data) {
                    var actExeSoil = $("#formRasta_actExe").val();
                    if (actExeSoil=='create') {
                        $('#btnSoil').on('click', function() {
                            ga('send', 'event', 'Soils', 'click', 'Create');
                        });
                    } else if (actExeSoil=='modify') {
                        $('#btnSoil').on('click', function() {
                            ga('send', 'event', 'Soils', 'click', 'Update');
                        });                
                    }
                    completeFormGetting('dialog-form', 'formRasta', 'divRasta', event.originalEvent.request.responseText);
                    setTimeout(function() {
                        showInfo("searchSoil.action?page=" + page, "divConListRasta");
                    }, 2000);
                });
                if($('.pop-over').length) {
                    $('.pop-over').popover();
                }
            </script>
        </div>
        <div class="row-fluid" id="divListRastaForm"></div>
    </body>
</html>