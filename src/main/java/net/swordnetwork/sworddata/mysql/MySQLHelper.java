package net.swordnetwork.sworddata.mysql;

import java.sql.*;
import java.util.Optional;
import java.util.UUID;

public class MySQLHelper implements AutoCloseable {

    private final Connection connection;
    private final String table = "sword_data";;

    public MySQLHelper(String host, int port, String databaseName, String username, String password) throws SQLException {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + databaseName;
        connection = DriverManager.getConnection(url, username, password);

        query("CREATE TABLE IF NOT EXISTS " + table + " (uuid UUID64, data JSON)");
    }

    public boolean doesPlayerExist(UUID uuid) {
        Optional<ResultSet> result = query("SELECT * FROM " + table + "WHERE UUID=?", uuid.toString());

        if (result.isPresent()) {
            try {
                if (result.get().next()) {
                    return true;
                }
            } catch (SQLException ignored) {}
        }

        return false;
    }

    private Optional<ResultSet> query(String query, String... data) {
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            statement = connection.prepareStatement(query);

            for (int i = 0; i < data.length; i++) {
                statement.setString(i + 1, data[i]);
            }

            if (statement.execute()) {
                result = statement.getResultSet();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ignored) {}

                statement = null;
            }
        }

        return Optional.ofNullable(result);
    }

    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ignored) {}
    }
}
