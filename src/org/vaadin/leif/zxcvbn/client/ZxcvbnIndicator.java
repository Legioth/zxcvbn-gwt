package org.vaadin.leif.zxcvbn.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

public class ZxcvbnIndicator extends Widget {
    private static boolean cssLoaded = false;

    private int lastScore = 0;

    public ZxcvbnIndicator() {
        DivElement root = Document.get().createDivElement();
        setElement(root);

        setStylePrimaryName("zxcvbn-indicator");

        for (int i = 0; i < 5; i++) {
            SpanElement block = Document.get().createSpanElement();
            block.setInnerHTML("&nbsp;");
            root.appendChild(block);
        }

        setScore(0);
        setWidth("");
    }

    public ZxcvbnIndicator(ZxcvbnResult result) {
        this();
        showResult(result);
    }

    @Override
    public void setWidth(String width) {
        setStyleDependentName("undefinedWidth", width.length() == 0);
        super.setWidth(width);
    }

    private void setScore(int score) {
        removeStyleDependentName(Integer.toString(lastScore));
        addStyleDependentName(Integer.toString(score));
        lastScore = score;

        NodeList<Node> childNodes = getElement().getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Element item = childNodes.getItem(i).cast();
            if (i <= score) {
                item.getStyle().clearDisplay();
            } else {
                item.getStyle().setDisplay(Display.NONE);
            }
        }
    }

    public void showResult(ZxcvbnResult result) {
        int score = result.getScore();
        getElement().setTitle(
                "Estimated time to crack: " + result.getCrackTimeDisplay());
        setScore(score);
    }

    public static void loadCss() {
        if (!cssLoaded) {
            cssLoaded = true;
            addCssFile(GWT.getModuleBaseURL() + "zxcvbn.css");
        }
    }

    private static native void addCssFile(String url) /*-{
		var elem = $doc.createElement("link");
		elem.setAttribute("rel", "stylesheet");
		elem.setAttribute("type", "text/css");
		elem.setAttribute("href", url);
		$doc.getElementsByTagName("head")[0].appendChild(elem);
    }-*/;

}
