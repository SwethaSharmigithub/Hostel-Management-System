package Model;

import java.math.BigDecimal;
import java.util.Date; 

public class Booking {
    private int bookingID;
    private int host_ID;
    private int roomNumber;
    private Date bookedDate;
    private BigDecimal price; 
	private int hostIdByUsername;
	private int roomNumber2;
	private Date currentDate;
	private BigDecimal zero;

    
    public Booking(int bookingID, int host_ID, int roomNumber, Date bookedDate, BigDecimal price) {
        this.bookingID = bookingID;
        this.host_ID = host_ID;
        this.roomNumber = roomNumber;
        this.bookedDate = bookedDate;
        this.price = price; 
    }

    public Booking(int hostIdByUsername, int roomNumber2, Date currentDate, BigDecimal zero) {
		
    	this.hostIdByUsername=hostIdByUsername;
    	this.roomNumber2=roomNumber2;
    	this.currentDate=currentDate;
    	this.zero=zero;
	}

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getHost_ID() {
        return host_ID;
    }

    public void setHost_ID(int host_ID) {
        this.host_ID = host_ID;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Date getBookedDate() {
        return bookedDate;
    }

    public void setBookedDate(Date bookedDate) {
        this.bookedDate = bookedDate;
    }

    public BigDecimal getPrice() { 
        return price;
    }

    public void setPrice(BigDecimal price) { 
        this.price = price;
    }
}
