<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<html>
    <head>
    </head>
    <body>
        <%@ include file="googleAnalytics.jsp" %>
        <div class="container">
            <div id="carousel-487454" class="carousel slide">
                <div class="carousel-inner">
                    <div class="item active">
                        <a href="signin.action"><img src="img/platform.jpg"></a>
                        <div class="container">
                            <div class="carousel-caption">
                                <p class="lead"><s:property value="getText('text.description.platform')" /></p>
                                <a href="signin.action" class="btn btn-initial btn-large"><s:property value="getText('button.ingress.platform')" /></a>
                                <a href="signin.action?logSel=new" class="btn btn-default btn-large"><s:property value="getText('button.register.platform')" /></a>
                            </div>
                        </div>
                    </div>
<!--                    <div class="item">
                        <img src="img/carouselthumb.jpg" alt="thumb">
                        <div class="container">
                            <div class="carousel-caption">
                                <p class="lead"><s:property value="getText('text.description.blog')" /></p>
                                <a href="login.action" class="btn btn-initial btn-large"><s:property value="getText('button.ingress.platform')" /></a>
                            </div>
                        </div>
                    </div>-->
                    <div class="item">
                        <a href="https://play.google.com/store/apps/details?id=com.aepsmovil.aepsmovil"><img src="img/apps-mobile.jpg"></a>
                        <div class="container">
                            <div class="carousel-caption">
                                <p class="lead" style="margin-bottom:20px !important;"><s:property value="getText('text.description.mobile')" /></p>
                                <a href="https://play.google.com/store/apps/details?id=com.aepsmovil.aepsmovil" class="btn btn-primary btn-large"><s:property value="getText('button.go.mobile')" /></a>
                            </div>
                        </div>
                    </div>
                    <div class="item">
                        <a href="tryFree.action"><img src="img/usuario_prueba.png"></a>
                        <div class="container">
                            <div class="carousel-caption">
                                <p class="lead" style="margin-bottom:20px !important;">
                                    �No sabe si inscribirse?
                                    Puede probar la plataforma sin necesidad de crear un usuario.
                                    En donde se le permitir� entrar para visitar la plataforma, probar las diferentes funcionalidades sin ning�n compromiso. 
                                    Y luego, si le gusta, reg�strese para tener su propio usuario y proteger su informaci�n.
                                    Y si tiene preguntas, no dude en contactarnos.
                                </p>
                                <a href="tryFree.action" class="btn btn-initial btn-large">Probar sistema</a>
                            </div>
                        </div>
                    </div>
                </div>
                <ol class="carousel-indicators">
                    <li data-target="#carousel-487454" data-slide-to="0" class="active">
                    </li>
                    <li data-target="#carousel-487454" data-slide-to="1" class="">
                    </li>
                    <li data-target="#carousel-487454" data-slide-to="2" class="">
                    </li>
                </ol>
                <a class="left carousel-control" href="#carousel-487454" data-slide="prev">&lsaquo;</a> 
                <a class="right carousel-control" href="#carousel-487454" data-slide="next">&rsaquo;</a>
            </div>
            <script>
                !function ($) {
                  $(function(){
                    $('#carousel-487454').carousel();
                  })
                }(window.jQuery)
            </script>
            <div class="panel">
                <div class="panel-body">
                    <h3><s:property value="getText('text.title.group')" /></h3><!-- Replace all text with what you want -->
                    <p><s:property value="getText('text.description.group')" /></p>
                    <div class="tabbable tabs-left tabbable-bordered">
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#tb3_a" data-toggle="tab"><s:property value="getText('text.link.report')" /></a></li>
                            <li><a href="#tb3_b" data-toggle="tab"><s:property value="getText('text.link.info')" /></a></li>
                            <li><a href="#tb3_c" data-toggle="tab"><s:property value="getText('text.link.guide')" /></a></li>
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane active" id="tb3_a">
                                <!--<p><s:property value="getText('text.description.report')" /></p>-->
                                <p></p>
                            </div>
                            <div class="tab-pane" id="tb3_b">
                                <pre><s:property value="getText('text.description.info')" /></pre>
                            </div>
                            <div class="tab-pane" id="tb3_c">
                                <div class="control-group">
                                    <s:text name="%{getText('text.title.guide')}"/>
                                </div>
                                <div class="control-group">
                                    <div class="controls">
                                        <div class="row-fluid">
                                            <div class="span3">
                                                <a href="http://www.open-aeps.org/RASTA.pdf"><div class="img img-rasta"></div></a>
                                                <s:text name="%{getText('text.link.guidedownload')}"/>  
                                            </div>
                                            <div class="span8">
                                                <s:text name="%{getText('text.description.guide')}"/>                                
                                            </div>
                                        </div>
                                    </div>
                                </div>                                
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
