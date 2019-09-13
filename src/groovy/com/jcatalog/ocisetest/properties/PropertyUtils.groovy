package com.jcatalog.ocisetest.properties

import javax.servlet.http.HttpServletRequest

/**
 * Updates properties stored in session
 */
class PropertyUtils {
    static def updatePropertyValues(HttpServletRequest request) {
        Set keys = request.getParameterMap().keySet()
        Properties properties = request.getSession().getAttribute('properties') as Properties
//        for (Iterator i = properties.keySet().iterator(); i.hasNext();) {
        properties.keySet().each {
            def key = it as String
            if (keys.contains(key)) {
                properties.setProperty(key, request.getParameter(key))
            }
        }
    }
}
