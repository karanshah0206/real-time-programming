package traffic;

public class TrafficLightsSimulation {
    private static void createAndShowGUI() {
        (new IntersectionFrame("Traffic Lights")).setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
