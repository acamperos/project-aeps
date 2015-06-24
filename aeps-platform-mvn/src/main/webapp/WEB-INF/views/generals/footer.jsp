<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<% String coCodeF  = (String) session.getAttribute(APConstants.COUNTRY_CODE); %>
<div id="clients">
    <div class="container">
        <div class="section_header">
            <h3><s:property value="getText('title.partners.footer')" /></h3>
        </div>
        <div class="row">
            <div class="span2 client">
                <a href="http://ciat.cgiar.org/"><div class="img client1"></div></a>
            </div>
            <% if (coCodeF!=null && coCodeF.equals("CO")) { %>
                <div class="span2 client">
                    <a href="https://www.minagricultura.gov.co/"><div class="img client2"></div></a>
                </div>
                <div class="span2 client">
                    <a href="http://www.agronet.gov.co/"><div class="img client3"></div></a>
                </div>
                <div class="span2 client">
                    <a href="http://fenalce.org/nueva/index.php"><div class="img client4"></div></a>
                </div>
            <% } else if (coCodeF!=null && coCodeF.equals("NI")) { %>
                <div class="span2 client">
                    <a href="http://ccafs.cgiar.org/"><div class="img client5"></div></a>
                </div>
                <div class="span2 bigclient">
                    <a href="http://flar.org/"><div class="img client6"></div></a>
                </div>
                <div class="span2 bigclient">
                    <a href="http://www.anar.com.ni/"><div class="img client7"></div></a>
                </div>
            <% } %>
        </div>
    </div>
</div>
<footer id="footer">
    <div class="container">
        <s:text name="%{getText('area.madeinformation.footer')}"/>.
        <hr>
        <div class="row credits">
            <div class="copyright"> <s:property value="getText('label.rights.footer')" />.</div>
        </div>
    </div>
</footer>