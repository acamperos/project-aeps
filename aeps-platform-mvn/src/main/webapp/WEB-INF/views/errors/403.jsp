<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <link rel="icon" type="image/ico" href="favicon.ico">
        <title>AEPS</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width"> 				
				<link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/css/bootstrap/bootstrap-responsive.min.css">
        <link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/css/bootstrap/bootstrap.min.css">
        <link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/css/generals/justified-nav.css">
        <link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/css/generals/main.min.css">
        <link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/css/font-awesome/css/font-awesome.min.css">
        <link href = 'http://fonts.googleapis.com/css?family=Istok+Web:400700400cursiva,700italicysubconjunto=latin,latin-ext' rel='stylesheet' type='text/css'>
    </head>
    <body>
				<%@ taglib prefix="s" uri="/struts-tags" %>
        <div class="container">
            <div class="row">
                <div class="span12">
                    <div class="hero-unit center">
                        <h1><s:property value="getText('title.permission403.errors')" /> <small><font color="red"><s:property value="getText('text.permission403.errors')" /></font></small></h1>
                        <br />
                        <p><s:property value="getText('area.permission403.errors')" />.</p> 
                        <a href="<%= request.getContextPath() %>/dashboard.action" class="btn btn-large btn-initial"><i class="icon-home icon-white"></i> <s:property value="getText('link.permission.errors')" /></a>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
