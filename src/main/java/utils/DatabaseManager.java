package utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DatabaseManager {
    private static final String ADD = "INSERT INTO hits (hit, x, y, r, curr_time, execution_time) VALUES (?,?,?,?,?,?)";
    private static final String GET_ALL = "SELECT hit, x, y, r, curr_time, execution_time FROM hits";

    private final Connection connection;

    public DatabaseManager(Connection connection) {
        this.connection = connection;
    }

    public int addNewResult(Hit hit) throws SQLException {
        int rows = 0;
        PreparedStatement addStatement = connection.prepareStatement(ADD);
        addStatement.setString(1, hit.getHit());
        addStatement.setDouble(2, (float) hit.getX());
        addStatement.setDouble(3, (float) hit.getY());
        addStatement.setDouble(4, (float) hit.getR());
        addStatement.setString(5, hit.getCurrentTime());
        addStatement.setInt(6, (int) hit.getExecutionTime());
        rows = addStatement.executeUpdate();
        return rows;
    }

    public List<Hit> getAll() throws SQLException {
        List<Hit> list = new LinkedList<>();
        PreparedStatement getAllStatement = connection.prepareStatement(GET_ALL);
        ResultSet resultSet = getAllStatement.executeQuery();
        while (resultSet.next()) {
            String hit = resultSet.getString("hit");
            double x = round(resultSet.getFloat("x"), 2);
            double y = round(resultSet.getFloat("y"), 2);
            double r = round(resultSet.getFloat("r"), 2);
            String time = resultSet.getString("curr_time");
            long execution_time = resultSet.getInt("execution_time");
            list.add(new Hit(x, y, r, time, execution_time, hit));
        }
        return list;
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}