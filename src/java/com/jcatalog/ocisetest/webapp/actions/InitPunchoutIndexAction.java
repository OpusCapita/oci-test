package com.jcatalog.ocisetest.webapp.actions;

import com.jcatalog.ocisetest.schemas.SchemaHolderFactory;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * DOCUMENT ME!
 *
 * @author Alexander Shulga
 */
public class InitPunchoutIndexAction extends Action {
    private SchemaHolderFactory schemaHolderFactory;

    public void setSchemaHolderFactory(SchemaHolderFactory schemaHolderFactory) {
        this.schemaHolderFactory = schemaHolderFactory;
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        if (request.getSession().getServletContext().getAttribute("activeSchema") == null) {
            request.getSession().getServletContext().setAttribute("activeSchema",
                schemaHolderFactory.getSchemaHolders().keySet().iterator().next());
        }
        request.setAttribute("schemas", schemaHolderFactory.getSchemaHolders().keySet());
        return mapping.findForward("success");
    }
}
