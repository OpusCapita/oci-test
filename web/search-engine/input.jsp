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

        function run() {
          document.forms[0].action = document.forms[0].catalog_url.value;
          if (document.forms[0].targetFrame.value.length > 0) {
            document.forms[0].target = document.forms[0].targetFrame.value;
          } else {
            document.forms[0].target = "_self";
          }
          return true;
        }

        function addProperty(){
          document.forms[0].action = jcApplicationContextPath+'/showAddingPage.do';
          return true;
        }

        function save(){
          document.forms[0].action = jcApplicationContextPath+'/saveConfiguration.do';
          return true;
        }

        function back(){
          location.href="init.do"
        }

      </script>
    </head>
    <body>
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

      <form method="post">
        <fieldset>
          <legend>
            Fill the form
          </legend>
          <input type="hidden" name="cmd" value="OCILogin"/>
          <c:forEach items="${properties}" var="property" varStatus="status">
            <label><c:out value="${property.key}:"/></label>
            <html:text property="${property.key}" value="${property.value}" size="40"/><br/>
          </c:forEach>
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
