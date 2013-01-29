<%@page contentType="text/html; charset=utf-8"%>
<%@page import="es.uji.commons.sso.User"%>
<!DOCTYPE html>
<html>
  <head>
    <title>Horarios</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="http://static.uji.es/js/extjs/extjs-4.1.1/resources/css/ext-all.css">
    <link rel="stylesheet" type="text/css" href="http://static.uji.es/js/extjs/extensible-1.5.1/resources/css/extensible-all.css"> 
    <link rel="stylesheet" type="text/css" href="css/custom.css"> 
	<link rel="stylesheet" type="text/css"	href="http://static.uji.es/js/extjs/uji-commons-extjs/css/icons.css" />    
    <script type="text/javascript" src="http://static.uji.es/js/extjs/extjs-4.1.1/ext.js"></script>
    <script type="text/javascript" src="http://static.uji.es/js/extjs/extjs-4.1.1/locale/ext-lang-ca.js"></script>
    <script type="text/javascript" src="http://static.uji.es/js/extjs/extensible-1.5.1/src/Extensible.js"></script>
    <script type="text/javascript" src="http://static.uji.es/js/extjs/extensible-1.5.1/src/locale/extensible-lang-ca.js"></script>       
<%
    String login = ((User) session.getAttribute(User.SESSION_USER))
					.getName();
%>
	<script type="text/javascript">
	var login = '<%=login%>';
	</script>  
    <script type="text/javascript" src="app/Application.js"></script> 
    <style>
      #errChk label { cursor: pointer; }
      #errTitle { font-weight: bold; color: #AF1515; }
    </style>    
  </head>
  <body>
  </body>
</html>