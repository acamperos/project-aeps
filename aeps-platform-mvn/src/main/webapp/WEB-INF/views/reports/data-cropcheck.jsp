<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page import="java.lang.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<div class="panel">
    <div class="panel-body w-box">             
        <fieldset>
            <legend>
                <h2>
                    <s:property value="getText('title.infoCrop.report')" /> 
                </h2>    
            </legend>
            <fieldset>
                <legend><h5><s:property value="getText('title.identprod.report')" /></h5>  
                </legend>
                <table class="table table-bordered">
                    <%--<s:set name="field" value="fieldInfo"/>--%>
                    <tbody>
                        <tr>
                            <th style="width: 30%"><s:property value="getText('tr.producername.report')" /></th>
                            <td><s:property value="%{fieldInfo.get('name_producer')}" /></td>
                            <th><s:property value="getText('tr.docproducer.report')" /></th>
                            <td><s:property value="%{fieldInfo.get('type_doc_pro')}" />: <s:property value="%{fieldInfo.get('no_doc_pro')}" /></td>
                        </tr>
                        <tr>
                            <th style="width: 30%"><s:property value="getText('tr.department.report')" /></th>
                            <td><s:property value="%{fieldInfo.get('name_dep')}" /></td>
                            <th><s:property value="getText('tr.municipality.report')" /></th>
                            <td><s:property value="%{fieldInfo.get('name_mun')}" /></td>
                        </tr>
                        <tr>
                            <th style="width: 30%"><s:property value="getText('tr.communename.report')" /></th>
                            <td><s:property value="%{fieldInfo.get('name_commune')}" /></td>
                            <th><s:property value="getText('tr.farmname.report')" /></th>
                            <td><s:property value="%{fieldInfo.get('name_farm')}" /></td>
                        </tr>
                        <tr>
                            <th style="width: 30%"><s:property value="getText('tr.latitude.report')" /></th>
                            <td><s:property value="%{fieldInfo.get('latitude_lot')}" /></td>
                            <th><s:property value="getText('tr.longitude.report')" /></th>
                            <td><s:property value="%{fieldInfo.get('length_lot')}" /></td>
                        </tr>
                    </tbody>
                </table>
            </fieldset>
            <s:if test="%{rasta.idRas!=null}">             
                <fieldset>
                    <legend><h5><s:property value="getText('title.features.report')" /></h5>  
                    </legend>
                    <table class="table table-bordered">
                        <tbody>
                            <tr>
                                <th style="width: 30%"><s:property value="getText('tr.pendant.report')" /></th>
                                <td><s:property value="rasta.pendienteTerrenoRas" /></td>
                                <th><s:property value="getText('tr.terreno.report')" /></th>
                                <td><s:property value="rasta.terrenoCircundanteRas" /></td>
                            </tr>
                            <tr>
                                <th style="width: 30%"><s:property value="getText('tr.profilepos.report')" /></th>
                                <td><s:property value="rasta.posicionPerfilRas" /></td>
                                <th><s:property value="getText('tr.phval.report')" /></th>
                                <td><s:property value="rasta.phRas" /></td>
                            </tr>                            
                        </tbody>
                    </table>                
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th colspan="1" rowspan="1" style="width:64px;padding-left:0;text-align: center;"><s:property value="getText('tr.nolayer.soil')" /></th>
                                <th colspan="1" rowspan="1" style="width:64px;padding-left:0;text-align: center;"><s:property value="getText('tr.density.soil')" /></th>
                                <th colspan="1" rowspan="1" style="width:60px;padding-left:0;text-align: center;"><s:property value="getText('tr.drycolor.soil')" /></th>
                                <th colspan="1" rowspan="1" style="width:60px;padding-left:0;text-align: center;"><s:property value="getText('tr.wetcolor.soil')" /></th>
                                <th colspan="1" rowspan="1" style="width:175px;padding-left:0;text-align: center;"><s:property value="getText('tr.texture.soil')" /></th>
                                <th colspan="1" rowspan="1" style="width:250px;padding-left:0;text-align: center;"><s:property value="getText('tr.resistance.soil')" /></th>
                            </tr>
                        </thead>
                        <tbody id="tableAdit">
                            <s:if test="additionalsAtrib.size()>0">
                                <s:iterator value="additionalsAtrib" var="horizon" status="status">
                                    <s:include value="row-additional-horizon.jsp">
                                        <s:param name="numRows" value="#status.index+1" />
                                    </s:include>
                                </s:iterator>
                            </s:if>
                            <s:else>
                                <tr value="0">
                                </tr>   
                            </s:else>
                        </tbody>
                    </table>
                    <table class="table table-bordered">
                        <tbody>
                            <tr>
                                <th><s:property value="getText('tr.carbonates.report')" /></th>
                                <s:set var="carSel" value="rasta.carbonatosRas"/>
                                <s:iterator value="#{'no tiene':'no tiene', 'bajos a muy bajos':'bajos a muy bajos', 'medios':'medios', 'altos':'altos'}" var="incr">
                                    <td>
                                        <s:if test="%{#incr.value == #carSel}">                                             
                                            <s:property value="#incr.value"/><i class="icon-ok main-color"></i>
                                        </s:if>
                                        <s:else>
                                            <s:property value="#incr.value"/>
                                        </s:else>
                                    </td>
                                </s:iterator>
                                <th><s:property value="getText('tr.depthcarb.report')" /></th>
                                <td><s:property value="rasta.profundidadCarbonatosRas" /></td>
                            </tr>
                            <tr>
                                <th><s:property value="getText('tr.pedregosidadsup.report')" /></th>
                                <s:set var="rocasSupSel" value="rasta.rocasSuperficieRas"/>
                                <s:set var="piedrasSupSel" value="rasta.piedrasSuperficieRas"/>
                                <s:iterator value="#{'sin rocas':'sin rocas', 'rocoso':'rocoso', 'muy rocoso':'muy rocoso'}" var="incr">
                                    <td>
                                        <s:if test="%{#incr.value == #rocasSupSel}">                                             
                                            <s:property value="#incr.value"/><i class="icon-ok main-color"></i>
                                        </s:if>
                                        <s:else>
                                            <s:property value="#incr.value"/>
                                        </s:else>
                                    </td>
                                </s:iterator>
                                <s:iterator value="#{'sin piedras':'sin piedras', 'pedregoso':'pedregoso', 'muy pedregoso':'muy pedregoso'}" var="incr">
                                    <td>
                                        <s:if test="%{#incr.value == #piedrasSupSel}">                                             
                                            <s:property value="#incr.value"/><i class="icon-ok main-color"></i>
                                        </s:if>
                                        <s:else>
                                            <s:property value="#incr.value"/>
                                        </s:else>
                                    </td>
                                </s:iterator>
                            </tr>
                            <tr>
                                <th><s:property value="getText('tr.pedregosidadper.report')" /></th>
                                <s:set var="rocasPerSel" value="rasta.rocasPerfilRas"/>
                                <s:set var="piedrasPerSel" value="rasta.piedrasPerfilRas"/>
                                <s:iterator value="#{'sin rocas':'sin rocas', 'rocoso':'rocoso', 'muy rocoso':'muy rocoso'}" var="incr">
                                    <td>
                                        <s:if test="%{#incr.value == #rocasPerSel}">                                             
                                            <s:property value="#incr.value"/><i class="icon-ok main-color"></i>
                                        </s:if>
                                        <s:else>
                                            <s:property value="#incr.value"/>
                                        </s:else>
                                    </td>
                                </s:iterator>
                                <s:iterator value="#{'sin piedras':'sin piedras', 'pedregoso':'pedregoso', 'muy pedregoso':'muy pedregoso'}" var="incr">
                                    <td>
                                        <s:if test="%{#incr.value == #piedrasPerSel}">                                             
                                            <s:property value="#incr.value"/><i class="icon-ok main-color"></i>
                                        </s:if>
                                        <s:else>
                                            <s:property value="#incr.value"/>
                                        </s:else>
                                    </td>
                                </s:iterator>
                            </tr>
                            <tr>
                                <th><s:property value="getText('tr.horizon.report')" /></th>
                                <s:set var="horizonteSel" value="%{'' + rasta.horizontePedrogosoRocosoRas}"/>
                                <s:iterator value="#{'true':'Si', 'false':'No'}" status="status" var="incr">
                                    <td> 
                                        <s:if test="%{#horizonteSel == #incr.key}">                                             
                                            <s:property value="#incr.value"/><i class="icon-ok main-color"></i>
                                        </s:if>
                                        <s:else>
                                            <s:property value="#incr.value"/>
                                        </s:else>
                                    </td>
                                </s:iterator>
                                <th><s:property value="getText('tr.depthhorizon.report')" /></th>
                                <td><s:property value="rasta.profundidadHorizontePedregosoRas" /></td>
                                <th><s:property value="getText('tr.thickhorizon.report')" /></th>
                                <td><s:property value="rasta.espesorHorizontePedregosoRas" /></td>
                            </tr>
                            <tr>
                                <th colspan="3"><s:property value="getText('tr.depthfirstrocks.report')" /></th>
                                <td><s:property value="rasta.profundidadPrimerasPiedrasRas" /></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <th><s:property value="getText('tr.capasend.report')" /></th>
                                <s:set var="capasEndSel" value="%{'' + rasta.capasEndurecidasRas}"/>
                                <s:iterator value="#{'true':'Si', 'false':'No'}" status="status" var="incr">
                                    <td> 
                                        <s:if test="%{#capasEndSel == #incr.key}">                                             
                                            <s:property value="#incr.value"/><i class="icon-ok main-color"></i>
                                        </s:if>
                                        <s:else>
                                            <s:property value="#incr.value"/>
                                        </s:else>
                                    </td>
                                </s:iterator>
                                <th><s:property value="getText('tr.depthcapasend.report')" /></th>
                                <td><s:property value="rasta.prufundidadCapasRas" /></td>
                                <th><s:property value="getText('tr.thickcapasend.report')" /></th>
                                <td><s:property value="rasta.espesorCapaEndurecidaRas" /></td>
                            </tr>
                            <tr>
                                <th><s:property value="getText('tr.mottled.report')" /></th>
                                <s:set var="mottledSel" value="%{'' + rasta.moteadosRas}"/>
                                <s:iterator value="#{'true':'Si', 'false':'No'}" status="status" var="incr">
                                    <td> 
                                        <s:if test="%{#mottledSel == #incr.key}">                                             
                                            <s:property value="#incr.value"/><i class="icon-ok main-color"></i>
                                        </s:if>
                                        <s:else>
                                            <s:property value="#incr.value"/>
                                        </s:else>
                                    </td>
                                </s:iterator>
                                <th><s:property value="getText('tr.depthmottled.report')" /></th>
                                <td><s:property value="rasta.profundidadMoteadosRas" /></td>
                            </tr>
                            <tr>
                                <th><s:property value="getText('tr.viewroots.report')" /></th>
                                <s:set var="raicesVivasSel" value="%{'' + rasta.raicesVivasRas}"/>
                                <s:iterator value="#{'true':'Si', 'false':'No'}" status="status" var="incr">
                                    <td> 
                                        <s:if test="%{#raicesVivasSel == #incr.key}">                                             
                                            <s:property value="#incr.value"/><i class="icon-ok main-color"></i>
                                        </s:if>
                                        <s:else>
                                            <s:property value="#incr.value"/>
                                        </s:else>
                                    </td>
                                </s:iterator>
                                <th><s:property value="getText('tr.depthviewroots.report')" /></th>
                                <td><s:property value="rasta.profundidadRaicesRas" /></td>
                            </tr>
                            <tr>
                                <th><s:property value="getText('tr.structure.report')" /></th>
                                <s:set var="estructuraSel" value="%{'' + rasta.estructuraRas}"/>
                                <s:iterator value="#{'granular':'granular', 'aterronada':'aterronada', 'prismática':'prismática', 'columnar':'columnar', 'laminar':'laminar', 'suelta o polvosa':'suelta o polvosa', 'masiva':'masiva'}" status="status" var="incr">
                                    <td> 
                                        <s:if test="%{#estructuraSel == #incr.key}">                                             
                                            <s:property value="#incr.value"/><i class="icon-ok main-color"></i>
                                        </s:if>
                                        <s:else>
                                            <s:property value="#incr.value"/>
                                        </s:else>
                                    </td>
                                </s:iterator>
                            </tr>
                            <tr>
                                <th><s:property value="getText('tr.costrasduras.report')" /></th>
                                <s:set var="costrasDurasSel" value="%{'' + rasta.costrasDurasRas}"/>
                                <s:iterator value="#{'muy marcadas':'muy marcadas', 'poco marcadas':'poco marcadas', 'no hay':'no hay'}" status="status" var="incr">
                                    <td> 
                                        <s:if test="%{#costrasDurasSel == #incr.key}">                                             
                                            <s:property value="#incr.value"/><i class="icon-ok main-color"></i>
                                        </s:if>
                                        <s:else>
                                            <s:property value="#incr.value"/>
                                        </s:else>
                                    </td>
                                </s:iterator>
                            </tr>
                            <tr>
                                <th><s:property value="getText('tr.exposicion.report')" /></th>
                                <s:set var="exposicionSel" value="%{'' + rasta.exposicionSolRas}"/>
                                <s:iterator value="#{'la mañana y la tarde':'la mañana y la tarde', 'la mañana':'la mañana', 'la tarde':'la tarde'}" status="status" var="incr">
                                    <td> 
                                        <s:if test="%{#horizonteSel == #incr.key}">                                             
                                            <s:property value="#incr.value"/><i class="icon-ok main-color"></i>
                                        </s:if>
                                        <s:else>
                                            <s:property value="#incr.value"/>
                                        </s:else>
                                    </td>
                                </s:iterator>
                            </tr>
                            <tr>
                                <th><s:property value="getText('tr.costrasblancas.report')" /></th>
                                <s:set var="costrasBlancasSel" value="%{'' + rasta.costrasBlancasRas}"/>
                                <s:iterator value="#{'muy marcadas':'muy marcadas', 'poco marcadas':'poco marcadas', 'no hay':'no hay'}" status="status" var="incr">
                                    <td> 
                                        <s:if test="%{#costrasBlancasSel == #incr.key}">                                             
                                            <s:property value="#incr.value"/><i class="icon-ok main-color"></i>
                                        </s:if>
                                        <s:else>
                                            <s:property value="#incr.value"/>
                                        </s:else>
                                    </td>
                                </s:iterator>
                            </tr>
                            <tr>
                                <th><s:property value="getText('tr.costrascolor.report')" /></th>
                                <s:set var="costrasNegrasSel" value="%{'' + rasta.costrasNegrasRas}"/>
                                <s:iterator value="#{'muy marcadas':'muy marcadas', 'poco marcadas':'poco marcadas', 'no hay':'no hay'}" status="status" var="incr">
                                    <td> 
                                        <s:if test="%{#costrasNegrasSel == #incr.key}">                                             
                                            <s:property value="#incr.value"/><i class="icon-ok main-color"></i>
                                        </s:if>
                                        <s:else>
                                            <s:property value="#incr.value"/>
                                        </s:else>
                                    </td>
                                </s:iterator>
                            </tr>
                            <tr>
                                <th colspan="3"><s:property value="getText('tr.mottledseventy.report')" /></th>
                                <s:set var="mottledseventySel" value="%{'' + rasta.moteadosMas70cmRas}"/>
                                <s:iterator value="#{'true':'Si', 'false':'No'}" status="status" var="incr">
                                    <td> 
                                        <s:if test="%{#mottledseventySel == #incr.key}">                                             
                                            <s:property value="#incr.value"/><i class="icon-ok main-color"></i>
                                        </s:if>
                                        <s:else>
                                            <s:property value="#incr.value"/>
                                        </s:else>
                                    </td>
                                </s:iterator>
                            </tr>
                            <tr>
                                <th colspan="3"><s:property value="getText('tr.erosion.report')" /></th>
                                <s:set var="erosionSel" value="%{'' + rasta.erosionRas}"/>
                                <s:iterator value="#{'true':'Si', 'false':'No'}" status="status" var="incr">
                                    <td> 
                                        <s:if test="%{#erosionSel == #incr.key}">                                             
                                            <s:property value="#incr.value"/><i class="icon-ok main-color"></i>
                                        </s:if>
                                        <s:else>
                                            <s:property value="#incr.value"/>
                                        </s:else>
                                    </td>
                                </s:iterator>
                            </tr>
                            <tr>
                                <th colspan="3"><s:property value="getText('tr.moho.report')" /></th>
                                <s:set var="mohoSel" value="%{'' + rasta.mohoRas}"/>
                                <s:iterator value="#{'true':'Si', 'false':'No'}" status="status" var="incr">
                                    <td> 
                                        <s:if test="%{#mohoSel == #incr.key}">                                             
                                            <s:property value="#incr.value"/><i class="icon-ok main-color"></i>
                                        </s:if>
                                        <s:else>
                                            <s:property value="#incr.value"/>
                                        </s:else>
                                    </td>
                                </s:iterator>
                            </tr>
                            <tr>
                                <th colspan="3"><s:property value="getText('tr.dryregion.report')" /></th>
                                <s:set var="dryregionSel" value="%{'' + rasta.regionSecaRas}"/>
                                <s:iterator value="#{'true':'Si', 'false':'No'}" status="status" var="incr">
                                    <td> 
                                        <s:if test="%{#dryregionSel == #incr.key}">                                             
                                            <s:property value="#incr.value"/><i class="icon-ok main-color"></i>
                                        </s:if>
                                        <s:else>
                                            <s:property value="#incr.value"/>
                                        </s:else>
                                    </td>
                                </s:iterator>
                            </tr>                            
                            <tr>
                                <th colspan="3"><s:property value="getText('tr.viewsmallplants.report')" /></th>
                                <s:set var="smallPlantsSel" value="%{'' + rasta.plantasPequenasRas}"/>
                                <s:iterator value="#{'poco afectadas':'poco afectadas', 'muy afectadas':'muy afectadas', 'plantas normales':'plantas normales', 'no hay cultivo':'no hay cultivo'}" status="status" var="incr">
                                    <td> 
                                        <s:if test="%{#smallPlantsSel == #incr.key}">                                             
                                            <s:property value="#incr.value"/><i class="icon-ok main-color"></i>
                                        </s:if>
                                        <s:else>
                                            <s:property value="#incr.value"/>
                                        </s:else>
                                    </td>
                                </s:iterator>
                            </tr>
                            <tr>
                                <th colspan="3"><s:property value="getText('tr.viewhojarasca.report')" /></th>
                                <s:set var="hojarascaSel" value="%{'' + rasta.hojarascaRas}"/>
                                <s:iterator value="#{'true':'Si', 'false':'No'}" status="status" var="incr">
                                    <td> 
                                        <s:if test="%{#hojarascaSel == #incr.key}">                                             
                                            <s:property value="#incr.value"/><i class="icon-ok main-color"></i>
                                        </s:if>
                                        <s:else>
                                            <s:property value="#incr.value"/>
                                        </s:else>
                                    </td>
                                </s:iterator>
                            </tr>
                            <tr>
                                <th colspan="3"><s:property value="getText('tr.soilisblack.report')" /></th>
                                <s:set var="soilBlackSel" value="%{'' + rasta.sueloNegroBlandoRas}"/>
                                <s:iterator value="#{'true':'Si', 'false':'No'}" status="status" var="incr">
                                    <td> 
                                        <s:if test="%{#soilBlackSel == #incr.key}">                                             
                                            <s:property value="#incr.value"/><i class="icon-ok main-color"></i>
                                        </s:if>
                                        <s:else>
                                            <s:property value="#incr.value"/>
                                        </s:else>
                                    </td>
                                </s:iterator>
                            </tr>
                            <tr>
                                <th colspan="3"><s:property value="getText('tr.knifeinhorizon.report')" /></th>
                                <s:set var="knifeSel" value="%{'' + rasta.cuchilloPrimerHorizonteRas}"/>
                                <s:iterator value="#{'true':'Si', 'false':'No'}" status="status" var="incr">
                                    <td> 
                                        <s:if test="%{#knifeSel == #incr.key}">                                             
                                            <s:property value="#incr.value"/><i class="icon-ok main-color"></i>
                                        </s:if>
                                        <s:else>
                                            <s:property value="#incr.value"/>
                                        </s:else>
                                    </td>
                                </s:iterator>
                            </tr>
                            <tr>
                                <th colspan="3"><s:property value="getText('tr.neartoriver.report')" /></th>
                                <s:set var="nearRiverSel" value="%{'' + rasta.cercaRiosQuebradasRas}"/>
                                <s:iterator value="#{'true':'Si', 'false':'No'}" status="status" var="incr">
                                    <td> 
                                        <s:if test="%{#nearRiverSel == #incr.key}">                                             
                                            <s:property value="#incr.value"/><i class="icon-ok main-color"></i>
                                        </s:if>
                                        <s:else>
                                            <s:property value="#incr.value"/>
                                        </s:else>
                                    </td>
                                </s:iterator>
                            </tr>
                            <tr>
                                <th colspan="2"><s:property value="getText('tr.coatingplant.report')" /></th>
                                <s:set var="recubSel" value="%{'' + rasta.recubrimientoVegetalRas}"/>
                                <s:iterator value="#{'muy bueno':'muy bueno', 'bueno':'bueno', 'regular':'regular', 'espaciado':'espaciado', 'sin cobertura':'sin cobertura'}" status="status" var="incr">
                                    <td> 
                                        <s:if test="%{#recubSel == #incr.key}">                                             
                                            <s:property value="#incr.value"/><i class="icon-ok main-color"></i>
                                        </s:if>
                                        <s:else>
                                            <s:property value="#incr.value"/>
                                        </s:else>
                                    </td>
                                </s:iterator>
                            </tr>
                        </tbody>
                    </table>
                    <h5><s:property value="getText('title.lastCropType.report')" /></h5>  
                    <table class="table table-bordered" style="width: 30%">
                        <tbody>
                            <tr>
                                <th><s:property value="getText('tr.lastcroptype.report')" /></th>
                            </tr>
                            <s:iterator value="lastCrops" var="incr">
                                <tr>
                                    <td>
                                        <s:property value="%{#incr.get('crop_name')}"/>
                                    </td>
                                </tr>
                            </s:iterator>
                        </tbody>
                    </table>
                </fieldset>
            </s:if>
            <fieldset>
                <legend><h5><s:property value="getText('title.preparations.report')" /></h5></legend>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th><s:property value="getText('tr.datepreparation.report')" /></th>
                            <th><s:property value="getText('tr.preparationtype.report')" /></th>
                            <th><s:property value="getText('tr.depthpreparation.report')" /></th>
                        </tr>
                    </thead>
                    <tbody>
                        <s:iterator value="listPrep" var="prep">
                            <tr>
                                <s:date name="datePrep" format="dd/MM/yyyy" var="dateTransformRowPrep"/>
                                <td><s:property value="%{#dateTransformRowPrep}" /></td>
                                <s:if test="%{#namePrep != ''}">                                             
                                    <td><s:property value="namePrep"/></td>
                                </s:if>
                                <s:else>
                                    <td><s:property value="otherNamePrep"/></td>
                                </s:else>     
                                <td><s:property value="depthPrep" /></td>                                              
                            </tr>
                        </s:iterator>
                    </tbody>
                </table>
            </fieldset>
            <fieldset>
                <legend><h5><s:property value="getText('title.sowing.report')" /></h5></legend>
                <table class="table table-bordered">
                    <tbody>
                        <tr>                            
                            <th><s:property value="getText('tr.materialstype.report')" /></th>
                            <s:iterator value="type_genotypes_sow" var="ferGen" status="estatus">
                                <td>
                                    <s:if test="%{idGenSow == sowing.genotypesSowing.idGenSow}">                                             
                                        <s:property value="nameGenSow"/><i class="icon-ok main-color"></i>
                                    </s:if>
                                    <s:else>
                                        <s:property value="nameGenSow"/>
                                    </s:else>
                                </td>
                            </s:iterator>
                        </tr>
                        <tr>
                            <th colspan="2"><s:property value="getText('tr.seednumbersite.report')" /></th>
                            <s:set var="typeCropSel" value="%{cropInfo.get('typeCrop')}"/>
                            <td colspan="3">
                                <s:if test="%{#typeCropSel==1}">
                                    <s:property value="maize.seedsNumberSiteMai" />
                                </s:if>
                                <s:elseif test="%{#typeCropSel==2}">
                                    <s:property value="beans.seedsNumberSiteBea" />
                                </s:elseif>
                            </td>
                        </tr>
                        <tr>
                            <th colspan="2"><s:property value="getText('tr.genotypename.report')" /></th>
                            <td colspan="3">
                                <s:if test="%{sowing.genotypes.idGen!=1000000}">
                                    <s:property value="sowing.genotypes.nameGen" />
                                </s:if>
                                <s:else>
                                    <s:property value="sowing.otherGenotypeSow" />
                                </s:else>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </fieldset>
            <fieldset>
                <legend><h5><s:property value="getText('title.cropnutrition.report')" /></h5></legend>
                <h5><s:property value="getText('title.chemical.report')" /></h5>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th><s:property value="getText('tr.appdate.report')" /></th>            
                            <th><s:property value="getText('tr.amountproduct.report')" /></th>       
                            <th><s:property value="getText('tr.composition.report')" /></th>
                            <th><s:property value="getText('tr.whicfertilization.report')" /></th>
                        </tr>
                    </thead>
                    <tbody>
                        <s:set name="valIdFer" value="0"/>
                        <s:iterator value="listFert" var="ferGen" status="estatus">
                            <s:if test="%{infoFert.get('ferTyp').equals('Quimica')}">
                                <tr id="trFerChe${idFerChe}">
                                    <s:if test="%{#valIdFer!=#attr.idFer}">   
                                        <s:date name="dateFer" format="dd/MM/yyyy" var="dateTransformRowFer"/>
                                        <td rowspan="${contFert}"><s:property value="%{#dateTransformRowFer}" /></td>
                                        <td><s:property value="%{infoFert.get('amountUsed')}" /></td>
                                        <td><s:property value="%{infoFert.get('composition')}" /></td>
                                        <s:set name="useFerTyp" value="%{infoFert.get('idFerTyp')}"/>
                                        <s:if test="%{#useFerTyp!=null}">
                                            <td><s:property value="%{infoFert.get('nameFerTyp')}" /></td>
                                        </s:if> 
                                        <s:elseif test="%{#useFerTyp==null}">
                                            <td><s:property value="%{infoFert.get('otherProduct')}" /></td>
                                        </s:elseif>
                                    </s:if>                                
                                    <s:else>
                                        <s:date name="dateFer" format="MM/dd/yyyy" var="dateTransformRowFer"/>
                                        <td><s:property value="%{infoFert.get('ferTyp')}" /></td>
                                        <td><s:property value="%{infoFert.get('amountUsed')}" /></td>
                                        <s:set name="useFerTyp" value="%{infoFert.get('idFerTyp')}"/>
                                        <s:if test="%{#useFerTyp!=null}">
                                            <td><s:property value="%{infoFert.get('nameFerTyp')}" /></td>
                                        </s:if> 
                                        <s:elseif test="%{#useFerTyp==null}">
                                            <td><s:property value="%{infoFert.get('otherProduct')}" /></td>
                                        </s:elseif>      
                                    </s:else>                                                               
                                </tr>
                            </s:if>
                            <s:set name="valIdFer" value="%{#attr.idFer}"/>
                        </s:iterator>
                    </tbody>
                </table>
                <h5><s:property value="getText('title.organic.report')" /></h5>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th><s:property value="getText('tr.appdate.report')" /></th>            
                            <th><s:property value="getText('tr.amountproduct.report')" /></th>       
                            <th><s:property value="getText('tr.whicfertilization.report')" /></th>
                        </tr>
                    </thead>
                    <tbody>
                        <s:set name="valIdFer" value="0"/>
                        <s:iterator value="listFert" var="ferGen" status="estatus">
                            <s:if test="%{infoFert.get('ferTyp').equals('Organica')}">
                                <tr id="trFerOrg${idFerOrg}">
                                    <s:if test="%{#valIdFer!=#attr.idFer}">   
                                        <s:date name="dateFer" format="dd/MM/yyyy" var="dateTransformRowFer"/>
                                        <td rowspan="${contFert}"><s:property value="%{#dateTransformRowFer}" /></td>
                                        <td><s:property value="%{infoFert.get('amountUsed')}" /></td>
                                        <s:set name="useFerTyp" value="%{infoFert.get('idFerTyp')}"/>
                                        <s:if test="%{#useFerTyp!=null}">
                                            <td><s:property value="%{infoFert.get('nameFerTyp')}" /></td>
                                        </s:if> 
                                        <s:elseif test="%{#useFerTyp==null}">
                                            <td><s:property value="%{infoFert.get('otherProduct')}" /></td>
                                        </s:elseif>
                                    </s:if>                                
                                    <s:else>
                                        <s:date name="dateFer" format="MM/dd/yyyy" var="dateTransformRowFer"/>
                                        <td><s:property value="%{infoFert.get('ferTyp')}" /></td>
                                        <td><s:property value="%{infoFert.get('amountUsed')}" /></td>
                                        <s:set name="useFerTyp" value="%{infoFert.get('idFerTyp')}"/>
                                        <s:if test="%{#useFerTyp!=null}">
                                            <td><s:property value="%{infoFert.get('nameFerTyp')}" /></td>
                                        </s:if> 
                                        <s:elseif test="%{#useFerTyp==null}">
                                            <td><s:property value="%{infoFert.get('otherProduct')}" /></td>
                                        </s:elseif>      
                                    </s:else>                                                          
                                </tr>
                            </s:if>
                            <s:set name="valIdFer" value="%{#attr.idFer}"/>
                        </s:iterator>
                    </tbody>
                </table>
                <h5><s:property value="getText('title.amendments.report')" /></h5>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th><s:property value="getText('tr.appdate.report')" /></th>            
                            <th><s:property value="getText('tr.amountproduct.report')" /></th>       
                            <th><s:property value="getText('tr.whicfertilization.report')" /></th>
                        </tr>
                    </thead>
                    <tbody>
                        <s:set name="valIdFer" value="0"/>
                        <s:iterator value="listFert" var="ferGen" status="estatus">
                            <s:if test="%{infoFert.get('ferTyp').equals('Enmienda')}">
                                <tr id="trFerAme${idFerAme}">
                                    <s:if test="%{#valIdFer!=#attr.idFer}">   
                                        <s:date name="dateFer" format="dd/MM/yyyy" var="dateTransformRowFer"/>
                                        <td rowspan="${contFert}"><s:property value="%{#dateTransformRowFer}" /></td>
                                        <td><s:property value="%{infoFert.get('amountUsed')}" /></td>
                                        <s:set name="useFerTyp" value="%{infoFert.get('idFerTyp')}"/>
                                        <s:if test="%{#useFerTyp!=null}">
                                            <td><s:property value="%{infoFert.get('nameFerTyp')}" /></td>
                                        </s:if> 
                                        <s:elseif test="%{#useFerTyp==null}">
                                            <td><s:property value="%{infoFert.get('otherProduct')}" /></td>
                                        </s:elseif>
                                    </s:if>                                
                                    <s:else>
                                        <s:date name="dateFer" format="MM/dd/yyyy" var="dateTransformRowFer"/>
                                        <td><s:property value="%{infoFert.get('ferTyp')}" /></td>
                                        <td><s:property value="%{infoFert.get('amountUsed')}" /></td>
                                        <s:set name="useFerTyp" value="%{infoFert.get('idFerTyp')}"/>
                                        <s:if test="%{#useFerTyp!=null}">
                                            <td><s:property value="%{infoFert.get('nameFerTyp')}" /></td>
                                        </s:if> 
                                        <s:elseif test="%{#useFerTyp==null}">
                                            <td><s:property value="%{infoFert.get('otherProduct')}" /></td>
                                        </s:elseif>      
                                    </s:else>                                                       
                                </tr>
                            </s:if>
                            <s:set name="valIdFer" value="%{#attr.idFer}"/>
                        </s:iterator>
                    </tbody>
                </table>
            </fieldset>
            <fieldset>
                <legend><h5><s:property value="getText('title.cropcontrol.report')" /></h5></legend>
                <h5><s:property value="getText('title.pests.report')" /></h5>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th><s:property value="getText('tr.datecontrol.report')" /></th>            
                            <th><s:property value="getText('tr.objtivecontrol.report')" /></th>       
                            <th><s:property value="getText('tr.controltype.report')" /></th>
                            <th><s:property value="getText('tr.dosecontrol.report')" /></th>
                        </tr>
                    </thead>
                    <tbody>
                        <s:iterator value="listCont" var="contGen" status="estatus">
                            <s:if test="%{idTarTyp==1}">
                                <tr id="trFerPest${idCon}">
                                    <s:date name="dateCon" format="MM/dd/yyyy" var="dateTransformRowCon"/>
                                    <td><s:property value="%{#dateTransformRowCon}" /></td>
                                    <td><s:property value="nameConTyp" /></td>
                                    <s:if test="%{conType==1}">
                                        <td><s:property value="orgCon" /><br />(<s:property value="getText('td.biologic.control')" />)</td>  
                                        <td><s:property value="doseCon" /></td>
                                    </s:if>
                                    <s:elseif test="%{conType==2 || conType==6}">
                                        <td><s:property value="chemCon" /><br />(<s:property value="getText('td.chemical.control')" />)</td>  
                                        <td><s:property value="doseCon" /></td>
                                    </s:elseif>
                                    <s:elseif test="%{conType==4 || conType==8}">
                                        <td>(<s:property value="getText('td.machined.control')" />)</td>  
                                        <td><s:property value="doseCon" /></td>    
                                    </s:elseif>
                                    <s:elseif test="%{conType==5 || conType==9}">
                                        <td>(<s:property value="getText('td.manual.control')" />)</td>
                                        <td><s:property value="doseCon" /></td>    
                                    </s:elseif>                                              
                                </tr>
                            </s:if>
                        </s:iterator>
                    </tbody>
                </table>
                <h5><s:property value="getText('title.diseases.report')" /></h5>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th><s:property value="getText('tr.datecontrol.report')" /></th>            
                            <th><s:property value="getText('tr.objtivecontrol.report')" /></th>       
                            <th><s:property value="getText('tr.controltype.report')" /></th>
                            <th><s:property value="getText('tr.dosecontrol.report')" /></th>
                        </tr>
                    </thead>
                    <tbody>
                        <s:iterator value="listCont" var="contGen" status="estatus">
                            <s:if test="%{idTarTyp==3}">
                                <tr id="trFerPest${idCon}">
                                    <s:date name="dateCon" format="MM/dd/yyyy" var="dateTransformRowCon"/>
                                    <td><s:property value="%{#dateTransformRowCon}" /></td>
                                    <td><s:property value="nameConTyp" /></td>
                                    <s:if test="%{conType==1}">
                                        <td><s:property value="orgCon" /><br />(<s:property value="getText('td.biologic.control')" />)</td>  
                                        <td><s:property value="doseCon" /></td>
                                    </s:if>
                                    <s:elseif test="%{conType==2 || conType==6}">
                                        <td><s:property value="chemCon" /><br />(<s:property value="getText('td.chemical.control')" />)</td>  
                                        <td><s:property value="doseCon" /></td>
                                    </s:elseif>
                                    <s:elseif test="%{conType==4 || conType==8}">
                                        <td>(<s:property value="getText('td.machined.control')" />)</td>  
                                        <td><s:property value="doseCon" /></td>    
                                    </s:elseif>
                                    <s:elseif test="%{conType==5 || conType==9}">
                                        <td>(<s:property value="getText('td.manual.control')" />)</td>
                                        <td><s:property value="doseCon" /></td>    
                                    </s:elseif>                                              
                                </tr>
                            </s:if>
                        </s:iterator>
                    </tbody>
                </table>
                <h5><s:property value="getText('title.weeds.report')" /></h5>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th><s:property value="getText('tr.datecontrol.report')" /></th>            
                            <th><s:property value="getText('tr.objtivecontrol.report')" /></th>       
                            <th><s:property value="getText('tr.controltype.report')" /></th>
                            <th><s:property value="getText('tr.dosecontrol.report')" /></th>
                        </tr>
                    </thead>
                    <tbody>
                        <s:iterator value="listCont" var="contGen" status="estatus">
                            <s:if test="%{idTarTyp==2}">
                                <tr id="trFerPest${idCon}">
                                    <s:date name="dateCon" format="MM/dd/yyyy" var="dateTransformRowCon"/>
                                    <td><s:property value="%{#dateTransformRowCon}" /></td>
                                    <td><s:property value="nameConTyp" /></td>
                                    <s:if test="%{conType==1}">
                                        <td><s:property value="orgCon" /><br />(<s:property value="getText('td.biologic.control')" />)</td>  
                                        <td><s:property value="doseCon" /></td>
                                    </s:if>
                                    <s:elseif test="%{conType==2 || conType==6}">
                                        <td><s:property value="chemCon" /><br />(<s:property value="getText('td.chemical.control')" />)</td>  
                                        <td><s:property value="doseCon" /></td>
                                    </s:elseif>
                                    <s:elseif test="%{conType==4 || conType==8}">
                                        <td>(<s:property value="getText('td.machined.control')" />)</td>  
                                        <td><s:property value="doseCon" /></td>    
                                    </s:elseif>
                                    <s:elseif test="%{conType==5 || conType==9}">
                                        <td>(<s:property value="getText('td.manual.control')" />)</td>
                                        <td><s:property value="doseCon" /></td>    
                                    </s:elseif>                                              
                                </tr>
                            </s:if>
                        </s:iterator>
                    </tbody>
                </table>
            </fieldset>
            <fieldset>
                <legend><h5><s:property value="getText('title.population.report')" /></h5></legend>
                <table class="table table-bordered" style="width: 40%">
                    <tbody>
                        <tr>                            
                            <th><s:property value="getText('tr.emergencydate.report')" /></th>
                            <td>
                                <s:date name="phys.emergencePhyMon" format="dd/MM/yyyy" var="dateTransformPhy"/>
                                <s:property value="%{#dateTransformPhy}"/>
                            </td>
                        </tr>
                        <tr>                            
                            <th><s:property value="getText('tr.dayspopulation.report')" /></th>
                            <td><s:property value="phys.daysPopulationMonFis"/></td>
                        </tr>
                        <tr>                            
                            <th><s:property value="getText('tr.floweringdate.report')" /></th>
                            <td>
                                <s:date name="phys.floweringDatePhyMon" format="dd/MM/yyyy" var="dateTransformFlow"/>
                                <s:property value="%{#dateTransformFlow}"/>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </fieldset>
            <fieldset>
                <legend><h5><s:property value="getText('title.irrigations.report')" /></h5></legend>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th><s:property value="getText('tr.irrigationdate.report')" /></th>
                            <th><s:property value="getText('tr.irrtypes.report')" /></th>
                            <th><s:property value="getText('tr.amountirr.report')" /></th>
                        </tr>
                    </thead>
                    <tbody>
                        <s:iterator value="listIrr" var="irr">
                            <tr>
                                <s:date name="dateIrr" format="dd/MM/yyyy" var="dateTransformRowIrr"/>
                                <td><s:property value="%{#dateTransformRowIrr}" /></td>                                
                                <td><s:property value="nameIrrType" /></td>       
                                <td><s:property value="amountIrr" /></td>
                            </tr>
                        </s:iterator>
                    </tbody>
                </table>
            </fieldset>
        </fieldset>
    </div>       
</div>