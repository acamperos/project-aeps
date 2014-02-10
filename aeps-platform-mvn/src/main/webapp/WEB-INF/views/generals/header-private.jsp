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
                                <div class="user-box">
                                    <div class="user-box-inner">
                                        <img src="img/user_ingress.png" alt="" class="user-avatar img-avatar">
                                        <div class="user-info">
                                            Bienvenido, <strong><%= user.getNameUserUsr() %></strong>
                                            <ul class="unstyled">
                                                <li><a href="configuration.action">Configuración</a></li>
                                                <li>&middot;</li>
                                                <!--<li><a href="logout.action"><i class="icon-off">Salir</i></a></li>-->
                                                <li><a href="logout.action">Salir</a></li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
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
                                <!--<li class="active homeCls">-->
                                    <%--<sj:a href="home.action" onclick="activeOption('ulOptionsMenu')" targets="divBodyLayout">Inicio</sj:a>--%>
                                <!--</li>-->
                                <% if (user != null) { %>
                                    <li class="dashCls">
                                        <sj:a href="resume.action" onclick="activeOption('ulOptionsMenu')" targets="divBodyLayout">Resumen General</sj:a>
                                    </li>
                                <% } %>
                                <!--<li class="aboutCls">-->
                                    <%--<sj:a href="about.action" onclick="activeOption('ulOptionsMenu')" targets="divBodyLayout">Quienes Somos</sj:a>--%>
                                <!--</li>-->
                                <!--<li class="contactCls">-->
                                    <%--<sj:a href="contact.action" onclick="activeOption('ulOptionsMenu')" targets="divBodyLayout">Contactenos</sj:a>--%>
                                <!--</li>-->
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <% if (user != null) { %>
            <script>
                $('.homeCls').removeClass("active");
                $('.dashCls').addClass("active");
            </script>
        <% } %>
    </body>
</html>