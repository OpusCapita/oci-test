<%@ page import="com.opuscapita.ocisetest.security.Encryptor; org.apache.commons.codec.binary.Base64" contentType="text/html;charset=UTF-8" %>

<g:set var="encryptor" value="${request.getAttribute('secretKey') != null ?
        new Encryptor(request.getAttribute("secretKey") as String) : null}"/>
<html>
<head>
    <title>${request.getCharacterEncoding()}</title>
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
                                ${new Date(Long.parseLong(encryptor.decrypt(parameterValue as String)))}
                            </g:if>
                            <g:elseif test="${'~xmlDocument' == parameter.key}">
                                ${new String(Base64.Decoder.decode((parameterValue as String).getBytes('UTF-8')))}
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