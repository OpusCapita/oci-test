<%--
  Created by IntelliJ IDEA.
  User: logich
  Date: 9/12/2019
  Time: 6:00 PM
--%>

<%@ page import="org.apache.commons.lang.StringEscapeUtils" contentType="text/html;charset=UTF-8" %>
<html>
<body>
<%!
    String replace(String where, String what, String to) {
        for (int i = where.indexOf(what); i != -1;
             i = where.indexOf(what, i + to.length())) {
            where =
                    where.substring(0, i) + to + where.substring(i + what.length())
        }

        return where
    }


%>

<%
    String result = request.getParameter("cxml-urlencoded") as String

    result = StringEscapeUtils.escapeXml(result)

    println("<pre>")
    println(result)
    println("</pre>")

%>
</body>
</html>