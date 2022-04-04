package robotLogic;

public class MathOperations {
    private MathOperations() {}

    public static double distance(double x1, double y1, double x2, double y2)
    {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return java.lang.Math.sqrt(diffX * diffX + diffY * diffY);
    }

    public static double angleTo(double fromX, double fromY, double toX, double toY)
    {
        double diffX = toX - fromX;
        double diffY = toY - fromY;

        return asNormalizedRadians(java.lang.Math.atan2(diffY, diffX));
    }

    public static double applyLimits(double value, double min, double max)
    {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    public static double asNormalizedRadians(double angle)
    {
        while (angle < 0)
        {
            angle += 2* java.lang.Math.PI;
        }
        while (angle >= 2* java.lang.Math.PI)
        {
            angle -= 2* java.lang.Math.PI;
        }
        return angle;
    }
}
