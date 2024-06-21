package DAO;

import Model.StudentModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private Connection connection;

    public StudentDAO(Connection connection) {
        this.connection = connection;
    }

    public void register(StudentModel student) throws SQLException {
        String sql = "INSERT INTO Hosteller (host_ID, name, age, username, password, host_type) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, student.getHost_ID());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setInt(3, student.getAge());
            preparedStatement.setString(4, student.getUsername());
            preparedStatement.setString(5, student.getPassword());
            preparedStatement.setString(6, student.getHost_type());

            preparedStatement.executeUpdate();
        }
    }

    public boolean login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM Hosteller WHERE username = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public StudentModel getStudentByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM Hosteller WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int host_ID = resultSet.getInt("host_ID");
                    String name = resultSet.getString("name");
                    int age = resultSet.getInt("age");
                    String password = resultSet.getString("password");
                    String host_type = resultSet.getString("host_type");
                    return new StudentModel(host_ID, name, age, username, password, host_type);
                }
            }
        }
        return null; 
    }
    
    public boolean updateStudent(String username, String newName, int newAge, String newHostellerType) throws SQLException {
        String query = "UPDATE Hosteller SET name = ?, age = ?, Host_type = ? WHERE username = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newName);
            statement.setInt(2, newAge);
            statement.setString(3, newHostellerType);
            statement.setString(4, username);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; 
        }
    }

    public void deleteStudent(String username) throws SQLException {
        String sql = "DELETE FROM Hosteller WHERE username = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);

            preparedStatement.executeUpdate();
        }
    }

    public List<StudentModel> getAllStudents() throws SQLException {
        List<StudentModel> students = new ArrayList<>();
        String sql = "SELECT * FROM Hosteller";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int host_ID = resultSet.getInt("host_ID");
                    String name = resultSet.getString("name");
                    int age = resultSet.getInt("age");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String host_type = resultSet.getString("host_type");
                    students.add(new StudentModel(host_ID, name, age, username, password, host_type));
                }
            }
        }
        return students;
    }

    public int getHostIdByUsername(String username) throws SQLException {
        String query = "SELECT host_ID FROM Hosteller WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("host_ID");
                }
            }
        }
        return -1;
    }
}
