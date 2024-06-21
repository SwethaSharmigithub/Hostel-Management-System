package Controller;

import DAO.PaymentDAO;
import Model.Booking;
import Model.Payment;
import Model.StudentModel;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PaymentController {
    private PaymentDAO paymentDAO;
    private StudentController studentController;
    private RoomsController roomsController;

    public PaymentController(Connection connection, StudentController studentController, RoomsController roomsController) {
        this.paymentDAO = new PaymentDAO(connection);
        this.studentController = studentController;
        this.roomsController = roomsController;
    }

    public void addPayment(Payment payment, int bookingID, int host_ID, String username) throws SQLException {
        payment.setBookingID(bookingID);
        payment.setHost_ID(host_ID);
        paymentDAO.addPayment(payment, username);
    }

    public Payment getPaymentById(int paymentID) throws SQLException {
        return paymentDAO.getPaymentsById(paymentID);
    }

    public void updatePayment(Payment payment, int bookingID, int host_ID, String username) throws SQLException {
        payment.setBookingID(bookingID);
        payment.setHost_ID(host_ID);
        paymentDAO.updatePayment(payment, username);
    }

    public void deletePayment(int paymentID) throws SQLException {
        paymentDAO.deletePayment(paymentID);
    }

    public List<Payment> getPaymentsByHost_ID(int host_ID) throws SQLException {
        return paymentDAO.getPaymentsByHost_ID(host_ID);
    }

    public void addPaymentForBooking(Booking booking, String username) throws SQLException {
        StudentModel student = studentController.getStudentByUsername(username);
        if (student != null) {
            int host_ID = student.getHost_ID();
            Payment payment = new Payment();
            payment.setBookingID(booking.getBookingID());
            payment.setHost_ID(host_ID);
            BigDecimal amount = BigDecimal.ZERO;
            BigDecimal price = booking.getPrice();
            if (price != null) {
                amount = price;
            }
            payment.setAmount(amount);
            payment.setPaymentDate(LocalDate.now());
            paymentDAO.addPayment(payment, username);
        } else {
        }
    }

    public int getRoomNumberForPayment(String username) throws SQLException {
        return paymentDAO.getRoomNumberByUsername(username);
    }

    public BigDecimal getCurrentBalance(int roomNumber, String username) throws SQLException {
        BigDecimal totalPaid = getTotalPayments(username);
        BigDecimal roomPrice = roomsController.getRoomPrice(roomNumber);

        if (roomPrice != null) {
            return roomPrice.subtract(totalPaid);
        } else {
            return BigDecimal.ZERO;
        }
    }

    private BigDecimal getTotalPayments(String username) throws SQLException {
        List<Payment> payments = (List<Payment>) paymentDAO.getPaymentByUsername(username);
        BigDecimal totalPaid = BigDecimal.ZERO;
        for (Payment payment : payments) {
            totalPaid = totalPaid.add(payment.getAmount());
        }
        return totalPaid;
    }

    public int getRoomNumberByUsername(String username) throws SQLException {
        return paymentDAO.getRoomNumberByUsername(username);
    }
}
