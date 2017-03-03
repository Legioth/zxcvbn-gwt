package org.vaadin.leif.zxcvbn.client;

import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.LegacyConnector;
import com.vaadin.shared.ui.Connect;

@Connect(value = org.vaadin.leif.zxcvbn.ZxcvbnIndicator.class)
public class ZxcvbnIndicatorConnector extends LegacyConnector {

    private HandlerRegistration handlerRegistration;

    private TextBoxBase boundTo;

    private void bindTo(TextBoxBase targetField) {
        if (boundTo == targetField) {
            // Nothing to do
            return;
        }
        if (boundTo != null) {
            handlerRegistration.removeHandler();
            handlerRegistration = null;
        }
        boundTo = targetField;
        if (targetField != null) {
            handlerRegistration = TextBoxInterceptor.addInterceptor(
                    targetField, getWidget());
        }
    }

    @Override
    public ZxcvbnState getState() {
        return (ZxcvbnState) super.getState();
    }

    @Override
    public VZxcvbnIndicator getWidget() {
        return (VZxcvbnIndicator) super.getWidget();
    }

    @Override
    protected void init() {
        getWidget().setConnector(this);
    }

    @Override
    public void onUnregister() {
        super.onUnregister();
        bindTo(null);
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

        ComponentConnector targetField = (ComponentConnector) getState().targetField;
        if (targetField != null) {
            bindTo((TextBoxBase) targetField.getWidget());
        } else {
            bindTo(null);
        }
    }

    public void processResult(ZxcvbnResult result) {
        int score = result.getScore();
        String text = boundTo.getValue();
        getRpcProxy(ZxcvbnRpc.class).setRating(text, score);
    }
}
