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
        var jcApplicationContextPath = '<jsp:expression>request.getContextPath()</jsp:expression>';

        function addProperty(){
          document.forms[0].action = jcApplicationContextPath+'/showAddingPage.do?forward=punchout';
          return true;
        }

        function back(){
          location.href="initPunchoutIndex.do"
        }

        function save(){
          document.forms[0].action="savePunchoutSchema.do";
          return true;
        }

        function deleteProperty(property){
          location.href="deletePunchoutProperty.do?property="+property;
        }

      </script>
    </head>
    <body>

      <form method="post">
        <fieldset>
          <legend>
            <c:out value="${schema}"/>
          </legend>
          <c:forEach items="${properties}" var="property" varStatus="status">
            <label><c:out value="${property.key}:"/></label>
            <html:text property="${property.key}" value="${property.value}" size="40"/><![CDATA[&nbsp]]>
            <html:button property="" onclick="deleteProperty('${property.key}')" value="delete"/><br/>
          </c:forEach>
          <label><![CDATA[&nbsp]]></label>
          <input type="submit" value="add property" onClick="return addProperty();"/>
           <![CDATA[&nbsp]]>
          <input type="submit" value="save" onClick="save()"/>
            <![CDATA[&nbsp]]>
          <input type="button" value="back" onclick="back()"/>
        </fieldset>
      </form>

    </body>
  </html>
</jsp:root>
