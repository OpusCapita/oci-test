package com.jcatalog.ocisetest.webapp.actions;

import com.jcatalog.ocisetest.properties.PropertyUtils;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Shows page for adding new property
 *
 * @author Alexander Shulga
 */
public class ShowAddingPageAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        PropertyUtils.updatePropertyValues(request);
        return mapping.findForward("success");
    }
}
