<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<div id="divListPrep">
    <%@ include file="info-preparations.jsp" %>            
</div>
<hr class="divider-inner-separator">
<div id="divMessSowing"></div>
<s:form id="formCropSow" action="saveSowing" cssClass="form-horizontal">
    <fieldset>
        <legend>Formulario de Siembra</legend>    
        <div class="row">
            <div class="span5">
                <s:hidden name="idCrop"/>
                <s:hidden name="typeCrop"/>
                <s:hidden name="actExe"/>
                <s:hidden name="sowing.idSow"/>
                <div class="control-group">
                    <label for="formCropSow_sowing_dateSow" class="control-label req">
                        Fecha de siembra:
                    </label>
                    <div class="date controls">
                        <s:date name="sowing.dateSow" format="dd/MM/yyyy" var="dateTransformDateSow"/>
                        <s:textfield name="sowing.dateSow" value="%{#dateTransformDateSow}" readonly="true"/>
                        <span class="prefix sec">&nbsp;[dd/mm/yyyy]</span>
                        <span class="add-on"><i class="icon-calendar"></i></span>
                    </div>                          
                </div>                          
            </div>                          
            <div class="span4" style="padding-left: 28px">
                <div class="control-group">
                    <label for="formCropSow_sowing_sowingTypes_idSowTyp" class="control-label req">
                        Método de siembra:
                    </label>
                    <div class="controls">
                        <s:select
                            name="sowing.sowingTypes.idSowTyp"
                            list="type_sow_types" 
                            listKey="idSowTyp" 
                            listValue="nameSowTyp"            
                            headerKey="-1" 
                            headerValue="---" />
                    </div> 
                </div>
            </div>
        </div>
        <div class="row">
            <div class="span5">
                <div class="control-group">
                    <label for="formCropSow_sowing_seedsNumberSow" class="control-label req">
                        <% if (typeCrop==1) { %>
                            Numero de Semillas (ha):
                        <% } else if (typeCrop==2) { %>
                            Kilos sembrados:
                        <% } %>                        
                    </label>
                    <div class="controls">
                        <s:textfield name="sowing.seedsNumberSow"/>
                    </div>                          
                </div>                          
            </div>                          
            <div class="span4" style="padding-left: 28px">
                <div class="control-group">
                    <label for="formCropSow_sowing_treatedSeedsSow" class="control-label req">
                        Semillas tratadas:
                    </label>
                    <div class="controls radioSelect">
                        <s:radio list="#{'true':'Si', 'false':'No'}" name="sowing.treatedSeedsSow" onclick="showProductUse(this.value, 'divNewProductUse');" />
                    </div>
                </div>
            </div>
        </div>
        <% String classSowTre="hide"; %>
        <s:set name="treatedSeeds" value="sowing.treatedSeedsSow"/>
        <s:if test="%{#treatedSeeds==true}">
            <% classSowTre = "";%>
        </s:if>                     
        <div class="<%= classSowTre %>" id="divNewProductUse">
            <div class="row">
                <div class="span5">
                    <div class="control-group">
                        <label for="formCropSow_sowing_chemicalsSowing_idCheSow" class="control-label">
                            Producto usado:
                        </label>
                        <div class="controls">
                            <s:select
                                name="sowing.chemicalsSowing.idCheSow"
                                list="type_chem_sow" 
                                listKey="idCheSow" 
                                listValue="nameCheSow"            
                                headerKey="-1" 
                                headerValue="---"
                                onchange="showOtherElementProductUsed(this.value, 'divNewProductSowing', 'divInfoDose')"
                            />
                        </div>                         
                    </div>                          
                </div>      
                <% String classProSow="hide"; %>
                <s:set name="chemicalSowing" value="sowing.chemicalsSowing.idCheSow"/>
                <s:if test="%{#chemicalSowing==1000000}">
                    <% classProSow = "";%>
                </s:if>
                <div class="span4 <%= classProSow %>" style="padding-left: 28px" id="divNewProductSowing">
                    <div class="control-group">
                        <label for="formCropSow_sowing_otherChemicalUsedSow" class="control-label">
                             Nuevo producto usuado:
                        </label>
                        <div class="controls">
                            <s:textfield name="sowing.otherChemicalUsedSow"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row" id="divInfoDose">
                <div class="span5">
                    <div class="control-group">
                        <label for="formCropSow_sowing_seedTreatmentDosisSow" class="control-label">
                            Dosis del producto:
                        </label>
                        <div class="controls">
                            <s:textfield name="sowing.seedTreatmentDosisSow"/>
                        </div>                         
                    </div>                          
                </div>                          
                <div class="span4" style="padding-left: 28px">
                    <div class="control-group">
                        <label for="formCropSow_sowing_doseUnits_idDosUni" class="control-label">
                            Unidad del producto:
                        </label>
                        <div class="controls">
                            <s:select
                                name="sowing.doseUnits.idDosUni"
                                list="type_dose_units" 
                                listKey="idDosUni" 
                                listValue="nameDosUni"            
                                headerKey="-1" 
                                headerValue="---"
                            />
                        </div>
                    </div>
                </div>
            </div>            
        </div>    
        <% if (typeCrop==1) { %>
            <div class="row">
                <div class="span5">
                    <div class="control-group">
                        <label for="formCropSow_sowing_genotypesSowing_idGenSow" class="control-label req">
                            Tipo de material:
                        </label>
                        <div class="controls">
                            <s:select
                                name="sowing.genotypesSowing.idGenSow"
                                list="type_genotypes_sow" 
                                listKey="idGenSow" 
                                listValue="nameGenSow"            
                                headerKey="-1" 
                                headerValue="---"
                            />
                        </div>                         
                    </div>                          
                </div>                          
                <div class="span4" style="padding-left: 28px">
                    <div class="control-group">
                        <label for="formCropSow_maize_seedsColors_idSeeCol" class="control-label req">
                            Color del endospermo:
                        </label>
                        <div class="controls">
                            <s:select
                                name="maize.seedsColors.idSeeCol"
                                list="type_seed_color" 
                                listKey="idSeeCol" 
                                listValue="colorSeeCol"            
                                headerKey="-1" 
                                headerValue="---"
                                onchange="chargeValues('/aeps-plataforma-mvn/crop/comboGetCol.action?typeCrop=%{typeCrop}', 'idColor', this.value, 'formCropSow_sowing_genotypes_idGen', 'divMessage')"
                            />
                        </div>
                    </div>
                </div>
            </div>
        <% } else if (typeCrop==2) { %>
            <div class="row">
                <div class="span5">
                    <div class="control-group">
                        <label for="formCropSow_sowing_furrowsDistanceSow" class="control-label req">
                            Distancia entre surcos (m):
                        </label>
                        <div class="controls">
                            <s:textfield name="sowing.furrowsDistanceSow"/>
                        </div>                         
                    </div>                          
                </div>                          
                <div class="span4" style="padding-left: 28px">
                    <div class="control-group">
                        <label for="formCropSow_sowing_sitesDistanceSow" class="control-label req">
                            Distancia entre sitios (m):
                        </label>
                        <div class="controls">
                            <s:textfield name="sowing.sitesDistanceSow"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="span5">
                    <div class="control-group">
                        <label for="formCropSow_beans_seedsNumberSiteBea" class="control-label">
                            Numero de semillas por sitio:
                        </label>
                        <div class="controls">
                            <s:textfield name="beans.seedsNumberSiteBea"/>
                        </div>                         
                    </div>                          
                </div>                          
                <div class="span4" style="padding-left: 28px">
                    <div class="control-group">
                        <label for="formCropSow_beans_seedsTypes_idSeeTyp" class="control-label req">
                            Tipo de semilla:
                        </label>
                        <div class="controls">
                            <s:select
                                name="beans.seedsTypes.idSeeTyp"
                                list="type_seed_type" 
                                listKey="idSeeTyp" 
                                listValue="nameSeeTyp"            
                                headerKey="-1" 
                                headerValue="---"
                            />
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="span5">
                    <div class="control-group">
                        <label for="formCropSow_sowing_seedsOrigins_idSeeOri" class="control-label">
                            Procedencia:
                        </label>
                        <div class="controls">
                            <s:select
                                name="sowing.seedsOrigins.idSeeOri"
                                list="type_seed_org" 
                                listKey="idSeeOri" 
                                listValue="nameSeeOri"            
                                headerKey="-1" 
                                headerValue="---"
                            />
                        </div>                         
                    </div>                          
                </div>             
            </div>
            <div class="row">
                <div class="span5">
                    <div class="control-group">
                        <label for="formCropSow_beans_seedsInoculations_idSeeIno" class="control-label req">
                            Inoculación de semillas:
                        </label>
                        <div class="controls">
                            <s:select
                                name="beans.seedsInoculations.idSeeIno"
                                list="type_seed_in" 
                                listKey="idSeeIno" 
                                listValue="nameSeeIno"            
                                headerKey="-1" 
                                headerValue="---"
                                onchange="showOtherElement(this.value, 'divNewInoculationSeed')"
                            />
                        </div>
                    </div>                         
                </div>        
                <% String classInoculation="hide"; %>
                <s:set name="seedInoculation" value="beans.seedsInoculations.idSeeIno"/>
                <s:if test="%{#seedInoculation==1000000}">
                    <% classInoculation = "";%>
                </s:if>
                <div class="span4 <%= classInoculation %>" style="padding-left: 28px" id="divNewInoculationSeed">
                    <div class="control-group">
                        <label for="formCropSow_beans_otroInoculationBea" class="control-label req">
                            Nueva inoculación de semillas:
                        </label>
                        <div class="controls">
                            <s:textfield name="beans.otroInoculationBea"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="span5">
                    <div class="control-group">
                        <label for="formCropSow_beans_growingEnvironment_idGroEnv" class="control-label req">
                            Hábito de Crecimiento:
                        </label>
                        <div class="controls">
                            <s:select
                                name="beans.growingEnvironment.idGroEnv"
                                list="type_grow_env" 
                                listKey="idGroEnv" 
                                listValue="nameGroEnv"            
                                headerKey="-1" 
                                headerValue="---"
                                onchange="chargeValues('/aeps-plataforma-mvn/crop/comboGetGrow.action?typeCrop=%{typeCrop}', 'idGrow', this.value, 'formCropSow_sowing_genotypes_idGen', 'divMessage')"
                            />
                        </div>                         
                    </div>                          
                </div>             
            </div>
        <% } %>
        <div class="row">
            <div class="span5">
                <div class="control-group">
                    <label for="formCropSow_sowing_genotypes_idGen" class="control-label req">
                        Material genético (nombre):
                    </label>
                    <div class="controls">
                        <s:select
                            name="sowing.genotypes.idGen"
                            list="type_genotypes" 
                            listKey="idGen" 
                            listValue="nameGen"            
                            headerKey="-1" 
                            headerValue="---"
                            onchange="showOtherElement(this.value, 'divNewMatGenet')"
                        />
                    </div>                         
                </div>                          
            </div>  
            <% String classNewMat="hide"; %>
            <s:set name="genotypesSel" value="sowing.genotypes.idGen"/>
            <s:if test="%{#genotypesSel==1000000}">
                <% classNewMat = "";%>
            </s:if>
            <div class="span4 <%= classNewMat %>" style="padding-left: 28px" id="divNewMatGenet">
                <div class="control-group">
                    <label for="formCropSow_sowing_otherGenotypeSow" class="control-label req">
                        Nuevo material genético:
                    </label>
                    <div class="controls">
                        <s:textfield name="sowing.otherGenotypeSow"/>
                    </div>
                </div>
            </div>
        </div>
        <p class="warnField reqBef">Campos Requeridos</p>
        <script>
            $("#formCropSow_sowing_dateSow").datepicker({dateFormat: 'dd/mm/yy'});
            $("#formCropSow_sowing_dateSow").mask("99/99/9999", {placeholder: " "});
            $("#formCropSow_sowing_seedsNumberSow").numeric({negative: false});
            $("#formCropSow_sowing_furrowsDistanceSow").numeric({negative: false});
            $("#formCropSow_sowing_sitesDistanceSow").numeric({negative: false});
            $("#formCropSow_beans_seedsNumberSiteBea").numeric({ decimal: false, negative: false });
            
            $("#formCropSow_sowing_seedsNumberSow").val(parsePointSeparated($("#formCropSow_sowing_seedsNumberSow").val())); 
            $("#formCropSow_sowing_furrowsDistanceSow").val(parsePointSeparated($("#formCropSow_sowing_furrowsDistanceSow").val())); 
            $("#formCropSow_sowing_sitesDistanceSow").val(parsePointSeparated($("#formCropSow_sowing_sitesDistanceSow").val())); 
