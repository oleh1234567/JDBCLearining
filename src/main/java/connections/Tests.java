package connections;

import java.sql.*;
import java.util.Properties;

public class Tests {
    public static void main(String[] args) {
        String url = "jdbc:postgresql:mydatabase";
        String user = "postgres";
        String password = "ilikerap2009";

        Properties properties = new Properties();
        properties.put("user", "postgres");
        properties.put("password", "ilikerap2009");

        try(Connection connection =
                    DriverManager.getConnection(url, properties)){
            System.out.println(connection.getMetaData().supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE));
            try (Statement statement =
                    connection.createStatement()) {
                connection.setAutoCommit(false);
                String sql = "SELECT * FROM products";
                try (ResultSet result =
                        statement.executeQuery(sql)){
                   /* while (result.next()){
                        int id = result.getInt("id");
                        String name = result.getString("name");
                        String department = result.getString("department");
                        int price = result.getInt("price");
                        int weight = result.getInt("weight");
                        System.out.format("%2d %30s %15s %10d %10d%n",
                                id, name, department, price, weight);
                    }*/
                }

            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
