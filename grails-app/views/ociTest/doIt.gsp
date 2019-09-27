<%--
  Created by IntelliJ IDEA.
  User: logich
  Date: 9/20/2019
  Time: 6:26 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>oci-test</title>
    <meta name="layout" content="main"/>
    <g:if test="${ociRequestParams.useIE7mode == 'on'}">
        <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
    </g:if>
</head>

<body>
<r:script>
    window.onload = function () {
        document.forms['ociSubmit'].submit();
        window.close();
    };
</r:script>
<form target="${params.targetFrame}" name="ociSubmit" action="${params.catalog_url}" method="post">
    <g:each in="${ociRequestParams}" var="item">
        <input type="hidden" name="${item.key}" value="${item.value}"><br/>
    </g:each>
</form>
</body>
</html>