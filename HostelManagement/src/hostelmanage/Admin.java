package hostelmanage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin {
    public static boolean login(String username, String password, Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM admin WHERE username = ? AND password = ?"
        )) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next(); 
        } catch (SQLException e) {
            System.out.println("An error occurred while logging in as admin: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean insertAdmin(String username, String password, Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO admin (username, password) VALUES (?, ?)"
        )) {
            statement.setString(1, username);
            statement.setString(2, password);
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println("An error occurred while inserting admin credentials: " + e.getMessage());
            return false;
        }
    }

}
