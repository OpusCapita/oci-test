package com.jcatalog.ocisetest.webapp.actions;

import com.jcatalog.ocisetest.properties.Properties;

import org.apache.commons.beanutils.BeanUtils;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Action for adding new property to properties stored in session
 *
 * @author Alexander Shulga
 */
public class AddAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ((Properties) request.getSession().getAttribute("properties")).put(BeanUtils
                .getSimpleProperty(form, "name"),
            BeanUtils.getSimpleProperty(form, "value"));

        return mapping.findForward("success");
    }
}
