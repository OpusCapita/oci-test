<?xml version="1.0" encoding="UTF-8"?>

<jsp:root version="1.2"
  xmlns:jsp="http://java.sun.com/JSP/Page"
  xmlns:html="http://jakarta.apache.org/struts/tags-html-el"
  xmlns:c="http://java.sun.com/jstl/core">

  <jsp:directive.page contentType="text/html; charset=UTF-8"/>

  <html>

    <head>
      <style type="text/css">
        @import url("<c:url value="/css/default.css"/>");
      </style>
    </head>

    <body>
      <ul>OPC OCI test
        <c:forEach items="${actions}" var="action">
          <li><html:link action="/showOciParameters.do?function=${action}"><c:out value="${action}"/></html:link></li>
        </c:forEach>
      </ul>
    </body>

  </html>

</jsp:root>