package oci.test.grails

import com.jcatalog.ocisetest.properties.Properties
import com.jcatalog.ocisetest.properties.PropertiesHolder
import com.jcatalog.ocisetest.properties.PropertyUtils
import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.methods.EntityEnclosingMethod
import org.apache.commons.httpclient.methods.PostMethod

//todo refactor! separate additional controllers / services
class IndexController {
    def propertiesHolderFactory

    def index() {
        def actions = applicationContext.getBean('propertiesHolderFactory') as Map
        [actions: actions.keySet()]
    }

    def addPropertyPage() {}

    def addPropertyAction() {
        session.getAttribute('properties').putAt(params.name, params.value)
        render(view: 'route66', model: [propertiesMap: session.getAttribute('properties')])
    }

    def deletePropertyAction() {
        def propertiesHolders = session.getAttribute('properties') as Map
        propertiesHolders.remove(params.rowToRemove)
        session.removeAttribute('properties')
        session.setAttribute('properties', [:])
        propertiesHolders.each {
            (session.getAttribute('properties')[it.key as String] = (it.value as String))
        }
        render(view: 'route66', model: [propertiesMap: session.getAttribute('properties')])
    }

    def saveConfigurationAction() {
        def propertiesHolders = propertiesHolderFactory as Map
        PropertyUtils.updatePropertyValues(request)
        PropertiesHolder propertiesHolder = propertiesHolders.get(session.getAttribute("function")) as PropertiesHolder
        propertiesHolder.properties = session.getAttribute("properties") as Properties
        propertiesHolder.saveProperties()
        render(view: 'route66', model: [propertiesMap: propertiesHolder.properties, saveFlag: true])
    }

    def route66() {
        def propertiesHolders = propertiesHolderFactory as Map
        String action = params.actionlink as String
        PropertiesHolder propertiesHolder = propertiesHolders.get(action) as PropertiesHolder
        propertiesHolder.loadProperties()

        if (!propertiesHolder.properties.getProperty('HOOK_URL')) {
            propertiesHolder.properties.setProperty("HOOK_URL",
                    "http://" + request.getServerName() + ":" + request.getServerPort()
                            + request.getContextPath() + "/inbound")
        }

        session.setAttribute("function", action)
        session.setAttribute("properties", propertiesHolder.properties)
//        render 'hello oci'
        [propertiesMap: propertiesHolder.properties]
    }

    //todo doesn't work properly for now
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
