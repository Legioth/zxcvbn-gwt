package org.vaadin.leif.zxcvbn;

import java.lang.reflect.Method;
import java.util.Map;

import org.vaadin.leif.zxcvbn.client.VZxcvbnIndicator;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.tools.ReflectTools;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.ClientWidget;
import com.vaadin.ui.Component;

@ClientWidget(value = VZxcvbnIndicator.class)
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

    private AbstractTextField targetField;
    private String password;
    private int passwordScore;

    public void setTargetField(AbstractTextField targetField) {
        this.targetField = targetField;
        requestRepaint();
    }

    public AbstractTextField getTargetField() {
        return targetField;
    }

    @Override
    public void paintContent(PaintTarget target) throws PaintException {
        target.addAttribute(VZxcvbnIndicator.TARGET_FIELD_ATTR,
                getTargetField());
    }

    @Override
    public void changeVariables(Object source, Map<String, Object> variables) {
        boolean fireEvent = false;

        String password = (String) variables.get(VZxcvbnIndicator.PASSWORD_VAR);

        // Never fire event for null password
        if (password != null && !password.equals(this.password)) {
            fireEvent = true;
            this.password = password;
        }

        Integer score = (Integer) variables.get(VZxcvbnIndicator.SCORE_VAR);
        if (score != null) {
            int newScore = score.intValue();
            if (!fireEvent && newScore != passwordScore) {
                fireEvent = true;
            }
            passwordScore = newScore;
        }

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
