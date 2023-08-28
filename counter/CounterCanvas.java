package counter;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class CounterCanvas extends Canvas {
    private static final int MAX = 20;
    private static final int FONT_SIZE = 96;
    private static final int ARC_SIZE = 20;
    private static final int CANVAS_SIZE = 200;

    private static final Color ARC_COLOUR = Color.BLUE;
    private static final Color TEXT_COLOUR = Color.BLACK;
    private static final Color CROSSHAIR_COLOUR = Color.RED;

    private int fCurrentValue;
    private Font fCounterFont;

    public CounterCanvas() {
        setPreferredSize(new Dimension(CANVAS_SIZE, CANVAS_SIZE));

        fCurrentValue = 0;
        fCounterFont = new Font("Segoe  GUI", Font.BOLD, FONT_SIZE);
    }

    public int getValue() {
        return fCurrentValue;
    }

    public boolean canIncrement() {
        return fCurrentValue < MAX;
    }

    public boolean canDecrement() {
        return fCurrentValue > 0;
    }

    public void increment() {
        if (canIncrement()) {
            fCurrentValue++;
            repaint();
        }
    }

    public void decrement() {
        if (canDecrement()) {
            fCurrentValue--;
            repaint();
        }
    }

    public void paint(Graphics aGraphics) {
        update(aGraphics);
    }

    public void update(Graphics aGraphics) {
        // clear window
        aGraphics.setColor(getBackground());
        aGraphics.fillRect(0, 0, getWidth(), getHeight());

        // draw arc
        aGraphics.setColor(ARC_COLOUR);
        aGraphics.fillArc(5, 5, getWidth() - 10, getHeight() - 10, 90, -fCurrentValue * (360 / MAX));
        aGraphics.setColor(getBackground());
        aGraphics.fillArc(5 + ARC_SIZE, 5 + ARC_SIZE, getWidth() - 2 * (5 + ARC_SIZE),
            getHeight() - 2 * (5 + ARC_SIZE), 90, -fCurrentValue * (360 / MAX));

        // draw crosshair
        aGraphics.setColor(CROSSHAIR_COLOUR);
        aGraphics.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
        aGraphics.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);

        // draw count
        aGraphics.setColor(TEXT_COLOUR);
        aGraphics.setFont(fCounterFont);
        FontMetrics lFontMetrics = aGraphics.getFontMetrics();
        String lCountString = String.valueOf(fCurrentValue);
        int lXPos = (getWidth() - lFontMetrics.stringWidth(lCountString)) / 2;
        int lYPos = (getHeight() - lFontMetrics.getHeight()) / 2 + lFontMetrics.getAscent();
        aGraphics.drawString(lCountString, lXPos, lYPos);
    }
}
