<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
        <link rel="icon" type="image/ico" href="favicon.ico">
        <title>AEPS</title>
        <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
        <sj:head jqueryui="false"/>
        <script type="text/javascript" src="scripts/js/geoxml/geoxml3.js"></script>
        <link rel="stylesheet" href="scripts/css/geoxml/gmap.css"/>
        <style>
            html { height: 100% }
            body { height: 100%; margin: 0; padding: 0; }
            div#map_container{
                width:100%;
                height:350px;
            }
        </style>		        
    </head>
    <body>
        <div id="map_canvas" style="width:100%; height:100%"></div>
    </body>
    <script type="text/javascript"> 
        //<![CDATA[
              // this variable will collect the html which will eventually be placed in the side_bar 
              var side_bar_html = ""; 
              var elevator;

              // arrays to hold copies of the markers and html used by the side_bar 
              // because the function closure trick doesnt work there 
              var gmarkers = []; 
              var gicons = [];
             // global "map" variable
             var myOptions = {
                zoom: 8,
                center: new google.maps.LatLng(3.721745231068953, -72.894287109375),
                mapTypeControl: true,
                mapTypeControlOptions: {style: google.maps.MapTypeControlStyle.DROPDOWN_MENU},
                navigationControl: true,
                mapTypeId: google.maps.MapTypeId.ROADMAP
              }
            var map = new google.maps.Map(document.getElementById("map_canvas"),
                                        myOptions);
//              var map = null;
              gicons["red"] = new google.maps.MarkerImage("img/mapIcons/marker_red.png",
              // This marker is 20 pixels wide by 34 pixels tall.
              new google.maps.Size(20, 34),
              // The origin for this image is 0,0.
              new google.maps.Point(0,0),
              // The anchor for this image is at 9,34.
              new google.maps.Point(9, 34));
          // Marker sizes are expressed as a Size of X,Y
          // where the origin of the image (0,0) is located
          // in the top left of the image.

          // Origins, anchor positions and coordinates of the marker
          // increase in the X direction to the right and in
          // the Y direction down.

          var iconImage = new google.maps.MarkerImage('img/mapIcons/marker_red.png',
              // This marker is 20 pixels wide by 34 pixels tall.
              new google.maps.Size(20, 34),
              // The origin for this image is 0,0.
              new google.maps.Point(0,0),
              // The anchor for this image is at 9,34.
              new google.maps.Point(9, 34));
          var iconShadow = new google.maps.MarkerImage('img/mapIcons/marker_shadow.png',
              // The shadow image is larger in the horizontal dimension
              // while the position and offset are the same as for the main image.
              new google.maps.Size(37, 34),
              new google.maps.Point(0,0),
              new google.maps.Point(9, 34));

        function getMarkerImage(iconColor) {
           if ((typeof(iconColor)=="undefined") || (iconColor==null)) { 
              iconColor = "red"; 
           }
           if (!gicons[iconColor]) {
              gicons[iconColor] = new google.maps.MarkerImage("img/mapIcons/marker_"+ iconColor +".png",
              // This marker is 20 pixels wide by 34 pixels tall.
              new google.maps.Size(20, 34),
              // The origin for this image is 0,0.
              new google.maps.Point(0,0),
              // The anchor for this image is at 6,20.
              new google.maps.Point(9, 34));
           } 
           return gicons[iconColor];

        }

        gicons["blue"]  = getMarkerImage("blue");
        gicons["green"] = getMarkerImage("green");
        gicons["yelow"] = getMarkerImage("yellow");
        // A function to create the marker and set up the event window function 
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
//                infowindow.setContent(contentString); 
                infowindow.open(map,marker);
                });
                // Switch icon on marker mouseover and mouseout
                google.maps.event.addListener(marker, "mouseover", function() {
                  marker.setIcon(gicons["yellow"]);
                });
                google.maps.event.addListener(marker, "mouseout", function() {
                  marker.setIcon(gicons["blue"]);
                });
                
            google.maps.event.addListener(marker, 'dragend', function(event) { 
////                markerMoved(event.latLng); 
////                infowindow.setContent(contentString);
//                infowindow.setContent('<b>Location</b><br>'+event.latLng+' The elevation at this point <br>is ' + getElevation(event) + ' meters.'); 
//                infowindow.setContent('<b>Location</b><br>'+event.latLng); 
                getElevation(event);
            });    
    
            gmarkers.push(marker);
            // add a line to the side_bar html
            var marker_num = gmarkers.length-1;
            return marker;
            //side_bar_html += '<a href="javascript:myclick(' + marker_num + ')" onmouseover="gmarkers['+marker_num+'].setIcon(gicons.yellow)" onmouseout="gmarkers['+marker_num+'].setIcon(gicons.blue)">' + name + '<\/a><br>';
        }

        // This function picks up the click and opens the corresponding info window
        function myclick(i) {
          google.maps.event.trigger(gmarkers[i], "click");
        }
        var elevation = null;
        function getElevation(event) {

            var locations = [];          

            // Retrieve the clicked location and push it on the array
            var clickedLocation = event.latLng;
            locations.push(clickedLocation);

            // Create a LocationElevationRequest object using the array's one value
            var positionalRequest = {
              'locations': locations
            }

            // Initiate the location request
            elevator.getElevationForLocations(positionalRequest, function(results, status) {
              if (status == google.maps.ElevationStatus.OK) {
                // Retrieve the first result
                if (results[0]) {

                  // Open an info window indicating the elevation at the clicked position
                  infowindow.setContent('<b>Location</b><br>'+clickedLocation+'The elevation at this point <br>is ' + results[0].elevation + ' meters.');
//                  infowindow.setPosition(clickedLocation);
//                  infowindow.open(map);
//                  alert(results[0].elevation);
//                  var marker = createMarker(event.latLng,"name",'<b>Location</b><br>'+clickedLocation+'The elevation at this point <br>is ' + results[0].elevation + ' meters.',"green");
//                  elevation = results[0].elevation;
//                  return elevation;
                } else {
                  alert('No results found');
                }
              } else {
                alert('Elevation service failed due to: ' + status);
              }
            });
//            return elevation;
        }

        function initialize() {
            // create the map
            google.maps.event.addListener(map, 'click', function() {
              infowindow.close();
            });
          
            // Create an ElevationService
            elevator = new google.maps.ElevationService();

            // Add a listener for the click event and call getElevation on that location
            google.maps.event.addListener(map, 'click', getElevation);
          
          google.maps.event.addListener(map, 'click', function(event) {
             var marker = createMarker(event.latLng,"name",'<b>Location</b><br>'+event.latLng+'The elevation at this point <br>is ' + getElevation(event) + ' meters.',"green");
          });
        }

        var infowindow = new google.maps.InfoWindow({ 
//            size: new google.maps.Size(150,50),
            maxWidth: 200
        });
          
