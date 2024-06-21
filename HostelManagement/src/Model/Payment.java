package Model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Payment {
    private int paymentID;
    private int bookingID;
    private int host_ID;
    private BigDecimal amount;
    private LocalDate paymentDate;

    public Payment() {
    }

    public Payment(int paymentID, int bookingID, int host_ID, BigDecimal amount, LocalDate paymentDate) {
        this.paymentID = paymentID;
        this.bookingID = bookingID;
        this.host_ID = host_ID;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public int getBookingID() {
        return bookingID;
    }

    public int getHost_ID() {
        return host_ID;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public void setHost_ID(int host_ID) {
        this.host_ID = host_ID;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }
}
