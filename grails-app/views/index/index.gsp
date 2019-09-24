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
    <meta name="layout" content="main"/>
    <r:require modules="frontUtils, jquery"/>
</head>

<body>
<ul>OPC OCI test
    <g:each in="${actions}" var="actionLink">
        <li><g:link controller="index" action="anyActionPage"
                    params="[actionlink: actionLink]">${actionLink}</g:link></li>
    </g:each>
</ul>
<ul>OPC Ariba test
    <li><g:link controller="tester" action="aribaTesterPage">Ariba Tester</g:link></li>
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