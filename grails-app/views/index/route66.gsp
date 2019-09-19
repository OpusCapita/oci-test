<%--
  Created by IntelliJ IDEA.
  User: logich
  Date: 9/16/2019
  Time: 4:42 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>oci-test</title>
    <meta name="layout" content="main"/>
    %{--<r:external uri="/js/oci-default.js"/>--}%
    <r:external uri="/css/default.css"/>
    <r:require modules="jsUtils, jquery"/>
</head>

<body>
<h5>${session.getAttribute('function')}</h5>

<form id="ociForm">
    <g:if test="${saveFlag}">
        <h6>Configuration was successfully saved</h6>
    </g:if>
    <fieldset>
        <legend>
            Fill the form
        </legend>
        <input type="hidden" name="cmd" value="OCILogin"/>
        <table cellspacing="2" cellpadding="0">
            <tr>
                <td class="label"><label>send values encrypted/encoded:</label></td>
                <td><input type="checkbox" id="useEncryption" name="useEncryption" onclick="storeChecked();
                changeEncryptionVisibility();"/></td>
                %{--<td><![CDATA[&nbsp]]></td>--}%
            </tr>
            <tr>
                <td class="label"><label>set IE7 compatibility mode:</label></td>
                <td><input type="checkbox" id="useIE7mode" name="useIE7mode" onclick="storeChecked();"/></td>
                %{--<td><![CDATA[&nbsp]]></td>--}%
            </tr>
            <tr>
                <td class="label"><label>Enable scroll in OPC:</label></td>
                <td><input type="checkbox" id="enableScrollbar" name="enableScrollbar" onclick="storeChecked();"/></td>
                %{--<td><![CDATA[&nbsp]]></td>--}%
            </tr>
            <g:each in="${propertiesMap}" var="property">
                <tr id="${property.key}">
                    <td class="label">
                        <label>
                        %{--<c:choose>--}%
                        %{--<c:when test="${property.key == 'validityInterval'}">--}%
                        %{--<c:out value="request validity interval (seconds):"/>--}%
                        %{--</c:when>--}%
                        %{--<c:otherwise>--}%
                        %{--<c:out value="${property.key}:"/>--}%
                        %{--</c:otherwise>--}%
                        %{--</c:choose>--}%
                            <g:if test="${property.key == 'validityInterval'}">
                                <a>request validity interval (seconds):</a>
                            </g:if>
                            <g:else>
                                <a>${property.key}:</a>
                            </g:else>
                        </label>
                    </td>
                    %{--<td><a property="${property.key}" value="${property.value}" size="40"/><![CDATA[&nbsp]]></td>--}%
                    <td>
                        %{--<c:choose>--}%
                        %{--<c:when test="${property.key == 'secretKey' or property.key=='validityInterval'}">--}%
                        %{--<![CDATA[&nbsp]]>--}%
                        %{--</c:when>--}%
                        %{--<c:otherwise>--}%
                        %{--<html:button property="" value="delete" onclick="deleteProperty('${property.key}')"/><br/>--}%
                        %{--</c:otherwise>--}%
                        %{--</c:choose>--}%
                        <g:textField style="width: 25em" name="test" value="${property.value}"/>
                    </td>
                    <g:if test="${property.key != 'secretKey' && property.key != 'validityInterval'}">
                    %{--<td><input type="button" value="delete" onclick="removeRow(this.parentElement.parentElement);"/></td>--}%
                        <td><button type="submit" value="delete"><g:link style="color: #111122" controller="index"
                                                                         action="deletePropertyAction"
                                                                         params="[rowToRemove: property?.key]">delete</g:link></button>
                        </td>
                    </g:if>
                </tr>
            </g:each>
        </table>
        %{--<![CDATA[&nbsp]]>--}%
        %{--<button value="do it" onclick="return run();">do it</button>--}%
        <input type="button" value="do it"/>
        %{--<![CDATA[&nbsp]]>--}%
        %{--<input type="submit" value="add property" onClick="return addPropertyPage();"/>--}%
        <button type="submit" value="add property"><g:link style="color: #111122" controller="index"
                                                           action="addPropertyPage">add property</g:link></button>
        %{--<![CDATA[&nbsp]]>--}%
        <button type="submit" value="save configuration"><g:link style="color: #111122" controller="index"
                                                                 action="saveConfigurationAction">save configuration</g:link></button>
        %{--<![CDATA[&nbsp]]>--}%
        %{--<input type="button" value="back" onclick="back()"/>--}%
        <button type="submit" value="back"><g:link style="color: #111122" controller="index"
                                                   action="index">back</g:link></button>
    </fieldset>
</form>
</body>
</html>