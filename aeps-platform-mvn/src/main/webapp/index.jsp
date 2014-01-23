<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <link rel="icon" type="image/ico" href="favicon.ico">
        <title>AEPS</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width"> 							
        <link rel="stylesheet" href="scripts/css/bootstrap/bootstrap-responsive.min.css">
        <link rel="stylesheet" href="scripts/css/bootstrap/bootstrap.min.css">
        <link rel="stylesheet" href="scripts/css/generals/justified-nav.css">
        <!-- <link rel="stylesheet" href="scripts/css/bootstrap-theme.min.css"> -->
        <link rel="stylesheet" href="scripts/css/generals/main.css">
        <link rel="stylesheet" type="text/css" href="scripts/css/generals/style.css">
        <link rel="stylesheet" href="scripts/css/generals/responsive.css">
        <link rel="stylesheet" href="scripts/css/generals/responsiveslides.css" />		
        <link rel="stylesheet" href="scripts/css/generals/beoro.css">
        <style>
            body {
                padding-top: 50px;
                padding-bottom: 20px;
            }
        </style>				
        <!--<script src="scripts/js/vendor/modernizr-2.6.2-respond-1.1.0.min.js"></script>-->
        <!-- <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script> -->
        <script src="scripts/js/jquery/jquery-1.9.1.js"></script>
        <!-- <script>window.jQuery || document.write('<script src="scripts/js/vendor/jquery-1.10.1.min.js"><\/script>')</script> -->
        <script src="scripts/js/bootstrap/bootstrap.min.js"></script>
        <script src="scripts/js/generals/main.js"></script>				
        <script src="scripts/js/generals/responsiveslides.js"></script>
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
    </head>
    <body>
        <div class="container">
            <div class="masthead">
                <div class="row">
                    <div class="span6">	
                        <h3 class="text-muted">AEPS</h3>
                    </div>
                </div>					
            </div>
            <div class="masthead">
                <div class="navbar">
                    <div class="navbar-inner">
                        <div class="container">
                            <ul class="nav">
                                <li class="active"><a href="#">Inicio</a></li>
                                <li><a href="about.action">Quienes Somos</a></li>
                                <li><a href="contact.action">Contactenos</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="container">
            <!-- Jumbotron -->
            <div class="rslides_container">
                <ul class="rslides" id="slider">							
                    <li>
                        <div class="jumbotron">
                            <div class="row">
                                <div class="span6">
                                    <h1>Imagen Plataforma</h1>
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
            <!-- Example row of columns -->
            <!-- <div class="row">
                <div class="span4">
                    <img src="img/logo-google-play-vetor.png"/>
                    <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
                    <p><a class="btn btn-primary" href="#" role="button">View details »</a></p>
                </div>
                <div class="span4">
                    <h2>Heading</h2>
                    <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
                    <p><a class="btn btn-primary" href="#" role="button">View details »</a></p>
             </div>
                <div class="span4">
                    <h2>Heading</h2>
                    <p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Vestibulum id ligula porta felis euismod semper. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa.</p>
                    <p><a class="btn btn-primary" href="#" role="button">View details »</a></p>
                </div>
            </div> -->
            <!-- Site footer -->
            <div class="footer">
                <p>© Compañia 2013</p>
            </div>
        </div> <!-- /container -->
        <!-- Bootstrap core JavaScript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
    </body>
</html>
