package com.jcatalog.ocisetest.webapp.servlets;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.SortedSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Additional preprocessing of OCI inbound
 *
 * @author Alexander Shulga
 */
public class OciInboundServlet extends HttpServlet {
    private static final Log log = LogFactory.getLog(OciInboundServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        //parsing secret key from HOOK_URL (TEST functionality only)
        String secretKey = StringUtils.substringAfterLast(request.getRequestURI(),
                "inbound/");

        if (StringUtils.isNotBlank(secretKey)) {
            log.info("Secrect key: '" + secretKey + "'");
            request.setAttribute("secretKey", secretKey);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/inbound.do");

        Comparator<String> comparator = new Comparator<String>() {
            public int compare(String str1, String str2) {
                int itemPositionFirstString = 0;
                int itemPositionSecondString = 0;
                Matcher matcher = Pattern.compile("\\[([^]])|_([\\d])").matcher(str1);
                if (matcher.find()) {
                    try {
                        itemPositionFirstString = Integer.parseInt(matcher.group(1) != null ? matcher.group(1) : matcher.group(2));

                    } catch (NumberFormatException e) {
                        log.info("NumberFormatException has occured, no item index was found");
                        return str1.compareTo(str2);
                    }
                }
                matcher = Pattern.compile("\\[([^]])|_([\\d])").matcher(str2);
                if (matcher.find()) {
                    try {
                        itemPositionSecondString = Integer.parseInt(matcher.group(1) != null ? matcher.group(1) : matcher.group(2));
                    } catch (NumberFormatException e) {
                        log.info("NumberFormatException has occured, no item index was found");
                        return str1.compareTo(str2);
                    }
                }
                if (itemPositionFirstString > itemPositionSecondString) {
                    return 1;
                } else if (itemPositionFirstString == itemPositionSecondString) {
                    return str1.compareTo(str2);
                } else {
                    return -1;
                }
            }
        };

        Map<String, String[]> sortedParameters = new LinkedHashMap<String, String[]>();
        SortedSet<String> keys = new TreeSet<String>(comparator);
        keys.addAll(request.getParameterMap().keySet());
        for (String key : keys) {
            sortedParameters.put(key, (String[]) request.getParameterMap().get(key));
        }
        request.setAttribute("sortedParametersMap", sortedParameters);
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        doPost(request, response);
    }
}
