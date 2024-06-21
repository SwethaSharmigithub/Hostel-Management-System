package DAO;

import Model.Rooms;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomsDAO {
    private Connection connection;

    public RoomsDAO(Connection connection) {
        this.connection = connection;
    }

    public void addRoom(Rooms room) throws SQLException {
        String sql = "INSERT INTO Room (roomNumber, capacity, occupied, roomType, price) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
         
            if (room.getOccupied() < room.getCapacity()) {
                preparedStatement.setInt(1, room.getRoomNumber());
                preparedStatement.setInt(2, room.getCapacity());
                preparedStatement.setInt(3, room.getOccupied()); 
                preparedStatement.setString(4, room.getRoomType());
                preparedStatement.setBigDecimal(5, room.getPrice()); 

                preparedStatement.executeUpdate();
            } else {
             
                System.out.println("Error: Room capacity exceeded.");
            }
        }
    }

    public List<Rooms> getAllRooms() throws SQLException {
        List<Rooms> rooms = new ArrayList<>();
        String sql = "SELECT * FROM Room";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Rooms room = new Rooms(
                        resultSet.getInt("roomNumber"),
                        resultSet.getInt("capacity"),
                        resultSet.getInt("occupied"),
                        resultSet.getString("roomType"),
                        resultSet.getBigDecimal("price")
                );
                rooms.add(room);
            }
        }
        return rooms;
    }

    public BigDecimal getRoomPrice(int roomNumber) throws SQLException {
        String query = "SELECT Price FROM Room WHERE RoomNumber = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, roomNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBigDecimal("Price");
                }
            }
        }
        return null;
    }
    
    public boolean roomExists(int roomNumber) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Room WHERE roomNumber = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, roomNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1) > 0;
            }
        }
    }

    public void updateRoom(int roomNumber,int newOccupied) throws SQLException {
        String sql = "UPDATE Room SET occupied = ? WHERE roomNumber = ?";
   
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, newOccupied);
            preparedStatement.setInt(2, roomNumber);
            preparedStatement.executeUpdate();
        }
    }

    public void updateRoomType(int roomNumber, String roomType) throws SQLException {
        String sql = "UPDATE Room SET roomType = ? WHERE roomNumber = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, roomType);
            preparedStatement.setInt(2, roomNumber);
            preparedStatement.executeUpdate();
        }
    }

    public void updateRoomPrice(int roomNumber, double price) throws SQLException {
        String sql = "UPDATE Room SET price = ? WHERE roomNumber = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, price);
            preparedStatement.setInt(2, roomNumber);
            preparedStatement.executeUpdate();
        }
    }

    public void deleteRoom(int roomNumber) throws SQLException {
        String sql = "DELETE FROM Room WHERE roomNumber = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, roomNumber);
            preparedStatement.executeUpdate();
        }
    }

    public List<Rooms> getVacantRooms() throws SQLException {
        List<Rooms> allRooms = getAllRooms();
        List<Rooms> vacantRooms = new ArrayList<>();
        for (Rooms room : allRooms) {
            if (room.getCapacity() > room.getOccupied()) {
                vacantRooms.add(room);
            }
        }
        return vacantRooms;
    }

    public boolean isRoomAvailable(int roomNumber) throws SQLException {
        return roomExists(roomNumber) && !isRoomCapacityExceeded(roomNumber);
    }

    public boolean isRoomCapacityExceeded(int roomNumber) throws SQLException {
        Rooms room = getRoomByNumber(roomNumber);
        return room != null && room.getOccupied() >= room.getCapacity();
    }

    public Rooms getRoomByNumber(int roomNumber) throws SQLException {
        String sql = "SELECT * FROM Room WHERE roomNumber = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, roomNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Rooms(
                            resultSet.getInt("roomNumber"),
                            resultSet.getInt("capacity"),
                            resultSet.getInt("occupied"),
                            resultSet.getString("roomType"),
                            resultSet.getBigDecimal("price")
                    );
                }
            }
        }
        return null;
    }
    
    public int getRoomOccupancy(int roomNumber) throws SQLException {
        int occupancy = -1; // Default value if room is not found
        String sql = "SELECT occupied FROM Room WHERE roomNumber = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, roomNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    occupancy = resultSet.getInt("occupied");
                }
            }
        }
        return occupancy;
    }

    public int getRoomCapacity(int roomNumber) throws SQLException {
        int capacity = -1; 
        String sql = "SELECT capacity FROM Room WHERE roomNumber = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, roomNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    capacity = resultSet.getInt("capacity");
                }
            }
        }
        return capacity;
    }
}
