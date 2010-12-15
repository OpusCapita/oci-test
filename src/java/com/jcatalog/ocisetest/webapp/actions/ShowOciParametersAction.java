package com.jcatalog.ocisetest.webapp.actions;

import com.jcatalog.ocisetest.properties.PropertiesHolder;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Action for oci properties retrieving
 *
 * @author Alexander Shulga
 */
public class ShowOciParametersAction extends Action {
    private Map propertiesHolders = null;

    public void setPropertiesHolders(Map propertiesHolders) {
        this.propertiesHolders = propertiesHolders;
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        PropertiesHolder propertiesHolder = (PropertiesHolder) propertiesHolders.get(request
                    .getParameter("function"));
        propertiesHolder.loadProperties();

        if (StringUtils.isEmpty(propertiesHolder.getProperties().getProperty("HOOK_URL"))) {
            propertiesHolder.getProperties().setProperty("HOOK_URL",
                "http://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath() + "/inbound");
        }

        request.getSession().setAttribute("function", request.getParameter("function"));

        request.getSession().setAttribute("properties", propertiesHolder.getProperties());
        return mapping.findForward("success");
    }
}
