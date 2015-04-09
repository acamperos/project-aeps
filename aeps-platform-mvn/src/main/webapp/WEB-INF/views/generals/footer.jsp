<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<div id="clients">
    <div class="container">
        <div class="section_header">
            <h3>Socios</h3>
        </div>
        <div class="row">
            <div class="span2 client">
                <a href="http://ciat.cgiar.org/"><div class="img client1"></div></a>
            </div>
            <div class="span2 client">
                <a href="https://www.minagricultura.gov.co/"><div class="img client2"></div></a>
            </div>
            <div class="span2 client">
                <a href="http://www.agronet.gov.co/"><div class="img client3"></div></a>
            </div>
            <div class="span2 client">
                <a href="http://fenalce.org/nueva/index.php"><div class="img client4"></div></a>
            </div>
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