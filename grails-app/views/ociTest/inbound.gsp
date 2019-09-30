<%--
  Created by IntelliJ IDEA.
  User: logich
  Date: 9/30/2019
  Time: 2:06 PM
--%>

<%@ page import="javax.servlet.jsp.PageContext; com.opuscapita.ocisetest.properties.Encryptor" contentType="text/html;charset=UTF-8" %>
<%
    String secretKey = request.getAttribute("secretKey") as String
    if (secretKey != null) {
        Encryptor encryptor = new Encryptor(secretKey)
        pageContext.setAttribute("encryptor", encryptor)
    }
%>
<html>
<head>
    <title><% request.getCharacterEncoding() %></title>
</head>

<body>

<g:if test="${!inboundParametersMap.isEmpty()}">
    <form>
        <table width="100%">
            <g:each in="${inboundParametersMap}" var="parameter">
                <tr>
                    <td width="25%">
                        <g:if test="${parameter.key != 'action' && parameter.key != 'controller' &&
                                parameter.key != 'format' && parameter.key != 'id'}">
                            <label>${parameter.key}</label>
                        </g:if>
                    </td>
                    <td>
                        <g:each in="${parameter.value}" var="parameterValue">
                            <g:if test="${encryptor != null && 'TIMESTAMP' == parameter.key}">
                                <%
                                    new Date(Long.parseLong(((Encryptor) pageContext.getAttribute("encryptor")).decrypt(pageContext.getAttribute("parameterValue") as String)))
                                %>
                            </g:if>
                            <g:elseif test="${'~xmlDocument' == parameter.key}">
                                <%
                                    String encodedXml = pageContext.getAttribute("parameterValue") as String
                                    String returnXML = new String(Base64.Decoder.decode(encodedXml.getBytes("UTF-8")))
                                    pageContext.setAttribute("returnXML", returnXML)
                                %>
                                ${returnXML}
                            </g:elseif>
                            <g:else>
                                ${parameterValue}
                            </g:else>
                        </g:each>
                    </td>
                </tr>
            </g:each>
        </table>
    </form>
</g:if>

</body>
</html>