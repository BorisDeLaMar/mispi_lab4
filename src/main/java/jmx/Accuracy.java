package jmx;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Accuracy implements AccuracyMBean{

    @Override
    public double inversed_accuracy(){
        return (double) Points.getNumber_of_odds()/Points.getPoints_cnt();
    }
}
