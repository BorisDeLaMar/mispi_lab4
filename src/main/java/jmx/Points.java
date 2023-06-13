package jmx;

import javax.faces.bean.ManagedBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;


@ManagedBean
public class Points extends NotificationBroadcasterSupport implements PointsMBean{

    /*private MBeanServer mbs = null;

    public Points() {
        mbs = ManagementFactory.getPlatformMBeanServer();
        try {
            ObjectName name = new ObjectName("jmx:type=Points");
            mbs.registerMBean(this, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public void checkCoordinates(int x, int y) {
        // Check if x and y coordinates are within the bounds of the coordinate plane
        if (x < -5 || x > 5 || y < -3 || y > 3) {
            // If the coordinates are out of bounds, send a notification
            Notification notification = new Notification(
                    "coordinatesOutOfBounds",
                    this,
                    0,
                    System.currentTimeMillis(),
                    "The coordinates are out of bounds."
            );
            sendNotification(notification);
        }
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo(){
        String[] types = new String[]{
                AttributeChangeNotification.ATTRIBUTE_CHANGE
        };

        String name = AttributeChangeNotification.class.getName();
        String description = "This Point is not in Area";
        MBeanNotificationInfo info = new MBeanNotificationInfo(types, name, description);
        return new MBeanNotificationInfo[]{info};
    }


    private static long points_cnt = 0;
    private long points_cnt_bean = 0;
    private static long number_of_odds = 0;
    private long number_of_odds_bean = 0;
    private static List<Long> points_cnt_hist = new ArrayList<>();
    private static List<Long> points_odd_hist = new ArrayList<>();

    public long getPoints_cnt_bean() {
        points_cnt_bean = points_cnt;
        return points_cnt_bean;
    }

    public long getNumber_of_odds_bean() {
        number_of_odds_bean = number_of_odds;
        return number_of_odds_bean;
    }

    public static long getNumber_of_odds() {
        return number_of_odds;
    }

    public static long getPoints_cnt() {
        return points_cnt;
    }

    @Override
    public long num_of_points() {
        return points_cnt;
    }

    @Override
    public long num_of_odd_points() {
        return number_of_odds;
    }

    public static void warning() {
        System.out.println("Не попал!");
    }

    public static void incrementPoints_cnt() {
        points_cnt += 1;
        points_cnt_hist.add(points_cnt);
    }
    public static void incrementOddPoints_cnt() {
        number_of_odds += 1;
        points_odd_hist.add(number_of_odds);
    }

}
