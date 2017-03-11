package com.jaasexample;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import javax.inject.Inject;

/**
 * This UI is the application entry point. A UI may either represent a browser window (or tab) or some part of
 * a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be overridden to add
 * component to the user interface and initialize non-component functionality.
 */
@CDIUI("")
@Theme("jaasexampletheme")
public class JaasExampleUI extends UI {

    @Inject
    private CDIViewProvider viewProvider;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout contentArea = new VerticalLayout();
        contentArea.setMargin(false);
        setContent(contentArea);

        final Navigator navigator = new Navigator(this, contentArea);
        navigator.addProvider(viewProvider);
        navigator.setErrorView(UnauthorizedErrorView.class);

        if (isUserAuthenticated(vaadinRequest)) {
            navigator.navigateTo(SecureView.VIEW_NAME);
        } else {
            navigator.navigateTo(LoginView.VIEW_NAME + "/" + SecureView.VIEW_NAME);
        }
    }

    private boolean isUserAuthenticated(final VaadinRequest vaadinRequest) {
        return vaadinRequest.getUserPrincipal() != null;
    }

}
