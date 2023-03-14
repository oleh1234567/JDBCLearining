package jdbcexample;

import java.sql.*;

public class JdbcExample {
    public static void main(String[] args) throws ClassNotFoundException {
        String url = "jdbc:postgresql:mydatabase";
        String user = "postgres";
        String password = "ilikerap2009";

        try (Connection connection =
                     DriverManager.getConnection(url, user, password)) {
            try (Statement statement = connection.createStatement()) {
                String sql = "SELECT * FROM products";
                String insert = "INSERT INTO products (name, department, price, weight)" +
                                "VALUES ('MOZART', 'GARBAGE', 1, 10)";
                String update = "UPDATE products " +
                        "SET name = 'Mozart', department = 'Garbage' " +
                        "WHERE id = 9";
                try (ResultSet result = statement.executeQuery(sql)) {
                    System.out.format("%2s %30s %15s %10s %10s%n",
                            "id", "name", "department", "price", "weight");
                    while (result.next()) {
                       int id = result.getInt("id");
                        String name = result.getString("name");
                        String department = result.getString("department");
                        int price = result.getInt("price");
                        int weight = result.getInt("weight");
                        System.out.format("%2d %30s %15s %10d %10d%n",
                                id, name, department, price, weight);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}