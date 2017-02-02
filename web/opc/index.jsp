<?xml version="1.0" encoding="UTF-8"?>

<jsp:root version="1.2"
  xmlns:jsp="http://java.sun.com/JSP/Page"
  xmlns:html="http://jakarta.apache.org/struts/tags-html-el"
  xmlns:c="http://java.sun.com/jstl/core">

  <jsp:directive.page contentType="text/html; charset=UTF-8"/>

  <html>

    <head>
      <script>
        function openInWindowWithoutScroll() {
          window.open(window.location.href, 'testWindow',
                  "directories=0,titlebar=0,toolbar=0,location=0,status=0,menubar=0,scrollbars=no,resizable=no,width=1300,height=900");
        }
      </script>
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
      <ul>OPC Ariba test
        <li><a href="ariba-test/createSetupRequest.jsp">Ariba Tester</a></li>
      </ul>
      <ul>OPC Oracle Transparent Punchout test
        <li><a href="oracle-transparent-punchout-test/createItemSearchRequest.jsp">Oracle Transparent Punchout Tester</a></li>
      </ul>

      <div style="margin-left:25px;">
        Open OCI test window without scroll
        <br/><small>Need for testing 'enableScrollbar' parameter for OCI login into OPC from SAP SRM systems.</small><br/><br/>
        <a href="#" onclick="openInWindowWithoutScroll();">OPC OCI test without scroll</a>
      </div>
    </body>

  </html>

</jsp:root>