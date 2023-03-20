package transactions;

import java.sql.*;

public class Transactions {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql:mydatabase";
        String user = "postgres";
        String password = "ilikerap2009";

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);

            try (Statement statement1 = connection.createStatement()) {
                statement1.executeUpdate("UPDATE people SET name = 'John' WHERE id = 123");
            }

            try (Statement statement2 = connection.createStatement()) {
                statement2.executeUpdate("UPDATE people SET name = 'Gary' WHERE id = 456");
            }

            connection.commit();
        } catch (SQLException e) {
            System.out.println("Transaction failed. Rolling back.");
            e.printStackTrace();

            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
