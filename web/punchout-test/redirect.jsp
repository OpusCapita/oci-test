<?xml version="1.0" encoding="UTF-8"?>

<jsp:root version="1.2"
  xmlns:jsp="http://java.sun.com/JSP/Page"
  xmlns:c="http://java.sun.com/jstl/core"
  xmlns:html="http://jakarta.apache.org/struts/tags-html-el">

  <jsp:directive.page contentType="text/html"/>

  <html>
    <body onload="document.forms.oci_data.submit();">
      <jsp:text><![CDATA[<form name="oci_data" action="]]></jsp:text><c:out value="${HOOK_URL}"/><jsp:text><![CDATA[" method="post">]]></jsp:text>
        <c:forEach items="${items}" var="item" varStatus="status">
          <c:forEach items="${item}" var="property">
            <html:hidden property="${property.key}" value="${property.value}"/>
          </c:forEach>
        </c:forEach>
      <jsp:text><![CDATA[</form>]]></jsp:text>
    </body>
  </html>

  <jsp:scriptlet>
    session.invalidate();
  </jsp:scriptlet>
  
</jsp:root>
