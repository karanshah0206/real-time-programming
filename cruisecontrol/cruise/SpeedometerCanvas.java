package cruise;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import util.BufferedCanvas;
import util.Text;

public class SpeedometerCanvas extends BufferedCanvas { 
    private static final int SIZE = 400;
    private static final int RADIUS = 130;

    private Font fTinyFont, fSmallFont, fBigFont, fHugeFont;
    private Text fTextSpeed, fTextTick, fTextIgnition, fTextThrottle, fTextBrake, fTextControl;
    private double fMaxSpeed, fMaxThrottle, fMaxBrake;
    private double fSpeed, fDistance, fThrottle, fBrake;
    private int fStepSize, fStepDivider, fUnitsPerTick;
    private boolean fIgnitionOn;
    private int fDistanceDigits[];

    private void mapDistance() {
        // distance measured in meters, displayed in hectometers
        int lDistance = (int)fDistance/10;
        for (int i = 4; i >= 0; i--) {
            lDistance /= 10;
            fDistanceDigits[i] = lDistance % 10;
        }
    }

    private void drawSpeedometer(Graphics2D aGraphics, int aXCentre, int aYCentre, int aRadius) {
        aGraphics.setStroke(new BasicStroke(2.0f));
        aGraphics.setColor(Color.WHITE);

        aGraphics.drawArc(aXCentre - aRadius, aYCentre - aRadius, aRadius * 2, aRadius * 2, 0, 360);

        for (int i = 0; i <= 12; i++) {
            // get angle
            int lAngleInDeg = 210 - i * 20;
            if (lAngleInDeg < 0) lAngleInDeg += 360;
            double lAngleInRad = (lAngleInDeg * Math.PI) / 180;

            // draw marker line
            int lXOffset = aXCentre + (int)(aRadius * Math.cos(lAngleInRad));
            int lYOffset = aYCentre - (int)(aRadius * Math.sin(lAngleInRad));
            aGraphics.drawLine(aXCentre, aYCentre, lXOffset, lYOffset);

            // draw number
            lXOffset = aXCentre + (int)((aRadius + 10) * Math.cos(lAngleInRad));
            lYOffset = aYCentre - (int)((aRadius + 10) * Math.sin(lAngleInRad));
            if (i <= 5) lXOffset -= 15;
            else if (i == 6) lXOffset -= 10;
            else if (i == 7) lXOffset -= 5;
            fTextTick.setValue(String.valueOf(i * fStepSize));
            fTextTick.render(aGraphics, lXOffset, lYOffset);
        }

        // draw speed arc
        if (fSpeed > 0) {
            aGraphics.setColor(Color.GREEN);
            aGraphics.fillArc(
                aXCentre - aRadius + 2, aYCentre - aRadius + 2,
                aRadius * 2 - 2, aRadius * 2 - 4,
                210, (int)(fSpeed != 0 ? -((int)(fSpeed / fStepDivider)) : -1)
            );
        }
        aGraphics.setColor(Color.BLACK);
        aGraphics.fillArc(
            aXCentre - aRadius + 10, aYCentre - aRadius + 10,
            aRadius * 2 - 20, aRadius * 2 - 20,
            0, 360
        );

        // draw ignition status
        fTextIgnition.render(aGraphics, aXCentre - 40, aYCentre + 50);
        if (fIgnitionOn) aGraphics.setColor(Color.GREEN);
        else aGraphics.setColor(Color.RED);
        aGraphics.fillArc(aXCentre + 30, aYCentre + 38, 12, 12, 0, 360);

        // draw speed value
        fTextSpeed.setValue(String.valueOf((int)fSpeed));
        fTextSpeed.renderXCentred(aGraphics, getWidth(), 130);
    }

    private void drawOdometer(Graphics2D aGraphics) {
        String lZero = "0";

        aGraphics.setFont(fBigFont);
        FontMetrics lFontData = aGraphics.getFontMetrics();

        int lCWidth = lFontData.stringWidth(lZero);
        int lCHeight = lFontData.getHeight();
        int lXCentre = getWidth() / 2 - (lCWidth + 4) * 5 / 2;
        int lYCentre = getHeight() / 2 - 40;

        for (int i = 0; i < 5; i++) {
            aGraphics.setColor(Color.WHITE);
            aGraphics.drawRect(lXCentre + (lCWidth + 4) * i, lYCentre, lCWidth + 4, lCHeight + 2);
            if (i == 4) aGraphics.setColor(Color.RED);
            else aGraphics.setColor(Color.YELLOW);
            aGraphics.drawString(
                String.valueOf(fDistanceDigits[i]),
                lXCentre + (lCWidth + 4) * i + 3,
                lYCentre + lCHeight - 4
            );
        }
    }

