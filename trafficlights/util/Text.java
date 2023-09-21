package util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class Text {
    private String fValue;
    private Color fColour;
    private Font fFont;

    public Text(String aValue, Color aColour, Font aFont) {
        fValue = aValue;
        fColour = aColour;
        fFont = aFont;
    }

    public void setValue(String aValue) {
        fValue = aValue;
    }

    public void setColour(Color aColour) {
        fColour = aColour;
    }

    public void setFont(Font aFont) {
        fFont = aFont;
    }

    public int getBaselineOffset(Graphics2D aGraphics) {
        aGraphics.setFont(fFont);
        FontMetrics lFontData = aGraphics.getFontMetrics();
        return lFontData.getHeight() / 2 + lFontData.getAscent();
    }

    public int getRenderWidth(Graphics2D aGraphics) {
        aGraphics.setFont(fFont);
        return aGraphics.getFontMetrics().stringWidth(fValue);
    }

    public void render(Graphics2D aGraphics, int aX, int aY) {
        aGraphics.setFont(fFont);
        aGraphics.setColor(fColour);
        aGraphics.drawString(fValue, aX, aY);
    }

    public void renderCentred(Graphics2D aGraphics, Dimension aSurface) {
        aGraphics.setFont(fFont);
        FontMetrics lFontData = aGraphics.getFontMetrics();
        int lRenderWidth = lFontData.stringWidth(fValue);
        render(
            aGraphics, (aSurface.width - lRenderWidth) / 2,
            (aSurface.height - lFontData.getHeight()) / 2 + lFontData.getAscent()
        );
    }

    public void renderXCentred(Graphics2D aGraphics, int aWidth, int aY) {
        aGraphics.setFont(fFont);
        render(aGraphics, (aWidth - aGraphics.getFontMetrics().stringWidth(fValue)) / 2, aY);
    }
}
