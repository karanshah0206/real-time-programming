package util;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MakeButton {
    public static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 18);

    public static JButton createButton(String aTitle, ActionListener aListener, Font aFont) {
        JButton lResult = new JButton(aTitle);
        lResult.setFont(aFont);
        lResult.addActionListener(aListener);
        return lResult;
    }

    public static JButton createButton(String aTitle, ActionListener aListener) {
        return createButton(aTitle, aListener, DEFAULT_FONT);
    }

    public static JButton createButton(String aTitle, ActionListener aListener, Dimension aSize, Font aFont) {
        JButton lResult = createButton(aTitle, aListener, aFont);
        lResult.setPreferredSize(aSize);
        return lResult;
    }

    public static JButton createButton(String aTitle, ActionListener aListener, Dimension aSize) {
        return createButton(aTitle, aListener, aSize, DEFAULT_FONT);
    }

    public static JButton createButton(String aTitle, ActionListener aListener, int aHeight, Font aFont) {
        JButton lResult = createButton(aTitle, aListener, aFont);
        Dimension lSize = lResult.getPreferredSize();
        lSize.height = aHeight;
        lResult.setPreferredSize(lSize);
        return lResult;
    }

    public static JButton createButton(String aTitle, ActionListener aListener, int aHeight) {
        return createButton(aTitle, aListener, aHeight, DEFAULT_FONT);
    }
}
