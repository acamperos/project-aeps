<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <link rel="icon" type="image/ico" href="favicon.ico">
        <title>AEPS</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width"> 							
        <link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/css/bootstrap/bootstrap-responsive.min.css">
        <link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/css/bootstrap/bootstrap.min.css">
        <link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/css/generals/justified-nav.css">
        <link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/css/generals/main.css">
        <link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/css/font-awesome/css/font-awesome.min.css">
        <link href = 'http://fonts.googleapis.com/css?family=Istok+Web:400700400cursiva,700italicysubconjunto=latin,latin-ext' rel='stylesheet' type='text/css'>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="span12">
                    <div class="hero-unit center">
                        <h1>Pagina no encontrada <small><font face="Tahoma" color="red">Error 404</font></small></h1>
                        <br />
                        <p>La pagina o el recurso solicitado no se encuentra. Por favor comuniquese con el administrador respectivo o intente mas tarde. </p>
<!--                            Use your browsers <b>Back</b> button to navigate to the page you have prevously come from</p>
                        <p><b>Or you could just press this neat little button:</b></p>-->
                        <a href="<%= request.getContextPath() %>/initial.action" class="btn btn-large btn-initial"><i class="icon-home icon-white"></i> Llevar al inicio</a>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
