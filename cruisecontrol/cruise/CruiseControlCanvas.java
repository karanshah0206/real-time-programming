package cruise;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;

import util.BufferedCanvas;
import util.Text;

public class CruiseControlCanvas extends BufferedCanvas {
    private static final int SIZE = 220;

    private Font fSmallFont, fBigFont;
    private Text fTextCruiseControl, fTextCruiseSpeed, fTextSpeed, fTextEnabled, fTextDisabled;
    private int fSpeed;
    private boolean fEnabled;

    public CruiseControlCanvas() {
        super();

        setPreferredSize(new Dimension(SIZE, SIZE));

        fBigFont = new Font("Arial", Font.BOLD, 24);
        fSmallFont = new Font("Arial", Font.BOLD, 18);

        fTextCruiseControl = new Text("Cruise Control", Color.WHITE, fBigFont);
        fTextCruiseSpeed = new Text("Cruise Speed", Color.WHITE, fSmallFont);
        fTextSpeed = new Text("0", Color.WHITE, fBigFont);
        fTextEnabled = new Text("Enabled", Color.GREEN, fSmallFont);
        fTextDisabled = new Text("Disabled", Color.RED, fSmallFont);

        fSpeed = 0;
        fEnabled = false;
    }

    public boolean get() {
        return  fEnabled;
    }

    public void set(boolean aEnabled) {
        fEnabled = aEnabled;
        repaint();
    }

    public void setSpeed(int aSpeed) {
        if (fSpeed != aSpeed) {
            fSpeed = aSpeed;
            repaint();
        }
    }

    public void render(Graphics2D aGraphics) {
        aGraphics.setColor(Color.DARK_GRAY);
        aGraphics.fillRect(0, 0, getWidth(), getHeight());
        aGraphics.setColor(Color.WHITE);
        aGraphics.drawRect(5, 5, getWidth() - 10, getHeight() - 10);

        fTextCruiseControl.renderXCentred(aGraphics, getWidth(), 35);
        fTextCruiseSpeed.renderXCentred(aGraphics, getWidth(), 90);

        aGraphics.drawRect(30, 60, 160, 80);

        fTextSpeed.setValue(String.valueOf(fSpeed));
        fTextSpeed.renderXCentred(aGraphics, getWidth(), 125);

        Text lStateText = (fEnabled ? fTextEnabled : fTextDisabled);
        int lRenderWidth = lStateText.getRenderWidth(aGraphics);
        int lXPos = (getWidth() - lRenderWidth) / 2 - 20;
        lStateText.render(aGraphics, lXPos, 175);
        aGraphics.fillArc(lXPos + lRenderWidth + 20, 160, 20, 20, 0, 360);
    }
}
