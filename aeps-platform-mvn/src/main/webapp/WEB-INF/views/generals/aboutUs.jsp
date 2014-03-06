<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<!DOCTYPE html>
<html>
    <head></head>
    <body>
        <div class="container">
            <div class="w-box">
                <div class="w-box-header">
                    <!-- <h4>Tabs</h4> -->
                </div>
                <div class="w-box-content cnt_b">
                    <div class="row-fluid">
                        <div class="span12">
                            <div class="tabbable tabs-left tabbable-bordered">
                                <ul class="nav nav-tabs">
                                    <li class="active"><a href="#tb3_a" data-toggle="tab"><s:property value="getText('text.mision.aboutus')" /></a></li>
                                    <li><a href="#tb3_b" data-toggle="tab"><s:property value="getText('text.vision.aboutus')" /></a></li>
                                </ul>
                                <div class="tab-content">
                                    <div class="tab-pane active" id="tb3_a">
                                        <p><s:property value="getText('text.mision.aboutus')" /></p>
                                        <p><s:property value="getText('desc.mision.aboutus')" /></p>
                                    </div>
                                    <div class="tab-pane" id="tb3_b">
                                        <p><s:property value="getText('text.vision.aboutus')" /></p>
                                        <p><s:property value="getText('desc.vision.aboutus')" /></p>
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


