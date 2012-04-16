package org.vaadin.leif.zxcvbn.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class TextBoxInterceptor implements HandlerRegistration {
    private final TextBoxBase textBox;
    private final ZxcvbnIndicator indicator;
    private String lastValue = null;

    private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();

    private TextBoxInterceptor(TextBoxBase textBox, ZxcvbnIndicator indicator) {
        this.textBox = textBox;
        this.indicator = indicator;

        registrations.add(textBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                onEvent();
            }
        }));

        registrations.add(textBox.addBlurHandler(new BlurHandler() {
            @Override
            public void onBlur(BlurEvent event) {
                onEvent();
            }
        }));

        registrations.add(textBox.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                onEvent();
            }
        }));
    }

    public static HandlerRegistration addInterceptor(TextBoxBase textBox,
            ZxcvbnIndicator indicator) {
        return new TextBoxInterceptor(textBox, indicator);
    }

    private void onEvent() {
        String currentValue = textBox.getValue();
        if (!currentValue.equals(lastValue)) {
            lastValue = currentValue;
            Zxcvbn zxcvbn = Zxcvbn.get();
            ZxcvbnResult result = zxcvbn.testPassword(currentValue);
            indicator.showResult(result);
        }
    }

    @Override
    public void removeHandler() {
        for (HandlerRegistration registration : registrations) {
            registration.removeHandler();
        }
    }

}
