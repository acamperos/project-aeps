<%@ taglib prefix="s" uri="/struts-tags" %>
<% int pageSel = (request.getParameter("page") != null) ? Integer.parseInt(String.valueOf(request.getParameter("page"))) : 1;%>
<div class="container">
    <div class="row-fluid">
        <div class="span12">
            <s:hidden name="coCode"/>
            <s:hidden name="points"/>
            <div class="tabbable tabbable-bordered">
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#tb3_a" data-toggle="tab">Mapa</a></li>
                    <li><a href="#tb3_b" data-toggle="tab">Listado</a></li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane active" id="tb3_a" style="height: 570px">
                        <div id="map_general" style="width:68%; height:90%; position: absolute;"></div>
                        <script>                
                            var pageSel = "<%=pageSel%>";
                            var jsonString  = $("#points").val();
                            var codeCountry = $("#coCode").val();    
                            var posCountry = null;
                            if (codeCountry=='NI') {
                                posCountry = new google.maps.LatLng(12.1146, -84.2353);
                            } else if (codeCountry=='CO') {
                                posCountry = new google.maps.LatLng(3.721745231068953, -72.894287109375);
                            }


                            var mapInfo = document.getElementById('map_general');  
                            var map;
                            var infowindow = new google.maps.InfoWindow({
                                maxWidth: 300
                            });   

                            var locateFarm = "";
                            if(navigator.language=='es-ES' || navigator.language=='es-CO' || navigator.language=='es-PE' || navigator.language=='es-NI' || navigator.language=='es') {
                                    locateFarm = "Ubicacion";
                            }

                            if(navigator.language=='en-EN' || navigator.language=='en') {
                                    locateFarm = "Location";
                            }

                          var image = '../../img/market.png';

//                          var jsonString = '{"type": "FeatureCollection", "features": [{"type": "Feature","geometry": {"type": "Point","coordinates": [-38.3613558,-8.8044875]},"properties": { "info": "<div style=\'line-height:1.35;overflow:hidden;white-space:nowrap;\'> Feature id = vale<br/>Feature Value = Zone 1 <button onclick=\'closeWindow();\' class=\'btn btn-large bt_cancel_farm\'><i class=\'icon-ban-circle\'></i>  Cancelar</button></div>", "Ordem": "193", "Eixo": "Leste", "Meta": "1L", "Municipio": "Petrolândia", "Estado": "PE", "Nome da Comunidade": "Agrovila 4"}}, {"type": "Feature","geometry": {"type": "Point","coordinates": [-38.3445892,-8.7940031]},"properties": {"Ordem": "194","Eixo": "Leste","Meta": "1L", "Municipio": "Petrolândia / Floresta", "Estado": "PE", "Nome da Comunidade": "Agrovila 5"}}]}';
                          var geojson = $.parseJSON(jsonString);

                          var myOptions = {
                            zoom: 5,
                            center: posCountry,
                            mapTypeControl: true,
                            mapTypeControlOptions: {style: google.maps.MapTypeControlStyle.DROPDOWN_MENU},
                            navigationControl: true,
                            mapTypeId: google.maps.MapTypeId.HYBRID 
                          }

                            map = new google.maps.Map(mapInfo, myOptions);
                            var featureStyle = {
                              icon: image
                            }
//                            map.data.setStyle(featureStyle);

                            map.data.addGeoJson(geojson);
                            function showContent(feature) {
                                var info = '<div>'+
                                  '<div class="w-box">'+
                                      '<fieldset>'+
                                          '<table class="table table-bordered">'+
                                              '<tbody>'+
                                                  '<tr>'+
                                                      '<th style="width: 30%">Nombre</th>'+
                                                      '<td>'+feature.getProperty("nameFarm")+'</td>'+
                                                  '</tr>'+
                                                  '<tr>'+
                                                      '<th>Indicación (Como llegar)</th>'+
                                                      '<td>'+feature.getProperty("dirFarm")+'</td>'+
                                                  '</tr>'+   
                                                  '<tr>'+   
                                                      '<th>Departamento</th>'+
                                                      '<td>'+feature.getProperty("nameDep")+'</td>'+
                                                  '</tr>'+
                                                  '<tr>'+
                                                      '<th>Muncipio</th>'+
                                                      '<td>'+feature.getProperty("nameMun")+'</td>'+
                                                  '</tr>'+
                                                  '<tr>'+
                                                      '<th>Latitud</th>'+
                                                      '<td>'+feature.getProperty("latFarm")+'</td>'+
                                                  '</tr>'+
                                                  '<tr>'+
                                                      '<th>Longitud</th>'+
                                                      '<td>'+feature.getProperty("lonFarm")+'</td>'+
                                                  '</tr>'+
                                                  '<tr>'+
                                                      '<th>Altura</th>'+
                                                      '<td>'+feature.getProperty("altFarm")+'</td>'+
                                                  '</tr>'+
                                              '</tbody>'+
                                          '</table>'+
                                      '</fieldset>'+
                                  '</div>'+       
                              '</div>'+
                              '<button type="button" class="btn btn-initial" onclick="viewForm(\'/showFarm.action?action=modify&page='+pageSel+'\', \'idFar\', '+feature.getProperty("idFarm")+', \'Editar Finca\', 1050, 550)">'+
                                  '<i class="icon-pencil"></i> Editar Finca'+
                              '</button>';
                                infowindow.setContent(info);
                            };
                            
                            function initialize() {   
                                google.maps.event.addListener(map, 'click', function() {
                                  infowindow.close();
                                });
                            }  
                            var markers=[];
                            map.data.forEach(function(feature){
                                  var latLngPos = feature.getGeometry().get();
                                  var marker = new google.maps.Marker({
                                       position: latLngPos,
                                       icon: image
                                  });
                                  markers.push(marker);
                                  map.data.remove(feature);
                                  google.maps.event.addListener(marker, 'click', function() {
                                      infowindow.open(map,marker);
                                      showContent(feature);
                                  });

                            });
//
                            var markerCluster = new MarkerClusterer(map, markers);
                            google.maps.event.addDomListener(window, 'load', initialize);
                        </script> 
                    </div>
                    <div class="tab-pane" id="tb3_b">
                        <div class="container">
                            <div class="panel">
                                <div class="panel-body">
                                    <%@ include file="search-farm.jsp" %>
                                </div>
                            </div>    
                        </div>     
                        <div class="container" id="divConListFarms">
                            <%@ include file="info-farm.jsp" %>            
                        </div> 
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>