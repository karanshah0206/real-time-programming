package cruise;

public class Simulator {
    private static void createAndShowSimulator(SimulatorSpecifications aSimulatorSpecifications) {
        (new SimulatorFrame("Cruise Control Simulator", aSimulatorSpecifications)).setVisible(true);
    }

    public static void main(String[] args) {
        // Specifications for Audi A4
        SimulatorSpecifications lAudiA4 = new SimulatorSpecifications(240, 5, 10, 1500, 0.31, 2.12, 1.2, 20);

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowSimulator(lAudiA4);
            }
        });
    }
}
