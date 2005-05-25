<?xml version="1.0" encoding="UTF-8"?>

<jsp:root version="1.2"
  xmlns:jsp="http://java.sun.com/JSP/Page"
  xmlns:html="http://jakarta.apache.org/struts/tags-html-el"
  xmlns:c="http://java.sun.com/jstl/core">

  <jsp:directive.page contentType="text/html; charset=UTF-8"/>

  <html>

    <head>
      <style type="text/css">
        @import url("<c:url value="/css/default.css"/>");
      </style>

      <script language="javascript">

        function deleteSchema(schemaName){
          location.href="deletePunchoutSchema.do?schemaName="+schemaName;

        }

        function editSchema(schemaName){
          location.href="editPunchoutSchema.do?schemaName="+schemaName;
        }

        function duplicateSchema(schemaName){
          location.href="duplicatePunchoutSchema.do?schemaName="+schemaName;
        }

        function setActiveSchema(schemaName){
          location.href="setActive.do?schemaName="+schemaName;
        }

      </script>

    </head>

    <body>
      <html:link action="applicationIndex.do">Back to main page</html:link>
      <table>
         <tr><td><h6>Punchout connection URI should be set to<br/>http://[hostname]:[port]/oci-test/punchoutTest.do<br/>for Punchout test using </h6></td></tr>
        <tr>
          <td><b>Search-Engine Punchout test</b></td>
        </tr>
        <c:forEach items="${schemas}" var="schema">
          <tr>
            <c:choose>
              <c:when test="${schema==activeSchema}">
                <td style="color:red"><c:out value="${schema}"/></td>
              </c:when>
              <c:otherwise>
                <td><c:out value="${schema}"/></td>
              </c:otherwise>
            </c:choose>
            <td><html:button value="Edit" property="" onclick="editSchema('${schema}')"/></td>
            <td><html:button value="Delete"  property ="" onclick="deleteSchema('${schema}')"/></td>
            <td><html:button value="Duplicate"  property ="" onclick="duplicateSchema('${schema}')"/></td>
            <td><html:button value="Set Active"  property ="" onclick="setActiveSchema('${schema}')"/></td>
          </tr>
        </c:forEach>
      </table>
    </body>

  </html>

</jsp:root>