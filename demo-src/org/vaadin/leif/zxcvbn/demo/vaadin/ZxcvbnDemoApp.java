package org.vaadin.leif.zxcvbn.demo.vaadin;

import org.vaadin.leif.zxcvbn.ZxcvbnIndicator;
import org.vaadin.leif.zxcvbn.ZxcvbnIndicator.ZxcvbnChangeEvent;
import org.vaadin.leif.zxcvbn.ZxcvbnIndicator.ZxcvbnChangeListener;

import com.vaadin.Application;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

public class ZxcvbnDemoApp extends Application {
    @Override
    public void init() {
        setTheme("zxcvbn-demo");

        Window mainWindow = new Window("Zxcvbn demo");
        setMainWindow(mainWindow);

        TextField textField = new TextField("Sample field");
        textField.setImmediate(true);

        ZxcvbnIndicator zxcvbnIndicator = new ZxcvbnIndicator();
        zxcvbnIndicator.setTargetField(textField);
        zxcvbnIndicator.addZxcvbnChangeListener(new ZxcvbnChangeListener() {
            @Override
            public void onZxcvbnChange(ZxcvbnChangeEvent event) {
                ZxcvbnIndicator indicator = event.getSource();
                getMainWindow().showNotification(
                        indicator.getPassword() + " has score "
                                + indicator.getPasswordScore());
            }
        });

        mainWindow.addComponent(textField);
        mainWindow.addComponent(zxcvbnIndicator);
    }

}
