<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<!DOCTYPE html>
<html>
    <head>
    </head>
    <body>
        <%@ include file="googleAnalytics.jsp" %>
        <div class="container">
            <div class="panel">
                <div class="panel-body">
                    <div class="row-fluid">
                        <div class="span12">
                            <div class="tabbable tabs-left tabbable-bordered">
                                <ul class="nav nav-tabs">
                                    <li class="active"><a href="#tb3_a" data-toggle="tab"><s:property value="getText('link.mision.aboutus')" /></a></li>
                                    <li><a href="#tb3_b" data-toggle="tab"><s:property value="getText('link.vision.aboutus')" /></a></li>
                                    <li><a href="#tb3_c" data-toggle="tab"><s:property value="getText('link.projects.aboutus')" /></a></li>
                                    <li><a href="#tb3_d" data-toggle="tab"><s:property value="getText('link.ourteam.aboutus')" /></a></li>
                                </ul>
                                <div class="tab-content">
                                    <div class="tab-pane active" id="tb3_a">
                                        <p><strong><s:property value="getText('title.mision.aboutus')" /></strong></p>
                                        <hr>
                                        <p><s:property value="getText('area.mision.aboutus')" /></p>
                                    </div>
                                    <div class="tab-pane" id="tb3_b">
                                        <p><strong><s:property value="getText('title.vision.aboutus')" /></strong></p>
                                        <hr>
                                        <p><s:property value="getText('area.vision.aboutus')" /></p>
                                    </div>
                                    <div class="tab-pane" id="tb3_c">
                                        <p><strong><s:property value="getText('title.projects.aboutus')" /></strong></p>
                                        <hr>
                                        <p><s:text name="%{getText('area.projects.aboutus')}" /></p>                                        
                                    </div>
                                    <div class="tab-pane" id="tb3_d">
                                        <p><strong><s:property value="getText('title.ourteam.aboutus')" /></strong></p>
                                        <hr>
                                        <p><s:text name="%{getText('area.ourteam.aboutus')}" /></p>                                        
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


