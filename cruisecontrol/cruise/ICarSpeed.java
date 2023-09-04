package cruise;

public interface ICarSpeed {
    public int getMaxSpeed();
    public double getSpeed();
    public void setThrottle(double aThrottleValue);
    public void setCruiseController(ICruiseControl aController);
    public double drag();
}
