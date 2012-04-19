package org.vaadin.leif.zxcvbn.demo.vaadin;

import org.vaadin.leif.zxcvbn.ZxcvbnIndicator;
import org.vaadin.leif.zxcvbn.ZxcvbnIndicator.ZxcvbnChangeEvent;
import org.vaadin.leif.zxcvbn.ZxcvbnIndicator.ZxcvbnChangeListener;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.terminal.WrappedRequest;
import com.vaadin.ui.Root;
import com.vaadin.ui.TextField;

@Theme("zxcvbn-demo")
@Widgetset("org.vaadin.leif.zxcvbn.demo.vaadin.ZxcvbnDemoWidgetset")
public class ZxcvbnDemoApp extends Root {
    @Override
    protected void init(WrappedRequest request) {
        setCaption("Zxcvbn demo");

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

        addComponent(textField);
        addComponent(zxcvbnIndicator);
    }

}
