package oci.test.grails

import javax.servlet.http.HttpServletRequest

class IndexService {
    static transactional = false

    Map getRequestParams(HttpServletRequest request) {
        Map params = [:]
        request.getParameterNames().each {
            params[it] = request.getParameter(it)
        }
        return params
    }
}