//            $("#formCropSow_sowing_productionHar").numeric({decimal: false, negative: false});
//            $("#formCropSow_sowing_yieldHar").numeric({negative: false});
//            $("#formCropSow_sowing_productionPerPlantHar").numeric({negative: false});
//            $("#formCropSow_sowing_humidityPercentageHar").numeric({negative: false});
        </script>
    </fieldset>
</s:form>	
<% String actExeSow   = String.valueOf(request.getAttribute("actExe")); %>
<% if ((actExeSow=="create" && usrDao.getPrivilegeUser(user.getIdUsr(), "crop/create")) || (actExeSow=="modify" && usrDao.getPrivilegeUser(user.getIdUsr(), "crop/modify"))) { %>
    <div style="margin-bottom: 15px" id="divBtSowing">
        <sj:submit type="button" formIds="formCropSow" cssClass="btn btn-initial btn-large" onclick="addMessageProcess()" targets="divMessage" onCompleteTopics="completeSowing" validate="true" validateFunction="validationForm"><i class="icon-save"></i>  Guardar Siembra</sj:submit>
        <%--<sj:submit type="button" formIds="formCropSow" cssClass="btn btn-initial btn-large" onclick="addMessageProcess()" targets="divMessage" validate="true" onCompleteTopics="completeSowing"><i class="icon-save"></i>  Guardar Siembra</sj:submit>--%>
    </div>
<% } %>
<script>
    $.subscribe('completeSowing', function(event, data) {
        completeFormCrop('', 'formCropSow', 'divMessSowing', event.originalEvent.request.responseText);
    });
</script>