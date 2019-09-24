package oci.test.grails

import com.jcatalog.ocisetest.properties.PropertiesHolder
import com.jcatalog.ocisetest.properties.PropertyUtils

class IndexController {
    def propertiesHolderFactory
    def ibmOciRequestEncrypter
    def indexService

    // index page with all available functions (action) to do
    def index() {
        def actions = applicationContext.getBean('propertiesHolderFactory') as Map
        [actions: actions.keySet()]
    }

    /**
     * Method determine a link on index page (search, retrieveoci, ociLogin, etc.) that user clicked on
     * and generate appropriate form to fill and submit.
     */
    def anyActionPage() {
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

    // handle 'do it' button
    def doIt() {
        Map ociRequestParams = params?.useEncryption == 'on' ?
                ibmOciRequestEncrypter.encrypt(indexService.getRequestParams(request)) :
                indexService.getRequestParams(request)
        /*
        '_action_ + ${method_name}! do not change string below w/o renaming method and vice versa
        For some reason there is an incorrect behavior with this parameter in form; necessarily to remove it from params
         */
        ociRequestParams.remove('_action_doIt')
        request.setAttribute("ociRequestParams", ociRequestParams)
        [ociRequestParams: ociRequestParams]
    }

    // method just to show 'add property' page
    def addPropertyPage() {}

    // action to handle new property submitting
    def addPropertyAction() {
        session.getAttribute('properties').putAt(params.name, params.value)
        render(view: 'anyActionPage', model: [propertiesMap: session.getAttribute('properties')])
    }

    // action to handle deleting existing property
    def deletePropertyAction() {
        def propertiesHolders = session.getAttribute('properties') as Map
        propertiesHolders.remove(params.rowToRemove)
        session.removeAttribute('properties')
        session.setAttribute('properties', [:])
        propertiesHolders.each {
            (session.getAttribute('properties')[it.key as String] = (it.value as String))
        }
        render(view: 'anyActionPage', model: [propertiesMap: session.getAttribute('properties')])
    }

    // action to handle configuration saving
    def saveConfigurationAction() {
        def propertiesHolders = propertiesHolderFactory as Map
        PropertiesHolder propertiesHolder = propertiesHolders.get(session.getAttribute("function")) as PropertiesHolder
        propertiesHolder.properties = PropertyUtils.updatePropertyValues(request)
        propertiesHolder.saveProperties()
        render(view: 'anyActionPage', model: [propertiesMap: propertiesHolder.properties, saveFlag: true])
    }
}