package org.vaadin.leif.zxcvbn.demo.vaadin;

import org.vaadin.leif.zxcvbn.ZxcvbnIndicator;
import org.vaadin.leif.zxcvbn.ZxcvbnIndicator.ZxcvbnChangeEvent;
import org.vaadin.leif.zxcvbn.ZxcvbnIndicator.ZxcvbnChangeListener;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("zxcvbn-demo")
@Widgetset("org.vaadin.leif.zxcvbn.demo.vaadin.ZxcvbnDemoWidgetset")
public class ZxcvbnDemoApp extends UI {
    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("Zxcvbn demo");

        TextField textField = new TextField("Sample field");
        textField.setImmediate(true);

        ZxcvbnIndicator zxcvbnIndicator = new ZxcvbnIndicator();
        zxcvbnIndicator.setTargetField(textField);
        zxcvbnIndicator.addZxcvbnChangeListener(new ZxcvbnChangeListener() {
            @Override
            public void onZxcvbnChange(ZxcvbnChangeEvent event) {
                ZxcvbnIndicator indicator = event.getSource();
                showNotification(indicator.getPassword() + " has score "
                        + indicator.getPasswordScore());
            }
        });

        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);

        layout.addComponent(textField);
        layout.addComponent(zxcvbnIndicator);
    }

}
