package cruise;

public class SimulatorSpecifications {
    private double fMaxSpeed; // km/h
    private double fMaxThrottle; // m/s^2
    private double fMaxBrake; // m/s^s
    private double fWeight; // kg
    private double fDragCoeff; // dimensionless
    private double fCrossSecArea; // m^2
    private double fAirDensity; // kg/m^3
    private double fDragMultiplier;
    private int fUnitsPerTick;

    public SimulatorSpecifications(double aMaxSpeed, double aMaxThrottle, double aMaxBrake, double aWeight,
            double aDragCoeff, double aCrossSecArea, double aAirDensity, int aUnitsPerTick) {
        fMaxSpeed = aMaxSpeed;
        fMaxThrottle = aMaxThrottle;
        fMaxBrake = aMaxBrake;
        fWeight = aWeight;
        fDragCoeff = aDragCoeff;
        fCrossSecArea = aCrossSecArea;
        fAirDensity = aAirDensity;
        fUnitsPerTick = aUnitsPerTick;

        // drag force = drag coefficient * area * air density * relative speed * 0.5 (m^2/s^2)
        fDragMultiplier = fDragCoeff * fCrossSecArea * fAirDensity * 0.5 / fWeight;
    }

    public double getMaxSpeed() {
        return fMaxSpeed;
    }

    public double getMaxThrottle() {
        return fMaxThrottle;
    }

    public double getMaxBrake() {
        return fMaxBrake;
    }

    public double getWeight() {
        return fWeight;
    }

    public double getDragCoeff() {
        return fDragCoeff;
    }

    public double getCrossSecArea() {
        return fCrossSecArea;
    }

    public double getAirDensity() {
        return fAirDensity;
    }

    public double getDragMultiplier() {
        return fDragMultiplier;
    }

    public int getUnitsPerTick() {
        return fUnitsPerTick;
    }

    public double getDrag(double aCurrentSpeed) {
        return getDragMultiplier() * ((aCurrentSpeed * aCurrentSpeed) / 3.6);
    }
}
