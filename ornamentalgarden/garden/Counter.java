package garden;

import util.Hardware;
import util.NumberCanvas;

public class Counter {
    private NumberCanvas fView;

    public Counter(NumberCanvas aView) {
        fView = aView;
        fView.setValue(0);
    }

    public void increment() {
        int temp = fView.getValue();
        Hardware.HWinterrupt();
        temp++;
        fView.setValue(temp);
    }
}
