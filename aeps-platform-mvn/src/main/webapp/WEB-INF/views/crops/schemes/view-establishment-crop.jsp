<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<% if (coCode.equals("CO")) { %>
    <div id="divListRes">
        <%@ include file="../residuals/info-residuals.jsp" %>            
    </div>
<% } %>
<div id="divListPrep">
    <%@ include file="../preparations/info-preparations.jsp" %>            
</div>
<%@page import="org.aepscolombia.platform.models.dao.EntitiesDao"%>
<% Integer entTypeSowId = new EntitiesDao().getEntityTypeId(user.getIdUsr()); %>
<hr class="divider-inner-separator">
<div id="divMessSowing"></div>
<s:form id="formCropSow" action="saveSowing" cssClass="form-horizontal">
    <fieldset>
        <legend><s:property value="getText('title.formsowing.crop')" /></legend>    
        <div class="row">
            <div class="span5">
                <s:hidden name="idCrop"/>
                <s:hidden name="typeCrop"/>
                <s:hidden name="actExe"/>
                <s:hidden name="sowing.idSow"/>
                <div class="control-group">
                    <label for="formCropSow_sowing_dateSow" class="control-label req">
                        <s:property value="getText('text.sowdate.crop')" />:
                    </label>
                    <div class="date controls">
                        <s:date name="sowing.dateSow" format="MM/dd/yyyy" var="dateTransformDateSow"/>
                        <s:textfield name="sowing.dateSow" value="%{#dateTransformDateSow}" readonly="true"/>
                        <span class="prefix sec">&nbsp;[mm/dd/yyyy]</span>
                        <span class="add-on"><i class="icon-calendar"></i></span>
                    </div>                          
                </div>                          
            </div>                          
            <div class="span4" style="padding-left: 28px">
                <div class="control-group">
                    <label for="formCropSow_sowing_sowingTypes_idSowTyp" class="control-label req">
                        <s:property value="getText('select.sowmethod.crop')" />:
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
                    <label for="formCropSow_event_expected_production_pro_eve" class="control-label">
                        <s:property value="getText('text.bestyield.crop')" />:
                        <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.bestyield.crop')" />." data-title="<s:property value="getText('help.bestyield.crop')" />" data-placement="right" data-trigger="hover"></i>
                    </label>
                    <div class="controls">
                        <s:hidden name="event.idProEve"/>
                        <s:hidden name="event.fields.idFie"/>
                        <s:hidden name="event.cropsTypes.idCroTyp"/>
                        <s:hidden name="event.idProjectProEve"/>
                        <s:hidden name="event.formerCropProEve"/>
                        <s:hidden name="event.status"/>
                        <s:number name="event.expectedProductionProEve" maximumFractionDigits="10" type="number" var="performanceCrop" />
                        <s:textfield name="event.expectedProductionProEve" value="%{#performanceCrop}"/>
                    </div>                  
                </div>                          
            </div>                          
            <div class="span4" style="padding-left: 28px">
                <div class="control-group">
                    <label for="formCropSow_event_drainingProEve" class="control-label">
                        <s:property value="getText('radio.drainparcel.crop')" />:
                    </label>
                    <div class="controls radioSelect">
                        <s:radio list="#{'true':'Si', 'false':'No'}" name="event.drainingProEve" />
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="span5">
                <div class="control-group">
                    <label for="formCropSow_sowing_seedsNumberSow" class="control-label req">
                        <% if (typeCrop==1) { %>
                            <s:property value="getText('text.kgseedha.crop')" />:
                            <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.kgseedha.crop')" />." data-title="<s:property value="getText('help.kgseedha.crop')" />" data-placement="right" data-trigger="hover"></i> 
                        <% } else if (typeCrop==2) { %>
                            <s:property value="getText('text.seednumber.crop')" />:
                        <% } else if (typeCrop==4) { %>
                            <s:property value="getText('text.seednumberrice.crop')" />:
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
                        <s:property value="getText('radio.treatmentseed.crop')" />:
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
                            <s:property value="getText('select.productused.crop')" />:
                        </label>
                        <div class="controls">
                            <s:select
                                name="sowing.chemicalsSowing.idCheSow"
                                list="type_chem_sow" 
                                listKey="idCheSow" 
                                listValue="nameCheSow"            
                                headerKey="-1" 
                                headerValue="---"
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
                            <s:property value="getText('text.newprodused.crop')" />:
                        </label>
                        <div class="controls">
                            <s:textfield name="sowing.otherChemicalUsedSow"/>
                        </div>
                    </div>
                </div>
            </div>           
        </div>
        <% if (coCode.equals("CO")) { %>
            <div class="row">
                <div class="span5">
                    <div class="control-group">
                        <label for="formCropSow_sowing_furrowsDistanceSow" class="control-label req">
                            <s:property value="getText('text.furrowdistance.crop')" /> (m):
                        </label>
                        <div class="controls">
                            <s:textfield name="sowing.furrowsDistanceSow"/>
                        </div>                         
                    </div>                          
                </div>                          
                <div class="span4" style="padding-left: 28px">
                    <div class="control-group">
                        <label for="formCropSow_sowing_sitesDistanceSow" class="control-label req">
                            <s:property value="getText('text.sitesdistance.crop')" /> (m):
                        </label>
                        <div class="controls">
                            <s:textfield name="sowing.sitesDistanceSow"/>
                        </div>
                    </div>
                </div>
            </div>
        <% } %>
        <% if (typeCrop==1) { %>
            <div class="row">
                <div class="span5">
                    <div class="control-group">
                        <label for="formCropSow_maize_seedsNumberSiteMai" class="control-label req">
                            <s:property value="getText('text.seednumbersite.crop')" />:
                        </label>
                        <div class="controls">
                            <s:textfield name="maize.seedsNumberSiteMai"/>
                        </div>                         
                    </div>                          
                </div>
            </div>
            <div class="row">
                <div class="span5">
                    <div class="control-group">
                        <label for="formCropSow_sowing_genotypesSowing_idGenSow" class="control-label req">
                            <s:property value="getText('select.genotypesow.crop')" />:
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
                            <s:property value="getText('select.seedcolor.crop')" />:
                        </label>
                        <div class="controls">
                            <s:select
                                name="maize.seedsColors.idSeeCol"
                                list="type_seed_color" 
                                listKey="idSeeCol" 
                                listValue="colorSeeCol"            
                                headerKey="-1" 
                                headerValue="---"
                                onchange="chargeValues('/crop/comboGetCol.action?typeCrop=%{typeCrop}', 'idColor', this.value, 'formCropSow_sowing_genotypes_idGen', 'divMessage')"
                            />
                        </div>
                    </div>
                </div>
            </div>            
        <% } else if (typeCrop==2) { %>            
            <div class="row">
                <div class="span5">
                    <div class="control-group">
                        <label for="formCropSow_beans_seedsNumberSiteBea" class="control-label req">
                            <s:property value="getText('text.seednumbersite.crop')" />:
                        </label>
                        <div class="controls">
                            <s:textfield name="beans.seedsNumberSiteBea"/>
                        </div>                         
                    </div>                          
                </div>    
            </div>
            <div class="row">
                <div class="span5">
                    <div class="control-group">
                        <label for="formCropSow_sowing_seedsOrigins_idSeeOri" class="control-label req">
                            <s:property value="getText('select.seedorigin.crop')" />:
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
                        <label for="formCropSow_beans_seedsInoculations_idSeeIno" class="control-label">
                            <s:property value="getText('select.seedinoculation.crop')" />:
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
                            <s:property value="getText('text.newinoculation.crop')" />:
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
                            <s:property value="getText('select.growenv.crop')" />:
                        </label>
                        <div class="controls">
                            <s:select
                                name="beans.growingEnvironment.idGroEnv"
                                list="type_grow_env" 
                                listKey="idGroEnv" 
                                listValue="nameGroEnv"            
                                headerKey="-1" 
                                headerValue="---"
                                onchange="chargeValues('/crop/comboGetGrow.action?typeCrop=%{typeCrop}', 'idGrow', this.value, 'formCropSow_sowing_genotypes_idGen', 'divMessage')"
                            />
                        </div>                         
                    </div>                          
                </div>             
            </div>        
        <% } else if (typeCrop==4) { %>
            <div class="row">
                <div class="span5">
                    <div class="control-group">
                        <label for="formCropSow_sowing_genotypesSowing_idGenSow" class="control-label req">
                            <s:property value="getText('select.genotypesow.crop')" />:
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
                        <label for="formCropSow_rice_riceSystem_idRieSys" class="control-label req">
                            <s:property value="getText('select.ricesystem.crop')" />:
                        </label>
                        <div class="controls">
                            <s:select
                                name="rice.riceSystem.idRieSys"
                                list="type_rice_system" 
                                listKey="idRieSys" 
                                listValue="nameRieSys"            
                                headerKey="-1" 
                                headerValue="---"
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
                        <s:property value="getText('select.genotypename.crop')" />:
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
                        <s:property value="getText('text.newgenotypename.crop')" />:
                    </label>
                    <div class="controls">
                        <s:textfield name="sowing.otherGenotypeSow"/>
                    </div>
                </div>
            </div>
                   
        </div>
                        <div id="costsowing" class="row ">
                          <div class="span5">
                           <div class="control-group">
                            <label for="formCropSow_sowing_costSow" class="control-label ">
                                <s:property value="getText('text.costsowing.crop')" />:
                            </label>
                            <div class="controls">
                                <s:textfield name="sowing.costSow"/>
                            </div>
                          </div>
                         </div>   
                        </div>     
                    
        <% if (entTypeSowId!=3) { %>
            <p class="warnField reqBef"><s:property value="getText('label.requirefields')" /></p>
        <% } %>
        <script>
            $("#formCropSow_sowing_dateSow").datepicker({dateFormat: 'mm/dd/yy'});
            $("#formCropSow_sowing_dateSow").mask("99/99/9999", {placeholder: " "});
            $("#formCropSow_event_expected_production_pro_eve").numeric({negative: false});
            
            $("#formCropSow_sowing_seedsNumberSow").numeric({decimal: false, negative: false});
            $("#formCropSow_sowing_furrowsDistanceSow").numeric({negative: false});
            $("#formCropSow_sowing_sitesDistanceSow").numeric({negative: false});
            $("#formCropSow_beans_seedsNumberSiteBea").numeric({ decimal: false, negative: false });
            
            $("#formCropSow_sowing_seedsNumberSow").val(parsePointSeparated($("#formCropSow_sowing_seedsNumberSow").val())); 
            $("#formCropSow_sowing_furrowsDistanceSow").val(parsePointSeparated($("#formCropSow_sowing_furrowsDistanceSow").val())); 
            $("#formCropSow_sowing_sitesDistanceSow").val(parsePointSeparated($("#formCropSow_sowing_sitesDistanceSow").val())); 
        </script>
    </fieldset>
</s:form>
<% String actExeSow   = String.valueOf(request.getAttribute("actExe")); %>
<% if ((actExeSow=="create" && usrDao.getPrivilegeUser(user.getIdUsr(), "crop/create")) || (actExeSow=="modify" && usrDao.getPrivilegeUser(user.getIdUsr(), "crop/modify"))) { %>
    <% if (entTypeSowId!=3) { %>
        <div style="margin-bottom: 15px" id="divBtSowing">
            <sj:submit type="button" formIds="formCropSow" cssClass="btn btn-initial btn-large" onclick="searchDecimalNumber('formCropSow'); addMessageProcess()" targets="divMessage" onCompleteTopics="completeSowing" validate="true" validateFunction="validationForm"><i class="icon-save"></i>  <s:property value="getText('button.sowingsave.crop')" /></sj:submit>
        </div>
    <% } %>
<% } %>
<script>
    $.subscribe('completeSowing', function(event, data) {
        completeFormCrop('', 'formCropSow', 'divMessSowing', event.originalEvent.request.responseText);
    });
</script>