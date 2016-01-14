<html>
<body>
<%!
   public  String replace(String where, String what, String to) {
        for (int i = where.indexOf(what); i != -1;
                i = where.indexOf(what, i + to.length())) {
            where =
                where.substring(0, i) + to + where.substring(i + what.length());
        }

        return where;
    }


%>

<%
String result = (String)request.getParameter("cxml-urlencoded");

result = org.apache.commons.lang.StringEscapeUtils.escapeXml(result);

out.println("<pre>");
out.println(result);
out.println("</pre>");

%>
</body>
</html>

  










