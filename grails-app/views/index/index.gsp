<%--
  Created by IntelliJ IDEA.
  User: logich
  Date: 9/12/2019
  Time: 3:14 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>oci-test</title>
    <r:external uri="/css/default.css"/>
    <r:external uri="/js/oci-default.js"/>
</head>

<body>
<ul>OPC OCI test
%{--<c:forEach items="${actions}" var="action">--}%
%{--<li><html:link action="/showOciParameters.do?function=${action}"><c:out value="${action}"/></html:link></li>--}%
%{--</c:forEach>--}%
    <li><a href="http://google.com">Google</a></li>
</ul>
<ul>OPC Ariba test
    <li><a href="index/createSetupRequest.gsp">Ariba Tester</a></li>
</ul>
<ul>OPC Oracle Transparent Punchout test
    <li><a href="oracle-transparent-punchout-test/createItemSearchRequest.jsp">Oracle Transparent Punchout Tester</a>
    </li>
</ul>

<div style="margin-left:25px;">
    Open OCI test window without scroll
    <br/><small>Need for testing 'enableScrollbar' parameter for OCI login into OPC from SAP SRM systems.</small><br/><br/>
    <a href="#" onclick="openInWindowWithoutScroll();">OPC OCI test without scroll</a>
</div>
</body>
</html>