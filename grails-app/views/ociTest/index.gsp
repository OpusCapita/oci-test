<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>oci-test</title>
    <meta name="layout" content="main"/>
</head>

<body>
<ul>OPC OCI test
    <g:each in="${actions}" var="actionLink">
        <li><g:link controller="ociTest" action="ociTestAction"
                    params="[actionlink: actionLink]">${actionLink}</g:link></li>
    </g:each>
</ul>
<ul>OPC Ariba test
    <li><g:link controller="ociTest" action="showAribaTester">Ariba Tester</g:link></li>
</ul>
<ul>OPC Oracle Transparent Punchout test
    <li><g:link controller="ociTest" action="showPunchoutTester">Oracle Transparent Punchout Tester</g:link></li>
</ul>

<div style="margin-left:25px;">
    Open OCI test window without scroll
    <br/><small>Need for testing 'enableScrollbar' parameter for OCI login into OPC from SAP SRM systems.</small><br/><br/>
    <a href="#"
       onclick="window.open(window.location.href, 'testWindow', 'directories=0,titlebar=0,toolbar=0,location=0,status=0,menubar=0,scrollbars=no,resizable=no,width=1300,height=900');">OPC OCI test without scroll</a>
</div>
</body>
</html>