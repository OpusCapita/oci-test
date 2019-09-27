<%--
  Created by IntelliJ IDEA.
  User: logich
  Date: 9/17/2019
  Time: 2:56 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>oci-test</title>
    <meta name="layout" content="main"/>
    <r:external uri="/css/default.css"/>
</head>

<body>
<g:form controller="ociTest" action="addProperty">
    <fieldset>
        <legend>
            Input new property parameters
        </legend>

        <label>Property name</label>
        <g:textField property="name" size="40" name="name"/><br/>
        <label>Property value</label>
        <g:textField property="value" size="40" name="value"/><br/>

        <br/>
        <label>&nbsp;</label>
        <input type="button" value="back" onClick="window.history.back();"/>
        <input type="submit" value="add property"/>
    </fieldset>
</g:form>
</body>
</html>