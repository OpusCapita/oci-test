package com.jcatalog.ocisetest.properties;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;


/**
 * Updates properties stored in session
 *
 * @author Alexander Shulga
 */
public class PropertyUtils {
    public static void updatePropertyValues(HttpServletRequest request) {
        Set keys = request.getParameterMap().keySet();
        Properties properties = ((Properties) request.getSession().getAttribute("properties"));

        for (Iterator i = properties.keySet().iterator(); i.hasNext();) {
            String key = (String) i.next();
            if (keys.contains(key)) {
                properties.setProperty(key, request.getParameter(key));
            }
        }
    }
}
