package DAO;

import Model.Payment;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {
    private Connection connection;

    public PaymentDAO(Connection connection) {
        this.connection = connection;
    }

    public void addPayment(Payment payment, String username) throws SQLException {
        String query = "INSERT INTO Payment (BookingID, Host_ID, Amount, PaymentDate) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            int bookingID = getBookingID(username);
            int host_ID = getHostID(username);
            statement.setInt(1, bookingID);
            statement.setInt(2, host_ID);
            statement.setBigDecimal(3, payment.getAmount());
            statement.setObject(4, payment.getPaymentDate());
            statement.executeUpdate();
        }
    }

    private int getBookingID(String username) throws SQLException {
        String query = "SELECT BookingID FROM Booking WHERE Host_ID = (SELECT Host_ID FROM Hosteller WHERE Username = ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("BookingID");
                }
            }
        }
        return -1; 
    }

    private int getHostID(String username) throws SQLException {
        String query = "SELECT Host_ID FROM Hosteller WHERE Username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("Host_ID");
                }
            }
        }
        return -1; 
    }

    public Payment getPaymentsById(int paymentID) throws SQLException {
        String query = "SELECT * FROM Payment WHERE PaymentID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, paymentID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractPaymentFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    public void updatePayment(Payment payment, String username) throws SQLException {
        String query = "UPDATE Payment SET BookingID = ?, Host_ID = ?, Amount = ?, PaymentDate = ? WHERE PaymentID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            int bookingID = getBookingID(username);
            int host_ID = getHostID(username);
            statement.setInt(1, bookingID);
            statement.setInt(2, host_ID);
            statement.setBigDecimal(3, payment.getAmount());
            statement.setObject(4, payment.getPaymentDate());
            statement.setInt(5, payment.getPaymentID());
            statement.executeUpdate();
        }
    }

    public void deletePayment(int paymentID) throws SQLException {
        String query = "DELETE FROM Payment WHERE PaymentID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, paymentID);
            statement.executeUpdate();
        }
    }

    public List<Payment> getAllPayments() throws SQLException {
        List<Payment> payments = new ArrayList<>();
        String query = "SELECT * FROM Payment";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Payment payment = extractPaymentFromResultSet(resultSet);
                payments.add(payment);
            }
        }
        return payments;
    }

    private Payment extractPaymentFromResultSet(ResultSet resultSet) throws SQLException {
        int paymentID = resultSet.getInt("PaymentID");
        int bookingID = resultSet.getInt("BookingID");
        int host_ID = resultSet.getInt("Host_ID");
        BigDecimal amount = resultSet.getBigDecimal("Amount");
        
        java.sql.Date paymentDate = resultSet.getDate("PaymentDate");
        LocalDate localDate = null;
        if (paymentDate != null) {
            localDate = paymentDate.toLocalDate();
        }
        return new Payment(paymentID, bookingID, host_ID, amount, localDate);
    }

    public Payment getPaymentByUsername(String username) throws SQLException {
        String query = "SELECT p.* FROM Payment p JOIN Hosteller s ON p.Host_ID = s.Host_ID WHERE s.Username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractPaymentFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    public List<Payment> getPaymentsByHost_ID(int host_ID) throws SQLException {
        List<Payment> payments = new ArrayList<>();
        String query = "SELECT * FROM Payment WHERE Host_ID IN (SELECT Host_ID FROM Hosteller WHERE Host_ID = ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, host_ID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Payment payment = extractPaymentFromResultSet(resultSet);
                    payments.add(payment);
                }
            }
        }
        return payments;
    }
    

    public int getRoomNumberByUsername(String username) throws SQLException {
        String query = "SELECT RoomNumber FROM Booking WHERE Host_ID = (SELECT Host_ID FROM Hosteller WHERE Username = ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("RoomNumber");
                }
            }
        }
        return -1;
    }

}
