package Controller;

import DAO.BookingDAO;
import Model.Booking;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class BookingController {
    private BookingDAO bookingDAO;
    private PaymentController paymentController;
    private StudentController studentController;
    private RoomsController roomsController;

    public BookingController(Connection connection, StudentController studentController, RoomsController roomsController, PaymentController paymentController) {
        this.bookingDAO = new BookingDAO(connection);
        this.studentController = studentController;
        this.roomsController = roomsController;
        this.paymentController = paymentController;
    }

    public Booking getBookingById(int bookingID) {
        try {
            return bookingDAO.getBookingById(bookingID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Booking> getAllBookings() {
        try {
            return bookingDAO.getAllBookings();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteBooking(int bookingId) {
        try {
            return bookingDAO.deleteBooking(bookingId);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public int getHostIdByUsername(String username) throws SQLException {
        return studentController.getHostIdByUsername(username);
    }

    public void addBooking(String username, int roomNumber) {
        try {
            int studentID = studentController.getHostIdByUsername(username);
            if (studentID != -1) {
                int currentOccupancy = roomsController.getRoomOccupancy(roomNumber);
                if (currentOccupancy != -1) {
                    int roomCapacity = roomsController.getRoomCapacity(roomNumber);
                    if (currentOccupancy < roomCapacity) {
                        BigDecimal price = roomsController.getRoomPrice(roomNumber);
                        if (price != null) { // Check if price is not null
                            Booking booking = new Booking(0, studentID, roomNumber, new Date(System.currentTimeMillis()), price);
                            int newOccupancy = currentOccupancy + 1;
                            bookingDAO.addBooking(booking);
                            roomsController.updateRoomOccupancy(roomNumber, newOccupancy);
                            paymentController.addPaymentForBooking(booking, username);
                            System.out.println("Room booked Successfully!!");
                        } else {
                            System.out.println("Price for the room is not available.");
                        }
                    } else {
                        System.out.println("Room is already fully occupied.");
                    }
                } else {
                    System.out.println("Room not found.");
                }
            } else {
                System.out.println("Student not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public boolean isRoomAvailable(int roomNumber) throws SQLException {
        return bookingDAO.isRoomAvailable(roomNumber);
    }

    public boolean bookRoom(String username, int roomNumber) {
        try {
        
            BigDecimal roomPrice = roomsController.getRoomPrice(roomNumber);
            return bookingDAO.bookRoom(username, roomNumber, roomPrice);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    

}
