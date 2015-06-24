<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <body>
        <s:hidden name="latitude_property"/>
        <s:hidden name="length_property"/>
        <s:hidden name="altitude_property"/>
        <s:hidden name="location"/>
        <s:hidden name="coCode"/>
        <div class="row">
            <div class="span12" style="height:100%; width:100%;">
                <div id="map_canvas" style="width:98%; height:90%; position: absolute;"></div>
            </div>
        </div>
        <div class="row">
            <div class="span12">
                <div style="margin-left: 40%; position: absolute;"> 
                    <button class="btn btn-large bt_cancel_field" onclick="toggleAndClean('divFarmsForm', 'divListFarmsForm');"><i class="icon-arrow-left"></i>  <s:property value="getText('button.backarrow.farm')" /></button>
                    <button class="btn btn-large bt_cancel_field" onclick="selValPos('formFarm_latitude_property', 'latitude_property', 'formFarm_length_property', 'length_property'); generateDegrees('formFarm_latitude_property', 'formFarm_latitude_degrees_property', 'formFarm_latitude_minutes_property', 'formFarm_latitude_seconds_property');
                    generateDegrees('formFarm_length_property', 'formFarm_length_degrees_property', 'formFarm_length_minutes_property', 'formFarm_length_seconds_property'); toggleAndClean('divFarmsForm', 'divListFarmsForm');"><i class="icon-check"></i>  <s:property value="getText('button.pointsel.farm')" /></button>
                </div>
            </div>
            <div class="span12">
                <div style="margin-left:75%; margin-top:100px; position: absolute;"> 
                    <div class="alert mapAdv">
                        <h4><s:property value="getText('title.information.farm')" />:</h4>
                        <p><s:property value="getText('area.information.farm')" />.</p>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script>                
        var valLocation = $("#location").val();
        if (valLocation=="0") {
            alert("Has seleccionado un punto en el agua por favor verificar si es el punto correcto");            
        }
        $("#formFarm_altitude_property").val($("#altitude_property").val());
        var valLat = $("#latitude_property").val();     
        var valLon = $("#length_property").val();          
        var side_bar_html = ""; 
        var elevator;
        var codeCountry = $("#coCode").val();     
        var posCountry = null;
        if (codeCountry=='NI') {
            posCountry = new google.maps.LatLng(12.1146, -84.2353);
        } else if (codeCountry=='CO') {
            posCountry = new google.maps.LatLng(3.721745231068953, -72.894287109375);
        }
        
        
        var mapInfo = document.getElementById('map_canvas');  
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
      function createMarker(latlng,name) {
            var marker = new google.maps.Marker({
                position: latlng,
                draggable:true,
                map: map,
                title: name,
                icon: image,
                zIndex: Math.round(latlng.lat()*-100000)<<5
                });

            google.maps.event.addListener(marker, 'click', function() {
                infowindow.open(map,marker);
            });
            
            google.maps.event.addListener(marker, "mouseover", function() {
                marker.setIcon();
            });
            
            google.maps.event.addListener(marker, "mouseout", function() {
                marker.setIcon();
            });

            google.maps.event.addListener(marker, 'dragend', function(event) { 
                var clickedLocation = event.latLng;
                infowindow.setContent('<b>'+locateFarm+'</b><br>'+clickedLocation);
                $("#latitude_property").val(clickedLocation.lat());
                $("#length_property").val(clickedLocation.lng());
            });    

//            gmarkers.push(marker);
//            var marker_num = gmarkers.length-1;
            return marker;
      }
      
      var jsonString = '{"type": "FeatureCollection", "features": [{"type": "Feature","geometry": {"type": "Point","coordinates": [-38.3613558,-8.8044875]},"properties": { "info": "<div style=\'line-height:1.35;overflow:hidden;white-space:nowrap;\'> Feature id = vale<br/>Feature Value = Zone 1 <button onclick=\'closeWindow();\' class=\'btn btn-large bt_cancel_farm\'><i class=\'icon-ban-circle\'></i>  Cancelar</button></div>", "Ordem": "193", "Eixo": "Leste", "Meta": "1L", "Municipio": "Petrolândia", "Estado": "PE", "Nome da Comunidade": "Agrovila 4"}}, {"type": "Feature","geometry": {"type": "Point","coordinates": [-38.3445892,-8.7940031]},"properties": {"Ordem": "194","Eixo": "Leste","Meta": "1L", "Municipio": "Petrolândia / Floresta", "Estado": "PE", "Nome da Comunidade": "Agrovila 5"}}]}';
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
      
      function initialize() {         
          map.data.setStyle(featureStyle);
          google.maps.event.addListener(map, 'click', function() {
            infowindow.close();
          });
      }    
      //$.getJSON("../../../points.json"); //same as map.data.loadGeoJson();
//      google.maps.event.addDomListener(mapInfo, 'load', initialize);
        var featureStyle = {
          icon: image
        }

        map.data.addGeoJson(geojson);
        map.data.addListener('click', function(event) {
            var info = '<div>'+
              '<div class="w-box">'+
                  '<fieldset>'+
                      '<table class="table table-bordered">'+
                          '<tbody>'+
                              '<tr>'+
                                  '<th style="width: 30%">Productor</th>'+
                                  '<td>Juan Felipe</td>'+
                              '</tr>'+
                              '<tr>'+
                                  '<th>Numero de documento</th>'+
                                  '<td>'+event.feature.getProperty("Ordem")+'</td>'+
                              '</tr>'+
                              '<tr>'+
                                  '<th>Nombre de la finca</th>'+
                                  '<td>El Lido</td>'+
                              '</tr>'+   
                              '<tr>'+   
                                  '<th>Departamento</th>'+
                                  '<td>Valle del Cauca</td>'+
                              '</tr>'+
                              '<tr>'+
                                  '<th>Muncipio</th>'+
                                  '<td>Cali</td>'+
                              '</tr>'+
                          '</tbody>'+
                      '</table>'+
                  '</fieldset>'+
              '</div>'+       
          '</div>'+
          '<button type="button" class="btn btn-initial" onclick="viewForm(\'/showFarm.action?action=modify&page=1\', \'idFar\', 520, \'Editar Finca\', 1050, 550)">'+
              '<i class="icon-pencil"></i> Editar Finca'+
          '</button>';
            infowindow.setContent(info);
            var anchor   = new google.maps.MVCObject();
            var position = new google.maps.LatLng(event.latLng.lat()+0.09, event.latLng.lng());
            anchor.set("position",position);
            anchor.set("zIndex",Math.round(event.latLng.lat()*-100000)<<5);
            infowindow.open(map,anchor);
        });
        map.data.setStyle(featureStyle);
      var latLng = new google.maps.LatLng(parseFloat(valLat), parseFloat(valLon)); 
      var marker = createMarker(latLng,"");
      marker.setMap(map);
      infowindow.setContent('<b>'+locateFarm+'</b><br>'+latLng);
      google.maps.event.addDomListener(mapInfo, 'load', initialize);
    </script> 
</html>
