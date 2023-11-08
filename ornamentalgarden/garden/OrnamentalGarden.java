package garden;

public class OrnamentalGarden {
    public static int MAX = 20;

    private static void createAndShowGUI() {
        (new OrnamentalGardenFrame("Ornamental Garden")).setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
