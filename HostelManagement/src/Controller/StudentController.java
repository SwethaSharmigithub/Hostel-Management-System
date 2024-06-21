package Controller;

import DAO.StudentDAO;
import Model.StudentModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class StudentController {
    private StudentDAO studentDAO;

    public StudentController(Connection connection) {
        this.studentDAO = new StudentDAO(connection);

    }

    public StudentModel getStudentByUsername(String username) throws SQLException {
        return studentDAO.getStudentByUsername(username);
    }

    public int getHostIdByUsername(String username) throws SQLException {
        return studentDAO.getHostIdByUsername(username);
    }

    public boolean login(String username, String password) {
        try {
            return studentDAO.login(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void register(StudentModel student) {
        try {
            studentDAO.register(student);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateStudent(String username, String newName, int newAge, String newHostellerType) throws SQLException {
        return studentDAO.updateStudent(username, newName, newAge, newHostellerType);
    }

    public void deleteStudent(String username) {
        try {
            studentDAO.deleteStudent(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<StudentModel> getAllStudents() {
        try {
            return studentDAO.getAllStudents();
        } catch (SQLException e) {
            e.printStackTrace();
            return null; 
        }
    }
}
