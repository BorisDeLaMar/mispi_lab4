import beans.Processor;
import jmx.Points;
import utils.DatabaseConnector;
import utils.DatabaseManager;
import utils.Hit;

import javax.faces.bean.ApplicationScoped;
import javax.management.*;
import java.lang.management.ManagementFactory;
import java.sql.SQLException;

public class TestClass {

    public static void main(String[] args) throws SQLException {
        Processor  processor = new Processor();
        processor.hit.setX(2);
        processor.hit.setY(1);
        processor.hit.setX(3);
        processor.addHit();
    }
}
