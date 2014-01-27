<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<!--<html>
    <head>-->
        <script>
            /*var _gaq=[['_setAccount','UA-XXXXX-X'],['_trackPageview']];
             (function(d,t){var g=d.createElement(t),s=d.getElementsByTagName(t)[0];
             g.src='//www.google-analytics.com/ga.js';
             s.parentNode.insertBefore(g,s)}(document,'script'));*/
            $(function() {
                $("#slider").responsiveSlides({
                    auto: true,
                    pager: false,
                    nav: true,
                    speed: 500,
                    maxwidth: 962,
                    namespace: "centered-btns"
                });
            });
        </script>
<!--    </head>
    <body>-->
        <div class="container">
            <div class="rslides_container">
                <ul class="rslides" id="slider">							
                    <li>
                        <div class="jumbotron">
                            <div class="row">
                                <div class="span6">
                                    <h1>Imagen Plataformaasdfsadfsdafasdf</h1>
                                </div>
                                <div class="span6">
                                    <p class="text-left lead">Cras justo odio, dapibus ac facilisis in, egestas eget quam. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet.</p>
                                    <p>
                                        <a href="login.action" class="btn btn-primary btn-success btn-lg">Ingresar</a>
                                        <a href="login.action?user=new" class="btn btn-default btn-lg">Registrarse</a>
                                    </p>
                                </div>
                            </div>								
                        </div>
                    </li>
                    <li>
                        <div class="jumbotron">
                            <div class="row">
                                <div class="span6">
                                    <h1>Imagen Blog</h1>
                                </div>
                                <div class="span6">
                                    <p class="text-left lead">Cras justo odio, dapibus ac facilisis in, egestas eget quam. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet.</p>
                                    <p>
                                        <button type="button" class="btn btn-primary btn-success btn-lg">Ir al Blog</button>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="jumbotron">
                            <div class="row">
                                <div class="span6">
                                    <h1>Imagen Movil</h1>
                                </div>
                                <div class="span6">
                                    <p class="text-left lead">Cras justo odio, dapibus ac facilisis in, egestas eget quam. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet.</p>
                                    <!--<p>-->
                                    <div id="img_container">
                                        <!-- <img src="img/logo-google-play-vetor.png"/> -->
                                        <button type="button" class="btn btn-primary btn-lg">Ir a Google Play</button>
                                    </div>
                                    <!--</p>-->
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="row">
                <div class="span12">
                    <h3>Que es AEPS 33¿? (Agricultura Espec&iacute;fica Por Sitio)</h3><!-- Replace all text with what you want -->
                    <p>Hey there, my name is &quot;Your Name&quot; and I am a photographer and web developer! This is my brand new portfolio. It's super cool because it's completely responsive! That means you can re-size it to whatever size you like and it always looks great. Have a look around and enjoy.</p>
                </div>
            </div>
            <div class="row">
                <div class="span12">
                    <div class="tabbable tabs-left tabbable-bordered">
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#tb3_a" data-toggle="tab">Reportes Generales</a></li>
                            <li><a href="#tb3_b" data-toggle="tab">Como usar el sistema</a></li>
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane active" id="tb3_a">
                                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi elit dui, porta ac scelerisque placerat, rhoncus vitae sem. Nulla eget libero enim, facilisis accumsan eros.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
<!--    </body>
</html>-->
