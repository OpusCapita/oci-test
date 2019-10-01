package com.opuscapita.ocitest

import com.opuscapita.ocisetest.comparator.InboundComparator
import com.opuscapita.ocisetest.properties.PropertiesHolder
import com.opuscapita.ocisetest.properties.PropertyUtils
import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.methods.InputStreamRequestEntity
import org.apache.commons.httpclient.methods.PostMethod
import org.apache.commons.lang.StringUtils

class OciTestController {
    def propertiesHolderFactory
    def ociRequestEncryptor

    def beforeInterceptor = {
        params.remove("_action_${actionName}".toString())
    }

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
                ociRequestEncryptor.encrypt(params) :
                params
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
        post.setRequestEntity(new InputStreamRequestEntity(new ByteArrayInputStream(cxmltosend.getBytes())))
        cxmltosend.length() < Integer.MAX_VALUE ? post.setContentChunked(false) : post.setContentChunked(true)
        post.setRequestHeader("Content-type", "text/xml; charset=ISO-8859-1")
        HttpClient httpclient = new HttpClient()
        int responseCode = 0
        try {
            responseCode = httpclient.executeMethod(post)
            String strXML = post.getResponseBodyAsString()
            render(text: strXML, contentType: "text/xml", encoding: "UTF-8")
        } catch (e) {
            (responseCode in 403..404 || e.getClass() == UnknownHostException) ?
                    render(text: 'Please check URL you have entered.') :
                    render(text: 'Something went wrong, please try again later.')
            e.printStackTrace()
        } finally {
            post.releaseConnection()
        }
    }

    def inbound() {
        //parsing secret key from HOOK_URL (TEST functionality only)
        String secretKey = StringUtils.substringAfterLast(request.getRequestURI(), "inbound/")

        if (StringUtils.isNotBlank(secretKey)) {
            log.info("Secret key: '" + secretKey + "'")
            request.setAttribute("secretKey", secretKey)
        }

        Map<String, String[]> inboundParameters = [:]
        SortedSet<String> keys = new TreeSet<String>(InboundComparator.comparator)
        keys.addAll(params.keySet())
        for (String key : keys) {
            inboundParameters.put(key, request.getParameterMap().get(key) as String[])
        }
        request.setAttribute("inboundParametersMap", inboundParameters)

        [inboundParametersMap: inboundParameters]
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