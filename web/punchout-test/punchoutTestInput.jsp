<?xml version="1.0" encoding="UTF-8"?>

<jsp:root version="1.2"
  xmlns:jsp="http://java.sun.com/JSP/Page"
  xmlns:c="http://java.sun.com/jstl/core"
  xmlns:html="http://jakarta.apache.org/struts/tags-html-el">

  <jsp:directive.page contentType="text/html;charset=UTF-8"/>

  <html>
    <head>
      <style type="text/css">
        @import url("<c:url value="/css/default.css"/>");
      </style>

      <script language="javascript">
        var jcApplicationContextPath = '<jsp:expression>request.getContextPath()</jsp:expression>';

        function copyItem(number){
          document.forms[number].action="copyItem.do?number="+number;
          return true;
        }

        function deleteItem(number){
          location.href="deleteItem.do?number="+number;
        }

        function saveItem(number){
          document.forms[number].action="saveItem.do?number="+number;
          return true;
        }

        function submitData(){
          location.href="showInboundData.do";
        }

      </script>
    </head>
    <body>
      <c:forEach items="${itemStorage.items}" var="item" varStatus="status">
        <form method="post">
          <fieldset>
            <c:forEach items="${item}" var="property">
              <label><c:out value="${property.key}:"/></label>
              <html:text property="${property.key}" value="${property.value}" size="40"/><br/>
            </c:forEach>
            <label><![CDATA[&nbsp]]></label>
            <html:submit value="Copy" onclick="return copyItem(${status.index});"/>
             <![CDATA[&nbsp]]>
            <html:submit value="Save" onclick="return saveItem(${status.index});"/>
            <![CDATA[&nbsp]]>
            <html:button property="" value="Delete" onclick="deleteItem(${status.index});"/>
          </fieldset><br/>
        </form>
      </c:forEach>
      <label><![CDATA[&nbsp]]></label>
      <input type="submit" value="Submit Data" onClick="submitData();"/>
      <br/>
    </body>
  </html>
</jsp:root>
