<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <body>
        <s:hidden name="latitude_property"/>
        <s:hidden name="length_property"/>
        <div class="row">
            <div class="span12">
                <div id="map_canvas" style="width:98%; height:90%; position: absolute;"></div>
            </div>
        </div>
        <div class="row">
            <div class="span12">
                <div style="margin-left: 40%; position: absolute;"> 
                    <button class="btn btn-large bt_cancel_field" onclick="toggleAndClean('divFarmsForm', 'divListFarmsForm');"><i class="icon-arrow-left"></i>  Atras</button>
                    <button class="btn btn-large bt_cancel_field" onclick="selValPos('formFarm_latitude_property', 'latitude_property', 'formFarm_length_property', 'length_property'); generateDegrees('formFarm_latitude_property', 'formFarm_latitude_degrees_property', 'formFarm_latitude_minutes_property', 'formFarm_latitude_seconds_property');
                    generateDegrees('formFarm_length_property', 'formFarm_length_degrees_property', 'formFarm_length_minutes_property', 'formFarm_length_seconds_property'); toggleAndClean('divFarmsForm', 'divListFarmsForm');"><i class="icon-check"></i>  Seleccionar punto</button>
                </div>
            </div>
            <div class="span12">
                <div style="margin-left:75%; margin-top:100px; position: absolute;"> 
                    <div class="alert mapAdv">
                        <h4>Información de interes:</h4>
                        <p>En este mapa usted podrá visualizar las coordenadas que acaba de registrar. Si no están exactamente en el sitio indicado, 
                        usted puede dar click al punto y arrastrarlo para posicionarlo con mas precisión.</p>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script>                
        var valLat = $("#latitude_property").val();     
        var valLon = $("#length_property").val();     
        var side_bar_html = ""; 
        var elevator;

        var gmarkers = []; 
        var gicons = [];
        var myOptions = {
            zoom: 5,
            center: new google.maps.LatLng(3.721745231068953, -72.894287109375),
            mapTypeControl: true,
            mapTypeControlOptions: {style: google.maps.MapTypeControlStyle.DROPDOWN_MENU},
            navigationControl: true,
            mapTypeId: google.maps.MapTypeId.HYBRID 
          }
        var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
            gicons["red"] = new google.maps.MarkerImage("img/mapIcons/marker_red.png",
            new google.maps.Size(20, 34),
            new google.maps.Point(0,0),
            new google.maps.Point(9, 34));

        var iconImage = new google.maps.MarkerImage('img/mapIcons/marker_red.png',
            new google.maps.Size(20, 34),
            new google.maps.Point(0,0),
            new google.maps.Point(9, 34));
            
        var iconShadow = new google.maps.MarkerImage('img/mapIcons/marker_shadow.png',
            new google.maps.Size(37, 34),
            new google.maps.Point(0,0),
            new google.maps.Point(9, 34));

      function getMarkerImage(iconColor) {
         if ((typeof(iconColor)=="undefined") || (iconColor==null)) { 
            iconColor = "red"; 
         }
         if (!gicons[iconColor]) {
            gicons[iconColor] = new google.maps.MarkerImage("img/mapIcons/marker_"+ iconColor +".png",
            new google.maps.Size(20, 34),
            new google.maps.Point(0,0),
            new google.maps.Point(9, 34));
         } 
         return gicons[iconColor];
      }

      gicons["blue"]  = getMarkerImage("blue");
      gicons["green"] = getMarkerImage("green");
      gicons["yelow"] = getMarkerImage("yellow");
      
      function createMarker(latlng,name,html,color) {
            var contentString = html;
            var marker = new google.maps.Marker({
                position: latlng,
                icon: gicons[color],
                shadow: iconShadow,
                draggable:true,
                map: map,
                title: name,
                zIndex: Math.round(latlng.lat()*-100000)<<5
                });

            google.maps.event.addListener(marker, 'click', function() {
                infowindow.open(map,marker);
            });
            
            google.maps.event.addListener(marker, "mouseover", function() {
                marker.setIcon(gicons["yellow"]);
            });
            
            google.maps.event.addListener(marker, "mouseout", function() {
                marker.setIcon(gicons["blue"]);
            });

            google.maps.event.addListener(marker, 'dragend', function(event) { 
                var clickedLocation = event.latLng;
                infowindow.setContent('<b>Ubicacion</b><br>'+clickedLocation);
                $("#latitude_property").val(clickedLocation.lat());
                $("#length_property").val(clickedLocation.lng());
            });    

            gmarkers.push(marker);
            var marker_num = gmarkers.length-1;
            return marker;
      }

      function myclick(i) {
        google.maps.event.trigger(gmarkers[i], "click");
      }

      function initialize() {
          google.maps.event.addListener(map, 'click', function() {
            infowindow.close();
          });
      }

      var infowindow = new google.maps.InfoWindow({
          maxWidth: 300
      });     

      google.maps.event.addDomListener(window, 'load', initialize); 
      var latLng = new google.maps.LatLng(parseFloat(valLat), parseFloat(valLon)); 
      var marker = createMarker(latLng,"",'<b>Location</b><br>'+latLng,"blue");
      marker.setMap(map);
      infowindow.setContent('<b>Ubicacion</b><br>'+latLng);
    </script> 
</html>
