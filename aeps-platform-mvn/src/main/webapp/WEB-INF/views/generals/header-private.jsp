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
                    <div class="span3">	
                        <img src="<%= request.getContextPath() %>/img/logoAEPS.png" style="width: 200px; height: 80px;">
                    </div>
                    <div class="span3">	
                        <label class="titlePrin">Agricultura Específica por Sitio</label>
                    </div>
                    <div class="span6">
                        <img src="<%= request.getContextPath() %>/img/header.png">
                    </div>                    
                </div>					
            </div>
        </div>
        <div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <ul class="nav" id="ulOptionsMenu">
                        <!--<li class="active homeCls">-->
                            <%--<sj:a href="home.action" onclick="activeOption('ulOptionsMenu')" targets="divBodyLayout">Inicio</sj:a>--%>
                        <!--</li>-->
                        <% if (user != null) { %>
                            <li class="dashboardCls">
                                <s:set id="contextPath"  value="#request.get('javax.servlet.forward.context_path')" />
                                <s:a href="%{contextPath}/dashboard.action" onclick="activeOption('ulOptionsMenu')" targets="divBodyLayout"><i class="icon-home"></i>Inicio</s:a>
                            </li>
                            <!--<li class="resumeCls">-->
                                <%--<s:a href="resume.action" onclick="activeOption('ulOptionsMenu')" targets="divBodyLayout">Resumen General</s:a>--%>
                            <!--</li>-->
                        <% } %>
                        <!--<li class="aboutCls">-->
                            <%--<sj:a href="about.action" onclick="activeOption('ulOptionsMenu')" targets="divBodyLayout">Quienes Somos</sj:a>--%>
                        <!--</li>-->
                        <!--<li class="contactCls">-->
                            <%--<sj:a href="contact.action" onclick="activeOption('ulOptionsMenu')" targets="divBodyLayout">Contactenos</sj:a>--%>
                        <!--</li>-->
                    </ul>
                    <!--<div>-->
                        <div class="formIngress">
                            <% if (user != null) { %>
                                <div class="user-box">
                                    <div class="user-box-inner">
                                        <img src="<%= request.getContextPath() %>/img/user_ingress.png" alt="" class="user-avatar img-avatar">
                                        <div class="user-info">
                                            Bienvenido, <strong><%= user.getNameUserUsr() %></strong>
                                            <ul class="unstyled">
                                                <li><s:a href="%{contextPath}/configuration.action">Configuración</s:a></li>
                                                <li>-</li>
                                                <!--<li><a href="logout.action"><i class="icon-off">Salir</i></a></li>-->
                                                <li><a href="<%= request.getContextPath() %>/logout.action">Salir</a></li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            <% } else { %>
                            <% } %>
                        </div>
                        <div class="formIngress">
                            <s:url id="localeEN" namespace="/" action="localePrivate" >
                                <s:param name="lang">en</s:param>
                            </s:url>
                            <s:url id="localeES" namespace="/" action="localePrivate" >
                                <s:param name="lang">es</s:param>
                            </s:url>
                            <ul class="nav">
                                <li>
                                    <a><s:property value="getText('text.select.language')" /></a>
                                </li>
                                <li>                                    
                                    <div class="btn-group">
                                        <a class="btn" href="#"><s:property value="getText('text.language')" /></a>
                                        <a class="btn dropdown-toggle" data-toggle="dropdown" href="#"><span class="caret"></span></a>
                                        <ul class="dropdown-menu">
                                            <li><s:a href="%{localeEN}"><img src="<%= request.getContextPath() %>/img/languages/kingdom-flat.png" class="img-rounded" /> <s:property value="getText('text.english')" /></s:a></li>
                                            <li><s:a href="%{localeES}"><img src="<%= request.getContextPath() %>/img/languages/spain-flat.png" class="img-rounded" /> <s:property value="getText('text.spanish')" /></s:a></li>
                                        </ul>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    <!--</div>-->
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