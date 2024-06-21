package View;

import Controller.BookingController;
import Controller.PaymentController;
import Controller.RoomsController;
import Controller.StudentController;
import Model.StudentModel;
import Model.Rooms;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class StudentView {
    private RoomsController roomsController;
    private BookingController bookingController;
    private PaymentController paymentController;
    private Scanner scanner;
    private Connection connection;
    private StudentController studentController;
    private PaymentView paymentView;

    public StudentView(StudentController studentController, RoomsController roomsController,BookingController bookingController, PaymentController paymentController, Scanner scanner, Connection connection) {
        this.studentController = studentController;
        this.roomsController = roomsController;
        this.bookingController = bookingController;
        this.paymentController = paymentController;
        this.paymentView = new PaymentView(scanner, connection); 
        this.scanner = scanner;
        this.connection = connection;
    }

    public void displayMenu() throws SQLException {
        boolean exit = false;

        while (!exit) {
            System.out.println("-----------------Student Management---------------");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.println("Enter your choice:");

            int choice = scanner.nextInt();
            scanner.nextLine(); 
            switch (choice) {
                case 1:
                    selectUserType(); 
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    System.out.println("Exiting Student management.");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        }
    }


    private void selectUserType() throws SQLException {
        System.out.println("Are you an employee or a student?");
        System.out.println("1. Student");
        System.out.println("2. Employee");
        System.out.println("Enter your choice:");

        int choice = scanner.nextInt();
        scanner.nextLine(); 
        switch (choice) {
            case 1:
                studentLogin();
                break;
            case 2:
                employeeLogin();
                break;
            default:
                System.out.println("Invalid choice. Please enter 1 for Student or 2 for Employee.");
                break;
        }
    }

    private void studentLogin() throws SQLException {
        System.out.println("Enter username:");
        String username = scanner.nextLine();

        System.out.println("Enter password:");
        String password = scanner.nextLine();

        StudentModel student = studentController.getStudentByUsername(username);
        if (student != null && student.getPassword().equals(password)) {
            System.out.println("Login successful. Welcome, " + username + "!");

            boolean exit = false;

            while (!exit) {
                System.out.println("1. Edit Details");
                System.out.println("2. View Room Status");
                System.out.println("3. Book Room");
                System.out.println("4. Logout");
                System.out.println("Enter your choice:");

                int choice = 0;
                try {
                    choice = scanner.nextInt();
                    scanner.nextLine(); 
                } catch (NoSuchElementException e) {
                    System.out.println("Invalid input. Please try again.");
                    scanner.nextLine(); 
                    continue;
                } catch (IllegalStateException e) {
                    System.out.println("Scanner closed. Exiting...");
                    exit = true;
                    continue;
                }

                switch (choice) {
                    case 1:
                        editDetails(username);
                        break;
                    case 2:
                        viewRoomStatus();
                        break;
                    case 3:
                        BookingView bookingView = new BookingView(bookingController, scanner, paymentController, paymentView);
                        bookRoom(username, bookingView, roomsController);
                        break;
                    case 4:
                        System.out.println("Logging out...");
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                        break;
                }
            }
        } else {
            System.out.println("Login failed. Invalid credentials.");
        }
    }

    private void employeeLogin() throws SQLException {
        System.out.println("Enter username:");
        String username = scanner.nextLine();

        System.out.println("Enter password:");
        String password = scanner.nextLine();
        
        if (username.equals("admin") && password.equals("admin")) {
            System.out.println("Login successful. Welcome, " + username + "!");
    
            boolean exit = false;

            while (!exit) {
                System.out.println("1. Edit Details");
                System.out.println("2. View Room Status");
                System.out.println("3. Book Room");
                System.out.println("4. Logout");
                System.out.println("Enter your choice:");

                int choice = 0;
                try {
                    choice = scanner.nextInt();
                    scanner.nextLine(); 
                } catch (NoSuchElementException e) {
                    System.out.println("Invalid input. Please try again.");
                    scanner.nextLine(); 
                    continue;
                } catch (IllegalStateException e) {
                    System.out.println("Scanner closed. Exiting...");
                    exit = true;
                    continue;
                }

                switch (choice) {
                    case 1:
                        editDetails(username);
                        break;
                    case 2:
                        viewRoomStatus();
                        break;
                    case 3:
                        BookingView bookingView = new BookingView(bookingController, scanner, paymentController, paymentView);
                        bookRoom(username, bookingView, roomsController);
                        break;
                    case 4:
                        System.out.println("Logging out...");
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                        break;
                }
            }
        } else {
            System.out.println("Login failed. Invalid credentials.");
        }
    }

    private void register() {
        System.out.println("Enter name:");
        String name = scanner.nextLine();

        System.out.println("Enter age:");
        int age = scanner.nextInt();
        scanner.nextLine(); 

        System.out.println("Enter username:");
        String username = scanner.nextLine();

        System.out.println("Enter password:");
        String password = scanner.nextLine();

        System.out.println("Are you a student or an employee?");
        System.out.println("1. Student");
        System.out.println("2. Employee");
        System.out.println("Enter your choice:");
        int userType = scanner.nextInt();
        scanner.nextLine(); 

        String hostType = "";
        switch (userType) {
            case 1:
                hostType = "Student";
                break;
            case 2:
                hostType = "Employee";
                break;
            default:
                System.out.println("Invalid choice. Defaulting to Student.");
                hostType = "Student";
                break;
        }

        studentController.register(new StudentModel(0, name, age, username, password, hostType));
        System.out.println("Registration successful!");
    }

    

    private void viewRoomStatus() throws SQLException {
        List<Rooms> roomsList = roomsController.getAllRooms();
        for (Rooms room : roomsList) {
            System.out.println(room);
        }
    }

    private void bookRoom(String username, BookingView bookingView, RoomsController roomsController) throws SQLException {
        System.out.println("Enter Room Number:");
        int roomNumber = scanner.nextInt();
        scanner.nextLine();

        if (roomsController.isRoomAvailable(roomNumber)) {
            boolean bookingSuccess = bookingController.bookRoom(username, roomNumber);
            int host_ID = studentController.getHostIdByUsername(username);
            if (bookingSuccess) {
                int currentOccupancy = roomsController.getRoomOccupancy(roomNumber);
                int newOccupancy = currentOccupancy + 1;
                roomsController.updateRoom(roomNumber, newOccupancy);
                System.out.println("Room booked successfully.");
            } else {
                System.out.println("Failed to book room. Please try again later.");
            }
           
            bookingView.displayBookingOptions(roomNumber, username, host_ID);
        } else {
            System.out.println("Room is not available. Please choose another room.");
        }
    }

    private void editDetails(String username) throws SQLException {
   
        StudentModel student = studentController.getStudentByUsername(username);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Current Details:");
        System.out.println("Username: " + student.getUsername());
        System.out.println("Name: " + student.getName());
        System.out.println("Age: " + student.getAge());
        System.out.println("Hosteller Type: " + student.getHost_type());
        System.out.println("-------------------------");

        System.out.println("Enter the details you want to update:");
        System.out.println("1. Name");
        System.out.println("2. Age");
        System.out.println("3. Username");
        System.out.println("4. Hosteller Type");
        System.out.println("5. Cancel");
        System.out.println("Enter your choice:");

        int choice = scanner.nextInt();
        scanner.nextLine(); 

        switch (choice) {
            case 1:
                System.out.println("Enter new name:");
                String newName = scanner.nextLine();
                studentController.updateStudent(username, newName, student.getAge(), student.getHost_type());
                System.out.println("Name updated successfully!");
                break;
            case 2:
                System.out.println("Enter new age:");
                int newAge = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                studentController.updateStudent(username, student.getName(), newAge, student.getHost_type());
                System.out.println("Age updated successfully!");
                break;
            case 3:
                System.out.println("Enter new username:");
                String newUsername = scanner.nextLine();
                studentController.updateStudent(username, newUsername, student.getAge(), student.getHost_type());
                System.out.println("Username updated successfully!");
                break;
            case 4:
                System.out.println("Enter new hosteller type:");
                String newHostellerType = scanner.nextLine();
                studentController.updateStudent(username, student.getName(), student.getAge(), newHostellerType);
                System.out.println("Hosteller type updated successfully!");
                break;
            case 5:
                System.out.println("Cancelling update.");
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }
}