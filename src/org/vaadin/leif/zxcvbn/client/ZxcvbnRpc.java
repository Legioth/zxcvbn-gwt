package org.vaadin.leif.zxcvbn.client;

import com.vaadin.shared.annotations.Delayed;
import com.vaadin.shared.communication.ServerRpc;

public interface ZxcvbnRpc extends ServerRpc {
    @Delayed(lastOnly = true)
    public void setRating(String password, int score);
}
