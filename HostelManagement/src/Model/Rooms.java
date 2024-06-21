package Model;

import java.math.BigDecimal;

public class Rooms {
    private int roomNumber;
    private int capacity;
    private int occupied;
    private String roomType;
    BigDecimal price; 

    public Rooms() {
    }

    public Rooms(int roomNumber, int capacity, int occupied, String roomType,BigDecimal price) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.occupied = occupied;
        this.roomType = roomType;
        this.price = price;
    }
    
    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getOccupied() {
        return occupied;
    }

    public void setOccupied(int occupied) {
        this.occupied = occupied;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
    
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public String toString() {
        return "Room Number: " + roomNumber + "\n" +
               "Capacity: " + capacity + "\n" +
               "Occupied: " + occupied + "\n" +
               "Room Type: " + roomType + "\n" +
               "Price: " + price + "\n";
    }

}
