package beans;

import jmx.Accuracy;
import utils.DatabaseConnector;
import utils.DatabaseManager;
import utils.Hit;
import jmx.Points;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.management.*;
import java.lang.management.ManagementFactory;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.abs;

public class Processor {
    private DatabaseConnector databaseConnector = new DatabaseConnector("localhost",
            "postgres",  "6291604", "postgres", 5432); //oops :)
    private DatabaseManager databaseManager = new DatabaseManager(databaseConnector.getConnection());

    private long startTime;

    private long hit_flag;

    public long getHit_flag() {
        return hit_flag;
    }
    public void setHit_flag(long flag){
        hit_flag = flag;
    }

    static{
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName points_mbs = null;
        ObjectName accuracy = null;
        try {
            points_mbs = new ObjectName("jmx:type=Points");
            accuracy = new ObjectName("jmx:type=Accuracy");
        } catch (MalformedObjectNameException e) {
            throw new RuntimeException(e);
        }
        Points points = new Points();
        Accuracy accur = new Accuracy();
        try {
            mbs.registerMBean(points, points_mbs);
            mbs.registerMBean(accur, accuracy);
        } catch (InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException e) {
            throw new RuntimeException(e);
        }
    }


    public Hit hit = new Hit();
    public List<Hit> hits = new ArrayList<>();

    public Processor() throws SQLException {
        setHits(databaseManager.getAll());
    }

    public void setHit(Hit hit) {
        this.hit = hit;
    }

    public Hit getHit() {
        return hit;
    }

    public void setHits(List<Hit> hits) {
        this.hits = hits;
    }

    public List<Hit> getHits() {
        List<Hit> outputHits = new ArrayList<>(hits);
        Collections.reverse(outputHits);
        return outputHits;
    }

    public void addHit() throws SQLException {
        startTime = System.currentTimeMillis();

        hit.setHit(checkHit());
        hit.setCurrentTime(LocalDateTime.now().toString());
        hit.setExecutionTime((System.currentTimeMillis() - startTime));
        hits.add(hit);
        databaseManager.addNewResult(hit);
        hit = new Hit(hit.getX(), hit.getY(), hit.getR());
    }

    public String checkHit() {
        double x = hit.getX();
        double y = hit.getY();
        double R = hit.getR();

        if(abs(x) > 5 || abs(y) > 3){
            Points.warning();
        }
        else{
            Points.incrementPoints_cnt();

            System.out.println("Входящие координаты: " + hit.getX() + ", " + hit.getY() + ", " + hit.getR());
            if (checkCircle(x, y, R) || checkRectangle(x, y, R) || checkTriangle(x, y, R)) {
                return "Hit";
            } else {

                Points.incrementOddPoints_cnt();

                return "Miss";
            }
        }
        return "";
    }

    private boolean checkCircle(double x, double y, double R) {
        return (x >= 0) && (y >= 0) && (Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(R, 2));
    }

    private boolean checkRectangle(double x, double y, double R) {
        return (x <= 0) && (x >= -R) && (y >= 0) & (y <= R/2);
    }

    private boolean checkTriangle(double x, double y, double R) {
        return (x >= 0) && (x <= R/2) && (y <= 0) && (y > x - R/2);
    }
}
