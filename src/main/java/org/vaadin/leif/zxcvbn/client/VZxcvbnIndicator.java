package org.vaadin.leif.zxcvbn.client;

public class VZxcvbnIndicator extends ZxcvbnIndicator {

    private ZxcvbnIndicatorConnector connector;

    public void setConnector(ZxcvbnIndicatorConnector connector) {
        this.connector = connector;
    }

    @Override
    public void showResult(ZxcvbnResult result) {
        super.showResult(result);
        connector.processResult(result);
    }

}
