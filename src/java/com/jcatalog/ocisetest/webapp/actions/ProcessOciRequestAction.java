package com.jcatalog.ocisetest.webapp.actions;

import com.jcatalog.ocisetest.security.OciRequestEncrypter;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Action for oci properties retrieving
 *
 * @author Alexander Shulga
 */
public class ProcessOciRequestAction extends Action {
    private OciRequestEncrypter ociParametersEncrypter;

    public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        Map ociRequestParams = (request.getParameter("useEncryption") != null)
            ? ociParametersEncrypter.encrypt(getRequestParams(request))
            : getRequestParams(request);

        request.setAttribute("ociRequestParams", ociRequestParams);

        return mapping.findForward("success");
    }

    private Map getRequestParams(HttpServletRequest request) {
        Map params = new HashMap();
        Enumeration names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            params.put(name, request.getParameter(name));
        }
        return params;
    }

    public void setOciParametersEncrypter(OciRequestEncrypter ociParametersEncrypter) {
        this.ociParametersEncrypter = ociParametersEncrypter;
    }
}
