package jmx;

public class Points implements PointsMBean{

    private static long points_cnt = 0;
    private static long number_of_odds = 0;

    @Override
    public long num_of_points() {
        return points_cnt;
    }

    @Override
    public long num_of_odd_points() {
        return number_of_odds;
    }

    @Override
    public void warning() {

    }

    public static void incrementPoints_cnt() {
        points_cnt += 1;
    }
    public static void incrementOddPoints_cnt() {
        number_of_odds += 1;
    }
}
