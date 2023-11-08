package multithreading;

public class MultiThreading {
    private static void createAndShowGUI() {
        (new MultiThreadingFrame("Multithreading")).setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
