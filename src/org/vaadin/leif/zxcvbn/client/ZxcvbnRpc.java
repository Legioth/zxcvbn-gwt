package org.vaadin.leif.zxcvbn.client;

import com.vaadin.terminal.gwt.client.communication.ServerRpc;

public interface ZxcvbnRpc extends ServerRpc {
    public void setRating(String password, int score);
}
