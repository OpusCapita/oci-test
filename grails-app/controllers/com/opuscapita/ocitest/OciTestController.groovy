package com.opuscapita.ocitest

import com.opuscapita.ocisetest.properties.PropertiesHolder
import com.opuscapita.ocisetest.properties.PropertyUtils
import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.methods.EntityEnclosingMethod
import org.apache.commons.httpclient.methods.PostMethod

class OciTestController {
    def propertiesHolderFactory
    def ibmOciRequestEncryptor

    def index() {
        def actions = applicationContext.getBean('propertiesHolderFactory') as Map
        [actions: actions.keySet()]
    }

    /**
     * Method determine a link on index page (search, retrieveoci, ociLogin, etc.) that user clicked on
     * and generate appropriate form to fill and submit.
     */
    def ociTestAction() {
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
        [propertiesMap: propertiesHolder.properties]
    }

    def doIt() {
        Map ociRequestParams = params?.useEncryption == 'on' ?
                ibmOciRequestEncryptor.encrypt(params) :
                params
        /*
        '_action_ + ${method_name}! do not change string below w/o renaming method and vice versa
        For some reason there is an incorrect behavior with this parameter in form; necessarily to remove it from params
         */
        ociRequestParams.remove('_action_doIt')
        [ociRequestParams: ociRequestParams]
    }

    def addProperty() {
        session.getAttribute('properties').putAt(params.name, params.value)
        render(view: 'ociTestAction', model: [propertiesMap: session.getAttribute('properties')])
    }

    def deleteProperty() {
        def propertiesHolders = session.getAttribute('properties') as Map
        propertiesHolders.remove(params.rowToRemove)
        session.removeAttribute('properties')
        session.setAttribute('properties', [:])
        propertiesHolders.each {
            (session.getAttribute('properties')[it.key as String] = (it.value as String))
        }
        render(view: 'ociTestAction', model: [propertiesMap: session.getAttribute('properties')])
    }

    def saveConfiguration() {
        def propertiesHolders = propertiesHolderFactory as Map
        PropertiesHolder propertiesHolder = propertiesHolders.get(session.getAttribute("function")) as PropertiesHolder
        propertiesHolder.properties = PropertyUtils.updatePropertyValues(request)
        propertiesHolder.saveProperties()
        render(view: 'ociTestAction', model: [propertiesMap: propertiesHolder.properties, saveFlag: true])
    }

    def handleTester() {
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

    def showAddProperty() {
        render(view: 'addProperty')
    }

    def showAribaTester() {
        render(view: 'aribaTester')
    }

    def showPunchoutTester() {
        render(view: 'punchoutTester')
    }
}