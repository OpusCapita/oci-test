<%
  String cxmltosend = request.getParameter("cxmltosend");
  String urltotest = request.getParameter("urltotest");

  PostMethod post = new PostMethod(urltotest);
  post.setRequestBody(new ByteArrayInputStream(cxmltosend.getBytes()));
 //  out.println(cxmltosend);
    if (cxmltosend.length() < Integer.MAX_VALUE) {
              post.setRequestContentLength(cxmltosend.length());
          } else {
              post.setRequestContentLength(EntityEnclosingMethod.CONTENT_LENGTH_CHUNKED);
          }

    post.setRequestHeader("Content-type", "text/xml; charset=ISO-8859-1");
    HttpClient httpclient = new HttpClient();
    int result = httpclient.executeMethod(post);
    String strXML = post.getResponseBodyAsString();
    out.println(strXML);
    post.releaseConnection();
/*    out.println("pre");
    out.println(strXML);
    out.println("/pre");  */
%>
<%@ page import="java.net.URL, java.net.HttpURLConnection, org.apache.commons.httpclient.HttpClient, org.apache.commons.httpclient.methods.PostMethod, java.io.ByteArrayInputStream, org.apache.commons.httpclient.methods.EntityEnclosingMethod"%>
<%@ page import="java.net.URLConnection"%>
<%@ page contentType="text/xml"%>
