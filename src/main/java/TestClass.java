import beans.Processor;
import jmx.Points;
import utils.DatabaseConnector;
import utils.DatabaseManager;
import utils.Hit;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.sql.SQLException;

public class TestClass {

    public static void main(String[] args) throws SQLException, MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName points_mbs = new ObjectName("mbeans:type=Points");
        Points points = new Points();
        mbs.registerMBean(points, points_mbs);
        Processor  processor = new Processor();
        processor.hit.setX(2);
        processor.hit.setY(1);
        processor.hit.setX(3);
        processor.addHit();
    }
}
