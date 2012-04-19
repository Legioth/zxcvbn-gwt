package org.vaadin.leif.zxcvbn.client;

import com.google.gwt.core.client.GWT;
import com.vaadin.terminal.gwt.client.communication.RpcProxy;
import com.vaadin.terminal.gwt.client.ui.Connect;
import com.vaadin.terminal.gwt.client.ui.Vaadin6Connector;

@Connect(value = org.vaadin.leif.zxcvbn.ZxcvbnIndicator.class)
public class ZxcvbnIndicatorConnector extends Vaadin6Connector {

    // Bind this in case someone cares...
    private ZxcvbnRpc rpc = RpcProxy.create(ZxcvbnRpc.class, this);

    @Override
    protected VZxcvbnIndicator createWidget() {
        return GWT.create(VZxcvbnIndicator.class);
    }

    @Override
    public VZxcvbnIndicator getWidget() {
        return (VZxcvbnIndicator) super.getWidget();
    }
}
