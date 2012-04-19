package org.vaadin.leif.zxcvbn.client;

import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.ComponentConnector;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.communication.MethodInvocation;

public class VZxcvbnIndicator extends ZxcvbnIndicator implements Paintable {
    public static final String TARGET_FIELD_ATTR = "field";

    public static final String PASSWORD_VAR = "password";

    public static final String SCORE_VAR = "score";

    private TextBoxBase boundTo;

    private ApplicationConnection client;

    private String id;

    private HandlerRegistration handlerRegistration;

    private class LazyMethodInvocation extends MethodInvocation {
        public LazyMethodInvocation() {
            super(id, "org.vaadin.leif.zxcvbn.client.ZxcvbnRpc", "setRating",
                    new Object[2]);
        }

        @Override
        public String getInterfaceName() {
            lazyMethodInvocation = null;
            return super.getInterfaceName();
        }
    }

    private LazyMethodInvocation lazyMethodInvocation = null;

    public VZxcvbnIndicator() {
        Zxcvbn.get().ensureLoaded();
    }

    @Override
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        id = uidl.getId();
        this.client = client;

        if (client.updateComponent(this, uidl, true)) {
            return;
        }

        ComponentConnector paintable = (ComponentConnector) uidl
                .getPaintableAttribute(TARGET_FIELD_ATTR, client);

        bindTo((TextBoxBase) paintable.getWidget());

    }

    @Override
    public void showResult(ZxcvbnResult result) {
        super.showResult(result);
        int score = result.getScore();
        String text = boundTo.getValue();

        if (lazyMethodInvocation == null) {
            lazyMethodInvocation = new LazyMethodInvocation();
            client.addMethodInvocationToQueue(lazyMethodInvocation, false);
        }
        Object[] parameters = lazyMethodInvocation.getParameters();
        parameters[0] = text;
        parameters[1] = Integer.valueOf(score);
    }

    private void bindTo(TextBoxBase targetField) {
        if (boundTo != null) {
            handlerRegistration.removeHandler();
            handlerRegistration = null;
        }
        boundTo = targetField;
        if (targetField != null) {
            handlerRegistration = TextBoxInterceptor.addInterceptor(
                    targetField, this);
        }
    }
}
