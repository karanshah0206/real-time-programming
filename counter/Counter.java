package counter;

public class Counter {
    public static void createAndShowGUI() {
        (new CounterFrame("Counter")).setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
