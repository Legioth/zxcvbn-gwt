package org.vaadin.leif.zxcvbn.demo.vaadin;

import org.vaadin.leif.zxcvbn.ZxcvbnIndicator;

import com.vaadin.Application;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

public class ZxcvbnDemoApp extends Application {
    @Override
    public void init() {
        Window mainWindow = new Window("Zxcvbn demo");
        setMainWindow(mainWindow);

        TextField textField = new TextField("Sample field");
        ZxcvbnIndicator zxcvbnIndicator = new ZxcvbnIndicator();
        zxcvbnIndicator.setTargetField(textField);

        mainWindow.addComponent(textField);
        mainWindow.addComponent(zxcvbnIndicator);
    }

}
