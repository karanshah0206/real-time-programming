package cruise;

import java.awt.GridLayout;

import javax.swing.JFrame;

public class SimulatorFrame extends JFrame {
    CarSimulatorController fCarSimulator;
    CruiseControlController fCruiseControl;

    public SimulatorFrame(String aTitle, SimulatorSpecifications aSimulatorSpecifications) {
        super(aTitle);

        JFrame.setDefaultLookAndFeelDecorated(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setLayout(new GridLayout(1, 2, 1, 1));

        fCarSimulator = new CarSimulatorController(aSimulatorSpecifications);
        fCruiseControl = new CruiseControlController(fCarSimulator);

        getContentPane().add(fCarSimulator);
        getContentPane().add(fCruiseControl);
        pack();

        setLocationRelativeTo(null);
    }
}
