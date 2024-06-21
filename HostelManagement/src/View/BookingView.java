package View;

import Controller.BookingController;
import Controller.PaymentController;
import Model.Booking;
import Model.Payment;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class BookingView {
    private BookingController bookingController;
    private Scanner scanner;
    private PaymentController paymentController;
    private PaymentView paymentView;

    public BookingView(BookingController bookingController, Scanner scanner, PaymentController paymentController, PaymentView paymentView) {
        this.bookingController = bookingController;
        this.scanner = scanner;
        this.paymentController = paymentController;
        this.paymentView = paymentView;
    }

    public void displayBookingOptions(int roomNo, String username, int host_ID) throws SQLException {
        boolean exit = false;

        try {
            while (!exit) {
                System.out.println("1. View Booking Status");
                System.out.println("2. Delete Booking");
                System.out.println("3. Make a payment");
                System.out.println("4. Display Payment Status");
                System.out.println("5. Exit");
                System.out.println("Enter your choice:");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        displayBookingStatus();
                        break;
                    case 2:
                        deleteBooking();
                        break;
                    case 3:
                        makePayment(roomNo, username);
                        break;
                    case 4:
                        displayPaymentStatus(username); 
                        break;
                    case 5:
                        System.out.println("Exiting booking options.");
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            }
        }
        catch(Exception e) {
        	System.out.println(e.getMessage());
        }
    }

    public void displayBookingStatus() {
        try {
            List<Booking> bookings = bookingController.getAllBookings();
            System.out.println("Booking Status:");
            if (bookings.isEmpty()) {
                System.out.println("No bookings available.");
            } 
            else {
                for (Booking booking : bookings) {
                    System.out.println("Booking ID: " + booking.getBookingID());
                    System.out.println("Student ID: " + booking.getHost_ID());
                    System.out.println("Room Number: " + booking.getRoomNumber());
                    System.out.println("Booked Date: " + booking.getBookedDate());
                    System.out.println("------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteBooking() {
        try {
            System.out.println("Enter Booking ID to delete:");
            int bookingID = scanner.nextInt();
            boolean deleted = bookingController.deleteBooking(bookingID);
            if (deleted) {
                System.out.println("Booking deleted successfully!");
            } else {
                System.out.println("Failed to delete booking. Booking ID not found.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void makePayment(int roomNo, String username) {
        try {
            Payment payment = paymentView.getPaymentDetailsFromUser(username);
            paymentController.addPayment(payment, roomNo, roomNo, username);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void bookRoom(String username, int roomNumber) {
        try {
            boolean isRoomAvailable = bookingController.isRoomAvailable(roomNumber);

            if (isRoomAvailable) {
               
                boolean bookingSuccess = bookingController.bookRoom(username, roomNumber);

                if (bookingSuccess) {
                    System.out.println("Room booked successfully!");

                } else {
                    System.out.println("Failed to book room. Please try again later.");
                }
            } else {
                System.out.println("Room is not available. Choose another room.");
            }
        } catch (SQLException e) {
            System.out.println("Error booking room: " + e.getMessage());
        }
    }

    public void displayPaymentStatus(String username) {
        try {
            int host_ID = bookingController.getHostIdByUsername(username);
            List<Payment> payments = paymentController.getPaymentsByHost_ID(host_ID);
            if (payments.isEmpty()) {
                System.out.println("No payments found for this student.");
            } else {
                System.out.println("Payment Status:");
                for (Payment payment : payments) {
                    System.out.println("Payment ID: " + payment.getPaymentID());
                    System.out.println("Booking ID: "+payment.getBookingID());
                    System.out.println("Hosteller_ID: " + payment.getHost_ID());
                    System.out.println("Amount: " + payment.getAmount());
                    System.out.println("Payment Date: " + payment.getPaymentDate());
                    System.out.println("------------------------");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching payment status: " + e.getMessage());
        }
    }
}
