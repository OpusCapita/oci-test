<?xml version="1.0" encoding="UTF-8"?>

<jsp:root version="1.2"
  xmlns:jsp="http://java.sun.com/JSP/Page"
  xmlns:c="http://java.sun.com/jstl/core">

  <jsp:directive.page contentType="text/html;charset=UTF-8"/>
  <jsp:directive.page import="java.util.PropertyResourceBundle"/>

  <html>
  <head>
    <link href="../css/default.css" type="text/css" rel="stylesheet"/>
    <script language="javascript">

    function prepareFormData(functionName) {
      document.forms[0].action = document.forms[0].catalog_url.value;
      if (document.forms[0].targetFrame.value.length > 0) {
        document.forms[0].target = document.forms[0].targetFrame.value;
      } else {
        document.forms[0].target = "_self";
      }
      document.forms[0].FUNCTION.value = functionName;
      return true;
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
    <form method="post">
      <fieldset>
        <legend>
            Fill the form
        </legend>
        <input type="hidden" name="cmd" value="OCILogin"/>
        <input type="hidden" name="FUNCTION" value=""/>
        <label>Catalog url:</label><![CDATA[<input name="catalog_url" size="40" value="]]><c:out value="${url}"/><![CDATA["/>]]><br/>
        <label>Target frame:</label><![CDATA[<input name="targetFrame" size="40" value=""/>]]><br/>

        <label>HOOK_URL:</label><![CDATA[<input name="HOOK_URL" size="40" value="]]><jsp:expression>"http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/search-engine/inbound.jsp"</jsp:expression><![CDATA["/><br/>]]>
        <label>~OkCode:</label><input name="~OkCode" value="test" size="40"/><br/>
        <label>~TARGET:</label><input name="~TARGET" value="" size="40"/><br/>
        <label>~CALLER:</label><input name="~CALLER" value="test" size="40"/><br/>
        <label>login:</label><input name="login" value="jcadmin" size="40"/><br/>
        <label>password:</label><input name="password" value="jcadmin" size="40"/><br/>
        <label>language:</label><input name="language" value="en" size="40"/><br/>
        <label>callerID:</label><input name="callerID" value="SAP" size="40"/><br/>
        <label>OCI_VERSION:</label><input name="OCI_VERSION" value="3.0" size="40"/><br/>
        <label>UserRole:</label><input name="UserRole" value="" size="40"/><br/>
        <label>CurrentItemCount:</label><input name="CurrentItemCount" value="" size="40"/><br/>
        <label>PRODUCTDESC:</label><input name="PRODUCTDESC" value="" size="40"/><br/>
        <label>ESN:</label><input name="ESN" value="" size="40"/><br/>
        <label>SUPPLIER:</label><input name="SUPPLIER" value="" size="40"/><br/>
        <label>VENDORMAT:</label><input name="VENDORMAT" value="" size="40"/><br/>
        <label>tab:</label><input name="tab" value="quickSearch" size="40"/>(quickSearch/advancedSearch)<br/>
        <label>PRODUCTID[0]:</label><input name="PRODUCTID[0]" value="13" size="40"/><br/>
        <label>ITEM_QUANTITY[0]:</label><input name="ITEM_QUANTITY[0]" value="1" size="40"/><br/>
        <label>NEW_ITEM-LONGTEXT_0:132[]:</label><input name="NEW_ITEM-LONGTEXT_0:132[]" value="" size="40"/><br/>
        <label>PRODUCTID[1]:</label><input name="PRODUCTID[1]" value="14" size="40"/><br/>
        <label>ITEM_QUANTITY[1]:</label><input name="ITEM_QUANTITY[1]" value="1" size="40"/><br/>
        <label>NEW_ITEM-LONGTEXT_1:132[]:</label><input name="NEW_ITEM-LONGTEXT_1:132[]" value="" size="40"/><br/>
        <label>&amp;nbsp;</label><input type="submit" value="connect" onClick="return prepareFormData('');"/>
        <input type="submit" value="details" onClick="return prepareFormData('DETAIL');"/>
        <input type="submit" value="createCart" onClick="return prepareFormData('CREATECART');"/>
        <input type="submit" value="OCILogin" onClick="return prepareFormData('OCILogin');"/>
        </fieldset>
    </form>
  </body>
  </html>
</jsp:root>
