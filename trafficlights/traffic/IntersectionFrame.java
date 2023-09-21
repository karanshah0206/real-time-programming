package traffic;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class IntersectionFrame extends JFrame implements Runnable {
    private static final long serialVersionUID = 1L;

    private TrafficLightsCanvas fEast, fNorth;
    private JButton fStart;
    private Thread fController;

    private void animate() {
        if (fController == null) {
            fController = new Thread(this);
            fController.start();
            fStart.setText("Stop");
        } else {
            fController.interrupt();
            fController = null;
            fEast.setRed();
            fNorth.setRed();
            fStart.setText("Start");
        }
    }

    public IntersectionFrame(String aTitle) {
        super(aTitle);
        
        fEast = new TrafficLightsCanvas("East");
        fNorth = new TrafficLightsCanvas("North");
        fStart = new JButton("Start");
        fStart.setFont(new Font("Arial", Font.BOLD, 36));
        fStart.addActionListener(e -> animate());

        JFrame.setDefaultLookAndFeelDecorated(true);
        setResizable(false);

        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints lConstraints = new GridBagConstraints();
        lConstraints.fill = GridBagConstraints.HORIZONTAL;

        lConstraints.gridx = 0;
        lConstraints.gridy = 0;
        lConstraints.gridwidth = 1;
        getContentPane().add(fEast, lConstraints);

        lConstraints.gridx = 1;
        getContentPane().add(fNorth, lConstraints);

        lConstraints.gridx = 0;
        lConstraints.gridy = 1;
        lConstraints.gridwidth = 2;
        getContentPane().add(fStart, lConstraints);

        pack();
        setLocationRelativeTo(null);
    }

    public void run() {
        try {
            synchronized(this) {
                while (fController != null) {
                    Thread.sleep(500);
                    fNorth.setGreen();
                    Thread.sleep(4000);
                    fNorth.setAmber();
                    Thread.sleep(1000);
                    fNorth.setRed();
                    Thread.sleep(500);
                    fEast.setGreen();
                    Thread.sleep(4000);
                    fEast.setAmber();
                    Thread.sleep(1000);
                    fEast.setRed();
                }
            }
        } catch (InterruptedException e) {
            // intentionally empty
        } finally {
            fController = null;
        }
    }
}
