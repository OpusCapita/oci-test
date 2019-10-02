<%@ page contentType="text/html;charset=UTF-8" %>

<oc:ociSet var="encryptor" value="${request.getAttribute('secretKey')}"/>
<html>
<head>
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
                                <oc:ociParamValue value="${parameterValue}" encryptor="${encryptor}"/>
                            </g:if>
                            <g:elseif test="${'~xmlDocument' == parameter.key}">
                                <oc:ociParamValue value="${parameterValue}" decode="true"/>
                            </g:elseif>
                            <g:else>
                                <oc:ociParamValue value="${parameterValue}"/>
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