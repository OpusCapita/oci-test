<?xml version="1.0" encoding="UTF-8"?>

<jsp:root version="1.2"
xmlns:jsp="http://java.sun.com/JSP/Page"
xmlns:c="http://java.sun.com/jstl/core"
xmlns:html="http://jakarta.apache.org/struts/tags-html-el">

<jsp:directive.page contentType="text/html;charset=UTF-8"/>

<html style="height:90%;">
  <head></head>
  <body onload="document.forms['ociSubmit'].submit()" style="height:100%;">
    <![CDATA[<form target="iframe" name="ociSubmit" action="]]><c:out value="${param.catalog_url}" /><![CDATA[" method="post">]]>
      <c:forEach items="${ociRequestParams}" var="item">
        <jsp:text><![CDATA[<input type="hidden" name="]]></jsp:text><c:out value="${item.key}"/><jsp:text><![CDATA[" value="]]></jsp:text><c:out value="${item.value}"/><jsp:text><![CDATA["/>]]></jsp:text>
      </c:forEach>
   <![CDATA[</form>]]>
    <p>Application is loaded in iframe</p>
    <iframe id="iframe" name="iframe" frameborder="1" width="100%" height="100%"></iframe>
  </body>
</html>
</jsp:root>
