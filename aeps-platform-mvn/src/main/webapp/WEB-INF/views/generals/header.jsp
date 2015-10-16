<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<html>
    <head></head>
    <body>
        <%@page import="org.aepscolombia.platform.util.APConstants"%>
        <%@page import="org.aepscolombia.platform.models.entity.Users"%>
        <%@page import="org.aepscolombia.platform.models.entity.IdiomCountry"%>
        <%@page import="org.aepscolombia.platform.models.dao.IdiomCountryDao"%>
        <% Users user = (Users) session.getAttribute(APConstants.SESSION_USER);%>
        <% String coCode = (String) session.getAttribute(APConstants.COUNTRY_CODE); %>
        <% IdiomCountry idiom = new IdiomCountryDao().objectById(coCode); %>
        <% String nameCountry = ""; %>
        <% if (idiom != null) {%>
            <% nameCountry = idiom.getCountryIdCo(); %>
        <% }%>
        
        <div class="container">
            <div class="masthead">
                <div class="row">
                    <div class="span3">	
                        <img src="img/logoAEPS.png" style="margin-top: 10px;">
                    </div>  
                    <div class="span3">	
                        <label class="titlePrin">
                            <s:property value="getText('title.aepsmeans.header')" />
                            <br /><label style="top: 10px; position: relative;"><%=nameCountry %></label>
                        </label>
                    </div>
                    <div class="span6">
                        <img src="img/header.png" style="height: 130px;">
                    </div>
                </div>					
            </div>
        </div>
        <div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
										<ul class="nav" id="ulOptionsMenu">
												<li class="active homeCls">
                                                                                                    <s:set id="contextPath"  value="#request.get('javax.servlet.forward.context_path')" />
                                                                                                        <s:a href="locale.action" onclick="activeOption('ulOptionsMenu')"><i class="icon-home"></i><s:property value="getText('button.home.platform')" /></s:a>
												</li>
												<li class="aboutCls">
														<s:a href="about.action" onclick="activeOption('ulOptionsMenu')"><s:property value="getText('button.howis.platform')" /></s:a>
												</li>
												<li class="contactCls">
														<s:a href="contact.action" onclick="activeOption('ulOptionsMenu')"><s:property value="getText('button.contact.platform')" /></s:a>
												</li>
										</ul>
										<div class="formIngress">
												<% if (user != null) {%>
												<p style="margin-bottom:0px"><s:property value="getText('label.youareconnect.header')" /></p>
												<a style="float: right" href="signin.action" class="btn btn-initial"><s:property value="getText('link.ingress.header')" /></a>
												<% } else {%>
												<s:url id="ingress" namespace="/" action="signin" >
														<%--<s:param name="request_locale" ><%= request.getParameter("lang") %></s:param>--%>                                    
												</s:url>
												<s:url id="register" namespace="/" action="signin" >
														<s:param name="logSel" >new</s:param>                                    
														<%--<s:param name="request_locale" ><%= request.getParameter("lang") %></s:param>--%>                                    
												</s:url>
												<s:a href="%{ingress}" cssClass="btn btn-initial btn-large"><s:property value="getText('button.ingress.platform')" /></s:a>
												<s:a href="%{register}" cssClass="btn btn-default btn-large"><s:property value="getText('button.register.platform')" /></s:a>			
												<% }%>
										</div>
                                <div class="formIngress">
                                    <s:url id="localeEN" namespace="/" action="locale" >
                                        <s:param name="lang">en</s:param>
                                    </s:url>
                                    <s:url id="localeES" namespace="/" action="locale" >
                                        <s:param name="lang">espe</s:param>
                                    </s:url>
                                    <ul class="nav">
                                        <li>
                                            <a><s:property value="getText('select.language.header')" /></a>
                                        </li>
                                        <li>                                    
                                            <div class="btn-group">
                                                <a class="btn" href="#"><s:property value="getText('link.languagespa.header')" /></a>
                                                <a class="btn dropdown-toggle" data-toggle="dropdown" href="#"><span class="caret"></span></a>
                                                <ul class="dropdown-menu">
                                                    <li><a onclick="sendCountry('initial.action?lang=en')"><img src="img/languages/kingdom-flat.png" class="img-rounded" /> <s:property value="getText('link.optenglish.header')" /></a></li>
                                                    <li><a onclick="sendCountry('initial.action?lang=es')"><img src="img/languages/spain-flat.png" class="img-rounded" /> <s:property value="getText('link.optspanish.header')" /></a></li>
                                                </ul>
                                            </div>
                                        </li>
                                    </ul>
                                </div>                                    
                            <!--</div>-->
                            <!--</div>-->	
                        <!--</div>-->    
                    <!--</div>-->
            </div>
        </div>
        </div>
        <% if (user != null) {%>
            <script>
                $('.homeCls').removeClass("hide");
                $('.dashCls').addClass("hide");
            </script>
        <% }%>
    </body>
</html>