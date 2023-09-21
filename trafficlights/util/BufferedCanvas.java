package util;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.image.VolatileImage;

public abstract class BufferedCanvas extends Canvas {
    private static final long serialVersionUID = 1L;

    private static boolean fUseBuffering = !System.getProperty("os.name").startsWith("Mac OS");
    private static boolean fUseBufferedImage = false;

    private Image fBufferImage;

    public static boolean isBuffering() {
        return fUseBuffering;
    }

    public static void useBufferedImage(boolean aFlag) {
        fUseBufferedImage = aFlag;
    }

    private void ensureBufferImage() {
        if (fBufferImage == null) {
            if (fUseBufferedImage) {
                fBufferImage = createImage(getWidth(), getHeight());
            } else {
                fBufferImage = createVolatileImage(getWidth(), getHeight());
            }
        }
    }

    private void createBufferImage() {
        fBufferImage = null;
        if (fUseBuffering) {
            ensureBufferImage();
        }
    }

    private void ensureCompatibility() {
        if (((VolatileImage)fBufferImage).validate(getGraphicsConfiguration()) == VolatileImage.IMAGE_INCOMPATIBLE) {
            createBufferImage();
        }
    }

    public BufferedCanvas() {
        super();
        createBufferImage();
    }

    public BufferedCanvas(GraphicsConfiguration aConfiguration) {
        super(aConfiguration);
        createBufferImage();
    }

    public final void paint(Graphics aGraphics) {
        if (fUseBuffering) {
            ensureBufferImage();
            if (fUseBufferedImage) {
                Graphics2D lGraphics = (Graphics2D)fBufferImage.getGraphics();
                render(lGraphics);
                lGraphics.dispose();
                aGraphics.drawImage(fBufferImage, 0, 0, this);
            } else {
                do {
                    ensureCompatibility();
                    Graphics2D lGraphics = (Graphics2D)fBufferImage.getGraphics();
                    render(lGraphics);
                    lGraphics.dispose();
                    aGraphics.drawImage(fBufferImage, 0, 0, this);
                } while (((VolatileImage)fBufferImage).contentsLost());
            }
        } else {
            render((Graphics2D)aGraphics);
        }
    }

    public final void update(Graphics aGraphics) {
        paint(aGraphics);
    }

    public abstract void render(Graphics2D aGraphics);
}
