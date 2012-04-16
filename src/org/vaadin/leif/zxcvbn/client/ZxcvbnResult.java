package org.vaadin.leif.zxcvbn.client;

import com.google.gwt.core.client.JavaScriptObject;

public final class ZxcvbnResult extends JavaScriptObject {
    protected ZxcvbnResult() {
        // JSO constructor
    }

    public native int getCalcTime() /*-{
		return this.calc_time;
    }-*/;

    public native double getCrackTime() /*-{
		return this.crack_time;
    }-*/;

    public native String getCrackTimeDisplay() /*-{
		return this.crack_time_display;
    }-*/;

    public native double getEntropy() /*-{
		return this.entropy;
    }-*/;

    public native String getPassword() /*-{
		return this.password();
    }-*/;

    public native int getScore()/*-{
		return this.score;
    }-*/;

}
