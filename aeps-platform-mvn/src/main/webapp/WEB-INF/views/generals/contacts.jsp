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
            <div class="w-box">
                <div class="w-box-header">
                    <!-- <h4>Tabs</h4> -->
                </div>
                <div class="w-box-content cnt_b">
                    <s:form theme="simple">
                        <fieldset>
                            <legend>Contactenos</legend>
                            <div class="form-group">
                                <s:label for="formContact_nameUser">Nombre Completo *:</s:label>
                                <s:textfield cssClass="form-control" id="formContact_nameUser"/>
                            </div>
                            <div class="form-group">
                                <s:label for="formContact_emailUser">Correo Electr&oacute;nico *:</s:label>
                                <s:textfield cssClass="form-control" id="formContact_emailUser" placeholder="Ingrese correo"/>
                            </div>
                            <div class="row">
                                <div class="span4">
                                    <s:label for="formContact_celphone">Celular :</s:label>
                                    <s:textfield cssClass="form-control" id="formContact_celphone"/>
                                </div>
                                <div class="span6">
                                    <s:label for="formContact_telephone">Telefono :</s:label>
                                    <s:textfield cssClass="form-control" id="formContact_telephone"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <s:label for="formContact_whatneed">Necesidad *:</s:label>
                                <textarea rows="5" id="formContact_whatneed"></textarea>
                            </div>					 
                            <div class="row">
                                <div class="span6">
                                    <!--<button type="submit" class="btn btn-primary">Enviar</button>-->
                                    <!-- <button class="btn btn-default" data-href="home.action">Volver</button> -->
                                <sj:a href="home.action" cssClass="btn btn-default" targets="divBodyLayout">Volver</sj:a>
                                    <!--<a href="home.action" class="btn btn-default">Volver</a>-->
                                </div>  
                            </div>  
                        </fieldset>
                    </s:form>
                </div>
            </div>
        </div>
<!--    </body>
</html>-->