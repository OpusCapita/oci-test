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
      <script language="javascript">
        function back(){
          window.location="<c:url value="/editPunchoutSchema.do"/>";
        }
      </script>
    </head>
    <body>
      <html:form action="/addPunchoutProperty.do" method="post">
        <fieldset>
          <legend>
              Input new property parameters
          </legend>

          <label><c:out value="property name"/></label>
          <html:text property="name" size="40"/><br/>
          <label><c:out value="property value"/></label>
          <html:text property="value" size="40"/><br/>

          <label>&amp;nbsp;</label>
          <input type="submit" value="add property"/>
          <![CDATA[&nbsp]]>
          <input type="button" value="back" onClick="back()"/>
        </fieldset>
      </html:form>
    </body>
  </html>
</jsp:root>
