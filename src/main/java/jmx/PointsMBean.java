package jmx;

public interface PointsMBean {
    long num_of_points();
    long num_of_odd_points();
    long getPoints_cnt_bean();
    long getNumber_of_odds_bean();
    public void checkCoordinates(int x, int y);
}
