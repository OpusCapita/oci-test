<?xml version="1.0" encoding="UTF-8"?>

<jsp:root version="1.2"
  xmlns:jsp="http://java.sun.com/JSP/Page"
  xmlns:html="http://jakarta.apache.org/struts/tags-html-el">

  <jsp:directive.page contentType="text/html;charset=UTF-8"/>
  <html>
    <frameset cols="40%,60%" bordercolor="red" border="2" frameborder="2" framespacing="2">
    <html:frame frameName="mainStartFrame" href="outbound.jsp" scrolling="auto"/>
    <![CDATA[<frame name="targetFrame" border="1" src="about:blank" scrolling="auto"/>]]>
    </frameset>
  </html>
</jsp:root>
