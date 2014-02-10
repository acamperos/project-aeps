<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<html>
    <head></head>
    <body>
        <%@page import="org.aepscolombia.platform.util.APConstants"%>
        <%@page import="org.aepscolombia.platform.models.entity.Users"%>
        <% Users user = (Users) session.getAttribute(APConstants.SESSION_USER); %>
        <div class="container">
            <div class="masthead">
                <div class="row">
                    <div class="span6">	
                        <h3 class="text-muted">AEPS</h3>
                    </div>
                    <div class="span6">
                        <div class="formIngress">
                            <% if (user != null) { %>
                                <p style="margin-bottom:0px">Ya te encuentras registrado</p>
                                <a style="float: right" href="signin.action" class="btn btn-primary btn-success btn-lg">Ingresar</a>
                            <% } else { %>
                                <a href="signin.action" class="btn btn-primary btn-success btn-lg">Ingresar</a>
                                <a href="signin.action?logSel=new" class="btn btn-default btn-lg">Registrarse</a>			
                            <% } %>
                        </div>
                    </div>
                </div>					
            </div>
            <div class="masthead">
                <div class="navbar">
                    <div class="navbar-inner">
                        <div class="container">
                            <ul class="nav" id="ulOptionsMenu">
                                <li class="active homeCls">
                                    <sj:a href="home.action" onclick="activeOption('ulOptionsMenu')" targets="divBodyLayout">Inicio</sj:a>
                                </li>
                                <li class="aboutCls">
                                    <sj:a href="about.action" onclick="activeOption('ulOptionsMenu')" targets="divBodyLayout">Quienes Somos</sj:a>
                                </li>
                                <li class="contactCls">
                                    <sj:a href="contact.action" onclick="activeOption('ulOptionsMenu')" targets="divBodyLayout">Contactenos</sj:a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <% if (user != null) { %>
            <script>
                $('.homeCls').removeClass("hide");
                $('.dashCls').addClass("hide");
            </script>
        <% } %>
    </body>
</html>