package com.jcatalog.ocisetest.webapp.actions;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Action used to initialize the list of oci functions
 *
 * @author Alexander Shulga
 */
public class InitAction extends Action {
    private Map propertiesHolders;

    public void setPropertiesHolders(Map propertiesHolders) {
        this.propertiesHolders = propertiesHolders;
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        request.setAttribute("actions", propertiesHolders.keySet());
        return mapping.findForward("success");
    }
}
