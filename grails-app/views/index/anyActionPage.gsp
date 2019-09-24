<%--
  Created by IntelliJ IDEA.
  User: logich
  Date: 9/16/2019
  Time: 4:42 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <title>oci-test</title>
    <meta name="layout" content="main"/>
    <r:require modules="frontUtils, jquery"/>
</head>

<body>
<h5>${session.getAttribute('function')}</h5>

<g:form id="ociForm" name="ociForm">
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
            </tr>
            <tr>
                <td class="label"><label>set IE7 compatibility mode:</label></td>
                <td><input type="checkbox" id="useIE7mode" name="useIE7mode" onclick="storeChecked();"/></td>
            </tr>
            <tr>
                <td class="label"><label>Enable scroll in OPC:</label></td>
                <td><input type="checkbox" id="enableScrollbar" name="enableScrollbar" onclick="storeChecked();"/></td>
            </tr>
            <g:each in="${propertiesMap}" var="property">
                <tr id="${property.key}">
                    <td class="label">
                        <label>
                            <g:if test="${property.key == 'validityInterval'}">
                                <a>request validity interval (seconds):</a>
                            </g:if>
                            <g:else>
                                <a>${property.key}:</a>
                            </g:else>
                        </label>
                    </td>
                    <td>
                        <g:textField name="${property.key}" style="width: 25em" value="${property.value}"/>
                    </td>
                    <g:if test="${property.key != 'secretKey' && property.key != 'validityInterval'}">
                        <td><button value="delete"><g:link style="color: #111122" controller="index"
                                                           action="deletePropertyAction"
                                                           params="[rowToRemove: property?.key]">delete</g:link></button>
                        </td>
                    </g:if>
                </tr>
            </g:each>
        </table>
        <g:actionSubmit value="do It" action="doIt"/>
        <g:actionSubmit value="add property" action="addPropertyPage"/>
        <g:actionSubmit value="save configuration" action="saveConfigurationAction"/>
        <g:actionSubmit value="back" action="index"/>
    </fieldset>
</g:form>
<br/>
</body>
</html>