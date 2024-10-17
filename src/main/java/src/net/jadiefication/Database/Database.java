package src.net.jadiefication.Database;

import org.bukkit.entity.Player;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Database {

    public static Connection connection;

    public static void initDatabase(String databaseName, String dataFolder) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder + "\\" + databaseName + ".db");

            Statement statement = connection.createStatement();
            // Create table if it doesn't exist
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS ignorelist (id INTEGER PRIMARY KEY AUTOINCREMENT, uuid TEXT);");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addToIgnoreList(Player player) {
        UUID uuid = player.getUniqueId();
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO ignorelist (uuid) VALUES (?);");
            ps.setString(1, String.valueOf(uuid));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getIgnoreList() {
        List<String> ignoreList = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT uuid FROM ignorelist;");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ignoreList.add(rs.getString("uuid"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ignoreList;
    }

    public static void closeDatabase() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
