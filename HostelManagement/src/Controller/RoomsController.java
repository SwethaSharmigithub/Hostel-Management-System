package Controller;

import DAO.RoomsDAO;
import Model.Rooms;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class RoomsController {
    private final RoomsDAO roomsDAO;

    public RoomsController(Connection connection) {
        this.roomsDAO = new RoomsDAO(connection);
    }

    public void addRoom(Rooms room) {
        try {
            roomsDAO.addRoom(room);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Rooms> getAllRooms() {
        try {
            return roomsDAO.getAllRooms();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean roomExists(int roomNumber) {
        try {
            return roomsDAO.roomExists(roomNumber);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateRoom(int roomNumber,int occupied) {
        try {
            roomsDAO.updateRoom(roomNumber,occupied);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateRoomType(int roomNumber, String roomType) {
        try {
            roomsDAO.updateRoomType(roomNumber, roomType);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateRoomPrice(int roomNumber, double price) {
        try {
            roomsDAO.updateRoomPrice(roomNumber, price);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRoom(int roomNumber) {
        try {
            roomsDAO.deleteRoom(roomNumber);
            System.out.println("Room deleted successfully!");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Cannot delete room. It has associated bookings.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isRoomCapacityExceeded(int roomNumber) {
        try {
            return roomsDAO.isRoomCapacityExceeded(roomNumber);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public int getRoomOccupancy(int roomNumber) throws SQLException {
        return roomsDAO.getRoomOccupancy(roomNumber);
    }

    public int getRoomCapacity(int roomNumber) throws SQLException {
        return roomsDAO.getRoomCapacity(roomNumber);
    }

    public List<Rooms> getVacantRooms() {
        try {
            return roomsDAO.getVacantRooms();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isRoomAvailable(int roomNumber) {
        try {
            return roomsDAO.isRoomAvailable(roomNumber);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public BigDecimal getRoomPrice(int roomNumber) {
        try {
            return roomsDAO.getRoomPrice(roomNumber);
        } catch (SQLException e) {
            e.printStackTrace();
            return BigDecimal.ZERO; 
        }
    }

    public void updateRoomOccupancy(int roomNumber, int isOccupied) {
        try {
            roomsDAO.updateRoom(roomNumber, isOccupied);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
