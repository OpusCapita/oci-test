package com.jcatalog.ocisetest.webapp.actions;

import com.jcatalog.commons.web.struts.ComplexAction;

import com.jcatalog.ocisetest.properties.Properties;
import com.jcatalog.ocisetest.schemas.SchemaHolder;
import com.jcatalog.ocisetest.schemas.SchemaHolderFactory;

import org.apache.commons.beanutils.BeanUtils;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * DOCUMENT ME!
 *
 * @author Alexander Shulga
 */
public class PunchoutSchemaManagerAction extends ComplexAction {
    private SchemaHolderFactory schemaHolderFactory;

    public void setSchemaHolderFactory(SchemaHolderFactory schemaHolderFactory) {
        this.schemaHolderFactory = schemaHolderFactory;
    }

    public ActionForward editSchema(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) {
        String schema = (request.getParameter("schemaName") != null)
            ? request.getParameter("schemaName")
            : (String) request.getSession().getAttribute("schema");

        SchemaHolder schemaHolder = (SchemaHolder) schemaHolderFactory.getSchemaHolders()
                .get(schema);
        Properties properties = schemaHolder.getSchema();
        request.getSession().setAttribute("schema", schema);
        request.getSession().setAttribute("properties", properties);
        return mapping.findForward("success");
    }

    public ActionForward saveSchema(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        SchemaHolder schemaHolder = (SchemaHolder) schemaHolderFactory.getSchemaHolders()
                .get((String) request.getSession().getAttribute("schema"));
        Set keys = request.getParameterMap().keySet();
        Properties properties = schemaHolder.getSchema();
        for (Iterator i = properties.keySet().iterator(); i.hasNext();) {
            String key = (String) i.next();
            if (keys.contains(key)) {
                properties.setProperty(key, request.getParameter(key));
            }
        }
        schemaHolderFactory.saveSchema((String) request.getSession().getAttribute("schema"));
        return mapping.findForward("success");
    }

    public ActionForward deleteSchema(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) {
        schemaHolderFactory.deleteSchema(request.getParameter("schemaName"));
        return mapping.findForward("success");
    }

    public ActionForward duplicateSchema(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String newSchema = schemaHolderFactory.duplicateSchema((String) request
                    .getParameter("schemaName"));
        SchemaHolder schemaHolder = (SchemaHolder) schemaHolderFactory.getSchemaHolders()
                .get(newSchema);
        request.getSession().setAttribute("properties", schemaHolder.getSchema());
        request.getSession().setAttribute("schema", newSchema);
        return mapping.findForward("success");
    }

    public ActionForward addProperty(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        SchemaHolder schemaHolder = ((SchemaHolder) schemaHolderFactory.getSchemaHolders()
                .get(request.getSession().getAttribute("schema")));

        schemaHolder.getSchema().put(BeanUtils.getSimpleProperty(form, "name"),
            BeanUtils.getSimpleProperty(form, "value"));

        return mapping.findForward("success");
    }

    public ActionForward deleteProperty(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        SchemaHolder schemaHolder = ((SchemaHolder) schemaHolderFactory.getSchemaHolders()
                .get(request.getSession().getAttribute("schema")));
        schemaHolder.getSchema().remove(request.getParameter("property"));
        schemaHolderFactory.saveSchema((String) request.getSession().getAttribute("schema"));

        return mapping.findForward("success");
    }

    public ActionForward setActive(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        request.getSession().getServletContext().setAttribute("activeSchema",
            request.getParameter("schemaName"));

        return mapping.findForward("success");
    }
}
