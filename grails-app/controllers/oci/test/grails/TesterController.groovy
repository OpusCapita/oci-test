package oci.test.grails

import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.methods.EntityEnclosingMethod
import org.apache.commons.httpclient.methods.PostMethod

class TesterController {

    // handle form submission from ariba tester and punchout tester
    def handleTesterAction() {
        String cxmltosend = request.getParameter("xmltosend")
        String urltotest = request.getParameter("urltotest")

        PostMethod post = new PostMethod(urltotest)
        post.setRequestBody(new ByteArrayInputStream(cxmltosend.getBytes()))

        cxmltosend.length() < Integer.MAX_VALUE ?
                post.setRequestContentLength(cxmltosend.length()) :
                post.setRequestContentLength(EntityEnclosingMethod.CONTENT_LENGTH_CHUNKED)

        post.setRequestHeader("Content-type", "text/xml; charset=ISO-8859-1")
        HttpClient httpclient = new HttpClient()
        int result = httpclient.executeMethod(post)
        String strXML = post.getResponseBodyAsString()
        post.releaseConnection()
        render(text: strXML, contentType: "text/xml", encoding: "UTF-8")
    }

    def aribaTesterPage() {}

    def punchoutTesterPage() {}
}
