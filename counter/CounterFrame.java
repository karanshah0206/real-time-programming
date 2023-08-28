package counter;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class CounterFrame extends JFrame {
    private CounterCanvas fCanvas;
    private JButton fIncrementButton;
    private JButton fDecrementButton;

    private void updateButtons() {
        fIncrementButton.setEnabled(fCanvas.canIncrement());
        fDecrementButton.setEnabled(fCanvas.canDecrement());
    }

    private void increment() {
        fCanvas.increment();
        updateButtons();
    }

    private void decrement() {
        fCanvas.decrement();
        updateButtons();
    }

    public CounterFrame(String aTitle) {
        super(aTitle);

        JFrame.setDefaultLookAndFeelDecorated(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints lConstraints = new GridBagConstraints();
        lConstraints.fill = GridBagConstraints.BOTH;

        // draw canvas
        fCanvas = new CounterCanvas();
        lConstraints.gridx = 0;
        lConstraints.gridy = 0;
        lConstraints.gridwidth = 2;
        getContentPane().add(fCanvas, lConstraints);

        // draw increment button
        fIncrementButton = new JButton("Increment");
        fIncrementButton.addActionListener(e -> increment());
        lConstraints.gridy = 1;
        lConstraints.gridwidth = 1;
        lConstraints.weightx = 0.5;
        getContentPane().add(fIncrementButton, lConstraints);

        // draw decement button
        fDecrementButton = new JButton("Decrement");
        fDecrementButton.addActionListener(e -> decrement());
        lConstraints.gridx = 1;
        getContentPane().add(fDecrementButton, lConstraints);

        pack();
        setLocationRelativeTo(null);
        updateButtons();
    }
}
