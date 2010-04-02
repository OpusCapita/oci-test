package com.jcatalog.ocisetest.webapp.servlets;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

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

        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        doPost(request, response);
    }
}
