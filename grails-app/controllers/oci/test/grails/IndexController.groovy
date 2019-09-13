package oci.test.grails

import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.methods.EntityEnclosingMethod
import org.apache.commons.httpclient.methods.PostMethod

class IndexController {

    def index() {}

    def createSetupRequest() {}

    def punchoutSetupTester() {
        String cxmltosend = request.getParameter("cxmltosend") //todo xml vs cxml
        String urltotest = request.getParameter("urltotest")

        PostMethod post = new PostMethod(urltotest)
        post.setRequestBody(new ByteArrayInputStream(cxmltosend.getBytes()))
        if (cxmltosend.length() < Integer.MAX_VALUE) {
            post.setRequestContentLength(cxmltosend.length())
        } else {
            post.setRequestContentLength(EntityEnclosingMethod.CONTENT_LENGTH_CHUNKED)
        }

        post.setRequestHeader("Content-type", "text/xml; charset=ISO-8859-1")
        HttpClient httpclient = new HttpClient()
        int result = httpclient.executeMethod(post)
        String strXML = post.getResponseBodyAsString()
        render(strXML)
        post.releaseConnection()
    }
}
