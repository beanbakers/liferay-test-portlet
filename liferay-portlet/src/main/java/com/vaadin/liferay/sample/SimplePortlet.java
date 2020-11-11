package com.vaadin.liferay.sample;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.portlet.GenericPortlet;
import javax.portlet.HeaderRequest;
import javax.portlet.HeaderResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * Dummy portlet
 */
public class SimplePortlet extends GenericPortlet 
{
    private AtomicBoolean isPortlet3 = new AtomicBoolean();

    @Override
    public void renderHeaders(HeaderRequest request, HeaderResponse response)
            throws PortletException, IOException {
        // This is only called for portlet 3.0 portlets.
        // NOTE: Liferay 7.3.x seems to not call this for VaadinPortlets
        isPortlet3.set(true);
        response.addDependency("PortletHub", "javax.portlet", "3.0.0");

        // How do we get this to only exec once for multiple portlets on same
        // page, but still every time the page refreshes?
        String initScript = "<script type=\"text/javascript\">window.testObj = window.testObj || {};</script>";

        if (response.getContentType() == null)
            response.setContentType("text/html");

        response.getWriter().println(initScript);
    }

    @Override
    protected void doDispatch(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        super.doDispatch(request, response);

        response.setContentType("text/html");
        response.getWriter().println("Hello World!" + (isPortlet3.get() ? " - Portlet 3.0" : ""));
    }

    @Override
    protected void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
    }
}
