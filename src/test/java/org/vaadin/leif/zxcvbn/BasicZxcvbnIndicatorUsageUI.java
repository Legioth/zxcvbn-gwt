package org.vaadin.leif.zxcvbn;

import org.vaadin.addonhelpers.AbstractTest;
import org.vaadin.leif.zxcvbn.ZxcvbnIndicator.ZxcvbnChangeEvent;
import org.vaadin.leif.zxcvbn.ZxcvbnIndicator.ZxcvbnChangeListener;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * Add many of these with different configurations, combine with different
 * components, for regressions and also make them dynamic if needed.
 */
public class BasicZxcvbnIndicatorUsageUI extends AbstractTest {

    @Override
    public Component getTestComponent() {
        getPage().setTitle("Zxcvbn demo");

        TextField textField = new TextField("Sample field");

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

        return layout;
    }

    @Override
    protected void init(VaadinRequest request) {
        super.init(request);

        // Bug in AbstractTest?
        setContent(content);
    }

}
