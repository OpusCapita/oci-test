<?xml version="1.0" encoding="UTF-8"?>

<jsp:root version="1.2"
  xmlns:jsp="http://java.sun.com/JSP/Page"
  xmlns:c="http://java.sun.com/jstl/core">

  <jsp:directive.page contentType="text/html;charset=UTF-8"/>
  <jsp:directive.page import="java.util.PropertyResourceBundle"/>
  
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
     function encode64(input) {
      var output = "";
      var chr1, chr2, chr3 = "";
      var enc1, enc2, enc3, enc4 = "";
      var i = 0;

      do {
         chr1 = input.charCodeAt(i++);
         chr2 = input.charCodeAt(i++);
         chr3 = input.charCodeAt(i++);

         enc1 = chr1 >> 2;
         enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
         enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
         enc4 = chr3 & 63;

         if (isNaN(chr2)) {
            enc3 = enc4 = 64;
         } else if (isNaN(chr3)) {
            enc4 = 64;
         }

         output = output +
            keyStr.charAt(enc1) +
            keyStr.charAt(enc2) +
            keyStr.charAt(enc3) +
            keyStr.charAt(enc4);
         chr1 = chr2 = chr3 = "";
         enc1 = enc2 = enc3 = enc4 = "";
      } while (i < input.length);

      return output;
    }

    function prepareFormData() {
      var tmpStr = new String(document.forms[0].ASSET_SOURCE.value);
      if (tmpStr.length > 0){
        document.forms[0].ASSET.value = encode64(tmpStr);
      } else {
        document.forms[0].ASSET.value = "";
      }
      document.forms[0].action = document.forms[0].catalog_url.value;
      return true;
    }
    ]]>
   </script>


  </head>
  <body>
  
  	 <jsp:scriptlet>
      
      String url = "";

      try {

        PropertyResourceBundle propertyBoundle =
          new PropertyResourceBundle(this.getServletConfig().getServletContext().getResourceAsStream("project.properties"));
        
          if (!propertyBoundle.getString("select.fast.url").equals("")) url = propertyBoundle.getString("select.fast.url");
        }
        catch (Exception e){
          url="";
        }
        
      request.setAttribute("url", url);
    </jsp:scriptlet>

    <form method="post">
     <fieldset>
          <input type="hidden" name="ASSET"/>
          <legend>
            In asset don't use non-Latin symbols!!!
          </legend>
          <label>
            Catalog url:
          </label>
          <![CDATA[<input name="catalog_url" size="40" value="]]><c:out value="${url}"/><![CDATA["/>]]>
          <br/>
          <label>
            HOOK_URL:
          </label>
          <![CDATA[<input name="HOOK_URL" size="40" value="]]><jsp:expression>"http://" + request.getServerName() + ":" + request.getServerPort() + "/oci-test/select-fast/inbound.jsp"</jsp:expression><![CDATA["/><br/>]]>
          <label>
            ~OkCode:
          </label>
          <input name="~OkCode" value="test" size="40"/><br/>
          <label>
            ~TARGET:
          </label>
          <input name="~TARGET" value="" size="40"/><br/>
          <label>
            ~CALLER:
          </label>
          <input name="~CALLER" value="test" size="40"/><br/>
          <label>
            USERNAME:
          </label>
          <input name="USERNAME" value="jcadmin" size="40"/><br/>
          <label>
            PASSWORD:
          </label>
          <input name="PASSWORD" value="jcadmin" size="40"/><br/>
          <label>
            LANGUAGE:
          </label>
          <input name="LANGUAGE" value="en" size="40"/><br/>
          <label>
            ASSET:
          </label>
          <![CDATA[<textarea name="ASSET_SOURCE" rows="10" cols="80" value=""></textarea>]]>
          <br/>
          <label>&amp;nbsp;</label>
          <input type="submit" onClick="return prepareFormData();"/>
     </fieldset>
    </form>
  </body>
  </html>
</jsp:root>
