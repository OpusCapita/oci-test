package com.jcatalog.ocisetest.webapp.actions;

import com.jcatalog.ocisetest.properties.Properties;
import com.jcatalog.ocisetest.properties.PropertiesHolder;
import com.jcatalog.ocisetest.properties.PropertyUtils;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Action for saving user configuration
 *
 * @author Alexander Shulga
 */
public class SaveOciConfigurationAction extends Action {
    private Map propertiesHolders;

    public void setPropertiesHolders(Map propertiesHolders) {
        this.propertiesHolders = propertiesHolders;
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        PropertyUtils.updatePropertyValues(request);
        PropertiesHolder propertiesHolder = (PropertiesHolder) propertiesHolders.get(request.getSession()
                    .getAttribute("function"));
        propertiesHolder.setProperties((Properties) request.getSession().getAttribute("properties"));
        propertiesHolder.saveProperties();
        request.setAttribute("saveFlag", Boolean.TRUE);
        return mapping.findForward("success");
    }
}
