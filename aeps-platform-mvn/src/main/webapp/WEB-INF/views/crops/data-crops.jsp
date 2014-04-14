<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<!DOCTYPE html>
<html>
    <head></head>
    <body>     
        <div class="container">
            <ul id="breadcrumbs">
                <li><s:a href="%{request.getContextPath()}/dashboard.action" targets="divBodyLayout"><i class="icon-home"></i>Inicio</s:a></li>
                <li><s:a href="%{request.getContextPath()}/getting.action" targets="divBodyLayout">Recolectar datos</s:a></li>
                <li><s:a href="%{request.getContextPath()}/listCrop.action" targets="divBodyLayout">Listar cultivos</s:a></li>
                <li><span>Informacion cultivo</span></li>
            </ul>
        </div>
        <div class="container" id="divDataInfoCrop">
            <%@ include file="data-infocrop.jsp" %>                 
        </div>     
        <% int typeCrop = (request.getAttribute("typeCrop") != null) ? Integer.parseInt(String.valueOf(request.getAttribute("typeCrop"))) : 1;%>                    
        <div class="container panel" id="divDataExtendCrop" style="margin-top: 20px"> 
            <%--<%@ include file="info-crops.jsp" %>--%>
            <div class="accordion" id="accordion2" style="margin-bottom: 0">
                <div class="accordion-group">
                    <div class="accordion-heading">
                        <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseOne">
                            <h3>Establecimiento del cultivo <i class="colOne icon-chevron-down"></i></h3> 
                        </a>
                    </div>
                    <div id="collapseOne" class="accordion-body collapse">				
                        <div class="accordion-inner">
                            <%@ include file="stablishment-crop.jsp" %>
                        </div>
                    </div>
                </div>
                <div class="accordion-group">
                    <div class="accordion-heading">
                        <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
                            <h3>Nutrici&oacute;n <i class="colTwo icon-chevron-down"></i></h3>
                        </a>
                    </div>
                    <div id="collapseTwo" class="accordion-body collapse">				
                        <div class="accordion-inner">
                            <%--<%@ include file="nutrition.jsp" %>--%>
                        </div>
                    </div>
                </div>
                <div class="accordion-group">
                    <div class="accordion-heading">
                        <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion3" href="#collapseThree">
                            <h3>Protecci&oacute;n <i class="colThree icon-chevron-down"></i></h3>
                        </a>
                    </div>
                    <div id="collapseThree" class="accordion-body collapse">				
                        <div class="accordion-inner">
                            <%--<%@ include file="protection.jsp" %>--%>
                        </div>
                    </div>
                </div>  
                <div class="accordion-group">
                    <div class="accordion-heading">
                        <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion4" href="#collapseFour">
                            <h3>Monitoreo <i class="colFour icon-chevron-down"></i></h3>
                        </a>
                    </div>
                    <div id="collapseFour" class="accordion-body collapse">				
                        <div class="accordion-inner">
                            <%--<%@ include file="monitoring.jsp" %>--%>
                            <!--<?php include_once('control.php') ?>-->
                        </div>
                    </div>
                </div>
                <div class="accordion-group">
                    <div class="accordion-heading">
                        <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion5" href="#collapseFive">
                            <h3>Datos de la cosecha <i class="colFive icon-chevron-down"></i></h3>
                        </a>
                    </div>
                    <div id="collapseFive" class="accordion-body collapse">                        
                        <div class="accordion-inner">
                            <%@ include file="harvest.jsp" %>                            
                        </div>				
                    </div>				
                </div>      
            </div>               
        </div>   
        <script>
            $('#collapseOne').on('shown', function () {
               $(".colOne").removeClass("icon-chevron-down").addClass("icon-chevron-up");
            });

            $('#collapseOne').on('hidden', function () {
                $(".colOne").removeClass("icon-chevron-up").addClass("icon-chevron-down");
            });

            $('#collapseTwo').on('shown', function () {
               $(".colTwo").removeClass("icon-chevron-down").addClass("icon-chevron-up");
            });

            $('#collapseTwo').on('hidden', function () {
                $(".colTwo").removeClass("icon-chevron-up").addClass("icon-chevron-down");
            });

            $('#collapseThree').on('shown', function () {
               $(".colThree").removeClass("icon-chevron-down").addClass("icon-chevron-up");
            });

            $('#collapseThree').on('hidden', function () {
                $(".colThree").removeClass("icon-chevron-up").addClass("icon-chevron-down");
            });

            $('#collapseFour').on('shown', function () {
               $(".colFour").removeClass("icon-chevron-down").addClass("icon-chevron-up");
            });

            $('#collapseFour').on('hidden', function () {
                $(".colFour").removeClass("icon-chevron-up").addClass("icon-chevron-down");
            });

            $('#collapseFive').on('shown', function () {
               $(".colFive").removeClass("icon-chevron-down").addClass("icon-chevron-up");
            });

            $('#collapseFive').on('hidden', function () {
                $(".colFive").removeClass("icon-chevron-up").addClass("icon-chevron-down");
            });

            $("#formCropHar_harv_dateHar").datepicker({changeMonth: true, changeYear: true});
            $("#formCropHar_harv_dateHar").mask("99/99/9999", {placeholder: " "});
            $("#formCropHar_harv_productionHar").numeric({decimal: false, negative: false});
            $("#formCropHar_harv_yieldHar").numeric({negative: false});
            $("#formCropHar_harv_productionPerPlantHar").numeric({negative: false});
            $("#formCropHar_harv_humidityPercentageHar").numeric({negative: false});
        </script>
    </body>
</html>           