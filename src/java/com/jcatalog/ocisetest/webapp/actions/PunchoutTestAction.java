package com.jcatalog.ocisetest.webapp.actions;

import com.jcatalog.commons.web.struts.ComplexAction;

import com.jcatalog.ocisetest.properties.Properties;
import com.jcatalog.ocisetest.schemas.ItemStorage;
import com.jcatalog.ocisetest.schemas.ItemStorageManager;
import com.jcatalog.ocisetest.schemas.SchemaHolder;
import com.jcatalog.ocisetest.schemas.SchemaHolderFactory;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * DOCUMENT ME!
 *
 * @author Alexander Shulga
 */
public class PunchoutTestAction extends ComplexAction {
    private ItemStorageManager itemStorageManager = null;
    private SchemaHolderFactory schemaHolderFactory;

    public void setSchemaHolderFactory(SchemaHolderFactory schemaHolderFactory) {
        this.schemaHolderFactory = schemaHolderFactory;
    }

    public void setItemStorageManager(ItemStorageManager itemStorageManager) {
        this.itemStorageManager = itemStorageManager;
    }

    public ActionForward punchoutTest(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String schema = (String) request.getSession().getServletContext().getAttribute("activeSchema");
        SchemaHolder schemaHolder = (SchemaHolder) schemaHolderFactory.getSchemaHolders()
                .get(schema);
        if (schemaHolder == null) {
            schemaHolder = (SchemaHolder) schemaHolderFactory.getSchemaHolders().get(schemaHolderFactory.getSchemaHolders()
                        .keySet().iterator().next());
        }

        ItemStorage itemStorage = itemStorageManager.createStorage(schemaHolder);
        request.getSession().setAttribute("HOOK_URL", request.getParameter("HOOK_URL"));
        request.getSession().setAttribute("itemStorage", itemStorage);
        return mapping.findForward("success");
    }

    public ActionForward copyItem(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        saveItem(mapping, form, request, response);
        itemStorageManager.copyItem((ItemStorage) request.getSession().getAttribute("itemStorage"),
            Integer.parseInt(request.getParameter("number")));
        return mapping.findForward("success");
    }

    public ActionForward deleteItem(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        itemStorageManager.deleteItem((ItemStorage) request.getSession().getAttribute("itemStorage"),
            Integer.parseInt(request.getParameter("number")));
        return mapping.findForward("success");
    }

    public ActionForward saveItem(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        Properties properties = (Properties) ((ItemStorage) request.getSession()
                .getAttribute("itemStorage")).getItems().get(Integer.parseInt(
                    request.getParameter("number")));

        for (Iterator i = properties.keySet().iterator(); i.hasNext();) {
            String key = (String) i.next();
            if (request.getParameterMap().containsKey(key)) {
                properties.setProperty(key, request.getParameter(key));
            }
        }
        return mapping.findForward("success");
    }

    public ActionForward showInboundData(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ItemStorage itemStorage = (ItemStorage) request.getSession().getAttribute("itemStorage");
        request.setAttribute("items", itemStorageManager.getIndexedItems(itemStorage));
        return mapping.findForward("success");
    }
}
