<?xml version="1.0" encoding="UTF-8"?>

<jsp:root version="1.2"
  xmlns:jsp="http://java.sun.com/JSP/Page"
  xmlns:c="http://java.sun.com/jstl/core">

  <jsp:scriptlet>
    request.setCharacterEncoding("UTF-8");
  </jsp:scriptlet>
  <jsp:directive.page contentType="text/html;charset=UTF-8"/>
  <html>
  <head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8"/>
    <link href="../css/default.css" type="text/css" rel="stylesheet"/>
    <script language="JavaScript1.2">
    <![CDATA[
     var keyStr = "ABCDEFGHIJKLMNOP" +
      "QRSTUVWXYZabcdef" +
      "ghijklmnopqrstuv" +
      "wxyz0123456789+/" +
      "=";
    function decode64(input) {
      var output = "";
      var chr1, chr2, chr3 = "";
      var enc1, enc2, enc3, enc4 = "";
      var i = 0;

      // remove all characters that are not A-Z, a-z, 0-9, +, /, or =
      input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

      do {
         enc1 = keyStr.indexOf(input.charAt(i++));
         enc2 = keyStr.indexOf(input.charAt(i++));
         enc3 = keyStr.indexOf(input.charAt(i++));
         enc4 = keyStr.indexOf(input.charAt(i++));

         chr1 = (enc1 << 2) | (enc2 >> 4);
         chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
         chr3 = ((enc3 & 3) << 6) | enc4;

         output = output + String.fromCharCode(chr1);

         if (enc3 != 64) {
            output = output + String.fromCharCode(chr2);
         }
         if (enc4 != 64) {
            output = output + String.fromCharCode(chr3);
         }

         chr1 = chr2 = chr3 = "";
         enc1 = enc2 = enc3 = enc4 = "";

      } while (i < input.length);

      return output;
   }
   ]]>
   </script>
  </head>
  <body>
    <c:if test="${not empty paramValues}" >
    <form>
    <table width="100%">
    <c:forEach items="${paramValues}" var="parameter">
      <tr>
      <td>
      <label><c:out value="${parameter.key}"/><![CDATA[&nbsp;:&nbsp;]]></label>
      </td>
      <td>
      <c:forEach items="${parameter.value}" var="parameterValue" varStatus="status">
        <c:if test="${status.index ne 0}">;</c:if>
        <c:choose>
        <c:when test="${parameter.key eq 'NEW_ITEM-LONGTEXT_1:132[]'}">
          <textarea name="xmltext1" cols="70" rows="20">
            <c:out value="${parameterValue}"/>
          </textarea>
          <br/>
          <jsp:scriptlet>String param = request.getParameter("NEW_ITEM-LONGTEXT_1:132[]");</jsp:scriptlet>
          <textarea name="xmltext" cols="70" rows="20">
            <jsp:expression>new String(org.apache.commons.codec.binary.Base64.decodeBase64(param.getBytes("UTF-8")), "UTF-8")</jsp:expression>
          </textarea>
        </c:when>
        <c:otherwise>
          <c:out value="${parameterValue}"/>
        </c:otherwise>
        </c:choose>
      </c:forEach>
      </td>
      </tr>
    </c:forEach>
    </table>
    </form>
    <script>
//      document.forms[0].xmltext.value = decode64(document.forms[0].xmltext1.value);
    </script>
    </c:if>
  </body>
  </html>
</jsp:root>