    private void drawControl(Graphics2D aGraphics, Text aName, int aPos, double aValue, double aMax, Color aColour) {
        int lColumnWidth = getWidth() / 2, lStart = 28 + lColumnWidth * aPos,
            lEnd = (lColumnWidth - 58), lYPos = getHeight() - 60;

        aName.render(
            aGraphics,
            (lColumnWidth - aName.getRenderWidth(aGraphics)) / 2 + lColumnWidth * aPos,
            lYPos
        );

        aGraphics.setStroke(new BasicStroke(2.0f));
        aGraphics.drawRect(lStart, lYPos + 15, lEnd, 16);
        aGraphics.setColor(aColour);
        aGraphics.fillRect(lStart + 2, lYPos + 16, (int) ((aValue / aMax) * (lEnd - 2)), 15);

        fTextControl.setValue(String.format("%1.1f", aValue));
        fTextControl.render(
            aGraphics,
            (lColumnWidth - fTextControl.getRenderWidth(aGraphics)) / 2 + lColumnWidth * aPos,
            lYPos + 28
        );
    }

    public SpeedometerCanvas(double aMaxSpeed, double aMaxThrottle, double aMaxBrake, int aUnitsPerTick) {
        super();

        setPreferredSize(new Dimension(SIZE, SIZE));

        fHugeFont = new Font("Arial", Font.BOLD, 48);
        fBigFont = new Font("Arial", Font.BOLD, 24);
        fSmallFont = new Font("Arial", Font.BOLD, 18);
        fTinyFont = new Font("Arial", Font.BOLD, 12);

        fMaxSpeed = aMaxSpeed;
        fMaxThrottle = aMaxThrottle;
        fMaxBrake = aMaxBrake;
        fUnitsPerTick = aUnitsPerTick;
        fStepSize = fUnitsPerTick;
        fStepDivider = fUnitsPerTick / 20;
        fDistanceDigits = new int[5];

        clear();

        fTextSpeed = new Text("0", Color.GREEN, fHugeFont);
        fTextTick = new Text("0", Color.WHITE, fSmallFont);
        fTextIgnition = new Text("Ignition", Color.WHITE, fSmallFont);
        fTextThrottle = new Text("Throttle", Color.WHITE, fBigFont);
        fTextBrake = new Text("Brake", Color.WHITE, fBigFont);
        fTextControl = new Text("0.0", Color.YELLOW, fTinyFont);
    }

    public void setSpeed(double aSpeed) {
        if (fSpeed != aSpeed) {
            fSpeed = Math.min(aSpeed, fMaxSpeed);
            repaint();
        }
    }

    public void setDistance(double aDistance) {
        if (fDistance != aDistance) {
            fDistance = aDistance;
            mapDistance();
            repaint();
        }
    }

    public void setThrottle(double aThrottle) {
        if (fThrottle != aThrottle) {
            fThrottle = Math.min(aThrottle, fMaxThrottle);
            fBrake = 0;
            repaint();
        }
    }

    public void setBrake(double aBrake) {
        if (fBrake != aBrake) {
            fBrake = Math.min(aBrake, fMaxBrake);
            fThrottle = 0;
            repaint();
        }
    }

    public void setIgnitionOn() {
        fIgnitionOn = true;
        repaint();
    }

    public void setIgnitionOff() {
        fIgnitionOn = false;
        repaint();
    }

    public void clear() {
        fIgnitionOn = false;
        fSpeed = 0;
        fDistance = 0;
        fThrottle = 0;
        fBrake = 0;
        mapDistance();
        repaint();
    }

    public void render(Graphics2D aGraphics) {
        aGraphics.setColor(Color.BLACK);
        aGraphics.fillRect(0, 0, getWidth(), getHeight());
        drawSpeedometer(aGraphics, getWidth() / 2, getHeight() / 2 - 20, RADIUS);
        drawOdometer(aGraphics);
        drawControl(aGraphics, fTextThrottle, 0, fThrottle, fMaxThrottle, Color.GREEN);
        drawControl(aGraphics, fTextBrake, 1, fBrake, fMaxBrake, Color.RED);
    }
}
