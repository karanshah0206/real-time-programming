package cruise;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Panel;

import javax.swing.JButton;

import util.MakeButton;

public class CarSimulatorController extends Panel implements Runnable, ICarSpeed {
    public final static int TICKS_PER_SECOND = 5;

    private SimulatorSpecifications fSimSpecs;
    private ICruiseControl fCruiseController;
    private SpeedometerCanvas fSpeedometerView;
    private JButton fEngine, fAccelerate, fBrake;
    private double fCurrentSpeed, fDistanceTravelled, fThrottleValue, fBrakeValue;
    private boolean fIgnitionOn;
    private Thread fEnginThread;

    private synchronized void engineOnOff() {
        if (fIgnitionOn) {
            fEnginThread = null;
            fIgnitionOn = false;
            fEngine.setText("Engine On");
            fSpeedometerView.clear();
            fAccelerate.setEnabled(false);
            fBrake.setEnabled(false);
            fSpeedometerView.setIgnitionOff();

            if (fCruiseController != null) {
                fCruiseController.engineOff();
            }
        } else {
            fIgnitionOn = true;
            fEngine.setText("Engine Off");
            fAccelerate.setEnabled(true);
            fBrake.setEnabled(true);
            fSpeedometerView.setIgnitionOn();

            if (fEnginThread == null) {
                fEnginThread = new Thread(this);
                fEnginThread.start();
            }

            if (fCruiseController != null) {
                fCruiseController.engineOn();
            }
        }
    }

    private synchronized void accelerate() {
        if (fThrottleValue < fSimSpecs.getMaxThrottle()) {
            fBrakeValue = 0;
            fThrottleValue = Math.min(fThrottleValue + 1, fSimSpecs.getMaxBrake());

            fSpeedometerView.setThrottle(fThrottleValue);
        }
    }

    private synchronized void brake() {
        if (fBrakeValue < fSimSpecs.getMaxBrake()) {
            fThrottleValue = 0;
            fBrakeValue = Math.min(fBrakeValue + 1, fSimSpecs.getMaxBrake());

            if (fCruiseController != null) {
                fCruiseController.brake();
            }

            fSpeedometerView.setBrake(fBrakeValue);
        }
    }

    private void setupSpeedometerView() {
        setLayout(new GridBagLayout());
        GridBagConstraints lConstraints = new GridBagConstraints();
        lConstraints.fill = GridBagConstraints.HORIZONTAL;

        fSpeedometerView = new SpeedometerCanvas(
            fSimSpecs.getMaxSpeed(), fSimSpecs.getMaxThrottle(),
            fSimSpecs.getMaxBrake(), fSimSpecs.getUnitsPerTick()
        );
        lConstraints.gridx = 0;
        lConstraints.gridy = 0;
        lConstraints.gridheight = 5;
        lConstraints.gridwidth = 3;
        add(fSpeedometerView, lConstraints);

        fEngine = MakeButton.createButton("Engine On", e -> engineOnOff(), 40);
        lConstraints.gridx = 0;
        lConstraints.gridy = 6;
        lConstraints.gridheight = 1;
        lConstraints.gridwidth = 1;
        add(fEngine, lConstraints);

        fAccelerate = MakeButton.createButton("Accelerate", e -> accelerate(), 40);
        fAccelerate.setEnabled(false);
        lConstraints.gridx = 1;
        add(fAccelerate, lConstraints);

        fBrake = MakeButton.createButton("Brake", e -> brake(), 40);
        fBrake.setEnabled(false);
        lConstraints.gridx = 2;
        add(fBrake, lConstraints);
    }

    public CarSimulatorController(SimulatorSpecifications aSimulatorSpecifications) {
        super();

        fSimSpecs = aSimulatorSpecifications;

        fCurrentSpeed = 0;
        fDistanceTravelled = 0;
        fThrottleValue = 0;
        fBrakeValue = 0;
        fIgnitionOn = false;

        setupSpeedometerView();
    }

    public synchronized double getSpeed() {
        return fCurrentSpeed;
    }

    public int getMaxSpeed() {
        return (int)fSimSpecs.getMaxSpeed();
    }

    public double drag() {
        return fSimSpecs.getDrag(fCurrentSpeed);
    }

    public void setCruiseController(ICruiseControl aController) {
        fCruiseController = aController;
    }

    public synchronized void setThrottle(double aThrottleValue) {
        if (aThrottleValue < 0) {
            fBrakeValue = Math.min(
                aThrottleValue / (Math.pow(fSimSpecs.getMaxThrottle(), 2) / -fSimSpecs.getMaxBrake()),
                fSimSpecs.getMaxBrake() / 2
            );
        } else {
            fBrakeValue = 0;
        }

        fThrottleValue = Math.max(0, Math.min(fSimSpecs.getMaxThrottle(), aThrottleValue));

        fSpeedometerView.setBrake(fBrakeValue);
        fSpeedometerView.setThrottle(fThrottleValue);
    }

    public void run() {
        try {
            synchronized(this) {
                while (fEnginThread != null) {
                    wait(1000 / TICKS_PER_SECOND);

                    double lRelativeAcceleration = fThrottleValue - drag() - fBrakeValue;

                    fCurrentSpeed = Math.max(0, Math.min(
                        fSimSpecs.getMaxSpeed(),
                        fCurrentSpeed + (lRelativeAcceleration * 3.6) / TICKS_PER_SECOND
                    ));
                    fEngine.setEnabled((int)fCurrentSpeed == 0);
                    fSpeedometerView.setSpeed(fCurrentSpeed);

                    fDistanceTravelled += fCurrentSpeed / TICKS_PER_SECOND;
                    fSpeedometerView.setDistance(fDistanceTravelled);

                    if (fBrakeValue > 0) {
                        fBrakeValue -= (fBrakeValue * 0.5) / TICKS_PER_SECOND;
                        fSpeedometerView.setBrake(fBrakeValue);
                    }

                    if (fThrottleValue > 0) {
                        fThrottleValue -= (fThrottleValue * 0.05) / TICKS_PER_SECOND;
                        fSpeedometerView.setThrottle(fThrottleValue);
                    }
                }
            }
        } catch (InterruptedException e) {
            // intentionally empty
        } finally {
            fCurrentSpeed = 0;
            fDistanceTravelled = 0;
            fThrottleValue = 0;
            fBrakeValue = 0;
            fSpeedometerView.clear();
        }
    }
}