//        var geojson = '{            "type": "FeatureCollection",            "features": [              { "type": "Feature",                "geometry": {"type": "Point", "coordinates": [102.0, 0.5]},                "properties": {"prop0": "value0"}              },              { "type": "Feature",                "geometry": {                  "type": "LineString",                  "coordinates": [                    [102.0, 0.0], [103.0, 1.0], [104.0, 0.0], [105.0, 1.0]                  ]                },                "properties": {                  "prop0": "value0",                 "prop1": 0.0                }              },              { "type": "Feature",                "geometry": {                  "type": "Polygon",                  "coordinates": [                    [ [100.0, 0.0], [101.0, 0.0], [101.0, 1.0],                    [100.0, 1.0], [100.0, 0.0] ]                  ]                },                "properties": {                  "prop0": "value0",                  "prop1": {"this": "that"}                }              }            ]          }';
//        var googleOptions = {
//            strokeColor: '#CCC',
//            strokeWeight: 1
//        };
//        var googleMapsVector = new GeoJSON(geojson, googleOptions);
//        googleMapsVector.setMap(map);   
        google.maps.event.addDomListener(window, 'load', initialize);
        var jsonArch = '[{"codigo":"3127","nombre":"name","zona":"3127","geoX":"3.721745231068953","geoY":"-72.894287109375"}]';

        var archiJASON = jQuery.parseJSON(jsonArch);
        jQuery.each(archiJASON, function(key, data) {
//            alert(parseFloat(data.geoX));
//            alert(parseFloat(data.geoY));
            var latLng = new google.maps.LatLng(parseFloat(data.geoX), parseFloat(data.geoY)); 
            // Creating a marker and putting it on the map
            var marker = createMarker(latLng,"name",'<b>Location</b><br>'+latLng,"green");
            marker.setMap(map);
//            var marker = new google.maps.Marker({
//                position: latLng,
//                map: map,
//                title: data.nombre
//            });
//            marker.setMap(map);
        });
//        var gml = new GeoXml("gml", map, "Lotes_maiz.kml");
//        gml.parse();
        var myParser = new geoXML3.parser({map: map, suppressInfoWindows: true});
        myParser.parse('Lotes_maiz.kml');
        //]]>
    </script> 

</html>
