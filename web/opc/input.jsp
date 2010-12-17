<?xml version="1.0" encoding="UTF-8"?>

<jsp:root version="1.2"
  xmlns:jsp="http://java.sun.com/JSP/Page"
  xmlns:c="http://java.sun.com/jstl/core"
  xmlns:html="http://jakarta.apache.org/struts/tags-html-el">

  <jsp:directive.page contentType="text/html;charset=UTF-8"/>
  <jsp:directive.page import="java.util.PropertyResourceBundle"/>

  <html>
    <head>
      <style type="text/css">
        @import url("<c:url value="/css/default.css"/>");
      </style>
      <jsp:text><![CDATA[<script type="text/javascript" src="]]></jsp:text><c:url value="/js/cookies.js"/><jsp:text><![CDATA["></script>]]></jsp:text>

      <script language="javascript">
        var jcApplicationContextPath = '<jsp:expression>request.getContextPath()</jsp:expression>';

        function run() {
          if (document.forms[0].targetFrame.value.length > 0) {
            document.forms[0].target = document.forms[0].targetFrame.value;
          } else {
            document.forms[0].target = "_self";
          }
          return true;
        }

        function addProperty(){
          document.forms[0].action = jcApplicationContextPath+'/showAddingPage.do?forward=oci';
          return true;
        }

        function save(){
          document.forms[0].action = jcApplicationContextPath+'/saveOciConfiguration.do';
          return true;
        }

        function deleteProperty(property){
          location.href="deleteOciProperty.do?property="+property;
        }

        function back(){
          location.href="initOciIndex.do"
        }

        function changeEncryptionVisibility(){
          var form = document.getElementById("ociForm");
          var checked = document.getElementById("useEncryption").checked;
          if (form.secretKey) {
            form.secretKey.parentNode.parentNode.style.display = checked ? '' : 'none';
            form.secretKey.style.disabled = checked ? '' : 'disabled';
          }
          if (form.validityInterval) {
            form.validityInterval.parentNode.parentNode.style.display = checked ? '' : 'none';
            form.validityInterval.style.disabled = checked ? '' : 'disabled';
          }
        }

        var OCI_ENCRYPTED_COOKIE_NAME = "oci-encrypted";
        function restoreChecked(){
            if (getCookie(OCI_ENCRYPTED_COOKIE_NAME) == "true"){
                document.getElementById("useEncryption").checked = true;
            }
        }
        function storeChecked(el){
            setCookie(OCI_ENCRYPTED_COOKIE_NAME, el.checked);
        }
      </script>
    </head>
    <body onload="restoreChecked();changeEncryptionVisibility();">
      <jsp:scriptlet>

        String url = "";

        try {

          PropertyResourceBundle propertyBoundle =
            new PropertyResourceBundle(this.getServletConfig().getServletContext().getResourceAsStream("WEB-INF/conf/system.properties"));

            if (!propertyBoundle.getString("search.engine.url").equals("")) url = propertyBoundle.getString("search.engine.url");
          }
          catch (Exception e){
            url="";
          }

        request.setAttribute("url", url);
      </jsp:scriptlet>

      <h5><c:out value="${function}"/></h5>

      <c:choose>
        <c:when test="${saveFlag}">
          <h6>Configuration was successfully saved</h6><br/>
        </c:when>
        <c:otherwise>
          <h6><![CDATA[&nbsp]]></h6><br/>
        </c:otherwise>
      </c:choose>

      <form id="ociForm" method="post" action="processOciRequest.do">
        <fieldset>
          <legend>
            Fill the form
          </legend>
          <input type="hidden" name="cmd" value="OCILogin"/>
          <table cellspacing="2" cellpadding="0">
            <tr>
              <td class="label"><label><![CDATA[send values encrypted/encoded:]]></label></td>
              <td><input type="checkbox" id="useEncryption" name="useEncryption" onclick="storeChecked(this);changeEncryptionVisibility();"/></td>
              <td><![CDATA[&nbsp]]></td>
            </tr>
            <c:forEach items="${properties}" var="property" varStatus="status">
              <tr>
                <td class="label">
                    <label>
                        <c:choose>
                            <c:when test="${property.key == 'validityInterval'}">
                                <c:out value="request validity interval (seconds):"/>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${property.key}:"/>
                            </c:otherwise>
                        </c:choose>
                    </label>
                </td>
                <td><html:text property="${property.key}" value="${property.value}" size="40"/><![CDATA[&nbsp]]></td>
                <td>
                    <c:choose>
                        <c:when test="${property.key == 'secretKey' or property.key=='validityInterval'}">
                            <![CDATA[&nbsp]]>
                        </c:when>
                        <c:otherwise>
                            <html:button property="" value="delete" onclick="deleteProperty('${property.key}')"/><br/>
                        </c:otherwise>
                    </c:choose>
                </td>
              </tr>
            </c:forEach>
          </table>
            <![CDATA[&nbsp]]>
          <html:submit value="do it" onclick="return run();"/>
            <![CDATA[&nbsp]]>
          <input type="submit" value="add property" onClick="return addProperty();"/>
            <![CDATA[&nbsp]]>
          <input type="submit" value="save configuration" onClick="return save();"/>
            <![CDATA[&nbsp]]>
          <input type="button" value="back" onclick="back()"/>
        </fieldset>
      </form>

    </body>
  </html>
</jsp:root>