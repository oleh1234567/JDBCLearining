package theverynewexample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Example {
    public static void main(String[] args) {
        String url = "jdbc:postgresql:mydatabase";
        String user = "postgres";
        String password = "ilikerap2009";

        try(Connection connection =
                    DriverManager.getConnection(url, user, password)){
            String sql = "UPDATE users SET first_name = ?, last_name = ? WHERE id = ?";
            connection.setAutoCommit(false);
            try(PreparedStatement preparedStatement =
                    connection.prepareStatement(sql)){
                preparedStatement.setString(1, "Loh");
                preparedStatement.setString(2, "Prokopovych");
                preparedStatement.setInt(3, 16);
                preparedStatement.addBatch();

                preparedStatement.setString(1, "Gnydard");
                preparedStatement.setString(2, "Chmozrtovski");
                preparedStatement.setInt(3, 17);
                preparedStatement.addBatch();

                preparedStatement.executeBatch();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
