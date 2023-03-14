package jdbcexample;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class NewExample {
    public static void main(String[] args) {
        Map<String, Integer> duplicates = new HashMap<>();
        String url = "jdbc:postgresql:mydatabase";
        String user = "postgres";
        String password = "ilikerap2009";

        try (Connection connection =
                     DriverManager.getConnection(url, user, password)) {
            try (Statement statement = connection.createStatement()) {

                String[] usernames = {"loh", "debil", "vasya", "govnenchuk", "petya"};

                //insertIntoTable(statement, usernames);


                getAndPrintDuplicates(duplicates, statement);
                //todo:: upgrade this method to remove duplicates with 3 or more occurrences
                //removeDuplicates(duplicates, statement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertIntoTable(Statement statement, String... usernames) throws SQLException {
        StringBuilder builder = new StringBuilder();

        builder.append("INSERT INTO test(username) VALUES ");

        for (String username : usernames) {
            builder.append("('");
            builder.append(username);
            builder.append("'), ");
        }
        builder.deleteCharAt(builder.lastIndexOf(","));

        String sql = builder.toString();

        statement.execute(sql);
    }

    private static void getAndPrintDuplicates(Map<String, Integer> duplicates,
                                              Statement statement) throws SQLException {
        String getDuplicatesSQL = "SELECT id, username" +
                " FROM test" +
                " WHERE username IN (" +
                " SELECT username " +
                " FROM test" +
                " GROUP BY username" +
                " HAVING COUNT(*) > 1);";

        try (ResultSet result = statement.executeQuery(getDuplicatesSQL)) {
            System.out.println("*************dublicates*************");
            while (result.next()) {
                int id = result.getInt(1);
                String username = result.getString("username");
                duplicates.put(username, id);
                System.out.format("%3d %20s%n", id, username);
            }
        }
    }

    private static void removeDuplicates(Map<String, Integer> duplicates, Statement statement) throws SQLException {
        String delete = "DELETE FROM test WHERE id = ";
        for (Integer i : duplicates.values()) {
            statement.execute(delete + i);
        }
    }
}
