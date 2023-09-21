package traffic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;

import util.BufferedCanvas;
import util.Text;

public class TrafficLightsCanvas extends BufferedCanvas {
    private static final int WIDTH = 150;
    private static final int HEIGHT = 400;

    private enum State { RED, GREEN, AMBER };

    private State fState;
    private Text fDirectionText;

    public TrafficLightsCanvas(String aDirection) {
        super();

        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        fState = State.RED;
        fDirectionText = new Text(aDirection, Color.BLACK, new Font("Arial", Font.BOLD, 24));
    }

    public void setRed() {
        fState = State.RED;
        repaint();
    }

    public void setAmber() {
        fState = State.AMBER;
        repaint();
    }

    public void setGreen() {
        fState = State.GREEN;
        repaint();
    }

    public void render(Graphics2D aGraphics) {
        // clear
        aGraphics.setColor(getBackground());
        aGraphics.fillRect(0, 0, getWidth(), getHeight());

        // draw direction header
        fDirectionText.renderXCentred(aGraphics, WIDTH, 50);

        // draw 3 circles (the traffic lights)
        int lYPos = 85;

        aGraphics.setColor(fState == State.RED ? Color.RED : Color.DARK_GRAY);
        aGraphics.fillArc(30, lYPos, 90, 90, 0, 360);

        lYPos += 100;
        aGraphics.setColor(fState == State.AMBER ? Color.ORANGE : Color.DARK_GRAY);
        aGraphics.fillArc(30, lYPos, 90, 90, 0, 360);

        lYPos += 100;
        aGraphics.setColor(fState == State.GREEN ? Color.GREEN : Color.DARK_GRAY);
        aGraphics.fillArc(30, lYPos, 90, 90, 0, 360);
    }
}
