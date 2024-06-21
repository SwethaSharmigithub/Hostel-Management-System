package View;

import Controller.RoomsController;
import Controller.BookingController;
import Controller.PaymentController;
import Controller.StudentController;
import Model.Rooms;
import Model.StudentModel;
import hostelmanage.DBconnection;
import View.PaymentView;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class RoomsView {
    private RoomsController roomsController;
    private BookingController bookingController;
    private StudentController studentController;
    private Scanner scanner;
    private Connection connection;

    public RoomsView(RoomsController roomsController, BookingController bookingController, StudentController studentController, Scanner scanner) {
        this.roomsController = roomsController;
        this.bookingController = bookingController;
        this.studentController = studentController;
        this.scanner = scanner;
        try {
            this.connection = DBconnection.getConnection(); 
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
    }
    public void displayMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("-----------------Room Management---------------");
            System.out.println("1. Add rooms");
            System.out.println("2. View rooms");
            System.out.println("3. Update rooms");
            System.out.println("4. Delete rooms");
            System.out.println("5. View Room Status");
            System.out.println("6. View Booking Status");
            System.out.println("7. View Registered Student Details");
            System.out.println("8. Exit");
            System.out.println("Enter your choice:");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    addRoom();
                    break;
                case 2:
                    viewRooms();
                    break;
                case 3:
                    updateRoom();
                    break;
                case 4:
                    deleteRoom();
                    break;
                case 5:
                    viewRoomStatus();
                    break;
                case 6:
                    viewBookingStatus();
                    break;
                case 7:
                    viewStudentRegistrationDetails();
                    break;
                case 8:
                    System.out.println("Exiting Room management.");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        }
    }

    private void addRoom() {
        System.out.println("Enter room details:");

        System.out.println("Room Number:");
        int roomNumber = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Room Capacity:");
        int capacity = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Rooms Occupied:");
        int occupied = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Room Type:");
        String roomType = scanner.nextLine();

        System.out.println("Price:");
        BigDecimal price = scanner.nextBigDecimal();
        scanner.nextLine();

        roomsController.addRoom(new Rooms(roomNumber, capacity, occupied, roomType, price));
        System.out.println("Room added successfully!");
    }


    private void viewRooms() {
        List<Rooms> rooms = roomsController.getAllRooms();

        System.out.println("List of Rooms:");
        for (Rooms room : rooms) {
            System.out.println("Room Number: " + room.getRoomNumber());
            System.out.println("Capacity: " + room.getCapacity());
            System.out.println("Occupied: " + room.getOccupied());
            System.out.println("Room Type: " + room.getRoomType());
            System.out.println("Price: " + room.getPrice());
            System.out.println("------------------------------");
        }
    }

    private void updateRoom() {
        System.out.println("Enter the Room Number to update:");
        int roomNumber = scanner.nextInt();
        scanner.nextLine();

        if (roomsController.roomExists(roomNumber)) {
            System.out.println("Select which details to update:");
            System.out.println("1. Capacity");
            System.out.println("2. Room Type");
            System.out.println("3. Price");
            System.out.println("Enter your choice:");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter updated Capacity:");
                    int updatedCapacity = scanner.nextInt();
                    scanner.nextLine();
                    roomsController.updateRoom(roomNumber, updatedCapacity);
                    break;
                case 2:
                    System.out.println("Enter updated Room Type:");
                    String updatedRoomType = scanner.nextLine();
                    roomsController.updateRoomType(roomNumber, updatedRoomType);
                    break;
                case 3:
                    System.out.println("Enter updated Room Price:");
                    double updatedPrice = scanner.nextDouble();
                    scanner.nextLine();
                    roomsController.updateRoomPrice(roomNumber, updatedPrice);
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } else {
            System.out.println("Room with Number " + roomNumber + " does not exist.");
        }
    }

    private void deleteRoom() {
        System.out.println("Enter the Room Number to delete:");
        int roomNumber = scanner.nextInt();
        scanner.nextLine();

        if (roomsController.roomExists(roomNumber)) {
            roomsController.deleteRoom(roomNumber);
            System.out.println("Room deleted successfully!");
        } else {
            System.out.println("Room with Number " + roomNumber + " does not exist.");
        }
    }

    public void viewRoomStatus() {
        List<Rooms> vacantRooms = roomsController.getVacantRooms();
        if (vacantRooms.isEmpty()) {
            System.out.println("No vacant rooms available.");
        } else {
            System.out.println("Vacant Rooms:");
            for (Rooms room : vacantRooms) {
                System.out.println("Room Number: " + room.getRoomNumber());
                System.out.println("Capacity: " + room.getCapacity());
                System.out.println("Occupied: " + room.getOccupied());
                System.out.println("Room Type: " + room.getRoomType());
                System.out.println("Price: " + room.getPrice());
                System.out.println("------------------------------");
            }
        }
    }

    private void viewBookingStatus() {
        PaymentController paymentController = new PaymentController(connection, studentController, roomsController);
        PaymentView paymentView = new PaymentView(scanner, connection); // Instantiate PaymentView
        BookingView bookingView = new BookingView(bookingController, scanner, paymentController, paymentView); // Pass paymentView to BookingView
        bookingView.displayBookingStatus();
    }

    private void viewStudentRegistrationDetails() {
        List<StudentModel> students = studentController.getAllStudents();
        if (students == null || students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("Student Registration Details:");
            for (StudentModel student : students) {
                System.out.println("Name: " + student.getName());
                System.out.println("Age: " + student.getAge());
                System.out.println("Username: " + student.getUsername());
                System.out.println("host_type: "+student.getHost_type());
                System.out.println("------------------------");
            }
        }
    }
}
