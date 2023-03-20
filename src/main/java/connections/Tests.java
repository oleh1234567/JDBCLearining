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
        System.out.println(properties);

        try(Connection connection =
                    DriverManager.getConnection(url, properties)){
            try (Statement statement =
                    connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                String sql = "SELECT * FROM products WHERE id = 1";
                try (ResultSet result =
                        statement.executeQuery(sql)){
                    result.moveToInsertRow();
                    result.updateInt(1, 101);
                    result.updateString("name", "Black cola");
                    result.updateString("department", "Drinks");
                    result.updateInt("price", 123);
                    result.updateInt("weight", 1);
                    result.insertRow();

                    result.beforeFirst();

                    while (result.next()){
                        System.out.println(result.getString("name"));
                    }
                }

            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
