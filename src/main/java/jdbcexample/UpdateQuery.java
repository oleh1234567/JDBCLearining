package jdbcexample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateQuery {
    public static void main(String[] args) {
        String url = "jdbc:postgresql:validation";
        String user = "postgres";
        String password = "ilikerap2009";

        try(Connection connection = DriverManager.getConnection(url, user, password)) {
            try(Statement statement = connection.createStatement()){
                String sql = "UPDATE products SET name = 'Gnyda' WHERE id = 9";
                int rowsAffected = statement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
