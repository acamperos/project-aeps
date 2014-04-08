<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<!DOCTYPE html>
<!--<html>
    <head>-->
<!--    </head>
    <body>-->
        <s:actionerror theme="bootstrap"/>
        <s:actionmessage theme="bootstrap"/>
        <s:fielderror theme="bootstrap"/>
        <div class="container">
            <div class="panel">
                <div class="panel-body">
                    <div class="row">
                        <div class="span4">
                            <div class="panel">
                                <div class="panel-heading">
                                    <h3><i class="icon-pushpin main-color"></i> Nuestra Oficina</h3>
                                </div>
                                <div class="panel-body">
                                    <address>
                                        <strong>CIAT</strong><br>
                                        Km 17, Recta Cali-Palmira<br>
                                        Apartado Aéreo 6713<br>
                                        Cali, Colombia<br>
                                        Telefono: <i class="icon-phone-sign"></i> (+57) 2 445-0000
                                    </address>
                                </div>
                            </div>
                            <div class="panel">
                                <div class="panel-heading">
                                  <h3><i class="icon-time main-color"></i> Horarios de oficina</h3>
                                </div>
                                <div class="panel-body">
                                    <table class="table table-hover">
                                      <thead>
                                        <tr>
                                          <th>Dia</th>
                                          <th>Horario</th>
                                        </tr>
                                      </thead>
                                      <tbody>
                                        <tr class="success">
                                          <td>Lunes</td>
                                          <td>7:30 a 4:30</td>
                                        </tr>
                                        <tr class="success">
                                          <td>Martes</td>
                                          <td>7:30 a 4:30</td>
                                        </tr>
                                        <tr class="success">
                                          <td>Miercoles</td>
                                          <td>7:30 a 4:30</td>
                                        </tr>
                                        <tr class="success">
                                          <td>Jueves</td>
                                          <td>7:30 a 4:30</td>
                                        </tr>
                                        <tr class="success">
                                          <td>Viernes</td>
                                          <td>7:30 a 4:30</td>
                                        </tr>
                                        <tr class="error">
                                          <td>Sabado</td>
                                          <td>Dia libre</td>
                                        </tr>
                                        <tr class="error">
                                          <td>Domingo</td>
                                          <td>Dia libre</td>
                                        </tr>
                                      </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="span7">
                            <div class="panel">
                                <div class="panel-heading">
                                    <h3><i class="icon-envelope main-color"></i> <s:property value="getText('text.titlecontact.contact')" /></h3>
                                </div>
                                <div class="panel-body">
                                    <s:form id="formContact" action="sendInformation.action" method="post">
                                        <s:hidden name="actExe" value="contact"/>
                                            <div class="control-group required_field_mark">
                                                <label for="formContact_nameUser" class="control-label"><s:property value="getText('text.name.contact')" /> <span>*</span>:</label>
                                                <div class="controls">
                                                    <s:textfield cssClass="form-control" id="formContact_nameUser" name="nameUser"/>
                                                </div>
                                            </div>
                                            <div class="control-group required_field_mark">
                                                <label for="formContact_emailUser" class="control-label"><s:property value="getText('text.email.contact')" /> <span>*</span>:</label>
                                                <div class="controls">
                                                    <s:textfield cssClass="form-control" id="formContact_emailUser" name="emailUser" placeholder="%{getText('text.ingressemial.contact')}"/>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="span3 control-group">
                                                    <label for="formContact_celphone" class="control-label"><s:property value="getText('text.cellphone.contact')" /> :</label>
                                                    <div class="controls">
                                                        <s:textfield cssClass="form-control" id="formContact_celphone" name="celphone"/>
                                                    </div>
                                                </div>
                                                <div class="span3 control-group">
                                                    <label for="formContact_telephone" class="control-label"><s:property value="getText('text.phone.contact')" /> :</label>
                                                    <div class="controls">
                                                        <s:textfield cssClass="form-control" id="formContact_telephone" name="telephone"/>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="control-group required_field_mark">
                                                <label for="formContact_whatneed" class="control-label"><s:property value="getText('text.whatneed.contact')" /> <span>*</span>:</label>
                                                <div class="controls">
                                                    <s:textarea rows="5" cssClass="span6" name="whatneed"></s:textarea>
                                                    <!--<textarea rows="5" class="span6" id="formContact_whatneed" name="whatneed"></textarea>-->
                                                </div>					 
                                            </div>					 
                                            <div class="row">
                                                <div class="span6">
                                                    <!--<button type="submit" class="btn btn-initial">Enviar</button>-->
                                                    <!-- <button class="btn btn-default" data-href="home.action">Volver</button> -->
                                                    <%--<sj:a href="home.action" cssClass="btn btn-default" targets="divBodyLayout">Volver</sj:a>--%>
                                                    <!--<a href="home.action" class="btn btn-default">Volver</a>-->
                                                    <sj:submit cssClass="btn btn-large btn-initial" onclick="addMessageProcess()" targets="divMessage" onCompleteTopics="completeContact" value="Enviar informacion" validate="true" validateFunction="validationForm"/>
                                                </div>  
                                            </div>  
                                            <script>
                                                $.subscribe('completeContact', function(event, data) {
                                                    completeFormChange('', 'formContact', event.originalEvent.request.responseText);
                                                });
                                            </script>
                                    </s:form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
<!--    </body>
</html>-->