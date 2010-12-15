<?xml version="1.0" encoding="UTF-8"?>

<jsp:root version="1.2"
    xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core">

  <jsp:directive.page import="com.jcatalog.oci.section.outbound.encryptor.Encryptor"/>
  <jsp:directive.page import="java.util.Date"/>
  <jsp:directive.page import="org.apache.commons.codec.binary.Base64"/>

  <jsp:scriptlet>
    // request.setCharacterEncoding(pageContext.getServletConfig().getServletContext().getInitParameter("characterEncoding"));
    String secretKey = (String) request.getAttribute("secretKey");
    if (secretKey != null) {
      Encryptor encryptor = new Encryptor(secretKey);
      pageContext.setAttribute("encryptor", encryptor);
    }
  </jsp:scriptlet>

  <jsp:directive.page contentType="text/html;charset=UTF-8"/>

  <html>
    <head>
      <title>
        <jsp:expression>request.getCharacterEncoding()</jsp:expression>
      </title>
    </head>
    <body>

    <c:if test="${not empty paramValues}">
      <form>
        <table width="100%">
          <c:forEach items="${paramValues}" var="parameter">
            <tr>
              <td width="25%">
                <label><c:out value="${parameter.key}"/><![CDATA[&nbsp;:&nbsp;]]></label>
              </td>
              <td>
                <c:forEach items="${parameter.value}" var="parameterValue" varStatus="status">
                  <c:if test="${status.index ne 0}">;</c:if>
                  <c:choose>
                    <c:when test="${encryptor != null and 'TIMESTAMP'==parameter.key}">
                      <jsp:expression>
                        new Date(Long.parseLong(((Encryptor)pageContext.getAttribute("encryptor")).decrypt((String) pageContext.getAttribute("parameterValue"))))
                      </jsp:expression>
                    </c:when>
                    <c:when test="${'~xmlDocument'==parameter.key}">
                      <jsp:scriptlet>
                        Base64 base64 = new Base64();
                        String encodedXml = (String) pageContext.getAttribute("parameterValue");
                        String returnXML = new String(base64.decode(encodedXml.getBytes("UTF-8")));
                        pageContext.setAttribute("returnXML", returnXML);
                      </jsp:scriptlet>
                      <c:out value="${returnXML}"/>
                    </c:when>
                    <c:otherwise>
                      <c:out value="${parameterValue}"/>
                    </c:otherwise>
                  </c:choose>
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
