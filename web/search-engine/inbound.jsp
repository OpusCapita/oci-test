<?xml version="1.0" encoding="UTF-8"?>

<jsp:root version="1.2"
  xmlns:jsp="http://java.sun.com/JSP/Page"
  xmlns:c="http://java.sun.com/jstl/core">

  <jsp:scriptlet>
    // request.setCharacterEncoding(pageContext.getServletConfig().getServletContext().getInitParameter("characterEncoding"));
  </jsp:scriptlet>

  <jsp:directive.page contentType="text/html;charset=UTF-8"/>

  <html>
  <head>
    <title><jsp:expression>request.getCharacterEncoding()</jsp:expression></title>
  </head>
  <body>
    <c:if test="${not empty paramValues}" >
    <form>
    <table width="100%">
    <c:forEach items="${paramValues}" var="parameter">
      <jsp:scriptlet>
        // log.info(((java.util.Map.Entry)pageContext.getAttribute("parameter")).getKey());
        // log.info(((String[])((java.util.Map.Entry)pageContext.getAttribute("parameter")).getValue())[0]);
      </jsp:scriptlet>
      <tr>
      <td width="25%">
        <label><c:out value="${parameter.key}"/><![CDATA[&nbsp;:&nbsp;]]></label>
      </td>
      <td>
      <c:forEach items="${parameter.value}" var="parameterValue" varStatus="status">
        <c:if test="${status.index ne 0}">;</c:if>
        <c:out value="${parameterValue}"/>
      </c:forEach>
      </td>
      </tr>
    </c:forEach>
    </table>
    </form>
    </c:if>
  </body>
  </html>
</jsp:root>
