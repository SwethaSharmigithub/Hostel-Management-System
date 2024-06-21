package View;

import Model.Payment;
import hostelmanage.DBconnection;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;
import Controller.PaymentController;
import Controller.RoomsController;
import Controller.StudentController;

public class PaymentView {
    private Scanner scanner;
    private Connection connection;
    private PaymentController paymentController;
    private StudentController studentController;
    private RoomsController roomsController;

    public PaymentView(Scanner scanner, Connection connection) {
        this.scanner = scanner;
        this.connection = connection;
        this.studentController = new StudentController(connection);
        this.roomsController = new RoomsController(connection);
        this.paymentController = new PaymentController(connection, studentController, roomsController); 
    }

    public Payment getPaymentDetailsFromUser(String username) {
        Payment payment = new Payment();
        System.out.println("Enter Amount:");
        BigDecimal amount=scanner.nextBigDecimal();
        payment.setAmount(amount);
        scanner.nextLine(); 
        payment.setPaymentDate(LocalDate.now());
        System.out.println("Payment successful!!!!"); 
        return payment;
    }

    public static Connection connectToDatabase() throws SQLException {
        try {
            return DBconnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error connecting to the database.");
        }
    }
}
