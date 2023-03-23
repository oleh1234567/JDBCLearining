package transactions;

import java.sql.*;

public class MyTransaction {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql:mydatabase";
        String user = "postgres";
        String password = "ilikerap2009";
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);
            String sql = "UPDATE products " +
                    "SET name = ?, department = ? " +
                    "WHERE id = ?";

            try(PreparedStatement preparedStatement =
                    connection.prepareStatement(sql)){
                preparedStatement.setString(1, "Rosehenka");
                preparedStatement.setString(2, "Zelenska");
                preparedStatement.setInt(3, 14);
                preparedStatement.addBatch();

                preparedStatement.setString(1, "Poroshenko");
                preparedStatement.setString(2, "Nikolsiy");
                preparedStatement.setInt(3, 15);
                preparedStatement.addBatch();

                preparedStatement.setString(1, "gfgfgf");
                preparedStatement.setString(2, "Neeks");
                preparedStatement.setInt(3, 16);
                preparedStatement.addBatch();

                preparedStatement.executeBatch();
            }

            /*try(Statement statement =
                    connection.createStatement()){
                String addConstraint = "ALTER TABLE products ALTER COLUMN department SET NOT NULL;";
                statement.execute(addConstraint);
            }*/

            connection.commit();

        } catch (Exception e) {
            System.out.println("Transaction failed. Rolling back.");
            e.printStackTrace();
            if (connection != null) {
                connection.rollback();
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
