package DAO;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Model.Booking;
import java.util.Date;

public class BookingDAO {
    private Connection connection;

    public BookingDAO(Connection connection) {
        this.connection = connection;
    }

    public void addBooking(Booking booking) throws SQLException {
        String query = "INSERT INTO Booking (bookingID, host_ID, roomNumber, bookedDate, price) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, booking.getBookingID());
            preparedStatement.setInt(2, booking.getHost_ID());
            preparedStatement.setInt(3, booking.getRoomNumber());
            java.sql.Date sqlDate = new java.sql.Date(booking.getBookedDate().getTime());
            preparedStatement.setDate(4, sqlDate);

            preparedStatement.setBigDecimal(5, booking.getPrice());
            preparedStatement.executeUpdate();
        }
    }

    public Booking getBookingById(int bookingID) throws SQLException {
        String query = "SELECT * FROM Booking WHERE bookingID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bookingID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int host_ID = resultSet.getInt("host_ID");
                int roomNumber = resultSet.getInt("roomNumber");
                Date bookedDate = resultSet.getDate("bookedDate");
                BigDecimal price = resultSet.getBigDecimal("price");
                return new Booking(bookingID, host_ID, roomNumber, bookedDate, price);
            }
        }
        return null;
    }

    public List<Booking> getAllBookings() throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM Booking";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int bookingID = resultSet.getInt("bookingID");
                int host_ID = resultSet.getInt("host_ID");
                int roomNumber = resultSet.getInt("roomNumber");
                Date bookedDate = resultSet.getDate("bookedDate");
                BigDecimal price = resultSet.getBigDecimal("price");
                bookings.add(new Booking(bookingID, host_ID, roomNumber, bookedDate, price));
            }
        }
        return bookings;
    }

    public void updateBooking(Booking booking) throws SQLException {
        String query = "UPDATE Booking SET host_ID = ?, roomNumber = ?, bookedDate = ?, price = ? WHERE bookingID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, booking.getHost_ID());
            preparedStatement.setInt(2, booking.getRoomNumber());

            // Convert java.util.Date to java.sql.Date
            java.sql.Date sqlDate = new java.sql.Date(booking.getBookedDate().getTime());
            preparedStatement.setDate(3, sqlDate);

            preparedStatement.setBigDecimal(4, booking.getPrice());
            preparedStatement.setInt(5, booking.getBookingID());
            preparedStatement.executeUpdate();
        }
    }

    public boolean deleteBooking(int bookingId) throws SQLException {
        String sql = "DELETE FROM Booking WHERE bookingID = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, bookingId);
            
            int rowsDeleted = statement.executeUpdate();
            
            if (rowsDeleted > 0) {
                return true; 
            } else {
                return false; 
            }
        } catch (SQLException e) {
            System.err.println("Failed to delete booking: " + e.getMessage());
            throw e; 
        }
    }
    
    public boolean isRoomAvailable(int roomNumber) throws SQLException {
        String query = "SELECT COUNT(*) FROM Booking WHERE roomNumber = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, roomNumber);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int bookingsCount = resultSet.getInt(1);
                return bookingsCount == 0; 
            }
        }

        return false; 
    }

    public boolean bookRoom(String username, int roomNumber, BigDecimal roomPrice) throws SQLException {
        String query = "INSERT INTO Booking (host_ID, roomNumber, bookedDate, price) VALUES (?, ?, ?, ?)";
        java.util.Date currentDate = new java.util.Date();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, getHostIdByUsername(username));
            statement.setInt(2, roomNumber);
            java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
            statement.setDate(3, sqlDate);

            statement.setBigDecimal(4, roomPrice);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; 
        }
    }


    private int getHostIdByUsername(String username) throws SQLException {
        String query = "SELECT host_ID FROM Hosteller WHERE username = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("host_ID");
            }
        }

        return -1; 
    }

}
