package jdbcexample;

import java.sql.*;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class NewExample {
    public static void main(String[] args) {

        String url = "jdbc:postgresql:mydatabase";
        String user = "postgres";
        String password = "ilikerap2009";

        try (Connection connection =
                     DriverManager.getConnection(url, user, password)) {
            try (Statement statement = connection.createStatement()) {

                String[] usernames = {"loh", "debil", "vasya", "govnenchuk", "petya"};

                //insertIntoTable(statement, usernames);

                removeDuplicates(getAndPrintDuplicates(statement), statement);

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

    private static Map<Long, String> getAndPrintDuplicates(Statement statement) throws SQLException {
        Map<Long, String> duplicates = new HashMap<>();

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
                long id = result.getLong(1);
                String username = result.getString("username");
                duplicates.put(id, username);
            }
        }
        System.out.println(duplicates);
        return duplicates;
    }

    private static void removeDuplicates(Map<Long, String> duplicates, Statement statement) throws SQLException {
        String delete = "DELETE FROM test WHERE id = ";
        Set<String> bucket = new HashSet<>(); // here we retrieve at least one element

        for(Map.Entry<Long, String> entry : duplicates.entrySet()){
            if (!bucket.contains(entry.getValue())) {
                bucket.add(entry.getValue());
            } else {
                statement.execute(delete + entry.getKey());
            }
        }
    }
}
