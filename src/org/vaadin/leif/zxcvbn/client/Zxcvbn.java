package org.vaadin.leif.zxcvbn.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArrayString;

public class Zxcvbn {
    private static final Zxcvbn instance = new Zxcvbn();
    private boolean jsLoaded = false;

    private Zxcvbn() {
        // Singleton constructor
    }

    public static Zxcvbn get() {
        return instance;
    }

    public void ensureLoaded() {
        if (!isLoaded()) {
            loadJs();
        }
    }

    private void loadJs() {
        assert !isLoaded();
        String url = GWT.getModuleBaseURL() + "zxcvbn.js";
        addScript(url);
    }

    public boolean isLoaded() {
        if (!jsLoaded) {
            // Check again
            jsLoaded = checkFunction();
        }
        return jsLoaded;
    }

    public ZxcvbnResult testPassword(String password) {
        ensureLoaded();
        return doTestPassword(password, null);
    }

    public ZxcvbnResult testPassword(String password, String... userInputs) {
        ensureLoaded();
        JsArrayString userInputsArray;
        if (userInputs != null && userInputs.length > 0) {
            userInputsArray = JsArrayString.createArray().cast();
            for (String userInput : userInputs) {
                userInputsArray.push(userInput);

            }
        } else {
            userInputsArray = null;
        }
        return doTestPassword(password, userInputsArray);
    }

    private native ZxcvbnResult doTestPassword(String password,
            JsArrayString userInputsArray)/*-{
		return $wnd.zxcvbn(password, userInputsArray);
    }-*/;

    private static native void addScript(String url) /*-{
		var elem = $doc.createElement("script");
		elem.setAttribute("language", "JavaScript");
		elem.setAttribute("src", url);
		$doc.getElementsByTagName("head")[0].appendChild(elem);
    }-*/;

    private static native boolean checkFunction() /*-{
		if ($wnd.zxcvbn && typeof $wnd.zxcvvn == 'function') {
			return true;
		} else {
			return false;
		}
    }-*/;

}
