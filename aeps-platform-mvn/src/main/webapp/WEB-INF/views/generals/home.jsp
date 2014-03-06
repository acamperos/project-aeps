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
                                    <h1><s:property value="getText('image.platform')" /></h1>
                                </div>
                                <div class="span6">
                                    <p class="text-left lead"><s:property value="getText('text.description.platform')" /></p>
                                    <p>
                                        <a href="login.action" class="btn btn-primary btn-success btn-lg"><s:property value="getText('button.ingress.platform')" /></a>
                                        <a href="login.action?user=new" class="btn btn-default btn-lg"><s:property value="getText('button.register.platform')" /></a>
                                    </p>
                                </div>
                            </div>								
                        </div>
                    </li>
                    <li>
                        <div class="jumbotron">
                            <div class="row">
                                <div class="span6">
                                    <h1><s:property value="getText('image.blog')" /></h1>
                                </div>
                                <div class="span6">
                                    <p class="text-left lead"><s:property value="getText('text.description.blog')" /></p>
                                    <p>
                                        <button type="button" class="btn btn-primary btn-success btn-lg"><s:property value="getText('button.go.blog')" /></button>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="jumbotron">
                            <div class="row">
                                <div class="span6">
                                    <h1><s:property value="getText('image.mobile')" /></h1>
                                </div>
                                <div class="span6">
                                    <p class="text-left lead"><s:property value="getText('text.description.mobile')" /></p>
                                    <!--<p>-->
                                    <div id="img_container">
                                        <!-- <img src="img/logo-google-play-vetor.png"/> -->
                                        <button type="button" class="btn btn-primary btn-lg"><s:property value="getText('button.go.mobile')" /></button>
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
                    <h3><s:property value="getText('text.title.group')" /></h3><!-- Replace all text with what you want -->
                    <p><s:property value="getText('text.description.group')" /></p>
                </div>
            </div>
            <div class="row">
                <div class="span12">
                    <div class="tabbable tabs-left tabbable-bordered">
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#tb3_a" data-toggle="tab"><s:property value="getText('text.link.report')" /></a></li>
                            <li><a href="#tb3_b" data-toggle="tab"><s:property value="getText('text.link.info')" /></a></li>
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane active" id="tb3_a">
                                <p><s:property value="getText('text.description.report')" /></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
<!--    </body>
</html>-->
