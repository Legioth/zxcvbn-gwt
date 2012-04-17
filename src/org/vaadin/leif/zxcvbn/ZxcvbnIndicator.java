package org.vaadin.leif.zxcvbn;

import org.vaadin.leif.zxcvbn.client.VZxcvbnIndicator;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.ClientWidget;

@ClientWidget(value = VZxcvbnIndicator.class)
public class ZxcvbnIndicator extends AbstractComponent {
    private AbstractTextField targetField;

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
}
