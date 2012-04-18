package org.vaadin.leif.zxcvbn.client;

import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;

public class VZxcvbnIndicator extends ZxcvbnIndicator implements Paintable {
    public static final String TARGET_FIELD_ATTR = "field";

    public static final String PASSWORD_VAR = "password";

    public static final String SCORE_VAR = "score";

    private TextBoxBase boundTo;

    private ApplicationConnection client;

    private String id;

    private HandlerRegistration handlerRegistration;

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

        Paintable paintable = uidl.getPaintableAttribute(TARGET_FIELD_ATTR,
                client);

        bindTo((TextBoxBase) paintable);

    }

    @Override
    public void showResult(ZxcvbnResult result) {
        super.showResult(result);
        int score = result.getScore();
        String text = boundTo.getValue();

        client.updateVariable(id, PASSWORD_VAR, text, false);
        client.updateVariable(id, SCORE_VAR, score, false);
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
