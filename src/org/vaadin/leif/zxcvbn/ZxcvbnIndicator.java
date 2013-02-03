package org.vaadin.leif.zxcvbn;

import java.lang.reflect.Method;

import org.vaadin.leif.zxcvbn.client.ZxcvbnRpc;
import org.vaadin.leif.zxcvbn.client.ZxcvbnState;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Component;
import com.vaadin.util.ReflectTools;

@JavaScript("public/zxcvbn.js")
@StyleSheet("public/zxcvbn.css")
public class ZxcvbnIndicator extends AbstractComponent {

    private static final Method ZXCVBN_EVENT_METHOD = ReflectTools.findMethod(
            ZxcvbnChangeListener.class, "onZxcvbnChange",
            ZxcvbnChangeEvent.class);

    public static class ZxcvbnChangeEvent extends Component.Event {
        public ZxcvbnChangeEvent(ZxcvbnIndicator source) {
            super(source);
        }

        @Override
        public ZxcvbnIndicator getSource() {
            return (ZxcvbnIndicator) super.getSource();
        }
    }

    public static interface ZxcvbnChangeListener {
        public void onZxcvbnChange(ZxcvbnChangeEvent event);
    }

    private String password;
    private int passwordScore;

    public ZxcvbnIndicator() {
        registerRpc(new ZxcvbnRpc() {
            @Override
            public void setRating(String password, int score) {
                doSetRating(password, score);
            }
        });
    }

    public void setTargetField(AbstractTextField targetField) {
        getState().targetField = targetField;
    }

    public AbstractTextField getTargetField() {
        return (AbstractTextField) getState().targetField;
    }

    @Override
    protected ZxcvbnState getState() {
        return (ZxcvbnState) super.getState();
    }

    private void doSetRating(String password, int score) {
        boolean fireEvent = false;
        // Never fire event for null password
        if (password != null && !password.equals(this.password)) {
            fireEvent = true;
            this.password = password;
        }

        if (!fireEvent && score != passwordScore) {
            fireEvent = true;
        }
        passwordScore = score;

        if (fireEvent) {
            fireEvent(new ZxcvbnChangeEvent(this));
        }
    }

    public String getPassword() {
        return password;
    }

    public int getPasswordScore() {
        return passwordScore;
    }

    public void addZxcvbnChangeListener(ZxcvbnChangeListener listener) {
        addListener(ZxcvbnChangeEvent.class, listener, ZXCVBN_EVENT_METHOD);
    }

    public void removeZxcvbnChangeListener(ZxcvbnChangeListener listener) {
        removeListener(ZxcvbnChangeEvent.class, listener, ZXCVBN_EVENT_METHOD);
    }

}
