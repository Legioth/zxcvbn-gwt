package org.vaadin.leif.zxcvbn.demo.client;

import org.vaadin.leif.zxcvbn.client.TextBoxInterceptor;
import org.vaadin.leif.zxcvbn.client.Zxcvbn;
import org.vaadin.leif.zxcvbn.client.ZxcvbnIndicator;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class ZxcvbnDemo implements EntryPoint {

    private final ZxcvbnIndicator indicator = new ZxcvbnIndicator();
    private final TextBox textBox = new TextBox();
    private final Zxcvbn zxcvbn = Zxcvbn.get();
    private HandlerRegistration interceptorRegistration;

    @Override
    public void onModuleLoad() {
        RootPanel root = RootPanel.get();
        ZxcvbnIndicator.loadCss();
        zxcvbn.ensureLoaded();

        root.add(textBox);
        root.add(indicator);
        interceptorRegistration = TextBoxInterceptor.addInterceptor(textBox,
                indicator);
    }

}
