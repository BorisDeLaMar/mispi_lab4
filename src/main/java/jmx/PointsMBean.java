package jmx;
import javax.management.ManagedAttribute;
public interface PointsMBean {
    @ManagedAttribute(description="gfdg")
    long num_of_points();
    long num_of_odd_points();
    double inversed_accuracy();
}
