<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<html>
    <body>
        <%@page import="org.aepscolombia.platform.util.APConstants"%>
        <%@page import="org.aepscolombia.platform.models.entity.Users"%>
        <%@page import="org.aepscolombia.platform.models.entity.Entities"%>
        <%@page import="org.aepscolombia.platform.models.dao.EntitiesDao"%>
        <% Users user = (Users) session.getAttribute(APConstants.SESSION_USER); %>
        <% String entType = new EntitiesDao().getEntityType(user.getIdUsr()); %>
        <% //request.setAttribute("entType", entType); %>
        <div class="container">
            <div class="masthead">
                <div class="row">
                    <div class="span3">	
                        <img src="<%= request.getContextPath() %>/img/logoAEPS.png" style="margin-top: 10px;">
                    </div>
                    <div class="span3">	
                        <label class="titlePrin"><s:property value="getText('title.aepsmeans.header')" /></label>
                    </div>
                    <div class="span6">
                        <img src="<%= request.getContextPath() %>/img/header.png" style="height: 130px;">
                    </div>                    
                </div>					
            </div>
        </div>
        <div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <ul class="nav" id="ulOptionsMenu">
                        <% if (user != null) { %>
                            <li class="dashboardCls">
                                <s:set id="contextPath"  value="#request.get('javax.servlet.forward.context_path')" />
                                <s:a href="%{contextPath}/dashboard.action" onclick="activeOption('ulOptionsMenu')" targets="divBodyLayout"><i class="icon-home"></i><s:property value="getText('button.home.platform')" /></s:a>
                            </li>
                        <% } %>
                    </ul>
                        <div class="formIngress">
                            <% if (user != null) { %>
                                <div class="user-box">
                                    <div class="user-box-inner">
                                        <img src="<%= request.getContextPath() %>/img/user_ingress.png" alt="" class="user-avatar img-avatar">
                                        <div class="user-info">
                                            <s:property value="getText('title.welcome.header')" />, <strong><%= user.getNameUserUsr() %></strong> (<%= entType %>)
                                            <ul class="unstyled">
                                                <li><s:a href="%{contextPath}/configuration.action"><i class="icon-wrench"></i> <s:property value="getText('link.setting.header')" /></s:a></li>
                                                <li>-</li>
                                                <li><a href="<%= request.getContextPath() %>/logout.action" onclick="ga('send', 'event', 'Register', 'click', 'LogOut');"><i class="icon-signout"></i> <s:property value="getText('link.exit.header')" /></a></li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            <% } else { %>
                            <% } %>
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